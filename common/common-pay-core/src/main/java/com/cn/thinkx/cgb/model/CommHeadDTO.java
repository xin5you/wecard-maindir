package com.cn.thinkx.cgb.model;

import java.io.Serializable;


public class CommHeadDTO implements Serializable {

    private static final long serialVersionUID = -3502191910824645361L;
    /**
     * 序列化ID
     */

    private String tranCode;//交易代码:0001
    private String cifMaster;//网银客户号
    private String entSeqNo;//企业财务系统自己产生的流水号。每天唯一
    private String tranDate;//操作日期
    private String tranTime;//操作时间
    private String retCode;//返回码
    private String entUserId;//操作员
    private String password;//

    public String getTranCode() {
        return tranCode;
    }

    public void setTranCode(String tranCode) {
        this.tranCode = tranCode;
    }

    public String getCifMaster() {
        return cifMaster;
    }

    public void setCifMaster(String cifMaster) {
        this.cifMaster = cifMaster;
    }

    public String getEntSeqNo() {
        return entSeqNo;
    }

    public void setEntSeqNo(String entSeqNo) {
        this.entSeqNo = entSeqNo;
    }

    public String getTranDate() {
        return tranDate;
    }

    public void setTranDate(String tranDate) {
        this.tranDate = tranDate;
    }

    public String getTranTime() {
        return tranTime;
    }

    public void setTranTime(String tranTime) {
        this.tranTime = tranTime;
    }

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public String getEntUserId() {
        return entUserId;
    }

    public void setEntUserId(String entUserId) {
        this.entUserId = entUserId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "CommHeadDTO{" +
                "tranCode='" + tranCode + '\'' +
                ", cifMaster='" + cifMaster + '\'' +
                ", entSeqNo='" + entSeqNo + '\'' +
                ", tranDate='" + tranDate + '\'' +
                ", tranTime='" + tranTime + '\'' +
                ", retCode='" + retCode + '\'' +
                ", entUserId='" + entUserId + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
