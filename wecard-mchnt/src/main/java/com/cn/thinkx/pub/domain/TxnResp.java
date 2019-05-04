package com.cn.thinkx.pub.domain;

/**
 * 交易返回对象，用于接收交易返回时转换json字符串
 * 
 * @author pucker
 *
 */
public class TxnResp {

	private String code;
	private String info;
	private String transAmt;
	private String userId;
	private String cardHolderFee; //手续费
	private String balance;

	/**返回接口层流水主键**/
	private String interfacePrimaryKey;
	
	//嘉福退款订单号
	private String orderId;//
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getTransAmt() {
		return transAmt;
	}

	public void setTransAmt(String transAmt) {
		this.transAmt = transAmt;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCardHolderFee() {
		return cardHolderFee;
	}

	public void setCardHolderFee(String cardHolderFee) {
		this.cardHolderFee = cardHolderFee;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getInterfacePrimaryKey() {
		return interfacePrimaryKey;
	}

	public void setInterfacePrimaryKey(String interfacePrimaryKey) {
		this.interfacePrimaryKey = interfacePrimaryKey;
	}

	public String getBalance() {
		return balance;
	}

	public void setBalance(String balance) {
		this.balance = balance;
	}
	
}
