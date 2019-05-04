package com.cn.thinkx.oms.module.channel.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cn.thinkx.oms.module.channel.model.PaymentChannelApi;

/**
 * @author kpp
 */
@Repository("paymentChannelApiMapper")
public interface PaymentChannelApiMapper {

	/**
	 * 添加支付通道
	 * 
	 * @param entity
	 * @return
	 */
	int insertPaymentChannelApi(PaymentChannelApi entity);

	/**
	 * 修改支付通道
	 * 
	 * @param id
	 * @return
	 */
	int updatePaymentChannelApiById(PaymentChannelApi entity);

	/**
	 * 删除支付通道
	 * 
	 * @param id
	 * @return
	 */
	int deletePaymentChannelApiById(String id);

	/**
	 * 查询支付通道信息
	 * 
	 * @param entity
	 * @return
	 */
	List<PaymentChannelApi> getPaymentChannelsApisList(PaymentChannelApi entity);

	/**
	 * 通过主键查询支付通道信息
	 * 
	 * @param id
	 * @return
	 */
	PaymentChannelApi getPaymentChannelsApiById(String id);
	
	/**
	 * 删除支付通道(通过通道主键删除)
	 * 
	 * @param channelId
	 * @return
	 */
	int deletePaymentChannelApiByChannelId(String channelId);
}
