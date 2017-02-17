package com.kingschan.blog.controller.admin;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kingschan.blog.services.BlogResService;
import com.kingschan.blog.util.BlogUtil;
/**
 * 博客资源管理
 * @author kings.chan
 *
 */
@Controller
@RequestMapping("/admin")
public class BlogResController {
	@Autowired
	private BlogResService blog_serv;
	/**
	 * 资源列表
	 * @param page
	 * @param resname
	 * @param model fancybox
	 * @param callback 回调函数
	 * @param multiSelect 是否支持多选
	 * @return
	 */
	@RequestMapping("/blogres_list")
	public ModelAndView blogResList(Integer page,String resname){
		ModelAndView mav = new ModelAndView("/admin/blogres_list");
		mav.addObject("page", page);
		mav.addObject("resname", resname);
		return mav;
	}
	/**
	 * 弹出框资源列表
	 * @param page
	 * @param resname
	 * @param model fancybox
	 * @param callback 回调函数
	 * @param multiSelect 是否支持多选
	 * @return
	 */
	@RequestMapping("/blogres_dialog")
	public ModelAndView blogResDlalog(Integer page,String resname,String callback,String multiSelect){
		ModelAndView mav = new ModelAndView("/admin/dialog/blogres_dialog");
		mav.addObject("page", page);
		mav.addObject("resname", resname);
		mav.addObject("callback", callback);
		mav.addObject("multiSelect", multiSelect);
		return mav;
	}
	/**
	 * 删除资源
	 * @param keys
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/del_blogres")
	public String delBlogRes(@RequestParam("keys[]")String[] keys,HttpServletRequest request){
		BlogUtil bu = new BlogUtil(request);
		JSONObject json = new JSONObject();
		int affected=0;
		try {
			affected=blog_serv.delBlogRes(keys, bu.getCurrentAdminWebSite().getId());
		} catch (Exception e) {
			e.printStackTrace();
			json.put("msg", e.getMessage());
		}
		json.put("affected", affected);
		return json.toString();
	}
	
	@ResponseBody
	@RequestMapping("/res_rename")
	public String resRename(String id,String name,HttpServletRequest request){
		BlogUtil bu = new BlogUtil(request);
		String result="success";
		try {
			blog_serv.rename(id, name, bu.getCurrentAdminWebSite().getId());
		} catch (Exception e) {
			e.printStackTrace();
			result=e.getMessage();
		}
		return result;
	}
}
