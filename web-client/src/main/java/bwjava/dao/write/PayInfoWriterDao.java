package bwjava.dao.write;

import bwjava.entity.PayInfo;

public interface PayInfoWriterDao {
    int deleteByPrimaryKey(Long id);

    int insert(PayInfo record);

    int insertSelective(PayInfo record);

    int updateByPrimaryKeySelective(PayInfo record);

    int updateByPrimaryKey(PayInfo record);
}