package com.cn.thinkx.wecard.api.module.customer.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.cn.thinkx.common.redis.util.ChannelSignUtil;
import com.cn.thinkx.common.service.module.channel.domain.ChannelSecurityInf;
import com.cn.thinkx.facade.bean.CusAccListQueryRequest;
import com.cn.thinkx.facade.bean.CusAccOpeningRequest;
import com.cn.thinkx.facade.bean.CusAccQueryRequest;
import com.cn.thinkx.facade.bean.CustomerBuyCardStocksRequest;
import com.cn.thinkx.facade.service.HKBTxnFacade;
import com.cn.thinkx.pms.base.utils.BaseConstants;
import com.cn.thinkx.wecard.api.module.core.domain.TxnResp;
import com.cn.thinkx.wecard.api.module.customer.util.ChannelInfRedisCacheUtil;
import com.cn.thinkx.wecard.api.module.customer.util.CustomerUserReqValid;

@Controller
@RequestMapping("customer/user")
public class CustomerUserController {
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	@Qualifier("hkbTxnFacade")
	private HKBTxnFacade hkbTxnFacade;
	/**
	 * 客户开户接口
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/accOpening")
	@ResponseBody
	public String customerAccountOpening(HttpServletRequest request){
		TxnResp resp = new TxnResp();
		
		String channel = request.getParameter("channel");                    //渠道号  40001001
		String userId = request.getParameter("userId");                        //用户ID   oyih9wI9GhMAvKEwAQ0UjOR_ZjT4
		String userName = request.getParameter("userName");          //用户姓名  zp 
		String mobile = request.getParameter("mobile");                   //手机号      13501755206
		String sign = request.getParameter("sign");                          //签名
//		String channel = "40001001";
//		String userId = "oyih9wI9GhMAvKEwAQ0UjOR_ZjT4";
//		String userName = "zp";
//		String mobile = "13501755206";
//		String sign = "2A4B3B34DE5145683B44A1E3AC400194";
		
		CusAccOpeningRequest req = new CusAccOpeningRequest();
		req.setChannel(channel);
		req.setUserId(userId);
		req.setUserName(userName);
		req.setMobile(mobile);
		req.setSign(sign);

		logger.info("CustomerUserController.customerAccountOpening req param jsonStr is -------------->" + JSONArray.toJSONString(req));
	
		ChannelSecurityInf channelInf = ChannelInfRedisCacheUtil.getChannelSecurityInfByCode(channel);
		
		// 参数校验
		if (CustomerUserReqValid.cusAccOpeningValid(req, resp, channelInf)) {
			return JSONArray.toJSONString(resp);
		}
		
		//重新生成签名
		req.setTimestamp(System.currentTimeMillis());
		req.setSign(ChannelSignUtil.genSign(req));
		
		String resultjsonStr="";
		try {
			resultjsonStr=hkbTxnFacade.customerAccountOpeningITF(req);
		} catch (Exception e) {
			logger.error("客户开户--->" + BaseConstants.RESPONSE_EXCEPTION_INFO, e);
		}
		return resultjsonStr;
	}
	
	/**
	 *  客户账户查询接口
	 * @param request
	 * @return
	 */
	@RequestMapping("/accQuery")
	@ResponseBody
	public String customerAccountQuery(HttpServletRequest request){
		TxnResp resp = new TxnResp();
		
		String channel = request.getParameter("channel");                                            //渠道号
		String innerMerchantNo = request.getParameter("innerMerchantNo");            //商户号
		String userId = request.getParameter("userId");                                              //用户ID
		String sign = request.getParameter("sign");                                                   //签名
		
		CusAccQueryRequest req = new CusAccQueryRequest();
		
		req.setChannel(channel);
		req.setInnerMerchantNo(innerMerchantNo);
		req.setUserId(userId);
		req.setSign(sign);
		
		logger.info("CustomerUserController.customerAccountQuery req param jsonStr is -------------->" + JSONArray.toJSONString(req));
		//获取渠道信息
		ChannelSecurityInf channelInf = ChannelInfRedisCacheUtil.getChannelSecurityInfByCode(channel);
		
		//参数校验
		if(CustomerUserReqValid.cusAccQueryValid(req, resp, channelInf)){
			return JSONArray.toJSONString(resp);
		}
		//重新生产签名
		req.setTimestamp(System.currentTimeMillis());
		req.setSign(ChannelSignUtil.genSign(req));
		
		String resultJsonStr = "";
		
		try {
			resultJsonStr = hkbTxnFacade.customerAccountQueryITF(req);
		} catch (Exception e) {
			logger.error("客户账户查询--->" + BaseConstants.RESPONSE_EXCEPTION_INFO, e);
		}
		
		return resultJsonStr;
	}
	
