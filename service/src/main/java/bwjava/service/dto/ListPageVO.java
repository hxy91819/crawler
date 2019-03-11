package bwjava.service.dto;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author chenjing
 * @date 2019-03-11 22:12
 */
@Getter
@Setter
public class ListPageVO implements Serializable {
    /**
     * 主键编号
     */
    private String modelId;
    /**
     * 标题
     */
    private String title;

    /**
     * 发布日期
     */
    private String releaseDate;

    /**
     * 图片数量
     */
    private Integer picCount;

    /**
     * 入口地址
     */
    private String entranceUrl;

    /**
     * 封面地址
     */
    private String thumbPic;

    private static final long serialVersionUID = 1L;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
