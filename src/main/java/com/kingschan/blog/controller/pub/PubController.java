package com.kingschan.blog.controller.pub;

import java.awt.Color;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.github.bingoohuang.patchca.color.ColorFactory;
import com.github.bingoohuang.patchca.color.SingleColorFactory;
import com.github.bingoohuang.patchca.custom.ConfigurableCaptchaService;
import com.github.bingoohuang.patchca.filter.predefined.*;
import com.github.bingoohuang.patchca.utils.encoder.EncoderHelper;
import com.github.bingoohuang.patchca.word.RandomWordFactory;
import com.kingschan.blog.services.system.impl.EmailNotifyServiceImpl;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import weibo4j.http.AccessToken;

import com.kingschan.blog.common.enums.Variable;
import com.kingschan.blog.common.sina.SinaWeiBoUtil;
import com.kingschan.blog.model.vo.ArticleCommentVo;
import com.kingschan.blog.model.vo.UserVo;
import com.kingschan.blog.po.User;
import com.kingschan.blog.services.font.impl.FontBlogServiceImpl;
import com.kingschan.blog.services.impl.UserServiceImpl;
import com.kingschan.blog.util.BlogUtil;
import com.kingschan.blog.util.CookieUtil;
import com.kingschan.blog.util.DesEncrypt;
import com.kingschan.blog.util.RegexUtil;
/**
 * 公共控制器  （不需要登录就可以访问）
 * @author kingschan
 *
 */
@Controller
@RequestMapping("/pub")
public class PubController {

	private Logger log =LoggerFactory.getLogger(PubController.class);
	@Autowired
    private UserServiceImpl user_serv;
	@Autowired
	private FontBlogServiceImpl fontServ;
	@Autowired
	private EmailNotifyServiceImpl emailNotifyService;
    private static ConfigurableCaptchaService cs = null;
    private static ColorFactory cf = null;
    private static RandomWordFactory wf = null;
    private static Random r = new Random();
    private static CurvesRippleFilterFactory crff = null;//干扰线波纹
    private static MarbleRippleFilterFactory mrff = null;//大理石波纹
    private static DoubleRippleFilterFactory drff = null;//双波纹
    private static WobbleRippleFilterFactory wrff = null;//摆波纹
    private static DiffuseRippleFilterFactory dirff = null;//漫波纹
    /**
     * 生成验证码
     * @param request
     * @param response
     * @throws IOException
     */
    @RequestMapping(value = "/vcode.do")
    public void vcode(HttpServletRequest request, HttpServletResponse response) throws IOException {
        cs = new ConfigurableCaptchaService();
        cf = new SingleColorFactory(new Color(25, 60, 170));
        wf = new RandomWordFactory();
        crff = new CurvesRippleFilterFactory(cs.getColorFactory());
        drff = new DoubleRippleFilterFactory();
        wrff = new WobbleRippleFilterFactory();
        dirff = new DiffuseRippleFilterFactory();
        mrff = new MarbleRippleFilterFactory();
        cs.setWordFactory(wf);
        cs.setColorFactory(cf);
        
        response.setContentType("image/png");
        response.setHeader("cache", "no-cache");
        wf.setMaxLength(4);
        wf.setMinLength(4);
        cs.setWidth(260);
        cs.setHeight(62);

        HttpSession session = request.getSession(true);
        OutputStream os = response.getOutputStream();
        switch (r.nextInt(5))
        {
        case 0:
          cs.setFilterFactory(crff);
          break;
        case 1:
          cs.setFilterFactory(mrff);
          break;
        case 2:
          cs.setFilterFactory(drff);
          break;
        case 3:
          cs.setFilterFactory(wrff);
          break;
        case 4:
          cs.setFilterFactory(dirff);
        }
//	        cs.setFilterFactory(dirff);
        String captcha = EncoderHelper.getChallangeAndWriteImage(cs, "png", os);
        session.setAttribute("vcode", captcha);
        os.flush();
        os.close();
    }
	    
