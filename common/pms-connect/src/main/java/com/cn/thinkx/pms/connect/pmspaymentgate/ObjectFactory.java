
package com.cn.thinkx.pms.connect.pmspaymentgate;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the com.cn.thinkx.pms.pmspaymentgate package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: com.cn.thinkx.pms.pmspaymentgate
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SearchResponse }
     * 
     */
    public SearchResponse createSearchResponse() {
        return new SearchResponse();
    }

    /**
     * Create an instance of {@link SearchResponseHeard }
     * 
     */
    public SearchResponseHeard createSearchResponseHeard() {
        return new SearchResponseHeard();
    }

    /**
     * Create an instance of {@link MessageBody }
     * 
     */
    public MessageBody createMessageBody() {
        return new MessageBody();
    }

    /**
     * Create an instance of {@link SearchRequest }
     * 
     */
    public SearchRequest createSearchRequest() {
        return new SearchRequest();
    }

    /**
     * Create an instance of {@link SearchRequestHeard }
     * 
     */
    public SearchRequestHeard createSearchRequestHeard() {
        return new SearchRequestHeard();
    }

}
