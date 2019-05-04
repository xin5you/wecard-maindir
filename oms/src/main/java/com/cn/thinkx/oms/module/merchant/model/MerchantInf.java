package com.cn.thinkx.oms.module.merchant.model;

import com.cn.thinkx.oms.base.model.BaseDomain;

/**
 * 
 * 商户表
 * @author zqy
 *
 */
public class MerchantInf extends BaseDomain {
	
	private String mchntId;
	private String insId;
	private String mchntCode;
	private String mchntName;
	private String mchntType;
	private String dataStat;
	private String accountStat;
	private String industryType1;
	private String industryType2;
	private String industryType3;
	
	private Integer soldCount; //售卡量
	
	private String industryName1; //行业类型
	private String industryName2; //行业类型
	
	private String productCode;// 产品号 
	
	public String getMchntId() {
		return mchntId;
	}
	public String getInsId() {
		return insId;
	}
	public String getMchntCode() {
		return mchntCode;
	}
	public String getMchntName() {
		return mchntName;
	}
	public String getMchntType() {
		return mchntType;
	}
	public String getDataStat() {
		return dataStat;
	}
	public String getAccountStat() {
		return accountStat;
	}
	public String getIndustryType1() {
		return industryType1;
	}
	public String getIndustryType2() {
		return industryType2;
	}
	public String getIndustryType3() {
		return industryType3;
	}
	public Integer getSoldCount() {
		return soldCount;
	}
	public void setMchntId(String mchntId) {
		this.mchntId = mchntId;
	}
	public void setInsId(String insId) {
		this.insId = insId;
	}
	public void setMchntCode(String mchntCode) {
		this.mchntCode = mchntCode;
	}
	public void setMchntName(String mchntName) {
		this.mchntName = mchntName;
	}
	public void setMchntType(String mchntType) {
		this.mchntType = mchntType;
	}
	public void setDataStat(String dataStat) {
		this.dataStat = dataStat;
	}
	public void setAccountStat(String accountStat) {
		this.accountStat = accountStat;
	}
	public void setIndustryType1(String industryType1) {
		this.industryType1 = industryType1;
	}
	public void setIndustryType2(String industryType2) {
		this.industryType2 = industryType2;
	}
	public void setIndustryType3(String industryType3) {
		this.industryType3 = industryType3;
	}
	public void setSoldCount(Integer soldCount) {
		this.soldCount = soldCount;
	}
	public String getIndustryName1() {
		return industryName1;
	}
	public String getIndustryName2() {
		return industryName2;
	}
	public void setIndustryName1(String industryName1) {
		this.industryName1 = industryName1;
	}
	public void setIndustryName2(String industryName2) {
		this.industryName2 = industryName2;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	
}
