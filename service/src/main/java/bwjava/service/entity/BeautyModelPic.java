package bwjava.service.entity;

import java.io.Serializable;
import java.util.Date;

public class BeautyModelPic implements Serializable {
    /** 主键编号 */
    private Long id;

    /** 模特信息表主键 */
    private Long modelId;

    /** 图片地址 */
    private String picUrl;

    /** 创建时间 */
    private Date createTime;

    /** 修改时间 */
    private Date updateTime;

    /** 排序号 */
    private Integer sortNo;

    private static final long serialVersionUID = 1L;

    /** 主键编号 */
    public Long getId() {
        return id;
    }

    /** 主键编号 */
    public void setId(Long id) {
        this.id = id;
    }

    /** 模特信息表主键 */
    public Long getModelId() {
        return modelId;
    }

    /** 模特信息表主键 */
    public void setModelId(Long modelId) {
        this.modelId = modelId;
    }

    /** 图片地址 */
    public String getPicUrl() {
        return picUrl;
    }

    /** 图片地址 */
    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl == null ? null : picUrl.trim();
    }

    /** 创建时间 */
    public Date getCreateTime() {
        return createTime;
    }

    /** 创建时间 */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /** 修改时间 */
    public Date getUpdateTime() {
        return updateTime;
    }

    /** 修改时间 */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /** 排序号 */
    public Integer getSortNo() {
        return sortNo;
    }

    /** 排序号 */
    public void setSortNo(Integer sortNo) {
        this.sortNo = sortNo;
    }
}