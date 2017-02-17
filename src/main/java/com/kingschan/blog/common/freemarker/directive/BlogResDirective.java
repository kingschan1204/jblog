package com.kingschan.blog.common.freemarker.directive;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kingschan.blog.dao.Pagination;
import com.kingschan.blog.services.BlogResService;

import freemarker.core.Environment;
import freemarker.template.ObjectWrapper;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
/**
 * 
*  <pre>    
* 类名称：BlogResDirective 
* 类描述：  资源管理标签
* 创建人：陈国祥   (kingschan)
* 创建时间：2016-2-20 上午9:28:48   
* 修改人：kingschan   
* 修改时间：2016-2-20 上午9:28:48   
* 修改备注：   
* @version V1.0
* </pre>
 */
@Component("BlogRes")
public class BlogResDirective implements TemplateDirectiveModel {

    
    
    @Autowired
    private BlogResService blogres_serv;
    @Override
    public void execute(Environment env, @SuppressWarnings("rawtypes") Map params, TemplateModel[] tm, TemplateDirectiveBody body)
            throws TemplateException, IOException {
        int page =params.containsKey("page")&&params.get("page").toString().matches("\\d+")?Integer.valueOf(params.get("page").toString()):1;
        int limit =params.containsKey("limit")&&params.get("limit").toString().matches("\\d+")?Integer.valueOf(params.get("limit").toString()):10;
        String websiteid=params.containsKey("websiteid")?params.get("websiteid").toString():"";//网站
        String resname=params.containsKey("resname")?params.get("resname").toString():"";//名称
        Map<String, Object> map = new HashMap<String, Object>();
        Pagination p=null;
        map.put("websiteid", websiteid);
        if (!resname.isEmpty()) {
			map.put("resname", resname.concat("%"));
			map.put("resKey", resname);
			
		}
        try {
            p=blogres_serv.getResList(page, limit, map);
            env.setVariable("blogres_lis", ObjectWrapper.DEFAULT_WRAPPER.wrap(p.getData()));
            env.setVariable("page", ObjectWrapper.DEFAULT_WRAPPER.wrap(p));
            body.render(env.getOut());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   
}
