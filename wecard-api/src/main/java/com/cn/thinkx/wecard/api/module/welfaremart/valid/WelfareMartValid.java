package com.cn.thinkx.wecard.api.module.welfaremart.valid;

import java.util.Map;
import java.util.SortedMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.cn.thinkx.common.redis.util.ChannelSignUtil;
import com.cn.thinkx.pms.base.utils.StringUtil;
import com.cn.thinkx.wecard.api.module.welfaremart.vo.NotifyOrder;
import com.cn.thinkx.wechat.base.wxapi.util.WxSignUtil;

public class WelfareMartValid {
	
	public static Logger logger = LoggerFactory.getLogger(WelfareMartValid.class);

	/**
	 * 卡券集市 异步回调 参数校验
	 * 
	 * @param resp
	 * @return
	 */
	public static boolean buyCardNotifyValid(NotifyOrder resp) {
		logger.info("卡券集市--->购卡异步回调接口请求参数[{}]", JSONArray.toJSONString(resp));
		if (StringUtil.isNullOrEmpty(resp.getUserId())) {
			logger.error("##卡券集市--->购卡异步回调接口，userID为空");
			return true;
		}
		if (StringUtil.isNullOrEmpty(resp.getChannel())) {
			logger.error("##卡券集市--->购卡异步回调接口，渠道号为空，userID[{}]", resp.getUserId());
			return true;
		}
		if (StringUtil.isNullOrEmpty(resp.getRespResult())) {
			logger.error("##卡券集市--->购卡异步回调接口，成功失败标识为空，userID[{}]", resp.getUserId());
			return true;
		}
		if (StringUtil.isNullOrEmpty(resp.getInnerMerchantNo())) {
			logger.error("##卡券集市--->购卡异步回调接口，商户号为空，userID[{}]", resp.getUserId());
			return true;
		}
		if (StringUtil.isNullOrEmpty(resp.getInnerShopNo())) {
			logger.error("##卡券集市--->购卡异步回调接口，门店号为空，userID[{}]", resp.getUserId());
			return true;
		}
		if (StringUtil.isNullOrEmpty(resp.getOrderId())) {
			logger.error("##卡券集市--->购卡异步回调接口，商户订单号为空，userID[{}]", resp.getUserId());
			return true;
		}
		if (StringUtil.isNullOrEmpty(resp.getSettleDate())) {
			logger.error("##卡券集市--->购卡异步回调接口，清算日期为空，userID[{}]", resp.getUserId());
			return true;
		}
		if (StringUtil.isNullOrEmpty(resp.getTxnAmount())) {
			logger.error("##卡券集市--->购卡异步回调接口，交易金额为空，userID[{}]", resp.getUserId());
			return true;
		}
		if (StringUtil.isNullOrEmpty(resp.getOriTxnAmount())) {
			logger.error("##卡券集市--->购卡异步回调接口，原交易金额为空，userID[{}]", resp.getUserId());
			return true;
		}
		if (StringUtil.isNullOrEmpty(resp.getSign())) {
			logger.error("##卡券集市--->购卡异步回调接口，签名为空，userID[{}]", resp.getUserId());
			return true;
		}
		String genSign = ChannelSignUtil.genSign(resp);
		if (!genSign.equals(resp.getSign())) {
			StringBuffer buf = new StringBuffer();
			SortedMap<String,String> map = WxSignUtil.genSortedMap(resp);
			for(Map.Entry<String, String> item : map.entrySet()){
				String key = item.getKey();
                String val = item.getValue();
                buf.append(key).append("=").append(val);
                buf.append("&");  
			}
			logger.info("##卡券集市--->购卡异步回调接口，验签失败，签名[{}]，卡券集市生成签名[{}]，userID[{}]", resp.getSign(), genSign, resp.getUserId());
			return true;
		}
		return false;
	}
	
}
