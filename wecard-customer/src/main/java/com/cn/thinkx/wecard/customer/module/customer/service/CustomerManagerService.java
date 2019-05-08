package com.cn.thinkx.wecard.customer.module.customer.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.ModelAndView;

import com.cn.thinkx.wecard.customer.module.base.domain.JsonView;
import com.cn.thinkx.wecard.customer.module.pub.domain.TxnResp;

public interface CustomerManagerService {
	
	/**
	 * 用户注册确认
	 * 
	 * @param request
	 * @param openid
	 * @param resp
	 * @param mvSuccess
	 * @param userId
	 * @param password
	 * @param mvFail
	 * @return
	 * @throws Exception
	 */
	boolean userRegisterCommit(HttpServletRequest request, String openid, JsonView resp, ModelAndView mvSuccess, 
			String userId, String password, ModelAndView mvFail) throws Exception;

	/**
	 * 薪无忧通卡账户开通
	 * 
	 * @param userId 用户Id
	 * @param openid 微信openID
	 * @return
	 */
	TxnResp doHKBAccountOpening(String userId, String openid) throws Exception;
	
}
