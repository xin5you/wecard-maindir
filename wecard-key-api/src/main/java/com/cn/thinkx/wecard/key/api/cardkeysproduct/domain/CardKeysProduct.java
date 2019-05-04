package com.cn.thinkx.wecard.key.api.cardkeysproduct.domain;

import com.cn.thinkx.pms.base.domain.BaseDomain;

/**
 * 卡密产品信息
 * 
 * @author xiaomei
 *
 */
public class CardKeysProduct extends BaseDomain {

	/*
	 * 产品号
	 */
	private String productCode;
	/*
	 * 产品名称
	 */
	private String productName;
	/*
	 * 产品类型
	 */
	private String productType;
	/*
	 * 面额
	 */
	private String orgAmount;
	/*
	 * 金额
	 */
	private String amount;
	/*
	 * 已发总数
	 */
	private String totalNum;
	/*
	 * 可购数量
	 */
	private String availableNum;
	/*
	 * 供应商
	 */
	private String supplier;
	/*
	 * LOGO
	 */
	private String logoUrl;
	/*
	 * 产品描述
	 */
	private String productDesc;
	
	/*
	 * 状态
	 */
	private String dataStat;
	
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getOrgAmount() {
		return orgAmount;
	}
	public void setOrgAmount(String orgAmount) {
		this.orgAmount = orgAmount;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(String totalNum) {
		this.totalNum = totalNum;
	}
	public String getAvailableNum() {
		return availableNum;
	}
	public void setAvailableNum(String availableNum) {
		this.availableNum = availableNum;
	}
	public String getSupplier() {
		return supplier;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	public String getLogoUrl() {
		return logoUrl;
	}
	public void setLogoUrl(String logoUrl) {
		this.logoUrl = logoUrl;
	}
	public String getProductDesc() {
		return productDesc;
	}
	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}
	public String getDataStat() {
		return dataStat;
	}
	public void setDataStat(String dataStat) {
		this.dataStat = dataStat;
	}
	
}
