package com.cn.thinkx.wecard.customer.module.jfpay.ctrl;

import java.math.BigDecimal;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.cn.thinkx.common.redis.core.JedisClusterUtils;
import com.cn.thinkx.common.redis.util.HttpWebUtil;
import com.cn.thinkx.common.redis.util.RedisDictProperties;
import com.cn.thinkx.common.redis.util.SignUtil;
import com.cn.thinkx.common.redis.vo.CustomerQrCodeVO;
import com.cn.thinkx.common.wecard.domain.channeluser.ChannelUserInf;
import com.cn.thinkx.common.wecard.domain.detail.DetailBizInfo;
import com.cn.thinkx.common.wecard.domain.merchant.MerchantInf;
import com.cn.thinkx.common.wecard.domain.shop.ShopInf;
import com.cn.thinkx.common.wecard.domain.trans.WxTransLog;
import com.cn.thinkx.facade.bean.MchntInfQueryRequest;
import com.cn.thinkx.facade.service.HKBTxnFacade;
import com.cn.thinkx.pms.base.utils.BaseConstants;
import com.cn.thinkx.pms.base.utils.BaseConstants.ChannelCode;
import com.cn.thinkx.pms.base.utils.BaseConstants.ITFRespCode;
import com.cn.thinkx.pms.base.utils.DateUtil;
import com.cn.thinkx.pms.base.utils.NumberUtils;
import com.cn.thinkx.pms.base.utils.RSAUtil;
import com.cn.thinkx.pms.base.utils.StringUtil;
import com.cn.thinkx.wecard.customer.module.customer.service.ChannelUserInfService;
import com.cn.thinkx.wecard.customer.module.customer.service.WxTransLogService;
import com.cn.thinkx.wecard.customer.module.customer.util.SignUtils;
import com.cn.thinkx.wecard.customer.module.jfpay.service.JfPayService;
import com.cn.thinkx.wecard.customer.module.jfpay.vo.JFQrCodeReq;
import com.cn.thinkx.wecard.customer.module.merchant.service.MerchantInfService;
import com.cn.thinkx.wecard.customer.module.merchant.service.ShopInfService;
import com.cn.thinkx.wecard.customer.module.pub.domain.TxnResp;
import com.cn.thinkx.wecard.customer.module.pub.service.CommonSerivce;
import com.cn.thinkx.wecard.customer.module.pub.service.PublicService;
import com.cn.thinkx.wecard.middleware.resp.hkbtxnfacade.entity.MerchantInfoQueryITFResp;
import com.cn.thinkx.wecard.middleware.resp.hkbtxnfacade.vo.MerchantInfoQueryITFVo;
import com.cn.thinkx.wechat.base.wxapi.util.WxConstants;

import net.sf.json.JSONObject;

@Controller
@RequestMapping(value = "/jfPay")
public class JfPayController {
	

	Logger logger = LoggerFactory.getLogger(JfPayController.class);
	
	@Autowired
	@Qualifier("jFPayService")
	private JfPayService jFPayService;
	
	@Autowired
	@Qualifier("wxTransLogService")
	private WxTransLogService wxTransLogService;

	@Autowired
	@Qualifier("shopInfService")
	private ShopInfService shopInfService;

	@Autowired
	@Qualifier("merchantInfService")
	private MerchantInfService merchantInfService;
	
	@Autowired
	private HKBTxnFacade hkbTxnFacade;
	
	@Autowired
	@Qualifier("publicService")
	private PublicService publicService;
	
	@Autowired
	private CommonSerivce commonSerivce;
	
	@Autowired
	@Qualifier("channelUserInfService")
	private ChannelUserInfService channelUserInfService;

