package com.cn.thinkx.biz.mchnt.model;

import com.cn.thinkx.biz.core.model.BaseDomain;

public class ShopInf extends BaseDomain {
	private static final long serialVersionUID = 1L;
	private String mchntCode; // 商户号
	private String shopCode; // 门店号
	private String mchntName; // 商户名称
	private String shopName;// 门店名称
	private String detailFlag;// 是否查询明细
	
	private String latitude; //纬度
	private String longitude; //经度
	private String distance; //距离
	private String sort; //10：销量最高、20评价最好 30：距离由近到远
	private String industryType; //行业类型
	private int geoLength; //geohash 长度
	private String[] geohashs; //geohash 区块

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

	public String getDetailFlag() {
		return detailFlag;
	}

	public void setDetailFlag(String detailFlag) {
		this.detailFlag = detailFlag;
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

	public String getSort() {
		return sort;
	}

	public String getIndustryType() {
		return industryType;
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

	public void setSort(String sort) {
		this.sort = sort;
	}

	public void setIndustryType(String industryType) {
		this.industryType = industryType;
	}

	public int getGeoLength() {
		return geoLength;
	}

	public void setGeoLength(int geoLength) {
		this.geoLength = geoLength;
	}

	public String[] getGeohashs() {
		return geohashs;
	}

	public void setGeohashs(String[] geohashs) {
		this.geohashs = geohashs;
	}
}
