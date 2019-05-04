package com.cn.thinkx.biz.mchnt.model;

import com.cn.thinkx.biz.core.model.BaseDomain;

public class MchtCommodities extends BaseDomain {
	private static final long serialVersionUID = 1L;

	private String mchntCode;

	private String productCode;// 产品号
	private String productName; //产品名称
	
	private String commodityCode; // 商品号
	private String commodityName; // 商品名称
	private String commodityAmount; // 商品面额
	private String commodityPrice; // 商品售价
	private String commodityStatus; // 商品有效状态
	private String activeRule;// 使用规则
	private String commodityStocks; //剩余购卡数
	private String activeId; //活动主键


	public String getMchntCode() {
		return mchntCode;
	}

	public void setMchntCode(String mchntCode) {
		this.mchntCode = mchntCode;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getCommodityCode() {
		return commodityCode;
	}

	public void setCommodityCode(String commodityCode) {
		this.commodityCode = commodityCode;
	}

	public String getCommodityName() {
		return commodityName;
	}

	public void setCommodityName(String commodityName) {
		this.commodityName = commodityName;
	}

	public String getCommodityAmount() {
		return commodityAmount;
	}

	public void setCommodityAmount(String commodityAmount) {
		this.commodityAmount = commodityAmount;
	}

	public String getCommodityPrice() {
		return commodityPrice;
	}

	public void setCommodityPrice(String commodityPrice) {
		this.commodityPrice = commodityPrice;
	}

	public String getCommodityStatus() {
		return commodityStatus;
	}

	public void setCommodityStatus(String commodityStatus) {
		this.commodityStatus = commodityStatus;
	}

	public String getActiveRule() {
		return activeRule;
	}

	public void setActiveRule(String activeRule) {
		this.activeRule = activeRule;
	}

	public String getCommodityStocks() {
		return commodityStocks;
	}

	public void setCommodityStocks(String commodityStocks) {
		this.commodityStocks = commodityStocks;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getActiveId() {
		return activeId;
	}

	public void setActiveId(String activeId) {
		this.activeId = activeId;
	}
}
