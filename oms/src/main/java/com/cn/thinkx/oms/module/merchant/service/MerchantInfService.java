package com.cn.thinkx.oms.module.merchant.service;

import java.util.List;
import java.util.Map;

import com.cn.thinkx.oms.module.merchant.model.MerchantInf;
import com.cn.thinkx.oms.module.merchant.model.MerchantInfList;
import com.cn.thinkx.oms.module.merchant.model.ShopInf;
import com.github.pagehelper.PageInfo;

public interface MerchantInfService {


	/**
	 * 查找商户信息
	 * @param merchantId
	 * @return
	 */
	public MerchantInf getMerchantInfById(String mchntId);
	
	
	/**
	 * 保存商户信息
	 * @param merchantId
	 * @return
	 */
	public Map<String,Object> insertMerchantInf(MerchantInf merchantInf,MerchantInfList merchantInfList,ShopInf shopInf);
	
	/**
	 * 修改商户信息
	 * @param merchantId
	 * @return
	 */
	public int updateMerchantInf(MerchantInf entity,MerchantInfList merchantInfList);
	
	
	/**
	 * 删除商户信息
	 * @param merchantId
	 * @return
	 */
	public int deleteMerchantInf(String entityId); 
	
	/**
	 * 查询所有商户
	 * @param MerchantInf
	 * @return
	 */
	public List<MerchantInf> getMerchantInfList(MerchantInf entity);
	
	/**
	 * 商户列表
	 * @param startNum
	 * @param pageSize
	 * @param user
	 * @return
	 */
    public PageInfo<MerchantInf> getMerchantInfPage(int startNum, int pageSize, MerchantInf entity);
    
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
