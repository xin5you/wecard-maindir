package com.cn.thinkx.wecard.customer.module.eshop.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Calendar;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.cn.thinkx.common.redis.util.RedisConstants;
import com.cn.thinkx.common.wecard.domain.eshop.MchntEshopInf;
import com.cn.thinkx.common.wecard.domain.person.PersonInf;
import com.cn.thinkx.common.wecard.module.eshop.mapper.MchntEshopInfMapper;
import com.cn.thinkx.pms.base.http.HttpClientUtil;
import com.cn.thinkx.pms.base.utils.BaseConstants;
import com.cn.thinkx.pms.base.utils.MD5Util;
import com.cn.thinkx.pms.base.utils.RandomUtils;
import com.cn.thinkx.pms.base.utils.StringUtil;
import com.cn.thinkx.wecard.customer.module.customer.service.PersonInfService;
import com.cn.thinkx.wecard.customer.module.customer.util.SignUtils;
import com.cn.thinkx.wecard.customer.module.eshop.service.MchntEshopInfService;
import com.cn.thinkx.wecard.customer.module.eshop.util.DES3Util;
import com.cn.thinkx.wecard.customer.module.eshop.vo.HTTTDParams;
import com.cn.thinkx.wecard.customer.module.eshop.vo.JFExtandJson;
import com.cn.thinkx.wecard.customer.module.eshop.vo.JFHome;

import net.sf.json.JSONObject;
import redis.clients.jedis.JedisCluster;

@Service("mchntEshopInfService")
public class MchntEshopInfServiceImpl implements MchntEshopInfService {
	
	Logger logger = LoggerFactory.getLogger(MchntEshopInfServiceImpl.class);
	
	@Autowired
	@Qualifier("mchntEshopInfMapper")
	private MchntEshopInfMapper mchntEshopInfMapper;
	
	@Autowired
	@Qualifier("personInfService")
	private PersonInfService personInfService;

	@Autowired
	@Qualifier("jedisCluster")
	private JedisCluster jedisCluster;

	@Override
	public MchntEshopInf getMchntEshopInfById(String eShopId) {
		return mchntEshopInfMapper.getMchntEshopInfById(eShopId);
	}

	@Override
	public List<MchntEshopInf> getMchntEshopInfList(MchntEshopInf mchEshop) {
		return mchntEshopInfMapper.getMchntEshopInfList(mchEshop);
	}

	@Override
	public MchntEshopInf getMchntEshopInfByMchntCode(String mchntCode) {
		return mchntEshopInfMapper.getMchntEshopInfByMchntCode(mchntCode);
	}

