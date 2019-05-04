package com.cn.thinkx.common.wecard.module.merchant.mapper;

import com.cn.thinkx.common.wecard.domain.merchant.MerchantCashManager;
import com.cn.thinkx.common.wecard.domain.merchant.MerchantInf;

public interface MerchantInfDao {

	/**
	 * 查找商户信息
	 * @param mId
	 * @return
	 */
	public MerchantInf getMerchantInfById(String mId);
	
	/**
	 * 查找商户信息
	 * @param mchntCode 商户号
	 * @return
	 */
	public MerchantInf getMerchantInfByCode(String mchntCode);
	
	/**
	 * 查找商户提现管理信息
	 * @param mchntId
	 * @return
	 */
	public MerchantCashManager getMerchantCashManagerByMchntId(String mchntId);
	
	/**
	 * 获取机构code
	 * @param insId
	 * @return
	 */
	public String getInsCodeByInsId(String insId);
	
	/**
	 * 获取商户code 机构code
	 * @param mchntId
	 * @return
	 */
	public MerchantInf getMchntAndInsInfBymchntId(String mchntId);
}