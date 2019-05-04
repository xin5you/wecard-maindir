package com.cn.thinkx.oms.module.merchant.model;

import com.cn.thinkx.oms.base.model.BaseDomain;

/**
 * 
 * 产品表
 * @author hqw
 *
 */
public class Product extends BaseDomain {
	
	private String productCode;     //产品号
	private String versionId;       //密钥版本_id
	private String cardBin;         //卡Bin
	private String productName;     //卡产品名称
	private String onymousStat;     //署名类型
	private String businessType;    //业务类型
	private String productType;     //卡产品类型
	private int validityPeriod;     //有效期月数
	private String maxBalance;      //最大余额
	private String consumTimes;     //消费次数
	private String rechargeTimes;   //充值次数
	private String cvv2ErrTimes;   //cvv2错误次数
	private String cardFace;        //卡面
	private String dataStat;        //启用状态
	private String balKeyIndex;     //余额加密索引
	private String cvvKeyIndex;     //CVV加密索引
	private String pwdKeyIndex;     //密码加密索引
	private int lastCardNum;        //最终卡序号
	
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getVersionId() {
		return versionId;
	}
	public void setVersionId(String versionId) {
		this.versionId = versionId;
	}
	public String getCardBin() {
		return cardBin;
	}
	public void setCardBin(String cardBin) {
		this.cardBin = cardBin;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getOnymousStat() {
		return onymousStat;
	}
	public void setOnymousStat(String onymousStat) {
		this.onymousStat = onymousStat;
	}
	public String getBusinessType() {
		return businessType;
	}
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public int getValidityPeriod() {
		return validityPeriod;
	}
	public void setValidityPeriod(int validityPeriod) {
		this.validityPeriod = validityPeriod;
	}
	public String getMaxBalance() {
		return maxBalance;
	}
	public void setMaxBalance(String maxBalance) {
		this.maxBalance = maxBalance;
	}
	public String getConsumTimes() {
		return consumTimes;
	}
	public void setConsumTimes(String consumTimes) {
		this.consumTimes = consumTimes;
	}
	public String getRechargeTimes() {
		return rechargeTimes;
	}
	public void setRechargeTimes(String rechargeTimes) {
		this.rechargeTimes = rechargeTimes;
	}
	public String getCvv2ErrTimes() {
		return cvv2ErrTimes;
	}
	public void setCvv2ErrTimes(String cvv2ErrTimes) {
		this.cvv2ErrTimes = cvv2ErrTimes;
	}
	public String getCardFace() {
		return cardFace;
	}
	public void setCardFace(String cardFace) {
		this.cardFace = cardFace;
	}
	public String getDataStat() {
		return dataStat;
	}
	public void setDataStat(String dataStat) {
		this.dataStat = dataStat;
	}
	public String getBalKeyIndex() {
		return balKeyIndex;
	}
	public void setBalKeyIndex(String balKeyIndex) {
		this.balKeyIndex = balKeyIndex;
	}
	public String getCvvKeyIndex() {
		return cvvKeyIndex;
	}
	public void setCvvKeyIndex(String cvvKeyIndex) {
		this.cvvKeyIndex = cvvKeyIndex;
	}
	public String getPwdKeyIndex() {
		return pwdKeyIndex;
	}
	public void setPwdKeyIndex(String pwdKeyIndex) {
		this.pwdKeyIndex = pwdKeyIndex;
	}
	public int getLastCardNum() {
		return lastCardNum;
	}
	public void setLastCardNum(int lastCardNum) {
		this.lastCardNum = lastCardNum;
	}
	
}
