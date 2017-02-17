package com.kingschan.blog.common.freemarker.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.kingschan.blog.util.PathUtil;

import freemarker.template.Template;

/**
 * 
 * <pre>
 * 类名称：TemplateStaticUtil 
 * 类描述：   模板静态化处理
 * 创建人：陈国祥   (kingschan)
 * 创建时间：2014-8-8 上午10:58:06   
 * 修改人：Administrator   
 * 修改时间：2014-8-8 上午10:58:06   
 * 修改备注：   
 * @version V1.0
 * </pre>
 */
@Component
public class TemplateStaticUtil {
	
	private static Logger log = LoggerFactory.getLogger(TemplateStaticUtil.class);
	@Resource(name="freemarkerConfig")
	private FreeMarkerConfigurer freemarkerCfg;
	/**
	 * 是否存在缓存
	 * @param htmlname
	 * @return
	 */
	public boolean existsCache(String htmlname){
		String path=String.format("%s%s", PathUtil.getWebInfPath().concat("/page/cache/"),htmlname);
		File f = new File(path);
		return f.exists();
	}
	/**
	 * 删除缓存
	 * @param htmlname
	 * @return
	 */
	public boolean deleteCache(String htmlname){
		String path=String.format("%s%s", PathUtil.getWebInfPath(),htmlname);
		File f = new File(path);
		return f.delete();
	}
	
	/**
	 * 生成静态页面主方法
	 * 
	 * @param context
	 *            ServletContext
	 * @param data
	 *            一个Map的数据结果集
	 * @param templatePath
	 *            ftl模版路径
	 * @param htmlpath
	 *            生成静态页面的名称
	 */
	public Boolean crateHTML(Map<String, Object> data, String templatePath, String htmlname) {
		boolean successed=false;
		// 加载模版
//		freemarkerCfg.setServletContextForTemplateLoading(context, "/");
//		freemarkerCfg.setEncoding(Locale.getDefault(), container.getConf().getEncoding());
//		String cacheFolder=container.getSerializationPath();//缓存目录
		File htmlFile=null;
		try {
			// 指定模版路径
			Template template = freemarkerCfg.getConfiguration().getTemplate(templatePath,"UTF-8");
			template.setEncoding("UTF-8");// 静态页面路径
			String htmlPath = String.format("%s%s", PathUtil.getWebInfPath().concat("/page/cache/"),htmlname);
			htmlFile = new File(htmlPath);
			Writer out = new BufferedWriter(new OutputStreamWriter(
					new FileOutputStream(htmlFile), "UTF-8"));
			// 处理模版
			template.process(data, out);
			out.flush();
			out.close();
			successed=true;
		} catch (Exception e) {
			log.error("静态化处理操作失败,执行删除："+deleteCache(htmlname));
			e.printStackTrace();			
		}
		return successed;
	}
	/**
	 * 渲染模板
	 * @param context
	 * @param data
	 * @param templatePath
	 * @param htmlname
	 * @throws Exception 
	 */
	public void renderTemplate(HttpServletRequest req,HttpServletResponse rep,Map<String, Object> data, String templatePath) {
		try {
			rep.setContentType("text/html;charSet=UTF-8");
//			String basePath =String.format("%s://%s:%s%s/", req.getScheme(),req.getServerName(),req.getServerPort(),req.getContextPath());
//			FreeActionCoreRequestFilter.cfg.setSharedVariable("path", basePath);
			Template temp =freemarkerCfg.getConfiguration().getTemplate(templatePath,"UTF-8");
			temp.setEncoding("UTF-8");// 静态页面路径 
			Writer out = new OutputStreamWriter(rep.getOutputStream(),"UTF-8");
			temp.process(data, out);	
			out.flush();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
