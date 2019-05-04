package com.cn.thinkx.pms.base.utils;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringEscapeUtils;

public class StringUtils
  extends org.apache.commons.lang3.StringUtils
{
  private static final char SEPARATOR = '_';
  private static final String CHARSET_NAME = "UTF-8";
  private static final String HEX_STRING = "0123456789ABCDEF";
  public static byte[] getBytes(String str)
  {
    if (str != null) {
      try
      {
        return str.getBytes("UTF-8");
      }
      catch (UnsupportedEncodingException e)
      {
        return null;
      }
    }
    return null;
  }
  
  public static String toString(byte[] bytes)
  {
    try
    {
      return new String(bytes, "UTF-8");
    }
    catch (UnsupportedEncodingException e) {}
    return "";
  }
  
  public static boolean inString(String str, String... strs)
  {
    if (str != null) {
      for (String s : strs) {
        if (str.equals(trim(s))) {
          return true;
        }
      }
    }
    return false;
  }
  
  public static String replaceHtml(String html)
  {
    if (isBlank(html)) {
      return "";
    }
    String regEx = "<.+?>";
    Pattern p = Pattern.compile(regEx);
    Matcher m = p.matcher(html);
    String s = m.replaceAll("");
    return s;
  }
  
  public static String replaceMobileHtml(String html)
  {
    if (html == null) {
      return "";
    }
    return html.replaceAll("<([a-z]+?)\\s+?.*?>", "<$1>");
  }
  
  
  public static String abbr(String str, int length)
  {
    if (str == null) {
      return "";
    }
    try
    {
      StringBuilder sb = new StringBuilder();
      int currentLength = 0;
      for (char c : replaceHtml(StringEscapeUtils.unescapeHtml4(str)).toCharArray())
      {
        currentLength += String.valueOf(c).getBytes("GBK").length;
        if (currentLength <= length - 3)
        {
          sb.append(c);
        }
        else
        {
          sb.append("...");
          break;
        }
      }
      return sb.toString();
    }
    catch (UnsupportedEncodingException e)
    {
      e.printStackTrace();
    }
    return "";
  }
  
  public static String abbr2(String param, int length)
  {
    if (param == null) {
      return "";
    }
    StringBuffer result = new StringBuffer();
    int n = 0;
    
    boolean isCode = false;
    boolean isHTML = false;
    for (int i = 0; i < param.length(); i++)
    {
      char temp = param.charAt(i);
      if (temp == '<')
      {
        isCode = true;
      }
      else if (temp == '&')
      {
        isHTML = true;
      }
      else if ((temp == '>') && (isCode))
      {
        n -= 1;
        isCode = false;
      }
      else if ((temp == ';') && (isHTML))
      {
        isHTML = false;
      }
      try
      {
        if ((!isCode) && (!isHTML)) {
          n += String.valueOf(temp).getBytes("GBK").length;
        }
      }
      catch (UnsupportedEncodingException e)
      {
        e.printStackTrace();
      }
      if (n <= length - 3)
      {
        result.append(temp);
      }
      else
      {
        result.append("...");
        break;
      }
    }
    String temp_result = result.toString().replaceAll("(>)[^<>]*(<?)", "$1$2");
    
    temp_result = temp_result.replaceAll("</?(AREA|BASE|BASEFONT|BODY|BR|COL|COLGROUP|DD|DT|FRAME|HEAD|HR|HTML|IMG|INPUT|ISINDEX|LI|LINK|META|OPTION|P|PARAM|TBODY|TD|TFOOT|TH|THEAD|TR|area|base|basefont|body|br|col|colgroup|dd|dt|frame|head|hr|html|img|input|isindex|li|link|meta|option|p|param|tbody|td|tfoot|th|thead|tr)[^<>]*/?>", "");
    
    temp_result = temp_result.replaceAll("<([a-zA-Z]+)[^<>]*>(.*?)</\\1>", "$2");
    
    Pattern p = Pattern.compile("<([a-zA-Z]+)[^<>]*>");
    Matcher m = p.matcher(temp_result);
    List<String> endHTML = new ArrayList<String>();
    while (m.find()) {
      endHTML.add(m.group(1));
    }
    for (int i = endHTML.size() - 1; i >= 0; i--)
    {
      result.append("</");
      result.append((String)endHTML.get(i));
      result.append(">");
    }
    return result.toString();
  }
  
  public static Double toDouble(Object val)
  {
    if (val == null) {
      return Double.valueOf(0.0D);
    }
    try
    {
      return Double.valueOf(trim(val.toString()));
    }
    catch (Exception e) {}
    return Double.valueOf(0.0D);
  }
  
  public static Float toFloat(Object val)
  {
    return Float.valueOf(toDouble(val).floatValue());
  }
  
  public static Long toLong(Object val)
  {
    return Long.valueOf(toDouble(val).longValue());
  }
  
  public static Integer toInteger(Object val)
  {
    return Integer.valueOf(toLong(val).intValue());
  }
  
  public static String toCamelCase(String s)
  {
    if (s == null) {
      return null;
    }
    s = s.toLowerCase();
    
    StringBuilder sb = new StringBuilder(s.length());
    boolean upperCase = false;
    for (int i = 0; i < s.length(); i++)
    {
      char c = s.charAt(i);
      if (c == '_')
      {
        upperCase = true;
      }
      else if (upperCase)
      {
        sb.append(Character.toUpperCase(c));
        upperCase = false;
      }
      else
      {
        sb.append(c);
      }
    }
    return sb.toString();
  }
  
  public static String toCapitalizeCamelCase(String s)
  {
    if (s == null) {
      return null;
    }
    s = toCamelCase(s);
    return s.substring(0, 1).toUpperCase() + s.substring(1);
  }
  
  public static String toUnderScoreCase(String s)
  {
    if (s == null) {
      return null;
    }
    StringBuilder sb = new StringBuilder();
    boolean upperCase = false;
    for (int i = 0; i < s.length(); i++)
    {
      char c = s.charAt(i);
      
      boolean nextUpperCase = true;
      if (i < s.length() - 1) {
        nextUpperCase = Character.isUpperCase(s.charAt(i + 1));
      }
      if ((i > 0) && (Character.isUpperCase(c)))
      {
        if ((!upperCase) || (!nextUpperCase)) {
          sb.append('_');
        }
        upperCase = true;
      }
      else
      {
        upperCase = false;
      }
      sb.append(Character.toLowerCase(c));
    }
    return sb.toString();
  }
  
  public static void setValueIfNotBlank(String target, String source)
  {
    if (isNotBlank(source)) {
      target = source;
    }
  }
  
  public static String jsGetVal(String objectString)
  {
    StringBuilder result = new StringBuilder();
    StringBuilder val = new StringBuilder();
    String[] vals = split(objectString, ".");
    for (int i = 0; i < vals.length; i++)
    {
      val.append("." + vals[i]);
      result.append("!" + val.substring(1) + "?'':");
    }
    result.append(val.substring(1));
    return result.toString();
  }
  
  public static String listToString(List<String> list, String separator)
  {
    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < list.size(); i++) {
      sb.append((String)list.get(i)).append(separator);
    }
    return sb.toString().substring(0, sb.toString().length() - 1);
  }
  
  public static boolean isMobile(String str)
  {
    Pattern p = null;
    Matcher m = null;
    boolean b = false;
    p = Pattern.compile("^[1][3,4,5,6,7,8,9][0-9]{9}$");
    m = p.matcher(str);
    b = m.matches();
    return b;
  }
  
  public static boolean isPhone(String str)
  {
    Pattern p1 = null;Pattern p2 = null;
    Matcher m = null;
    boolean b = false;
    p1 = Pattern.compile("^[0][1-9]{2,3}-[0-9]{5,10}$");
    p2 = Pattern.compile("^[1-9]{1}[0-9]{5,8}$");
    if (str.length() > 9)
    {
      m = p1.matcher(str);
      b = m.matches();
    }
    else
    {
      m = p2.matcher(str);
      b = m.matches();
    }
    return b;
  }
  
  public static String nvl(Object obj)
  {
	  if(obj == null)
	  {
		  return "";
	  }
	  return obj.toString();
  }
  
  /**
	 * 获取当前时间
	 * @return
	 */
	public static String getCurTime()
	{
		Date curDate = new Date();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(curDate);
	}
	
	/**  
	 * 将字符串编码成16进制数字
	 */  
	public static String stringToHexString(byte[] bytes) {  
		// 根据默认编码获取字节数组  
		StringBuilder sb = new StringBuilder(bytes.length * 2);
		// 将字节数组中每个字节拆解成2位16进制整数 
		for (int i = 0; i < bytes.length; i++) {
		    sb.append(HEX_STRING.charAt((bytes[i] & 0xf0) >> 4));
		    sb.append(HEX_STRING.charAt((bytes[i] & 0x0f) >> 0));
		}
		return sb.toString();
	}
	
  public static void main(String[] args) throws Exception {
	  String print="设备信息\t 001";
	  System.out.println(stringToHexString(print.getBytes("GBK")));
  }
}
