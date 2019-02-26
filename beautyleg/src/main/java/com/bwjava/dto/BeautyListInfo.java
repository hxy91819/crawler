package com.bwjava.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author chenjing
 * @date 2019-02-26 22:06
 */
@Getter
@Setter
public class BeautyListInfo {
    /**
     * 模特详情地址
     */
    private List<String> url;

    /**
     * 模特封面图地址
     */
    private List<String> thumbPic;
}
