package bwjava.service.dao.read;

import bwjava.service.entity.BeautyModelPic;

import java.util.List;

public interface BeautyModelPicReaderDao {
    BeautyModelPic selectByPrimaryKey(Long id);

    /**
     * 根据模特id查询picUrls
     *
     * @param modelId
     * @return
     */
    List<BeautyModelPic> selectPicUrls(Long modelId);
}