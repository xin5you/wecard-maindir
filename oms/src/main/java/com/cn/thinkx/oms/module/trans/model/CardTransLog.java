package com.cn.thinkx.oms.module.trans.model;

import com.cn.thinkx.oms.base.model.BaseDomain;

public class CardTransLog extends BaseDomain {

	private String txnPrimaryKey;
	private String settleDate;
	private String orgTxnPrimaryKey;
	private String dmsRelatedKey;
	private String orgDmsRelatedKey;
	private String transId;
	private long transSt;
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

	private String referenceNo;// 交易参考号
	private String mchntName;// 商户名称
	private String shopName;// 门店名称
	private String transTime;// 交易时间
	private String personName;//用户名
	private String chnlName;//渠道名称

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

	public long getTransSt() {
		return transSt;
	}

	public void setTransSt(long transSt) {
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

	public void setTransAmt(String transAmt) {
		this.transAmt = transAmt;
	}

	public String getOrgTransAmt() {
		return orgTransAmt;
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

	public String getReferenceNo() {
		return referenceNo;
	}

	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}

	public String getMchntName() {
		return mchntName;
	}

	public void setMchntName(String mchntName) {
		this.mchntName = mchntName;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getTransTime() {
		return transTime;
	}

	public void setTransTime(String transTime) {
		this.transTime = transTime;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public String getChnlName() {
		return chnlName;
	}

	public void setChnlName(String chnlName) {
		this.chnlName = chnlName;
	}
}
