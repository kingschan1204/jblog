package com.kingschan.blog.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.kingschan.blog.dao.BookMarksDao;
import com.kingschan.blog.dao.HibernateBaseDao;
import com.kingschan.blog.dao.Pagination;
import com.kingschan.blog.po.Bookmarks;
import com.kingschan.blog.po.BookmarksFolder;
@Repository("BookMarksDaoImpl")
public class BookMarksDaoImpl extends HibernateBaseDao implements BookMarksDao {

    @Override
    public void saveBookmarksFolder(BookmarksFolder bf) throws Exception {
        if (null==bf.getId()) {
            save(bf);
        }else{
            update(bf);
        }

    }

    @Override
    public Bookmarks saveBookMark(Bookmarks bms) throws Exception {
        if (null==bms.getId()) {
            save(bms);
        }else{
            update(bms);
        }
        return bms;
    }

    @Override
    public int delBookmars(Integer[] ids) throws Exception {
        String hql="delete from Bookmarks b where b.id in (:ids)";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("ids", ids);
       return executeHQL(hql, map);

    }

    @SuppressWarnings("unchecked")
	@Override
    public List<BookmarksFolder> getAllBookMarksFolder(String websiteid) throws Exception {
        String hql="from BookmarksFolder bf where bf.websiteid=?";
        return (List<BookmarksFolder>) queryForListByHql(hql, true,false, websiteid);
    }

    @Override
    public Pagination getBookMarks(String websiteid, Map<String, Object> map, int limit, int page)
            throws Exception {
        StringBuffer hql = new StringBuffer("from Bookmarks b where b.bookmarksFolder.websiteid=:websiteid");
        if (map.containsKey("title")) {
            hql.append(" and bookmarksName like :title");
        }
        if (map.containsKey("folder")) {
            hql.append(" and bookmarksFolder.id=:folder");
        }
        if (map.containsKey("orderby")) {
            hql.append(" order by "+map.get("orderby"));
            map.remove("orderby");
        }
        map.put("websiteid", websiteid);
        return PaginationsByHQLMapParams(hql.toString(), page, limit, true, map);
    }

	@Override
	public void saveBookFolder(BookmarksFolder bf) throws Exception {
		if (null==bf.getId()) {
			save(bf);
		}else{
			update(bf);
		}
		
	}

	@Override
	public int delBookFolder(Integer id,String websiteid) throws Exception {
		String total_hql="select count(*) from Bookmarks a where a.bookmarksFolder.id =:id";
		String del_hql="delete from BookmarksFolder c where c.id =:id and c.websiteid=:webid";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", id);
		Object val=uniqueQueryByHQL(total_hql, true, map);
		if (Integer.valueOf(val.toString())>0) {
			throw new Exception("要删除的类型中还有书签，操作失败!");
		}
		map.put("webid", websiteid);
		return executeHQL(del_hql, map);
		
	}

}
