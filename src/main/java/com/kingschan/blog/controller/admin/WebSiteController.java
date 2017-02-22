package com.kingschan.blog.controller.admin;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kingschan.blog.model.vo.WebSiteVo;
import com.kingschan.blog.po.WebSite;
import com.kingschan.blog.services.WebSiteService;
import com.kingschan.blog.util.BlogUtil;
/**
 * 
*  <pre>    
* 类名称：WebSiteController 
* 类描述：   网站管理
* 创建人：陈国祥   (kingschan)
* 创建时间：2016-3-9 下午2:47:46   
* 修改人：Administrator   
* 修改时间：2016-3-9 下午2:47:46   
* 修改备注：   
* @version V1.0
* </pre>
 */
@Controller
@RequestMapping("/admin")
public class WebSiteController {
    @Autowired
    private WebSiteService website_serv;
    /**
     * 博客信息
     * @return
     */
    @RequestMapping("/website_info.do")
    public ModelAndView getWebsiteInfo(HttpServletRequest req){
        ModelAndView mav = new ModelAndView("/admin/website_info");
       String keyword= new BlogUtil(req).getCurrentAdminWebSite().getWebsiteName();
       WebSite web = null;
		try {
			web = website_serv.getWebSite(keyword);
		} catch (Exception e) {
			e.printStackTrace();
		}
        mav.addObject("blogsite",web );
        return mav;
    }
    /**
     * 保存博客信息
     * @return
     */
    @RequestMapping("/save_website_info.do")
    public String saveWebsiteInfo(@ModelAttribute("ws") WebSiteVo ws,HttpServletRequest req,RedirectAttributes attr){
        BlogUtil bu = new BlogUtil(req);
        try {
           WebSite web= website_serv.saveWebSite(ws,bu.getCurrentUser().toUser());
            bu.setCurrentAdminWebSite(web);
            attr.addFlashAttribute("msg", "success");
        } catch (Exception e) {
            e.printStackTrace();
            attr.addFlashAttribute("msg", "保存失败,系统错误!"+e.getClass().getName());
        }
        return "redirect:/admin/website_info.do";
    } 
}
