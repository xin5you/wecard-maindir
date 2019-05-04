package com.cn.thinkx.merchant.domain;

/**
 * 交易流水
 * 
 * @author pucker
 *
 */
public class IntfaceTransLog {
	private String interfacePrimaryKey;
	private String settleDate;
	private String orgInterfacePrimaryKey;
	private String dmsRelatedKey;
	private String orgDmsRelatedKey;
	private String transId;
	private int transSt;
	private String insCode;
	private String mchntCode;
	private String shopCode;
	private String respCode;
	private String priAcctNo;
	private String cardNo;
	private String userInfUserName;
	private String productCode;
	private String transAmt;
	private String uploadAmt;
	private String transCurrCd;
	private String transChnl;
	private String transFee;
	private String transFeeType;

	public String getInterfacePrimaryKey() {
		return interfacePrimaryKey;
	}

	public void setInterfacePrimaryKey(String interfacePrimaryKey) {
		this.interfacePrimaryKey = interfacePrimaryKey;
	}

	public String getSettleDate() {
		return settleDate;
	}

	public void setSettleDate(String settleDate) {
		this.settleDate = settleDate;
	}

	public String getOrgInterfacePrimaryKey() {
		return orgInterfacePrimaryKey;
	}

	public void setOrgInterfacePrimaryKey(String orgInterfacePrimaryKey) {
		this.orgInterfacePrimaryKey = orgInterfacePrimaryKey;
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

	public int getTransSt() {
		return transSt;
	}

	public void setTransSt(int transSt) {
		this.transSt = transSt;
	}

	public String getInsCode() {
		return insCode;
	}

	public void setInsCode(String insCode) {
		this.insCode = insCode;
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

	public String getUserInfUserName() {
		return userInfUserName;
	}

	public void setUserInfUserName(String userInfUserName) {
		this.userInfUserName = userInfUserName;
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

	public String getUploadAmt() {
		return uploadAmt;
	}

	public void setUploadAmt(String uploadAmt) {
		this.uploadAmt = uploadAmt;
	}

	public String getTransCurrCd() {
		return transCurrCd;
	}

	public void setTransCurrCd(String transCurrCd) {
		this.transCurrCd = transCurrCd;
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

}
