package com.kingschan.blog.dao.impl;

import com.kingschan.blog.po.User;
import com.kingschan.blog.services.system.EmailNotifyService;
import com.kingschan.blog.services.system.impl.EmailNotifyServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.junit.Test;
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
                "classpath:/applicationContext-mvc.xml"
        })
public class UserDaoImplTest extends AbstractTransactionalJUnit4SpringContextTests {

    @Qualifier("UserDaoImpl")
    @Autowired
    private UserDaoImpl userDao;

    @Autowired
    private EmailNotifyServiceImpl mailServ;

    @Transactional
    @Test
    public void getUsersByUserNamesTest() throws Exception {
      mailServ.sendEmailToUsersByText("@kingschan@root","title","this is a test !");
    }
}
