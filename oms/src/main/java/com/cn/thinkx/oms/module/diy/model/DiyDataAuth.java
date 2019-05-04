package com.cn.thinkx.oms.module.diy.model;

import com.cn.thinkx.oms.base.model.BaseDomain;

public class DiyDataAuth extends BaseDomain{

	private String id;	//权限id
	private String mchntCode;	//商户号
	private String shopCode;	//门店号
	private String casecade;	//级别
	private String dataStat;	//状态
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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
	public String getCasecade() {
		return casecade;
	}
	public void setCasecade(String casecade) {
		this.casecade = casecade;
	}
	public String getDataStat() {
		return dataStat;
	}
	public void setDataStat(String dataStat) {
		this.dataStat = dataStat;
	}
	
	
	
	
}
