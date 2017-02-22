package com.kingschan.blog.services.system.impl;

import com.kingschan.blog.dao.ArticleDao;
import com.kingschan.blog.dao.CategoryDao;
import com.kingschan.blog.dao.LableDao;
import com.kingschan.blog.po.Category;
import com.kingschan.blog.services.system.UrlValidationService;
import com.kingschan.blog.util.BlogUrlHelper;
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
    @Qualifier("LableDaoImpl")
    @Autowired
    private LableDao labDao;
    @Qualifier("ArticleDaoImpl")
    @Autowired
    private ArticleDao aritcleDao;
    @Override
    public boolean validateCategory(String websiteId, String urlPath) throws Exception {
        String category= BlogUrlHelper.getLastSlashData(urlPath);
        Category cg =categoryDao.getCategoryByKeyword(websiteId, category);
        return null!=cg;
    }

    @Override
    public boolean validateLable(String websiteId, String urlPath) throws Exception {
        String lab= BlogUrlHelper.getLastSlashData(urlPath);
        int quantity=labDao.getLableQuantityByName(websiteId,lab);
        return quantity>0;
    }

    @Override
    public boolean validateDate(String websiteId, String urlPath) throws Exception {
        String date= BlogUrlHelper.getLastSlashData(urlPath);
        return aritcleDao.getArticleDateQuantity(websiteId,date)>0;
    }
}
