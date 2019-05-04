package com.cn.thinkx.wecard.customer.module.customer.service.impl;

import java.io.UnsupportedEncodingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.thinkx.common.wecard.domain.channeluser.ChannelUserInf;
import com.cn.thinkx.common.wecard.domain.person.PersonInf;
import com.cn.thinkx.common.wecard.domain.user.UserInf;
import com.cn.thinkx.common.wecard.module.account.mapper.AccountFansDao;
import com.cn.thinkx.common.wecard.module.channeluser.mapper.ChannelUserInfMapper;
import com.cn.thinkx.common.wecard.module.person.mapper.PersonInfDao;
import com.cn.thinkx.common.wecard.module.user.mapper.UserInfDao;
import com.cn.thinkx.pms.base.utils.BaseConstants;
import com.cn.thinkx.wecard.customer.module.customer.service.PersonInfService;
import com.cn.thinkx.wecard.customer.module.pub.service.PublicService;
import com.cn.thinkx.wechat.base.wxapi.domain.AccountFans;

@Service("personInfService")
public class PersonInfServiceImpl implements PersonInfService {

	@Autowired
	private PersonInfDao personInfDao;

	@Autowired
	private UserInfDao userInfDao;

	@Autowired
	private ChannelUserInfMapper channelUserInfMapper;

	@Autowired
	private AccountFansDao accountFansDao;

	@Autowired
	private PublicService publicService;

	public int insertPersonInf(PersonInf personInf) {
		return personInfDao.insertPersonInf(personInf);
	}

	@Override
	public PersonInf findPersonInfById(String personId) {
		return personInfDao.getPersonInfById(personId);
	}

	public PersonInf getPersonInfByUserId(String userId) {
		return personInfDao.getPersonInfByUserId(userId);
	}

	public int updateUserInf(UserInf userInf) {
		return userInfDao.updateUserInf(userInf);
	}

	public String addPersonInfRegister(String openid, String phoneNumber) {
		String userId;
		int operNum = 0;
		AccountFans accountFans = accountFansDao.getByOpenId(openid);
		UserInf user = userInfDao.getUserInfByOpenId(openid);

		/*** 判断用户 手机号是否已经注册，如果已经注册 判断用户是同一人 2017-07-27 ***/
		PersonInf personInf = personInfDao.getPersonInfByPhoneNo(phoneNumber);

		if (user == null) {
			if (personInf == null) {
				/** 用户信息主键 */
				userId = publicService.getPrimaryKey();
				user = new UserInf();
				user.setUserId(userId);
				user.setDataStat(BaseConstants.DataStatEnum.TRUE_STATUS.getCode());
				user.setUserType(BaseConstants.UserTypeEnum.WX_TYPE.getCode());
				userInfDao.insertUserInf(user);

				/** 个人信息 **/
				personInf = new PersonInf();
				personInf.setUserId(user.getUserId());
				personInf.setMobilePhoneNo(phoneNumber);
				try {
					if (accountFans != null && accountFans.getNickname() != null) {
						personInf.setPersonalName(new String(accountFans.getNickname(), "UTF-8"));
					}
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				this.insertPersonInf(personInf);
			} else {
				userId = personInf.getUserId();
			}

			/** 用户渠道信息 **/
			ChannelUserInf channelUserInf = new ChannelUserInf();
			channelUserInf.setUserId(userId);
			channelUserInf.setExternalId(openid);
			channelUserInf.setChannelCode(BaseConstants.ChannelCode.CHANNEL1.toString());
			channelUserInf.setDataStat(BaseConstants.DataStatEnum.TRUE_STATUS.getCode());
			operNum = channelUserInfMapper.insertChannelUserInf(channelUserInf);
		} else {
			userId = user.getUserId();
			personInf = personInfDao.getPersonInfByUserId(user.getUserId());
			if (personInf == null) {
				personInf = new PersonInf();
				personInf.setUserId(user.getUserId());
			}
			personInf.setMobilePhoneNo(phoneNumber);
			operNum = personInfDao.updatePersonInf(personInf);
		}
		if (operNum == 1) {
			return userId;
		} else {
			return null;
		}
	}

	/**
	 * 获取用户的手机号码
	 * 
	 * @param openid
	 * @return
	 */
	public String getPhoneNumberByOpenId(String openid) {
		return personInfDao.getPhoneNumberByOpenId(openid);
	}

	public PersonInf getPersonInfByAccountNo(String accountNo) {
		return personInfDao.getPersonInfByAccountNo(accountNo);
	}

	@Override
	public PersonInf getPersonInfByPhoneAndChnl(String phoneNo, String channel) {
		return personInfDao.getPersonInfByPhoneAndChnl(phoneNo, channel);
	}

	@Override
	public PersonInf getPersonInfByOpenId(String openid) {
		return personInfDao.getPersonInfByOpenId(openid);
	}
}
