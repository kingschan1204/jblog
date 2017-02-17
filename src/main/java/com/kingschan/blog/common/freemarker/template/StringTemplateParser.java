package com.kingschan.blog.common.freemarker.template;

import java.io.StringWriter;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class StringTemplateParser {

    private String text;
    public StringTemplateParser(String p_text){
        this.text=p_text;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    /**
     * 模板解析
     * @param map
     * @return
     * @throws Exception
     */
    public String process(Map<String, Object> map) throws Exception{
        Configuration cfg = new Configuration();
        cfg.setTemplateLoader(new StringTemplateLoader(text));
        cfg.setDefaultEncoding("UTF-8");
        Template template = cfg.getTemplate("");      
        StringWriter writer = new StringWriter();
        template.process(map, writer);
        return writer.toString(); 
    }
}
