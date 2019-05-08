package com.cn.iboot.diy.api.base.service.impl;

import java.net.URLEncoder;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cn.iboot.diy.api.base.http.HttpClient;
import com.cn.iboot.diy.api.base.http.HttpRequest;
import com.cn.iboot.diy.api.base.http.HttpResponse;
import com.cn.iboot.diy.api.base.service.MessageService;
import com.cn.iboot.diy.api.base.utils.StringUtil;

@Service("messageService")
public class MessageServiceImpl implements MessageService {

	private static Logger LOG = LoggerFactory.getLogger(MessageServiceImpl.class);
	
	@Value("${SEND_MSG_FLAG}")
	private String flag;
	
	@Value("${SEND_URL}")
	private String url;
	
	@Value("${ACCOUNT}")
	private String account;
	
	@Value("${PASSWORD}")
	private String pwd;

	@Override
	public boolean sendMessage(String target, String content) {
		if("true".equals(flag)){
			try {
				StringBuffer strBuf = new StringBuffer(url);
				strBuf.append("?un=").append(account);
				strBuf.append("&pw=").append(pwd);
				strBuf.append("&da=").append(target);
				strBuf.append("&sm=").append(URLEncoder.encode(content, "UTF-8"));
				strBuf.append("&dc=15&rd=1&rf=2&tf=3");
	
				LOG.info("手机号：" + target + "--->" + "短信开始发送");
				HttpResponse response = HttpClient.get(new HttpRequest(strBuf.toString().replace("+","%20")));
				String result = null;
				if (response != null && StringUtil.isNotEmpty(result = response.getStringResult())) {
					JSONObject obj = JSON.parseObject(result);
					if (obj != null && obj.getBooleanValue("success")) {
						return true;
					}
				}
			} catch (Exception e) {
				LOG.error("手机号：" + target + "--->" + "短信发送失败", e);
			}
		}
		return false;
	}

	/*public static void main(String[] args) {
		MessageService pro = new MessageServiceImpl();
		System.out.println(pro.sendMessage("13162666043", "【薪无忧】亲爱的会员，你尾号为0265的会员卡于2016-10-09 13:09:17充值100.00元，余额234.72元[上海宸树]"));
	}*/
}
