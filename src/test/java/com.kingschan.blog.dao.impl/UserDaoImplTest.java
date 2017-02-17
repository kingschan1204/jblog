package com.kingschan.blog.dao.impl;

import com.kingschan.blog.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.test.context.ContextConfiguration;
import org.junit.Test;
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
public class UserDaoImplTest {

    @Qualifier("UserDaoImpl")
    @Autowired
    private UserDaoImpl userDao;


    @Transactional
    @Test
    public void getUsersByUserNamesTest() throws Exception {
       List<User> lis= userDao.getUsersByUserNames(new String[]{"kingschan","chenyc"});
        for (User u : lis) {
            System.out.println(u.getUserName());
        }
    }
}
