package com.kingschan.blog.dao;

public interface ArticleCommentDao {

	/**
	 * 得到点赞人清单
	 * @param id
	 * @return
	 * @throws Exception
	 */
	Pagination getSupportUsersByCommentId(String id)throws Exception;
}
