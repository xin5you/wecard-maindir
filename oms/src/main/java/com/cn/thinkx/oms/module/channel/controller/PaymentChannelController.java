package com.cn.thinkx.oms.module.channel.controller;

import java.util.HashMap;
import java.util.List;
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
import com.cn.thinkx.oms.module.channel.model.PaymentChannel;
import com.cn.thinkx.oms.module.channel.service.PaymentChannelService;
import com.cn.thinkx.oms.module.merchant.model.ScanBoxDeviceInf;
import com.cn.thinkx.oms.module.merchant.service.ScanBoxDeviceInfService;
import com.cn.thinkx.oms.util.StringUtils;
import com.cn.thinkx.pms.base.utils.BaseIntegrationPayConstants.OMSChannelType;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("channel/paymentChannel")
public class PaymentChannelController extends BaseController {

	@Autowired
	@Qualifier("paymentChannelService")
	private PaymentChannelService paymentChannelService;
	
	@Autowired
	@Qualifier("scanBoxDeviceInfService")
	private ScanBoxDeviceInfService scanBoxDeviceInfService;

	@RequestMapping(value = "/listPaymentChannel")
	public ModelAndView listPaymentChannel(HttpServletRequest req, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("channel/paymentChannel/listPaymentChannelInf");
		PageInfo<PaymentChannel> pageList = null;
		int startNum = parseInt(req.getParameter("pageNum"), 1);
		int pageSize = parseInt(req.getParameter("pageSize"), 10);
		String channelName = StringUtils.nullToString(req.getParameter("channelName"));
		String enable = StringUtils.nullToString(req.getParameter("enable"));
		PaymentChannel pc = new PaymentChannel();
		pc.setChannelName(channelName);
		pc.setEnable(enable);
		pageList = paymentChannelService.getPaymentChannelsListPage(startNum, pageSize, pc);
		mv.addObject("pageInfo", pageList);
		mv.addObject("pc", pc);
		return mv;
	}

	@RequestMapping(value = "intoAddPaymentChannelInf")
	public ModelAndView intoAddPaymentChannelInf() {
		ModelAndView mv = new ModelAndView("channel/paymentChannel/addPaymentChannelInf");
		Map<String, String> mapType = new HashMap<String, String>();
		for (OMSChannelType st : OMSChannelType.values()) {
			mapType.put(st.getCode(), st.getValue());
		}
		mv.addObject("mapType", mapType);
		return mv;
	}

	@RequestMapping(value = "addPaymentChannelInfCommit")
	@ResponseBody
	public ModelMap addPaymentChannelInfCommit(HttpServletRequest req) {
		ModelMap resultMap = new ModelMap();
		resultMap.addAttribute("status", Boolean.TRUE);
		try {
			String channelNo = StringUtils.nullToString(req.getParameter("channelNo"));
			if (channelNo != null && channelNo != "") {
				int countChannelNo = paymentChannelService.getPaymentChannelsByChannelNo(channelNo);
				if (countChannelNo < 1) {
					paymentChannelService.insertPaymentChannel(req);	
				}else{
					resultMap.addAttribute("status", Boolean.FALSE);
					resultMap.addAttribute("msg", "通道号已存在，请重新输入");
				}
			}
		} catch (Exception e) {
			resultMap.addAttribute("status", Boolean.FALSE);
			resultMap.addAttribute("msg", "新增失败，请重新添加");
			logger.error("## 新增失败，请重新添加", e);
		}
		return resultMap;
	}

	@RequestMapping(value = "editPaymentChannelInf")
	public ModelAndView editPaymentChannelInf(HttpServletRequest req) {
		ModelAndView mv = new ModelAndView("channel/paymentChannel/editPaymentChannelInf");
		String id = StringUtils.nullToString(req.getParameter("id"));
		PaymentChannel paymentChannel = paymentChannelService.getPaymentChannelsById(id);
		Map<String, String> mapType = new HashMap<String, String>();
		for (OMSChannelType st : OMSChannelType.values()) {
			mapType.put(st.getCode(), st.getValue());
		}
		mv.addObject("mapType", mapType);
		mv.addObject("pc", paymentChannel);
		return mv;
	}

	@RequestMapping(value = "editPaymentChannelInfCommit")
	@ResponseBody
	public ModelMap editPaymentChannelInfCommit(HttpServletRequest req) {
		ModelMap resultMap = new ModelMap();
		resultMap.addAttribute("status", Boolean.TRUE);
		try {
			paymentChannelService.updatePaymentChannelById(req);
		} catch (Exception e) {
			resultMap.addAttribute("status", Boolean.FALSE);
			resultMap.addAttribute("msg", "修改失败，请重新修改");
			logger.error("## 修改失败，请重新修改", e);
		}
		return resultMap;
	}

	@RequestMapping(value = "deletePaymentChannelInfById")
	@ResponseBody
	public ModelMap deletePaymentChannelInfById(HttpServletRequest req) {
		ModelMap resultMap = new ModelMap();
		resultMap.addAttribute("status", Boolean.TRUE);
		String id = "";
		try {
			id = StringUtils.nullToString(req.getParameter("id"));
			paymentChannelService.deletePaymentChannelById(id);
		} catch (Exception e) {
			logger.error("## 支付通道[{}]删除失败", id);
			resultMap.addAttribute("status", Boolean.FALSE);
			resultMap.addAttribute("msg", "删除失败");
			logger.error("## 该支付通道删除失败", e);
		}
		return resultMap;
	}

	@RequestMapping(value = "checkPaymentChannelInf")
	@ResponseBody
	public ModelMap checkPaymentChannelInf(HttpServletRequest req) {
		ModelMap resultMap = new ModelMap();
		resultMap.addAttribute("status", Boolean.TRUE);
		String id = "";
		try {
			id = StringUtils.nullToString(req.getParameter("id"));
			PaymentChannel pc = paymentChannelService.getPaymentChannelsById(id);
			ScanBoxDeviceInf sbdf = new ScanBoxDeviceInf();
			List<ScanBoxDeviceInf> sbList = scanBoxDeviceInfService.getScanBoxDeviceInfList(sbdf);
			for (ScanBoxDeviceInf scanBoxDeviceInf : sbList) {
				if(scanBoxDeviceInf != null && scanBoxDeviceInf.getChannelNo().equals(pc.getChannelNo())){
					resultMap.addAttribute("status", Boolean.FALSE);
					resultMap.addAttribute("msg", "该通道还在使用，请核实后再做删除操作");
					return resultMap;
				}
			}
		} catch (Exception e) {
			logger.error("## 支付通道[{}]删除失败", id);
			resultMap.addAttribute("status", Boolean.FALSE);
			resultMap.addAttribute("msg", "删除失败");
			logger.error("## 该支付通道删除失败", e);
		}
		return resultMap;
	}
}
