package com.cn.thinkx.oms.module.cardkeys.model;

import com.cn.thinkx.pms.base.domain.BaseDomain;

/**
 * 出款订单信息
 * 
 * @author xiaomei
 *
 */
public class WithdrawOrder extends BaseDomain {

	/**
	 * 订单号
	 */
	private String orderId;
	/**
	 * 批次号
	 */
	private String batchNo;
	/**
	 * 用户ID
	 */
	private String userId;
	/**
	 * 出款总手续费
	 */
	private String totalFee;
	/**
	 * 出款总金额
	 */
	private String totalAmount;
	/**
	 * 出款笔数
	 */
	private String totalNum;
	/**
	 * 出款日期
	 */
	private String withdrawDate;
	/**
	 * 订单名称
	 */
	private String orderName;
	/**
	 * 订单状态
	 */
	private String stat;
	/**
	 * 代付机构
	 */
	private String paidIns;
	/**
	 * 代付流水
	 */
	private String paidId;
	/**
	 * 代付结果说明
	 */
	private String paidRespDesc;
	/**
	 * 出款成功金额
	 */
	private String successAmount;
	/**
	 * 出款成功笔数
	 */
	private String successNum;
	/**
	 * 出款总金额
	 */
	private String successFee;
	/**
	 * 出款总金额
	 */
	private String failAmount;
	/**
	 * 出款总金额
	 */
	private String failNum;
	/**
	 * 状态
	 */
	private String dataStat;

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(String totalFee) {
		this.totalFee = totalFee;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(String totalNum) {
		this.totalNum = totalNum;
	}

	public String getWithdrawDate() {
		return withdrawDate;
	}

	public void setWithdrawDate(String withdrawDate) {
		this.withdrawDate = withdrawDate;
	}

	public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	public String getStat() {
		return stat;
	}

	public void setStat(String stat) {
		this.stat = stat;
	}

	public String getPaidIns() {
		return paidIns;
	}

	public void setPaidIns(String paidIns) {
		this.paidIns = paidIns;
	}

	public String getPaidId() {
		return paidId;
	}

	public void setPaidId(String paidId) {
		this.paidId = paidId;
	}

	public String getPaidRespDesc() {
		return paidRespDesc;
	}

	public void setPaidRespDesc(String paidRespDesc) {
		this.paidRespDesc = paidRespDesc;
	}

	public String getSuccessAmount() {
		return successAmount;
	}

	public void setSuccessAmount(String successAmount) {
		this.successAmount = successAmount;
	}

	public String getSuccessNum() {
		return successNum;
	}

	public void setSuccessNum(String successNum) {
		this.successNum = successNum;
	}

	public String getSuccessFee() {
		return successFee;
	}

	public void setSuccessFee(String successFee) {
		this.successFee = successFee;
	}

	public String getFailAmount() {
		return failAmount;
	}

	public void setFailAmount(String failAmount) {
		this.failAmount = failAmount;
	}

	public String getFailNum() {
		return failNum;
	}

	public void setFailNum(String failNum) {
		this.failNum = failNum;
	}

	public String getDataStat() {
		return dataStat;
	}

	public void setDataStat(String dataStat) {
		this.dataStat = dataStat;
	}

	@Override
	public String toString() {
		return "WithdrawOrder [orderId=" + orderId + ", batchNo=" + batchNo + ", userId=" + userId + ", totalFee="
				+ totalFee + ", totalAmount=" + totalAmount + ", totalNum=" + totalNum + ", withdrawDate="
				+ withdrawDate + ", orderName=" + orderName + ", stat=" + stat + ", paidIns=" + paidIns + ", paidId="
				+ paidId + ", paidRespDesc=" + paidRespDesc + ", successAmount=" + successAmount + ", successNum="
				+ successNum + ", successFee=" + successFee + ", failAmount=" + failAmount + ", failNum=" + failNum
				+ ", dataStat=" + dataStat + "]";
	}

}
