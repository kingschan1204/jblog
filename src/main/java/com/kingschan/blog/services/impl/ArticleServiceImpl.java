package com.kingschan.blog.services.impl;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.kingschan.blog.po.*;
import com.kingschan.blog.util.*;
import org.hibernate.Session;
import org.hibernate.search.FullTextSession;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.kingschan.blog.model.vo.ArticleCommentVo;
import com.kingschan.blog.model.vo.ArticleVo;
import com.kingschan.blog.model.vo.CategoryVo;
import com.kingschan.blog.model.vo.LableVo;
import com.kingschan.blog.model.vo.UserVo;
import com.kingschan.blog.dao.CategoryDao;
import com.kingschan.blog.dao.Pagination;
import com.kingschan.blog.dao.impl.ArticleDaoImpl;
import com.kingschan.blog.dao.impl.WebSiteDaoImpl;
import com.kingschan.blog.services.ArticleService;

import javax.servlet.http.HttpServletRequest;

/**
 * <pre>
 * 类名称：ArticleServiceImpl
 * 类描述：
 * 创建人：陈国祥   (kingschan)
 * 创建时间：2016-2-20 上午10:58:47
 * 修改人：Administrator
 * 修改时间：2016-2-20 上午10:58:47
 * 修改备注：
 * @version V1.0
 * </pre>
 */
@Service
public class ArticleServiceImpl extends CommonServiceImpl implements ArticleService {

    private Logger log = LoggerFactory.getLogger(ArticleServiceImpl.class);

    @Qualifier("ArticleDaoImpl")
    @Autowired
    private ArticleDaoImpl article_dao;
    @Qualifier("CategoryDaoImpl")
    @Autowired
    private CategoryDao category_dao;
    @Autowired
    private UserServiceImpl userServ;
    @Autowired
    private WebSiteDaoImpl websiteDao;
    @Autowired
    private BlogResServiceImpl resServ;

    @Override
    public Pagination getArticleList(int page, int limit, String website, Map<String, Object> args)
            throws Exception {
        //转成articleVo
        Pagination p = article_dao.getNewArticleByPage(page, limit, website, args);
        List<ArticleVo> result = new ArrayList<ArticleVo>();
        List<Article> list = (List<Article>) p.getData();
        for (Article a : list) {
            ArticleText at = a.getArticleText();

            ArticleVo vo = new ArticleVo();
            CategoryVo cgv = new CategoryVo();

            BeanUtils.copyProperties(a, vo);
            BeanUtils.copyProperties(a.getCategory(), cgv);
            vo.setArticlePubtime(TimeStampUtil.timestampToString(a.getArticlePubtime()));
            vo.setArticleUpdatetime((TimeStampUtil.timestampToString(a.getArticleUpdatetime())));
            vo.setArticleContent(at.getArticleContent());
            vo.setArticleSummary(at.getArticleSummary());
            vo.setCategory(cgv);
            result.add(vo);
        }
        p.setData(result);
        return p;
    }

    @Override
    public Article ArticleInfo(String keyword, boolean readonce, String website) throws Exception {
        Article ar = null;
        if (keyword.matches("\\w{8}-\\w{4}-\\w{4}-\\w{4}-\\w{12}") || keyword.matches("\\w{32}")) {
            ar = article_dao.getArticleByID(keyword.replace("-", ""), website);
        } else {
            ar = article_dao.getArticleByTitleOrLinkURL(keyword, website);
        }
        //记录阅读次数
        if (readonce && null != ar) {
            ar.setArticleViewcount(ar.getArticleViewcount() + 1);
        }
        return ar;
    }