	/**
	 * 钱包 扫一扫,输入支付金额之后，跳转到确人支付画面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/payConfirm", method = RequestMethod.POST)
	public ModelAndView payConfirm(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("jfpay/payConfirm");
		DetailBizInfo detail = new DetailBizInfo();
		String payAmountTemp = request.getParameter("payAmount");
		detail.setInsCode(request.getParameter("insCode"));
		detail.setMchntCode(request.getParameter("mchntCode"));
		detail.setMchntName(request.getParameter("mchntName"));
		detail.setShopCode(request.getParameter("shopCode"));
		detail.setShopName(request.getParameter("shopName"));
		String JFUserId = request.getParameter("JFUserId");
		String HKBUserId = request.getParameter("HKBUserId");
		// 金额转换（元转分）
		BigDecimal b1 = new BigDecimal(payAmountTemp);
		BigDecimal b2 = new BigDecimal(100);
		String RMBYuanToCent = b1.multiply(b2).setScale(0, BigDecimal.ROUND_DOWN).toString();
		// 金额转换（分转元）
		BigDecimal b3 = new BigDecimal(RMBYuanToCent);
		BigDecimal b4 = new BigDecimal(100);
		String payAmount = b3.divide(b4).setScale(2, BigDecimal.ROUND_DOWN).toString();

		// 获取页面的key(用于加密密码)，生成密钥的处理
		try {
			mv = getPublicPrivateKey(request, mv);
		} catch (Exception e) {
			logger.error("生成密钥失败", e);
			mv = new ModelAndView("common/500");
			mv.addObject("failureMsg", "系统异常");
			return mv;
		}
		String brandLogo = request.getParameter("brandLogo");
		mv.addObject("brandLogo", brandLogo);
		mv.addObject("detail", detail);
		mv.addObject("payAmount", payAmount);
		mv.addObject("JFUserId", JFUserId);
		mv.addObject("HKBUserId", HKBUserId);
		return mv;
	}
	
	/*
	 * 公钥和私钥 的处理
	 */
	public ModelAndView getPublicPrivateKey(HttpServletRequest request, ModelAndView mv) {
		HashMap<String, Object> map = null;
		try {
			map = RSAUtil.getKeys();
			// 生成公钥和私钥
			RSAPublicKey publicKey = (RSAPublicKey) map.get("public");
			RSAPrivateKey privateKey = (RSAPrivateKey) map.get("private");
			// 私钥保存在session中，用于解密
			request.getSession().setAttribute(WxConstants.RSA_PRIVATE_KEY_SESSION, privateKey);

			// 公钥信息保存在页面，用于加密
			String publicKeyExponent = publicKey.getPublicExponent().toString(16);
			String publicKeyModulus = publicKey.getModulus().toString(16);

			mv.addObject("publicKeyExponent", publicKeyExponent);
			mv.addObject("publicKeyModulus", publicKeyModulus);
		} catch (Exception e) {
			logger.error("生成密钥失败", e);
			mv = new ModelAndView("common/500");
			mv.addObject("failureMsg", "系统异常");
		}
		return mv;
	}
	
	
	/** 消费交易-嘉福   Created by Pucker 2016/8/1 **/
	/** 消费交易-嘉福  Edit by Pucker 2017/11/1 **/
	@RequestMapping(value = "/scanCodeJava2JFBusiness")
	@ResponseBody
	public TxnResp scanCodeJava2JFBusiness(HttpServletRequest request, HttpSession session) {
		TxnResp resp = jFPayService.scanCodeJava2JFBusiness(request);
		return resp;
	}
	
	/**消费交易-插入微信端流水-嘉福   Created by Pucker 2016/7/20 **/
	/**消费交易-插入微信端流水-嘉福   Edit by Pucker 2017/10/31 **/
	@RequestMapping(value = "/insertWxTransLog")
	@ResponseBody
	public WxTransLog insertWxTransLog(HttpServletRequest request) {
		WxTransLog log = jFPayService.insertWxTransLog(request);
		return log;
	}
	
