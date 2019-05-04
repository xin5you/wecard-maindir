package com.cn.iboot.diy.api.invoice.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.cn.iboot.diy.api.invoice.domain.InvoiceOrder;
@Mapper
public interface InvoOrdStatisticsMapper {

	/**
	 * 统计开票金额（个人开票，批量充值开票）
	 * 
	 * @param entity
	 * @return
	 */
	List<InvoiceOrder> getInvoOrdStatistics(InvoiceOrder entity);
}
