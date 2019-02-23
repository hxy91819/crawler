package bwjava.dao.read;

import bwjava.entity.BeautyModelPic;

public interface BeautyModelPicReaderDao {
    BeautyModelPic selectByPrimaryKey(Long id);
}