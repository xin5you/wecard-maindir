package com.cn.thinkx.wecard.facade.telrecharge.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cn.thinkx.common.base.core.mapper.BaseMapper;
import com.cn.thinkx.wecard.facade.telrecharge.model.TelProviderOrderInf;

@Repository("telProviderOrderInfMapper")
public interface TelProviderOrderInfMapper extends BaseMapper<TelProviderOrderInf> {

	TelProviderOrderInf getTelOrderInfByChannelOrderId(String channelOrderId);
	
	/**
	 * 查找updateTime 10分钟以内，1分钟以上的订单
	 * @param telProviderOrderInf
	 * @return
	 */
	List<TelProviderOrderInf> getListByTimer(TelProviderOrderInf telProviderOrderInf);
}
