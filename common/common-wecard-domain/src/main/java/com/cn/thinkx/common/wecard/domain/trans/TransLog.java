package com.cn.thinkx.common.wecard.domain.trans;

import java.util.Date;

import com.cn.thinkx.pms.base.utils.StringUtil;

public class TransLog {
	
	private String txnPrimaryKey;
	private String settleDate;
	private String orgTxnPrimaryKey;
	private String dmsRelatedKey;
	private String orgDmsRelatedKey;
	private String transId;
	private String transSt;
	private String termCode;
	private String shopCode;
	private String mchntCode;
	private String insCode;
	private String respCode;
	private String priAcctNo;
	private String cardNo;
	private String userName;
	private String productCode;
	private String transAmt;
	private String orgTransAmt;
	private String transCurrCd;
	private String cardAttr;
	private String transChnl;
	private String transFee;
	private String transFeeType;
	private String tfrInAcctNo;
	private String tfrOutAcctNo;
	private String additionalInfo;
	private String remarks;
	private String createUser;
	private String updateUser;
	private Date createTime;
	private Date updateTime;
	
	
	/******/
	private String shopName;
	
	private Integer transCount; /**交易笔数**/
	
	private double consumerAmount;  //消费金额
	private Integer consumerCount;  //消费笔数
	
	private double rechargeAmount; //充值金额
	private Integer rechargeCount; //充值笔数
	
	private String mobilePhoneNo;
	
	public String getTxnPrimaryKey() {
		return txnPrimaryKey;
	}
	public void setTxnPrimaryKey(String txnPrimaryKey) {
		this.txnPrimaryKey = txnPrimaryKey;
	}
	public String getSettleDate() {
		return settleDate;
	}
	public void setSettleDate(String settleDate) {
		this.settleDate = settleDate;
	}
	public String getOrgTxnPrimaryKey() {
		return orgTxnPrimaryKey;
	}
	public void setOrgTxnPrimaryKey(String orgTxnPrimaryKey) {
		this.orgTxnPrimaryKey = orgTxnPrimaryKey;
	}
	public String getDmsRelatedKey() {
		return dmsRelatedKey;
	}
	public void setDmsRelatedKey(String dmsRelatedKey) {
		this.dmsRelatedKey = dmsRelatedKey;
	}
	public String getOrgDmsRelatedKey() {
		return orgDmsRelatedKey;
	}
	public void setOrgDmsRelatedKey(String orgDmsRelatedKey) {
		this.orgDmsRelatedKey = orgDmsRelatedKey;
	}
	public String getTransId() {
		return transId;
	}
	public void setTransId(String transId) {
		this.transId = transId;
	}
	public String getTransSt() {
		return transSt;
	}
	public void setTransSt(String transSt) {
		this.transSt = transSt;
	}
	public String getTermCode() {
		return termCode;
	}
	public void setTermCode(String termCode) {
		this.termCode = termCode;
	}
	public String getShopCode() {
		return shopCode;
	}
	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}
	public String getMchntCode() {
		return mchntCode;
	}
	public void setMchntCode(String mchntCode) {
		this.mchntCode = mchntCode;
	}
	public String getInsCode() {
		return insCode;
	}
	public void setInsCode(String insCode) {
		this.insCode = insCode;
	}
	public String getRespCode() {
		return respCode;
	}
	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}
	public String getPriAcctNo() {
		return priAcctNo;
	}
	public void setPriAcctNo(String priAcctNo) {
		this.priAcctNo = priAcctNo;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getTransAmt() {
		return transAmt;
	}
	public String getOrgTransAmt() {
		return orgTransAmt;
	}
	public void setTransAmt(String transAmt) {
		this.transAmt = transAmt;
	}
	public void setOrgTransAmt(String orgTransAmt) {
		this.orgTransAmt = orgTransAmt;
	}
	public String getTransCurrCd() {
		return transCurrCd;
	}
	public void setTransCurrCd(String transCurrCd) {
		this.transCurrCd = transCurrCd;
	}
	public String getCardAttr() {
		return cardAttr;
	}
	public void setCardAttr(String cardAttr) {
		this.cardAttr = cardAttr;
	}
	public String getTransChnl() {
		return transChnl;
	}
	public void setTransChnl(String transChnl) {
		this.transChnl = transChnl;
	}
	public String getTransFee() {
		return transFee;
	}
	public void setTransFee(String transFee) {
		this.transFee = transFee;
	}
	public String getTransFeeType() {
		return transFeeType;
	}
	public void setTransFeeType(String transFeeType) {
		this.transFeeType = transFeeType;
	}
	public String getTfrInAcctNo() {
		return tfrInAcctNo;
	}
	public void setTfrInAcctNo(String tfrInAcctNo) {
		this.tfrInAcctNo = tfrInAcctNo;
	}
	public String getTfrOutAcctNo() {
		return tfrOutAcctNo;
	}
	public void setTfrOutAcctNo(String tfrOutAcctNo) {
		this.tfrOutAcctNo = tfrOutAcctNo;
	}
	public String getAdditionalInfo() {
		return additionalInfo;
	}
	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public Integer getTransCount() {
		return transCount;
	}
	public void setTransCount(Integer transCount) {
		this.transCount = transCount;
	}
	public double getConsumerAmount() {
		return consumerAmount;
	}
	public Integer getConsumerCount() {
		return consumerCount;
	}
	public double getRechargeAmount() {
		return rechargeAmount;
	}
	public Integer getRechargeCount() {
		return rechargeCount;
	}
	public void setConsumerAmount(double consumerAmount) {
		this.consumerAmount = consumerAmount;
	}
	public void setConsumerCount(Integer consumerCount) {
		this.consumerCount = consumerCount;
	}
	public void setRechargeAmount(double rechargeAmount) {
		this.rechargeAmount = rechargeAmount;
	}
	public void setRechargeCount(Integer rechargeCount) {
		this.rechargeCount = rechargeCount;
	}
	public String getMobilePhoneNo() {
		return mobilePhoneNo;
	}
	public void setMobilePhoneNo(String mobilePhoneNo) {
		if(!StringUtil.isNullOrEmpty(mobilePhoneNo)){
			if(mobilePhoneNo.length()>=4){
				this.mobilePhoneNo=StringUtil.getPhoneNumberFormatLast4(mobilePhoneNo);
			}
		}else{
			this.mobilePhoneNo = mobilePhoneNo;
		}
	}
}
