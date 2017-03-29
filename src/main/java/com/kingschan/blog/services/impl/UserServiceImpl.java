package com.kingschan.blog.services.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.ehcache.Element;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.stereotype.Service;

import weibo4j.Users;
import weibo4j.http.AccessToken;

import com.kingschan.blog.common.enums.UserLevel;
import com.kingschan.blog.common.enums.Variable;
import com.kingschan.blog.common.freemarker.util.FreemarkerParseUtil;
import com.kingschan.blog.common.qiniu.QiNiuManagement;
import com.kingschan.blog.model.vo.UserVo;
import com.kingschan.blog.dao.UserDao;
import com.kingschan.blog.po.User;
import com.kingschan.blog.po.WebSite;
import com.kingschan.blog.services.UserService;
import com.kingschan.blog.util.Base64ImgUtil;
import com.kingschan.blog.util.BlogUtil;
import com.kingschan.blog.util.CommomEncrypt;
import com.kingschan.blog.util.CookieUtil;
import com.kingschan.blog.util.DesEncrypt;
import com.kingschan.blog.util.PathUtil;
import com.kingschan.blog.util.RegexUtil;
import com.kingschan.blog.util.StringUtil;
import com.kingschan.blog.util.TimeStampUtil;
@Service
public class UserServiceImpl extends CommonServiceImpl implements UserService {

	private Logger log =LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private UserDao user_dao;
    @Autowired
    private QiNiuManagement qiniuMangent;
    
    @Override
    public User getUser(String keyword) throws Exception {
        return user_dao.getUser(keyword);
    }

    @Override
    public void saveUser(UserVo user) throws Exception {
        User u = null;
        if (null==user.getId()||user.getId().isEmpty()) {
            u=new User();
            u.setUserDatetime(TimeStampUtil.getCurrentDate());
            u.setUserState((short) 0);
        }else{
            u=getUser(user.getId());
            u.setUserScreenName(user.getUserScreenName());
            u.setUserEmail(user.getUserEmail());
        }
        user_dao.saveUser(u);
    }

	@Override
	public int modifyPsw(String oldpassword, String newpassword, User u)throws Exception {
		if (!CommomEncrypt.MD5(oldpassword).equals(u.getUserPsw())) {
			throw new Exception("密码不正确！");
		}
		return user_dao.modifyPsw(CommomEncrypt.MD5(newpassword), u.getId());
	}

	@Autowired
	EhCacheCacheManager em;
	@Override
//	@Cacheable(value = "userLoginCache", key="#username") 
	public int getLoginFailures(String username) throws Exception {
		Element et= em.getCacheManager().getCache("userLoginCache").get(username);
		int value= null==et?0:Integer.valueOf(et.getObjectValue().toString());
		return value;
	}

	@Override
	@CachePut(value = "userLoginCache", key = "#username")
	public int setLoginFailures(String username) throws Exception {
		int total=getLoginFailures(username)+1;
		return total;
	}

	@Override
	@CacheEvict(value="userLoginCache",key="#username")
	public void clearLoginFailures(String username) throws Exception {
//		System.out.println("清空后的次数："+getLoginFailures(username));
	}
	
	@Override
	public int userLogin(String username, String psw,boolean isRemember, HttpServletRequest req,HttpServletResponse rep)
			throws Exception {
		String password=psw.matches("\\w{32}")?psw:CommomEncrypt.MD5(psw);
		BlogUtil bu = new BlogUtil(req);
		User user = getUser(username);// account, password
        if (null == user || !user.getUserPsw().equals(password)) {
        	//记录错误
        	addCache("userLoginCache", username, getLoginFailures(username)+1);
            return -1;
        }
        user.setUserLastlogin(TimeStampUtil.getCurrentDate());//记录本次登录时间
        CookieUtil cu = new CookieUtil(req, rep);
        cu.setCookieAttribute(Variable.COOKIE_JSESSIONID.getKey(), req.getSession().getId(), 60 * 60 * 24 * 3,"/",getShareCookHost());
        if (isRemember) {
            DesEncrypt des = DesEncrypt.getInstance(Variable.COOKIE_ENCRYPT_KEY.getKey());
            cu.setCookieAttribute(Variable.COOKIE_USERNAME_KEY.getKey(), des.getEncString(username), 60 * 60 * 24 * 3,"/",getShareCookHost());
            cu.setCookieAttribute(Variable.COOKIE_PSW_KEY.getKey(), des.getEncString(password), 60 * 60 * 24 * 3,"/",getShareCookHost());
        }
        Iterator<WebSite> itera=user.getWebSites().iterator();
        while (itera.hasNext()) {
        	 bu.getSession().setAttribute(Variable.SESSION_BLOG_ADMIN_WEBSITE.getKey(), itera.next());
		}
        UserVo vo =new UserVo();
		BeanUtils.copyProperties(user, vo);
		bu.getSession().setAttribute(BlogUtil.CURRENT_USER, vo);
        return 1;
	}

	@Override
	public List<UserVo> getUsersVoByEmail(String email) throws Exception {
		List<User> users=user_dao.getUsersByEmail(email);
		if (null==users||users.size()==0) {
			return null;
		}
		List<UserVo> lis = new ArrayList<UserVo>();
		for (User user : users) {
			UserVo u = new UserVo();
			BeanUtils.copyProperties(user, u);
			lis.add(u);
		}
		return lis;
	}

