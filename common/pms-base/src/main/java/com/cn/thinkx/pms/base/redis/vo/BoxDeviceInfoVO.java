package com.cn.thinkx.pms.base.redis.vo;

/**
 * 盒子社保信息
 *
 * @author zqy
 */
public class BoxDeviceInfoVO {

    private String deviceID;
    private String deviceType;
    private String deviceNo;
    private String insCode;
    private String mchntCode;
    private String shopCode;
    private String fixedPayFlag;
    private String fixedPayAmt;
    private String print;
    private String printQr;
    private String printType;
    private String receipt;
    private String dataStat;

    private String mchntName;
    private String shopName;

    private String channelNo;

    public String getChannelNo() {
        return channelNo;
    }

    public void setChannelNo(String channelNo) {
        this.channelNo = channelNo;
    }

    public String getDeviceID() {
        return deviceID;
    }

    public String getDeviceType() {
        return deviceType;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public String getInsCode() {
        return insCode;
    }

    public String getMchntCode() {
        return mchntCode;
    }

    public String getShopCode() {
        return shopCode;
    }

    public String getFixedPayFlag() {
        return fixedPayFlag;
    }

    public String getFixedPayAmt() {
        return fixedPayAmt;
    }

    public String getPrint() {
        return print;
    }

    public String getPrintQr() {
        return printQr;
    }

    public String getPrintType() {
        return printType;
    }

    public String getReceipt() {
        return receipt;
    }

    public String getDataStat() {
        return dataStat;
    }

    public String getMchntName() {
        return mchntName;
    }

    public String getShopName() {
        return shopName;
    }

    public void setDeviceID(String deviceID) {
        this.deviceID = deviceID;
    }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public void setInsCode(String insCode) {
        this.insCode = insCode;
    }

    public void setMchntCode(String mchntCode) {
        this.mchntCode = mchntCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode;
    }

    public void setFixedPayFlag(String fixedPayFlag) {
        this.fixedPayFlag = fixedPayFlag;
    }

    public void setFixedPayAmt(String fixedPayAmt) {
        this.fixedPayAmt = fixedPayAmt;
    }

    public void setPrint(String print) {
        this.print = print;
    }

    public void setPrintQr(String printQr) {
        this.printQr = printQr;
    }

    public void setPrintType(String printType) {
        this.printType = printType;
    }

    public void setReceipt(String receipt) {
        this.receipt = receipt;
    }

    public void setDataStat(String dataStat) {
        this.dataStat = dataStat;
    }

    public void setMchntName(String mchntName) {
        this.mchntName = mchntName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    @Override
    public String toString() {
        return "[deviceID=" + deviceID + ", deviceType=" + deviceType + ", deviceNo=" + deviceNo
                + ", insCode=" + insCode + ", mchntCode=" + mchntCode + ", shopCode=" + shopCode + ", fixedPayFlag="
                + fixedPayFlag + ", fixedPayAmt=" + fixedPayAmt + ", print=" + print + ", printQr=" + printQr
                + ", printType=" + printType + ", receipt=" + receipt + ", dataStat=" + dataStat + ", mchntName="
                + mchntName + ", shopName=" + shopName + ", channelNo=" + channelNo + "]";
    }

}
