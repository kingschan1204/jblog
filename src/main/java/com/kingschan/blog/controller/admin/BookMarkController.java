package com.kingschan.blog.controller.admin;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kingschan.blog.model.vo.BookMarkVo;
import com.kingschan.blog.po.Bookmarks;
import com.kingschan.blog.services.BookMarkService;
import com.kingschan.blog.util.BlogUtil;

@Controller
@RequestMapping("/admin")
public class BookMarkController {
    @Autowired
    private BookMarkService book_mark_serv;
    /**
     * 书签列表
     * @return
     */
    @RequestMapping("/bookmark_list.do")
    public ModelAndView getBookMarkList(Integer page,String title,Integer folder){
        ModelAndView mav = new ModelAndView("/admin/bookmark_list");
        mav.addObject("page", page);
        mav.addObject("title", title);
        mav.addObject("folder", folder);
        return mav;
    }
    /**
     * 书签目录管理
     * @param page
     * @param title
     * @param folder
     * @return
     */
    @RequestMapping("/bookmark_folder_list.do")
    public ModelAndView getBookMarkFolderList(Integer page,String title,Integer folder){
        ModelAndView mav = new ModelAndView("/admin/bookmark_folder_list");
        mav.addObject("page", page);
        mav.addObject("title", title);
        mav.addObject("folder", folder);
        return mav;
    }
    @RequestMapping("/bookmark_edit.do")
    public ModelAndView bookmarkEdit(Integer id){
        ModelAndView mav = new ModelAndView("/admin/bookmark_edit");
        Bookmarks book=new Bookmarks();
        if (null!=id) {
            try {
                book=book_mark_serv.getBookMark(id);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        mav.addObject("book",  book);
        return mav;
    }
    
    @RequestMapping("/save_bookmark.do")
    public String saveBookMark(@ModelAttribute("vo") BookMarkVo vo,HttpServletRequest request){
        BlogUtil util = new BlogUtil(request);
        Bookmarks book = null;
        try {
           book= book_mark_serv.editBookMark(vo, util.getCurrentUser().toUser());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:bookmark_edit.do?id="+book.getId();
    }
    
    
    @ResponseBody
    @RequestMapping("/del_bookmark.do")
    public String delBookMark(@RequestParam("ids[]") Integer[] ids){
        int affected=0;
        String msg="";
        try {
            affected=book_mark_serv.delBookmars(ids);
            msg=String.format("成功删除%s条记录!", affected);
        } catch (Exception e) {
            e.printStackTrace();
            msg=e.getMessage();
        }
        return msg;
    }
    
    
    /**
     * 书签包删除
     * @param id
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/del_bookmark_folder.do")
    public String delBookMark(Integer id,HttpServletRequest request){
        int affected=0;
        String msg="";
        try {
        	if (null==id) {
				throw new Exception("id不能为空！");
			}
        	BlogUtil util = new BlogUtil(request);
            affected=book_mark_serv.delBookFolder(id, util.getCurrentAdminWebSite().getId());
            msg=String.format("成功删除%s条记录!", affected);
        } catch (Exception e) {
            e.printStackTrace();
            msg=e.getMessage();
        }
        return msg;
    }
    
    /**
     * 书签保存
     * @param bookmarkid
     * @param bookmarksName
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping("/save_bookmark_folder.do")
    public String saveBookMarkFolder(Integer bookmarkid,String bookmarksName,HttpServletRequest request){
        String msg="";
        try {
        	BlogUtil util = new BlogUtil(request);
            book_mark_serv.saveBookFolder(bookmarkid,bookmarksName, util.getCurrentAdminWebSite().getId(),util.getCurrentUser().getId());
            msg="success";
        } catch (Exception e) {
            e.printStackTrace();
            msg=e.getMessage();
        }
        return msg;
    }
}
