package com.cn.thinkx.oms.module.phoneRecharge.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.ModelMap;

public interface TelChannelInfService {

	/**
	 * 通知分销商失败，手动再次回调分销商
	 * 
	 * @param channelOrderId
	 */
	ModelMap doCallBackNotifyChannel(String channelOrderId);

	/**
	 * 添加分销商折扣率
	 * 
	 * @param req
	 * @param channelId
	 * @param ids
	 * @return
	 */
	boolean addTelChannelRate(HttpServletRequest req, String channelId, String channelRate, String ids);
}
