package com.cn.thinkx.wecard.middleware.resp.hkbtxnfacade.vo;

import java.util.List;

public class ShopInfoQueryITFVo {

	private String brandLogo;// 商户LOGO
	private String mchntCode; // 商户号
	private String shopCode; // 门店号
	private String mchntName; // 商户名称
	private String shopName;// 门店名称
	private String industryType; // 行业类型
	private String activeRule; // 活动使用规则
	private List<String> shopImages; // 店内照
	private String evaluate; // 评价星级
	private String soldCount; // 售卡量
	private String shopAddr; // 门店地址
	private String longitude; // 门店经度
	private String latitude; // 门店纬度
	private String telephone; // 客服电话
	
	private String[] stars=new String[]{"0","0","0","0","0"};  //星级评分 0：0星 ，1:1星，其他表示半颗星级
	
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
	public String getIndustryType() {
		return industryType;
	}
	public void setIndustryType(String industryType) {
		this.industryType = industryType;
	}
	public String getActiveRule() {
		return activeRule;
	}
	public void setActiveRule(String activeRule) {
		this.activeRule = activeRule;
	}
	public List<String> getShopImages() {
		return shopImages;
	}
	public void setShopImages(List<String> shopImages) {
		this.shopImages = shopImages;
	}
	public String getEvaluate() {
		return evaluate;
	}
	public void setEvaluate(String evaluate) {
		this.evaluate = evaluate;
	}
	public String getSoldCount() {
		return soldCount;
	}
	public void setSoldCount(String soldCount) {
		this.soldCount = soldCount;
	}
	public String getShopAddr() {
		return shopAddr;
	}
	public void setShopAddr(String shopAddr) {
		this.shopAddr = shopAddr;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getTelephone() {
		return telephone;
	}
	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}
	public String[] getStars() {
		return stars;
	}
	public void setStars(String[] stars) {
		this.stars = stars;
	}
}
