package com.cn.thinkx.cgb.model;

import java.io.Serializable;


public class SignatureDTO implements Serializable {

    private static final long serialVersionUID = -7897749370773964512L;

    private String signatureMethod;
    private String digestMethod;
    private String signedInfo;

    public String getSignatureMethod() {
        return signatureMethod;
    }

    public void setSignatureMethod(String signatureMethod) {
        this.signatureMethod = signatureMethod;
    }

    public String getDigestMethod() {
        return digestMethod;
    }

    public void setDigestMethod(String digestMethod) {
        this.digestMethod = digestMethod;
    }

    public String getSignedInfo() {
        return signedInfo;
    }

    public void setSignedInfo(String signedInfo) {
        this.signedInfo = signedInfo;
    }
}
