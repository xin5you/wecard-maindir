package com.cn.thinkx.wecard.facade.telrecharge.service;

import java.util.List;

import com.cn.thinkx.wecard.facade.telrecharge.model.TelChannelOrderInf;
import com.cn.thinkx.wecard.facade.telrecharge.model.TelChannelOrderInfUpload;
import com.cn.thinkx.wecard.facade.telrecharge.resp.TeleRespDomain;
import com.cn.thinkx.wecard.facade.telrecharge.resp.TeleRespVO;
import com.github.pagehelper.PageInfo;


public interface TelChannelOrderInfFacade {

	TelChannelOrderInf getTelChannelOrderInfById(String channelOrderId) throws Exception;

	int saveTelChannelOrderInf(TelChannelOrderInf  telChannelOrderInf) throws Exception;

	int updateTelChannelOrderInf(TelChannelOrderInf  telChannelOrderInf) throws Exception;

	int deleteTelChannelOrderInfById(String channelOrderId) throws Exception;
	
	/**
	 * 分销商话费充值扣款
	 * @param telChannelOrderInf
	 * @param operId
	 * @param areaName
	 * @return
	 * @throws Exception
	 */
	TeleRespDomain<TeleRespVO> proChannelOrder(TelChannelOrderInf telChannelOrderInf,String operId,String areaName) throws Exception;
	
	List<TelChannelOrderInf> getTelChannelOrderInfList(TelChannelOrderInf  telChannelOrderInf) throws Exception;
	
	void doRechargeMobileMsg(String channelOrderId);
	
	/**
	 *  分销商 根据外部订单查询
	 * @param outerId
	 * @param channelId
	 * @return
	 * @throws Exception
	 */
	TelChannelOrderInf getTelChannelOrderInfByOuterId(String outerId,String channelId) throws Exception;
	
	/**
	 * 分销商订单分页列表
	 * @param startNum
	 * @param pageSize
	 * @param telChannelOrderInf
	 * @return  Exception
	 */
     PageInfo<TelChannelOrderInf> getTelChannelOrderInfPage(int startNum, int pageSize, TelChannelOrderInf telChannelOrderInf) throws Exception;
     
     PageInfo<TelChannelOrderInf> getTelChannelOrderInf(int startNum, int pageSize, TelChannelOrderInf telChannelOrderInf) throws Exception;
     
     List<TelChannelOrderInfUpload> getTelChannelOrderInfListToUpload(TelChannelOrderInf order);
     
     TelChannelOrderInf getTelChannelOrderInfCount(TelChannelOrderInf order);
}
