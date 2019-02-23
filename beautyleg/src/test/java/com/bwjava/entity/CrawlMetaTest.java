package com.bwjava.entity;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;

/**
 * @author chenjing
 * @date 2019-02-22 21:20
 */
public class CrawlMetaTest {
    @Test
    public void testCreate() {
        CrawlMeta crawlMeta = new CrawlMeta("https://www.chenjingtalk.com/xxxxx", new HashSet<>());
        Assert.assertEquals("https://www.chenjingtalk.com", crawlMeta.getDomain());
    }
}