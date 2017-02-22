package com.kingschan.blog.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.pegdown.Extensions;
import org.pegdown.PegDownProcessor;

import com.fasterxml.uuid.EthernetAddress;
import com.fasterxml.uuid.Generators;
import com.kingschan.blog.common.enums.BLOG_SKIN_PAGE;
import com.kingschan.blog.common.enums.Variable;
import com.kingschan.blog.model.vo.UserVo;
import com.kingschan.blog.po.WebSite;
import com.kingschan.blog.services.impl.CommonServiceImpl;

import eu.bitwalker.useragentutils.UserAgent;
/**
 * 
*    
* 类名称：BlogUtil   
* 类描述：   博客工具类
* 创建人：kings.chan
* 创建时间：2016-7-21 上午12:24:52   
* 修改人：   
* 修改时间：
* 项目：ROOT
* 修改备注：   
* @version    
*
 */
public class BlogUtil {

    private HttpServletRequest req;
    public static final String CURRENT_USER = "BLOG_CURRENT_USER";
    public BlogUtil(HttpServletRequest p_req){this.req=p_req;}
    /**
     * 是否已经登录
     * @return
     */
    public boolean isLogin(){
        UserVo u =(UserVo) getSession().getAttribute(CURRENT_USER);
        return null==u?false:true;
    }
    /**
     * 得到当前登录的用户
     * @return
     */
    public UserVo getCurrentUser(){
        return (UserVo) getSession().getAttribute(CURRENT_USER);
    }
    
    /**
     * 得到后台当前博客
     * @return
     */
    public WebSite getCurrentAdminWebSite(){
        return (WebSite) getSession().getAttribute(Variable.SESSION_BLOG_ADMIN_WEBSITE.getKey());
    }
    /**
     * 设置后台当前博客
     * @param ws
     */
    public void setCurrentAdminWebSite(WebSite ws){
         getSession().setAttribute(Variable.SESSION_BLOG_ADMIN_WEBSITE.getKey(),ws);
    }
    /**
     * 得到前端当前博客
     * @return
     */
    public WebSite getCurrentFontWebSite(){
        return (WebSite) getSession().getAttribute(Variable.SESSION_BLOG_WEBSITE.getKey());
    }
    /**
    * 退出
    * @param req
    */
   public  void loginOut(){
	   String sid=new CookieUtil(req,null).getCookieAttribute("JSESSIONID");
   		if (null!=sid&&sid.matches("\\w{32}")) {
   		try {
   				CommonServiceImpl serv=SpringUtil.getBean("CommonServiceImpl");
   				HttpSession session=(HttpSession) serv.getCache(Variable.CACHE_CONTENT_SESSION.getKey(), sid);
				if (null!=session) {
					session.invalidate();
				}
				serv.removeCache(Variable.CACHE_CONTENT_SESSION.getKey(), sid);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			req.getSession().invalidate();
		}
   		
   }
   /**
    * set sina referer
    * @param url
    */
   public void setSinaLoginReferer(String url){
	   getSession().setAttribute("referer",url.matches(".*/entry/\\w{32}(.html)?")?url.concat("#commentDiv"):url);
   }
   /**
    * sina login referer
    * @return
    */
   public String getSinaLoginReferer(){
	   Object ref=getSession().getAttribute("referer");
	   return null==ref?String.format("redirect:http://%s.51so.info", getCurrentFontWebSite().getWebsiteName()):ref.toString();
   }
   /**
    * 得到博客对应的模板
    * @param template_key
    * @param isFontSite 是否前端
    * @return
    */
   public String getTemplate(BLOG_SKIN_PAGE template_key,Boolean isFontSite){
	   String skin=isFontSite?getCurrentFontWebSite().getWebsiteSkin():getCurrentAdminWebSite().getWebsiteSkin();
	   return String.format(template_key.getPath(), skin);
   }
   /**
    * 得到session
    * @return
    */
   public HttpSession getSession(){
	   String sid=new CookieUtil(req,null).getCookieAttribute("JSESSIONID");
	   HttpSession session=null;
   		if (null!=sid&&sid.matches("\\w{32}")) {
   		try {
   				CommonServiceImpl serv=SpringUtil.getBean("CommonServiceImpl");
				session=(HttpSession) serv.getCache(Variable.CACHE_CONTENT_SESSION.getKey(), sid);
				if (null==session) {
					session=req.getSession();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			session=req.getSession();
		}
   		return session;
   }
   /**
    * 得到定户端IP地址
    * @param request
    * @return
    */
   public static String getClientIP(HttpServletRequest request) {
       String ipAddress = null;
       ipAddress = request.getHeader("x-forwarded-for");
       if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
           ipAddress = request.getHeader("Proxy-Client-IP");
       }
       if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
           ipAddress = request.getHeader("WL-Proxy-Client-IP");
       }
       if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
           ipAddress = request.getRemoteAddr();
       }
       // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
       if (ipAddress != null && ipAddress.length() > 15) { // "***.***.***.***".length() = 15
           if (ipAddress.indexOf(",") > 0) {
               ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
           }
       }
       return ipAddress;
   }