    @Override
    public Pagination getArticleByLable(String lableName, String website, int page, int limit) throws Exception {
        Pagination p = article_dao.getArticleByLable(lableName, website, page, limit);
        List<ArticleVo> result = new ArrayList<ArticleVo>();
        List<Article> list = (List<Article>) p.getData();
        for (Article a : list) {
            ArticleText at = a.getArticleText();

            ArticleVo vo = new ArticleVo();
            CategoryVo cgv = new CategoryVo();

            BeanUtils.copyProperties(a, vo);
            BeanUtils.copyProperties(a.getCategory(), cgv);
            vo.setArticlePubtime(TimeStampUtil.timestampToString(a.getArticlePubtime()));
            vo.setArticleUpdatetime((TimeStampUtil.timestampToString(a.getArticleUpdatetime())));
            vo.setArticleContent(at.getArticleContent());
            vo.setArticleSummary(at.getArticleSummary());
            vo.setCategory(cgv);
            result.add(vo);
        }
        p.setData(result);
        return p;
    }

    @Override
//    @Cacheable(value="hotLable",key="#websiteid")
    public List<Article> getHotArticle(String websiteid, int limit) throws Exception {
        return article_dao.getHotArticle(websiteid, limit);
    }

    @Override
    public List<Object[]> getSiteMapByWebSite(String websiteid) throws Exception {
        return article_dao.getSiteMapByWebSite(websiteid);
    }

    @Override
    public Map<String, Object> getEveryDayArticleInfo(String websiteid, int year, int month)
            throws Exception {
        return article_dao.getEveryDayArticleInfo(websiteid, year, month);
    }

    @Override
    public Pagination getFullTextSearch(int page, int limit, String website, Boolean isback, String keyword,
                                        String... fields) throws Exception {
        Pagination p = article_dao.getFullTextSearch(page, limit, website, isback, keyword, fields);
        return p;
    }

    @Override
    public String saveArticle(ArticleVo vo, String type, User user, WebSite ws) throws Exception {
        boolean add = false;
        Article a = null;
        ArticleText at = null;
        Set<Lable> labes = new HashSet<Lable>();
        if (null == vo.getId() || vo.getId().isEmpty()) {
            a = new Article();
            at = new ArticleText();
            a.setArticleViewcount(0);
            a.setArticleStatus((short) 1);
            a.setArticleSort(0);
            a.setArticleLikes(0);
            a.setWebsiteid(ws.getId());
            if (null == vo.getArticlePubtime() || vo.getArticlePubtime().isEmpty()) {
                a.setArticlePubtime(TimeStampUtil.getCurrentDate());
            } else {
                a.setArticlePubtime(TimeStampUtil.convertStringToTimeStamp(vo.getArticlePubtime()));
            }
            a.setArticleUpdatetime(TimeStampUtil.getCurrentDate());
            add = true;
        } else {
            a = article_dao.getArticleByID(vo.getId());
            at = a.getArticleText();
            //如果是发布就设置更新时间
            if (null != type && type.equalsIgnoreCase("release")) {
                a.setArticleUpdatetime(TimeStampUtil.getCurrentDate());
            }
        }
        a.setArticleCover(vo.getArticleCover());
        a.setArticleAllowcomments(null == vo.getArticleAllowcomments() ? false : vo.getArticleAllowcomments());
        a.setArticleTitle(Jsoup.parse(vo.getArticleTitle()).text());
        a.setCategory(category_dao.getObj(vo.getCategory().getId()));
        a.setArticlePrivate(null == vo.getArticlePrivate() ? false : vo.getArticlePrivate());
        a.setArticleLinkurl(null == vo.getArticleLinkurl() ? "" : vo.getArticleLinkurl());
        a.setArticlePassword(vo.getArticlePassword());
        a.setArticleEditor(vo.getArticleEditor());
        if (null != vo.getArticleLableStr()) {
            String[] lables = vo.getArticleLableStr().replaceAll("\\s", "").split(",");
            Map<String, String> map = new HashMap<String, String>();
            //前端传过来的值去重
            for (String str : lables) {
                if (!str.isEmpty()) {
                    map.put(str, null);
                }
            }
            if (!add) {
                //把已存在的lable放入map
                for (Lable la : a.getLables()) {
                    map.put(la.getLableName(), "");
                }
            }
            for (String keyword : map.keySet()) {
                String key = keyword.trim();
                if (map.containsKey(key)) {
                    if (null == map.get(key) && !key.matches("\\w{32}")) {
                        labes.add(new Lable(ws, a, key, user.getId(), TimeStampUtil.getCurrentDate()));
                    }
                }
            }
        }

        at.setArticleContent(vo.getArticleContent());
        at.setArticleSummary(vo.getArticleSummary());
        a.setUser(user);
        a.setArticleMd5(CommomEncrypt.MD5(at.getArticleContent()));
        a.setLables(labes);
        if (a.getArticleEditor().equals("html")) {
            String _html = Jsoup.clean(at.getArticleContent(),
                    Whitelist.relaxed()
                    .addAttributes("a","target")
                    .addAttributes("tr","class")
            );
            at.setArticleContent(_html);
        }
        if (add) {
            article_dao.addObj(a);
            at.setId(a.getId());
            article_dao.save(at);
        } else {
            article_dao.updateObj(a);
            article_dao.update(at);
        }
        return a.getId();
    }

