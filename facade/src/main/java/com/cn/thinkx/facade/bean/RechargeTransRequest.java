package com.cn.thinkx.facade.bean;

import com.cn.thinkx.facade.bean.base.BaseTxnReq;

public class RechargeTransRequest extends BaseTxnReq {

	private static final long serialVersionUID = 1L;
	/**
	 * 商品号
	 */
	private String commodityCode;
	/**
	 * 商品数量
	 */
	private String commodityNum;

	public String getCommodityCode() {
		return commodityCode;
	}

	public void setCommodityCode(String commodityCode) {
		this.commodityCode = commodityCode;
	}

	public String getCommodityNum() {
		return commodityNum;
	}

	public void setCommodityNum(String commodityNum) {
		this.commodityNum = commodityNum;
	}

}
