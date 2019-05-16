package com.cn.thinkx.pay.domain;

/**
 * 中付统一代付请求查询VO类
 */
public class UnifyQueryVO implements java.io.Serializable{

    private String service; //	服务类型	<20	不可空
    private String merchantNo; //	商户号 	15 	不可空
    private String orderNumber; //	订单号	<=50	可空
    private String inTradeOrderNo; //	原订单号	<=50	可空
    private String tradeTime; //	交易日期	8	不可空
    private String MD5; //	验签密钥		不可空
    private String sessionId; //	签到码	<=50	不可空
    private String Name1; //		保留域1
    private String Name2; //		保留域2
    private String Name3; //		保留域3
    private String Name4; //		保留域4

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getMerchantNo() {
        return merchantNo;
    }

    public void setMerchantNo(String merchantNo) {
        this.merchantNo = merchantNo;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getInTradeOrderNo() {
        return inTradeOrderNo;
    }

    public void setInTradeOrderNo(String inTradeOrderNo) {
        this.inTradeOrderNo = inTradeOrderNo;
    }

    public String getTradeTime() {
        return tradeTime;
    }

    public void setTradeTime(String tradeTime) {
        this.tradeTime = tradeTime;
    }

    public String getMD5() {
        return MD5;
    }

    public void setMD5(String MD5) {
        this.MD5 = MD5;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getName1() {
        return Name1;
    }

    public void setName1(String name1) {
        Name1 = name1;
    }

    public String getName2() {
        return Name2;
    }

    public void setName2(String name2) {
        Name2 = name2;
    }

    public String getName3() {
        return Name3;
    }

    public void setName3(String name3) {
        Name3 = name3;
    }

    public String getName4() {
        return Name4;
    }

    public void setName4(String name4) {
        Name4 = name4;
    }
}
