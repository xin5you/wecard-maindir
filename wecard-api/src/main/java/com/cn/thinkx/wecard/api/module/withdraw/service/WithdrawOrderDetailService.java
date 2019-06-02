package com.cn.thinkx.wecard.api.module.withdraw.service;

import com.cn.thinkx.wecard.api.module.withdraw.domain.WithdrawOrderDetail;
import com.cn.thinkx.wecard.api.module.withdraw.suning.vo.Content;

public interface WithdrawOrderDetailService {
	
	/**
	 * 获取主键
	 * @param paramMap
	 */
	String getPrimaryKey();
	
	int getCountBySerialNo(String serialNo);
	
	int insertWithdrawOrderDetail(WithdrawOrderDetail withdrawOrderDetail);
	
	int updateWithdrawOrderDetail(WithdrawOrderDetail withdrawOrderDetail);
	
	void YFBBatchWithdrawNotify(Content content) throws Exception;
	
	void YFBBatchWithdrawSendMsg(Content content) throws Exception;
	
	boolean YFBBatchWithdrawNotifyUpdateUserCardKey(String userId, String orderId);

	void zfPayNotify(String orderNumber, String inTradeOrderNo, String payMoney, String respCode, String respMsg);
	
}
