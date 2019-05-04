package com.cn.thinkx.wecard.api.module.withdraw.service.impl;

import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cn.thinkx.common.redis.util.RedisConstants;
import com.cn.thinkx.common.redis.util.RedisDictProperties;
import com.cn.thinkx.common.redis.util.RedisPropertiesUtils;
import com.cn.thinkx.pms.base.utils.BaseConstants;
import com.cn.thinkx.pms.base.utils.BaseConstants.TransFeeType;
import com.cn.thinkx.pms.base.utils.BaseConstants.TransType;
import com.cn.thinkx.pms.base.utils.BaseConstants.orderStat;
import com.cn.thinkx.pms.base.utils.BaseConstants.orderType;
import com.cn.thinkx.pms.base.utils.DateUtil;
import com.cn.thinkx.pms.base.utils.RandomUtils;
import com.cn.thinkx.pms.base.utils.SignUtil;
import com.cn.thinkx.pms.base.utils.StringUtil;
import com.cn.thinkx.wecard.api.module.welfaremart.model.CardKeys;
import com.cn.thinkx.wecard.api.module.welfaremart.model.CardKeysOrderInf;
import com.cn.thinkx.wecard.api.module.welfaremart.model.CardKeysProduct;
import com.cn.thinkx.wecard.api.module.welfaremart.model.CardKeysTransLog;
import com.cn.thinkx.wecard.api.module.welfaremart.model.UserBankInf;
import com.cn.thinkx.wecard.api.module.welfaremart.service.CardKeysOrderInfService;
import com.cn.thinkx.wecard.api.module.welfaremart.service.CardKeysProductService;
import com.cn.thinkx.wecard.api.module.welfaremart.service.CardKeysService;
import com.cn.thinkx.wecard.api.module.welfaremart.service.CardKeysTransLogService;
import com.cn.thinkx.wecard.api.module.welfaremart.service.UserBankInfService;
import com.cn.thinkx.wecard.api.module.withdraw.domain.WithdrawOrder;
import com.cn.thinkx.wecard.api.module.withdraw.mapper.WithdrawOrderMapper;
import com.cn.thinkx.wecard.api.module.withdraw.service.WithdrawOrderService;
import com.cn.thinkx.wecard.api.module.withdraw.suning.utils.BizUtil;
import com.cn.thinkx.wecard.api.module.withdraw.suning.utils.Constants;
import com.cn.thinkx.wecard.api.module.withdraw.suning.utils.HttpClientUtil;
import com.cn.thinkx.wecard.api.module.withdraw.util.MessageUtil;
import com.cn.thinkx.wecard.api.module.withdraw.vo.WelfaremartResellReq;
import com.cn.thinkx.wecard.api.module.withdraw.vo.WelfaremartResellResp;
import com.cn.thinkx.wecard.api.module.withdraw.vo.WithdrawOrderVO;

import redis.clients.jedis.JedisCluster;

@Service("withdrawOrderService")
public class WithdrawOrderServiceImpl implements WithdrawOrderService {
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	private static final String MERCHANT_NO = RedisPropertiesUtils.getProperty("WITHDRAW_YFB_MERCHANT_CODE");
	private static final String PUBLIC_KEY_INDEX = "0001";
    private static final String SIGN_ALGORITHM = "RSA";
    private static final String INPUT_CHARSET = "UTF-8";
    private static final String CURRENCY = "CNY";
			
	@Autowired
	private WithdrawOrderMapper withdrawOrderMapper;
	
	@Autowired
	@Qualifier("cardKeysService")
	private CardKeysService cardKeysService;

	@Autowired
	@Qualifier("cardKeysTransLogService")
	private CardKeysTransLogService cardKeysTransLogService;

	@Autowired
	@Qualifier("cardKeysOrderInfService")
	private CardKeysOrderInfService cardKeysOrderInfService;
	
	@Autowired
	@Qualifier("cardKeysProductService")
	private CardKeysProductService cardKeysProductService;

