package com.cn.thinkx.wecard.facade.telrecharge.model;

import java.math.BigDecimal;

import com.cn.thinkx.common.base.core.domain.BaseEntity;

public class TelChannelItemList extends BaseEntity {


	private static final long serialVersionUID = 6880940512280622278L;

	private String id;
	
	private String productId;
	
	private String channelId;
	
	private BigDecimal channelRate;

	private String resv1;

	private String resv2;

	private String resv3;

	private String resv4;

	private String resv5;

	private String resv6;
	
	private String areaName;	//区域名称
	private String operName;	//运营商名称
	private String operId;	//运营商标识
	
	private String channelName; //供应商名称

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public BigDecimal getChannelRate() {
		return channelRate;
	}

	public void setChannelRate(BigDecimal channelRate) {
		this.channelRate = channelRate;
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

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getOperName() {
		return operName;
	}

	public void setOperName(String operName) {
		this.operName = operName;
	}

	public String getOperId() {
		return operId;
	}

	public void setOperId(String operId) {
		this.operId = operId;
	}

	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
}
