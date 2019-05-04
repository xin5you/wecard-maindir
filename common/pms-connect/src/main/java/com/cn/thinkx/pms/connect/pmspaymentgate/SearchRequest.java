
package com.cn.thinkx.pms.connect.pmspaymentgate;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="searchRequestHeard" type="{http://pms.thinkx.cn.com/PMSPaymentGate}searchRequestHeard"/>
 *         &lt;element name="searchRequestBody" type="{http://pms.thinkx.cn.com/PMSPaymentGate}messageBody"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "searchRequestHeard",
    "searchRequestBody"
})
@XmlRootElement(name = "searchRequest")
public class SearchRequest {

    @XmlElement(required = true)
    protected SearchRequestHeard searchRequestHeard;
    @XmlElement(required = true)
    protected MessageBody searchRequestBody;

    /**
     * Gets the value of the searchRequestHeard property.
     * 
     * @return
     *     possible object is
     *     {@link SearchRequestHeard }
     *     
     */
    public SearchRequestHeard getSearchRequestHeard() {
        return searchRequestHeard;
    }

    /**
     * Sets the value of the searchRequestHeard property.
     * 
     * @param value
     *     allowed object is
     *     {@link SearchRequestHeard }
     *     
     */
    public void setSearchRequestHeard(SearchRequestHeard value) {
        this.searchRequestHeard = value;
    }

    /**
     * Gets the value of the searchRequestBody property.
     * 
     * @return
     *     possible object is
     *     {@link MessageBody }
     *     
     */
    public MessageBody getSearchRequestBody() {
        return searchRequestBody;
    }

    /**
     * Sets the value of the searchRequestBody property.
     * 
     * @param value
     *     allowed object is
     *     {@link MessageBody }
     *     
     */
    public void setSearchRequestBody(MessageBody value) {
        this.searchRequestBody = value;
    }

}
