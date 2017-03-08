package com.kingschan.blog.common.freemarker.directive.article;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.kingschan.blog.model.vo.ArticleVo;
import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kingschan.blog.dao.Pagination;
import com.kingschan.blog.po.Article;
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
@Component("Article")
public class ArticleDirective implements TemplateDirectiveModel {

    
    
    @Autowired
    private ArticleService article_serv;
    @SuppressWarnings("unchecked")
	@Override
    public void execute(Environment env, @SuppressWarnings("rawtypes") Map params, TemplateModel[] tm, TemplateDirectiveBody body) throws TemplateException, IOException {
        List<ArticleVo> lis=null;
        try {
            int page =params.containsKey("page")&&params.get("page").toString().matches("\\d+")?Integer.valueOf(params.get("page").toString()):1;
            int limit =params.containsKey("limit")&&params.get("limit").toString().matches("\\d+")?Integer.valueOf(params.get("limit").toString()):10;
            String date=params.containsKey("date")?params.get("date").toString():"";//日期
            String tag=params.containsKey("tag")?params.get("tag").toString():"";//标签
            String category=params.containsKey("category")?params.get("category").toString():"";//类型
            String categoryId=params.containsKey("categoryId")?params.get("categoryId").toString():"";//类型
            String fulltext=params.containsKey("fulltext")?params.get("fulltext").toString():"";//全文检索
            String title=params.containsKey("title")?params.get("title").toString():"";//文章标题
            String website_id=params.containsKey("website_id")?params.get("website_id").toString():"";//网站
            String orderby=params.containsKey("orderby")?params.get("orderby").toString():"";//排序
            String model=params.containsKey("model")?params.get("model").toString():"";// font | back 前端还是后台
            
            
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("model", model);
            Pagination p=null;
            if (!category.isEmpty()) {
                map.put("category", category);
            }else if (!categoryId.isEmpty()) {
            	map.put("categoryId", categoryId);
			}else if (date.matches("\\d+")) {
                    map.put("year", Integer.valueOf(date.substring(0, 4)));
                    map.put("month", Integer.valueOf(date.substring(4,6)));
                if(date.length()==8){
                    map.put("day", Integer.valueOf(date.substring(6)));
                }
            }
            if (!title.isEmpty()) {
                map.put("title", params.get("title").toString());
            }
            if (!orderby.isEmpty()) {
                map.put("orderby", orderby);
            }
            
            if (!tag.isEmpty()) {
                //通过标签名取文章
                p=article_serv.getArticleByLable(params.get("tag").toString(),website_id, page,limit);
            }else if (!fulltext.isEmpty()) {
                //全文检索
                p=article_serv.getFullTextSearch(page, limit, website_id,model.equals("back"), fulltext, "articleTitle","articleText.articleContent","id");
            }else{
                p=article_serv.getArticleList(page,limit, website_id, map);
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
            }
            env.setVariable("article_lis", ObjectWrapper.DEFAULT_WRAPPER.wrap(fulltext.isEmpty()?lis:p.getData()));
            env.setVariable("page", ObjectWrapper.DEFAULT_WRAPPER.wrap(p));
            body.render(env.getOut());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   
}
