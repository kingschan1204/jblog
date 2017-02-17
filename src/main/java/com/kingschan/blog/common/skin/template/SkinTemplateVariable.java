package com.kingschan.blog.common.skin.template;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.kingschan.blog.common.enums.BLOG_MODEL;
import com.kingschan.blog.common.enums.Variable;
import com.kingschan.blog.po.WebSite;
import com.kingschan.blog.services.impl.CommonServiceImpl;
import com.kingschan.blog.util.BlogUtil;
import com.kingschan.blog.util.RegexUtil;
/**
 * 皮肤模板变量
 * @author kings.chan
 *<pre>
 * ${blogprefix} : 前端博客URl前缀
 * ${webroot} : http://部署的域名
 * ${host} : 部署的域名
 * ${admin_blogprefix} : 后台博客前缀
 *</pre>
 */
@Component
public class SkinTemplateVariable {

	@Qualifier("CommonServiceImpl")
	@Autowired
	private CommonServiceImpl commonServ;
	/**
	 * 设置模板变量
	 * @param req
	 * @param site
	 */
	public  void setVariable(HttpServletRequest req,WebSite site,BLOG_MODEL model ){
		//blogprefix BLOG_PREFIX
		/*Properties prop = System.getProperties(); 
		String os = prop.getProperty("os.name");*/
		switch (model) {
		case FONT_MODEL:
			/*if (os.toLowerCase().indexOf("win")!=-1) {
				//windows|http://${website.websiteName!}.${host!}${projectName}
				 req.getSession().setAttribute(Variable.FT_BLOG_PREFIX.getKey(), 
				 String.format("http://%s.%s", site.getWebsiteName(),host)
				);
			}else{
				//windows|${webroot}/${website.websiteName!}
				
			}*/
			//设置博客URL前缀 ${blogprefix}
			req.getSession().setAttribute(Variable.FT_BLOG_PREFIX.getKey(), getBlogprefix(req,site));
			//设置模板根路径
			req.getSession().setAttribute(Variable.FT_BLOG_TPATH.getKey(), 
					String.format("/skin/%s/font/", site.getWebsiteSkin())
					);
			req.getSession().setAttribute("locationUri",req.getRequestURI());
			BlogUtil bu = new BlogUtil(req);
			String device=null==bu.getUserAgent().getOperatingSystem()?"":String.valueOf(bu.getUserAgent().getOperatingSystem().getDeviceType().getName());
			//设置访问设备
			req.setAttribute("device",device);
			break;

		case ADMIN_MODEL:
			 req.getSession().setAttribute(Variable.FT_ADMIN_BLOG_PREFIX.getKey(), 
					 String.format("http://%s", commonServ.getHost())
					);
			break;
		}
		
		
	}
	
	/**
	 * 返回前台博客前缀URL
	 * @param req
	 * @param site
	 * @return ${blogprefix}
	 */
	public String getBlogprefix(HttpServletRequest req,WebSite site){
		//开发模式下的 localhost/博客名方式
		String url=req.getRequestURL().toString();
		if (url.matches("^http(s)?://localhost/.*")) {
			return RegexUtil.findStrByRegx(url, "^http(s)?://localhost/\\w+");
		}
		return String.format("http://%s.%s", site.getWebsiteName(),commonServ.getHost());
		
	}
	
}
