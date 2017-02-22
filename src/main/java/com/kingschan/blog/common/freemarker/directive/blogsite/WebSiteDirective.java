package com.kingschan.blog.common.freemarker.directive.blogsite;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kingschan.blog.model.vo.WebSiteVo;
import com.kingschan.blog.services.WebSiteService;

import freemarker.core.Environment;
import freemarker.template.ObjectWrapper;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
/**
 * 博客列表
 * @author Administrator
 *
 */
@Component("WebSiteDirective")
public class WebSiteDirective  implements TemplateDirectiveModel{

	@Autowired
	private WebSiteService ws;
	@Override
	public void execute(Environment env, @SuppressWarnings("rawtypes") Map params, TemplateModel[] tm, TemplateDirectiveBody body)
            throws TemplateException, IOException {
		try {
			List<WebSiteVo> data=ws.getWebSiteList();
			env.setVariable("website_lis", ObjectWrapper.DEFAULT_WRAPPER.wrap(data));
			body.render(env.getOut());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
