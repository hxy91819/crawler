package bwjava.service.service;

import bwjava.service.dao.read.BeautyModelPicReaderDao;
import bwjava.service.dao.read.BeautyModelReaderDao;
import bwjava.service.dto.ListPageVO;
import bwjava.service.entity.BeautyModel;
import bwjava.service.entity.BeautyModelPic;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 模特查询服务
 *
 * @author chenjing
 * @date 2019-03-08 21:54
 */
@Service
public class BeautyLegService {

    @Resource
    private BeautyModelReaderDao beautyModelReaderDao;

    @Resource
    private BeautyModelPicReaderDao beautyModelPicReaderDao;

    /**
     * 分页查询模特列表
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    public List<ListPageVO> listPage(String org, int pageNum, int pageSize, String searchContent) {
        if (!StringUtils.isEmpty(searchContent)) {
            searchContent = "%" + searchContent + "%";
        }
        String finalSearchContent = searchContent;
        Page<BeautyModel> listPageVOS = PageHelper.startPage(pageNum, pageSize)
                .doSelectPage(() -> beautyModelReaderDao.selectEntranceurlThumbpic(org, finalSearchContent));
        return listPageVOS.stream().map(x -> {
            ListPageVO listPageVO = new ListPageVO();
            BeanUtils.copyProperties(x, listPageVO);
            listPageVO.setModelId(String.valueOf(x.getId()));
            return listPageVO;
        }).collect(Collectors.toList());
    }

    /**
     * 分页查询模特图片
     *
     * @param modelId
     * @param pageNum
     * @param pageSize
     * @return
     */
    public Page<BeautyModelPic> listModelPics(long modelId, int pageNum, int pageSize) {
        return PageHelper.startPage(pageNum, pageSize)
                .doSelectPage(() -> beautyModelPicReaderDao.selectPicUrls(modelId));
    }

    /**
     * 按摄影机构分组
     * @return
     */
    public List<BeautyModel> listGroupByOrg(){
        return beautyModelReaderDao.selectGroupbyOrg();
    }
}
