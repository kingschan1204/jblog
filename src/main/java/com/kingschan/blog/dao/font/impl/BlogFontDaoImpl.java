package com.kingschan.blog.dao.font.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.hankcs.lucene.HanLPIndexAnalyzer;
import org.apache.commons.lang.StringUtils;
import org.apache.lucene.search.highlight.Formatter;
import org.apache.lucene.search.highlight.Fragmenter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.hibernate.search.FullTextQuery;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import com.kingschan.blog.model.vo.ArticleCommentVo;
import com.kingschan.blog.model.vo.ArticleVo;
import com.kingschan.blog.model.vo.UserVo;
import com.kingschan.blog.dao.HibernateBaseDao;
import com.kingschan.blog.dao.Pagination;
import com.kingschan.blog.dao.font.BlogFontDao;
import com.kingschan.blog.po.Article;
import com.kingschan.blog.po.ArticleComment;
import com.kingschan.blog.po.ArticleCommentSupport;
import com.kingschan.blog.po.BlogMsgBoard;
import com.kingschan.blog.po.User;
import com.kingschan.blog.util.BlogUtil;
import com.kingschan.blog.util.SqlUtil;
import com.kingschan.blog.util.TimeStampUtil;

@Repository("BlogFontDaoImpl")
@SuppressWarnings("unchecked")
public class BlogFontDaoImpl extends HibernateBaseDao implements BlogFontDao {

	private Logger log =LoggerFactory.getLogger(BlogFontDaoImpl.class);
	@Override
	public void addMsgBoard(BlogMsgBoard bmb) throws Exception {
		save(bmb);
		BlogMsgBoard parent =StringUtils.isEmpty(bmb.getMsgRoot())?null:(BlogMsgBoard) get(BlogMsgBoard.class, bmb.getMsgRoot());
		if (null!=parent) {
			parent.setMsgCount(parent.getMsgCount()+1);
			save(parent);
		}
	}

	@Override
	public Pagination getArticleDiscuss(int page, int limit,String articleId, String userid,String root, String model) throws Exception {
		Pagination p= new Pagination();
		List<Map<String, Object>> lis =null;
		Map<String, Object> params= new HashMap<String, Object>();
		params.put("articleid", articleId);
		params.put("userid", userid);
		params.put("root", root);
		String sqlcmd=SqlUtil.getSql("article","article_comments");
		StringBuffer sql=new StringBuffer(sqlcmd);
		if (model.equals("default")) {
			sql.append(" order by a.c_datetime desc");
		}else if (model.equals("hot")) {
			sql.append(" order by a.c_support desc,a.c_datetime desc ");
		}else if (model.equals("aboutme")) {
			sql.append(" and (a.c_user_id=:userid or a.c_tosomeone=:userid) ")
			.append(" order by a.c_datetime desc");
		}
		if (root.matches("\\w{32}")) {
			//加载回复评论不分页
			lis=PaginationBySQL(sql.toString(), page, limit, false, params);
		}else{
			//加载根节点分页
			 p=PaginationsBySQL(sql.toString(), page, limit, false, params);
			 lis = (List<Map<String, Object>>) p.getData();
		}
		
		List<ArticleCommentVo> rows = new ArrayList<ArticleCommentVo>();
		for (Map<String, Object> map : lis) {
			ArticleCommentVo ac = new ArticleCommentVo();
			ac.setCDatetime((Timestamp)map.get("c_datetime"));
			ac.setId(map.get("id").toString());
			String content=map.get("c_text").toString();//评论的内容
			String html=BlogUtil.markDownToHtml(content);
			html=BlogUtil.atUserFormat(html);//处理@用户
			ac.setCText(html.replaceAll("\\<(\\/)?p\\>", ""));//将markdown转成html
			ac.setCSupport(Integer.valueOf(map.get("c_support").toString()));
			ac.setCReplyTotal(Integer.valueOf(map.get("reply_total").toString()));
			//谁评论的
			UserVo fromU=new UserVo();
			fromU.setId(map.get("from_user_id").toString());
			fromU.setUserProfileImg(map.get("user_profile_img").toString());
			fromU.setUserScreenName(map.get("user_screen_name").toString());
			fromU.setUserName(map.get("user_name").toString());
			fromU.setExtendUserUrl(map.get("from_user_url").toString());
			ac.setCUser(fromU);
			//@谁
			if (null!=map.get("to_uid")) {
				UserVo toU=new UserVo();
				toU.setId(map.get("to_uid").toString());
				toU.setUserProfileImg(map.get("to_img_s").toString());
				toU.setUserScreenName(map.get("to_screen_name").toString());
				toU.setExtendUserUrl(map.get("to_user_url").toString());
				ac.setCTosomeone(toU);
			}
			ac.setExistsSupport(null!=map.get("support"));//当前登录用户是否已经点赞过
			rows.add(ac);
		}
		p.setData(rows);
		return p;
	}

