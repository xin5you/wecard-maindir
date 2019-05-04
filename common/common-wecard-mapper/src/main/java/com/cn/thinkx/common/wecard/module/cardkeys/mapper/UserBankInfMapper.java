package com.cn.thinkx.common.wecard.module.cardkeys.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cn.thinkx.common.wecard.domain.cardkeys.UserBankInf;

public interface UserBankInfMapper {
	
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
	UserBankInf getUserBankInfByBankNo(@Param("bankNo")String bankNo);
	
	/**
	 * 根据用户ID查询用户银行卡信息
	 * 
	 * @param userId
	 * @return
	 */
	List<UserBankInf> getUserBankInfByUserId(@Param("userId")String userId);
	
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
	UserBankInf getIsDefaultByUserId(@Param("userId")String userId);
	
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
	int deleteUserBankInf(@Param("bankNo")String bankNo);
}
