package com.kingschan.blog.po;
// default package

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import com.hankcs.lucene.HanLPAnalyzer;
import com.hankcs.lucene.HanLPIndexAnalyzer;
import com.kingschan.blog.common.hibernate.search.ArticleTextBridge;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.search.annotations.*;
import org.hibernate.search.annotations.Index;
import org.springframework.beans.BeanUtils;
import com.kingschan.blog.common.bean.convert.BeanConvert;



@Entity
@Table(name="blog_article")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
@Indexed //hibernate search
@Analyzer(impl=HanLPIndexAnalyzer.class)
public class Article  implements java.io.Serializable,BeanConvert {

    
    private static final long serialVersionUID = 1L;
    // Fields    
     @DocumentId ////表示这个对象的主键  
     private String id;
     private User user;
     private Category category;
     @Field(index=Index.YES, analyze=Analyze.YES, store=Store.YES) 
     private String articleTitle;
//     @Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
//     private String articleContent;
//     private String articleSummary;
     private Timestamp articlePubtime;
     private Timestamp articleUpdatetime;
     @Field(index=Index.YES, analyze=Analyze.NO, store=Store.YES) 
     private String websiteid;
     private Short articleStatus;
     private Integer articleViewcount;
     @Field(index=Index.YES, analyze=Analyze.NO, store=Store.YES) 
     private Boolean articlePrivate;
     private String articlePassword;
     private Boolean articleAllowcomments;
     private String articleLinkurl;
     private String articleMd5;
     private Integer articleTotalComment;
     private Integer articleSort;
     private String articleCover;
     private String articleEditor;
     private Integer articleLikes;
     private Set<Lable> lables = new HashSet<Lable>(0);
    @FieldBridge(impl=ArticleTextBridge.class)
 //   @Field(index=Index.YES, analyze=Analyze.YES, store=Store.YES)
    @IndexedEmbedded
    private ArticleText articleText;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id", nullable=false)
    @Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
    public ArticleText getArticleText() {
        return articleText;
    }

    public void setArticleText(ArticleText articleText) {
        this.articleText = articleText;
    }



    // Constructors

    /** default constructor */
    public Article() {
    }

    public Article(String id,String title) {
    	this.id=id;
    	this.articleTitle=title;
    }
    
	/** minimal constructor */
    public Article(User user, Category category, String articleTitle, String articleContent, Timestamp articlePubtime, Timestamp articleUpdatetime, String websiteid, Short articleStatus, Integer articleViewcount) {
        this.user = user;
        this.category = category;
        this.articleTitle = articleTitle;
//        this.articleContent = articleContent;
        this.articlePubtime = articlePubtime;
        this.articleUpdatetime = articleUpdatetime;
        this.websiteid = websiteid;
        this.articleStatus = articleStatus;
        this.articleViewcount = articleViewcount;
    }
    
    /** full constructor */
    public Article(User user, Category category, String articleTitle, String articleContent, String articleSummary, Timestamp articlePubtime, Timestamp articleUpdatetime, String websiteid, Short articleStatus, Integer articleViewcount, Boolean articlePrivate, String articlePassword, Boolean articleAllowcomments, String articleLinkurl, Set<Lable> lables) {
        this.user = user;
        this.category = category;
        this.articleTitle = articleTitle;
//        this.articleContent = articleContent;
//        this.articleSummary = articleSummary;
        this.articlePubtime = articlePubtime;
        this.articleUpdatetime = articleUpdatetime;
        this.websiteid = websiteid;
        this.articleStatus = articleStatus;
        this.articleViewcount = articleViewcount;
        this.articlePrivate = articlePrivate;
        this.articlePassword = articlePassword;
        this.articleAllowcomments = articleAllowcomments;
        this.articleLinkurl = articleLinkurl;
        this.lables = lables;
    }

   
    // Property accessors
    @GenericGenerator(name="generator", strategy="uuid.hex")@Id @GeneratedValue(generator="generator")
    
    @Column(name="id", unique=true, nullable=false, length=32)

    public String getId() {
        return this.id;
    }
    
    public void setId(String id) {
        this.id = id;
    }
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="article_creator", nullable=false)
	@Cache(usage=CacheConcurrencyStrategy.READ_ONLY)
    public User getUser() {
        return this.user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
	@ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="article_categoryid", nullable=false)
    public Category getCategory() {
        return this.category;
    }
    
    public void setCategory(Category category) {
        this.category = category;
    }
    
    @Column(name="article_title", nullable=false, length=200)
    public String getArticleTitle() {
        return this.articleTitle;
    }
    
    public void setArticleTitle(String articleTitle) {
        this.articleTitle = articleTitle;
    }
    
