package com.cn.thinkx.wecard.customer.module.pub.ctrl;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cn.thinkx.common.wecard.domain.base.ResultHtml;
import com.cn.thinkx.common.wecard.domain.merchant.MerchantInf;
import com.cn.thinkx.common.wecard.domain.shop.ShopInf;
import com.cn.thinkx.common.wecard.domain.trans.WxTransLog;
import com.cn.thinkx.pms.base.utils.BaseConstants;
import com.cn.thinkx.pms.base.utils.BaseConstants.ITFRespCode;
import com.cn.thinkx.pms.base.utils.NumberUtils;
import com.cn.thinkx.pms.base.utils.StringUtil;
import com.cn.thinkx.wecard.customer.module.base.ctrl.BaseController;
import com.cn.thinkx.wecard.customer.module.customer.service.WxTransLogService;
import com.cn.thinkx.wecard.customer.module.merchant.service.MerchantInfService;
import com.cn.thinkx.wecard.customer.module.merchant.service.ShopInfService;
import com.cn.thinkx.wecard.customer.module.pub.service.CommonSerivce;

@Controller
@RequestMapping(value = "/pub")
public class CommonController extends BaseController {

	Logger logger = LoggerFactory.getLogger(CommonController.class);

	@Autowired
	@Qualifier("commonSerivce")
	private CommonSerivce commonSerivce;

	@Autowired
	@Qualifier("wxTransLogService")
	private WxTransLogService wxTransLogService;

	@Autowired
	@Qualifier("shopInfService")
	private ShopInfService shopInfService;

	@Autowired
	@Qualifier("merchantInfService")
	private MerchantInfService merchantInfService;

	/***
	 * 发送短信
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("sendPhoneSMS")
	@ResponseBody
	public ResultHtml sendPhoneSMS(HttpServletRequest request) {
		ResultHtml resultMap = commonSerivce.sendPhoneSMS(request);
		return resultMap;
	}

	/***
	 * 客户会员发送短信
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("sendUserPhoneSMS")
	@ResponseBody
	public ResultHtml sendUserPhoneSMS(HttpServletRequest request) {
		ResultHtml resultMap = commonSerivce.sendUserPhoneSMS(request);
		return resultMap;
	}

	/***
	 * 商户发送短信
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("sendMchntPhoneSMS")
	@ResponseBody
	public ResultHtml sendMchntPhoneSMS(HttpServletRequest request) {
		ResultHtml resultMap = commonSerivce.sendMchntPhoneSMS(request);
		return resultMap;
	}

	/**
	 * 支付成功
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/paySuccess")
	public ModelAndView paySuccess(HttpServletRequest request) {
		ModelAndView mv;
		String transAmt = StringUtil.nullToString(request.getParameter("transAmt"));
		String transId = StringUtil.nullToString(request.getParameter("transId"));
		String mchntCode = StringUtil.nullToString(request.getParameter("mchntCode"));
		String amount = "";
		if (!StringUtil.isNullOrEmpty(transAmt)) {
			amount = StringUtil.isNullOrEmpty(transAmt) ? "0" : NumberUtils.RMBCentToYuan(transAmt);// 分转成元
		}

		String wxPrimaryKey = StringUtil.trim(request.getParameter("wxTransLogKey"));// 业务流水(微信端)
		if ("W20".equals(transId)) {
			mv = new ModelAndView("customer/card/buyCardpaySuccess");
		} else {
			mv = new ModelAndView("customer/wallet/paySuccess");
		}
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
	 * 用户被扫时，支付成功页面
	 * 
	 * @param request
	 * @return
	 */
//	@RequestMapping(value = "/qrCodePaySuccess")
//	public ModelAndView qrCodePaySuccess(HttpServletRequest request) {
//		ModelAndView mv = new ModelAndView("customer/wallet/paySuccess");
//		String transAmt = StringUtil.nullToString(request.getParameter("transAmt"));
//		String transId = StringUtil.nullToString(request.getParameter("transId"));
//		String mchntCode = StringUtil.nullToString(request.getParameter("mchntCode"));
//		
//		String wxPrimaryKey = StringUtil.trim(request.getParameter("wxTransLogKey"));// 业务流水(微信端)
//		
//		if (!StringUtil.isNullOrEmpty(transId)) {
//			if ("W10".equals(transId) || "W71".equals(transId)) {
//				WxTransLog log = wxTransLogService.getWxTransLogById(wxPrimaryKey);// 查询业务流水
//				if (log != null) {
//					ShopInf shopInf = shopInfService.getShopInfByCode(log.getShopCode());
//					MerchantInf merchntInf = merchantInfService.getMerchantInfByCode(log.getMchntCode());
//					mv.addObject("shopName", shopInf.getShopName());
//					mv.addObject("mchntName", merchntInf.getMchntName());
//					mv.addObject("transTime", com.cn.thinkx.wechat.base.wxapi.util.DateUtil.COMMON_FULL.getFormat()
//							.format(log.getCreateTime()));
//				}
//			}
//		}
//		mv.addObject("transAmt", transAmt);
//		mv.addObject("transId", transId);
//		mv.addObject("mchntCode", mchntCode);
//		return mv;
//	}
	/**
	 * 用户被扫时，支付失败
	 * 
	 * @param request
	 * @return
	 */
//	@RequestMapping(value = "/qrCodePayFailed")
//	public ModelAndView qrCodePayFailed(HttpServletRequest request) {
//		ModelAndView mv = new ModelAndView("customer/wallet/payfail");
//
//		String transCode = StringUtil.nullToString(request.getParameter("transCode"));
//		String transAmt = StringUtil.nullToString(request.getParameter("transAmt"));
//		String transId = StringUtil.nullToString(request.getParameter("transId"));
//		String mchntCode = StringUtil.nullToString(request.getParameter("mchntCode"));
//		String errorInfo = "";
//		
//		if (StringUtil.isNullOrEmpty(transCode)) {
//			transCode = BaseConstants.TXN_TRANS_ERROR;
//		}
//		if (BaseConstants.TXN_TRANS_ERROR.equals(transCode)
//				|| BaseConstants.RESPONSE_EXCEPTION_CODE.equals(transCode)) {
//			errorInfo = BaseConstants.RESPONSE_EXCEPTION_INFO;
//		} else {
//			ITFRespCode respCodeEnum = ITFRespCode.findByCode(transCode);
//			if (respCodeEnum != null) {
//				errorInfo = respCodeEnum.getValue();
//			} else {
//				errorInfo = BaseConstants.RESPONSE_EXCEPTION_INFO;
//			}
//		}
//
//		mv.addObject("transCode", transCode);
//		mv.addObject("transAmt", transAmt);
//		mv.addObject("transId", transId);
//		mv.addObject("errorInfo", errorInfo);
//		mv.addObject("mchntCode", mchntCode);
//		return mv;
//	}
	
	
	/**
	 * 支付失败
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/payFailed")
	public ModelAndView payFailed(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("customer/wallet/payfail");

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
	
}
