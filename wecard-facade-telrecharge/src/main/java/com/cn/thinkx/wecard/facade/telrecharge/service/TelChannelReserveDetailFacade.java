package com.cn.thinkx.wecard.facade.telrecharge.service;

import java.util.List;

import com.cn.thinkx.wecard.facade.telrecharge.model.TelChannelReserveDetail;
import com.github.pagehelper.PageInfo;

/**
 * 分销商 可购买的产品表
 * @author zhuqiuyou
 *
 */						 
public interface TelChannelReserveDetailFacade {

	TelChannelReserveDetail getTelChannelReserveDetailById(String id) throws Exception;

	int saveTelChannelReserveDetail(TelChannelReserveDetail  telChannelReserveDetail) throws Exception;

	int updateTelChannelReserveDetail(TelChannelReserveDetail  telChannelReserveDetail) throws Exception;

	int deleteTelChannelReserveDetailById(String id) throws Exception;
	
	List<TelChannelReserveDetail> getTelChannelReserveDetailList(TelChannelReserveDetail  telChannelReserveDetail) throws Exception;

	PageInfo<TelChannelReserveDetail> getTelChannelReserveDetailPage(int startNum, int pageSize, TelChannelReserveDetail entity) throws Exception;

	boolean updateTelChannelInfReserve(TelChannelReserveDetail  telChannelReserveDetail) throws Exception;
}
