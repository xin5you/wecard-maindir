package com.cn.thinkx.biz.core.model;

/**
 * 图片管理model
 * 
 * @author pucker
 *
 */
public class ImageManager extends BaseDomain {
	private static final long serialVersionUID = 4771613143414925069L;
	private String imageId;// 图片ID
	private String application;// 应用种类
	private String applicationId; // 应用id
	private String applicationType; // 应用类型
	private String imageSize; // 图片大小
	private String imageUrl; // 图片路径
	private String dataStat; // 状态

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public String getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}

	public String getApplicationType() {
		return applicationType;
	}

	public void setApplicationType(String applicationType) {
		this.applicationType = applicationType;
	}

	public String getImageSize() {
		return imageSize;
	}

	public void setImageSize(String imageSize) {
		this.imageSize = imageSize;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getDataStat() {
		return dataStat;
	}

	public void setDataStat(String dataStat) {
		this.dataStat = dataStat;
	}

}
