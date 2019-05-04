package com.cn.thinkx.oms.module.mchnteshop.model;

import com.cn.thinkx.oms.base.model.BaseDomain;

public class MchntEshopInf extends BaseDomain {
	private String eShopId;
	private String eShopName;
	private String mchntCode;
	private String shopCode;
	private String eShopUrl;
	private String dataStat;
	private String eShopLogo;
	private String bgUrl;
	private String channelCode;
	
	private String mchntName;
	private String shopName;
	public String geteShopId() {
		return eShopId;
	}
	public void seteShopId(String eShopId) {
		this.eShopId = eShopId;
	}
	public String geteShopName() {
		return eShopName;
	}
	public void seteShopName(String eShopName) {
		this.eShopName = eShopName;
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
	public String geteShopUrl() {
		return eShopUrl;
	}
	public void seteShopUrl(String eShopUrl) {
		this.eShopUrl = eShopUrl;
	}
	public String getDataStat() {
		return dataStat;
	}
	public void setDataStat(String dataStat) {
		this.dataStat = dataStat;
	}
	public String geteShopLogo() {
		return eShopLogo;
	}
	public void seteShopLogo(String eShopLogo) {
		this.eShopLogo = eShopLogo;
	}
	public String getBgUrl() {
		return bgUrl;
	}
	public void setBgUrl(String bgUrl) {
		this.bgUrl = bgUrl;
	}
	public String getChannelCode() {
		return channelCode;
	}
	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}
	public String getMchntName() {
		return mchntName;
	}
	public void setMchntName(String mchntName) {
		this.mchntName = mchntName;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	
}