	@Autowired
	@Qualifier("userBankInfService")
	private UserBankInfService userBankInfService;
	
	@Autowired
	@Qualifier("jedisCluster")
	private JedisCluster jedisCluster;
	
	@Override
	public String getPrimaryKey() {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("id", "");
		withdrawOrderMapper.getPrimaryKey(paramMap);
		String id = (String) paramMap.get("id");
		return id;
	}
	
	@Override
	public int getCountByBatchNo(String batchNo) {
		return this.withdrawOrderMapper.getCountByBatchNo(batchNo);
	}

	
	@Override
	public int insertWithdrawOrder(WithdrawOrder withdrawOrder) {
		return this.withdrawOrderMapper.insertWithdrawOrder(withdrawOrder);
	}

	@Override
	public int updateWithdrawOrder(WithdrawOrder withdrawOrder) {
		return this.withdrawOrderMapper.updateWithdrawOrder(withdrawOrder);
	}

	@Override
	public String YFBBatchWithdraw(String batchNo, String jsonData) throws Exception {
		// 解析请求批次json数组
		JSONArray pBatchArray = JSONArray.parseArray(jsonData);
		if (pBatchArray == null || pBatchArray.size() < 1) {
			logger.error("## YFBBatchWithDraw pBatchArray{} is null or size less than 1", pBatchArray);
			return null;
		}
		
		String totalAmount = null;
		String batchOrderName = null;
		String userId = null;
		
		JSONArray batchArray = new JSONArray();
        for (int i = 0; i < pBatchArray.size(); i++) {// 遍历出款批次
        	JSONObject pBatchObject = (JSONObject) pBatchArray.get(i);// 获得请求单个批次jsonObject
        	if (pBatchObject == null || pBatchObject.size() < 1) {
    			logger.error("## YFBBatchWithDraw pBatchObject{} is null or size less than 1", pBatchObject);
    			return null;
    		}
        	
        	JSONArray pDetailArray = pBatchObject.getJSONArray("detailData");// 获得请求单个批次的所有明细jsonArray
        	if (pDetailArray == null || pDetailArray.size() < 1) {
    			logger.error("## YFBBatchWithDraw pDetailArray{} is null or size less than 1", pDetailArray);
    			return null;
    		}
        	
        	// TODO 查询易付宝出款流水是否已受理出款
        	/*String queryResult = YFBBatchWithdrawQuery(batchNo, MERCHANT_NO);
        	if (queryResult != null) {
        		WithdrawQuery withdrawQuery = JSON.parseObject(queryResult, WithdrawQuery.class);
        		if (!"2080".equals(withdrawQuery.getResponseCode())) {
        			logger.error("## YFBBatchWithDraw--->调用代付查询接口返回信息[{}]", queryResult);
        			return null;
        		}
        	}*/
        	
        	// 组装代付请求参数  生成body批次数据 bulidBatchContentJosn
        	JSONObject batchObject = new JSONObject();
            batchObject.put("batchNo", batchNo);
            batchObject.put("merchantNo", MERCHANT_NO);
            batchObject.put("productCode", RedisPropertiesUtils.getProperty("WITHDRAW_YFB_PRODUCT_CODE"));
            if (pBatchObject.containsKey("totalAmount")) {
            	totalAmount = (String) pBatchObject.get("totalAmount");
            	batchObject.put("totalAmount", pBatchObject.get("totalAmount"));
            }
            batchObject.put("currency", CURRENCY);
            if (pBatchObject.containsKey("payDate")) {
            	batchObject.put("payDate", pBatchObject.get("payDate"));
            }
            if (pBatchObject.containsKey("tunnelData")) {
            	batchObject.put("tunnelData", pBatchObject.get("tunnelData"));
            }
            batchObject.put("notifyUrl", RedisDictProperties.getInstance().getdictValueByCode(BaseConstants.WITHDRAW_NOTIFY_URL));
            if (pBatchObject.containsKey("batchOrderName")) {
            	batchOrderName = (String) pBatchObject.get("batchOrderName");
            	batchObject.put("batchOrderName", pBatchObject.get("batchOrderName"));
            }
            batchObject.put("goodsType", RedisPropertiesUtils.getProperty("WITHDRAW_YFB_GOODS_TYPE"));
        	
        	JSONArray detailArray = new JSONArray();
        	WithdrawOrder order = new WithdrawOrder();
        	
        	// 组装代付请求参数  生成body明细数据 bulidDetailContentJosn
        	for (int j = 0; j < pDetailArray.size(); j++) {// 遍历出款明细
        		JSONObject pDetailObject = (JSONObject) pDetailArray.get(j);// 获得请求单条明细jsonObject
        		JSONObject detailObject = new JSONObject();
        		userId = (String) pDetailObject.get("orderName");
        		String serialNo = (String) pDetailObject.get("serialNo");
        		
        		// 查询出款订单表是否有当前卡密交易订单记录
        		if (withdrawOrderMapper.getCountBySerialNo(serialNo) > 0) {
        			logger.error("## 用户[{}]新增出款订单信息重复 批次号[{}] 出款订单号[{}]", userId, batchNo, serialNo);
        			return null;
        		}
        		
                detailObject.put("receiverCurrency", CURRENCY);
                if (pDetailObject.containsKey("serialNo"))
                	detailObject.put("serialNo", pDetailObject.get("serialNo"));
                if (pDetailObject.containsKey("receiverType"))
                	detailObject.put("receiverType", pDetailObject.get("receiverType"));
                if (pDetailObject.containsKey("receiverName"))
                	detailObject.put("receiverName", pDetailObject.get("receiverName"));
                if (pDetailObject.containsKey("amount"))
                	detailObject.put("amount", pDetailObject.get("amount"));
                if (pDetailObject.containsKey("orderName"))
                	detailObject.put("orderName", pDetailObject.get("orderName"));
                if (pDetailObject.containsKey("bankName"))
                	detailObject.put("bankName", pDetailObject.get("bankName"));
                if (pDetailObject.containsKey("receiverCardNo"))
                	detailObject.put("receiverCardNo", pDetailObject.get("receiverCardNo"));
                if (pDetailObject.containsKey("bankCode"))
                	detailObject.put("bankCode", pDetailObject.get("bankCode"));
                if (pDetailObject.containsKey("bankProvince"))
                	detailObject.put("bankProvince", pDetailObject.get("bankProvince"));
                if (pDetailObject.containsKey("bankCity"))
                	detailObject.put("bankCity", pDetailObject.get("bankCity"));
                if (pDetailObject.containsKey("payeeBankLinesNo"))
                	detailObject.put("payeeBankLinesNo", pDetailObject.get("payeeBankLinesNo"));
                
                detailArray.add(detailObject);
                
                //插入出款订单表信息
        		order.setOrderId(getPrimaryKey());
        		order.setBatchNo(batchNo);
        		order.setUserId(userId);
        		order.setTotalFee("0");
        		order.setTotalAmount(totalAmount);
        		order.setTotalNum(String.valueOf(detailArray.size()));
        		order.setWithdrawDate(DateUtil.getCurrentDateStr());
        		order.setOrderName(batchOrderName);
        		order.setSuccessAmount("0");
        		order.setSuccessNum("0");
        		order.setSuccessFee("0");
        		order.setFailAmount("0");
        		order.setFailNum("0");
        		order.setPaidId(serialNo);// 代付流水为出款订单号
        		order.setPaidIns("苏宁易付宝");
        		order.setStat(Constants.withdrawStat.S66.getCode());
        		if (insertWithdrawOrder(order) < 1) {
        			logger.error("## 用户[{}]新增出款订单信息失败 批次号[{}] 出款订单号[{}]", userId, batchNo, serialNo);
        			return null;
        		}
			}
        	
            batchObject.put("totalNum", detailArray.size());
            batchObject.put("detailData", detailArray);
            batchArray.add(batchObject);
        }
		
		// 代付请求地址
		String baseUrl = RedisDictProperties.getInstance().getdictValueByCode(BaseConstants.WITHDRAW_YFB_URL);
		
		// 计算签名值
		String sign = BizUtil.calculateSign(batchArray.toJSONString());
		String responseStr = null;
		
		try {
			logger.info("发送易付宝代付参数{}", batchArray.toJSONString());
			// 发送HTTP POST请求至苏宁易付宝代付返回结果
			responseStr = HttpClientUtil.post(PUBLIC_KEY_INDEX, SIGN_ALGORITHM, MERCHANT_NO, INPUT_CHARSET, baseUrl, sign, batchArray.toJSONString());
			logger.info("易付宝代付返回{}", responseStr);
		} catch (NoSuchAlgorithmException e) {
			logger.error("## YFBBatchWithDraw NoSuchAlgorithmException", e);
		} catch (KeyManagementException e) {
			logger.error("## YFBBatchWithDraw KeyManagementException", e);
		} catch (UnsupportedEncodingException e) {
			logger.error("## YFBBatchWithDraw UnsupportedEncodingException", e);
		}
		
		return responseStr;
	}

