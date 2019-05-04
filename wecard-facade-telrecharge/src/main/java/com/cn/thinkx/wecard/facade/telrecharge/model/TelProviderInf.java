package com.cn.thinkx.wecard.facade.telrecharge.model;


import com.cn.thinkx.common.base.core.domain.BaseEntity;

public class TelProviderInf extends BaseEntity {
	
	private static final long serialVersionUID = 6955327888569894630L;
	
	private String providerId;
	private String providerName;
	private String appUrl;
	private String appSecret;
	private String accessToken;
	private String defaultRoute;
	private String providerRate;
	private Integer operSolr;
	
	private String resv1;
	private String resv2;
	private String resv3;
	private String resv4;
	private String resv5;
	private String resv6;
	public String getProviderId() {
		return providerId;
	}
	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}
	public String getProviderName() {
		return providerName;
	}
	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}
	public String getAppUrl() {
		return appUrl;
	}
	public void setAppUrl(String appUrl) {
		this.appUrl = appUrl;
	}
	public String getAppSecret() {
		return appSecret;
	}
	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getDefaultRoute() {
		return defaultRoute;
	}
	public void setDefaultRoute(String defaultRoute) {
		this.defaultRoute = defaultRoute;
	}
	public String getProviderRate() {
		return providerRate;
	}
	public void setProviderRate(String providerRate) {
		this.providerRate = providerRate;
	}
	public Integer getOperSolr() {
		return operSolr;
	}
	public void setOperSolr(Integer operSolr) {
		this.operSolr = operSolr;
	}
	public String getResv1() {
		return resv1;
	}
	public void setResv1(String resv1) {
		this.resv1 = resv1;
	}
	public String getResv2() {
		return resv2;
	}
	public void setResv2(String resv2) {
		this.resv2 = resv2;
	}
	public String getResv3() {
		return resv3;
	}
	public void setResv3(String resv3) {
		this.resv3 = resv3;
	}
	public String getResv4() {
		return resv4;
	}
	public void setResv4(String resv4) {
		this.resv4 = resv4;
	}
	public String getResv5() {
		return resv5;
	}
	public void setResv5(String resv5) {
		this.resv5 = resv5;
	}
	public String getResv6() {
		return resv6;
	}
	public void setResv6(String resv6) {
		this.resv6 = resv6;
	}
	
}
