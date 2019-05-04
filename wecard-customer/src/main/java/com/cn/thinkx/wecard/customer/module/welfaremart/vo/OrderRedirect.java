package com.cn.thinkx.wecard.customer.module.welfaremart.vo;

/**
 * 收银台重定向参数 
 * 
 * @author xiaomei
 *
 */
public class OrderRedirect {

	private String channel;		//渠道号
	private String respResult;	//成功失败标识
	private String userId;		//用户ID
	private String orderId;		//订单号
	private String txnFlowNo;	//交易流水号
	private String attach;		//附加信息
	private String sign;		//签名

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getRespResult() {
		return respResult;
	}

	public void setRespResult(String respResult) {
		this.respResult = respResult;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getTxnFlowNo() {
		return txnFlowNo;
	}

	public void setTxnFlowNo(String txnFlowNo) {
		this.txnFlowNo = txnFlowNo;
	}

	public String getAttach() {
		return attach;
	}

	public void setAttach(String attach) {
		this.attach = attach;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	@Override
	public String toString() {
		return "{respResult=[" + respResult + "], orderId=[" + orderId + "], channel=[" + channel + "], userId=["
				+ userId + "], txnFlowNo=[" + txnFlowNo + "], attach=[" + attach + "], sign=[" + sign + "]}";
	}

}
