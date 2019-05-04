package com.cn.thinkx.merchant.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cn.thinkx.merchant.domain.MerchantManager;

public interface MerchantManagerDao {

	/**
	 * 提供非保密数据查询 可以传输到页面的字段
	 * @param mId
	 * @return
	 */
	public MerchantManager getMerchantRoleTypeById(String mId);
	
	public MerchantManager getMerchantManagerByOpenId(String openId);
	
	/**
	 * 通过openId 查询商户所属的商户，机构等信息 
	 * @param openId
	 * @return
	 */
	public MerchantManager getMerchantInsInfByOpenId(String openId);
	
	public MerchantManager getMerchantManagerById(String mId);
	
	public int insertMerchantManager(MerchantManager entity);
	
	public int updateMerchantManager(MerchantManager entity);
	
	public int deleteMerchantManager(String mId);
	
	public List<MerchantManager> getMerchantManagerList(MerchantManager entity);
	
	/**
	 * 通过手机号查找商户管理员信息
	 * @param phoneNumber
	 * @return
	 */
	public MerchantManager getMchntManagerByPhoneNumber(String phoneNumber);
	
	public MerchantManager getMchntManagerTmpByPhoneNumber(String phoneNumber);
	
	/**
	 * 删除商户管理 临时信息
	 * @param mchntManagerTmpId
	 * @return
	 */
	public int deleteMchntManagerTmpById(String mchntManagerTmpId);
	
	/**
	 * 查找某个商户，门店下的 管理员角色列表
	 * @param mchntCode
	 * @param shopCode
	 * @param roleType
	 * @return
	 */
	public List<MerchantManager> getMerchantManagerByRoleType(@Param("mchntCode")String mchntCode,@Param("shopCode")String shopCode,@Param("roleType")String roleType);
	
}