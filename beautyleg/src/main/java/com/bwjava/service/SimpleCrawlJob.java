package com.bwjava.service;

import com.bwjava.entity.CrawlHttpConf;
import com.bwjava.entity.CrawlMeta;
import com.bwjava.entity.CrawlResult;
import com.bwjava.util.HttpUtils;
import com.google.common.base.Preconditions;
import lombok.Getter;
import lombok.Setter;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.*;

/**
 * @author chenjing
 * @date 2019-02-21 15:35
 */
@Getter
@Setter
public class SimpleCrawlJob extends AbstractJob {
    /**
     * 配置项信息
     */
    private CrawlMeta crawlMeta;
    /**
     * 存储爬取的结果
     */
    private CrawlResult crawlResult;

    /**
     * HTTP头配置信息
     */
    private CrawlHttpConf crawlHttpConf = new CrawlHttpConf();

    /**
     * 批量查询的结果
     */
    private List<CrawlResult> crawlResults = new ArrayList<>();


    /**
     * 爬网页的深度, 默认为0， 即只爬取当前网页
     */
    private int depth = 0;


    /**
     * 执行抓取网页
     *
     * @throws Exception
     */
    @Override
    public void doFetchPage() throws Exception {
        Preconditions.checkArgument(!this.crawlMeta.getSelectorRules().isEmpty());
        doFetchNextPage(0, this.crawlMeta.getUrl());
        this.crawlResult = this.crawlResults.get(0);
    }

    private void doFetchNextPage(int currentDepth, String url) throws Exception {
        // url 补偿，部分url的
        final String httpHeader = "https://";
        if (!url.startsWith(httpHeader)) {
            url = this.crawlMeta.getDomain() + url;
        }

        HttpResponse response = HttpUtils.request(new CrawlMeta(url, this.crawlMeta.getSelectorRules()), crawlHttpConf);
        Preconditions.checkArgument(response != null);
        String res = EntityUtils.toString(response.getEntity(), "UTF-8");
        CrawlResult result;
        if (response.getStatusLine().getStatusCode() != 200) {
            result = new CrawlResult();
            result.setStatus(response.getStatusLine().getStatusCode(), response.getStatusLine().getReasonPhrase());
            result.setUrl(crawlMeta.getUrl());
            this.crawlResults.add(result);
            return;
        }
        result = doParse(res);
        this.crawlResults.add(result);

        // 超过最大深度， 不继续爬
        if (currentDepth >= depth) {
            return;
        }

        // 选择出需要继续fetch的a标签，如果选择器长度小于1，则不需要进行深度爬取
        Set<List<String>> selectorHrefs = crawlMeta.getSelectorHrefs();
        for (List<String> selectorHref : selectorHrefs) {
            Document doc = result.getHtmlDoc();
            Elements elements = doDocumentFilter(selectorHref, doc);
            for (Element element : elements) {
                doFetchNextPage(currentDepth + 1, element.attr("href"));
            }
        }
    }

    private CrawlResult doParse(String html) {
        Document doc = Jsoup.parse(html);
        Map<List<String>, List<String>> map = new HashMap<>(crawlMeta.getSelectorRules().size());
        for (List<String> rule : crawlMeta.getSelectorRules()) {
            Elements elements = doDocumentFilter(rule, doc);
            List<String> list = new ArrayList<>();
            for (Element element : elements) {
                switch (element.tag().getName()) {
                    // img标签，则提取src
                    case "img":
                        list.add(element.attr("src"));
                        break;
                    // a 标签，提取文字
                    case "a":
                        list.add(element.text());
                    default:
                        break;
                }
            }

            if (list.isEmpty()) {
                continue;
            }
            map.put(rule, list);
        }

        CrawlResult result = new CrawlResult();
        result.setHtmlDoc(doc);
        result.setUrl(crawlMeta.getUrl());
        result.setResult(map);
        result.setStatus(CrawlResult.SUCCESS);
        return result;
    }

    /**
     * 根据list中的规则，按次序过滤元素
     *
     * @param rule 规则列表；按次序
     * @param doc  需要过滤的文档
     * @return 过滤之后的元素列表
     */
    private Elements doDocumentFilter(Collection<String> rule, Document doc) {
        Preconditions.checkArgument(!rule.isEmpty());
        Iterator<String> iterator = rule.iterator();
        Elements select = doc.select(iterator.next());
        while (iterator.hasNext()) {
            select = select.select(iterator.next());
        }
        return select;
    }
}
