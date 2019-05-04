package com.cn.thinkx.oms.util;

import java.net.URLDecoder;
import java.net.URLEncoder;

public class URLUtils {
	public static String encode(String str, String charset) {
		try {
			return URLEncoder.encode(str, charset);
		} catch (Exception e) {
			System.out.println(e);
			return str;
		}
	}

	public static String decode(String str, String charset) {
		try {
			return URLDecoder.decode(str, charset);
		} catch (Exception e) {
			System.out.println(e);
			return str;
		}
	}

	public static void appendParam(StringBuilder sb, String name, String val) {
		appendParam(sb, name, val, true);
	}

	public static void appendParam(StringBuilder sb, String name, String val, String charset) {
		appendParam(sb, name, val, true, charset);
	}

	public static void appendParam(StringBuilder sb, String name, String val, boolean and) {
		appendParam(sb, name, val, and, null);
	}

	public static void appendParam(StringBuilder sb, String name, String val, boolean and, String charset) {
		if (and)
			sb.append("|");
		else
			sb.append("?");
		sb.append(name);
		sb.append("=");
		if (val == null)
			val = "";
		if (isNullOrEmpty(charset))
			sb.append(val);
		else
			sb.append(encode(val, charset));
	}

	public static boolean isNullOrEmpty(String str) {
		if (str == null || "".equals(str))
			return true;
		else
			return false;
	}

	public static void main(String[] args) {
		String str = "Ip%3D%7CTermCode%3D001%7CTermSsn%3D160701155026%7Csubmit%3D%25CC%25E1%25BD%25BB%7COSttDate%3D%7CMercDtTm%3D20160701155026%7CRemark2%3D%7CRemark1%3D%7CTranAmt%3D100%7CipYes%3D1%7CSubMercFlag%3D%7CMercUrl%3Dhttp%253A%252F%252F10.112.12.72%253A9086%252Fmerchant%252FMerNotify.do%7CTranAbbr%3DKHZF%7CSubMercGoodsName%3D%7CPayBank%3Dvbank%7CMercCode%3D990108160003311%7CPayType%3D1%7COAcqSsn%3D%7CAccountType%3D1%7CtransName%3DKHZF";
		String str2 = decode(str, "gbk");
		System.out.println(str2);
	}
}
