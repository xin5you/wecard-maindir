package com.cn.thinkx.oms.module.merchant.model;

import com.cn.thinkx.oms.base.model.BaseDomain;

/**
 * 门店管理员表
 * 
 * @author 13501
 *
 */
public class MerchantManagerTmp extends BaseDomain {
	private static final long serialVersionUID = 1L;
	private String mangerId;
	private String mangerName;
	private String mchntId;
	private String shopId;
	private String roleType; // 100 boss 200 财务 300 店长 400收银员 500服务员
	private String cashPasswd;
	private String name;
	private String phoneNumber;
	private String dataStat;
	private String remarks;
	/** 关联字段 **/
	private String roleName;
	/** 所属机构code **/
	private String mchntName;
	private String shopName;

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getMangerId() {
		return mangerId;
	}

	public String getMangerName() {
		return mangerName;
	}

	public String getMchntId() {
		return mchntId;
	}

	public String getShopId() {
		return shopId;
	}

	public String getRoleType() {
		return roleType;
	}

	public String getCashPasswd() {
		return cashPasswd;
	}

	public String getName() {
		return name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getDataStat() {
		return dataStat;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public String getRoleName() {
		return roleName;
	}

	public String getMchntName() {
		return mchntName;
	}

	public String getShopName() {
		return shopName;
	}

	public void setMangerId(String mangerId) {
		this.mangerId = mangerId;
	}

	public void setMangerName(String mangerName) {
		this.mangerName = mangerName;
	}

	public void setMchntId(String mchntId) {
		this.mchntId = mchntId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	public void setCashPasswd(String cashPasswd) {
		this.cashPasswd = cashPasswd;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setDataStat(String dataStat) {
		this.dataStat = dataStat;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public void setMchntName(String mchntName) {
		this.mchntName = mchntName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

}
