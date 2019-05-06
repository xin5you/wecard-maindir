package com.cn.thinkx.biz.user.model;

import com.cn.thinkx.biz.core.model.BaseDomain;
import com.cn.thinkx.pms.base.redis.util.RedisDictProperties;
import com.cn.thinkx.pms.base.utils.StringUtil;

public class CustomerAccount extends BaseDomain {

    private static final long serialVersionUID = 1L;
    private String brandLogo;// 商户LOGO
    private String mchntCode;// 商户号
    private String productCode;// 产品号
    private String productName; // 产品名称
    private String productImage; // 产品(卡)面
    private String productBalance; // 产品(卡)余额

    public String getBrandLogo() {
        return brandLogo;
    }

    public void setBrandLogo(String brandLogo) {
        if (StringUtil.isNullOrEmpty(brandLogo))
            this.brandLogo = RedisDictProperties.getInstance().getdictValueByCode("HKB_DEFAULT_LOGO_IMG");
        else
            this.brandLogo = RedisDictProperties.getInstance().getdictValueByCode("HKB_URL_IMG") + brandLogo;
    }

    public String getMchntCode() {
        return mchntCode;
    }

    public void setMchntCode(String mchntCode) {
        this.mchntCode = mchntCode;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        if (StringUtil.isNullOrEmpty(productImage))
            this.productImage = RedisDictProperties.getInstance().getdictValueByCode("HKB_DEFAULT_CARD_IMG");
        else
            this.productImage = RedisDictProperties.getInstance().getdictValueByCode("HKB_URL_IMG") + productImage;
    }

    public String getProductBalance() {
        return productBalance;
    }

    public void setProductBalance(String productBalance) {
        this.productBalance = productBalance;
    }

}
