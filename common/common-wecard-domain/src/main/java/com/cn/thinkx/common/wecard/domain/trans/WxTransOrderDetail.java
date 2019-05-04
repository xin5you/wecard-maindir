package com.cn.thinkx.common.wecard.domain.trans;

import com.cn.thinkx.common.wecard.domain.base.BaseDomain;

/**
 * 交易订单明细
 * 
 * @author zqy
 *
 */
public class WxTransOrderDetail extends BaseDomain {

	private static final long serialVersionUID = -5086714778373703680L;
	private String orderListId;
	private String orderKey;
	private String settleDate;
	private String txnFlowNo;
	private String OrgTxnFlowNo;
	private String transId;
	private String transSt;
	private String commodityName;
	private String commodityNum;
	private String transAmt;
	private String uploadAmt;
	private String respCode;
	private String signType;
	private String notifyType;
	private String notifyUrl;
	private String redirectType;
	private String redirectUrl;
	private String orderRespStat;
	private String additionalInfo;
	private String dataStat;

	public String getOrderListId() {
		return orderListId;
	}

	public String getOrderKey() {
		return orderKey;
	}

	public String getSettleDate() {
		return settleDate;
	}

	public String getTxnFlowNo() {
		return txnFlowNo;
	}

	public String getOrgTxnFlowNo() {
		return OrgTxnFlowNo;
	}

	public String getTransId() {
		return transId;
	}

	public String getTransSt() {
		return transSt;
	}

	public String getCommodityName() {
		return commodityName;
	}

	public String getCommodityNum() {
		return commodityNum;
	}

	public String getTransAmt() {
		return transAmt;
	}

	public String getUploadAmt() {
		return uploadAmt;
	}

	public String getRespCode() {
		return respCode;
	}

	public String getSignType() {
		return signType;
	}

	public String getNotifyType() {
		return notifyType;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public String getRedirectType() {
		return redirectType;
	}

	public String getRedirectUrl() {
		return redirectUrl;
	}

	public String getOrderRespStat() {
		return orderRespStat;
	}

	public String getAdditionalInfo() {
		return additionalInfo;
	}

	public String getDataStat() {
		return dataStat;
	}

	public void setOrderListId(String orderListId) {
		this.orderListId = orderListId;
	}

	public void setOrderKey(String orderKey) {
		this.orderKey = orderKey;
	}

	public void setSettleDate(String settleDate) {
		this.settleDate = settleDate;
	}

	public void setTxnFlowNo(String txnFlowNo) {
		this.txnFlowNo = txnFlowNo;
	}

	public void setOrgTxnFlowNo(String orgTxnFlowNo) {
		OrgTxnFlowNo = orgTxnFlowNo;
	}

	public void setTransId(String transId) {
		this.transId = transId;
	}

	public void setTransSt(String transSt) {
		this.transSt = transSt;
	}

	public void setCommodityName(String commodityName) {
		this.commodityName = commodityName;
	}

	public void setCommodityNum(String commodityNum) {
		this.commodityNum = commodityNum;
	}

	public void setTransAmt(String transAmt) {
		this.transAmt = transAmt;
	}

	public void setUploadAmt(String uploadAmt) {
		this.uploadAmt = uploadAmt;
	}

	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}

	public void setSignType(String signType) {
		this.signType = signType;
	}

	public void setNotifyType(String notifyType) {
		this.notifyType = notifyType;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public void setRedirectType(String redirectType) {
		this.redirectType = redirectType;
	}

	public void setRedirectUrl(String redirectUrl) {
		this.redirectUrl = redirectUrl;
	}

	public void setOrderRespStat(String orderRespStat) {
		this.orderRespStat = orderRespStat;
	}

	public void setAdditionalInfo(String additionalInfo) {
		this.additionalInfo = additionalInfo;
	}

	public void setDataStat(String dataStat) {
		this.dataStat = dataStat;
	}

	@Override
	public String toString() {
		return "WxTransOrderDetail [orderListId=" + orderListId + ", orderKey=" + orderKey + ", settleDate="
				+ settleDate + ", txnFlowNo=" + txnFlowNo + ", OrgTxnFlowNo=" + OrgTxnFlowNo + ", transId=" + transId
				+ ", transSt=" + transSt + ", commodityName=" + commodityName + ", commodityNum=" + commodityNum
				+ ", transAmt=" + transAmt + ", uploadAmt=" + uploadAmt + ", respCode=" + respCode + ", signType="
				+ signType + ", notifyType=" + notifyType + ", notifyUrl=" + notifyUrl + ", redirectType="
				+ redirectType + ", redirectUrl=" + redirectUrl + ", orderRespStat=" + orderRespStat
				+ ", additionalInfo=" + additionalInfo + ", dataStat=" + dataStat + "]";
	}

}
