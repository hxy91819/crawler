package bwjava.service;

/**
 * beauty 信息
 * @author chenjing
 * @date 2019-02-23 12:05
 */
public interface BeautyService {
    /**
     * 保存单个模特
     * @param url
     * @return
     */
    int saveSingleBeautyModel(String url);

    int testSnow();
}
