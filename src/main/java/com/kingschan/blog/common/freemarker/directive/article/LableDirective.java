package com.kingschan.blog.common.freemarker.directive.article;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kingschan.blog.services.LableService;

import freemarker.core.Environment;
import freemarker.template.ObjectWrapper;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
/**
 * 
*  <pre>    
* 类名称：LableDirective 
* 类描述：   
* 创建人：陈国祥   (kingschan)
* 创建时间：2016-2-20 下午3:20:55   
* 修改人：Administrator   
* 修改时间：2016-2-20 下午3:20:55   
* 修改备注：   
* @version V1.0
* </pre>
 */
@Component("Lable")
public class LableDirective implements TemplateDirectiveModel {

    
    
    @Autowired
    private LableService lable_serv;
    @Override
    public void execute(Environment env, @SuppressWarnings("rawtypes") Map params, TemplateModel[] tm, TemplateDirectiveBody body)
            throws TemplateException, IOException {
        //如果limit ==0就是查出所有
        int limit =params.containsKey("limit")&&params.get("limit").toString().matches("\\d+")?Integer.valueOf(params.get("limit").toString()):10;
        String orderby=params.containsKey("orderby")?params.get("orderby").toString():null;//标签
        //lableName
        try {
            List<Map<String, Object>> lis=lable_serv.getHotLableList(params.get("website_id").toString(), limit,orderby);
            env.setVariable("lable_lis", ObjectWrapper.DEFAULT_WRAPPER.wrap(lis));
            body.render(env.getOut());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   
}
