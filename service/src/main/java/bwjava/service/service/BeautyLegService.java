package bwjava.service.service;

import bwjava.service.dao.read.BeautyModelPicReaderDao;
import bwjava.service.dao.read.BeautyModelReaderDao;
import bwjava.service.entity.BeautyModel;
import bwjava.service.entity.BeautyModelPic;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
    public Page<BeautyModel> listPage(int pageNum, int pageSize) {
        return PageHelper.startPage(pageNum, pageSize).doSelectPage(() -> beautyModelReaderDao.selectEntranceurlThumbpic());
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
        return PageHelper.startPage(pageNum, pageSize).doSelectPage(() -> beautyModelPicReaderDao.selectPicUrls(modelId));
    }
}
