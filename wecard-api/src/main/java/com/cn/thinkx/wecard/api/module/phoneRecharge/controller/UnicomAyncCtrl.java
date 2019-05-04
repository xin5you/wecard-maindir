package com.cn.thinkx.wecard.api.module.phoneRecharge.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cn.thinkx.wecard.api.module.phoneRecharge.service.UnicomAyncService;

@Controller
@RequestMapping("/unicomAync")
public class UnicomAyncCtrl{
	Logger logger = LoggerFactory.getLogger(UnicomAyncCtrl.class);

	@Autowired
	@Qualifier("unicomAyncService")
	private UnicomAyncService unicomAyncService;

	@RequestMapping("/query")
	public String query(HttpServletRequest request) {
		return unicomAyncService.query(request);
	}

	@RequestMapping("/notify")
	public String notify(HttpServletRequest request) {
		return unicomAyncService.notify(request);
	}
	
	/*@RequestMapping("/mobileSign")
	public List<CardKeysProduct> mobileSign(HttpServletRequest request) {
		return unicomAyncService.mobileSign(request);
	}*/

}
