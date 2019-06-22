package com.cn.thinkx.oms.module.enterpriseorder.model;

import com.cn.thinkx.pms.base.domain.BaseDomain;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 出款订单明细信息
 * 
 * @author xiaomei
 *
 */
public class BatchWithdrawOrderDetail extends BaseDomain {

	/**
	 * 明细ID
	 */
	private String detailId;
	/**
	 * 订单号
	 */
	private String orderId;
	/**
	 * 出款流水号
	 */
	private String serialNo;
	/**
	 * 收款方名称
	 */
	private String receiverName;
	/**
	 * 收款方银行卡号
	 */
	private String receiverCardNo;
	/**
	 * 收款方类型
	 */
	private String receiverType;
	/**
	 * 银行卡类型
	 */
	private String bankType;
	/**
	 * 开户行名称
	 */
	private String bankName;
	/**
	 * 开户行编号
	 */
	private String bankCode;
	/**
	 * 联行号
	 */
	private String payeeBankLinesNo;
	/**
	 * 开户行省
	 */
	private String bankProvince;
	/**
	 * 开户行市
	 */
	private String bankCity;

	/**
	 *
	 */
	private String branchAddress;
	/**
	 * 出款机构内部流水号
	 */
	private String dmsSerialNo;
	/**
	 * 金额
	 */
	private BigDecimal amount;
	/**
	 * 手续费
	 */
	private BigDecimal fee;
	/**
	 * 处理返回码
	 */
	private String respCode;
	/**
	 * 处理返回信息
	 */
	private String respMsg;
	/**
	 * 出款完成时间
	 */
	private Date payTime;

	private String payChanel;
	/**
	 * 状态
	 */
	private String dataStat;

	public String getDetailId() {
		return detailId;
	}

	public void setDetailId(String detailId) {
		this.detailId = detailId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getSerialNo() {
		return serialNo;
	}

	public void setSerialNo(String serialNo) {
		this.serialNo = serialNo;
	}

	public String getReceiverName() {
		return receiverName;
	}

	public void setReceiverName(String receiverName) {
		this.receiverName = receiverName;
	}

	public String getReceiverCardNo() {
		return receiverCardNo;
	}

	public void setReceiverCardNo(String receiverCardNo) {
		this.receiverCardNo = receiverCardNo;
	}

	public String getReceiverType() {
		return receiverType;
	}

	public void setReceiverType(String receiverType) {
		this.receiverType = receiverType;
	}

	public String getBankType() {
		return bankType;
	}

	public void setBankType(String bankType) {
		this.bankType = bankType;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getPayeeBankLinesNo() {
		return payeeBankLinesNo;
	}

	public void setPayeeBankLinesNo(String payeeBankLinesNo) {
		this.payeeBankLinesNo = payeeBankLinesNo;
	}

	public String getBankProvince() {
		return bankProvince;
	}

	public void setBankProvince(String bankProvince) {
		this.bankProvince = bankProvince;
	}

	public String getBankCity() {
		return bankCity;
	}

	public void setBankCity(String bankCity) {
		this.bankCity = bankCity;
	}

	public String getDmsSerialNo() {
		return dmsSerialNo;
	}

	public void setDmsSerialNo(String dmsSerialNo) {
		this.dmsSerialNo = dmsSerialNo;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}

	public String getRespCode() {
		return respCode;
	}

	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}

	public String getRespMsg() {
		return respMsg;
	}

	public void setRespMsg(String respMsg) {
		this.respMsg = respMsg;
	}

	public Date getPayTime() {
		return payTime;
	}

	public void setPayTime(Date payTime) {
		this.payTime = payTime;
	}

	public String getPayChanel() {
		return payChanel;
	}

	public void setPayChanel(String payChanel) {
		this.payChanel = payChanel;
	}

	public String getDataStat() {
		return dataStat;
	}

	public void setDataStat(String dataStat) {
		this.dataStat = dataStat;
	}

	public String getBranchAddress() {
		return branchAddress;
	}

	public void setBranchAddress(String branchAddress) {
		this.branchAddress = branchAddress;
	}

	@Override
	public String toString() {
		return "BatchWithdrawOrderDetail{" +
				"detailId='" + detailId + '\'' +
				", orderId='" + orderId + '\'' +
				", serialNo='" + serialNo + '\'' +
				", receiverName='" + receiverName + '\'' +
				", receiverCardNo='" + receiverCardNo + '\'' +
				", receiverType='" + receiverType + '\'' +
				", bankType='" + bankType + '\'' +
				", bankName='" + bankName + '\'' +
				", bankCode='" + bankCode + '\'' +
				", payeeBankLinesNo='" + payeeBankLinesNo + '\'' +
				", bankProvince='" + bankProvince + '\'' +
				", bankCity='" + bankCity + '\'' +
				", branchAddress='" + branchAddress + '\'' +
				", dmsSerialNo='" + dmsSerialNo + '\'' +
				", amount=" + amount +
				", fee=" + fee +
				", respCode='" + respCode + '\'' +
				", respMsg='" + respMsg + '\'' +
				", payTime='" + payTime + '\'' +
				", payChanel='" + payChanel + '\'' +
				", dataStat='" + dataStat + '\'' +
				"} " + super.toString();
	}
}
