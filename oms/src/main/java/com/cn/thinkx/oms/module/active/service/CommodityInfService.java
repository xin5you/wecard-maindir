package com.cn.thinkx.oms.module.active.service;

import java.util.List;

import com.cn.thinkx.oms.module.active.model.CommodityInf;
import com.github.pagehelper.PageInfo;

public interface CommodityInfService {

	String addCommodityInf(CommodityInf commodity);

	int updateCommodityInf(CommodityInf commodity);

	CommodityInf getCommodityInfById(String id);
	
	List<CommodityInf> getCommoListByMchntId(String mchntId);

	List<CommodityInf> findCommodityInfList(CommodityInf commodityInf);

	int deleteCommodityInf(String commId);

	/**
	 * 商品列表
	 * 
	 * @param startNum
	 * @param pageSize
	 * @param user
	 * @return
	 */
	PageInfo<CommodityInf> getCommodityInfPage(int startNum, int pageSize, CommodityInf entity);

}
