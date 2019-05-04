package com.cn.thinkx.oms.module.active.model;

import java.util.List;

import com.cn.thinkx.oms.base.model.BaseDomain;

/**
 * 商品基础类
 * 
 * @author pucker
 *
 */
public class CommodityInf extends BaseDomain {
	/**
	 * 商品ID
	 */
	private String commodityId;
	/**
	 * 商户ID
	 */
	private String merchantId;
	/**
	 * 产品号
	 */
	private String productCode;
	/**
	 * 商品名称
	 */
	private String commodityName;
	/**
	 * 商品面额
	 */
	private String commodityFacevalue;
	/**
	 * 商品成本
	 */
	private String commodityCost;
	/**
	 * 商品有效状态
	 */
	private String dataStat;

	/** 查询相关参数 */
	private String mchntName;
	private String mchntCode;

	/**
	 * 售价
	 */
	private String sellingPrice;
	/**
	 * 库存
	 */
	private String stock;
	/**
	 * 活动明细ID
	 */
	private String activeListId;
	/**
	 * 根据商户ID获取的所有商品列表
	 */
	private List<CommodityInf> commList;

	public String getCommodityId() {
		return commodityId;
	}

	public void setCommodityId(String commodityId) {
		this.commodityId = commodityId;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getCommodityName() {
		return commodityName;
	}

	public void setCommodityName(String commodityName) {
		this.commodityName = commodityName;
	}

	public String getCommodityFacevalue() {
		return commodityFacevalue;
	}

	public void setCommodityFacevalue(String commodityFacevalue) {
		this.commodityFacevalue = commodityFacevalue;
	}

	public String getCommodityCost() {
		return commodityCost;
	}

	public void setCommodityCost(String commodityCost) {
		this.commodityCost = commodityCost;
	}

	public String getDataStat() {
		return dataStat;
	}

	public void setDataStat(String dataStat) {
		this.dataStat = dataStat;
	}

	public String getMchntName() {
		return mchntName;
	}

	public void setMchntName(String mchntName) {
		this.mchntName = mchntName;
	}

	public String getMchntCode() {
		return mchntCode;
	}

	public void setMchntCode(String mchntCode) {
		this.mchntCode = mchntCode;
	}

	public String getSellingPrice() {
		return sellingPrice;
	}

	public void setSellingPrice(String sellingPrice) {
		this.sellingPrice = sellingPrice;
	}

	public String getStock() {
		return stock;
	}

	public void setStock(String stock) {
		this.stock = stock;
	}

	public String getActiveListId() {
		return activeListId;
	}

	public void setActiveListId(String activeListId) {
		this.activeListId = activeListId;
	}

	public List<CommodityInf> getCommList() {
		return commList;
	}

	public void setCommList(List<CommodityInf> commList) {
		this.commList = commList;
	}

}
