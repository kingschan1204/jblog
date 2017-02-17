package com.kingschan.blog.common.freemarker.directive.bookmark;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kingschan.blog.dao.Pagination;
import com.kingschan.blog.services.BookMarkService;

import freemarker.core.Environment;
import freemarker.template.ObjectWrapper;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 
*  <pre>    
* 类名称：BookMarkDirective 
* 类描述：   书签
* 创建人：陈国祥   (kingschan)
* 创建时间：2016-3-14 下午3:42:40   
* 修改人：Administrator   
* 修改时间：2016-3-14 下午3:42:40   
* 修改备注：   
* @version V1.0
* </pre>
 */
@Component("BookMark")
public class BookMarkDirective implements TemplateDirectiveModel{

    @Autowired
    private BookMarkService bookmark_serv;
    @Override
    public void execute(Environment env, @SuppressWarnings("rawtypes") Map params, TemplateModel[] tm, TemplateDirectiveBody body)
            throws TemplateException, IOException {
        int page =params.containsKey("page")&&params.get("page").toString().matches("\\d+")?Integer.valueOf(params.get("page").toString()):1;
        int limit =params.containsKey("limit")&&params.get("limit").toString().matches("\\d+")?Integer.valueOf(params.get("limit").toString()):10;
        String title=params.containsKey("title")?params.get("title").toString():"";//标题
        Integer folder=params.containsKey("folder")&&params.get("folder").toString().matches("\\d+")?Integer.valueOf(params.get("folder").toString()):0;//类型
        String website_id=params.containsKey("website_id")?params.get("website_id").toString():"";//网站
        String disablepage=params.containsKey("disablepage")?params.get("disablepage").toString():"";//是否设置分布信息
        String orderby=params.containsKey("orderby")?params.get("orderby").toString():"";//排序
        Map<String, Object> map =new HashMap<String, Object>();
        if (!title.isEmpty()) {
            map.put("title", "%"+title+"%");
        }
        if (folder!=0) {
            map.put("folder", folder);
        }
        if (!orderby.isEmpty()) {
            map.put("orderby", orderby);
        }
        map.put("websiteid", website_id);
        Pagination p=null;
        try {
           p= bookmark_serv.getBookMarks(website_id, map, limit, page);
            env.setVariable("bookmark_lis", ObjectWrapper.DEFAULT_WRAPPER.wrap(p.getData()));
            //是否设置分布信息
            if (disablepage.isEmpty()) {
            	env.setVariable("page", ObjectWrapper.DEFAULT_WRAPPER.wrap(p));
			}
            body.render(env.getOut());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
