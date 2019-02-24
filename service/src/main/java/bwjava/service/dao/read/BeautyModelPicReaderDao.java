package bwjava.service.dao.read;

import bwjava.service.entity.BeautyModelPic;

public interface BeautyModelPicReaderDao {
    BeautyModelPic selectByPrimaryKey(Long id);
}