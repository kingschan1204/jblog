package com.kingschan.blog.common.freemarker.directive.bookmark;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kingschan.blog.po.BookmarksFolder;
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
* 类描述：   书签类型
* 创建人：陈国祥   (kingschan)
* 创建时间：2016-3-14 下午3:42:40   
* 修改人：Administrator   
* 修改时间：2016-3-14 下午3:42:40   
* 修改备注：   
* @version V1.0
* </pre>
 */
@Component("BookMarkFolderDirective")
public class BookMarkFolderDirective implements TemplateDirectiveModel{

    @Autowired
    private BookMarkService bookmark_serv;
    @Override
    public void execute(Environment env, @SuppressWarnings("rawtypes") Map params, TemplateModel[] tm, TemplateDirectiveBody body)
            throws TemplateException, IOException {
        String websiteid=params.containsKey("websiteid")?params.get("websiteid").toString():"";//网站ID
        List<BookmarksFolder> lis=null;
        try {
           lis= bookmark_serv.getAllBookMarksFolder(websiteid);
           env.setVariable("bookmarkfolder_lis", ObjectWrapper.DEFAULT_WRAPPER.wrap(lis));
           body.render(env.getOut());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
