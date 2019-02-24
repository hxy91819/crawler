package bwjava.service.entity;

import java.io.Serializable;
import java.util.Date;

public class BeautyModel implements Serializable {
    /** 主键编号 */
    private Long id;

    /** 机构 */
    private String org;

    /** 标题 */
    private String title;

    /** 发布日期 */
    private String releaseDate;

    /** 模特名称 */
    private String modelName;

    /** 图片数量 */
    private Integer picCount;

    /** 入口地址 */
    private String entranceUrl;

    /** 创建时间 */
    private Date createTime;

    /** 修改时间 */
    private Date updateTime;

    private static final long serialVersionUID = 1L;

    /** 主键编号 */
    public Long getId() {
        return id;
    }

    /** 主键编号 */
    public void setId(Long id) {
        this.id = id;
    }

    /** 机构 */
    public String getOrg() {
        return org;
    }

    /** 机构 */
    public void setOrg(String org) {
        this.org = org == null ? null : org.trim();
    }

    /** 标题 */
    public String getTitle() {
        return title;
    }

    /** 标题 */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /** 发布日期 */
    public String getReleaseDate() {
        return releaseDate;
    }

    /** 发布日期 */
    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate == null ? null : releaseDate.trim();
    }

    /** 模特名称 */
    public String getModelName() {
        return modelName;
    }

    /** 模特名称 */
    public void setModelName(String modelName) {
        this.modelName = modelName == null ? null : modelName.trim();
    }

    /** 图片数量 */
    public Integer getPicCount() {
        return picCount;
    }

    /** 图片数量 */
    public void setPicCount(Integer picCount) {
        this.picCount = picCount;
    }

    /** 入口地址 */
    public String getEntranceUrl() {
        return entranceUrl;
    }

    /** 入口地址 */
    public void setEntranceUrl(String entranceUrl) {
        this.entranceUrl = entranceUrl == null ? null : entranceUrl.trim();
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
}