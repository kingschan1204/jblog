package com.kingschan.blog.util;

import java.io.File;

public class PathUtil {
	/**
	 * 得到web-inf
	 * @return
	 */
	public static String getWebInfPath(){
		String path=PathUtil.class.getClassLoader().getResource("").getPath();
		File f = new File(path);
		return f.getParentFile().getPath();
	}
	
	

}
