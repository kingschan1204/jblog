package com.kingschan.blog.controller.font;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kingschan.blog.services.font.impl.FontBlogServiceImpl;
import com.kingschan.blog.util.BlogUtil;
/**
 *  只要登录了都可以访问的  公共资源
 * @author kingschan
 *
 */
@Controller
@RequestMapping("/font")
public class FontController {
//	private Logger log =LoggerFactory.getLogger(FontController.class);
	@Autowired
	private FontBlogServiceImpl fontServ;
	 /**
     * 退出登录
     * 
     * @param request
     *            HTTP请求对象实例
     */
    @RequestMapping(value = "/logout.do")
    public String logoff(HttpServletRequest request,HttpServletResponse res) {
    	BlogUtil bu= new BlogUtil(request);
    	bu.loginOut();
        bu.clearLoginCookies(request,res,fontServ.getShareCookHost());  
        String referer =request.getHeader("referer");
        return String.format("redirect:%s", null==referer?"/pub/login.do":referer);
    }
    
    /**
     * 留言
     * @param root
     * @param at
     * @param content
     * @param request
     * @return
     */
    @RequestMapping("/addMsgBoard.do")
	public String addMsgBoard(String root,String at,String content,HttpServletRequest request){
    	String url="";
		try {
			BlogUtil util = new BlogUtil(request);
			if (util.isLogin()) {
				String website=util.getCurrentFontWebSite().getId();
				String userid=util.getCurrentUser().getId();
				String target =StringUtils.isEmpty(root)?util.getCurrentFontWebSite().getUser().getId():at;
				 url=request.getHeader("referer");
				if (StringUtils.isEmpty(root)) {
					url=url.replaceFirst("\\?.*", "");
				}
				url=url.concat("#msgboardlist");
				fontServ.addMsgBoard(root, target, content, userid, website,url);
			}else{
				request.setAttribute("msg", "你还未登录!");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return String.format("redirect:%s",url);
		
	}
    
    
}
