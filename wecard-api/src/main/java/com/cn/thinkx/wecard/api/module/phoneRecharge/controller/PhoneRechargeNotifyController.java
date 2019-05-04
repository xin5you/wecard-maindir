package com.cn.thinkx.wecard.api.module.phoneRecharge.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cn.thinkx.wecard.api.module.phoneRecharge.service.PhoneRechargeService;

@Controller
@RequestMapping("/phoneRechargeNotify")
public class PhoneRechargeNotifyController {
	
	Logger logger = LoggerFactory.getLogger(PhoneRechargeNotifyController.class);
	
	@Autowired
	@Qualifier("phoneRechargeService")
	private PhoneRechargeService phoneRechargeService;
	
	@RequestMapping(value = "/flowRechargeNotify")
	@ResponseBody
	public String flowRechargeNotify(HttpServletRequest request) {
		boolean flag = phoneRechargeService.flowRechargeNotify(request);
		if (flag) {
			return "SUCCESS";
		}
		return "FAIL";
	}
	
	@RequestMapping(value = "/phoneRechargeBackResult")
	@ResponseBody
	public String phoneRechargeBackResult(HttpServletRequest request) {
		boolean flag = phoneRechargeService.phoneRechargeNotify(request);
		if (flag) {
			return "SUCCESS";
		}
		return "FAIL";
	}
	
}
