package com.cn.thinkx.oms.util;

import java.util.UUID;

/**
 *
 * 字符串操作公共类
 *
 */
public class StringUtils {

	public static String getUUID() {
		return UUID.randomUUID().toString().replaceAll("-", "");
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
		if (o != null) {
			return o.toString();
		}
		return "";
	}

	/**
	 * 将字符串转换成null
	 * 
	 * @param o
	 * @return
	 */
	public static String stringToNull(Object o) {
		if (o == null || o.toString().trim().equals("")) {
			return null;
		}
		return o.toString();
	}

	public static boolean isNotNull(Object o) {
		if (o != null && o.toString().trim().length() > 0) {
			return true;
		}
		return false;
	}

	public static boolean isNull(Object o) {
		if (o == null) {
			return true;
		}
		if (o.toString().trim().length() < 1) {
			return true;
		}
		return false;
	}

	public static boolean isNull(String str) {
		return str == null || str.trim().length() < 1;
	}

	/**
	 * 得到文件扩展名
	 * 
	 * @param fileName
	 * @return String
	 */
	public static String getFileExtension(String fileName) {
		if (fileName != null && fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
			return fileName.substring(fileName.lastIndexOf("."));
		} else {
			return "";
		}
	}

	/**
	 * 利用正则表达式将每个中文字符转换为"**" 匹配中文字符的正则表达式： [\u4e00-\u9fa5]
	 * 匹配双字节字符(包括汉字在内)：[^\x00-\xff]
	 * 
	 * @param str
	 * @return
	 */
	public static int getChineseLength(String str) {
		String temp = str.replaceAll("[^\\x00-\\xff]", "**");
		return temp.length();
	}

	public static boolean isEmpty(String str) {
		return str == null || str.isEmpty();
	}

	/**
	 * 隐藏手机中间4位
	 * 
	 * @param phone
	 * @return String
	 */
	public static String encryPhone(String phone) {
		String info = "";
		if (isEmpty(phone)) {
			return info;
		}
		StringBuilder sb = new StringBuilder();
		sb.append(phone);
		info = sb.replace(3, 7, "****").toString();
		return info;
	}

	public static String restoreMd5(String md5, String code) {
		int codeLen = code.length();
		int md5Len = md5.length() - 1;
		int startIndex, endIndex;
		String startChar, endChar;
		StringBuilder md5Sb = new StringBuilder();
		md5Sb.append(md5);
		for (int i = 0; i < codeLen; i++) {
			startIndex = Integer.parseInt(code.substring(i, i + 1), 16);
			endIndex = md5Len - startIndex;
			startChar = md5.substring(startIndex, startIndex + 1);
			endChar = md5.substring(endIndex, endIndex + 1);
			md5Sb.replace(startIndex, startIndex + 1, endChar);
			md5Sb.replace(endIndex, endIndex + 1, startChar);
		}
		return md5Sb.toString();
	}

	public static boolean isNullOrEmpty(String str) {
		if (str == null || "".equals(str))
			return true;
		else
			return false;
	}

	public static String dealStr(String o) {
		if (o != null) {
			return o.toString();
		}
		return "";
	}

	public static void main(String args[]) {
		// System.out.println(isNull(null));
		String md5 = "27e9244b456c18df737b57fdbc4558a8";
		String code = "0369bf";

		String md6 = restoreMd5(md5, code);
		System.out.println(md5 + " md5: " + md6);

		boolean b = isEmpty(null);
		System.out.println(b);
	}
}
