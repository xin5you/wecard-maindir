package com.cn.thinkx.wecard.facade.telrecharge.service;

import java.math.BigDecimal;
import java.util.List;

import com.cn.thinkx.wecard.facade.telrecharge.model.TelChannelInf;
import com.github.pagehelper.PageInfo;

public interface TelChannelInfFacade {

	TelChannelInf getTelChannelInfById(String channelId) throws Exception;

	int saveTelChannelInf(TelChannelInf telChannelInf) throws Exception;

	int updateTelChannelInf(TelChannelInf telChannelInf) throws Exception;

	int deleteTelChannelInfById(String channelId) throws Exception;
	
	List<TelChannelInf> getTelChannelInfList(TelChannelInf telChannelInf) throws Exception;
	
	/**
	 * 扣减的渠道金额
	 * @param payAmt 订单金额，需扣减的金额
	 * @return
	 */
	int subChannelReserveAmt(String channelId,BigDecimal payAmt) throws Exception;
	
	PageInfo<TelChannelInf> getTelChannelInfPage(int startNum, int pageSize, TelChannelInf entity) throws Exception;
	
	TelChannelInf getTelChannelInfByMchntCode(String mchntCode) throws Exception;
}
