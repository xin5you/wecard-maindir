package com.cn.thinkx.wecard.centre.module.biz.service.impl;

import com.cn.thinkx.pms.base.redis.vo.PaymentChannelVO;
import com.cn.thinkx.wecard.centre.module.biz.mapper.PaymentChannelMapper;
import com.cn.thinkx.wecard.centre.module.biz.service.PaymentChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("paymentChannelService")
public class PaymentChannelServiceImpl implements PaymentChannelService {

    @Autowired
    private PaymentChannelMapper paymentChannelMapper;

    @Override
    public List<PaymentChannelVO> getPaymentChannelList() {
        return paymentChannelMapper.getPaymentChannelList();
    }

}
