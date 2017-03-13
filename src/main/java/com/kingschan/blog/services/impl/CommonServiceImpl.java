package com.kingschan.blog.services.impl;

import java.util.Date;

import javax.annotation.Resource;
import javax.mail.internet.MimeMessage;

import net.sf.ehcache.Element;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.kingschan.blog.dao.HibernateBaseDao;
import com.kingschan.blog.po.RequestLog;
import com.kingschan.blog.services.CommonService;
import com.kingschan.blog.util.TimeStampUtil;
/**
 * 通用service实现
 * @author kings.chan
 *
 */
@Service("CommonServiceImpl")
public class CommonServiceImpl implements CommonService {

	@Autowired
	private EhCacheCacheManager em;
	@Qualifier("HibernateBaseDao")
	@Autowired
	private HibernateBaseDao baseDao;
	@Value("${app.debug}")
    protected boolean debug;//是否开发模式
	/**
	 * 部署域名
	 */
	@Value("${app.host}")
	protected String host;

	public String getCdnhost() {
		return cdnhost;
	}

	/**
	 * CDN文件加速域名
	 */
	@Value("${app.cdn.host}")
	protected String cdnhost;
	/**
	 * 文件上传处理临时目录
	 */
	@Value("${app.file.temp.folder}")
	protected String fileTempFolder;
	
	public boolean isDebug() {
		return debug;
	}

	public String getHost() {
		return host;
	}
	
	public String getFileTempFolder() {
		return fileTempFolder;
	}

	/**
	 * 返回cookie 共享顶级域名
	 * @return
	 */
	public String getShareCookHost(){
		//如果是本地开发模式，配置的localhost那就不要加点，不然登录会有问题
		if (host.contains("localhost")) {
			return host;
		}
		return String.format(".%s", host);
	}
	
	@Override
	public boolean cacheContrainKey(String cacheName, String key)
			throws Exception {
		Element et= em.getCacheManager().getCache(cacheName).get(key);
		return null==et.getObjectValue();
	}

	@Override
	public void addCache(String cacheName, String key, Object value)throws Exception {
		Element et=new Element(key, value);
		em.getCacheManager().getCache(cacheName).put(et);
	}

	@Override
	public Object getCache(String cacheName, String key) throws Exception {
		Element et= em.getCacheManager().getCache(cacheName).get(key);
		return null==et||null==et.getObjectValue()?null:et.getObjectValue();
	}

	@Override
	public void removeCache(String cacheName, String key) throws Exception {
		em.getCacheManager().getCache(cacheName).remove(key);
	}

	@Override
	public void addHttpRequestLog(String url, String method, String ip,
			String agent, String referer, int runmills,String blog) throws Exception {
		RequestLog log = new RequestLog();
		log.setReqUrl(url);
		log.setReqMethod(method);
		log.setReqIp(ip);
		log.setReqAgent(agent);
		log.setReqReferer(referer);
		log.setReqRunTime(runmills);
		log.setReqDatetime(TimeStampUtil.getCurrentDate());
		log.setReqBlog(blog);
		baseDao.save(log);
		
	}

	@Override
	public Object get(Class<?> clazz, Object id) throws Exception {
		return baseDao.get(clazz, id);
	}
	
	


	
}
