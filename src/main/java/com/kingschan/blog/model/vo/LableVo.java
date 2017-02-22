package com.kingschan.blog.model.vo;

import java.sql.Timestamp;

public class LableVo {

	private String id;
	private WebSiteVo webSite;
	private ArticleVo article;
	private String lableName;
	private String lableCreator;
	private Timestamp lableDatetime;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public WebSiteVo getWebSite() {
		return webSite;
	}
	public void setWebSite(WebSiteVo webSite) {
		this.webSite = webSite;
	}
	public ArticleVo getArticle() {
		return article;
	}
	public void setArticle(ArticleVo article) {
		this.article = article;
	}
	public String getLableName() {
		return lableName;
	}
	public void setLableName(String lableName) {
		this.lableName = lableName;
	}
	public String getLableCreator() {
		return lableCreator;
	}
	public void setLableCreator(String lableCreator) {
		this.lableCreator = lableCreator;
	}
	public Timestamp getLableDatetime() {
		return lableDatetime;
	}
	public void setLableDatetime(Timestamp lableDatetime) {
		this.lableDatetime = lableDatetime;
	}
	
	
}