    @Override
    public int delArticles(String[] ids) throws Exception {
        return article_dao.delArticles(ids);
    }

    @Override
    public int updateArticleType(String[] ids, String typeid) throws Exception {
        return article_dao.updateArticleType(ids, typeid);
    }

    @Override
    public void buildIndex() throws Exception {
        Session session = article_dao.getSession();
//	      session.getTransaction().begin();
        FullTextSession ftSession = org.hibernate.search.Search.getFullTextSession(session);
        List<Article> articles = (List<Article>) session.createQuery("from Article ").list();
        for (Article ar : articles) {
            ftSession.index(ar);
        }
//	      ftSession.getTransaction().commit();

    }

    @Override
    public int updateArticlesType(String[] ids, String category, String websiteid) throws Exception {
        return article_dao.updateArticlesType(ids, category, websiteid);
    }

    @Cacheable(value = "MonthArticleInfoCache", key = "#key")
    @Override
    public Map<String, Object> getEveryDayArticleInfoForCache(String key) throws Exception {
        String keys[] = key.split(",");
        //(key)website,year,month
        return getEveryDayArticleInfo(keys[0], Integer.valueOf(keys[1]), Integer.valueOf(keys[2]));
    }

    @Override
    public Pagination getHomeArticleList(int page, int limit,
                                         Map<String, Object> args) throws Exception {
        Pagination p = article_dao.getHomeArticleList(page, limit, args);
        List<ArticleVo> lis = new ArrayList<ArticleVo>();
        @SuppressWarnings("unchecked")
        List<Object[]> data = (List<Object[]>) p.getData();
        ArticleVo temp_article = null;
        UserVo temp_user = null;
        for (Object[] obj : data) {
            Article article = (Article) obj[0];
            ArticleText at = (ArticleText) obj[1];
            User user = (User) obj[2];
            temp_article = new ArticleVo();
            temp_user = new UserVo();
            temp_article.setArticlePubtime(TimeStampUtil.timestampToString(article.getArticlePubtime()));
            BeanUtils.copyProperties(article, temp_article);
            BeanUtils.copyProperties(user, temp_user);
            temp_article.setUser(temp_user);
            temp_article.setArticleSummary(at.getArticleSummary());
            temp_article.setArticleContent(at.getArticleContent());
            lis.add(temp_article);
        }
        p.setData(lis);
        return p;
    }

    @Override
    public Pagination getHomeFullTextSearch(int page, int limit,
                                            String keyword, String... fields) throws Exception {
        Pagination p = article_dao.getHomeFullTextSearch(page, limit, keyword, fields);
        return p;
    }

    @Override
    public ArticleVo getArticle(String keyword, boolean readonce, String website)
            throws Exception {
        Article a = ArticleInfo(keyword, readonce, website);
        if (null == a) {
            return null;
        }
        ArticleText at = a.getArticleText();
        ArticleVo vo = new ArticleVo();
        BeanUtils.copyProperties(a, vo);
        //lables
        Set<LableVo> lables = new HashSet<LableVo>();
        for (Lable lab : a.getLables()) {
            LableVo labvo = new LableVo();
            BeanUtils.copyProperties(lab, labvo);
            lables.add(labvo);
        }
        vo.setLables(lables);
        //category
        Category cg = a.getCategory();
        CategoryVo cgvo = new CategoryVo();
        BeanUtils.copyProperties(cg, cgvo);
        vo.setCategory(cgvo);

        User u = a.getUser();
        UserVo uvo = new UserVo();
        BeanUtils.copyProperties(u, uvo);
        vo.setUser(uvo);
        vo.setArticlePubtime(TimeStampUtil.timestampToString(a.getArticlePubtime()));
        vo.setArticleUpdatetime((TimeStampUtil.timestampToString(a.getArticleUpdatetime())));
        vo.setArticleSummary(at.getArticleSummary());
        vo.setArticleContent(at.getArticleContent());
        return vo;
    }

