package com.kingschan.blog.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hankcs.lucene.HanLPIndexAnalyzer;
import com.kingschan.blog.po.*;
import org.apache.lucene.search.highlight.Formatter;
import org.apache.lucene.search.highlight.Fragmenter;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.hibernate.search.FullTextQuery;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.jsoup.Jsoup;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Repository;

import com.kingschan.blog.model.vo.ArticleCommentVo;
import com.kingschan.blog.model.vo.ArticleVo;
import com.kingschan.blog.model.vo.CategoryVo;
import com.kingschan.blog.model.vo.UserVo;
import com.kingschan.blog.dao.ArticleDao;
import com.kingschan.blog.dao.HibernateBaseDao;
import com.kingschan.blog.dao.Pagination;
import com.kingschan.blog.util.TimeStampUtil;
import com.kingschan.blog.util.UnixDate;
/**
 * 
*  <pre>    
* 类名称：ArticleDaoImpl 
* 类描述：   文章数据访问dao
* 创建人：陈国祥   (kingschan)
* 创建时间：2016-2-18 下午9:06:50   
* 修改人：Administrator   
* 修改时间：2016-2-18 下午9:06:50   
* 修改备注：   
* @version V1.0
* </pre>
 */
@Repository("ArticleDaoImpl")
@SuppressWarnings("unchecked")
public class  ArticleDaoImpl extends HibernateBaseDao implements ArticleDao{

    @Override
    public void addObj(Article obj) throws Exception {
        save(obj);
    }

    @Override
    public void deleteObj(Article obj) throws Exception {
        delete(obj);        
    }

    @Override
    public void updateObj(Article obj) throws Exception {
        update(obj);
        
    }

    @Override
    public Article getObj(Object id) throws Exception {
        return (Article) get(Article.class, id.toString());
    }

    @Override
    public Pagination getNewArticleByPage(int page,int limit, String website,Map<String, Object> args) throws Exception {
        StringBuffer hql = new StringBuffer(String.format("from Article a inner join a.articleText inner join a.category  where a.websiteid='%s'", website));
        if (args.get("model").toString().equalsIgnoreCase("font")) {
            hql.append(" and a.articlePrivate=false ");
        }
        if (args.containsKey("category")) {
            hql.append(" and a.category.categoryName=:category ");
        }else if (args.containsKey("categoryId")) {
        	hql.append(" and a.category.id=:categoryId ");
		}
        else if (args.containsKey("year")) {
            hql.append(" and YEAR(a.articlePubtime)=:year ")
            .append(" and MONTH(a.articlePubtime)=:month ");
            if (args.containsKey("day")) {
                hql.append(" and DAY(a.articlePubtime)=:day ");
            }
        }
        if (args.containsKey("title")) {
            hql.append(" and a.articleTitle like '%").append(args.get("title").toString()).append("%'");
            args.remove("title");
        }
        if (args.containsKey("orderby")) {
        	hql.append(" order by ").append(args.get("orderby").toString());
        	args.remove("orderby");
		}else{
			hql.append(" order by a.articleSort desc ,a.articleUpdatetime desc");
		}
        args.remove("model");
        Pagination p = PaginationsByHQLMapParams(hql.toString(), page, limit, true, args);
        List<Object[]> list = (List<Object[]>)p.getData();
        List<Article> result=new ArrayList<Article>();
        for (Object[] row:list) {
            Article a =(Article)row[0];
            ArticleText at =(ArticleText)row[1];
            Category cg =(Category)row[2];
            a.setArticleText(at);
            a.setCategory(cg);
            result.add(a);
        }
        p.setData(result);
        return p;
    }
    
    @Override
	public Pagination getHomeArticleList(int page,
			int limit, Map<String, Object> args) throws Exception {
    	StringBuffer hql = new StringBuffer(" from Article a left join a.articleText left join a.user where  a.articlePrivate=false");
        if (args.containsKey("category")) {
            hql.append(" and a.category.categoryName=:category ");
        }       
        hql.append(" order by a.articleUpdatetime desc");
        return PaginationsByHQLMapParams(hql.toString(), page, limit, true, args);
	}
    
    @Override
    public Article getArticleByID(String id) throws Exception {
        return getObj(id);
    }

    
    //select b.id,b.article_title,a.lable_name from blog_label a left join blog_article b on a.lable_articleid=b.id  where a.lable_name='java' ORDER BY b.article_updatetime desc limit 10
    @Override
    public Pagination getArticleByLable(String lableName,String website,int page,int limit) throws Exception {
        Pagination p = null;
        //String hql="select lableName,article from Lable a where a.lableName =:lablename and a.webSite.id=:website and a.article.articlePrivate=false ";
        String hql =" from Lable a inner join a.article inner join a.article.articleText where a.webSite.id=:website and a.lableName=:lablename";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("lablename", lableName);
        map.put("website", website);
        p=PaginationsByHQLMapParams(hql, page, limit, true,map);
        List<Object[]> lis= (List<Object[]>) p.getData();//(List<Object[]>) Pagination(hql, page, 10,true, lableName);
        List<Article> list = new ArrayList<Article>();
        for (Object[] objects : lis) {
            Article a = (Article) objects[1];
            ArticleText at = (ArticleText)objects[2];
            a.setArticleText(at);
            list.add(a);
        }
        p.setData(list);
        return p;
    }

