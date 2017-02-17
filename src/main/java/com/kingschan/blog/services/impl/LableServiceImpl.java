package com.kingschan.blog.services.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kingschan.blog.dao.LableDao;
import com.kingschan.blog.dao.Pagination;
import com.kingschan.blog.services.LableService;
/**
 * 
*  <pre>    
* 类名称：LableServiceImpl 
* 类描述：   
* 创建人：陈国祥   (kingschan)
* 创建时间：2016-2-20 下午2:56:09   
* 修改人：Administrator   
* 修改时间：2016-2-20 下午2:56:09   
* 修改备注：   
* @version V1.0
* </pre>
 */
@Service
public class LableServiceImpl implements LableService {

    @Autowired
    private LableDao lable_dao;
    @Override
    public List<Map<String, Object>> getHotLableList(String websiteid,int limit,String orderby ) throws Exception {
        return lable_dao.getHotLableList(websiteid,limit,orderby);
    }
    @Override
    public int delArticleLable(String id) throws Exception {
        return lable_dao.delArticleLable(id);
    }
	@Override
	public Pagination getLableArticleItemList(String lableName,String websiteid,
			int limit,int page, String orderby) throws Exception {
		return lable_dao.getLableArticleItemList(lableName,websiteid, limit, page, orderby);
	}

}
