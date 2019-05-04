package com.cn.iboot.diy.api.base.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cn.iboot.diy.api.base.constants.Constants.RandomCodeType;
import com.cn.iboot.diy.api.base.utils.NumberUtils;
import com.cn.iboot.diy.api.base.utils.ValidateCode;

/**
 *
 * 验证码跳转层
 *
 */
@Controller
@RequestMapping(value = "/authcode")
public class AuthCodeController {

	Logger logger = LoggerFactory.getLogger(AuthCodeController.class);
	
	@GetMapping("genAuthCode/{codeType}/{random}")
	public ModelAndView genAuthCode(HttpServletRequest request, HttpServletResponse response, 
			@PathVariable("codeType") String codeType) {
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expires", 0);
		response.setContentType("image/jpeg");

		HttpSession session = request.getSession();
		ValidateCode vCode = new ValidateCode(130, 40, 4, 20);

		RandomCodeType rcy = RandomCodeType.findByCode(NumberUtils.parseInt(codeType));
		if (rcy != null) {
			session.setAttribute(rcy.getCode(), vCode.getCode());
		}
		try {
			vCode.write(response.getOutputStream());
		} catch (Exception e) {
			logger.error("验证码生成失败：", e);
		}

		return null;
	}
}
