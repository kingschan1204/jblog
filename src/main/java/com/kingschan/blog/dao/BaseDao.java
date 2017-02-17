package com.kingschan.blog.dao;
/**
 * 
*  <pre>    
* 类名称：BaseDao 
* 类描述：   
* 创建人：陈国祥   (kingschan)
* 创建时间：2016-2-16 下午4:40:45   
* 修改人：Administrator   
* 修改时间：2016-2-16 下午4:40:45   
* 修改备注：   
* @version V1.0
* </pre>
 */
public interface BaseDao<T> {

    void addObj(T obj)throws Exception;
    
    void deleteObj(T obj)throws Exception;
    
    void updateObj(T obj)throws Exception;
    
    T getObj(Object id)throws Exception;
}
