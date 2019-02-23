package bwjava.dao.read;

import bwjava.entity.UserInfo;

public interface UserInfoReaderDao {
    UserInfo selectByPrimaryKey(Integer id);
}