package com.cn.thinkx.common.wecard.domain.trans;

import java.io.Serializable;
import java.util.Date;





/**
 * 交易流水
 * 
 * @author pucker
 *
 */
public class WxTransLog implements Serializable {
	private static final long serialVersionUID = 1L;

	private String wxPrimaryKey;
	private String settleDate;
	private String orgWxPrimaryKey;
	private String dmsRelatedKey;
	private String orgDmsRelatedKey;
	private String transId;
	private int transSt;
	private String insCode;
	private String mchntCode;
	private String shopCode;
	private String sponsor;// 00 -- 客户微信平台 10 -- 商户微信平台
	private String operatorOpenId;
	private String respCode;
	private String priAcctNo;
	private String cardNo;
	private String userInfUserName;
	private String productCode;
	private String transAmt;
	private String uploadAmt;
	private String transCurrCd;
	private String transChnl;
	private String transFee; // 手续费
	private String transFeeType;
	private String tfrInAcctNo;
	private String tfrOutAccNo;
	private String additionalInfo;
	private String remarks;

	private String tableNum;
	private int orderNum;
	/**
	 * 创建人
	 */
	private String createUser;
	/**
	 * 更新人
	 */
	private String updateUser;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 更新时间
	 */
	private Date updateTime;
	
	/**订单ID**/
	private String orderId;
	
	/**外部订单ID**/
	private String orgOrderId;

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

	public String getOrgWxPrimaryKey() {
		return orgWxPrimaryKey;
	}

	public void setOrgWxPrimaryKey(String orgWxPrimaryKey) {
		this.orgWxPrimaryKey = orgWxPrimaryKey;
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

	public String getSponsor() {
		return sponsor;
	}

	public void setSponsor(String sponsor) {
		this.sponsor = sponsor;
	}

	public String getOperatorOpenId() {
		return operatorOpenId;
	}

	public void setOperatorOpenId(String operatorOpenId) {
		this.operatorOpenId = operatorOpenId;
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

	public String getTfrInAcctNo() {
		return tfrInAcctNo;
	}

	public void setTfrInAcctNo(String tfrInAcctNo) {
		this.tfrInAcctNo = tfrInAcctNo;
	}

	public String getTfrOutAccNo() {
		return tfrOutAccNo;
	}

	public void setTfrOutAccNo(String tfrOutAccNo) {
		this.tfrOutAccNo = tfrOutAccNo;
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

	public String getTableNum() {
		return tableNum;
	}

	public void setTableNum(String tableNum) {
		this.tableNum = tableNum;
	}

	public int getOrderNum() {
		return orderNum;
	}

	public void setOrderNum(int orderNum) {
		this.orderNum = orderNum;
	}

	public String getCreateUser() {
		return createUser;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getOrderId() {
		return orderId;
	}

	public String getOrgOrderId() {
		return orgOrderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public void setOrgOrderId(String orgOrderId) {
		this.orgOrderId = orgOrderId;
	}
}
