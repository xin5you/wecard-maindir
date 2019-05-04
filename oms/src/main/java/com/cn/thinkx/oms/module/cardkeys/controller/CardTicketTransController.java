package com.cn.thinkx.oms.module.cardkeys.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cn.thinkx.oms.base.controller.BaseController;
import com.cn.thinkx.oms.module.cardkeys.model.CardKeysOrderInf;
import com.cn.thinkx.oms.module.cardkeys.model.CardKeysTransLog;
import com.cn.thinkx.oms.module.cardkeys.service.CardKeysOrderInfService;
import com.cn.thinkx.oms.module.cardkeys.service.CardKeysTransLogService;
import com.cn.thinkx.oms.util.DateUtils;
import com.cn.thinkx.oms.util.StringUtils;
import com.cn.thinkx.pms.base.utils.BaseConstants;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.StringUtil;

@Controller
@RequestMapping(value = "cardTicketTrans")
public class CardTicketTransController extends BaseController {

	Logger logger = LoggerFactory.getLogger(CardTicketTransController.class);

	@Autowired
	@Qualifier("cardKeysOrderInfService")
	private CardKeysOrderInfService cardKeysOrderInfService;
	
	@Autowired
	@Qualifier("cardKeysTransLogService")
	private CardKeysTransLogService cardKeysTransLogService;
	
	/**
	 * 查询卡券交易订单列表
	 * 
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/listCardTicketTransOrder")
	public ModelAndView listCardKeysTransOrder(HttpServletRequest req, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("enterpriseOrder/cardTicketTrans/listCardTicketTransOrder");
		String orderId = StringUtils.nullToString(req.getParameter("orderId"));
		String orderStat = StringUtils.nullToString(req.getParameter("orderStat"));
		String orderType = StringUtils.nullToString(req.getParameter("orderType"));
		String personalName = StringUtils.nullToString(req.getParameter("personalName"));
		String queryType = StringUtils.nullToString(req.getParameter("queryType"));
		String startTime = StringUtils.nullToString(req.getParameter("startTime"));
		String endTime = StringUtils.nullToString(req.getParameter("endTime"));
		String operStatus = StringUtils.nullToString(req.getParameter("operStatus"));
		PageInfo<CardKeysOrderInf> pageList = null;

		int startNum = parseInt(req.getParameter("pageNum"), 1);
		int pageSize = parseInt(req.getParameter("pageSize"), 10);
		
		CardKeysOrderInf cko = new CardKeysOrderInf();
		cko.setOrderId(orderId);
		cko.setType(orderType);
		cko.setStat(orderStat);
		cko.setPersonalName(personalName);
		cko.setQueryType(queryType);
		
		if (!StringUtils.isNotNull(queryType)) {
			cko.setStartTime(startTime);
			cko.setEndTime(endTime);
		} else if (queryType == "cur" || queryType.equals("cur")) {
			cko.setStartTime(DateUtils.getMinToday());
			cko.setEndTime(DateUtils.getMaxTodayAfterDay(0));
		} else if (queryType == "his" || queryType.equals("his")) {
			cko.setStartTime(startTime);
			cko.setEndTime(endTime);
		}
		
		try {
			pageList = cardKeysOrderInfService.getCardKeysOrderInfPage(startNum, pageSize, cko);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("## 查询卡券交易订单列表信息出错,订单号[{}]", orderId, e);
		}
		mv.addObject("pageInfo", pageList);
		mv.addObject("cardKeysOrderInf", cko);
		mv.addObject("operStatus", operStatus);
		mv.addObject("orderTypeList", BaseConstants.orderType.values());
		mv.addObject("orderStatList", BaseConstants.orderStat.values());
		return mv;
	}
	
	/**
	 * 查询卡券交易信息详情
	 * 
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/intoViewCardTicketTransLog")
	public ModelAndView intoViewCardTicketTransLog(HttpServletRequest req, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("enterpriseOrder/cardTicketTrans/listCardTicketTransLog");
		String orderId = StringUtils.nullToString(req.getParameter("orderId"));
		String operStatus = StringUtils.nullToString(req.getParameter("operStatus"));
		PageInfo<CardKeysTransLog> pageList = null;

		int startNum = parseInt(req.getParameter("pageNum"), 1);
		int pageSize = parseInt(req.getParameter("pageSize"), 10);
		
		CardKeysTransLog ckt = new CardKeysTransLog();
		ckt.setOrderId(orderId);
		
		try {
			pageList = cardKeysTransLogService.getCardKeysTransLogPage(startNum, pageSize, ckt);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("## 查询卡券交易流水列表信息出错,订单号[{}]", orderId, e);
		}
		mv.addObject("pageInfo", pageList);
		mv.addObject("orderId", orderId);
		mv.addObject("operStatus", operStatus);
		return mv;
	}
	
	/**
	 * 充值卡券交易订单
	 * 
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/resetCardTicketTransOrderCommit")
	@ResponseBody
	public boolean resetCardTicketTransOrderCommit(HttpServletRequest req, HttpServletResponse response) {
		String orderId = StringUtils.nullToString(req.getParameter("orderId"));
		if (!StringUtil.isNotEmpty(orderId)) {
			logger.error("## 重置卡券交易信息失败，请求参数orderId为空");
			return false;
		}
		boolean isReset = cardKeysOrderInfService.searchCardTicketTransOrderFail(orderId);
		return isReset;
	}

}
