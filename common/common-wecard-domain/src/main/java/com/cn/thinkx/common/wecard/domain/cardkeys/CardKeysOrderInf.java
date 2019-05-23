package com.cn.thinkx.common.wecard.domain.cardkeys;

import com.cn.thinkx.pms.base.domain.BaseDomain;

import java.util.Objects;

/**
 * 卡密交易订单表
 *
 * @author xiaomei
 */
public class CardKeysOrderInf extends BaseDomain {

    /**
     * 订单号
     */
    private String orderId;
    /**
     * 用户ID
     */
    private String userId;
    /**
     * 产品号
     */
    private String productCode;
    /**
     * 银行卡号
     */
    private String bankNo;
    /**
     * 订单金额
     */
    private String amount;
    /**
     * 出款金额
     */
    private String paidAmount;
    /**
     * 订单类型
     */
    private String type;

    /**
     * 订单状态
     */
    private String stat;

    /**
     * 订单数量
     */
    private String num;

    /**
     * 状态
     */
    private String dataStat;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 开户行
     */
    private String accountBank;

    /**
     * 开户行所在地
     */
    private String accountBankAddr;

    /**
     * 联行号
     */
    private String cnaps;

    /**
     * 卡密产品名称
     */
    private String productName;

    /**
     * 卡密产品类型
     */
    private String productType;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    public String getDataStat() {
        return dataStat;
    }

    public void setDataStat(String dataStat) {
        this.dataStat = dataStat;
    }

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    public String getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(String paidAmount) {
        this.paidAmount = paidAmount;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAccountBank() {
        return accountBank;
    }

    public void setAccountBank(String accountBank) {
        this.accountBank = accountBank;
    }

    public String getAccountBankAddr() {
        return accountBankAddr;
    }

    public void setAccountBankAddr(String accountBankAddr) {
        this.accountBankAddr = accountBankAddr;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getCnaps() {
        return cnaps;
    }

    public void setCnaps(String cnaps) {
        this.cnaps = cnaps;
    }

    @Override
    public String toString() {
        return "CardKeysOrderInf [orderId=" + orderId + ", userId=" + userId + ", productCode=" + productCode
                + ", bankNo=" + bankNo + ", amount=" + amount + ", paidAmount=" + paidAmount + ", type=" + type
                + ", stat=" + stat + ", num=" + num + ", dataStat=" + dataStat + ", userName=" + userName
                + ", accountBank=" + accountBank + ", accountBankAddr=" + accountBankAddr + ", lockVersion="
                + super.getLockVersion() + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CardKeysOrderInf that = (CardKeysOrderInf) o;
        return orderId.equals(that.orderId) &&
                userId.equals(that.userId) &&
                productCode.equals(that.productCode) &&
                bankNo.equals(that.bankNo) &&
                amount.equals(that.amount) &&
                paidAmount.equals(that.paidAmount) &&
                type.equals(that.type) &&
                stat.equals(that.stat) &&
                num.equals(that.num) &&
                dataStat.equals(that.dataStat) &&
                userName.equals(that.userName) &&
                accountBank.equals(that.accountBank) &&
                Objects.equals(accountBankAddr, that.accountBankAddr) &&
                Objects.equals(productName, that.productName) &&
                Objects.equals(productType, that.productType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderId, userId, productCode, bankNo, amount, paidAmount, type, stat, num, dataStat, userName, accountBank, accountBankAddr, productName, productType);
    }
}
