package com.cn.thinkx.pms.connect.pmspaymentgate;

import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;
import javax.xml.ws.Service;

/**
 * This class was generated by Apache CXF 2.5.2
 * 2016-02-25T15:48:21.754+08:00
 * Generated source version: 2.5.2
 * 
 */
@WebServiceClient(name = "PMSService", 
                  wsdlLocation = "wsdl/PMSPaymentGate.wsdl",
                  targetNamespace = "http://pms.thinkx.cn.com/PMSPaymentGate") 
public class PMSService extends Service {

    public final static URL WSDL_LOCATION;

    public final static QName SERVICE = new QName("http://pms.thinkx.cn.com/PMSPaymentGate", "PMSService");
    public final static QName PMSServicePort = new QName("http://pms.thinkx.cn.com/PMSPaymentGate", "PMSServicePort");
    static {
        URL url = PMSService.class.getResource("wsdl/PMSPaymentGate.wsdl");
        if (url == null) {
            java.util.logging.Logger.getLogger(PMSService.class.getName())
                .log(java.util.logging.Level.INFO, 
                     "Can not initialize the default wsdl from {0}", "wsdl/PMSPaymentGate.wsdl");
        }       
        WSDL_LOCATION = url;
    }

    public PMSService(URL wsdlLocation) {
        super(wsdlLocation, SERVICE);
    }

    public PMSService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public PMSService() {
        super(WSDL_LOCATION, SERVICE);
    }
    

    /**
     *
     * @return
     *     returns PMSPaymentGateServicePortType
     */
    @WebEndpoint(name = "PMSServicePort")
    public PMSPaymentGateServicePortType getPMSServicePort() {
        return super.getPort(PMSServicePort, PMSPaymentGateServicePortType.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns PMSPaymentGateServicePortType
     */
    @WebEndpoint(name = "PMSServicePort")
    public PMSPaymentGateServicePortType getPMSServicePort(WebServiceFeature... features) {
        return super.getPort(PMSServicePort, PMSPaymentGateServicePortType.class, features);
    }

}
