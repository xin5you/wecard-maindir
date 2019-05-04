package com.cn.iboot.diy.api.invoice.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.cn.iboot.diy.api.invoice.domain.InvoiceOrder;
import com.cn.iboot.diy.api.trans.domain.TransLog;
import com.github.pagehelper.PageInfo;

public interface InvoiceOrderService {
	
	/**
	 * 查看个人充值的交易信息
	 * 
	 * @param log
	 * @return
	 */
	List<TransLog> getTransLogCur(TransLog log);
	
	/**
	 * 查看个人充值的交易信息(含分页)
	 * 
	 * @param startNum
	 * @param pageSize
	 * @param log
	 * @return
	 */
	PageInfo<TransLog> getTransLogPage(int startNum, int pageSize,TransLog log);
	
	/**
	 * 通过流水号查看个人充值的交易信息
	 * 
	 * @param transLog
	 * @return
	 */
	TransLog getTransLogByDmsRelatedKey(TransLog transLog);
	
	/**
	 * 添加开票信息
	 * 
	 * @param invoiceOrder
	 */
    void insertInvoiceOrder(HttpServletRequest request);
    
    /**
	 * 查询开票信息详情
	 * 
	 * @param itfPrimaryKey
	 * @return
	 */
	InvoiceOrder getInvoiceByItfPrimaryKey(String itfPrimaryKey);
	
	int getInvoiceCountByItfPrimaryKey(String itfPrimaryKey);
}
