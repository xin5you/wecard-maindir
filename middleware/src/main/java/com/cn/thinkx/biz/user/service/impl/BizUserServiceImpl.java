package com.cn.thinkx.biz.user.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cn.thinkx.biz.core.mapper.CtrlSystemMapper;
import com.cn.thinkx.biz.core.model.ImageManager;
import com.cn.thinkx.biz.core.service.ImageManagerService;
import com.cn.thinkx.biz.user.mapper.BizUserMapper;
import com.cn.thinkx.biz.user.model.ChannelUserInf;
import com.cn.thinkx.biz.user.model.CustomerAccount;
import com.cn.thinkx.biz.user.model.PersonInf;
import com.cn.thinkx.biz.user.model.UserInf;
import com.cn.thinkx.biz.user.model.UserMerchantAcct;
import com.cn.thinkx.biz.user.service.BizUserService;
import com.cn.thinkx.dubbo.entity.TxnResp;
import com.cn.thinkx.facade.bean.CusAccOpeningRequest;
import com.cn.thinkx.pms.base.utils.BaseConstants;
import com.cn.thinkx.pms.base.utils.BaseConstants.AppType;
import com.cn.thinkx.pms.base.utils.BaseConstants.Application;

@Service("bizUserService")
public class BizUserServiceImpl implements BizUserService {

	@Autowired
	@Qualifier("bizUserMapper")
	private BizUserMapper bizUserMapper;

	@Autowired
	@Qualifier("ctrlSystemMapper")
	private CtrlSystemMapper ctrlSystemMapper;

	@Autowired
	@Qualifier("imageManagerService")
	private ImageManagerService imageManagerService;

	@Override
	public UserInf getUserInfByUserName(String userName, String channel) {
		if (BaseConstants.ChannelCode.CHANNEL2.toString().equals(channel))
			channel = BaseConstants.ChannelCode.CHANNEL1.toString();

		return bizUserMapper.getUserInfByUserName(userName, channel);
	}

	@Override
	public String getPhoneNoByUserId(String userId) {
		return bizUserMapper.getPhoneNoByUserId(userId);
	}

	@Override
	public String getUserIdByUserName(String userName, String channel) {
		if (BaseConstants.ChannelCode.CHANNEL2.toString().equals(channel))
			channel = BaseConstants.ChannelCode.CHANNEL1.toString();

		return bizUserMapper.getUserIdByUserName(userName, channel);
	}

	@Override
	public TxnResp addPersonInf(CusAccOpeningRequest req) {
		TxnResp resp = new TxnResp();
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("id", "");
		bizUserMapper.getPrimaryKey(paramMap);
		String userId = (String) paramMap.get("id");

		PersonInf personInf = this.getPersonInfByPhoneNo(req.getMobile(), req.getChannel());

		if (personInf != null) {
			resp.setCode(BaseConstants.RESPONSE_EXCEPTION_CODE);
			resp.setInfo("该用户手机号已经注册");
			return resp;
		}

		/** 客户会员注册 先查询是否从薪无忧注册过 **/
		UserInf user = this.getUserInfByPhoneNo(req.getMobile(), BaseConstants.ChannelCode.CHANNEL1.toString());
		if (user == null) 
			user = this.getUserInfByPhoneNo(req.getMobile(), BaseConstants.ChannelCode.CHANNEL0.toString());// 是否从管理平台注册过
		
		int u = 0, p = 0;
		if (user == null) {
			user = new UserInf();
			user.setUserId(userId);
			user.setDataStat(BaseConstants.DataStatEnum.TRUE_STATUS.getCode());
			u = bizUserMapper.insertUserInf(user);

			/** 个人信息 **/
			personInf = new PersonInf();
			personInf.setUserId(user.getUserId());
			personInf.setMobilePhoneNo(req.getMobile());
			personInf.setPersonalName(req.getUserName());
			p = bizUserMapper.insertPersonInf(personInf);
		} else {
			u = 1;
			p = 1;
			userId = user.getUserId();
		}
		/** 用户渠道信息 **/
		ChannelUserInf channelUser = new ChannelUserInf();
		channelUser.setUserId(userId);
		channelUser.setExternalId(req.getUserId());
		channelUser.setChannelCode(req.getChannel());
		channelUser.setDataStat(BaseConstants.DataStatEnum.TRUE_STATUS.getCode());
		int c = bizUserMapper.insertChannelUserInf(channelUser);

		if (u > 0 && p > 0 && c > 0) {
			resp.setCode(BaseConstants.RESPONSE_SUCCESS_CODE);
			resp.setInfo(BaseConstants.RESPONSE_SUCCESS_INFO);
			resp.setHkbUserID(userId);
			return resp;
		} else {
			resp.setCode(BaseConstants.RESPONSE_EXCEPTION_CODE);
			resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
			return resp;
		}
	}

