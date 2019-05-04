package com.cn.iboot.diy.api.original.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.iboot.diy.api.base.service.impl.BaseServiceImpl;
import com.cn.iboot.diy.api.base.utils.NumberUtils;
import com.cn.iboot.diy.api.original.domain.ShopStatistics;
import com.cn.iboot.diy.api.original.domain.ShopStatisticsUpload;
import com.cn.iboot.diy.api.original.mapper.ShopStatisticsMapper;
import com.cn.iboot.diy.api.original.service.ShopStatisticsService;
import com.cn.iboot.diy.api.original.util.ComputeUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service("shopStatisticsService")
public class ShopStatisticsServiceImpl extends BaseServiceImpl<ShopStatistics> implements ShopStatisticsService {

	@Autowired
	private ShopStatisticsMapper shopStatisticsMapper;
	
	@Override
	public PageInfo<ShopStatistics> getShopStatisticsPage(int startNum, int pageSize, ShopStatistics ss) {
		PageHelper.startPage(startNum, pageSize);
		List<ShopStatistics> ssList = shopStatisticsMapper.getShopStatisticsList(ss);
		PageInfo<ShopStatistics> ssPage = new PageInfo<ShopStatistics>(ssList);
		if(ssPage.getList()!=null){
			ssPage.getList().stream().filter(e -> {
				e.setCardPayAmt(NumberUtils.RMBCentToYuan(e.getCardPayAmt()));
				e.setQuickPayAmt(NumberUtils.RMBCentToYuan(e.getQuickPayAmt()));
				e.setOlRechargeDen(NumberUtils.RMBCentToYuan(e.getOlRechargeDen()));
				e.setOlRechargeAmt(NumberUtils.RMBCentToYuan(e.getOlRechargeAmt()));
				e.setPfRechargeDen(NumberUtils.RMBCentToYuan(e.getPfRechargeDen()));
				e.setPfRechargeAmt(NumberUtils.RMBCentToYuan(e.getPfRechargeAmt()));
				e.setPfSubsidyAmt(NumberUtils.RMBCentToYuan(e.getPfSubsidyAmt()));
				return true;
				}).collect(Collectors.toList());
		}
		return ssPage;
	}

	@Override
	public List<ShopStatistics> getShopStatisticsList(ShopStatistics ss) {
		List<ShopStatistics> list = shopStatisticsMapper.getShopStatisticsList(ss);
		return list.stream().filter(e -> {
			e.setPayCountAmt(NumberUtils.RMBCentToYuan(ComputeUtil.toSum(e.getCardPayAmt(), e.getQuickPayAmt())));
			e.setCardPayAmt(NumberUtils.RMBCentToYuan(e.getCardPayAmt()));
			e.setQuickPayAmt(NumberUtils.RMBCentToYuan(e.getQuickPayAmt()));
			return true;
			}).collect(Collectors.toList());
	}

	@Override
	public List<ShopStatisticsUpload> getShopStatisticsUploadList(ShopStatistics ss) {
		List<ShopStatisticsUpload> list =shopStatisticsMapper.getShopStatisticsUploadList(ss);
		return list.stream().filter(e -> {
			e.setCardPayAmt(NumberUtils.RMBCentToYuan(e.getCardPayAmt()));
			e.setQuickPayAmt(NumberUtils.RMBCentToYuan(e.getQuickPayAmt()));
			e.setPfSubsidyAmt(NumberUtils.RMBCentToYuan(e.getPfSubsidyAmt()));
			return true;
			}).collect(Collectors.toList());
	}
}
