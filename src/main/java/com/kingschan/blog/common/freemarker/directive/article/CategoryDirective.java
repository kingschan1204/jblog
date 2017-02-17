package com.kingschan.blog.common.freemarker.directive.article;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kingschan.blog.po.Category;
import com.kingschan.blog.services.CategoryService;

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
@Component("Category")
public class CategoryDirective implements TemplateDirectiveModel {

    
    
    @Autowired
    private CategoryService category_serv;
    @Override
    public void execute(Environment env, @SuppressWarnings("rawtypes") Map params, TemplateModel[] tm, TemplateDirectiveBody body)
            throws TemplateException, IOException {
        String website_id=params.containsKey("website_id")?params.get("website_id").toString():"";//网站
        try {
            List<Category> lis= category_serv.getCategoryList(website_id);
            env.setVariable("category_lis", ObjectWrapper.DEFAULT_WRAPPER.wrap(lis));
            body.render(env.getOut());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
