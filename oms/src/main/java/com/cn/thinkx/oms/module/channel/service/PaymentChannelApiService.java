package com.cn.thinkx.oms.module.channel.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.cn.thinkx.oms.module.channel.model.PaymentChannelApi;
import com.github.pagehelper.PageInfo;

public interface PaymentChannelApiService {

	/**
	 * 添加支付通道对应的API
	 * 
	 * @param entity
	 * @return
	 */
	int insertPaymentChannelApi(HttpServletRequest req);

	/**
	 * 修改支付通道对应的API
	 * 
	 * @param id
	 * @return
	 */
	int updatePaymentChannelApiById(HttpServletRequest req);

	/**
	 * 删除支付通道对应的API
	 * 
	 * @param id
	 * @return
	 */
	int deletePaymentChannelApiById(String id);

	/**
	 * 查询支付通道对应的API
	 * 
	 * @param entity
	 * @return
	 */
	List<PaymentChannelApi> getPaymentChannelsApisList(PaymentChannelApi entity);

	/**
	 * 查询支付通道信息(含分页)
	 * 
	 * @param startNum
	 * @param pageSize
	 * @param entity
	 * @return
	 */
	PageInfo<PaymentChannelApi> getPaymentChannelsApiListPage(int startNum, int pageSize, PaymentChannelApi entity);
	
	/**
	 * 通过主键查询支付通道对应的API
	 * 
	 * @param id
	 * @return
	 */
	PaymentChannelApi getPaymentChannelsApiById(String id);
}
