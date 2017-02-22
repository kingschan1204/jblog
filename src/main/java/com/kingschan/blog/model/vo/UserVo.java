package com.kingschan.blog.model.vo;

import org.springframework.beans.BeanUtils;

import com.kingschan.blog.po.User;

public class UserVo {

	private String id;
	private String userName;
	private String userPsw;
	private Boolean userSex;
	private String userScreenName;
	private String userEmail;
	private Short userState;
	private String userDatetime;
	private String userLastlogin;
	private Boolean userEmailActivate;
	private Long sinaUid;
	private String userProfileImg;
	private String sinaToken;
	private Long sinaExpireIn;
	private String sinaProfileUrl;
	private String userLevel;

	/**
	 * 用户URL 新浪微博or 博客地址
	 */
	private String extendUserUrl;
	public User toUser() {
		User u = new User();
		BeanUtils.copyProperties(this, u);
		return u;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserPsw() {
		return userPsw;
	}

	public void setUserPsw(String userPsw) {
		this.userPsw = userPsw;
	}

	public Boolean getUserSex() {
		return userSex;
	}

	public void setUserSex(Boolean userSex) {
		this.userSex = userSex;
	}

	public String getUserScreenName() {
		return userScreenName;
	}

	public void setUserScreenName(String userScreenName) {
		this.userScreenName = userScreenName;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public Short getUserState() {
		return userState;
	}

	public void setUserState(Short userState) {
		this.userState = userState;
	}

	public String getUserDatetime() {
		return userDatetime;
	}

	public void setUserDatetime(String userDatetime) {
		this.userDatetime = userDatetime;
	}

	public String getUserLastlogin() {
		return userLastlogin;
	}

	public void setUserLastlogin(String userLastlogin) {
		this.userLastlogin = userLastlogin;
	}

	public Boolean getUserEmailActivate() {
		return userEmailActivate;
	}

	public void setUserEmailActivate(Boolean userEmailActivate) {
		this.userEmailActivate = userEmailActivate;
	}

	public Long getSinaUid() {
		return sinaUid;
	}

	public void setSinaUid(Long sinaUid) {
		this.sinaUid = sinaUid;
	}

	public String getSinaToken() {
		return sinaToken;
	}

	public void setSinaToken(String sinaToken) {
		this.sinaToken = sinaToken;
	}

	public Long getSinaExpireIn() {
		return sinaExpireIn;
	}

	public void setSinaExpireIn(Long sinaExpireIn) {
		this.sinaExpireIn = sinaExpireIn;
	}

	public String getSinaProfileUrl() {
		if (null!=sinaProfileUrl&&!sinaProfileUrl.isEmpty()) {
			return String.format("http://weibo.com/%s", sinaProfileUrl);
		}else{
			if (null==sinaUid) {
				return "";
			}else{
				return String.format("http://weibo.com/u/%s", sinaUid);
			}
		}
	}

	public void setSinaProfileUrl(String sinaProfileUrl) {
		this.sinaProfileUrl = sinaProfileUrl;
	}

	public String getUserLevel() {
		return userLevel;
	}

	public void setUserLevel(String userLevel) {
		this.userLevel = userLevel;
	}

	public String getExtendUserUrl() {
		return extendUserUrl;
	}

	public void setExtendUserUrl(String extendUserUrl) {
		this.extendUserUrl = extendUserUrl;
	}

	public String getUserProfileImg() {
		return userProfileImg;
	}

	public void setUserProfileImg(String userProfileImg) {
		this.userProfileImg = userProfileImg;
	}

	
}
