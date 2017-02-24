package com.kingschan.blog.services;

import java.util.List;

import com.kingschan.blog.model.vo.WebSiteVo;
import com.kingschan.blog.po.User;
import com.kingschan.blog.po.WebSite;
/**
 * 
*  <pre>    
* 类名称：WebSiteService 
* 类描述：   
* 创建人：陈国祥   (kingschan)
* 创建时间：2016-2-22 上午11:10:57   
* 修改人：Administrator   
* 修改时间：2016-2-22 上午11:10:57   
* 修改备注：   
* @version V1.0
* </pre>
 */
public interface WebSiteService {

    /**
     * 通过关键字得到网站对象
     * @param keyword
     * @return
     * @throws Exception
     */
    WebSite getWebSite(String keyword)throws Exception;

    /**
     * 保存
     * @param vo
     * @param user
     * @return
     * @throws Exception
     */
    WebSite saveWebSite(WebSiteVo vo, User user)throws Exception;
    
    /**
     * 得到博客列表
     * @return
     * @throws Exception
     */
    List<WebSiteVo> getWebSiteList()throws Exception;
    
    
    
    /**
     * 分析http agent
     * @throws Exception
     */
    void analysisRequestAgent()throws Exception;
}
