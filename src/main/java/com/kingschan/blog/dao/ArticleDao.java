package com.kingschan.blog.dao;

import java.util.List;
import java.util.Map;

import com.kingschan.blog.po.Article;

public interface ArticleDao extends BaseDao<Article>{

    /**
     * 得到最新的文章
     * @param page
     * @param website
     * @return
     * @throws Exception
     */
    Pagination getNewArticleByPage(int page, int limit, String website, Map<String, Object> args)throws Exception;
    /**
     * 主页文章列表
     * @param page
     * @param limit
     * @param args
     * @return
     * @throws Exception
     */
    Pagination getHomeArticleList(int page, int limit, Map<String, Object> args)throws Exception;


    /**
     * 全文检索
     * @param page
     * @param limit
     * @param website
     * @param isback 是否后台（如果是前）
     * @param keyword
     * @param fields
     * @return
     * @throws Exception
     */
    Pagination getFullTextSearch(int page, int limit, String website,Boolean isback,String keyword, String... fields)throws Exception;
    
    /**
     *  主文检索
     * @param page
     * @param limit
     * @param keyword
     * @param fields
     * @return
     * @throws Exception
     */
    Pagination getHomeFullTextSearch(int page, int limit, String keyword, String... fields)throws Exception;
    
    /**
     * 通过ID得到文章
     * @param id
     * @return
     * @throws Exception
     */
    Article getArticleByID(String id)throws Exception;
    
    /**
     * 通过id和网站得到文章
     * @param id
     * @param website
     * @return
     * @throws Exception
     */
    Article getArticleByID(String id, String website)throws Exception;

    /**
     * 通过标题得到文章
     * @param keyword
     * @param website
     * @return
     * @throws Exception
     */
    Article getArticleByTitleOrLinkURL(String keyword, String website)throws Exception;


    /**
     * 通知标签名字得到文章
     * @param lableName
     * @param website
     * @param page
     * @param limit
     * @return
     * @throws Exception
     */
    Pagination getArticleByLable(String lableName, String website, int page, int limit)throws Exception;
    
    /**
     * 得到阅读次数最多的文章
     * @param websiteid
     * @return
     * @throws Exception
     */
    List<Article> getHotArticle(String websiteid, int limit) throws Exception;
    
    
    /**
     * 得到sitemap
     * @param websiteid
     * @return
     * @throws Exception
     */
    List<Object[]> getSiteMapByWebSite(String websiteid)throws Exception;
    
    /**
     * 统计月度写日志情况
     * @param websiteid
     * @return
     * @throws Exception
     */
    Map<String,Object> getEveryDayArticleInfo(String websiteid, int year, int month)throws Exception;
    
    /**
     * 删除文章
     * @param ids
     * @throws Exception
     */
    int delArticles(String[] ids)throws Exception;
    /**
     * 删除自己个人的评论
     * @param id
     * @param userid
     * @return
     * @throws Exception
     */
    int delSelfComments(String id, String userid) throws Exception ;
    
    /**
     * 修改文章类型
     * @param ids
     * @param typeid
     * @return
     * @throws Exception
     */
    int updateArticleType(String[] ids, String typeid)throws Exception;
    
    
    /**
     * 批量修改博客类型
     * @param ids
     * @param category
     * @param websiteid
     * @return
     * @throws Exception
     */
    int updateArticlesType(String[] ids, String category, String websiteid)throws Exception;
    
    /**
     * 得到文章评论
     * @param page
     * @param limit
     * @param articleId
     * @param args 如果包含CUserId 则只显示此用户所有评论
     * @return
     * @throws Exception
     */
    Pagination getCommentsByArticle(int page, int limit, String articleId, Map<String, Object> args)throws Exception;
    
    /**
     * 删除评论
     * @param ids
     * @return
     * @throws Exception
     */
    int delArticleComments(String[] ids) throws Exception;
    
    /**
     * 文章置顶
     * @param ids
     * @param websiteid
     * @return
     * @throws Exception
     */
    int fixedTop(String[] ids, String websiteid) throws Exception;
    /**
     * 取消置顶
     * @param ids
     * @param websiteid
     * @return
     * @throws Exception
     */
    int cancleFixed(String[] ids, String websiteid) throws Exception;
    
    /**
     * 设置文章封面
     * @param ids
     * @param websiteid
     * @param resKey
     * @return
     * @throws Exception
     */
    int setCover(String[] ids, String websiteid, String resKey) throws Exception;
    
    /**
     * 文章归档
     * @param page 页码
     * @param limit
     * @param website 网站 
     * @return
     * @throws Exception
     */
    Pagination articleArchive(int page, int limit, String website)throws Exception;


    /**
     * 统计指定日期发表的文章数量
     * @param websiteId
     * @param dateString
     * @return
     * @throws Exception
     */
    int getArticleDateQuantity(String websiteId,String dateString)throws Exception;

}