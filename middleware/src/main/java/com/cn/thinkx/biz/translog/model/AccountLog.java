package com.cn.thinkx.biz.translog.model;

import com.cn.thinkx.biz.core.model.BaseDomain;

/**
 * 
 * @author zqy
 *
 */
public class AccountLog extends BaseDomain {
	private static final long serialVersionUID = 1L;
	private String actPrimaryKey;
	private String settleDate;
	private String txnPrimaryKey;
	private String insCode;
	private String accountNo;
	private String batNo;
	private String txnDate;
	private String txnTime;
	private String transId;
	private String transChnl;
	private String txnRealAmt;
	private String txnRealAmtType;
	private String txnAmt;
	private String txnFee;
	private String accType;
	private String accTotalBal;
	private String accValidBal;
	private String cancelFlag;
	private String revsalFlag;
	private String returnFlag;
	private String returnAmt;
	private String adjustFlag;
	private String adjustAmt;
	private String errNo;
	private String orgActPrimaryKey;
	private String orgTxnPrimaryKey;
	private String orgBatNo;
	private String respCode;

	public String getActPrimaryKey() {
		return actPrimaryKey;
	}

	public String getSettleDate() {
		return settleDate;
	}

	public String getTxnPrimaryKey() {
		return txnPrimaryKey;
	}

	public String getInsCode() {
		return insCode;
	}

	public String getAccountNo() {
		return accountNo;
	}

	public String getBatNo() {
		return batNo;
	}

	public String getTxnDate() {
		return txnDate;
	}

	public String getTxnTime() {
		return txnTime;
	}

	public String getTransId() {
		return transId;
	}

	public String getTransChnl() {
		return transChnl;
	}

	public String getTxnRealAmt() {
		return txnRealAmt;
	}

	public String getTxnRealAmtType() {
		return txnRealAmtType;
	}

	public String getTxnAmt() {
		return txnAmt;
	}

	public String getTxnFee() {
		return txnFee;
	}

	public String getAccType() {
		return accType;
	}

	public String getAccTotalBal() {
		return accTotalBal;
	}

	public String getAccValidBal() {
		return accValidBal;
	}

	public String getCancelFlag() {
		return cancelFlag;
	}

	public String getRevsalFlag() {
		return revsalFlag;
	}

	public String getReturnFlag() {
		return returnFlag;
	}

	public String getReturnAmt() {
		return returnAmt;
	}

	public String getAdjustFlag() {
		return adjustFlag;
	}

	public String getAdjustAmt() {
		return adjustAmt;
	}

	public String getErrNo() {
		return errNo;
	}

	public String getOrgActPrimaryKey() {
		return orgActPrimaryKey;
	}

	public String getOrgTxnPrimaryKey() {
		return orgTxnPrimaryKey;
	}

	public String getOrgBatNo() {
		return orgBatNo;
	}

	public String getRespCode() {
		return respCode;
	}

	public void setActPrimaryKey(String actPrimaryKey) {
		this.actPrimaryKey = actPrimaryKey;
	}

	public void setSettleDate(String settleDate) {
		this.settleDate = settleDate;
	}

	public void setTxnPrimaryKey(String txnPrimaryKey) {
		this.txnPrimaryKey = txnPrimaryKey;
	}

	public void setInsCode(String insCode) {
		this.insCode = insCode;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public void setBatNo(String batNo) {
		this.batNo = batNo;
	}

	public void setTxnDate(String txnDate) {
		this.txnDate = txnDate;
	}

	public void setTxnTime(String txnTime) {
		this.txnTime = txnTime;
	}

	public void setTransId(String transId) {
		this.transId = transId;
	}

	public void setTransChnl(String transChnl) {
		this.transChnl = transChnl;
	}

	public void setTxnRealAmt(String txnRealAmt) {
		this.txnRealAmt = txnRealAmt;
	}

	public void setTxnRealAmtType(String txnRealAmtType) {
		this.txnRealAmtType = txnRealAmtType;
	}

	public void setTxnAmt(String txnAmt) {
		this.txnAmt = txnAmt;
	}

	public void setTxnFee(String txnFee) {
		this.txnFee = txnFee;
	}

	public void setAccType(String accType) {
		this.accType = accType;
	}

	public void setAccTotalBal(String accTotalBal) {
		this.accTotalBal = accTotalBal;
	}

	public void setAccValidBal(String accValidBal) {
		this.accValidBal = accValidBal;
	}

	public void setCancelFlag(String cancelFlag) {
		this.cancelFlag = cancelFlag;
	}

	public void setRevsalFlag(String revsalFlag) {
		this.revsalFlag = revsalFlag;
	}

	public void setReturnFlag(String returnFlag) {
		this.returnFlag = returnFlag;
	}

	public void setReturnAmt(String returnAmt) {
		this.returnAmt = returnAmt;
	}

	public void setAdjustFlag(String adjustFlag) {
		this.adjustFlag = adjustFlag;
	}

	public void setAdjustAmt(String adjustAmt) {
		this.adjustAmt = adjustAmt;
	}

	public void setErrNo(String errNo) {
		this.errNo = errNo;
	}

	public void setOrgActPrimaryKey(String orgActPrimaryKey) {
		this.orgActPrimaryKey = orgActPrimaryKey;
	}

	public void setOrgTxnPrimaryKey(String orgTxnPrimaryKey) {
		this.orgTxnPrimaryKey = orgTxnPrimaryKey;
	}

	public void setOrgBatNo(String orgBatNo) {
		this.orgBatNo = orgBatNo;
	}

	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}
}