	@Override
	public String JFUrl(HttpServletRequest request, PersonInf personInf, String ecmChnl, String mchntCode,
			String shopCode) {
		String JD_KEY = jedisCluster.hget(RedisConstants.REDIS_HASH_TABLE_TB_BASE_DICT_KV, BaseConstants.ACC_HKB_JD_MD5_KEY);
		String JD_URL = jedisCluster.hget(RedisConstants.REDIS_HASH_TABLE_TB_BASE_DICT_KV, BaseConstants.ACC_HKB_JD_URL);
		//把参数按照嘉福的要求添加至Map中,并对解密参数进行MD5加密（加密后的用户信息）
		JFHome biz_content = new JFHome();
		biz_content.setE_eid(BaseConstants.E_EID.toString());
		biz_content.setE_uid(personInf.getUserId());
		biz_content.setMobile(personInf.getMobilePhoneNo());
		if (("40006005").equals(ecmChnl)) {
			biz_content.setType("2");
		} else if (("40006004").equals(ecmChnl)) {
			biz_content.setType("1");
		}
		String JFExtand = toJFExtandJsonStr(personInf.getMobilePhoneNo(), personInf.getUserId(), mchntCode, shopCode, ecmChnl, BaseConstants.ChannelCode.CHANNEL1.toString());
		SortedMap<String, String> itemsMap = new TreeMap<String, String>();
		itemsMap.put("biz_content", JSONArray.toJSONString(biz_content));
		itemsMap.put("charset", "utf-8");
		itemsMap.put("extand_params", JFExtand);
		itemsMap.put("format", "json");
		itemsMap.put("ident", BaseConstants.IDENT.toString());
		switch (ecmChnl) {
		case "40006005":
			itemsMap.put("service", BaseConstants.DIANPING_SERVICE.toString());
			break;
		case "40006004":
			itemsMap.put("service", BaseConstants.MEITUAN_SERVICE.toString());
			break;
		case "40006003":
			itemsMap.put("service", BaseConstants.JINGDONG_SERVICE.toString());
			break;
		default:
			break;
		}
		itemsMap.put("sign_type", "MD5");
		itemsMap.put("timestamp", String.valueOf(Calendar.getInstance().getTimeInMillis() / 1000));
		itemsMap.put("version", "1.0");
		StringBuilder forSign = new StringBuilder();
		for (String key : itemsMap.keySet()) {
			forSign.append(key).append("=").append(itemsMap.get(key)).append("&");
		}
		forSign.deleteCharAt(forSign.length()-1);
		forSign.append(JD_KEY.toString());
		String signs = SignUtils.MD5Encode(forSign.toString());

		//对参数进行urlencode
		String biz_contentUrl = urlEncoder(itemsMap.get("biz_content"));
		String extand_params = urlEncoder(itemsMap.get("extand_params"));

		//跳转嘉福链接及url参数
		StringBuffer jfUrl = new StringBuffer();
		jfUrl.append(JD_URL)
			.append("?ident=").append(itemsMap.get("ident")).append("&service=").append(itemsMap.get("service"))
			.append("&format=").append(itemsMap.get("format")).append("&charset=").append(itemsMap.get("charset"))
			.append("&timestamp=").append(itemsMap.get("timestamp")).append("&version=").append(itemsMap.get("version"))
			.append("&biz_content=").append(biz_contentUrl).append("&extand_params=").append(extand_params)
			.append("&sign_type=").append(itemsMap.get("sign_type")).append("&sign=").append(signs.toLowerCase());
		return jfUrl.toString();
	}

