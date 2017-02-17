package com.kingschan.blog.common.freemarker.directive.article;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kingschan.blog.services.impl.ArticleServiceImpl;

import freemarker.core.Environment;
import freemarker.template.ObjectWrapper;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
/**
 * 
*  <pre>    
* 类名称：文章时间轴 导航
* 类描述：   
* 创建人：陈国祥   (kingschan)
* 创建时间：2016-2-20 下午3:20:55   
* 修改人：Administrator   
* 修改时间：2016-2-20 下午3:20:55   
* 修改备注：   
* @version V1.0
* </pre>
 */
@Component("ArticleTimeLineNavigate")
public class ArticleTimeLineNavigateDirective implements TemplateDirectiveModel {
    
    @Autowired
    private ArticleServiceImpl articleServ;
    @Override
    public void execute(Environment env, @SuppressWarnings("rawtypes") Map params, TemplateModel[] tm, TemplateDirectiveBody body)
            throws TemplateException, IOException {
        String websiteid=params.get("website_id").toString();
        try {
			List<Map<String, Object>> lis = articleServ.getArticleTimeNavigate(websiteid);
        	env.setVariable("article_timeline_nav", ObjectWrapper.DEFAULT_WRAPPER.wrap(lis));
            body.render(env.getOut());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   
}
