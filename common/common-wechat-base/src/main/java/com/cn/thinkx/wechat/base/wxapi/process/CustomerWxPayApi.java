package com.cn.thinkx.wechat.base.wxapi.process;

import com.cn.thinkx.pms.base.redis.util.RedisDictProperties;
import com.cn.thinkx.pms.base.utils.StringUtil;
import com.cn.thinkx.pms.base.utils.StringUtils;
import com.cn.thinkx.wechat.base.wxapi.util.WxSignUtil;
import com.cn.thinkx.wechat.base.wxapi.vo.AuthCodeReqOpenId;
import com.cn.thinkx.wechat.base.wxapi.vo.WXPayOrderQueryRequest;
import com.cn.thinkx.wechat.base.wxapi.vo.WXPayRefundRequest;
import com.cn.thinkx.wechat.base.wxapi.vo.WXPayUnifiedOrderRequest;
import net.sf.json.JSONObject;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

/**
 * 微信支付 API
 */

public class CustomerWxPayApi {

    // 统一下单-商品描述
    public static final String BODY = "薪无忧-预付卡";
    // 统一下单请求URL
    private static final String UNIFIED_ORDER = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    // 订单查询请求URL
    private static final String ORDER_QUERY = "https://api.mch.weixin.qq.com/pay/orderquery";
    // 申请退款请求URL
    private static final String REFUND_URL = "https://api.mch.weixin.qq.com/secapi/pay/refund";

    /**
     * 获取统一下单请求URL
     *
     * @return
     */
    public static String getUnifiedOrderUrl() {
        return UNIFIED_ORDER;
    }

    /**
     * 获取订单查询请求URL
     *
     * @return
     */
    public static String getOrderQueryUrl() {
        return ORDER_QUERY;
    }

    /**
     * 获取申请退款请求URL
     *
     * @return
     */
    public static String getRefundUrl() {
        return REFUND_URL;
    }

    /**
     * 微信支付密钥
     **/
    public static String getWxPayKey() {
        return RedisDictProperties.getInstance().getdictValueByCode("WX_CUSTOMER_PAY_KEY");
    }

    /**
     * 申请退款签名
     *
     * @param req
     * @return
     * @throws Exception
     */
    public static String getRefundSign(WXPayRefundRequest req) throws Exception {
        SortedMap<String, String> maps = WxSignUtil.genSortedMap(req);
        StringBuffer buf = new StringBuffer();
        for (Map.Entry<String, String> item : maps.entrySet()) {
            if (StringUtils.isNotBlank(item.getKey())) {
                String key = item.getKey();
                String val = item.getValue();
                buf.append(key).append("=").append(val);
                buf.append("&");
            }
        }
        buf.append("key").append("=").append(CustomerWxPayApi.getWxPayKey());
        String signtemp = buf.toString();
        return WxSignUtil.MD5Encode(signtemp).toUpperCase();
    }

    /**
     * 生成统一下单签名
     *
     * @param req
     * @return
     * @throws Exception
     */
    public static String genUnifiedOrderSign(WXPayUnifiedOrderRequest req) throws Exception {
        String signTemp = "appid=" + req.getAppid() + "&attach=" + req.getAttach() + "&body=" + req.getBody()
                + "&device_info=" + req.getDevice_info() + "&mch_id=" + req.getMch_id() + "&nonce_str="
                + req.getNonce_str() + "&notify_url=" + req.getNotify_url() + "&openid=" + req.getOpenid()
                + "&out_trade_no=" + req.getOut_trade_no() + "&spbill_create_ip=" + req.getSpbill_create_ip()
                + "&total_fee=" + req.getTotal_fee() + "&trade_type=" + req.getTrade_type() + "&key=" + CustomerWxPayApi.getWxPayKey();
        return WxSignUtil.MD5Encode(signTemp).toUpperCase();
    }

