package com.cn.thinkx.pay.domain;

/**
 * 中付统一代付请求支付VO类
 */
public class UnifyPayForAnotherVO implements java.io.Serializable{

    private String service; //	服务类型	<20	不可空
    private String merchantNo; //		商户号 	15 	不可空
    private String payMoney; //		代付金额密文	8	不可空
    private String orderId; //		订单号	<=50	不可空
    private String bankCard; //		银行卡号密文	<=21	不可空
    private String  name; //		持卡人姓名	<=20	不可空
    private String  bankName; //		银行名称	<=20	不可空
    private String bankCode; //		开户行号：

    private String acctType; //		账户类型	1	不可空
    private String qsBankCode; //		清算行号	12	可空
    private String cnaps; //		联行号	<=50	不可空
    private String  province; //		省份	<=20	不可空
    private String city; //		城市	<=20	不可空
    private String mobile; //		手机号	11	可空
    private String certType; //		证件类型	<=20	不可空
    private String certNumber; //		证件号	<=50	不可空
    private String sessionId; //		签到码	<=50	不可空
    private String merchantURL; //		商户异步通知地址	<=200	可空
    private String payKey; //		备付金支付密码	6	不可空
    private String MD5; //		验签密钥		不可空
    private String Name1; //		保留域1
    private String Name2; //		保留域2
    private String  Name3; //		保留域3
    private String  Name4; //		保留域4
    private String Name5; //		保留域5

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

    public String getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(String payMoney) {
        this.payMoney = payMoney;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getBankCard() {
        return bankCard;
    }

    public void setBankCard(String bankCard) {
        this.bankCard = bankCard;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getAcctType() {
        return acctType;
    }

    public void setAcctType(String acctType) {
        this.acctType = acctType;
    }

    public String getQsBankCode() {
        return qsBankCode;
    }

    public void setQsBankCode(String qsBankCode) {
        this.qsBankCode = qsBankCode;
    }

    public String getCnaps() {
        return cnaps;
    }

    public void setCnaps(String cnaps) {
        this.cnaps = cnaps;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCertType() {
        return certType;
    }

    public void setCertType(String certType) {
        this.certType = certType;
    }

    public String getCertNumber() {
        return certNumber;
    }

    public void setCertNumber(String certNumber) {
        this.certNumber = certNumber;
    }

    public String getSessionId() {
        return sessionId;
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getMerchantURL() {
        return merchantURL;
    }

    public void setMerchantURL(String merchantURL) {
        this.merchantURL = merchantURL;
    }

    public String getPayKey() {
        return payKey;
    }

    public void setPayKey(String payKey) {
        this.payKey = payKey;
    }

    public String getMD5() {
        return MD5;
    }

    public void setMD5(String MD5) {
        this.MD5 = MD5;
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

    public String getName5() {
        return Name5;
    }

    public void setName5(String name5) {
        Name5 = name5;
    }
}
