package com.cn.thinkx.common.wecard.domain.user;

/**
 * 查询客户所有的卡 所属的商户，机构信息列表
 * @author zqy
 *
 */
public class UserMerchantAcct {
	private String userId; //TB_USER_INF.user_id
	private String userName;//TB_USER_INF.user_name
	private String cardNo; //TB_CARD_INF.card
	private String pinStat;
	private String nopinTxnAmt;
    private String accountNo; //TB_ACCOUNT_INF.account_no 主账户号
    private String accBal; //TB_ACCOUNT_INF.acc_bal
    private String insId;  //TB_INS_INF.ins_id  卡所属机构信息
    private String insCode; //TB_INS_INF.ins_code 
    private String insName; //TB_INS_INF.ins_name 
    private String mchntId; //TB_MERCHANT_INF.mchnt_id
    private String mchntCode;//TB_MERCHANT_INF.mchnt_code
    private String mchntName; //TB_MERCHANT_INF.mchnt_name
    private String productCode; //TB_PRODUCT.product_code
    private String productName; //TB_PRODUCT.product_name
    
	private String channelCode ;//渠道标识
	private String externalId ;//外部渠道ID
	
	private String maxTxnAmt2;//单笔限额
	private String maxDayTxnAmt2;//日累计限额
	private String dayTotalAmt2;//日交易累计金额
	private String lastTxnDate;//account_inf 最后交易日期
    
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getAccountNo() {
		return accountNo;
	}
	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}
	public String getAccBal() {
		return accBal;
	}
	public void setAccBal(String accBal) {
		this.accBal = accBal;
	}
	public String getInsId() {
		return insId;
	}
	public void setInsId(String insId) {
		this.insId = insId;
	}
	public String getInsCode() {
		return insCode;
	}
	public void setInsCode(String insCode) {
		this.insCode = insCode;
	}
	public String getInsName() {
		return insName;
	}
	public void setInsName(String insName) {
		this.insName = insName;
	}
	public String getMchntId() {
		return mchntId;
	}
	public void setMchntId(String mchntId) {
		this.mchntId = mchntId;
	}
	public String getMchntCode() {
		return mchntCode;
	}
	public void setMchntCode(String mchntCode) {
		this.mchntCode = mchntCode;
	}
	public String getMchntName() {
		return mchntName;
	}
	public void setMchntName(String mchntName) {
		this.mchntName = mchntName;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getPinStat() {
		return pinStat;
	}
	public void setPinStat(String pinStat) {
		this.pinStat = pinStat;
	}
	public String getNopinTxnAmt() {
		return nopinTxnAmt;
	}
	public void setNopinTxnAmt(String nopinTxnAmt) {
		this.nopinTxnAmt = nopinTxnAmt;
	}
	public String getChannelCode() {
		return channelCode;
	}
	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}
	public String getExternalId() {
		return externalId;
	}
	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}
	public String getMaxTxnAmt2() {
		return maxTxnAmt2;
	}
	public String getMaxDayTxnAmt2() {
		return maxDayTxnAmt2;
	}
	public String getDayTotalAmt2() {
		return dayTotalAmt2;
	}
	public String getLastTxnDate() {
		return lastTxnDate;
	}
	public void setMaxTxnAmt2(String maxTxnAmt2) {
		this.maxTxnAmt2 = maxTxnAmt2;
	}
	public void setMaxDayTxnAmt2(String maxDayTxnAmt2) {
		this.maxDayTxnAmt2 = maxDayTxnAmt2;
	}
	public void setDayTotalAmt2(String dayTotalAmt2) {
		this.dayTotalAmt2 = dayTotalAmt2;
	}
	public void setLastTxnDate(String lastTxnDate) {
		this.lastTxnDate = lastTxnDate;
	}
}