    @Override
    public Pagination getCommentsByArticle(int page, int limit, String articleId, Map<String, Object> args) throws Exception {
        return article_dao.getCommentsByArticle(page, limit, articleId, args);
    }


    @Override
    public int delArticleComments(String[] ids) throws Exception {
        return article_dao.delArticleComments(ids);

    }

    @Override
    public int fixedTop(String[] ids, String websiteid) throws Exception {
        return article_dao.fixedTop(ids, websiteid);
    }

    @Override
    public int cancleFixed(String[] ids, String websiteid) throws Exception {
        return article_dao.cancleFixed(ids, websiteid);
    }

    @Override
    public int setCover(String[] ids, String websiteid, String resKey)
            throws Exception {
        return article_dao.setCover(ids, websiteid, resKey);
    }


    @Override
    public int delSelfComments(String id, String userid) throws Exception {
        return article_dao.delSelfComments(id, userid);
    }

    @Override
    public String articleCommentSupport(String userid, String commentId)
            throws Exception {
        String result = "+";
        ArticleComment ac = (ArticleComment) get(ArticleComment.class, commentId);
        // userid+comomentId=点赞表的主键
        String pk = CommomEncrypt.MD5(userid + commentId);
        ArticleCommentSupport acs = (ArticleCommentSupport) get(ArticleCommentSupport.class, pk);
        if (null == acs) {
            //点赞
            acs = new ArticleCommentSupport();
            acs.setId(pk);
            acs.setArticleComment(ac);
            acs.setCreatetime(TimeStampUtil.getCurrentDate());
            acs.setUserid(userid);
            ac.setCSupport(ac.getCSupport() + 1);
            article_dao.save(acs);
        } else {
            //取消点赞
            ac.setCSupport(ac.getCSupport() - 1);
            article_dao.delete(acs);
            result = "-";
        }
        article_dao.update(ac);
        return result;
    }

    @Override
    public Pagination getCommentsByArticle(int page, int limit, String articleId, String userid, String model) throws Exception {

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("articleid", articleId);
        params.put("userid", userid);
        params.put("root", "");
        String sqlcmd = SqlUtil.getSql("article", "article_comments");
        StringBuffer sql = new StringBuffer(sqlcmd);
        if (model.equals("default")) {
            sql.append(" order by a.c_datetime desc");
        } else if (model.equals("hot")) {
            sql.append(" order by a.c_support desc,a.c_datetime desc ");
        } else if (model.equals("aboutme")) {
            sql.append(" and (a.c_user_id=:userid or a.c_tosomeone=:userid) ")
                    .append(" order by a.c_datetime desc");
        }
        Pagination p = article_dao.PaginationsBySQL(sql.toString(), page, limit, false, params);
        @SuppressWarnings("unchecked")
        List<Map<String, Object>> lis = (List<Map<String, Object>>) p.getData();
        List<ArticleCommentVo> rows = new ArrayList<ArticleCommentVo>();
        for (Map<String, Object> map : lis) {
            ArticleCommentVo ac = new ArticleCommentVo();
            ac.setCDatetime((Timestamp) map.get("c_datetime"));
            ac.setId(map.get("id").toString());
            String content = map.get("c_text").toString();//评论的内容
            String html = BlogUtil.markDownToHtml(content);
            html = BlogUtil.atUserFormat(html);//处理@用户
            ac.setCText(html.replaceAll("\\<(\\/)?p\\>", ""));//将markdown转成html
            ac.setCSupport(Integer.valueOf(map.get("c_support").toString()));
            ac.setCReplyTotal(Integer.valueOf(map.get("reply_total").toString()));
            //谁评论的
            UserVo fromU = new UserVo();
            fromU.setId(map.get("from_user_id").toString());
            fromU.setUserProfileImg(map.get("user_profile_img").toString());
            fromU.setUserScreenName(map.get("user_screen_name").toString());
            fromU.setUserName(map.get("user_name").toString());
            fromU.setExtendUserUrl(map.get("from_user_url").toString());
            ac.setCUser(fromU);
            //@谁
            if (null != map.get("to_uid")) {
                UserVo toU = new UserVo();
                toU.setId(map.get("to_uid").toString());
                toU.setUserProfileImg(map.get("to_img_s").toString());
                toU.setUserScreenName(map.get("to_screen_name").toString());
                toU.setExtendUserUrl(map.get("to_user_url").toString());
                ac.setCTosomeone(toU);
            }
            ac.setExistsSupport(null != map.get("support"));//当前登录用户是否已经点赞过
            rows.add(ac);
        }
        p.setData(rows);
        return p;
    }

