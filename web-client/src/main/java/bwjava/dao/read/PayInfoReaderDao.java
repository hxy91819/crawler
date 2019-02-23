package bwjava.dao.read;

import bwjava.entity.PayInfo;

public interface PayInfoReaderDao {
    PayInfo selectByPrimaryKey(Long id);
}