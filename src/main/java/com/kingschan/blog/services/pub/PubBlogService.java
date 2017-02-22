package com.kingschan.blog.services.pub;

import java.util.List;

import com.kingschan.blog.model.vo.ArticleVo;
import com.kingschan.blog.dao.Pagination;

/**
 * 博客公共服务接口
 * @author kingschan
 *
 */
public interface PubBlogService {

	/**
	 * 博客动态
	 * @param limit
	 * @param page
	 * @param userid
	 * @return
	 * @throws Exception
	 */
	Pagination blogTimeLine(Integer limit, Integer page, String userid, String websitename)throws Exception;
	
	/**
	 * 留言板
	 * @param limit
	 * @param page
	 * @param website
	 * @return
	 * @throws Exception
	 */
	Pagination blogMsgBoard(Integer limit, Integer page, String website)throws Exception;
	
	/**
	 * 找到相似的文章
	 * @param website
	 * @param keyword
	 * @param limit
	 * @return
	 * @throws Exception
	 */
	List<ArticleVo> similarArticles(String website, String keyword, int limit)throws Exception;
}
