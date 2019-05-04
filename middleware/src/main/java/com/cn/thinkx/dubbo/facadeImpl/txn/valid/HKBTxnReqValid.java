package com.cn.thinkx.dubbo.facadeImpl.txn.valid;

import com.cn.thinkx.dubbo.entity.TxnResp;
import com.cn.thinkx.facade.bean.CardBalQueryRequest;
import com.cn.thinkx.facade.bean.CardTransDetailQueryRequest;
import com.cn.thinkx.facade.bean.CusAccListQueryRequest;
import com.cn.thinkx.facade.bean.CusAccOpeningRequest;
import com.cn.thinkx.facade.bean.CusAccQueryRequest;
import com.cn.thinkx.facade.bean.CusCardOpeningRequest;
import com.cn.thinkx.facade.bean.CustomerBuyCardStocksRequest;
import com.cn.thinkx.facade.bean.MchntInfQueryRequest;
import com.cn.thinkx.facade.bean.RechargeTransRequest;
import com.cn.thinkx.facade.bean.ShopInfQueryRequest;
import com.cn.thinkx.facade.bean.base.BaseTxnReq;
import com.cn.thinkx.itf.base.SignUtil;
import com.cn.thinkx.pms.base.utils.BaseConstants.ChannelCode;
import com.cn.thinkx.pms.base.utils.StringUtil;

public class HKBTxnReqValid {

