
package com.cn.thinkx.pms.connect.pmspaymentgate;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for messageBody complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="messageBody">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="txnCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="channel" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="rspCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="termId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="consumerCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="merchantCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="merchantName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="shopCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="orderId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="txnDate" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="txnTime" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="outCard" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="outAccount" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="intoCard" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="intoAccount" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="amount" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="origAmount" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="curType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="payChannel" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="cardNO" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="pin" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="account" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="cardHolder" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="productInfo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="remark" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="merchantURL" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="settleDate" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="txnNo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="origTxnNo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="txnType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="oldTxnType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="txnState" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="sequenceNo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="flag" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="totalRow" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="realRow" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="adjustType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="start_dt" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="end_dt" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="start_row" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="end_row" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="srvFee" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="binValue" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="operaterId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="otherData" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="payChnl" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="serviceFee" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="batchNo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="batchFileInfo" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "messageBody", propOrder = {
    "txnCode",
    "channel",
    "rspCode",
    "termId",
    "consumerCode",
    "merchantCode",
    "merchantName",
    "shopCode",
    "orderId",
    "txnDate",
    "txnTime",
    "outCard",
    "outAccount",
    "intoCard",
    "intoAccount",
    "amount",
    "origAmount",
    "curType",
    "payChannel",
    "cardNO",
    "pin",
    "account",
    "cardHolder",
    "productInfo",
    "remark",
    "merchantURL",
    "settleDate",
    "txnNo",
    "origTxnNo",
    "txnType",
    "oldTxnType",
    "txnState",
    "sequenceNo",
    "type",
    "flag",
    "totalRow",
    "realRow",
    "adjustType",
    "startDt",
    "endDt",
    "startRow",
    "endRow",
    "srvFee",
    "binValue",
    "operaterId",
    "otherData",
    "payChnl",
    "serviceFee",
    "batchNo",
    "batchFileInfo"
})
public class MessageBody {

    @XmlElement(required = true)
    protected String txnCode;
    @XmlElement(required = true)
    protected String channel;
    @XmlElement(required = true)
    protected String rspCode;
    @XmlElement(required = true)
    protected String termId;
    @XmlElement(required = true)
    protected String consumerCode;
    @XmlElement(required = true)
    protected String merchantCode;
    @XmlElement(required = true)
    protected String merchantName;
    @XmlElement(required = true)
    protected String shopCode;
    @XmlElement(required = true)
    protected String orderId;
    @XmlElement(required = true)
    protected String txnDate;
    @XmlElement(required = true)
    protected String txnTime;
    @XmlElement(required = true)
    protected String outCard;
    @XmlElement(required = true)
    protected String outAccount;
    @XmlElement(required = true)
    protected String intoCard;
    @XmlElement(required = true)
    protected String intoAccount;
    @XmlElement(required = true)
    protected String amount;
    @XmlElement(required = true)
    protected String origAmount;
    @XmlElement(required = true)
    protected String curType;
    @XmlElement(required = true)
    protected String payChannel;
    @XmlElement(required = true)
    protected String cardNO;
    @XmlElement(required = true)
    protected String pin;
    @XmlElement(required = true)
    protected String account;
    @XmlElement(required = true)
    protected String cardHolder;
    @XmlElement(required = true)
    protected String productInfo;
    @XmlElement(required = true)
    protected String remark;
    @XmlElement(required = true)
    protected String merchantURL;
    @XmlElement(required = true)
    protected String settleDate;
    @XmlElement(required = true)
    protected String txnNo;
    @XmlElement(required = true)
    protected String origTxnNo;
    @XmlElement(required = true)
    protected String txnType;
    @XmlElement(required = true)
    protected String oldTxnType;
    @XmlElement(required = true)
    protected String txnState;
    @XmlElement(required = true)
    protected String sequenceNo;
    @XmlElement(required = true)
    protected String type;
    @XmlElement(required = true)
    protected String flag;
    @XmlElement(required = true)
    protected String totalRow;
    @XmlElement(required = true)
    protected String realRow;
    @XmlElement(required = true)
    protected String adjustType;
    @XmlElement(name = "start_dt", required = true)
    protected String startDt;
    @XmlElement(name = "end_dt", required = true)
    protected String endDt;
    @XmlElement(name = "start_row", required = true)
    protected String startRow;
    @XmlElement(name = "end_row", required = true)
    protected String endRow;
    @XmlElement(required = true)
    protected String srvFee;
    @XmlElement(required = true)
    protected String binValue;
    @XmlElement(required = true)
    protected String operaterId;
    @XmlElement(required = true)
    protected String otherData;
    @XmlElement(required = true)
    protected String payChnl;
    @XmlElement(required = true)
    protected String serviceFee;
    @XmlElement(required = true)
    protected String batchNo;
    @XmlElement(required = true)
    protected String batchFileInfo;

