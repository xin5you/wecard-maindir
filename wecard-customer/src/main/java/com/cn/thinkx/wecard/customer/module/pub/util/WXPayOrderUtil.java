package com.cn.thinkx.wecard.customer.module.pub.util;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cn.thinkx.common.wecard.domain.trans.WxTransLog;
import com.cn.thinkx.wechat.base.wxapi.process.CustomerWxPayApiClient;
import com.cn.thinkx.wechat.base.wxapi.process.WxMemoryCacheClient;

import net.sf.json.JSONObject;

public class WXPayOrderUtil {
	
	private static Logger logger = LoggerFactory.getLogger(WXPayOrderUtil.class);

	/**
	 * 微信快捷消费退款
	 * 
	 * @param wxTransLog
	 * @param request
	 * @return
	 */
	public static JSONObject wxTransRefundOrder(WxTransLog wxTransLog, HttpServletRequest request) {
		JSONObject refundResJson = CustomerWxPayApiClient.getRefundOrder(WxMemoryCacheClient.getSingleMpAccount(),
				wxTransLog.getWxPrimaryKey(), wxTransLog.getWxPrimaryKey(), wxTransLog.getTransAmt(),
				wxTransLog.getTransAmt(), request);// 申請退款
		if (refundResJson != null) {
			if (refundResJson.containsKey("return_code") && "SUCCESS".equals(refundResJson.getString("return_code"))) {
				return refundResJson;
			} else { // 如果失败 重新发起退款
				refundResJson = CustomerWxPayApiClient.getRefundOrder(WxMemoryCacheClient.getSingleMpAccount(),
						wxTransLog.getWxPrimaryKey(), wxTransLog.getWxPrimaryKey(), wxTransLog.getTransAmt(),
						wxTransLog.getTransAmt(), request);// 申請退款
				logger.info("微信快捷消费退款返回[{}]", refundResJson);
			}
		}
		return refundResJson;
	}

	/***
	 * 微信充值退款
	 * 
	 * @param wxTransLog
	 * @param request
	 * @return
	 */
	public static JSONObject wxRechargeRefundOrder(WxTransLog wxTransLog, HttpServletRequest request) {
		JSONObject refundResJson = CustomerWxPayApiClient.getRefundOrder(WxMemoryCacheClient.getSingleMpAccount(),
				wxTransLog.getWxPrimaryKey(), wxTransLog.getWxPrimaryKey(), wxTransLog.getUploadAmt(),
				wxTransLog.getUploadAmt(), request);// 申請退款
		if (refundResJson != null) {
			if (refundResJson.containsKey("return_code") && "SUCCESS".equals(refundResJson.getString("return_code"))) {
				return refundResJson;
			} else {
				// 如果失败 重新发起退款
				refundResJson = CustomerWxPayApiClient.getRefundOrder(WxMemoryCacheClient.getSingleMpAccount(),
						wxTransLog.getWxPrimaryKey(), wxTransLog.getWxPrimaryKey(), wxTransLog.getUploadAmt(),
						wxTransLog.getUploadAmt(), request);// 申請退款
				logger.info("微信充值退款返回[{}]", refundResJson);
			}
		}
		return refundResJson;
	}

}
