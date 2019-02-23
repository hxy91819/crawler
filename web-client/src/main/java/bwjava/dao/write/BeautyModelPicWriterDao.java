package bwjava.dao.write;

import bwjava.entity.BeautyModelPic;

public interface BeautyModelPicWriterDao {
    int deleteByPrimaryKey(Long id);

    int insert(BeautyModelPic record);

    int insertSelective(BeautyModelPic record);

    int updateByPrimaryKeySelective(BeautyModelPic record);

    int updateByPrimaryKey(BeautyModelPic record);
}