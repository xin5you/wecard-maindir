package com.cn.thinkx.wecard.centre.module.biz.service;

import com.cn.thinkx.pms.base.redis.vo.PaymentChannelVO;

import java.util.List;

public interface PaymentChannelService {
    /**
     * 查询支付通道信息
     *
     * @return
     */
    List<PaymentChannelVO> getPaymentChannelList();

}
