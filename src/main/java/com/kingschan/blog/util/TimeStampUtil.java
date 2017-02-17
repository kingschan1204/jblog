package com.kingschan.blog.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * 
*  <pre>    
* 类名称：TimeStampUtil 
* 类描述：   TimeStamp工具类
* 创建人：陈国祥   (kingschan)
* 创建时间：2014-7-31 上午9:21:11   
* 修改人：Administrator   
* 修改时间：2014-7-31 上午9:21:11   
* 修改备注：   
* @version V1.0
* </pre>
 */
public class TimeStampUtil {
	/**
	 * 得到当前系统时间(yyyy-MM-dd hh:mm:ss)
	 * @return timestamp对象
	 */
	public static Timestamp getCurrentDate(){
	    Timestamp time = new Timestamp(System.currentTimeMillis()); 
		return time;
	}
	/**
	 * 将日期字符串格式化成timeStamp
	 * @param date  日期字符串(yyyy-MM-dd HH:mm:ss)
	 * @return timeStamp对象
	 * @throws ParseException 
	 */
	public static Timestamp convertStringToTimeStamp(String date) {
		//19
		if (null==date||date.isEmpty()) {
			return null;
		}
		Timestamp ts = null;
		if (date.indexOf("/")!=-1) {
			date=date.replace("/", "-");
		}
		try {
			SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
			Date date11 = df1.parse(date);
			String time = df1.format(date11); 
			ts = Timestamp.valueOf(time); 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ts;
	}
	/**
	 * 将timestamp转换成date
	 * @author hellostoy
	 * @param tt
	 * @return
	 */
	public static Date timestampToDate(Timestamp tt){
		return new Date(tt.getTime());
	}
	/**
	 * timestamp转字符串
	 * @param time
	 * @return
	 */
	public static String timestampToString(Timestamp time){
		DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		return sdf.format(time);
	}
}
