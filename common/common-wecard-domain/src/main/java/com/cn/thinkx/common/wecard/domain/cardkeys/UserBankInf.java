package com.cn.thinkx.common.wecard.domain.cardkeys;

import com.cn.thinkx.pms.base.domain.BaseDomain;

/**
 * 用户银行卡信息
 * 
 * @author xiaomei
 *
 */
public class UserBankInf extends BaseDomain {
	
	/**
	 * 主键
	 */
	private String id;

	/**
	 * 银行卡号
	 */
	private String bankNo;
	
	/**
	 * 用户人ID
	 */
	private String userId;
	
	/**
	 * 用户名称
	 */
	private String userName;
	
	/**
	 * 银行卡类型
	 */
	private String bankType;
	
	/**
	 * 开户行
	 */
	private String accountBank;
	
	/**
	 * 开户支行
	 */
	private String accountBranch;
	
	/**
	 * 开户行所在地
	 */
	private String accountBankAddr;
	
	/**
	 * 是否默认
	 */
	private String isdefault;
	
	/**
	 * 状态
	 */
	private String dataStat;
	
	//隐藏卡号中间数字
	private String bankNum;
	
	private String bankName;	//银行卡名称
	private String bankTypeName;	//银行卡类型名称
	private String check;	//是否已存在相同记录
	
	private String logo;	//银行logo

	public String getBankNo() {
		return bankNo;
	}

	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getBankType() {
		return bankType;
	}

	public void setBankType(String bankType) {
		this.bankType = bankType;
	}

	public String getAccountBank() {
		return accountBank;
	}

	public void setAccountBank(String accountBank) {
		this.accountBank = accountBank;
	}

	public String getAccountBranch() {
		return accountBranch;
	}

	public void setAccountBranch(String accountBranch) {
		this.accountBranch = accountBranch;
	}

	public String getAccountBankAddr() {
		return accountBankAddr;
	}

	public void setAccountBankAddr(String accountBankAddr) {
		this.accountBankAddr = accountBankAddr;
	}

	public String getDataStat() {
		return dataStat;
	}

	public void setDataStat(String dataStat) {
		this.dataStat = dataStat;
	}

	public String getIsdefault() {
		return isdefault;
	}

	public void setIsdefault(String isdefault) {
		this.isdefault = isdefault;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankTypeName() {
		return bankTypeName;
	}

	public void setBankTypeName(String bankTypeName) {
		this.bankTypeName = bankTypeName;
	}

	public String getCheck() {
		return check;
	}

	public void setCheck(String check) {
		this.check = check;
	}

	public String getBankNum() {
		return bankNum;
	}

	public void setBankNum(String bankNum) {
		this.bankNum = bankNum;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}
	
}
