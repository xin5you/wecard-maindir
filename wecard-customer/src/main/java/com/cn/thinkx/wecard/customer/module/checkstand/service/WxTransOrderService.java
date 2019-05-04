package com.cn.thinkx.wecard.customer.module.checkstand.service;

import javax.servlet.http.HttpServletRequest;

import com.cn.thinkx.common.wecard.domain.base.ResultHtml;
import com.cn.thinkx.common.wecard.domain.trans.WxTransLog;
import com.cn.thinkx.common.wecard.domain.trans.WxTransOrder;
import com.cn.thinkx.common.wecard.domain.trans.WxTransOrderDetail;
import com.cn.thinkx.wecard.customer.module.checkstand.vo.OrderRefund;
import com.cn.thinkx.wecard.customer.module.checkstand.vo.TranslogRefundReq;
import com.cn.thinkx.wecard.customer.module.pub.domain.TxnResp;

public interface WxTransOrderService {

	/**
	 * 根据订单号查询
	 * @param orderKey
	 * @return
	 */
	WxTransOrder getWxTransOrdeByOrderKey(String orderKey);
	
	
	/**
	 * 根据流水号查询
	 * @param channelCode 渠道号
	 * @param orderId 商户订单号
	 * @param txnFlowNo 交易流水号
	 * @return
	 */
	WxTransOrder getWxTransOrdeByTxnFlowNo(String channelCode,String orderId,String txnFlowNo);
	
	/**
	 * 新增交易订单
	 * @param transOrder
	 * @return
	 */
	int insertWxTransOrder(WxTransOrder transOrder);
	

	/**
	 * 修改交易订单
	 * @param transOrder
	 * @return
	 */
	int updateWxTransOrder(WxTransOrder transOrder);
	
	/**
	 * 保存订单
	 * @param transOrder
	 * @param orderDetail
	 * @return
	 */
	int saveWxTransOrder(WxTransOrder transOrder,WxTransOrderDetail orderDetail);
	
	
	/**
	 * 根据订单号查找订单明细
	 * @param orderKey
	 * @return
	 */
	WxTransOrderDetail getWxTransOrdeDetailByOrderId(String orderId);
	
	/**
	 * 新增交易订单明细
	 * @param transOrder
	 * @return
	 */
	int insertWxTransOrderDetail(WxTransOrderDetail orderDetail);
	

	/**
	 * 修改交易订单明细
	 * @param transOrder
	 * @return
	 */
	int updateWxTransOrderDetail(WxTransOrderDetail orderDetail);
	
	
	/**收银台 会员卡消费交易-验密后调用交易核心**/
	TxnResp doTransOrderJava2TxnBusiness(HttpServletRequest request);
	
	/**收银台 微信快捷支付 回调通知**/
	String doTransOrderWeChantQuickNotify(HttpServletRequest request);
	
	/**收银台 消费交易-插入微信端流水**/
	WxTransLog insertWxTransLogByTransOrder(HttpServletRequest request);
	
	/***判断用户是否已经是某商户会员**/
	String getMchntAccBal(String innerMerchantNo,String userId);
	
	ResultHtml doTranslogRefund(TranslogRefundReq translogRefund,HttpServletRequest request);
	
	/**
	 * 查询wx层交易订单信息
	 * 
	 * @param transOrder
	 * @return
	 */
	WxTransOrder getWxTransOrdeByWxTransOrder(WxTransOrder transOrder);
	
	/**
	 * 交易撤销方法
	 * 
	 * @param request
	 * @return
	 */
	String transOrderRefund(OrderRefund req);
	
}