    /**
     * 登录
     * 
     * @param req HTTP请求对象实例
     * @param rep HTTP响应对象实例
     * @return 返回跳转路径模型
     * @throws Exception 
     */
    @RequestMapping(value = "/login.do")
    public String signIn(HttpServletRequest req, HttpServletResponse rep, Boolean isRemember,
            String account, String password, String vcode,String code,String url ) throws Exception {
    	BlogUtil bu= new BlogUtil(req);
    	if (null!=code&&!code.isEmpty()) {
    		AccessToken token =SinaWeiBoUtil.token(code);
    		User u =user_serv.existsSinaUser(Long.valueOf(token.getUid()), true);
    		UserVo vo =user_serv.sinaUserSynchronization(token,u);
    		req.getSession().setAttribute(BlogUtil.CURRENT_USER, vo);
    		String turl=String.format("redirect:%s", bu.getSinaLoginReferer(),user_serv.getHost());
    		return turl;
		}
        int is_success = 0;
        boolean remember = (null == isRemember) ? false : isRemember;
        boolean formmodel=true;
        int loginfailure=0;
        try {
            if (bu.isLogin()) {
                log.info(String.format("用户[%s]已经登录！", bu.getCurrentUser().getUserName()));
                is_success = 1;
            } else {
                CookieUtil cu = new CookieUtil(req, rep);
                Cookie[] cookies = cu.getCookies();
                if (null != cookies && cookies.length != 0) {
                    // cookie 存在
                    String username = cu.getCookieAttribute(Variable.COOKIE_USERNAME_KEY.getKey());
                    String psw = cu.getCookieAttribute(Variable.COOKIE_PSW_KEY.getKey());
                    DesEncrypt des = DesEncrypt.getInstance(Variable.COOKIE_ENCRYPT_KEY.getKey());
                    if (null != psw && !psw.isEmpty()) {
                        psw = des.getDesString(psw);
                    }
                    if (null != username && null != psw) {
                        log.debug("使用cookie记忆登录");
                        formmodel=false;
                        is_success =login(req, rep, des.getDesString(username), psw, remember, null,false);
                        if (is_success != 1) {
                            bu.clearLoginCookies(req, rep,user_serv.getShareCookHost());
                            log.debug("cookie 信息错误，清除cookie!");
                        }
                    } 
                } 
                if (formmodel) {
	                 loginfailure=user_serv.getLoginFailures(account);
	               	 if (loginfailure>=3) {
	               		 is_success=-9;//错误超过3次锁定
						}else{
							is_success = login(req, rep, account, password, remember, vcode, loginfailure>0?true:false);
						}
					}
            }
            if (is_success==1) {
				user_serv.clearLoginFailures(account);
			}else{
				if (is_success == -1) {
	                req.setAttribute("msg", "帐号或密码错误!");
	            } else if (is_success == -2) {
	                req.setAttribute("msg", "验证码错误!");
	            }else if (is_success==-9) {
	            	req.setAttribute("msg", "错误次数过多,该帐号已被锁定1天!");
				}
	            req.setAttribute("login_error", user_serv.getLoginFailures(account));//登录错误次数
			}
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.toString());
        }
        String rurl=String.format("redirect:%s",url==null?"http://"+user_serv.getHost()+"/admin/main.do":url) ;
        return is_success == 1 ? rurl: "/admin/pub/login";
    }
    
    /**
     * 1正常 -1帐号密码错误 0第一将次进入 -2 验证码错误 
     * 
     * @param req
     * @param rep
     * @param account
     * @param password
     * @param isRemember
     * @return
     * @throws Exception
     */
    int login(HttpServletRequest req, HttpServletResponse rep, String account, String password,
            Boolean isRemember, String vcode, boolean enable_vcode) throws Exception {
        if (null == account || null == password || account.isEmpty() || password.isEmpty()) {
            log.info("第一次进入，表单信息为空!");
            return 0;
        }
        //如果有输入验证码就先验证再说
        if (null!=vcode&&!vcode.isEmpty()) {
			if (!vcode.equals(req.getSession().getAttribute("vcode"))) {
				return -2;
			}
		}
        if (enable_vcode) {
            if (null == vcode || vcode.isEmpty()
                    || !vcode.equals(req.getSession().getAttribute("vcode"))) {
                return -2;
            }
        }
        return user_serv.userLogin(account, password, isRemember, req, rep);
    }
   
    
    
	    /**
	     * 找回密码
	     * @param req
	     * @param rep
	     * @param u 用户Id
	     * @param key token key 
	     * @param vcode 验证码
	     * @param password1 新密码确认密码
	     * @param password2
	     * @return
	     */
	    @RequestMapping("/findpass.do")
	    public ModelAndView findpass(HttpServletRequest req,HttpServletResponse rep, String u,String key,String vcode,String password1,String password2,RedirectAttributes attr) {
	    	ModelAndView mav = new ModelAndView("/admin/pub/findpass");
	    	String msg_50X="授权码过期或者非法操作!";
	    	try {
	    		boolean isok= user_serv.canFindPass(u, key);
	    		if (null==vcode||vcode.isEmpty()) {
					//点邮件过来或者直接非法访问的 
	    			if (isok) {
	    				mav.addObject("key", key);
	    				mav.addObject("u", u);
					}else{
						rep.sendError(500,msg_50X);
					}
				}else{
					//提交过来的
					if (null==vcode||!vcode.equals(req.getSession().getAttribute("vcode"))) {
						mav.addObject("msg", "验证码错误");
					}else if(!isok){
						mav.addObject("msg", msg_50X);
					}else if (!password1.equals(password2)) {
						mav.addObject("msg", "两次密码不一致!");
					}else{
						UserVo vo= user_serv.resetPass(password1, u);
						user_serv.userLogin(vo.getUserName(),vo.getUserPsw(),false,  req, rep);
						rep.sendRedirect(String.format("http://%s/admin/main.do", user_serv.getHost()));
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				mav.addObject("msg", "系统出错!");
				log.error("找回密码",e);
			}
	    	
	        return mav;
	    }
	   
		/**
		 * 发送重置邮件
		 * @param req
		 * @param email
		 * @param vcode
		 * @return
		 */
	    @RequestMapping("/sendResetMail.do")
	    public ModelAndView sendResetEmail(HttpServletRequest req,String email,String vcode) {
	    	ModelAndView mav = new ModelAndView("/admin/pub/sendResetMail");
//	    	JavaMailSender sender =mail;
	        try {
	        	if (null!=email&&null!=vcode) {
	        		if (null==vcode||!vcode.equals(req.getSession().getAttribute("vcode"))) {
						mav.addObject("msg", "验证码错误");
						mav.addObject("email", email);
					}else if (!email.matches(RegexUtil.regex_email)) {
						mav.addObject("msg", "邮箱格式错误");
					}
	        		else{
						String result=null;
						try {
							 result=user_serv.findPswByEmail(email);
							emailNotifyService.sendEmail(email,"51so.info密码找回", result);
				             mav.addObject("msg", "邮件发送成功！");
						} catch (Exception e) {
							mav.addObject("msg", "邮件不存在！");
							log.error("重置密码邮件发送",e);
						}
					}
	        		
				}
	        	
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    	
	        return mav;
	    }
	    
	    
	    
	    /**
	     * 表情
	     * @param callback 回调函数
	     * @return
	     * @throws IOException 
	     */
		@RequestMapping("/emojify_dialog")
		public ModelAndView emojifyDialog(HttpServletRequest req,HttpServletResponse rep, String callback) throws IOException{
			String referer =req.getHeader("referer");
			if (null==referer|| !referer.matches(".*51so.info/.*")) {
				rep.sendError(505, "Access denied !");
			}
			ModelAndView mav = new ModelAndView("/admin/dialog/emojify_dialog");
			mav.addObject("callback", callback);
			return mav;
		}
		/**
		 * 人个人信息小卡片
		 * @param username
		 * @return
		 */
		@RequestMapping("/uinfo_card")
		public ModelAndView getUserInfo(String username,HttpServletRequest req,HttpServletResponse rep){
			ModelAndView ma = new ModelAndView("/admin/pub/userCard");
			try {
				String refer=req.getHeader("referer");
				if (StringUtils.isEmpty(refer)
						||StringUtils.isEmpty(username)
						||!refer.matches("^http://(\\w+\\.)?51so.info/.*")
						||(null!=username&&!username.matches("\\w+"))) {
					rep.sendError(403);
					return null;
				}
				Map<String, Object> data=user_serv.getUserInfoCard(username);
				ma.addAllObjects(data);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return ma;
		}
		/**
		 * 加载回复内容
		 * @param id
		 * @param page
		 * @param req
		 * @param rep
		 * @return
		 */
		@ResponseBody
		@RequestMapping("/loadMsgBoardReply")
		public String loadMsgBoardReply(String id,Integer page,HttpServletRequest req,HttpServletResponse rep){
			String result="";
			try {
				String refer=req.getHeader("referer");
				if (StringUtils.isEmpty(refer)
						||!refer.matches("^http://(\\w+\\.)?51so.info/.*")) {
					rep.sendError(403);
					return "";
				}else if (StringUtils.isEmpty(id)||null==page||page==0) {
					rep.sendError(403);
					return "";
				}
				BlogUtil bu = new BlogUtil(req);
				JSONArray jsons =fontServ.loadMsgBoardReply(bu.getCurrentFontWebSite().getId(), id, page);
				result=jsons.toString();
			} catch (Exception e) {
				result="error";
				e.printStackTrace();
			}
			return result;
		}
		
		@RequestMapping("/loadArticleDiscussReply")
		public ModelAndView loadArticleDiscussReply(String root,String articleId,Integer page,HttpServletRequest req,HttpServletResponse rep){
			ModelAndView mav = new ModelAndView("/skin/green/font/ajax/article_discuss");
			try {
				String refer=req.getHeader("referer");
				if (StringUtils.isEmpty(refer)
						||!refer.matches("^http://(\\w+\\.)?51so.info/.*")) {
					rep.sendError(403);
					return null;
				}else if (StringUtils.isEmpty(root)||StringUtils.isEmpty(articleId)||null==page||page==0) {
					rep.sendError(403);
					return null;
				}
				BlogUtil bu = new BlogUtil(req);
				String userid=bu.isLogin()?bu.getCurrentUser().getId():"";
				List<ArticleCommentVo> lis =fontServ.loadArticleDiscussReply(articleId, root, userid, page);
				mav.addObject("rows",lis);
				mav.addObject("root",root);
			} catch (Exception e) {
				mav.addObject("error", "error");
				e.printStackTrace();
			}
			return mav;
		}
}
