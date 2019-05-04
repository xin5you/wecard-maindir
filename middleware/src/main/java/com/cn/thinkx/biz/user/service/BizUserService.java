package com.cn.thinkx.biz.user.service;

import java.util.List;

import com.cn.thinkx.biz.user.model.CustomerAccount;
import com.cn.thinkx.biz.user.model.PersonInf;
import com.cn.thinkx.biz.user.model.UserInf;
import com.cn.thinkx.biz.user.model.UserMerchantAcct;
import com.cn.thinkx.dubbo.entity.TxnResp;
import com.cn.thinkx.facade.bean.CusAccOpeningRequest;

public interface BizUserService {
	
	/**
	 * 根据userId查找客户记录
	 * 
	 * @param userName
	 * @param channel
	 * @return
	 */
	UserInf getUserInfByUserName(String userName,String channel);
	
	/**
	 * 根据userId查找客户电话
	 * 
	 * @param userId
	 * @return
	 */
	String getPhoneNoByUserId(String userId);
	
	/**
	 * 根据userName查找userId
	 * 
	 * @param userName
	 * @param channel 渠道号
	 * @return
	 */
	String getUserIdByUserName(String userName, String channel);
	
	/**
	 * 客户开户
	 * 
	 * @param req
	 * @return
	 */
	TxnResp addPersonInf(CusAccOpeningRequest req);
	
	/**
	 * 通过UserId获取用户个人信息
	 * 
	 * @param userName 外部渠道用户Id
	 * @param channel 渠道号
	 * @return
	 */
	PersonInf getPersonInfByUserName(String userName,String channel);
	
	/**
	 * 获取客户所在商户的会员卡余额
	 * @param acc
	 * @return
	 */
	String getAccBalance(UserMerchantAcct acc);
	
	/**
	 * 获取客户所在商户的会员卡卡号
	 * @param acc
	 * @return
	 */
	String getCardNo(UserMerchantAcct acc);
	
	/**
	 * 获取客户会员卡列表
	 * 
	 * @param acc
	 * @return
	 */
	List<CustomerAccount> getCusAccList(UserMerchantAcct acc);

	/**
	 * 获取商户管理员Id
	 * @param openid
	 * @return
	 */
	String getManagerIdByOpenId(String openid);
	
	/**
	 * 根据手机号查找个人信息 适用于用户注册
	 * @param phoneNo
	 * @param channel 渠道标识
	 * @return
	 */
	PersonInf getPersonInfByPhoneNo(String phoneNo,String channel);
	
	/**
	 * 根据手机号查找用户信息 适用于用户注册
	 * @param phoneNo 适用于用户注册
	 * @param userName
	 * @return
	 */
	UserInf getUserInfByPhoneNo(String phoneNo,String channel);
	
	/**
	 * 获取用户主账户号
	 * @param acc
	 * @return
	 */
	String getAccountNoByExternalId(UserMerchantAcct acc);
	
}
