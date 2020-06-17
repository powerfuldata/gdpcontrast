package org.gdp.contrast.server.core.model;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.helper.StringUtil;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author heshiyuan
 * @description <p>爬虫抓页面模型</p>
 */
@Slf4j
public class Page {

    /**
     * 该页面的url信息
     */
    private UrlSeed urlSeed;
    /**
     * 该页面的jsoup文档，设置了baseUrl
     */
    private Document document;
    /**
     * 新种子
     */
    private List<UrlSeed> newUrlSeed;
    /**
     * 待存储的json
     */
    private Map<Object, Object> items;


    public static Page create() {
        return new Page();
    }

    public Page(UrlSeed urlSeed, String html) {
        newUrlSeed = new ArrayList<>();
        items = new HashMap<>();
        this.urlSeed = urlSeed;
        this.document = Jsoup.parse(html, urlSeed.getUrl());
    }

    public Page() {
        newUrlSeed = new ArrayList<>();
        items = new HashMap<>();
    }

    public List<UrlSeed> links() {
        List<UrlSeed> result = new ArrayList<>();
        Elements elements = document.select("a");
        List<String> links = new ArrayList<>(elements.size());
        for (Element element0 : elements) {
            if (!StringUtil.isBlank(element0.baseUri())) {
                links.add(element0.attr("abs:href"));
            } else {
                links.add(element0.attr("href"));
            }
        }
        links.forEach(str -> {
            result.add(new UrlSeed(5, str));
        });
        return result;
    }

    public Page setNewUrlSeed(List<UrlSeed> newUrlSeed) {
        this.newUrlSeed = newUrlSeed;
        return this;
    }

    public void addNewUrlSeed(String url, long priority) {
        newUrlSeed.add(new UrlSeed(priority, url));
    }

    public void addNewUrlSeed(UrlSeed urlSeed) {
        newUrlSeed.add(urlSeed);
    }

    public void addNewUrlSeed(String url) {
        newUrlSeed.add(new UrlSeed(5, url));
    }

    public UrlSeed getUrlSeed() {
        return urlSeed;
    }

    public Page setUrlSeed(UrlSeed urlSeed) {
        this.urlSeed = urlSeed;
        return this;
    }

    public Document getDocument() {
        return document;
    }

    public Page setDocument(Document document) {
        this.document = document;
        return this;
    }

    public Map<Object, Object> getItems() {
        return items;
    }

    public Page setItems(Map items) {
        this.items = items;
        return this;
    }

    public List<UrlSeed> getNewUrlSeed() {
        return newUrlSeed;
    }
}
