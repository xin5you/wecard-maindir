package com.cn.thinkx.common.service.module.jiafupay.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.cn.thinkx.common.redis.util.RedisDictProperties;
import com.cn.thinkx.common.redis.util.RedisPropertiesUtils;
import com.cn.thinkx.common.service.module.jiafupay.constants.Constants;
import com.cn.thinkx.common.service.module.jiafupay.constants.Constants.JFRespCode;
import com.cn.thinkx.common.service.module.jiafupay.req.JFPayReq;
import com.cn.thinkx.common.service.module.jiafupay.req.JFServiceReq;
import com.cn.thinkx.common.service.module.jiafupay.resp.JFPayResp;
import com.cn.thinkx.common.service.module.jiafupay.resp.JFServiceResp;
import com.cn.thinkx.common.service.module.jiafupay.service.JFPayService;
import com.cn.thinkx.common.service.module.jiafupay.util.HttpClientUtil;
import com.cn.thinkx.common.service.module.jiafupay.util.RSAUtil;
import com.cn.thinkx.common.service.module.jiafupay.vo.JFChnlReq;
import com.cn.thinkx.common.service.module.jiafupay.vo.JFChnlResp;
import com.cn.thinkx.pms.base.utils.BaseConstants.ITFRespCode;
import com.cn.thinkx.pms.base.utils.StringUtil;


@Service("jfPayService")
public class JFPayServiceImpl implements JFPayService {

	private Logger logger = LoggerFactory.getLogger(JFPayServiceImpl.class);

	@Override
	public String doPayMentTrans(JFChnlReq jfChnlReq) {
		JFChnlResp resp = new JFChnlResp();

		String jfParam = jfParams(jfChnlReq);//封装嘉福支付接口请求参数
		String url = RedisDictProperties.getInstance().getdictValueByCode("JF_PAY_URL");//嘉福收银台地址
		//		logger.info("调用嘉福支付接口的URL[{}]，参数[{}]", url, jfParam);

		//http请求嘉福支付接口
		String json = HttpClientUtil.sendPost(url, jfParam);
		if (StringUtil.isNullOrEmpty(json)) {
			logger.error("##调用嘉福支付接口返回参数为空,知了企服流水[{}]", jfChnlReq.getSwtFlowNo());
			json = HttpClientUtil.sendPost(url, jfParam);
			if (StringUtil.isNullOrEmpty(json)) {
				resp.setCode(ITFRespCode.CODE96.getCode());
				resp.setMsg(ITFRespCode.CODE96.getValue());
				return JSONArray.toJSONString(resp);
			}
		}
		
		JFPayResp jfResp = JSONArray.parseObject(json, JFPayResp.class);
		logger.info("嘉福支付接口返回参数[{}]", JSONArray.toJSONString(jfResp));
		
		if (!StringUtil.isNullOrEmpty(jfResp.getResp_data())) {//设置返回值
			JFServiceResp respData = JSON.parseObject(jfResp.getResp_data(), JFServiceResp.class);
			jfResp.setResp_data(JSONArray.toJSONString(respData));
			resp.setSwtFlowNo(respData.getTid());
		}

		//验签
		boolean flag = signVaild(jfResp);
		if (flag) {
			resp.setCode(jfResp.getCode());
			resp.setMsg(jfResp.getErrmsg());
			return JSONArray.toJSONString(resp);
		}
		logger.info("接收嘉福支付接口返回参数，验签失败，嘉福流水[{}],再次调用支付接口确认交易是否成功", jfChnlReq.getSwtFlowNo());

		//验签失败再次调用嘉福收银台确认交易是否成功
		json = HttpClientUtil.sendPost(url, jfParam);
		if (StringUtil.isNullOrEmpty(json)) {
			logger.error("## 调用嘉福支付接口返回参数为空，知了企服流水[{}]", jfChnlReq.getSwtFlowNo());
			resp.setCode(ITFRespCode.CODE96.getCode());
			resp.setMsg(ITFRespCode.CODE96.getValue());
			return JSONArray.toJSONString(resp);
		}
		jfResp = JSONArray.parseObject(json, JFPayResp.class);
		logger.info("再次 调用嘉福支付接口返回参数[{}]", JSONArray.toJSONString(jfResp));
		if (!StringUtil.isNullOrEmpty(jfResp.getSign())) {
			String signParams = urlDecoder(jfResp.getSign());
			jfResp.setSign(signParams);
		}
		flag = signVaild(jfResp);
		if (flag) {
			resp.setCode(jfResp.getCode());
			resp.setMsg(jfResp.getErrmsg());
		} else {
			logger.error("再次调用嘉福支付接口返回参数[{}],验签失败", JSONArray.toJSONString(jfResp));
			resp.setCode(JFRespCode.R03.getCode());
			resp.setMsg(JFRespCode.R03.getValue());
		}

		return JSONArray.toJSONString(resp);
	}

