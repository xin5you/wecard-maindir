package com.cn.thinkx.biz.mchnt.service;

import java.util.List;

import com.cn.thinkx.biz.mchnt.model.CardTransInf;
import com.cn.thinkx.biz.mchnt.model.MchtCommodities;
import com.cn.thinkx.biz.mchnt.model.MerchantInf;
import com.cn.thinkx.biz.mchnt.model.ShopDetailInf;
import com.cn.thinkx.biz.mchnt.model.ShopInf;
import com.cn.thinkx.biz.mchnt.model.ShopListInf;
import com.cn.thinkx.biz.user.model.UserMerchantAcct;
import com.github.pagehelper.PageInfo;

public interface BizMchtService {

	/**
	 * 根据商户号获取商户商户信息
	 * 
	 * @param mchntCode
	 * @return
	 */
	MerchantInf getMerchantInfByCode(String mchntCode);

	/**
	 * 根据商户号获取产品号
	 * 
	 * @param mchntCode
	 * @return
	 */
	String getProCodeByMchntCode(String mchntCode);

	/**
	 * 获取当前商户在售卡列表
	 * 
	 * @param mchtCode
	 * @return
	 */
	List<MchtCommodities> getSellingCardList(String mchtCode);

	/**
	 * 根据商户号和商品号得到商户在售卡
	 * 
	 * @param mc
	 * @return
	 */
	MchtCommodities getSellingCard(MchtCommodities mc);

	/**
	 * 获取商户门店信息(不含明细)
	 * 
	 * @param shop
	 * @return
	 */
	ShopDetailInf getShopSimpleInfo(ShopInf shop);

	/**
	 * 获取商户门店信息(含明细)
	 * 
	 * @param shop
	 * @return
	 */
	ShopDetailInf getShopDetailInfo(ShopInf shop);

	/**
	 * 获取商户门店列表(分页查询)
	 * 
	 * @param startNum
	 * @param pageSize
	 * @param shop
	 * @return
	 */
	PageInfo<ShopListInf> getShopListInfoPage(int startNum, int pageSize, ShopInf shop);
	
	/**
	 * 获取会员卡交易明细(分页查询)
	 * 
	 * @param startNum
	 * @param pageSize
	 * @param acc
	 * @return
	 */
	PageInfo<CardTransInf> getCardTransDetailListPage(int startNum, int pageSize, UserMerchantAcct acc);

}
