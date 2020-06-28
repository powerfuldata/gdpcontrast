package org.gdp.contrast.server.core.scheduler.impl;
import org.gdp.contrast.server.core.model.UrlSeed;
import org.gdp.contrast.server.core.scheduler.Scheduler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * @description <p>优先级调度器实现类</p>
 * @author heshiyuan
 */
public class PriorityQueueScheduler implements Scheduler {

    public static final int defaultPriority = 5;
    private Logger _logger = LoggerFactory.getLogger(getClass());

    private PriorityBlockingQueue<UrlSeed> priorityQueue = new PriorityBlockingQueue<UrlSeed>(defaultPriority, (o1, o2) ->
            -(Long.compare(o1.getPriority(), o2.getPriority()))
    );
    private Set<UrlSeed> urlSet = Collections.synchronizedSet(new HashSet<>());

    @Override
    public void push(UrlSeed urlSeed) {
        if (urlSeed.getUrl() == null
                || urlSeed.getUrl().trim().equals("")
                || urlSeed.getUrl().trim().equals("#")
                || urlSeed.getUrl().trim().toLowerCase().contains("javascript:"))
            return;
        if (urlSet.add(urlSeed)) {
            priorityQueue.add(urlSeed);
        } else {
            //            logger.info("UrlSeed重复:" + urlSeed.getUrl());

        }
    }

    @Override
    public UrlSeed poll() {
        return priorityQueue.poll();
    }
}
