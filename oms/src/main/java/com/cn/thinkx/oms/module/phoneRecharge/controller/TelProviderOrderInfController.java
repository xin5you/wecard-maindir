package com.cn.thinkx.oms.module.phoneRecharge.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cn.thinkx.oms.base.controller.BaseController;
import com.cn.thinkx.oms.util.StringUtils;
import com.cn.thinkx.pms.base.utils.BaseConstants;
import com.cn.thinkx.wecard.facade.telrecharge.model.TelProviderOrderInf;
import com.cn.thinkx.wecard.facade.telrecharge.service.TelProviderOrderInfFacade;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping(value = "provider/providerOrder")
public class TelProviderOrderInfController extends BaseController {

	@Autowired
	private TelProviderOrderInfFacade telProviderOrderInfFacade;

	/**
	 * 供应商订单列表
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/listTelProviderOrderInf")
	public ModelAndView listTelProviderOrderInf(HttpServletRequest req) {
		ModelAndView mv = new ModelAndView("phoneRecharge/telProviderOrderInf/listTelProviderOrderInf");
		String operStatus = StringUtils.nullToString(req.getParameter("operStatus"));
		int startNum = parseInt(req.getParameter("pageNum"), 1);
		int pageSize = parseInt(req.getParameter("pageSize"), 10);
		try {
			TelProviderOrderInf telProviderOrderInf = this.getTelProviderOrderInf(req);
			PageInfo<TelProviderOrderInf> pageList = telProviderOrderInfFacade.getTelProviderOrderInfPage(startNum,
					pageSize, telProviderOrderInf);
			mv.addObject("pageInfo", pageList);
			mv.addObject("telProviderOrderInf", telProviderOrderInf);
		} catch (Exception e) {
			logger.error("## 供应商订单列表查询异常", e);
		}
		mv.addObject("rechargeStateList", BaseConstants.providerOrderRechargeState.values());
		mv.addObject("operStatus", operStatus);
		return mv;
	}

	/**
	 * 封装供应商订单
	 * 
	 * @param req
	 * @return
	 */
	public TelProviderOrderInf getTelProviderOrderInf(HttpServletRequest req) {
		TelProviderOrderInf telProviderOrderInf = new TelProviderOrderInf();
		telProviderOrderInf.setRegOrderId(StringUtils.nullToString(req.getParameter("regOrderId")));
		telProviderOrderInf.setChannelOrderId(StringUtils.nullToString(req.getParameter("channelOrderId")));
		telProviderOrderInf.setBillId(StringUtils.nullToString(req.getParameter("billId")));
		return telProviderOrderInf;
	}
}
