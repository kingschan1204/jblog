package com.kingschan.blog.services.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingschan.blog.model.vo.BookMarkFolderVo;
import com.kingschan.blog.model.vo.BookMarkVo;
import com.kingschan.blog.dao.Pagination;
import com.kingschan.blog.dao.impl.BookMarksDaoImpl;
import com.kingschan.blog.po.Bookmarks;
import com.kingschan.blog.po.BookmarksFolder;
import com.kingschan.blog.po.User;
import com.kingschan.blog.services.BookMarkService;
import com.kingschan.blog.util.TimeStampUtil;
@Service
public class BookMarkServiceImpl implements BookMarkService {

    @Autowired
    private BookMarksDaoImpl bookMark_dao;
    @Override
    public void editBookMarkFolder(BookMarkFolderVo vo,User u,String website) throws Exception {
        BookmarksFolder bf = null;
        if (null==vo.getId()) {
            bf = new BookmarksFolder();
            bf.setBookmarksCreator(u.getId());
            bf.setBookmarksDatetime(TimeStampUtil.getCurrentDate());
            bf.setWebsiteid(website);
        }else{
            bf =(BookmarksFolder) bookMark_dao.get(BookmarksFolder.class, vo.getId());
        }
        if(null==vo.getBookmarksRemark()){vo.setBookmarksRemark("");}
        BeanUtils.copyProperties(vo, bf);
        bookMark_dao.saveBookmarksFolder(bf);
    }

    @Override
    public Bookmarks editBookMark(BookMarkVo vo,User u) throws Exception {
        Bookmarks bmk =null;
        if (null==vo.getId()) {
            bmk= new Bookmarks();
            bmk.setBookmarksCreator(u.getId());
            bmk.setBookmarksDatetime(TimeStampUtil.getCurrentDate());
            BookmarksFolder bf =(BookmarksFolder) bookMark_dao.get(BookmarksFolder.class, vo.getBfid());
            bmk.setBookmarksFolder(bf);
        }else{
            bmk=(Bookmarks) bookMark_dao.get(Bookmarks.class, vo.getId());
            BookmarksFolder bf =(BookmarksFolder) bookMark_dao.get(BookmarksFolder.class, vo.getBfid());
            bmk.setBookmarksFolder(bf);
        }
        BeanUtils.copyProperties(vo, bmk,"bfid");
       return bookMark_dao.saveBookMark(bmk);
    }

    @Override
    public int delBookmars(Integer[] ids) throws Exception {
      return  bookMark_dao.delBookmars(ids);

    }

    @Override
    public List<BookmarksFolder> getAllBookMarksFolder(String websiteid) throws Exception {
        return bookMark_dao.getAllBookMarksFolder(websiteid);
    }

    @Override
    public Pagination getBookMarks(String websiteid, Map<String, Object> map, int limit, int page)
            throws Exception {
        return bookMark_dao.getBookMarks(websiteid, map, limit, page);
    }

    @Override
    public Bookmarks getBookMark(int id) throws Exception {
        return (Bookmarks) bookMark_dao.get(Bookmarks.class, id);
    }

	@Override
	public void saveBookFolder(Integer id, String name,String website,String userid) throws Exception {
		BookmarksFolder bf = null;
		if (null!=id) {
			bf =(BookmarksFolder) bookMark_dao.get(BookmarksFolder.class, id);
		}else{
			bf = new BookmarksFolder();
			bf.setBookmarksCreator(userid);
			bf.setBookmarksDatetime(TimeStampUtil.getCurrentDate());
			bf.setWebsiteid(website);
		}
		bf.setBookmarksName(name);
		bf.setBookmarksRemark("");
		bookMark_dao.saveBookFolder(bf);
	}

	@Override
	public int delBookFolder(Integer id,String websiteId) throws Exception {
		return bookMark_dao.delBookFolder(id, websiteId);
	}

}
