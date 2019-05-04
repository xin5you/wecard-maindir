package com.cn.thinkx.merchant.service;

import java.util.List;

import com.cn.thinkx.core.domain.ResultHtml;
import com.cn.thinkx.merchant.domain.MerchantManager;
import com.cn.thinkx.wechat.base.wxapi.domain.AccountFans;
import com.cn.thinkx.wxclient.domain.MerchantShareInf;

public interface MerchantManagerService {

	public MerchantManager getMerchantRoleTypeById(String openId);
	
	/**
	 * 通过openId 查询商户管理员
	 * @param openId
	 * @return
	 */
	public MerchantManager getMerchantManagerByOpenId(String openId);
	
	/**
	 * 通过openId 查询商户所属的商户，机构等信息
	 * @param openId
	 * @return
	 */
	public MerchantManager getMerchantInsInfByOpenId(String openId);
	
	public MerchantManager getMerchantManagerById(String mId);
	
	public List<MerchantManager> getMerchantManagerList(MerchantManager entity);
	
	/**
	 * 修改管理
	 * @param entity
	 * @return
	 */
	public int updateMerchantManager(MerchantManager entity);
	
	/**
	 * 员工管理 0:新增，1：编辑 2：删除
	 * @param m0 管理员对象
	 * @param entity
	 * @param flag
	 * @return
	 */
	public ResultHtml updateMerchantManager(MerchantManager m0,MerchantManager entity,String flag);
	
	/**
	 * 员工注册认证
	 * @param accountFans 粉丝对象 
	 * @param shareInf 	  链接分享对象 
	 * @return
	 */
	public int insertEmployeeManager(AccountFans accountFans, MerchantShareInf shareInf);
	
	
	/**
	 * 通过手机号查找商户管理员临时表信息
	 * @param phoneNumber
	 * @return
	 */
	public MerchantManager getMchntManagerTmpByPhoneNumber(String phoneNumber);
	public MerchantManager getMchntManagerByPhoneNumber(String phoneNumber);
	
	/**
	 * 员工注册认证
	 * @param accountFans 粉丝对象 
	 * @param merchantManagerTmp 	临时表对象
	 * @return
	 */
	public int insertEmployeeManager(AccountFans accountFans, MerchantManager merchantManagerTmp);
	
	/**
	 * 查找某个商户，门店下的 管理员角色列表
	 * @param mchntCode
	 * @param shopCode
	 * @param roleType
	 * @return
	 */
	public List<MerchantManager> getMerchantManagerByRoleType(String mchntCode,String shopCode,String roleType);
}
