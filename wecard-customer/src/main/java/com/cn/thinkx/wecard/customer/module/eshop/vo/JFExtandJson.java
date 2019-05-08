package com.cn.thinkx.wecard.customer.module.eshop.vo;

/**
 * 对接嘉福 拓展参数封装类
 * 
 * @author xiaomei
 *
 */
public class JFExtandJson {
	private String phoneNo;// 手机号
	private String userId;// 用户唯一标识
	private String mchntCode;// 薪无忧商户号
	private String shopCode;// 薪无忧门店号
	private String ecomChnl;// 电商标识
	private String channel;// 渠道号
	
	public String getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
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
	public String getEcomChnl() {
		return ecomChnl;
	}
	public void setEcomChnl(String ecomChnl) {
		this.ecomChnl = ecomChnl;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	@Override
	public String toString() {
		return "JFExtandJson [phoneNo=" + phoneNo + ", userId=" + userId + ", mchntCode=" + mchntCode + ", shopCode="
				+ shopCode + ", ecomChnl=" + ecomChnl + ", channel=" + channel + "]";
	}
	
}
