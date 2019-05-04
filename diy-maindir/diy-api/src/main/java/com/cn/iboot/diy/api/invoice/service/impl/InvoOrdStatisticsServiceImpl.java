package com.cn.iboot.diy.api.invoice.service.impl;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cn.iboot.diy.api.base.utils.NumberUtils;
import com.cn.iboot.diy.api.invoice.domain.InvoiceOrder;
import com.cn.iboot.diy.api.invoice.mapper.InvoOrdStatisticsMapper;
import com.cn.iboot.diy.api.invoice.service.InvoOrdStatisticsService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service("invoOrdStatisticsService")
public class InvoOrdStatisticsServiceImpl implements InvoOrdStatisticsService{
	
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	@Qualifier("invoOrdStatisticsMapper")
	private InvoOrdStatisticsMapper invoOrdStatisticsMapper;
	
	@Override
	public List<InvoiceOrder> getInvoOrdStatistics(InvoiceOrder entity) {
		List<InvoiceOrder> list = null;
		try {
			list = invoOrdStatisticsMapper.getInvoOrdStatistics(entity);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("开票统计出错", e);
		}
		return list;
	}

	@Override
	public PageInfo<InvoiceOrder> getInvoOrdStatisticsPage(int startNum, int pageSize,InvoiceOrder entity) {
		PageInfo<InvoiceOrder> invoOrdPage = null;
		try {
			if (entity.getStartDate() != null && !entity.getStartDate().equals(""))
				entity.setStartDate(LocalDate.parse(entity.getStartDate()).format(DateTimeFormatter.BASIC_ISO_DATE));
			if (entity.getEndDate() != null && !entity.getEndDate().equals("")) {
				entity.setEndDate(LocalDate.parse(entity.getEndDate()).format(DateTimeFormatter.BASIC_ISO_DATE));
			}
			PageHelper.startPage(startNum, pageSize);
			List<InvoiceOrder> invoOrdList = getInvoOrdStatistics(entity);
			invoOrdPage = new PageInfo<InvoiceOrder>(invoOrdList);
			invoOrdPage.getList().stream().filter(e -> {
				e.setInvoiceAmtCount(NumberUtils.RMBCentToYuan(e.getInvoiceAmtCount()));
				e.setBinvoiceAmtCount(NumberUtils.RMBCentToYuan(e.getBinvoiceAmtCount()));
				e.setAmtCount(NumberUtils.RMBCentToYuan(e.getAmtCount()));
				return true;
			}).collect(Collectors.toList());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("开票统计出错", e);
		}
		return invoOrdPage;
	}

}
