package com.cn.thinkx.biz.salesactivity.model;

import com.cn.thinkx.biz.core.model.BaseDomain;

public class SalesActivityDetail extends BaseDomain  {

	private static final long serialVersionUID = 1L;
	
	private String activityDetailId;
	private String activityId;
	private String activityType;
	private String activityBody;
	private String commodityId;
	private String dataStat;
	private int allStockNum; //总购卡数
	private int userStockNum;// 用户可购卡数量
	private String activityInfo;

	/**业务字段**/
	private String activityDsp;  //活动描述
	private int CommodityStocks;

	
	public String getActivityId() {
		return activityId;
	}
	public String getActivityType() {
		return activityType;
	}
	public String getActivityBody() {
		return activityBody;
	}

	public String getDataStat() {
		return dataStat;
	}
	public String getCommodityId() {
		return commodityId;
	}
	public void setCommodityId(String commodityId) {
		this.commodityId = commodityId;
	}
	public int getAllStockNum() {
		return allStockNum;
	}
	public void setAllStockNum(int allStockNum) {
		this.allStockNum = allStockNum;
	}
	public int getUserStockNum() {
		return userStockNum;
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
	public static long getSerialversionuid() {
		return serialVersionUID;
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
	public void setDataStat(String dataStat) {
		this.dataStat = dataStat;
	}
	public int getCommodityStocks() {
		return CommodityStocks;
	}
	public void setCommodityStocks(int commodityStocks) {
		CommodityStocks = commodityStocks;
	}
	public String getActivityDetailId() {
		return activityDetailId;
	}
	public void setActivityDetailId(String activityDetailId) {
		this.activityDetailId = activityDetailId;
	}
	public String getActivityDsp() {
		return activityDsp;
	}
	public void setActivityDsp(String activityDsp) {
		this.activityDsp = activityDsp;
	}
}
