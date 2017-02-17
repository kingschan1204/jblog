package com.kingschan.blog.common.enums;
/**
 * 平台变量-枚举
 * @author kings.chan
 * date:20160622
 *
 */
public enum Variable {
	
	//命名规则   作用域-名称
	/**
	 * 用户名cookie key 
	 */
	COOKIE_USERNAME_KEY("cookie_user_name_key",111),
	/**
	 * 密码cookie key
	 */
	COOKIE_PSW_KEY("cookie_user_psw_key",112),
	/**
	 * 平台DES加密密钥
	 */
	COOKIE_ENCRYPT_KEY("kingschan_rbac",113),
	/**
	 * sessionid
	 */
	COOKIE_JSESSIONID("JSESSIONID",114),
	/**
	 * freemarker skin template  博客URL前缀
	 */
	FT_BLOG_PREFIX("blogprefix",4),
	/**
	 * 前端模板根路径
	 */
	FT_BLOG_TPATH("tpath",44),
	/**
	 * 后台博客前缀
	 */
	FT_ADMIN_BLOG_PREFIX("admin_blogprefix",8),
	/**
	 * 未登录被拦截的URL
	 */
	SESSION_URL_INTERCEPT("intercept",5),
	/**
	 * 当前博客
	 */
	SESSION_BLOG_WEBSITE("website",6),
	/**
	 * 后台可管理的站点
	 */
	SESSION_BLOG_ADMIN_WEBSITE("AdminWebSite",7),
	
	//////////////////////ehcache.xml///////////////////////////////
	/**
	 * 打回密码
	 */
	CACHE_CONTENT_FINDPSW("findPswCache",802),
	/**
	 * session cache 
	 */
	CACHE_CONTENT_SESSION("SessionCache",800),
	/**
	 * 邮箱验证
	 */
	CACHE_CONTENT_VALIDATE_EMAIL("validateEmailCache",801);
	
	private String key;
	private Integer value;
    private Variable(String key, int value) {  
        this.key = key;  
        this.value = value;  
    }
	
	
	public String getKey() {
		return key;
	}


	public void setKey(String key) {
		this.key = key;
	}


	public Integer getValue() {
		return value;
	}


	public void setValue(Integer value) {
		this.value = value;
	}


	@Override
	public String toString() {
		return String.format("%s:%s", this.value,this.key);
	}
    
}
