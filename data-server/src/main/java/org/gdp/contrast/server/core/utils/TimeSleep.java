package org.gdp.contrast.server.core.utils;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @author heshiyuan
 */
@Slf4j
public class TimeSleep {
    /**
     * 睡眠等待
     * @param milliseconds 毫秒
     */
    public static void sleep(long milliseconds) {
        try {
            TimeUnit.MILLISECONDS.sleep(1500);
        } catch (InterruptedException e) {
            log.error("该线程无法获取到种子了（意味着线程正常结束！或者出错！）", e);
        }
    }

}
