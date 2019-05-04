package com.cn.thinkx.wxclient.mapper;

import com.cn.thinkx.wxclient.domain.MerchantShareInf;

public interface MerchantShareInfDao {

	/**
	 * 查找商户信息
	 * @param mId
	 * @return
	 */
	public MerchantShareInf getMerchantShareInfById(String shareId);
	
	public int insertMerchantShareInf(MerchantShareInf entity); 
	
	public int updateShareInfDateStat(String shareId);
	
	public int updateMerchantShareInf(MerchantShareInf entity);
}