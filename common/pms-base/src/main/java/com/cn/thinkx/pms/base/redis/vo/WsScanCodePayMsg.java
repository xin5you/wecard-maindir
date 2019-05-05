package com.cn.thinkx.pms.base.redis.vo;

import java.util.Date;

/**
 * 微信 websocket 扫码支付消息 domain
 *
 * @author zqy
 */
public class WsScanCodePayMsg {

    private String wxTransLogKey; //微信端交易页面流水号

    private String code;
    //发送者
    public String fromUser;

    //接收者
    public String toUser;

    //发送的文本
    public String text;

    //发送日期
    public Date date;

    public String transAmt;  //请求的交易金额

    public String oriTxnAmount;  //原交易金额


    public String reqType; // 当前发送 请求业务类型  客户端 C  和   商户端  B

    public String sendType; //发送类型 00：保持心跳, 10:订单支付请求,20 输入密码, 90扫描支付结果

    public String payType;  //支付方式  ：VIPCARD_PAY 会员卡支付 ， WECHAT_PAY 微信支付

    public String publicKeyExponent;

    public String publicKeyModulus;

    public String mchntCode;

    private String shopCode;


    public String getWxTransLogKey() {
        return wxTransLogKey;
    }

    public void setWxTransLogKey(String wxTransLogKey) {
        this.wxTransLogKey = wxTransLogKey;
    }

    public String getFromUser() {
        return fromUser;
    }

    public String getToUser() {
        return toUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getSendType() {
        return sendType;
    }

    public void setSendType(String sendType) {
        this.sendType = sendType;
    }

    public String getReqType() {
        return reqType;
    }

    public void setReqType(String reqType) {
        this.reqType = reqType;
    }

    public String getTransAmt() {
        return transAmt;
    }

    public void setTransAmt(String transAmt) {
        this.transAmt = transAmt;
    }

    public String getOriTxnAmount() {
        return oriTxnAmount;
    }

    public void setOriTxnAmount(String oriTxnAmount) {
        this.oriTxnAmount = oriTxnAmount;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPublicKeyExponent() {
        return publicKeyExponent;
    }

    public void setPublicKeyExponent(String publicKeyExponent) {
        this.publicKeyExponent = publicKeyExponent;
    }

    public String getPublicKeyModulus() {
        return publicKeyModulus;
    }

    public void setPublicKeyModulus(String publicKeyModulus) {
        this.publicKeyModulus = publicKeyModulus;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

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

}
