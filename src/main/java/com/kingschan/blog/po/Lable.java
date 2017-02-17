package com.kingschan.blog.po;
// default package

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name="blog_label")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class Lable  implements java.io.Serializable {


    // Fields    

     /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
     private WebSite webSite;
     private Article article;
     private String lableName;
     private String lableCreator;
     private Timestamp lableDatetime;


    // Constructors

    /** default constructor */
    public Lable() {
    }

    
    /** full constructor */
    public Lable(WebSite webSite, Article article, String lableName, String lableCreator, Timestamp lableDatetime) {
        this.webSite = webSite;
        this.article = article;
        this.lableName = lableName;
        this.lableCreator = lableCreator;
        this.lableDatetime = lableDatetime;
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
        @JoinColumn(name="websiteid", nullable=false)

    public WebSite getWebSite() {
        return this.webSite;
    }
    
    public void setWebSite(WebSite webSite) {
        this.webSite = webSite;
    }
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="lable_articleid", nullable=false)

    public Article getArticle() {
        return this.article;
    }
    
    public void setArticle(Article article) {
        this.article = article;
    }
    
    @Column(name="lable_name", nullable=false, length=50)

    public String getLableName() {
        return this.lableName;
    }
    
    public void setLableName(String lableName) {
        this.lableName = lableName;
    }
    
    @Column(name="lable_creator", nullable=false, length=32)

    public String getLableCreator() {
        return this.lableCreator;
    }
    
    public void setLableCreator(String lableCreator) {
        this.lableCreator = lableCreator;
    }
    
    @Column(name="lable_datetime", nullable=false, length=19)

    public Timestamp getLableDatetime() {
        return this.lableDatetime;
    }
    
    public void setLableDatetime(Timestamp lableDatetime) {
        this.lableDatetime = lableDatetime;
    }
   








}