package com.cn.thinkx.oms.module.mchnteshop.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cn.thinkx.oms.module.mchnteshop.mapper.MchntEshopInfMapper;
import com.cn.thinkx.oms.module.mchnteshop.model.MchntEshopInf;
import com.cn.thinkx.oms.module.mchnteshop.service.MchntEshopInfService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service("mchntEshopInfService")
public class MchntEshopInfServiceImpl implements MchntEshopInfService {
	
	@Autowired
	@Qualifier("mchntEshopInfMapper")
	private MchntEshopInfMapper mchntEshopInfMapper;

	@Override
	public MchntEshopInf getMchntEshopInfById(String eShopId) {
		return mchntEshopInfMapper.getMchntEshopInfById(eShopId);
	}

	@Override
	public int insertMchntEshopInf(MchntEshopInf mchEshop) {
		return mchntEshopInfMapper.insertMchntEshopInf(mchEshop);
	}

	@Override
	public List<MchntEshopInf> getMchntEshopInfList(MchntEshopInf mchEshop) {
		return mchntEshopInfMapper.getMchntEshopInfList(mchEshop);
	}

	@Override
	public int updateMchntEshopInf(MchntEshopInf mchEshop) {
		return mchntEshopInfMapper.updateMchntEshopInf(mchEshop);
	}

	@Override
	public int deleteMchntEshopInf(String eShopId) {
		return mchntEshopInfMapper.deleteMchntEshopInf(eShopId);
	}

	@Override
	public PageInfo<MchntEshopInf> getMchntEshopInfPage(int startNum, int pageSize, MchntEshopInf entity) {
		PageHelper.startPage(startNum, pageSize);
		List<MchntEshopInf> list = getMchntEshopInfList(entity);
		PageInfo<MchntEshopInf> page = new PageInfo<MchntEshopInf>(list);
		return page;
	}

	@Override
	public MchntEshopInf getMchntEshopInfByMchntCode(String mchntCode) {
		return mchntEshopInfMapper.getMchntEshopInfByMchntCode(mchntCode);
	}

}
