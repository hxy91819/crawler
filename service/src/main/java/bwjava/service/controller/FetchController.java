package bwjava.service.controller;

import bwjava.service.service.FetchService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

/**
 * 管理类，不开放到外网
 *
 * @author chenjing
 * @date 2019-03-07 22:50
 */
@RestController
@RequestMapping("/manage/fetch")
public class FetchController {
    @Resource
    private FetchService fetchService;

    @RequestMapping("/fetchAndSaveBeautyList")
    public String fetchAndSaveBeautyList(@RequestParam String url) {
        try {
            url = URLDecoder.decode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        fetchService.fetchAndSaveBeautyList(url);
        return "ok";
    }

    @RequestMapping("/fetchAndSaveBeautyPics")
    public String fetchAndSaveBeautyPics(@RequestParam String org) {
        try {
            org = URLDecoder.decode(org, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        fetchService.fetchAndSaveBeautyPics(org);
        return "ok";
    }

    @RequestMapping("/fetchAndSaveBeautyPicsByPage")
    public String fetchAndSaveBeautyPicsByPage(@RequestParam String org, @RequestParam int pageNum, @RequestParam int pageSize) {
        try {
            org = URLDecoder.decode(org, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        fetchService.fetchAndSaveBeautyPicsByPage(org, pageNum, pageSize);
        return "ok";
    }
}