    /**
     * 生成统一下单返回签名
     *
     * @param obj
     * @return
     */
    public static String genUnifiedOrderBackSign(JSONObject obj) {
        StringBuffer sb = new StringBuffer();
        if (obj != null) {
            sb.append("appid=" + StringUtil.trim(obj.getString("appid")));
            if (obj.containsKey("code_url")) {
                sb.append("&code_url=" + StringUtil.trim(obj.getString("code_url")));
            }
            if (obj.containsKey("device_info")) {
                sb.append("&device_info=" + StringUtil.trim(obj.getString("device_info")));
            }
            if (obj.containsKey("err_code")) {
                sb.append("&err_code=" + StringUtil.trim(obj.getString("err_code")));
            }
            if (obj.containsKey("err_code_des")) {
                sb.append("&err_code_des=" + StringUtil.trim(obj.getString("err_code_des")));
            }
            sb.append("&mch_id=" + StringUtil.trim(obj.getString("mch_id")));
            sb.append("&nonce_str=" + StringUtil.trim(obj.getString("nonce_str")));
            sb.append("&prepay_id=" + StringUtil.trim(obj.getString("prepay_id")));
            sb.append("&result_code=" + StringUtil.trim(obj.getString("result_code")));
            sb.append("&return_code=" + StringUtil.trim(obj.getString("return_code")));
            sb.append("&return_msg=" + StringUtil.trim(obj.getString("return_msg")));
            sb.append("&trade_type=" + StringUtil.trim(obj.getString("trade_type")));
            sb.append("&key=" + CustomerWxPayApi.getWxPayKey());
            return WxSignUtil.MD5Encode(sb.toString()).toUpperCase();
        }
        return null;
    }

    /**
     * 获取微信支付签名
     *
     * @param appId
     * @param timeStamp
     * @param nonceStr
     * @param packageStr
     * @return
     */
    public static String getPaySign(String appId, String timeStamp, String nonceStr, String packageStr) {
        String signTemp = "appId=" + appId + "&nonceStr=" + nonceStr + "&package=" + packageStr + "&signType=MD5"
                + "&timeStamp=" + timeStamp + "&key=" + CustomerWxPayApi.getWxPayKey();
        return WxSignUtil.MD5Encode(signTemp).toUpperCase();
    }

    /**
     * 生成订单查询签名
     *
     * @param req
     * @return
     * @throws Exception
     */
    public static String genOrderQuerySign(WXPayOrderQueryRequest req) throws Exception {
        String signTemp = "appid=" + req.getAppid() + "&mch_id=" + req.getMch_id() + "&nonce_str="
                + req.getNonce_str() + "&out_trade_no=" + req.getOut_trade_no() + "&key=" + CustomerWxPayApi.getWxPayKey();
        return WxSignUtil.MD5Encode(signTemp).toUpperCase();
    }


    /**
     * 微信支付二维码获取openId
     *
     * @param req
     * @return
     */
    public static String genAuthCodeReqOpenId(AuthCodeReqOpenId req) {
        String signTemp = "appid=" + req.getAppid() + "&auth_code=" + req.getAuth_code() + "&mch_id=" + req.getMch_id() + "&nonce_str="
                + req.getNonce_str() + "&key=" + CustomerWxPayApi.getWxPayKey();
        return WxSignUtil.MD5Encode(signTemp).toUpperCase();
    }