    @Override
    public Article getArticleByTitleOrLinkURL(String keyword,String website) throws Exception {
        String hql="from Article a where a.websiteid=? and ( a.articleTitle=? or a.articleLinkurl=? )";
        List<Article> lis = (List<Article>) queryForListByHql(hql,true,false,website, keyword,keyword);
        return null!=lis&&lis.size()>0?lis.get(0):null;
    }

    @Override
    public List<Article> getHotArticle(String websiteid,int limit) throws Exception {
        String hql="from Article a  where a.websiteid=? order by a.articleViewcount desc";
        return (List<Article>) PaginationByHql(hql, 1, limit, true, websiteid);
    }

    @Override
    public List<Object[]> getSiteMapByWebSite(String websiteid) throws Exception {
        String hql="select a.id,a.articleTitle from Article a  where a.websiteid=? order by a.articleUpdatetime desc";
        return (List<Object[]>) queryForListByHql(hql, true,false, websiteid);
    }

    @Override
    public Map<String, Object> getEveryDayArticleInfo(String websiteid, int year, int month)
            throws Exception {
       String sql="select date_format(article_pubtime,'%Y-%m-%d') times,count(*) total from blog_article where YEAR(article_pubtime)=:year and MONTH(article_pubtime)=:month and websiteid=:website  group by times";
       Map<String, Object> map = new HashMap<String, Object>();
       map.put("year", year);
       map.put("month", month);
       map.put("website", websiteid);
        return  (Map<String, Object>) queryForSingleMap(sql, map, "times", "total");
    }

    @Override
    public Pagination getFullTextSearch(int page, int limit, String website,Boolean isback,
            String keyword, String... fields) throws Exception {
        Pagination p = new Pagination();
        QueryBuilder qb = getFullTextSession().getSearchFactory()
        .buildQueryBuilder().forEntity(Article.class).get();
        org.apache.lucene.search.Query query = null;
        if (isback){
            query=qb.bool().must(
                    qb.keyword().onField("websiteid").matching(website).createQuery()
            ).must(
                    qb.keyword().onFields(fields).matching(keyword).createQuery()
            ).createQuery();
        }else if (null!=website) {
        	query= qb.bool().must(
        	            qb.keyword().onField("websiteid").matching(website).createQuery()
        	         ).must(
				             qb.keyword().onField("articlePrivate").matching("false").createQuery()
					   ).must(
        	             qb.keyword().onFields(fields).matching(keyword).createQuery()
        	         ).createQuery();
		}
        else{
			query=qb.bool().must(
			             qb.keyword().onFields(fields).matching(keyword).createQuery()
			         ).must(
				             qb.keyword().onField("articlePrivate").matching("false").createQuery()
					   ).createQuery();
		}
       /* org.apache.lucene.search.Query query = qb
        .keyword().onFields(fields).matching(keyword)
        .createQuery();*/
        // wrap Lucene query in a org.hibernate.Query
        FullTextQuery fullTextQuery = getFullTextSession().createFullTextQuery(query, Article.class);
        fullTextQuery.setCacheable(true);
        fullTextQuery.setFirstResult((page-1)*limit);
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
               summary=article.getArticleText().getArticleSummary();
              if (null==summary||summary.isEmpty()) {
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
             //类型转换
             Category category=article.getCategory();
             CategoryVo categoryVo= new CategoryVo();
             BeanUtils.copyProperties(category, categoryVo);
             vo.setCategory(categoryVo);
             
             //用户信息
             UserVo temp_user=new UserVo();
             BeanUtils.copyProperties(article.getUser(), temp_user);
             vo.setUser(temp_user);
             list.add(vo);
          }
        //builder Pagination  对象
        p.setData(list);
        p.setLimit(limit);
        p.setPageindex(page);
        Integer total=fullTextQuery.getResultSize();
        p.setTotal(total);
        p.setMaxPage((total-1)/limit+1);
        return p;
    }

    @Override
    public int delArticles(String[] ids) throws Exception {
        String del_article="delete from Article where id in(:ids)";
        String del_lable="delete from Lable l where l.article.id in(:ids)";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("ids", ids);
        executeHQL(del_lable, map);
       return executeHQL(del_article, map);
    }

    @Override
    public int updateArticleType(String[] ids, String typeid) throws Exception {
        String hql ="update from Article set category.id=:category where id in(:ids)";
        Map<String, Object> map = new HashMap<String, Object>();
        return executeHQL(hql, map);
    }

    @Override
    public Article getArticleByID(String id, String website) throws Exception {
        String hql="from Article a where a.id=? and a.websiteid=?";
        List<Article> lis = (List<Article>) queryForListByHql(hql,false,false, id,website);
        return null!=lis&&lis.size()>0?lis.get(0):null;
    }