	/**
	 * 支付成功
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/paySuccess")
	public ModelAndView paySuccess(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("jfpay/paySuccess");;
		String transAmt = StringUtil.nullToString(request.getParameter("transAmt"));
		String transId = StringUtil.nullToString(request.getParameter("transId"));
		String mchntCode = StringUtil.nullToString(request.getParameter("mchntCode"));
		String amount = "";
		if (!StringUtil.isNullOrEmpty(transAmt)) {
			amount = StringUtil.isNullOrEmpty(transAmt) ? "0" : NumberUtils.RMBCentToYuan(transAmt);// 分转成元
		}

		String wxPrimaryKey = StringUtil.trim(request.getParameter("wxTransLogKey"));// 业务流水(微信端)
		
		if (!StringUtil.isNullOrEmpty(transId)) {
			if ("W10".equals(transId) || "W71".equals(transId)) {
				WxTransLog log = wxTransLogService.getWxTransLogById(wxPrimaryKey);// 查询业务流水
				if (log != null) {
					ShopInf shopInf = shopInfService.getShopInfByCode(log.getShopCode());
					MerchantInf merchntInf = merchantInfService.getMerchantInfByCode(log.getMchntCode());
					mv.addObject("shopName", shopInf.getShopName());
					mv.addObject("mchntName", merchntInf.getMchntName());
					mv.addObject("transTime", com.cn.thinkx.wechat.base.wxapi.util.DateUtil.COMMON_FULL.getFormat()
							.format(log.getCreateTime()));
				}
			}
		}
		mv.addObject("transAmt", amount);
		mv.addObject("transId", transId);
		mv.addObject("mchntCode", mchntCode);
		return mv;
	}

	/**
	 * 支付失败
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/payFailed")
	public ModelAndView payFailed(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("jfpay/payfail");

		String transCode = StringUtil.nullToString(request.getParameter("transCode"));
		String transAmt = StringUtil.nullToString(request.getParameter("transAmt"));
		String transId = StringUtil.nullToString(request.getParameter("transId"));
		String mchntCode = StringUtil.nullToString(request.getParameter("mchntCode"));
		String errorInfo = "";
		String amount = "";
		if (!StringUtil.isNullOrEmpty(transAmt)) {
			amount = StringUtil.isNullOrEmpty(transAmt) ? "0" : NumberUtils.RMBCentToYuan(transAmt);// 分转成元
		}
		if (StringUtil.isNullOrEmpty(transCode)) {
			transCode = BaseConstants.TXN_TRANS_ERROR;
		}
		if (BaseConstants.TXN_TRANS_ERROR.equals(transCode)
				|| BaseConstants.RESPONSE_EXCEPTION_CODE.equals(transCode)) {
			errorInfo = BaseConstants.RESPONSE_EXCEPTION_INFO;
		} else {
			ITFRespCode respCodeEnum = ITFRespCode.findByCode(transCode);
			if (respCodeEnum != null) {
				errorInfo = respCodeEnum.getValue();
			} else {
				errorInfo = BaseConstants.RESPONSE_EXCEPTION_INFO;
			}
		}

		mv.addObject("transCode", transCode);
		mv.addObject("transAmt", amount);
		mv.addObject("transId", transId);
		mv.addObject("errorInfo", errorInfo);
		mv.addObject("mchntCode", mchntCode);
		return mv;
	}
	
	/** 钱包 扫一扫 Created by Pucker 2016/8/12 **/
	@RequestMapping(value = "/scanCode")
	public ModelAndView scanCode(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("jfpay/scanCode");

		String mchntCode = request.getParameter("mchntCode");
		String shopCode = request.getParameter("shopCode");
		String JFUserId = request.getParameter("JFUserId");
		String HKBUserId= request.getParameter("HKBUserId");

		if (StringUtil.isNullOrEmpty(mchntCode) || StringUtil.isNullOrEmpty(shopCode)) {
			logger.info("Customer scan code failed that's because of the merchant code or shop code is null.");
			mv = new ModelAndView("common/failure");
			mv.addObject("failureMsg", "扫描二维码失败");
		} else {
			getMchntInfo(mv,mchntCode, shopCode,JFUserId,HKBUserId);
		}
		return mv;
	}
	
