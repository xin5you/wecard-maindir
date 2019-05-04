package com.cn.thinkx.integrationpay.shenxinpay.utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.cn.thinkx.integrationpay.base.entity.IntegrationPayResp;
import com.cn.thinkx.integrationpay.base.utils.HttpClientUtil;
import com.cn.thinkx.pms.base.utils.NumberUtils;
import com.cn.thinkx.pms.base.utils.StringUtil;

public class PaymentBuildUtil {
	private static Logger log = LoggerFactory.getLogger(PaymentBuildUtil.class);
	
	/**
	 * 申鑫支付接口调用
	 * 
	 * @param keys
	 * @param params
	 * @param service
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String buildPay(String[] keys, String[] params, String service) throws UnsupportedEncodingException {
		String str = _MakeURL(keys, params);
		log.info("申鑫支付待加密参数[{}]", str);
		String sign = MD5Util.getMD5Str1(str + "1234567890ABCDEF");
//		log.info("申鑫支付MD5加密之后获得的签名[{}]", sign);

		StringBuilder sb = new StringBuilder(str.toString());
		sb.append("&");
		sb.append("sign");
		sb.append('=');
		sb.append(sign);
		String paramStr = CryptUtil.GetEncodeStr(sb.toString());
		String finalStr = Base64Util.encode(paramStr.getBytes());
		String param = "sText=" + finalStr;

		return HttpClientUtil.httpUrlConnection(param, service);
	}
	
	public static String buildRefund(String[] keys, String[] params, String service, String[] keysAddi, String[] paramsAddi)
			throws UnsupportedEncodingException {
		String str = _MakeURL2(keys, params);
		log.info("申鑫退款待加密参数[{}]", str);
		String sign = MD5Util.getMD5Str1(str);
//		log.info("申鑫退款MD5加密之后获得的签名[{}]", sign);

		JSONObject jsonObject = new JSONObject();
		for (int i = 0; i < keys.length; i++) {
			jsonObject.put(keys[i], params[i]);
		}

		for (int i = 0; i < keys.length; i++) {
			jsonObject.put(keysAddi[i], paramsAddi[i]);
		}
		jsonObject.put("signData", str);
		jsonObject.put("signResult", sign);

		return HttpClientUtil.refundHttpUrlConnection(jsonObject.toJSONString(), service);
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
	public static IntegrationPayResp parseSXPayReturnXml(String str) throws IOException, DocumentException {
		IntegrationPayResp resp = new IntegrationPayResp();
		// 解析XML
		SAXReader reader = new SAXReader();
		Document document = reader.read(new ByteArrayInputStream(str.getBytes()));
		Element root = document.getRootElement();
		List<Element> elementList = root.elements();
		for (Element e : elementList) {
			String name = e.getName();
			String text = e.getText();
			if ("RSPCOD".equals(name)) {
				resp.setCode(text);
			} else if ("RSPMSG".equals(name)) {
				resp.setInfo(text);
			} else if ("OUTORDERID".equals(name)) {
				resp.setTermSwtFlowNo(text);
			} else if ("ORD_NO".equals(name)) {
				resp.setSwtFlowNo(text);
			} else if ("ORDER_ID".equals(name)) {
				resp.setRemarks(text);
			} else if ("ORD_DATE".equals(name)) {
				resp.setTransDate(text);
			} else if ("ORD_TIME".equals(name)) {
				resp.setTransDate(resp.getTransDate()+" "+text);
			} else if ("PAY_CHANNEL".equals(name)) {
				resp.setPaymentType(text);
			} else if ("BUYERPAYAMOUT".equals(name)) {
				resp.setTransAmt(NumberUtils.RMBYuanToCent(text));
			} else if ("POINTAMOUT".equals(name)) {
				resp.setPreferentialAmt(text);
			} 
		}
		return resp;
	}
	
	
	/**
	 * 解析申鑫退款的返回信息
	 * 
	 * @param str
	 * @return
	 * @throws IOException
	 * @throws DocumentException
	 */
	public static IntegrationPayResp parseSXRefund(String str) {
		IntegrationPayResp resp = new IntegrationPayResp();
		JSONObject obj = JSONObject.parseObject(str);
		if (!StringUtil.isNullOrEmpty(obj.getString("version"))) {
			resp.setVersion(obj.getString("version"));
		}
		if (!StringUtil.isNullOrEmpty(obj.getString("respCode"))) {
			resp.setCode(obj.getString("respCode"));
		}
		if (!StringUtil.isNullOrEmpty(obj.getString("respMessage"))) {
			resp.setInfo(obj.getString("respMessage"));
		}
		if (!StringUtil.isNullOrEmpty(obj.getString("instId"))) {
			resp.setInstId(obj.getString("instId"));
		}
		if (!StringUtil.isNullOrEmpty(obj.getString("mid"))) {
			resp.setmId(obj.getString("mid"));
		}
		if (!StringUtil.isNullOrEmpty(obj.getString("tid"))) {
			resp.settId(obj.getString("tid"));
		}
		if (!StringUtil.isNullOrEmpty(obj.getString("instOrderId"))) {
			resp.setSwtFlowNo(obj.getString("instOrderId"));
		}
		if (!StringUtil.isNullOrEmpty(obj.getString("requestStatus"))) {
			resp.setRequestStatus(obj.getString("requestStatus"));
		}
		if (!StringUtil.isNullOrEmpty(obj.getString("status"))) {
			resp.setStatus(obj.getString("status"));
		}
		if (!StringUtil.isNullOrEmpty(obj.getString("respDate"))) {
			resp.setTransDate(obj.getString("respDate"));
		}
		if (!StringUtil.isNullOrEmpty(obj.getString("respTime"))) {
			resp.setTransDate(resp.getTransDate() + " " + obj.getString("respTime"));
		}
		if (!StringUtil.isNullOrEmpty(obj.getString("traceId"))) {
			resp.setTraceId(obj.getString("traceId"));
		}
		if (!StringUtil.isNullOrEmpty(obj.getString("refundAmt"))) {
			resp.setTransAmt(NumberUtils.RMBYuanToCent(obj.getString("refundAmt")));
		}
		if (!StringUtil.isNullOrEmpty(obj.getString("platformSeq"))) {
			resp.setRemarks(obj.getString("platformSeq"));
		}
		if (!StringUtil.isNullOrEmpty(obj.getString("refundSeq"))) {
			resp.setRefundSeq(obj.getString("refundSeq"));
		}
		return resp;
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
	public static IntegrationPayResp parseSXQueryReturnXml(String str) throws IOException, DocumentException {
		IntegrationPayResp resp = new IntegrationPayResp();
		// 解析XML
		SAXReader reader = new SAXReader();
		Document document = reader.read(new ByteArrayInputStream(str.getBytes()));
		Element root = document.getRootElement();
		List<Element> elementList = root.elements();
		for (Element e : elementList) {
			String name = e.getName();
			String text = e.getText();
			if ("PAY_STATUS".equals(name)) {
				resp.setPayStatus(text);
			} else if ("PAY_DESC".equals(name)) {
				resp.setPayDesc(text);
			} else if ("RSPMSG".equals(name)) {
				resp.setRespMsg(text);
			} else if ("ORD_ID".equals(name)) {
				resp.setSwtFlowNo(text);
			} else if ("RSPCOD".equals(name)) {
				resp.setRespCode(text);
			}
		}
		return resp;
	}
}
