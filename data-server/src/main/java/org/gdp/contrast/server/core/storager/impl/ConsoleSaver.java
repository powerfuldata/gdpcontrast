package org.gdp.contrast.server.core.storager.impl;
import lombok.extern.slf4j.Slf4j;
import org.gdp.contrast.server.core.model.Page;
import org.gdp.contrast.server.core.storager.Saver;

import java.util.Map;

/**
 * @description <p>控制台存储器，将爬虫内容输出在控制台上，此时logback.xml日志输出级别设置成error</p>
 * @author heshiyuan
 * @date 22/09/2017 2:26 PM
 * @email shiyuan4work@sina.com
 * @github https://github.com/shiyuan2he.git
 * Copyright (c) 2016 shiyuan4work@sina.com All rights reserved
 */
@Slf4j
public class ConsoleSaver implements Saver {

    @Override
    public Page save(Page page) {
        Map<Object, Object> map = page.getItems();
        map.forEach((k, v) -> log.info(k + " : " + v));
        return page;
    }

}
