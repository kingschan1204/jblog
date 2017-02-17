package com.kingschan.blog.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.HttpRequestHandler;

import com.baidu.ueditor.ActionEnter;
/**
 * 文章发布时图片上传
*  <pre>    
* 类名称：UploadImgServlet 
* 类描述：   
* 创建人：陈国祥   (kingschan)
* 创建时间：2016-3-7 下午5:27:48   
* 修改人：Administrator   
* 修改时间：2016-3-7 下午5:27:48   
* 修改备注：   
* @version V1.0
* </pre>
 */
//@WebServlet("/admin/article_uploadImg.do")
@Component("UploadImgServlet")
public class UploadImgServlet implements HttpRequestHandler  {
    
    Logger log = Logger.getLogger(UploadImgServlet.class);

    
    /**
     * 生成图片存放路径 
     * @param path_prefix
     * @return
     */
    String getImgSavePath(String path_prefix){
        Calendar c = Calendar.getInstance();
        int year=c.get(Calendar.YEAR);
        int month=c.get(Calendar.MONTH)+1;
        int day=c.get(Calendar.DATE);
        String path;
        boolean windows=System.getProperties().get("os.name").toString().toLowerCase().startsWith("win");
        if (windows) {
            path=String.format("%s\\%s\\%s\\%s",path_prefix, year,month,day);
        }else{
            path=String.format("%s/%s/%s/%s",path_prefix,year,month,day);
        }
        return path;
    }

    /**
     * 图片url地址
     * @param filename
     * @return
     */
    String getImgUrl(String filename){
        Calendar c = Calendar.getInstance();
        int year=c.get(Calendar.YEAR);
        int month=c.get(Calendar.MONTH)+1;
        int day=c.get(Calendar.DATE);
        return String.format("%s/%s/%s/%s",year,month,day,filename);
    }

    @Override
    public void handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
       /* String path = getImgSavePath(request.getRealPath("/www/upload"));
        File file = new File(path);
        if (!file.exists())
            file.mkdirs();
        String fileName = "";// 文件名称
        *//**上传文件处理内容**//*
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload sfu = new ServletFileUpload(factory);
        sfu.setHeaderEncoding("UTF-8"); // 处理中文问题
        sfu.setSizeMax(1024 * 1024*1024); // 限制文件大小
        try {
            List<FileItem> fileItems = sfu.parseRequest(request); // 解码请求
            for (FileItem fi : fileItems) {
                fileName = UUID.randomUUID()+fi.getName().substring(fi.getName().lastIndexOf("."),fi.getName().length());
                File ff=new File(path, fileName);
                fi.write(ff);
                ImgWaterMark.pressText("51so.info", ff.getAbsolutePath());
                log.info(ff.getAbsolutePath());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //获取图片url地址
        String imgUrl =String.format("%s/www/upload/%s",webroot,getImgUrl(fileName));
        log.info(imgUrl);
        response.setContentType("text/text;charset=utf-8");
        PrintWriter out = response.getWriter();
        out.print(imgUrl);  //返回url地址
        out.flush();
        out.close();*/
        
        request.setCharacterEncoding( "utf-8" );
        response.setHeader("Content-Type" , "text/html");
        String rootPath =request.getSession().getServletContext().getRealPath("/");
        String finalstr=new ActionEnter( request, rootPath ).exec();
        PrintWriter out = response.getWriter();
        out.print(finalstr);  //返回url地址
        out.flush();
        out.close();
    }
}
