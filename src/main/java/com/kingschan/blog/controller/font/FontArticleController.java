package com.kingschan.blog.controller.font;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kingschan.blog.po.User;
import com.kingschan.blog.services.ArticleService;
import com.kingschan.blog.services.UserService;
import com.kingschan.blog.services.font.impl.FontBlogArticleServiceImpl;
import com.kingschan.blog.util.BlogUtil;
import com.kingschan.blog.util.RegexUtil;
/**
 * 
*    
* 类名称：ArticleCommentController   
* 类描述：  
* 创建人：kings.chan
* 创建时间：2016-7-12 上午11:55:13   
* 修改人：   
* 修改时间：
* 项目：ROOT
* 修改备注：   
* @version    
*
 */
@Controller
@RequestMapping("/font")
public class FontArticleController {

	private Logger log =LoggerFactory.getLogger(FontArticleController.class);
	@Autowired
	private ArticleService article_serv;
	@Autowired
	private UserService userServ;
	@Autowired
	private FontBlogArticleServiceImpl fontBlogServ;
	/**
	 * 评论
	 * @param content
	 * @param articleId
	 * @param request
	 * @return
	 */
	@RequestMapping("/articleReplay.do")
	public String articleComment(String content,String articleId,String root,HttpServletRequest request){
		try {
			String txt=content;
			BlogUtil util = new BlogUtil(request);
			String userid=null;
			String target=RegexUtil.findStrByRegx(content, "\\@\\w+");
			if (!target.isEmpty()) {
				String[] users=target.replaceAll("\\@|\\:", "").split(",");
				User u = userServ.getUser(users[0]);
				if (null!=u) {
					userid =u.getId();
					//txt=txt.replace("@"+users[0], "");
				}else{
					userid=util.getCurrentFontWebSite().getUser().getId();
				}
			}
			userid=null==userid?userid=util.getCurrentFontWebSite().getUser().getId():userid;
			fontBlogServ.addArticleDiscuss(util.getCurrentUser().getId(), userid, articleId, txt, root);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return String.format("redirect:%s%s",request.getHeader("referer"),"#commentForm");
		
	}
	
	 /**
     * 评论回复
     * @param someone
     * @param article
     * @param text
     * @param req
     * @return
     */
    /*@ResponseBody
    @RequestMapping("/commentReply.do")
    public String commentReply(String someone,String article,String text,String model,HttpServletRequest req){
        String msg="success";
        try {
        	String userid=new BlogUtil(req).getCurrentUser().getId();
//			article_serv.articleCommentReply(userid, someone, article, text);
        } catch (Exception e) {
            msg=e.getMessage();
            e.printStackTrace();
        }
        if (null!=model&&model.equals("font")) {
        	return String.format("redirect:%s%s",req.getHeader("referer"),"#commentForm");
		}
        return msg;
    }*/
    
  
    /**
     * 删除自己个人的评论
     * @param id
     * @param req
     * @return
     */
    @ResponseBody
    @RequestMapping("/delComments.do")
    public String delArticleComments(String id,HttpServletRequest req){
        int affected=0;
        String msg=null;
        try {
        	String userid=new BlogUtil(req).getCurrentUser().getId();
            affected=article_serv.delSelfComments(id, userid);
            msg=affected>0?"success":"failure";
        } catch (Exception e) {
            e.printStackTrace();
            msg=e.getMessage();
        }
        return msg;
    }
    /**
     * 评论点赞
     * @param commentId
     * @return
     */
    @ResponseBody
    @RequestMapping("/commentSupport.do")
    public String commentSupport(HttpServletRequest req,String commentId){
    	String msg="";
    	BlogUtil bu = new BlogUtil(req);
    	if (!commentId.matches("\\w{32}")) {
			return "参数错误！";
		}
    	if (!bu.isLogin()) {
    		return "您还未登录！";
		}
    	try {
        	msg=article_serv.articleCommentSupport(bu.getCurrentUser().getId(), commentId);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("点赞",e);
			msg="系统出错！";
		}
    	return msg;
    }
    
    /**
     * 文章点赞
     * @param req
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping("/articleSupport.do")
    public String articleSupport(HttpServletRequest req,String id){
    	String msg="";
    	BlogUtil bu = new BlogUtil(req);
    	if (!id.matches("\\w{32}")) {
			return "参数错误！";
		}
    	if (!bu.isLogin()) {
    		return "您还未登录！";
		}
    	try {
        	msg=article_serv.articleSupport(bu.getCurrentUser().getId(), id);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("点赞",e);
			msg="系统出错！";
		}
    	return msg;
    }
    
}
