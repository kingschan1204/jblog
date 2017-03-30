package com.kingschan.blog.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kingschan.blog.po.BlogStatisticalSite;
import org.springframework.stereotype.Repository;
import com.kingschan.blog.dao.HibernateBaseDao;
import com.kingschan.blog.dao.Pagination;
import com.kingschan.blog.dao.ReportDao;
import com.kingschan.blog.po.BlogMsgBoard;
import com.kingschan.blog.util.SqlUtil;
@Repository("ReportDaoImpl")
@SuppressWarnings("unchecked")
public class ReportDaoImpl extends HibernateBaseDao implements ReportDao {

	
	@Override
	public List<Map<String, Object>> dayHttpReq(Integer limit, String website)
			throws Exception {
		String sql=SqlUtil.getSql("report","dayReport");
		StringBuffer sb=new StringBuffer(sql);
		if (null!=limit&&limit>0) {
			sb.append(String.format(" limit %d ", limit));
		}
		 return (List<Map<String, Object>>) queryForListMapBySql(sb.toString(), false,website,website);
	}

	@Override
	public List<Map<String, Object>> sumBlogAccess() throws Exception {
		String sql=SqlUtil.getSql("report","sumBlogAccess");
		 return (List<Map<String, Object>>) queryForListMapBySql(sql.toString(), false);
	}

	@Override
	public List<Map<String, Object>> blogAgent() throws Exception {
		String sql=SqlUtil.getSql("report","blogAgent");
		 return (List<Map<String, Object>>) queryForListMapBySql(sql.toString(), false);
	}

	@Override
	public List<Map<String, Object>> dayOfArticleQuantity(Integer limit,
			String website) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		if (null!=website) {
			map.put("websiteid", website);
		}
		StringBuffer sb=new StringBuffer(SqlUtil.getSql("report","dayOfArticleQuantity", map));
		if (null!=limit&&limit>0) {
			sb.append(String.format(" limit %d ", limit));
		}
		if (null==website) {
			return (List<Map<String, Object>>) queryForListMapBySql(sb.toString(), false);
		}
		return (List<Map<String, Object>>) queryForListMapBySql(sb.toString(), false,website);
	}

	@Override
	public Pagination websiteAccessLog(Integer limit,
			Integer page, String website, Map<String, Object> map)
			throws Exception {
		String s=SqlUtil.getSql("report","accessLog");
		StringBuffer sql=new StringBuffer(s).append(" where 1=1 ");
		if (null!=website&&!website.isEmpty()) {
			sql.append(" and a.req_blog=:website");
			map.put("website", website);
		}
		if (map.containsKey("ip")) {
			sql.append(" and a.req_ip=:ip ");
		}
		if (map.containsKey("url")) {
			sql.append(" and a.req_url=:url ");
		}
		if (map.containsKey("agentname")) {
			sql.append(" and b.agent_browser_name=:agentname ");
		}
		if (map.containsKey("os")) {
			sql.append(" and b.agent_os=:os ");
		}
		if (map.containsKey("device")) {
			sql.append(" and b.agent_device=:device ");
		}
		if (map.containsKey("datetime1")&&!map.get("datetime1").toString().isEmpty()) {
			if (map.containsKey("datetime2")) {
				sql.append(" and a.req_datetime>=:datetime1 and a.req_datetime<=:datetime2 ");
			}else{
				sql.append(" and a.req_datetime>=:datetime1 ");
			}
			
		}
		if (map.containsKey("order")) {
			sql.append(" order by ").append(map.get("order"));
			map.remove("order");
		}else{
			sql.append(" order by a.req_datetime desc");
		}
		return PaginationsBySQL(sql.toString(), page, limit, false, map);
	}

	@Override
	public List<Map<String, Object>> articlePercent() throws Exception {
		String sql=SqlUtil.getSql("report","articlePercent");
		return (List<Map<String, Object>>) queryForListMapBySql(sql, false);
	}

	@Override
	public BlogStatisticalSite websiteCountInfo(String siteId)
			throws Exception {
		BlogStatisticalSite bss = (BlogStatisticalSite)get(BlogStatisticalSite.class,siteId);
		return bss;
	}

	@Override
	public Pagination blogTimeLine(Integer limit,Integer page,String userid)
			throws Exception {
		String sql=SqlUtil.getSql("report","blog_timeline");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("userid", userid);
		return PaginationsBySQL(sql, page, limit, false, map);
	}

	@Override
	public Pagination blogMsgBoard(Integer limit,
			Integer page, String website) throws Exception {
		String hql =" from BlogMsgBoard a where a.websiteid=? and a.msgRoot='' and msgFlag='√' order by a.msgDatetime desc"; 
		return PaginationsByHQLArrayParams(hql, page, limit, false, website);
	}

	@Override
	public List<BlogMsgBoard> getMsgBoardByrootId(String website,String rootid,Integer page)throws Exception{
		String hql="from BlogMsgBoard a where  a.msgRoot=? order by a.msgDatetime desc ";
		return (List<BlogMsgBoard>) PaginationByHql(hql, page, 10, false,rootid);
	}

	@Override
	public void refreshBlogStatistical() throws Exception {
		String clear_sql="truncate table blog_statistical_site";
		executeSQL(clear_sql);
		String sql =SqlUtil.getSql("report","refresh_Blog_Statistical");;
		executeSQL(sql);
	}


}

