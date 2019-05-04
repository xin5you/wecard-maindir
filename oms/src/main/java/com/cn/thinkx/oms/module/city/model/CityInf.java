package com.cn.thinkx.oms.module.city.model;

import com.cn.thinkx.oms.base.model.BaseDomain;

/**
 * 地区选择
 * @author zqy
 *
 */
public class CityInf extends BaseDomain {

	private String id;
	private String cityName;
	private String cityGrade; //0-国家 1-省 2-市 3-区县 4-街道
	private String parentId;
	private String cityDsp;
	private String dataStat;
	
	public String getId() {
		return id;
	}
	public String getCityName() {
		return cityName;
	}
	public String getCityGrade() {
		return cityGrade;
	}
	public String getParentId() {
		return parentId;
	}
	public String getCityDsp() {
		return cityDsp;
	}
	public String getDataStat() {
		return dataStat;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public void setCityGrade(String cityGrade) {
		this.cityGrade = cityGrade;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public void setCityDsp(String cityDsp) {
		this.cityDsp = cityDsp;
	}
	public void setDataStat(String dataStat) {
		this.dataStat = dataStat;
	}
	
}
