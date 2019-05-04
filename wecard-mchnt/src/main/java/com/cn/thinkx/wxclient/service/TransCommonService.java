package com.cn.thinkx.wxclient.service;

import com.cn.thinkx.merchant.domain.MerchantManager;
import com.cn.thinkx.pub.domain.TxnResp;
import com.cn.thinkx.wxclient.domain.WxTransLog;

/**
 * 交易请求封装 service
 * @author zqy
 *
 */
public interface TransCommonService {
	
	/**
	 * 消费交易-根据规则引擎计算后的金额判断客户是否需要输入密码 
	 * @param merchantManager 商户管理员
	 * @param copenId 客户端 openId
	 * @param transAmt drools 计算后的实际金额
	 * @return boolean true 需要输入密码   false 不输入密码
	 */
	public boolean doCustomerNeed2EnterPassword(MerchantManager merchantManager,String copenId,int transAmt)throws Exception;
	
	/**
	 * 消费交易-验密后调用交易核心
	 * @param log 微信端流水
	 * @param merchantManager 商户管理员
	 * @param@param oprOpenid 当前请求操作用户openId
	 * @param copenId 客户端 openId
	 * @param sponsor 请求渠道 // 00--客户微信平台  10--商户微信平台
	 * @param transAmt 交易金额 （单位分）
	 * @param oritxnAmount 原交易金额（单位分）
	 * @param transPwd 交易密码
	 * @return
	 */
	public TxnResp scanCodeJava2TxnBusiness(WxTransLog log,MerchantManager merchantManager,String oprOpenid,String copenId,String sponsor,int oritxnAmount,int transAmt,String transPwd) throws Exception;
}
