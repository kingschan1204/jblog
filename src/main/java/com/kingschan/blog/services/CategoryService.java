package com.kingschan.blog.services;

import java.util.List;

import com.kingschan.blog.model.vo.CategoryVo;
import com.kingschan.blog.po.Category;
import com.kingschan.blog.po.User;

/**
 * 
*  <pre>    
* 类名称：CategoryService 
* 类描述：   
* 创建人：陈国祥   (kingschan)
* 创建时间：2016-2-20 上午10:36:17   
* 修改人：Administrator   
* 修改时间：2016-2-20 上午10:36:17   
* 修改备注：   
* @version V1.0
* </pre>
 */
public interface CategoryService {
    /**
     * 得到博客类型列表
     * @param websiteid
     * @return
     * @throws Exception
     */
    List<Category> getCategoryList(String websiteid)throws Exception;
    
    /**
     * 统计分类
     * @param websiteid
     * @return
     * @throws Exception
     */
    List<CategoryVo> countCategory(String websiteid)throws Exception;
    
    /**
     * 验证类型名字是否能用
     * @param websiteid
     * @param name
     * @return
     * @throws Exception
     */
//    Boolean uniqueCategoryName(String websiteid,String name)throws Exception;
    
    /**
     * 保存category
     * @param vo
     * @throws Exception
     */
    void saveCategory(CategoryVo vo, String websiteid, User user)throws Exception;
    
    
    /**
     * 删除类型
     * @param ids
     * @return
     * @throws Exception
     */
    int delCategory(String[] ids, String websiteId)throws Exception;
}
