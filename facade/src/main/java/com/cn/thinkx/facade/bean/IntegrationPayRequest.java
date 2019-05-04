package com.cn.thinkx.facade.bean;

import com.cn.thinkx.facade.bean.base.BaseReq;

/**
 * 聚合支付交易请求对象
 * 
 * @author xiaomei
 *
 */
public class IntegrationPayRequest extends BaseReq{

	private static final long serialVersionUID = 1L;
	
	private String termNo;		//终端号
	private String insNo;		//机构号
	private String termChnlNo;	//通道号
	private String termOrderNO;	//终端订单号
	private String mchntNo;		//商户号
	private String mchntName;	//商户名称
	private String shopNo;		//门店号
	private String shopName;	//门店名称
	private String transChnlNo;	//交易渠道号
	private String termSwtFlowNo;//终端流水号
	private String paymentType;	//支付方式
	private String paymentScene;//支付场景
	private String transId;		//交易类型
	private String transAmt;	//交易金额
	private String authInfo;	//条码信息
	private String orderDesc;	//订单描述
	
	public String getTermNo() {
		return termNo;
	}
	public void setTermNo(String termNo) {
		this.termNo = termNo;
	}
	public String getInsNo() {
		return insNo;
	}
	public void setInsNo(String insNo) {
		this.insNo = insNo;
	}
	public String getTermChnlNo() {
		return termChnlNo;
	}
	public void setTermChnlNo(String termChnlNo) {
		this.termChnlNo = termChnlNo;
	}
	public String getTermOrderNO() {
		return termOrderNO;
	}
	public void setTermOrderNO(String termOrderNO) {
		this.termOrderNO = termOrderNO;
	}
	public String getMchntNo() {
		return mchntNo;
	}
	public void setMchntNo(String mchntNo) {
		this.mchntNo = mchntNo;
	}
	public String getMchntName() {
		return mchntName;
	}
	public void setMchntName(String mchntName) {
		this.mchntName = mchntName;
	}
	public String getShopNo() {
		return shopNo;
	}
	public void setShopNo(String shopNo) {
		this.shopNo = shopNo;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getTransChnlNo() {
		return transChnlNo;
	}
	public void setTransChnlNo(String transChnlNo) {
		this.transChnlNo = transChnlNo;
	}
	public String getTermSwtFlowNo() {
		return termSwtFlowNo;
	}
	public void setTermSwtFlowNo(String termSwtFlowNo) {
		this.termSwtFlowNo = termSwtFlowNo;
	}
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	public String getPaymentScene() {
		return paymentScene;
	}
	public void setPaymentScene(String paymentScene) {
		this.paymentScene = paymentScene;
	}
	public String getTransId() {
		return transId;
	}
	public void setTransId(String transId) {
		this.transId = transId;
	}
	public String getTransAmt() {
		return transAmt;
	}
	public void setTransAmt(String transAmt) {
		this.transAmt = transAmt;
	}
	public String getAuthInfo() {
		return authInfo;
	}
	public void setAuthInfo(String authInfo) {
		this.authInfo = authInfo;
	}
	public String getOrderDesc() {
		return orderDesc;
	}
	public void setOrderDesc(String orderDesc) {
		this.orderDesc = orderDesc;
	}
	
}
