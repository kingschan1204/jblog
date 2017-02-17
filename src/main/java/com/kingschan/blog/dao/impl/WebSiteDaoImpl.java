package com.kingschan.blog.dao.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.kingschan.blog.dao.HibernateBaseDao;
import com.kingschan.blog.dao.WebSiteDao;
import com.kingschan.blog.po.BaseAgent;
import com.kingschan.blog.po.WebSite;
import com.kingschan.blog.util.TimeStampUtil;

import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.UserAgent;

/**
 * 
 * <pre>    
* 类名称：WebSiteDaoImpl 
* 类描述：   
* 创建人：陈国祥   (kingschan)
* 创建时间：2016-2-22 上午11:06:24   
* 修改人：Administrator   
* 修改时间：2016-2-22 上午11:06:24   
* 修改备注：   
* @version V1.0
* </pre>
 */
@SuppressWarnings("unchecked")
@Repository
public class WebSiteDaoImpl extends HibernateBaseDao implements WebSiteDao {
	private static Logger log = LoggerFactory.getLogger(WebSiteDaoImpl.class);
    @Override
    public WebSite getWebSite(String keyword) throws Exception {
        if (keyword.matches("\\w{32}")) {
            return (WebSite) get(WebSite.class, keyword);
        } else {
            String hql = "from WebSite a where a.websiteName=?";
            List<WebSite> lis = (List<WebSite>) queryForListByHql(hql,true,false, keyword);
            return null != lis && lis.size() > 0 ? lis.get(0) : null;
        }

    }

    @Override
    public void saveWebSite(WebSite ws) throws Exception {
        if (null==ws.getId()||ws.getId().isEmpty()) {
            ws.setWebsiteDatetime(TimeStampUtil.getCurrentDate());
            save(ws);
        }else{
            update(ws);
        }
    }

	
	@Override
	public List<WebSite> getWebSiteList() throws Exception {
		String hql="from WebSite";
		return (List<WebSite>) queryForListByHql(hql,true,false);
	}

	@Override
	public void analysisRequestAgent() throws Exception {
		String insert_agent_sql="insert into base_agent(agent_key,agent_browser_name,agent_browser_version,agent_browser_engine,agent_os,agent_device) select req_agent,'','','','','' from blog_request_log a 	left join base_agent b on a.req_agent=b.agent_key	where a.req_agent <> '' and b.id is null group by req_agent ";
		int insert_affected=executeSQL(insert_agent_sql);
		log.info("插入未存在的agent_key {} 条",insert_affected);
		String hql =" from BaseAgent where agentBrowserName='' or agentOs='' or agentDevice=''";
		List<BaseAgent> agents = (List<BaseAgent>) queryForListByHql(hql, false, false);
		for (BaseAgent baseAgent : agents) {
			UserAgent userAgent = UserAgent.parseUserAgentString(baseAgent.getAgentKey());  
			Browser browser=userAgent.getBrowser();
			String engine=null==browser?"":String.valueOf(browser.getRenderingEngine());
			String browserName=null==browser?"":String.valueOf(browser.getGroup().getName());
			String version=null==userAgent.getBrowserVersion()?"":String.valueOf(userAgent.getBrowserVersion());
			String os=null==userAgent.getOperatingSystem()?"":String.valueOf(userAgent.getOperatingSystem().getDeviceType().getName());
			String device=null==userAgent.getOperatingSystem()?"":String.valueOf(userAgent.getOperatingSystem().getName());
			baseAgent.setAgentBrowserEngine(engine);
			baseAgent.setAgentBrowserName(browserName);
			baseAgent.setAgentBrowserVersion(version);
			baseAgent.setAgentDevice(os);
			baseAgent.setAgentOs(device);
			save(baseAgent);
		}
	}

}
