package com.kingschan.blog.po;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "blog_statistical_site")
public class BlogStatisticalSite implements java.io.Serializable {

	// Fields

	private String id;
	private String uid;
	private Timestamp WCreateTime;
	private Timestamp WLastLoginTime;
	private Integer WArticleQuantity;
	private Integer WCategoryQuantity;
	private Integer WLableQuantity;
	private Integer WDiscussQuantity;
	private Integer WRequestQuantity;

	// Constructors

	/** default constructor */
	public BlogStatisticalSite() {
	}

	/** full constructor */
	public BlogStatisticalSite(String id, String uid, Timestamp WCreateTime,
			Timestamp WLastLoginTime, Integer WArticleQuantity,
			Integer WCategoryQuantity, Integer WLableQuantity,
			Integer WDiscussQuantity, Integer WRequestQuantity) {
		this.id = id;
		this.uid = uid;
		this.WCreateTime = WCreateTime;
		this.WLastLoginTime = WLastLoginTime;
		this.WArticleQuantity = WArticleQuantity;
		this.WCategoryQuantity = WCategoryQuantity;
		this.WLableQuantity = WLableQuantity;
		this.WDiscussQuantity = WDiscussQuantity;
		this.WRequestQuantity = WRequestQuantity;
	}

	// Property accessors
	@Id
	@Column(name = "id", unique = true, nullable = false, length = 32)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "uid", nullable = false, length = 32)
	public String getUid() {
		return this.uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	@Column(name = "w_create_time", nullable = false, length = 19)
	public Timestamp getWCreateTime() {
		return this.WCreateTime;
	}

	public void setWCreateTime(Timestamp WCreateTime) {
		this.WCreateTime = WCreateTime;
	}

	@Column(name = "w_last_login_time", nullable = false, length = 19)
	public Timestamp getWLastLoginTime() {
		return this.WLastLoginTime;
	}

	public void setWLastLoginTime(Timestamp WLastLoginTime) {
		this.WLastLoginTime = WLastLoginTime;
	}

	@Column(name = "w_article_quantity", nullable = false)
	public Integer getWArticleQuantity() {
		return this.WArticleQuantity;
	}

	public void setWArticleQuantity(Integer WArticleQuantity) {
		this.WArticleQuantity = WArticleQuantity;
	}

	@Column(name = "w_category_quantity", nullable = false)
	public Integer getWCategoryQuantity() {
		return this.WCategoryQuantity;
	}

	public void setWCategoryQuantity(Integer WCategoryQuantity) {
		this.WCategoryQuantity = WCategoryQuantity;
	}

	@Column(name = "w_lable_quantity", nullable = false)
	public Integer getWLableQuantity() {
		return this.WLableQuantity;
	}

	public void setWLableQuantity(Integer WLableQuantity) {
		this.WLableQuantity = WLableQuantity;
	}

	@Column(name = "w_discuss_quantity", nullable = false)
	public Integer getWDiscussQuantity() {
		return this.WDiscussQuantity;
	}

	public void setWDiscussQuantity(Integer WDiscussQuantity) {
		this.WDiscussQuantity = WDiscussQuantity;
	}

	@Column(name = "w_request_quantity", nullable = false)
	public Integer getWRequestQuantity() {
		return this.WRequestQuantity;
	}

	public void setWRequestQuantity(Integer WRequestQuantity) {
		this.WRequestQuantity = WRequestQuantity;
	}

}