package com.cn.thinkx.facade.bean;

import com.cn.thinkx.facade.bean.base.BaseReq;

public class CustomerBuyCardStocksRequest extends BaseReq {

	private static final long serialVersionUID = -7536211806776038187L;

	/**
	 * 渠道号
	 */
	private String channel;
	
	/**
	 * 用户ID
	 */
	private String userId;
	
	
	/**
	 * 商品号
	 */
	private String commodityCode;
	

	public String getChannel() {
		return channel;
	}

	public String getUserId() {
		return userId;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getCommodityCode() {
		return commodityCode;
	}

	public void setCommodityCode(String commodityCode) {
		this.commodityCode = commodityCode;
	}
}
