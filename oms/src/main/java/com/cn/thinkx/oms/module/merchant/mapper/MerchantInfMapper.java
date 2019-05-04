package com.cn.thinkx.oms.module.merchant.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.cn.thinkx.oms.module.merchant.model.InsInf;
import com.cn.thinkx.oms.module.merchant.model.MerchantInf;

@Repository("merchantInfMapper")
public interface MerchantInfMapper {

	/**
	 * 查找商户信息
	 * @param mId
	 * @return
	 */
	public MerchantInf getMerchantInfById(String mId);
	
	public int insertMerchantInf(MerchantInf entity);
	
	public int updateMerchantInf(MerchantInf entity);
	
	public int deleteMerchantInf(String entityId);
	
	public List<MerchantInf> getMerchantInfList(MerchantInf entity);
	
	/**
	 * 添加机构
	 * @param entity
	 * @return
	 */
	public int InsertInsInf(InsInf entity);
	
	
	/**
	 * 编辑机构
	 * @param entity
	 * @return
	 */
	public int updateInsInf(InsInf entity);
	
	/**
	 * 获取商户下拉选择列表
	 * @return
	 */
	public List<MerchantInf> getMerchantInfListBySelect();
	
	public List<MerchantInf> getMchntProList();
	
	/**
	 * 查找商户信息
	 * @param mId
	 * @return
	 */
	public MerchantInf getMerchantInfByMchntCode(String mchntCode);
}