	@Override
	public PersonInf getPersonInfByUserName(String userName, String channel) {
		if (BaseConstants.ChannelCode.CHANNEL2.toString().equals(channel)) 
			channel = BaseConstants.ChannelCode.CHANNEL1.toString();
		
		return bizUserMapper.getPersonInfByUserName(userName, channel);
	}

	@Override
	public List<CustomerAccount> getCusAccList(UserMerchantAcct acc) {
		List<CustomerAccount> list = bizUserMapper.getCusAccList(acc);
		if (list != null && list.size() > 0) {
			for (CustomerAccount ca : list) {
				ImageManager im = new ImageManager();
				im.setApplicationId(ca.getProductCode());
				im.setApplication(Application.APP_PROD.getCode());
				im.setApplicationType(AppType.A3001.getCode());
				List<String> urlList = imageManagerService.getImagesUrl(im);
				if (urlList != null && urlList.size() > 0) {
					ca.setProductImage(urlList.get(0));// 产品卡面目前只有一张
				} else {
					ca.setProductImage(null);
				}

				urlList = null;
				im.setApplicationId(ca.getMchntCode());
				im.setApplication(Application.APP_MCHNT.getCode());
				im.setApplicationType(AppType.A1001.getCode());
				urlList = imageManagerService.getImagesUrl(im);
				if (urlList != null && urlList.size() > 0) {
					ca.setBrandLogo(urlList.get(0));// 商户LOGO目前只有一张
				} else {
					ca.setBrandLogo(null);
				}
				urlList = null;// 释放内存
			}
		} else {
			list = new ArrayList<CustomerAccount>();
		}
		return list;
	}

	public String getAccBalance(UserMerchantAcct acc) {
		UserMerchantAcct accT = acc;
		if (BaseConstants.ChannelCode.CHANNEL2.toString().equals(accT.getChannelCode())) 
			accT.setChannelCode(BaseConstants.ChannelCode.CHANNEL1.toString());
		
		return bizUserMapper.getAccBalance(accT);
	}

	public String getCardNo(UserMerchantAcct acc) {
		UserMerchantAcct accT = acc;
		if (BaseConstants.ChannelCode.CHANNEL2.toString().equals(accT.getChannelCode())) 
			accT.setChannelCode(BaseConstants.ChannelCode.CHANNEL1.toString());
		
		return bizUserMapper.getCardNo(accT);
	}

	public String getManagerIdByOpenId(String openid) {
		return bizUserMapper.getManagerIdByOpenId(openid);
	}

	/**
	 * 根据手机号查找个人信息 适用于用户注册
	 * 
	 * @param phoneNo
	 * @param channel
	 *            渠道标识
	 * @return
	 */
	public PersonInf getPersonInfByPhoneNo(String phoneNo, String channel) {
		if (BaseConstants.ChannelCode.CHANNEL2.toString().equals(channel)) 
			channel = BaseConstants.ChannelCode.CHANNEL1.toString();
		
		return bizUserMapper.getPersonInfByPhoneNo(phoneNo, channel);
	}

	/**
	 * 根据手机号查找用户信息 适用于用户注册
	 * 
	 * @param phoneNo
	 *            适用于用户注册
	 * @param userName
	 * @return
	 */
	public UserInf getUserInfByPhoneNo(String phoneNo, String channel) {
		if (BaseConstants.ChannelCode.CHANNEL2.toString().equals(channel)) 
			channel = BaseConstants.ChannelCode.CHANNEL1.toString();
		
		return bizUserMapper.getUserInfByPhoneNo(phoneNo, channel);
	}

	/**
	 * 获取用户主账户号
	 * 
	 * @param externalId
	 * @param channel
	 * @return
	 */
	public String getAccountNoByExternalId(UserMerchantAcct acc) {
		UserMerchantAcct accT = acc;
		if (BaseConstants.ChannelCode.CHANNEL2.toString().equals(accT.getChannelCode())) 
			accT.setChannelCode(BaseConstants.ChannelCode.CHANNEL1.toString());
		
		return bizUserMapper.getAccountNoByExternalId(accT);
	}

}
