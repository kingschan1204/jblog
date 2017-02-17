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
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name="blog_bookmarks_folder")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class BookmarksFolder  implements java.io.Serializable {


    // Fields    

     /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
     private String bookmarksName;
     private String websiteid;
     private String bookmarksCreator;
     private String bookmarksRemark;
     private Timestamp bookmarksDatetime;
     private Set<Bookmarks> bookmarkses = new HashSet<Bookmarks>(0);


    // Constructors

    /** default constructor */
    public BookmarksFolder() {
    }

	/** minimal constructor */
    public BookmarksFolder(String bookmarksName, String websiteid, String bookmarksCreator, Timestamp bookmarksDatetime) {
        this.bookmarksName = bookmarksName;
        this.websiteid = websiteid;
        this.bookmarksCreator = bookmarksCreator;
        this.bookmarksDatetime = bookmarksDatetime;
    }
    
    /** full constructor */
    public BookmarksFolder( String bookmarksName, String websiteid, String bookmarksCreator, Timestamp bookmarksDatetime, Set<Bookmarks> bookmarkses) {
        this.bookmarksName = bookmarksName;
        this.websiteid = websiteid;
        this.bookmarksCreator = bookmarksCreator;
        this.bookmarksDatetime = bookmarksDatetime;
        this.bookmarkses = bookmarkses;
    }

   
    // Property accessors
    @GenericGenerator(name="generator", strategy="increment")@Id @GeneratedValue(generator="generator")
    
    @Column(name="id", unique=true, nullable=false)

    public Integer getId() {
        return this.id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    @Column(name="bookmarks_name", nullable=false, length=50)

    public String getBookmarksName() {
        return this.bookmarksName;
    }
    
    public void setBookmarksName(String bookmarksName) {
        this.bookmarksName = bookmarksName;
    }
    
    @Column(name="websiteid", nullable=false, length=32)

    public String getWebsiteid() {
        return this.websiteid;
    }
    
    public void setWebsiteid(String websiteid) {
        this.websiteid = websiteid;
    }
    
    @Column(name="bookmarks_creator", nullable=false, length=32)

    public String getBookmarksCreator() {
        return this.bookmarksCreator;
    }
    
    public void setBookmarksCreator(String bookmarksCreator) {
        this.bookmarksCreator = bookmarksCreator;
    }
    
    @Column(name="bookmarks_datetime", nullable=false, length=19)

    public Timestamp getBookmarksDatetime() {
        return this.bookmarksDatetime;
    }
    
    public void setBookmarksDatetime(Timestamp bookmarksDatetime) {
        this.bookmarksDatetime = bookmarksDatetime;
    }
@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="bookmarksFolder")

    public Set<Bookmarks> getBookmarkses() {
        return this.bookmarkses;
    }
    
    public void setBookmarkses(Set<Bookmarks> bookmarkses) {
        this.bookmarkses = bookmarkses;
    }
    
    @Column(name="bookmarks_remark", nullable=false, length=400)
	public String getBookmarksRemark() {
		return bookmarksRemark;
	}

	public void setBookmarksRemark(String bookmarksRemark) {
		this.bookmarksRemark = bookmarksRemark;
	}
   








}