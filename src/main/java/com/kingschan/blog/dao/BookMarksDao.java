package com.kingschan.blog.dao;

import java.util.List;
import java.util.Map;

import com.kingschan.blog.po.Bookmarks;
import com.kingschan.blog.po.BookmarksFolder;

public interface BookMarksDao {
    /**
     * 保存书签包
     * @param bf
     * @throws Exception
     */
    void saveBookmarksFolder(BookmarksFolder bf)throws Exception;
    /**
     * 保存书签
     * @param bms
     * @throws Exception
     */
    Bookmarks saveBookMark(Bookmarks bms)throws Exception;
    
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
    void saveBookFolder(BookmarksFolder bf)throws Exception;
    
    /**
     * 删除书签
     * @param id
     * @throws Exception
     */
    int delBookFolder(Integer id, String websiteid)throws Exception;
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
