package com.cn.iboot.diy.api.invoice.service.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.iboot.diy.api.base.constants.BaseConstants;
import com.cn.iboot.diy.api.base.constants.Constants;
import com.cn.iboot.diy.api.base.utils.NumberUtils;
import com.cn.iboot.diy.api.base.utils.StringUtil;
import com.cn.iboot.diy.api.invoice.domain.InvoiceOrder;
import com.cn.iboot.diy.api.invoice.mapper.InvoiceOrderMapper;
import com.cn.iboot.diy.api.invoice.service.InvoiceOrderService;
import com.cn.iboot.diy.api.system.domain.User;
import com.cn.iboot.diy.api.trans.domain.TransLog;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service("invoiceOrderService")
public class InvoiceOrderServiceImpl implements InvoiceOrderService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private InvoiceOrderMapper invoiceOrderMapper;

	@Override
	public List<TransLog> getTransLogCur(TransLog log) {
		return invoiceOrderMapper.getTransLogCur(log);
	}

	@Override
	public PageInfo<TransLog> getTransLogPage(int startNum, int pageSize, TransLog log) {
		if (log.getStartTransTime() != null && !log.getStartTransTime().equals(""))
			log.setStartTransTime(LocalDate.parse(log.getStartTransTime()).format(DateTimeFormatter.BASIC_ISO_DATE));
		if (log.getEndTransTime() != null && !log.getEndTransTime().equals("")) {
			log.setEndTransTime(LocalDate.parse(log.getEndTransTime()).format(DateTimeFormatter.BASIC_ISO_DATE));
		}
		LocalDate date = LocalDate.now();
		if (("cur").equals(log.getQueryType())) {
			log.setStartTransTime(LocalDate.parse(date.toString()).format(DateTimeFormatter.BASIC_ISO_DATE));
			log.setEndTransTime(LocalDate.parse(date.toString()).format(DateTimeFormatter.BASIC_ISO_DATE));
		}
		PageHelper.startPage(startNum, pageSize);
		List<TransLog> ssList = getTransLogCur(log);
		PageInfo<TransLog> ssPage = new PageInfo<TransLog>(ssList);
		ssPage.getList().stream().filter(e -> {
			e.setSettleDate(LocalDate.parse(e.getSettleDate(), DateTimeFormatter.BASIC_ISO_DATE).toString());
			e.setTransType(BaseConstants.TransCode.CW20.getValue());
			e.setTransAmt(NumberUtils.RMBCentToYuan(e.getTransAmt()));
			e.setRespCode(BaseConstants.ITFRespCode.findByCode(e.getRespCode()).getValue());
			return true;
		}).collect(Collectors.toList());
		return ssPage;
	}

	@Override
	public TransLog getTransLogByDmsRelatedKey(TransLog transLog) {
		TransLog log = invoiceOrderMapper.getTransLogByDmsRelatedKey(transLog);
		log.setSettleDate(LocalDate.parse(log.getSettleDate(), DateTimeFormatter.BASIC_ISO_DATE).toString());
		log.setTransAmt(NumberUtils.RMBCentToYuan(log.getTransAmt()));
		return log;
	}

	@Override
	public void insertInvoiceOrder(HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute(Constants.SESSION_USER);
		String itfPrimaryKey = StringUtil.nullToString(request.getParameter("itfPrimaryKey"));
		String phoneNumber = StringUtil.nullToString(request.getParameter("phone"));
		String invoiceAmt = StringUtil.nullToString(request.getParameter("invoiceAmt"));
		String mchntCode = StringUtil.nullToString(request.getParameter("mchntCode"));
		String shopCode = StringUtil.nullToString(request.getParameter("shopCode"));
		String invoiceInfo = StringUtil.nullToString(request.getParameter("invoiceInfo"));
		InvoiceOrder invoiceOrder = new InvoiceOrder();
		invoiceOrder.setItfPrimaryKey(itfPrimaryKey);
		invoiceOrder.setInvoiceUserName(user.getUserName());
		invoiceOrder.setInvoiceMobile(phoneNumber);
		invoiceOrder.setInvoiceAmt(NumberUtils.RMBYuanToCent(invoiceAmt));
		invoiceOrder.setInvoiceType("个人充值开票");
		invoiceOrder.setMchntCode(mchntCode);
		invoiceOrder.setShopCode(shopCode);
		invoiceOrder.setInvoiceInfo(invoiceInfo);
		invoiceOrder.setInvoiceStat("1"); // 1代表已开票
		invoiceOrder.setCreateUser(user.getId());
		invoiceOrder.setUpdateUser(user.getId());
		invoiceOrderMapper.insert(invoiceOrder);
	}

	@Override
	public InvoiceOrder getInvoiceByItfPrimaryKey(String itfPrimaryKey) {
		InvoiceOrder invoiceOrder=null;
		invoiceOrder = invoiceOrderMapper.getInvoiceByItfPrimaryKey(itfPrimaryKey);
		if(!StringUtil.isNullOrEmpty(invoiceOrder.getInvoiceAmt())){
			invoiceOrder.setInvoiceAmt(NumberUtils.RMBCentToYuan(invoiceOrder.getInvoiceAmt()));
		}
		return invoiceOrder;
	}

	@Override
	public int getInvoiceCountByItfPrimaryKey(String itfPrimaryKey) {
		return invoiceOrderMapper.getInvoiceCountByItfPrimaryKey(itfPrimaryKey);
	}

}
