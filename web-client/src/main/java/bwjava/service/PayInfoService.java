package bwjava.service;

import bwjava.entity.PayInfo;

public interface PayInfoService {
    PayInfo selectByPrimaryKey(Long id);

    int insertSelective(PayInfo record);

    int deleteByPrimaryKey(Long id);
}
