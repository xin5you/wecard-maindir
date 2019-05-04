package com.cn.thinkx.facade.bean;

import com.cn.thinkx.facade.bean.base.BaseTxnReq;

public class CardTransDetailQueryRequest extends BaseTxnReq {

	private static final long serialVersionUID = 1L;
	/**
	 * 用户ID
	 */
	private String userId;

	private String pageNum;// 页码，默认1

	private String itemSize;// 每页条数，默认10

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPageNum() {
		return pageNum;
	}

	public void setPageNum(String pageNum) {
		this.pageNum = pageNum;
	}

	public String getItemSize() {
		return itemSize;
	}

	public void setItemSize(String itemSize) {
		this.itemSize = itemSize;
	}

}
