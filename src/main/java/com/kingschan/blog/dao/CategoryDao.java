package com.kingschan.blog.dao;

import java.util.List;
import java.util.Map;

import com.kingschan.blog.po.Category;
/**
 * 
*  <pre>    
* 类名称：CategoryDao 
* 类描述：   
* 创建人：陈国祥   (kingschan)
* 创建时间：2016-2-20 上午10:13:56   
* 修改人：Administrator   
* 修改时间：2016-2-20 上午10:13:56   
* 修改备注：   
* @version V1.0
* </pre>
 */
public interface CategoryDao extends BaseDao<Category>{
    
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
    List<Map<String,Object>> countCategory(String websiteid)throws Exception;
    
    /**
     * 验证类型名字是否能用
     * @param websiteid
     * @param name
     * @param id 修改时传入id
     * @return
     * @throws Exception
     */
    boolean uniqueCategoryName(String websiteid, String name, String id)throws Exception;
    
    /**
     * 保存类型
     * @param po
     * @throws Exception
     */
    void saveCategory(Category po)throws Exception;
    
    /**
     * 删除类型
     * @param ids
     * @return
     * @throws Exception
     */
    int delCategory(String[] ids, String websiteId)throws Exception;

    /**
     * get a category by a keyword
     * @param website
     * @param keyword
     * @return
     * @throws Exception
     */
    Category getCategoryByKeyword(String website,String keyword)throws Exception;

}
