package com.cn.thinkx.oms.module.common.model;

import com.cn.thinkx.oms.base.model.BaseDomain;

public class ImageManager extends BaseDomain {

	private String imageId; 
	private String application;
	private String applicationId;
	private String applicationType;
	private String imageSize;
	private String imageUrl;
	private String dataStat;
	
	public String getImageId() {
		return imageId;
	}
	public String getApplication() {
		return application;
	}
	public String getApplicationId() {
		return applicationId;
	}
	public String getApplicationType() {
		return applicationType;
	}
	public String getImageSize() {
		return imageSize;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public String getDataStat() {
		return dataStat;
	}
	public void setImageId(String imageId) {
		this.imageId = imageId;
	}
	public void setApplication(String application) {
		this.application = application;
	}
	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}
	public void setApplicationType(String applicationType) {
		this.applicationType = applicationType;
	}
	public void setImageSize(String imageSize) {
		this.imageSize = imageSize;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	public void setDataStat(String dataStat) {
		this.dataStat = dataStat;
	} 
}
