package com.kingschan.blog.common.freemarker.directive;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Calendar;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kingschan.blog.services.ArticleService;
import com.kingschan.blog.util.DateUtil;

import freemarker.core.Environment;
import freemarker.template.TemplateDirectiveBody;
import freemarker.template.TemplateDirectiveModel;
import freemarker.template.TemplateException;
import freemarker.template.TemplateModel;

/**
 * 
*  <pre>    
* 类名称：CalendarDirective 
* 类描述：   日期控件
* 创建人：陈国祥   (kingschan)
* 创建时间：2016-3-1 下午1:51:46   
* 修改人：Administrator   
* 修改时间：2016-3-1 下午1:51:46   
* 修改备注：   
* @version V1.0
* </pre>
 */
@Component("Calendar")
public class CalendarDirective implements TemplateDirectiveModel{

    @Autowired
    private ArticleService article_serv;
    
    @Override
    public void execute(Environment env, @SuppressWarnings("rawtypes") Map params, TemplateModel[] tm, TemplateDirectiveBody body)
            throws TemplateException, IOException {
        String date =params.containsKey("date")&&params.get("date").toString().matches("\\d+")?params.get("date").toString():null;
        String websiteid =params.containsKey("websiteid")&&params.get("websiteid").toString().matches("\\w+")?params.get("websiteid").toString():"";
        String url =params.containsKey("url")&&!params.get("url").toString().isEmpty()?params.get("url").toString():"";
        int year =0;
        int month =0;
        if (null!=date) {
            year=Integer.valueOf(date.substring(0, 4));
            month=Integer.valueOf(date.substring(4,6));
        }
       String html=builderHtml(year, month, url,websiteid);
       env.getOut().write(html);
    }

    public Map<String, Object> getData(int year,int month,String websiteid){
        Map<String, Object> data=null;
        try {
        	//使用缓存
             data = article_serv.getEveryDayArticleInfoForCache(String.format("%s,%s,%s", websiteid,year,month));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
    /**
     * 格式化
     * @param year
     * @param month
     * @param day
     * @return
     */
    public String formartDate(int year,int month,int day){
        String t_day=day<10?"0"+day:String.valueOf(day);
        String t_month=month<10?"0"+month:String.valueOf(month);
        return String.format("%s-%s-%s", year,t_month,t_day);
    }
    public String builderHtml(int year,int month,String url,String websiteid){
      //根据日历类对象的方法，实例化一个当前的日历类对象
        Calendar calendar=Calendar.getInstance();
        if (year==0||month==0) {
           year= calendar.get(Calendar.YEAR);
           month= calendar.get(Calendar.MONTH)+1;
        }
        //设置日历对象的年月日
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month-1);//月份是0-11
        calendar.set(Calendar.DATE, 1);
        Map<String, Object> data =getData(year, month, websiteid);
      //得到当前月份的最大值
        int day=calendar.getActualMaximum(Calendar.DATE);
        //得到本月中的第一天是星期几
        int week=calendar.get(Calendar.DAY_OF_WEEK);
        int count=0;//一个计数的变量
        //打印日历的星期
        StringBuffer sb = new StringBuffer();
        sb.append(String.format("<p class='text-center'>%s %s年%s月  %s</p>",dateLink(url, year, month, false), year,month,dateLink(url, year, month, true)));
        sb.append("<table style='text-align: center;'>");
        String strDate[]={"星期日\t","星期一\t","星期二\t","星期三\t","星期四\t","星期五\t","星期六\t"};
        sb.append("<tr>");
        for (int i = 0; i < strDate.length; i++) {
         sb.append("<td><b>").append(strDate[i]).append("</b></td>");
        }
        sb.append("</tr>");
        StringBuffer temp = new StringBuffer();
        //判断第一天对应的是星期几
        while (count<week-1) {
            temp.append("<td></td>");
            count++;//计数变量自增
        }
        //循环打印日历
        for (int i = 1; i <= day; i++,count++) {
         if (count%7==0&&count!=0) {
           sb.append("<tr>").append(temp.toString()).append("</tr>");
           temp = new StringBuffer();
         }
         temp.append("<td>");
         //2016-01-04
        String key =formartDate(year, month, i);
        String t=null;
        //显示当天发布的日志
         if (null!=data&&data.containsKey(key)) {
             BigInteger value=(BigInteger) data.get(key);
              t=String.format("<a href=\"%s/date/%s\" title=\"%s篇\">%d</a>", url,key.replace("-", ""),value,i);
        }else{
            t=String.valueOf(i);
        }
         //如果日期是今天
         if (key.equals(DateUtil.getCurrentDate())) {
             t=String.format("%s%s%s", "<span class=\"badge\">",t,"</span>");
        }
         temp.append(t);
         temp.append("</td>");
             if (i==day) {
                 sb.append("<tr>").append(temp.toString()).append("</tr>");
            }
        }
        sb.append("</table>");
        return sb.toString();
    }
    /**
     * 生成日期连接
     * @param year
     * @param month
     * @param add 加一个月或者减一个月
     * @return
     */
    public String dateLink(String url,int year,int month,boolean add){
        String html="<a href=\"%s/date/%s%s\">%s</a>";
        if (!add) {
            //减一个月
            if (month==1) {
                year--;
                month=12;
            }else{
                month--;
            }
        }else{
            //加一个月
            if (month==12) {
                year++;
                month=1;
            }else{
                month++;
            }
        }
        String t_month=(month<10)?"0"+month:String.valueOf(month);
        return String.format(html, url,year,t_month,add?">>":"<<");
    }
}
