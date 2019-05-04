package com.cn.thinkx.wecard.customer.module.pub.ctrl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cn.thinkx.common.wecard.domain.trans.WxTransLog;
import com.cn.thinkx.wecard.customer.module.base.ctrl.BaseController;
import com.cn.thinkx.wecard.customer.module.pub.domain.TxnResp;
import com.cn.thinkx.wecard.customer.module.pub.service.TransActionService;
import com.cn.thinkx.wechat.base.wxapi.process.MsgXmlUtil;
import com.cn.thinkx.wechat.base.wxapi.vo.WxPayCallback;

import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "/pay")
public class TransController extends BaseController{
	Logger logger = LoggerFactory.getLogger(TransController.class);
	
	@Autowired
	private TransActionService transActionService;
	
	/**消费交易-插入微信端流水   Created by Pucker 2016/7/20 **/
	/**消费交易-插入微信端流水   Edit by Pucker 2017/10/31 **/
	@RequestMapping(value = "/insertWxTransLog")
	@ResponseBody
	public WxTransLog insertWxTransLog(HttpServletRequest request) {
		WxTransLog log = transActionService.insertWxTransLog(request);
		return log;
	}
	
	/** 消费交易-根据规则引擎计算后的金额判断客户是否需要输入密码   Created by Pucker 2016/8/1 **/
	/** 消费交易-根据规则引擎计算后的金额判断客户是否需要输入密码    Edit by Pucker 2017/10/31 **/
	@RequestMapping(value = "/doCustomerNeed2EnterPassword")
	@ResponseBody
	public TxnResp doCustomerNeed2EnterPassword(HttpServletRequest request) {
		TxnResp resp = transActionService.doCustomerNeed2EnterPassword(request);
		return resp;
	}
	
	/** 消费交易-验密后调用交易核心   Created by Pucker 2016/8/1 **/
	/** 消费交易-验密后调用交易核心  Edit by Pucker 2017/11/1 **/
	@RequestMapping(value = "/scanCodeJava2TxnBusiness")
	@ResponseBody
	public TxnResp scanCodeJava2TxnBusiness(HttpServletRequest request, HttpSession session) {
		TxnResp resp = transActionService.scanCodeJava2TxnBusiness(request, session);
		return resp;
	}
	
	/** 卡充值交易   Created by Pucker 2016/8/17 **/
	/** 卡充值交易   Edit by Pucker 2017/11/2 **/
	@RequestMapping(value = "/cardRecharge")
	@ResponseBody
	public JSONObject cardRechargeCommit(HttpServletRequest request) {
		JSONObject jsonObj = transActionService.cardRechargeCommit(request);
		return jsonObj;
	}
	
	/** 微信回调通知   Edit by Pucker 2017/11/2 **/
	@RequestMapping(value = "/wxNotify")
	@ResponseBody 
	public String wxNotify(HttpServletRequest request) {
		WxPayCallback back = transActionService.wxNotify(request);
		return MsgXmlUtil.toXML(back);
	}
	
	/** 申鑫支付回调通知   add by Pucker 2018/3/6 **/
	@RequestMapping(value = "/sxNotify")
	@ResponseBody 
	public String sxNotify(HttpServletRequest request) {
		
		return null;
	}
	
	/** 微信支付订单查询   Edit by Pucker 2017/11/2 **/
	@RequestMapping(value = "/queryPayReusult")
	@ResponseBody 
	public JSONObject queryPayReusult(HttpServletRequest request) {
		JSONObject obj = transActionService.queryPayReusult(request);
		return obj;
	}
	
	/** 发送微信客服消息  Edit by Pucker 2017/11/2 **/
	@RequestMapping(value = "/wxCustomNotify")
	@ResponseBody 
	public JSONObject wxCustomNotify(HttpServletRequest request) {
		JSONObject obj = transActionService.wxCustomNotify(request);
		return obj;
	}
	
	/** B扫C 微信快捷支付  调用微信网关 请求支付 Created by zqy 2017/2/24 **/
	/** B扫C 微信快捷支付  调用微信网关 请求支付 Edit by Pucker 2017/11/2 **/
	@RequestMapping(value = "/BSCweChatQuickPay")
	@ResponseBody
	public JSONObject BSCweChatQuickPayCommit(HttpServletRequest request) {
		JSONObject obj = transActionService.BSCweChatQuickPayCommit(request);
		return obj;
	}
	
