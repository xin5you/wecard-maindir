package com.cn.thinkx.wecard.facade.telrecharge.service;

import java.util.List;

import com.cn.thinkx.wecard.facade.telrecharge.model.TelChannelItemList;
import com.github.pagehelper.PageInfo;

/**
 * 话费充值，产品地区关联中间service
 * 
 * @author zhuqiuyou
 *
 */
public interface TelChannelItemListFacade {

	TelChannelItemList getTelChannelItemListById(String id) throws Exception;

	int saveTelChannelItemList(TelChannelItemList telChannelItemList) throws Exception;

	int updateTelChannelItemList(TelChannelItemList telChannelItemList) throws Exception;

	int deleteTelChannelItemListById(String id) throws Exception;

	int deleteByProductId(String id) throws Exception;

	List<TelChannelItemList> getTelChannelItemList(TelChannelItemList telChannelItemList) throws Exception;

	PageInfo<TelChannelItemList> getTelChannelItemListPage(int startNum, int pageSize,
			TelChannelItemList telChannelItemList) throws Exception;
}
