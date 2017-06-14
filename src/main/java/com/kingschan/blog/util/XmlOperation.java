package com.kingschan.blog.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

/**
 * XML操作工具类
 * 
 * @author kingschan date:2013-8-13
 */
public class XmlOperation {
	private Document doc;
	/**
	 * 	解析一段xml字符串
	 * @param text
	 * @throws DocumentException
	 */
	public XmlOperation(String text) throws DocumentException {
		this.doc = getDoc(text);
	}
	/**
	 * 解析一个xml文件
	 * @param fis
	 * @throws DocumentException
	 */
	public XmlOperation(FileInputStream fis ) throws DocumentException {
		SAXReader reader = new SAXReader();
		this.doc =  reader.read(fis);
	}
	/**
	 * 建立一个文档实例 
	 * @throws DocumentException
	 */
	public XmlOperation() throws DocumentException {
		// 使用DocumentHelper.createDocument方法建立一个文档实例 
		this.doc = DocumentHelper.createDocument(); 
	}
	 
	/**
	 * 传入XML字符串返回doc
	 * 
	 * @param text
	 * @return
	 * @throws DocumentException
	 */
	public Document getDoc(String text) throws DocumentException {
		Document d = DocumentHelper.parseText(text);
		return d;
	}

	/**
	 * 传入xml文件构建doc
	 * 
	 * @param xmlFile
	 * @return
	 * @throws DocumentException
	 */
	public Document getDoc(File xmlFile) throws DocumentException {
		SAXReader reader = new SAXReader();
		Document d = reader.read(xmlFile);
		return d;
	}

	/**
	 * 返回根节点
	 * 
	 * @return
	 */
	public Element getRoot() {
		return doc.getRootElement();
	}

	/**
	 * 得到一个节点的所有属性
	 * @param element
	 * @return
	 */
	public Map<String, String> getNodeAttributes(Element element) {
		Map<String, String> map = new HashMap<String, String>();
		//Element root = doc.getRootElement();
		List<?> attrList = element.attributes();
		for (int i = 0; i < attrList.size(); i++) {
			// 属性的取得
			Attribute item = (Attribute) attrList.get(i);
			map.put(item.getName(), item.getValue());
		}
		return map;
	}

	/**
	 * <pre>
	 * <b>
	 * 传入查询表达式 返回搜索到的节点 
	 * 表达式 描述 nodename 选取此节点的所有子节点 
	 * / 从根节点选取 
	 * // 从匹配选择的当前节点选择文档中的节点，而不考虑它们的位置 
	 * . 选取当前节点 
	 * .. 选取当前节点的父节点
	 *  @ 选取属性
	 *  </b>
	 * </pre>
	 * @param expression
	 *            查询表达式
	 * @return
	 */
	public List<?> getNodesByExpression(String expression) {
		List<?> lis = doc.selectNodes(expression);
		return lis;
	}
	/**
	 * 根据表达式返回一组节点
	 * @param expression	表达式
	 * @param isAllowNull	是否允许为空
	 * @return
	 */
	public Map<String, String> getElementByExpression(String expression,boolean isAllowNull){
		Map<String, String> map = new HashMap<String, String>();
		@SuppressWarnings("unchecked")
		List<Element> lis =(List<Element>) getNodesByExpression(expression);
		String value=null;
		for (Element element : lis) {
			value=element.getText();
			if (!isAllowNull) {
				if("".equals(value))continue;
			}
			map.put(element.getName(), value);
		}
		return map;
		
	}
	
	/**
	 * 得到一个节点下的所有子节点
	 * @param e
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Element> getAllChild(Element e){
		List<Element> child = null;
		child=e.elements();
		return child;
	}
	/**
	 * 是否过滤值为空的节点
	 * @param e	节点
	 * @param isAllowNull  true:过滤掉值为空   false:允许为空不过滤
	 * @return
	 */
	public Map<String, String> getAllChild(Element e,Boolean isAllowNull){
		Map<String, String> map = new HashMap<String, String>();
		List<Element> lis = this.getAllChild(e);
		String value=null;
		for (Element element : lis) {
			value=element.getText();
			if (!isAllowNull) {
				if("".equals(value))continue;
			}
			map.put(element.getName(), value);
		}
		return map;
	}
	/**
	 * <pre>
	 * <b>
	 * 根据表达式查找单一的节点
	 * 表达式 描述 nodename 选取此节点的所有子节点 
	 * / 从根节点选取 
	 * // 从匹配选择的当前节点选择文档中的节点，而不考虑它们的位置 
	 * . 选取当前节点 
	 * .. 选取当前节点的父节点
	 *  @ 选取属性
	 *  <b>
	 * </pre>
	 * @param expression
	 *            查询表达式
	 * @return
	 */
	public Node getNodeByExpression(String expression) {
		return doc.selectSingleNode(expression);
	}
	/**
	 * 增加一个节点<font style="color:red;">父节点 NUll则增加根节点</font>
	 * @param parentNode	父节点 NUll则增加根节点
	 * @param elementName	节点名字
	 * @param attributes	节点属性
	 * @param text			节点内容
	 * @param Comment		节点注释
	 * @param cdata			CDATA节点
	 */
	public void addElement(Element parentNode,String elementName,Map<String, String> attributes,String text,String Comment,String cdata){
		Element e;
		if (null==parentNode) {
			 e= doc.addElement(elementName);
		}else{
			e=parentNode.addElement(elementName);
		}		
		if (null!=attributes) {
			Iterator<String> itera=attributes.keySet().iterator();
			while (itera.hasNext()) {
				String key =itera.next();
				e.addAttribute(key, attributes.get(key));				
			}
		}
		if(null!=text)e.setText(text);
		if(null!=Comment)e.addComment(Comment);
		if(null!=cdata)e.addCDATA(cdata);
		
	}
	
	/**
	 * 保存xml文件到指定的路径
	 * @param path	保存的路径和文件名
	 * @throws IOException
	 */
	public void save(String path) throws IOException{
		XMLWriter output;
		// 输出格式化
		OutputFormat format = OutputFormat.createPrettyPrint();
		output = new XMLWriter(new FileWriter(path), format);
		output.write(doc);
		output.close();
	}
	
	@Override
	public String toString() {
		return doc.asXML();
	}
	
	
}
