package com.cn.thinkx.oms.module.channel.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.cn.thinkx.oms.module.channel.model.PaymentChannel;
import com.github.pagehelper.PageInfo;

public interface PaymentChannelService {

	/**
	 * 添加支付通道
	 * 
	 * @param entity
	 * @return
	 */
	int insertPaymentChannel(HttpServletRequest req);

	/**
	 * 修改支付通道
	 * 
	 * @param id
	 * @return
	 */
	int updatePaymentChannelById(HttpServletRequest req);

	/**
	 * 删除支付通道
	 * 
	 * @param id
	 * @return
	 */
	int deletePaymentChannelById(String id);

	/**
	 * 查询支付通道信息
	 * 
	 * @param entity
	 * @return
	 */
	List<PaymentChannel> getPaymentChannelsList(PaymentChannel entity);

	/**
	 * 查询支付通道信息(含分页)
	 * 
	 * @param startNum
	 * @param pageSize
	 * @param entity
	 * @return
	 */
	PageInfo<PaymentChannel> getPaymentChannelsListPage(int startNum, int pageSize, PaymentChannel entity);

	/**
	 * 通过主键查询支付通道信息
	 * 
	 * @param id
	 * @return
	 */
	PaymentChannel getPaymentChannelsById(String id);
	
	/**
	 * 通过通道号查询
	 * 
	 * @param channelNo
	 * @return
	 */
	int getPaymentChannelsByChannelNo(String channelNo);
}
