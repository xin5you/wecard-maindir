package com.cn.thinkx.pms.connect.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class TxnPackageDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 20160316L;
	// 交易编号
	private String txnCode;
	// 渠道号
	private String channel;
	// 响应码
	private String rspCode;
	// 终端号
	private String termId;
	// 收单机构号
	private String consumerCode;
	// 商户号
	private String merchantCode;
	// 商户名称
	private String merchantName;
	// 门店号
	private String shopCode;
	// 订单号
	private String orderId;
	// 交易日期
	private String txnDate;
	// 交易时间
	private String txnTime;
	// 转出卡号
	private String outCard;
	// 转出账号
	private String outAccount;
	// 转入卡号
	private String intoCard;
	// 转入账号
	private String intoAccount;
	// 金额
	private String amount;
	// 原金额
	private String origAmount;
	// 币种
	private String curType;
	// 支付渠道
	private String payChannel;
	// 卡号
	private String cardNO;
	// 卡密码
	private String pin;
	// 账户
	private String account;
	// 订货人
	private String cardHolder;
	// 商品信息
	private String productInfo;
	// 附加信息
	private String remark;
	// 商户后台返回URL
	private String merchantURL;
	// 清算日期
	private String settleDate;
	// 交易流水号
	private String txnNo;
	// 原交易流水号
	private String origTxnNo;
	// 交易类型
	private String txnType;
	// 原交易类型
	private String oldTxnType;
	// 交易状态
	private String txnState;
	// 中心流水号
	private String sequenceNo;
	// 类型
	private String type;
	// 标志
	private String flag;
	// 总行数
	private String totalRow;
	// 实际行数
	private String realRow;
	// 调整类型
	private String adjustType;

	// 查询起始日期
	private String start_dt;
	// 查询结束日期
	private String end_dt;
	// 起始行
	private String start_row;
	// 结束行
	private String end_row;

	// 服务费
	private String srvFee;

	// 新加卡bin值
	private String binValue;

	// 操作人员ID
	private String operaterId;

	// 公共list<map>对象
	private List<Map<?, ?>> list;

	// 返回结果集
	private String otherData;

	// 网关消费渠道
	private String payChnl;

	private String serviceFee;

	/**
	 * 中小微批量充值将发送至TXN,增加批量相关属性
	 */
	// 批次号
	private String batchNo;
	// 批量信息
	private String batchFileInfo;

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getBatchFileInfo() {
		return batchFileInfo;
	}

	public void setBatchFileInfo(String batchFileInfo) {
		this.batchFileInfo = batchFileInfo;
	}

	public String getPayChnl() {
		return payChnl;
	}

	public void setPayChnl(String payChnl) {
		this.payChnl = payChnl;
	}

	public String getOtherData() {
		return otherData;
	}

	public void setOtherData(String otherData) {
		this.otherData = otherData;
	}

	public String getAdjustType() {
		return adjustType;
	}

	public void setAdjustType(String adjustType) {
		this.adjustType = adjustType;
	}

	public String getSrvFee() {
		return srvFee;
	}

	public void setSrvFee(String srvFee) {
		this.srvFee = srvFee;
	}

	public String getTxnCode() {
		return txnCode;
	}

	public void setTxnCode(String txnCode) {
		this.txnCode = txnCode;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getRspCode() {
		return rspCode;
	}

	public void setRspCode(String rspCode) {
		this.rspCode = rspCode;
	}

	public String getConsumerCode() {
		return consumerCode;
	}

	public void setConsumerCode(String consumerCode) {
		this.consumerCode = consumerCode;
	}

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getShopCode() {
		return shopCode;
	}

	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getTxnDate() {
		return txnDate;
	}

	public void setTxnDate(String txnDate) {
		this.txnDate = txnDate;
	}

	public String getTxnTime() {
		return txnTime;
	}

	public void setTxnTime(String txnTime) {
		this.txnTime = txnTime;
	}

	public String getOutCard() {
		return outCard;
	}

	public void setOutCard(String outCard) {
		this.outCard = outCard;
	}

	public String getOutAccount() {
		return outAccount;
	}

	public void setOutAccount(String outAccount) {
		this.outAccount = outAccount;
	}

	public String getIntoCard() {
		return intoCard;
	}

	public void setIntoCard(String intoCard) {
		this.intoCard = intoCard;
	}

	public String getIntoAccount() {
		return intoAccount;
	}

	public void setIntoAccount(String intoAccount) {
		this.intoAccount = intoAccount;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getOrigAmount() {
		return origAmount;
	}

	public void setOrigAmount(String origAmount) {
		this.origAmount = origAmount;
	}

	public String getCurType() {
		return curType;
	}

	public void setCurType(String curType) {
		this.curType = curType;
	}

	public String getCardNO() {
		return cardNO;
	}

	public void setCardNO(String cardNO) {
		this.cardNO = cardNO;
	}

	public String getPin() {
		return pin;
	}

	public void setPin(String pin) {
		this.pin = pin;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getCardHolder() {
		return cardHolder;
	}

	public void setCardHolder(String cardHolder) {
		this.cardHolder = cardHolder;
	}

	public String getProductInfo() {
		return productInfo;
	}

	public void setProductInfo(String productInfo) {
		this.productInfo = productInfo;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getMerchantURL() {
		return merchantURL;
	}

	public void setMerchantURL(String merchantURL) {
		this.merchantURL = merchantURL;
	}

	public String getSettleDate() {
		return settleDate;
	}

	public void setSettleDate(String settleDate) {
		this.settleDate = settleDate;
	}

	public String getTxnNo() {
		return txnNo;
	}

	public void setTxnNo(String txnNo) {
		this.txnNo = txnNo;
	}

	public String getOrigTxnNo() {
		return origTxnNo;
	}

	public void setOrigTxnNo(String origTxnNo) {
		this.origTxnNo = origTxnNo;
	}

	public String getTxnType() {
		return txnType;
	}

	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}

	public String getOldTxnType() {
		return oldTxnType;
	}

	public void setOldTxnType(String oldTxnType) {
		this.oldTxnType = oldTxnType;
	}

	public String getTxnState() {
		return txnState;
	}

	public void setTxnState(String txnState) {
		this.txnState = txnState;
	}

	public String getSequenceNo() {
		return sequenceNo;
	}

	public void setSequenceNo(String sequenceNo) {
		this.sequenceNo = sequenceNo;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getTotalRow() {
		return totalRow;
	}

	public void setTotalRow(String totalRow) {
		this.totalRow = totalRow;
	}

	public String getRealRow() {
		return realRow;
	}

	public void setRealRow(String realRow) {
		this.realRow = realRow;
	}

	public List<Map<?, ?>> getList() {
		return list;
	}

	public void setList(List<Map<?, ?>> list) {
		this.list = list;
	}

	public String getPayChannel() {
		return payChannel;
	}

	public void setPayChannel(String payChannel) {
		this.payChannel = payChannel;
	}

	public String getStart_dt() {
		return start_dt;
	}

	public void setStart_dt(String startDt) {
		start_dt = startDt;
	}

	public String getEnd_dt() {
		return end_dt;
	}

	public void setEnd_dt(String endDt) {
		end_dt = endDt;
	}

	public void setStart_row(String startRow) {
		start_row = startRow;
	}

	public String getEnd_row() {
		return end_row;
	}

	public void setEnd_row(String endRow) {
		end_row = endRow;
	}

	public String getStart_row() {
		return start_row;
	}

	public String getTermId() {
		return termId;
	}

	public void setTermId(String termId) {
		this.termId = termId;
	}

	public String getBinValue() {
		return binValue;
	}

	public void setBinValue(String binValue) {
		this.binValue = binValue;
	}

	public String getOperaterId() {
		return operaterId;
	}

	public void setOperaterId(String operaterId) {
		this.operaterId = operaterId;
	}

	public String getServiceFee() {
		return serviceFee;
	}

	public void setServiceFee(String serviceFee) {
		this.serviceFee = serviceFee;
	}

}
