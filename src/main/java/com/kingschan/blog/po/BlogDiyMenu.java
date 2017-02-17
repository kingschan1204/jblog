package com.kingschan.blog.po;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;


/*@Entity
@Table(name = "blog_diy_menu")*/
public class BlogDiyMenu implements java.io.Serializable {

	// Fields

	
	private static final long serialVersionUID = 1L;
	private String id;
	private WebSite webSite;
	private String menuParent;
	private String menuTitle;
	private String menuHref;
	private String menuTarget;
	private String menuRemark;
	private Timestamp menuDatetime;

	// Constructors

	/** default constructor */
	public BlogDiyMenu() {
	}

	/** full constructor */
	public BlogDiyMenu(WebSite webSite, String menuParent, String menuTitle,
			String menuHref, String menuTarget, String menuRemark,
			Timestamp menuDatetime) {
		this.webSite = webSite;
		this.menuParent = menuParent;
		this.menuTitle = menuTitle;
		this.menuHref = menuHref;
		this.menuTarget = menuTarget;
		this.menuRemark = menuRemark;
		this.menuDatetime = menuDatetime;
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
	@JoinColumn(name = "menu_website", nullable = false)
	public WebSite getWebSite() {
		return this.webSite;
	}

	public void setWebSite(WebSite webSite) {
		this.webSite = webSite;
	}

	@Column(name = "menu_parent", nullable = false, length = 32)
	public String getMenuParent() {
		return this.menuParent;
	}

	public void setMenuParent(String menuParent) {
		this.menuParent = menuParent;
	}

	@Column(name = "menu_title", nullable = false, length = 20)
	public String getMenuTitle() {
		return this.menuTitle;
	}

	public void setMenuTitle(String menuTitle) {
		this.menuTitle = menuTitle;
	}

	@Column(name = "menu_href", nullable = false, length = 250)
	public String getMenuHref() {
		return this.menuHref;
	}

	public void setMenuHref(String menuHref) {
		this.menuHref = menuHref;
	}

	@Column(name = "menu_target", nullable = false, length = 10)
	public String getMenuTarget() {
		return this.menuTarget;
	}

	public void setMenuTarget(String menuTarget) {
		this.menuTarget = menuTarget;
	}

	@Column(name = "menu_remark", nullable = false, length = 200)
	public String getMenuRemark() {
		return this.menuRemark;
	}

	public void setMenuRemark(String menuRemark) {
		this.menuRemark = menuRemark;
	}

	@Column(name = "menu_datetime", nullable = false, length = 19)
	public Timestamp getMenuDatetime() {
		return this.menuDatetime;
	}

	public void setMenuDatetime(Timestamp menuDatetime) {
		this.menuDatetime = menuDatetime;
	}

}