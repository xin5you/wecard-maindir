package com.cn.iboot.diy.api.original.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.iboot.diy.api.base.utils.NumberUtils;
import com.cn.iboot.diy.api.original.domain.PositDetail;
import com.cn.iboot.diy.api.original.domain.PositDetailUpload;
import com.cn.iboot.diy.api.original.domain.PositStatistics;
import com.cn.iboot.diy.api.original.domain.PositStatisticsUpload;
import com.cn.iboot.diy.api.original.mapper.PositStatisticsMapper;
import com.cn.iboot.diy.api.original.service.PositStatisticsService;
import com.cn.iboot.diy.api.original.util.ComputeUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service("positStatisticsService")
public class PositStatisticsServiceImpl implements PositStatisticsService {

	@Autowired
	private PositStatisticsMapper positStatisticsMapper;
	
	@Override
	public PageInfo<PositStatistics> getPositStatisticsPage(int startNum, int pageSize, PositStatistics ps) {
		PageHelper.startPage(startNum, pageSize);
		List<PositStatistics> psList = getPositStatisticsList(ps);
		PageInfo<PositStatistics> psPage = new PageInfo<PositStatistics>(psList);
		if(psPage.getList() != null){
			psPage.getList().stream()
			.filter(e -> {
				e.setCardPayAmt(NumberUtils.RMBCentToYuan(e.getCardPayAmt()));
				e.setQuickPayAmt(NumberUtils.RMBCentToYuan(e.getQuickPayAmt()));
				e.setPayAmt(NumberUtils.RMBCentToYuan(e.getPayAmt()));
				return true;
			}).collect(Collectors.toList());
		}
		return psPage;
	}

	@Override
	public PageInfo<PositStatistics> getPositDetailPage(int startNum, int pageSize, PositDetail pd) {
		PageHelper.startPage(startNum, pageSize);
		PageInfo<PositStatistics> pdPage = new PageInfo<PositStatistics>(getPositDetailList(pd));
		if(pdPage.getList() != null){
			pdPage.getList().stream().filter(e -> {
				e.setSettleDate(e.getSettleDate().substring(0, 4)+"年"+e.getSettleDate().substring(4, 6)+"月"+e.getSettleDate().substring(6)+"日");
				e.setCardPayAmt(NumberUtils.RMBCentToYuan(e.getCardPayAmt()));
				e.setQuickPayAmt(NumberUtils.RMBCentToYuan(e.getQuickPayAmt()));
				e.setPayAmt(NumberUtils.RMBCentToYuan(e.getPayAmt()));
				return true;
			}).collect(Collectors.toList());
		}
		return pdPage;
	}

	@Override
	public List<PositStatistics> getPositDetailList(PositDetail pd) {
		List<PositStatistics> odList = positStatisticsMapper.getPositDetailList(pd);
		return odList;
	}

	@Override
	public List<PositStatisticsUpload> getPositStatisticsUploadList(PositStatistics ps) {
		List<PositStatisticsUpload> list = positStatisticsMapper.getPositStatisticsUploadList(ps);
		return list.stream().filter(e -> {
			e.setCardPayAmt(NumberUtils.RMBCentToYuan(e.getCardPayAmt()));
			e.setQuickPayAmt(NumberUtils.RMBCentToYuan(e.getQuickPayAmt()));
			e.setPayAmt(NumberUtils.RMBCentToYuan(e.getPayAmt()));
			return true;
		}).collect(Collectors.toList());
	}

	@Override
	public List<PositDetailUpload> getPositDetailUploadList(PositDetail pd) {
		List<PositDetailUpload> list = positStatisticsMapper.getPositDetailUploadList(pd);
		return list.stream().filter(e -> {
			e.setSettleDate(e.getSettleDate().substring(0, 4)+"年"+e.getSettleDate().substring(4, 6)+"月"+e.getSettleDate().substring(6)+"日");
			e.setCardPayAmt(NumberUtils.RMBCentToYuan(e.getCardPayAmt()));
			e.setQuickPayAmt(NumberUtils.RMBCentToYuan(e.getQuickPayAmt()));
			e.setPayAmt(NumberUtils.RMBCentToYuan(e.getPayAmt()));
			return true;
		}).collect(Collectors.toList());
	}

	@Override
	public List<PositStatistics> getPositStatisticsList(PositStatistics ps) {
		return positStatisticsMapper.getPositStatisticsList(ps);
	}

}
