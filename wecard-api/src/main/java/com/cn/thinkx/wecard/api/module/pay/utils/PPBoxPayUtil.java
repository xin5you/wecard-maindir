package com.cn.thinkx.wecard.api.module.pay.utils;

import com.alibaba.fastjson.JSONObject;
import com.cn.thinkx.pms.base.utils.DateUtil;
import com.cn.thinkx.pms.base.utils.StringUtil;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

/**
 * 派派设备基础数据
 *
 * @author zqy
 */
public class PPBoxPayUtil {
    private static Logger log = LoggerFactory.getLogger(PPBoxPayUtil.class);

    /**
     * 付款授权码获取支付方式
     *
     * @param code
     * @return
     */
    public static String getPayTypeByAuthCode(String authcode) {
        if (StringUtil.notEmpty(authcode)) {
            String code2 = authcode.substring(0, 2);
            int codelength = authcode.length();
            if (("40".equals(code2) || "41".equals(code2) || "42".equals(code2) || "43".equals(code2)
                    || "44".equals(code2) || "45".equals(code2)) && (codelength == 20)) {
                return PPBoxConstants.PPBoxPayType.HKBPAY.getValue();
            } else if (("10".equals(code2) || "11".equals(code2) || "12".equals(code2) || "13".equals(code2)
                    || "14".equals(code2) || "15".equals(code2)) && (codelength == 18)) {
                return PPBoxConstants.PPBoxPayType.WXPAY.getValue();
            } else if (("25".equals(code2) || "26".equals(code2) || "27".equals(code2) || "28".equals(code2)
                    || "29".equals(code2) || "30".equals(code2)) && (codelength >= 16 && codelength <= 24)) {
                return PPBoxConstants.PPBoxPayType.ALIPAY.getValue();
            } else {
                return PPBoxConstants.PPBoxPayType.INVALID.getValue();
            }
        }
        return "";
    }

    public static String sendGet(String url, String param) {
        String result = "";
        BufferedReader in = null;
        try {
            String urlNameString = url + "?" + param;
            URL realUrl = new URL(urlNameString);
            // 打开和URL之间的连接
            URLConnection connection = realUrl.openConnection();
            // 设置通用的请求属性
            connection.setRequestProperty("accept", "*/*");
            connection.setRequestProperty("connection", "Keep-Alive");
            connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible;MSIE 6.0;Windows NT 5.1;SV1)");
            // 建立实际的连接
            connection.connect();
            // 定义 BufferedReader输入流来读取URL的响应
            in = new BufferedReader(new
                    InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            e.printStackTrace();
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return result;
    }

    public static String buildPay(String[] keys, String[] params, String service) throws UnsupportedEncodingException {
        String str = _MakeURL(keys, params);
        log.info("申鑫支付待加密参数[{}]", str);
        String sign = MD5Util.getMD5Str1(str + "1234567890ABCDEF");
        log.info("申鑫支付MD5加密之后获得的签名[{}]", sign);

        StringBuilder sb = new StringBuilder(str.toString());
        sb.append("&");
        sb.append("sign");
        sb.append('=');
        sb.append(sign);
        String paramStr = CryptUtil.GetEncodeStr(sb.toString());
        String finalStr = Base64Util.encode(paramStr.getBytes());

        String param = "sText=" + finalStr;

        InputStream is = null;
        HttpURLConnection httpUrlConnection = null;
        try {
            URL url = new URL("http://27.115.99.214:9905/WebPay/online/" + service);// 测试环境

            URLConnection urlConnection = url.openConnection();
            httpUrlConnection = (HttpURLConnection) urlConnection;
            httpUrlConnection.setDoOutput(true);
            httpUrlConnection.setDoInput(true);
            httpUrlConnection.setUseCaches(false);
            httpUrlConnection.setRequestMethod("POST");
            httpUrlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpUrlConnection.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
            httpUrlConnection.setRequestProperty("Charset", "UTF-8");
            httpUrlConnection.connect();

            DataOutputStream dos = new DataOutputStream(httpUrlConnection.getOutputStream());
            dos.writeBytes(param);
            dos.flush();
            dos.close();

            int resultCode = httpUrlConnection.getResponseCode();
            if (HttpURLConnection.HTTP_OK == resultCode) {
                StringBuffer sb1 = new StringBuffer();
                String readLine = new String();
                BufferedReader responseReader = new BufferedReader(
                        new InputStreamReader(httpUrlConnection.getInputStream()));
                while ((readLine = responseReader.readLine()) != null) {
                    sb1.append(readLine).append("\n");
                }
                responseReader.close();
                return sb1.toString();
            }
        } catch (Exception e) {
            log.error("Send ShenXinPay throws Exception", e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    log.error("Send ShenXinPay throws Exception", e);
                }
            }
            if (httpUrlConnection != null) {
                httpUrlConnection.disconnect();
            }
        }
        return null;
    }

