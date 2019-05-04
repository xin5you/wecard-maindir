package com.cn.thinkx.facade.bean;

import com.cn.thinkx.facade.bean.base.BaseTxnReq;

public class CusCardOpeningRequest extends BaseTxnReq {

	private static final long serialVersionUID = 1L;
	/**
	 * 用户ID
	 */
	private String userId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

}
