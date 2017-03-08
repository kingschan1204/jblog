package com.kingschan.blog.services.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.kingschan.blog.common.qiniu.QiNiuManagement;
import com.kingschan.blog.dao.BlogResDao;
import com.kingschan.blog.dao.Pagination;
import com.kingschan.blog.po.BlogRes;
import com.kingschan.blog.po.WebSite;
import com.kingschan.blog.services.BlogResService;
import com.kingschan.blog.util.BlogUtil;
import com.kingschan.blog.util.TimeStampUtil;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
@Service
public class BlogResServiceImpl implements BlogResService {

	@Autowired
	private QiNiuManagement qiniu;
	@Qualifier("BlogResDaoImpl")
	@Autowired
	private BlogResDao resdao;
	
	private Logger log =Logger.getLogger(BlogResServiceImpl.class);

	@Override
	public String uploadFile(String filepath, String filename, String filetype,
			HttpServletRequest request) throws Exception {
		File f = new File(filepath);
		BlogUtil blogutil = new BlogUtil(request);
		JSONObject json=qiniu.upload(filepath,null);
		BlogRes br = new BlogRes(filename,filetype,json.getString("key"), json.getString("hash"),
				blogutil.getCurrentAdminWebSite().getId(), TimeStampUtil.getCurrentDate(), 
				blogutil.getCurrentUser().getId(),f.length());
		resdao.saveRes(br);
		f.delete();//删除文件
		return json.getString("key");
	}



	@Override
	public String getResList(int actionCode, int pageindex,HttpServletRequest request) throws Exception {
		BlogUtil blogutil = new BlogUtil(request);
		WebSite ws =blogutil.getCurrentAdminWebSite();
		Pagination page =null;
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("websiteid", ws.getId());
		switch (actionCode) {
		case 7://image
			page =resdao.getResList( "image", pageindex==0?1:pageindex/20, 20,map);
			break;
		case 6://file
			page =resdao.getResList( "file", pageindex==0?1:pageindex/20, 20,map);
			break;
		}
		@SuppressWarnings("unchecked")
		List<BlogRes> lis = (List<BlogRes>) page.getData();
		JSONArray jsons = new JSONArray();
		for (BlogRes res : lis) {
			JSONObject json = new JSONObject();
			json.put("url", res.getResKey());
			json.put("state", "SUCCESS");
			jsons.add(json);
		}
		JSONObject json= new JSONObject();
		json.put("state", "SUCCESS");
		json.put("total", page.getTotal());
		json.put("start", pageindex);
		json.put("list", jsons);
		return json.toString();
	}



	@Override
	public Pagination getResList(int pageindex, int limit,Map<String, Object> map) throws Exception {
		return resdao.getResList( pageindex, limit, map);
	}



	@Override
	public int delBlogRes(String[] keys, String website) throws Exception {
		List<String> lis=resdao.auth(keys, website);
		List<String> res=new ArrayList<String>();
		for (String key : lis) {
			try {
				qiniu.delRes(key);
				res.add(key);
			} catch (QiniuException e) {
				Response re=e.response;
				if (re.error.equals("no such file or directory")){
					res.add(key);
				}
				log.error(re.bodyString());
			}
		}
		if (res.size()>0) {
			String[] ids =new String[]{};
			ids=res.toArray(ids);
			return resdao.delBlogRes(ids, website);
		}
		return 0;
	}



	@Override
	public void rename(String id, String name, String websiteid)
			throws Exception {
		resdao.rename(id, name, websiteid);
	}

	
}
