package com.cn.thinkx.common.wecard.domain.cardkeys;

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
	 * 产品单位
	 */
	private String productUnit;
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
	 * 是否上下架
	 */
	private String isPutaway;
	/*
	 * 状态
	 */
	private String dataStat;
	
	//福利集市(损失金额)
	private String loseAmount;
	
	//用户Id
	private String userId;
	//张数
	private String num;
	//未使用额度
	private String unusedAmount;
	//预计收入金额
	private String gainAmount;
	//手机号
	private String mobile;
	//卡产品类型名称
	private String productTypeCode;
	
	public String getUnusedAmount() {
		return unusedAmount;
	}
	public void setUnusedAmount(String unusedAmount) {
		this.unusedAmount = unusedAmount;
	}
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
	public String getLoseAmount() {
		return loseAmount;
	}
	public void setLoseAmount(String loseAmount) {
		this.loseAmount = loseAmount;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getNum() {
		return num;
	}
	public void setNum(String num) {
		this.num = num;
	}
	public String getGainAmount() {
		return gainAmount;
	}
	public void setGainAmount(String gainAmount) {
		this.gainAmount = gainAmount;
	}
	public String getProductUnit() {
		return productUnit;
	}
	public void setProductUnit(String productUnit) {
		this.productUnit = productUnit;
	}
	public String getIsPutaway() {
		return isPutaway;
	}
	public void setIsPutaway(String isPutaway) {
		this.isPutaway = isPutaway;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getProductTypeCode() {
		return productTypeCode;
	}
	public void setProductTypeCode(String productTypeCode) {
		this.productTypeCode = productTypeCode;
	}
	
}
