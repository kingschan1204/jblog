package com.kingschan.blog.common.bean.convert;

public interface BeanConvert {

	/**
	 * po 2 vo 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	<T> T po2vo(T obj)throws Exception;
}
