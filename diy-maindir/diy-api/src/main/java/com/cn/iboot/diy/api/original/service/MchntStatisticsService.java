package com.cn.iboot.diy.api.original.service;

import java.util.List;

import com.cn.iboot.diy.api.base.service.BaseService;
import com.cn.iboot.diy.api.original.domain.MchntStatistics;

public interface MchntStatisticsService extends BaseService<MchntStatistics> {
	
	/**
	 * 查询商户下的所有门店
	 * @param mchntCode
	 * @return
	 */
	List<String> getShopCodeByMchntCode(String mchntCode);
	
	/**
	 * 查询商户总计
	 * @param ms
	 * @return
	 */
	List<MchntStatistics> getMchntStatisticsList(MchntStatistics ms);
	
	/**
	 * 查询会员卡余额
	 * @param ms
	 * @return
	 */
	String getCardBal(MchntStatistics ms);
}
