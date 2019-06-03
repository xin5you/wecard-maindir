package com.cn.thinkx.cgb.model;

import java.io.Serializable;
import java.security.PrivateKey;
import java.security.PublicKey;


public class KeyDTO implements Serializable {

    private static final long serialVersionUID = 1263290666994500293L;

    private  PublicKey publicKey;
    private  PrivateKey privateKey;
    private boolean flag;
    private String msg;



    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "KeyDTO{" +
                "publicKey=" + publicKey +
                ", privateKey=" + privateKey +
                ", flag=" + flag +
                ", msg='" + msg + '\'' +
                '}';
    }
}
