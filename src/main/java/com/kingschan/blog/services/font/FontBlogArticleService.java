package com.kingschan.blog.services.font;

import java.util.List;

import com.kingschan.blog.model.vo.ArticleVo;
import com.kingschan.blog.dao.Pagination;
import com.kingschan.blog.po.ArticleComment;

/**
 * 前端博客文章服务接口定义
 * @author kingschan
 *
 */
public interface FontBlogArticleService {

	/**
	 * 加载文章评论
	 * @param page
	 * @param limit
	 * @param website
	 * @param articleId
	 * @param model
	 * @param currentUser
	 * @return
	 * @throws Exception
	 */
	Pagination getArticleDiscuss(Integer page, Integer limit, String website, String articleId, String model, String currentUser)throws Exception;
	
	/**
	 * 加载指定文章评论回复列表
	 * @param page
	 * @param limit
	 * @param rootId
	 * @return
	 * @throws Exception
	 */
	List<ArticleComment> getArticleDiscussReplyById(Integer page, Integer limit, String rootId, String currentUser)throws Exception;
	
	/**
	 * 文章评论
	 * @param userId
	 * @param tosomeOne
	 * @param articleid
	 * @param text
	 * @param root
	 * @throws Exception
	 */
	void addArticleDiscuss(String userId, String tosomeOne, String articleid, String text, String root) throws Exception;
	
	
	/**
	 * 得到下一篇文章
	 * @param datetime
	 * @param website
	 * @return
	 * @throws Exception
	 */
	ArticleVo getNext(String datetime, String website)throws Exception;
	/**
	 * 得到上一篇文章
	 * @param datetime
	 * @param website
	 * @return
	 * @throws Exception
	 */
	ArticleVo getPrevious(String datetime, String website)throws Exception;
}
