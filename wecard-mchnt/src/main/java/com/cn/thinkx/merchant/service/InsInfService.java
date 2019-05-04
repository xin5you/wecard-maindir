package com.cn.thinkx.merchant.service;

import com.cn.thinkx.merchant.domain.InsInf;

public interface InsInfService {

	/**
	 * 根据商户id查询商户的机构、产品信息
	 * @param mchntId
	 * @return
	 */
	public InsInf getInsInfByMchntId(String mchntId);
}
