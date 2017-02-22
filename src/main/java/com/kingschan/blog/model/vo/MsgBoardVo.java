package com.kingschan.blog.model.vo;

import java.sql.Timestamp;

import com.kingschan.blog.po.User;

public class MsgBoardVo {

	private String id;
	private String websiteid;
	private String msgText;
	private User msgSendUser;
	private User msgAt;
	private String msgRoot;
	private Timestamp msgDatetime;
	private String msgFlag;
	private Integer msgLike;
	private Integer msgCount;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getWebsiteid() {
		return websiteid;
	}
	public void setWebsiteid(String websiteid) {
		this.websiteid = websiteid;
	}
	public String getMsgText() {
		return msgText;
	}
	public void setMsgText(String msgText) {
		this.msgText = msgText;
	}
	public User getMsgSendUser() {
		return msgSendUser;
	}
	public void setMsgSendUser(User msgSendUser) {
		this.msgSendUser = msgSendUser;
	}
	public User getMsgAt() {
		return msgAt;
	}
	public void setMsgAt(User msgAt) {
		this.msgAt = msgAt;
	}
	public String getMsgRoot() {
		return msgRoot;
	}
	public void setMsgRoot(String msgRoot) {
		this.msgRoot = msgRoot;
	}
	public Timestamp getMsgDatetime() {
		return msgDatetime;
	}
	public void setMsgDatetime(Timestamp msgDatetime) {
		this.msgDatetime = msgDatetime;
	}
	public String getMsgFlag() {
		return msgFlag;
	}
	public void setMsgFlag(String msgFlag) {
		this.msgFlag = msgFlag;
	}
	public Integer getMsgLike() {
		return msgLike;
	}
	public void setMsgLike(Integer msgLike) {
		this.msgLike = msgLike;
	}
	public Integer getMsgCount() {
		return msgCount;
	}
	public void setMsgCount(Integer msgCount) {
		this.msgCount = msgCount;
	}
	
	
	
}