    /**
     * 解析微信支付成功后的返回信息
     *
     * @param request
     * @return
     * @throws IOException
     * @throws DocumentException
     */
    @SuppressWarnings("unchecked")
    public static JSONObject parseWxPayReturnXml(HttpServletRequest request) throws IOException, DocumentException {
        JSONObject obj = new JSONObject();
        // 解析XML
        InputStream inputStream = request.getInputStream();

        SAXReader reader = new SAXReader();
        Document document = reader.read(inputStream);
        Element root = document.getRootElement();
        List<Element> elementList = root.elements();
        for (Element e : elementList) {
            String name = e.getName();
            String text = e.getText();
            if ("return_code".equals(name)) {
                obj.put("return_code", text);
            } else if ("return_msg".equals(name)) {
                obj.put("return_msg", text);
            } else if ("appid".equals(name)) {
                obj.put("appid", text);
            } else if ("mch_id".equals(name)) {
                obj.put("mch_id", text);
            } else if ("device_info".equals(name)) {
                obj.put("device_info", text);
            } else if ("nonce_str".equals(name)) {
                obj.put("nonce_str", text);
            } else if ("sign".equals(name)) {
                obj.put("sign", text);
            } else if ("result_code".equals(name)) {
                obj.put("result_code", text);
            } else if ("err_code".equals(name)) {
                obj.put("err_code", text);
            } else if ("err_code_des".equals(name)) {
                obj.put("err_code_des", text);
            } else if ("openid".equals(name)) {
                obj.put("openid", text);
            } else if ("is_subscribe".equals(name)) {
                obj.put("is_subscribe", text);
            } else if ("trade_type".equals(name)) {
                obj.put("trade_type", text);
            } else if ("bank_type".equals(name)) {
                obj.put("bank_type", text);
            } else if ("total_fee".equals(name)) {
                obj.put("total_fee", text);
            } else if ("settlement_total_fee".equals(name)) {
                obj.put("settlement_total_fee", text);
            } else if ("fee_type".equals(name)) {
                obj.put("fee_type", text);
            } else if ("cash_fee".equals(name)) {
                obj.put("cash_fee", text);
            } else if ("cash_fee_type".equals(name)) {
                obj.put("cash_fee_type", text);
            } else if ("coupon_fee".equals(name)) {
                obj.put("coupon_fee", text);
            } else if ("coupon_count".equals(name)) {
                obj.put("coupon_count", text);
            } else if ("coupon_type_0".equals(name)) {
                obj.put("coupon_type_0", text);
            } else if ("coupon_id_0".equals(name)) {
                obj.put("coupon_id_0", text);
            } else if ("coupon_fee_0".equals(name)) {
                obj.put("coupon_fee_0", text);
            } else if ("coupon_type_1".equals(name)) {
                obj.put("coupon_type_1", text);
            } else if ("coupon_id_1".equals(name)) {
                obj.put("coupon_id_1", text);
            } else if ("coupon_fee_1".equals(name)) {
                obj.put("coupon_fee_1", text);
            } else if ("coupon_type_2".equals(name)) {
                obj.put("coupon_type_2", text);
            } else if ("coupon_id_2".equals(name)) {
                obj.put("coupon_id_2", text);
            } else if ("coupon_fee_2".equals(name)) {
                obj.put("coupon_fee_2", text);
            } else if ("transaction_id".equals(name)) {
                obj.put("transaction_id", text);
            } else if ("out_trade_no".equals(name)) {
                obj.put("out_trade_no", text);
            } else if ("attach".equals(name)) {
                obj.put("attach", text);
            } else if ("time_end".equals(name)) {
                obj.put("time_end", text);
            }
        }
        return obj;
    }

