package com.cn.thinkx.oms.module.phoneRecharge.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cn.thinkx.oms.base.controller.BaseController;
import com.cn.thinkx.oms.module.phoneRecharge.service.TelChannelInfService;
import com.cn.thinkx.oms.util.StringUtils;
import com.cn.thinkx.pms.base.utils.BaseConstants;
import com.cn.thinkx.wecard.facade.telrecharge.model.TelChannelOrderInf;
import com.cn.thinkx.wecard.facade.telrecharge.service.TelChannelOrderInfFacade;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping(value = "channel/channelOrder")
public class TelChannelOrderInfController extends BaseController {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private TelChannelOrderInfFacade telChannelOrderInfFacade;

	@Autowired
	private TelChannelInfService telChannelInfService;

	/**
	 * 分销商订单列表
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/listTelChannelOrderInf")
	public ModelAndView listTelChannelOrderInf(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("phoneRecharge/telChannelOrderInf/listTelChannelOrderInf");
		String operStatus = StringUtils.nullToString(request.getParameter("operStatus"));
		int startNum = parseInt(request.getParameter("pageNum"), 1);
		int pageSize = parseInt(request.getParameter("pageSize"), 10);
		TelChannelOrderInf orderInf = new TelChannelOrderInf();
		orderInf.setChannelName(StringUtils.nullToString(request.getParameter("channelName")));
		orderInf.setChannelOrderId(StringUtils.nullToString(request.getParameter("channelOrderId")));
		orderInf.setOuterTid(StringUtils.nullToString(request.getParameter("outerTid")));
		orderInf.setRechargeType(StringUtils.nullToString(request.getParameter("rechargeType")));
		orderInf.setOrderStat(StringUtils.nullToString(request.getParameter("orderStat")));
		orderInf.setNotifyStat(StringUtils.nullToString(request.getParameter("notifyStat")));
		try {
			PageInfo<TelChannelOrderInf> pageList = telChannelOrderInfFacade.getTelChannelOrderInfPage(startNum,
					pageSize, orderInf);
			mv.addObject("pageInfo", pageList);
		} catch (Exception e) {
			logger.error("## 分销商订单查询异常", e);
		}
		mv.addObject("telChannelOrderInf", orderInf);
		mv.addObject("rechargeTypeList", BaseConstants.ShopType.values());
		mv.addObject("channelOrderStatList", BaseConstants.ChannelOrderStat.values());
		mv.addObject("channelOrderNotifyStatList", BaseConstants.ChannelOrderNotifyStat.values());
		mv.addObject("operStatus", operStatus);
		return mv;
	}

	@RequestMapping(value = "/doCallBackNotifyChannel")
	@ResponseBody
	public ModelMap doCallBackNotifyChannel(HttpServletRequest req) {
		ModelMap resultMap = new ModelMap();
		String channelOrderId = req.getParameter("channelOrderId");
		try {
			resultMap = telChannelInfService.doCallBackNotifyChannel(channelOrderId);
		} catch (Exception e) {
			resultMap.addAttribute("status", Boolean.FALSE);
			resultMap.addAttribute("msg", "回调通知分销商异常,请联系管理员");
			logger.error("## 回调通知分销商异常", e);
		}
		return resultMap;
	}
}
