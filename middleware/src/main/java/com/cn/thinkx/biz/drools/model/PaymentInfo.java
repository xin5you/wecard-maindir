package com.cn.thinkx.biz.drools.model;

public class PaymentInfo extends BaseFact {

	private static final long serialVersionUID = 1L;
	/**
	 * 录入金额
	 */
	private double inMoney;

	/**
	 * 条件金额
	 */
	private double condition;
	/**
	 * 折扣
	 */
	private double discount;
	/**
	 * 优惠后金额
	 */
	private double outMoney;

	public PaymentInfo() {

	}

	public double getInMoney() {
		return inMoney;
	}

	public void setInMoney(double inMoney) {
		this.inMoney = inMoney;
	}

	public double getCondition() {
		return condition;
	}

	public void setCondition(double condition) {
		this.condition = condition;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public double getOutMoney() {
		return outMoney;
	}

	public void setOutMoney(double outMoney) {
		this.outMoney = outMoney;
	}

}
