package com.cn.thinkx.common.wecard.module.merchant.mapper;

import org.apache.ibatis.annotations.Param;

/**
 * 商户账户DAO
 * @author zqy
 *
 */
public interface MchntAcctInfDao {

	/**
	 * 查找商户沉淀资金
	 * @param insId
	 * @param manchtId 商户ID
	 * @return
	 */
	public long getSumAccBalByMchnt(@Param("acctType")String acctType,@Param("acctSata")String acctSata,@Param("insId")String insId,@Param("manchtId")String manchtId);
	
	
	/**
	 * 卡余额 
	 * @param settleDate 清算日期
	 * @param insId		机构
	 * @param manchtId 	商户ID
	 * @return
	 */
	public long getSumCardBalByMchnt(@Param("settleDate")String settleDate,@Param("insId")String insId,@Param("manchtId")String manchtId);
}