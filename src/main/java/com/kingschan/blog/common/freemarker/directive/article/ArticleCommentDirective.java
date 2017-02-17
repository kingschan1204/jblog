package com.kingschan.blog.common.freemarker.directive.article;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kingschan.blog.dao.Pagination;
import com.kingschan.blog.services.ArticleService;

import freemarker.core.Environment;
import freemarker.template.ObjectWrapper;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
/**
 * 
*    
* 类名称：ArticleCommentDirective   
* 类描述：   文章评论
* 创建人：kings.chan
* 创建时间：2016-7-13 上午9:26:01   
* 修改人：   
* 修改时间：
* 项目：ROOT
* 修改备注：   
* @version    
*
 */
@Component("ArticleComment")
public class ArticleCommentDirective implements TemplateDirectiveModel {

    private static Logger log =LoggerFactory.getLogger(ArticleCommentDirective.class);
    
    @Autowired
    private ArticleService article_serv;
	@Override
    public void execute(Environment env, @SuppressWarnings("rawtypes") Map params, TemplateModel[] tm, TemplateDirectiveBody body)
            throws TemplateException, IOException {
            int page =params.containsKey("page")&&params.get("page").toString().matches("\\d+")?Integer.valueOf(params.get("page").toString()):1;
            int limit =params.containsKey("limit")&&params.get("limit").toString().matches("\\d+")?Integer.valueOf(params.get("limit").toString()):10;
            String articleId=params.containsKey("articleId")?params.get("articleId").toString():"";//文章
            String website=params.containsKey("website")?params.get("website").toString():"";//网站
            String exists_support_user=params.containsKey("exists_support_user")?params.get("exists_support_user").toString():"";//当前登录用户
            
            HashMap<String, Object> map= new HashMap<String, Object>();
            if (!website.isEmpty()) {
            	map.put("website", website);
			}
            if (!exists_support_user.isEmpty()) {
            	map.put("exists_support_user", exists_support_user); 
			}
            Pagination p=null;
            try {
            	p=article_serv.getCommentsByArticle(page, limit, articleId, map);
            	if (null!=p) {
            		env.setVariable("article_comment_lis", ObjectWrapper.DEFAULT_WRAPPER.wrap(p.getData()));
                    env.setVariable("page", ObjectWrapper.DEFAULT_WRAPPER.wrap(p));
                    body.render(env.getOut()); 
				}
			} catch (Exception e) {
				e.printStackTrace();
				log.error("ArticleCommentDirective",e);
			}
          
            
       
    }

   
}
