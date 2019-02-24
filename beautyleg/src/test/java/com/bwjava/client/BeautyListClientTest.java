package com.bwjava.client;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author chenjing
 * @date 2019-02-24 16:03
 */
public class BeautyListClientTest {

    BeautyListClient beautyListClient = new BeautyListClient("https://www.meitulu.com/t/beautyleg/", 0);

    @Test
    public void testGetList(){
        beautyListClient.getModelPicUrls();
    }

}