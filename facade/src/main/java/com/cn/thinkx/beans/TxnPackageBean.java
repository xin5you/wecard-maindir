package com.cn.thinkx.beans;

import java.io.Serializable;

/**
 * 交易核心报文封装类
 * @author pucker
 *
 */
public class TxnPackageBean implements Serializable {

	private static final long serialVersionUID = 3014841969681739205L;
	/**
	 * 数据头
	 */
	private String dataHead;
	/**
	 * 交易类型
	 */
	private String txnType;
	/**
	 * 交易日期
	 */
	private String swtTxnDate;
	/**
	 * 交易时间
	 */
	private String swtTxnTime;
	/**
	 * 清算日期
	 */
	private String swtSettleDate;
	/**
	 * 渠道号
	 */
	private String channel;
	/**
	 * 批次号
	 */
	private String swtBatchNo;
	/**
	 * 流水号
	 */
	private String swtFlowNo;
	/**
	 * 接受交易日期
	 */
	private String recTxnDate;
	/**
	 * 接受交易时间
	 */
	private String recTxnTime;
	/**
	 * 接受交易批次号
	 */
	private String recBatchNo;
	/**
	 * 接受交易流水号
	 */
	private String recFlowNo;
	/**
	 * 机构渠道号
	 */
	private String issChannel;
	/**
	 * 商户号
	 */
	private String innerMerchantNo;
	/**
	 * 门店号
	 */
	private String innerShopNo;
	/**
	 * 终端号
	 */
	private String innerPosNo;
	/**
	 * 二磁信息
	 */
	private String track2;
	/**
	 * 三磁信息
	 */
	private String track3;
	/**
	 * 卡号
	 */
	private String cardNo;
	/**
	 * CVV2
	 */
	private String cvv2;
	/**
	 * 有效期
	 */
	private String expDate;
	/**
	 * 账户类型
	 */
	private String accType;
	/**
	 * 交易金额
	 */
	private String txnAmount;
	/**
	 * 手续费
	 */
	private String cardHolderFee;
	/**
	 * 余额
	 */
	private String balance;
	/**
	 * 查询密码
	 */
	private String pinQuiry;
	/**
	 * 新的查询密码
	 */
	private String pinQuiryNew;
	/**
	 * 交易密码
	 */
	private String pinTxn;
	/**
	 * 新的交易密码
	 */
	private String pinTxnNew;
	/**
	 * 返回码
	 */
	private String respCode;
	/**
	 * 授权码
	 */
	private String authCode;
	/**
	 * 交易参考号
	 */
	private String referenceNo;
	/**
	 * 原交易批次号
	 */
	private String oriSwtBatchNo;
	/**
	 * 原交易流水号
	 */
	private String oriSwtFlowNo;
	/**
	 * 接受交易原批次号
	 */
	private String oriRecBatchNo;
	/**
	 * 接受交易原流水号
	 */
	private String oriRecFlowNo;
	/**
	 * 原交易金额
	 */
	private String oriTxnAmount;
	/**
	 * 原交易手续费
	 */
	private String oriCardHolderFee;
	/**
	 * 文件路径
	 */
	private String filepath;
	/**
	 * 备用1
	 */
	private String resv1;
	/**
	 * 备用2
	 */
	private String resv2;
	/**
	 * 备用3
	 */
	private String resv3;
	/**
	 * 备用4
	 */
	private String resv4;
	/**
	 * 备用5
	 */
	private String resv5;
	/**
	 * 备用6
	 */
	private String resv6;  //1:不验证密码
	/**
	 * 备用7
	 */
	private String resv7;
	/**
	 * Mac验证码
	 */
	private String mac;
	/**
	 * 其他
	 */
	private String otherData;
	
	/**
	 *  用户id
	 */
	private String userId;
	
	/**
	 *  时间戳（作为交易签名验证参数
	 */
	private long timestamp;
	
	/**
	 * 签名
	 */
	private String signature;
	
	public String getDataHead() {
		return dataHead;
	}

	public void setDataHead(String dataHead) {
		this.dataHead = dataHead;
	}

	public String getTxnType() {
		return txnType;
	}

	public void setTxnType(String txnType) {
		this.txnType = txnType;
	}

	public String getSwtTxnDate() {
		return swtTxnDate;
	}

	public void setSwtTxnDate(String swtTxnDate) {
		this.swtTxnDate = swtTxnDate;
	}

	public String getSwtTxnTime() {
		return swtTxnTime;
	}

	public void setSwtTxnTime(String swtTxnTime) {
		this.swtTxnTime = swtTxnTime;
	}

