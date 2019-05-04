package com.cn.thinkx.common.activemq.service;

import java.util.TreeMap;

import com.cn.thinkx.common.activemq.domain.WechatCustomerParam;
import com.cn.thinkx.common.activemq.domain.WechatTemplateParam;

/**
 * 手机充值 分销商存储到
 * @author zqy
 * @description 队列消息生产者，发送消息到队列
 * 
 */

public interface RechargeMobileProducerService {

	/**
	 *话费充值 充值订单号，发送到activemq中
	 * 
	 * @param regOrderId 分销商话费充值订单号
	 */
	void sendRechargeMobileMsg(final String channelOrderId);

}
