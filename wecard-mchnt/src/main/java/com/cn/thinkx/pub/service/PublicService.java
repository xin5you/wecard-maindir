package com.cn.thinkx.pub.service;

import com.cn.thinkx.pub.domain.DetailBizInfo;

public interface PublicService {

	/**
	 * 获取主键
	 * 
	 * @return
	 */
	public String getPrimaryKey();

	/**
	 * 获取商户业务详细信息（商户号、商户名称、门店号、门店名称、机构号、openid等）
	 * 
	 * @param detail
	 * @return
	 */
	public DetailBizInfo getDetailBizInfo(DetailBizInfo detail);


}
