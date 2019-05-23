package com.cn.thinkx.wecard.api.module.welfaremart.mapper;

import com.cn.thinkx.wecard.api.module.welfaremart.model.UserBankInf;

import java.util.List;

public interface UserBankInfMapper {

    /**
     * 查询所有用户银行卡信息
     *
     * @param userBankInf
     * @return
     */
    List<UserBankInf> getUserBankInfList(UserBankInf userBankInf);

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
     * 新增用户银行卡信息
     *
     * @param userBankInf
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
