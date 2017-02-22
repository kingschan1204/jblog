package com.kingschan.blog.services.font;

import java.util.List;

import com.kingschan.blog.model.vo.ArticleCommentVo;

import net.sf.json.JSONArray;

public interface FontBlogService {

	/**
	 * 留言
	 * @param root
	 * @param at
	 * @param content
	 * @param currentUser
	 * @param website
	 * @param url
	 * @throws Exception
	 */
	 void addMsgBoard(String root, String at, String content, String currentUser, String website, String url)throws Exception;
	 
	 
	 
	 /**
	  * 加载更多留言板回复
	  * @param id
	  * @param page
	  * @return
	  * @throws Exception
	  */
	JSONArray loadMsgBoardReply(String website, String id, Integer page)throws Exception;
	
	/**
	 * 加载更多文章回复
	 * @param articleId
	 * @param root
	 * @param currentUser
	 * @param page
	 * @return
	 * @throws Exception
	 */
	List<ArticleCommentVo> loadArticleDiscussReply(String articleId, String root, String currentUser, Integer page)throws Exception;
}