	@Override
	public List<ArticleComment> getArticleDiscussReplyById(Integer page, Integer limit,
			String rootId,String currentUser) throws Exception {
		String hql=" from ArticleComment a left join a.CUser b left join a.CTosomeone c left join a.supports d  where a.CRoot=? and a.CIsdel='√' and d.userid=?  order by a.CDatetime desc";
		List<ArticleComment> list = new ArrayList<ArticleComment>();
		List<Object[]> lis = (List<Object[]>) PaginationByHql(hql, page, limit, false, rootId,currentUser);
		for (Object[] item : lis) {
			ArticleComment comments=(ArticleComment) item[0];
			comments.setCUser((User)item[1]);
			if (null!=item[2]) {
				comments.setCTosomeone((User)item[2]);
			}
			if (null!=item[3]) {
				Set<ArticleCommentSupport> set =new HashSet<ArticleCommentSupport>();
				set.add((ArticleCommentSupport)item[3]);
				comments.setSupports(set);
			}
			list.add(comments);
		}
		return list;
	}

	@Override
	public ArticleVo getNext(String datetime, String website) throws Exception {
		String sql =String.format("select * from (select id,article_title,UNIX_TIMESTAMP(article_updatetime) sort from blog_article where websiteid='%s')t where  sort > UNIX_TIMESTAMP('%s')order by sort asc ", 
				website,datetime);
		List<Map<String, Object>> lis= PaginationBySQL(sql, 1, 1, false, null);
		if (null!=lis&&lis.size()>0) {
			Map<String, Object> row=lis.get(0);
			return new ArticleVo(row.get("id").toString(), row.get("article_title").toString());
		}
		return null;
	}

	@Override
	public ArticleVo getPrevious(String datetime, String website)
			throws Exception {
		String sql =String.format("select * from (select id,article_title,UNIX_TIMESTAMP(article_updatetime) sort from blog_article where websiteid='%s')t where  sort < UNIX_TIMESTAMP('%s')order by sort desc ", 
				website,datetime);
		List<Map<String, Object>> lis= PaginationBySQL(sql, 1, 1, false, null);
		if (null!=lis&&lis.size()>0) {
			Map<String, Object> row=lis.get(0);
			return new ArticleVo(row.get("id").toString(), row.get("article_title").toString());
		}
		return null;
	}

	@Override
	public List<ArticleVo> similarArticles(String website,String keyword,int limit) throws Exception {
		log.debug("相似文章查找：{}",keyword);
        QueryBuilder qb = getFullTextSession().getSearchFactory()
        .buildQueryBuilder().forEntity(Article.class).get();
        org.apache.lucene.search.Query query = null;
        	query= qb.bool().must(
        	            qb.keyword().onField("websiteid").matching(website).createQuery()
        	         ).must(
				             qb.keyword().onFields("articlePrivate").matching("false").createQuery()
					   ).must(
        	             qb.keyword().onFields("articleTitle","articleText.articleContent").matching(keyword).createQuery()
        	         ).createQuery();
       /* org.apache.lucene.search.Query query = qb
        .keyword().onFields(fields).matching(keyword)
        .createQuery();*/
        // wrap Lucene query in a org.hibernate.Query
        FullTextQuery fullTextQuery = getFullTextSession().createFullTextQuery(query, Article.class);
        fullTextQuery.setCacheable(true);
        fullTextQuery.setFirstResult(0);
        fullTextQuery.setMaxResults(limit);
        List<Article> lis=fullTextQuery.list();
        //高亮处理
        Formatter formatter = new SimpleHTMLFormatter("<font style='color:red;'>","</font>");
        //用于高亮查询,query是Lucene的查询对象Query
        QueryScorer scorer = new QueryScorer(query);
        //创建一个高亮器
        Highlighter highlighter = new Highlighter(formatter, scorer);
        //设置文本摘要大小
        Fragmenter fragmenter = new SimpleFragmenter(300);
        highlighter.setTextFragmenter(fragmenter);  
        String summary=null;
        List<ArticleVo> list=new ArrayList<ArticleVo>();
          for (Article article : lis) {
              String title=article.getArticleTitle();
              if (null==article.getArticleText().getArticleSummary()||article.getArticleText().getArticleSummary().isEmpty()) {
                  summary=Jsoup.parse(article.getArticleText().getArticleContent()).text();
                  summary=summary.length()>300? summary.substring(0,300):summary;
              }else {
                  summary=Jsoup.parse(article.getArticleText().getArticleSummary()).text();
              }
              String highlighterTitle = highlighter.getBestFragment(new HanLPIndexAnalyzer() , "articleTitle", title);
              //转换为vo
              ArticleVo vo = new ArticleVo();
              BeanUtils.copyProperties(article, vo);
             vo.setArticlePubtime(TimeStampUtil.timestampToString(article.getArticlePubtime()));
             if (null!=highlighterTitle) {
                vo.setArticleTitle(highlighterTitle);
             }
             vo.setArticleSummary(summary);
             list.add(vo);
          }
          return list;
	}

	

}
