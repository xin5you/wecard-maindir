package com.cn.thinkx.common.wecard.module.trans.mapper;

import org.apache.ibatis.annotations.Param;

import com.cn.thinkx.common.wecard.domain.trans.WxTransOrder;
import com.cn.thinkx.common.wecard.domain.trans.WxTransOrderDetail;

/**
 * 交易订单表
 * @author zqy
 *
 */
public interface WxTransOrderMapper{

	/**
	 * 根据订单号查询
	 * @param orderKey
	 * @return
	 */
	WxTransOrder getWxTransOrdeByOrderKey(@Param("orderKey")String orderKey);
	
	
	/**
	 * 根据流水号查询
	 * @param channelCode 渠道号
	 * @param orderId 商户订单号
	 * @param txnFlowNo 交易流水号
	 * @return
	 */
	WxTransOrder getWxTransOrdeByTxnFlowNo(@Param("channelCode")String channelCode,@Param("orderId") String orderId,@Param("txnFlowNo")String txnFlowNo);
	
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
	int insertWxTransOrderDetail(WxTransOrderDetail transOrderDetail);

	/**
	 * 修改交易订单明细
	 * @param transOrder
	 * @return
	 */
	int updateWxTransOrderDetail(WxTransOrderDetail transOrderDetail);
	
	/**
	 * 查询wx层交易订单信息
	 * 
	 * @param transOrder
	 * @return
	 */
	WxTransOrder getWxTransOrdeByWxTransOrder(WxTransOrder transOrder);
}
