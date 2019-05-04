package com.cn.thinkx.service.drools;

public interface WxDroolsExcutor {

	/**
	 * 获取消费优惠
	 * 
	 * @param mchntId
	 *            商户Id
	 * @param platformId
	 *            平台id
	 * @param oriTxnAmount
	 *            原交易金额
	 * @return 实际交易金额
	 */
	public int getConsumeDiscount(String mchntId, String platformId, int oriTxnAmount) throws Exception;
	
	
	/**
	 * 充值优惠
	 * @param mchntId
	 * @param platformId
	 * @param oriTxnAmount
	 * @return
	 * @throws Exception
	 */
	public int getRechargeDiscount(String mchntId, String platformId, int oriTxnAmount) throws Exception;
}