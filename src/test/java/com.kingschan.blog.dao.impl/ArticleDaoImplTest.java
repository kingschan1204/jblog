package com.kingschan.blog.dao.impl;

import com.kingschan.blog.services.system.impl.EmailNotifyServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractTransactionalJUnit4SpringContextTests;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by kingschan on 2017/2/17.
 */
@ContextConfiguration(locations =
        {
                "classpath:/applicationContext.xml",
                "classpath:/applicationContext-db.xml",
                "classpath:/applicationContext-cache.xml",
                "classpath:/applicationContext-bean.xml",
                "classpath:/applicationContext-mvc.xml"
        })
public class ArticleDaoImplTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Qualifier("ArticleDaoImpl")
    @Autowired
    private ArticleDaoImpl articleDao;

    @Transactional
    @Test
    public void getArticleDateQuantityTest() throws Exception {
       int val= articleDao.getArticleDateQuantity("3c2ec7b28db9416cafc488660c603bb4","201602");
        System.out.println(val);
    }
}
