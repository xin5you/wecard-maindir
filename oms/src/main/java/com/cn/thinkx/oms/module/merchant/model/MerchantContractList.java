package com.cn.thinkx.oms.module.merchant.model;

import com.cn.thinkx.oms.base.model.BaseDomain;

/**
 * 
 * 商户合同明细表
 * @author hqw
 *
 */
public class MerchantContractList extends BaseDomain {
	
	private String contractDetailId;     //合同明细_id
	private String mchntContractId;      //商户合同号
	private String contractType;         //合同明细类型,区分复合卡结算，暂时不用
	private String productCode;          //卡产品号
	private int    contractRate;         //手续费率
	private String dataStat;             //状态
	private String contractStartDate;    //开始日期
	private String contractEndDate;      //结束日期
	
	public String getContractDetailId() {
		return contractDetailId;
	}
	public void setContractDetailId(String contractDetailId) {
		this.contractDetailId = contractDetailId;
	}
	public String getMchntContractId() {
		return mchntContractId;
	}
	public void setMchntContractId(String mchntContractId) {
		this.mchntContractId = mchntContractId;
	}
	public String getContractType() {
		return contractType;
	}
	public void setContractType(String contractType) {
		this.contractType = contractType;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public int getContractRate() {
		return contractRate;
	}
	public void setContractRate(int contractRate) {
		this.contractRate = contractRate;
	}
	public String getDataStat() {
		return dataStat;
	}
	public void setDataStat(String dataStat) {
		this.dataStat = dataStat;
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
}
