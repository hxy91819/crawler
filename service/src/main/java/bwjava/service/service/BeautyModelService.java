package bwjava.service.service;

import bwjava.service.dao.write.BeautyModelWriterDao;
import bwjava.service.entity.BeautyModel;
import bwjava.util.SnowFlake;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author chenjing
 * @date 2019-02-24 11:57
 */
@Service
public class BeautyModelService {

    @Resource
    private BeautyModelWriterDao beautyModelWriterDao;

    private SnowFlake snowFlake = new SnowFlake(1, 1);

    public int save() {
        BeautyModel beautyModel = new BeautyModel();
        beautyModel.setId(snowFlake.nextId());
        beautyModel.setModelName("test");
        return beautyModelWriterDao.insertSelective(beautyModel);
    }
}
