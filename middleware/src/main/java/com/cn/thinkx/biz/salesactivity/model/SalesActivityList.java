package com.cn.thinkx.biz.salesactivity.model;

import java.util.Date;

public class SalesActivityList {

	private String activityListId;
	private String activityId;
	private String activityDetailId;
	private String joinBody;
	private String transChnl;
	private String interfaceKey;
	private Date transDate;
	private String dataStat;
	private String remarks;
	private String createUser;
	private String updateUser;
	private Date createTime;
	private Date updateTime;
	private long lockVersion;
	private int cardNum; //当前购卡数量
	
	private String orgInterfaceKey; //原交易接口层流水
	
	private String respCode; //充值送接口说明

	/***业务扩展字段****/
	private String activityBody; //营销活动主题，一般指商品code
	private int allStockNum; //总购卡数
	private int userStockNum;// 用户可购卡数量
	private int commodityStocks; //商品库存，用户可购买数
	public String getActivityListId() {
		return activityListId;
	}
	public String getActivityId() {
		return activityId;
	}
	public String getJoinBody() {
		return joinBody;
	}
	public String getTransChnl() {
		return transChnl;
	}
	public String getInterfaceKey() {
		return interfaceKey;
	}
	public Date getTransDate() {
		return transDate;
	}
	public String getDataStat() {
		return dataStat;
	}
	public String getRemarks() {
		return remarks;
	}
	public String getCreateUser() {
		return createUser;
	}
	public String getUpdateUser() {
		return updateUser;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public long getLockVersion() {
		return lockVersion;
	}
	public int getCardNum() {
		return cardNum;
	}
	public String getOrgInterfaceKey() {
		return orgInterfaceKey;
	}
	public String getRespCode() {
		return respCode;
	}
	public String getActivityBody() {
		return activityBody;
	}
	public int getAllStockNum() {
		return allStockNum;
	}
	public int getUserStockNum() {
		return userStockNum;
	}
	public int getCommodityStocks() {
		return commodityStocks;
	}
	public void setActivityListId(String activityListId) {
		this.activityListId = activityListId;
	}
	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}
	public void setJoinBody(String joinBody) {
		this.joinBody = joinBody;
	}
	public void setTransChnl(String transChnl) {
		this.transChnl = transChnl;
	}
	public void setInterfaceKey(String interfaceKey) {
		this.interfaceKey = interfaceKey;
	}
	public void setTransDate(Date transDate) {
		this.transDate = transDate;
	}
	public void setDataStat(String dataStat) {
		this.dataStat = dataStat;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public void setLockVersion(long lockVersion) {
		this.lockVersion = lockVersion;
	}
	public void setCardNum(int cardNum) {
		this.cardNum = cardNum;
	}
	public void setOrgInterfaceKey(String orgInterfaceKey) {
		this.orgInterfaceKey = orgInterfaceKey;
	}
	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}
	public void setActivityBody(String activityBody) {
		this.activityBody = activityBody;
	}
	public void setAllStockNum(int allStockNum) {
		this.allStockNum = allStockNum;
	}
	public void setUserStockNum(int userStockNum) {
		this.userStockNum = userStockNum;
	}
	public void setCommodityStocks(int commodityStocks) {
		this.commodityStocks = commodityStocks;
	}
	public String getActivityDetailId() {
		return activityDetailId;
	}
	public void setActivityDetailId(String activityDetailId) {
		this.activityDetailId = activityDetailId;
	}
}
