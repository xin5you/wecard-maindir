package com.cn.thinkx.api.bm001.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cn.thinkx.api.bm001.constants.BMConstants;
import com.cn.thinkx.api.bm001.domain.BaseResult;
import com.cn.thinkx.api.bm001.req.PayBillReq;
import com.cn.thinkx.api.bm001.service.BMOpenApiService;
import com.cn.thinkx.api.bm001.service.BMOrderService;
import com.cn.thinkx.api.bm001.utils.ResultsUtil;
import com.qianmi.open.api.response.BmOrderCustomGetResponse;
import com.qianmi.open.api.response.BmRechargeMobileGetItemInfoResponse;
import com.qianmi.open.api.response.BmRechargeMobilePayBillResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("bmOrderService")
public class BMOrderServiceImpl implements BMOrderService {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private BMOpenApiService bmOpenApiService;

	@Override
	public BaseResult handleHbkToBMPayBill(String mobileNo, String rechargeAmount, String orderId, String callBack, String accessToken) {
		try {
			BmRechargeMobileGetItemInfoResponse getItemInfoResp = bmOpenApiService.handleGetItemInfo(mobileNo, rechargeAmount, accessToken);
			logger.info("查询单个话费直充商品是否有效，返回参数[{}]", JSONObject.toJSONString(getItemInfoResp));
			if (!getItemInfoResp.isSuccess()) {
				return ResultsUtil.error(BaseResult.ERROR_CODE, getItemInfoResp.getMsg());
			} 
		} catch (Exception e) {
			logger.error(" ## 查询单个话费直充商品是否有效异常-->[{}]",e);
		}
		PayBillReq payBillReq = new PayBillReq();
		payBillReq.setMobileNo(mobileNo);
		payBillReq.setRechargeAmount(rechargeAmount);
		payBillReq.setOuterTid(orderId);
		payBillReq.setCallback(callBack);
		
		try {
			BmRechargeMobilePayBillResponse payBillResp = bmOpenApiService.handlePayBill(payBillReq, accessToken);
			logger.info("话费订单充值，返回参数[{}]", JSONObject.toJSONString(payBillResp));
			if (payBillResp != null) {
				if (!payBillResp.isSuccess()) {
					logger.info("话费订单充值受理失败，返回参数[{}]",JSONObject.toJSONString(payBillResp));
					return ResultsUtil.error(BaseResult.ERROR_CODE, payBillResp.getMsg());
				}
				if (BMConstants.RechargeState.RechargeState09.getCode().equals(payBillResp.getOrderDetailInfo().getRechargeState())) {
					return ResultsUtil.error(BaseResult.ERROR_CODE, payBillResp.getMsg());
				}else{
					return ResultsUtil.success(payBillResp.getOrderDetailInfo());
				}
			} else {
				BmOrderCustomGetResponse customOrderResp = bmOpenApiService.handleGetCustomOrder(orderId, accessToken);
				logger.info("话费订单查询，返回参数[{}]",JSONObject.toJSONString(customOrderResp));
				if(customOrderResp != null){
					if(!customOrderResp.isSuccess()){
						return ResultsUtil.error(BaseResult.ERROR_CODE, customOrderResp.getMsg());
					}
					if (BMConstants.RechargeState.RechargeState09.getCode().equals(customOrderResp.getOrderDetailInfo().getRechargeState())) {
						return ResultsUtil.error(BaseResult.ERROR_CODE, customOrderResp.getMsg());
					} else {
						return ResultsUtil.success(customOrderResp.getOrderDetailInfo());
					}
				} else {
					return ResultsUtil.error(BaseResult.ERROR_CODE, BaseResult.ERROR_MSG);
				}
			}
		} catch (Exception e) {
			logger.error(" ## 话费订单充值异常，请求参数[{}]",payBillReq.toString());
			return ResultsUtil.error(BaseResult.ERROR_CODE, BaseResult.ERROR_MSG);
		}
	}
}
