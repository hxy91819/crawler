package com.bwjava.dto;

import lombok.Data;

import java.util.List;

/**
 * @author chenjing
 * @date 2019-02-24 15:06
 */
@Data
public class ModelInfo {

    /**
     * 模特id
     */
    private Long id;

    private String title;

    private Integer picCount;

    /**
     * 入口地址
     */
    private String entranceUrl;

    /**
     * 模特下面的图片
     */
    private List<String> picUrls;
}