    /**
     * 获取微信支付回调通知签名
     *
     * @param obj
     * @return
     */
    public static String getPayNotifySign(JSONObject obj) {
        StringBuffer sb = new StringBuffer();
        if (obj != null) {
            sb.append("appid=" + StringUtil.trim(obj.getString("appid")));
            if (obj.containsKey("attach")) {
                sb.append("&attach=" + StringUtil.trim(obj.getString("attach")));
            }
            sb.append("&bank_type=" + StringUtil.trim(obj.getString("bank_type")));
            sb.append("&cash_fee=" + StringUtil.trim(obj.getString("cash_fee")));
            if (obj.containsKey("cash_fee_type")) {
                sb.append("&cash_fee_type=" + StringUtil.trim(obj.getString("cash_fee_type")));
            }
            if (obj.containsKey("coupon_count")) {
                sb.append("&coupon_count=" + StringUtil.trim(obj.getString("coupon_count")));
            }
            if (obj.containsKey("coupon_fee")) {
                sb.append("&coupon_fee=" + StringUtil.trim(obj.getString("coupon_fee")));
            }
            if (obj.containsKey("coupon_fee_0")) {
                sb.append("&coupon_fee_0=" + StringUtil.trim(obj.getString("coupon_fee_0")));
            }
            if (obj.containsKey("coupon_id_0")) {
                sb.append("&coupon_id_0=" + StringUtil.trim(obj.getString("coupon_id_0")));
            }
            if (obj.containsKey("coupon_type_0")) {
                sb.append("&coupon_type_0=" + StringUtil.trim(obj.getString("coupon_type_0")));
            }

            if (obj.containsKey("coupon_fee_1")) {
                sb.append("&coupon_fee_1=" + StringUtil.trim(obj.getString("coupon_fee_1")));
            }
            if (obj.containsKey("coupon_id_1")) {
                sb.append("&coupon_id_1=" + StringUtil.trim(obj.getString("coupon_id_1")));
            }
            if (obj.containsKey("coupon_type_1")) {
                sb.append("&coupon_type_1=" + StringUtil.trim(obj.getString("coupon_type_1")));
            }

            if (obj.containsKey("coupon_fee_2")) {
                sb.append("&coupon_fee_2=" + StringUtil.trim(obj.getString("coupon_fee_2")));
            }
            if (obj.containsKey("coupon_id_2")) {
                sb.append("&coupon_id_2=" + StringUtil.trim(obj.getString("coupon_id_2")));
            }
            if (obj.containsKey("coupon_type_2")) {
                sb.append("&coupon_type_2=" + StringUtil.trim(obj.getString("coupon_type_2")));
            }

            if (obj.containsKey("device_info")) {
                sb.append("&device_info=" + StringUtil.trim(obj.getString("device_info")));
            }
            if (obj.containsKey("err_code")) {
                sb.append("&err_code=" + StringUtil.trim(obj.getString("err_code")));
            }
            if (obj.containsKey("err_code_des")) {
                sb.append("&err_code_des=" + StringUtil.trim(obj.getString("err_code_des")));
            }
            if (obj.containsKey("fee_type")) {
                sb.append("&fee_type=" + StringUtil.trim(obj.getString("fee_type")));
            }
            if (obj.containsKey("is_subscribe")) {
                sb.append("&is_subscribe=" + StringUtil.trim(obj.getString("is_subscribe")));
            }
            sb.append("&mch_id=" + StringUtil.trim(obj.getString("mch_id")));
            sb.append("&nonce_str=" + StringUtil.trim(obj.getString("nonce_str")));
            sb.append("&openid=" + StringUtil.trim(obj.getString("openid")));
            sb.append("&out_trade_no=" + StringUtil.trim(obj.getString("out_trade_no")));
            sb.append("&result_code=" + StringUtil.trim(obj.getString("result_code")));
            sb.append("&return_code=" + StringUtil.trim(obj.getString("return_code")));
            if (obj.containsKey("return_msg")) {
                sb.append("&return_msg=" + StringUtil.trim(obj.getString("return_msg")));
            }
            if (obj.containsKey("settlement_total_fee")) {
                sb.append("&settlement_total_fee=" + StringUtil.trim(obj.getString("settlement_total_fee")));
            }
            sb.append("&time_end=" + StringUtil.trim(obj.getString("time_end")));
            sb.append("&total_fee=" + StringUtil.trim(obj.getString("total_fee")));
            sb.append("&trade_type=" + StringUtil.trim(obj.getString("trade_type")));
            sb.append("&transaction_id=" + StringUtil.trim(obj.getString("transaction_id")));
            sb.append("&key=" + CustomerWxPayApi.getWxPayKey());
            return WxSignUtil.MD5Encode(sb.toString()).toUpperCase();
        }
        return null;
    }

