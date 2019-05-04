package com.cn.thinkx.biz.core.service;

/**
 * 
 * @author zqy
 * @description  队列消息生产者，发送消息到队列
 * 
 */

public interface MQProducerService {
	
	
	/**
	 * 发送微信客服消息
	 * @param transId 交易类型
	 * @param txnId 交易流水号
	 * @param txnTime 交易时间(yyyy-MM-dd hh:mm:ss)
	 * @param mchntCode 商户号
	 * @param shopCode 门店号
	 * @param payAmt 支付金额 单位（分）
	 * @param giveAmt 优惠金额（分）
	 * @param phoneNumLast4 客户手机号后4位
	 * @return
	 * @throws Exception
	 */
	boolean sendCustomTextMsg(String transId,String txnId, String txnTime, String mchntCode, String shopCode, String payAmt,
			String giveAmt, String phoneNumLast4) throws Exception;
}
