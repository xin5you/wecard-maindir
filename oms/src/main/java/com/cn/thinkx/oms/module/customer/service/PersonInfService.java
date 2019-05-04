package com.cn.thinkx.oms.module.customer.service;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.cn.thinkx.oms.module.customer.model.CardInf;
import com.cn.thinkx.oms.module.customer.model.PersonInf;
import com.github.pagehelper.PageInfo;

public interface PersonInfService {
	
	/**
	 * 根据Id类型查询
	 * @param personId
	 * @return
	 */
	 PersonInf findPersonInfById(String personId);

	/**
	 * insert用户信息
	 * @return
	 */
	 int insertPersonInf(PersonInf personInf);
		
	/**
	 * 修改用户信息
	 * @param userInf
	 * @return
	 */
	 int updatePersonInf(PersonInf personInf);
	 
	/**
	 * 根据手机号查找用户
	 * @param phoneNo
	 * @return
	 */
	PersonInf getPersonInfByPhoneNo(String phoneNo);
	
	/**
	 * 根据手机号查找用户
	 * @param phoneNo
	 * @param channel
	 * @return
	 */
	 PersonInf getPersonInfByPhoneAndChnl(String phoneNo,String channel);
	 
	 /**
	  * 用户注册
	  * @param personInf
	  * @return
	  */
	 int doPersonInfRegister(PersonInf personInf);
	
	 /**
	  * 用户信息
	  * 
	  * @return
	  */
	 List<PersonInf> getPersonInfList(PersonInf personInf);
	 
	 PageInfo<PersonInf> getPersonInfListPage(int startNum, int pageSize,PersonInf personInf);
	 
	 /**
	  * 通过用户id删除对应用户渠道信息（物理删除）
	  * 
	  * @param userId
	  * @return
	  */
	 void deleteChannelUserInfByUserId(HttpServletRequest req);
	 
	 /**
	  * 会员卡注销卡产品
	  * 
	  * @param cardNo
	  * @return
	  */
	 int updateCardInf(String cardNo);
	 
	 /**
	  * 通过卡号查询卡产品
	  * 
	  * @param cardNo
	  * @return
	  */
	 CardInf getCardInfByCardNo(String cardNo);

}
