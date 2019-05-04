package com.cn.thinkx.wecard.facade.telrecharge.mapper;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.cn.thinkx.common.base.core.mapper.BaseMapper;
import com.cn.thinkx.wecard.facade.telrecharge.model.TelChannelProductInf;

@Repository("telChannelProductInfMapper")
public interface TelChannelProductInfMapper extends BaseMapper<TelChannelProductInf> {

	TelChannelProductInf getProductRateByMaps(Map maps);

	/**
	 * 查看分销商的折扣率
	 * 
	 * @param id
	 * @return
	 */
	TelChannelProductInf getChannelProductByItemId(String id);

	/**
	 * 通过分销商id获取手机充值产品
	 * 
	 * @param channelId
	 * @return
	 */
	List<TelChannelProductInf> getChannelProductListByChannelId(String channelId);
}
