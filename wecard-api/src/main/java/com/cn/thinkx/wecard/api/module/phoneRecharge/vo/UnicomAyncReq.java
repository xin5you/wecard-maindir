package com.cn.thinkx.wecard.api.module.phoneRecharge.vo;

/**
 * 鼎驰统一直充供货接口请求参数类
 * 
 * @author pucker
 *
 */
public class UnicomAyncReq {

	/** ====购买接口请求参数 start==== */
	private String userId; // 必填，合作方用户编号(鼎驰科技方提供)
	private String itemId; // 必填，商品编号 (鼎驰科技方提供)
	private String itemPrice; // 商品供货价格，带了itemPrice后，会做价格校验 (以厘为单位)
	private String amt; // 目前只能充1笔
	private String areaCode; // QB充值、积分兑换必填、地区号或手机号、如填写应该作为加密串
	private String uid; // 必填，客户手机号，被充值号码
	private String serialno; // 必填，合作方商户系统的流水号,全局唯一
	private String dtCreate; // 必填，合作方交易时间(也可以是订单创建时间，格式为：yyyyMMddHHmmss)
	/** ====购买接口请求参数 end==== */

	private String sign; // 必填，签名

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getItemId() {
		return itemId;
	}

	public void setItemId(String itemId) {
		this.itemId = itemId;
	}

	public String getItemPrice() {
		return itemPrice;
	}

	public void setItemPrice(String itemPrice) {
		this.itemPrice = itemPrice;
	}

	public String getAmt() {
		return amt;
	}

	public void setAmt(String amt) {
		this.amt = amt;
	}

	public String getAreaCode() {
		return areaCode;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getSerialno() {
		return serialno;
	}

	public void setSerialno(String serialno) {
		this.serialno = serialno;
	}

	public String getDtCreate() {
		return dtCreate;
	}

	public void setDtCreate(String dtCreate) {
		this.dtCreate = dtCreate;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

}
