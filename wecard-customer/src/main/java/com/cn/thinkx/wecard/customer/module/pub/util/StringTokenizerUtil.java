package com.cn.thinkx.wecard.customer.module.pub.util;

import java.util.StringTokenizer;

import com.alibaba.fastjson.JSONArray;


public class StringTokenizerUtil {
	
	public static JSONArray split(String str, String delim) {
		JSONArray json = new JSONArray();
		StringTokenizer st = new StringTokenizer(str, delim, false);
		while(st.hasMoreTokens()) {
			json.add(st.nextToken());
		}
		return json;
	}

	public static void main(String[] args) {
	}
}
