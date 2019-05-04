package com.cn.thinkx.wecard.customer.module.checkstand.valid;

import java.util.Map;
import java.util.SortedMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cn.thinkx.common.redis.util.ChannelSignUtil;
import com.cn.thinkx.pms.base.utils.BaseConstants.refundFalg;
import com.cn.thinkx.pms.base.utils.StringUtil;
import com.cn.thinkx.wecard.customer.module.checkstand.vo.OrderBaseTxnResp;
import com.cn.thinkx.wecard.customer.module.checkstand.vo.OrderRefund;
import com.cn.thinkx.wecard.customer.module.checkstand.vo.TransOrderReq;
import com.cn.thinkx.wecard.customer.module.checkstand.vo.TransOrderResp;
import com.cn.thinkx.wecard.customer.module.checkstand.vo.TranslogRefundReq;
import com.cn.thinkx.wechat.base.wxapi.util.WxSignUtil;

public class WxTransOrderTxnReqValid {
	
	public static Logger logger = LoggerFactory.getLogger(WxTransOrderTxnReqValid.class);

	
	/**
	 * 收银台 交易订单查询 参数校验
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public static boolean transOrderQueryValid(TransOrderReq req, TransOrderResp resp) {
		
		if (StringUtil.isNullOrEmpty(req.getChannel())) {
			resp.setInfo("渠道号为空");
			return true;
		}
		if (StringUtil.isNullOrEmpty(req.getUserId())) {
			resp.setInfo("用户ID为空");
			return true;
		}
		if (StringUtil.isNullOrEmpty(req.getOrderId()) && StringUtil.isNullOrEmpty(req.getTxnFlowNo())) {
			resp.setInfo("商户订单号和交易流水号二者不能全部为空");
			return true;
		}

		if (StringUtil.isNullOrEmpty(req.getSign())) {
			resp.setInfo("签名为空");
			return true;
		}
		if (!ChannelSignUtil.genSign(req).equals(req.getSign())) {
			resp.setInfo("签名错误");
			return true;
		}
		return false;
	}
	
	/**
	 * 收银台下单 参数校验
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public static boolean unifiedOrderValid(TransOrderReq req, TransOrderResp resp) {
		
		if (StringUtil.isNullOrEmpty(req.getChannel())) {
			resp.setInfo("渠道号为空");
			return true;
		}
		if (StringUtil.isNullOrEmpty(req.getUserId())) {
			resp.setInfo("用户ID为空");
			return true;
		}
		if (StringUtil.isNullOrEmpty(req.getOrderId())) {
			resp.setInfo("商户订单号为空");
			return true;
		}
		if (StringUtil.isNullOrEmpty(req.getInnerMerchantNo())) {
			resp.setInfo("商户号为空");
			return true;
		}
		if (StringUtil.isNullOrEmpty(req.getInnerShopNo())) {
			resp.setInfo("门店号为空");
			return true;
		}
		if (StringUtil.isNullOrEmpty(req.getCommodityName())) {
			resp.setInfo("商品为空");
			return true;
		}
		if (StringUtil.isNullOrEmpty(req.getCommodityNum())) {
			resp.setInfo("商品数量为空");
			return true;
		}
		if (StringUtil.isNullOrEmpty(req.getTxnAmount())) {
			resp.setInfo("交易金额为空");
			return true;
		}
		if (StringUtil.isNullOrEmpty(req.getNotify_url())) {
			resp.setInfo("通知地址为空");
			return true;
		}
		if (StringUtil.isNullOrEmpty(req.getSign())) {
			resp.setInfo("签名为空");
			return true;
		}
		String genSign = ChannelSignUtil.genSign(req);
		if (!genSign.equals(req.getSign())) {
			StringBuffer buf = new StringBuffer();
			SortedMap<String,String> map = WxSignUtil.genSortedMap(req);
			for(Map.Entry<String, String> item:map.entrySet()){
				String key = item.getKey();
                String val = item.getValue();
                buf.append(key).append("=").append(val);
                buf.append("&");  
			}
			logger.info("知了企服生成签名入参 -->" + buf.toString());
			logger.info("知了企服生成签名 -->"+genSign);
			resp.setInfo("签名错误");
			return true;
		}
		return false;
	}
	
	
	/**
	 * 退款 参数校验
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public static boolean translogRefundValid(TranslogRefundReq req, OrderBaseTxnResp resp) {
		
		if (StringUtil.isNullOrEmpty(req.getChannel())) {
			resp.setInfo("渠道号为空");
			return true;
		}
		if (StringUtil.isNullOrEmpty(req.getTxnPrimaryKey())) {
			resp.setInfo("交易流水号为空");
			return true;
		}
		if (StringUtil.isNullOrEmpty(req.getPhoneNumber())) {
			resp.setInfo("手机号为空");
			return true;
		}
		if (StringUtil.isNullOrEmpty(req.getPhoneCode())) {
			resp.setInfo("验证码为空");
			return true;
		}
		if (StringUtil.isNullOrEmpty(req.getSign())) {
			resp.setInfo("签名为空");
			return true;
		}
		String genSign = ChannelSignUtil.genSign(req);
		if (!genSign.equals(req.getSign())) {
			resp.setInfo("签名错误");
			return true;
		}
		return false;
	}
	
	/**
	 * 退款交易参数校验
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public static String transOrderRefundValid(OrderRefund refund) {
		if (StringUtil.isNullOrEmpty(refund.getRefundFlag())) {
			return "退款标志为空";
		}
		if (StringUtil.isNullOrEmpty(refund.getRefundOrderId())) {
			return "退款订单号为空";
		}
		if (StringUtil.isNullOrEmpty(refund.getRefundAmount())) {
			return "退款金额为空";
		}
		if (StringUtil.isNullOrEmpty(refund.getChannel())) {
			return "渠道号为空";
		}
		if (StringUtil.isNullOrEmpty(refund.getTimestamp())) {
			return "时间戳为空";
		}
		if (StringUtil.isNullOrEmpty(refund.getSign())) {
			return "签名为空";
		}
		if (StringUtil.isNullOrEmpty(refund.getOriOrderId())) {
			return "外部原交易订单号为空";
		}
		if (refundFalg.refundFalg1.getCode().equals(refund.getRefundFlag())) {
			logger.info("退款接口--->系统自动发起退款");
		} else if (refundFalg.refundFalg2.getCode().equals(refund.getRefundFlag())) {
			logger.info("退款接口--->用户端发起退款");
		} else {
			return "退款标志不存在";
		}
		/*long currentTime = System.currentTimeMillis();
		if (SignUtils.isSignExpired(currentTime, refund.getTimestamp())) {
			return "签名过期";
		}*/
		String genSign = ChannelSignUtil.genSign(refund);
		if (!genSign.equals(refund.getSign())) {
			logger.error("## 退款接口--->签名验证失败，知了企服生成签名[{}]", genSign);
			return "签名错误";
		}
		return null;
	}
	
}
