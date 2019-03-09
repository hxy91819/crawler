package bwjava.service.dao.read;

import bwjava.service.entity.BeautyModelPic;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BeautyModelPicReaderDao {
    BeautyModelPic selectByPrimaryKey(Long id);

    /**
     * 根据模特id查询picUrls
     *
     * @param modelId
     * @return
     */
    List<BeautyModelPic> selectPicUrls(@Param("modelId") Long modelId);
}