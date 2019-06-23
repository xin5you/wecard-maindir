package com.cn.thinkx.oms.module.enterpriseorder.model;

import com.cn.thinkx.pms.base.domain.BaseDomain;

import java.math.BigDecimal;

/**
 * 出款订单信息
 * 
 * @author xiaomei
 *
 */
public class BatchWithdrawOrder extends BaseDomain {

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
	private BigDecimal totalFee;
	/**
	 * 出款总金额
	 */
	private BigDecimal totalAmount;
	/**
	 * 出款笔数
	 */
	private Integer totalNum;
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
	private BigDecimal successAmount;
	/**
	 * 出款成功笔数
	 */
	private Integer successNum;
	/**
	 * 出款总金额
	 */
	private BigDecimal successFee;
	/**
	 * 出款总金额
	 */
	private BigDecimal failAmount;
	/**
	 * 出款总金额
	 */
	private Integer failNum;
	/**
	 * 状态
	 */
	private String dataStat;

	private String startTime;

	private String endTime;

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

	public BigDecimal getTotalFee() {
		return totalFee;
	}

	public void setTotalFee(BigDecimal totalFee) {
		this.totalFee = totalFee;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Integer getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(Integer totalNum) {
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

	public BigDecimal getSuccessAmount() {
		return successAmount;
	}

	public void setSuccessAmount(BigDecimal successAmount) {
		this.successAmount = successAmount;
	}

	public Integer getSuccessNum() {
		return successNum;
	}

	public void setSuccessNum(Integer successNum) {
		this.successNum = successNum;
	}

	public BigDecimal getSuccessFee() {
		return successFee;
	}

	public void setSuccessFee(BigDecimal successFee) {
		this.successFee = successFee;
	}

	public BigDecimal getFailAmount() {
		return failAmount;
	}

	public void setFailAmount(BigDecimal failAmount) {
		this.failAmount = failAmount;
	}

	public Integer getFailNum() {
		return failNum;
	}

	public void setFailNum(Integer failNum) {
		this.failNum = failNum;
	}

	public String getDataStat() {
		return dataStat;
	}

	public void setDataStat(String dataStat) {
		this.dataStat = dataStat;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	@Override
	public String toString() {
		return "BatchWithdrawOrder{" +
				"orderId='" + orderId + '\'' +
				", batchNo='" + batchNo + '\'' +
				", userId='" + userId + '\'' +
				", totalFee=" + totalFee +
				", totalAmount=" + totalAmount +
				", totalNum=" + totalNum +
				", withdrawDate='" + withdrawDate + '\'' +
				", orderName='" + orderName + '\'' +
				", stat='" + stat + '\'' +
				", paidIns='" + paidIns + '\'' +
				", paidId='" + paidId + '\'' +
				", paidRespDesc='" + paidRespDesc + '\'' +
				", successAmount=" + successAmount +
				", successNum=" + successNum +
				", successFee=" + successFee +
				", failAmount='" + failAmount + '\'' +
				", failNum=" + failNum +
				", dataStat='" + dataStat + '\'' +
				"} " + super.toString();
	}
}
