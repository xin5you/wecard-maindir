package com.cn.thinkx.wecard.customer.module.dingchi.vo;

/**
 * 鼎驰统一直充供货接口请求回调类
 * 
 * @author pucker
 *
 */
public class UnicomAyncNotify {
	private String userId;
	private String bizId;
	private String id;
	private String downstreamSerialno;
	private String status;
	private String statusDesc;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getBizId() {
		return bizId;
	}

	public void setBizId(String bizId) {
		this.bizId = bizId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDownstreamSerialno() {
		return downstreamSerialno;
	}

	public void setDownstreamSerialno(String downstreamSerialno) {
		this.downstreamSerialno = downstreamSerialno;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusDesc() {
		return statusDesc;
	}

	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}

}