	@Override
	public int updateArticlesType(String[] ids, String category,String websiteid)throws Exception {
		String hql=" update Article a set a.category.id=:category where a.id in(:ids) and a.websiteid=:websiteid";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ids", ids);
		map.put("websiteid", websiteid);
		map.put("category", category);
		return executeHQL(hql, map);
	}

	@Override
	public Pagination getHomeFullTextSearch(int page,
			int limit, String keyword, String... fields) throws Exception {
		return getFullTextSearch(page, limit, null,false,keyword, fields);
	}

	@Override
	public Pagination getCommentsByArticle(int page,
			int limit, String articleId, Map<String, Object> args)
			throws Exception {
		HashMap<String, Object> params= new HashMap<String, Object>();
		StringBuffer hql =new StringBuffer(" from ArticleComment a left  join a.CUser u  left  join a.CTosomeone u1 where a.CIsdel='√' ");
		//指定文章
		if (null!=articleId&&!articleId.isEmpty()) {
			hql.append(" and a.article.id=:articleId ");
			params.put("articleId", articleId);
		}
		if (null!=args) {		
			//指定用户
			if (args.containsKey("CUserId")) {
				hql.append(" and a.CUser.id=:CUserId");
				params.put("CUserId", args.get("CUserId"));
			}
			if (args.containsKey("website")) {
				hql.append(" and a.CWebsiteId=:website");
				params.put("website", args.get("website"));
			}
		}
		hql.append(" order by a.CDatetime desc");
		Pagination p = PaginationsByHQLMapParams(hql.toString(), page, limit, true, params);
		List<Object[]> rows = (List<Object[]>) p.getData();
		if (null!=rows&&rows.size()>0) {
			List<ArticleCommentVo> lis = new ArrayList<ArticleCommentVo>();
			for (Object[] objects : rows) {
				ArticleComment comment=(ArticleComment) objects[0];
				User user=(User) objects[1];
				User touser=(User) objects[2];
				
				ArticleCommentVo acv =new ArticleCommentVo();
				ArticleVo av = new ArticleVo();
				UserVo u1 = new UserVo();
				UserVo u2 = new UserVo();
				BeanUtils.copyProperties(comment, acv);
				if (null!=user) {
					BeanUtils.copyProperties(user, u1);
				}
				if (null!=touser) {
					BeanUtils.copyProperties(touser, u2);
				}
				if (null!=comment.getArticle()) {
					BeanUtils.copyProperties(comment.getArticle(), av);
				}
				acv.setCUser(u1);
				acv.setCTosomeone(u2);
				acv.setArticle(av);
				lis.add(acv);
			}
			p.setData(lis);
		}
		return p;
	}

	@Override
	public int delArticleComments(String[] ids) throws Exception {
		String hql="update ArticleComment set CIsdel='X'  where id in (:ids)";
		 Map<String, Object> map = new HashMap<String, Object>();
		 map.put("ids", ids);
		return executeHQL(hql, map);
	}

	@Override
	public int fixedTop(String[] ids, String websiteid) throws Exception {
		String hql="update Article set articleSort=:sort where id in (:ids) and websiteid =:website";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ids", ids);
		map.put("website", websiteid);
		Integer i =(int) new UnixDate().getCurrentDate();
		map.put("sort", i);
		return executeHQL(hql, map);
	}

	@Override
	public int cancleFixed(String[] ids, String websiteid) throws Exception {
		String hql="update Article set articleSort=0 where id in (:ids) and websiteid =:website";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ids", ids);
		map.put("website", websiteid);
		return executeHQL(hql, map);
	}

	@Override
	public int setCover(String[] ids, String websiteid, String resKey)
			throws Exception {
		String hql="update Article set articleCover=:reskey where id in (:ids) and websiteid =:website";
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("ids", ids);
		map.put("website", websiteid);
		String key =null==resKey?"":resKey;
		map.put("reskey",key.trim());
		return executeHQL(hql, map);
	}

	@Override
	public Pagination articleArchive(int page,int limit, String website) throws Exception {
		return null;
	}

    @Override
    public int getArticleDateQuantity(String websiteId, String dateString) throws Exception {
        Integer year=Integer.valueOf(dateString.substring(0, 4)) ;
        Integer month=Integer.valueOf(dateString.substring(4,6));
        ArrayList<Object> args = new ArrayList<Object>();
        args.add(websiteId);
        args.add(year);
        args.add(month);
        StringBuffer hql = new StringBuffer("select count(1) from Article a where a.websiteid =?  and YEAR(a.articlePubtime)=? and MONTH(a.articlePubtime)=?");
        if(dateString.length()==8){
            Integer day=Integer.valueOf(dateString.substring(6));
            args.add(day);
            hql.append(" and DAY(a.articlePubtime)=?");
        }
        Object val = uniqueQueryByHql(hql.toString(),true,args.toArray(new Object[1]));
        return Integer.valueOf(val.toString());
    }

    @Override
	public int delSelfComments(String id, String userid) throws Exception {
		String hql="update ArticleComment set CIsdel='X'  where id = :id and CUser.id=:userid";
		 Map<String, Object> map = new HashMap<String, Object>();
		 map.put("id", id);
		 map.put("userid", userid);
		return executeHQL(hql, map);
	}

	

    

}
