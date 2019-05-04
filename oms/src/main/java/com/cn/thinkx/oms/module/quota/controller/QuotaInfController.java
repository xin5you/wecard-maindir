package com.cn.thinkx.oms.module.quota.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping(value = "quotaManage")
public class QuotaInfController {

	@RequestMapping("/listQuotaInf")
	public ModelAndView listQuotaInf(HttpServletRequest request){
		ModelAndView mv = new ModelAndView();
		
		
		
		return mv;
	}
	
	public ModelMap editQuotaInfCommit(HttpServletRequest request){
		ModelMap map = new ModelMap();
		
		return map;
	}
}
