package com.cn.thinkx.wecard.api.module.welfaremart.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cn.thinkx.wecard.api.module.welfaremart.service.WelfareMartService;

@Controller
@RequestMapping("/welfareNotify")
public class WelfareNotifyController {
	
	Logger logger = LoggerFactory.getLogger(WelfareNotifyController.class);
	
	@Autowired
	@Qualifier("welfareMartService")
	private WelfareMartService welfareMartService;

	/**
	 * 购卡异步回调
	 * 
	 * @param request
	 */
	@RequestMapping(value = "/welfareBuyCardNotify")
	@ResponseBody
	public String welfareBuyCardNotify(HttpServletRequest request) {
		return welfareMartService.welfareBuyCardNotify(request);
	}
	
	/**
	 * 卡券充值回调接口
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/welfareRechargeNotify", method = RequestMethod.POST)
	@ResponseBody
	public String welfareRechargeNotify(HttpServletRequest request) {
		return welfareMartService.welfareRechargeNotify(request);
	}
	
}
