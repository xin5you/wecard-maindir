package com.cn.thinkx.oms.module.customer.service;

import java.util.List;

import com.cn.thinkx.oms.module.common.model.TxnResp;
import com.cn.thinkx.oms.module.customer.model.UserMerchantAcct;

public interface UserMerchantAcctService {
	
	
	/**
	 * 获取商户下所有的卡
	 * @param entity
	 * @return
	 */
	public List<UserMerchantAcct> getMerchantCardByMchnt(UserMerchantAcct entity);
	
	/***
	 * 客户开卡
	 * @param userId 用户Id
	 * @param personId 用户信息id
	 * @param productCode 产品号
	 * @return TxnResp
	 */
	public TxnResp doCustomerCardOpeningITF(String orderListId,String userId,String personId,String productCode)throws Exception;
	
	
	/***
	 * 客户充值
	 * @param orderListId 订单明细Id
	 * @param userId 用户Id
	 * @param cardNo 卡号
	 * @param transAmt 交易金额（分）
	 * @param mchntCode 商户号
	 * @param insCode  机构号
	 * @return TxnResp
	 */
	public TxnResp doCustomerRechargeTransactionITF(String orderListId,String userId,String cardNo,String transAmt,String mchntCode,String insCode)throws Exception;
	
	/**
	 * 获取所有卡下的余额
	 * 
	 * @param userMerchantAcct
	 * @return
	 */
	List<UserMerchantAcct> getUserMerchantAcctByUser(UserMerchantAcct userMerchantAcct);
}