	/**
	 * 请求嘉福支付接口参数封装方法
	 * 
	 * @param jfChnlReq
	 * @return
	 */
	private String jfParams(JFChnlReq jfChnlReq) {
		//封装嘉福支付接口请求参数
		JFServiceReq content = new JFServiceReq();
		content.setAmount(jfChnlReq.getTxnAmount());
		content.setUid(jfChnlReq.getJfUserId());
		content.setBizid(jfChnlReq.getSwtFlowNo());
		content.setPayMethod(jfChnlReq.getPayType());
		JFPayReq jfPayReq = new JFPayReq();
		jfPayReq.setChannel(Constants.CHANNEL);
		jfPayReq.setCharset(Constants.CHARSET);
		jfPayReq.setContent(JSONArray.toJSONString(content));
		jfPayReq.setFormat(Constants.FORMAT);
		jfPayReq.setService(Constants.SERVICE);
		jfPayReq.setSign_type(Constants.SignType.ST1.toString());
		jfPayReq.setTimestamp(String.valueOf(Calendar.getInstance().getTimeInMillis() / 1000));
		jfPayReq.setVersion(Constants.VERSION);
//		logger.info("请求嘉福支付接口对象[{}]", JSONArray.toJSONString(jfPayReq));
		//按字典排序法拼接参数
		String forSignReq = RSAUtil.jfParamMap(jfPayReq);
		String sign = null;
		//按嘉福要求进行RSA加密
		logger.info("请求嘉福支付接口加密HKB_PRIVATEKEY[{}],待加密字符串[{}]", RedisPropertiesUtils.getProperty("HKB_PRIVATEKEY"), forSignReq);
		try {
			sign = RSAUtil.sign(forSignReq.getBytes(), RedisPropertiesUtils.getProperty("HKB_PRIVATEKEY"));
		} catch (Exception e) {
			logger.error("## 调用嘉福支付接口，RSA加密发生异常", e);
		}
//		logger.info("请求嘉福支付接口签名sign[{}]", sign);
		//对参数进行urlencode
		String signParam = urlEncoder(sign);

		//拼接请求参数
		StringBuffer jfParams = new StringBuffer();
		jfParams.append("channel=").append(jfPayReq.getChannel())
			.append("&service=").append(jfPayReq.getService())
			.append("&format=").append(jfPayReq.getFormat())
			.append("&charset=").append(jfPayReq.getCharset())
			.append("&timestamp=").append(jfPayReq.getTimestamp())
			.append("&version=").append(jfPayReq.getVersion())
			.append("&content=").append(jfPayReq.getContent())
			.append("&sign_type=").append(jfPayReq.getSign_type())
			.append("&sign=").append(signParam);
		return jfParams.toString();
	}

	/**
	 * 校验嘉福支付接口返回参数封装方法
	 * 
	 * @param jfResp
	 * @return
	 */
	private boolean signVaild(JFPayResp jfResp){
		//按字典排序法拼接参数
		String forSignResp = RSAUtil.jfParamMap(jfResp);
		boolean flag = false;
		try {
			flag = RSAUtil.verify(forSignResp.getBytes(), RedisPropertiesUtils.getProperty("JF_PUBLICKEY"), jfResp.getSign());
		} catch (Exception e) {
			logger.error("## 接收嘉福支付接口返回参数验签发生异常，嘉福流水[{}]", jfResp.getResp_data(), e);
		}
		return flag;
	}

	/**
	 * 对参数值进行urlEncoder
	 * 
	 * @param params
	 * @return
	 */
	private String urlEncoder (String params) {
		String param = "";
		if (StringUtil.isNullOrEmpty(params)) 
			return param;
		try {
			param = URLEncoder.encode(params, "utf-8");
		} catch (UnsupportedEncodingException e) {
			logger.error("## 对参数params进行urlencode出错，urlencode前为[{}]", params);
		}
		return param;
	}

	/**
	 * 对参数值进行urlDecoder
	 * 
	 * @param params
	 * @return
	 */
	private String urlDecoder (String params) {
		String param = "";
		if (StringUtil.isNullOrEmpty(params)) 
			return param;
		try {
			param = URLDecoder.decode(params, "utf-8");
		} catch (UnsupportedEncodingException e) {
			logger.error("## 对参数params进行urldecode出错，urldecode前为[{}]", params);
		}
		return param;
	}

}
