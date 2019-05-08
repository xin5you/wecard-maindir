package com.cn.thinkx.oms.util;

import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cn.thinkx.pms.base.http.HttpClient;
import com.cn.thinkx.pms.base.http.HttpRequest;
import com.cn.thinkx.pms.base.http.HttpResponse;
import com.cn.thinkx.pms.base.utils.StringUtil;

public class SMSUtil {
	private static Logger LOG = LoggerFactory.getLogger(SMSUtil.class);

	private final static String SEND_URL = PropertiesUtils.getCurrProperty("SEND_URL");
	private final static String ACCOUNT = PropertiesUtils.getCurrProperty("ACCOUNT");
	private final static String PASSWORD = PropertiesUtils.getCurrProperty("PASSWORD");

	public boolean sendMessage(String target, String content) {
		try {
			StringBuffer strBuf = new StringBuffer(SEND_URL);
			strBuf.append("?un=").append(ACCOUNT);
			strBuf.append("&pw=").append(PASSWORD);
			strBuf.append("&da=").append(target);
			strBuf.append("&sm=").append(URLEncoder.encode(content, "UTF-8"));
			strBuf.append("&dc=15&rd=1&rf=2&tf=3");

			LOG.info("手机号[{}]开始发送短信", target);
			HttpResponse response = HttpClient.get(new HttpRequest(strBuf.toString().replace("+", "%20")));
			String result = null;
			if (response != null && StringUtil.isNotEmpty(result = response.getStringResult())) {
				JSONObject obj = JSON.parseObject(result);
				if (obj != null && obj.getBooleanValue("success")) {
					return true;
				}
			}
		} catch (Exception e) {
			LOG.error("手机号[{}]短信发送失败", target, e);
		}
		return false;
	}

	public static void main(String[] args) {
		SMSUtil pro = new SMSUtil();
		System.out.println(pro.sendMessage("13501755206", "【薪无忧】亲爱的会员，你尾号为0265的会员卡于2016-10-09 13:09:17充值100.00元，余额234.72元[上海宸树]"));
	}
}
