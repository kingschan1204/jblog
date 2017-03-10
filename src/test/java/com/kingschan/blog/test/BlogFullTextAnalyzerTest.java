package com.kingschan.blog.test;

import com.hankcs.hanlp.HanLP;
import com.hankcs.lucene.HanLPIndexAnalyzer;
import com.kingschan.blog.dao.HibernateBaseDao;
import com.kingschan.blog.dao.Pagination;
import com.kingschan.blog.dao.impl.ArticleDaoImpl;
import com.kingschan.blog.model.vo.ArticleVo;
import com.kingschan.blog.model.vo.CategoryVo;
import com.kingschan.blog.model.vo.UserVo;
import com.kingschan.blog.po.Article;
import com.kingschan.blog.po.ArticleText;
import com.kingschan.blog.po.Category;
import com.kingschan.blog.util.TimeStampUtil;
import org.apache.lucene.search.highlight.*;
import org.hibernate.search.FullTextQuery;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.jsoup.Jsoup;
import org.junit.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kingschan on 2017/2/24.
 */
@ContextConfiguration(locations =
        {
                "classpath:/applicationContext.xml",
                "classpath:/applicationContext-db.xml",
                "classpath:/applicationContext-cache.xml",
                "classpath:/applicationContext-bean.xml",
                "classpath:/applicationContext-mvc.xml"
        })
public class BlogFullTextAnalyzerTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Qualifier("ArticleDaoImpl")
    @Autowired
    private ArticleDaoImpl articleDao;

    @Qualifier("HibernateBaseDao")
    @Autowired
    private HibernateBaseDao baseDao;

    @Transactional
//    @Test
    public void getArticleDateQuantityTest() throws Exception {
//        int val= articleDao.getArticleDateQuantity("3c2ec7b28db9416cafc488660c603bb4","201602");
//        System.out.println(val);

        Pagination page = articleDao.getFullTextSearch(1, 10, "3c2ec7b28db9416cafc488660c603bb4",true, "java对数据库事务进行处理", "articleTitle", "articleText.articleContent");
        List<ArticleVo> lis = (List<ArticleVo>) page.getData();
        for (ArticleVo a : lis) {
            System.out.println(a.getArticleTitle());
            String text = Jsoup.parse(a.getArticleTitle() + a.getArticleContent()).text();
            //关键字提取
            List<String> keywordList = HanLP.extractKeyword(text, 3);
            System.out.println(keywordList);
            //自动摘要
            List<String> sentenceList = HanLP.extractSummary(text, 3);
            System.out.println(sentenceList);
            System.out.println("---------------------------------------------------");
        }
    /*List<ArticleText> lis=(List<ArticleText>) baseDao.PaginationByHql(" from ArticleText",1,10,false);
        for (ArticleText text:lis
             ) {
            System.out.println(text.getArticleSummary());
        }
    }*/
    }

}
