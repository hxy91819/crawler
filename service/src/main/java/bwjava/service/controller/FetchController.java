package bwjava.service.controller;

import bwjava.service.service.FetchService;
import com.bwjava.util.ExecutorServiceUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author chenjing
 * @date 2019-03-07 22:50
 */
@RestController
@RequestMapping("/api/fetch")
public class FetchController {
    @Resource
    private FetchService fetchService;

    @RequestMapping("/fetchAndSaveBeautyPics")
    public String fetchAndSaveBeautyPics() {
        ExecutorServiceUtil.getInstance().execute(() -> {
            fetchService.fetchAndSaveBeautyPics();
        });
        return "ok";
    }

    @RequestMapping("/fetchAndSaveBeautyPicsByPage")
    public String fetchAndSaveBeautyPicsByPage(@RequestParam int pageNum, @RequestParam int pageSize) {
        fetchService.fetchAndSaveBeautyPicsByPage(pageNum, pageSize);
        return "ok";
    }
}
