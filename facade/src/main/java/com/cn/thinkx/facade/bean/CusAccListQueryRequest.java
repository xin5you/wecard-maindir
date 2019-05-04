package com.cn.thinkx.facade.bean;

import com.cn.thinkx.facade.bean.base.BaseTxnReq;

public class CusAccListQueryRequest extends BaseTxnReq {

	private static final long serialVersionUID = 1L;
	/**
	 * 用户ID
	 */
	private String userId;
	/**
	 * 是否为通卡
	 */
	private String remarks;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getRemarks() {
		return remarks;
	}
	
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	

}
