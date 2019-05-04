package com.cn.thinkx.wecard.facade.telrecharge.mapper;

import org.springframework.stereotype.Repository;

import com.cn.thinkx.common.base.core.mapper.BaseMapper;
import com.cn.thinkx.wecard.facade.telrecharge.model.TelChannelInf;

@Repository("telChannelInfMapper")
public interface TelChannelInfMapper extends BaseMapper<TelChannelInf> {

	TelChannelInf getTelChannelInfByMchntCode(String mchntCode);
}
