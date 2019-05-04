package com.cn.thinkx.oms.module.withdrawBlacklist.mapper;

import java.util.List;

import com.cn.thinkx.oms.module.withdrawBlacklist.model.WithdrawBlacklist;

public interface BatchOpenWBMapper {

	/**
	 * 查询提现黑名单列表
	 * 
	 * @param entity
	 * @return
	 */
	List<WithdrawBlacklist> getWithdrawBlacklist(WithdrawBlacklist entity);
	
	/**
	 * 插入提现黑名单
	 * 
	 * @param entity
	 * @return
	 */
	int insertWithdrawBlacklist(WithdrawBlacklist entity);
	
	WithdrawBlacklist getWithdrawBlacklistByUserPhone(String userPhone);
	
	int deleteWithdrawBlacklistById(String id);
}
