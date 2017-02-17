package com.kingschan.blog.common.freemarker.directive.util;

import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.kingschan.blog.util.DateUtil;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
/**
 * 返回人性化时间差
 * @author kingschan
 *
 */
@Component("TimeSummary")
public class TimeSummaryDirective implements TemplateDirectiveModel {
    
    @Override
    public void execute(Environment env, @SuppressWarnings("rawtypes") Map params, TemplateModel[] tm, TemplateDirectiveBody body)
            throws TemplateException, IOException {
        String time=params.containsKey("time")?params.get("time").toString():"";//传入的字符串
        if (time.isEmpty()) {
        	env.getOut().write("");
		}else{
			env.getOut().write(DateUtil.timeSummary(time));	
		}
        
    }

   
}
