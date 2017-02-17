package com.kingschan.blog.common.freemarker.util;

import java.io.File;
import java.io.StringWriter;
import java.util.Locale;
import java.util.Map;

import com.kingschan.blog.common.freemarker.template.StringTemplateLoader;
import com.kingschan.blog.util.PathUtil;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;

/**
 * freemarker 模板解析器
 * @author kings.chan
 * 2016-06-26
 *
 */
public class FreemarkerParseUtil {

	/**
	 * 解析模板返因String
	 * String.format("%s/%s", PathUtil.getWebInfPath(),"\\email-template\\"))
	 * @param data 数据模型 
	 * @param templateFolder 模板目录
	 * @param templateName 模板名称
	 * @return
	 * @throws Exception
	 */
	public static String parserFileTemplate(Map<String, Object> data,String templateFolder,String templateName) throws Exception{
		/* 在整个应用的生命周期中，这个工作你应该只做一次。 */
		/* 创建和调整配置。 */
		String encode="UTF-8";
		Configuration cfg = new Configuration();
		cfg.setDirectoryForTemplateLoading(new File(templateFolder));
		cfg.setObjectWrapper(new DefaultObjectWrapper());
		cfg.setDefaultEncoding(encode);
        cfg.setOutputEncoding(encode);
        cfg.setEncoding(Locale.CHINA,encode);
		/* 在整个应用的生命周期中，这个工作你可以执行多次 */
		/* 获取或创建模板*/
		Template temp = cfg.getTemplate(templateName);
		temp.setEncoding(encode);   
		/* 创建数据模型 */
		/* 将模板和数据模型合并 */
//		Writer out = new OutputStreamWriter(System.out);
		StringWriter writer = new StringWriter();
		temp.process(data, writer);
//		out.flush();
		return writer.toString();
	}
	/**
	 * 解析freemarker 字符串并返回内容
	 * @param freemarkerStr
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static String parserString(String freemarkerStr,Map<String, Object> data) throws Exception{
		 Configuration cfg = new Configuration();
	     cfg.setTemplateLoader(new StringTemplateLoader(freemarkerStr));
	     cfg.setDefaultEncoding("UTF-8");
	     Template template = cfg.getTemplate("");       
	     StringWriter writer = new StringWriter();
	     template.process(data, writer);
	     return writer.toString();
	}
	public static void main(String[] args) throws Exception {
		String s=FreemarkerParseUtil.parserFileTemplate(null, String.format("%s/%s", PathUtil.getWebInfPath(),"\\email-template\\"), "findpsw.html");
		System.out.println(s);
	}
}
