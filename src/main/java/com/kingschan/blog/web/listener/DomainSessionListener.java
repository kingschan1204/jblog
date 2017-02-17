package com.kingschan.blog.web.listener;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import com.kingschan.blog.common.enums.Variable;
import com.kingschan.blog.services.impl.CommonServiceImpl;
import com.kingschan.blog.util.SpringUtil;
/**
 * 
*    
* 类名称：DomainSessionListener   
* 类描述：session监听器   
* 创建人：kings.chan
* 创建时间：2016-7-27 上午10:12:37   
* 修改人：   
* 修改时间：
* 项目：ROOT
* 修改备注：   
* @version    
*
 */
public class DomainSessionListener  implements HttpSessionListener{

	@Override
	public void sessionCreated(HttpSessionEvent event) {
		CommonServiceImpl serv=SpringUtil.getBean("CommonServiceImpl");
		try {
			serv.addCache(Variable.CACHE_CONTENT_SESSION.getKey(), event.getSession().getId(), event.getSession());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		CommonServiceImpl serv=SpringUtil.getBean("CommonServiceImpl");
		try {
			serv.removeCache(Variable.CACHE_CONTENT_SESSION.getKey(), event.getSession().getId()) ;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}
