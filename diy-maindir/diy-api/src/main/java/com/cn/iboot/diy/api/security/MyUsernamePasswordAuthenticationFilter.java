package com.cn.iboot.diy.api.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.context.WebApplicationContext;

import com.cn.iboot.diy.api.base.constants.Constants;
import com.cn.iboot.diy.api.base.utils.MD5Utils;
import com.cn.iboot.diy.api.base.utils.StringUtil;
import com.cn.iboot.diy.api.base.utils.StringUtils;
import com.cn.iboot.diy.api.redis.utils.JedisClusterUtils;
import com.cn.iboot.diy.api.system.domain.User;
import com.cn.iboot.diy.api.system.service.UserService;

import net.sf.json.JSONObject;
public class MyUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

	public static final String VALIDATE_CODE = "authCode";
	public static final String USERNAME = "username";
	public static final String PASSWORD = "password";

	private int showCheckCode = 0;
	
	@Autowired
	private WebApplicationContext context;	//获取上下文

	public int getShowCheckCode() {
		return showCheckCode;
	}

	public void setShowCheckCode(int showCheckCode) {
		this.showCheckCode = showCheckCode;
	}

	@Autowired
	private UserService userService;

	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse resp)
			throws AuthenticationException {
		if (!req.getMethod().equals("POST"))
			throw new AuthenticationServiceException("Authentication method not supported: " + req.getMethod());
		// 检测验证码
		checkValidateCode(req);
		String username = obtainUsername(req).trim();
		String password = obtainPassword(req).trim();
		String authCode = obtainValidateCodeParameter(req).trim();
		// 验证用户账号与密码是否对应
//		username = username.trim();
		User user = userService.getUserByPhoneNo(username);
		HttpSession session = req.getSession();
		session = req.getSession(false);// false代表不创建新的session，直接获取当前的session
		if (user == null) {
			session.setAttribute("showCheckCode", "1");
			session.setAttribute("SECURITY_LOGIN_EXCEPTION", "用户名或密码错误！");
			logger.error("手机号为"+username+"的用户名或密码错误！");
			throw new AuthenticationServiceException("手机号为"+username+"的用户名或密码错误！");
		} else if (user.getPassword() == "" || user.getPassword() == null) {
			session.setAttribute("showCheckCode", "1");
			session.setAttribute("SECURITY_LOGIN_EXCEPTION", "用户名或密码错误！");
			logger.error("手机号为"+username+"的用户名或密码错误！");
			throw new AuthenticationServiceException("手机号为"+username+"的用户名或密码错误！");
		} else if (!(user.getDataStat() == "0" || "0".equals(user.getDataStat()))) {
			session.setAttribute("showCheckCode", "1");
			session.setAttribute("SECURITY_LOGIN_EXCEPTION", "当前用户不存在，请重新输入!");
			logger.error("当前用户"+username+"不存在，请重新输入！");
			throw new AuthenticationServiceException("当前用户"+username+"不存在，请重新输入！");
		}
		String sysMd5Password = MD5Utils.MD5(user.getPassword() + authCode);
		if (!user.getPhoneNo().equals(username) || !sysMd5Password.equals(password)) {
			session.setAttribute("showCheckCode", "1");
			session.setAttribute("SECURITY_LOGIN_EXCEPTION", "用户名或密码错误！");
			logger.error("手机号为"+username+"的用户名或密码错误！");
			throw new AuthenticationServiceException("手机号为"+username+"的用户名或密码错误！");
		} else {
			if (session.getAttribute("showCheckCode") == "1")
				session.setAttribute("showCheckCode", "0");
		}
		if(user.getUserName().equals(user.getPhoneNo()))
			user.setUserName(user.getUserName().substring(0, 3) + "****" + user.getUserName().substring(8, 11));
		
		if(!StringUtil.isNullOrEmpty(session.getId())){
			user.setSessionId(session.getId().toString());
//			logger.error("登录用户的sessinId:--->"+session.getId());
		}
		
		JSONObject json = JSONObject.fromObject(user);	// 对象转JSON
		String jsonStr = json.toString();	// 转成String
		JedisClusterUtils.getInstance(context).setex(user.getId(), jsonStr, 1800);// 设置30分钟过期（与session设置的周期相同）
		
		session.setAttribute(Constants.SESSION_USER, user);
//		User u = (User) session.getAttribute(Constants.SESSION_USER);
//		logger.info("session==================>>>"+u.toString());
		session.removeAttribute("SECURITY_LOGIN_EXCEPTION");
		session.removeAttribute("showCheckCode");
		// UsernamePasswordAuthenticationToken实现 Authentication
		// 这里要注意，第二个参数是用md5对密码加密后再去传参，因为密码都是加密后存进数据库的。
		// 如果这里不加密，那么和在数据库取出来的不匹配，最终即使登录账号和密码都正确，也将无法登录成功。
		// 因为在AbstractUserDetailsAuthenticationProvider里还会对用户和密码验证，分别是
		// user = retrieveUser(username, (UsernamePasswordAuthenticationToken) authentication);这个通过才能顺利通过
		//另一个是 additionalAuthenticationChecks(user,(UsernamePasswordAuthenticationToken) authentication);
		//如果retrieveUser方法验证不通过，将无法访问
		UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, user.getPassword());
		// 允许子类设置详细属性
		setDetails(req, authRequest);
		// 运行UserDetailsService的loadUserByUsername 再次封装Authentication
		return this.getAuthenticationManager().authenticate(authRequest);
	}

	protected void checkValidateCode(HttpServletRequest request) {
		HttpSession session = request.getSession();
		String sessionValidateCode = obtainSessionValidateCode(session);
		if (StringUtils.isEmpty(sessionValidateCode)) {
			session.setAttribute("SECURITY_LOGIN_EXCEPTION", "验证码失效，请重新输入");
			throw new AuthenticationServiceException("验证码失效！");
		}
		String authCode = obtainValidateCodeParameter(request);
		if (!authCode.equalsIgnoreCase(sessionValidateCode)) {
			session.setAttribute("SECURITY_LOGIN_EXCEPTION", "验证码错误");
			throw new AuthenticationServiceException("验证码错误！");
		}
	}

	private String obtainValidateCodeParameter(HttpServletRequest request) {
		Object obj = request.getParameter(VALIDATE_CODE);
		return null == obj ? "" : obj.toString();
	}

	protected String obtainSessionValidateCode(HttpSession session) {
		Object obj = session.getAttribute(Constants.RandomCodeType.LOGIN.getCode());
		return null == obj ? "" : obj.toString();
	}

	@Override
	protected String obtainUsername(HttpServletRequest request) {
		Object obj = request.getParameter(USERNAME);
		return null == obj ? "" : obj.toString();
	}

	@Override
	protected String obtainPassword(HttpServletRequest request) {
		Object obj = request.getParameter(PASSWORD);
		return null == obj ? "" : obj.toString();
	}
}
