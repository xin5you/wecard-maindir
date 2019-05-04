package com.cn.thinkx.merchant.service;

import com.cn.thinkx.merchant.domain.MerchantCashManager;
import com.cn.thinkx.merchant.domain.MerchantInf;
import com.cn.thinkx.wechat.base.wxapi.domain.AccountFans;

public interface MerchantInfService {

	/**
	 * 商户代表注册
	 * @param fansId 粉丝表ID
	 * @param merchantInf  商户表
	 * @param 商户明细Id  商户表
	 */
	public int addMerchantByBossReg(AccountFans accountFans,MerchantInf merchantInf) throws Exception;
	
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
	
}
