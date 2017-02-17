package com.kingschan.blog.util;

import java.io.File;
import java.util.Map;

import org.dom4j.Node;

import com.kingschan.blog.common.freemarker.util.FreemarkerParseUtil;
import com.kingschan.blog.dao.impl.ReportDaoImpl;
public class SqlUtil {

	/**
	 * 得到SQL语句
	 * @param fileName 文件夹
	 * @param id 
	 * @return
	 */
	public static String getSql(String fileName,String id){
		String sql=null;
		try {
			String path=ReportDaoImpl.class.getClassLoader().getResource("").getPath().toString().concat(String.format("%s.xml", fileName));
			File f = new File(path);
			XmlOperation xml = new XmlOperation(f);
			Node node= xml.getNodeByExpression(String.format("/body/sql[@id='%s']", id));
			sql=node.getText().trim().replaceAll("\\s", " ");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sql;
	}
	/**
	 * 得到SQL并解析返回
	 * @param fileName 文件名
	 * @param id id号
	 * @param data 参数
	 * @return
	 */
	public static String getSql(String fileName,String id,Map<String,Object> data){
		String sql=null;
		try {
			sql=getSql(fileName, id);
			if (null!=sql&&!sql.isEmpty()) {
				sql=FreemarkerParseUtil.parserString(sql, data);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sql;
		
	}
}
