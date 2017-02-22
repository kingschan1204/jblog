package com.kingschan.blog.model.vo;

import java.util.HashSet;
import java.util.Set;

public class ArticleVo {
	// Fields    
    private String id;
    private UserVo user;
    private CategoryVo category;
    private String articleTitle;
    private String articleContent;
    private String articleSummary;
    private String articlePubtime;
    private String articleUpdatetime;
    private String websiteid;
    private Short articleStatus;
    private Integer articleViewcount;
    private Boolean articlePrivate;
    private String articlePassword;
    private Boolean articleAllowcomments;
    private String articleLinkurl;
    private Integer articleTotalComment;
    private Integer articleSort;
    private String articleCover;
    private String articleEditor;
    private Integer articleLikes;
    private Set<LableVo> lables = new HashSet<LableVo>(0);
    //
    private String articleLableStr;
    
    public ArticleVo(){}
    public ArticleVo(String id,String title) {
    	this.id=id;
    	this.articleTitle=title;
    }
    
    
    
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	
	public UserVo getUser() {
		return user;
	}
	public void setUser(UserVo user) {
		this.user = user;
	}
	public CategoryVo getCategory() {
		return category;
	}
	public void setCategory(CategoryVo category) {
		this.category = category;
	}
	public String getArticleTitle() {
		return articleTitle;
	}
	public void setArticleTitle(String articleTitle) {
		this.articleTitle = articleTitle;
	}
	public String getArticleContent() {
		return articleContent;
	}
	public void setArticleContent(String articleContent) {
		this.articleContent = articleContent;
	}
	public String getArticleSummary() {
		return articleSummary;
	}
	public void setArticleSummary(String articleSummary) {
		this.articleSummary = articleSummary;
	}
	public String getArticlePubtime() {
		return articlePubtime;
	}
	public void setArticlePubtime(String articlePubtime) {
		this.articlePubtime = articlePubtime;
	}
	public String getArticleUpdatetime() {
		return articleUpdatetime;
	}
	public void setArticleUpdatetime(String articleUpdatetime) {
		this.articleUpdatetime = articleUpdatetime;
	}
	public String getWebsiteid() {
		return websiteid;
	}
	public void setWebsiteid(String websiteid) {
		this.websiteid = websiteid;
	}
	public Short getArticleStatus() {
		return articleStatus;
	}
	public void setArticleStatus(Short articleStatus) {
		this.articleStatus = articleStatus;
	}
	public Integer getArticleViewcount() {
		return articleViewcount;
	}
	public void setArticleViewcount(Integer articleViewcount) {
		this.articleViewcount = articleViewcount;
	}
	public Boolean getArticlePrivate() {
		return articlePrivate;
	}
	public void setArticlePrivate(Boolean articlePrivate) {
		this.articlePrivate = articlePrivate;
	}
	public String getArticlePassword() {
		return articlePassword;
	}
	public void setArticlePassword(String articlePassword) {
		this.articlePassword = articlePassword;
	}
	public Boolean getArticleAllowcomments() {
		return articleAllowcomments;
	}
	public void setArticleAllowcomments(Boolean articleAllowcomments) {
		this.articleAllowcomments = articleAllowcomments;
	}
	public String getArticleLinkurl() {
		return articleLinkurl;
	}
	public void setArticleLinkurl(String articleLinkurl) {
		this.articleLinkurl = articleLinkurl;
	}
	public Set<LableVo> getLables() {
		return lables;
	}
	public void setLables(Set<LableVo> lables) {
		this.lables = lables;
	}
	public String getArticleLableStr() {
		return articleLableStr;
	}
	public void setArticleLableStr(String articleLableStr) {
		this.articleLableStr = articleLableStr;
	}
	public Integer getArticleTotalComment() {
		return articleTotalComment;
	}
	public void setArticleTotalComment(Integer articleTotalComment) {
		this.articleTotalComment = articleTotalComment;
	}
	public Integer getArticleSort() {
		return articleSort;
	}
	public void setArticleSort(Integer articleSort) {
		this.articleSort = articleSort;
	}
	public String getArticleCover() {
		return articleCover;
	}
	public void setArticleCover(String articleCover) {
		this.articleCover = articleCover;
	}
	public String getArticleEditor() {
		return articleEditor;
	}
	public void setArticleEditor(String articleEditor) {
		this.articleEditor = articleEditor;
	}
	public Integer getArticleLikes() {
		return articleLikes;
	}
	public void setArticleLikes(Integer articleLikes) {
		this.articleLikes = articleLikes;
	}
    
    
    
}
