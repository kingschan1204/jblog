package com.kingschan.blog.util;

import java.util.UUID;

/**
 * 
 * <pre>    
* 类名称：StringUtil 
* 类描述：   字符串工具类
* 创建人：陈国祥   (kingschan)
* 创建时间：2012-12-04 上午11:27:16   
* 修改人：Administrator   
* 修改时间：2012-12-04 上午11:27:16  
* 修改备注：   
* @version V1.0
* </pre>
 */
public class StringUtil {

    /**
     * 将初始二进制字符串转换成字符串数组，以空格相隔
     * 
     * @param str
     * @return 字符串数组
     */
    public static String[] StrToStrArray(String str) {
        return str.split(" ");
    }

    /**
     * 将二进制字符串转换成int数组
     * 
     * @param binStr
     * @return Int数组
     */
    public static int[] BinstrToIntArray(String binStr) {
        char[] temp = binStr.toCharArray();
        int[] result = new int[temp.length];
        for (int i = 0; i < temp.length; i++) {
            result[i] = temp[i] - 48;
        }
        return result;
    }

    /**
     * 将二进制字符串转换成Unicode字符串
     * 
     * @param binStr
     * @return unicode编码
     */
    public static String BinstrToStr(String binStr) {
        String[] tempStr = StrToStrArray(binStr);
        char[] tempChar = new char[tempStr.length];
        for (int i = 0; i < tempStr.length; i++) {
            tempChar[i] = BinstrToChar(tempStr[i]);
        }
        return String.valueOf(tempChar);
    }

    /**
     * 将二进制字符串转换为char
     * 
     * @param binStr
     * @return char
     */
    public static char BinstrToChar(String binStr) {
        int[] temp = BinstrToIntArray(binStr);
        int sum = 0;
        for (int i = 0; i < temp.length; i++) {
            sum += temp[temp.length - 1 - i] << i;
        }
        return (char) sum;
    }

    /**
     * Ascii码转成字符串
     * 
     * @param value
     * @return string
     */
    public static String asciiToString(String value) {
        StringBuffer sbu = new StringBuffer();
        String[] chars = value.split(",");
        for (int i = 0; i < chars.length; i++) {
            sbu.append((char) Integer.parseInt(chars[i]));
        }
        return sbu.toString();
    }

    /**
     * 将字符串转换成二进制字符串，以空格相隔
     * 
     * @param str
     * @return 二进制字符串
     */
    public static String toBinary(String str) {
        char[] strChar = str.toCharArray();
        String result = "";
        for (int i = 0; i < strChar.length; i++) {
            result += Integer.toBinaryString(strChar[i]) + " ";
        }
        return result;
    }

    /**
     * 将字符串转成ASCII码
     * 
     * @param value
     * @return ascii码
     */
    public static String toAscii(String value) {
        StringBuffer sbu = new StringBuffer();
        char[] chars = value.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (i != chars.length - 1) {
                sbu.append((int) chars[i]).append(",");
            } else {
                sbu.append((int) chars[i]);
            }
        }
        return sbu.toString();
    }

    /**
     * 转UTF-8
     * 
     * @param inPara 需要转换的gb2312中文字符
     * @return 该中文字符对应的UTF-8编码的字符
     */
    public static String toUTF8(String inPara) {
        char temChr;
        int ascChr;
        int i;
        String rtStr = new String("");
        if (inPara == null) {
            inPara = "";
        }
        for (i = 0; i < inPara.length(); i++) {
            temChr = inPara.charAt(i);
            ascChr = temChr + 0;
            rtStr = rtStr + "&#x" + Integer.toHexString(ascChr) + ";";
        }
        return rtStr;
    }

   


    /**
     * 把null转成 ""
     * 
     * @param obj 对象
     * @return 空字符串
     */
    public static String null2Empty(Object obj) {
        if (null == obj) {
            return "";
        } else {
            return obj.toString().trim();
        }

    }
    /**
     * 把null转成 ""
     * @param obj
     * @return
     */
    @Deprecated
    public static String nullToEmpty(Object obj) {
        return null2Empty(obj);
    }
    /**
     * 去掉所有 空格 换行 回车
     * 
     * @param obj 对象
     * @return 字符串
     */
    public static String replaceSpaceLine(Object obj) {
        return null2Empty(obj).replaceAll("\\s*|/t|/r|/n", "");
    }


    /**
     * 将已逗号分隔的字符串转成适合sql in(xxxxx)的格式
     * 
     * @param str a,b,c,d...
     * @return 'a','b','c'...
     */
    public static String convertStrToSqlInstr(String str, boolean isNumberic) {
        StringBuffer sb = new StringBuffer();
        String[] args = str.split(",");
        for (int i = 0; i < args.length; i++) {
            sb.append("'");
            sb.append(args[i]);
            sb.append("'");
            if (i != args.length - 1) {
                sb.append(",");
            }
        }
        String temp = sb.toString();
        if (isNumberic) {
            temp = temp.replace("'", "");
        }
        return temp;
    }

    /**
     * 得到一个32位的UUID
     * 
     * @return 32uuid
     */
    public static String getUUID() {
        UUID u = UUID.randomUUID();
        return u.toString().replace("-", "");
    }

    /**
     * 得到一个字符串的长度,显示的长度,一个汉字或日韩文长度为1,英文字符长度为0.5  
     * @param s
     * @return
     */
    public static double getLength(String s) {  
	    double valueLength = 0;    
	       String chinese = "[\u4e00-\u9fa5]";    
	       // 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1    
	       for (int i = 0; i < s.length(); i++) {    
	           // 获取一个字符    
	           String temp = s.substring(i, i + 1);    
	           // 判断是否为中文字符    
	           if (temp.matches(chinese)) {    
	               // 中文字符长度为1    
	               valueLength += 1;    
	           } else {    
	               // 其他字符长度为0.5    
	               valueLength += 0.5;    
	           }    
	       }    
	       //进位取整    
	       return  Math.ceil(valueLength);    
	   } 
    /**
     * 返回指定长度的字符串
     * @param s
     * @param length
     * @param overflow 如果超出长度在后面追加指定的字符串
     * @return
     */
    public static String getLengthOfSub(String s,int length,String overflow) {  
	    double valueLength = 0;    
	    StringBuffer sb = new StringBuffer();
	       String chinese = "[\u4e00-\u9fa5]";    
	       // 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1    
	       for (int i = 0; i < s.length(); i++) {    
	           // 获取一个字符    
	           String temp = s.substring(i, i + 1);    
	           // 判断是否为中文字符    
	           if (temp.matches(chinese)) {    
	               // 中文字符长度为1    
	               valueLength += 1;    
	           } else {    
	               // 其他字符长度为0.5    
	               valueLength += 0.5;    
	           }   
	           if (valueLength<=length) {
	        	   sb.append(temp);
	           }else{
	        	   if (null!=overflow&&!overflow.isEmpty()) {
	        		   sb.append(overflow);
				}
	        	   break;
	           }
	           
	       }    
	       return  sb.toString();    
	   }
}
