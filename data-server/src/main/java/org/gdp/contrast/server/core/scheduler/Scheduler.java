package org.gdp.contrast.server.core.scheduler;

import org.gdp.contrast.server.core.model.UrlSeed;

/**
 * 调度器
 * 我们可以自定义调度器。
 * 需要自己实现去重功能！
 * @author heshiyuan
 */
public interface Scheduler {

    /**
     * 写进去url种子
     * @param urlSeed url种子
     * @author heshiyuan
     */
    void push(UrlSeed urlSeed);

    /**
     * 拉出url种子
     * @return UrlSeed url种子
     * @author heshiyuan
     */
    UrlSeed poll();
}
