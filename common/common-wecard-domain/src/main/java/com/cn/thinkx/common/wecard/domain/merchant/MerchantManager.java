package com.cn.thinkx.common.wecard.domain.merchant;

import java.io.UnsupportedEncodingException;

import com.cn.thinkx.common.wecard.domain.base.BaseDomain;

/**
 * 门店管理员表
 * 
 * @author 13501
 *
 */
public class MerchantManager extends BaseDomain {
	
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

	/** 关联字段 **/
	private String roleName;

	/**
	 * 微信昵称
	 */
	private byte[] nickname;
	private String nicknameStr;
	
	/**所属机构code**/
	private String insCode;
	private String insId;
	private String mchntCode;
	private String shopCode;
	private String mchntName;
	private String shopName;

	public String getMangerId() {
		return mangerId;
	}

	public void setMangerId(String mangerId) {
		this.mangerId = mangerId;
	}

	public String getMangerName() {
		return mangerName;
	}

	public void setMangerName(String mangerName) {
		this.mangerName = mangerName;
	}

	public String getMchntId() {
		return mchntId;
	}

	public void setMchntId(String mchntId) {
		this.mchntId = mchntId;
	}

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public String getRoleType() {
		return roleType;
	}

	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}

	public String getCashPasswd() {
		return cashPasswd;
	}

	public void setCashPasswd(String cashPasswd) {
		this.cashPasswd = cashPasswd;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getDataStat() {
		return dataStat;
	}

	public void setDataStat(String dataStat) {
		this.dataStat = dataStat;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public byte[] getNickname() {
		return nickname;
	}

	public void setNickname(byte[] nickname) {
		this.nickname = nickname;
	}

	public String getNicknameStr() {
		try {
			return new String(nickname,"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return nicknameStr;
	}

	public void setNicknameStr(String nicknameStr) {
		this.nicknameStr = nicknameStr;
	}

	public String getInsCode() {
		return insCode;
	}

	public void setInsCode(String insCode) {
		this.insCode = insCode;
	}
	
	public String getInsId() {
		return insId;
	}

	public void setInsId(String insId) {
		this.insId = insId;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getMchntCode() {
		return mchntCode;
	}

	public void setMchntCode(String mchntCode) {
		this.mchntCode = mchntCode;
	}

	public String getShopCode() {
		return shopCode;
	}
	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}

	public String getMchntName() {
		return mchntName;
	}

	public String getShopName() {
		return shopName;
	}
	public void setMchntName(String mchntName) {
		this.mchntName = mchntName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
}
