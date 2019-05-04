package com.cn.thinkx.wxclient.domain;

import com.cn.thinkx.core.domain.BaseDomain;

public class WxResource extends BaseDomain {

	private String resourceId;
	private String resourceName;
	private String desp;
	private String dataStat;
	
	/**所属微信openID***/
	private String openId;
	
	/**所属粉丝Id**/
	private String fansId;
	
	public String getResourceId() {
		return resourceId;
	}
	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}
	public String getResourceName() {
		return resourceName;
	}
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	public String getDesp() {
		return desp;
	}
	public void setDesp(String desp) {
		this.desp = desp;
	}
	public String getDataStat() {
		return dataStat;
	}
	public void setDataStat(String dataStat) {
		this.dataStat = dataStat;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getFansId() {
		return fansId;
	}
	public void setFansId(String fansId) {
		this.fansId = fansId;
	}
}
