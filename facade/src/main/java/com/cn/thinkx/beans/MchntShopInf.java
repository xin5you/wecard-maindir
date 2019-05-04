package com.cn.thinkx.beans;

public class MchntShopInf {
	
	private String mchntCode;
	private String mchntName;
	private String industryType;
	private String shopCode;
	private String shopName;  //
	private String shopAddr;  //门店地址
	private String evaluate; //评价星级，范围0~100按10递增
	private String latitude; //纬度
	private String longitude; //经度
	private String distance; //距离
	private String brandLogo; //商户LOGO
	private String activeExplain; //活动说明
	
	private String[] geohashs;

	public String getMchntCode() {
		return mchntCode;
	}

	public String getMchntName() {
		return mchntName;
	}

	public String getIndustryType() {
		return industryType;
	}


	public String getShopCode() {
		return shopCode;
	}

	public String getShopName() {
		return shopName;
	}

	public String getShopAddr() {
		return shopAddr;
	}

	public String getEvaluate() {
		return evaluate;
	}

	public String getLatitude() {
		return latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public String getDistance() {
		return distance;
	}

	public String getBrandLogo() {
		return brandLogo;
	}

	public String getActiveExplain() {
		return activeExplain;
	}

	public String[] getGeohashs() {
		return geohashs;
	}

	public void setMchntCode(String mchntCode) {
		this.mchntCode = mchntCode;
	}

	public void setMchntName(String mchntName) {
		this.mchntName = mchntName;
	}

	public void setIndustryType1(String industryType) {
		this.industryType = industryType;
	}



	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public void setShopAddr(String shopAddr) {
		this.shopAddr = shopAddr;
	}

	public void setEvaluate(String evaluate) {
		this.evaluate = evaluate;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public void setBrandLogo(String brandLogo) {
		this.brandLogo = brandLogo;
	}

	public void setActiveExplain(String activeExplain) {
		this.activeExplain = activeExplain;
	}

	public void setGeohashs(String[] geohashs) {
		this.geohashs = geohashs;
	}
	
}
