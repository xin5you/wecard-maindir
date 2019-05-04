package com.cn.thinkx.pub.utils;

import java.util.StringTokenizer;

import com.alibaba.fastjson.JSONArray;
import com.cn.thinkx.core.util.Constants;

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
		JSONArray json = StringTokenizerUtil.split("123456@abc", Constants.WX_FLAG_SEND_PASS);
        System.out.println(json.get(1));
	}
}
