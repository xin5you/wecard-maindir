package com.cn.thinkx.wecard.middleware.resp.hkbtxnfacade.vo;

import com.cn.thinkx.wecard.middleware.resp.domain.entity.BaseDomain;

public class CustomerAccListQueryITFVo extends BaseDomain{

	private static final long serialVersionUID = 1L;
	private String brandLogo;// 商户LOGO
	private String mchntCode;// 商户号
	private String productCode;// 产品号
	private String productName; // 产品名称
	private String productImage; // 产品(卡)面
	private String productBalance; // 产品(卡)余额
	public String getBrandLogo() {
		return brandLogo;
	}
	public void setBrandLogo(String brandLogo) {
		this.brandLogo = brandLogo;
	}
	public String getMchntCode() {
		return mchntCode;
	}
	public void setMchntCode(String mchntCode) {
		this.mchntCode = mchntCode;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductImage() {
		return productImage;
	}
	public void setProductImage(String productImage) {
		this.productImage = productImage;
	}
	public String getProductBalance() {
		return productBalance;
	}
	public void setProductBalance(String productBalance) {
		this.productBalance = productBalance;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}
