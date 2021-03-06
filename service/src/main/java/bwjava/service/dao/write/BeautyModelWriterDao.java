package bwjava.service.dao.write;

import bwjava.service.entity.BeautyModel;

import java.util.List;

public interface BeautyModelWriterDao {
    int deleteByPrimaryKey(Long id);

    int insert(BeautyModel record);

    int insertSelective(BeautyModel record);

    int updateByPrimaryKeySelective(BeautyModel record);

    int updateByPrimaryKey(BeautyModel record);

    int insertBatch(List<BeautyModel> records);
}