   /**
    * 得到代理工具访问对象
    * @return
    */
   public UserAgent getUserAgent(){
	   return UserAgent.parseUserAgentString(req.getHeader("User-Agent")); 
   }
   /**
    * 当前访问是否是机器人
    * @return
    */
   public boolean isRobot(){
	   return getUserAgent().getOperatingSystem().getName().equals("Unknown");
   }
   
   /**
    * 清理掉记录的登录信息
    * @param req
    * @param res
    * @param domain cookie share domain sample:.51so.info
    */
   public void clearLoginCookies(HttpServletRequest req, HttpServletResponse res,String domain) {
       CookieUtil cu = new CookieUtil(req, res);
       cu.removeCookie(domain,Variable.COOKIE_USERNAME_KEY.getKey());
       cu.removeCookie(domain,Variable.COOKIE_PSW_KEY.getKey());
       cu.removeCookie(domain,Variable.COOKIE_JSESSIONID.getKey());
   }
   /**
    * 得到文章避编辑器
    * @param type
    * @return
    */
   public String getArticleEditorTemplate(String type){
	  return type.equalsIgnoreCase("html")?
   			"/admin/article/article_edit_ueditor":"/admin/article/article_edit_markdown";
   }
   /**
    * 得到安全的提交字符串  jsoup basic
    * @param content
    * @return
    */
   public static String getSafeSubmitString(String content){
	   return Jsoup.clean(content, Whitelist.basic());
   }
   /**
    * 过滤xss攻击
    * @param content
    * @return
    */
   public static String filterXss(String content){
	   if (StringUtils.isNotEmpty(content)) {
		try {
			return StringEscapeUtils.escapeHtml(URLDecoder.decode(content, "utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	return null;
		
   }
   
   /**
    * 把markdown转成html
    * @param content
    * @return
    */
   public static String markDownToHtml(String content){
	   PegDownProcessor pegDownProcessor = new PegDownProcessor(Extensions.ALL_OPTIONALS | Extensions.ALL_WITH_OPTIONALS, 5000);
	   return pegDownProcessor.markdownToHtml(content);
   }
   
   /**
    * 评论时 at 用户名格式化
    * @param content
    * @return
    */
   public static String atUserFormat(String content){
	   Pattern pattern = Pattern.compile("\\@\\w+"); 
		Matcher matcher = pattern.matcher(content); 
		    while (matcher.find()) { 
		      String hit=matcher.group();
		      if (!hit.trim().isEmpty()) {
			      content=content.replace(hit, String.format("<a class=\"atlink\">%s</a>", hit));
		      }
		    } 
		return content;
   }
   
   public static UUID  getUUIDByTimeBase(){
	 return Generators.timeBasedGenerator(EthernetAddress.fromInterface()).generate();
   }
  
   public static void main(String[] args) {
	 for (int i = 0; i < 20; i++) {
		 UUID uuid =BlogUtil.getUUIDByTimeBase();
		 Calendar cal = Calendar.getInstance();
		 cal.setTimeInMillis(uuid.timestamp()/1000000*100);
		 cal.add(Calendar.YEAR, 	1582-1970);
		 cal.add(Calendar.MONTH, 10);
		 cal.add(Calendar.DAY_OF_MONTH, 15);
		 //输出2017-03-15 10:20:34，其实当前系统时间是2017-02-10 10:20:34 时间上还是有误差
		 String time=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(cal.getTime());
		System.out.println(String.format("%s|%s|%s", uuid.toString(),uuid.timestamp(),time));
	}
   }
}