    @Override
    public Pagination getArticleTimeLine(String websiteId, int page, int limit, String date) throws Exception {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("website", websiteId);
        if (null != date && !date.isEmpty()) {
            params.put("date", date);
        }
        StringBuffer sql = new StringBuffer(SqlUtil.getSql("article", "article_timeline_yyyymm", params));
        Pagination p = article_dao.PaginationsBySQL(sql.toString(), page, limit, false, params);
        return p;
    }

    @SuppressWarnings("unchecked")
    @Override
    public List<Map<String, Object>> getArticleTimeNavigate(String websiteId)
            throws Exception {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("website", websiteId);
        StringBuffer sql = new StringBuffer(SqlUtil.getSql("article", "article_timeline_navigate"));
        return (List<Map<String, Object>>) article_dao.queryForListMapBySql2(sql.toString(), false, params);
    }

    @Override
    public String articleSupport(String userid, String articleId)
            throws Exception {
        String result = "+";
        Article article = (Article) get(Article.class, articleId);
        // userid+articleId=点赞表的主键
        String pk = CommomEncrypt.MD5(userid + articleId);
        BlogArticleLikes al = (BlogArticleLikes) get(BlogArticleLikes.class, pk);
        if (null == al) {
            //点赞
            al = new BlogArticleLikes();
            al.setId(pk);
            al.setArticle(article);
            al.setCreatetime(TimeStampUtil.getCurrentDate());
            al.setUserid(userid);
            article.setArticleLikes(article.getArticleLikes() + 1);
            article_dao.save(al);
        } else {
            //取消点赞
            article.setArticleLikes(article.getArticleLikes() - 1);
            article_dao.delete(al);
            result = "-";
        }
        article_dao.update(article);
        return result;
    }

    @Override
    public BlogArticleLikes getArticleLike(String userid, String articleId)
            throws Exception {
        String pk = CommomEncrypt.MD5(userid + articleId);
        return (BlogArticleLikes) get(BlogArticleLikes.class, pk);
    }

    @Override
    public void downloadArticleImg(String articleId, HttpServletRequest req) throws Exception {
        ArticleText text = (ArticleText) get(ArticleText.class, articleId);
        Document doc = Jsoup.parse(text.getArticleContent());
        Elements es = doc.getElementsByTag("img");
        for (Element img : es) {
            // img.attr("src","res.51so.info");
            String url = img.attr("src").toString();
            if (!url.startsWith(getCdnhost())) {
                DownLoadImgUtil file = new DownLoadImgUtil().download(url, getFileTempFolder());
                if (null != file) {
                    //img.attr("src",)
                    try {
                        String key = resServ.uploadFile(file.getFilePath(), file.getFileName(), file.getFileType(), req);
                        img.attr("src", String.format("%s/%s", getCdnhost(), key));
                    } catch (Exception e) {
                        log.error("downloadArticleImg:", e);
                    }
                }
            }
        }
        text.setArticleContent(doc.toString());
        article_dao.update(text);
    }

}
