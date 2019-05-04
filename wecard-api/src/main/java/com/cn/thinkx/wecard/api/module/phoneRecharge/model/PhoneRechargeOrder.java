package com.cn.thinkx.wecard.api.module.phoneRecharge.model;

import com.cn.thinkx.pms.base.domain.BaseDomain;

/**
 * 手机充值类
 * 
 * @author xiaomei
 *
 */
public class PhoneRechargeOrder extends BaseDomain {

	private String rId;
    private String supplierOrdeNo;
    private String channelOrderNo;
    private String supplier;
    private String userId;
    private String phone;
    private String telephoneFace;
    private String transAmt;
    private String discountsAmt;
    private String supplierAmt;
    private String fluxFace;
    private String goodsNo;
    private String transStat;
    private String orderType;
    private String reqChannel;
    private String notifyUrl;
    private String attach;
    private String resv1;
    private String resv2;
    private String resv3;
    private String resv4;
    private String resv5;
    private String resv6;
    private String dataStat;
    
    private String accessToken;
    
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	public String getrId() {
		return rId;
	}
	public void setrId(String rId) {
		this.rId = rId;
	}
	public String getSupplierOrdeNo() {
		return supplierOrdeNo;
	}
	public void setSupplierOrdeNo(String supplierOrdeNo) {
		this.supplierOrdeNo = supplierOrdeNo;
	}
	public String getChannelOrderNo() {
		return channelOrderNo;
	}
	public void setChannelOrderNo(String channelOrderNo) {
		this.channelOrderNo = channelOrderNo;
	}
	public String getSupplier() {
		return supplier;
	}
	public void setSupplier(String supplier) {
		this.supplier = supplier;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getTelephoneFace() {
		return telephoneFace;
	}
	public void setTelephoneFace(String telephoneFace) {
		this.telephoneFace = telephoneFace;
	}
	public String getTransAmt() {
		return transAmt;
	}
	public void setTransAmt(String transAmt) {
		this.transAmt = transAmt;
	}
	public String getDiscountsAmt() {
		return discountsAmt;
	}
	public void setDiscountsAmt(String discountsAmt) {
		this.discountsAmt = discountsAmt;
	}
	public String getSupplierAmt() {
		return supplierAmt;
	}
	public void setSupplierAmt(String supplierAmt) {
		this.supplierAmt = supplierAmt;
	}
	public String getFluxFace() {
		return fluxFace;
	}
	public void setFluxFace(String fluxFace) {
		this.fluxFace = fluxFace;
	}
	public String getGoodsNo() {
		return goodsNo;
	}
	public void setGoodsNo(String goodsNo) {
		this.goodsNo = goodsNo;
	}
	public String getTransStat() {
		return transStat;
	}
	public void setTransStat(String transStat) {
		this.transStat = transStat;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getReqChannel() {
		return reqChannel;
	}
	public void setReqChannel(String reqChannel) {
		this.reqChannel = reqChannel;
	}
	public String getNotifyUrl() {
		return notifyUrl;
	}
	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}
	public String getAttach() {
		return attach;
	}
	public void setAttach(String attach) {
		this.attach = attach;
	}
	public String getResv1() {
		return resv1;
	}
	public void setResv1(String resv1) {
		this.resv1 = resv1;
	}
	public String getResv2() {
		return resv2;
	}
	public void setResv2(String resv2) {
		this.resv2 = resv2;
	}
	public String getResv3() {
		return resv3;
	}
	public void setResv3(String resv3) {
		this.resv3 = resv3;
	}
	public String getResv4() {
		return resv4;
	}
	public void setResv4(String resv4) {
		this.resv4 = resv4;
	}
	public String getResv5() {
		return resv5;
	}
	public void setResv5(String resv5) {
		this.resv5 = resv5;
	}
	public String getResv6() {
		return resv6;
	}
	public void setResv6(String resv6) {
		this.resv6 = resv6;
	}
	public String getDataStat() {
		return dataStat;
	}
	public void setDataStat(String dataStat) {
		this.dataStat = dataStat;
	}
    
}
