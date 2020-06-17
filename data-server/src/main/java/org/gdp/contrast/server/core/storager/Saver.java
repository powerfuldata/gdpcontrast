package org.gdp.contrast.server.core.storager;

import org.gdp.contrast.server.core.model.Page;

/**
 * @description <p>存储器接口定义</p>
 * @author heshiyuan
 */
public interface Saver {
    /**
     * @description <p>存储器</p>
     * @param page 定义需要存储的页面内容
     */
    Page save(Page page);
}
