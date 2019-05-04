package com.cn.iboot.diy.api.channel.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cn.iboot.diy.api.base.constants.Constants;
import com.cn.iboot.diy.api.system.domain.User;
import com.cn.thinkx.wecard.facade.telrecharge.model.TelChannelInf;
import com.cn.thinkx.wecard.facade.telrecharge.model.TelChannelOrderInf;
import com.cn.thinkx.wecard.facade.telrecharge.service.TelChannelInfFacade;

@Controller
@RequestMapping("/channel")
public class ChannelInfController {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private TelChannelInfFacade telChannelInfFacade;

	@GetMapping("/listChannel")
	public ModelAndView listChannel(HttpServletRequest req,TelChannelOrderInf order){
		ModelAndView mv = new ModelAndView("channel/listChannel");
		User user = (User) req.getSession().getAttribute(Constants.SESSION_USER);
		try {
			TelChannelInf channel = telChannelInfFacade.getTelChannelInfByMchntCode(user.getMchntCode());
			mv.addObject("channel", channel);
		} catch (Exception e) {
			logger.error(" ## 查看分销商信息出错 ",e);
		}
		return mv;
	}
}