    /**
     * Gets the value of the txnCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTxnCode() {
        return txnCode;
    }

    /**
     * Sets the value of the txnCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTxnCode(String value) {
        this.txnCode = value;
    }

    /**
     * Gets the value of the channel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getChannel() {
        return channel;
    }

    /**
     * Sets the value of the channel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setChannel(String value) {
        this.channel = value;
    }

    /**
     * Gets the value of the rspCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRspCode() {
        return rspCode;
    }

    /**
     * Sets the value of the rspCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRspCode(String value) {
        this.rspCode = value;
    }

    /**
     * Gets the value of the termId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTermId() {
        return termId;
    }

    /**
     * Sets the value of the termId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTermId(String value) {
        this.termId = value;
    }

    /**
     * Gets the value of the consumerCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConsumerCode() {
        return consumerCode;
    }

    /**
     * Sets the value of the consumerCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConsumerCode(String value) {
        this.consumerCode = value;
    }

    /**
     * Gets the value of the merchantCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMerchantCode() {
        return merchantCode;
    }

    /**
     * Sets the value of the merchantCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMerchantCode(String value) {
        this.merchantCode = value;
    }

    /**
     * Gets the value of the merchantName property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMerchantName() {
        return merchantName;
    }

    /**
     * Sets the value of the merchantName property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMerchantName(String value) {
        this.merchantName = value;
    }

    /**
     * Gets the value of the shopCode property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShopCode() {
        return shopCode;
    }

    /**
     * Sets the value of the shopCode property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShopCode(String value) {
        this.shopCode = value;
    }

    /**
     * Gets the value of the orderId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     * Sets the value of the orderId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderId(String value) {
        this.orderId = value;
    }

    /**
     * Gets the value of the txnDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTxnDate() {
        return txnDate;
    }

    /**
     * Sets the value of the txnDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTxnDate(String value) {
        this.txnDate = value;
    }

    /**
     * Gets the value of the txnTime property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTxnTime() {
        return txnTime;
    }

    /**
     * Sets the value of the txnTime property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTxnTime(String value) {
        this.txnTime = value;
    }

    /**
     * Gets the value of the outCard property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOutCard() {
        return outCard;
    }

    /**
     * Sets the value of the outCard property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutCard(String value) {
        this.outCard = value;
    }

    /**
     * Gets the value of the outAccount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOutAccount() {
        return outAccount;
    }

    /**
     * Sets the value of the outAccount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOutAccount(String value) {
        this.outAccount = value;
    }

    /**
     * Gets the value of the intoCard property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIntoCard() {
        return intoCard;
    }

    /**
     * Sets the value of the intoCard property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIntoCard(String value) {
        this.intoCard = value;
    }

    /**
     * Gets the value of the intoAccount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIntoAccount() {
        return intoAccount;
    }

    /**
     * Sets the value of the intoAccount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIntoAccount(String value) {
        this.intoAccount = value;
    }

    /**
     * Gets the value of the amount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAmount() {
        return amount;
    }

    /**
     * Sets the value of the amount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAmount(String value) {
        this.amount = value;
    }

    /**
     * Gets the value of the origAmount property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrigAmount() {
        return origAmount;
    }

    /**
     * Sets the value of the origAmount property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrigAmount(String value) {
        this.origAmount = value;
    }

    /**
     * Gets the value of the curType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCurType() {
        return curType;
    }

    /**
     * Sets the value of the curType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCurType(String value) {
        this.curType = value;
    }

    /**
     * Gets the value of the payChannel property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPayChannel() {
        return payChannel;
    }

    /**
     * Sets the value of the payChannel property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPayChannel(String value) {
        this.payChannel = value;
    }

    /**
     * Gets the value of the cardNO property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCardNO() {
        return cardNO;
    }

    /**
     * Sets the value of the cardNO property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCardNO(String value) {
        this.cardNO = value;
    }

    /**
     * Gets the value of the pin property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPin() {
        return pin;
    }

    /**
     * Sets the value of the pin property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPin(String value) {
        this.pin = value;
    }

    /**
     * Gets the value of the account property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccount() {
        return account;
    }

    /**
     * Sets the value of the account property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccount(String value) {
        this.account = value;
    }

    /**
     * Gets the value of the cardHolder property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCardHolder() {
        return cardHolder;
    }

    /**
     * Sets the value of the cardHolder property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCardHolder(String value) {
        this.cardHolder = value;
    }

    /**
     * Gets the value of the productInfo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProductInfo() {
        return productInfo;
    }

    /**
     * Sets the value of the productInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProductInfo(String value) {
        this.productInfo = value;
    }

    /**
     * Gets the value of the remark property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRemark() {
        return remark;
    }

    /**
     * Sets the value of the remark property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRemark(String value) {
        this.remark = value;
    }

    /**
     * Gets the value of the merchantURL property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMerchantURL() {
        return merchantURL;
    }

    /**
     * Sets the value of the merchantURL property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMerchantURL(String value) {
        this.merchantURL = value;
    }

    /**
     * Gets the value of the settleDate property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSettleDate() {
        return settleDate;
    }

    /**
     * Sets the value of the settleDate property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSettleDate(String value) {
        this.settleDate = value;
    }

    /**
     * Gets the value of the txnNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTxnNo() {
        return txnNo;
    }

    /**
     * Sets the value of the txnNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTxnNo(String value) {
        this.txnNo = value;
    }

    /**
     * Gets the value of the origTxnNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrigTxnNo() {
        return origTxnNo;
    }

    /**
     * Sets the value of the origTxnNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrigTxnNo(String value) {
        this.origTxnNo = value;
    }

    /**
     * Gets the value of the txnType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTxnType() {
        return txnType;
    }

    /**
     * Sets the value of the txnType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTxnType(String value) {
        this.txnType = value;
    }

    /**
     * Gets the value of the oldTxnType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOldTxnType() {
        return oldTxnType;
    }

    /**
     * Sets the value of the oldTxnType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOldTxnType(String value) {
        this.oldTxnType = value;
    }

    /**
     * Gets the value of the txnState property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTxnState() {
        return txnState;
    }

    /**
     * Sets the value of the txnState property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTxnState(String value) {
        this.txnState = value;
    }

    /**
     * Gets the value of the sequenceNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSequenceNo() {
        return sequenceNo;
    }

    /**
     * Sets the value of the sequenceNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSequenceNo(String value) {
        this.sequenceNo = value;
    }

    /**
     * Gets the value of the type property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * Sets the value of the type property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

    /**
     * Gets the value of the flag property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFlag() {
        return flag;
    }

    /**
     * Sets the value of the flag property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFlag(String value) {
        this.flag = value;
    }

    /**
     * Gets the value of the totalRow property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTotalRow() {
        return totalRow;
    }

    /**
     * Sets the value of the totalRow property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTotalRow(String value) {
        this.totalRow = value;
    }

    /**
     * Gets the value of the realRow property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRealRow() {
        return realRow;
    }

    /**
     * Sets the value of the realRow property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRealRow(String value) {
        this.realRow = value;
    }

    /**
     * Gets the value of the adjustType property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAdjustType() {
        return adjustType;
    }

    /**
     * Sets the value of the adjustType property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAdjustType(String value) {
        this.adjustType = value;
    }

    /**
     * Gets the value of the startDt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStartDt() {
        return startDt;
    }

    /**
     * Sets the value of the startDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStartDt(String value) {
        this.startDt = value;
    }

    /**
     * Gets the value of the endDt property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEndDt() {
        return endDt;
    }

    /**
     * Sets the value of the endDt property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEndDt(String value) {
        this.endDt = value;
    }

    /**
     * Gets the value of the startRow property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStartRow() {
        return startRow;
    }

    /**
     * Sets the value of the startRow property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStartRow(String value) {
        this.startRow = value;
    }

    /**
     * Gets the value of the endRow property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEndRow() {
        return endRow;
    }

    /**
     * Sets the value of the endRow property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEndRow(String value) {
        this.endRow = value;
    }

    /**
     * Gets the value of the srvFee property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSrvFee() {
        return srvFee;
    }

    /**
     * Sets the value of the srvFee property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSrvFee(String value) {
        this.srvFee = value;
    }

    /**
     * Gets the value of the binValue property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBinValue() {
        return binValue;
    }

    /**
     * Sets the value of the binValue property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBinValue(String value) {
        this.binValue = value;
    }

    /**
     * Gets the value of the operaterId property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOperaterId() {
        return operaterId;
    }

    /**
     * Sets the value of the operaterId property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOperaterId(String value) {
        this.operaterId = value;
    }

    /**
     * Gets the value of the otherData property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOtherData() {
        return otherData;
    }

    /**
     * Sets the value of the otherData property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOtherData(String value) {
        this.otherData = value;
    }

    /**
     * Gets the value of the payChnl property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPayChnl() {
        return payChnl;
    }

    /**
     * Sets the value of the payChnl property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPayChnl(String value) {
        this.payChnl = value;
    }

    /**
     * Gets the value of the serviceFee property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getServiceFee() {
        return serviceFee;
    }

    /**
     * Sets the value of the serviceFee property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setServiceFee(String value) {
        this.serviceFee = value;
    }

    /**
     * Gets the value of the batchNo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBatchNo() {
        return batchNo;
    }

    /**
     * Sets the value of the batchNo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBatchNo(String value) {
        this.batchNo = value;
    }

    /**
     * Gets the value of the batchFileInfo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBatchFileInfo() {
        return batchFileInfo;
    }

    /**
     * Sets the value of the batchFileInfo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBatchFileInfo(String value) {
        this.batchFileInfo = value;
    }

}
