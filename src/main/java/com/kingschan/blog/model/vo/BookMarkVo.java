package com.kingschan.blog.model.vo;

import com.kingschan.blog.util.RegexUtil;


public class BookMarkVo {

    private Integer id;
    private Integer bfid;
    private String bookmarksName;
    private String bookmarksHref;
    private Integer bookmarksOrder;
    
    
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getBfid() {
        return bfid;
    }
    public void setBfid(Integer bfid) {
        this.bfid = bfid;
    }
    public String getBookmarksName() {
        return bookmarksName;
    }
    public void setBookmarksName(String bookmarksName) {
    	//去掉html标签
        if (null!=bookmarksName) {
        	this.bookmarksName = RegexUtil.replaceAllHtmlTag(bookmarksName);
		}
    }
    public String getBookmarksHref() {
        return bookmarksHref;
    }
    public void setBookmarksHref(String bookmarksHref) {
        this.bookmarksHref = bookmarksHref;
    }
    public Integer getBookmarksOrder() {
        return bookmarksOrder;
    }
    public void setBookmarksOrder(Integer bookmarksOrder) {
        this.bookmarksOrder = bookmarksOrder;
    }
    
    
    
    
}
