package com.kingschan.blog.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class BlogUrlHelper {

	/**
	 * 得到url最后一个斜杠后面的字符串
	 * @param url
	 * @return
	 */
	public static String getLastSlashData(String url){
			String[] s=url.split("/");
            String keyword = null;
			try {
				keyword = URLDecoder.decode(s[s.length-1], "utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
            return keyword;
        
	}
}
