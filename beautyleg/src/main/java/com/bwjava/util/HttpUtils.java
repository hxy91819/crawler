package com.bwjava.util;

import com.bwjava.entity.CrawlHttpConf;
import com.bwjava.entity.CrawlMeta;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.ssl.SSLContextBuilder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author chenjing
 * @date 2019-02-21 16:26
 */
public class HttpUtils {
    public static HttpResponse request(CrawlMeta crawlMeta, CrawlHttpConf httpConf) throws Exception {
        switch (httpConf.getMethod()) {
            case GET:
                return doGet(crawlMeta, httpConf);
            case POST:
                return doPost(crawlMeta, httpConf);
            default:
                return null;
        }
    }


    private static HttpResponse doGet(CrawlMeta crawlMeta, CrawlHttpConf httpConf) throws Exception {
        SSLContextBuilder builder = new SSLContextBuilder();
        //         全部信任 不做身份鉴定
        builder.loadTrustMaterial(null, (x509Certificates, s) -> true);
        HttpClient httpClient = HttpClientBuilder.create().setSSLContext(builder.build()).build();

        // 设置请求参数
        StringBuilder param = new StringBuilder(crawlMeta.getUrl()).append("?");
        for (Map.Entry<String, Object> entry : httpConf.getRequestParams().entrySet()) {
            param.append(entry.getKey())
                    .append("=")
                    .append(entry.getValue())
                    .append("&");
        }

        // 过滤掉最后一个无效字符
        HttpGet httpGet = new HttpGet(param.substring(0, param.length() - 1));

        // 设置请求头
        for (Map.Entry<String, String> head : httpConf.getRequestHeaders().entrySet()) {
            httpGet.addHeader(head.getKey(), head.getValue());
        }

        // 执行网络请求
        return httpClient.execute(httpGet);
    }


    private static HttpResponse doPost(CrawlMeta crawlMeta, CrawlHttpConf httpConf) throws Exception {
        SSLContextBuilder builder = new SSLContextBuilder();
//         全部信任 不做身份鉴定
        builder.loadTrustMaterial(null, (x509Certificates, s) -> true);
        HttpClient httpClient = HttpClientBuilder.create().setSSLContext(builder.build()).build();

        HttpPost httpPost = new HttpPost(crawlMeta.getUrl());


        // 建立一个NameValuePair数组，用于存储欲传送的参数
        List<NameValuePair> params = new ArrayList<>();
        for (Map.Entry<String, Object> param : httpConf.getRequestParams().entrySet()) {
            params.add(new BasicNameValuePair(param.getKey(), param.getValue().toString()));
        }

        httpPost.setEntity(new UrlEncodedFormEntity(params, HTTP.UTF_8));


        // 设置请求头
        for (Map.Entry<String, String> head : httpConf.getRequestHeaders().entrySet()) {
            httpPost.addHeader(head.getKey(), head.getValue());
        }

        return httpClient.execute(httpPost);
    }
}
