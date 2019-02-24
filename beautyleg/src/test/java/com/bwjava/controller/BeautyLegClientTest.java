package com.bwjava.controller;

import org.junit.Test;

/**
 * @author chenjing
 * @date 2019-02-24 15:25
 */
public class BeautyLegClientTest {
    private BeautyLegClient client = new BeautyLegClient();

    private String url = "https://www.meitulu.com/item/15665.html";

    @Test
    public void testGetModelInfo() {
        client.getModelInfo(url);
    }

    @Test
    public void testGetPics() {
        client.getPicUrls(url, 48);
    }
}