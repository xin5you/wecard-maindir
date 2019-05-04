package com.cn.thinkx.merchant.domain;

import com.cn.thinkx.core.domain.BaseDomain;

public class InsInf extends BaseDomain {
	
	private static final long serialVersionUID = 6892731091957109646L;
	
	private String insId;
	private String insCode;
	private String insName;
	private String dataStat;
	//产品code
	private String productCode;

	public String getInsId() {
		return insId;
	}

	public void setInsId(String insId) {
		this.insId = insId;
	}

	public String getInsCode() {
		return insCode;
	}

	public void setInsCode(String insCode) {
		this.insCode = insCode;
	}

	public String getInsName() {
		return insName;
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

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	

}
