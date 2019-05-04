package com.cn.iboot.diy.api.system.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cn.iboot.diy.api.base.constants.Constants;

@Controller
public class LoginController {

	//private Logger logger = LoggerFactory.getLogger(getClass());

	@GetMapping(value = { "", "/", "/login" })
	public ModelAndView toLogin() {
		ModelAndView mv = new ModelAndView("login/login");
		return mv;
	}

	@GetMapping(value = "/main")
	public ModelAndView main(HttpServletRequest req) {
		ModelAndView mv = new ModelAndView("login/main");
		HttpSession session = req.getSession();
		mv.addObject("user", session.getAttribute(Constants.SESSION_USER));
		return mv;
	}

	@GetMapping(value = "/error")
	public ModelAndView error(HttpServletRequest req) {
		ModelAndView mv = new ModelAndView("main/error");

		return mv;
	}

}
