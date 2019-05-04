package com.cn.thinkx.oms.module.merchant.model;

import com.cn.thinkx.oms.base.model.BaseDomain;

public class ShopInf extends BaseDomain {

	private String shopId;
	private String shopCode;
	private String mchntId;
	private String shopName;
	private String shopAddr;
	private String dataStat;
	private String qrCodeUrl;
	private String shopType; //门店类型
	private String sellCardFlag;  //售卡标志
	private Integer shopLevel; //门店级别
	private String pShopCode; //所属门店
	private String pShopName; 

	private String country;
	private String province;
	private String city;
	private String district;
	private String street;
	private String longitude;
	private String latitude;
	private String geohash;
	private String telephone;
	private String evaluate;
	private String businessHours;
	
	private String mchntName;
	private String mchntCode;


	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public String getShopCode() {
		return shopCode;
	}

	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}

	public String getMchntId() {
		return mchntId;
	}

	public void setMchntId(String mchntId) {
		this.mchntId = mchntId;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getShopAddr() {
		return shopAddr;
	}

	public void setShopAddr(String shopAddr) {
		this.shopAddr = shopAddr;
	}

	public String getDataStat() {
		return dataStat;
	}

	public void setDataStat(String dataStat) {
		this.dataStat = dataStat;
	}

	public String getQrCodeUrl() {
		return qrCodeUrl;
	}

	public void setQrCodeUrl(String qrCodeUrl) {
		this.qrCodeUrl = qrCodeUrl;
	}

	public String getShopType() {
		return shopType;
	}

	public void setShopType(String shopType) {
		this.shopType = shopType;
	}

	public String getSellCardFlag() {
		return sellCardFlag;
	}

	public void setSellCardFlag(String sellCardFlag) {
		this.sellCardFlag = sellCardFlag;
	}
	
	
	public Integer getShopLevel() {
		return shopLevel;
	}

	public void setShopLevel(Integer shopLevel) {
		this.shopLevel = shopLevel;
	}

	public String getpShopCode() {
		return pShopCode;
	}

	public void setpShopCode(String pShopCode) {
		this.pShopCode = pShopCode;
	}

	public String getpShopName() {
		return pShopName;
	}

	public void setpShopName(String pShopName) {
		this.pShopName = pShopName;
	}

	public String getCountry() {
		return country;
	}

	public String getProvince() {
		return province;
	}

	public String getCity() {
		return city;
	}

	public String getDistrict() {
		return district;
	}

	public String getStreet() {
		return street;
	}

	public String getLongitude() {
		return longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public String getGeohash() {
		return geohash;
	}

	public String getTelephone() {
		return telephone;
	}

	public String getEvaluate() {
		return evaluate;
	}

	public String getBusinessHours() {
		return businessHours;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public void setGeohash(String geohash) {
		this.geohash = geohash;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public void setEvaluate(String evaluate) {
		this.evaluate = evaluate;
	}

	public void setBusinessHours(String businessHours) {
		this.businessHours = businessHours;
	}

	public String getMchntName() {
		return mchntName;
	}

	public String getMchntCode() {
		return mchntCode;
	}

	public void setMchntName(String mchntName) {
		this.mchntName = mchntName;
	}

	public void setMchntCode(String mchntCode) {
		this.mchntCode = mchntCode;
	}
}
