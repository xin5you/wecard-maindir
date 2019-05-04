package com.cn.thinkx.wecard.facade.telrecharge.service;

import java.util.List;

import com.cn.thinkx.wecard.facade.telrecharge.model.TelProviderOrderInf;
import com.github.pagehelper.PageInfo;

/**
 * 供应商 充值订单
 * @author zhuqiuyou
 *
 */						 
public interface TelProviderOrderInfFacade {

	TelProviderOrderInf getTelProviderOrderInfById(String regOrderId) throws Exception;

	int saveTelProviderOrderInf(TelProviderOrderInf  telProviderOrderInf) throws Exception;

	int updateTelProviderOrderInf(TelProviderOrderInf  telProviderOrderInf) throws Exception;

	int deleteTelProviderOrderInfById(String regOrderId) throws Exception;
	
	List<TelProviderOrderInf> getTelProviderOrderInfList(TelProviderOrderInf  telProviderOrderInf) throws Exception;
	
	TelProviderOrderInf getTelOrderInfByChannelOrderId(String channelOrderId) throws Exception;
	
	PageInfo<TelProviderOrderInf> getTelProviderOrderInfPage(int startNum, int pageSize, TelProviderOrderInf telProviderOrderInf) throws Exception;
	
	/**
	 * 查找updateTime 10分钟以内，1分钟以上的订单
	 * @param telProviderOrderInf
	 * @return
	 */
	List<TelProviderOrderInf> getListByTimer(TelProviderOrderInf telProviderOrderInf);
}
