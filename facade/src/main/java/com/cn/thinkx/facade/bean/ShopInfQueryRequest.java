package com.cn.thinkx.facade.bean;

import com.cn.thinkx.facade.bean.base.BaseTxnReq;

public class ShopInfQueryRequest extends BaseTxnReq {

	private static final long serialVersionUID = 1L;
	/**
	 * 是否查询明细
	 */
	private String detailFlag;

	private String latitude; // 纬度
	private String longitude; // 经度
	private String distance; // 距离
	private String sort; // 10：销量最高、20评价最好 30：距离由近到远
	private String industryType; // 行业类型
	private String pageNum;// 页码，默认1
	private String itemSize;// 每页条数，默认10

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getLatitude() {
		return latitude;
	}

	public String getLongitude() {
		return longitude;
	}

	public String getDistance() {
		return distance;
	}

	public String getSort() {
		return sort;
	}

	public String getIndustryType() {
		return industryType;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public void setIndustryType(String industryType) {
		this.industryType = industryType;
	}

	public String getDetailFlag() {
		return detailFlag;
	}

	public void setDetailFlag(String detailFlag) {
		this.detailFlag = detailFlag;
	}

	public String getPageNum() {
		return pageNum;
	}

	public void setPageNum(String pageNum) {
		this.pageNum = pageNum;
	}

	public String getItemSize() {
		return itemSize;
	}

	public void setItemSize(String itemSize) {
		this.itemSize = itemSize;
	}

}
