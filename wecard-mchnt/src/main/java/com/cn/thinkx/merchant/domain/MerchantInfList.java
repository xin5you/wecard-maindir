package com.cn.thinkx.merchant.domain;

import com.cn.thinkx.core.domain.BaseDomain;

/**
 * 
 * 商户信息明细表
 * 
 * 
 * @author 13501
 *
 */
public class MerchantInfList extends BaseDomain {

	private String insId;	//商户信息_id	
	private String mchntId;	//商户_id
	private String mchntType; //企业类型
	private String mchntName; //企业名称
	private String mchntCode; //组织机构代码
	private String busLicence; //营业执照代码
	private String name; //法人姓名
	private String idCardNo;
	private String phoneNumber;//法人手机号码
	private String backName;
	private String backAct;
	private String backActName;
	private String insScale;
	private String brandName;
	private String insCodeCard;
	private String businessLicence;
	private String idCard;
	private String brandLogo;
	private String dataStat;
	private String inviteCode;
	private String inviteCodeStat;
	
	public String getInsId() {
		return insId;
	}
	public void setInsId(String insId) {
		this.insId = insId;
	}
	public String getMchntId() {
		return mchntId;
	}
	public void setMchntId(String mchntId) {
		this.mchntId = mchntId;
	}
	public String getMchntType() {
		return mchntType;
	}
	public void setMchntType(String mchntType) {
		this.mchntType = mchntType;
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
	public String getBusLicence() {
		return busLicence;
	}
	public void setBusLicence(String busLicence) {
		this.busLicence = busLicence;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIdCardNo() {
		return idCardNo;
	}
	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public String getBackName() {
		return backName;
	}
	public void setBackName(String backName) {
		this.backName = backName;
	}
	public String getBackAct() {
		return backAct;
	}
	public void setBackAct(String backAct) {
		this.backAct = backAct;
	}
	public String getBackActName() {
		return backActName;
	}
	public void setBackActName(String backActName) {
		this.backActName = backActName;
	}
	public String getInsScale() {
		return insScale;
	}
	public void setInsScale(String insScale) {
		this.insScale = insScale;
	}
	public String getBrandName() {
		return brandName;
	}
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	public String getInsCodeCard() {
		return insCodeCard;
	}
	public void setInsCodeCard(String insCodeCard) {
		this.insCodeCard = insCodeCard;
	}
	public String getBusinessLicence() {
		return businessLicence;
	}
	public void setBusinessLicence(String businessLicence) {
		this.businessLicence = businessLicence;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public String getBrandLogo() {
		return brandLogo;
	}
	public void setBrandLogo(String brandLogo) {
		this.brandLogo = brandLogo;
	}
	public String getDataStat() {
		return dataStat;
	}
	public void setDataStat(String dataStat) {
		this.dataStat = dataStat;
	}
	public String getInviteCode() {
		return inviteCode;
	}
	public void setInviteCode(String inviteCode) {
		this.inviteCode = inviteCode;
	}
	public String getInviteCodeStat() {
		return inviteCodeStat;
	}
	public void setInviteCodeStat(String inviteCodeStat) {
		this.inviteCodeStat = inviteCodeStat;
	}
}
