package com.cn.thinkx.oms.module.trans.model;

public class PayChannelTransLogUpload{

	private String wxPrimaryKey;
	private String settleDate;
	private String chnlName;//渠道名称
	private String mchntName;// 商户名称
//	private String shopName;// 门店名称
	private String payChannelName;	//支付通道名称
	private String termCode;	//设备号
	private String transId;
	private String transAmt;
	private String respCode;
	private String createTime;
	private String transChnl;
	private String transSt;
	
	public String getTransSt() {
		return transSt;
	}
	public void setTransSt(String transSt) {
		this.transSt = transSt;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getChnlName() {
		return chnlName;
	}
	public void setChnlName(String chnlName) {
		this.chnlName = chnlName;
	}
	public String getWxPrimaryKey() {
		return wxPrimaryKey;
	}
	public void setWxPrimaryKey(String wxPrimaryKey) {
		this.wxPrimaryKey = wxPrimaryKey;
	}
	public String getSettleDate() {
		return settleDate;
	}
	public void setSettleDate(String settleDate) {
		this.settleDate = settleDate;
	}
	public String getTransId() {
		return transId;
	}
	public void setTransId(String transId) {
		this.transId = transId;
	}
	public String getTransAmt() {
		return transAmt;
	}
	public void setTransAmt(String transAmt) {
		this.transAmt = transAmt;
	}
	public String getRespCode() {
		return respCode;
	}
	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}
	
	public String getTransChnl() {
		return transChnl;
	}
	public void setTransChnl(String transChnl) {
		this.transChnl = transChnl;
	}
	public String getMchntName() {
		return mchntName;
	}
	public void setMchntName(String mchntName) {
		this.mchntName = mchntName;
	}
//	public String getShopName() {
//		return shopName;
//	}
//	public void setShopName(String shopName) {
//		this.shopName = shopName;
//	}
	public String getPayChannelName() {
		return payChannelName;
	}
	public void setPayChannelName(String payChannelName) {
		this.payChannelName = payChannelName;
	}
	public String getTermCode() {
		return termCode;
	}
	public void setTermCode(String termCode) {
		this.termCode = termCode;
	}
	
}
