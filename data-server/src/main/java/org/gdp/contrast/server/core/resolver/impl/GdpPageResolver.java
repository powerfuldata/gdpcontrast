package org.gdp.contrast.server.core.resolver.impl;
import lombok.extern.slf4j.Slf4j;
import org.gdp.contrast.server.core.model.Page;
import org.gdp.contrast.server.core.resolver.PageResolver;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @description <p>gdp页面抓取</p>
 * @author heshiyuan
 */
@Slf4j
public class GdpPageResolver implements PageResolver {

    @Override
    public Page process(Page page) {
        log.info("【解析器】开始解析页面");
        Document htmlDoc = page.getDocument();
        try {
            Element table = htmlDoc.getElementById("table_id");
            Elements elements = table.getElementsByTag("tbody");
            elements.forEach(data -> {
                data.getElementsByTag("tr").forEach(
                    trEle -> {
                        log.info("{},{},{},{}",
                                trEle.getElementsByIndexEquals(0).text(),
                                trEle.getElementsByIndexEquals(1).text(),
                                trEle.getElementsByIndexEquals(2).text(),
                                trEle.getElementsByIndexEquals(3).text()
                        );
                    }
                );
            });
            //用来存放爬取到的信息，供之后存储！map类型的即可，可以自定义各种嵌套！
            Map<String, String> items = new HashMap<>();
            //放入items中，之后会自动保存（如果你自己实现了下载器，请自己操作它。如下我自定义了自己的下载器，并将它保存到了文本中！）！
            page.setItems(items);
        } catch (NullPointerException e) {
            log.info("该页面没有解析到相关东西！跳过");
        }

        return page;
    }

    /**
     * 页面中解析出来的地址降低优先级
     */
    @Override
    public Page processNewUrlSeeds(Page page) {
        page.setNewUrlSeed(page.getNewUrlSeed().stream().map(urlSeed -> {urlSeed.setPriority(1);return urlSeed;}).collect(Collectors.toList()));
        return page;
    }
}
