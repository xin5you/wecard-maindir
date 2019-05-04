package com.cn.iboot.diy.api.channel.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cn.iboot.diy.api.base.constants.BaseConstants.providerOrderRechargeState;
import com.cn.iboot.diy.api.base.constants.Constants;
import com.cn.iboot.diy.api.base.utils.DateUtil;
import com.cn.iboot.diy.api.base.utils.ExcelUtils;
import com.cn.iboot.diy.api.base.utils.NumberUtils;
import com.cn.iboot.diy.api.base.utils.UploadUtil;
import com.cn.iboot.diy.api.system.domain.User;
import com.cn.thinkx.wecard.facade.telrecharge.model.TelChannelOrderInf;
import com.cn.thinkx.wecard.facade.telrecharge.model.TelChannelOrderInfUpload;
import com.cn.thinkx.wecard.facade.telrecharge.service.TelChannelOrderInfFacade;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping("/channel/order")
public class ChannelOrderController {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private TelChannelOrderInfFacade telChannelOrderInfFacade;

	@GetMapping("/listOrder")
	public ModelAndView listOrder(HttpServletRequest req,TelChannelOrderInf order){
		ModelAndView mv = new ModelAndView("channel/listChannelOrder");
		User user = (User) req.getSession().getAttribute(Constants.SESSION_USER);
		int startNum = NumberUtils.parseInt(req.getParameter("pageNum"), 1);
		int pageSize = NumberUtils.parseInt(req.getParameter("pageSize"), 10);
		PageInfo<TelChannelOrderInf> pageList = null;
		try{
			order.setMchntCode(user.getMchntCode());
			order.setStartDate(DateUtil.getCurrentDateStr2() + " 00:00:00");
			order.setEndDate(DateUtil.getCurrentDateStr2() + " 23:59:59");
			pageList = telChannelOrderInfFacade.getTelChannelOrderInf(startNum, pageSize, order);
			
			TelChannelOrderInf channelOrder = telChannelOrderInfFacade.getTelChannelOrderInfCount(order);
			mv.addObject("pageInfo", pageList);
			mv.addObject("orderStatTypeList", providerOrderRechargeState.values());
			mv.addObject("channelOrder", channelOrder);
		} catch(Exception e){
			logger.error(" ## 查看分销商订单列表出错 ",e);
		}
		mv.addObject("order", order);
		return mv;
	}
	
	@PostMapping(value = "/listOrder")
	public ModelAndView listOrder(HttpServletRequest req,HttpServletResponse response,TelChannelOrderInf order) {
		ModelAndView mv = new ModelAndView("channel/listChannelOrder");
		User user = (User) req.getSession().getAttribute(Constants.SESSION_USER);
		int startNum = NumberUtils.parseInt(req.getParameter("pageNum"), 1);
		int pageSize = NumberUtils.parseInt(req.getParameter("pageSize"), 10);
		PageInfo<TelChannelOrderInf> pageList = null;
		try{
			order.setMchntCode(user.getMchntCode());
			pageList = telChannelOrderInfFacade.getTelChannelOrderInf(startNum, pageSize, order);
			TelChannelOrderInf channelOrder = telChannelOrderInfFacade.getTelChannelOrderInfCount(order);
			mv.addObject("pageInfo", pageList);
			mv.addObject("orderStatTypeList", providerOrderRechargeState.values());
			mv.addObject("channelOrder", channelOrder);
		} catch(Exception e){
			logger.error(" ## 查看分销商订单列表出错 ",e);
		}
		mv.addObject("order", order);
		return mv;
	}
	
	@GetMapping(value="/listOrderUpload")
	public void listOrderUpload(HttpServletRequest req,HttpServletResponse response){
		String channelOrderId = req.getParameter("channelOrderId");
		String rechargePhone = req.getParameter("rechargePhone");
		String rechargeState = req.getParameter("rechargeState");
		String startDate = req.getParameter("startDate");
		String endDate = req.getParameter("endDate");
		
		User user = (User) req.getSession().getAttribute(Constants.SESSION_USER);
		TelChannelOrderInf order = new TelChannelOrderInf();
		order.setChannelOrderId(channelOrderId);
		order.setRechargePhone(rechargePhone);
		order.setRechargeState(rechargeState);
		order.setStartDate(startDate);
		order.setEndDate(endDate);
		order.setMchntCode(user.getMchntCode());
		List<TelChannelOrderInfUpload> list = telChannelOrderInfFacade.getTelChannelOrderInfListToUpload(order);
		
		try{
			String title ="充值订单报表";
			String[] titlehead = new String[]{"订单编号","商品名称","充值手机","充值数量","充值金额","支付金额","订单状态","订单时间"};
			ExcelUtils<TelChannelOrderInfUpload> ex = new ExcelUtils<TelChannelOrderInfUpload>();
			HSSFWorkbook workBook = ex.exportExcel(title, null, null, null, titlehead, null, null, list,TelChannelOrderInfUpload.class,null);
			UploadUtil.upLoad(workBook, title, response);
		} catch(Exception e){
			logger.error(" ## 导出分销商订单报表出错 ",e);
		}
	}
	
//	public static void main(String[] args) {
//		String a = DateUtil.getCurrentDateStr2() + " 00:00:00";
//		System.out.println(a + " 00:00:00");
//		
//	}
}
