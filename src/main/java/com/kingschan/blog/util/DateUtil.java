package com.kingschan.blog.util;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 
*  <pre>    
* 类名称：DateUtil 
* 类描述：   日期工具类
* 创建人：陈国祥   (kingschan)
* 创建时间：2013-6-7 上午10:47:17   
* 修改人：Administrator   
* 修改时间：2013-6-7 上午10:47:17   
* 修改备注：   
* @version V1.0
* </pre>
 */

public class DateUtil {
	
	public enum Format {
		Month, Year, Day
	}

	/**
	 * 得到当前系统时间yyyy-MM-dd HH:mm:ss
	 * 
	 * @return yyyy-MM-dd HH:mm:ss
	 */
	public static String getCurrentDateTime() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(
				System.currentTimeMillis()));
	}

	/**
	 * 得到当前系统的年月日时间
	 * 
	 * @return  YYYY-MM-DD
	 */
	public static String getCurrentDate() {
		return new SimpleDateFormat("yyyy-MM-dd").format(new Date(System
				.currentTimeMillis()));
	}

	/**
	 * 返回两个时间相差的秒数 
	 * @param beginDate
	 * @param endDate
	 * @return	秒数
	 */
	public static Long secondsDifference(long beginDate, long endDate) {
		if (endDate > beginDate) {
			return (endDate - beginDate) / 1000;
		} else {
			return Long.valueOf(0);
		}
	}

	/**
	 * 根据格式枚举格式化当前时间(Month, Year, Day)
	 * @param format
	 * @return  返回传入的格式
	 */
	public static String formatCurrentDate(Format format) {
		String dateStr = "";
		switch (format) {
		case Year:
			dateStr = new SimpleDateFormat("yyyy").format(new Date(System
					.currentTimeMillis()));
			break;
		case Month:
			dateStr = new SimpleDateFormat("MM").format(new Date(System
					.currentTimeMillis()));
			break;
		case Day:
			dateStr = new SimpleDateFormat("dd").format(new Date(System
					.currentTimeMillis()));
			break;
		default:
			break;
		}
		return dateStr;
	}

	/**  
     * 格式化日期  
     * @param date 日期对象  
     * @return String 日期字符串  
     */  
    public static String formatDate(Date date){   
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");   
        String sDate = f.format(date);   
        return sDate;   
    }   
	/**
	 * 在传入的日期基础上加天数
	 * 
	 * @param dateStr
	 *            日期字符串
	 * @param days
	 *            天数 传入负数则为减
	 * @return 在传入的日期基础上加天数
	 * @throws ParseException
	 */	
	public static String appendDate(String dateStr, int days) throws ParseException {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(df.parse(dateStr));
		cal.add(Calendar.DAY_OF_MONTH, days);
		return df.format(cal.getTime());
	}

	/**
	 * 在传入的日期基础上加月数
	 * 
	 * @param dateStr
	 *            日期字符串 yyyy-MM-dd
	 * @param Months
	 *            月数 传入负数则为减
	 * @return	计算后的时间 yyyy-MM-dd
	 * @throws ParseException
	 */
	
	public static String appendMonth(String dateStr, int Months)
			throws ParseException {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		Calendar cal = Calendar.getInstance();
		cal.setTime(df.parse(dateStr));
		cal.add(Calendar.MONTH, Months);
		return df.format(cal.getTime());
	}

	/**
	 * 得到本月最大天数 
	 * @return 本月多少天
	 */
	public static int getCurrentMonthMaxDay() {
		Calendar time = Calendar.getInstance();
		time.clear();
		time.set(Calendar.YEAR, Integer.valueOf(DateUtil
				.formatCurrentDate(Format.Year))); // year 为 int
		time.set(Calendar.MONTH, Integer.valueOf(DateUtil
				.formatCurrentDate(Format.Month)) - 1);// 注意,Calendar对象默认一月为0
		int day = time.getActualMaximum(Calendar.DAY_OF_MONTH);// 本月份的天数
		return day;
	}

	/**
	 * <pre>
	 * 得到当前日期是星期几 
	 switch (dayOfWeek) { 
	 case 1: 
	 		System.out.println("星期日");break; 
	  case 2: 
	  		System.out.println("星期一"); break; 
	  case 3:
	   		System.out.println("星期二"); break; 
	  case 4: 
	  		System.out.println("星期三"); break; 
	  case 5: 
	  		System.out.println("星期四"); break; 
	  case 6: 
	  		System.out.println("星期五"); break; 
	  case 7: System.out.println("星期六");
	  break;
	  }
	  </pre>
	 *
	 */
	
	public static int getCurrentWeekDay() {
		Calendar c = Calendar.getInstance();
		c.setTime(new Date(System.currentTimeMillis()));
		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);		
		return dayOfWeek - 1;
	}

	

	/**  
     * 获取某年第一天日期  
     * @param year 年份  
     * @return Date  传入年份的第一天
     */  
    public static Date getCurrYearFirst(int year){   
        Calendar calendar = Calendar.getInstance();   
        calendar.clear();   
        calendar.set(Calendar.YEAR, year);   
        Date currYearFirst = calendar.getTime();   
        return currYearFirst;   
    }   
       
    /**  
     * 获取某年最后一天日期  
     * @param year 年份  
     * @return Date  传入年份的最后一天
     */  
    public static Date getCurrYearLast(int year){   
        Calendar calendar = Calendar.getInstance();   
        calendar.clear();   
        calendar.set(Calendar.YEAR, year);   
        calendar.roll(Calendar.DAY_OF_YEAR, -1);   
        Date currYearLast = calendar.getTime();             
        return currYearLast;   
    }   
  /**
   * 得到本月第一天
   * @return  本月第一天
   */
    public static String getCurrentMonthFirstDay(){
    	Calendar c = Calendar.getInstance();    
        c.add(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天 
        String first = DateUtil.formatDate(c.getTime());
        return first;
    }
	/**
	 * 得到本月最后一天
	 * @return 本月最后一天
	 */
    public static String getCurrentMonthLastDay(){
    	Calendar ca = Calendar.getInstance();    
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));  
        String last = DateUtil.formatDate(ca.getTime());
        return last;
    }
    	
    public void getCallInfo() {
        StackTraceElement stack[] = Thread.currentThread().getStackTrace();
        System.out.println(String.format("调用对象：%s.%s", stack[2].getClassName(),stack[2].getMethodName()));
    }
    /**
     * 计算两日期差
     * @param date1 
     * @param date2
     * @return 返回相差天数
     */
    public static long dateDiff(Date date1,Date date2){
    	long diff = date1.getTime() - date2.getTime();
        long days = diff / (1000 * 60 * 60 * 24);
        return days;
    }
    /**
     * 计算两日期差
     * @param date1
     * @param date2
     * @return 返回相差天数
     */
    public static long dateDiff(String date1,String date2){
         DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
         Date d1 = null;
         Date d2 = null;
         try {
        	  d1 = df.parse(date1);
              d2 = df.parse(date2);
		} catch (Exception e) {
			e.printStackTrace();
		}
         return dateDiff(d1, d2);
    }
   /**
    * 得到人性化提示时间差
    * @param times
    * @return
    */
    public static String timeSummary(String times){
		Timestamp st =TimeStampUtil.convertStringToTimeStamp(times);
		String shortTime=new SimpleDateFormat("HH:mm").format(st);
		long time1=st.getTime();
		long time2 =System.currentTimeMillis();
		double result=(time2-time1)/1000;//这个是秒数
		double year=60*60*24*365;
		double month=60*60*24*30;
		double week=60*60*24*7;
		double day=60*60*24;
		double hour=60*60;
		double minute=60;
		String s="";
		if (result>year) {
			double d= result/year;
			if (result%year!=0) {
				String t=String.valueOf(d).replaceAll("\\d+\\.", "");
				t=t.substring(0,1);
				int td=Integer.valueOf(t);
				if (td>=5&&td<=7) {
					return s=String.format("%s年半前", (int)d);
				}
			}
			s=String.format("%.0f年前", d);
		}else if (result>month) {
			int d =(int) (result/month);
			if (d==1) {
				s="上个月";
			}else if (d>=6&&d<=7) {
				s="半年前";
			}else{
				s=String.format("%s个月前",d);
			}
		}else if (result>week){
			int d =(int) (result/week);
			if (d==1) {
				s="上个星期";
			}else if (d==2||d==3) {
				s="半个月前";
			}else{
				s=String.format("%s天前", (int) (result/day));
			}
		}else if (result>day){
			int d =(int) (result/day);
			if (d==1) {
				s=String.format("昨天%s", shortTime);
			}else if (d==2) {
				s=String.format("前天%s", shortTime);
			}else{
				s=String.format("%s天前", d);
			}
			
		}else if (result>hour){
			s=String.format("%.0f个小时前", result/hour);
		}else if (result>minute){
			int d =(int) (result/minute);
			if (d>=25&&d<=40) {
				s="半个小时前";
			}else{
				s=String.format("%s分钟前",d);
			}
		}else{
			s="刚刚";
		}
		return s;
    }
    
}
