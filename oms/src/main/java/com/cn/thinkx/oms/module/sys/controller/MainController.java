package com.cn.thinkx.oms.module.sys.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cn.thinkx.oms.base.controller.BaseController;

@Controller
@RequestMapping(value = "/main")
public class MainController extends BaseController {
	

	@RequestMapping(value = "/index")
	public ModelAndView main(HttpServletRequest req, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("main/index");

		return mv;
	}

}
