package com.cn.thinkx.oms.module.mchnteshop.mapper;

import java.util.List;

import com.cn.thinkx.oms.module.mchnteshop.model.MchntEshopInf;

public interface MchntEshopInfMapper {
	
	public MchntEshopInf getMchntEshopInfById(String eShopId);
	
	public int insertMchntEshopInf(MchntEshopInf mchEshop);
	
	public List<MchntEshopInf> getMchntEshopInfList(MchntEshopInf mchEshop);
	
	public int updateMchntEshopInf(MchntEshopInf mchEshop);
	
	public int deleteMchntEshopInf(String eShopId);
	
	public MchntEshopInf getMchntEshopInfByMchntCode(String mchntCode);
	
}
