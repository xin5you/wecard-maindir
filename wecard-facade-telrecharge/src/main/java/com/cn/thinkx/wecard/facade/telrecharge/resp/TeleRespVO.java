package com.cn.thinkx.wecard.facade.telrecharge.resp;

/**
 * 话费充值返回数据
 * 
 * @author zhuqiuyou
 *
 */
public class TeleRespVO extends TeleBaseDomain {

	private static final long serialVersionUID = 7835690664388502116L;

	private String channelOrderId; // 平台流水号

	private String saleAmount;// 销售金额

	private String orderTime; // 订单创建时间

	private String operateTime; // 订单处理时间

	private String payState; // 扣款状态

	private String rechargeState; // 充值状态

	private String facePrice; // 充值面值

	private String itemNum; // 充值数量

	private String outerTid; // 外部分销商订单号

	private String subErrorCode;
	private String subErrorMsg;

	public String getChannelOrderId() {
		return channelOrderId;
	}

	public void setChannelOrderId(String channelOrderId) {
		this.channelOrderId = channelOrderId;
	}

	public String getSaleAmount() {
		return saleAmount;
	}

	public void setSaleAmount(String saleAmount) {
		this.saleAmount = saleAmount;
	}

	public String getOrderTime() {
		return orderTime;
	}

	public void setOrderTime(String orderTime) {
		this.orderTime = orderTime;
	}

	public String getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(String operateTime) {
		this.operateTime = operateTime;
	}

	public String getPayState() {
		return payState;
	}

	public void setPayState(String payState) {
		this.payState = payState;
	}

	public String getRechargeState() {
		return rechargeState;
	}

	public void setRechargeState(String rechargeState) {
		this.rechargeState = rechargeState;
	}

	public String getFacePrice() {
		return facePrice;
	}

	public void setFacePrice(String facePrice) {
		this.facePrice = facePrice;
	}

	public String getItemNum() {
		return itemNum;
	}

	public void setItemNum(String itemNum) {
		this.itemNum = itemNum;
	}

	public String getOuterTid() {
		return outerTid;
	}

	public void setOuterTid(String outerTid) {
		this.outerTid = outerTid;
	}

	public String getSubErrorCode() {
		return subErrorCode;
	}

	public void setSubErrorCode(String subErrorCode) {
		this.subErrorCode = subErrorCode;
	}

	public String getSubErrorMsg() {
		return subErrorMsg;
	}

	public void setSubErrorMsg(String subErrorMsg) {
		this.subErrorMsg = subErrorMsg;
	}
}
