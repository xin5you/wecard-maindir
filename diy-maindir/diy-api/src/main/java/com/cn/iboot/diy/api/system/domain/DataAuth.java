package com.cn.iboot.diy.api.system.domain;
import com.cn.iboot.diy.api.base.domain.BaseEntity;

public class DataAuth extends BaseEntity {
	
	private static final long serialVersionUID = -4688626183004316392L;
	
    private String id;

    private String mchntCode;

    private String shopCode;

    private String casecade;
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getMchntCode() {
        return mchntCode;
    }

    public void setMchntCode(String mchntCode) {
        this.mchntCode = mchntCode == null ? null : mchntCode.trim();
    }

    public String getShopCode() {
        return shopCode;
    }

    public void setShopCode(String shopCode) {
        this.shopCode = shopCode == null ? null : shopCode.trim();
    }

    public String getCasecade() {
        return casecade;
    }

    public void setCasecade(String casecade) {
        this.casecade = casecade;
    }

}