package com.kingschan.blog.model.vo;

import java.sql.Timestamp;

/**
 * Created by kingschan on 2017/2/24.
 */
public class BlogSiteStatisticalVo {
    private String id;
    private String uid;
    private Timestamp WCreateTime;
    private Timestamp WLastLoginTime;
    private Integer WArticleQuantity;
    private Integer WCategoryQuantity;
    private Integer WLableQuantity;
    private Integer WDiscussQuantity;
    private Integer WRequestQuantity;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Timestamp getWCreateTime() {
        return WCreateTime;
    }

    public void setWCreateTime(Timestamp WCreateTime) {
        this.WCreateTime = WCreateTime;
    }

    public Timestamp getWLastLoginTime() {
        return WLastLoginTime;
    }

    public void setWLastLoginTime(Timestamp WLastLoginTime) {
        this.WLastLoginTime = WLastLoginTime;
    }

    public Integer getWArticleQuantity() {
        return WArticleQuantity;
    }

    public void setWArticleQuantity(Integer WArticleQuantity) {
        this.WArticleQuantity = WArticleQuantity;
    }

    public Integer getWCategoryQuantity() {
        return WCategoryQuantity;
    }

    public void setWCategoryQuantity(Integer WCategoryQuantity) {
        this.WCategoryQuantity = WCategoryQuantity;
    }

    public Integer getWLableQuantity() {
        return WLableQuantity;
    }

    public void setWLableQuantity(Integer WLableQuantity) {
        this.WLableQuantity = WLableQuantity;
    }

    public Integer getWDiscussQuantity() {
        return WDiscussQuantity;
    }

    public void setWDiscussQuantity(Integer WDiscussQuantity) {
        this.WDiscussQuantity = WDiscussQuantity;
    }

    public Integer getWRequestQuantity() {
        return WRequestQuantity;
    }

    public void setWRequestQuantity(Integer WRequestQuantity) {
        this.WRequestQuantity = WRequestQuantity;
    }
}
