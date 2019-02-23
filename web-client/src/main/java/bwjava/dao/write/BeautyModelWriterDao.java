package bwjava.dao.write;

import bwjava.entity.BeautyModel;

public interface BeautyModelWriterDao {
    int deleteByPrimaryKey(Long id);

    int insert(BeautyModel record);

    int insertSelective(BeautyModel record);

    int updateByPrimaryKeySelective(BeautyModel record);

    int updateByPrimaryKey(BeautyModel record);
}