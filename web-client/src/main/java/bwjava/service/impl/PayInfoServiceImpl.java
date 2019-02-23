package bwjava.service.impl;

import bwjava.dao.read.PayInfoReaderDao;
import bwjava.dao.write.PayInfoWriterDao;
import bwjava.entity.PayInfo;
import bwjava.service.PayInfoService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class PayInfoServiceImpl implements PayInfoService {
    @Resource
    private PayInfoReaderDao payInfoReaderDao;
    @Resource
    private PayInfoWriterDao payInfoWriterDao;

    @Override
    public PayInfo selectByPrimaryKey(Long id) {
        return payInfoReaderDao.selectByPrimaryKey(id);
    }

    @Override
    public int insertSelective(PayInfo record) {
        return payInfoWriterDao.insertSelective(record);
    }

    @Override
    public int deleteByPrimaryKey(Long id) {
        return payInfoWriterDao.deleteByPrimaryKey(id);
    }
}
