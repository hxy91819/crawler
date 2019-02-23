package bwjava.service.impl;

import bwjava.dao.read.UserInfoReaderDao;
import bwjava.entity.UserInfo;
import bwjava.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    @Autowired
    private UserInfoReaderDao userInfoDao;

    @Override
    public UserInfo selectByPrimaryKey(Integer id) {
        return userInfoDao.selectByPrimaryKey(id);
    }
}
