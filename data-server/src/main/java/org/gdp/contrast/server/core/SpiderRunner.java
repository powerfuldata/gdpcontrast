package org.gdp.contrast.server.core;
import org.gdp.contrast.server.core.downloader.impl.HttpClientPoolDownloader;
import org.gdp.contrast.server.core.resolver.impl.GdpPageResolver;
import org.gdp.contrast.server.core.resolver.impl.TextPageResolver;
import org.gdp.contrast.server.core.scheduler.impl.QueueScheduler;
import org.gdp.contrast.server.core.storager.impl.ConsoleSaver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * @author heshiyuan
 * @date 2020/6/15 18:50
 */
@Component
public class SpiderRunner  {
//    public static final String url = "https://data.worldbank.org.cn/indicator/NY.GDP.MKTP.CD?most_recent_value_desc=true&view=chart" ;
//    public static final String url = "https://www.worldometers.info/gdp/gdp-by-country/" ;
    public static final String url = "http://statisticstimes.com/economy/countries-by-gdp.php" ;
    public static final String regexRule = "+http://.*.jianshu.com/.*";

    @Autowired
    GdpPageResolver gdpPageResolver;


//    @Override
    public void run(String... args) throws Exception {
//        Spider.build()
//            .setScheduler(new QueueScheduler())
//            .setDownloader(new HttpClientPoolDownloader())
//            .setProcessor(gdpPageResolver)
//            .setSaver(new ConsoleSaver())
//            .thread(5)
//            .addUrlSeed(url)
////            .addRegexRule(regexRule)
//            .run();
    }
}