	public String getSwtSettleDate() {
		return swtSettleDate;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public void setSwtSettleDate(String swtSettleDate) {
		this.swtSettleDate = swtSettleDate;
	}

	public String getSwtBatchNo() {
		return swtBatchNo;
	}

	public void setSwtBatchNo(String swtBatchNo) {
		this.swtBatchNo = swtBatchNo;
	}

	public String getSwtFlowNo() {
		return swtFlowNo;
	}

	public void setSwtFlowNo(String swtFlowNo) {
		this.swtFlowNo = swtFlowNo;
	}

	public String getRecTxnDate() {
		return recTxnDate;
	}

	public void setRecTxnDate(String recTxnDate) {
		this.recTxnDate = recTxnDate;
	}

	public String getRecTxnTime() {
		return recTxnTime;
	}

	public void setRecTxnTime(String recTxnTime) {
		this.recTxnTime = recTxnTime;
	}

	public String getRecBatchNo() {
		return recBatchNo;
	}

	public void setRecBatchNo(String recBatchNo) {
		this.recBatchNo = recBatchNo;
	}

	public String getRecFlowNo() {
		return recFlowNo;
	}

	public void setRecFlowNo(String recFlowNo) {
		this.recFlowNo = recFlowNo;
	}

	public String getIssChannel() {
		return issChannel;
	}

	public void setIssChannel(String issChannel) {
		this.issChannel = issChannel;
	}

	public String getInnerMerchantNo() {
		return innerMerchantNo;
	}

	public void setInnerMerchantNo(String innerMerchantNo) {
		this.innerMerchantNo = innerMerchantNo;
	}

	public String getInnerShopNo() {
		return innerShopNo;
	}

	public void setInnerShopNo(String innerShopNo) {
		this.innerShopNo = innerShopNo;
	}

	public String getInnerPosNo() {
		return innerPosNo;
	}

	public void setInnerPosNo(String innerPosNo) {
		this.innerPosNo = innerPosNo;
	}

	public String getTrack2() {
		return track2;
	}

	public void setTrack2(String track2) {
		this.track2 = track2;
	}

	public String getTrack3() {
		return track3;
	}

	public void setTrack3(String track3) {
		this.track3 = track3;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getCvv2() {
		return cvv2;
	}

	public void setCvv2(String cvv2) {
		this.cvv2 = cvv2;
	}

	public String getExpDate() {
		return expDate;
	}

	public void setExpDate(String expDate) {
		this.expDate = expDate;
	}

	public String getAccType() {
		return accType;
	}

	public void setAccType(String accType) {
		this.accType = accType;
	}

	public String getTxnAmount() {
		return txnAmount;
	}

	public void setTxnAmount(String txnAmount) {
		this.txnAmount = txnAmount;
	}

	public String getCardHolderFee() {
		return cardHolderFee;
	}

	public void setCardHolderFee(String cardHolderFee) {
		this.cardHolderFee = cardHolderFee;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}

	public String getPinQuiry() {
		return pinQuiry;
	}

	public void setPinQuiry(String pinQuiry) {
		this.pinQuiry = pinQuiry;
	}

	public String getPinQuiryNew() {
		return pinQuiryNew;
	}

	public void setPinQuiryNew(String pinQuiryNew) {
		this.pinQuiryNew = pinQuiryNew;
	}

	public String getPinTxn() {
		return pinTxn;
	}

	public void setPinTxn(String pinTxn) {
		this.pinTxn = pinTxn;
	}

	public String getPinTxnNew() {
		return pinTxnNew;
	}

	public void setPinTxnNew(String pinTxnNew) {
		this.pinTxnNew = pinTxnNew;
	}

	public String getRespCode() {
		return respCode;
	}

	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}

	public String getAuthCode() {
		return authCode;
	}

	public void setAuthCode(String authCode) {
		this.authCode = authCode;
	}

	public String getReferenceNo() {
		return referenceNo;
	}

	public void setReferenceNo(String referenceNo) {
		this.referenceNo = referenceNo;
	}

	public String getOriSwtBatchNo() {
		return oriSwtBatchNo;
	}

	public void setOriSwtBatchNo(String oriSwtBatchNo) {
		this.oriSwtBatchNo = oriSwtBatchNo;
	}

	public String getOriSwtFlowNo() {
		return oriSwtFlowNo;
	}

	public void setOriSwtFlowNo(String oriSwtFlowNo) {
		this.oriSwtFlowNo = oriSwtFlowNo;
	}

	public String getOriRecBatchNo() {
		return oriRecBatchNo;
	}

	public void setOriRecBatchNo(String oriRecBatchNo) {
		this.oriRecBatchNo = oriRecBatchNo;
	}

	public String getOriRecFlowNo() {
		return oriRecFlowNo;
	}

	public void setOriRecFlowNo(String oriRecFlowNo) {
		this.oriRecFlowNo = oriRecFlowNo;
	}

	public String getOriTxnAmount() {
		return oriTxnAmount;
	}

	public void setOriTxnAmount(String oriTxnAmount) {
		this.oriTxnAmount = oriTxnAmount;
	}

	public String getOriCardHolderFee() {
		return oriCardHolderFee;
	}

	public void setOriCardHolderFee(String oriCardHolderFee) {
		this.oriCardHolderFee = oriCardHolderFee;
	}

	public String getFilepath() {
		return filepath;
	}

	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}

	public String getResv1() {
		return resv1;
	}

	public void setResv1(String resv1) {
		this.resv1 = resv1;
	}

	public String getResv2() {
		return resv2;
	}

	public void setResv2(String resv2) {
		this.resv2 = resv2;
	}

	public String getResv3() {
		return resv3;
	}

	public void setResv3(String resv3) {
		this.resv3 = resv3;
	}

	public String getResv4() {
		return resv4;
	}

	public void setResv4(String resv4) {
		this.resv4 = resv4;
	}

	public String getResv5() {
		return resv5;
	}

	public void setResv5(String resv5) {
		this.resv5 = resv5;
	}

	public String getResv6() {
		return resv6;
	}

	public void setResv6(String resv6) {
		this.resv6 = resv6;
	}

	public String getResv7() {
		return resv7;
	}

	public void setResv7(String resv7) {
		this.resv7 = resv7;
	}

	public String getMac() {
		return mac;
	}

	public void setMac(String mac) {
		this.mac = mac;
	}

	public String getOtherData() {
		return otherData;
	}

	public void setOtherData(String otherData) {
		this.otherData = otherData;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}
}
