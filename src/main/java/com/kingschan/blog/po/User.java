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
import javax.persistence.UniqueConstraint;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.beans.BeanUtils;

import com.kingschan.blog.common.bean.convert.BeanConvert;

@Entity
@Table(name = "blog_user", catalog = "blog", uniqueConstraints = @UniqueConstraint(columnNames = "user_name"))
public class User implements java.io.Serializable, BeanConvert {

	// Fields

	private static final long serialVersionUID = 1L;
	private String id;
	private String userName;
	private String userPsw;
	private Boolean userSex;
	private String userScreenName;
	private String userEmail;
	private Short userState;
	private Timestamp userDatetime;
	private Timestamp userLastlogin;
	private Boolean userEmailActivate;
	private Long sinaUid;
	private String sinaToken;
	private Long sinaExpireIn;
	private String sinaProfileUrl;
	private String userLevel;
	private String userProfileImg;
	private String userUrl;
	private Set<Category> categories = new HashSet<Category>(0);
	private Set<Article> articles = new HashSet<Article>(0);
	private Set<WebSite> webSites = new HashSet<WebSite>(0);

	// Constructors

	/** default constructor */
	public User() {
	}

	/** minimal constructor */
	public User(String userName, String userPsw, Boolean userSex,
			Short userState, Timestamp userDatetime) {
		this.userName = userName;
		this.userPsw = userPsw;
		this.userSex = userSex;
		this.userState = userState;
		this.userDatetime = userDatetime;
	}

	/** full constructor */
	public User(String userName, String userPsw, Boolean userSex,
			String userScreenName, String userEmail, Short userState,
			Timestamp userDatetime, Timestamp userLastlogin,
			Boolean userEmailActivate, Long sinaUid, String sinaToken, Long sinaExpireIn,
			String sinaProfileUrl, String userLevel, Set<Category> categories,
			Set<Article> articles, Set<WebSite> webSites) {
		this.userName = userName;
		this.userPsw = userPsw;
		this.userSex = userSex;
		this.userScreenName = userScreenName;
		this.userEmail = userEmail;
		this.userState = userState;
		this.userDatetime = userDatetime;
		this.userLastlogin = userLastlogin;
		this.userEmailActivate = userEmailActivate;
		this.sinaUid = sinaUid;
		this.sinaToken = sinaToken;
		this.sinaExpireIn = sinaExpireIn;
		this.sinaProfileUrl = sinaProfileUrl;
		this.userLevel = userLevel;
		this.categories = categories;
		this.articles = articles;
		this.webSites = webSites;
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

	@Column(name = "user_name", unique = true, nullable = false, length = 10)
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "user_psw", nullable = false, length = 32)
	public String getUserPsw() {
		return this.userPsw;
	}

	public void setUserPsw(String userPsw) {
		this.userPsw = userPsw;
	}

	@Column(name = "user_sex", nullable = false)
	public Boolean getUserSex() {
		return this.userSex;
	}

	public void setUserSex(Boolean userSex) {
		this.userSex = userSex;
	}

	@Column(name = "user_screen_name", length = 50)
	public String getUserScreenName() {
		return userScreenName;
	}

	public void setUserScreenName(String userScreenName) {
		this.userScreenName = userScreenName;
	}

	@Column(name = "user_email", length = 32)
	public String getUserEmail() {
		return this.userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	@Column(name = "user_state", nullable = false)
	public Short getUserState() {
		return this.userState;
	}

	public void setUserState(Short userState) {
		this.userState = userState;
	}

	@Column(name = "user_datetime", nullable = false, length = 19)
	public Timestamp getUserDatetime() {
		return this.userDatetime;
	}

	public void setUserDatetime(Timestamp userDatetime) {
		this.userDatetime = userDatetime;
	}

	@Column(name = "user_lastlogin", length = 19)
	public Timestamp getUserLastlogin() {
		return this.userLastlogin;
	}

	public void setUserLastlogin(Timestamp userLastlogin) {
		this.userLastlogin = userLastlogin;
	}

	@Column(name = "user_email_activate")
	public Boolean getUserEmailActivate() {
		return null == this.userEmailActivate ? false : this.userEmailActivate;
	}

	public void setUserEmailActivate(Boolean userEmailActivate) {
		this.userEmailActivate = userEmailActivate;
	}

	@Column(name = "sina_uid")
	public Long getSinaUid() {
		return this.sinaUid;
	}

	public void setSinaUid(Long sinaUid) {
		this.sinaUid = sinaUid;
	}

	@Column(name = "sina_token", length = 50)
	public String getSinaToken() {
		return this.sinaToken;
	}

	public void setSinaToken(String sinaToken) {
		this.sinaToken = sinaToken;
	}

	@Column(name = "sina_expire_in")
	public Long getSinaExpireIn() {
		return this.sinaExpireIn;
	}

	public void setSinaExpireIn(Long sinaExpireIn) {
		this.sinaExpireIn = sinaExpireIn;
	}

	@Column(name = "sina_profile_url", length = 50)
	public String getSinaProfileUrl() {
		return sinaProfileUrl;
	}

	public void setSinaProfileUrl(String sinaProfileUrl) {
		this.sinaProfileUrl = sinaProfileUrl;
	}

	@Column(name = "user_level", length = 10)
	public String getUserLevel() {
		return this.userLevel;
	}

	public void setUserLevel(String userLevel) {
		this.userLevel = userLevel;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
	public Set<Category> getCategories() {
		return this.categories;
	}

	public void setCategories(Set<Category> categories) {
		this.categories = categories;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
	public Set<Article> getArticles() {
		return this.articles;
	}

	public void setArticles(Set<Article> articles) {
		this.articles = articles;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
	public Set<WebSite> getWebSites() {
		return this.webSites;
	}

	public void setWebSites(Set<WebSite> webSites) {
		this.webSites = webSites;
	}

	@Override
	public <UserVo> UserVo po2vo(UserVo obj) throws Exception {
		BeanUtils.copyProperties(this, obj);
		return obj;
	}

	@Column(name = "user_profile_img", length = 300)
	public String getUserProfileImg() {
		return userProfileImg;
	}

	public void setUserProfileImg(String userProfileImg) {
		this.userProfileImg = userProfileImg;
	}

	@Column(name = "user_url", length = 50)
	public String getUserUrl() {
		return userUrl;
	}

	public void setUserUrl(String userUrl) {
		this.userUrl = userUrl;
	}
}