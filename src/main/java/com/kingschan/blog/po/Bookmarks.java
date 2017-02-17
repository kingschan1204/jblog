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
@Table(name="bolg_bookmarks")
@Cache(usage=CacheConcurrencyStrategy.READ_WRITE)
public class Bookmarks  implements java.io.Serializable {


    // Fields    

     /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
     private BookmarksFolder bookmarksFolder;
     private String bookmarksName;
     private String bookmarksHref;
     private Integer bookmarksOrder;
     private String bookmarksCreator;
     private Timestamp bookmarksDatetime;


    // Constructors

    /** default constructor */
    public Bookmarks() {
    }

    
    /** full constructor */
    public Bookmarks(BookmarksFolder bookmarksFolder, String bookmarksName, String bookmarksHref, Integer bookmarksOrder, String bookmarksCreator, Timestamp bookmarksDatetime) {
        this.bookmarksFolder = bookmarksFolder;
        this.bookmarksName = bookmarksName;
        this.bookmarksHref = bookmarksHref;
        this.bookmarksOrder = bookmarksOrder;
        this.bookmarksCreator = bookmarksCreator;
        this.bookmarksDatetime = bookmarksDatetime;
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
	@ManyToOne(fetch=FetchType.LAZY)
        @JoinColumn(name="bookmarks_tid", nullable=false)

    public BookmarksFolder getBookmarksFolder() {
        return this.bookmarksFolder;
    }
    
    public void setBookmarksFolder(BookmarksFolder bookmarksFolder) {
        this.bookmarksFolder = bookmarksFolder;
    }
    
    @Column(name="bookmarks_name", nullable=false, length=50)

    public String getBookmarksName() {
        return this.bookmarksName;
    }
    
    public void setBookmarksName(String bookmarksName) {
        this.bookmarksName = bookmarksName;
    }
    
    @Column(name="bookmarks_href", nullable=false, length=200)

    public String getBookmarksHref() {
        return this.bookmarksHref;
    }
    
    public void setBookmarksHref(String bookmarksHref) {
        this.bookmarksHref = bookmarksHref;
    }
    
    @Column(name="bookmarks_order", nullable=false)

    public Integer getBookmarksOrder() {
        return this.bookmarksOrder;
    }
    
    public void setBookmarksOrder(Integer bookmarksOrder) {
        this.bookmarksOrder = bookmarksOrder;
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
   








}