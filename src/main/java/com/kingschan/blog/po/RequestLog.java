package com.kingschan.blog.po;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;


@Entity
@Table(name = "blog_request_log")
public class RequestLog implements java.io.Serializable {

	// Fields

	private static final long serialVersionUID = 1L;
	private String id;
	private String reqUrl;
	private String reqMethod;
	private String reqIp;
	private String reqAgent;
	private String reqReferer;
	private Integer reqRunTime;
	private Timestamp reqDatetime;
	private String reqBlog;

	// Constructors

	/** default constructor */
	public RequestLog() {
	}

	/** minimal constructor */
	public RequestLog(String reqUrl, String reqMethod, String reqIp,
			Integer reqRunTime, Timestamp reqDatetime) {
		this.reqUrl = reqUrl;
		this.reqMethod = reqMethod;
		this.reqIp = reqIp;
		this.reqRunTime = reqRunTime;
		this.reqDatetime = reqDatetime;
	}

	/** full constructor */
	public RequestLog(String reqUrl, String reqMethod, String reqIp,
			String reqAgent, String reqReferer, Integer reqRunTime,
			Timestamp reqDatetime, String reqBlog) {
		this.reqUrl = reqUrl;
		this.reqMethod = reqMethod;
		this.reqIp = reqIp;
		this.reqAgent = reqAgent;
		this.reqReferer = reqReferer;
		this.reqRunTime = reqRunTime;
		this.reqDatetime = reqDatetime;
		this.reqBlog = reqBlog;
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

	@Column(name = "req_url", nullable = false, length = 300)
	public String getReqUrl() {
		return this.reqUrl;
	}

	public void setReqUrl(String reqUrl) {
		this.reqUrl = reqUrl;
	}

	@Column(name = "req_method", nullable = false, length = 5)
	public String getReqMethod() {
		return this.reqMethod;
	}

	public void setReqMethod(String reqMethod) {
		this.reqMethod = reqMethod;
	}

	@Column(name = "req_ip", nullable = false, length = 30)
	public String getReqIp() {
		return this.reqIp;
	}

	public void setReqIp(String reqIp) {
		this.reqIp = reqIp;
	}

	@Column(name = "req_agent", length = 400)
	public String getReqAgent() {
		return this.reqAgent;
	}

	public void setReqAgent(String reqAgent) {
		this.reqAgent = reqAgent;
	}

	@Column(name = "req_referer", length = 300)
	public String getReqReferer() {
		return this.reqReferer;
	}

	public void setReqReferer(String reqReferer) {
		this.reqReferer = reqReferer;
	}

	@Column(name = "req_run_time", nullable = false)
	public Integer getReqRunTime() {
		return this.reqRunTime;
	}

	public void setReqRunTime(Integer reqRunTime) {
		this.reqRunTime = reqRunTime;
	}

	@Column(name = "req_datetime", nullable = false, length = 19)
	public Timestamp getReqDatetime() {
		return this.reqDatetime;
	}

	public void setReqDatetime(Timestamp reqDatetime) {
		this.reqDatetime = reqDatetime;
	}

	@Column(name = "req_blog", length = 40)
	public String getReqBlog() {
		return this.reqBlog;
	}

	public void setReqBlog(String reqBlog) {
		this.reqBlog = reqBlog;
	}

}