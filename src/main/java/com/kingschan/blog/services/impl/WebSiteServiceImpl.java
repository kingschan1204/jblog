package com.kingschan.blog.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingschan.blog.model.vo.UserVo;
import com.kingschan.blog.model.vo.WebSiteVo;
import com.kingschan.blog.dao.WebSiteDao;
import com.kingschan.blog.po.User;
import com.kingschan.blog.po.WebSite;
import com.kingschan.blog.services.WebSiteService;
@Service
public class WebSiteServiceImpl implements WebSiteService {

    @Autowired
    private WebSiteDao website_dao;
    
    @Override
    public WebSite getWebSite(String keyword) throws Exception {
        return website_dao.getWebSite(keyword);
    }

    @Override
    public WebSite saveWebSite(WebSiteVo vo,User user) throws Exception {
        WebSite ws=null;
        if (null==vo.getId()||vo.getId().isEmpty()) {
            ws=new WebSite();
        }else{
            ws=website_dao.getWebSite(vo.getId());
        }
        BeanUtils.copyProperties(vo, ws, "websiteDatetime","websiteName","websiteAnalyticscode");
        ws.setUser(user);
        website_dao.saveWebSite(ws);
        return ws;
    }

	@Override
	public List<WebSiteVo> getWebSiteList() throws Exception {
		List<WebSiteVo> lis = new ArrayList<WebSiteVo>();
		List<WebSite> data= website_dao.getWebSiteList();
		WebSiteVo temp =null;
		for (WebSite webSite : data) {
			temp = new WebSiteVo();
			User u = webSite.getUser();
			UserVo uvo = new UserVo();
			BeanUtils.copyProperties(webSite, temp);
			BeanUtils.copyProperties(u, uvo,"sinaExpireIn");
			temp.setUser(uvo);
			lis.add(temp);
		}
		return lis;
	}

	@Override
	public void analysisRequestAgent() throws Exception {
		website_dao.analysisRequestAgent();
	}

}
