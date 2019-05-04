package com.cn.thinkx.wecard.api.module.mchnt.mapper;

import org.springframework.stereotype.Repository;

import com.cn.thinkx.wecard.api.module.mchnt.model.ShopInf;

@Repository("shopInfMapper")
public interface ShopInfMapper {

	public ShopInf getShopInfByCode(String shopCode);
}