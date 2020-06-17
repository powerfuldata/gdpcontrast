package org.gdp.contrast.server.core;

import lombok.extern.slf4j.Slf4j;
import org.gdp.contrast.server.core.downloader.Downloader;
import org.gdp.contrast.server.core.downloader.impl.HttpClientPoolDownloader;
import org.gdp.contrast.server.core.model.Page;
import org.gdp.contrast.server.core.model.RegexRule;
import org.gdp.contrast.server.core.model.UrlSeed;
import org.gdp.contrast.server.core.resolver.PageResolver;
import org.gdp.contrast.server.core.resolver.impl.TextPageResolver;
import org.gdp.contrast.server.core.scheduler.Scheduler;
import org.gdp.contrast.server.core.scheduler.impl.QueueScheduler;
import org.gdp.contrast.server.core.storager.Saver;
import org.gdp.contrast.server.core.storager.impl.ConsoleSaver;
import org.gdp.contrast.server.core.utils.TimeSleep;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author heshiyuan
 * @description <p>爬虫启动器</p>
 */
@Slf4j
public class Spider {
    /**
     * 调度器
     */
    private Scheduler scheduler;
    /**
     * 下载器
     */
    private Downloader downloader;
    /**
     * 页面解析器
     */
    private PageResolver pageProcessor;
    /**
     * 存储器
     */
    private Saver saver;

    /**
     * 新种子的过滤器，只有通过正则的，才会加入到待爬取种子队列
     */
    private RegexRule regexRule;
    /**
     * 线程池大小。默认5个爬虫在进行。
     */
    private int threadNum = 5;
    private ThreadPoolExecutor pool;

    /**
     * 最多几个爬虫在进行。
     * 默认5个。
     *
     * @param threadNum
     * @return 自己
     */
    public Spider thread(int threadNum) {
        this.threadNum = threadNum;
        if (threadNum <= 0) {
            this.threadNum = 5;
        }
        /**
         * corePoolSize 线程池线程总数量
         * maximumPoolSize 线程池最大数量
         * keepAliveTime，线程存活时间，1.5秒
         * unit 时间单位，毫秒
         * workQueue 线程执行队列
         */
        pool = new ThreadPoolExecutor(
                threadNum,
                threadNum,
                1500L,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(),
                new ThreadFactory() {

                    SecurityManager s = System.getSecurityManager();
                    // 添加线程组
                    private final ThreadGroup group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();

                    private final AtomicInteger poolNumber = new AtomicInteger(1);
                    private final AtomicInteger threadNumber = new AtomicInteger(1);
                    private final String namePrefix = "pool-" + poolNumber.getAndIncrement() + "-thread-";

                    @Override
                    public Thread newThread(Runnable runnable) {
                        // 线程池新线程
                        Thread thread = new Thread(group, runnable,namePrefix + threadNumber.getAndIncrement(), 0);
                        log.info("正在new一个线程，线程(ThreadName:{})：", Thread.currentThread().getName());
                        return thread ;
                    }
                }
        );
        return this;
    }

    public static Spider build() {
        return new Spider();
    }

    public Spider() {
        setDefaultComponents();
        regexRule = new RegexRule();
    }

    public Spider setScheduler(Scheduler scheduler) {
        this.scheduler = scheduler;
        return this;
    }

    public Spider setDownloader(Downloader d) {
        this.downloader = d;
        return this;
    }

    public Spider setProcessor(PageResolver p) {
        this.pageProcessor = p;
        return this;
    }

    public Spider setSaver(Saver s) {
        this.saver = s;
        return this;
    }

    /**
     * 添加初始化种子，可以多个
     *
     * @param url
     * @return Spider
     */
    public Spider addUrlSeed(String url) {
        scheduler.push(new UrlSeed(url));
        return this;
    }

