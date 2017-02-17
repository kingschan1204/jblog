package com.kingschan.blog.dao;

import java.util.List;

import com.kingschan.blog.po.WebSite;

/**
 * 
*  <pre>    
* 类名称：WebSiteDao 
* 类描述：   
* 创建人：陈国祥   (kingschan)
* 创建时间：2016-2-22 上午11:03:21   
* 修改人：Administrator   
* 修改时间：2016-2-22 上午11:03:21   
* 修改备注：   
* @version V1.0
* </pre>
 */
public interface WebSiteDao {

    /**
     * 得到website对象
     * @param keyword
     * @return
     * @throws Exception
     */
    WebSite getWebSite(String keyword)throws Exception;
    
    /**
     * 保存
     * @param ws
     * @return
     * @throws Exception
     */
    void saveWebSite(WebSite ws)throws Exception;
    
    /**
     * 查出所有博客列表
     * @return
     * @throws Exception
     */
    List<WebSite> getWebSiteList()throws Exception;
    
    /**
     * 分析http agent
     * @throws Exception
     */
    void analysisRequestAgent() throws Exception;
}
