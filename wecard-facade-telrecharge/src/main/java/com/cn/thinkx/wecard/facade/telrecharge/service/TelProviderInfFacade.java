package com.cn.thinkx.wecard.facade.telrecharge.service;

import java.util.List;

import com.cn.thinkx.wecard.facade.telrecharge.model.TelProviderInf;
import com.github.pagehelper.PageInfo;

/**
 * 分销商 可购买的产品表
 * @author zhuqiuyou
 *
 */						 
public interface TelProviderInfFacade {

	TelProviderInf getTelProviderInfById(String providerId) throws Exception;

	int saveTelProviderInf(TelProviderInf  telProviderInf) throws Exception;

	int updateTelProviderInf(TelProviderInf  telProviderInf) throws Exception;

	int deleteTelProviderInfById(String providerId) throws Exception;
	
	List<TelProviderInf> getTelProviderInfList(TelProviderInf  telProviderInf) throws Exception;
	
	PageInfo<TelProviderInf> getTelProviderInfPage(int startNum, int pageSize, TelProviderInf telProviderInf) throws Exception; 
}
