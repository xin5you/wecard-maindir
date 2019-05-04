package com.cn.thinkx.wecard.middleware.resp.hkbtxnfacade.vo;

/**
 * 查询商户门店列表
 * @author zqy
 *
 */
public class ShopListQueryITFVo {

	private String mchntCode;
	private String mchntName;
	private String industryType; //行业类型
	private String shopCode;
	private String shopName;  //
	private String shopAddr;  //门店地址
	private String evaluate; //评价星级，范围0~100按10递增
	private String latitude; //纬度
	private String longitude; //经度
	private String distance; //距离
	private String brandLogo; //商户LOGO
	private String activeExplain; //活动说明
	private String soldCount; //售卡量
	
	
	private String[] stars=new String[]{"0","0","0","0","0"};  //星级评分 0：0星 ，1:1星，其他表示半颗星级
	
	
	public String getMchntCode() {
		return mchntCode;
	}
	public void setMchntCode(String mchntCode) {
		this.mchntCode = mchntCode;
	}
	public String getMchntName() {
		return mchntName;
	}
	public void setMchntName(String mchntName) {
		this.mchntName = mchntName;
	}
	public String getIndustryType() {
		return industryType;
	}
	public void setIndustryType(String industryType) {
		this.industryType = industryType;
	}
	public String getShopCode() {
		return shopCode;
	}
	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
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
	public String getEvaluate() {
		return evaluate;
	}
	public void setEvaluate(String evaluate) {
		this.evaluate = evaluate;
	}
	public String getLatitude() {
		return latitude;
	}
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	public String getLongitude() {
		return longitude;
	}
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	public String getDistance() {
		return distance;
	}
	public void setDistance(String distance) {
		this.distance = distance;
	}
	public String getBrandLogo() {
		return brandLogo;
	}
	public void setBrandLogo(String brandLogo) {
		this.brandLogo = brandLogo;
	}
	public String getActiveExplain() {
		return activeExplain;
	}
	public void setActiveExplain(String activeExplain) {
		this.activeExplain = activeExplain;
	}
	public String getSoldCount() {
		return soldCount;
	}
	public void setSoldCount(String soldCount) {
		this.soldCount = soldCount;
	}
	public String[] getStars() {
		return stars;
	}
	public void setStars(String[] stars) {
		this.stars = stars;
	}
}
