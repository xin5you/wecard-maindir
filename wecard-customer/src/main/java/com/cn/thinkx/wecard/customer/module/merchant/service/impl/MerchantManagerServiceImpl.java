package com.cn.thinkx.wecard.customer.module.merchant.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.thinkx.common.wecard.domain.merchant.MerchantManager;
import com.cn.thinkx.common.wecard.module.merchant.mapper.MerchantManagerDao;
import com.cn.thinkx.wecard.customer.module.merchant.service.MerchantManagerService;

@Service("merchantManagerService")
public class MerchantManagerServiceImpl implements MerchantManagerService {

	
	@Autowired
	private  MerchantManagerDao merchantManagerDao;
	
	public MerchantManager getMerchantRoleTypeById(String openId){
		return merchantManagerDao.getMerchantRoleTypeById(openId);
	}

	public MerchantManager getMerchantManagerById(String mId) {
		return merchantManagerDao.getMerchantManagerById(mId);
	}
	
	public List<MerchantManager> getMerchantManagerList(MerchantManager entity){
		return merchantManagerDao.getMerchantManagerList(entity);
	}

	public MerchantManager getMerchantManagerByOpenId(String openId) {
		return merchantManagerDao.getMerchantManagerByOpenId(openId);
	}
	
	/**
	 * 通过openId 查询商户所属的商户，机构等信息
	 * @param openId
	 * @return
	 */
	public MerchantManager getMerchantInsInfByOpenId(String openId){
		return merchantManagerDao.getMerchantInsInfByOpenId(openId);
	}
	
	/**
	 * 查找某个商户，门店下的 管理员角色列表
	 * @param mchntCode
	 * @param shopCode
	 * @param roleType
	 * @return
	 */
	public List<MerchantManager> getMerchantManagerByRoleType(String mchntCode,String shopCode,String roleType){
		return merchantManagerDao.getMerchantManagerByRoleType(mchntCode, shopCode, roleType);
	}
}
