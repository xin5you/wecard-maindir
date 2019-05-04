package com.cn.thinkx.wecard.centre.module.biz.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.thinkx.common.redis.vo.PaymentChannelVO;
import com.cn.thinkx.wecard.centre.module.biz.mapper.PaymentChannelMapper;
import com.cn.thinkx.wecard.centre.module.biz.service.PaymentChannelService;

@Service("paymentChannelService")
public class PaymentChannelServiceImpl implements PaymentChannelService {

	@Autowired
	private PaymentChannelMapper paymentChannelMapper;

	@Override
	public List<PaymentChannelVO> getPaymentChannelList() {
		return paymentChannelMapper.getPaymentChannelList();
	}

}
