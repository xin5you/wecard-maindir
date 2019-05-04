package com.cn.thinkx.oms.module.merchant.model;

import com.cn.thinkx.oms.base.model.BaseDomain;

/**
 * 
 * 商户合同表
 * @author hqw
 *
 */
public class MerchantContract extends BaseDomain {
	
	private String  mchntContractId;         //商户合同号
	private String  mchntId;                 //商户_id
	private String  mchntCode;               //商户号
	private String  settleType;              //结算方式
	private String  settleCycle;             //结算周期
	private String  contractStartDate;       //开始日期
	private String  contractEndDate;         //结束日期
	private String  preSettleDate;           //上一结算日
	private String  dataStat;                //状态
	
	public String getMchntContractId() {
		return mchntContractId;
	}
	public void setMchntContractId(String mchntContractId) {
		this.mchntContractId = mchntContractId;
	}
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
	public String getSettleType() {
		return settleType;
	}
	public void setSettleType(String settleType) {
		this.settleType = settleType;
	}
	public String getSettleCycle() {
		return settleCycle;
	}
	public void setSettleCycle(String settleCycle) {
		this.settleCycle = settleCycle;
	}
	public String getContractStartDate() {
		return contractStartDate;
	}
	public void setContractStartDate(String contractStartDate) {
		this.contractStartDate = contractStartDate;
	}
	public String getContractEndDate() {
		return contractEndDate;
	}
	public void setContractEndDate(String contractEndDate) {
		this.contractEndDate = contractEndDate;
	}
	public String getPreSettleDate() {
		return preSettleDate;
	}
	public void setPreSettleDate(String preSettleDate) {
		this.preSettleDate = preSettleDate;
	}
	public String getDataStat() {
		return dataStat;
	}
	public void setDataStat(String dataStat) {
		this.dataStat = dataStat;
	}
}
