package com.kingschan.blog.services;

import java.util.List;
import java.util.Map;

import com.kingschan.blog.model.vo.ArticleVo;
import com.kingschan.blog.dao.Pagination;
import com.kingschan.blog.po.Article;
import com.kingschan.blog.po.BlogArticleLikes;
import com.kingschan.blog.po.User;
import com.kingschan.blog.po.WebSite;

import javax.servlet.http.HttpServletRequest;

/**
 * 
*  <pre>    
* 类名称：ArticleService 
* 类描述：   
* 创建人：陈国祥   (kingschan)
* 创建时间：2016-2-20 上午10:54:29   
* 修改人：Administrator   
* 修改时间：2016-2-20 上午10:54:29   
* 修改备注：   
* @version V1.0
* </pre>
 */
public interface ArticleService {

    /**
     * 得到文章列表
     * @param page
     * @param website
     * @param args
     * @return
     * @throws Exception
     */
    Pagination getArticleList(int page, int limit, String website, Map<String, Object> args)throws Exception;
    
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
     * 文章全文检索
     * @param page
     * @param limit
     * @param website
     * @param isback 是否后台
     * @param keyword
     * @param fields
     * @return
     * @throws Exception
     */
    Pagination getFullTextSearch(int page, int limit, String website,Boolean isback, String keyword, String... fields)throws Exception;

    /**
     * 文章全文检索
     * @param page
     * @param limit
     * @param keyword
     * @param fields
     * @return
     * @throws Exception
     */
    Pagination getHomeFullTextSearch(int page, int limit, String keyword, String... fields)throws Exception;

    /**
     * 得到文章
     * @param keyword
     * @param readonce 是否需要记录阅读次数
     * @return
     * @throws Exception
     */
    Article ArticleInfo(String keyword, boolean readonce, String website)throws Exception;
    
    /**
     * 得到文章
     * @param keyword
     * @param readonce 是否需要记录阅读次数
     * @param website
     * @return
     * @throws Exception
     */
    ArticleVo getArticle(String keyword, boolean readonce, String website)throws Exception;
    
    
    /**
     * 文章归档时间轴
     * @param websiteId
     * @param page
     * @param limit
     * @param date yyyy-mm
     * @return
     * @throws Exception
     */
    Pagination getArticleTimeLine(String websiteId, int page, int limit, String date)throws Exception;
    /**
     * 文章归档时间轴导航  年-月
     * @param websiteId
     * @return
     * @throws Exception
     */
    List<Map<String, Object>> getArticleTimeNavigate(String websiteId)throws Exception;
    /**
     * 通过标签得到文章列表
     * @param lableName
     * @param page
     * @return
     * @throws Exception
     */
    Pagination getArticleByLable(String lableName, String website, int page, int limit) throws Exception ;
    
    /**
     * 得到阅读次数最多的文章
     * @param websiteid
     * @return
     * @throws Exception
     */
    List<Article> getHotArticle(String websiteid, int limit)throws Exception;
    
    
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
     * 统计月度写日志情况（使用缓存）
     * @param key( websiteid, year, month逗号分隔加起组成key)
     * @return
     * @throws Exception
     */
    Map<String,Object> getEveryDayArticleInfoForCache(String key)throws Exception;
    
    
    /**
     * 保存文章
     * @param vo
     * @param type 方式： release 发布  update 更新
     * @param user
     * @param ws
     * @return
     * @throws Exception
     */
    String saveArticle(ArticleVo vo, String type, User user, WebSite ws)throws Exception;
    
    /**
     * 删除文章
     * @param ids
     * @return
     * @throws Exception
     */
    int delArticles(String[] ids) throws Exception;
    
    /**
     * 修改文章类别
     * @param ids
     * @param typeid
     * @return
     * @throws Exception
     */
    int updateArticleType(String[] ids, String typeid) throws Exception;
    
    /**
     * 整个文章重生生成索引
     * @throws Exception
     */
    void buildIndex()throws Exception;
    
    /**
     * 改变文章类型
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
      * 得到文章评论(包含点赞)
      * @param page
      * @param limit
      * @param articleId
      * @param userid
      * @param args
      * @return
      * @throws Exception
      */
     Pagination getCommentsByArticle(int page, int limit, String articleId, String userid, String model)throws Exception;
     
     
     
     /**
      * 删除文章评论
      * @param ids
      * @return
      * @throws Exception
      */
     int delArticleComments(String[] ids) throws Exception;
     
     /**
      * 删除自已的评论
      * @param id
      * @param userid
      * @return
      * @throws Exception
      */
     int delSelfComments(String id, String userid) throws Exception;
     
     
     
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
      * 点赞取消点赞
      * @param userid
      * @param commentId
      * @return +点赞  -取消点赞
      * @throws Exception
      */
     String articleCommentSupport(String userid, String commentId)throws Exception;
     
     /**
      * 喜欢 || 取消喜欢文章
      * @param userid
      * @param articleId
      * @return
      * @throws Exception
      */
     String articleSupport(String userid, String articleId)throws Exception;
     
     
     /**
      * 得到当前用户是否已经点赞过该文章
      * @param userid
      * @param articleId
      * @return
      * @throws Exception
      */
     BlogArticleLikes getArticleLike(String userid, String articleId)throws Exception;

    /**
     * 将文章里面的图片外部链接转换成自已CDN加速网站
     * @param articleId
     * @param req
     * @throws Exception
     */
    void downloadArticleImg(String articleId, HttpServletRequest req)throws Exception;

}