	/** B扫C 微信快捷支付 回调通知 Created by zqy 2017/2/24 **/
	/** B扫C 微信快捷支付 回调通知 Edit by Pucker 2017/11/2 **/
	@RequestMapping(value = "/BSCweChantQuickNotify")
	@ResponseBody 
	public String BSCweChantQuickNotify(HttpServletRequest request) {
		String rtnStr = transActionService.BSCweChantQuickNotify(request);
		return rtnStr;
	}
	
	/** B扫C 消费交易查询  Created by zqy 2017/3/9 **/
	/** B扫C 消费交易查询  Edit by Pucker 2017/11/3 **/
	@RequestMapping(value = "/BSCweChantQuickPayOrderQuery")
	@ResponseBody
	public TxnResp BSCweChantQuickPayOrderQuery(HttpServletRequest request, HttpSession session) {
		TxnResp resp = transActionService.BSCweChantQuickPayOrderQuery(request);
		return resp;
	}
	
	/** C扫B 微信快捷支付  调用微信网关 请求支付 Created by zqy 2017/3/9 **/
	/** C扫B 微信快捷支付  调用微信网关 请求支付 Edit by Pucker 2017/11/3 **/
	@RequestMapping(value = "/CSBweChatQuickPay")
	@ResponseBody
	public JSONObject CSBweChatQuickPayCommit(HttpServletRequest request) {
		JSONObject jsonObj = transActionService.CSBweChatQuickPayCommit(request);
		return jsonObj;
	}
	
	/** C扫B 微信快捷支付  回调 Created by zqy 2017/3/9 **/
	/** C扫B 微信快捷支付  回调 Edit by Pucker 2017/11/3 **/
	@RequestMapping(value = "/CSBweChantQuickNotify")
	@ResponseBody 
	public String CSBweChantQuickNotify(HttpServletRequest request) {
		String rtnStr = transActionService.CSBweChantQuickNotify(request);
		return rtnStr;
	}
	
	/** C扫B 消费交易查询-Created by zqy 2017/3/9 **/
	/** C扫B 消费交易查询-Edit by Pucker 2017/11/7 **/
	@RequestMapping(value = "/CSBweChantQuickPayOrderQuery")
	@ResponseBody
	public TxnResp CSBweChantQuickPayOrderQuery(HttpServletRequest request, HttpSession session) {
		TxnResp resp = transActionService.CSBweChantQuickPayOrderQuery(request);
		return resp;
	}
	
	/** 购卡充值 微信支付  调用微信网关 请求支付 Created by zqy 2017/4/20 **/
	/** 购卡充值 微信支付  调用微信网关 请求支付 Edit by Pucker 2017/11/7 **/
	@RequestMapping(value = "/buyCardWechatPay")
	@ResponseBody
	public JSONObject buyCardWechatPayCommit(HttpServletRequest request) {
		JSONObject jsonObj = transActionService.buyCardWechatPayCommit(request);
		return jsonObj;
	}
	
	/** 购卡充值 微信支付  回调 Created by zqy 2017/3/9 **/
	/** 购卡充值 微信支付  回调 Edit by Pucker 2017/11/7 **/
	@RequestMapping(value = "/buyCardWechatPayNotify")
	@ResponseBody 
	public String buyCardWechatPayNotify(HttpServletRequest request) {
		String rtnStr = transActionService.buyCardWechatPayNotify(request);
		return rtnStr;
	}
	
	/** 购卡微信支付订单查询  Created by zqy 2017/3/9 **/
	/** 购卡微信支付订单查询  Edit by Pucker 2017/11/7 **/
	@RequestMapping(value = "/wxBuyCardOrderQuery")
	@ResponseBody
	public TxnResp wxBuyCardOrderQuery(HttpServletRequest request, HttpSession session) {
		TxnResp resp = transActionService.wxBuyCardOrderQuery(request);
		return resp;
	}
	
}
