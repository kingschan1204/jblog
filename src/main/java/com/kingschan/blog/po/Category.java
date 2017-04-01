package com.kingschan.blog.po;
// default package

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name="blog_category")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class Category  implements java.io.Serializable {


    // Fields    

     /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
     private User user;
     private String categoryName;
     private String categoryRemark;
     private String categoryWebsiteid;
     private Integer categoryPosition;
     private Timestamp categoryDatetime;
     private Set<Article> articles = new HashSet<Article>(0);


    // Constructors

    /** default constructor */
    public Category() {
    }

	/** minimal constructor */
    public Category(User user, String categoryName, String categoryWebsiteid, Integer categoryPosition, Timestamp categoryDatetime) {
        this.user = user;
        this.categoryName = categoryName;
        this.categoryWebsiteid = categoryWebsiteid;
        this.categoryPosition = categoryPosition;
        this.categoryDatetime = categoryDatetime;
    }
    
    /** full constructor */
    public Category(User user, String categoryName, String categoryRemark, String categoryWebsiteid, Integer categoryPosition, Timestamp categoryDatetime, Set<Article> articles) {
        this.user = user;
        this.categoryName = categoryName;
        this.categoryRemark = categoryRemark;
        this.categoryWebsiteid = categoryWebsiteid;
        this.categoryPosition = categoryPosition;
        this.categoryDatetime = categoryDatetime;
        this.articles = articles;
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
        @JoinColumn(name="category_creator", nullable=false)

    public User getUser() {
        return this.user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    @Column(name="category_name", nullable=false, length=60)

    public String getCategoryName() {
        return this.categoryName;
    }
    
    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    
    @Column(name="category_remark", length=100)

    public String getCategoryRemark() {
        return this.categoryRemark;
    }
    
    public void setCategoryRemark(String categoryRemark) {
        this.categoryRemark = categoryRemark;
    }
    
    @Column(name="category_websiteid", nullable=false, length=32)

    public String getCategoryWebsiteid() {
        return this.categoryWebsiteid;
    }
    
    public void setCategoryWebsiteid(String categoryWebsiteid) {
        this.categoryWebsiteid = categoryWebsiteid;
    }
    
    @Column(name="category_position", nullable=false)

    public Integer getCategoryPosition() {
        return this.categoryPosition;
    }
    
    public void setCategoryPosition(Integer categoryPosition) {
        this.categoryPosition = categoryPosition;
    }
    
    @Column(name="category_datetime", nullable=false, length=19)

    public Timestamp getCategoryDatetime() {
        return this.categoryDatetime;
    }
    
    public void setCategoryDatetime(Timestamp categoryDatetime) {
        this.categoryDatetime = categoryDatetime;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="category")

    public Set<Article> getArticles() {
        return this.articles;
    }
    
    public void setArticles(Set<Article> articles) {
        this.articles = articles;
    }
   








}