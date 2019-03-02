package bwjava.service.controller;

import bwjava.service.entity.BeautyModel;
import bwjava.service.service.BeautyModelService;
import com.alibaba.fastjson.JSON;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author chenjing
 * @date 2019-03-02 22:31
 */
@RestController
@RequestMapping("/api/beautyleg")
public class BeautyLegController {
    @Resource
    private BeautyModelService beautyModelService;

    @RequestMapping("/listbypage")
    public String listByPage(@RequestParam int pageNum, @RequestParam int pageSize) {
        List<BeautyModel> beautyModels = beautyModelService.listAll(pageNum, pageSize);
        return JSON.toJSONString(beautyModels);
    }
}
