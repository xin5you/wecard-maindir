package com.cn.thinkx.biz.mchnt.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.cn.thinkx.biz.mchnt.model.CardTransInf;
import com.cn.thinkx.biz.mchnt.model.MchtCommodities;
import com.cn.thinkx.biz.mchnt.model.MerchantInf;
import com.cn.thinkx.biz.mchnt.model.MerchantManager;
import com.cn.thinkx.biz.mchnt.model.ShopDetailInf;
import com.cn.thinkx.biz.mchnt.model.ShopInf;
import com.cn.thinkx.biz.mchnt.model.ShopListInf;
import com.cn.thinkx.biz.user.model.UserMerchantAcct;


@Repository("bizMchtMapper")
public interface BizMchtMapper {

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
	 * 获取当前活动商品的信息
	 * 
	 * @param commodityId 商品ID
	 * @return
	 */
	MchtCommodities getSellingCardStocksByCommodityId(String commodityId);

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
	 * 获取门店列表信息
	 * 
	 * @param shop
	 * @return
	 */
	List<ShopListInf> getShopListInfo(ShopInf shop);
	
	/**
	 * 获取会员卡交易明细
	 * 
	 * @param shop
	 * @return
	 */
	List<CardTransInf> getCardTransDetailList(UserMerchantAcct shop);
	
	/**
	 * 获取产品名称
	 * @param productCode
	 * @return
	 */
	String getProductNameByCode(String productCode);
	
	/**
	 * 查找某个商户，门店下的 管理员角色列表
	 * @param mchntCode
	 * @param shopCode
	 * @param roleType
	 * @return
	 */
	public List<MerchantManager> getMerchantManagerByRoleType(@Param("mchntCode")String mchntCode,@Param("shopCode")String shopCode,@Param("roleType")String roleType);

}
