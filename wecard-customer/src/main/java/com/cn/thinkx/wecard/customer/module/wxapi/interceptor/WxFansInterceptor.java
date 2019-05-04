package com.cn.thinkx.wecard.customer.module.wxapi.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.cn.thinkx.pms.base.utils.StringUtil;
import com.cn.thinkx.wecard.customer.core.util.HttpRequestDeviceUtils;
import com.cn.thinkx.wecard.customer.core.util.HttpUtil;
import com.cn.thinkx.wecard.customer.module.wxapi.service.BizService;
import com.cn.thinkx.wecard.customer.module.wxcms.service.AccountFansService;
import com.cn.thinkx.wechat.base.wxapi.domain.AccountFans;
import com.cn.thinkx.wechat.base.wxapi.process.MpAccount;
import com.cn.thinkx.wechat.base.wxapi.process.WxMemoryCacheClient;

/**
 * 微信客户端用户请求验证拦截器
 */
public class WxFansInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private AccountFansService accountFansService;

	@Autowired
	@Qualifier("bizService")
	private BizService bizService;

	/**
	 * 开发者自行处理拦截逻辑， 方便起见，此处只处理includes
	 */
	private String[] excludes;// 不需要拦截的
	private String[] includes;// 需要拦截的

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
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
		if (!HttpRequestDeviceUtils.isMicroMessenger(request)) { // 判读是否是微信浏览器访问
			HttpUtil.redirectUrl(request, response, "/base/unvalidated.html");
			return false;
		}

		String openid = WxMemoryCacheClient.getOpenid(request);// 先从缓存中获取openid
		if (StringUtil.isNullOrEmpty(openid)) {
			return true;
		}

		AccountFans accountFans = accountFansService.getByOpenId(openid); // 获取粉丝信息
		if (accountFans == null || accountFans.getSubscribestatus() == 0) { // 判断是否已经关注了公众号
			MpAccount mpAccount = WxMemoryCacheClient.getSingleMpAccount();
			accountFans = bizService.syncAccountFans(openid, mpAccount, true);
			if (accountFans == null || accountFans.getSubscribestatus() == 0) {
				HttpUtil.redirectUrl(request, response, "/base/fansTips.html");
				return false;
			}
		}
		return true;
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
