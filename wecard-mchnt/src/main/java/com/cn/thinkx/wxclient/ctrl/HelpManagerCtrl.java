package com.cn.thinkx.wxclient.ctrl;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cn.thinkx.core.ctrl.BaseController;

@Controller
@RequestMapping("/help")
public class HelpManagerCtrl extends BaseController {
	Logger logger = LoggerFactory.getLogger(HelpManagerCtrl.class);

	/**
	 * 商户管理 帮助
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/helpCenter")
	public ModelAndView mchtRegister(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("wxclient/help/helpCenter");
		return mv;
	}
}