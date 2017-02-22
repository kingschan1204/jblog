package com.kingschan.blog.model.vo;

import java.sql.Timestamp;

public class ArticleCommentVo {

	private String id;
    private ArticleVo article;
    private String CText;
    private Timestamp CDatetime;
    private UserVo CUser;
    private String CWebsiteId;
    private UserVo CTosomeone;
    private String CIsdel;
    private Integer CSupport;
    private String CRoot;
    private Integer CReplyTotal;
    //当前登录用户是否已点赞
    private Boolean existsSupport;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public ArticleVo getArticle() {
		return article;
	}
	public void setArticle(ArticleVo article) {
		this.article = article;
	}
	public String getCText() {
		return CText;
	}
	public void setCText(String cText) {
		CText = cText;
	}
	public Timestamp getCDatetime() {
		return CDatetime;
	}
	public void setCDatetime(Timestamp cDatetime) {
		CDatetime = cDatetime;
	}
	public UserVo getCUser() {
		return CUser;
	}
	public void setCUser(UserVo cUser) {
		CUser = cUser;
	}
	public String getCWebsiteId() {
		return CWebsiteId;
	}
	public void setCWebsiteId(String cWebsiteId) {
		CWebsiteId = cWebsiteId;
	}
	public UserVo getCTosomeone() {
		return CTosomeone;
	}
	public void setCTosomeone(UserVo cTosomeone) {
		CTosomeone = cTosomeone;
	}
	public String getCIsdel() {
		return CIsdel;
	}
	public void setCIsdel(String cIsdel) {
		CIsdel = cIsdel;
	}
	public Integer getCSupport() {
		return CSupport;
	}
	public void setCSupport(Integer cSupport) {
		CSupport = cSupport;
	}
	public Boolean getExistsSupport() {
		return existsSupport;
	}
	public void setExistsSupport(Boolean existsSupport) {
		this.existsSupport = existsSupport;
	}
	public String getCRoot() {
		return CRoot;
	}
	public void setCRoot(String cRoot) {
		CRoot = cRoot;
	}
	public Integer getCReplyTotal() {
		return CReplyTotal;
	}
	public void setCReplyTotal(Integer cReplyTotal) {
		CReplyTotal = cReplyTotal;
	}
    
    
    
}
