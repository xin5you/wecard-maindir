package com.cn.iboot.diy.api.original.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.cn.iboot.diy.api.base.mapper.BaseDao;
import com.cn.iboot.diy.api.original.domain.MchntStatistics;
@Mapper
public interface MchntStatisticsMapper extends BaseDao<MchntStatistics> {
	

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
