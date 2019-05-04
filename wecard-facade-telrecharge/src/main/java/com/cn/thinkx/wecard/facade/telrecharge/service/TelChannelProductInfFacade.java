package com.cn.thinkx.wecard.facade.telrecharge.service;

import java.util.List;
import java.util.Map;

import com.cn.thinkx.wecard.facade.telrecharge.model.TelChannelProductInf;
import com.github.pagehelper.PageInfo;

/**
 * 分销商 可购买的产品表
 * @author zhuqiuyou
 *
 */
public interface TelChannelProductInfFacade {

	TelChannelProductInf getTelChannelProductInfById(String productId) throws Exception;

	int saveTelChannelProductInf(TelChannelProductInf  telChannelProductInf) throws Exception;
	/**
	 * 保存对象返回ID
	 * @param telChannelProductInf
	 * @return
	 * @throws Exception
	 */
	String saveTelChannelProductForId(TelChannelProductInf  telChannelProductInf) throws Exception;

	int updateTelChannelProductInf(TelChannelProductInf  telChannelProductInf) throws Exception;

	int deleteTelChannelProductInfById(String productId) throws Exception;
	
	/**
	 * 获取分销商产品的折扣
	 * @return maps --> operId:运营商，productType: 类型， areaName:地区名称，productAmt:产品面额（3位小数）
	 */
	TelChannelProductInf getProductRateByMaps(Map maps);
	
	List<TelChannelProductInf> getTelChannelProductInfList(TelChannelProductInf  telChannelProductInf) throws Exception;
	
	PageInfo<TelChannelProductInf> getTelChannelProductInfPage(int startNum, int pageSize, 	TelChannelProductInf  telChannelProductInf) throws Exception;
	
	 /**
	  * 查询分銷商手机充值产品（带分销商的折扣率）
	  * 
	  * @param telChannelProductInf
	  * @return
	  */
	TelChannelProductInf getChannelProductByItemId(String id) throws Exception;
	 
	 /**
	  * 通过分销商id获取手机充值产品
	  * 
	  * @param channelId
	  * @return
	  */
	 List<TelChannelProductInf> getChannelProductListByChannelId(String channelId) throws Exception;

}
