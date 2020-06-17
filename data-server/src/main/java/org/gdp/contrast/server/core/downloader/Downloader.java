package org.gdp.contrast.server.core.downloader;

import org.gdp.contrast.server.core.model.Page;
import org.gdp.contrast.server.core.model.UrlSeed;

/**
 * 下载器
 * @author heshiyuan
 */
public interface Downloader {

    /**
     * 下载器
     * @param urlSeed 下载url页面
     */
    Page download(UrlSeed urlSeed);
}
