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
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import com.cn.iboot.diy.api.base.constants.BaseConstants;
import com.cn.iboot.diy.api.base.constants.Constants;
import com.cn.iboot.diy.api.base.constants.ExceptionEnum;
import com.cn.iboot.diy.api.base.domain.BaseResult;
import com.cn.iboot.diy.api.base.exception.BizHandlerException;
import com.cn.iboot.diy.api.base.utils.NumberUtils;
import com.cn.iboot.diy.api.base.utils.ResultsUtil;
import com.cn.iboot.diy.api.base.utils.StringUtil;
import com.cn.iboot.diy.api.invoice.domain.InvoiceOrder;
import com.cn.iboot.diy.api.invoice.service.InvoiceOrderService;
import com.cn.iboot.diy.api.operate.service.PositOprStatisticsService;
import com.cn.iboot.diy.api.redis.utils.JedisClusterUtils;
import com.cn.iboot.diy.api.system.domain.User;
import com.cn.iboot.diy.api.system.service.UserService;
import com.cn.iboot.diy.api.trans.domain.TransLog;
import com.cn.iboot.diy.api.trans.domain.TransRefundReq;
import com.github.pagehelper.PageInfo;

@RestController
@RequestMapping(value = "invoice/invoiceOrder")
public class InvoiceOrderController{

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private InvoiceOrderService invoiceOrderService;

	@Autowired
	private PositOprStatisticsService positOprStatisticsService;

	@Autowired
	private UserService userService;
	
	@Autowired
	private WebApplicationContext context; // 获取上下文

	/**
	 * 查询个人充值的交易信息（个人充值开票）
	 * 
	 * @param req
	 * @param log
	 * @return
	 */
	@PostMapping("/getTransQuery")
	public ModelAndView getTransQuery(HttpServletRequest req,HttpServletResponse response, TransLog log) {
		ModelAndView mv = new ModelAndView("invoice/invoiceOrder");
		try {
			int startNum = NumberUtils.parseInt(req.getParameter("pageNum"), 1);
			int pageSize = NumberUtils.parseInt(req.getParameter("pageSize"), 10);
			PageInfo<TransLog> pageList = new PageInfo<TransLog>(null);
			if(!StringUtil.isNullOrEmpty(req.getParameter("searchstat"))){	//页面默认标识
				HttpSession session = req.getSession();
				User user = (User) session.getAttribute(Constants.SESSION_USER);
				log.setMchntCode(user.getMchntCode());
				pageList = invoiceOrderService.getTransLogPage(startNum, pageSize, log);	
				if (log.getStartTransTime() != null && !log.getStartTransTime().equals(""))
					log.setStartTransTime(LocalDate.parse(log.getStartTransTime(), DateTimeFormatter.BASIC_ISO_DATE).toString());
				if (log.getEndTransTime() != null && !log.getEndTransTime().equals("")) 
					log.setEndTransTime(LocalDate.parse(log.getEndTransTime(), DateTimeFormatter.BASIC_ISO_DATE).toString());
			}
			mv.addObject("pageInfo", pageList);
			mv.addObject("transTypeList", BaseConstants.TransCode.values());
			mv.addObject("invoiceTypeList", BaseConstants.DiyInvoiceStat.values());
			mv.addObject("log", log);
		} catch (Exception e) {
			logger.error("## 开票查询页面出错",e);
		}
		return mv;
	}
	
	@GetMapping("/getTransQuery")
	public ModelAndView getTransQuery(HttpServletRequest req, TransLog log) {
		ModelAndView mv = new ModelAndView("invoice/invoiceOrder");
		try {
			int startNum = NumberUtils.parseInt(req.getParameter("pageNum"), 1);
			int pageSize = NumberUtils.parseInt(req.getParameter("pageSize"), 10);
			PageInfo<TransLog> pageList = new PageInfo<TransLog>(null);
			if(!StringUtil.isNullOrEmpty(req.getParameter("searchstat"))){	//页面默认标识
				HttpSession session = req.getSession();
				User user = (User) session.getAttribute(Constants.SESSION_USER);
				log.setMchntCode(user.getMchntCode());
				pageList = invoiceOrderService.getTransLogPage(startNum, pageSize, log);	
				if (log.getStartTransTime() != null && !log.getStartTransTime().equals(""))
					log.setStartTransTime(LocalDate.parse(log.getStartTransTime(), DateTimeFormatter.BASIC_ISO_DATE).toString());
				if (log.getEndTransTime() != null && !log.getEndTransTime().equals("")) 
					log.setEndTransTime(LocalDate.parse(log.getEndTransTime(), DateTimeFormatter.BASIC_ISO_DATE).toString());
			}
			mv.addObject("pageInfo", pageList);
			mv.addObject("transTypeList", BaseConstants.TransCode.values());
			mv.addObject("invoiceTypeList", BaseConstants.DiyInvoiceStat.values());
			mv.addObject("log", log);
		} catch (Exception e) {
			logger.error("## 开票查询页面出错",e);
		}
		return mv;
	}

