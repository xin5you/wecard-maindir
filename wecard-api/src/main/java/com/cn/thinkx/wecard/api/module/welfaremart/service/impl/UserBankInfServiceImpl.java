package com.cn.thinkx.wecard.api.module.welfaremart.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.thinkx.pms.base.utils.BankUtil;
import com.cn.thinkx.pms.base.utils.NumberUtils;
import com.cn.thinkx.wecard.api.module.welfaremart.mapper.UserBankInfMapper;
import com.cn.thinkx.wecard.api.module.welfaremart.model.UserBankInf;
import com.cn.thinkx.wecard.api.module.welfaremart.service.UserBankInfService;

@Service("userBankInfService")
public class UserBankInfServiceImpl implements UserBankInfService {

	@Autowired
	private UserBankInfMapper userBankInfMapper;

	@Override
	public List<UserBankInf> getUserBankInfList(UserBankInf userBankInf) {
		return this.userBankInfMapper.getUserBankInfList(userBankInf);
	}

	@Override
	public UserBankInf getUserBankInfByBankNo(String bankNo) {
		return this.userBankInfMapper.getUserBankInfByBankNo(bankNo);
	}

	@Override
	public List<UserBankInf> getUserBankInfByUserId(String userId) {
		List<UserBankInf> userBankList = userBankInfMapper.getUserBankInfByUserId(userId);
		for (UserBankInf u : userBankList) {
			u.setBankName(BankUtil.getBankNameByCode(u.getAccountBank()));
			u.setBankNum(NumberUtils.hideCardNo(u.getBankNo()));
		}
		return userBankList;
	}

	@Override
	public int insertUserBankInf(UserBankInf userBankInf) {
		return this.userBankInfMapper.insertUserBankInf(userBankInf);
	}

	@Override
	public int updateUserBankInf(UserBankInf userBankInf) {
		return this.userBankInfMapper.updateUserBankInf(userBankInf);
	}

	@Override
	public int deleteUserBankInf(String bankNo) {
		return this.userBankInfMapper.deleteUserBankInf(bankNo);
	}

	@Override
	public UserBankInf getUserBankInf(UserBankInf userBankInf) {
		return this.userBankInfMapper.getUserBankInf(userBankInf);
	}


}
