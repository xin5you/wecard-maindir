package com.cn.thinkx.wecard.centre.module.biz.service;

import java.util.List;

import com.cn.thinkx.common.redis.vo.PaymentChannelVO;

public interface PaymentChannelService {
	/**
	 * 查询支付通道信息
	 * 
	 * @return
	 */
	List<PaymentChannelVO> getPaymentChannelList();

}
