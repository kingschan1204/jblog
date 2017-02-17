package com.kingschan.blog.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 
 * <pre>
 * 类名称：UnixDate 
 * 类描述：   unix时间戳
 * 创建人：陈国祥   
 * 创建时间：2014-7-2 下午3:37:43   
 * 修改人：Administrator   
 * 修改时间：2014-7-2 下午3:37:43   
 * 修改备注：   
 * @version V1.0
 * long date=UnixDate.getCurrentDate();
 * System.out.println(date);
 * System.out.println(UnixDate.formatUnixDate(1404873607));
 * </pre>
 */
public class UnixDate {
	/**
	 * 当前时间戳
	 * 
	 * @return 10位的数字
	 */
	public long getCurrentDate() {
		long date = System.currentTimeMillis() / 1000;
		return date;
	}

	
	
	/**
	 * 输入一个时间,获取该时间的时间戳
	 * @param dateString 日期字符串(yyyy-MM-dd HH:mm:ss)
	 * @return long类型的10位数字
	 * @throws ParseException
	 */
	public long dateToUnixTime(String dateString) throws ParseException {
		Date date1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
				.parse(dateString);// HH:mm:ss
		long temp = date1.getTime();// JAVA的时间戳长度是13位
		return temp / 1000;
	}

	/**
	 * 格式化unix 时间戳成日期字符串(yyyy-MM-dd HH:mm:ss)
	 * 
	 * @param time
	 *            10位
	 * @return yyyy-MM-dd HH:mm:ss的字符串
	 */
	public String formatUnixDate(long time) {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(
				time * 1000));
	}

}
