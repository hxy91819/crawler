package bwjava.service;

import bwjava.entity.UserInfo;

public interface UserInfoService {
    UserInfo selectByPrimaryKey(Integer id);
}
