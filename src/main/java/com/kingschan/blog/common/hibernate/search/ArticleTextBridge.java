package com.kingschan.blog.common.hibernate.search;

import com.kingschan.blog.po.ArticleText;
import org.hibernate.search.bridge.StringBridge;

/**
 * Created by kingschan on 2017/2/27.
 */
public class ArticleTextBridge implements StringBridge {

    @Override
    public String objectToString(Object o) {
        ArticleText at = (ArticleText)o;
        return at.getArticleContent();
    }
}
