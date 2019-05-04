package com.cn.thinkx.wecard.api.module.pay.req;

import com.cn.thinkx.wecard.api.module.pay.vo.BoxBaseModel;

/**
 * 提交扫码req
 * 
 * @author zqy
 *
 */
public class AggrPayReq extends BoxBaseModel {

	private String device_no; // 终端设备号
	private String auth_code; // 付款授权码或卡券核销码
	private String goods_desc; // 商品或支付单简要描述
	private String pp_trade_no; // 派派订单号
	private String total_fee; // 订单金额
	private String bill_create_ip; // IP

	private String mchntCode;
	private String shopCode;
	private String mchntName;
	private String shopName;

	public String getMchntCode() {
		return mchntCode;
	}

	public void setMchntCode(String mchntCode) {
		this.mchntCode = mchntCode;
	}

	public String getShopCode() {
		return shopCode;
	}

	public void setShopCode(String shopCode) {
		this.shopCode = shopCode;
	}

	public String getMchntName() {
		return mchntName;
	}

	public void setMchntName(String mchntName) {
		this.mchntName = mchntName;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public String getDevice_no() {
		return device_no;
	}

	public String getAuth_code() {
		return auth_code;
	}

	public String getGoods_desc() {
		return goods_desc;
	}

	public String getPp_trade_no() {
		return pp_trade_no;
	}

	public String getTotal_fee() {
		return total_fee;
	}

	public String getBill_create_ip() {
		return bill_create_ip;
	}

	public void setDevice_no(String device_no) {
		this.device_no = device_no;
	}

	public void setAuth_code(String auth_code) {
		this.auth_code = auth_code;
	}

	public void setGoods_desc(String goods_desc) {
		this.goods_desc = goods_desc;
	}

	public void setPp_trade_no(String pp_trade_no) {
		this.pp_trade_no = pp_trade_no;
	}

	public void setTotal_fee(String total_fee) {
		this.total_fee = total_fee;
	}

	public void setBill_create_ip(String bill_create_ip) {
		this.bill_create_ip = bill_create_ip;
	}

}