	/**
	 * 客户会员卡列表查询接口
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/customerAccListQuery")
	@ResponseBody
	public String customerAccListQuery(HttpServletRequest request){
		TxnResp resp = new TxnResp();
		
		String channel = request.getParameter("channel");                                            //渠道号
		String userId = request.getParameter("userId");                                                //用户ID
		String innerMerchantNo = request.getParameter("innerMerchantNo");            //商户号
		String sign = request.getParameter("sign");                                                     //签名
		
		CusAccListQueryRequest req = new CusAccListQueryRequest();
		
		req.setChannel(channel);
		req.setUserId(userId);
		req.setInnerMerchantNo(innerMerchantNo);
		req.setSign(sign);
		
		logger.info("CustomerUserController.customerAccListQuery req param jsonStr is -------------->" + JSONArray.toJSONString(req));
		//获取渠道信息
		ChannelSecurityInf channelInf = ChannelInfRedisCacheUtil.getChannelSecurityInfByCode(channel);
		
		//参数校验
		if(CustomerUserReqValid.cusAccListQueryValid(req, resp, channelInf)){
			return JSONArray.toJSONString(resp);
		}
		//重新生产签名
		req.setTimestamp(System.currentTimeMillis());
		req.setSign(ChannelSignUtil.genSign(req));
		
		String resultJsonStr = "";
		
		try {
			resultJsonStr = hkbTxnFacade.customerAccListQueryITF(req);
		} catch (Exception e) {
			logger.error("客户会员卡列表查询--->" + BaseConstants.RESPONSE_EXCEPTION_INFO, e);
		}
		
		return resultJsonStr;
	}
	
	/**
	 * 客户可购卡数量查询接口
	 * @param request
	 * @return
	 */
	public String customerBuyCardStocksQuery(HttpServletRequest request){
		TxnResp resp = new TxnResp();
		
		String channel = request.getParameter("channel");                                            //渠道号
		String userId = request.getParameter("userId");                                                //用户ID
		String commodityCode = request.getParameter("commodityCode");                //商品号
		String sign = request.getParameter("sign");                                                    //签名
		
		CustomerBuyCardStocksRequest req = new CustomerBuyCardStocksRequest();
		req.setChannel(channel);
		req.setUserId(userId);
		req.setCommodityCode(commodityCode);
		req.setSign(sign);
		
		logger.info("CustomerUserController.customerBuyCardStocksQuery req param jsonStr is -------------->" + JSONArray.toJSONString(req));
		//获取渠道信息
		ChannelSecurityInf channelInf = ChannelInfRedisCacheUtil.getChannelSecurityInfByCode(channel);
		
		//参数校验
		if(CustomerUserReqValid.customerBuyCardStocksQueryValid(req, resp, channelInf)){
			return JSONArray.toJSONString(resp);
		}
		//重新生产签名
		req.setTimestamp(System.currentTimeMillis());
		req.setSign(ChannelSignUtil.genSign(req));
		
		String resultJsonStr = "";
		
		try {
			resultJsonStr = hkbTxnFacade.customerBuyCardStocksQueryITF(req);
		} catch (Exception e) {
			logger.error("客户可购卡数量查询--->" + BaseConstants.RESPONSE_EXCEPTION_INFO, e);
		}
		
		return resultJsonStr;
	}

}
