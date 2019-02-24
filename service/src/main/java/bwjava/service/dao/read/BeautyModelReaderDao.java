package bwjava.service.dao.read;

import bwjava.service.entity.BeautyModel;

public interface BeautyModelReaderDao {
    BeautyModel selectByPrimaryKey(Long id);
}