    /**
     * 获取微信支付查询订单签名
     *
     * @param obj
     * @return
     */
    public static String getQueryOrderSign(JSONObject obj) {
        StringBuffer sb = new StringBuffer();
        if (obj != null) {
            sb.append("appid=" + StringUtil.trim(obj.getString("appid")));
            if (obj.containsKey("attach")) {
                sb.append("&attach=" + StringUtil.trim(obj.getString("attach")));
            }
            sb.append("&bank_type=" + StringUtil.trim(obj.getString("bank_type")));
            sb.append("&cash_fee=" + StringUtil.trim(obj.getString("cash_fee")));
            if (obj.containsKey("cash_fee_type")) {
                sb.append("&cash_fee_type=" + StringUtil.trim(obj.getString("cash_fee_type")));
            }
            if (obj.containsKey("coupon_batch_id_0")) {
                sb.append("&coupon_batch_id_0=" + StringUtil.trim(obj.getString("coupon_batch_id_0")));
            }
            if (obj.containsKey("coupon_batch_id_1")) {
                sb.append("&coupon_batch_id_1=" + StringUtil.trim(obj.getString("coupon_batch_id_1")));
            }
            if (obj.containsKey("coupon_batch_id_2")) {
                sb.append("&coupon_batch_id_2=" + StringUtil.trim(obj.getString("coupon_batch_id_2")));
            }
            if (obj.containsKey("coupon_count")) {
                sb.append("&coupon_count=" + StringUtil.trim(obj.getString("coupon_count")));
            }
            if (obj.containsKey("coupon_fee")) {
                sb.append("&coupon_fee=" + StringUtil.trim(obj.getString("coupon_fee")));
            }
            if (obj.containsKey("coupon_fee_0")) {
                sb.append("&coupon_fee_0=" + StringUtil.trim(obj.getString("coupon_fee_0")));
            }
            if (obj.containsKey("coupon_id_0")) {
                sb.append("&coupon_id_0=" + StringUtil.trim(obj.getString("coupon_id_0")));
            }
            if (obj.containsKey("coupon_type_0")) {
                sb.append("&coupon_type_0=" + StringUtil.trim(obj.getString("coupon_type_0")));
            }
            if (obj.containsKey("coupon_fee_1")) {
                sb.append("&coupon_fee_1=" + StringUtil.trim(obj.getString("coupon_fee_1")));
            }
            if (obj.containsKey("coupon_id_1")) {
                sb.append("&coupon_id_1=" + StringUtil.trim(obj.getString("coupon_id_1")));
            }
            if (obj.containsKey("coupon_type_1")) {
                sb.append("&coupon_type_1=" + StringUtil.trim(obj.getString("coupon_type_1")));
            }
            if (obj.containsKey("coupon_fee_2")) {
                sb.append("&coupon_fee_2=" + StringUtil.trim(obj.getString("coupon_fee_2")));
            }
            if (obj.containsKey("coupon_id_2")) {
                sb.append("&coupon_id_2=" + StringUtil.trim(obj.getString("coupon_id_2")));
            }
            if (obj.containsKey("coupon_type_2")) {
                sb.append("&coupon_type_2=" + StringUtil.trim(obj.getString("coupon_type_2")));
            }

            if (obj.containsKey("device_info")) {
                sb.append("&device_info=" + StringUtil.trim(obj.getString("device_info")));
            }
            if (obj.containsKey("err_code")) {
                sb.append("&err_code=" + StringUtil.trim(obj.getString("err_code")));
            }
            if (obj.containsKey("err_code_des")) {
                sb.append("&err_code_des=" + StringUtil.trim(obj.getString("err_code_des")));
            }
            if (obj.containsKey("fee_type")) {
                sb.append("&fee_type=" + StringUtil.trim(obj.getString("fee_type")));
            }
            if (obj.containsKey("is_subscribe")) {
                sb.append("&is_subscribe=" + StringUtil.trim(obj.getString("is_subscribe")));
            }
            sb.append("&mch_id=" + StringUtil.trim(obj.getString("mch_id")));
            sb.append("&nonce_str=" + StringUtil.trim(obj.getString("nonce_str")));
            sb.append("&openid=" + StringUtil.trim(obj.getString("openid")));
            sb.append("&out_trade_no=" + StringUtil.trim(obj.getString("out_trade_no")));
            sb.append("&result_code=" + StringUtil.trim(obj.getString("result_code")));
            sb.append("&return_code=" + StringUtil.trim(obj.getString("return_code")));
            if (obj.containsKey("return_msg")) {
                sb.append("&return_msg=" + StringUtil.trim(obj.getString("return_msg")));
            }
            if (obj.containsKey("settlement_total_fee")) {
                sb.append("&settlement_total_fee=" + StringUtil.trim(obj.getString("settlement_total_fee")));
            }
            sb.append("&time_end=" + StringUtil.trim(obj.getString("time_end")));
            sb.append("&total_fee=" + StringUtil.trim(obj.getString("total_fee")));
            sb.append("&trade_state=" + StringUtil.trim(obj.getString("trade_state")));
            if (obj.containsKey("trade_state_desc")) {
                sb.append("&trade_state_desc=" + StringUtil.trim(obj.getString("trade_state_desc")));
            }
            sb.append("&trade_type=" + StringUtil.trim(obj.getString("trade_type")));
            sb.append("&transaction_id=" + StringUtil.trim(obj.getString("transaction_id")));
            sb.append("&key=" + CustomerWxPayApi.getWxPayKey());
            return WxSignUtil.MD5Encode(sb.toString()).toUpperCase();
        }
        return null;
    }

}