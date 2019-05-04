package com.cn.thinkx.wecard.facade.telrecharge.mapper;

import org.springframework.stereotype.Repository;

import com.cn.thinkx.common.base.core.mapper.BaseMapper;
import com.cn.thinkx.wecard.facade.telrecharge.model.TelChannelItemList;

@Repository("telChannelItemListMapper")
public interface TelChannelItemListMapper extends BaseMapper<TelChannelItemList> {

	int deleteByProductId(String id) throws Exception;
}
