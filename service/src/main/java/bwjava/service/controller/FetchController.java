package bwjava.service.controller;

import bwjava.service.service.BeautyModelService;
import com.bwjava.util.ExecutorServiceUtil;
import org.springframework.web.bind.annotation.RequestMapping;
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
    private BeautyModelService beautyModelService;

    @RequestMapping("/fetchAll")
    public String listByPage() {
        ExecutorServiceUtil.getInstance().execute(() -> {
            beautyModelService.fetchAndSaveBeautyPics();
        });
        return "ok";
    }
}
