package org.gdp.contrast.server.core.storager.impl;
import com.alibaba.fastjson.JSONArray;
import lombok.extern.slf4j.Slf4j;
import org.gdp.contrast.server.core.model.Page;
import org.gdp.contrast.server.core.storager.Saver;

import java.util.Map;

/**
 * @description <p>控制台存储器，将爬虫内容输出在控制台上，此时logback.xml日志输出级别设置成error</p>
 * @author heshiyuan
 */
@Slf4j
public class ConsoleSaver implements Saver {

    @Override
    public Page save(Page page) {
        log.info("【保存器】...");
        Map<Object, Object> map = page.getItems();
        map.forEach((k, v) -> {
            log.info("【保存器】{}", JSONArray.toJSONString(v));
        });
        return page;
    }

}
