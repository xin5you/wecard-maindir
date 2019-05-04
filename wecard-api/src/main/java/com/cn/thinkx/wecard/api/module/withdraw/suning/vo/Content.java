package com.cn.thinkx.wecard.api.module.withdraw.suning.vo;

import java.util.List;

/**
 * 代付回调参数
 * 
 * @author xiaomei
 *
 */
public class Content {

	private String batchNo;		//批次号
	private String merchantNo;	//付款方商户号
	private String dataSource;	//数据源
	private int totalNum;		//总笔数
	private long totalAmount;	//支付总金额
	private int successNum;		//成功笔数
	private long successAmount;	//成功总金额
	private int failNum;		//失败笔数
	private long failAmount;	//失败金额
	private long poundage;		//总手续费
	private List<TransferOrders> transferOrders;	//批次明细
	private String status;		//状态
	private String errorCode;	//错误码
	private String errorMsg;	//错误原因
	
	private String payMerchantno;	//付款方商户号
	
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public String getMerchantNo() {
		return merchantNo;
	}
	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}
	public String getDataSource() {
		return dataSource;
	}
	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}
	public int getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(int totalNum) {
		this.totalNum = totalNum;
	}
	public long getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(long totalAmount) {
		this.totalAmount = totalAmount;
	}
	public int getSuccessNum() {
		return successNum;
	}
	public void setSuccessNum(int successNum) {
		this.successNum = successNum;
	}
	public long getSuccessAmount() {
		return successAmount;
	}
	public void setSuccessAmount(long successAmount) {
		this.successAmount = successAmount;
	}
	public int getFailNum() {
		return failNum;
	}
	public void setFailNum(int failNum) {
		this.failNum = failNum;
	}
	public long getFailAmount() {
		return failAmount;
	}
	public void setFailAmount(long failAmount) {
		this.failAmount = failAmount;
	}
	public long getPoundage() {
		return poundage;
	}
	public void setPoundage(long poundage) {
		this.poundage = poundage;
	}
	public List<TransferOrders> getTransferOrders() {
		return transferOrders;
	}
	public void setTransferOrders(List<TransferOrders> transferOrders) {
		this.transferOrders = transferOrders;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getErrorCode() {
		return errorCode;
	}
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public String getPayMerchantno() {
		return payMerchantno;
	}
	public void setPayMerchantno(String payMerchantno) {
		this.payMerchantno = payMerchantno;
	}
	
}
