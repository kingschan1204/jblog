package com.kingschan.blog.services;

import java.util.List;
import java.util.Map;

import com.kingschan.blog.model.vo.BlogSiteStatisticalVo;
import com.kingschan.blog.dao.Pagination;

/**
 * 
*    
* 类名称：ReportService   
* 类描述：报表
* 创建人：kings.chan
* 创建时间：2016-9-22 上午11:50:10   
* 修改人：   
* 修改时间：
* 项目：ROOT
* 修改备注：   
* @version    
*
 */
public interface ReportService {

	/**
	 * 网站每天请求数
	 * @param limit
	 * @param website
	 * @return
	 * @throws Exception
	 */
	List<Map<String, Object>> dayHttpReq(Integer limit, String website)throws Exception;
	
	
	/**
	 * 博客访问情况
	 * @return
	 * @throws Exception
	 */
	List<Map<String, Object>> sumBlogAccess()throws Exception;
	
	/**
	 * 博客代理类型
	 * @return
	 * @throws Exception
	 */
	List<Map<String, Object>> blogAgent()throws Exception;	
	/**
	 * 日志发表日统计
	 * @param limit
	 * @param website
	 * @return
	 * @throws Exception
	 */
	List<Map<String, Object>> dayOfArticleQuantity(Integer limit, String website)throws Exception;
	
	
	/**
	 * 网站访问情况
	 * @param limit 显示条数
	 * @param page 页码
	 * @param website 网站
	 * @param map 查询参数 
	 * @return
	 * @throws Exception
	 */
	 Pagination websiteAccessLog(Integer limit, Integer page, String website, Map<String, Object> map)throws Exception ;
	 
	 /**
	  * 网站日志比重
	  * @return
	  */
	 List<Map<String, Object>> articlePercent() throws Exception;


    /**
	 * 博客网站统计
	 * @param siteid
	 * @return
	 * @throws Exception
     */
	BlogSiteStatisticalVo websiteCountInfo(String siteid)throws Exception;
}
