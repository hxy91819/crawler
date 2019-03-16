package com.bwjava.client;

import com.bwjava.dto.BeautyListInfo;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chenjing
 * @date 2019-02-24 16:03
 */
public class BeautyListClientTest {

    private static BeautyListClient beautyListClient = new BeautyListClient("https://www.meituri.com/x/63/");

    @Test
    public void testGetList() {
        BeautyListInfo modelPicUrls = beautyListClient.getModelPicUrls();
        System.out.println("end");
    }

    @Test
    public void getSuiteCount() {
        beautyListClient.getSuiteCount();
    }

    @Test
    public void test(){
        double ceil = Math.ceil((double)450 /(double) 40);
        System.out.println(ceil);
    }
}