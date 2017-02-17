package com.kingschan.blog.services;

import java.util.List;
import java.util.Map;

import com.kingschan.blog.dao.Pagination;

/**
 * 
*  <pre>    
* 类名称：LableService 
* 类描述：   
* 创建人：陈国祥   (kingschan)
* 创建时间：2016-2-20 下午2:55:07   
* 修改人：Administrator   
* 修改时间：2016-2-20 下午2:55:07   
* 修改备注：   
* @version V1.0
* </pre>
 */
public interface LableService {
    /**
     * 得到热门的标签
     * @param websiteid
     * @return
     * @throws Exception
     */
    List<Map<String, Object>> getHotLableList(String websiteid, int limit, String orderby) throws Exception;
    
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
     * 删除文单标签
     * @param id
     * @return
     * @throws Exception
     */
    int delArticleLable(String id)throws Exception;
}
