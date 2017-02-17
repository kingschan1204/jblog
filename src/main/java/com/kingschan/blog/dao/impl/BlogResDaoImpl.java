package com.kingschan.blog.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.kingschan.blog.dao.BlogResDao;
import com.kingschan.blog.dao.HibernateBaseDao;
import com.kingschan.blog.po.BlogRes;
@SuppressWarnings("unchecked")
@Repository("BlogResDaoImpl")
public class BlogResDaoImpl extends HibernateBaseDao implements BlogResDao {

	@Override
	public void saveRes(BlogRes po) throws Exception {
		save(po);
	}



	@Override
	public com.kingschan.blog.dao.Pagination getResList(String type,
			int pageindex, int limit, Map<String, Object> map) throws Exception {
		String hql ="from BlogRes res where res.resWebsiteid=:websiteid ";
		if (type.equals("image")) {
			hql+="  and res.resType like 'image%'";
		}else if (type.equals("file")) {
			hql+="  and res.resType not like 'image%'";
		}
		return PaginationsByHQLMapParams(hql, pageindex, limit, true, map);
	}



	@Override
	public com.kingschan.blog.dao.Pagination getResList(int pageindex,
			int limit, Map<String, Object> map) throws Exception {
		StringBuffer hql =new StringBuffer(" from BlogRes res where res.resWebsiteid=:websiteid ");
		if (map.containsKey("resname")) {
			hql.append(" and ( res.resName like :resname or res.resKey=:resKey )");
		}
		hql.append(" order by res.resDate desc");
		return PaginationsByHQLMapParams(hql.toString(), pageindex, limit, true, map);
	}



	@Override
	public int delBlogRes(String[] keys, String websiteid) throws Exception {
		String hql ="delete from BlogRes res where res.resKey in(:ids) and res.resWebsiteid=:website";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ids", keys);
		map.put("website", websiteid);
		return executeHQL(hql, map);
	}



	@Override
	public int auth(String[] keys, String websiteid) throws Exception {
		String hql ="select count(*) from BlogRes res where res.resKey in(:ids) and res.resWebsiteid=:website";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ids", keys);
		map.put("website", websiteid);
		Object value=uniqueQueryByHQL(hql, false, map);
		return Integer.valueOf(value.toString());
	}



	@Override
	public void rename(String id, String name, String websiteid)
			throws Exception {
		String hql=" from BlogRes where id=? and resWebsiteid=?";
		List<BlogRes>	lis=(List<BlogRes>) queryForListByHql(hql, false, false, id,websiteid);
		 if (null!=lis&&lis.size()>0) {
			 BlogRes br =lis.get(0);
			 br.setResName(name);
			 save(br);
		 }
		
	}



	

}
