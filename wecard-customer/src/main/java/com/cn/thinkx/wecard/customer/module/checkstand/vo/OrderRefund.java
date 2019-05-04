package com.cn.thinkx.wecard.customer.module.checkstand.vo;

/**
 * 退款交易参数
 * 
 * @author xiaomei
 *
 */
public class OrderRefund {

	private String oriOrderId;		//外部原交易订单号
	private String refundOrderId;	//外部退款订单号
	private String refundAmount;	//退款金额(分)
	private String channel;			//渠道号
	private String refundFlag;		//退款标志（1：代表系统自动发起退款，退款流水跟原交易流水一致；2：代表用户端发起退款，退款流水跟原交易流水值不能相同）
	private long timestamp;			//时间戳
	private String sign;			//签名
	
	private String code;			//返回码（00：成功）
	private String msg;				//返回信息
	private String txnFlowNo;		//知了企服退款流水
	
	private String channelName;		//渠道名称(只有当channel=40006001时会用到此字段)
	
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getRefundFlag() {
		return refundFlag;
	}
	public void setRefundFlag(String refundFlag) {
		this.refundFlag = refundFlag;
	}
	public String getTxnFlowNo() {
		return txnFlowNo;
	}
	public void setTxnFlowNo(String txnFlowNo) {
		this.txnFlowNo = txnFlowNo;
	}
	public String getOriOrderId() {
		return oriOrderId;
	}
	public void setOriOrderId(String oriOrderId) {
		this.oriOrderId = oriOrderId;
	}
	public String getRefundOrderId() {
		return refundOrderId;
	}
	public void setRefundOrderId(String refundOrderId) {
		this.refundOrderId = refundOrderId;
	}
	public String getRefundAmount() {
		return refundAmount;
	}
	public void setRefundAmount(String refundAmount) {
		this.refundAmount = refundAmount;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	
}
