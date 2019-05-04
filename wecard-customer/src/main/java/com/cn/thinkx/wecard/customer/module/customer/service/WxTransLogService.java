package com.cn.thinkx.wecard.customer.module.customer.service;

import com.cn.thinkx.common.wecard.domain.trans.WxTransLog;
import com.cn.thinkx.wecard.customer.module.pub.domain.TxnResp;

public interface WxTransLogService {
	
	/**
	 * 获取主键
	 * @return primaryKey
	 */
	String getPrimaryKey();
	
	WxTransLog getWxTransLogById(String id);
	
	int countWxTransLogByOrderId(String orderId);

	int insertWxTransLog(WxTransLog log);


	int updateWxTransLog(String tableNum, String wxPrimaryKey, String respCode, String transAmt);
	
	/**
	 * 更新流水
	 * @param log 微信端流水表对象
	 * @param txnResp 接口段返回信息
	 * @return
	 */
	 int updateWxTransLog(WxTransLog log,TxnResp txnResp);
	 
	 WxTransLog getWxTransLogByWxTransLog(WxTransLog log);
}
