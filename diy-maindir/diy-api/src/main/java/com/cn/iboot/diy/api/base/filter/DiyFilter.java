package com.cn.iboot.diy.api.base.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.WebApplicationContext;

import com.cn.iboot.diy.api.base.constants.Constants;
import com.cn.iboot.diy.api.base.utils.StringUtil;
import com.cn.iboot.diy.api.base.utils.StringUtils;
import com.cn.iboot.diy.api.redis.utils.JedisClusterUtils;
import com.cn.iboot.diy.api.system.domain.User;

import net.sf.json.JSONObject;

/**
 * 过滤器
 * 
 * @author kpplg
 *
 */
@Configuration
public class DiyFilter implements Filter {

	private Logger logger = LoggerFactory.getLogger(DiyFilter.class);
	
	@Autowired
	private WebApplicationContext context; // 获取上下文

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		String urlString = httpRequest.getRequestURL().toString();
		if(!StringUtil.isNullOrEmpty(urlString)){
			if (urlString.contains("/login") || urlString.contains("/logout") || urlString.contains("/error")
					|| urlString.contains("/authcode") || urlString.contains("/error") || urlString.contains("/diy/js")
					|| urlString.contains("/diy/css") || urlString.contains("/images") || urlString.contains("/icon")
					|| urlString.contains("/jquery") || urlString.contains("/favicon.ico")) {
				chain.doFilter(request, response);
			} else {// 功能性请求验证
				validate(httpRequest, httpResponse, chain);
			}
		}
	}

	@Override
	public void destroy() {

	}

	private void validate(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpSession session = request.getSession(); // 获取redis中存储的用户信息
		User u = (User) session.getAttribute(Constants.SESSION_USER);
		String usertemp = "";
		if(!StringUtil.isNullOrEmpty(u)){
			usertemp = JedisClusterUtils.getInstance(context).get(u.getId());
		}
		String type = request.getHeader("X-Requested-With") == null ? "" : request.getHeader("X-Requested-With");// XMLHttpRequest
		
		if(StringUtil.isNullOrEmpty(usertemp)){
			if (StringUtils.equals("XMLHttpRequest", type)) {
				// 处理ajax请求
				response.setHeader("SESSIONSTATUS", "TIMEOUT");
				response.setHeader("CONTEXTPATH", request.getContextPath() + "/login");
				response.setStatus(HttpServletResponse.SC_FORBIDDEN);
				return;
			} else {
				response.sendRedirect(request.getContextPath() + "/login");
				return;
			}
		}
		JSONObject jsonObject = JSONObject.fromObject(usertemp);
		User userRedis = (User) JSONObject.toBean(jsonObject, User.class);
		if (session.getId().equals(userRedis.getSessionId())) { // 如果当前用户的sessionId和redis中不一致，要跳转到登录页面
			chain.doFilter(request, response);
		} else {
			logger.error("该账号在别处已登录，请重新登录！--->"+u.getPhoneNo());
			session.setAttribute("showCheckCode", "1");
			session.setAttribute("SECURITY_LOGIN_EXCEPTION", "该账号在别处已登录，请重新登录！");
			if (StringUtils.equals("XMLHttpRequest", type)) {
				// 处理ajax请求
				response.setHeader("SESSIONSTATUS", "TIMEOUT");
				response.setHeader("CONTEXTPATH", request.getContextPath() + "/login");
				response.setStatus(HttpServletResponse.SC_FORBIDDEN);
				return;
			} else {
				response.sendRedirect(request.getContextPath() + "/login");
				return;
			}
		}
	}
}