	@Override
	public String HTTTDUrl(HttpServletRequest request, PersonInf personInf) {
		String HTTTD_KEY = jedisCluster.hget(RedisConstants.REDIS_HASH_TABLE_TB_BASE_DICT_KV, BaseConstants.HTTTD_3DES_KEY);
		String HTTTD_VALUES_URL = jedisCluster.hget(RedisConstants.REDIS_HASH_TABLE_TB_BASE_DICT_KV, BaseConstants.HTTTD_VALUES_URL);
		String HTTTD_HTTP_URL = jedisCluster.hget(RedisConstants.REDIS_HASH_TABLE_TB_BASE_DICT_KV, BaseConstants.HTTTD_HTTP_URL);
		String APPID = jedisCluster.hget(RedisConstants.REDIS_HASH_TABLE_TB_BASE_DICT_KV, BaseConstants.HTTTD_APPID);
		String INSTITUTIONNO = jedisCluster.hget(RedisConstants.REDIS_HASH_TABLE_TB_BASE_DICT_KV, BaseConstants.HTTTD_INSTITUTIONNO);
		
		String randomKey = RandomUtils.getRandomNumbernStr(6);
		String timestamp = String.valueOf(System.currentTimeMillis());
		/** 生成 sign */
		SortedMap<String, String> HTTTDSignMap = new TreeMap<String, String>();
		HTTTDSignMap.put("appId", APPID);
		HTTTDSignMap.put("institutionNo", INSTITUTIONNO);
		HTTTDSignMap.put("mobile", personInf.getMobilePhoneNo());
		HTTTDSignMap.put("nickName", personInf.getPersonalName());
		HTTTDSignMap.put("randomKey", randomKey);
		HTTTDSignMap.put("timestamp", timestamp);
		HTTTDSignMap.put("userId", personInf.getUserId());
		StringBuilder htttdSignParams = new StringBuilder();
		for (String key : HTTTDSignMap.keySet()) {
			htttdSignParams.append(key).append("=").append(HTTTDSignMap.get(key)).append("&");
		}
		htttdSignParams.append("key=").append(HTTTD_KEY);
		String sign = MD5Util.sha1(htttdSignParams.toString());
		logger.info("海豚通通兑--->加密前sign数据[{}]，加密后sign数据[{}]", htttdSignParams.toString(), sign);
		
		/** data 参数3DES加密*/
		HTTTDParams data = new HTTTDParams();
		data.setInstitutionNo(INSTITUTIONNO);
		data.setMobile(personInf.getMobilePhoneNo());
		data.setNickName(personInf.getPersonalName());
		data.setUserId(personInf.getUserId());
		String dataDesc = DES3Util.encryptMode(HTTTD_KEY, JSONArray.toJSONString(data));
		logger.info("海豚通通兑--->加密前data数据[{}]，加密后data数据[{}]", JSONArray.toJSONString(data), dataDesc);
		//请求海豚通通兑获取token等参数值
		JSONObject params = new JSONObject();
	    params.put("appId", APPID);
	    params.put("data", dataDesc);
	    params.put("randomKey", randomKey);
	    params.put("timestamp", timestamp);
	    params.put("sign", sign);
	    logger.info("海豚通通兑--->海豚通商城链接[{}]，请求参数[{}]", HTTTD_VALUES_URL, params.toString());
		String json = HttpClientUtil.sendPost(HTTTD_VALUES_URL, params.toString());
		if (StringUtil.isNullOrEmpty(json)) {
			logger.error("##海豚通通兑--->请求海豚通获取商城参数为空，userID[{}]", personInf.getUserId());
			return null;
		}
		logger.info("海豚通通兑--->请求海豚通获取token参数[{}]", json);
		HTTTDParams htttdParams = JSONArray.parseObject(json, HTTTDParams.class);
		String token = DES3Util.decryptMode(HTTTD_KEY, htttdParams.getSessionId());
		//跳转海豚通通兑商城链接
		StringBuffer HTTTDUrl = new StringBuffer();
		HTTTDUrl.append(HTTTD_HTTP_URL)
			.append("?token=").append(token)
			.append("&appId=").append(APPID)
			.append("&userId=").append(personInf.getUserId())
			.append("&institutionNo=").append(INSTITUTIONNO);
		logger.info("海豚通通兑--->跳转海豚通通兑商城链接参数[{}]", HTTTDUrl);
		return HTTTDUrl.toString();
	}
	
	/**
	 * 嘉福拓展参数封装方法
	 * 
	 * @param pNo
	 * @param uNo
	 * @param mNo
	 * @param sNo
	 * @param eCh
	 * @param ch
	 * @return
	 */
	private String toJFExtandJsonStr(String pNo, String uNo, String mNo, String sNo, String eCh, String ch) {
		JFExtandJson extJson = new JFExtandJson();
		extJson.setPhoneNo(pNo);
		extJson.setUserId(uNo);
		extJson.setMchntCode(mNo);
		extJson.setShopCode(sNo);
		extJson.setEcomChnl(eCh);
		extJson.setChannel(ch);
		return JSONArray.toJSONString(extJson);
	}

	/**
	 * 对参数值进行urlEncoder
	 * 
	 * @param params
	 * @return
	 */
	private String urlEncoder(String params){
		String encoderParams = "";
		try {
			encoderParams = URLEncoder.encode(params, "utf-8");
		} catch (UnsupportedEncodingException e) {
			logger.error("## 通卡商城--->对参数params进行urlencode出错，urlencode前为[{}]", params);
		}
		return encoderParams;
	}

}
