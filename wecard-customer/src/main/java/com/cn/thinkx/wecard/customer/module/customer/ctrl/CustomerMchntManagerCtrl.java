package com.cn.thinkx.wecard.customer.module.customer.ctrl;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cn.thinkx.common.wecard.domain.merchant.MerchantInf;
import com.cn.thinkx.common.wecard.domain.page.Pagination;
import com.cn.thinkx.common.wecard.domain.trans.TransRuleDeclare;
import com.cn.thinkx.common.wecard.domain.user.UserInf;
import com.cn.thinkx.common.wecard.domain.user.UserMerchantAcct;
import com.cn.thinkx.pms.base.utils.BaseConstants;
import com.cn.thinkx.pms.base.utils.StringUtil;
import com.cn.thinkx.wecard.customer.core.util.MessageUtil;
import com.cn.thinkx.wecard.customer.module.base.ctrl.BaseController;
import com.cn.thinkx.wecard.customer.module.customer.service.UserInfService;
import com.cn.thinkx.wecard.customer.module.customer.service.UserMerchantAcctService;
import com.cn.thinkx.wecard.customer.module.merchant.service.MerchantInfService;
import com.cn.thinkx.wecard.customer.module.merchant.service.TransRuleService;
import com.cn.thinkx.wecard.customer.module.pub.domain.TxnResp;
import com.cn.thinkx.wechat.base.wxapi.process.WxMemoryCacheClient;

/**
 * 微信会员页面
 */
@Controller
@RequestMapping("/customer/mchnt")
public class CustomerMchntManagerCtrl extends BaseController {
	Logger logger = LoggerFactory.getLogger(CustomerMchntManagerCtrl.class);

	@Autowired
	@Qualifier("userMerchantAcctService")
	private UserMerchantAcctService userMerchantAcctService;

	@Autowired
	@Qualifier("transRuleService")
	private TransRuleService transRuleService;

	@Autowired
	@Qualifier("merchantInfService")
	private MerchantInfService merchantInfService;

	@Autowired
	@Qualifier("userInfService")
	private UserInfService userInfService;

	/**
	 * 商户优惠页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/mchntDiscountList")
	public ModelAndView mchntDiscountList(HttpServletRequest request, TransRuleDeclare td, Pagination<TransRuleDeclare> pagination) {
		ModelAndView mv = new ModelAndView("customer/discount/mchntDiscountList");
		try {
			pagination = transRuleService.getTransRuleByMchnt(td, pagination);
			mv.addObject("td", td);
			mv.addObject("pagination", pagination);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return mv;
	}

	/**
	 * 查看某商户优惠页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/viewMchntRule")
	public ModelAndView mchntDiscountList(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("customer/discount/viewMchntRule");
		String ruleId = request.getParameter("ruleId");
		TransRuleDeclare transRule = transRuleService.getTransRuleDeclareByRuleId(ruleId);
		int isProduct = 0;
		if (transRule != null) {
			MerchantInf merchantInf = merchantInfService.getMerchantInfById(transRule.getMchntId());
			String openid = WxMemoryCacheClient.getOpenid(request);
			UserMerchantAcct userMerchantAcct = new UserMerchantAcct();
			userMerchantAcct.setExternalId(openid);
			userMerchantAcct.setMchntCode(merchantInf.getMchntCode());
			List<UserMerchantAcct> productlist = userMerchantAcctService.getUserMerchantAcctByUser(userMerchantAcct);
			if (productlist != null && productlist.size() > 0) {
				isProduct = productlist.size(); // 是否在该商户下有开卡
			}
		}
		mv.addObject("isProduct", isProduct);
		mv.addObject("rule", transRule);
		return mv;
	}

	/**
	 * 客户在某一商户下开卡
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/openAccountByMchnt")
	public ModelAndView openAccountByMchnt(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("customer/discount/customerOpenAccount");
		String mchntCode = request.getParameter("mchntCode");
		String shopCode = request.getParameter("shopCode");
		String openid = WxMemoryCacheClient.getOpenid(request);
		if (StringUtil.isNullOrEmpty(mchntCode) || StringUtil.isNullOrEmpty(openid)) {
			return unValidated(request);
		}

		MerchantInf merchantInf = merchantInfService.getMerchantInfByCode(mchntCode);

		/*** 用户是否已经注册汇卡包会员 **/
		UserInf user = userInfService.getUserInfByOpenId(openid);
		if (user == null) {
			mv = new ModelAndView("redirect:/customer/user/userRegister.html");
			return mv;
		}

		/*** 判断用户是否已经是某商户会员 **/
		UserMerchantAcct userMerchantAcct = new UserMerchantAcct();
		userMerchantAcct.setExternalId(openid);
		userMerchantAcct.setMchntCode(mchntCode);
		List<UserMerchantAcct> productlist = userMerchantAcctService.getUserMerchantAcctByUser(userMerchantAcct);
		if (merchantInf == null) {
			return unValidated(request);
		}

		if (productlist == null || productlist.size() <= 0) {
			try {
				// 请求开卡
				TxnResp cardCheckResp = userMerchantAcctService.doCustomerAccountOpening(null, null, openid, mchntCode,
						merchantInf.getInsCode());
				if (cardCheckResp != null && !BaseConstants.TXN_TRANS_RESP_SUCCESS.equals(cardCheckResp.getCode())) {
					userMerchantAcctService.doCustomerAccountOpening(null, null, openid, mchntCode,
							merchantInf.getInsCode());
				}
			} catch (Exception ex) {
				logger.error("扫码快捷支付，用户开卡失败-->" + ex);
			}
		}
		/*** 查询开卡数据 **/
		productlist = userMerchantAcctService.getUserMerchantAcctByUser(userMerchantAcct);
		if (productlist != null && productlist.size() > 0) {
			mv = new ModelAndView("redirect:/customer/user/scanCode.html?mchntCode="
					+ StringUtil.nullToString(mchntCode) + "&shopCode=" + StringUtil.nullToString(shopCode));
			return mv;
		}
		mv.addObject("merchantInf", merchantInf);
		return mv;
	}

	/**
	 * 客户会员开户成为某个商户下的会员
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/openAccountByMchntCommit")
	public @ResponseBody TxnResp cardRechargeCommit(HttpServletRequest request) {
		TxnResp resp = new TxnResp();
		String mchntId = request.getParameter("mchntId");
		String productCode = request.getParameter("productCode");
		String openid = WxMemoryCacheClient.getOpenid(request);
		MerchantInf merchantInf = merchantInfService.getMchntAndInsInfBymchntId(mchntId);
		try {
			resp = userMerchantAcctService.doCustomerAccountOpening(productCode, null, openid,
					merchantInf.getMchntCode(), merchantInf.getInsCode());
		} catch (Exception ex) {
			resp.setCode(BaseConstants.RESPONSE_EXCEPTION_CODE);
			resp.setInfo(MessageUtil.ERROR_MSSAGE);// 系统异常提示
			return resp;
		}
		return resp;
	}
}
