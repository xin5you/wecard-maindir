package com.cn.thinkx.wxapi.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.cn.thinkx.core.util.HttpUtil;
import com.cn.thinkx.merchant.service.MerchantManagerService;
import com.cn.thinkx.pms.base.utils.StringUtil;
import com.cn.thinkx.wechat.base.wxapi.process.MpAccount;
import com.cn.thinkx.wechat.base.wxapi.process.OAuthScope;
import com.cn.thinkx.wechat.base.wxapi.process.WxApi;
import com.cn.thinkx.wechat.base.wxapi.process.WxApiClient;
import com.cn.thinkx.wechat.base.wxapi.process.WxMemoryCacheClient;

/**
 * 微信客户端用户请求验证拦截器
 */
public class WxOAuth2Interceptor extends HandlerInterceptorAdapter {
	
	@Autowired
	@Qualifier("merchantManagerService")
	private MerchantManagerService merchantManagerService;

	/**
	 * 开发者自行处理拦截逻辑， 方便起见，此处只处理includes
	 */
	private String[] excludes;// 不需要拦截的
	private String[] includes;// 需要拦截的

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String uri = request.getRequestURI();

		boolean oauthFlag = false;// 为方便展示的参数，开发者自行处理
		for (String s : includes) {
			if (uri.contains(s)) {// 如果包含，就拦截
				oauthFlag = true;
				break;
			}
		}
		if (!oauthFlag) {// 如果不需要oauth认证
			return true;
		}

		String openid = WxMemoryCacheClient.getOpenid(request);// 先从缓存中获取openid		

		if (StringUtils.isBlank(openid)) {// 没有，通过微信页面授权获取
			String code = request.getParameter("code");
			if (!StringUtils.isBlank(code)) {// 如果request中包括code，则是微信回调
				try {
					openid = WxApiClient.getOAuthOpenId(WxMemoryCacheClient.getSingleMpAccount(), code);
					if (!StringUtils.isBlank(openid)) {
						openid = StringUtil.trim(openid);
						WxMemoryCacheClient.setOpenid(request, openid);// 缓存openid
						return true;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			} else {// oauth获取code
				MpAccount mpAccount = WxMemoryCacheClient.getSingleMpAccount();// 获取缓存中的唯一账号
				String redirectUrl = HttpUtil.getRequestFullUriNoContextPath(request);// 请求code的回调url
				String state = OAuth2RequestParamHelper.prepareState(request);
				//带参数的路径请求
				String redirectParam=StringUtil.nullToString(request.getQueryString());
				if(!"".equals(redirectParam)){
					redirectUrl= redirectUrl+"?"+redirectParam;
				}
				String url = WxApi.getOAuthCodeUrl(mpAccount.getAppid(),redirectUrl, OAuthScope.Base.toString(), state);
				HttpUtil.redirectHttpUrl(request, response, url);
				return false;
			}
		} else {
			// System.out.println("#### WxOAuth2Interceptor Session : openid = " + openid);
			return true;
		}
		HttpUtil.redirectUrl(request, response, "/error/101.html");
		return false;
	}

	public String[] getExcludes() {
		return excludes;
	}

	public void setExcludes(String[] excludes) {
		this.excludes = excludes;
	}

	public String[] getIncludes() {
		return includes;
	}

	public void setIncludes(String[] includes) {
		this.includes = includes;
	}

}
