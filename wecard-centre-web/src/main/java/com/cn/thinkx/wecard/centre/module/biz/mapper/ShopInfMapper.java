package com.cn.thinkx.wecard.centre.module.biz.mapper;

import java.util.List;

import com.cn.thinkx.common.redis.vo.ShopInfVO;

public interface ShopInfMapper {

	/*
	 * 查询门店信息以及对应的商户和机构名称
	 */
	List<ShopInfVO> getShopInfList();
}
