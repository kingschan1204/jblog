package com.kingschan.blog.controller.admin;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kingschan.blog.model.vo.UserVo;
import com.kingschan.blog.po.User;
import com.kingschan.blog.services.impl.UserServiceImpl;
import com.kingschan.blog.util.BlogUtil;
/**
 * 用户业务处理控制器
 * @author kingschan
 *2016-11-11
 */
@Controller
@RequestMapping("/admin")
public class UserController {
	
	private static Logger log = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserServiceImpl user_serv;
    
    
    /**
     * 修改密码
     * @param request
     * @param newpsw
     * @param oldpsw
     * @return
     */
    @ResponseBody
    @RequestMapping("/modify_psw")
    public String modifyPsw(HttpServletRequest request,String newpsw,String oldpsw){
    	BlogUtil bu = new BlogUtil(request);
    	String msg="success";
    	try {
    		 int affected=user_serv.modifyPsw(oldpsw, newpsw, bu.getCurrentUser().toUser());
			 if (affected==0) {
				msg="更新失败!";
			}else{
				User u = user_serv.getUser(bu.getCurrentUser().getId());
				UserVo user = new UserVo();
				BeanUtils.copyProperties(u, user);
				request.getSession().setAttribute(BlogUtil.CURRENT_USER, user);
			}
		} catch (Exception e) {
			msg=e.getMessage();
		}
		return msg;
    	
    }
    /**
     * 用户个人信息加载
     * @param req
     * @return
     */
    @RequestMapping("/profile.do")
    public ModelAndView profile(HttpServletRequest req) {
    	String userid=new BlogUtil(req).getCurrentUser().getId();
    	ModelAndView mav = new ModelAndView("/admin/profile");
    	try {
			User user= user_serv.getUser(userid);
			mav.addObject("u", user);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
        return mav;
    }
    /**
     * 保存个人信息
     * @param request
     * @param u
     * @param attr
     * @return
     */
    @RequestMapping("/savePorfile")
    public String modifyPsw(HttpServletRequest request,@ModelAttribute UserVo u,RedirectAttributes attr){
    	BlogUtil bu = new BlogUtil(request);
    	String msg="success";
    	try {
    		u.setId(bu.getCurrentUser().getId());
    		user_serv.saveUser(u);
		} catch (Exception e) {
			log.error("savePorfile",e);
			e.printStackTrace();
			msg=e.getMessage();
		}
    	attr.addFlashAttribute("msg", msg);
		return String.format("redirect:%s", "/admin/profile.do");
    	
    }
    
    
    @RequestMapping("/upload-profile")
    @ResponseBody
    public String uploadProfile(HttpServletRequest request,String base64){
    	BlogUtil bu = new BlogUtil(request);
    	String str=null;
    	try {
			str=user_serv.uploadProfile(bu.getCurrentUser().getId(), base64);
//			bu.getCurrentUser().setSinaProfileImgS(str);
//			bu.getCurrentUser().setSinaProfileImgL(str);
		} catch (Exception e) {
			str="error";
			e.printStackTrace();
		}
		return str;
    	
    } 
    
}
