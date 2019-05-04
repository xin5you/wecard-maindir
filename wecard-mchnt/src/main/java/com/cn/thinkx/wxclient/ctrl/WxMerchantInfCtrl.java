package com.cn.thinkx.wxclient.ctrl;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cn.thinkx.common.redis.util.HttpWebUtil;
import com.cn.thinkx.core.ctrl.BaseController;
import com.cn.thinkx.merchant.domain.MerchantManager;
import com.cn.thinkx.merchant.service.MerchantManagerService;
import com.cn.thinkx.pms.base.utils.StringUtil;
import com.cn.thinkx.pub.domain.DetailBizInfo;
import com.cn.thinkx.pub.service.PublicService;
import com.cn.thinkx.wechat.base.wxapi.process.WxMemoryCacheClient;
import com.cn.thinkx.wxclient.service.CtrlSystemService;

/**
 * 手机微信页面
 */
@Controller
@RequestMapping("/wxclient/mchnt")
public class WxMerchantInfCtrl  extends BaseController{
	Logger logger = LoggerFactory.getLogger(WxMerchantInfCtrl.class);
	
	
	@Autowired
	private PublicService publicService;
	
	@Autowired
	@Qualifier("ctrlSystemService")
	private CtrlSystemService ctrlSystemService;

	@Autowired
	private MerchantManagerService merchantManagerService;

	/***
	 * 商戶管理 ctrl
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/mchtManager")
	public ModelAndView mchtManager(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("wxclient/merchant/merchantInfList");
		// 拦截器已经处理了缓存,这里直接取
		String openid = WxMemoryCacheClient.getOpenid(request);
		if(StringUtil.isNullOrEmpty(openid)){
			return unValidated(request);//商户代表注册，未获取的openId,跳转到页面
		}
		MerchantManager merchantManager =merchantManagerService.getMerchantManagerByOpenId(openid);
		
		String roleType ="";
		if(merchantManager !=null ){
			roleType=merchantManager.getRoleType();
		}
		mv.addObject("roleType",roleType);
		return mv;
	}
	
	/** 收银台 扫一扫   Created by Pucker 2016/7/16 **/
	@RequestMapping(value = "/scanCode")
	public ModelAndView scanCode(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("wxclient/cashier/mchntScanCode");
		
		String openid = WxMemoryCacheClient.getOpenid(request);// 从缓存中获取openid
		DetailBizInfo detail = new DetailBizInfo();
		detail.setMangerName(openid);
		detail = publicService.getDetailBizInfo(detail);
		if (detail == null)
			mv.addObject("detail", new DetailBizInfo());
		else
			mv.addObject("detail", detail);
		
		//websocket 连接域名
		mv.addObject("wsUrl", HttpWebUtil.getMerchantWsUrl());
		return mv;
	}
	
	/** 收银台 收款码   Created by Pucker 2016/7/19 **/
	@RequestMapping(value = "/qrCode")
	public ModelAndView qrCode(HttpServletRequest request){
		ModelAndView mv = new ModelAndView("wxclient/cashier/mchntQrCode");
		
		String openid = WxMemoryCacheClient.getOpenid(request);// 从缓存中获取openid
		DetailBizInfo detail = new DetailBizInfo();
		detail.setMangerName(openid);
		detail = publicService.getDetailBizInfo(detail);
		if (detail == null) {
			logger.info("商户收银台-->收款码-->查询商户信息失败");
			mv = new ModelAndView("common/failure");
			mv.addObject("failureMsg", "对不起，您不是商户管理员");
			return mv;
		}
		String payUrl =HttpWebUtil.getCustomerScanPayUrl(detail.getMchntCode(), detail.getShopCode());
//		String httpDomain = HttpWebUtil.getCustomerDomainUrl();  //获取客户端域名
//		if (!StringUtil.isNullOrEmpty(url) && url.indexOf(httpDomain) >= 0) {
//			payUrl = detail.getQrCodeUrl();
//		} else {
//			ShopInf entity = new ShopInf();
//			entity.setQrCodeUrl(payUrl);
//			entity.setShopId(detail.getShopId());
//			shopInfService.updateShopInfUrl(entity);
//		}
		mv.addObject("payUrl", payUrl);
		return mv;
	}
	
}