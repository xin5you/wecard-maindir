package com.cn.thinkx.oms.module.margin.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cn.thinkx.oms.module.margin.model.MerchantMarginList;

@Repository("merchantMarginListMapper")
public interface MerchantMarginListMapper {

	
	public MerchantMarginList getMerchantMarginListById(String id);
	
	public int insertMerchantMarginList(MerchantMarginList entity);
	
	public int updateMerchantMarginList(MerchantMarginList entity);
	
	public List<MerchantMarginList> getMerchantMarginList(MerchantMarginList entity);
	
	public int deleteMerchantMarginList(String id);
	
	public List<MerchantMarginList> getMerchantMarginApproveList(MerchantMarginList entity);
}