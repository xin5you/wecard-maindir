package com.cn.thinkx.cgb.model;

import java.io.Serializable;


public class BankMappingDTO implements Serializable {

    private static final long serialVersionUID = 7606634695832137311L;
    
    private  String bankCode;
    private  String bankName;
    private  String bankShortName;

    public String getBankCode() {
        return bankCode;
    }

    public void setBankCode(String bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBankShortName() {
        return bankShortName;
    }

    public void setBankShortName(String bankShortName) {
        this.bankShortName = bankShortName;
    }
}
