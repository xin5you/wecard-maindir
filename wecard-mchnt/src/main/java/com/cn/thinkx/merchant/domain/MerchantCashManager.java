package com.cn.thinkx.merchant.domain;

import com.cn.thinkx.core.domain.BaseDomain;

/**
 * 商户提现管理表
 * @author zqy
 *
 */
public class MerchantCashManager extends BaseDomain {
	
	
	private String chashId;
	private String mchntId;
	private String insId;
	private String mortgageFlg;//0-平台给商户押款 1-商户给平台押款
	private String mortgageAmt; //押款金额
	private String getQuota; //押款金额获取额度
	private String rechargeAmt; //累计充值金额
	private String rechargeFaceAmt; //累计充值面额
	private String dataStat;
	private String cashThresholdAmt;  //提现阀值
	private String grossProfitRate;//毛利率
	
	private String mchntName;
	private String mchntCode;
	
	public String getChashId() {
		return chashId;
	}
	public String getMchntId() {
		return mchntId;
	}
	public String getInsId() {
		return insId;
	}
	public String getMortgageFlg() {
		return mortgageFlg;
	}
	public String getMortgageAmt() {
		return mortgageAmt;
	}
	public String getGetQuota() {
		return getQuota;
	}
	public String getRechargeAmt() {
		return rechargeAmt;
	}
	public String getRechargeFaceAmt() {
		return rechargeFaceAmt;
	}
	public String getDataStat() {
		return dataStat;
	}
	public String getCashThresholdAmt() {
		return cashThresholdAmt;
	}
	public String getGrossProfitRate() {
		return grossProfitRate;
	}
	public String getMchntName() {
		return mchntName;
	}
	public String getMchntCode() {
		return mchntCode;
	}
	public void setChashId(String chashId) {
		this.chashId = chashId;
	}
	public void setMchntId(String mchntId) {
		this.mchntId = mchntId;
	}
	public void setInsId(String insId) {
		this.insId = insId;
	}
	public void setMortgageFlg(String mortgageFlg) {
		this.mortgageFlg = mortgageFlg;
	}
	public void setMortgageAmt(String mortgageAmt) {
		this.mortgageAmt = mortgageAmt;
	}
	public void setGetQuota(String getQuota) {
		this.getQuota = getQuota;
	}
	public void setRechargeAmt(String rechargeAmt) {
		this.rechargeAmt = rechargeAmt;
	}
	public void setRechargeFaceAmt(String rechargeFaceAmt) {
		this.rechargeFaceAmt = rechargeFaceAmt;
	}
	public void setDataStat(String dataStat) {
		this.dataStat = dataStat;
	}
	public void setCashThresholdAmt(String cashThresholdAmt) {
		this.cashThresholdAmt = cashThresholdAmt;
	}
	public void setGrossProfitRate(String grossProfitRate) {
		this.grossProfitRate = grossProfitRate;
	}
	public void setMchntName(String mchntName) {
		this.mchntName = mchntName;
	}
	public void setMchntCode(String mchntCode) {
		this.mchntCode = mchntCode;
	}
}