	/**
	 * 点击开票，进入开票确认
	 * 
	 * @param reqest
	 * @return
	 */
	@PostMapping(value = "/intoInvoieceOrder")
	public TransRefundReq intoInvoieceOrder(HttpServletRequest reqest) {
		TransRefundReq req = new TransRefundReq();
		String dmsRelatedKey = StringUtil.nullToString(reqest.getParameter("dmsRelatedKey"));
		User user = (User) reqest.getSession().getAttribute(Constants.SESSION_USER);
		try {
			String tableNum = positOprStatisticsService.getCurLogNum();
			TransLog transLog = new TransLog();
			transLog.setTableNum(tableNum);
			transLog.setDmsRelatedKey(dmsRelatedKey);
			TransLog log = invoiceOrderService.getTransLogByDmsRelatedKey(transLog);
			req.setTxnPrimaryKey(log.getTxnPrimaryKey());
			req.setPhoneNumber(user.getPhoneNo());
			req.setTransAmt(log.getTransAmt());
			req.setDmsRelatedKey(log.getDmsRelatedKey());
			req.setSettleDate(log.getSettleDate());
			req.setMchntCode(log.getMchntCode());
			req.setMchntName(log.getMchntName());
			req.setShopCode(user.getShopCode());
			String shopName = null;
			shopName = userService.getShopNameByShopCode(user.getShopCode());
			req.setShopName(shopName);
		} catch (Exception e) {
			logger.error("## into确认开票页面出错",e);
		}
		return req;
	}

	/**
	 * 开票提交
	 * 
	 * @param request
	 * @return
	 * @throws InterruptedException 
	 */
	@PostMapping(value = "/invoieceOrderCommit")
	public BaseResult<Object> invoieceOrderCommit(HttpServletRequest request) throws InterruptedException {
		try {
			String itfPrimaryKey = StringUtil.nullToString(request.getParameter("itfPrimaryKey"));
			if(!JedisClusterUtils.getInstance(context).exists(itfPrimaryKey)){
				if(invoiceOrderService.getInvoiceCountByItfPrimaryKey(itfPrimaryKey) < 1){
					JedisClusterUtils.getInstance(context).set(itfPrimaryKey, itfPrimaryKey, 0);
					invoiceOrderService.insertInvoiceOrder(request);
					JedisClusterUtils.getInstance(context).del(itfPrimaryKey);
					return ResultsUtil.success();
				}else{
					return ResultsUtil.error(ExceptionEnum.userNews.UN20.getCode(), ExceptionEnum.userNews.UN20.getMsg());
				}
			}else{
				logger.error("该条流水在别处已被开票");
				return ResultsUtil.error(ExceptionEnum.userNews.UN20.getCode(), ExceptionEnum.userNews.UN20.getMsg());
			}
		} catch (BizHandlerException e) {
			logger.error("## 开票失败",e);
			return ResultsUtil.error(e.getCode(), e.getMessage());
		}
	}
	
	/**
	 * 开票详情
	 * 
	 * @param request
	 * @return
	 */
	@PostMapping(value = "/getInvoiceByItfPrimaryKey")
	public TransRefundReq getInvoiceByItfPrimaryKey(HttpServletRequest request){
		TransRefundReq req = new TransRefundReq();
		String dmsRelatedKey = StringUtil.nullToString(request.getParameter("dmsRelatedKey"));
		InvoiceOrder invoiceOrder = invoiceOrderService.getInvoiceByItfPrimaryKey(dmsRelatedKey);
		req.setDmsRelatedKey(invoiceOrder.getItfPrimaryKey());
		req.setTransAmt(invoiceOrder.getInvoiceAmt());
		req.setPhoneNumber(invoiceOrder.getInvoiceMobile());
		req.setMchntName(invoiceOrder.getMchntName());
		req.setShopName(invoiceOrder.getShopName());
		req.setInvoiceInfo(invoiceOrder.getInvoiceInfo());
		req.setCreateTime(invoiceOrder.getCreateTime());
		return req;
	}
}
