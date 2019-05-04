package com.cn.thinkx.wecard.api.module.mchnt.service;

import com.cn.thinkx.wecard.api.module.mchnt.model.ShopInf;

public interface ShopInfService {

	
	/**
	 * 门店CODE 查找门店信息
	 * @param shopCode
	 * @return
	 */
	public ShopInf getShopInfByCode(String shopCode);

}
