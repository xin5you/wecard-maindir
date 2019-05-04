package com.cn.thinkx.wecard.facade.telrecharge.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.thinkx.pms.base.utils.BaseConstants;
import com.cn.thinkx.pms.base.utils.StringUtil;
import com.cn.thinkx.wecard.facade.telrecharge.mapper.TelProviderOrderInfMapper;
import com.cn.thinkx.wecard.facade.telrecharge.model.TelProviderOrderInf;
import com.cn.thinkx.wecard.facade.telrecharge.service.TelProviderOrderInfFacade;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service("telProviderOrderInfFacade")
public class TelProviderOrderInfImpl implements TelProviderOrderInfFacade {

	@Autowired
	private TelProviderOrderInfMapper telProviderOrderInfMapper;

	@Override
	public TelProviderOrderInf getTelProviderOrderInfById(String regOrderId) throws Exception {
		return telProviderOrderInfMapper.getById(regOrderId);
	}

	@Override
	public int saveTelProviderOrderInf(TelProviderOrderInf regOrderId) throws Exception {
		return telProviderOrderInfMapper.insert(regOrderId);
	}

	@Override
	public int updateTelProviderOrderInf(TelProviderOrderInf telProviderOrderInf) throws Exception {
		return telProviderOrderInfMapper.update(telProviderOrderInf);
	}

	@Override
	public int deleteTelProviderOrderInfById(String regOrderId) throws Exception {
		return telProviderOrderInfMapper.deleteById(regOrderId);
	}

	@Override
	public List<TelProviderOrderInf> getTelProviderOrderInfList(TelProviderOrderInf telProviderOrderInf)
			throws Exception {
		return telProviderOrderInfMapper.getList(telProviderOrderInf);
	}

	@Override
	public TelProviderOrderInf getTelOrderInfByChannelOrderId(String channelOrderId) throws Exception {
		return telProviderOrderInfMapper.getTelOrderInfByChannelOrderId(channelOrderId);
	}

	/**
	 * 供应商订单分页
	 * 
	 * @param startNum
	 * @param pageSize
	 * @param telProviderOrderInf
	 * @return
	 * @throws Exception
	 */
	public PageInfo<TelProviderOrderInf> getTelProviderOrderInfPage(int startNum, int pageSize,
			TelProviderOrderInf telProviderOrderInf) throws Exception {
		PageHelper.startPage(startNum, pageSize);
		List<TelProviderOrderInf> telProviderOrderInfList = getTelProviderOrderInfList(telProviderOrderInf);
		for (TelProviderOrderInf telProviderOrderInf2 : telProviderOrderInfList) {
			if(!StringUtil.isNullOrEmpty(telProviderOrderInf2.getPayState()))
				telProviderOrderInf2.setPayState(BaseConstants.providerOrderPayState.findByCode(telProviderOrderInf2.getPayState()));
			if(!StringUtil.isNullOrEmpty(telProviderOrderInf2.getRechargeState()))
				telProviderOrderInf2.setRechargeState(BaseConstants.providerOrderRechargeState.findByCode(telProviderOrderInf2.getRechargeState()));
		}
		PageInfo<TelProviderOrderInf> telProviderOrderInfPage = new PageInfo<TelProviderOrderInf>(
				telProviderOrderInfList);
		return telProviderOrderInfPage;
	}
	
	/**
	 * 查找updateTime 10分钟以内，1分钟以上的订单
	 * @param telProviderOrderInf
	 * @return
	 */
	public List<TelProviderOrderInf> getListByTimer(TelProviderOrderInf telProviderOrderInf){
		return telProviderOrderInfMapper.getListByTimer(telProviderOrderInf);
	}
}
