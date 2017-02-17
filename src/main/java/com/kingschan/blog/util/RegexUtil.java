package com.kingschan.blog.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
*  <pre>    
* 类名称：RegexUtil 
* 类描述：   正则表达式工具类
* 创建人：陈国祥   (kingschan)
* 创建时间：2013-6-25
* 修改人：Administrator   
* 修改时间：2013-6-25
* 修改备注：   
* @version V1.0
* </pre>
 */
public class RegexUtil {
	//匹配数字
	public static final String regex_number="^[-+]?([0]{1}(\\.[0-9]+)?|[1-9]{1}\\d*(\\.[0-9]+)?)";//"^[-+]?[0-9]+(\\.[0-9]+)?$";
	//匹配日期(年-月-日)
	public static final String regex_date="^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";
	//匹配日期（年-月-日 时：分：秒）
	public static final String regex_dateTime="^(((20[0-3][0-9]-(0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|(20[0-3][0-9]-(0[2469]|11)-(0[1-9]|[12][0-9]|30))) (20|21|22|23|[0-1][0-9]):[0-5][0-9]:[0-5][0-9])$";
	//邮箱地址
	public static final String regex_email="\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";
	//URL
	public static final String regex_url="[http|https]+://([\\w-]+\\.)+[\\w-]+(/[\\w- ./?%&=]*)?";
	//ip地址
	public static final String regex_ipaddress="^(d{1,2}|1dd|2[0-4]d|25[0-5]).(d{1,2}|1dd|2[0-4]d|25[0-5]).(d{1,2}|1dd|2[0-4]d|25[0-5]).(d{1,2}|1dd|2[0-4]d|25[0-5])$";
	//包含中文
	public static final String regex_includeCN="[\u4e00-\u9fa5]+";
	//EL表达式
	public static final String regex_expression="[\\$|\\#]\\{[\\w]+\\}";
	
	/**
	 * 是否匹配传入的正则表达式
	 * @param regex
	 * @param str
	 * @return
	 */
	public static boolean matching(String regex,String str){
		return str.matches(regex);
	}
	
	
	/**
	 * 提取字符串或一段文本中的手机号码 
	 * @param text
	 * @return
	 */
	public static String getPhoneNumbers(String text){
		Pattern pattern = Pattern.compile("(?<!\\d)(?:(?:1[358]\\d{9})|(?:861[358]\\d{9}))(?!\\d)"); 
		Matcher matcher = pattern.matcher(text); 
		    StringBuffer bf = new StringBuffer(64); 
		    while (matcher.find()) { 
		      bf.append(matcher.group()).append(","); 
		    } 
		    int len = bf.length(); 
		    if (len > 0) { 
		      bf.deleteCharAt(len - 1); 
		    } 
		return bf.toString();
	}
	/**
	 * 提取文本中匹配正则的字符串
	 * @param text
	 * @param regx 正则
	 * @return 多个结果已,分隔
	 */
	public static String findStrByRegx(String text,String regx){
		StringBuffer bf = new StringBuffer(64); 
		try {
			Pattern pattern = Pattern.compile(regx); 
			Matcher matcher = pattern.matcher(text); 
			    while (matcher.find()) { 
			      bf.append(matcher.group()).append(","); 
			    } 
			    int len = bf.length(); 
			    if (len > 0) { 
			      bf.deleteCharAt(len - 1); 
			    } 
		    return bf.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		
	}
	/**
	 * 去掉所有HTML标记
	 * @param htmlStr HTML字符串
	 * @return
	 */
	public static String replaceAllHtmlTag(String htmlStr){ 
        String regEx_script="<script[^>]*?>[\\s\\S]*?<\\/script>"; //定义script的正则表达式 
        String regEx_style="<style[^>]*?>[\\s\\S]*?<\\/style>"; //定义style的正则表达式 
        String regEx_html="<[^>]+>"; //定义HTML标签的正则表达式 
         
        Pattern p_script=Pattern.compile(regEx_script,Pattern.CASE_INSENSITIVE); 
        Matcher m_script=p_script.matcher(htmlStr); 
        htmlStr=m_script.replaceAll(""); //过滤script标签 
         
        Pattern p_style=Pattern.compile(regEx_style,Pattern.CASE_INSENSITIVE); 
        Matcher m_style=p_style.matcher(htmlStr); 
        htmlStr=m_style.replaceAll(""); //过滤style标签 
         
        Pattern p_html=Pattern.compile(regEx_html,Pattern.CASE_INSENSITIVE); 
        Matcher m_html=p_html.matcher(htmlStr); 
        htmlStr=m_html.replaceAll(""); //过滤html标签 
        htmlStr = htmlStr.replaceAll("&nbsp;", "");  
        htmlStr = htmlStr.replaceAll("<.*>|</.*>", "");  
        return htmlStr.trim(); //返回文本字符串 
    } 
	/**
	 * 过滤特殊字符
	 * @param content
	 * @return
	 */
	public static String filterSpecialChar(String content){
		StringBuffer sb = new StringBuffer();
		String[] array=content.split("");
		for (String str : array) {
			if (str.matches("\\w|[\u4e00-\u9fa5]|\\*|\\.")) {
				sb.append(str);
			}
		}
		return sb.toString();
	}
	
}
