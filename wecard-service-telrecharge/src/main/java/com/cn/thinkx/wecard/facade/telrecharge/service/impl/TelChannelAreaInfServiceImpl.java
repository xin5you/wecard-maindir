package com.cn.thinkx.wecard.facade.telrecharge.service.impl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.thinkx.wecard.facade.telrecharge.mapper.TelChannelAreaInfMapper;
import com.cn.thinkx.wecard.facade.telrecharge.model.TelChannelAreaInf;
import com.cn.thinkx.wecard.facade.telrecharge.service.TelChannelAreaInfFacade;

@Service("telChannelAreaInfFacade")
public class TelChannelAreaInfServiceImpl  implements TelChannelAreaInfFacade {

	@Autowired
	private TelChannelAreaInfMapper telChannelAreaInfMapper;
	
	@Override
	public TelChannelAreaInf getTelChannelAreaInfById(String areaId) throws Exception {
		return telChannelAreaInfMapper.getById(areaId);
	}

	@Override
	public int saveTelChannelAreaInf(TelChannelAreaInf telChannelAreaInf) throws Exception {
		 return telChannelAreaInfMapper.insert(telChannelAreaInf);
	}

	@Override
	public int updateTelChannelAreaInf(TelChannelAreaInf telChannelAreaInf) throws Exception {
		return telChannelAreaInfMapper.update(telChannelAreaInf);
	}

	@Override
	public int deleteTelChannelAreaInfById(String areaId) throws Exception {
		return telChannelAreaInfMapper.deleteById(areaId);
	}

	@Override
	public List<TelChannelAreaInf> getTelChannelAreaInfList(TelChannelAreaInf telChannelAreaInf) throws Exception {
		return telChannelAreaInfMapper.getList(telChannelAreaInf);
	}
}
