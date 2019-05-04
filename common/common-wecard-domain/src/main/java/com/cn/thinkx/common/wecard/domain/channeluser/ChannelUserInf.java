package com.cn.thinkx.common.wecard.domain.channeluser;

import com.cn.thinkx.common.wecard.domain.base.BaseDomain;

public class ChannelUserInf extends BaseDomain {

	private static final long serialVersionUID = 7462583472895496196L;

	private String channelUserId;

	private String userId;

	private String externalId;

	private String channelCode;

	private String dataStat;

	public String getChannelUserId() {
		return channelUserId;
	}

	public void setChannelUserId(String channelUserId) {
		this.channelUserId = channelUserId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public String getDataStat() {
		return dataStat;
	}

	public void setDataStat(String dataStat) {
		this.dataStat = dataStat;
	}

}
