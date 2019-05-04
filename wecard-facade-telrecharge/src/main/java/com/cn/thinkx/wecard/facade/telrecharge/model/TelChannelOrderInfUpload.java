package com.cn.thinkx.wecard.facade.telrecharge.model;

import java.io.Serializable;
import java.math.BigDecimal;

public class TelChannelOrderInfUpload implements Serializable {
	private String channelOrderId;
	private String operName;
	private String rechargePhone;
	private String itemNum;
	private BigDecimal rechargeValue;
	private BigDecimal payAmt;
	private String rechargeState;
	private String createTime;
	public String getChannelOrderId() {
		return channelOrderId;
	}
	public void setChannelOrderId(String channelOrderId) {
		this.channelOrderId = channelOrderId;
	}
	public String getOperName() {
		return operName;
	}
	public void setOperName(String operName) {
		this.operName = operName;
	}
	public String getRechargePhone() {
		return rechargePhone;
	}
	public void setRechargePhone(String rechargePhone) {
		this.rechargePhone = rechargePhone;
	}
	public String getItemNum() {
		return itemNum;
	}
	public void setItemNum(String itemNum) {
		this.itemNum = itemNum;
	}
	public BigDecimal getRechargeValue() {
		return rechargeValue;
	}
	public void setRechargeValue(BigDecimal rechargeValue) {
		this.rechargeValue = rechargeValue;
	}
	public BigDecimal getPayAmt() {
		return payAmt;
	}
	public void setPayAmt(BigDecimal payAmt) {
		this.payAmt = payAmt;
	}
	public String getRechargeState() {
		return rechargeState;
	}
	public void setRechargeState(String rechargeState) {
		this.rechargeState = rechargeState;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
}
