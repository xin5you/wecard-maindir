package com.cn.thinkx.wechat.base.wxapi.process;

import com.cn.thinkx.pms.base.redis.util.RedisDictProperties;
import com.cn.thinkx.pms.base.utils.StringUtil;
import com.cn.thinkx.wechat.base.wxapi.vo.AuthCodeReqOpenId;
import com.cn.thinkx.wechat.base.wxapi.vo.WXPayOrderQueryRequest;
import com.cn.thinkx.wechat.base.wxapi.vo.WXPayRefundRequest;
import com.cn.thinkx.wechat.base.wxapi.vo.WXPayUnifiedOrderRequest;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.util.Map;
import java.util.UUID;

/**
 * 微信 客户端，统一处理微信支付相关接口
 */
public class CustomerWxPayApiClient {

    private static Logger logger = LoggerFactory.getLogger(CustomerWxPayApiClient.class);

    /**
     * 申请退款
     *
     * @param log
     * @param req
     * @return
     */
    public static JSONObject getRefundOrder(MpAccount mpAccount, String out_trade_no, String out_refund_no,
                                            String totalFee, String refundFee, HttpServletRequest req) {
        String wxMchntId = RedisDictProperties.getInstance().getdictValueByCode("WX_CUSTOMER_MCH_ID");
        WXPayRefundRequest request = new WXPayRefundRequest();
        request.setAppid(StringUtil.trim(mpAccount.getAppid()));
        request.setMch_id(wxMchntId);
        request.setNonce_str(StringUtil.trim(UUID.randomUUID().toString().replace("-", "")));

        request.setOut_trade_no(StringUtil.trim(out_trade_no));
        request.setOut_refund_no(StringUtil.trim(out_refund_no));
        request.setTotal_fee(StringUtil.trim(totalFee));
        request.setRefund_fee(StringUtil.trim(refundFee));
        request.setRefund_fee_type("CNY");
        request.setOp_user_id(wxMchntId);

        String xml = null;
        Map<String, String> result = null;
        try {
            String sign = CustomerWxPayApi.getRefundSign(request);
            request.setSign(sign);
            String reqXml = MsgXmlUtil.toXML(request);

            // 申请退款 注意PKCS12证书 是从微信商户平台-》账户设置-》 API安全 中下载的
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            FileInputStream instream = new FileInputStream(
                    new File(RedisDictProperties.getInstance().getdictValueByCode("WX_CUSTOMER_APICLIENT_CERT")));// P12文件目录
            try {
                keyStore.load(instream, wxMchntId.toCharArray());// 这里写密码..默认是你的MCHID
            } finally {
                instream.close();
            }
            SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, wxMchntId.toCharArray())// 默认是你的MCHID
                    .build();
            // Allow TLSv1 protocol only
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[]{"TLSv1"},
                    null, SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);

            CloseableHttpClient httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();