    public static String buildRefund(String[] keys, String[] params, String service, String[] keysAddi, String[] paramsAddi)
            throws UnsupportedEncodingException {

        String str = _MakeURL2(keys, params);
        log.info("申鑫退款待加密参数[{}]", str);
        String sign = MD5Util.getMD5Str1(str);
        log.info("申鑫退款MD5加密之后获得的签名[{}]", sign);

        JSONObject jsonObject = new JSONObject();
        for (int i = 0; i < keys.length; i++) {
            jsonObject.put(keys[i], params[i]);
        }

        for (int i = 0; i < keys.length; i++) {
            jsonObject.put(keysAddi[i], paramsAddi[i]);
        }
        jsonObject.put("signData", str);
        jsonObject.put("signResult", sign);

        InputStream is = null;
        HttpURLConnection httpUrlConnection = null;
        try {
            URL url = new URL("http://27.115.99.214:9905/WebPay/qrc/" + service);

            URLConnection urlConnection = url.openConnection();
            httpUrlConnection = (HttpURLConnection) urlConnection;
            httpUrlConnection.setDoOutput(true);
            httpUrlConnection.setDoInput(true);
            httpUrlConnection.setUseCaches(false);
            httpUrlConnection.setRequestMethod("GET");
            httpUrlConnection.setRequestProperty("Content-Type", "application/json");
            httpUrlConnection.setRequestProperty("Connection", "Keep-Alive");// 维持长连接
            httpUrlConnection.setRequestProperty("Charset", "UTF-8");
            httpUrlConnection.connect();
            DataOutputStream dos = new DataOutputStream(httpUrlConnection.getOutputStream());
            dos.writeBytes(jsonObject.toJSONString());
            dos.flush();
            dos.close();

            int resultCode = httpUrlConnection.getResponseCode();
            if (HttpURLConnection.HTTP_OK == resultCode) {
                StringBuffer sb1 = new StringBuffer();
                String readLine = new String();
                BufferedReader responseReader = new BufferedReader(new InputStreamReader(httpUrlConnection.getInputStream()));
                while ((readLine = responseReader.readLine()) != null) {
                    sb1.append(readLine).append("\n");
                }
                responseReader.close();
                return sb1.toString();
            }
        } catch (Exception e) {
            log.error("Send ShenXinRefund throws Exception", e);
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    log.error("Send ShenXinRefund throws Exception", e);
                }
            }
            if (httpUrlConnection != null) {
                httpUrlConnection.disconnect();
            }
        }
        return null;
    }

    public static String _MakeURL(String[] keys, String[] params) {
        if (keys.length != params.length) {
            return null;
        }

        StringBuilder url = new StringBuilder();
        for (int i = 0; i < params.length; i++) {
            url.append('&');
            url.append(keys[i]);
            url.append('=');
            url.append(params[i]);
        }

        return url.toString().replaceFirst("&", "");
    }

    public static String _MakeURL2(String[] keys, String[] params) {
        if (keys.length != params.length) {
            return null;
        }

        StringBuilder url = new StringBuilder();
        for (int i = 0; i < params.length; i++) {
            url.append('&');
            url.append(params[i]);
        }

        return url.toString().replaceFirst("&", "");
    }

    public static String _MakeSign(String[] params) {
        StringBuilder sign = new StringBuilder();
        for (int i = 0; i < params.length; i++) {
            sign.append('&');
            sign.append(params[i]);
        }
        return sign.toString().replaceFirst("&", "");
    }

    /**
     * 解析申鑫支付的返回信息
     *
     * @param str
     * @return
     * @throws IOException
     * @throws DocumentException
     */
    @SuppressWarnings("unchecked")
    public static JSONObject parseSXPayReturnXml(String str) throws IOException, DocumentException {
        JSONObject obj = new JSONObject();
        // 解析XML
        SAXReader reader = new SAXReader();
        Document document = reader.read(new ByteArrayInputStream(str.getBytes()));
        Element root = document.getRootElement();
        List<Element> elementList = root.elements();
        for (Element e : elementList) {
            String name = e.getName();
            String text = e.getText();
            if ("RSPCOD".equals(name)) {
                obj.put("RSPCOD", text);
            } else if ("RSPMSG".equals(name)) {
                obj.put("RSPMSG", text);
            } else if ("OUTORDERID".equals(name)) {
                obj.put("OUTORDERID", text);
            } else if ("ORD_NO".equals(name)) {
                obj.put("ORD_NO", text);
            } else if ("ORDER_ID".equals(name)) {
                obj.put("ORDER_ID", text);
            } else if ("ORD_DATE".equals(name)) {
                obj.put("ORD_DATE", text);
            } else if ("ORD_TIME".equals(name)) {
                obj.put("ORD_TIME", text);
            } else if ("PAY_CHANNEL".equals(name)) {
                obj.put("PAY_CHANNEL", text);
            } else if ("BUYERPAYAMOUT".equals(name)) {
                obj.put("BUYERPAYAMOUT", text);
            } else if ("POINTAMOUT".equals(name)) {
                obj.put("POINTAMOUT", text);
            }
        }
        return obj;
    }

    public static void main(String[] args) {
		/*String authCode = "251234567891234567";
		System.out.println("支付方式：" + PPBoxPayUtil.getPayTypeByAuthCode(authCode));*/
		
		/*String[] keys = { "INSTID", "USRID", "OUTORDERID", "TXAMT", "BODY", "TXNTYPE",
                "NOTIFYURL", "SCENE", "AUTHCODE", "SUBJECT"};
		String[] params = { "000000", "854290057223034", "11111111111111112", "1", "测试",
                  "05", "http://gn3pr7.natappfree.cc/WebPay/notify/shbank_noticed", "bar_code", "135102184615165393", "CESHI"};
		try {
			String str = buildPay(keys, params, "online_barcode_dopay.xml");
			JSONObject obj = PPBoxPayUtil.parseSXPayReturnXml(str);
			System.out.println(str.trim());
			System.out.println(obj.getString("RSPCOD"));
			System.out.println(obj.getString("OUTORDERID"));
		} catch (Exception e) {
			e.printStackTrace();
		} */

        String[] keys = {"instId", "mid", "tid", "instOrderId", "refundAmt", "platformSeq", "oriTradeDate"};
        String[] params = {"000000", "854290057223034", "00901978", "71aaff881967457a9f1e93a782893e1d", "0.01", "024476", "20180326"};

        String[] keysAddi = {"batchNo", "channelFlag", "notifyURL", "version", "media", "requestDate", "requestTime"};
        String[] paramsAddi = {"", "WECHAT", "hkb", "1.0.0", "app", DateUtil.getCurrentDateStr(), DateUtil.getCurrentTimeStr()};

        try {
            String str = buildRefund(keys, params, "refund", keysAddi, paramsAddi);
            System.out.println(str.trim());
		   /*{"version":"1.0.0","respCode":"00","respMessage":"退款成功",
			"instId":"000000","mid":"854290057223034","tid":"000000",
			"instOrderId":"5b2aa582f72e4a84a4987ef5785947cd",
			"requestStatus":"S","status":"S",
			"respDate":"20180307","respTime":"141607",
			"traceId":"7c862ac92af34dfe85114dbb161becbc",
			"refundAmt":"1","platformSeq":"024333",
			"refundSeq":"000525"}*/
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
