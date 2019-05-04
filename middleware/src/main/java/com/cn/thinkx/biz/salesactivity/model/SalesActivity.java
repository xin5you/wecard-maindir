package com.cn.thinkx.biz.salesactivity.model;

import java.util.Date;

import com.cn.thinkx.biz.core.model.BaseDomain;

public class SalesActivity extends BaseDomain  {

	private static final long serialVersionUID = 1L;
	
	private String activityId;
	private String activityType;
	private String activityBody;
	private Date startTime;
	private Date endTime;
	private String dataStat;
	
	private int allStockNum; //总购卡数
	private int userStockNum;// 用户可购卡数量
	
	private String activityInfo;
	
	private String activityDsp;  //活动描述
	
	public String getActivityId() {
		return activityId;
	}
	public String getActivityType() {
		return activityType;
	}
	public String getActivityBody() {
		return activityBody;
	}
	public Date getStartTime() {
		return startTime;
	}
	public Date getEndTime() {
		return endTime;
	}
	public String getDataStat() {
		return dataStat;
	}


	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}
	public void setActivityBody(String activityBody) {
		this.activityBody = activityBody;
	}
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}
	public void setDataStat(String dataStat) {
		this.dataStat = dataStat;
	}

	public int getAllStockNum() {
		return allStockNum;
	}
	public int getUserStockNum() {
		return userStockNum;
	}
	public void setAllStockNum(int allStockNum) {
		this.allStockNum = allStockNum;
	}
	public void setUserStockNum(int userStockNum) {
		this.userStockNum = userStockNum;
	}
	public String getActivityInfo() {
		return activityInfo;
	}
	public void setActivityInfo(String activityInfo) {
		this.activityInfo = activityInfo;
	}
	public String getActivityDsp() {
		return activityDsp;
	}
	public void setActivityDsp(String activityDsp) {
		this.activityDsp = activityDsp;
	}
}
