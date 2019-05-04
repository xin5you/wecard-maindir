package com.cn.thinkx.oms.module.merchant.model;

import com.cn.thinkx.oms.base.model.BaseDomain;

public class InsInf extends BaseDomain {

	private String insId;
	private String insCode;
	private String insName;
	private String dataStat;
	
	public String getInsId() {
		return insId;
	}
	public String getInsCode() {
		return insCode;
	}
	public String getInsName() {
		return insName;
	}
	public void setInsId(String insId) {
		this.insId = insId;
	}
	public void setInsCode(String insCode) {
		this.insCode = insCode;
	}
	public void setInsName(String insName) {
		this.insName = insName;
	}
	public String getDataStat() {
		return dataStat;
	}
	public void setDataStat(String dataStat) {
		this.dataStat = dataStat;
	}
}
