package com.cn.thinkx.cgb.model;

public class CgbRequestDTO implements  java.io.Serializable {
    private static final long serialVersionUID = -8870785475407963495L;

    private String account;  //企业账户号
    private String entSeqNo;//企业财务系统自己产生的流水号。每天唯一

    private String outAccName;//付款人
    private String outAcc;//付款账号
    private String inAccName;//收款人
    private String inAcc;//收款账号
    private String inAccBank;//收款银行
    private String inAccAdd;//收款银行地址
    private String amount;//金额 单位元 1:表示1.00元人民币
    private String remark;//摘要
    private String comment;//附言
    private String paymentBankid;//联行号


    private String origEntseqno;
    private String origEntdate;


    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getEntSeqNo() {
        return entSeqNo;
    }

    public void setEntSeqNo(String entSeqNo) {
        this.entSeqNo = entSeqNo;
    }

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

    public String getOrigEntseqno() {
        return origEntseqno;
    }

    public void setOrigEntseqno(String origEntseqno) {
        this.origEntseqno = origEntseqno;
    }

    public String getOrigEntdate() {
        return origEntdate;
    }

    public void setOrigEntdate(String origEntdate) {
        this.origEntdate = origEntdate;
    }

    @Override
    public String toString() {
        return "CgbRequestDTO{" +
                "account='" + account + '\'' +
                ", entSeqNo='" + entSeqNo + '\'' +
                ", outAccName='" + outAccName + '\'' +
                ", outAcc='" + outAcc + '\'' +
                ", inAccName='" + inAccName + '\'' +
                ", inAcc='" + inAcc + '\'' +
                ", inAccBank='" + inAccBank + '\'' +
                ", inAccAdd='" + inAccAdd + '\'' +
                ", amount='" + amount + '\'' +
                ", remark='" + remark + '\'' +
                ", comment='" + comment + '\'' +
                ", paymentBankid='" + paymentBankid + '\'' +
                ", origEntseqno='" + origEntseqno + '\'' +
                ", origEntdate='" + origEntdate + '\'' +
                '}';
    }
}
