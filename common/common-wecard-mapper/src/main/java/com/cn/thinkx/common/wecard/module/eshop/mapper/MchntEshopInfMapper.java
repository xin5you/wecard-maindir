package com.cn.thinkx.common.wecard.module.eshop.mapper;

import java.util.List;

import com.cn.thinkx.common.wecard.domain.eshop.MchntEshopInf;

public interface MchntEshopInfMapper {
	
	MchntEshopInf getMchntEshopInfById(String eShopId);
	
	List<MchntEshopInf> getMchntEshopInfList(MchntEshopInf mchEshop);
	
	MchntEshopInf getMchntEshopInfByMchntCode(String mchntCode);
	
}