	@Override
	public String findPswByEmail(String email) throws Exception {
		List<User> users=user_dao.getUsersByEmail(email);
		if (null==users||users.size()==0) {
			throw new Exception("该email不存在！");
		}
		Map<String, Object> root = new HashMap<String, Object>();
		String random=StringUtil.getUUID();
		for (User user : users) {
			addCache("findPswCache", user.getId(), random);
		}
		root.put("users", users);
		root.put("random", random);
		///home/kingschan/apache-tomcat-7.0.62/webapps/ROOT/WEB-INF/\template\
		String path=String.format("%s/%s", PathUtil.getWebInfPath(),"/template/");
		String result=FreemarkerParseUtil.parserFileTemplate(root, path,"findpsw.html");
		return result;
	}

	@Override
	public User existsSinaUser(long uid, boolean login) throws Exception {
		User u =user_dao.existsSinaUser(uid);
		if (null==u) {
			return null;
		}
		if (login) {
			u.setUserLastlogin(TimeStampUtil.getCurrentDate());
		}
		return u;
	}

	@Override
	public UserVo sinaUserSynchronization(AccessToken token,User user) throws Exception {
		Users um = new Users(token.getAccessToken());
		weibo4j.model.User sina_user = um.showUserById(token.getUid());
		
		boolean update=null!=user;//是否是更新
		User u = null;
		if (update) {
			u=user;
		}else{
			u=new User();
			u.setId("");
			u.setUserName(sina_user.getId());
			u.setUserPsw("password");
			////性别,m--男，f--女,n--未知
			//sina_user.getGender().equals("m")
			u.setUserSex(true);
			u.setUserEmail("");
			u.setUserState((short) 1);
			u.setUserDatetime(TimeStampUtil.getCurrentDate());
			u.setUserLastlogin(TimeStampUtil.getCurrentDate());
			u.setUserEmailActivate(false);
			u.setSinaUid(Long.valueOf(sina_user.getId()));
			u.setUserLevel(UserLevel.GUEST.toString());
		}
		u.setUserScreenName(sina_user.getScreenName());
		u.setUserProfileImg(sina_user.getAvatarLarge());
		u.setSinaToken(token.getAccessToken());
		u.setSinaExpireIn(Long.valueOf(token.getExpireIn()));
		u.setSinaProfileUrl(sina_user.getUserDomain());
		
		user_dao.saveUser(u);
		UserVo vo1 =new UserVo();
		BeanUtils.copyProperties(u, vo1);
		return vo1;
	}

	@Override
	public boolean canFindPass(String uid, String key) throws Exception {
		if (null==uid||null==key||uid.isEmpty()||key.isEmpty()) {
			return false;
		}
		Object token= getCache("findPswCache", uid);
		if (null==token||!token.toString().equals(key)) {
			return false;
		}
		return true;
	}

	@Override
	public UserVo resetPass(String password, String uid) throws Exception {
		User u =getUser(uid);
		String pass=CommomEncrypt.MD5(password);
		u.setUserPsw(pass);
		user_dao.saveUser(u);
		removeCache("findPswCache", uid);
		UserVo vo = new UserVo();
		BeanUtils.copyProperties(u, vo);
		return vo;
	}

	@Override
	public UserVo getUserVoByKeyword(String keyword) throws Exception {
		User u = getUser(keyword);
		if (null!=u) {
			UserVo vo = new UserVo();
			BeanUtils.copyProperties(u, vo);
			return vo;
		}
		return null;
	}

	@Override
	public void activeEmail(HttpServletRequest req, String uid)
			throws Exception {
		User u =getUser(uid);
		if (null==u) {
			throw new Exception("对象不存在，非法操作！");
		}
		u.setUserEmailActivate(true);
		user_dao.saveUser(u);
		UserVo vo = new UserVo();
		BeanUtils.copyProperties(u, vo);
  		BlogUtil bu = new BlogUtil(req);
  		bu.getSession().setAttribute(BlogUtil.CURRENT_USER, vo);
	}

	@Override
	public Map<String, Object> getUserInfoCard(String username) throws Exception {
		//user_info_card
		Map<String, Object> data= user_dao.getUserInfoCard(username);
		return data;
	}

	@Override
	public String uploadProfile(String userid, String base64) throws Exception {
		String[] data=base64.split(",");
		//data:image/png;base64
		String suffix =RegexUtil.findStrByRegx(data[0], "/\\w+").substring(1);
		String fileName=String.format("%s.%s", userid,suffix);
		Base64ImgUtil.decodeBase64ToImage(data[1], getFileTempFolder(), fileName);
		String path=String.format("%s/%s", getFileTempFolder(),fileName);
		if (!new File(path).exists()) {
			throw new Exception("img don't exists...");
		}
		User u = getUser(userid);
		if (u.getUserProfileImg().contains("51so.info")) {
			String key=RegexUtil.findStrByRegx(u.getUserProfileImg(), "[\\w|-]+$");
			try {
				qiniuMangent.delRes(key);
			} catch (Exception e) {
				log.error("uploadProfile",e);
			}
		}
		JSONObject json= qiniuMangent.upload(path, null);
		String imgUrl=String.format("http://res.51so.info/%s", json.getString("key"));
		u.setUserProfileImg(imgUrl);
		return imgUrl;
	}
	
	
}
