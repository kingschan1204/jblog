package com.kingschan.blog.services;

import com.kingschan.blog.dao.Pagination;

public interface ArticleCommentService {

	/**
	 * 
	 * @param id
	 * @return
	 * @throws Exception
	 */
	Pagination getSupportUsersByCommentId(String id)throws Exception;
}
