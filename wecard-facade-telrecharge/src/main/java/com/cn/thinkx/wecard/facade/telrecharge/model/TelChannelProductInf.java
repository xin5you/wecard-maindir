package com.cn.thinkx.wecard.facade.telrecharge.model;

import java.math.BigDecimal;

import com.cn.thinkx.common.base.core.domain.BaseEntity;

public class TelChannelProductInf extends BaseEntity {
	
	private static final long serialVersionUID = 946746869188132111L;
	
	private String productId;
	private String operName;
	private String operId;
	private String areaFlag;
	private BigDecimal productAmt;
	private BigDecimal productPrice;
	private String productType;
	private String resv1;
	private String resv2;
	private String resv3;
	private String resv4;
	private String resv5;
	private String resv6;
	
	//业务扩展字段
	private String id;	//分销商产品关联供应商商品表主键
	private BigDecimal channelRate; //分销商折扣率
	private String areaId;	//区域id
	private String channelId;	//分销商id
	
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
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
	public String getAreaFlag() {
		return areaFlag;
	}
	public void setAreaFlag(String areaFlag) {
		this.areaFlag = areaFlag;
	}
	public BigDecimal getProductAmt() {
		return productAmt;
	}
	public void setProductAmt(BigDecimal productAmt) {
		this.productAmt = productAmt;
	}
	public BigDecimal getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(BigDecimal productPrice) {
		this.productPrice = productPrice;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public BigDecimal getChannelRate() {
		return channelRate;
	}
	public void setChannelRate(BigDecimal channelRate) {
		this.channelRate = channelRate;
	}
	public String getAreaId() {
		return areaId;
	}
	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
	public String getChannelId() {
		return channelId;
	}
	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
	
}
