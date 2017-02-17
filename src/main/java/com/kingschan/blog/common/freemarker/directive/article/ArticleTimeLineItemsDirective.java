package com.kingschan.blog.common.freemarker.directive.article;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kingschan.blog.dao.Pagination;
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
* 类名称：文章时间轴 
* 类描述：   
* 创建人：陈国祥   (kingschan)
* 创建时间：2016-2-20 下午3:20:55   
* 修改人：Administrator   
* 修改时间：2016-2-20 下午3:20:55   
* 修改备注：   
* @version V1.0
* </pre>
 */
@Component("ArticleTimeLineItems")
public class ArticleTimeLineItemsDirective implements TemplateDirectiveModel {
    
    @Autowired
    private ArticleServiceImpl articleServ;
    @Override
    public void execute(Environment env, @SuppressWarnings("rawtypes") Map params, TemplateModel[] tm, TemplateDirectiveBody body)
            throws TemplateException, IOException {
        //如果limit ==0就是查出所有
        int limit =params.containsKey("limit")&&params.get("limit").toString().matches("\\d+")?Integer.valueOf(params.get("limit").toString()):10;
        int page =params.containsKey("page")&&params.get("page").toString().matches("\\d+")?Integer.valueOf(params.get("page").toString()):1;
        String websiteid=params.get("website_id").toString();
        String date=params.containsKey("date")&&params.get("date").toString().matches("\\d{4}-\\d{2}")?params.get("date").toString():null;
//        String orderby=params.containsKey("orderby")?params.get("orderby").toString():null;//标签
        //lableName
        try {
        	
        	Pagination p =articleServ.getArticleTimeLine(websiteid, page, limit,date);
        	@SuppressWarnings("unchecked")
			List<Map<String, Object>> lis = (List<Map<String, Object>>) p.getData();
        	TreeMap<String, List<Map<String, Object>>> tmap =new TreeMap<String, List<Map<String,Object>>>( new Comparator<String>() {

				@Override
				public int compare(String o1, String o2) {
					return o2.compareTo(o1);  
				}
			});
        	String current=null;
        	for (Map<String, Object> map : lis) {
				String key =map.get("months").toString().trim();
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
