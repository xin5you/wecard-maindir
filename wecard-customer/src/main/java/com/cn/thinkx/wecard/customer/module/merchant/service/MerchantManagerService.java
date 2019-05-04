package com.cn.thinkx.wecard.customer.module.merchant.service;

import java.util.List;

import com.cn.thinkx.common.wecard.domain.merchant.MerchantManager;

public interface MerchantManagerService {

	public MerchantManager getMerchantRoleTypeById(String openId);
	
	/**
	 * 通过openId 查询商户管理员
	 * @param openId
	 * @return
	 */
	public MerchantManager getMerchantManagerByOpenId(String openId);
	
	/**
	 * 通过openId 查询商户所属的商户，机构等信息
	 * @param openId
	 * @return
	 */
	public MerchantManager getMerchantInsInfByOpenId(String openId);
	
	public MerchantManager getMerchantManagerById(String mId);
	
	public List<MerchantManager> getMerchantManagerList(MerchantManager entity);
	

	/**
	 * 查找某个商户，门店下的 管理员角色列表
	 * @param mchntCode
	 * @param shopCode
	 * @param roleType
	 * @return
	 */
	public List<MerchantManager> getMerchantManagerByRoleType(String mchntCode,String shopCode,String roleType);
}
