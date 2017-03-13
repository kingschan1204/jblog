package com.kingschan.blog.controller.admin;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.validator.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kingschan.blog.model.vo.ArticleVo;
import com.kingschan.blog.po.WebSite;
import com.kingschan.blog.services.ArticleService;
import com.kingschan.blog.services.LableService;
import com.kingschan.blog.util.BlogUtil;

@Controller
@RequestMapping("/admin")
public class ArticleController {

    private Logger log = LoggerFactory.getLogger(ArticleController.class);

    @Autowired
    private ArticleService article_serv;
    @Autowired
    private LableService lable_serv;
    
    /**
     * 文章列表
     * @param page
     * @return
     */
    @RequestMapping("/article_list.do")
    public ModelAndView articleList(Integer page,String title,String category,Integer limit){
        ModelAndView mav = new ModelAndView("/admin/article_list");
        mav.addObject("page", page);
        mav.addObject("title", title);
        mav.addObject("categoryName", category);
        mav.addObject("limit", limit);
        return mav;
    }
    /**
     * 评论管理
     * @param page
     * @param title
     * @param articleId
     * @param keyword
     * @return
     */
    @RequestMapping("/article_comments.do")
    public ModelAndView articleCommentList(Integer page,String title,String articleId,String keyword,Integer limit){
        ModelAndView mav = new ModelAndView("/admin/article_comment_list");
        mav.addObject("page", page);
        mav.addObject("title", title);
        mav.addObject("limit", limit);
        return mav;
    }
    
    @ResponseBody
    @RequestMapping("/delArticleComments.do")
    public String delArticleComments(@RequestParam("ids[]") String[] ids){
        int affected=0;
        String msg="";
        try {
            affected=article_serv.delArticleComments(ids);
            msg=String.format("成功删除%s条记录!", affected);
        } catch (Exception e) {
            e.printStackTrace();
            msg=e.getMessage();
        }
        return msg;
    }
    
    /**
     * 编辑文章
     * @param req
     * @param id
     * @return
     */
    @RequestMapping("/edit_article.do")
    public ModelAndView editArticle(HttpServletRequest req,String id){
    	BlogUtil bu=new BlogUtil(req);
        ModelAndView mav =null;
        if (null!=id&&id.matches("\\w{32}")) {
            try {
                WebSite ws =bu.getCurrentAdminWebSite();
                ArticleVo vo = article_serv.getArticle(id, false, ws.getId());
                mav=new ModelAndView(bu.getArticleEditorTemplate(vo.getArticleEditor()));
                mav.addObject("article",vo);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
        	mav = new ModelAndView(bu.getArticleEditorTemplate(bu.getCurrentAdminWebSite().getWebsiteEditor()));
        	ArticleVo vo = new ArticleVo();
        	vo.setArticleAllowcomments(true);
            mav.addObject("article",vo);
        }
        return mav;
    }
    
    /**
     * 保存文章
     * @param avo
     * @return
     */
    @RequestMapping("/save_article.do")
    public String save(@ModelAttribute("avo") ArticleVo avo,String type,HttpServletRequest req){
        String id=null;
        BlogUtil userutil=new BlogUtil(req);
        try {
           id= article_serv.saveArticle(avo,type,userutil.getCurrentUser().toUser(),userutil.getCurrentAdminWebSite());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return String.format("redirect:edit_article.do?id=%s", id);
    }
    /**
     * 删除文章标签
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("/del_article_lable.do")
    public String delArticleLable(@RequestParam String id){
        String msg="success";
        try {
            lable_serv.delArticleLable(id);
        } catch (Exception e) {
            msg=e.getMessage();
            e.printStackTrace();
        }
        return msg;
    }
    
    @ResponseBody
    @RequestMapping("/del_article.do")
    public String delArticles(@RequestParam("ids[]") String[] ids){
        int affected=0;
        String msg="";
        try {
            affected=article_serv.delArticles(ids);
            msg=String.format("成功删除%s条记录!", affected);
        } catch (Exception e) {
            e.printStackTrace();
            msg=e.getMessage();
        }
        return msg;
    }
    /**
     * 
     * @param ids
     * @param category
     * @return
     */
    @ResponseBody
    @RequestMapping("/update_articletype.do")
    public String updateArticlesType(@RequestParam("ids[]") String[] ids,String category,HttpServletRequest req){
        String msg="success";
        try {
        	BlogUtil bu = new BlogUtil(req);
            article_serv.updateArticlesType(ids, category, bu.getCurrentAdminWebSite().getId());
        } catch (Exception e) {
            e.printStackTrace();
            msg=e.getMessage();
        }
        return msg;
    }
    /**
     * 构建索引
     * @return
     */
    @ResponseBody
    @RequestMapping("/build_index.do")
    public String buildIndex(){
        String msg="";
        try {
        	article_serv.buildIndex();
            msg="success";
        } catch (Exception e) {
            e.printStackTrace();
            msg=e.getMessage();
        }
        return msg;
    }
    
    /**
     * 文章置顶
     * @param ids  文章id
     * @param req
     * @return
     */
    @ResponseBody
    @RequestMapping("/fixedTop.do")
    public String fixedTop(@RequestParam("ids[]") String[] ids,HttpServletRequest req){
        String msg="";
        try {
        	article_serv.fixedTop(ids, new BlogUtil(req).getCurrentAdminWebSite().getId());
            msg="success";
        } catch (Exception e) {
            e.printStackTrace();
            msg=e.getMessage();
        }
        return msg;
    }
    /**
     * 取消置顶
     * @param ids
     * @param req
     * @return
     */
    @ResponseBody
    @RequestMapping("/cancleFixedTop.do")
    public String cancleFixedTop(@RequestParam("ids[]") String[] ids,HttpServletRequest req){
        String msg="";
        try {
        	article_serv.cancleFixed(ids, new BlogUtil(req).getCurrentAdminWebSite().getId());
            msg="success";
        } catch (Exception e) {
            e.printStackTrace();
            msg=e.getMessage();
        }
        return msg;
    }
    
    /**
     * 设置封面
     * @param ids
     * @param req
     * @return
     */
    @ResponseBody
    @RequestMapping("/setCover.do")
    public String setCover(@RequestParam("ids[]") String[] ids,String resKey,HttpServletRequest req){
        String msg="";
        try {
        	if (null==resKey) {
				throw new Exception("资源key为空，操作失败！");
			}
        	article_serv.setCover(ids, new BlogUtil(req).getCurrentAdminWebSite().getId(),resKey);
            msg="success";
        } catch (Exception e) {
            e.printStackTrace();
            msg=e.getMessage();
        }
        return msg;
    }

    /**
     * 外键图片资源转换
     * @param id
     * @param req
     * @return
     */
    @ResponseBody
    @RequestMapping("/outsideImgResTransformation.do")
    public String outsideImgResTransformation(String id,HttpServletRequest req){
        String msg="success";
       try {
           article_serv.downloadArticleImg(id,req);
       }catch (Exception  ex){
           log.error("outsideImgResTransformation",ex);
           ex.printStackTrace();
           msg="error";
       }
        return msg;
    }
}
