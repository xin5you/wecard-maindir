package com.cn.thinkx.wxclient.service;

import com.cn.thinkx.pub.domain.TxnResp;
import com.cn.thinkx.wxclient.domain.WxTransLog;

public interface WxTransLogService {
	
	/**
	 * 获取主键
	 * @return primaryKey
	 */
	String getPrimaryKey();
	
	WxTransLog getWxTransLogById(String id);

	int insertWxTransLog(WxTransLog log);


	int updateWxTransLog(String tableNum, String wxPrimaryKey, String respCode, String transAmt);
	
	/**
	 * 更新流水
	 * @param log 微信端流水表对象
	 * @param txnResp 接口段返回信息
	 * @return
	 */
	 int updateWxTransLog(WxTransLog log,TxnResp txnResp);
}
