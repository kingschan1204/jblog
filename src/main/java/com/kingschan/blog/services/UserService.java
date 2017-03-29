package com.kingschan.blog.services;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import weibo4j.http.AccessToken;

import com.kingschan.blog.model.vo.UserVo;
import com.kingschan.blog.po.User;

public interface UserService {

    /**
     * 得到用户信息
     * @param keyword
     * @return
     * @throws Exception
     */
    User getUser(String keyword)throws Exception;
    /**
     * 得到用户小卡片
     * @param username
     * @return
     * @throws Exception
     */
    Map<String, Object> getUserInfoCard(String username)throws Exception;
    /**
     * 得到用户信息
     * @param keyword
     * @return
     * @throws Exception
     */
    UserVo getUserVoByKeyword(String keyword)throws Exception;
    /**
     * 用户登录
     * @param username
     * @param psw 这里要传入MD5值的密码
     * @param isRemember 是否记住密码
     * @param req
     * @param  rep
     * @return  1正常 -1帐号密码错误 0第一将次进入 -2 验证码错误
     * @throws Exception
     */
    int userLogin(String username, String psw, boolean isRemember, HttpServletRequest req, HttpServletResponse rep)throws Exception;
    /**
     * 保存用户
     * @param user
     * @throws Exception
     */
    void saveUser(UserVo user)throws Exception;
    
    /**
     * 更改密码
     * @param password
     * @param userid
     * @return
     * @throws Exception
     */
    int modifyPsw(String oldpassword, String newpassword, User u) throws Exception;
    
    /**
     * 登录失败次数记录在ehcache中
     * @param username
     * @return
     * @throws Exception
     */
    int getLoginFailures(String username)throws Exception;
    
    /**
     * 设置登录失败次数
     * @param username
     * @return
     * @throws Exception
     */
    int setLoginFailures(String username)throws Exception;
    
    
    /**
     * 清空登录失败信息
     * @param username
     * @throws Exception
     */
    void clearLoginFailures(String username)throws Exception;
    /**
     * 根据email找到用户
     * @param email
     * @return
     * @throws Exception
     */
    List<UserVo> getUsersVoByEmail(String email)throws Exception;
    
    /**
     * 通过邮箱找回密码
     * @param email
     * @return
     * @throws Exception
     */
    String findPswByEmail(String email)throws Exception;
    
    
    
    /**
     * 是否存在新浪用户
     * @param uid
     * @param login 是否是登录  是就写登录时间
     * @return
     * @throws Exception
     */
    User existsSinaUser(long uid, boolean login)throws Exception;
    
    /**
     * 新浪微薄用户同步（存在：更新|不存在新增）
     * @param code
     * @throws Exception
     */
    UserVo sinaUserSynchronization(AccessToken token, User user)throws Exception;
    
    /**
     * 是否可以找回密码
     * @param uid
     * @param key
     * @return
     * @throws Exception
     */
    boolean canFindPass(String uid, String key)throws Exception;
    
    /**
     * 只用于找回密码(重置密码)
     * @param password
     * @param uid
     * @throws Exception
     */
    UserVo resetPass(String password, String uid)throws Exception;
    
    /**
     * 邮件激活
     * @param req
     * @param uid
     * @throws Exception
     */
    void activeEmail(HttpServletRequest req, String uid)throws Exception;
    
    /**
     * 更新用户头像
     * @param userid 用户id
     * @param base64 base64 code
     * @return
     * @throws Exception
     */
    String uploadProfile(String userid, String base64)throws Exception;
}
