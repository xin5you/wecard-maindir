package com.cn.thinkx.oms.module.sys.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cn.thinkx.oms.base.controller.BaseController;
import com.cn.thinkx.oms.module.sys.service.UserService;

@Controller
@RequestMapping(value = "/login")
public class LoginController extends BaseController {
	
	@Autowired
	@Qualifier("userService")
	private UserService userService;
	


	@RequestMapping(value = "/loginIndex")
	public ModelAndView loginIndex(HttpServletRequest req, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("login/login");
		return mv;
	}

	@RequestMapping(value = "/main")
	public ModelAndView main(HttpServletRequest req, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("login/main");

		return mv;
	}
//	@RequestMapping(value = "doLogin")
//	@ResponseBody
//	public Map<String, Object> doLogin(HttpServletRequest req, HttpServletResponse response) {
//		
//		HttpSession session = req.getSession();
//		Map<String, Object> resultMap = new HashMap<String, Object>();
//
//		String userName = req.getParameter("userName");
//		String md5Code = req.getParameter("md5Code");
//		String authCode = req.getParameter("authCode");
//		try {
//			// 先判断验证码
//			String sysAuthCode = (String) req.getSession().getAttribute(RandomCodeType.LOGIN.getCode());
//			if (sysAuthCode == null) {// session过期
//				// response.setHeader("sessionStatus", "timeout");
//				resultMap.put("status", Boolean.FALSE);
//				resultMap.put("timeout", Boolean.TRUE);
//				resultMap.put("message", "会话过期，请重新再试");
//				return resultMap;
//			}
//			resultMap.put("timeout", Boolean.FALSE);
//			logger.info("sysAuthCode=" + sysAuthCode);
//			if (!authCode.equalsIgnoreCase(sysAuthCode)) {
//				resultMap.put("status", Boolean.FALSE);
//				resultMap.put("message", "验证码不正确，请重新输入");
//				return resultMap;
//			}
//		
//			User currUser = userService.getUserByLoginName(userName);
//			if (currUser == null || 1 > 2) {
//				resultMap.put("status", Boolean.FALSE);
//				resultMap.put("message", "用户名或密码错误，请重新输入");
//				return resultMap;
//			}
//
//			String sysMd5Password = MD5Utils.MD5(currUser.getPassword() + authCode);
//			if (!currUser.getLoginname().equals(userName) || !sysMd5Password.equals(md5Code)) {
//				resultMap.put("status", Boolean.FALSE);
//				resultMap.put("message", "用户名或密码错误，请重新输入");
//				return resultMap;
//			}
//  
//			UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(currUser.getLoginname(), currUser.getPassword());
//	        Authentication authentication = myAuthenticationManager.authenticate(authRequest); //调用loadUserByUsername
//	        SecurityContextHolder.getContext().setAuthentication(authentication);
//	        session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext()); // 这个非常重要，否则验证后将无法登陆
//
//	        session.setAttribute(Constants.SESSION_USER, currUser);
//			resultMap.put("status", Boolean.TRUE);
//			resultMap.put("message", "登录成功");
//
//		} catch (Exception e) {
//			e.printStackTrace();
//			resultMap.put("status", Boolean.FALSE);
//			resultMap.put("message", "登录失败，系统出错");
//			logger.error(e.getLocalizedMessage(), e);
//		}
//		return resultMap;
//	}
}
