package com.kingschan.blog.po;

import com.hankcs.lucene.HanLPIndexAnalyzer;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.search.annotations.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "blog_article_text")
@Cache(usage= CacheConcurrencyStrategy.READ_WRITE)
@Indexed //hibernate search
@Analyzer(impl=HanLPIndexAnalyzer.class)
public class ArticleText implements java.io.Serializable {

	// Fields

	private String id;
	private String articleSummary;
	@Field(index= Index.YES, analyze= Analyze.YES, store= Store.NO)
	private String articleContent;

	// Constructors

	/** default constructor */
	public ArticleText() {
	}

	/** full constructor */
	public ArticleText(String id, String articleSummary,
			String articleContent) {
		this.id = id;
		this.articleSummary = articleSummary;
		this.articleContent = articleContent;
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

	@Column(name = "article_summary", nullable = false, length = 500)
	public String getArticleSummary() {
		return this.articleSummary;
	}

	public void setArticleSummary(String articleSummary) {
		this.articleSummary = articleSummary;
	}

	@Column(name = "article_content", nullable = false)
	public String getArticleContent() {
		return this.articleContent;
	}

	public void setArticleContent(String articleContent) {
		this.articleContent = articleContent;
	}

}