package com.kingschan.blog.common.freemarker.directive.util;

import java.io.IOException;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.kingschan.blog.util.StringUtil;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;
/**
 * 
*  <pre>    
* 类名称：SubStringDirective 
* 类描述：  根据字母和中文自动计算返回指定长度的字符
* 创建人：陈国祥   (kingschan)
* 创建时间：2016-2-20 上午9:28:48   
* 修改人：Administrator   
* 修改时间：2016-2-20 上午9:28:48   
* 修改备注：   
* @version V1.0
* </pre>
 */
@Component("SubString")
public class SubStringDirective implements TemplateDirectiveModel {
    
    @Override
    public void execute(Environment env, @SuppressWarnings("rawtypes") Map params, TemplateModel[] tm, TemplateDirectiveBody body)
            throws TemplateException, IOException {
        String value=params.containsKey("value")?params.get("value").toString():"";//传入的字符串
        int limit =params.containsKey("limit")&&params.get("limit").toString().matches("\\d+")?Integer.valueOf(params.get("limit").toString()):0;
        String defval=params.containsKey("defval")?params.get("defval").toString():"";
        String overflow=params.containsKey("overflow")?params.get("overflow").toString():"";//超出指定的长度 在后面追加指定的字符串
        if (limit>0&&!value.isEmpty()) {
        	env.getOut().write(StringUtil.getLengthOfSub(value, limit,overflow));
		}
        if (!defval.isEmpty()&&value.isEmpty()) {
        	env.getOut().write(defval);
		}
        
    }

   
}