	public void getMchntInfo(ModelAndView mv,String mchntCode,String shopCode,String JFUserId,String HKBUserId) {
		if (!StringUtil.isNullOrEmpty(JFUserId)) {
			mv.addObject("JFUserId", JFUserId);
		}
		if (!StringUtil.isNullOrEmpty(HKBUserId)) {
			mv.addObject("HKBUserId", HKBUserId);
		}
		DetailBizInfo detail = new DetailBizInfo();
		if (!"null".equals(mchntCode) && !StringUtil.isNullOrEmpty(mchntCode))
			detail.setMchntCode(mchntCode);
		if (!"null".equals(shopCode) && !StringUtil.isNullOrEmpty(shopCode))
			detail.setShopCode(shopCode);
		detail = publicService.getDetailBizInfo(detail);
		if (detail == null) {
			mv.addObject("detail", new DetailBizInfo());
		} 

		// 查询商户二维码信息
		MerchantInfoQueryITFResp merchantInfo = new MerchantInfoQueryITFResp(); // 商户门店信息

		long timestamp = System.currentTimeMillis();// 时间戳
		MchntInfQueryRequest baseTxnReq = new MchntInfQueryRequest();

		try {
			baseTxnReq.setInnerMerchantNo(mchntCode);
			baseTxnReq.setTimestamp(timestamp);
			baseTxnReq.setSign(SignUtil.genSign(baseTxnReq));

			String jsonStr = hkbTxnFacade.merchantInfoQueryITF(baseTxnReq);
			merchantInfo = JSON.parseObject(jsonStr, MerchantInfoQueryITFResp.class);

			MerchantInfoQueryITFVo mchntInfo = null;
			if (merchantInfo != null && BaseConstants.TXN_TRANS_RESP_SUCCESS.equals(merchantInfo.getCode())) {
				if (merchantInfo.getMerchantInfo() != null) {
					mchntInfo = merchantInfo.getMerchantInfo();
				}
			}
			if (mchntInfo == null) {
				mchntInfo = new MerchantInfoQueryITFVo();
			}
			detail.setBrandLogo(mchntInfo.getBrandLogo());
			detail.setMchntType(mchntInfo.getMchntType());
			mv.addObject("detail", detail);
		} catch (Exception e) {
			logger.error("钱包 扫一扫	 查询商户信息失败--->", e);
		}
	}
	
