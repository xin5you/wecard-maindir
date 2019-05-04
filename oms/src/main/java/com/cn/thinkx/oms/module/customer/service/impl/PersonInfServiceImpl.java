package com.cn.thinkx.oms.module.customer.service.impl;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.thinkx.oms.constants.Constants;
import com.cn.thinkx.oms.module.customer.mapper.CardInfMapper;
import com.cn.thinkx.oms.module.customer.mapper.ChannelUserInfMapper;
import com.cn.thinkx.oms.module.customer.mapper.PersonInfMapper;
import com.cn.thinkx.oms.module.customer.mapper.UserInfMapper;
import com.cn.thinkx.oms.module.customer.mapper.UserMerchantAcctMapper;
import com.cn.thinkx.oms.module.customer.model.CardInf;
import com.cn.thinkx.oms.module.customer.model.ChannelUserInf;
import com.cn.thinkx.oms.module.customer.model.PersonInf;
import com.cn.thinkx.oms.module.customer.model.UserInf;
import com.cn.thinkx.oms.module.customer.model.UserMerchantAcct;
import com.cn.thinkx.oms.module.customer.service.PersonInfService;
import com.cn.thinkx.oms.module.sys.model.User;
import com.cn.thinkx.oms.util.StringUtils;
import com.cn.thinkx.pms.base.utils.BaseConstants;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service("personInfService")
public class PersonInfServiceImpl implements PersonInfService {

	@Autowired
	private PersonInfMapper personInfMapper;

	@Autowired
	private UserInfMapper userInfMapper;

	@Autowired
	private ChannelUserInfMapper channelUserInfMapper;

	@Autowired
	private CardInfMapper cardInfMapper;

	@Autowired
	private UserMerchantAcctMapper userMerchantAcctMapper;

	public int insertPersonInf(PersonInf personInf) {
		// 用户表
		return personInfMapper.insertPersonInf(personInf);
	}

	public PersonInf findPersonInfById(String personId) {
		return personInfMapper.getPersonInfById(personId);
	}

	/**
	 * 修改用户信息
	 * 
	 * @param userInf
	 * @return
	 */
	public int updatePersonInf(PersonInf personInf) {
		return personInfMapper.updatePersonInf(personInf);
	}

	public PersonInf getPersonInfByPhoneNo(String phoneNo) {
		return personInfMapper.getPersonInfByPhoneNo(phoneNo);
	}

	public PersonInf getPersonInfByPhoneAndChnl(String phoneNo, String channel) {
		return personInfMapper.getPersonInfByPhoneAndChnl(phoneNo, channel);
	}

	/**
	 * 用户注册
	 * 
	 * @param personInf
	 * @return
	 */
	public int doPersonInfRegister(PersonInf personInf) {

		int operNum = 0;

		// 判断是否在管理平台已经注册过
		PersonInf person = personInfMapper.getPersonInfByPhoneAndChnl(personInf.getMobilePhoneNo(),
				BaseConstants.ChannelCode.CHANNEL0.toString());

		if (person != null) {
			return 1;
		}
		person = personInfMapper.getPersonInfByPhoneNo(personInf.getMobilePhoneNo());

		if (person != null) {
			person.setPersonalCardType(personInf.getPersonalCardType()); // 证件类型
			person.setPersonalCardNo(personInf.getPersonalCardNo());// 证件信息
			person.setPersonalName(personInf.getPersonalName());// 用户姓名
			personInfMapper.updatePersonInf(person);
		} else {
			UserInf user = new UserInf();
			user.setDataStat(BaseConstants.DataStatEnum.TRUE_STATUS.getCode());
			user.setUserType(BaseConstants.UserTypeEnum.OMS_TYPE.getCode());
			userInfMapper.insertUserInf(user);

			/** 个人信息 **/
			person = new PersonInf();
			person.setUserId(user.getUserId());
			person.setPersonalCardType(personInf.getPersonalCardType()); // 证件类型
			person.setPersonalCardNo(personInf.getPersonalCardNo());// 证件信息
			person.setPersonalName(personInf.getPersonalName());// 用户姓名
			person.setMobilePhoneNo(personInf.getMobilePhoneNo());
			person.setSex(personInf.getSex());
			personInfMapper.insertPersonInf(person);
		}

		/** 用户渠道信息 **/
		ChannelUserInf channelUserInf = new ChannelUserInf();
		channelUserInf.setUserId(person.getUserId());
		channelUserInf.setExternalId(UUID.randomUUID().toString().replace("-", ""));
		channelUserInf.setChannelCode(BaseConstants.ChannelCode.CHANNEL0.toString());
		channelUserInf.setDataStat(BaseConstants.DataStatEnum.TRUE_STATUS.getCode());
		operNum = channelUserInfMapper.insertChannelUserInf(channelUserInf);

		return operNum;
	}

	@Override
	public List<PersonInf> getPersonInfList(PersonInf personInf) {
		return personInfMapper.getPersonInfList(personInf);
	}

	@Override
	public PageInfo<PersonInf> getPersonInfListPage(int startNum, int pageSize, PersonInf personInf) {
		PageHelper.startPage(startNum, pageSize);
		List<PersonInf> list = getPersonInfList(personInf);
		PageInfo<PersonInf> page = new PageInfo<PersonInf>(list);
		return page;
	}

	@Override
	public void deleteChannelUserInfByUserId(HttpServletRequest req) {
		String userId = StringUtils.nullToString(req.getParameter("userId"));
		HttpSession session=req.getSession();
		User user=(User)session.getAttribute(Constants.SESSION_USER);
		// 注销卡
		UserMerchantAcct userMerchantAcct = new UserMerchantAcct();
		userMerchantAcct.setUserId(userId);
		List<UserMerchantAcct> userMerchantAccList = userMerchantAcctMapper.getUserMerchantAcctByUser(userMerchantAcct);
		if (userMerchantAccList != null) {
			for (UserMerchantAcct userMerchantAcct2 : userMerchantAccList) {
				updateCardInf(userMerchantAcct2.getCardNo());
			}
		}
		// 通过userId删除用户信息(逻辑删除)
		UserInf userInf = userInfMapper.getUserInfById(userId);
		userInf.setDataStat(BaseConstants.DataStatEnum.FALSE_STATUS.getCode());
		userInf.setUpdateUser(user.getId().toString());
		userInf.setRemarks(user.getName());
		userInfMapper.updateUserInf(userInf);
		// 通过userId删除个人信息(逻辑删除)
		PersonInf personInf = personInfMapper.getPersonInfByUserId(userId);
		personInf.setDataStat(BaseConstants.DataStatEnum.FALSE_STATUS.getCode());
		personInf.setUpdateUser(user.getId().toString());
		personInf.setRemarks(user.getName());
		personInfMapper.updatePersonInf(personInf);
		// 通过用户id删除渠道用户表中信息（逻辑删除）
		ChannelUserInf channelUserInf = new ChannelUserInf();
		channelUserInf.setUserId(userId);
		channelUserInf.setUpdateUser(user.getId().toString());
		channelUserInf.setRemarks(user.getName());;
		channelUserInfMapper.deldteChannelUserInfByUserId(channelUserInf);
	}

	@Override
	public int updateCardInf(String cardNo) {
		return cardInfMapper.updateCardInf(cardNo); // 修改卡的注销状态
	}

	@Override
	public CardInf getCardInfByCardNo(String cardNo) {
		return cardInfMapper.getCardInfByCardNo(cardNo);
	}

}
