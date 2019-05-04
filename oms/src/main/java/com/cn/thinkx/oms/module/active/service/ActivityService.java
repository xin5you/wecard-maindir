package com.cn.thinkx.oms.module.active.service;

import java.util.List;

import com.cn.thinkx.oms.module.active.model.ActiveList;
import com.cn.thinkx.oms.module.active.model.CommodityInf;
import com.cn.thinkx.oms.module.active.model.MerchantActiveInf;
import com.github.pagehelper.PageInfo;

public interface ActivityService {

	String addMerchantActiveInf(MerchantActiveInf entity);
	
	String addActiveList(ActiveList entity);

	int updateMerchantActiveInf(MerchantActiveInf entity);
	
	int updateActiveList(ActiveList entity);

	MerchantActiveInf getMerchantActiveInfById(String id);
	
	ActiveList getActiveListById(String id);
	
	List<CommodityInf> getCommoListByActiveId(String activeId);
	
	int deleteMerchantActiveInf(MerchantActiveInf entity);
	
	int deleteActiveList(ActiveList entity);

	PageInfo<MerchantActiveInf> getMerchantActiveInfPage(int startNum, int pageSize, MerchantActiveInf entity);

}
