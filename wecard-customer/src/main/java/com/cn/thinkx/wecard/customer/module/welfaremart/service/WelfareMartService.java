package com.cn.thinkx.wecard.customer.module.welfaremart.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.cn.thinkx.common.wecard.domain.base.ResultHtml;
import com.cn.thinkx.common.wecard.domain.cardkeys.CardKeysOrderInf;
import com.cn.thinkx.common.wecard.domain.cardkeys.CardKeysProduct;
import com.cn.thinkx.common.wecard.domain.cardkeys.UserBankInf;
import com.cn.thinkx.common.wecard.domain.person.PersonInf;
import com.cn.thinkx.wecard.customer.module.checkstand.vo.TransOrderReq;
import com.cn.thinkx.wecard.customer.module.welfaremart.vo.CardRechargeResp;
import com.cn.thinkx.wecard.customer.module.welfaremart.vo.WelfareUserInfo;
import com.cn.thinkx.wecard.customer.module.welfaremart.vo.WelfaremartResellResp;

public interface WelfareMartService {

	/**
	 * 进入卡券集市主页
	 *
	 * @param request
	 * @return
	 */
	WelfareUserInfo toWelfareMartHome(String userId) throws Exception;

	/**
	 * 购买卡券提交
	 *
	 * @return
	 */

	/**
	 *
	 * @param openId
	 * @param num
	 * @param productCode
	 * @param mchntCode
	 * @param shopCode
	 * @param notifyUrl
	 * @param redirectUrl
	 * @param isCard
	 * @return
	 * @throws Exception
	 */
	TransOrderReq buyCardCommit(String openId,String num,String productCode,String mchntCode,String shopCode,String notifyUrl,String redirectUrl,String transAmt,boolean isCard) throws Exception;

	/**
	 * 购买卡券重定向方法
	 *
	 * @param request
	 * @return
	 * @throws Exception
	 */
	List<CardKeysProduct> welfareBugCardRedirect(HttpServletRequest request) throws Exception;

	/**
	 * 兑换卡包列表
	 *
	 * @param request
	 * @return
	 */
	List<CardKeysProduct> cardBagList(HttpServletRequest request) throws Exception;

	/**
	 * 转让提交
	 * @param resellNum
	 * @param productCode
	 * @param bankNo
	 * @param userId
	 * @return
	 * @throws Exception
	 */
	WelfaremartResellResp resellCommit(String resellNum,String productCode, String bankNo, String userId) throws Exception;

	/**
	 * 银行卡号校验
	 *
	 * @param bankNo
	 * @param userId
	 * @return
	 */
	UserBankInf bankNoValid(String bankNo, String userId) throws Exception;

	/**
	 * 添加银行卡
	 *
	 * @param request
	 * @return
	 */
	ResultHtml addBankCommit(HttpServletRequest request) throws Exception;

	/**
	 * 删除银行卡
	 *
	 * @param request
	 * @return
	 */
	boolean deleteBankCard(HttpServletRequest request) throws Exception;

	/**
	 * 银行卡列表
	 *
	 * @param request
	 * @return
	 */
	List<UserBankInf> bankCardList(HttpServletRequest request) throws Exception;

	/**
	 * 查询卡密交易订单明细
	 *
	 * @param userId
	 * @return
	 */
	List<CardKeysOrderInf> cardBagTransDetails(String userId) throws Exception;

	/**
	 * 设置银行卡默认
	 *
	 * @param request
	 * @return
	 * @throws Exception
	 */
	boolean isDefaultBankCard(HttpServletRequest request) throws Exception ;

	/**
	 * 卡券页充值
	 *
	 * @param request
	 * @return
	 * @throws Exception
	 */
	CardRechargeResp cardRecharge(HttpServletRequest request) throws Exception ;

	/**
	 * 卡券页充值提交
	 *
	 * @param productCode
	 * @param personInf
	 * @return
	 * @throws Exception
	 */
	boolean cardRechargeCommit(String productCode, PersonInf personInf) throws Exception ;

}
