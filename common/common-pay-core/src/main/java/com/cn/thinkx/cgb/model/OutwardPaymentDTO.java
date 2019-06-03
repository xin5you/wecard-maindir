package com.cn.thinkx.cgb.model;

import java.io.Serializable;


public class OutwardPaymentDTO implements Serializable {

    private static final long serialVersionUID = -767991643528462317L;

    private String outAccName;//付款人
    private String outAcc;//付款账号
    private String inAccName;//收款人
    private String inAcc;//收款账号
    private String inAccBank;//收款银行

    private String inAccAdd;//收款银行地址
    private String amount;//金额

    private String remark;//摘要
    private String comment;//附言
    private String paymentBankid;//联行号



    public String getOutAccName() {
        return outAccName;
    }

    public void setOutAccName(String outAccName) {
        this.outAccName = outAccName;
    }

    public String getOutAcc() {
        return outAcc;
    }

    public void setOutAcc(String outAcc) {
        this.outAcc = outAcc;
    }

    public String getInAccName() {
        return inAccName;
    }

    public void setInAccName(String inAccName) {
        this.inAccName = inAccName;
    }

    public String getInAcc() {
        return inAcc;
    }

    public void setInAcc(String inAcc) {
        this.inAcc = inAcc;
    }

    public String getInAccBank() {
        return inAccBank;
    }

    public void setInAccBank(String inAccBank) {
        this.inAccBank = inAccBank;
    }

    public String getInAccAdd() {
        return inAccAdd;
    }

    public void setInAccAdd(String inAccAdd) {
        this.inAccAdd = inAccAdd;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getPaymentBankid() {
        return paymentBankid;
    }

    public void setPaymentBankid(String paymentBankid) {
        this.paymentBankid = paymentBankid;
    }

    @Override
    public String toString() {
        return "OutwardPaymentDTO{" +
                "outAccName='" + outAccName + '\'' +
                ", outAcc='" + outAcc + '\'' +
                ", inAccName='" + inAccName + '\'' +
                ", inAcc='" + inAcc + '\'' +
                ", inAccBank='" + inAccBank + '\'' +
                ", inAccAdd='" + inAccAdd + '\'' +
                ", amount='" + amount + '\'' +
                ", remark='" + remark + '\'' +
                ", comment='" + comment + '\'' +
                ", paymentBankid='" + paymentBankid + '\'' +
                '}';
    }
}
