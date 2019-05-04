package com.cn.thinkx.wecard.api.module.trans.service;

import com.cn.thinkx.wecard.api.module.core.domain.TxnResp;
import com.cn.thinkx.wecard.api.module.trans.model.WxTransLog;

public interface WxTransLogService {
	
	/**
	 * 获取主键
	 * @return primaryKey
	 */
	String getPrimaryKey();
	
	WxTransLog getWxTransLogById(String id);

	int insertWxTransLog(WxTransLog log);

	/**
	 * 更新流水
	 * @param log 微信端流水表对象
	 * @param txnResp 接口段返回信息
	 * @return
	 */
	 int updateWxTransLog(WxTransLog log,TxnResp txnResp);
}
