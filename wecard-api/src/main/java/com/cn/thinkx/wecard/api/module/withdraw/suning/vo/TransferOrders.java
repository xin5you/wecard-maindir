package com.cn.thinkx.wecard.api.module.withdraw.suning.vo;

/**
 * 代付回调参数
 * 
 * @author xiaomei
 *
 */
public class TransferOrders {

	private String serialNo;		//流水号
	private String receiverName;	//收款方名称
	private String receiverCardNo;	//收款方卡号
	private String receiverType;	//收款方类型
	private String bankName;		//开户行名
	private String bankCode;		//开户行编号
	private String payeeBankLinesNo;	//联行号
	private String bankProvince;	//开户行省
	private String bankCity;		//开户行市
	private long id;				//易付宝内部流水号
	private long amount;			//金额（分）
	private long poundage;			//费用
	private boolean success;		//成功标识
	private String payTime;			//完成时间
	private String errMessage;		//错误消息
	private String refundTicket;	//退票标识
	
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
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public long getAmount() {
		return amount;
	}
	public void setAmount(long amount) {
		this.amount = amount;
	}
	public long getPoundage() {
		return poundage;
	}
	public void setPoundage(long poundage) {
		this.poundage = poundage;
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getPayTime() {
		return payTime;
	}
	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}
	public String getErrMessage() {
		return errMessage;
	}
	public void setErrMessage(String errMessage) {
		this.errMessage = errMessage;
	}
	public String getRefundTicket() {
		return refundTicket;
	}
	public void setRefundTicket(String refundTicket) {
		this.refundTicket = refundTicket;
	}
	
}
