package com.cn.thinkx.wecard.api.module.mchnt.controller;

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
import com.cn.thinkx.facade.bean.ShopInfQueryRequest;
import com.cn.thinkx.facade.bean.base.BaseTxnReq;
import com.cn.thinkx.facade.service.HKBTxnFacade;
import com.cn.thinkx.pms.base.utils.BaseConstants;
import com.cn.thinkx.wecard.api.module.core.domain.TxnResp;
import com.cn.thinkx.wecard.api.module.customer.util.ChannelInfRedisCacheUtil;
import com.cn.thinkx.wecard.api.module.mchnt.util.MchntReqValid;

@Controller
@RequestMapping("mchnt/shop")
public class ShopInfController {

	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	@Qualifier("hkbTxnFacade")
	private HKBTxnFacade hkbTxnFacade;
	
	/**
	 * 商户门店列表接口
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getShopListQuery")
	@ResponseBody
	public String getShopListQuery(HttpServletRequest request){
		TxnResp resp = new TxnResp();
		
		String channel = request.getParameter("channel");                                       //渠道号
		String longitude =request.getParameter("longitude");                                  //经度
		String latitude  =request.getParameter("latitude");                                      //纬度
		String distance  =request.getParameter("distance");                                   //距离（m）
		String industryType  =request.getParameter("industryType");                     //行业类型
		String sort  =request.getParameter("sort");                                              //排序类型
		String pageNum  =request.getParameter("pageNum");                          //页码
		String itemSize  =request.getParameter("itemSize");                               //每页条数
		String sign  =request.getParameter("sign");                                           //签名
		
		ShopInfQueryRequest req = new ShopInfQueryRequest();
		
		req.setChannel(channel);
		req.setLongitude(longitude);
		req.setLatitude(latitude);
		req.setDistance(distance);
		req.setIndustryType(industryType);
		req.setSort(sort);
		req.setPageNum(pageNum);
		req.setItemSize(itemSize);
		req.setSign(sign);
		
		logger.info("ShopInfController.getShopListQuery req param jsonStr is -------------->" + JSONArray.toJSONString(req));
		//获取渠道信息
		ChannelSecurityInf channelInf = ChannelInfRedisCacheUtil.getChannelSecurityInfByCode(channel);
		
		//参数校验
		if(MchntReqValid.getShopListQueryITF(req, resp, channelInf)){
			return JSONArray.toJSONString(resp);
		}
		
		//重新生成签名
		req.setTimestamp(System.currentTimeMillis());
		req.setSign(ChannelSignUtil.genSign(req));
		
		String resultJsonStr = "";
		
		try {
			resultJsonStr = hkbTxnFacade.getShopListQueryITF(req);
		} catch (Exception e) {
			logger.error("商户门店列表--->" + BaseConstants.RESPONSE_EXCEPTION_INFO, e);
		}
		
		return resultJsonStr;
	}
	
	/**
	 * 商户信息查询接口
	 * @param request
	 * @return
	 */
	@RequestMapping("/getMerchantInfoQuery")
	@ResponseBody
	public String getMerchantInfoQuery(HttpServletRequest request){
		TxnResp resp = new TxnResp();
		
		String channel = request.getParameter("channel");                                                       //渠道号
		String innerMerchantNo = request.getParameter("innerMerchantNo");                        //商户号
		String sign = request.getParameter("sign");                                                                  //签名
		
		BaseTxnReq req = new BaseTxnReq();
		
		req.setChannel(channel);
		req.setInnerMerchantNo(innerMerchantNo);
		req.setSign(sign);
		
		logger.info("ShopInfController.getMerchantInfoQuery req param jsonStr is -------------->" + JSONArray.toJSONString(req));
		
		//获取渠道信息
		ChannelSecurityInf channelInf = ChannelInfRedisCacheUtil.getChannelSecurityInfByCode(channel);
		
		//参数校验
		if(MchntReqValid.getMerchantInfoQueryValid(req, resp, channelInf)){
			return JSONArray.toJSONString(resp);
		}
		
		//重新生成签名
		req.setTimestamp(System.currentTimeMillis());
		req.setSign(ChannelSignUtil.genSign(req));
		
		String resultJsonStr = "";
		
		try {
			resultJsonStr = hkbTxnFacade.getMerchantInfoQueryITF(req);
		} catch (Exception e) {
			logger.error("商户信息查询-->" + BaseConstants.RESPONSE_EXCEPTION_INFO, e);
		}
		
		return resultJsonStr;
	}
	
