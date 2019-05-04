package com.cn.thinkx.wecard.api.module.accmchnt.service;

import com.cn.thinkx.wecard.api.module.accmchnt.model.AccMchntTransLog;
import com.cn.thinkx.wecard.api.module.core.domain.TxnResp;

public interface AccMchntTransLogService {

	/**
	 * 新增通卡会员卡交易关联流水记录
	 * 
	 * @param log
	 * @return
	 */
	int insertAccMchntTransLog(AccMchntTransLog accmchnt);

	/**
	 * 更新通卡会员卡交易关联流水记录
	 * 
	 * @param accmchnt
	 * @param txnResp
	 * @return
	 */
	int updateAccMchntTransLog(AccMchntTransLog accmchnt, TxnResp txnResp);
}
