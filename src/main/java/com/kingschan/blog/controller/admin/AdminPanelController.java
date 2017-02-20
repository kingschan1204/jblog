package com.kingschan.blog.controller.admin;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.kingschan.blog.services.system.impl.EmailNotifyServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kingschan.blog.common.enums.Variable;
import com.kingschan.blog.common.freemarker.util.FreemarkerParseUtil;
import com.kingschan.blog.services.impl.UserServiceImpl;
import com.kingschan.blog.util.BlogUtil;
import com.kingschan.blog.util.PathUtil;
import com.kingschan.blog.util.StringUtil;

/**
 * 
 * <pre>    
* 类名称：AdminPanelController 
* 类描述：   管理员控制面板
* 创建人：陈国祥   (kingschan)
* 创建时间：2016-3-1 上午8:57:15   
* 修改人：Administrator   
* 修改时间：2016-3-1 上午8:57:15   
* 修改备注：   
* @version V1.0
* </pre>
 */
@Controller
@RequestMapping("/admin")
public class AdminPanelController {
	private static Logger log = LoggerFactory.getLogger(AdminPanelController.class);
    @Autowired
    private UserServiceImpl user_serv;
	@Autowired
    private EmailNotifyServiceImpl emailNotifyService;
   
    /**
     * 登录成功后跳转页面
     * @return
     */
    @RequestMapping("/main.do")
    public String main() {
        return "/admin/main";
    }
    
    
    /**
     * 发送验证邮箱邮件
     * @param req
     * @param email
     * @return
     */
    @ResponseBody
    @RequestMapping("/sendValidateEmail.do")
    public String sendValidateEmail(HttpServletRequest req,String email){
    	BlogUtil bu = new BlogUtil(req);
//    	JavaMailSender sender =mail;
    	String result="邮件发送成功！";
    	try {
	    	   Map<String, Object> root = new HashMap<String, Object>();
	    	   String random=StringUtil.getUUID();
	    	   root.put("uid", bu.getCurrentUser().getId());
	    	   root.put("random", random);
	    	   String path=String.format("%s/%s", PathUtil.getWebInfPath(),"/template/");
	    	   String mailcontent=FreemarkerParseUtil.parserFileTemplate(root, path,"validate-email.html");
			   user_serv.addCache(Variable.CACHE_CONTENT_VALIDATE_EMAIL.getKey(), bu.getCurrentUser().getId(), random);
			   emailNotifyService.sendEmail(email,  "51so.info邮箱验证", mailcontent);
		} catch (Exception e) {
			result=e.getMessage();
			log.error("邮箱验证邮件发送",e);
		}
    	return result;
    }
    /**
     * 邮箱验证
     * @param req
     * @param key
     * @param code
     * @return
     */
    @RequestMapping("/validateEmail.do")
    public ModelAndView validateEmail(HttpServletRequest req,String key,String code) {
    	ModelAndView mav = new ModelAndView("/admin/pub/msg");
        try {
        	if (null==key||null==code||key.isEmpty()||code.isEmpty()) {
        		mav.addObject("msg", "<b style='color:red;'>参数为空，非法操作！</b>");
        	}else{
        		Object value=user_serv.getCache(Variable.CACHE_CONTENT_VALIDATE_EMAIL.getKey(), key);
        		 if (null!=value&&value.toString().equals(code)) {
        			 user_serv.activeEmail(req, key);
        			 user_serv.removeCache(Variable.CACHE_CONTENT_VALIDATE_EMAIL.getKey(), key);
        			 mav.addObject("msg", "<b style='color:green;'>邮箱验证成功！</b>");
        			}else{
        				 throw new Exception("<b style='color:red;'>验证信息已过期或者不存在！</b>");
        			}
        	}
        } catch (Exception e) {
            log.error("validateEmail",e);
            mav.addObject("msg", e.getMessage());
        }
        return mav;
    }
  
}
