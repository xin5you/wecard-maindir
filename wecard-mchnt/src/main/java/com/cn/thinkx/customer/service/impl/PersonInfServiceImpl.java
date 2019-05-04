package com.cn.thinkx.customer.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.thinkx.core.util.Constants;
import com.cn.thinkx.customer.domain.PersonInf;
import com.cn.thinkx.customer.domain.UserInf;
import com.cn.thinkx.customer.mapper.PersonInfDao;
import com.cn.thinkx.customer.mapper.UserInfDao;
import com.cn.thinkx.customer.service.PersonInfService;
import com.cn.thinkx.pub.service.PublicService;
import com.cn.thinkx.wechat.base.wxapi.domain.AccountFans;
import com.cn.thinkx.wxcms.mapper.AccountFansDao;

@Service("personInfService")
public class PersonInfServiceImpl implements PersonInfService {

	@Autowired
	private PersonInfDao personInfDao;
	
	@Autowired
	private UserInfDao userInfDao;
	
	@Autowired
	private AccountFansDao accountFansDao;
	
	@Autowired
	private PublicService publicService;

	public int insertPersonInf(PersonInf personInf) {
		//用户表
		return personInfDao.insertPersonInf(personInf);
	}

	@Override
	public PersonInf findPersonInfById(String personId) {
		return personInfDao.getPersonInfById(personId);
	}

	/**
	 * 获取用户个人信息
	 * @param userId
	 * @return
	 */
	public PersonInf getPersonInfByUserId(String userId){
		return personInfDao.getPersonInfByUserId(userId);
	}
	
	/**
	 * 修改用户信息
	 * @param userInf
	 * @return
	 */
	public int updateUserInf(UserInf userInf){
		return userInfDao.updateUserInf(userInf);
	}
	
	/**客户会员注册**/
	public String  addPersonInfRegister(String openid,String phoneNumber){
		String userId;
		int operNum=0;
		AccountFans accountFans=accountFansDao.getByOpenId(openid);
		UserInf user=userInfDao.getUserInfByOpenId(openid);
		PersonInf personInf=null;
		
		if(user==null){ //insert 操作
			/**用户信息*/
			userId=publicService.getPrimaryKey();
			user=new UserInf();
			user.setUserId(userId);
			user.setUserName(openid);
			user.setDataStat(Constants.DataStatEnum.TRUE_STATUS.getCode());
			user.setUserType(Constants.UserTypeEnum.WX_TYPE.getCode());
			userInfDao.insertUserInf(user);
			
			/**个人信息**/
			personInf=new PersonInf();
			personInf.setUserId(user.getUserId());
			personInf.setMobilePhoneNo(phoneNumber);
			personInf.setPersonalName(accountFans.getNicknameStr());
			operNum=this.insertPersonInf(personInf);
			
		}else{
			//update 
			userId=user.getUserId();
			user.setUserName(openid);
			operNum=userInfDao.updateUserInf(user);
			
			personInf=personInfDao.getPersonInfByUserId(user.getUserId());
			if(personInf==null){
				personInf=new PersonInf();
				personInf.setUserId(user.getUserId());
			}
			personInf.setMobilePhoneNo(phoneNumber);
			personInf.setPersonalName(accountFans.getNicknameStr());
			operNum=personInfDao.updatePersonInf(personInf);
		}
		if(operNum==1){
			return userId;
		}else{
			return null;
		}
	}
	
	/**
	 * 获取用户的手机号码
	 * @param openid
	 * @return
	 */
	public String getPhoneNumberByOpenId(String openid){
		return personInfDao.getPhoneNumberByOpenId(openid);
	}
	
	public PersonInf getPersonInfByAccountNo(String accountNo){
		return personInfDao.getPersonInfByAccountNo(accountNo);
	}
}
