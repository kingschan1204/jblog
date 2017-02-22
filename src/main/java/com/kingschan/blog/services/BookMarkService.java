package com.kingschan.blog.services;

import java.util.List;
import java.util.Map;

import com.kingschan.blog.model.vo.BookMarkFolderVo;
import com.kingschan.blog.model.vo.BookMarkVo;
import com.kingschan.blog.dao.Pagination;
import com.kingschan.blog.po.Bookmarks;
import com.kingschan.blog.po.BookmarksFolder;
import com.kingschan.blog.po.User;

public interface BookMarkService {

    /**
     * 根据书签ID得到书签对象
     * @param id
     * @return
     * @throws Exception
     */
    Bookmarks getBookMark(int id)throws Exception;
    /**
     * 编辑书签分类
     * @param vo
     * @throws Exception
     */
    void editBookMarkFolder(BookMarkFolderVo vo, User u, String website)throws Exception;
    /**
     * 编辑书签
     * @param vo
     * @throws Exception
     */
    Bookmarks editBookMark(BookMarkVo vo, User u)throws Exception;
    /**
     * 删除书签
     * @param ids
     * @throws Exception
     */
    int delBookmars(Integer[] ids)throws Exception;
    /**
     * 得到所有书签分类包
     * @param websiteid
     * @return
     */
    List<BookmarksFolder> getAllBookMarksFolder(String websiteid)throws Exception;
    
    
    /**
     * 书签包保存
     * @param id
     * @param name
     * @throws Exception
     */
    void saveBookFolder(Integer id, String name, String website, String userid)throws Exception;
    
    /**
     * 删除书签包
     * @param id
     * @throws Exception
     */
    int delBookFolder(Integer id, String websiteId)throws Exception;
    
    /**
     * 分页书签
     * @param websiteid
     * @param map
     * @param limit
     * @param page
     * @return
     * @throws Exception
     */
    Pagination getBookMarks(String websiteid, Map<String, Object> map, int limit, int page)throws Exception;
}
