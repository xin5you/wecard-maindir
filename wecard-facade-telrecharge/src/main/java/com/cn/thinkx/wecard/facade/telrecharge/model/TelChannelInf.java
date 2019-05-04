package com.cn.thinkx.wecard.facade.telrecharge.model;

import java.math.BigDecimal;

import com.cn.thinkx.common.base.core.domain.BaseEntity;

public class TelChannelInf extends BaseEntity {

	private static final long serialVersionUID = 505300107764983050L;

	private String channelId;
	
	private String channelName;
	
	private String channelCode;
	
	private String channelKey;
	
	private BigDecimal channelReserveAmt;
	
	private BigDecimal channelPrewarningAmt;
	
	private String phoneNo;
	
	private String email;
	
	private String resv1;
	
	private String resv2;
	
	private String resv3;
	
	private String resv4;
	
	private String resv5;
	
	private String resv6;

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public String getChannelKey() {
		return channelKey;
	}

	public void setChannelKey(String channelKey) {
		this.channelKey = channelKey;
	}

	public BigDecimal getChannelReserveAmt() {
		return channelReserveAmt;
	}

	public void setChannelReserveAmt(BigDecimal channelReserveAmt) {
		this.channelReserveAmt = channelReserveAmt;
	}

	public BigDecimal getChannelPrewarningAmt() {
		return channelPrewarningAmt;
	}

	public void setChannelPrewarningAmt(BigDecimal channelPrewarningAmt) {
		this.channelPrewarningAmt = channelPrewarningAmt;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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
