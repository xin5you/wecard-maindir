package com.cn.thinkx.wecard.customer.module.pub.service;

import com.cn.thinkx.common.wecard.domain.accmchnt.AccMchntTransLog;

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
	 * @param log
	 * @return
	 */
	int updateAccMchntTransLog(String wxPrimaryKey, String getCode);
}
