package com.cn.thinkx.facade.service;

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

/**
 * <h3>薪无忧交易接口类</h3> <br>
 * <ul>
 * <li>所有方法均返回json格式字符串，如操作成功返回：{"code":"00","info":"交易成功"}</li>
 * <li>json串的code为交易返回码，如交易成功为："00"</li>
 * <li>json串中info为交易系统返回码描述，如交易成功为："交易成功"</li>
 * <li>交易出现异常时返回：{"code":"96","info":"系统故障"}</li>
 * </ul>
 * 
 * @version 1.0
 * @since 2016/10/17
 * @author pucker
 *
 */
public interface HKBTxnFacade {

	/**
	 * 客户开户接口
	 * 
	 * @param req
	 * @return json格式字符串
	 */
	String customerAccountOpeningITF(CusAccOpeningRequest req) throws Exception;
	
	/**
	 * 客户开卡接口（非通卡）
	 * 
	 * @param req
	 * @return json格式字符串
	 */
	String customerCardOpeningITF(CusCardOpeningRequest req) throws Exception;
	
	/**
	 * 客户开卡接口（薪无忧通卡）
	 * 
	 * @param req
	 * @return json格式字符串
	 */
	String customerHKBCardOpeningITF(CusCardOpeningRequest req) throws Exception;
	
	/**
	 * 客户账户查询
	 * 
	 * @param req
	 * @return json格式字符串
	 */
	String customerAccountQueryITF(CusAccQueryRequest req) throws Exception;

	/**
	 * 充值交易接口
	 * 
	 * @param req
	 * @return json格式字符串
	 */
	String rechargeTransactionITF(RechargeTransRequest req) throws Exception;
	
	/**
	 * 会员卡余额查询
	 * 
	 * @param req
	 * @return json格式字符串
	 */
	String cardBalanceQueryITF(CardBalQueryRequest req) throws Exception;

	/**
	 * 会员卡消费交易接口
	 * 
	 * @param req
	 * @return json格式字符串
	 */
	String hkbConsumeTransactionITF(BaseTxnReq req) throws Exception;
	
	/**
	 * 快捷支付交易接口
	 * 
	 * @param req
	 * @return json格式字符串
	 */
	String quickPaymentTransactionITF(BaseTxnReq req) throws Exception;
	
	/**
	 * 商户在售卡列表查询
	 * 
	 * @param req
	 * @return json格式字符串
	 */
	String mchtSellingCardListQueryITF(BaseTxnReq req) throws Exception;
	
	/**
	 * 商户在售卡列表查询 (新版)
	 * 
	 * @param req
	 * @return json格式字符串
	 */
	String getMchtSellingCardListQueryITF(BaseTxnReq req) throws Exception;
	
	/**
	 * 客户会员卡列表查询
	 * 
	 * @param req
	 * @return json格式字符串
	 */
	String customerAccListQueryITF(CusAccListQueryRequest req) throws Exception;
	
	/**
	 * 商户门店明细查询
	 * 
	 * @param req
	 * @return json格式字符串
	 */
	String shopInfoQueryITF(ShopInfQueryRequest req) throws Exception;
	
	/**
	 * 商户门店明细查询 (新版)
	 * 
	 * @param req
	 * @return json格式字符串
	 */
	String getShopInfoQueryITF(ShopInfQueryRequest req) throws Exception;

	/**
	 * 商户门店列表查询(含分页)
	 * @param req
	 * @return
	 * @throws Exception
	 */
	String shopListQueryITF(ShopInfQueryRequest req)throws Exception;
	
	/**
	 * 商户门店列表查询(含分页) 新版
	 * @param req
	 * @return
	 * @throws Exception
	 */
	String getShopListQueryITF(ShopInfQueryRequest req)throws Exception;
	
	/**
	 * 商户信息查询
	 * 
	 * @param req
	 * @return
	 * @throws Exception
	 */
	String merchantInfoQueryITF(MchntInfQueryRequest req) throws Exception;
	
	/**
	 * 商户信息查询 (新版本)
	 * 
	 * @param req
	 * @return
	 * @throws Exception
	 */
	String getMerchantInfoQueryITF(BaseTxnReq req) throws Exception;
	
	/**
	 * 会员卡交易明细查询
	 * 
	 * @param req
	 * @return
	 * @throws Exception
	 */
	String cardTransDetailQueryITF(CardTransDetailQueryRequest req) throws Exception;
	
	/**
	 * 交易异常查询
	 * 
	 * @param BaseTxnReq
	 * @return
	 * @throws Exception
	 */
	String transExceptionQueryITF(BaseTxnReq req) throws Exception;
	
	/**
	 * 客户可购卡数量查询
	 * 
	 * @return
	 * @throws Exception
	 */
	String customerBuyCardStocksQueryITF(CustomerBuyCardStocksRequest req ) throws Exception;
}
