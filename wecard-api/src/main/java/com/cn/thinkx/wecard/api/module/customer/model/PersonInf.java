package com.cn.thinkx.wecard.api.module.customer.model;

import com.cn.thinkx.wecard.api.module.core.domain.BaseDomain;

public class PersonInf extends BaseDomain {
	
	private String personalId;		//
	private String userId;			// userInf.userId
	private String personalName;	//	姓名
	private String personalCardType;//	证件类型
	private String personalCardNo;	//	证件号码
	private String sex;				//	性别
	private String birthday;		//	生日
	private String nativePlace;		//籍贯
	private String mobilePhoneNo;	//手机号码
	private String email;			//邮箱
	private String marriageStat;	//婚姻状况
	private String homeAddress;   	//家庭住址
	private String companyAddress;	//公司地址
	private String transHabit; 		//消费习惯 100 现金 200 银行卡 300 微信 310 支付宝 900 其他
	
	
	public String getPersonalId() {
		return personalId;
	}
	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPersonalName() {
		return personalName;
	}
	public void setPersonalName(String personalName) {
		this.personalName = personalName;
	}
	public String getPersonalCardType() {
		return personalCardType;
	}
	public void setPersonalCardType(String personalCardType) {
		this.personalCardType = personalCardType;
	}
	public String getPersonalCardNo() {
		return personalCardNo;
	}
	public void setPersonalCardNo(String personalCardNo) {
		this.personalCardNo = personalCardNo;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getBirthday() {
		return birthday;
	}
	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}
	public String getNativePlace() {
		return nativePlace;
	}
	public void setNativePlace(String nativePlace) {
		this.nativePlace = nativePlace;
	}
	public String getMobilePhoneNo() {
		return mobilePhoneNo;
	}
	public void setMobilePhoneNo(String mobilePhoneNo) {
		this.mobilePhoneNo = mobilePhoneNo;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMarriageStat() {
		return marriageStat;
	}
	public void setMarriageStat(String marriageStat) {
		this.marriageStat = marriageStat;
	}
	public String getHomeAddress() {
		return homeAddress;
	}
	public void setHomeAddress(String homeAddress) {
		this.homeAddress = homeAddress;
	}
	public String getCompanyAddress() {
		return companyAddress;
	}
	public void setCompanyAddress(String companyAddress) {
		this.companyAddress = companyAddress;
	}
	public String getTransHabit() {
		return transHabit;
	}
	public void setTransHabit(String transHabit) {
		this.transHabit = transHabit;
	}
	
}