	@Override
	public WithdrawOrder getWithdrawOrderById(String batchNo) {
		return this.withdrawOrderMapper.getWithdrawOrderById(batchNo);
	}

	@Override
	public WelfaremartResellResp welfaremartResellCommit(WelfaremartResellReq req)  throws Exception{
		WelfaremartResellResp resp = new WelfaremartResellResp();
		resp.setCode("1");//不需要弹框提示
		resp.setStatus(Boolean.FALSE);
		
		String resellNum = req.getResellNum();
		String productCode = req.getProductCode();
		String bankNo = req.getBankNo();
		String userId = req.getUserId();
		String sign = req.getSign();
		
		if (StringUtil.isNullOrEmpty(userId)) {
			logger.error("## 卡券集市--->转让接口，接收userId为空");
			resp.setMsg(MessageUtil.ERROR_MSSAGE);
			return resp;
		}
		if (StringUtil.isNullOrEmpty(productCode)) {
			logger.error("## 卡券集市--->转让接口，接收用户[{}]productCode为空", userId);
			resp.setMsg(MessageUtil.ERROR_MSSAGE);
			return resp;
		}
		if (StringUtil.isNullOrEmpty(bankNo)) {
			logger.error("## 卡券集市--->转让接口，接收用户[{}]银行卡号为空", userId);
			resp.setMsg(MessageUtil.RESELL_BANKNO_IS_NULL);
			return resp;
		}
		if (StringUtil.isNullOrEmpty(resellNum) || Integer.parseInt(resellNum) == 0) {
			logger.error("## 卡券集市--->转让接口，接收用户[{}]resellNum为空", userId);
			resp.setMsg(MessageUtil.RESELL_NUM_IS_NULL);
			return resp;
		}
		if (StringUtil.isNullOrEmpty(sign)) {
			logger.error("## 卡券集市--->转让接口，接收用户ID[{}]sign为空", userId);
			resp.setMsg(MessageUtil.ERROR_MSSAGE);
			return resp;
		}
		try{
			String WELFAREMART_RESELL_KEY = jedisCluster.hget(RedisConstants.REDIS_HASH_TABLE_TB_BASE_DICT_KV, BaseConstants.WELFAREMART_RESELL_KEY);
			//验签
			String genSign = SignUtil.genSign(req, WELFAREMART_RESELL_KEY);
			if (!genSign.equals(req.getSign())) {
				logger.error("## 卡券集市--->转让接口，用户ID[{}]验签失败，当前生成签名[{}]", userId, genSign);
				resp.setMsg(MessageUtil.ERROR_MSSAGE);
				return resp;
			}
			
			//查询银行卡号是否属于该用户所持有的
			UserBankInf userBank = new UserBankInf();
			userBank.setUserId(userId);
			userBank.setBankNo(bankNo);
			UserBankInf bankInf = userBankInfService.getUserBankInf(userBank);
			if (bankInf == null) {
				logger.error("## 卡券集市--->转让接口，用户ID[{}]转让的银行卡号[{}]在UserBankInf信息中不存在", userId, bankNo);
				resp.setMsg(MessageUtil.RESELL_BANKNO_NOT_EXIST);
				return resp;
			}
			
			//可转让次数
			int appearNum = cardKeysOrderInfService.getMonthResellNum(userId);
			if (appearNum >= 3) {
				logger.error("## 卡券集市--->转让接口，用户[{}]本月卡券转让次数已用完", userId);
				resp.setMsg(MessageUtil.RESELL_NUM_USE_UP);
				return resp;
			}
	
			//可转让的张数
			CardKeys ck = new CardKeys();
			ck.setAccountId(userId);
			ck.setProductCode(productCode);
			int loseNum = cardKeysService.getLoseNumByAccountId(ck);
			if (Integer.parseInt(resellNum) > loseNum) {
				logger.error("## 卡券集市--->转让接口，用户[{}]卡券转让张数[{}]大于持有卡券张数[{}]", userId, resellNum, loseNum);
				resp.setMsg(MessageUtil.RESELL_NUM_MORE_THAN_OWN);
				return resp;
			}
	
			//校验卡密产品号是否存在
			CardKeysProduct product = new CardKeysProduct();
			product.setProductCode(productCode);
			CardKeysProduct ckp = cardKeysProductService.getCardKeysProductByCode(product);
			if (ckp == null) {
				logger.error("## 卡券集市--->转让接口，查询用户[{}]的产品[{}]信息为空", userId, productCode);
				resp.setMsg(MessageUtil.ERROR_MSSAGE);
				return resp;
			}
			
			int totalFee = new Double(Double.parseDouble(resellNum) * Double.parseDouble(ckp.getAmount()) * Double.parseDouble(BaseConstants.RESELL_FEE.toString()) / 100).intValue();
			int totalResellAmount = new Double(Double.parseDouble(resellNum) * Double.parseDouble(ckp.getAmount())).intValue();
			int gainAmount = totalResellAmount - totalFee;
			
			//校验转让额度是否超过五万
			if (totalResellAmount > 5000000) {
				logger.error("## 卡券集市--->转让接口，用户[{}]转让额度[{}]超过五万", userId, totalResellAmount);
				resp.setCode("2");//需要弹框提示
				resp.setMsg(MessageUtil.EROOR_RESELL_AMOUNT);
				return resp;
			}
	
			//新增转让卡密交易订单信息
			CardKeysOrderInf cko = new CardKeysOrderInf();
			cko.setOrderId(RandomUtils.getOrderIdByUUId("Z"));
			cko.setUserId(userId);
			cko.setProductCode(productCode);
			cko.setBankNo(bankNo);
			cko.setAmount(String.valueOf(gainAmount));
			cko.setPaidAmount(String.valueOf(gainAmount));
			cko.setType(orderType.O3.getCode());
			cko.setStat(orderStat.OS34.getCode());
			cko.setNum(resellNum);
			if (cardKeysOrderInfService.insertCardKeysOrderInf(cko) < 1) {
				logger.error("## 卡券集市--->转让接口，新增用户[{}]卡密订单信息{}失败", userId, JSONArray.toJSONString(cko));
				resp.setMsg(MessageUtil.ERROR_MSSAGE);
				return resp;
			}
			CardKeys cardKeys = new CardKeys();
			cardKeys.setAccountId(userId);
			cardKeys.setProductCode(productCode);
			cardKeys.setValidNum(resellNum);
			List<CardKeys> cardKeysList = cardKeysService.getCardKeysList(cardKeys);
			
			List<CardKeysTransLog> cktList = new ArrayList<CardKeysTransLog>();
			for (CardKeys card : cardKeysList) {
				// 检查订单流水对应的卡密是否已经有代付订单
				int orderNum = cardKeysOrderInfService.getCardKeysOrderByCardKeys(card.getCardKey());
				if (orderNum > 0) {
					logger.error("## 卡券集市--->转让接口，用户[{}]转让的卡密[{}]已存在代付订单", userId, card.getCardKey());
					resp.setMsg(MessageUtil.ERROR_MSSAGE);
					break;
				}
				/** 设置卡密交易流水信息 */
				CardKeysTransLog ckt = new CardKeysTransLog();
				String id = cardKeysTransLogService.getPrimaryKey();
				ckt.setTxnPrimaryKey(id);
				ckt.setCardKey(card.getCardKey());
				ckt.setOrderId(cko.getOrderId());
				ckt.setTransId(TransType.W30.getCode());
				ckt.setProductCode(cko.getProductCode());
				ckt.setTransAmt(ckp.getAmount());
				ckt.setOrgTransAmt(ckp.getAmount());
				ckt.setTransFee(String.valueOf(BaseConstants.RESELL_FEE.toString()));
				ckt.setTransFeeType(TransFeeType.findByCode(ckp.getProductType()).getCode());
				cktList.add(ckt);
			}
			
			//新增卡密交易流水信息
			if (cktList.size() < 1) {
				logger.error("## 卡券集市--->转让接口，用户[{}]没有需更新的卡密交易流水信息", userId);
				resp.setMsg(MessageUtil.ERROR_MSSAGE);
				return resp;
			}
			if (cardKeysTransLogService.insertBatchCardKeysTransLogList(cktList) < 1) {
				logger.error("## 卡券集市--->转让接口，新增用户[{}]卡密交易流水信息失败，订单号[{}]", userId, cko.getOrderId());
				resp.setMsg(MessageUtil.ERROR_MSSAGE);
				return resp;
			} else {
				CardKeysOrderInf order = new CardKeysOrderInf();
				order.setOrderId(cko.getOrderId());
				order.setStat(orderStat.OS30.getCode());
				int i = cardKeysOrderInfService.updateCardKeysOrderInfAndCardKeys(order, cardKeysList);
				if (i < 1) {
					logger.error("## 卡券集市--->转让接口,更新用户[{}]卡密交易订单[{}]及核销卡密信息[{}]失败", userId, JSONArray.toJSONString(order), JSONArray.toJSONString(cardKeysList));
					resp.setMsg(MessageUtil.ERROR_MSSAGE);
					return resp;
				}
			}
			resp.setOrderId(cko.getOrderId());
		}catch(Exception e){
			logger.error("## 卡券集市--->转让接口,用户[{}]转让异常{} ", userId, e);
			resp.setMsg(MessageUtil.ERROR_MSSAGE);
			return resp;
		}		
		resp.setStatus(Boolean.TRUE);
		logger.info("卡券集市--->转让接口，用户[{}]转让执行完毕", userId);
		return resp;
	}

