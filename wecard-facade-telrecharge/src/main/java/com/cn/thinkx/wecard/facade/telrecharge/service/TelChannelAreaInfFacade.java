package com.cn.thinkx.wecard.facade.telrecharge.service;

import java.util.List;

import com.cn.thinkx.wecard.facade.telrecharge.model.TelChannelAreaInf;

/**
 * 话费充值，地区维护service
 * @author zhuqiuyou
 *
 */
public interface TelChannelAreaInfFacade {

	TelChannelAreaInf getTelChannelAreaInfById(String area_id) throws Exception;

	int saveTelChannelAreaInf(TelChannelAreaInf  telChannelAreaInf) throws Exception;

	int updateTelChannelAreaInf(TelChannelAreaInf  telChannelAreaInf) throws Exception;

	int deleteTelChannelAreaInfById(String area_id) throws Exception;
	
	List<TelChannelAreaInf> getTelChannelAreaInfList(TelChannelAreaInf  telChannelAreaInf) throws Exception;
}
