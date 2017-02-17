package com.kingschan.blog.common.freemarker.template;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import freemarker.cache.TemplateLoader;
/**
 * 
*  <pre>    
* 类名称：StringTemplateLoader 
* 类描述：模板加载器   
* 创建人：陈国祥   (kingschan)
* 创建时间：2014-9-5 上午10:20:17   
* 修改人：Administrator   
* 修改时间：2014-9-5 上午10:20:17   
* 修改备注：   
* @version V1.0
* </pre>
 */
public class StringTemplateLoader implements TemplateLoader {
    private String template;
    public StringTemplateLoader(String template){
        this.template = template;
        if(null==template){
            this.template = "";
        }
    }

    @Override
    public void closeTemplateSource(Object templateSource) throws IOException {
        ((StringReader) templateSource).close();
    }

    @Override
    public Object findTemplateSource(String name) throws IOException {
        return new StringReader(template);
    }

    @Override
    public long getLastModified(Object templateSource) {
        return 0;
    }

    @Override
    public Reader getReader(Object templateSource, String encoding) throws IOException {
        return (Reader) templateSource;
    }

  /*  public static void main(String[] args) throws TemplateException, IOException {
        Configuration cfg = new Configuration();
        cfg.setTemplateLoader(new StringTemplateLoader("欢迎：${user} <#if 1==2>xxx</#if>"));
        cfg.setDefaultEncoding("UTF-8");
        Template template = cfg.getTemplate("");        
        Map root = new HashMap();
        root.put("user", "Keven Chen");        
        StringWriter writer = new StringWriter();
        template.process(root, writer);
        System.out.println(writer.toString());  

    }*/
}
