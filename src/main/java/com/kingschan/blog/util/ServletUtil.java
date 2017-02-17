package com.kingschan.blog.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

/**
 * 
*  <pre>    
* 类名称：ServletUtil 
* 类描述：   servlet 工具类
* 创建人：陈国祥   (kingschan)
* 创建时间：2013-4-13
* 修改人：Administrator   
* 修改时间：2014-7-31 上午11:42:15   
* 修改备注：   
* @version V1.0
* </pre>
 */
public class ServletUtil {

	  /**
	   * 得到request里面所有的参数
	   * @param request HttpServletRequest
	   * @return map(key:参数名,val:值)
	   */
	public static Map<String, Object> getParameterMap(HttpServletRequest request) {
		Map<String,String[]> properties =  request.getParameterMap();
		Map<String,Object> returnMap = new HashMap<String,Object>();
		Iterator<Entry<String, String[]>> entries = properties.entrySet().iterator();
		Entry<String, String[]> entry;
		String name = "";
		String value = "";
		while (entries.hasNext()) {
			entry = entries.next();
			name = entry.getKey();
			Object valueObj = entry.getValue();
			if(null == valueObj){
				value = "";
			}else if(valueObj instanceof String[]){
				String[] values = (String[])valueObj;
				for(int i=0;i<values.length;i++){
					value = values[i] + ",";
				}
				value = value.substring(0, value.length()-1);
			}else{
				value = valueObj.toString();
			}
			returnMap.put(name, value);
		}
		return returnMap;
	}
	
	
}
