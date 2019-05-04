package com.cn.iboot.diy.api.invoice.service;

import java.util.List;

import com.cn.iboot.diy.api.invoice.domain.InvoiceOrder;
import com.github.pagehelper.PageInfo;

public interface InvoOrdStatisticsService {

	/**
	 * 统计开票金额（个人开票，批量充值开票）
	 * 
	 * @param entity
	 * @return
	 */
	List<InvoiceOrder> getInvoOrdStatistics(InvoiceOrder entity);
	
	/**
	 * 统计开票金额（个人开票，批量充值开票    含分页）
	 * 
	 * @param startNum
	 * @param pageSize
	 * @param entity
	 * @return
	 */
	PageInfo<InvoiceOrder> getInvoOrdStatisticsPage(int startNum, int pageSize,InvoiceOrder entity);
}
