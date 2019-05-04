package com.cn.thinkx.wecard.facade.telrecharge.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.cn.thinkx.common.base.core.mapper.BaseMapper;
import com.cn.thinkx.wecard.facade.telrecharge.model.TelChannelOrderInf;
import com.cn.thinkx.wecard.facade.telrecharge.model.TelChannelOrderInfUpload;

@Repository("telChannelOrderInfMapper")
public interface TelChannelOrderInfMapper extends BaseMapper<TelChannelOrderInf> {

	TelChannelOrderInf getTelChannelOrderInfByOuterId(@Param("outerId")String outerId,@Param("channelId")String channelId) ;
	
	List<TelChannelOrderInf> getTelChannelOrderInfList(TelChannelOrderInf order);
	
	List<TelChannelOrderInfUpload> getTelChannelOrderInfListToUpload(TelChannelOrderInf order);
	
	TelChannelOrderInf getTelChannelOrderInfCount(TelChannelOrderInf order);
}
