package com.cn.thinkx.oms.module.common.model;

import com.cn.thinkx.oms.base.model.BaseDomain;

/**
 * 行业类型表
 * @author zqy
 *
 */
public class IndustryClassification extends BaseDomain {

	private String id;
	private String industryName;
	private String industryGrade;//1-一级类目 2-二级类目
	private String parentId;		
	private String transDsp;
	private String dataStat;
	
	public String getId() {
		return id;
	}
	public String getIndustryName() {
		return industryName;
	}
	public String getIndustryGrade() {
		return industryGrade;
	}
	public String getParentId() {
		return parentId;
	}
	public String getTransDsp() {
		return transDsp;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setIndustryName(String industryName) {
		this.industryName = industryName;
	}
	public void setIndustryGrade(String industryGrade) {
		this.industryGrade = industryGrade;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public void setTransDsp(String transDsp) {
		this.transDsp = transDsp;
	}
	public String getDataStat() {
		return dataStat;
	}
	public void setDataStat(String dataStat) {
		this.dataStat = dataStat;
	}
}