    /**
     * 添加新种子需要满足的正则信息（正则规则有两种，正正则和反正则）
     * <p>
     * URL符合正则规则需要满足下面条件：
     * 1.至少能匹配一条正正则
     * 2.不能和任何反正则匹配
     * 举例：
     * 正正则示例：+a.*c是一条正正则，正则的内容为a.*c，起始加号表示正正则
     * 反正则示例：-a.*c时一条反正则，正则的内容为a.*c，起始减号表示反正则
     * 如果一个规则的起始字符不为加号且不为减号，则该正则为正正则，正则的内容为自身
     * 例如a.*c是一条正正则，正则的内容为a.*c
     *
     * @param regex 正则
     * @return Spider
     */
    public Spider addRegexRule(String regex) {
        regexRule.addRule(regex);
        return this;
    }

    private Spider setDefaultComponents() {
        thread(threadNum);

        if (scheduler == null) {
            scheduler = new QueueScheduler();
        }
        if (downloader == null) {
            downloader = new HttpClientPoolDownloader();
        }
        if (pageProcessor == null) {
            pageProcessor = new TextPageResolver();
        }
        if (saver == null) {
            saver = new ConsoleSaver();
        }
        return this;
    }

    public void run() {
        log.info("【启动器】爬虫启动!");
        UrlSeed urlSeed = null;
        while (true) {
            TimeSleep.sleep(10000);
            log.info("【启动器】当前线程池已完成:{}   运行中：{}  最大运行:{} 等待队列:{}",
                    pool.getCompletedTaskCount(),pool.getActiveCount(),pool.getPoolSize(),pool.getQueue().size());
            if (pool.getQueue().size() > pool.getCorePoolSize()) {
                //如果等待队列大于了100.就暂停接收新的url。不然会影响优先级队列的使用。
                TimeSleep.sleep(1000);
                continue;
            }
            urlSeed = scheduler.poll();
            if (urlSeed == null && pool.getActiveCount() == 0) {
                pool.shutdown();
                try {
                    pool.awaitTermination(10, TimeUnit.SECONDS);
                } catch (InterruptedException e) {
                    log.error("【启动器】关闭线程池失败！", e);
                }
                log.info("【启动器】爬虫结束！");
                break;
            } else if (urlSeed == null) {
                //没有取到种子就等待!
                TimeSleep.sleep(1000);
            } else {
                log.info("【启动器】正在处理:" + urlSeed.getUrl() + "  优先级(默认:5):" + urlSeed.getPriority());
                pool.execute(new SpiderWork(urlSeed.clone()));
            }
        }

    }
    /**
     * @description <p>爬虫执行器</p>
     * @author heshiyuan
     */
    class SpiderWork implements Runnable {

        private UrlSeed urlSeed;
        SpiderWork(UrlSeed urlSeed) {
            this.urlSeed = urlSeed;
        }

        @Override
        public void run() {
            log.info("【执行器】线程:[" + Thread.currentThread().getName() + "]正在处理:" + urlSeed.getUrl());
            log.info("【执行器】当前线程池已完成:{}   运行中：{}  最大运行:{} 等待队列:{}",
                    pool.getCompletedTaskCount(),pool.getActiveCount(),pool.getPoolSize(),pool.getQueue().size());

            //下载器-下载指定页面
            Page nowPage = downloader.download(urlSeed);

            //解析器-解析页面
            pageProcessor.process(nowPage);

            //正则处理-过滤不满足规则的地址
            List<UrlSeed> urlSeedList = nowPage.links();
            for (Iterator<UrlSeed> it = urlSeedList.iterator(); it.hasNext(); ) {
                UrlSeed seed = it.next();
                if (!regexRule.regex(seed.getUrl())) {
                    log.info("【执行器】{}不满足正则规则,移除此网址。",seed.getUrl());
                    it.remove();
                }else{
                    log.info("【执行器】解析到新网址，{}", seed.getUrl());
                }
            }
            // 设置新的url种子
//            nowPage.setNewUrlSeed(urlSeedList);
            // 递归解析新地址里的页面
//            pageProcessor.processNewUrlSeeds(nowPage);

            // 重新调度
//            nowPage.getNewUrlSeed().forEach(seed -> scheduler.push(seed));
            // 存储器保存结果
            saver.save(nowPage);
        }
    }
}

