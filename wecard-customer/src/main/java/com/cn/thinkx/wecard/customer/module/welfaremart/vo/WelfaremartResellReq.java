package com.cn.thinkx.wecard.customer.module.welfaremart.vo;

public class WelfaremartResellReq {

	/*
	 * 转让张数
	 */
	private String resellNum;
	/*
	 * 卡密产品号
	 */
	private String productCode;
	/*
	 * 银行卡号
	 */
	private String bankNo;
	/*
	 * 用户ID
	 */
	private String userId;
	/*
	 * 签名
	 */
	private String sign;

	public String getResellNum() {
		return resellNum;
	}

	public void setResellNum(String resellNum) {
		this.resellNum = resellNum;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getBankNo() {
		return bankNo;
	}

	public void setBankNo(String bankNo) {
		this.bankNo = bankNo;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}
	
}
