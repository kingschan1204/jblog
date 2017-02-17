package com.kingschan.blog.controller.pub;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.kingschan.blog.util.BlogUtil;
/**
 * 
 * 主页
 * @author kings.chan
 *date:2016-05-17
 */
@Controller
public class HomeController {

	 @RequestMapping("/websitehome")
	    public ModelAndView home(HttpServletRequest request){
	        ModelAndView av =new ModelAndView("/home/index");
	        av.addObject("page", request.getParameter("page"));
	        return av;
	    }
	 
	 @RequestMapping("/q")
	    public ModelAndView query(HttpServletRequest request,String q){
	        ModelAndView av =new ModelAndView("/home/index");
	        av.addObject("page", request.getParameter("page"));
	        try {
	            if (null!=q&&!q.isEmpty()) {
	            	String s=BlogUtil.filterXss(q);
	                av.addObject("fulltext", s);
	                av.addObject("keyword", StringEscapeUtils.unescapeHtml(s));
	            }
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return av;
	    }
	 
}
