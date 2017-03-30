package com.kingschan.blog.controller.admin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.kingschan.blog.dao.Pagination;
import com.kingschan.blog.services.ReportService;
import com.kingschan.blog.services.WebSiteService;
import com.kingschan.blog.util.BlogUtil;
import com.kingschan.blog.util.RegexUtil;
import com.kingschan.blog.util.TimeStampUtil;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
/**
 * 
*    
* 类名称：ReportController   
* 类描述：   报表
* 创建人：kings.chan
* 创建时间：2016-9-22 上午11:49:41   
* 修改人：   
* 修改时间：
* 项目：ROOT
* 修改备注：   
* @version    
*
 */
@RequestMapping("/admin/report")
@Controller
public class ReportController {

	private static Logger log = LoggerFactory.getLogger(ReportController.class);
	@Autowired
	private ReportService reportServ;
	@Autowired
	private WebSiteService websiteServ;
	@RequestMapping("/page")
	public String report(){
		return "/admin/report/report";
	}
	
	/**
	 * 日ip pv
	 * @param limit
	 * @param req
	 * @return
	 */
	@RequestMapping("/dayHttpReq")
	@ResponseBody
	public String dayHttpReq(Integer limit,HttpServletRequest req){
		JSONArray jsons=null;
		try {
			BlogUtil bu = new BlogUtil(req);
			List<Map<String, Object>> lis = reportServ.dayHttpReq(limit, bu.getCurrentAdminWebSite().getWebsiteName());
			jsons =JSONArray.fromObject(lis);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsons.toString();
	}
	/**
	 * 博客访问统计
	 * @return
	 */
	@RequestMapping("/sumBlogAccess")
	@ResponseBody
	public String sumBlogAccess(){
		JSONArray jsons=null;
		try {
			List<Map<String, Object>> lis = reportServ.sumBlogAccess();
			jsons =JSONArray.fromObject(lis);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsons.toString();
	}
	/**
	 * 博客代理类型
	 * @return
	 */
	@RequestMapping("/blogAgent")
	@ResponseBody
	public String blogAgent(){
		JSONArray jsons=null;
		try {
			List<Map<String, Object>> lis = reportServ.blogAgent();
			jsons =JSONArray.fromObject(lis);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsons.toString();
	}
	/**
	 * 日志发表日统计
	 * @param limit 显示条数
	 * @param model 为空就是统计个人  反之为全局
	 * @param req
	 * @return
	 */
	@RequestMapping("/dayOfArticleQuantity")
	@ResponseBody
	public String dayOfArticleQuantity(Integer limit,String model,HttpServletRequest req){
		JSONArray jsons=null;
		try {
			BlogUtil bu = new BlogUtil(req);
			List<Map<String, Object>> lis = reportServ.dayOfArticleQuantity(limit,null==model?bu.getCurrentAdminWebSite().getId():null);
			jsons =JSONArray.fromObject(lis);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return jsons.toString();
	}
	
	@RequestMapping("/accessLog")
	public ModelAndView accessLog(Integer limit,Integer page, String ip,String url,String datetime1,String datetime2,String agentname,String os,String device,HttpServletRequest req){
		BlogUtil bu = new BlogUtil(req);
		ModelAndView mav = new ModelAndView("/admin/access_log");
		Map<String, Object> map = new HashMap<String, Object>();
		if (null!=ip&&!ip.isEmpty()) {
			map.put("ip", ip);
			mav.addObject("ip", ip);
		}
		if (null!=url&&!url.isEmpty()) {
			map.put("url", url);
			mav.addObject("url", url);
		}
		if (null!=datetime1&&datetime1.matches(RegexUtil.regex_dateTime)) {
			map.put("datetime1", TimeStampUtil.convertStringToTimeStamp(datetime1));
			mav.addObject("datetime1", datetime1);
		}
		if (null!=datetime2&&!datetime2.isEmpty()) {
			map.put("datetime2", datetime2);
			mav.addObject("datetime2", datetime2);
		}
		if (null!=agentname&&!agentname.isEmpty()) {
			map.put("agentname", agentname);
			mav.addObject("agentname", agentname);
		}
		if (null!=os&&!os.isEmpty()) {
			map.put("os", os);
			mav.addObject("os", os);
		}
		if (null!=device&&!device.isEmpty()) {
			map.put("device", device);
			mav.addObject("device", device);
		}
		Integer pagesize=null==limit?20:limit;
		Integer pageindex=null==page?1:page;
		try {
			Pagination p =reportServ.websiteAccessLog(pagesize, pageindex, bu.getCurrentAdminWebSite().getWebsiteName(), map);
			mav.addObject("page", p);
			mav.addObject("log_lis", p.getData());
			/*mav.addObject("page", pageindex);
			mav.addObject("limit", pagesize);*/
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}
	/**
	 * agent 分析
	 * @param req
	 * @return
	 */
	@RequestMapping("/analysisRequestAgent")
	@ResponseBody
	public String analysisRequestAgent(HttpServletRequest req){
		String result="success";
		try {
			websiteServ.analysisRequestAgent();
		} catch (Exception e) {
			result=e.getMessage();
			e.printStackTrace();
			log.error("analysisRequestAgent",e);
		}
		return result;
	}
	
	/**
	 * 日志占比
	 * @param req
	 * @return
	 */
	@RequestMapping("/articlePercent")
	@ResponseBody
	public String articlePercent(HttpServletRequest req){
		String result="";
		JSONArray jsons=null;
		try {
			List<Map<String, Object>> lis = reportServ.articlePercent();
			jsons =JSONArray.fromObject(lis);
			result=jsons.toString();
		} catch (Exception e) {
			e.printStackTrace();
			result=e.getMessage();
		}
		return result;
	}
}
