package com.cn.thinkx.wecard.api.module.phoneRecharge.vo;

/**
 * 鼎驰统一直充供货接口请求返回类
 * 
 * @author pucker
 *
 */
public class UnicomAyncResp {
	private String status;			// 返回状态
	private String code;			// 返回码
	private String desc;			// 返回描述
	private String amount;			// 交易总金额
	private String areaCode;		// 省域代码
	private String bizOrderId;		// 鼎驰科技方流水号
	private String carrierType;		// 手机运营商1联通 2电信 3移动
	private String itemFacePrice;	// 商品面值，单位为厘
	private String itemId;			// 商品编号
	private String itemName;		// 商品描述 :电信移动联通流量包60M
	private String price;			// 商品单价
	private String seriaIno;		// 合作方流水号

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getBizOrderId() {
		return bizOrderId;
	}

	public void setBizOrderId(String bizOrderId) {
		this.bizOrderId = bizOrderId;
	}

	public String getCarrierType() {
		return carrierType;
	}

	public void setCarrierType(String carrierType) {
		this.carrierType = carrierType;
	}

	public String getItemFacePrice() {
		return itemFacePrice;
	}

	public void setItemFacePrice(String itemFacePrice) {
		this.itemFacePrice = itemFacePrice;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getPrice() {
		return price;
	}

	public void setPrice(String price) {
		this.price = price;
	}

	public String getSeriaIno() {
		return seriaIno;
	}

	public void setSeriaIno(String seriaIno) {
		this.seriaIno = seriaIno;
	}

}
