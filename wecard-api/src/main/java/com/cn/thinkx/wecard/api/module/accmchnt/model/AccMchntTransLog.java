package com.cn.thinkx.wecard.api.module.accmchnt.model;

import com.cn.thinkx.wecard.api.module.core.domain.BaseDomain;

/**
 * 通卡会员卡交易关联表
 * 
 * @author xiaomei
 *
 */
public class AccMchntTransLog extends BaseDomain {
	private static final long serialVersionUID = 2802516098254996778L;
	private String accPrimaryKey;	//通卡流水号
	private String mchntPrimaryKey;	//商户会员卡流水号
	private String transAmt;		//交易金额
	private String settleDate;		//清算日期
	private String userInf;			//用户信息
	private String mchntCode;		//商户号
	private String shopCode;		//门店号
	private String deviceNo;		//设备号
	private int transSt;			//交易状态
	private String respCode;		//响应码
	private String transId;			//交易类型
	
	public String getAccPrimaryKey() {
		return accPrimaryKey;
	}
	public void setAccPrimaryKey(String accPrimaryKey) {
		this.accPrimaryKey = accPrimaryKey;
	}
	public String getMchntPrimaryKey() {
		return mchntPrimaryKey;
	}
	public void setMchntPrimaryKey(String mchntPrimaryKey) {
		this.mchntPrimaryKey = mchntPrimaryKey;
	}
	public String getTransAmt() {
		return transAmt;
	}
	public void setTransAmt(String transAmt) {
		this.transAmt = transAmt;
	}
	public String getSettleDate() {
		return settleDate;
	}
	public void setSettleDate(String settleDate) {
		this.settleDate = settleDate;
	}
	public String getUserInf() {
		return userInf;
	}
	public void setUserInf(String userInf) {
		this.userInf = userInf;
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
	public String getDeviceNo() {
		return deviceNo;
	}
	public void setDeviceNo(String deviceNo) {
		this.deviceNo = deviceNo;
	}
	public int getTransSt() {
		return transSt;
	}
	public void setTransSt(int transSt) {
		this.transSt = transSt;
	}
	public String getRespCode() {
		return respCode;
	}
	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}
	public String getTransId() {
		return transId;
	}
	public void setTransId(String transId) {
		this.transId = transId;
	}
	
	@Override
	public String toString() {
		return "AccMchntTransLog [accPrimaryKey=" + accPrimaryKey + ", mchntPrimaryKey=" + mchntPrimaryKey
				+ ", transAmt=" + transAmt + ", settleDate=" + settleDate + ", userInf=" + userInf + ", mchntCode="
				+ mchntCode + ", shopCode=" + shopCode + ", deviceNo=" + deviceNo + ", transSt=" + transSt
				+ ", respCode=" + respCode + ", transId=" + transId + "]";
	}
	
}
