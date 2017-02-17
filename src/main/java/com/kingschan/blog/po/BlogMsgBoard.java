package com.kingschan.blog.po;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.beans.BeanUtils;

import com.kingschan.blog.common.bean.convert.BeanConvert;


@Entity
@Table(name = "blog_msg_board")
public class BlogMsgBoard implements java.io.Serializable,BeanConvert {

	// Fields

	private static final long serialVersionUID = 1L;
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
	// Constructors

	/** default constructor */
	public BlogMsgBoard() {
	}

	/** full constructor */
	public BlogMsgBoard(String websiteid, String msgText, User msgUid,
			User msgAt, String msgRoot, Timestamp msgDatetime,
			String msgFlag, Integer msgLike) {
		this.websiteid = websiteid;
		this.msgText = msgText;
		this.msgSendUser = msgUid;
		this.msgAt = msgAt;
		this.msgRoot = msgRoot;
		this.msgDatetime = msgDatetime;
		this.msgFlag = msgFlag;
		this.msgLike = msgLike;
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

	@Column(name = "websiteid", nullable = false, length = 32)
	public String getWebsiteid() {
		return this.websiteid;
	}

	public void setWebsiteid(String websiteid) {
		this.websiteid = websiteid;
	}

	@Column(name = "msg_text", nullable = false, length = 500)
	public String getMsgText() {
		return this.msgText;
	}

	public void setMsgText(String msgText) {
		this.msgText = msgText;
	}

	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="msg_at", nullable=false)
	public User getMsgAt() {
		return this.msgAt;
	}
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="msg_uid", nullable=false)
	public User getMsgSendUser() {
		return msgSendUser;
	}

	public void setMsgSendUser(User msgSendUser) {
		this.msgSendUser = msgSendUser;
	}

	public void setMsgAt(User msgAt) {
		this.msgAt = msgAt;
	}

	@Column(name = "msg_root", nullable = false, length = 32)
	public String getMsgRoot() {
		return this.msgRoot;
	}

	public void setMsgRoot(String msgRoot) {
		this.msgRoot = msgRoot;
	}

	@Column(name = "msg_datetime", nullable = false, length = 19)
	public Timestamp getMsgDatetime() {
		return this.msgDatetime;
	}

	public void setMsgDatetime(Timestamp msgDatetime) {
		this.msgDatetime = msgDatetime;
	}

	@Column(name = "msg_flag", nullable = false, length = 2)
	public String getMsgFlag() {
		return this.msgFlag;
	}

	public void setMsgFlag(String msgFlag) {
		this.msgFlag = msgFlag;
	}

	@Column(name = "msg_like", nullable = false)
	public Integer getMsgLike() {
		return this.msgLike;
	}

	public void setMsgLike(Integer msgLike) {
		this.msgLike = msgLike;
	}
	@Column(name = "msg_count", nullable = false)
	public Integer getMsgCount() {
		return msgCount;
	}

	public void setMsgCount(Integer msgCount) {
		this.msgCount = msgCount;
	}

	@Override
	public <MsgBoardVo> MsgBoardVo po2vo(MsgBoardVo obj) throws Exception {
		BeanUtils.copyProperties(this, obj);
		return obj;
	}

	
}