package com.kingschan.blog.util;

import org.springframework.web.context.ContextLoader;

/**
 * 
*    
* 类名称：SpringUtil   
* 类描述：   
* 创建人：kings.chan
* 创建时间：2016-7-27 上午10:19:27   
* 修改人：   
* 修改时间：
* 项目：ROOT
* 修改备注：   
* @version    
*
 */
@SuppressWarnings("unchecked")
public class SpringUtil {

	
	public static <T> T getBean(Class<?> clazz){
		return (T) ContextLoader.getCurrentWebApplicationContext().getBean(clazz);
	}
	
	public static <T> T getBean(String id){
		return (T) ContextLoader.getCurrentWebApplicationContext().getBean(id);
	}
}
