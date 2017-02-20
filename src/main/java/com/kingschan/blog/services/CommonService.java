package com.kingschan.blog.services;
/**
 * 通用service
 * @author kings.chan
 *
 */
public interface CommonService {

	/**
	 * 指定的缓存容器是否包含指定的key
	 * @param cacheName ehcache配置的name
	 * @param key key
	 * @return
	 * @throws Exception
	 */
	boolean cacheContrainKey(String cacheName, String key)throws Exception;
	
	
	/**
	 * 向指定的缓存容器存放对象
	 * @param cacheName
	 * @param key
	 * @param value
	 * @throws Exception
	 */
	void addCache(String cacheName, String key, Object value)throws Exception;
	
	/**
	 * 移除某一项
	 * @param cacheName
	 * @param key
	 * @throws Exception
	 */
	void removeCache(String cacheName, String key)throws Exception;
	
	
	/**
	 * 得到缓存
	 * @param cacheName
	 * @param key
	 * @return
	 * @throws Exception
	 */
	Object getCache(String cacheName, String key)throws Exception;
	
	
	/**
	 * 记录http访问日志
	 * @param url
	 * @param method
	 * @param ip
	 * @param agent
	 * @param referer
	 * @param runmills
	 * @param blog
	 * @throws Exception
	 */
	void addHttpRequestLog(String url, String method, String ip, String agent, String referer, int runmills, String blog)throws Exception;
	
	
	
	/**
	 *  得到一个实体
	 * @param clazz
	 * @param id
	 * @return
	 * @throws Exception
	 */
	 Object get(Class<?> clazz, Object id) throws Exception;
	 
	 

}
