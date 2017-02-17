package com.kingschan.blog.web.interceptor.assist;

import java.util.Map;
import java.util.TreeMap;

public class BlogUrlMapping {
	private static Map<String, String> mapping;
	static{
		mapping =new TreeMap<String, String>();
		mapping.put("(\\/\\w+)?\\/entry/.*", "/entry/%s");//博文
		mapping.put("(\\/\\w+)?\\/tags/.*", "/tag/%s");//标签
		mapping.put("(\\/\\w+)?\\/category/.*", "/category/%s");//文章类型
		mapping.put("(\\/\\w+)?\\/date/\\d{6,8}", "/date/%s");//日期
	}
}
