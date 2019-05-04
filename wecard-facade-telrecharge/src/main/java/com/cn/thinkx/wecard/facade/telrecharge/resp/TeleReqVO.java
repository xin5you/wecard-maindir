package com.cn.thinkx.wecard.facade.telrecharge.resp;

/**
 * 话费充值 业务
 * 
 * @author zhuqiuyou
 *
 */
public class TeleReqVO extends TeleBaseDomain {

	private static final long serialVersionUID = -1005301884642435185L;

	private String rechargePhone; // 充值的电话号码

	private String rechargeAmount; // 充值金额

	private String outerTid; // 分销商订单Id

	private String callback; // 外部分销商订单

	private String productId; // 产品编号

	private String channelOrderId; // 平台订单号

	public String getRechargePhone() {
		return rechargePhone;
	}

	public void setRechargePhone(String rechargePhone) {
		this.rechargePhone = rechargePhone;
	}

	public String getRechargeAmount() {
		return rechargeAmount;
	}

	public void setRechargeAmount(String rechargeAmount) {
		this.rechargeAmount = rechargeAmount;
	}

	public String getOuterTid() {
		return outerTid;
	}

	public void setOuterTid(String outerTid) {
		this.outerTid = outerTid;
	}

	public String getCallback() {
		return callback;
	}

	public void setCallback(String callback) {
		this.callback = callback;
	}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getChannelOrderId() {
		return channelOrderId;
	}

	public void setChannelOrderId(String channelOrderId) {
		this.channelOrderId = channelOrderId;
	}

	@Override
	public String toString() {
		return "TeleReqVO [rechargePhone=" + rechargePhone + ", rechargeAmount=" + rechargeAmount + ", outerTid="
				+ outerTid + ", callback=" + callback + ", productId=" + productId + ", channelOrderId="
				+ channelOrderId + "]";
	}

}