	@Override
	public boolean YFBWithdrawCheckSign(String paramData) {
		JSONArray pBatchArray = JSONArray.parseArray(paramData);
		if (pBatchArray == null || pBatchArray.size() < 1) {
			logger.error("## YFBWithdrawCheckSign pBatchArray{} is null or size less than 1", pBatchArray);
			return false;
		}
		JSONObject pBatchObject = (JSONObject) pBatchArray.get(0);
		if (pBatchObject == null || pBatchObject.size() < 1) {
			logger.error("## YFBWithdrawCheckSign pBatchObject{} is null or size less than 1", pBatchObject);
			return false;
		}
		JSONArray pDetailArray = pBatchObject.getJSONArray("detailData");
		if (pDetailArray == null || pDetailArray.size() < 1) {
			logger.error("## YFBWithdrawCheckSign pDetailArray{} is null or size less than 1", pDetailArray);
			return false;
		}
		WithdrawOrderVO withOrder = new WithdrawOrderVO();
		if (pDetailArray.size() >= 1) {
			JSONObject pDetailObject = (JSONObject) pDetailArray.get(0);
			withOrder.setSerialNo((String) pDetailObject.get("serialNo"));
			withOrder.setReceiverCardNo((String) pDetailObject.get("receiverCardNo"));
			withOrder.setReceiverName((String) pDetailObject.get("receiverName"));
			withOrder.setReceiverType((String) pDetailObject.get("receiverType"));
			withOrder.setBankCity((String) pDetailObject.get("bankCity"));
			withOrder.setBankCode((String) pDetailObject.get("bankCode"));
			withOrder.setBankName((String) pDetailObject.get("bankName"));
			withOrder.setBankProvince((String) pDetailObject.get("bankProvince"));
			withOrder.setAmount((String) pDetailObject.get("amount"));
			withOrder.setOrderName((String) pDetailObject.get("orderName"));
		}
		withOrder.setTotalNum((String) pBatchObject.get("totalNum"));
		withOrder.setTotalAmount((String) pBatchObject.get("totalAmount"));
		withOrder.setPayDate((String) pBatchObject.get("payDate"));
		withOrder.setBatchOrderName((String) pBatchObject.get("batchOrderName"));
		withOrder.setSign((String) pBatchObject.get("sign"));
		
		String WELFAREMART_WITHDRAW_KEY = jedisCluster.hget(RedisConstants.REDIS_HASH_TABLE_TB_BASE_DICT_KV, BaseConstants.WELFAREMART_WITHDRAW_KEY);
		String sign = SignUtil.genSign(withOrder, WELFAREMART_WITHDRAW_KEY);
		
		if (!sign.equals(withOrder.getSign())) {
			logger.error("## 易付宝代付请求接口--->YFBWithdrawCheckSign，验签失败，待加密参数[{}]，key[{}]，加密后签名[{}]", JSONArray.toJSONString(withOrder), WELFAREMART_WITHDRAW_KEY, sign);
			return false;
		}
		return true;
	}

