package com.cn.thinkx.oms.module.active.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cn.thinkx.oms.module.active.model.ActiveList;
import com.cn.thinkx.oms.module.active.model.CommodityInf;
import com.cn.thinkx.oms.module.active.model.MerchantActiveInf;

@Repository("activityMapper")
public interface ActivityMapper {

	MerchantActiveInf getMerchantActiveInfById(String id);
	
	ActiveList getActiveListById(String id);
	
	List<CommodityInf> getCommoListByActiveId(String activeId);
	
	int insertMerchantActiveInf(MerchantActiveInf entity);
	
	int insertActiveList(ActiveList entity);

	int updateMerchantActiveInf(MerchantActiveInf entity);
	
	int updateActiveList(ActiveList entity);

	List<MerchantActiveInf> getMerchantActiveInfList(MerchantActiveInf entity);

	int deleteMerchantActiveInf(MerchantActiveInf entity);
	
	int deleteActiveList(ActiveList entity);

}