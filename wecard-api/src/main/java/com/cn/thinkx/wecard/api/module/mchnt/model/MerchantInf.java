package com.cn.thinkx.wecard.api.module.mchnt.model;

import com.cn.thinkx.wecard.api.module.core.domain.BaseDomain;

/**
 * 商户表
 * 
 * @author xiaomei
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
	
	public String getMchntId() {
		return mchntId;
	}
	public void setMchntId(String mchntId) {
		this.mchntId = mchntId;
	}
	public String getInsId() {
		return insId;
	}
	public void setInsId(String insId) {
		this.insId = insId;
	}
	public String getMchntCode() {
		return mchntCode;
	}
	public void setMchntCode(String mchntCode) {
		this.mchntCode = mchntCode;
	}
	public String getMchntName() {
		return mchntName;
	}
	public void setMchntName(String mchntName) {
		this.mchntName = mchntName;
	}
	public String getMchntType() {
		return mchntType;
	}
	public void setMchntType(String mchntType) {
		this.mchntType = mchntType;
	}
	public String getDataStat() {
		return dataStat;
	}
	public void setDataStat(String dataStat) {
		this.dataStat = dataStat;
	}
	public String getAccountStat() {
		return accountStat;
	}
	public void setAccountStat(String accountStat) {
		this.accountStat = accountStat;
	}
	
	@Override
	public String toString() {
		return "MerchantInf [mchntId=" + mchntId + ", insId=" + insId + ", mchntCode=" + mchntCode + ", mchntName="
				+ mchntName + ", mchntType=" + mchntType + ", dataStat=" + dataStat + ", accountStat=" + accountStat
				+ "]";
	}

}
