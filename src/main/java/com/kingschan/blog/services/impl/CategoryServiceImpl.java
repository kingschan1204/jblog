package com.kingschan.blog.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.kingschan.blog.model.vo.CategoryVo;
import com.kingschan.blog.dao.CategoryDao;
import com.kingschan.blog.po.Category;
import com.kingschan.blog.po.User;
import com.kingschan.blog.services.CategoryService;
import com.kingschan.blog.util.TimeStampUtil;
/**
 * 
*  <pre>    
* 类名称：CategoryServiceImpl 
* 类描述：   博客类型服务提供
* 创建人：陈国祥   (kingschan)
* 创建时间：2016-2-20 上午10:37:11   
* 修改人：Administrator   
* 修改时间：2016-2-20 上午10:37:11   
* 修改备注：   
* @version V1.0
* </pre>
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Qualifier("CategoryDaoImpl")
    @Autowired
    private CategoryDao category_dao;
    
    @Override
    public List<Category> getCategoryList(String websiteid) throws Exception {
        return category_dao.getCategoryList(websiteid);
    }

	@Override
	public void saveCategory(CategoryVo vo,String websiteid,User user) throws Exception {
		Category ca = null;
		if (null==vo.getId()||vo.getId().isEmpty()) {
			ca = new Category();
			ca.setCategoryDatetime(TimeStampUtil.getCurrentDate());
			ca.setCategoryWebsiteid(websiteid);
			ca.setUser(user);
		}else{
			ca=category_dao.getObj(vo.getId());
		}
		BeanUtils.copyProperties(vo, ca);
		category_dao.saveCategory(ca);
	}

	@Override
	public int delCategory(String[] ids,String websiteId) throws Exception {
		return category_dao.delCategory(ids,websiteId);
	}

	@Override
	public List<CategoryVo> countCategory(String websiteid) throws Exception {
		List<Map<String, Object>> lis= category_dao.countCategory(websiteid);
		List<CategoryVo> list = new ArrayList<CategoryVo>();
		for (Map<String, Object> row : lis) {
			CategoryVo vo=new CategoryVo(row.get("id").toString(), row.get("name").toString(), Integer.valueOf(row.get("total").toString()));
			vo.setCategoryRemark(row.get("remark").toString());
			list.add(vo);
		}
		return list;
	}

	

}
