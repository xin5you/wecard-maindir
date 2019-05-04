package com.cn.thinkx.common.service.module.channel.domain;

import java.util.Date;

import com.cn.thinkx.common.service.core.domain.BaseDomain;

public class ChannelSecurityInf extends BaseDomain {
	
	private String channelCode;  //渠道CODE
	private String channelName;	 //渠道名称
	private String channelKey;   //渠道签名
	private Date beginTime;		//渠道接入开始时间
	private Date endTime;		//渠道接入结束时间
	private String dataStat;	//渠道使用状态
	
	public String getChannelCode() {
		return channelCode;
	}
	public String getChannelName() {
		return channelName;
	}
	public String getChannelKey() {
		return channelKey;
	}
	public Date getBeginTime() {
		return beginTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public String getDataStat() {
		return dataStat;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public void setChannelKey(String channelKey) {
		this.channelKey = channelKey;
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public void setDataStat(String dataStat) {
		this.dataStat = dataStat;
	}
}
