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
    private static List<String> urls = new ArrayList<>();
    static {
        urls.add("https://www.meitulu.com/t/beautyleg/");
        for (int i = 2; i <= 22; i++) {
            urls.add("https://www.meitulu.com/t/beautyleg/" + i + ".html");
        }
    }

    private static BeautyListClient beautyListClient = new BeautyListClient(urls, 0);

    @Test
    public void testGetList(){
        BeautyListInfo modelPicUrls = beautyListClient.getModelPicUrls();
        System.out.println("end");
    }

}