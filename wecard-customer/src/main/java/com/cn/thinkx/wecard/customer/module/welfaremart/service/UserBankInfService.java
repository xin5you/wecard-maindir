package com.cn.thinkx.wecard.customer.module.welfaremart.service;

import java.util.List;

import com.cn.thinkx.common.wecard.domain.cardkeys.UserBankInf;

public interface UserBankInfService {
	
	/**
	 * 查询所有用户银行卡信息
	 * 
	 * @return
	 */
	List<UserBankInf> getUserBankInfList();
	
	/**
	 * 根据银行卡号查询用户银行卡信息
	 * 
	 * @param bankNo
	 * @return
	 */
	UserBankInf getUserBankInfByBankNo(String bankNo);
	
	/**
	 * 根据用户ID查询用户银行卡信息
	 * 
	 * @param userId
	 * @return
	 */
	List<UserBankInf> getUserBankInfByUserId(String userId);
	
	/**
	 * 根据userId和bankNo查询用户卡信息
	 * 
	 * @param userBankInf
	 * @return
	 */
	UserBankInf getUserBankInf(UserBankInf userBankInf);
	
	/**
	 * 查询用户银行卡是否存在默认状态
	 * 
	 * @param userId
	 * @return
	 */
	UserBankInf getIsDefaultByUserId(String userId);
	
	/**
	 * 新增用户银行卡信息
	 * 
	 * @param userBankInfo
	 * @return
	 */
	int insertUserBankInf(UserBankInf userBankInf);
	
	/**
	 * 更新用户银行卡信息
	 * 
	 * @param userBankInf
	 * @return
	 */
	int updateUserBankInf(UserBankInf userBankInf);
	
	/**
	 * 删除用户银行卡信息
	 * 
	 * @param bankNo
	 * @return
	 */
	int deleteUserBankInf(String bankNo);
	
}
