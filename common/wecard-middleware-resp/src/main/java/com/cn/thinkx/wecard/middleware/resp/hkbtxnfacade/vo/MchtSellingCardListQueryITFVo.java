package com.cn.thinkx.wecard.middleware.resp.hkbtxnfacade.vo;

import com.cn.thinkx.wecard.middleware.resp.domain.entity.BaseDomain;

/**
 * 商户在售卡列表bean
 * @author zqy
 *
 */
public class MchtSellingCardListQueryITFVo extends BaseDomain {

	private String mchntCode;
	private String productCode;// 产品号
	private String commodityCode; // 商品号
	private String commodityName; // 商品名称
	private String commodityAmount; // 商品面额
	private String commodityPrice; // 商品售价
	private String commodityStatus; // 商品有效状态
	private String activeRule;// 使用规则
	
	private String favorablePrice;  //优惠了的价格 
	
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
	public String getFavorablePrice() {
		return favorablePrice;
	}
	public void setFavorablePrice(String favorablePrice) {
		this.favorablePrice = favorablePrice;
	}
}
