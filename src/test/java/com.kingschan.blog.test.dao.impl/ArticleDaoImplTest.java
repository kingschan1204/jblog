package com.kingschan.blog.dao.impl;

import com.kingschan.blog.dao.HibernateBaseDao;
import com.kingschan.blog.po.Article;
import com.kingschan.blog.po.ArticleText;
import com.kingschan.blog.po.Lable;
import com.kingschan.blog.services.impl.ArticleServiceImpl;
import com.kingschan.blog.services.system.impl.EmailNotifyServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by kingschan on 2017/2/17.
 */
@ContextConfiguration(locations =
        {
                "classpath:/applicationContext.xml",
                "classpath:/applicationContext-db.xml",
                "classpath:/applicationContext-cache.xml",
                "classpath:/applicationContext-bean.xml",
                "classpath:/applicationContext-mvc.xml",
                "classpath:/applicationContext-quartz.xml"
        })
public class ArticleDaoImplTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Qualifier("ArticleDaoImpl")
    @Autowired
    private ArticleDaoImpl articleDao;
    @Autowired
    private ArticleServiceImpl articleServ;

    @Qualifier("HibernateBaseDao")
    @Autowired
    private HibernateBaseDao baseDao;

    public void getArticleDateQuantityTest() throws Exception {
       int val= articleDao.getArticleDateQuantity("3c2ec7b28db9416cafc488660c603bb4","201602");
        System.out.println(val);
    }
    @Transactional
    @Test
    public void builderIndexTest() throws Exception {
    //        articleServ.buildIndex();
        String hql =" from Lable a inner join a.article inner join a.article.articleText where a.webSite.id=? and a.lableName=?";
        List<Object[]> list =( List<Object[]>)baseDao.queryForListByHql(hql,false,false,"3c2ec7b28db9416cafc488660c603bb4","java");
        for (Object[] row: list){
            Lable lab=(Lable)row[0];
            Article a=(Article)row[1];
            ArticleText at = (ArticleText)row[2];
            System.out.println(a.getArticleTitle());
            System.out.println(at.getArticleSummary());
        }
    }
}
