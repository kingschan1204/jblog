package com.kingschan.blog.services.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingschan.blog.model.vo.WebSiteCountInfoVo;
import com.kingschan.blog.dao.Pagination;
import com.kingschan.blog.dao.ReportDao;
import com.kingschan.blog.services.ReportService;
/**
 * 
*    
* 类名称：ReportServiceImpl   
* 类描述：   
* 创建人：kings.chan
* 创建时间：2016-9-22 下午2:06:27   
* 修改人：   
* 修改时间：
* 项目：ROOT
* 修改备注：   
* @version    
*
 */
@Service
public class ReportServiceImpl implements ReportService {

	@Autowired
	private ReportDao reportDao;
	@Override
	public List<Map<String, Object>> dayHttpReq(Integer limit, String website)
			throws Exception {
		return reportDao.dayHttpReq(limit, website);
	}
	@Override
	public List<Map<String, Object>> sumBlogAccess() throws Exception {
		return reportDao.sumBlogAccess();
	}
	@Override
	public List<Map<String, Object>> blogAgent() throws Exception {
		return reportDao.blogAgent();
	}
	@Override
	public List<Map<String, Object>> dayOfArticleQuantity(Integer limit,
			String website) throws Exception {
		return reportDao.dayOfArticleQuantity(limit, website);
	}
	@Override
	public Pagination websiteAccessLog(Integer limit, Integer page,
			String website, Map<String, Object> map) throws Exception {
		return reportDao.websiteAccessLog(limit, page, website, map);
	}
	@Override
	public List<Map<String, Object>> articlePercent() throws Exception {
		return reportDao.articlePercent();
	}
	@Override
	public WebSiteCountInfoVo websiteCountInfo(String siteid)
			throws Exception {
		Map<String, Object>  map=reportDao.websiteCountInfo(siteid);
		if (null!=map) {
			WebSiteCountInfoVo web=new WebSiteCountInfoVo();
			org.apache.commons.beanutils.BeanUtils.populate(web, map);
			return web;
		}
		return null;
	}

}
