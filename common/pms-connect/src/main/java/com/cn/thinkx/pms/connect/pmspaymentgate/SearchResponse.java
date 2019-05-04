
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
 *         &lt;element name="searchResponseHeard" type="{http://pms.thinkx.cn.com/PMSPaymentGate}searchResponseHeard"/>
 *         &lt;element name="searchResponseBody" type="{http://pms.thinkx.cn.com/PMSPaymentGate}messageBody"/>
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
    "searchResponseHeard",
    "searchResponseBody"
})
@XmlRootElement(name = "searchResponse")
public class SearchResponse {

    @XmlElement(required = true)
    protected SearchResponseHeard searchResponseHeard;
    @XmlElement(required = true)
    protected MessageBody searchResponseBody;

    /**
     * Gets the value of the searchResponseHeard property.
     * 
     * @return
     *     possible object is
     *     {@link SearchResponseHeard }
     *     
     */
    public SearchResponseHeard getSearchResponseHeard() {
        return searchResponseHeard;
    }

    /**
     * Sets the value of the searchResponseHeard property.
     * 
     * @param value
     *     allowed object is
     *     {@link SearchResponseHeard }
     *     
     */
    public void setSearchResponseHeard(SearchResponseHeard value) {
        this.searchResponseHeard = value;
    }

    /**
     * Gets the value of the searchResponseBody property.
     * 
     * @return
     *     possible object is
     *     {@link MessageBody }
     *     
     */
    public MessageBody getSearchResponseBody() {
        return searchResponseBody;
    }

    /**
     * Sets the value of the searchResponseBody property.
     * 
     * @param value
     *     allowed object is
     *     {@link MessageBody }
     *     
     */
    public void setSearchResponseBody(MessageBody value) {
        this.searchResponseBody = value;
    }

}
