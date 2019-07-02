package com.cn.thinkx.api.bm001.req;


public class PayBillReq implements  java.io.Serializable{

	private static final long serialVersionUID = -11666576986195266L;

	private String mobileNo;

	private String rechargeAmount;

	private String outerTid;

	private String callback;

	private String itemId;

	public String getMobileNo() {
		return mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	public String getRechargeAmount() {
		return rechargeAmount;
	}

	public void setRechargeAmount(String rechargeAmount) {
		this.rechargeAmount = rechargeAmount;
	}

	public String getOuterTid() {
		return outerTid;
	}

	public void setOuterTid(String outerTid) {
		this.outerTid = outerTid;
	}

	public String getCallback() {
		return callback;
	}

	public void setCallback(String callback) {
		this.callback = callback;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	@Override
	public String toString() {
		return "PayBillReq{" +
				"mobileNo='" + mobileNo + '\'' +
				", rechargeAmount='" + rechargeAmount + '\'' +
				", outerTid='" + outerTid + '\'' +
				", callback='" + callback + '\'' +
				", itemId='" + itemId + '\'' +
				'}';
	}
}
