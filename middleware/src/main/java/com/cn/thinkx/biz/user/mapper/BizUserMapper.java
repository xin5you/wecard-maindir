package com.cn.thinkx.biz.user.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.cn.thinkx.biz.user.model.ChannelUserInf;
import com.cn.thinkx.biz.user.model.CustomerAccount;
import com.cn.thinkx.biz.user.model.PersonInf;
import com.cn.thinkx.biz.user.model.UserInf;
import com.cn.thinkx.biz.user.model.UserMerchantAcct;

@Repository("bizUserMapper")
public interface BizUserMapper {

	/**
	 * 获取主键
	 * 
	 * @param paramMap
	 */
	void getPrimaryKey(Map<String, String> paramMap);

	UserInf getUserInfByUserName(@Param("userName") String userName, @Param("channel") String channel);
	
	String getPhoneNoByUserId(@Param("userId")String userId);

	String getUserIdByUserName(@Param("userName") String userName, @Param("channel") String channel);

	int insertPersonInf(PersonInf entity);

	int insertUserInf(UserInf entity);

	/**
	 * 获取用户个人信息
	 * 
	 * @param userId 外部用户Id
	 * @return
	 */
	PersonInf getPersonInfByUserName(@Param("userName") String userName, @Param("channel") String channel);

	/**
	 * 获取客户所在商户的会员卡余额
	 * 
	 * @param acc
	 * @return
	 */
	String getAccBalance(UserMerchantAcct acc);

	/**
	 * 获取客户所在商户的会员卡卡号
	 * 
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
	 * 
	 * @param openid
	 * @return
	 */
	String getManagerIdByOpenId(String openid);

//	/**
//	 * 获取用户Id
//	 * 
//	 * @param openid
//	 * @return
//	 */
//	String getUserIdByOpenId(String openid);
	
	/**
	 * 添加渠道用户信息
	 * @return
	 */
	int insertChannelUserInf(ChannelUserInf channelUserInf);

	
	
	/**
	 * 根据手机号查找个人信息 适用于用户注册
	 * @param phoneNo
	 * @param channel 渠道标识
	 * @return
	 */
	PersonInf getPersonInfByPhoneNo(@Param("phoneNo") String phoneNo, @Param("channel") String channel);
	
	/**
	 * 根据手机号查找用户信息 适用于用户注册
	 * @param phoneNo 适用于用户注册
	 * @param userName
	 * @return
	 */
	UserInf getUserInfByPhoneNo(@Param("phoneNo") String phoneNo, @Param("channel") String channel);
	
	/**
	 * 获取用户主账户号
	 * 
	 * @param acc
	 * @return
	 */
	String getAccountNoByExternalId(UserMerchantAcct acc);

}
