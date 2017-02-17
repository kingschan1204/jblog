package com.kingschan.blog.common.freemarker.directive.article;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kingschan.blog.dao.Pagination;
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
@Component("LableArticleItem")
public class LableArticleItemsDirective implements TemplateDirectiveModel {

    
    
    @Autowired
    private LableService lable_serv;
    @Override
    public void execute(Environment env, @SuppressWarnings("rawtypes") Map params, TemplateModel[] tm, TemplateDirectiveBody body)
            throws TemplateException, IOException {
        //如果limit ==0就是查出所有
        int limit =params.containsKey("limit")&&params.get("limit").toString().matches("\\d+")?Integer.valueOf(params.get("limit").toString()):10;
        int page =params.containsKey("page")&&params.get("page").toString().matches("\\d+")?Integer.valueOf(params.get("page").toString()):1;
        String labName=params.containsKey("labName")?
        		(params.get("labName").toString().trim().isEmpty()?null:params.get("labName").toString())
        		:null;//标签
        //lableName
        try {
        	Pagination p =lable_serv.getLableArticleItemList(labName,params.get("website_id").toString(), limit, page, null);
        	@SuppressWarnings("unchecked")
			List<Map<String, Object>> lis = (List<Map<String, Object>>) p.getData();
        	TreeMap<String, List<Map<String, Object>>> tmap =new TreeMap<String, List<Map<String,Object>>>();
        	String current=null;
        	for (Map<String, Object> map : lis) {
				String key =map.get("lable_name").toString().trim();
				List<Map<String, Object>> tlis=null;
				if (null==current||!current.equals(key)) {
					//第一次
					tlis= new ArrayList<Map<String,Object>>();
					tlis.add(map);
					tmap.put(key, tlis);
					current=key;
				}else if (current.equals(key)) {
					//相同key
					tlis=tmap.get(key);
					tlis.add(map);
					tmap.put(key, tlis);
					current=key;
				}
			}
        	
        	env.setVariable("article_items", ObjectWrapper.DEFAULT_WRAPPER.wrap(tmap));
            env.setVariable("page", ObjectWrapper.DEFAULT_WRAPPER.wrap(p));
            body.render(env.getOut());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   
}
