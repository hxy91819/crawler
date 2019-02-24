package com.bwjava.client;

import org.junit.Test;

/**
 * @author chenjing
 * @date 2019-02-24 15:25
 */
public class BeautyLegClientTest {
    private String url = "https://www.meitulu.com/item/15665.html";

    private BeautyPicClient client = new BeautyPicClient(4, url);
    @Test
    public void testGetModelInfo() {
        client.getModelInfo();
    }

    @Test
    public void testGetPics() {
        client.getPicUrls(48);
    }
}