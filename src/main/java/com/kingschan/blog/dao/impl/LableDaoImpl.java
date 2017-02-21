package com.kingschan.blog.dao.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.stereotype.Repository;

import com.kingschan.blog.dao.HibernateBaseDao;
import com.kingschan.blog.dao.LableDao;
import com.kingschan.blog.dao.Pagination;
import com.kingschan.blog.po.Lable;
/**
 * 
*  <pre>    
* 类名称：LableDaoImpl 
* 类描述：   
* 创建人：陈国祥   (kingschan)
* 创建时间：2016-2-20 下午2:49:56   
* 修改人：Administrator   
* 修改时间：2016-2-20 下午2:49:56   
* 修改备注：   
* @version V1.0
* </pre>
 */
@Repository("LableDaoImpl")
public class LableDaoImpl extends HibernateBaseDao implements LableDao {

    @Override
    public void addObj(Lable obj) throws Exception {
           save(obj);
    }

    @Override
    public void deleteObj(Lable obj) throws Exception {
        delete(obj);
    }

    @Override
    public void updateObj(Lable obj) throws Exception {
        update(obj);
    }

    @Override
    public Lable getObj(Object id) throws Exception {
        return getObj(id);
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> getHotLableList(String websiteid,int limit,String orderby ) throws Exception {
        String hql ="select new Map ( lableName as lableName,COUNT(*) as total ) from Lable a where a.webSite.id=? group by a.lableName order by ";
        hql+=null==orderby?"total desc":orderby;
        List<Map<String, Object>> list =null;
        if (limit==0) {
            list=(List<Map<String, Object>>) queryForListByHql(hql, true,false, websiteid);
        }else{
            list =(List<Map<String, Object>>) PaginationByHql(hql, 1, limit,true, websiteid);
        }
       return list;
    }

    @Override
    public int delArticleLable(String id) throws Exception {
        String hql ="delete from Lable l where l.id=?";
       return executeHQL(hql, id);
        
    }

    @Override
    public int getLableQuantityByName(String websiteId, String labName) throws Exception {
        String hql =" select count(1) from Lable where webSite.id =? and lableName=?";
        Object val= uniqueQueryByHql(hql,true,websiteId,labName);
        Integer quantity=Integer.valueOf(val.toString());
        return quantity;
    }


    @Override
	public Pagination getLableArticleItemList(String lableName,String websiteid,int limit,int page, String orderby) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT a.lable_name as lable_name, b.article_title as article_title, b.id as id FROM blog_label a inner JOIN blog_article b ON a.lable_articleid = b.id and b.article_private=false  WHERE a.websiteid = ? ");
		if (null!=lableName) {
			sb.append("and lable_name='").append(StringEscapeUtils.escapeSql(lableName)).append("'");
		}
		sb.append(" ORDER BY a.lable_name,b.article_title");
		return PaginationsBySQL(sb.toString(), page, limit, false, websiteid);
	}

}
