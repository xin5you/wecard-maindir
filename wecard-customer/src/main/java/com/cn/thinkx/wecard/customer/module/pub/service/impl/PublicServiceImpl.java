package com.cn.thinkx.wecard.customer.module.pub.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.cn.thinkx.beans.TxnPackageBean;
import com.cn.thinkx.common.wecard.domain.detail.DetailBizInfo;
import com.cn.thinkx.common.wecard.domain.trans.WxTransLog;
import com.cn.thinkx.common.wecard.module.pub.mapper.PublicDao;
import com.cn.thinkx.pms.base.utils.BaseConstants;
import com.cn.thinkx.pms.base.utils.BaseConstants.ChannelCode;
import com.cn.thinkx.pms.base.utils.BaseConstants.TransCode;
import com.cn.thinkx.pms.base.utils.DateUtil;
import com.cn.thinkx.pms.base.utils.StringUtil;
import com.cn.thinkx.service.txn.Java2TxnBusinessFacade;
import com.cn.thinkx.wecard.customer.module.customer.service.WxTransLogService;
import com.cn.thinkx.wecard.customer.module.pub.domain.TxnResp;
import com.cn.thinkx.wecard.customer.module.pub.service.PublicService;
import com.cn.thinkx.wechat.base.wxapi.process.CustomerWxPayApi;
import com.cn.thinkx.wechat.base.wxapi.process.CustomerWxPayApiClient;
import com.cn.thinkx.wechat.base.wxapi.process.WxMemoryCacheClient;
import com.cn.thinkx.wechat.base.wxapi.vo.WxPayCallback;

import net.sf.json.JSONObject;

@Service("publicService")
public class PublicServiceImpl implements PublicService {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private WxTransLogService wxTransLogService;
	@Autowired
	private Java2TxnBusinessFacade java2TxnBusinessFacade;
	@Autowired
	@Qualifier("publicDao")
	private  PublicDao publicDao;
	
	@Override
	public String getPrimaryKey() {
		Map<String, String> paramMap = new HashMap<String, String>();
    	paramMap.put("id", "");
    	publicDao.getPrimaryKey(paramMap);
		String id = (String) paramMap.get("id");
		return id;
	}

	@Override
	public DetailBizInfo getDetailBizInfo(DetailBizInfo detail) {
		return publicDao.getDetailBizInfo(detail);
	}

	@Override
	public WxPayCallback queryWxPayReusult(WxTransLog log, WxPayCallback back) {
		// 支付成功
		if (BaseConstants.TXN_TRANS_RESP_SUCCESS.equals(log.getRespCode())
				|| BaseConstants.TRANS_FLAG_YES.equals(log.getTransSt())
				|| !StringUtil.isNullOrEmpty(log.getDmsRelatedKey())) {
			back.setReturn_code("SUCCESS");
			back.setReturn_msg("OK");
			return back;
		} else {// 支付失败或者支付通知回调失败
			JSONObject obj = CustomerWxPayApiClient.orderQuery(WxMemoryCacheClient.getSingleMpAccount(),log.getWxPrimaryKey());// 调用微信查询订单接口
			// 查询订单结果-成功
			if (obj.containsKey("return_code") && "SUCCESS".equals(obj.getString("return_code"))) {
				// 业务结果-成功
				if (obj.containsKey("result_code") && "SUCCESS".equals(obj.getString("result_code"))) {
					String querySign = CustomerWxPayApi.getQueryOrderSign(obj);// 查询订单返回签名
					// 验签通过
					if (obj.containsKey("sign") && obj.getString("sign").equals(querySign)) {
						// 支付状态-成功 操作回调方法未操作的业务
						if (obj.containsKey("trade_state") && "SUCCESS".equals(obj.getString("trade_state"))) {
							String cashFee = obj.getString("cash_fee");// 现金支付金额
							String transId = obj.getString("transaction_id");// 微信支付订单号
							TxnPackageBean txnBean = new TxnPackageBean();
							txnBean.setTxnType(TransCode.CW20.getCode() + "0");// 交易类型，发送报文时补0
							txnBean.setSwtTxnDate(DateUtil.getCurrentDateStr());// 交易日期
							txnBean.setSwtTxnTime(DateUtil.getCurrentTimeStr());// 交易时间
							txnBean.setSwtSettleDate(log.getSettleDate());// 清算日期
							txnBean.setChannel(ChannelCode.CHANNEL2.toString());// 渠道号
							txnBean.setSwtFlowNo(log.getWxPrimaryKey());
							txnBean.setIssChannel(log.getInsCode());// 机构渠道号
							txnBean.setInnerMerchantNo(log.getMchntCode());// 商户号
							txnBean.setInnerShopNo(log.getShopCode());// 门店号
							txnBean.setTxnAmount(cashFee);// 交易金额
							txnBean.setOriTxnAmount(log.getUploadAmt());// 原交易金额
							txnBean.setCardNo(log.getUserInfUserName());// 卡号
							try {
								String json = java2TxnBusinessFacade.rechargeTransactionITF(txnBean);// 远程调用充值接口
								TxnResp resp = JSONArray.parseObject(json, TxnResp.class);
								// 远程接口调用成功
								if (BaseConstants.TXN_TRANS_RESP_SUCCESS.equals(resp.getCode())) {
									log.setRespCode(BaseConstants.TXN_TRANS_RESP_SUCCESS);// 成功-返回码
									log.setTransAmt(cashFee);// 订单实际支付金额
									log.setDmsRelatedKey(transId);// 外部流水号
									wxTransLogService.updateWxTransLog(log, null);// 更新微信端流水
									back.setReturn_code("SUCCESS");
									back.setReturn_msg("OK");
								} else {
									logger.info("微信支付查询订单失败--->远程调用充值接口失败：" + resp.getInfo());
								}
							} catch(Exception e) {
								logger.error("微信支付查询订单失败--->远程调用充值接口异常，流水号：" + log.getWxPrimaryKey(), e);
							}
						}
					}
				} else {
					logger.info("微信支付查询订单失败：" + obj.getString("err_code_des"));
				}
			} else {
				logger.info("微信支付查询订单失败：" + obj.getString("return_msg"));
			}
		}
		return back;
	}
	
}
