package com.cn.iboot.diy.api.base.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.cn.iboot.diy.api.base.constants.Constants;

public class DiyInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
		Object o = session.getAttribute(Constants.SESSION_USER);
		if (o == null) {
			response.sendRedirect(request.getContextPath() + "/login");
			return false;
		}
		return super.preHandle(request, response, handler);
	}
}
