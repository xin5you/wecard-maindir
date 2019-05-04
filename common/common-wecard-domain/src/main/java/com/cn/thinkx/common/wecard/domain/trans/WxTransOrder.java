package com.cn.thinkx.common.wecard.domain.trans;

import com.cn.thinkx.common.wecard.domain.base.BaseDomain;

/**
 * 交易订单表
 * 
 * @author zqy
 *
 */
public class WxTransOrder extends BaseDomain {

	private static final long serialVersionUID = -717153142694764226L;
	private String orderKey;
	private String orgOrderKey;
	private String dmsRelatedKey;
	private String orgDmsRelatedKey;
	private String insCode;
	private String mchntCode;
	private String shopCode;
	private String sponsor;
	private String userId;
	private String priAcctNo;
	private String cardNo;
	private String userInfUserName;
	private String productCode;
	private String transCurrCd;
	private String transChnl;
	private String orderStat;
	private String orderAmt;
	private String transFee;
	private String transFeeType;
	private String tfrInAcctNo;
	private String tfrOutAcctNo;
	private String dataStat;

	private WxTransOrderDetail orderDetail;

	public String getOrderKey() {
		return orderKey;
	}

	public String getOrgOrderKey() {
		return orgOrderKey;
	}

	public String getDmsRelatedKey() {
		return dmsRelatedKey;
	}

	public String getOrgDmsRelatedKey() {
		return orgDmsRelatedKey;
	}

	public String getInsCode() {
		return insCode;
	}

	public String getMchntCode() {
		return mchntCode;
	}

	public String getShopCode() {
		return shopCode;
	}

	public String getSponsor() {
		return sponsor;
	}

	public String getUserId() {
		return userId;
	}

	public String getPriAcctNo() {
		return priAcctNo;
	}

	public String getCardNo() {
		return cardNo;
	}

	public String getUserInfUserName() {
		return userInfUserName;
	}

	public String getProductCode() {
		return productCode;
	}

	public String getTransCurrCd() {
		return transCurrCd;
	}

	public String getTransChnl() {
		return transChnl;
	}

	public String getOrderAmt() {
		return orderAmt;
	}

	public String getTransFee() {
		return transFee;
	}

	public String getTransFeeType() {
		return transFeeType;
	}

	public String getTfrInAcctNo() {
		return tfrInAcctNo;
	}

	public String getTfrOutAcctNo() {
		return tfrOutAcctNo;
	}

	public String getDataStat() {
		return dataStat;
	}

	public WxTransOrderDetail getOrderDetail() {
		return orderDetail;
	}

	public void setOrderKey(String orderKey) {
		this.orderKey = orderKey;
	}

	public void setOrgOrderKey(String orgOrderKey) {
		this.orgOrderKey = orgOrderKey;
	}

	public void setDmsRelatedKey(String dmsRelatedKey) {
		this.dmsRelatedKey = dmsRelatedKey;
	}

	public void setOrgDmsRelatedKey(String orgDmsRelatedKey) {
		this.orgDmsRelatedKey = orgDmsRelatedKey;
	}

	public void setInsCode(String insCode) {
		this.insCode = insCode;
	}

	public void setMchntCode(String mchntCode) {
		this.mchntCode = mchntCode;
	}

	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}

	public void setSponsor(String sponsor) {
		this.sponsor = sponsor;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setPriAcctNo(String priAcctNo) {
		this.priAcctNo = priAcctNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public void setUserInfUserName(String userInfUserName) {
		this.userInfUserName = userInfUserName;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public void setTransCurrCd(String transCurrCd) {
		this.transCurrCd = transCurrCd;
	}

	public void setTransChnl(String transChnl) {
		this.transChnl = transChnl;
	}

	public void setOrderAmt(String orderAmt) {
		this.orderAmt = orderAmt;
	}

	public void setTransFee(String transFee) {
		this.transFee = transFee;
	}

	public void setTransFeeType(String transFeeType) {
		this.transFeeType = transFeeType;
	}

	public void setTfrInAcctNo(String tfrInAcctNo) {
		this.tfrInAcctNo = tfrInAcctNo;
	}

	public void setTfrOutAcctNo(String tfrOutAcctNo) {
		this.tfrOutAcctNo = tfrOutAcctNo;
	}

	public void setDataStat(String dataStat) {
		this.dataStat = dataStat;
	}

	public void setOrderDetail(WxTransOrderDetail orderDetail) {
		this.orderDetail = orderDetail;
	}

	public String getOrderStat() {
		return orderStat;
	}

	public void setOrderStat(String orderStat) {
		this.orderStat = orderStat;
	}

	@Override
	public String toString() {
		return "WxTransOrder [orderKey=" + orderKey + ", orgOrderKey=" + orgOrderKey + ", dmsRelatedKey="
				+ dmsRelatedKey + ", orgDmsRelatedKey=" + orgDmsRelatedKey + ", insCode=" + insCode + ", mchntCode="
				+ mchntCode + ", shopCode=" + shopCode + ", sponsor=" + sponsor + ", userId=" + userId + ", priAcctNo="
				+ priAcctNo + ", cardNo=" + cardNo + ", userInfUserName=" + userInfUserName + ", productCode="
				+ productCode + ", transCurrCd=" + transCurrCd + ", transChnl=" + transChnl + ", orderStat=" + orderStat
				+ ", orderAmt=" + orderAmt + ", transFee=" + transFee + ", transFeeType=" + transFeeType
				+ ", tfrInAcctNo=" + tfrInAcctNo + ", tfrOutAcctNo=" + tfrOutAcctNo + ", dataStat=" + dataStat
				+ orderDetail.toString() + "]";
	}

}
