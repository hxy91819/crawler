package bwjava.service.dao.read;

import bwjava.service.entity.BeautyModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BeautyModelReaderDao {
    BeautyModel selectByPrimaryKey(Long id);

    List<BeautyModel> selectEntranceurl();

    List<BeautyModel> selectEntranceurlThumbpic(@Param("searchContent") String searchContent);

    List<BeautyModel> selectIdEntranceurl(@Param("org") String org);
}