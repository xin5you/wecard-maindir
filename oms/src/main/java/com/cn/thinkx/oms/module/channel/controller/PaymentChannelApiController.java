package com.cn.thinkx.oms.module.channel.controller;

import java.util.HashMap;
import java.util.Map;

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
import com.cn.thinkx.oms.module.channel.model.PaymentChannelApi;
import com.cn.thinkx.oms.module.channel.service.PaymentChannelApiService;
import com.cn.thinkx.oms.module.channel.service.PaymentChannelService;
import com.cn.thinkx.oms.util.StringUtils;
import com.cn.thinkx.pms.base.utils.BaseIntegrationPayConstants.TransCode;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("channel/paymentChannelApi")
public class PaymentChannelApiController extends BaseController{

	@Autowired
	@Qualifier("paymentChannelApiService")
	private PaymentChannelApiService pciService;
	
	@Autowired
	@Qualifier("paymentChannelService")
	private PaymentChannelService paymentChannelService;
	
	@RequestMapping(value = "/listPaymentChannelApi")
	public ModelAndView listPaymentChannelApi(HttpServletRequest req, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("channel/paymentChannelApi/listPaymentChannelApiInf");
		String channelId = StringUtils.nullToString(req.getParameter("channelId"));
		String apiType = StringUtils.nullToString(req.getParameter("apiType"));
		PageInfo<PaymentChannelApi> pageList = null;
		int startNum = parseInt(req.getParameter("pageNum"), 1);
		int pageSize = parseInt(req.getParameter("pageSize"), 10);
		String name = StringUtils.nullToString(req.getParameter("name"));
		PaymentChannelApi pci = new PaymentChannelApi();
		pci.setName(name);
		pci.setChannelId(channelId);
		pci.setApiType(apiType);
		pageList = pciService.getPaymentChannelsApiListPage(startNum, pageSize, pci);
		Map<String, String> apiTypeMap = new HashMap<String, String>();
		for (TransCode st : TransCode.values()) {
			apiTypeMap.put(st.getCode(), st.getValue());
		}
		mv.addObject("apiTypeMap", apiTypeMap);
		mv.addObject("apiType", apiType);
		mv.addObject("pageInfo", pageList);
		mv.addObject("pci", pci);
		return mv;
	}
	
	@RequestMapping(value="/intoAddPaymentChannelApiInf")
	public ModelAndView intoAddPaymentChannelApiInf(HttpServletRequest req){
		ModelAndView mv = new ModelAndView("channel/paymentChannelApi/addPaymentChannelApiInf");
		String channelId = StringUtils.nullToString(req.getParameter("channelId"));
		Map<String, String> mapType = new HashMap<String, String>();
		for (TransCode st : TransCode.values()) {
			mapType.put(st.getCode(), st.getValue());
		}
		mv.addObject("mapType", mapType);
		mv.addObject("channelId", channelId);
		return mv;
	}
	
	@RequestMapping(value="/addPaymentChannelApiInfCommit")
	@ResponseBody
	public ModelMap addPaymentChannelApiInfCommit(HttpServletRequest req){
		ModelMap resultMap = new ModelMap();
		resultMap.addAttribute("status", Boolean.TRUE);
		try {
			pciService.insertPaymentChannelApi(req);
		} catch (Exception e) {
			resultMap.addAttribute("status", Boolean.FALSE);
			resultMap.addAttribute("msg", "新增失败，请重新添加");
			logger.error("## 新增失败，请重新添加", e);
		}
		return resultMap;
	}
	
	@RequestMapping(value="/editPaymentChannelApiInf")
	public ModelAndView editPaymentChannelApiInf(HttpServletRequest req){
		ModelAndView mv = new ModelAndView("channel/paymentChannelApi/editPaymentChannelApiInf");
		String id = StringUtils.nullToString(req.getParameter("id"));
		String channelId = StringUtils.nullToString(req.getParameter("channelId"));
		PaymentChannelApi pci = pciService.getPaymentChannelsApiById(id);
		Map<String, String> mapType = new HashMap<String, String>();
		for (TransCode st : TransCode.values()) {
			mapType.put(st.getCode(), st.getValue());
		}
		mv.addObject("mapType", mapType);
		mv.addObject("pci", pci);
		mv.addObject("channelId", channelId);
		return mv;
	}
	
	@RequestMapping(value="/editPaymentChannelApiInfCommit")
	@ResponseBody
	public ModelMap editPaymentChannelApiInfCommit(HttpServletRequest req){
		ModelMap resultMap = new ModelMap();
		resultMap.addAttribute("status", Boolean.TRUE);
		try {
			pciService.updatePaymentChannelApiById(req);
		} catch (Exception e) {
			resultMap.addAttribute("status", Boolean.FALSE);
			resultMap.addAttribute("msg", "编辑失败");
			logger.error("## 编辑失败", e);
		}
		return resultMap;
	}
	
	@RequestMapping(value="/intoViewPaymentChannelApiInf")
	public ModelAndView intoViewPaymentChannelApiInf(HttpServletRequest req){
		ModelAndView mv = new ModelAndView("channel/paymentChannelApi/viewPaymentChannelApiInf");
		String id = StringUtils.nullToString(req.getParameter("id"));
		PaymentChannelApi pci = pciService.getPaymentChannelsApiById(id);
		mv.addObject("pci", pci);
		return mv;
	}
	@RequestMapping(value="deletePaymentChannelApiInfById")
	@ResponseBody
	public ModelMap deletePaymentChannelApiInfById(HttpServletRequest req){
		ModelMap resultMap = new ModelMap();
		resultMap.addAttribute("status", Boolean.TRUE);
		try {
			String id = StringUtils.nullToString(req.getParameter("id"));
			pciService.deletePaymentChannelApiById(id);
		} catch (Exception e) {
			resultMap.addAttribute("status", Boolean.FALSE);
			resultMap.addAttribute("msg", "删除失败");
			logger.error("## 删除失败", e);
		}
		return resultMap;
	}
}
