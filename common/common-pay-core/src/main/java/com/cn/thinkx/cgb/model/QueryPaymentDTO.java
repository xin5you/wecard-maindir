package com.cn.thinkx.cgb.model;

import java.io.Serializable;


public class QueryPaymentDTO implements Serializable {

    private static final long serialVersionUID = 7606634695832137322L;

    private String origEntseqno;

    private String origEntdate;


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
        return "QueryPaymentDTO{" +
                "origEntseqno='" + origEntseqno + '\'' +
                ", origEntdate='" + origEntdate + '\'' +
                '}';
    }
}
