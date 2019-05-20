package com.cn.thinkx.pms.base.domain;

/**
 * 中付-联行号
 *
 * @author pucker
 * @date 2019/5/20 14:41
 */
public class CnapsVO {
    /**
     * 银行名
     */
    private String bankName;

    /**
     * 省
     */
    private String province;

    /**
     * 市
     */
    private String city;

    /**
     * 联行号
     */
    private String cnapsNo;

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
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

    public String getCnapsNo() {
        return cnapsNo;
    }

    public void setCnapsNo(String cnapsNo) {
        this.cnapsNo = cnapsNo;
    }
}
