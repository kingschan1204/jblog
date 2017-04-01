package com.kingschan.blog.controller.admin;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kingschan.blog.model.vo.CategoryVo;
import com.kingschan.blog.services.CategoryService;
import com.kingschan.blog.util.BlogUtil;
/**
 * 文章类型管理
 * @author Administrator
 *
 */
@RequestMapping("/admin")
@Controller
public class CategoryController {
	private Logger log = LoggerFactory.getLogger(CategoryController.class);
	@Autowired
	private CategoryService category_serv;

	/**
	 * 类型列表管理
	 * @param page
	 * @param title
	 * @param category
	 * @return
	 */
	  @RequestMapping("/category_list.do")
	    public ModelAndView categoryList(Integer page,String title,String category){
	        ModelAndView mav = new ModelAndView("/admin/category_list");
	        mav.addObject("page", page);
	        mav.addObject("title", title);
	        mav.addObject("categoryName", category);
	        return mav;
	    }
	  /**
	   * 验证名字是否唯一
	   * @param name
	   * @param request
	   * @return
	   */
	/*  @ResponseBody
	  @RequestMapping("/category_validate_name.do")
	  public String validateCategoryName(String name,HttpServletRequest request){
		  JSONObject json = new JSONObject();
		  if (null==name||name.isEmpty()) {
			json.put("valid", false);
		  }else{
			  BlogUtil bu = new BlogUtil(request);
			try {
				json.put("valid", category_serv.uniqueCategoryName( bu.getCurrentWebSite().getId(), name)); 
			} catch (Exception e) {
				json.put("valid", false);
				e.printStackTrace();
			}
		  }
		  return json.toString();
	  }*/
	  /**
	   * 保存类型
	   * @param vo
	   * @param req
	   * @return
	   */
	  @ResponseBody
	  @RequestMapping("/category_save.do")
	  public String saveCategory(@ModelAttribute CategoryVo vo,HttpServletRequest req){
		  JSONObject json =JSONObject.fromObject("{msg:\"\",success:false}");
		  BlogUtil bu= new BlogUtil(req);
		  try {
			category_serv.saveCategory(vo,bu.getCurrentAdminWebSite().getId(),bu.getCurrentUser().toUser());
			json.put("success", true);
		} catch (Exception e) {
		   log.error("{}",e);
			json.put("msg", "服务出错!");
			e.printStackTrace();
		}
		  return json.toString();
	  }
	  /**
	   * 删除类型
	   * @param ids
	   * @param req
	   * @return
	   */
	  @ResponseBody
	  @RequestMapping("/category_del.do")
	  public String delCategory(@RequestParam("ids[]") String[] ids,HttpServletRequest req){
		  JSONObject json =JSONObject.fromObject("{msg:\"\",success:false}");
		  try {
			  BlogUtil bu = new BlogUtil(req);
			  category_serv.delCategory(ids,bu.getCurrentAdminWebSite().getId());
			json.put("success", true);
		} catch (Exception e) {
			json.put("msg", e.getMessage());
//			e.printStackTrace();
		}
		  return json.toString();
	  }
}
