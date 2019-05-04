package com.cn.thinkx.wecard.api.module.trans.service;

import java.util.List;

import com.cn.thinkx.wecard.api.module.core.domain.TxnResp;
import com.cn.thinkx.wecard.api.module.trans.model.UserMerchantAcct;

public interface UserMerchantAcctService {
	
	/**
	 * 获取客户的卡产品信息 所属的机构和 商户信息
	 * @param openid 微信openid
	 * @return
	 */
	public List<UserMerchantAcct> getUserMerchantAcctByUser(UserMerchantAcct entity);
	
	/**
	 * 获取商户下所有的卡
	 * @param entity
	 * @return
	 */
	public List<UserMerchantAcct> getMerchantCardByMchnt(UserMerchantAcct entity);
	
	/***
	 * 客户开卡
	 * @param productCode 卡产品Code
	 * @param personId 用户Id
	 * @param openid 
	 * @param mchntCode 商户号
	 * @param insCode  机构号
	 * @return 1:开户成功 ； 0：开户不成功
	 */
	public TxnResp doCustomerCardOpeningITF(String productCode,String personId,String openid,String mchntCode,String insCode)throws Exception;
}
