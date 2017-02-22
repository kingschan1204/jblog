package com.kingschan.blog.dao.font;

import java.util.List;

import com.kingschan.blog.model.vo.ArticleVo;
import com.kingschan.blog.dao.Pagination;
import com.kingschan.blog.po.ArticleComment;
import com.kingschan.blog.po.BlogMsgBoard;

public interface BlogFontDao {

	/**
	 * 留言
	 * 
	 * @param bmb
	 * @throws Exception
	 */
	void addMsgBoard(BlogMsgBoard bmb) throws Exception;

	/**
	 * 加载文章评论
	 * @param page
	 * @param limit
	 * @param articleId
	 * @param userid
	 * @param root
	 * @param model
	 * @return
	 * @throws Exception
	 */
	Pagination getArticleDiscuss(int page, int limit, String articleId, String userid, String root, String model) throws Exception;

	
	/**
	 *  加载指定文章评论回复列表
	 * @param page
	 * @param limit
	 * @param rootId
	 * @param currentUser
	 * @return
	 * @throws Exception
	 */
	List<ArticleComment> getArticleDiscussReplyById(Integer page, Integer limit, String rootId, String currentUser) throws Exception;
	
	
	
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
	
	/**
	 * 得到相似文章
	 * @param keyword
	 * @return
	 * @throws Exception
	 */
	List<ArticleVo> similarArticles(String website, String keyword, int limit)throws Exception;
}
