package com.kingschan.blog.dao.impl;

import com.kingschan.blog.dao.ArticleCommentDao;
import com.kingschan.blog.dao.HibernateBaseDao;
import com.kingschan.blog.dao.Pagination;

public class ArticleCommentDaoImpl extends HibernateBaseDao implements ArticleCommentDao{

	@Override
	public Pagination getSupportUsersByCommentId(String id) throws Exception {
		return null;
	}

}
