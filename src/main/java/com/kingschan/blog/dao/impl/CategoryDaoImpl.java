package com.kingschan.blog.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.kingschan.blog.dao.CategoryDao;
import com.kingschan.blog.dao.HibernateBaseDao;
import com.kingschan.blog.po.Category;
/**
 * 
*  <pre>    
* 类名称：CategoryDaoImpl 
* 类描述：   博客类型数据访问模型
* 创建人：陈国祥   (kingschan)
* 创建时间：2016-2-20 上午10:15:00   
* 修改人：Administrator   
* 修改时间：2016-2-20 上午10:15:00   
* 修改备注：   
* @version V1.0
* </pre>
 */
@Repository("CategoryDaoImpl")
@SuppressWarnings("unchecked")
public class CategoryDaoImpl extends HibernateBaseDao implements CategoryDao {

    @Override
    public void addObj(Category obj) throws Exception {
        save(obj);

    }

    @Override
    public void deleteObj(Category obj) throws Exception {
        delete(obj);
    }

    @Override
    public void updateObj(Category obj) throws Exception {
        update(obj);
    }

    @Override
    public Category getObj(Object id) throws Exception {
        return (Category) get(Category.class, id);
    }

    @Override
    public List<Category> getCategoryList(String websiteid) throws Exception {
        String hql="from Category c where c.categoryWebsiteid=? order by c.categoryPosition ";
        return (List<Category>) queryForListByHql(hql,true,false, websiteid);
    }

	@Override
	public boolean uniqueCategoryName(String websiteid, String name,String id)throws Exception {
		String hql="select count(*) as total from  Category c where c.categoryWebsiteid=? and c.categoryName =?";
		Object[] args = {websiteid,name,id};
		if (null!=id) {
			hql+=" and c.id <> ? ";
		}else{
			args = new Object[]{websiteid,name};
		}
		Object val= uniqueQueryByHql(hql, true, args);
		return Integer.valueOf(val.toString())==0;
	}

	@Override
	public void saveCategory(Category po) throws Exception {
		String id=(null==po.getId()||po.getId().isEmpty())?null:po.getId();
		if (!uniqueCategoryName(po.getCategoryWebsiteid(), po.getCategoryName(),id)) {
			throw new Exception(String.format("名称%s已被占用", po.getCategoryName()));
		}
		if (null==po.getId()||po.getId().isEmpty()) {
			save(po);
		}else{
			update(po);
		}
		
	}

	@Override
	public int delCategory(String[] ids,String websiteId) throws Exception {
		String total_hql="select count(*) from Article a where a.category.id in(:ids)";
		String del_hql="delete from Category c where c.id in(:ids) and c.categoryWebsiteid=:webid";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ids", ids);
		Object val=uniqueQueryByHQL(total_hql, false, map);
		if (Integer.valueOf(val.toString())>0) {
			throw new Exception("要删除的类型中还有文章，操作失败!");
		}
		map.put("webid", websiteId);
		return executeHQL(del_hql, map);
	}

	@Override
	public Category getCategoryByKeyword(String website, String keyword) throws Exception {
		if(keyword.matches("\\w{32}")){
			return (Category)get(Category.class,keyword);
		}
		String hql=" from Category where categoryWebsiteid =? and  categoryName=?";
		List<Category> list= (List<Category>)queryForListByHql(hql,true,false,website,keyword);
		if (null!=list&&list.size()>0)return list.get(0);
		return null;
	}

	@Override
	public  List<Map<String,Object>> countCategory(String websiteid) throws Exception {
		String hql ="select a.category.id as id,a.category.categoryName as name,a.category.categoryRemark as remark,count(1) as total from Article a where a.websiteid=? and a.articleStatus=1 group by a.category order by a.category.categoryPosition ";
		List<Map<String,Object>> lis= (List<Map<String, Object>>) queryForListByHql(hql, false,true,websiteid);
		return lis;
	}

}
