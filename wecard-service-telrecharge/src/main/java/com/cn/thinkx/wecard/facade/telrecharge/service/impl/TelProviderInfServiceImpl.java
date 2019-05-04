package com.cn.thinkx.wecard.facade.telrecharge.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.thinkx.pms.base.utils.BaseConstants;
import com.cn.thinkx.pms.base.utils.StringUtil;
import com.cn.thinkx.pms.base.utils.BaseConstants.providerDefaultRoute;
import com.cn.thinkx.wecard.facade.telrecharge.mapper.TelProviderInfMapper;
import com.cn.thinkx.wecard.facade.telrecharge.model.TelProviderInf;
import com.cn.thinkx.wecard.facade.telrecharge.service.TelProviderInfFacade;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service("telProviderInfFacade")
public class TelProviderInfServiceImpl implements TelProviderInfFacade {

	@Autowired
	private TelProviderInfMapper telProviderInfMapper;

	@Override
	public TelProviderInf getTelProviderInfById(String providerId) throws Exception {
		return telProviderInfMapper.getById(providerId);
	}

	@Override
	public int saveTelProviderInf(TelProviderInf telProviderInf) throws Exception {
		if(providerDefaultRoute.DefaultRoute0.getCode().equals(telProviderInf.getDefaultRoute())){
			telProviderInfMapper.updateByDefaultRoute();
		}
		return telProviderInfMapper.insert(telProviderInf);
	}

	@Override
	public int updateTelProviderInf(TelProviderInf telProviderInf) throws Exception {
		TelProviderInf tpInf = this.getTelProviderInfById(telProviderInf.getProviderId());
		if(providerDefaultRoute.DefaultRoute0.getCode().equals(telProviderInf.getDefaultRoute())){
			if(!providerDefaultRoute.DefaultRoute0.getCode().equals(tpInf.getDefaultRoute())){	//修改的供应商数据原来不是默认路由
				telProviderInfMapper.updateByDefaultRoute();
			}
		}
		tpInf.setProviderName(telProviderInf.getProviderName());
		tpInf.setAppUrl(telProviderInf.getAppUrl());
		tpInf.setAppSecret(telProviderInf.getAppSecret());
		tpInf.setAccessToken(telProviderInf.getAccessToken());
		tpInf.setDefaultRoute(telProviderInf.getDefaultRoute());
		tpInf.setProviderRate(telProviderInf.getProviderRate());
		tpInf.setOperSolr(telProviderInf.getOperSolr());
		tpInf.setRemarks(telProviderInf.getRemarks());
		tpInf.setUpdateUser(telProviderInf.getUpdateUser());
		return telProviderInfMapper.update(tpInf);
	}

	@Override
	public int deleteTelProviderInfById(String providerId) throws Exception {
		return telProviderInfMapper.deleteById(providerId);
	}

	@Override
	public List<TelProviderInf> getTelProviderInfList(TelProviderInf telProviderInf) throws Exception {
		return telProviderInfMapper.getList(telProviderInf);
	}

	/**
	 * 供应商信息分页
	 * 
	 * @param startNum
	 * @param pageSize
	 * @param telProviderInf
	 * @return
	 * @throws Exception
	 */
	public PageInfo<TelProviderInf> getTelProviderInfPage(int startNum, int pageSize, TelProviderInf telProviderInf)
			throws Exception {
		PageHelper.startPage(startNum, pageSize);
		List<TelProviderInf> telProviderInfList = getTelProviderInfList(telProviderInf);
		for (TelProviderInf telProviderInf2 : telProviderInfList) {
			if(!StringUtil.isNullOrEmpty(telProviderInf2.getDefaultRoute()))
				telProviderInf2.setDefaultRoute(BaseConstants.providerDefaultRoute.findByCode(telProviderInf2.getDefaultRoute()));
		}
		PageInfo<TelProviderInf> telProviderInfPage = new PageInfo<TelProviderInf>(telProviderInfList);
		return telProviderInfPage;
	}
}
