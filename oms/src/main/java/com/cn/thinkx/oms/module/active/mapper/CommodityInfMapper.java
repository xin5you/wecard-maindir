package com.cn.thinkx.oms.module.active.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cn.thinkx.oms.module.active.model.CommodityInf;

@Repository("commodityInfMapper")
public interface CommodityInfMapper {

	CommodityInf getCommodityInfById(String commodityInfId);

	int insertCommodityInf(CommodityInf entity);

	int updateCommodityInf(CommodityInf entity);

	List<CommodityInf> getCommodityInfList(CommodityInf entity);
	
	List<CommodityInf> getCommoListByMchntId(String mchntId);

	int deleteCommodityInf(String commodityInfId);

}