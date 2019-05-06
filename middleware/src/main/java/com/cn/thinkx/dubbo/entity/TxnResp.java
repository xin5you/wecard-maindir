package com.cn.thinkx.dubbo.entity;

import com.cn.thinkx.biz.mchnt.model.*;
import com.cn.thinkx.biz.user.model.CustomerAccount;
import com.cn.thinkx.facade.bean.base.BaseResp;
import com.cn.thinkx.pms.base.redis.util.RedisDictProperties;
import com.cn.thinkx.pms.base.utils.StringUtil;

import java.util.List;

/**
 * 交易返回对象，用于接收交易返回时转换json字符串(初始化为code:999 info:null)
 *
 * @author pucker
 */
public class TxnResp extends BaseResp {

    /**
     * 客户账户查询接口返回字段
     */
    private String accountFlag;
    private String cardFlag;

    /**
     * 商户在售卡列表查询接口返回字段
     */
    private String productCode;
    private String productImage;
    private String activeRule;
    private List<MchtCommodities> cardList;

    /**
     * 客户会员卡列表查询接口返回字段
     */
    private List<CustomerAccount> productList;

    /**
     * 商户门店列表查询接口返回字段
     */
    private List<ShopListInf> shopList;

    /**
     * 商户门店明细查询接口返回字段
     */
    private ShopDetailInf shopInfo;

    /**
     * 商户信息查询接口返回字段
     */
    private MerchantInf merchantInfo;

    /**
     * 与C端通信交易接口返回字段
     */
    private String balance;
    private String settleDate;
    private String txnFlowNo;
    private String swtFlowNo;
    private String transAmt;
    private String oriTxnAmount;

    private String cardHolderFee; // 手续费
    private List<String> shopImages;// 门店照

    private String pageSize; // 总页数
    private String currPageSize; // 当前页

    /**
     * 会员卡交易明细查询接口返回字段
     */
    private List<CardTransInf> transList;

    /**
     * 知了企服退款至嘉福接口返回字段
     */
    private String orderId;// 退款订单号

    /**
     * 商品可购数量 库存数量
     */
    private String commodityStocks;
    private String commodityCode;//商品号

    /**
     * 返回接口层流水主键
     **/
    private String interfacePrimaryKey;

    /**
     * 返回开户后知了企服用户id
     **/
    private String hkbUserID;

    public String getAccountFlag() {
        return accountFlag;
    }

    public void setAccountFlag(String accountFlag) {
        this.accountFlag = accountFlag;
    }

    public String getCardFlag() {
        return cardFlag;
    }

    public void setCardFlag(String cardFlag) {
        this.cardFlag = cardFlag;
    }

    public String getProductCode() {
        return productCode;
    }

    public void setProductCode(String productCode) {
        this.productCode = productCode;
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

    public String getActiveRule() {
        return activeRule;
    }

    public void setActiveRule(String activeRule) {
        this.activeRule = activeRule;
    }

    public List<MchtCommodities> getCardList() {
        return cardList;
    }

    public void setCardList(List<MchtCommodities> cardList) {
        this.cardList = cardList;
    }

    public List<CustomerAccount> getProductList() {
        return productList;
    }

    public void setProductList(List<CustomerAccount> productList) {
        this.productList = productList;
    }

    public ShopDetailInf getShopInfo() {
        return shopInfo;
    }

    public void setShopInfo(ShopDetailInf shopInfo) {
        this.shopInfo = shopInfo;
    }

    public MerchantInf getMerchantInfo() {
        return merchantInfo;
    }

    public void setMerchantInfo(MerchantInf merchantInfo) {
        this.merchantInfo = merchantInfo;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getSettleDate() {
        return settleDate;
    }

    public void setSettleDate(String settleDate) {
        this.settleDate = settleDate;
    }

    public String getTxnFlowNo() {
        return txnFlowNo;
    }

    public void setTxnFlowNo(String txnFlowNo) {
        this.txnFlowNo = txnFlowNo;
    }

    public String getSwtFlowNo() {
        return swtFlowNo;
    }

    public void setSwtFlowNo(String swtFlowNo) {
        this.swtFlowNo = swtFlowNo;
    }

    public String getTransAmt() {
        return transAmt;
    }

    public void setTransAmt(String transAmt) {
        this.transAmt = transAmt;
    }

    public String getOriTxnAmount() {
        return oriTxnAmount;
    }

    public void setOriTxnAmount(String oriTxnAmount) {
        this.oriTxnAmount = oriTxnAmount;
    }

    public String getCardHolderFee() {
        return cardHolderFee;
    }

    public void setCardHolderFee(String cardHolderFee) {
        this.cardHolderFee = cardHolderFee;
    }

    public List<String> getShopImages() {
        return shopImages;
    }

    public void setShopImages(List<String> shopImages) {
        this.shopImages = shopImages;
    }

    public List<ShopListInf> getShopList() {
        return shopList;
    }

    public String getPageSize() {
        return pageSize;
    }

    public String getCurrPageSize() {
        return currPageSize;
    }

    public void setShopList(List<ShopListInf> shopList) {
        this.shopList = shopList;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public void setCurrPageSize(String currPageSize) {
        this.currPageSize = currPageSize;
    }

    public List<CardTransInf> getTransList() {
        return transList;
    }

    public void setTransList(List<CardTransInf> transList) {
        this.transList = transList;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCommodityStocks() {
        return commodityStocks;
    }

    public void setCommodityStocks(String commodityStocks) {
        this.commodityStocks = commodityStocks;
    }

    public String getCommodityCode() {
        return commodityCode;
    }

    public void setCommodityCode(String commodityCode) {
        this.commodityCode = commodityCode;
    }

    public String getInterfacePrimaryKey() {
        return interfacePrimaryKey;
    }

    public void setInterfacePrimaryKey(String interfacePrimaryKey) {
        this.interfacePrimaryKey = interfacePrimaryKey;
    }

    public String getHkbUserID() {
        return hkbUserID;
    }

    public void setHkbUserID(String hkbUserID) {
        this.hkbUserID = hkbUserID;
    }
}
