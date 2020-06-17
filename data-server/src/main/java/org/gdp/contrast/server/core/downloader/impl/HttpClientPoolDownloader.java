package org.gdp.contrast.server.core.downloader.impl;
import lombok.extern.slf4j.Slf4j;
import org.gdp.contrast.server.core.downloader.Downloader;
import org.gdp.contrast.server.core.model.Page;
import org.gdp.contrast.server.core.model.UrlSeed;
import org.gdp.contrast.server.core.utils.HttpUtils;

/**
 * httpClient下载器
 * @author heshiyuan
 */
@Slf4j
public class HttpClientPoolDownloader implements Downloader {

    @Override
    public Page download(UrlSeed urlSeed) {
        log.info("【下载器】正在爬虫{}网站", urlSeed.getUrl());
        String html = HttpUtils.getInstance().get(urlSeed.getUrl());
        Page page = new Page(urlSeed, html);
        return page;
    }
}
