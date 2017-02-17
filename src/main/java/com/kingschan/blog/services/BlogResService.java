package com.kingschan.blog.services;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.kingschan.blog.dao.Pagination;


/**
 * 资源处理
 * @author kings.chan
 *
 */
public interface BlogResService {
	/**
	 * 上传图片到七牛
	 * @param filepath
	 * @param request
	 * @return
	 * @throws Exception
	 */
	String uploadFile(String filepath, String filename, String filetype, HttpServletRequest request)throws Exception;
	
	/**
	 * 得到资源列表
	 * @param actionCode 图片/附件
	 * @param pageindex
	 * @param request
	 * @return
	 * @throws Exception
	 */
	String getResList(int actionCode, int pageindex, HttpServletRequest request)throws Exception;
	
	/**
	 * 资源列表管理
	 * @param pageindex
	 * @param limit
	 * @param map
	 * @return
	 * @throws Exception
	 */
	Pagination getResList(int pageindex, int limit, Map<String, Object> map)throws Exception;
	
	/**
	 * 删除博客资源
	 * @param keys
	 * @param website
	 * @throws Exception
	 */
	int delBlogRes(String[] keys, String website)throws Exception;
	/**
	 * 资源重命名
	 * @param id
	 * @param name
	 * @param websiteid
	 * @throws Exception
	 */
	void rename(String id, String name, String websiteid)throws Exception;
}
