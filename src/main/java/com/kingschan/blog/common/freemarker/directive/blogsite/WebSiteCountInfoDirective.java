package com.kingschan.blog.common.freemarker.directive.blogsite;

import java.io.IOException;
import java.util.Map;

import com.kingschan.blog.model.vo.BlogSiteStatisticalVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.kingschan.blog.services.ReportService;
import freemarker.core.Environment;
import freemarker.template.ObjectWrapper;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
/**
 * 
 * @author kingschan
 *博客信息统计汇总
 */
@Component("WebSiteCountInfo")
public class WebSiteCountInfoDirective implements TemplateDirectiveModel {

    
    
    @Autowired
    private ReportService reportServ;
    @Override
    public void execute(Environment env, @SuppressWarnings("rawtypes") Map params, TemplateModel[] tm, TemplateDirectiveBody body)
            throws TemplateException, IOException {
        String siteid=params.containsKey("siteid")?params.get("siteid").toString():null;//标签
        //lableName
        try {
            BlogSiteStatisticalVo vo =reportServ.websiteCountInfo(siteid);
            env.setVariable("WebSiteCountInfoVo", ObjectWrapper.DEFAULT_WRAPPER.wrap(vo));
            body.render(env.getOut());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

   
}