//    @Column(name="article_content", nullable=false)
//    public String getArticleContent() {
//        return this.articleContent;
//    }
    
//    public void setArticleContent(String articleContent) {
//        this.articleContent = articleContent;
//    }
    
//    @Column(name="article_summary", length=500)

//    public String getArticleSummary() {
//        return this.articleSummary;
//    }
    
//    public void setArticleSummary(String articleSummary) {
//        this.articleSummary = articleSummary;
//    }
    
    @Column(name="article_pubtime", nullable=false, length=19)

    public Timestamp getArticlePubtime() {
        return this.articlePubtime;
    }
    
    public void setArticlePubtime(Timestamp articlePubtime) {
        this.articlePubtime = articlePubtime;
    }
    
    @Column(name="article_updatetime", nullable=false, length=19)

    public Timestamp getArticleUpdatetime() {
        return this.articleUpdatetime;
    }
    
    public void setArticleUpdatetime(Timestamp articleUpdatetime) {
        this.articleUpdatetime = articleUpdatetime;
    }
    
    @Column(name="websiteid", nullable=false, length=32)

    public String getWebsiteid() {
        return this.websiteid;
    }
    
    public void setWebsiteid(String websiteid) {
        this.websiteid = websiteid;
    }
    
    @Column(name="article_status", nullable=false)

    public Short getArticleStatus() {
        return this.articleStatus;
    }
    
    public void setArticleStatus(Short articleStatus) {
        this.articleStatus = articleStatus;
    }
    
    @Column(name="article_viewcount", nullable=false)

    public Integer getArticleViewcount() {
        return this.articleViewcount;
    }
    
    public void setArticleViewcount(Integer articleViewcount) {
        this.articleViewcount = articleViewcount;
    }
    
    @Column(name="article_private")

    public Boolean getArticlePrivate() {
        return this.articlePrivate;
    }
    
    public void setArticlePrivate(Boolean articlePrivate) {
        this.articlePrivate = articlePrivate;
    }
    
    @Column(name="article_password", length=10)

    public String getArticlePassword() {
        return this.articlePassword;
    }
    
    public void setArticlePassword(String articlePassword) {
        this.articlePassword = articlePassword;
    }
    
    @Column(name="article_allowcomments")

    public Boolean getArticleAllowcomments() {
        return this.articleAllowcomments;
    }
    
    public void setArticleAllowcomments(Boolean articleAllowcomments) {
        this.articleAllowcomments = articleAllowcomments;
    }
    
    @Column(name="article_linkurl", length=50)

    public String getArticleLinkurl() {
        return this.articleLinkurl;
    }
    
    public void setArticleLinkurl(String articleLinkurl) {
        this.articleLinkurl = articleLinkurl;
    }
    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.EAGER, mappedBy="article")
    //缓存子对象
    @Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
    public Set<Lable> getLables() {
        return this.lables;
    }
    
    public void setLables(Set<Lable> lables) {
        this.lables = lables;
    }
   

    @Column(name="article_md5", length=32)

    public String getArticleMd5() {
        return this.articleMd5;
    }
    
    public void setArticleMd5(String articleMd5) {
        this.articleMd5 = articleMd5;
    }

    @Column(name="article_total_comment")

    public Integer getArticleTotalComment() {
        return null==this.articleTotalComment?0:this.articleTotalComment;
    }
    
    public void setArticleTotalComment(Integer articleTotalComment) {
        this.articleTotalComment = articleTotalComment;
    }
    
    @Column(name="article_sort")

    public Integer getArticleSort() {
        return this.articleSort;
    }
    
    public void setArticleSort(Integer articleSort) {
        this.articleSort = articleSort;
    }
    
    @Column(name="article_cover", length=32)

    public String getArticleCover() {
        return this.articleCover;
    }
    
    public void setArticleCover(String articleCover) {
        this.articleCover = articleCover;
    }
    @Column(name="article_editor", length=10)
	public String getArticleEditor() {
		return articleEditor;
	}

	public void setArticleEditor(String articleEditor) {
		this.articleEditor = articleEditor;
	}
	 @Column(name="article_likes")
	public Integer getArticleLikes() {
		return articleLikes;
	}

	public void setArticleLikes(Integer articleLikes) {
		this.articleLikes = articleLikes;
	}



	@Override
	public <ArticleVo> ArticleVo po2vo(ArticleVo obj) throws Exception {
		BeanUtils.copyProperties(this, obj);
		return obj;
	}

    




}