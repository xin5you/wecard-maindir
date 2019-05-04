package com.cn.thinkx.common.wecard.module.accmchnt.mapper;

import com.cn.thinkx.common.wecard.domain.accmchnt.AccMchntTransLog;

//@Repository("accMchntTransLogMapper")
public interface AccMchntTransLogMapper {

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
	int updateAccMchntTransLog(AccMchntTransLog accmchnt);
}
