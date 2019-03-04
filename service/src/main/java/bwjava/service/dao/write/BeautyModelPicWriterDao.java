package bwjava.service.dao.write;

import bwjava.service.entity.BeautyModelPic;

import java.util.List;

public interface BeautyModelPicWriterDao {
    int deleteByPrimaryKey(Long id);

    int insert(BeautyModelPic record);

    int insertSelective(BeautyModelPic record);

    int updateByPrimaryKeySelective(BeautyModelPic record);

    int updateByPrimaryKey(BeautyModelPic record);

    int deleteByModelId(Long modelId);

    int insertBatch(List<BeautyModelPic> records);
}