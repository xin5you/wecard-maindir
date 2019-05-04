package com.cn.thinkx.wecard.customer.module.pub.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.cn.thinkx.common.wecard.domain.trans.WxTransLog;
import com.cn.thinkx.wecard.customer.module.pub.domain.TxnResp;
import com.cn.thinkx.wechat.base.wxapi.vo.WxPayCallback;

import net.sf.json.JSONObject;

/**
 * 交易处理接口
 * <p/>
 * 所有客户端交易逻辑的实现区域，交易的事物均在此接口实现类中控制，LOG需统一风格
 * <p/>
 * 暂不支持JDK1.7以上版本
 * 
 * @since 2017/10/31
 * @author pucker
 *
 */
public interface TransActionService {

	/**
	 * 插入微信端交易流水
	 * 
	 * @param httpServletRequest
	 * @return {@link WxTransLog}
	 * @author pucker
	 */
	WxTransLog insertWxTransLog(HttpServletRequest httpServletRequest);
	
	/**
	 * 根据规则引擎计算后的金额判断客户是否需要输入密码 
	 * 
	 * @param httpServletRequest
	 * @return {@link TxnResp}
	 * @author pucker
	 */
	TxnResp doCustomerNeed2EnterPassword(HttpServletRequest httpServletRequest);
	
	/**
	 * 客户端扫码验密后调用交易核心
	 * 
	 * @param httpServletRequest
	 * @param httpSession
	 * @return {@link TxnResp}
	 */
	TxnResp scanCodeJava2TxnBusiness(HttpServletRequest httpServletRequest, HttpSession httpSession);
	
	/**
	 * 卡充值交易
	 * 
	 * @param httpServletRequest
	 * @return JSONObject
	 */
	JSONObject cardRechargeCommit(HttpServletRequest httpServletRequest);
	
	/**
	 * 微信回调
	 * 
	 * @param httpServletRequest
	 * @return {@link WxPayCallback}
	 */
	WxPayCallback wxNotify(HttpServletRequest httpServletRequest);
	
	/**
	 * 微信支付订单查询
	 * 
	 * @param httpServletRequest
	 * @return JSONObject
	 */
	JSONObject queryPayReusult(HttpServletRequest httpServletRequest);
	
	/**
	 * 发送微信客服消息
	 * 
	 * @param httpServletRequest
	 * @return JSONObject
	 */
	JSONObject wxCustomNotify(HttpServletRequest httpServletRequest);
	
	/**
	 * B扫C 快捷支付
	 * 
	 * @param httpServletRequest
	 * @return JSONObject
	 */
	JSONObject BSCweChatQuickPayCommit(HttpServletRequest httpServletRequest);
	
	/**
	 * B扫C 回调
	 * 
	 * @param httpServletRequest
	 * @return String
	 */
	String BSCweChantQuickNotify(HttpServletRequest httpServletRequest);
	
	/**
	 * B扫C 交易查询
	 * 
	 * @param httpServletRequest
	 * @return {@link TxnResp}
	 */
	TxnResp BSCweChantQuickPayOrderQuery(HttpServletRequest httpServletRequest);
	
	/**
	 * C扫B 微信快捷支付
	 * 
	 * @param httpServletRequest
	 * @return JSONObject
	 */
	JSONObject CSBweChatQuickPayCommit(HttpServletRequest httpServletRequest);
	
	/**
	 * C扫B 回调
	 * 
	 * @param httpServletRequest
	 * @return String
	 */
	String CSBweChantQuickNotify(HttpServletRequest httpServletRequest);
	
	/**
	 * C扫B 交易查询
	 * 
	 * @param httpServletRequest
	 * @param httpSession
	 * @return {@link TxnResp}
	 */
	TxnResp CSBweChantQuickPayOrderQuery(HttpServletRequest httpServletRequest);
	
	/**
	 * 购卡充值-微信支付
	 * @param httpServletRequest
	 * @return JSONObject
	 */
	JSONObject buyCardWechatPayCommit(HttpServletRequest httpServletRequest);
	
	/**
	 * 购卡充值 回调
	 * 
	 * @param request
	 * @return String
	 */
	String buyCardWechatPayNotify(HttpServletRequest httpServletRequest);
	
	/**
	 * 购卡充值 交易查询
	 * 
	 * @param httpServletRequest
	 * @return {@link TxnResp}
	 */
	TxnResp wxBuyCardOrderQuery(HttpServletRequest httpServletRequest);
	
}
