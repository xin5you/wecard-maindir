package com.cn.iboot.diy.api.invoice.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.cn.iboot.diy.api.base.constants.Constants;
import com.cn.iboot.diy.api.base.utils.NumberUtils;
import com.cn.iboot.diy.api.base.utils.StringUtil;
import com.cn.iboot.diy.api.invoice.domain.InvoiceOrder;
import com.cn.iboot.diy.api.invoice.service.InvoOrdStatisticsService;
import com.cn.iboot.diy.api.system.domain.User;
import com.github.pagehelper.PageInfo;

@RestController
@RequestMapping(value = "invoice/invoOrdStatistics")
public class InvoOrdStatisticsController {

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private InvoOrdStatisticsService invoOrdStatisticsService;
	
	/**
	 * 开票交易查询统计
	 * 
	 * @param req
	 * @param response
	 * @param invoiceOrder
	 * @return
	 */
	@PostMapping(value="/getInvoOrdStatistics")
	public ModelAndView getInvoOrdStatistics(HttpServletRequest req,HttpServletResponse response,InvoiceOrder invoiceOrder){
		ModelAndView mv = new ModelAndView("invoice/invoiceOrderStatistics");
		int startNum = NumberUtils.parseInt(req.getParameter("pageNum"), 1);
		int pageSize = NumberUtils.parseInt(req.getParameter("pageSize"), 10);
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute(Constants.SESSION_USER);
		invoiceOrder.setMchntCode(user.getMchntCode());
		invoiceOrder.setShopCode(user.getShopCode());
		PageInfo<InvoiceOrder> pageList = new PageInfo<InvoiceOrder>(null);
		if(!StringUtil.isNullOrEmpty(invoiceOrder.getStartDate()) && !StringUtil.isNullOrEmpty(invoiceOrder.getEndDate())){
			pageList =  invoOrdStatisticsService.getInvoOrdStatisticsPage(startNum, pageSize, invoiceOrder);
		}
		if (!StringUtil.isNullOrEmpty(invoiceOrder.getStartDate()))
			invoiceOrder.setStartDate(LocalDate.parse(invoiceOrder.getStartDate(), DateTimeFormatter.BASIC_ISO_DATE).toString());
		if (!StringUtil.isNullOrEmpty(invoiceOrder.getEndDate())) 
			invoiceOrder.setEndDate(LocalDate.parse(invoiceOrder.getEndDate(), DateTimeFormatter.BASIC_ISO_DATE).toString());
		mv.addObject("pageInfo", pageList);
		mv.addObject("invoiceOrder", invoiceOrder);
		return mv;
	}
	
	@GetMapping(value="/getInvoOrdStatistics")
	public ModelAndView getInvoOrdStatistics(HttpServletRequest req,InvoiceOrder invoiceOrder){
		ModelAndView mv = new ModelAndView("invoice/invoiceOrderStatistics");
		int startNum = NumberUtils.parseInt(req.getParameter("pageNum"), 1);
		int pageSize = NumberUtils.parseInt(req.getParameter("pageSize"), 10);
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute(Constants.SESSION_USER);
		invoiceOrder.setMchntCode(user.getMchntCode());
		PageInfo<InvoiceOrder> pageList = new PageInfo<InvoiceOrder>(null);
		if(!StringUtil.isNullOrEmpty(invoiceOrder.getStartDate()) && !StringUtil.isNullOrEmpty(invoiceOrder.getEndDate())){
			pageList =  invoOrdStatisticsService.getInvoOrdStatisticsPage(startNum, pageSize, invoiceOrder);
		}
		if (invoiceOrder.getStartDate() != null && !invoiceOrder.getStartDate().equals(""))
			invoiceOrder.setStartDate(LocalDate.parse(invoiceOrder.getStartDate(), DateTimeFormatter.BASIC_ISO_DATE).toString());
		if (invoiceOrder.getEndDate() != null && !invoiceOrder.getEndDate().equals("")) 
			invoiceOrder.setEndDate(LocalDate.parse(invoiceOrder.getEndDate(), DateTimeFormatter.BASIC_ISO_DATE).toString());
		mv.addObject("pageInfo", pageList);
		mv.addObject("invoiceOrder", invoiceOrder);
		return mv;
	}
}
