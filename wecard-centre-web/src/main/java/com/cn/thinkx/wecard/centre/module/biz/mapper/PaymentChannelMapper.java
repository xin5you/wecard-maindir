package com.cn.thinkx.wecard.centre.module.biz.mapper;

import java.util.List;

import com.cn.thinkx.common.redis.vo.PaymentChannelVO;

public interface PaymentChannelMapper {
	/**
	 * 查询支付通道信息
	 * 
	 * @return
	 */
	List<PaymentChannelVO> getPaymentChannelList();

}
