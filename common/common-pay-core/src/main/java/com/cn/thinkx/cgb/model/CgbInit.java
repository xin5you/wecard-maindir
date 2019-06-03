package com.cn.thinkx.cgb.model;


import com.cn.thinkx.cgb.config.Constart;

public class CgbInit {
    private   String accountName ;
    private   String account ;
    private   String master ;
    private   String userId ;
    private   String password ;
    private   String pubPath ;
    private   String pvbPath ;
    private   String upProt ;
    public CgbInit(){
        accountName = Constart.accountName;
        account = Constart.account;
        master = Constart.master;
        userId = Constart.userId;
        password = Constart.password;
        pubPath = Constart.pubPath;
        pvbPath = Constart.pvbPath;
        upProt = Constart.upProt;
    }

    public String getMaster() {
        return master;
    }

    public void setMaster(String master) {
        this.master = master;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPubPath() {
        return pubPath;
    }

    public void setPubPath(String pubPath) {
        this.pubPath = pubPath;
    }

    public String getPvbPath() {
        return pvbPath;
    }

    public void setPvbPath(String pvbPath) {
        this.pvbPath = pvbPath;
    }

    public String getUpProt() {
        return upProt;
    }

    public void setUpProt(String upProt) {
        this.upProt = upProt;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
}