	/**
	 * 客户开户接口参数校验
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public static boolean cusAccOpeningValid(CusAccOpeningRequest req, TxnResp resp) {
		if (StringUtil.isNullOrEmpty(req.getChannel())) {
			resp.setInfo("渠道号为空");
			return true;
		}
		if (StringUtil.isNullOrEmpty(req.getUserId())) {
			resp.setInfo("用户ID为空");
			return true;
		}
		if (StringUtil.isNullOrEmpty(req.getMobile())) {
			resp.setInfo("手机号为空");
			return true;
		}
		if (StringUtil.isNullOrEmpty(req.getTimestamp())) {
			resp.setInfo("时间戳为空");
			return true;
		}
		if (StringUtil.isNullOrEmpty(req.getSign())) {
			resp.setInfo("签名为空");
			return true;
		}
		if (!SignUtil.genSign(req).equals(req.getSign())) {
			resp.setInfo("签名错误");
			return true;
		}
		if (SignUtil.isSignExpired(System.currentTimeMillis(), req.getTimestamp())) {
			resp.setInfo("签名过期");
			return true;
		}
		return false;
	}
	
	/**
	 * 客户开卡接口参数校验（非通卡）
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public static boolean cusCardOpeningValid(CusCardOpeningRequest req, TxnResp resp) {
		if (StringUtil.isNullOrEmpty(req.getSwtTxnDate())) {
			resp.setInfo("交易日期为空");
			return true;
		}
		if (StringUtil.isNullOrEmpty(req.getSwtTxnTime())) {
			resp.setInfo("交易时间为空");
			return true;
		}
		if (StringUtil.isNullOrEmpty(req.getChannel())) {
			resp.setInfo("渠道号为空");
			return true;
		}
		if (ChannelCode.findByCode(req.getChannel()) == null) {
			resp.setInfo("无效渠道号");
			return true;
		}
		if (StringUtil.isNullOrEmpty(req.getInnerMerchantNo())) {
			resp.setInfo("商户号为空");
			return true;
		}
		if (StringUtil.isNullOrEmpty(req.getUserId())) {
			resp.setInfo("用户ID为空");
			return true;
		}
		if (StringUtil.isNullOrEmpty(req.getTimestamp())) {
			resp.setInfo("时间戳为空");
			return true;
		}
		if (StringUtil.isNullOrEmpty(req.getSign())) {
			resp.setInfo("签名为空");
			return true;
		}
		if (!SignUtil.genSign(req).equals(req.getSign())) {
			resp.setInfo("签名错误");
			return true;
		}
		if (SignUtil.isSignExpired(System.currentTimeMillis(), req.getTimestamp())) {
			resp.setInfo("签名过期");
			return true;
		}
		return false;
	}
	
	/**
	 * 客户开卡接口参数校验（通卡）
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public static boolean cusHKBCardOpeningValid(CusCardOpeningRequest req, TxnResp resp) {
		if (StringUtil.isNullOrEmpty(req.getSwtTxnDate())) {
			resp.setInfo("交易日期为空");
			return true;
		}
		if (StringUtil.isNullOrEmpty(req.getSwtTxnTime())) {
			resp.setInfo("交易时间为空");
			return true;
		}
		if (StringUtil.isNullOrEmpty(req.getChannel())) {
			resp.setInfo("渠道号为空");
			return true;
		}
		if (ChannelCode.findByCode(req.getChannel()) == null) {
			resp.setInfo("无效渠道号");
			return true;
		}
		if (StringUtil.isNullOrEmpty(req.getUserId())) {
			resp.setInfo("用户ID为空");
			return true;
		}
		if (StringUtil.isNullOrEmpty(req.getTimestamp())) {
			resp.setInfo("时间戳为空");
			return true;
		}
		if (StringUtil.isNullOrEmpty(req.getSign())) {
			resp.setInfo("签名为空");
			return true;
		}
		if (!SignUtil.genSign(req).equals(req.getSign())) {
			resp.setInfo("签名错误");
			return true;
		}
		if (SignUtil.isSignExpired(System.currentTimeMillis(), req.getTimestamp())) {
			resp.setInfo("签名过期");
			return true;
		}
		return false;
	}
	
	/**
	 * 客户账户查询接口参数校验
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public static boolean cusAccQueryValid(CusAccQueryRequest req, TxnResp resp) {
		if (StringUtil.isNullOrEmpty(req.getChannel())) {
			resp.setInfo("渠道号为空");
			return true;
		}
		if (ChannelCode.findByCode(req.getChannel()) == null) {
			resp.setInfo("无效渠道号");
			return true;
		}
		if (StringUtil.isNullOrEmpty(req.getInnerMerchantNo())) {
			resp.setInfo("商户号为空");
			return true;
		}
		if (StringUtil.isNullOrEmpty(req.getUserId())) {
			resp.setInfo("用户ID为空");
			return true;
		}
		if (StringUtil.isNullOrEmpty(req.getTimestamp())) {
			resp.setInfo("时间戳为空");
			return true;
		}
		if (StringUtil.isNullOrEmpty(req.getSign())) {
			resp.setInfo("签名为空");
			return true;
		}
		if (!SignUtil.genSign(req).equals(req.getSign())) {
			resp.setInfo("签名错误");
			return true;
		}
		if (SignUtil.isSignExpired(System.currentTimeMillis(), req.getTimestamp())) {
			resp.setInfo("签名过期");
			return true;
		}
		return false;
	}
	
	/**
	 * 充值接口参数校验
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public static boolean rechargeTransValid(RechargeTransRequest req, TxnResp resp) {
		if (StringUtil.isNullOrEmpty(req.getSwtTxnDate())) {
			resp.setInfo("交易日期为空");
			return true;
		}
		if (StringUtil.isNullOrEmpty(req.getSwtTxnTime())) {
			resp.setInfo("交易时间为空");
			return true;
		}
		if (StringUtil.isNullOrEmpty(req.getChannel())) {
			resp.setInfo("渠道号为空");
			return true;
		}
		if (ChannelCode.findByCode(req.getChannel()) == null) {
			resp.setInfo("无效渠道号");
			return true;
		}
		if (StringUtil.isNullOrEmpty(req.getSwtFlowNo())) {
			resp.setInfo("流水号为空");
			return true;
		}
		if (StringUtil.isNullOrEmpty(req.getInnerMerchantNo())) {
			resp.setInfo("商户号为空");
			return true;
		}
		if (StringUtil.isNullOrEmpty(req.getCommodityCode())) {
			resp.setInfo("商品号为空");
			return true;
		}
		if (StringUtil.isNullOrEmpty(req.getCommodityNum())) {
			resp.setInfo("商品数为空");
			return true;
		}
		if (ChannelCode.CHANNEL4.equals(req.getChannel())) {// 购卡充值渠道
			if (StringUtil.isNullOrEmpty(req.getCommodityCode())) {
				resp.setInfo("商品号为空");
				return true;
			}
			if (StringUtil.isNullOrEmpty(req.getCommodityNum())) {
				resp.setInfo("商品数量为空");
				return true;
			}
			if (Integer.parseInt(req.getCommodityNum()) <= 0) {
				resp.setInfo("商品数量必须大于零");
				return true;
			}
		}
		if (StringUtil.isNullOrEmpty(req.getCardNo())) {
			resp.setInfo("卡号为空");
			return true;
		}
		if (StringUtil.isNullOrEmpty(req.getTxnAmount())) {
			resp.setInfo("交易金额为空");
			return true;
		}
		if (StringUtil.isNullOrEmpty(req.getOriTxnAmount())) {
			resp.setInfo("原交易金额为空");
			return true;
		}
		if (StringUtil.isNullOrEmpty(req.getTimestamp())) {
			resp.setInfo("时间戳为空");
			return true;
		}
		if (StringUtil.isNullOrEmpty(req.getSign())) {
			resp.setInfo("签名为空");
			return true;
		}
		if (!SignUtil.genSign(req).equals(req.getSign())) {
			resp.setInfo("签名错误");
			return true;
		}
		if (SignUtil.isSignExpired(System.currentTimeMillis(), req.getTimestamp())) {
			resp.setInfo("签名过期");
			return true;
		}
		return false;
	}
	
	/**
	 * 会员卡余额查询接口参数校验
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public static boolean cardBalQueryValid(CardBalQueryRequest req, TxnResp resp) {
		if (StringUtil.isNullOrEmpty(req.getChannel())) {
			resp.setInfo("渠道号为空");
			return true;
		}
		if (StringUtil.isNullOrEmpty(req.getUserId())) {
			resp.setInfo("用户ID为空");
			return true;
		}
		if (StringUtil.isNullOrEmpty(req.getInnerMerchantNo())) {
			resp.setInfo("商户号为空");
			return true;
		}
		if (StringUtil.isNullOrEmpty(req.getTimestamp())) {
			resp.setInfo("时间戳为空");
			return true;
		}
		if (StringUtil.isNullOrEmpty(req.getSign())) {
			resp.setInfo("签名为空");
			return true;
		}
		if (!SignUtil.genSign(req).equals(req.getSign())) {
			resp.setInfo("签名错误");
			return true;
		}
		if (SignUtil.isSignExpired(System.currentTimeMillis(), req.getTimestamp())) {
			resp.setInfo("签名过期");
			return true;
		}
		return false;
	}
	
	/**
	 * 会员卡消费接口参数校验
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public static boolean hkbConsumeTransValid(BaseTxnReq req, TxnResp resp) {
		if (StringUtil.isNullOrEmpty(req.getSwtTxnDate())) {
			resp.setInfo("交易日期为空");
			return true;
		}
		if (StringUtil.isNullOrEmpty(req.getSwtTxnTime())) {
			resp.setInfo("交易时间为空");
			return true;
		}
		if (StringUtil.isNullOrEmpty(req.getChannel())) {
			resp.setInfo("渠道号为空");
			return true;
		}
		if (ChannelCode.findByCode(req.getChannel()) == null) {
			resp.setInfo("无效渠道号");
			return true;
		}
		if (StringUtil.isNullOrEmpty(req.getSwtFlowNo())) {
			resp.setInfo("流水号为空");
			return true;
		}
		if (StringUtil.isNullOrEmpty(req.getInnerMerchantNo())) {
			resp.setInfo("商户号为空");
			return true;
		}
		if (StringUtil.isNullOrEmpty(req.getInnerShopNo())) {
			resp.setInfo("门店号为空");
			return true;
		}
		if (StringUtil.isNullOrEmpty(req.getCardNo())) {
			resp.setInfo("卡号为空");
			return true;
		}
		if (StringUtil.isNullOrEmpty(req.getTxnAmount())) {
			resp.setInfo("交易金额为空");
			return true;
		}
		if (StringUtil.isNullOrEmpty(req.getOriTxnAmount())) {
			resp.setInfo("原交易金额为空");
			return true;
		}
		if (StringUtil.isNullOrEmpty(req.getTimestamp())) {
			resp.setInfo("时间戳为空");
			return true;
		}
		if (StringUtil.isNullOrEmpty(req.getSign())) {
			resp.setInfo("签名为空");
			return true;
		}
		if (!SignUtil.genSign(req).equals(req.getSign())) {
			resp.setInfo("签名错误");
			return true;
		}
		if (SignUtil.isSignExpired(System.currentTimeMillis(), req.getTimestamp())) {
			resp.setInfo("签名过期");
			return true;
		}
		return false;
	}
	
	/**
	 * 快捷支付接口参数校验
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public static boolean quickPayTransValid(BaseTxnReq req, TxnResp resp) {
		if (StringUtil.isNullOrEmpty(req.getSwtTxnDate())) {
			resp.setInfo("交易日期为空");
			return true;
		}
		if (StringUtil.isNullOrEmpty(req.getSwtTxnTime())) {
			resp.setInfo("交易时间为空");
			return true;
		}
		if (StringUtil.isNullOrEmpty(req.getChannel())) {
			resp.setInfo("渠道号为空");
			return true;
		}
		if (ChannelCode.findByCode(req.getChannel()) == null) {
			resp.setInfo("无效渠道号");
			return true;
		}
		if (StringUtil.isNullOrEmpty(req.getSwtFlowNo())) {
			resp.setInfo("流水号为空");
			return true;
		}
		if (StringUtil.isNullOrEmpty(req.getInnerMerchantNo())) {
			resp.setInfo("商户号为空");
			return true;
		}
		if (StringUtil.isNullOrEmpty(req.getInnerShopNo())) {
			resp.setInfo("门店号为空");
			return true;
		}
		if (StringUtil.isNullOrEmpty(req.getCardNo())) {
			resp.setInfo("卡号为空");
			return true;
		}
		if (StringUtil.isNullOrEmpty(req.getTxnAmount())) {
			resp.setInfo("交易金额为空");
			return true;
		}
		if (StringUtil.isNullOrEmpty(req.getOriTxnAmount())) {
			resp.setInfo("原交易金额为空");
			return true;
		}
		if (StringUtil.isNullOrEmpty(req.getTimestamp())) {
			resp.setInfo("时间戳为空");
			return true;
		}
		if (StringUtil.isNullOrEmpty(req.getSign())) {
			resp.setInfo("签名为空");
			return true;
		}
		if (!SignUtil.genSign(req).equals(req.getSign())) {
			resp.setInfo("签名错误");
			return true;
		}
		if (SignUtil.isSignExpired(System.currentTimeMillis(), req.getTimestamp())) {
			resp.setInfo("签名过期");
			return true;
		}
		return false;
	}
	
	/**
	 * 商户在售卡列表查询接口参数校验
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public static boolean mchtSelListQueryValid(BaseTxnReq req, TxnResp resp) {
		if (StringUtil.isNullOrEmpty(req.getInnerMerchantNo())) {
			resp.setInfo("商户号为空");
			return true;
		}
		if (StringUtil.isNullOrEmpty(req.getTimestamp())) {
			resp.setInfo("时间戳为空");
			return true;
		}
		if (StringUtil.isNullOrEmpty(req.getSign())) {
			resp.setInfo("签名为空");
			return true;
		}
		if (!SignUtil.genSign(req).equals(req.getSign())) {
			resp.setInfo("签名错误");
			return true;
		}
		if (SignUtil.isSignExpired(System.currentTimeMillis(), req.getTimestamp())) {
			resp.setInfo("签名过期");
			return true;
		}
		return false;
	}
	
	/**
	 * 商户在售卡列表查询接口参数校验（新版）
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public static boolean getMchtSelListQueryValid(BaseTxnReq req, TxnResp resp) {
		if (StringUtil.isNullOrEmpty(req.getChannel())) {
			resp.setInfo("渠道号为空");
			return true;
		}
		if (ChannelCode.findByCode(req.getChannel()) == null) {
			resp.setInfo("无效渠道号");
			return true;
		}
		if (StringUtil.isNullOrEmpty(req.getInnerMerchantNo())) {
			resp.setInfo("商户号为空");
			return true;
		}
		if (StringUtil.isNullOrEmpty(req.getTimestamp())) {
			resp.setInfo("时间戳为空");
			return true;
		}
		if (StringUtil.isNullOrEmpty(req.getSign())) {
			resp.setInfo("签名为空");
			return true;
		}
		if (!SignUtil.genSign(req).equals(req.getSign())) {
			resp.setInfo("签名错误");
			return true;
		}
		if (SignUtil.isSignExpired(System.currentTimeMillis(), req.getTimestamp())) {
			resp.setInfo("签名过期");
			return true;
		}
		return false;
	}
	
	/**
	 * 客户会员卡列表查询接口参数校验
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public static boolean cusAccListQueryValid(CusAccListQueryRequest req, TxnResp resp) {
		if (StringUtil.isNullOrEmpty(req.getChannel())) {
			resp.setInfo("渠道号为空");
			return true;
		}
		/*if (StringUtil.isNullOrEmpty(req.getRemarks())) {
			resp.setInfo("卡类型为空");
			return true;
		}*/
		if (ChannelCode.findByCode(req.getChannel()) == null) {
			resp.setInfo("无效渠道号");
			return true;
		}
		if (StringUtil.isNullOrEmpty(req.getUserId())) {
			resp.setInfo("用户ID为空");
			return true;
		}
		if (StringUtil.isNullOrEmpty(req.getTimestamp())) {
			resp.setInfo("时间戳为空");
			return true;
		}
		if (StringUtil.isNullOrEmpty(req.getSign())) {
			resp.setInfo("签名为空");
			return true;
		}
		if (!SignUtil.genSign(req).equals(req.getSign())) {
			resp.setInfo("签名错误");
			return true;
		}
		if (SignUtil.isSignExpired(System.currentTimeMillis(), req.getTimestamp())) {
			resp.setInfo("签名过期");
			return true;
		}
		return false;
	}
	
	/**
	 * 商户门店明细查询接口参数校验
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public static boolean shopInfoQueryValid(ShopInfQueryRequest req, TxnResp resp) {
		if (StringUtil.isNullOrEmpty(req.getInnerMerchantNo())) {
			resp.setInfo("商户号为空");
			return true;
		}
		if (StringUtil.isNullOrEmpty(req.getInnerShopNo())) {
			resp.setInfo("门店号为空");
			return true;
		}
		if (StringUtil.isNullOrEmpty(req.getTimestamp())) {
			resp.setInfo("时间戳为空");
			return true;
		}
		if (StringUtil.isNullOrEmpty(req.getSign())) {
			resp.setInfo("签名为空");
			return true;
		}
		if (!SignUtil.genSign(req).equals(req.getSign())) {
			resp.setInfo("签名错误");
			return true;
		}
		if (SignUtil.isSignExpired(System.currentTimeMillis(), req.getTimestamp())) {
			resp.setInfo("签名过期");
			return true;
		}
		return false;
	}
	
	
	/**
	 * 商户门店明细查询接口参数校验(新版)
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public static boolean getShopInfoQueryValid(ShopInfQueryRequest req, TxnResp resp){
		if (StringUtil.isNullOrEmpty(req.getChannel())) {
			resp.setInfo("渠道号为空");
			return true;
		}
		if (StringUtil.isNullOrEmpty(req.getInnerMerchantNo())) {
			resp.setInfo("商户号为空");
			return true;
		}
		if (StringUtil.isNullOrEmpty(req.getInnerShopNo())) {
			resp.setInfo("门店号为空");
			return true;
		}
		if (StringUtil.isNullOrEmpty(req.getTimestamp())) {
			resp.setInfo("时间戳为空");
			return true;
		}
		if (StringUtil.isNullOrEmpty(req.getSign())) {
			resp.setInfo("签名为空");
			return true;
		}
		if (!SignUtil.genSign(req).equals(req.getSign())) {
			resp.setInfo("签名错误");
			return true;
		}
		if (SignUtil.isSignExpired(System.currentTimeMillis(), req.getTimestamp())) {
			resp.setInfo("签名过期");
			return true;
		}
		return false;
	}
	
	
	/**
	 * 商户门店列表查询接口参数校验
	 * @param req
	 * @param resp
	 * @return
	 */
	public static boolean shopListQueryITF(ShopInfQueryRequest req, TxnResp resp) {
		if (StringUtil.isNullOrEmpty(req.getLongitude())) {
			resp.setInfo("经度为空");
			return true;
		}
		if (StringUtil.isNullOrEmpty(req.getLatitude())) {
			resp.setInfo("纬度为空");
			return true;
		}
		if (StringUtil.isNullOrEmpty(req.getDistance())) {
			resp.setInfo("距离范围为空");
			return true;
		}
		if (StringUtil.isNullOrEmpty(req.getTimestamp())) {
			resp.setInfo("时间戳为空");
			return true;
		}
		if (StringUtil.isNullOrEmpty(req.getSign())) {
			resp.setInfo("签名为空");
			return true;
		}
		if (!SignUtil.genSign(req).equals(req.getSign())) {
			resp.setInfo("签名错误");
			return true;
		}
		if (SignUtil.isSignExpired(System.currentTimeMillis(), req.getTimestamp())) {
			resp.setInfo("签名过期");
			return true;
		}
		return false;
	}
	
	/**
	 * 商户门店列表查询接口参数校验 (新版)
	 * @param req
	 * @param resp
	 * @return
	 */
	public static boolean getShopListQueryITF(ShopInfQueryRequest req, TxnResp resp) {
		if (StringUtil.isNullOrEmpty(req.getChannel())) {
			resp.setInfo("渠道号为空");
			return true;
		}
		if (ChannelCode.findByCode(req.getChannel()) == null) {
			resp.setInfo("无效渠道号");
			return true;
		}
		if (StringUtil.isNullOrEmpty(req.getLongitude())) {
			resp.setInfo("经度为空");
			return true;
		}
		if (StringUtil.isNullOrEmpty(req.getLatitude())) {
			resp.setInfo("纬度为空");
			return true;
		}
		if (StringUtil.isNullOrEmpty(req.getDistance())) {
			resp.setInfo("距离范围为空");
			return true;
		}
		if (StringUtil.isNullOrEmpty(req.getTimestamp())) {
			resp.setInfo("时间戳为空");
			return true;
		}
		if (StringUtil.isNullOrEmpty(req.getSign())) {
			resp.setInfo("签名为空");
			return true;
		}
		if (!SignUtil.genSign(req).equals(req.getSign())) {
			resp.setInfo("签名错误");
			return true;
		}
		if (SignUtil.isSignExpired(System.currentTimeMillis(), req.getTimestamp())) {
			resp.setInfo("签名过期");
			return true;
		}
		return false;
	}
	
	/**
	 * 商户信息查询接口参数校验
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public static boolean merchantInfoQueryValid(MchntInfQueryRequest req, TxnResp resp) {
		if (StringUtil.isNullOrEmpty(req.getInnerMerchantNo())) {
			resp.setInfo("商户号为空");
			return true;
		}
		if (StringUtil.isNullOrEmpty(req.getTimestamp())) {
			resp.setInfo("时间戳为空");
			return true;
		}
		if (StringUtil.isNullOrEmpty(req.getSign())) {
			resp.setInfo("签名为空");
			return true;
		}
		if (!SignUtil.genSign(req).equals(req.getSign())) {
			resp.setInfo("签名错误");
			return true;
		}
		if (SignUtil.isSignExpired(System.currentTimeMillis(), req.getTimestamp())) {
			resp.setInfo("签名过期");
			return true;
		}
		return false;
	}
	
	/**
	 * 商户信息查询接口参数校验 (新版)
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public static boolean getMerchantInfoQueryValid(BaseTxnReq req, TxnResp resp) {
		if (StringUtil.isNullOrEmpty(req.getChannel())) {
			resp.setInfo("渠道号为空");
			return true;
		}
		if (ChannelCode.findByCode(req.getChannel()) == null) {
			resp.setInfo("无效渠道号");
			return true;
		}
		if (StringUtil.isNullOrEmpty(req.getInnerMerchantNo())) {
			resp.setInfo("商户号为空");
			return true;
		}
		if (StringUtil.isNullOrEmpty(req.getTimestamp())) {
			resp.setInfo("时间戳为空");
			return true;
		}
		if (StringUtil.isNullOrEmpty(req.getSign())) {
			resp.setInfo("签名为空");
			return true;
		}
		if (!SignUtil.genSign(req).equals(req.getSign())) {
			resp.setInfo("签名错误");
			return true;
		}
		if (SignUtil.isSignExpired(System.currentTimeMillis(), req.getTimestamp())) {
			resp.setInfo("签名过期");
			return true;
		}
		return false;
	}
	
	/**
	 * 会员卡交易明细查询接口参数校验
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public static boolean cardTransDetailQueryValid(CardTransDetailQueryRequest req, TxnResp resp) {
		if (StringUtil.isNullOrEmpty(req.getUserId())) {
			resp.setInfo("用户ID为空");
			return true;
		}
		if (StringUtil.isNullOrEmpty(req.getChannel())) {
			resp.setInfo("渠道号为空");
			return true;
		}
		if (StringUtil.isNullOrEmpty(req.getInnerMerchantNo())) {
			resp.setInfo("商户号为空");
			return true;
		}
		if (StringUtil.isNullOrEmpty(req.getTimestamp())) {
			resp.setInfo("时间戳为空");
			return true;
		}
		if (StringUtil.isNullOrEmpty(req.getSign())) {
			resp.setInfo("签名为空");
			return true;
		}
		if (!SignUtil.genSign(req).equals(req.getSign())) {
			resp.setInfo("签名错误");
			return true;
		}
		if (SignUtil.isSignExpired(System.currentTimeMillis(), req.getTimestamp())) {
			resp.setInfo("签名过期");
			return true;
		}
		return false;
	}
	
	/**
	 * 异常处理查询接口参数校验
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public static boolean transExceptionQueryValid(BaseTxnReq req, TxnResp resp) {
		if (StringUtil.isNullOrEmpty(req.getChannel())) {
			resp.setInfo("渠道号为空");
			return true;
		}
		if (ChannelCode.findByCode(req.getChannel()) == null) {
			resp.setInfo("无效渠道号");
			return true;
		}
		if (StringUtil.isNullOrEmpty(req.getSwtFlowNo())) {
			resp.setInfo("流水号为空");
			return true;
		}
		if (StringUtil.isNullOrEmpty(req.getTimestamp())) {
			resp.setInfo("时间戳为空");
			return true;
		}
		if (StringUtil.isNullOrEmpty(req.getSign())) {
			resp.setInfo("签名为空");
			return true;
		}
		if (!SignUtil.genSign(req).equals(req.getSign())) {
			resp.setInfo("签名错误");
			return true;
		}
		if (SignUtil.isSignExpired(System.currentTimeMillis(), req.getTimestamp())) {
			resp.setInfo("签名过期");
			return true;
		}
		return false;
	}
	
	
	/**
	 * 客户可购卡数量查询接口参数校验
	 * 
	 * @param req
	 * @param resp
	 * @return
	 */
	public static boolean customerBuyCardStocksQueryValid(CustomerBuyCardStocksRequest req, TxnResp resp) {
		if (StringUtil.isNullOrEmpty(req.getChannel())) {
			resp.setInfo("渠道号为空");
			return true;
		}
		if (ChannelCode.findByCode(req.getChannel()) == null) {
			resp.setInfo("无效渠道号");
			return true;
		}
		if (StringUtil.isNullOrEmpty(req.getUserId())) {
			resp.setInfo("用户ID为空");
			return true;
		}
		if (StringUtil.isNullOrEmpty(req.getCommodityCode())) {
			resp.setInfo("商品号为空");
			return true;
		}
		if (StringUtil.isNullOrEmpty(req.getTimestamp())) {
			resp.setInfo("时间戳为空");
			return true;
		}
		if (StringUtil.isNullOrEmpty(req.getSign())) {
			resp.setInfo("签名为空");
			return true;
		}
		if (!SignUtil.genSign(req).equals(req.getSign())) {
			resp.setInfo("签名错误");
			return true;
		}
		if (SignUtil.isSignExpired(System.currentTimeMillis(), req.getTimestamp())) {
			resp.setInfo("签名过期");
			return true;
		}
		return false;
	}
}
