package com.cn.iboot.diy.api.base.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.sql.Clob;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtil extends StringUtils  {

	/**
	 * 格式化字符串
	 * 
	 * 例：formateString("xxx{0}bbb",1) = xxx1bbb
	 * 
	 * @param str
	 * @param params
	 * @return
	 */
	public static String formateString(String str, String... params) {
		for (int i = 0; i < params.length; i++) {
			str = str.replace("{" + i + "}", params[i] == null ? "" : params[i]);
		}
		return str;
	}

	public static String addZero(String str, int len) {
		for (int i = 0; i < len - str.length(); i++) {
			str = "0" + str;
		}
		return str;
	}
    /**
    *
    * 将Object对象转换成字符串，如果对象为空转换成“”
    *
    * @param o 需要转换的Object对象
    *
    * @return 字符串
    */
   public static String nullToString(Object o) {
       if (o!=null) {
          return o.toString();
       }
       return "";
   }
	/**
	 * get comma format of a string like xxx,xxx,xxx.00
	 * 
	 * @param strValue
	 * @return
	 */
	public static String getCommaStr(String strValue) {
		if (strValue == null || strValue.length() == 0) {
			return "";
		}

		try {
			String strInsert = ",";
			String strLeft = "";
			strValue = delCommaStr(strValue);
			if (strValue.startsWith("-")) {
				strLeft = "-";
				strValue = strValue.substring(1);
			}
			int iLength, iDotIndex;
			String strSubValue;
			String strSubAfDot;
			iLength = strValue.length();
			iDotIndex = strValue.indexOf(".");
			if (iDotIndex == -1) {
				if (iLength > 3) {
					int n;
					n = iLength / 3;
					if ((iLength % 3) == 0)
						n = n - 1;
					strSubValue = strValue.substring(0, iLength - n * 3);
					strValue = strValue.substring(iLength - n * 3, iLength);
					for (int i = 0; i < n; i++) {
						strSubValue = strSubValue + strInsert;
						strSubValue = strSubValue + strValue.substring(i * 3, i * 3 + 3);
					}
					return strLeft + strSubValue;
				} else {
					return strLeft + strValue;
				}
			} else {
				strSubAfDot = strValue.substring(iDotIndex, iLength);
				strValue = strValue.substring(0, iDotIndex);
				int iSubLength;
				iSubLength = strValue.length();
				if (iSubLength > 3) {
					int n;
					n = iSubLength / 3;
					if ((iSubLength % 3) == 0)
						n = n - 1;
					strSubValue = strValue.substring(0, iSubLength - n * 3);
					strValue = strValue.substring(iSubLength - n * 3, iSubLength);
					for (int i = 0; i < n; i++) {
						strSubValue = strSubValue + strInsert;
						strSubValue = strSubValue + strValue.substring(i * 3, i * 3 + 3);
					}
					strSubValue += strSubAfDot;
					return strLeft + strSubValue;
				} else {
					return strLeft + strValue + strSubAfDot;
				}
			}
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * get comma format of a string and spec the fractional division's length
	 * 
	 * @param strValue
	 * @param tail
	 * @return
	 */
	public static String getCommaStr(String strValue, int tail) {
		strValue = formatNumber(strValue, tail);
		return getCommaStr(strValue);
	}

	/**
	 * del all commas from a str
	 * 
	 * @param strValue
	 * @param tail
	 * @return
	 */
	public static String delCommaStr(String str) {

		if (str == null) {
			return str;
		}
		str = replace(str, ",", "");
		return str;
	}
	
	public static boolean arrayIsEmpty(String[] s){
		return s == null || s.length == 0 || (s.length == 1 && s[0] == null) || (s.length == 1 && s[0].length() == 0);
	}
	public static boolean arrayIsNotEmpty(String[] s){
		return !arrayIsEmpty(s);
	}

	/**
	 * get formatted str of a number
	 * 
	 * @param strValue
	 * @param tail
	 * @return
	 */
	public static String formatNumber(String value, String format) {
		try {
			Double x = new Double(value);
			String retValue = formatNumber(x, format);
			return retValue;

		} catch (Exception e) {
			Double x = new Double(0);
			String retValue = formatNumber(x, format);
			return retValue;
		}
	}

	/**
	 * get formatted str of a number
	 * 
	 * @param strValue
	 * @param tail
	 * @return
	 */
	public static String formatNumber(String strValue, int tail) {
		if (isEmpty(strValue)) {
			return strValue;
		}
		StringBuffer sb = new StringBuffer(strValue);
		int pos = strValue.indexOf(".");
		if (pos == -1) {
			sb.append(".");
			for (int i = 0; i < tail; i++) {
				sb.append("0");
			}
		} else {
			int tailNow = strValue.length() - pos - 1;
			if (tailNow < tail) {
				for (int i = 0; i < tail - tailNow; i++) {
					sb.append("0");
				}
			}
		}
		return sb.toString();
	}

	/**
	 * get formatted str of a number
	 * 
	 * @param strValue
	 * @param tail
	 * @return
	 */
	public static String formatNumber(Number value, String format) {
		if ((value == null) || (format == null)) {
			return "";
		}
		try {
			DecimalFormat df = new DecimalFormat(format);
			double x = value.doubleValue();
			String retValue = df.format(x);
			return retValue;
		} catch (Exception e) {
			Double x = new Double(0);
			String retValue = formatNumber(x, format);
			return retValue;
		}
	}

	/**
	 * make calc between two doubles
	 * 
	 * @param strValue
	 * @param tail
	 * @return
	 */
	public static double calcDecimal(double left, char operator, double right, int scale) {

		return calcDecimal(new BigDecimal(Double.toString(left)), operator, new BigDecimal(Double.toString(right)),
				scale, BigDecimal.ROUND_DOWN);
	}

	/**
	 * make calc between two doubles
	 * 
	 * @param strValue
	 * @param tail
	 * @return
	 */
	public static double calcDecimal(double left, char operator, long right, int scale) {

		return calcDecimal(new BigDecimal(Double.toString(left)), operator, new BigDecimal(Long.toString(right)),
				scale, BigDecimal.ROUND_DOWN);
	}

	/**
	 * make calc between two doubles
	 * 
	 * @param strValue
	 * @param tail
	 * @return
	 */
	public static double calcDecimal(long left, char operator, double right, int scale) {

		return calcDecimal(new BigDecimal(Long.toString(left)), operator, new BigDecimal(Double.toString(right)),
				scale, BigDecimal.ROUND_DOWN);
	}

	/**
	 * make calc between two doubles
	 * 
	 * @param strValue
	 * @param tail
	 * @return
	 */
	public static double calcDecimal(long left, char operator, long right, int scale) {

		return calcDecimal(new BigDecimal(Long.toString(left)), operator, new BigDecimal(Long.toString(right)), scale,
				BigDecimal.ROUND_DOWN);
	}

	/**
	 * make calc between two doubles
	 * 
	 * @param strValue
	 * @param tail
	 * @return
	 */
	public static double calcDecimal(double left, char operator, double right, int scale, int roundingMode) {

		return calcDecimal(new BigDecimal(Double.toString(left)), operator, new BigDecimal(Double.toString(right)),
				scale, roundingMode);
	}

	/**
	 * make calc between two doubles
	 * 
	 * @param strValue
	 * @param tail
	 * @return
	 */
	public static double calcDecimal(BigDecimal left, char operator, BigDecimal right, int scale, int roundingMode) {

		BigDecimal result = null;
		switch (operator) {
		case '+':
			result = left.add(right);
			break;
		case '-':
			result = left.subtract(right);
			break;
		case '*':
			result = left.multiply(right);
			break;
		case '/':
			result = left.divide(right, scale, roundingMode);
			break;
		default:
			result = new BigDecimal(0);
		}
		return result.doubleValue();
	}

	/**
	 * make calc between two doubles
	 * 
	 * @param strValue
	 * @param tail
	 * @return
	 */
	public static String calcDecimal(String leftStr, char operator, String rightStr, int scale, int roundingMode) {
		BigDecimal left = new BigDecimal(leftStr);
		BigDecimal right = new BigDecimal(rightStr);
		BigDecimal result = null;
		switch (operator) {
		case '+':
			result = left.add(right);
			break;
		case '-':
			result = left.subtract(right);
			break;
		case '*':
			result = left.multiply(right);
			break;
		case '/':
			result = left.divide(right, scale, roundingMode);
			break;
		default:
			result = new BigDecimal(0);
		}
		return result.toString();
	}

	/**
	 * convert null to " "
	 * 
	 * @param strValue
	 * @param tail
	 * @return
	 */
	public static String nulltoSpace(String value) {
		if (value == null || value.length() == 0) {
			return " ";
		}
		return value;
	}

	/**
	 * convert null to blank
	 * 
	 * @param strValue
	 * @param tail
	 * @return
	 */
	public static String null2Blank(String value) {
		if (value == null) {
			return "";
		}
		return value;
	}

	public static Object null2Blank(Object value) {
		if (value == null) {
			return "";
		}
		return value;
	}

	/**
	 * convert null to 0
	 * 
	 * @param strValue
	 * @param tail
	 * @return
	 */
	public static String null2zero(String value) {
		if (value == null) {
			return "0";
		}
		return value;
	}

	/**
	 * convert empty to 0
	 * 
	 * @param strValue
	 * @param tail
	 * @return
	 */
	public static String empty2zero(String value) {
		if (value == null || value.equals("")) {
			return "0";
		}
		return value;
	}

	/**
	 * convert convert special chars to html tag
	 * 
	 * @param strValue
	 * @param tail
	 * @return
	 */
	public static String replaceTagChar(String strin) {

		if (strin == null) {
			return strin;
		}
		if (strin.equals("")) {
			return strin;
		}
		strin = replace(strin, "&", "&amp;");
		strin = replace(strin, "<", "&lt;");
		strin = replace(strin, ">", "&gt;");
		strin = replace(strin, "\"", "&quot;");
		strin = replace(strin, "\r\n", "<br>");
		strin = replace(strin, "\n", "<br>");

		return strin;
	}

	/**
	 * before sql's convert in action
	 * 
	 * @param strValue
	 * @param tail
	 * @return
	 */
	public static String replaceSqlLike(String strin, char chEscape) {
		String strEscape = String.valueOf(chEscape);
		strin = replace(strin, strEscape, strEscape + strEscape);
		strin = replace(strin, "%", strEscape + "%");
		strin = replace(strin, "_", strEscape + "_");
		strin = replace(strin, "'", "''");
		return strin;
	}

	/**
	 * return double from string
	 * 
	 * @param strValue
	 * @param tail
	 * @return
	 */
	public static double getDouble(String inputData) {
		double result = 0;
		try {
			if (inputData == null || "".equals(inputData)) {
				result = 0;
			} else {
				result = Double.parseDouble(inputData);
			}
		} finally {

		}
		return result;
	}

	/**
	 * in the select's option
	 * 
	 * @param strValue
	 * @param tail
	 * @return
	 */
	public static String getConvertedStr(String strValue) {
		if (StringUtils.isNotEmpty(strValue)) {
			strValue = StringUtils.replace(strValue, "\\", "\\\\");
			strValue = StringUtils.replace(strValue, "'", "\\'");
		}
		return strValue;
	}

	/**
	 * convert null to html(" ")
	 * 
	 * @param strValue
	 * @param tail
	 * @return
	 */
	public static String nulltoNbsp(String value) {
		if (value == null || value.length() == 0) {
			return "&nbsp;";
		}
		return value;
	}

	public static Integer zeroToNull(Integer input) {
		if (input != null && input.intValue() == 0)
			return null;
		return input;
	}

	public static String readMessageTextFromClobToStr(Clob clob) throws SQLException, IOException {
		Reader r = null;
		BufferedReader br = null;
		StringBuffer sb = null;
		try {
			r = clob.getCharacterStream();
			br = new BufferedReader(r);
			sb = new StringBuffer();
			while (true) {
				String tempStr = br.readLine();
				if (tempStr == null)
					break;
				sb.append(tempStr + "\n");
			}
		} catch (Exception e) {
			return null;
		} finally {
			br.close();
			r.close();
		}
		return sb.toString();
	}
	
	public static boolean isNumberString(String str){
		boolean bRet = true;
		if(StringUtils.isEmpty(str)){
			bRet = false;
		}
		if(!StringUtils.isNumeric(str)){
			bRet = false;
		}
		return bRet;
	}
	
	public static boolean notEmpty(String str) {
		return (str!=null && str.length()>0);
	}
	
	/**
	 * sequence the string
	 * @param c char
	 * @param size int
	 * @return String
	 */
	public static String sequences(char c, int size) {
        final char[] buf = new char[size];
        for (int i = 0; i < buf.length; i++) {
            buf[i] = c;
        }
        return new String(buf);
    }
	
	public static String convertChar(Character ca){
		if(ca == null){
			return null;
		}else{
			return ca.toString();
		}
	}

	public static boolean isContain(String str, String subString, String flag){
		if(str == null){
			return false;
		}
		StringTokenizer subStrList = new StringTokenizer(subString, flag);
		return isContain(str, subStrList);
	}
	
	public static boolean isContain(String str, StringTokenizer subStrList){
		if(str == null){
			return false;
		}
		while (subStrList.hasMoreTokens()) {
			String tmpStr = subStrList.nextToken();
			if(str.indexOf(tmpStr)>=0){
				return true;
			}
		}
		return false;
	}
	public static String generateBizNo(int length) {
		String allChar = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
		StringBuffer sb = new StringBuffer();
		Random random = new Random();
		for (int i = 0; i < length; i++) {
			sb.append(allChar.charAt(random.nextInt(allChar.length())));
		}
		return sb.toString();
	}
	public static boolean isNullOrEmpty(Object object) {
		if (object != null) {
			if (object instanceof Object[]) {
				Object[] objects = (Object[]) object;
				if (objects[0] != null && !objects[0].equals("")) {
					return false;
				}
			}else{
				if (!object.equals("")) {
					return false;
				}
			}
		}
		return true;
	}
	
	public static List<String> getStringListByComma(String str,String comma) {
		if("".equals(str))
			return new ArrayList<String>();
		String[] s = str.split(comma);	 
		return Arrays.asList(s);
	}
	
	public static String getStrValue(Object obj){
		if(obj == null){
			return "";
		}else if(obj instanceof String){
			return (String)obj;
		}else if(obj instanceof Integer || obj instanceof BigDecimal){
			return obj.toString();
		}else if(obj instanceof Date){
			return DateUtil.getStringFromDate((Date)obj, "yyyy-MM-dd HH:mm:ssSSS");
		}else{
			return obj.toString();
		}
	}
	
	public static String upperFirstChar(String str){
		if(StringUtils.isEmpty(str) || "".equals(str.trim())){
			return "";
		}
		str = str.trim();
		return StringUtils.upperCase(str.substring(0,1)) + str.substring(1, str.length());
	}
	
	public static String intToStr(int num, int count) {
		if (num < 0)
			return "-" + intToStr(-num, count - 1);
		
		if (count <= 0) {
			if (num == 0)
				return "";
			else
				return num + "";
		}
		return intToStr(num / 10, count - 1) + num % 10 + "";
	}
	
	public static boolean isSameIgnoreNullEmpty(String str1, String str2){
		if(str1 == null && str2 == null){
			return true;
		}else if(str1 != null && str2 != null){
			return str1.trim().equals(str2.trim());
		}else if(str1 != null){
			return "".equals(str1.trim());
		}else{
			return "".equals(str2.trim());
		}
	}
	
	/**
	 * 元转换为分.
	 * 
	 * @param yuan
	 * @return cent
	 */
	public static String fromYuanToCent(String yuan) {
		String cent = "0";
		if (isBlank(yuan)) {
    		return cent;
    	}
		Pattern pattern = Pattern.compile("-?[0-9]+.?[0-9]+");
		Matcher matcher = pattern.matcher(yuan);
		if (matcher.matches()) {
			try {
				NumberFormat format = NumberFormat.getInstance();
				Number number = format.parse(yuan);
				double temp = number.doubleValue() * 100.0;
				format.setGroupingUsed(false);
				// 设置返回数的小数部分所允许的最大位数
				format.setMaximumFractionDigits(0);
				cent = format.format(temp);
			} catch (ParseException e) {
				e.printStackTrace();
			}
		} else {
			//error data;
		}
		return cent;
	}
	
	 /**  
     * 分转换为元.  
     *   
     * @param cent  
     * @return yuan
     */  
    public static String fromCentToYuan(String cent) {
    	
    	String yuan = "0.00";  
    	if (isBlank(cent)) {
    		return yuan;
    	}
        int MULTIPLIER = 100;  
        Pattern pattern = Pattern.compile("-?[0-9]+.?[0-9]+");  
        Matcher matcher = pattern.matcher(cent);  
        if (matcher.matches()) {  
            yuan = new BigDecimal(cent).divide(new BigDecimal(MULTIPLIER)).setScale(2).toString();  
        } else {  
        	//error data;
        }  
        return yuan;  
    }  
	/**
	 * 字符串长度不够 补0
	 * 
	 * @param num
	 * @param len
	 * @return
	 */
	public static String getFormattedNum(String num, int len) {
		int numLen = num == null ? 0 : num.length();
		StringBuffer buf = new StringBuffer();
		len = len - numLen;
		for (int i = 0; i < len; i++) {
			buf.append("0");
		}

		return numLen > 0 ? buf.append(num).toString() : buf.toString();
	}

	/**
	 * 获取length个空格
	 * 
	 * @param len
	 * @return
	 */
	public static String getRepeatSpace(int len) {
		return getRepeatString(" ", len);
	}

	/**
	 * 获取len个0
	 * 
	 * @param len
	 * @return
	 */
	public static String getRepeatZero(int len) {
		return getRepeatString("0", len);
	}

	/**
	 * 获取len个s字符串
	 * 
	 * @param len
	 * @return
	 */
	public static String getRepeatString(String s, int len) {
		StringBuffer buf = new StringBuffer(len);
		for (int i = 0; i < len; i++) {
			buf.append(s);
		}
		return buf.toString();
	}

	/**
	 * 将16进制字符转化为byte[]
	 * 
	 * @param hexStr
	 * @return
	 */
	public static byte[] toByteArray(String hexStr) {
		byte[] bytes = null;

		if (hexStr != null) {
			if (hexStr.startsWith("0x") || hexStr.startsWith("0X")) {
				hexStr = hexStr.substring(2);
			}
			int len = hexStr.length();

			if (len % 2 != 0) {
				throw new IllegalStateException("invalid hex string: " + hexStr);
			}

			len = len / 2;
			bytes = new byte[len];
			int idx = 0;

			for (int i = 0; i < len; i++) {
				bytes[i] = (byte) (0xff & Integer.parseInt(hexStr.substring(idx, idx + 2), 16));
				idx += 2;
			}
		}
		return bytes == null ? new byte[0] : bytes;
	}

	/**
	 * 把byte[]转化为十六进制字符串
	 * 
	 * @param bytes
	 * @return
	 */
	public static String toHexString(byte[] bytes) {
		StringBuffer buf = new StringBuffer();
		int len = bytes == null ? 0 : bytes.length;
		String str = null;
		for (int i = 0; i < len; i++) {
			str = Integer.toHexString(0xff & bytes[i]);
			buf.append(str.length() < 2 ? "0".concat(str) : str);
		}
		return buf.toString();
	}

	/**
	 * 将byte[] 转化为四个二进制一位
	 * 
	 * @param bytes
	 * @return
	 */
	public static String toBinString(byte[] bytes) {
		StringBuffer buf = new StringBuffer();
		for (int j = 0; j < bytes.length; j++) {
			for (int i = 7; i >= 0; i--) {
				if (((1 << i) & bytes[j]) != 0) {
					buf.append("1");
				} else {
					buf.append("0");
				}
			}
		}
		return buf.toString();
	}

	/**
	 * 将字符串按照delim进行分割
	 * 
	 * @param source
	 * @param delim
	 * @return
	 */
	public static String[] split(String source, String delim) {
		int i = 0;
		int l = 0;
		if (source == null || delim == null)
			return new String[0];
		if (source.equals("") || delim.equals(""))
			return new String[0];

		StringTokenizer st = new StringTokenizer(source, delim);
		l = st.countTokens();
		String[] s = new String[l];
		while (st.hasMoreTokens()) {
			s[i++] = st.nextToken();
		}
		return s;
	}

	/**
	 * 将字符转化成两位一组的十六进制字符
	 * 
	 * @param bArray
	 * @return
	 */
	public static String bytesToHexString(byte[] bArray) {
		StringBuffer sb = new StringBuffer(bArray.length);
		String sTemp;
		for (int i = 0; i < bArray.length; i++) {
			sTemp = Integer.toHexString(0xFF & bArray[i]);
			if (sTemp.length() < 2)
				sb.append(0);
			sb.append(sTemp.toUpperCase());
		}
		return sb.toString();
	}

	public static byte[] hexString2Bytes(String hexStr) {
		byte[] baKeyword = new byte[hexStr.length() / 2];
		for (int i = 0; i < baKeyword.length; i++) {
			try {
				baKeyword[i] = (byte) (0xff & Integer.parseInt(hexStr.substring(i * 2, i * 2 + 2), 16));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return baKeyword;
	}

	public static String stringXor(String str1, String str2) throws Exception {
		StringBuffer sb = new StringBuffer();
		if (str1.length() != str2.length()) {
			throw new Exception("xor args must be have the same length");
		}

		byte[] bt1 = Str2Hex(str1);
		byte[] bt2 = Str2Hex(str2);

		for (int i = 0; i < bt1.length; i++) {
			sb.append(addZero(Integer.toHexString((bt1[i] ^ bt2[i]) & 0xFF), 2));
		}

		return sb.toString();
	}


	/** 字符串转字节数组 * */
	public static byte[] Str2Hex(String str) {
		char[] ch = str.toCharArray();
		byte[] b = new byte[ch.length / 2];
		for (int i = 0; i < ch.length; i++) {
			if (ch[i] == 0)
				break;
			if (ch[i] >= '0' && ch[i] <= '9') {
				ch[i] = (char) (ch[i] - '0');
			} else if (ch[i] >= 'A' && ch[i] <= 'F') {
				ch[i] = (char) (ch[i] - 'A' + 10);
			}
		}
		for (int i = 0; i < b.length; i++) {
			b[i] = (byte) (((ch[2 * i] << 4) & 0xf0) + (ch[2 * i + 1] & 0x0f));
		}
		return b;
	}


	/**
	 * 转成16进制
	 * 
	 * @param b
	 * @return
	 */
	public static String Hex2Str(byte[] b) {
		StringBuffer d = new StringBuffer(b.length * 2);
		for (int i = 0; i < b.length; i++) {
			char hi = Character.forDigit((b[i] >> 4) & 0x0F, 16);
			char lo = Character.forDigit(b[i] & 0x0F, 16);
			d.append(Character.toUpperCase(hi));
			d.append(Character.toUpperCase(lo));
		}
		return d.toString();
	}

	public static byte[] fillByteArray(byte[] a, int length) {
		byte[] msg = new byte[length];
		if (a.length < length) {
			int len = length - a.length;
			byte[] b = new byte[len];
			for (int i = 0; i < len; i++) {
				b[i] = 0;
			}
			System.arraycopy(a, 0, msg, 0, a.length);
			System.arraycopy(b, 0, msg, a.length, b.length);
		}
		return msg;
	}

	/**
	 * 10进制转2进制
	 * 
	 * @param value
	 * @return
	 */
	public static byte[] StringTobyte(int sizeItr) {
		int lenFieldLen = 8;
		byte[] body = new byte[lenFieldLen];
		byte byteLen = 0;
		for (int i = lenFieldLen - 1; i >= 0; i--) {
			byteLen = (byte) (sizeItr % 256);
			body[lenFieldLen - i - 1] = byteLen;
			sizeItr = sizeItr / 256;
		}
		return body;
	}

	// 按length填充String 不足前补空格
	public static String blankFillString(String value, int length) {
		String a = "";
		int len = value.length();
		if (len < length) {
			for (int i = 0; i < length - len; i++) {
				a += " ";
			}
		}
		return a + value;
	}

	// 按length填充String 不足前补零
	public static String fillString(String value, int length) {
		String a = "";
		int len = value.length();
		if (len < length) {
			for (int i = 0; i < length - len; i++) {
				a += "0";
			}
		}
		return a + value;
	}

	public static String SetToString(Set<String> value) {
		String b = "";
		if (null != value && value.size() > 0) {
			String a = value.toString();
			int len = a.length();
			b = a.substring(1, len - 1);
		}
		return b;
	}

	public static String CollectionToString(Collection<Object> value) {
		Object[] a = value.toArray();
		String b = "";
		if (null != a && a.length > 0) {
			for (int i = 0; i < a.length; i++) {
				b += a[i] + "|";
			}
		}
		return b;
	}

	public static boolean isEmpty(Object obj) {
		if (null == obj) {
			return true;
		}else if(obj instanceof String && ((String) obj).trim().length()==0){
			return true;
		}else if(obj instanceof Collection<?> && ((Collection<?>) obj).size()==0){
			return true;
		}else if(obj instanceof Map<?,?> && ((Map<?, ?>) obj).size()==0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 密码计算加密初始数据 balock
	 * @param block1
	 * @param block2
	 * @return
	 */
	public static String xorStr16(String block1,String block2){
        long b1 = Long.parseLong (block1,16);
   	 	long b2 = Long.parseLong (block2,16);
        long st = b1 ^ b2;
        String hex = Long.toHexString (st);
        return hex;
	}
	
	/**
	 * 手机号码格式化
	 * @param 手机号码
	 * @return
	 */
    public static String getPhoneNumberFormat(String phone) {
    	if(phone !=null && phone.length()>=11){
	        String reStr = phone.substring(phone.length() - 4, phone.length());
	        String preStr = phone.substring(0, phone.length() - 9);
	        StringBuilder sb = new StringBuilder();
	        sb.append(preStr).append("****").append(reStr);
	        return sb.toString();
    	}else{
    		return "****";
    	}

    }
    public static String getPhoneNumberFormatLast4(String phone) {
    	String preStr ="";
    	if(phone !=null && phone.length()>=11){
    		preStr = phone.substring(phone.length()-4, phone.length());
    	}else{
    		return "****";
    	}
    	 return preStr;
    }

    public static int parseInt(String str, int defaultNum) {
		int num = 0;
		try {
			num = Integer.parseInt(str);
		} catch (Exception e) {
			// e.printStackTrace();
			num = defaultNum;
		}
		return num;
	}
    
    public static int parseInt(int val, int defaultNum) {
		int num = 0;
		try {
			if(val<=0){
				num=defaultNum;
			}else{
				num=val;
			}
		} catch (Exception e) {
			// e.printStackTrace();
			num = defaultNum;
		}
		return num;
	}
}
