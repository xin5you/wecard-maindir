package com.cn.thinkx.oms.module.margin.model;

import java.util.Date;

import com.cn.thinkx.oms.base.model.BaseDomain;

/**
 * 商户保证金申请明细表
 * @author zqy
 *
 */
public class MerchantMarginList extends BaseDomain {
	private String marginListId;			
	private String 	chashId;	
	private String 	merchantId;	
	private String 	mortgageAmt;//押款金额
	private String 	mortgageFlg;
	private String 	addMortgageAmt;
	private String  getQuota; //押款金额获取额度
	private String 	rechargeAmt;//累计充值金额
	private String  rechargeFaceAmt; //累计充值面额
	private String  addGetQuota; //追加金额获取总额度
	private String 	applyUserId;//申请人id
	private String 	applyUserName; //申请人姓名
	private Date 	applyTime; //申请时间
	private String 	approveId;  //审核人
	private String  approveStat; //审核状态
	private String 	approveName; //审核人姓名
	private Date 	approveTime; //审核时间
	private String 	dataStat;
	
	
	private String confirmPaymentId; //押款确认人ID
	private String confirmPaymentName; //y押款确认姓名
	private Date   confirmPaymentTime;//押款确认时间

	
	private String[] approveStats; //
	
	private String mchntName;
	private String mchntCode;
	
	public String getMarginListId() {
		return marginListId;
	}
	public String getChashId() {
		return chashId;
	}
	public String getMerchantId() {
		return merchantId;
	}
	public String getMortgageAmt() {
		return mortgageAmt;
	}

	public String getMortgageFlg() {
		return mortgageFlg;
	}
	public String getAddMortgageAmt() {
		return addMortgageAmt;
	}
	public String getApproveStat() {
		return approveStat;
	}
	public String getApplyUserId() {
		return applyUserId;
	}
	public String getApplyUserName() {
		return applyUserName;
	}
	public Date getApplyTime() {
		return applyTime;
	}
	public String getApproveId() {
		return approveId;
	}
	public String getApproveName() {
		return approveName;
	}
	public Date getApproveTime() {
		return approveTime;
	}
	public String getDataStat() {
		return dataStat;
	}
	public void setMarginListId(String marginListId) {
		this.marginListId = marginListId;
	}
	public void setChashId(String chashId) {
		this.chashId = chashId;
	}
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}
	public void setMortgageAmt(String mortgageAmt) {
		this.mortgageAmt = mortgageAmt;
	}

	public void setMortgageFlg(String mortgageFlg) {
		this.mortgageFlg = mortgageFlg;
	}
	public void setAddMortgageAmt(String addMortgageAmt) {
		this.addMortgageAmt = addMortgageAmt;
	}
	public void setApproveStat(String approveStat) {
		this.approveStat = approveStat;
	}
	public void setApplyUserId(String applyUserId) {
		this.applyUserId = applyUserId;
	}
	public void setApplyUserName(String applyUserName) {
		this.applyUserName = applyUserName;
	}
	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}
	public void setApproveId(String approveId) {
		this.approveId = approveId;
	}
	public void setApproveName(String approveName) {
		this.approveName = approveName;
	}
	public void setApproveTime(Date approveTime) {
		this.approveTime = approveTime;
	}
	public void setDataStat(String dataStat) {
		this.dataStat = dataStat;
	}
	public String getGetQuota() {
		return getQuota;
	}
	public String getRechargeFaceAmt() {
		return rechargeFaceAmt;
	}
	public void setGetQuota(String getQuota) {
		this.getQuota = getQuota;
	}
	public void setRechargeFaceAmt(String rechargeFaceAmt) {
		this.rechargeFaceAmt = rechargeFaceAmt;
	}
	public String getAddGetQuota() {
		return addGetQuota;
	}
	public void setAddGetQuota(String addGetQuota) {
		this.addGetQuota = addGetQuota;
	}
	public String getRechargeAmt() {
		return rechargeAmt;
	}
	public void setRechargeAmt(String rechargeAmt) {
		this.rechargeAmt = rechargeAmt;
	}
	public String getMchntName() {
		return mchntName;
	}
	public String getMchntCode() {
		return mchntCode;
	}
	public void setMchntName(String mchntName) {
		this.mchntName = mchntName;
	}
	public void setMchntCode(String mchntCode) {
		this.mchntCode = mchntCode;
	}
	public String[] getApproveStats() {
		return approveStats;
	}
	public void setApproveStats(String[] approveStats) {
		this.approveStats = approveStats;
	}
	public String getConfirmPaymentId() {
		return confirmPaymentId;
	}
	public String getConfirmPaymentName() {
		return confirmPaymentName;
	}
	public Date getConfirmPaymentTime() {
		return confirmPaymentTime;
	}
	public void setConfirmPaymentId(String confirmPaymentId) {
		this.confirmPaymentId = confirmPaymentId;
	}
	public void setConfirmPaymentName(String confirmPaymentName) {
		this.confirmPaymentName = confirmPaymentName;
	}
	public void setConfirmPaymentTime(Date confirmPaymentTime) {
		this.confirmPaymentTime = confirmPaymentTime;
	}
}