	/** 提供给嘉福的 付款码 Created by xiaomei 2018/4/18 **/
	@RequestMapping(value = "/user/qrCode")
	public ModelAndView jfQrCode(HttpServletRequest request, JFQrCodeReq jfReq) {
//		logger.info("二维码生成器方法  请求参数[{}]", jfReq.toString());
		ModelAndView mv = new ModelAndView("jfpay/qrCode");
		
		if (StringUtil.isNullOrEmpty(jfReq.getHkbUserID()) || StringUtil.isNullOrEmpty(jfReq.getJfUserID())) {
			logger.error("## 二维码生成器方法--->request hkbUserID or jfUserID is null");
			mv = new ModelAndView("common/500");
			mv.addObject("failureMsg", "网络异常，请稍后再试");
			return mv;
		}
		
		String key = RedisDictProperties.getInstance().getdictValueByCode("JF_QRCODE_MD5_KEY");
		if (!SignUtils.genSign(jfReq, key).equals(jfReq.getSign())) {
			logger.error("## 二维码生成器方法--->验签失败,请求参数为[{}]", jfReq.toString());
			mv = new ModelAndView("common/500");
			mv.addObject("failureMsg", "网络异常，请稍后再试");
			return mv;
		}
		
		ChannelUserInf channelUserInf = new ChannelUserInf();
		channelUserInf.setUserId(jfReq.getHkbUserID());
		channelUserInf.setChannelCode(ChannelCode.CHANNEL4.toString());
		String openid = channelUserInfService.getExternalId(channelUserInf);
		if (StringUtil.isNullOrEmpty(openid)) {
			logger.error("## 二维码生成器方法--->request hkbUserID[{}]在知了企服用户系统中不存在", jfReq.getHkbUserID());
			mv = new ModelAndView("common/500");
			mv.addObject("failureMsg", "网络异常，请稍后再试");
			return mv;
		}
		
		mv.addObject("hkbUserID", jfReq.getHkbUserID());
		mv.addObject("jfUserID", jfReq.getJfUserID());
		mv.addObject("openid", openid);
		
		try {
			// 生成密钥的处理
			mv = getPublicPrivateKey(request, mv);
			// websocket 连接域名
			mv.addObject("wsUrl", HttpWebUtil.getMerchantWsUrl());
			// map = RSAUtil.getKeys();
			// //生成公钥和私钥
			// RSAPublicKey publicKey = (RSAPublicKey) map.get("public");
			// RSAPrivateKey privateKey = (RSAPrivateKey) map.get("private");
			// //私钥保存在session中，用于解密
			// request.getSession().setAttribute(WxConstants.RSA_PRIVATE_KEY_SESSION,
			// privateKey);
			//
			// //公钥信息保存在页面，用于加密
			// String publicKeyExponent =
			// publicKey.getPublicExponent().toString(16);
			// String publicKeyModulus = publicKey.getModulus().toString(16);
			//
			// mv.addObject("publicKeyExponent", publicKeyExponent);
			// mv.addObject("publicKeyModulus", publicKeyModulus);
			//
		} catch (Exception e) {
			logger.error("生成密钥失败", e);
			mv = new ModelAndView("common/500");
			mv.addObject("failureMsg", "网络异常，请稍后再试");
			return mv;
		}
		
		return mv;
	}

	/** 钱包 定时生成付款二维码 Created by xiaomei 2018/4/18 **/
	@RequestMapping(value = "/user/genCustomerQrcode")
	@ResponseBody
	public Map<String, String> genCustomerQrcode(HttpServletRequest request) {
		Map<String, String> obj = new HashMap<String, String>();
		obj.put("codeValue", "");

		String payType = request.getParameter("payType");
		String hkbUserID = request.getParameter("hkbUserID");
		String jfUserID = request.getParameter("jfUserID");
		String openID = request.getParameter("openID");
//		logger.info("钱包 定时生成付款码  params--->payType[{}], hkbUserID[{}], jfUserID[{}], openID[{}]", payType, hkbUserID, jfUserID, openID);
		
		if (StringUtil.isNullOrEmpty(StringUtil.trim(openID)) || StringUtil.isNullOrEmpty(jfUserID) || StringUtil.isNullOrEmpty(hkbUserID)) {
			logger.error("## 钱包二维码生成失败：openid，jfUserID 或   hkbUserID 获取失败");
			return obj;
		}

		long currTime = DateUtil.getCurrentTimeMillis(); // 和商户端获取时间戳保持一致
		// 将用户信息封装 成对象
		CustomerQrCodeVO customerQrCodeVo = new CustomerQrCodeVO();
		customerQrCodeVo.setCurrTime(currTime);
		customerQrCodeVo.setOpenid(openID);
		customerQrCodeVo.setPayType(payType);
		customerQrCodeVo.setHkbUserID(hkbUserID);
		customerQrCodeVo.setJfUserID(jfUserID);

		// 对象转JSON
		JSONObject json = JSONObject.fromObject(customerQrCodeVo);
		// 转成String
		String jsonStr = json.toString();
		// 获取二维码auth_code数字
		String authCode = commonSerivce.findMmSsAddSeqId("40");
		try {
			// 向redis中set 当前用户auth_code 信息
			JedisClusterUtils.getInstance().set(authCode, jsonStr, 90);// 设置90秒过期
			String codeValue = authCode;
			obj.put("codeValue", codeValue);
		} catch (Exception e) {
			logger.error("## 二维码加密生成失败：", e);
		}
		return obj;
	}
	
}
