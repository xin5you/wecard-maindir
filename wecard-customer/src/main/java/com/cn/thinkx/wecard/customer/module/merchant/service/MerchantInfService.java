package com.cn.thinkx.wecard.customer.module.merchant.service;

import com.cn.thinkx.common.wecard.domain.merchant.MerchantCashManager;
import com.cn.thinkx.common.wecard.domain.merchant.MerchantInf;

public interface MerchantInfService {

	/**
	 * 查找商户信息
	 * @param merchantId
	 * @return
	 */
	public MerchantInf getMerchantInfById(String mchntId);
	
	
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
	 * 获取商户code 机构code
	 * @param mchntId
	 * @return
	 */
	public MerchantInf getMchntAndInsInfBymchntId(String mchntId);
	
	
	/**
	 * 获取机构code
	 * @param insId
	 * @return
	 */
	public String getInsCodeByInsId(String insId);
	
	/**
	 * 判断用户是否已经是某商户会员
	 * @param mchntCode
	 * @param openid
	 */
	
	public String getCheckMerchantInf(String mchntCode,String openid);
	
}
