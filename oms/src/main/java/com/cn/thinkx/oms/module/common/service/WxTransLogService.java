package com.cn.thinkx.oms.module.common.service;

import com.cn.thinkx.oms.module.common.model.TxnResp;
import com.cn.thinkx.oms.module.common.model.WxTransLog;

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
	 
	 /**
	  * 更新当日流水
	  * 
	  * @param log
	  * @return
	  */
	 int updateWxCurTransLog(WxTransLog log);
	 
	 /**
	  * 更新历史微信流水
	  * 
	  * @param log
	  * @return
	  */
	 int updateWxHisTransLog(WxTransLog log);
}

