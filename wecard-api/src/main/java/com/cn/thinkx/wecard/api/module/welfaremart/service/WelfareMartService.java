package com.cn.thinkx.wecard.api.module.welfaremart.service;

import javax.servlet.http.HttpServletRequest;

public interface WelfareMartService {
	
	String welfareBuyCardNotify(HttpServletRequest request);
	
	String welfareRechargeNotify(HttpServletRequest request);
	
}
