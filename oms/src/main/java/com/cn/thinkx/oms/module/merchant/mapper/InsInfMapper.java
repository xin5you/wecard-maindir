package com.cn.thinkx.oms.module.merchant.mapper;

import org.springframework.stereotype.Repository;

import com.cn.thinkx.oms.module.merchant.model.InsInf;

@Repository("insInfMapper")
public interface InsInfMapper {
	
	public InsInf getInsInfById(String InsInfId);
	
	public int insertInsInf(InsInf entity);
	
	public int updateInsInf(InsInf entity);

	
}