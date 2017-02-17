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
@Table(name = "blog_website")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class WebSite implements java.io.Serializable {

	// Fields

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String id;
	private User user;
	private String websiteName;
	private String websiteTitle;
	private String websiteTagline;
	private String websiteKeyword;
	private String websiteAbout;
	private String websiteAnalyticscode;
	private Timestamp websiteDatetime;
	private Boolean websiteAllowcomments;
	private String websiteSkin;
	//
	private String websiteEditor;
	private String websiteNotice;
	private String websiteFooter;
	private String websiteCover;
	private Set<Lable> lables = new HashSet<Lable>(0);
//	private Set<BlogDiyMenu> menus=new HashSet<BlogDiyMenu>(0);

	// private Set<BookmarksFolder> bookmarksFolders = new
	// HashSet<BookmarksFolder>(0);

	// Constructors

	/** default constructor */
	public WebSite() {
	}

	/** minimal constructor */
	public WebSite(User user, String websiteName, String websiteTitle,
			String websiteTagline, String websiteKeyword,
			Timestamp websiteDatetime, Boolean websiteAllowcomments) {
		this.user = user;
		this.websiteName = websiteName;
		this.websiteTitle = websiteTitle;
		this.websiteTagline = websiteTagline;
		this.websiteKeyword = websiteKeyword;
		this.websiteDatetime = websiteDatetime;
		this.websiteAllowcomments = websiteAllowcomments;
	}

	/** full constructor */
	public WebSite(User user, String websiteName, String websiteTitle,
			String websiteTagline, String websiteKeyword, String websiteAbout,
			String websiteAnalyticscode, Timestamp websiteDatetime,
			Boolean websiteAllowcomments, Set<Lable> lables) {
		this.user = user;
		this.websiteName = websiteName;
		this.websiteTitle = websiteTitle;
		this.websiteTagline = websiteTagline;
		this.websiteKeyword = websiteKeyword;
		this.websiteAbout = websiteAbout;
		this.websiteAnalyticscode = websiteAnalyticscode;
		this.websiteDatetime = websiteDatetime;
		this.websiteAllowcomments = websiteAllowcomments;
		this.lables = lables;
		// this.bookmarksFolders = bookmarksFolders; , Set<BookmarksFolder>
		// bookmarksFolders
	}

	// Property accessors
	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id", unique = true, nullable = false, length = 32)
	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "webiste_creator", nullable = false)
	public User getUser() {
		return this.user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Column(name = "website_name", nullable = false, length = 12)
	public String getWebsiteName() {
		return this.websiteName;
	}

	public void setWebsiteName(String websiteName) {
		this.websiteName = websiteName;
	}

	@Column(name = "website_title", nullable = false, length = 50)
	public String getWebsiteTitle() {
		return this.websiteTitle;
	}

	public void setWebsiteTitle(String websiteTitle) {
		this.websiteTitle = websiteTitle;
	}

	@Column(name = "website_tagline", nullable = false, length = 100)
	public String getWebsiteTagline() {
		return this.websiteTagline;
	}

	public void setWebsiteTagline(String websiteTagline) {
		this.websiteTagline = websiteTagline;
	}

	@Column(name = "website_keyword", nullable = false, length = 120)
	public String getWebsiteKeyword() {
		return this.websiteKeyword;
	}

	public void setWebsiteKeyword(String websiteKeyword) {
		this.websiteKeyword = websiteKeyword;
	}

	@Column(name = "website_about", length = 300)
	public String getWebsiteAbout() {
		return this.websiteAbout;
	}

	public void setWebsiteAbout(String websiteAbout) {
		this.websiteAbout = websiteAbout;
	}

	@Column(name = "website_analyticscode", length = 500)
	public String getWebsiteAnalyticscode() {
		return null==this.websiteAnalyticscode?"":this.websiteAnalyticscode;
	}

	public void setWebsiteAnalyticscode(String websiteAnalyticscode) {
		this.websiteAnalyticscode = websiteAnalyticscode;
	}

	@Column(name = "website_datetime", nullable = false, length = 19)
	public Timestamp getWebsiteDatetime() {
		return this.websiteDatetime;
	}

	public void setWebsiteDatetime(Timestamp websiteDatetime) {
		this.websiteDatetime = websiteDatetime;
	}

	@Column(name = "website_allowcomments", nullable = false)
	public Boolean getWebsiteAllowcomments() {
		return this.websiteAllowcomments;
	}

	public void setWebsiteAllowcomments(Boolean websiteAllowcomments) {
		this.websiteAllowcomments = websiteAllowcomments;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "webSite")
	public Set<Lable> getLables() {
		return this.lables;
	}

	public void setLables(Set<Lable> lables) {
		this.lables = lables;
	}

	/*
	 * @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY,
	 * mappedBy="webSite")
	 * 
	 * public Set<BookmarksFolder> getBookmarksFolders() { return
	 * this.bookmarksFolders; }
	 * 
	 * public void setBookmarksFolders(Set<BookmarksFolder> bookmarksFolders) {
	 * this.bookmarksFolders = bookmarksFolders; }
	 */
	@Column(name = "website_skin", length = 20)
	public String getWebsiteSkin() {
		return websiteSkin;
	}

	public void setWebsiteSkin(String websiteSkin) {
		this.websiteSkin = websiteSkin;
	}

	@Column(name = "website_editor", nullable = false, length = 10)
	public String getWebsiteEditor() {
		return this.websiteEditor;
	}

	public void setWebsiteEditor(String websiteEditor) {
		this.websiteEditor = websiteEditor;
	}

	@Column(name = "website_notice", nullable = false, length = 300)
	public String getWebsiteNotice() {
		return this.websiteNotice;
	}

	public void setWebsiteNotice(String websiteNotice) {
		this.websiteNotice = websiteNotice;
	}

	@Column(name = "website_footer", nullable = false, length = 100)
	public String getWebsiteFooter() {
		return this.websiteFooter;
	}

	public void setWebsiteFooter(String websiteFooter) {
		this.websiteFooter = websiteFooter;
	}
	/*@OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="webSite")
	public Set<BlogDiyMenu> getMenus() {
		return menus;
	}

	public void setMenus(Set<BlogDiyMenu> menus) {
		this.menus = menus;
	}
*/
	@Column(name = "website_cover", nullable = false, length = 100)
	public String getWebsiteCover() {
		return websiteCover;
	}

	public void setWebsiteCover(String websiteCover) {
		this.websiteCover = websiteCover;
	}
	
	
}