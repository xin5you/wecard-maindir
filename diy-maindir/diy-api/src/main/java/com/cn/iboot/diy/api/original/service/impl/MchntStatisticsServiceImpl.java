package com.cn.iboot.diy.api.original.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.iboot.diy.api.base.service.impl.BaseServiceImpl;
import com.cn.iboot.diy.api.base.utils.NumberUtils;
import com.cn.iboot.diy.api.original.domain.MchntStatistics;
import com.cn.iboot.diy.api.original.mapper.MchntStatisticsMapper;
import com.cn.iboot.diy.api.original.service.MchntStatisticsService;
import com.cn.iboot.diy.api.original.util.ComputeUtil;

@Service("mchntStatisticsService")
public class MchntStatisticsServiceImpl extends BaseServiceImpl<MchntStatistics> implements MchntStatisticsService {

	@Autowired
	private MchntStatisticsMapper mchntStatisticsMapper;

	@Override
	public List<String> getShopCodeByMchntCode(String mchntCode) {
		return mchntStatisticsMapper.getShopCodeByMchntCode(mchntCode);
	}

	@Override
	public List<MchntStatistics> getMchntStatisticsList(MchntStatistics ms) {
		List<MchntStatistics> mchntList = mchntStatisticsMapper.getMchntStatisticsList(ms);
		return mchntList.stream().filter(e -> {
			e.setSettleAmt(NumberUtils.RMBCentToYuan(ComputeUtil.toCompute(e.getSettleAmt(),e.getFee())));
			e.setCardPayAmt(NumberUtils.RMBCentToYuan(e.getCardPayAmt()));
			e.setQuickPayAmt(NumberUtils.RMBCentToYuan(e.getQuickPayAmt()));
			e.setOlRechargeDen(NumberUtils.RMBCentToYuan(e.getOlRechargeDen()));
			e.setOlRechargeAmt(NumberUtils.RMBCentToYuan(e.getOlRechargeAmt()));
			e.setPfRechargeDen(NumberUtils.RMBCentToYuan(e.getPfRechargeDen()));
			e.setPfRechargeAmt(NumberUtils.RMBCentToYuan(e.getPfRechargeAmt()));
			e.setPfSubsidyAmt(NumberUtils.RMBCentToYuan(e.getPfSubsidyAmt()));
			e.setCardBal(NumberUtils.RMBCentToYuan(getCardBal(ms)));
			e.setFee(NumberUtils.RMBCentToYuan(e.getFee()));
			return true;
		}).collect(Collectors.toList());
	}

	@Override
	public String getCardBal(MchntStatistics ms) {
		return mchntStatisticsMapper.getCardBal(ms);
	}
	
}
