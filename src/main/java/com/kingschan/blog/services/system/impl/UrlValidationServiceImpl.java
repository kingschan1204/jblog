package com.kingschan.blog.services.system.impl;

import com.kingschan.blog.dao.CategoryDao;
import com.kingschan.blog.po.Category;
import com.kingschan.blog.services.system.UrlValidationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.net.URLDecoder;

/**
 * Created by kingschan on 2017/2/21.
 */
@Service
public class UrlValidationServiceImpl implements UrlValidationService {
    @Qualifier("CategoryDaoImpl")
    @Autowired
    private CategoryDao categoryDao;

    @Override
    public boolean validateCategory(String websiteId, String urlPath) throws Exception {
        String[] s=urlPath.split("/");
        String category= URLDecoder.decode(s[s.length-1], "utf-8");
        Category cg =categoryDao.getCategoryByKeyword(websiteId, category);
        return null!=cg;
    }
}
