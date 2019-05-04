package com.cn.iboot.diy.api.system.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.cn.iboot.diy.api.base.mapper.BaseDao;
import com.cn.iboot.diy.api.system.domain.DataAuth;

@Mapper
public interface DataAuthMapper extends BaseDao<DataAuth> {

	/**
	 * 根据门店号查询商户号
	 * 
	 * @param shopCode
	 * @return
	 */
	String getMchntCodeByShopCode(String shopCode);
	
	/**
	 * 通过用户id查看对应的商户信息
	 * 
	 * @param userId
	 * @return
	 */
	DataAuth selectDataAuthByUserId(String userId);
	
}
