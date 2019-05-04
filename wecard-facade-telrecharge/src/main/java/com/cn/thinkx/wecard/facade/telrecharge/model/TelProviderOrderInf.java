package com.cn.thinkx.wecard.facade.telrecharge.model;

import java.math.BigDecimal;
import java.util.Date;

import com.cn.thinkx.common.base.core.domain.BaseEntity;

public class TelProviderOrderInf extends BaseEntity {

	private static final long serialVersionUID = 4743861091318278165L;

	private String regOrderId;
	
	private String channelOrderId;
	
	private BigDecimal regOrderAmt;
	
	private Date operateTime;
	
	private String payState;
	
	private String rechargeState;
	
	private BigDecimal itemCost;
	
	private BigDecimal transCost;
	
	private String revokeMessage;
	
	private String billId;
	
	private String providerId;
	
	private int operNum;

	private String resv1;
	private String resv2;
	private String resv3;
	private String resv4;
	private String resv5;
	private String resv6;
	public String getRegOrderId() {
		return regOrderId;
	}
	public void setRegOrderId(String regOrderId) {
		this.regOrderId = regOrderId;
	}
	public String getChannelOrderId() {
		return channelOrderId;
	}
	public void setChannelOrderId(String channelOrderId) {
		this.channelOrderId = channelOrderId;
	}
	public BigDecimal getRegOrderAmt() {
		return regOrderAmt;
	}
	public void setRegOrderAmt(BigDecimal regOrderAmt) {
		this.regOrderAmt = regOrderAmt;
	}
	public Date getOperateTime() {
		return operateTime;
	}
	public void setOperateTime(Date operateTime) {
		this.operateTime = operateTime;
	}
	public String getPayState() {
		return payState;
	}
	public void setPayState(String payState) {
		this.payState = payState;
	}
	public String getRechargeState() {
		return rechargeState;
	}
	public void setRechargeState(String rechargeState) {
		this.rechargeState = rechargeState;
	}
	public BigDecimal getItemCost() {
		return itemCost;
	}
	public void setItemCost(BigDecimal itemCost) {
		this.itemCost = itemCost;
	}
	public BigDecimal getTransCost() {
		return transCost;
	}
	public void setTransCost(BigDecimal transCost) {
		this.transCost = transCost;
	}
	public String getRevokeMessage() {
		return revokeMessage;
	}
	public void setRevokeMessage(String revokeMessage) {
		this.revokeMessage = revokeMessage;
	}
	public String getBillId() {
		return billId;
	}
	public void setBillId(String billId) {
		this.billId = billId;
	}
	public String getProviderId() {
		return providerId;
	}
	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}
	public int getOperNum() {
		return operNum;
	}
	public void setOperNum(int operNum) {
		this.operNum = operNum;
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
