package com.cn.thinkx.oms.module.customer.controller;

import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cn.thinkx.oms.base.controller.BaseController;
import com.cn.thinkx.oms.module.customer.model.CardInf;
import com.cn.thinkx.oms.module.customer.model.PersonInf;
import com.cn.thinkx.oms.module.customer.model.UserMerchantAcct;
import com.cn.thinkx.oms.module.customer.service.PersonInfService;
import com.cn.thinkx.oms.module.customer.service.UserMerchantAcctService;
import com.cn.thinkx.oms.util.StringUtils;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping(value = "customer/personInf")
public class PersonInfController extends BaseController {

	@Autowired
	@Qualifier("personInfService")
	private PersonInfService personInfService;

	@Autowired
	@Qualifier("userMerchantAcctService")
	private UserMerchantAcctService userMerchantAcctService;

	/**
	 * 查询所有会员卡用户信息
	 * 
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/getPersonInfList")
	public ModelAndView getPersonInfList(HttpServletRequest req, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("customer/personInf/listPersonInf");
		PersonInf personInf = new PersonInf();
		int startNum = parseInt(req.getParameter("pageNum"), 1);
		int pageSize = parseInt(req.getParameter("pageSize"), 10);
		PageInfo<PersonInf> pageInfo = null;
		personInf.setMobilePhoneNo(StringUtils.nullToString(req.getParameter("mobilePhoneNo")));
		personInf.setPersonalName(StringUtils.nullToString(req.getParameter("personalName")));
		pageInfo = personInfService.getPersonInfListPage(startNum, pageSize, personInf);
		mv.addObject("pageInfo", pageInfo);
		mv.addObject("personInf", personInf);
		return mv;
	}

	/**
	 * 注销会员卡用户（注销手机号码）
	 * 
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/deletePersonInfByUserId")
	@ResponseBody
	public ModelMap deletePersonInfByUserId(HttpServletRequest req, HttpServletResponse response) {
		ModelMap resultMap = new ModelMap();
		resultMap.addAttribute("status", Boolean.TRUE);
		try {
			personInfService.deleteChannelUserInfByUserId(req);
		} catch (Exception e) {
			resultMap.addAttribute("status", Boolean.FALSE);
			resultMap.addAttribute("msg", "删除失败");
			logger.error("## 注销会员卡用户失败", e);
		}
		return resultMap;
	}

//	/**
//	 * 进入会员卡注销卡产品页面
//	 * 
//	 * @param req
//	 * @param response
//	 * @return
//	 */
//	@RequestMapping(value = "/intoCancelCandByUserId")
//	public ModelAndView intoCancelCandByUserId(HttpServletRequest req, HttpServletResponse response) {
//		ModelAndView mv = new ModelAndView("customer/personInf/cancelCard");
//		String mobilePhoneNo = StringUtils.nullToString(req.getParameter("mobilePhoneNo"));
//		PersonInf perconInf = null;
//		List<UserMerchantAcct> userMerchantAccList = null;
//		try {
//			perconInf = personInfService.getPersonInfByPhoneNo(mobilePhoneNo);
//			if (perconInf != null) {
//				UserMerchantAcct userMerchantAcct = new UserMerchantAcct();
//				userMerchantAcct.setUserId(perconInf.getUserId());
//				userMerchantAccList = userMerchantAcctService.getUserMerchantAcctByUser(userMerchantAcct);
//				Iterator<UserMerchantAcct> it = userMerchantAccList.iterator();
//				while (it.hasNext()) {
//					UserMerchantAcct userMerchantAcct2 = it.next();
//					CardInf card = personInfService.getCardInfByCardNo(userMerchantAcct2.getCardNo());
//					if ("0".equals(card.getCancelStat()) && "20".equals(card.getCardStat())) {
//						it.remove();
//					}
//				}
//			}
//		} catch (Exception e) {
//			logger.error("注销手机号失败：手机号码--->[{}]", mobilePhoneNo, e);
//		}
//		mv.addObject("perconInf", perconInf);
//		mv.addObject("userMerchantAccList", userMerchantAccList);
//		return mv;
//	}
//
//	/**
//	 * 会员卡注销卡产品提交
//	 * 
//	 * @param req
//	 * @param response
//	 * @return
//	 */
//	@RequestMapping(value = "/commitCancelCandByCardNo")
//	@ResponseBody
//	public ModelMap commitCancelCandByCardNo(HttpServletRequest req, HttpServletResponse response) {
//		ModelMap resultMap = new ModelMap();
//		resultMap.addAttribute("status", Boolean.TRUE);
//		String cardNo = StringUtils.nullToString(req.getParameter("cardNo"));
//		try {
//			personInfService.updateCardInf(cardNo);
//		} catch (Exception e) {
//			resultMap.addAttribute("status", Boolean.FALSE);
//			resultMap.addAttribute("msg", "删除失败");
//			logger.error("## 会员卡注销卡产品失败，卡号：[{}]", cardNo, e);
//		}
//		return resultMap;
//	}
}