	/**
	 * 商户门店信息查询接口
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getShopInfQuery")
	@ResponseBody
	public String getShopInfQuery(HttpServletRequest request){
		TxnResp resp = new TxnResp();
		
		String channel = request.getParameter("channel");                                             //渠道号
		String innerMerchantNo = request.getParameter("innerMerchantNo");              //商户号
		String innerShopNo = request.getParameter("innerShopNo");                           //门店好
		String detailFlag = request.getParameter("detailFlag");                                     //是否明细查询
		String sign = request.getParameter("sign");                                                      //签名
		
		ShopInfQueryRequest  req = new ShopInfQueryRequest();
		
		req.setChannel(channel);
		req.setInnerMerchantNo(innerMerchantNo);
		req.setInnerShopNo(innerShopNo);
		req.setDetailFlag(detailFlag);
		req.setSign(sign);
		
		logger.info("ShopInfController.getShopInfQuery req param jsonStr is -------------->" + JSONArray.toJSONString(req));
		
		//获取渠道信息
		ChannelSecurityInf channelInf = ChannelInfRedisCacheUtil.getChannelSecurityInfByCode(channel);
		
		//参数校验
		if(MchntReqValid.getShopInfoQueryValid(req, resp, channelInf)){
			JSONArray.toJSONString(resp);
		}

		//重新生成签名
		req.setTimestamp(System.currentTimeMillis());
		req.setSign(ChannelSignUtil.genSign(req));
		
		String resultJsonStr = "";
		
		try {
			resultJsonStr = hkbTxnFacade.getShopInfoQueryITF(req);
		} catch (Exception e) {
			logger.error("商户门店信息查询-->" + BaseConstants.RESPONSE_EXCEPTION_INFO, e);
		}
		
		return resultJsonStr;
	}
	
	/**
	 * 商户在售卡列表查询接口
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/getMchntSellingCardListQuery")
	@ResponseBody
	public String getMchntSellingCardListQuery(HttpServletRequest request){
		TxnResp resp = new TxnResp();
		
		String channel = request.getParameter("channel");                                                //渠道号
		String innerMerchantNo = request.getParameter("innerMerchantNo");                 //商户号
		String sign = request.getParameter("sign");                                                          //签名
		
		BaseTxnReq req = new BaseTxnReq();
		
		req.setChannel(channel);
		req.setInnerMerchantNo(innerMerchantNo);
		req.setSign(sign);
		
		logger.info("ShopInfController.getMchntSellingCardListQuery req param jsonStr is -------------->" + JSONArray.toJSONString(req));
		
		//获取渠道信息
		ChannelSecurityInf channelInf = ChannelInfRedisCacheUtil.getChannelSecurityInfByCode(channel);
		
		//参数校验
		if(MchntReqValid.getMchtSelListQueryValid(req, resp, channelInf)){
			return JSONArray.toJSONString(resp);
		}
		
		//重新生成签名
		req.setTimestamp(System.currentTimeMillis());
		req.setSign(ChannelSignUtil.genSign(req));
		
		String resultJsonStr = "";
		
		try {
			resultJsonStr = hkbTxnFacade.getMchtSellingCardListQueryITF(req);
		} catch (Exception e) {
			logger.error("商户在售卡列表查询-->" + BaseConstants.RESPONSE_EXCEPTION_INFO, e);
		}
		
		return resultJsonStr;
	}
	
}