	@Override
	public String YFBBatchWithdrawQuery(String batchNo, String payMerchantNo) {
		if (StringUtil.isNullOrEmpty(batchNo) || StringUtil.isNullOrEmpty(payMerchantNo)) {
			logger.error("## YFBBatchWithdrawQuery batchNo{} is null or payMerchantNo{} is null", batchNo, payMerchantNo);
			return null;
		}
		JSONObject content = new JSONObject();
		content.put("batchNo", batchNo);
		content.put("payMerchantNo", payMerchantNo);
		
		// 代付查询请求地址
		String queryUrl = RedisDictProperties.getInstance().getdictValueByCode(BaseConstants.WITHDRAW_QUERY_YFB_URL);
		
		//计算签名
		String sign = BizUtil.calculateSign(content.toString());
		String responseStr = null;
		
		try {
			logger.info("发送易付宝代付查询接口参数：{}", content.toString());
			// 发送HTTP POST请求至苏宁易付宝代付返回结果
			responseStr = HttpClientUtil.post(PUBLIC_KEY_INDEX, SIGN_ALGORITHM, MERCHANT_NO, INPUT_CHARSET, queryUrl, sign, content.toString());
		} catch (NoSuchAlgorithmException e) {
			logger.error("## YFBBatchWithdrawQuery NoSuchAlgorithmException", e);
		} catch (KeyManagementException e) {
			logger.error("## YFBBatchWithdrawQuery KeyManagementException", e);
		} catch (UnsupportedEncodingException e) {
			logger.error("## YFBBatchWithdrawQuery UnsupportedEncodingException", e);
		}
		return responseStr;
		/*if (StringUtil.isNullOrEmpty(responseStr)) {
			logger.error("## 易付宝代付查询接口返回参数为空");
			return null;
		}
		WithdrawQuery withdrawQuery = JSON.parseObject(responseStr, WithdrawQuery.class);
			
		return JSONArray.toJSONString(withdrawQuery);*/
	}

	@Override
	public WithdrawOrder getWithdrawOrderByPaidId(String paidId) {
		return this.withdrawOrderMapper.getWithdrawOrderByPaidId(paidId);
	}

}
