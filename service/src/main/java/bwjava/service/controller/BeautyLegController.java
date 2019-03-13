package bwjava.service.controller;

import bwjava.service.dto.ListPageVO;
import bwjava.service.entity.BeautyModelPic;
import bwjava.service.service.BeautyLegService;
import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 模特查询api
 *
 * @author chenjing
 * @date 2019-03-02 22:31
 */
@Log4j2
@RestController
@RequestMapping("/api/beautyleg")
public class BeautyLegController {

    @Resource
    private BeautyLegService beautyLegService;

    /**
     * 分页查询列表
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("/listPage")
    public List<ListPageVO> listByPage(@RequestParam int pageNum, @RequestParam int pageSize, @RequestParam(required = false) String searchContent) {
        List<ListPageVO> listPageVOS = beautyLegService.listPage(pageNum, pageSize, searchContent);
        log.info("listpagevos:{}", listPageVOS);
        return listPageVOS;
    }

    /**
     * 获取模特图片列表
     *
     * @param modelId
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/listModelPics")
    public String listModelPics(@RequestParam long modelId, @RequestParam int pageNum, @RequestParam int pageSize) {
        Page<BeautyModelPic> beautyModelPics = beautyLegService.listModelPics(modelId, pageNum, pageSize);
        return JSON.toJSONString(beautyModelPics);
    }
}
