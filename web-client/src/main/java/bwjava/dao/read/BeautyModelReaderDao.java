package bwjava.dao.read;

import bwjava.entity.BeautyModel;

public interface BeautyModelReaderDao {
    BeautyModel selectByPrimaryKey(Long id);
}