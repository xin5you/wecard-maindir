package com.cn.thinkx.wecard.api.module.pub.model;

/**
 * 业务详细信息（商户号、商户名称、门店号、门店名称、机构号等）
 * 
 * @author xiaomei
 *	2018/4/11
 */
public class DetailBizInfo {

	private String insCode;
	private String mchntName;
	private String mchntCode;
	private String shopId;
	private String shopName;
	private String shopCode;
	private String mangerId;
	private String mangerName;// openid
	private String qrCodeUrl;

	public String getInsCode() {
		return insCode;
	}

	public void setInsCode(String insCode) {
		this.insCode = insCode;
	}

	public String getMchntName() {
		return mchntName;
	}

	public void setMchntName(String mchntName) {
		this.mchntName = mchntName;
	}

	public String getMchntCode() {
		return mchntCode;
	}

	public void setMchntCode(String mchntCode) {
		this.mchntCode = mchntCode;
	}

	public String getShopId() {
		return shopId;
	}

	public void setShopId(String shopId) {
		this.shopId = shopId;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getShopCode() {
		return shopCode;
	}

	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}

	public String getMangerId() {
		return mangerId;
	}

	public void setMangerId(String mangerId) {
		this.mangerId = mangerId;
	}

	public String getMangerName() {
		return mangerName;
	}

	public void setMangerName(String mangerName) {
		this.mangerName = mangerName;
	}

	public String getQrCodeUrl() {
		return qrCodeUrl;
	}

	public void setQrCodeUrl(String qrCodeUrl) {
		this.qrCodeUrl = qrCodeUrl;
	}

}
