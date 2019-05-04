package com.cn.thinkx.common.wecard.domain.merchant;

import com.cn.thinkx.common.wecard.domain.base.BaseDomain;

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
	
	private String phoneNumber; //手机号码
	private String insCode; //机构code
	
	public String getMchntId() {
		return mchntId;
	}
	public void setMchntId(String mchntId) {
		this.mchntId = mchntId;
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
	public String getInsId() {
		return insId;
	}
	public void setInsId(String insId) {
		this.insId = insId;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getAccountStat() {
		return accountStat;
	}
	public void setAccountStat(String accountStat) {
		this.accountStat = accountStat;
	}
	public String getInsCode() {
		return insCode;
	}
	public void setInsCode(String insCode) {
		this.insCode = insCode;
	}
	
}
