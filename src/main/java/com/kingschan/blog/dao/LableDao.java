package com.kingschan.blog.dao;

import java.util.List;
import java.util.Map;

import com.kingschan.blog.po.Lable;

/**
 * 
*  <pre>    
* 类名称：LableDao 
* 类描述：   
* 创建人：陈国祥   (kingschan)
* 创建时间：2016-2-20 下午1:58:50   
* 修改人：Administrator   
* 修改时间：2016-2-20 下午1:58:50   
* 修改备注：   
* @version V1.0
* </pre>
 */
public interface LableDao extends BaseDao<Lable>{
    
    /**
     * 得到热门的标签
     * @param websiteid
     * @return
     * @throws Exception
     */
    List<Map<String, Object>> getHotLableList(String websiteid, int limit, String orderby)throws Exception;
    
    /**
     * 标签文章明细
     * @param lableName
     * @param websiteid
     * @param limit
     * @param page
     * @param orderby
     * @return
     * @throws Exception
     */
    Pagination getLableArticleItemList(String lableName, String websiteid, int limit, int page, String orderby) throws Exception;
    
    /**
     * 删除文章标签
     * @param id
     * @throws Exception
     */
    int delArticleLable(String id)throws Exception;

    /**
     * get label quantity
     * @param websiteId
     * @param labName
     * @return
     * @throws Exception
     */
    int getLableQuantityByName(String websiteId,String labName)throws Exception;

}