            try {
                HttpPost httpost = new HttpPost(CustomerWxPayApi.getRefundUrl()); // 设置响应头信息
                httpost.addHeader("Connection", "keep-alive");
                httpost.addHeader("Accept", "*/*");
                httpost.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
                httpost.addHeader("Host", "api.mch.weixin.qq.com");
                httpost.addHeader("X-Requested-With", "XMLHttpRequest");
                httpost.addHeader("Cache-Control", "max-age=0");
                httpost.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0) ");
                httpost.setEntity(new StringEntity(reqXml, "UTF-8"));
                CloseableHttpResponse response = httpclient.execute(httpost);
                try {
                    HttpEntity entity = response.getEntity();
                    xml = EntityUtils.toString(response.getEntity(), "UTF-8");
                    EntityUtils.consume(entity);
                    result = MsgXmlUtil.parseXml(xml);
                } finally {
                    response.close();
                }
            } finally {
                httpclient.close();
            }
        } catch (Exception e) {
            logger.error("## 调用微信支付[申请退款]接口失败", e);
        }
        return JSONObject.fromObject(result);
    }

    /**
     * 统一下单
     *
     * @param log
     * @param req
     * @return
     */
    public static JSONObject unifiedOrder(MpAccount mpAccount, String Out_trade_no, String transAmt, String openId,
                                          String spbill_create_ip, String notifyUrl) {
        WXPayUnifiedOrderRequest request = new WXPayUnifiedOrderRequest();
        request.setAppid(StringUtil.trim(mpAccount.getAppid()));
        request.setMch_id(RedisDictProperties.getInstance().getdictValueByCode("WX_CUSTOMER_MCH_ID"));
        request.setDevice_info("WEB");
        request.setNonce_str(StringUtil.trim(UUID.randomUUID().toString().replace("-", "")));
        request.setBody(CustomerWxPayApi.BODY);
        request.setAttach(StringUtil.trim(Out_trade_no));
        request.setOut_trade_no(StringUtil.trim(Out_trade_no));
        request.setTotal_fee(StringUtil.trim(transAmt));
        request.setSpbill_create_ip(spbill_create_ip);
        request.setNotify_url(notifyUrl);
        request.setTrade_type("JSAPI");
        request.setOpenid(StringUtil.trim(openId));

        String xml = null;
        Map<String, String> result = null;
        try {
            String sign = CustomerWxPayApi.genUnifiedOrderSign(request);
            request.setSign(sign);
            String reqXml = MsgXmlUtil.toXML(request);
            logger.info("微信支付统一下单unifiedOrder[{}]", reqXml);
            xml = WxApi.httpsRequestBase(CustomerWxPayApi.getUnifiedOrderUrl(), HttpMethod.POST, reqXml);
            result = MsgXmlUtil.parseXml(xml);
        } catch (Exception e) {
            logger.error("## 调用微信支付[统一下单]接口失败", e);
        }
        return JSONObject.fromObject(result);
    }

    /**
     * 查询订单
     *
     * @param log
     * @return
     */
    public static JSONObject orderQuery(MpAccount mpAccount, String out_trade_no) {
        WXPayOrderQueryRequest request = new WXPayOrderQueryRequest();
        request.setAppid(StringUtil.trim(mpAccount.getAppid()));
        request.setMch_id(RedisDictProperties.getInstance().getdictValueByCode("WX_CUSTOMER_MCH_ID"));
        request.setNonce_str(StringUtil.trim(UUID.randomUUID().toString().replace("-", "")));
        request.setOut_trade_no(StringUtil.trim(out_trade_no));

        String xml = null;
        Map<String, String> result = null;
        try {
            String sign = CustomerWxPayApi.genOrderQuerySign(request);
            request.setSign(sign);
            xml = WxApi.httpsRequestBase(CustomerWxPayApi.getOrderQueryUrl(), HttpMethod.POST,
                    MsgXmlUtil.toXML(request));
            result = MsgXmlUtil.parseXml(xml);
        } catch (Exception e) {
            logger.error("## 调用微信支付[查询订单]接口失败", e);
        }
        return JSONObject.fromObject(result);
    }

    /**
     * 授权码获取openID
     *
     * @param mpAccount
     * @param auth_code
     * @return
     */
    public static JSONObject authCodeOpenId(MpAccount mpAccount, String auth_code) {
        AuthCodeReqOpenId request = new AuthCodeReqOpenId();
        request.setAppid(StringUtil.trim(mpAccount.getAppid()));
        request.setAuth_code(auth_code);
        request.setMch_id(RedisDictProperties.getInstance().getdictValueByCode("WX_CUSTOMER_MCH_ID"));
        request.setNonce_str(StringUtil.trim(UUID.randomUUID().toString().replace("-", "")));
        String xml = null;
        Map<String, String> result = null;
        try {
            String sign = CustomerWxPayApi.genAuthCodeReqOpenId(request);
            request.setSign(sign);
            xml = WxApi.httpsRequestBase(WxApi.getAuthCodeToOpenId(), HttpMethod.POST, MsgXmlUtil.toXML(request));
            result = MsgXmlUtil.parseXml(xml);
        } catch (Exception e) {
            logger.error("## 调用微信支付[授权码查询openid]接口失败", e);
        }
        return JSONObject.fromObject(result);
    }
}
