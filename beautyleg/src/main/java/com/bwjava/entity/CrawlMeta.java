package com.bwjava.entity;

import com.google.common.base.Preconditions;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author chenjing
 * @date 2019-02-21 15:29
 */
@ToString
@AllArgsConstructor
public class CrawlMeta {

    /**
     * 需要抓取的开始url
     */
    @Getter
    @Setter
    private String url;

    /**
     * 需要抓取的元素（选择器）
     */
    @Setter
    private Set<List<String>> selectorRules;

    /**
     * 深度抓取时，需要过滤的链接（可选）
     */
    @Setter
    private Set<List<String>> selectorHrefs;

    /**
     * 域名
     */
    @Getter
    private String domain;

    public CrawlMeta(String url, Set<List<String>> selectorRules) {
        Preconditions.checkArgument(url.startsWith("https"));
        // 自动填充domain
        this.domain = url.substring(0, url.indexOf(".com") + ".com".length());
        this.url = url;
        this.selectorRules = selectorRules;
    }

    public Set<List<String>> getSelectorRules() {
        return selectorRules != null ? selectorRules : new HashSet<>();
    }

    public Set<List<String>> getSelectorHrefs() {
        return selectorHrefs != null ? selectorHrefs : new HashSet<>();
    }
}
