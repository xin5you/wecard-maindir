package com.cn.thinkx.wecard.centre.module.vo;

import java.util.List;

/**
 * 付款批次参数
 * 
 * @author pucker
 *
 */
public class BatchDataVO {

	private String batchNo;
	private String merchantNo;
	private String productCode;
	private String totalNum;
	private String totalAmount;
	private String currency;
	private String payDate;
	private String tunnelData;
	private List<DetailDataVO> detailData;
	private String notifyUrl;
	private String batchOrderName;
	private String goodsType;
	private String sign;

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getMerchantNo() {
		return merchantNo;
	}

	public void setMerchantNo(String merchantNo) {
		this.merchantNo = merchantNo;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(String totalNum) {
		this.totalNum = totalNum;
	}

	public String getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getPayDate() {
		return payDate;
	}

	public void setPayDate(String payDate) {
		this.payDate = payDate;
	}

	public String getTunnelData() {
		return tunnelData;
	}

	public void setTunnelData(String tunnelData) {
		this.tunnelData = tunnelData;
	}

	public List<DetailDataVO> getDetailData() {
		return detailData;
	}

	public void setDetailData(List<DetailDataVO> detailData) {
		this.detailData = detailData;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public String getBatchOrderName() {
		return batchOrderName;
	}

	public void setBatchOrderName(String batchOrderName) {
		this.batchOrderName = batchOrderName;
	}

	public String getGoodsType() {
		return goodsType;
	}

	public void setGoodsType(String goodsType) {
		this.goodsType = goodsType;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

}
