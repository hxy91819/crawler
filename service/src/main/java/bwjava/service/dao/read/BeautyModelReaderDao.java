package bwjava.service.dao.read;

import bwjava.service.entity.BeautyModel;

import java.util.List;

public interface BeautyModelReaderDao {
    BeautyModel selectByPrimaryKey(Long id);

    List<BeautyModel> selectEntranceurl();

    List<BeautyModel> selectEntranceurlThumbpic();

    List<BeautyModel> selectIdEntranceurl();
}