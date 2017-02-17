package com.kingschan.blog.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
*  <pre>    
* 类名称：CookieUtil 
* 类描述：   cookie 操作便捷类
* 创建人：陈国祥   (kingschan)
* 创建时间：2013-4-3  
* 修改人：Administrator   
* 修改时间：2014-7-31 上午11:41:39   
* 修改备注：   
* @version V1.0
* </pre>
 */
public class CookieUtil {
	private HttpServletRequest request;
	private HttpServletResponse response;
	
	public CookieUtil(HttpServletRequest request,HttpServletResponse response){
	    this.request=request;
	    this.response=response;
	}
	/**
	 * 得到cookie数组
	 * @return cookie[]
	 */
	public  Cookie[] getCookies(){
		Cookie[] cs=this.getRequest().getCookies();			
		return cs;
	}
	/**
	 * 传入一个cookie键 删除一个cookie
	 * @param domain sample:.51so.info
	 * @param key
	 */
	public  void removeCookie(String domain,String key){
		Cookie[] cs=this.getRequest().getCookies();	
		for (Cookie cookie : cs) {
			if (cookie.getName().equals(key)) {
				cookie = new Cookie(key, null); 
				cookie.setDomain(domain);
				cookie.setMaxAge(0);
				cookie.setPath("/");
				this.getResponse().addCookie(cookie);
			}
		}
	}
		
	/**
	 * 根据一个cookie的名字得到cookie值 
	 * <font style="color:red;">如果没有找到则返回null</font>
	 * @param key cookie的名字
	 * @return  cookie的值
	 */
	public  String getCookieAttribute(String key){
		Cookie[] cs=getCookies();
		String target=null;
		if (null==cs) {
			return null;
		}
		for (Cookie cookie : cs) {
			if (cookie.getName().equals(key)) {
				target=cookie.getValue();
			}
		}
		return target;
	}
	/**
	 * 添加一个cookie对象
	 * @param name cookie名字
	 * @param value cookie值 
	 * @param age   cookie的存活期  已秒为单位
	 */
	public  void setCookieAttribute(String name,String value,int age,String path,String domain){
		Cookie c = new  Cookie(name, value);
		c.setMaxAge(age);
		c.setPath(path);
		c.setDomain(domain);
		getResponse().addCookie(c);
	}
	

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}
	
	
}
