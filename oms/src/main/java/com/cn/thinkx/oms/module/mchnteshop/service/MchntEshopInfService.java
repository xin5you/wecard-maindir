package com.cn.thinkx.oms.module.mchnteshop.service;

import java.util.List;

import com.cn.thinkx.oms.module.mchnteshop.model.MchntEshopInf;
import com.github.pagehelper.PageInfo;

public interface MchntEshopInfService {

	public MchntEshopInf getMchntEshopInfById(String eShopId);
	
	public int insertMchntEshopInf(MchntEshopInf mchEshop);
	
	public List<MchntEshopInf> getMchntEshopInfList(MchntEshopInf mchEshop);
	
	public int updateMchntEshopInf(MchntEshopInf mchEshop);
	
	public int deleteMchntEshopInf(String eShopId);
	
	public PageInfo<MchntEshopInf> getMchntEshopInfPage(int startNum, int pageSize, MchntEshopInf entity);
	
	public MchntEshopInf getMchntEshopInfByMchntCode(String mchntCode);
	
}
