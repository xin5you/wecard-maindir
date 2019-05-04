package com.cn.thinkx.oms.module.withdrawBlacklist.service;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.ModelMap;

import com.cn.thinkx.oms.module.withdrawBlacklist.model.WithdrawBlacklist;
import com.github.pagehelper.PageInfo;

public interface BatchOpenWBService {

	PageInfo<WithdrawBlacklist> getWithdrawBlacklistPage(int startNum, int pageSize, WithdrawBlacklist entity);

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
	ModelMap insertWithdrawBlacklist(HttpServletRequest req);
	
	WithdrawBlacklist getWithdrawBlacklistByUserPhone(String userPhone);
	
	int deleteWithdrawBlacklistById(String id);
}
