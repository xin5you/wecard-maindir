package com.cn.thinkx.wecard.api.module.accmchnt.mapper;

import org.springframework.stereotype.Repository;

import com.cn.thinkx.wecard.api.module.accmchnt.model.AccMchntTransLog;

@Repository("accMchntTransLogMapper")
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
