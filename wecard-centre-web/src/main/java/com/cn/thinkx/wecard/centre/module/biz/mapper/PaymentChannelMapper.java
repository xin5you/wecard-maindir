package com.cn.thinkx.wecard.centre.module.biz.mapper;

import com.cn.thinkx.pms.base.redis.vo.PaymentChannelVO;

import java.util.List;

public interface PaymentChannelMapper {
    /**
     * 查询支付通道信息
     *
     * @return
     */
    List<PaymentChannelVO> getPaymentChannelList();

}
