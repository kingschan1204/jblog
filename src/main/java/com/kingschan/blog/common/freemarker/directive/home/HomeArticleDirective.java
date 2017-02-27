package com.kingschan.blog.common.freemarker.directive.home;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kingschan.blog.model.vo.ArticleVo;
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
*  <pre>    
* 类名称：CategoryDirective 
* 类描述：   
* 创建人：陈国祥   (kingschan)
* 创建时间：2016-2-20 上午9:28:48   
* 修改人：Administrator   
* 修改时间：2016-2-20 上午9:28:48   
* 修改备注：   
* @version V1.0
* </pre>
 */
@Component("HomeArticle")
public class HomeArticleDirective implements TemplateDirectiveModel {

    
    
    @Autowired
    private ArticleService article_serv;
	@SuppressWarnings("unchecked")
	@Override
    public void execute(Environment env, @SuppressWarnings("rawtypes") Map params, TemplateModel[] tm, TemplateDirectiveBody body)
            throws TemplateException, IOException {
        List<ArticleVo> lis=null;
        try {
            int page =params.containsKey("page")&&params.get("page").toString().matches("\\d+")?Integer.valueOf(params.get("page").toString()):1;
            int limit =params.containsKey("limit")&&params.get("limit").toString().matches("\\d+")?Integer.valueOf(params.get("limit").toString()):10;
            String category=params.containsKey("category")?params.get("category").toString():"";//类型
            String fulltext=params.containsKey("fulltext")?params.get("fulltext").toString():"";//全文检索
            
            
            Map<String, Object> map = new HashMap<String, Object>();
            Pagination p=null;
            if (!category.isEmpty()) {
                map.put("category", params.get("category").toString());
            } if (!fulltext.isEmpty()) {
                //全文检索
                p=article_serv.getHomeFullTextSearch(page, limit, fulltext, "articleTitle","articleText.articleContent","id");
            }else{
                p=article_serv.getHomeArticleList(page,limit, map);
            }
            if (fulltext.isEmpty()) {
            	lis=(List<ArticleVo>) p.getData();
                String summary=null;
                for (ArticleVo article : lis) {
                    if (null==article.getArticleSummary()||article.getArticleSummary().isEmpty()) {
                        summary=Jsoup.parse(article.getArticleContent()).text();
                        summary=summary.length()>300? summary.substring(0,300):summary;
                    }else {
                        summary=Jsoup.parse(article.getArticleSummary()).text();
                    }
                    article.setArticleSummary(summary);
                }
                p.setData(lis);
            }
            env.setVariable("article_lis", ObjectWrapper.DEFAULT_WRAPPER.wrap(p.getData()));
            env.setVariable("page", ObjectWrapper.DEFAULT_WRAPPER.wrap(p));
            body.render(env.getOut());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   
}
