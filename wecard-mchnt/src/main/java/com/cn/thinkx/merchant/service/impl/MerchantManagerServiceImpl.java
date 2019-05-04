package com.cn.thinkx.merchant.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cn.thinkx.core.domain.ResultHtml;
import com.cn.thinkx.core.util.Constants;
import com.cn.thinkx.core.util.MessageUtil;
import com.cn.thinkx.merchant.domain.MerchantManager;
import com.cn.thinkx.merchant.mapper.MerchantManagerDao;
import com.cn.thinkx.merchant.service.MerchantManagerService;
import com.cn.thinkx.pms.base.utils.StringUtil;
import com.cn.thinkx.wechat.base.wxapi.domain.AccountFans;
import com.cn.thinkx.wxclient.domain.MerchantShareInf;
import com.cn.thinkx.wxclient.domain.WxFansRole;
import com.cn.thinkx.wxclient.domain.WxRole;
import com.cn.thinkx.wxclient.service.MerchantShareInfService;
import com.cn.thinkx.wxclient.service.WxFansRoleService;
import com.cn.thinkx.wxclient.service.WxRoleService;
import com.cn.thinkx.wxcms.service.AccountFansService;

@Service("merchantManagerService")
public class MerchantManagerServiceImpl implements MerchantManagerService {

	@Autowired
	private MerchantManagerDao merchantManagerDao;

	@Autowired
	private MerchantShareInfService merchantShareInfService;

	@Autowired
	private WxRoleService wxRoleService;

	@Autowired
	private WxFansRoleService wxFansRoleService;

	@Autowired
	private AccountFansService accountFansService;

	public MerchantManager getMerchantRoleTypeById(String openId) {
		return merchantManagerDao.getMerchantRoleTypeById(openId);
	}

	public MerchantManager getMerchantManagerById(String mId) {
		return merchantManagerDao.getMerchantManagerById(mId);
	}

	public List<MerchantManager> getMerchantManagerList(MerchantManager entity) {
		return merchantManagerDao.getMerchantManagerList(entity);
	}

	public MerchantManager getMerchantManagerByOpenId(String openId) {
		return merchantManagerDao.getMerchantManagerByOpenId(openId);
	}

	/**
	 * 通过openId 查询商户所属的商户，机构等信息
	 * 
	 * @param openId
	 * @return
	 */
	public MerchantManager getMerchantInsInfByOpenId(String openId) {
		return merchantManagerDao.getMerchantInsInfByOpenId(openId);
	}

	/**
	 * 修改管理
	 * 
	 * @param entity
	 * @return
	 */
	public int updateMerchantManager(MerchantManager entity) {

		if (Constants.DataStatEnum.FALSE_STATUS.getCode().equals(entity.getDataStat())) { // 修改管理状态
																							// 如果是禁用状态
			/*** 修改粉丝菜单 ***/
			AccountFans accountFans = accountFansService.getByOpenId(entity.getMangerName());
			accountFans.setFansStatus(Constants.FansStatusEnum.Fans_STATUS_00.getCode());
			accountFans.setGroupid("0");
			accountFansService.updateAccountFansByMcht(accountFans);

			/*** 删除粉丝权限 **/
			wxFansRoleService.deleteWxMchntFansByFansId(accountFans.getId());
		}

		return merchantManagerDao.updateMerchantManager(entity);
	}

	/**
	 * 员工管理 0:新增，1：编辑 2：删除
	 * 
	 * @param m0
	 *            当前操作管理员对象
	 * @param m1
	 *            编辑员工对象
	 * @param flag
	 * @return
	 */
	public ResultHtml updateMerchantManager(MerchantManager m0, MerchantManager entity, String flag) {
		ResultHtml result = new ResultHtml();
		result.setStatus(false);

		if ("0".equals(flag)) {
			return result;
		} else {
			// MerchantManager
			// m0=merchantManagerDao.getMerchantManagerById(merchantManager.getMchntId());
			// //管理员对象
			MerchantManager m1 = merchantManagerDao.getMerchantManagerById(entity.getMangerId()); // 编辑对象

			if (m0 != null && m1 != null && m0.getMchntId().equals(m1.getMchntId())) {
				if ("1".equals(flag)) {
					m1.setName(entity.getName());
				} else if ("2".equals(flag)) {
					m1.setRoleType(""); // 取消用户权限
					m1.setDataStat(Constants.DataStatEnum.FALSE_STATUS.getCode()); // 设置当前用户的状态
				}
				int y = this.updateMerchantManager(m1);
				if (y > 0) {
					result.setStatus(true);
				} else {
					result.setMsg(MessageUtil.ERROR_MSSAGE);
				}
			}
		}
		return result;

	}

	/**
	 * 员工注册认证
	 * 
	 * @param accountFans
	 *            粉丝对象
	 * @param shareInf
	 *            链接分享对象
	 * @return
	 */
	public int insertEmployeeManager(AccountFans accountFans, MerchantShareInf shareInf) {
		int optionNum = 0;

		accountFans.setFansStatus(Constants.FansStatusEnum.Fans_STATUS_10.getCode());
		accountFans.setGroupid("100");// 管理员组Id
		accountFansService.updateAccountFansByMcht(accountFans); // 更改粉丝菜单操作权限

		/*** 添加管理员 ***/
		// MerchantManager
		// m=merchantManagerDao.getMerchantInsInfByOpenId(accountFans.getOpenId());
		// if(m !=null){
		// return 0;
		// }else{
		MerchantManager m = new MerchantManager();
		// }
		m.setMangerName(accountFans.getOpenId());
		m.setMchntId(shareInf.getMchntId()); // 所属商户
		m.setShopId(shareInf.getShopId()); // 所属门店
		m.setRoleType(shareInf.getRoleType());
		m.setPhoneNumber(shareInf.getPhoneNumber());
		m.setName(shareInf.getRemarks());
		m.setDataStat(Constants.DataStatEnum.TRUE_STATUS.getCode()); // 启用
		merchantManagerDao.insertMerchantManager(m);

		/** 添加权限 **/
		WxRole wxRole = wxRoleService.findWxRoleByRoleType(shareInf.getRoleType()); // 根据角色类型查找
		WxFansRole wxFansRole = new WxFansRole();
		wxFansRole.setFansId(accountFans.getId());
		wxFansRole.setRoleId(wxRole.getRoleId());
		wxFansRole.setDataStat(Constants.DataStatEnum.TRUE_STATUS.getCode()); // 启用标记
		wxFansRoleService.insertWxFansRole(wxFansRole);

		/** 修改分享链接装 ***/
		optionNum = merchantShareInfService.updateMerchantShareInfDataStat(shareInf.getShareId());

		return optionNum;
	}

	/**
	 * 通过手机号查找商户管理员临时表信息
	 * 
	 * @param phoneNumber
	 * @return
	 */
	public MerchantManager getMchntManagerTmpByPhoneNumber(String phoneNumber) {
		return merchantManagerDao.getMchntManagerTmpByPhoneNumber(phoneNumber);
	}

	public MerchantManager getMchntManagerByPhoneNumber(String phoneNumber) {
		return merchantManagerDao.getMchntManagerByPhoneNumber(phoneNumber);
	}

	/**
	 * 员工注册认证
	 * 
	 * @param accountFans
	 *            粉丝对象
	 * @param mchntManagerTmp
	 *            临时表对象
	 * @return
	 */
	public int insertEmployeeManager(AccountFans accountFans, MerchantManager mchntManagerTmp) {
		int optionNum = 0;

		accountFans.setFansStatus(Constants.FansStatusEnum.Fans_STATUS_10.getCode());
		accountFans.setGroupid("100");// 管理员组Id
		optionNum = accountFansService.updateAccountFansByMcht(accountFans); // 更改粉丝菜单操作权限
		MerchantManager m = new MerchantManager();

		/** 添加权限 **/
		WxRole wxRole = null;
		String roleTypeTmp = mchntManagerTmp.getRoleType();
		if (StringUtil.isNotEmpty(roleTypeTmp)) {
			if (roleTypeTmp.indexOf("100") > 0 || roleTypeTmp.indexOf("200") > 0) {// 老板和财务权限时，员工注册信息门店ID为空
				m.setShopId(""); // 所属门店
			} else {
				m.setShopId(mchntManagerTmp.getShopId());
			}
			String[] roleTypes = roleTypeTmp.split(",");
			WxFansRole wxFansRole = new WxFansRole();
			for (int i = 0; i < roleTypes.length; i++) {
				wxRole = wxRoleService.findWxRoleByRoleType(roleTypes[i]); // 根据角色类型查找
				wxFansRole.setFansId(accountFans.getId());
				wxFansRole.setRoleId(wxRole.getRoleId());
				wxFansRole.setDataStat(Constants.DataStatEnum.TRUE_STATUS.getCode()); // 启用标记
				System.out.println("roleType:" + roleTypes[i] + "|" + "fansId:" + accountFans.getId() + "|" + "roleId:" + wxRole.getRoleId());
				wxFansRoleService.insertWxFansRole(wxFansRole);
			}
		}

		m.setMangerName(accountFans.getOpenId());
		m.setMchntId(mchntManagerTmp.getMchntId()); // 所属商户
		m.setRoleType(mchntManagerTmp.getRoleType());
		m.setPhoneNumber(mchntManagerTmp.getPhoneNumber());
		m.setName(mchntManagerTmp.getName());
		m.setRemarks(mchntManagerTmp.getRemarks());
		m.setDataStat(Constants.DataStatEnum.TRUE_STATUS.getCode()); // 启用
		optionNum = merchantManagerDao.insertMerchantManager(m);

		optionNum = merchantManagerDao.deleteMchntManagerTmpById(mchntManagerTmp.getMangerId());
		return optionNum;
	}

	/**
	 * 查找某个商户，门店下的 管理员角色列表
	 * 
	 * @param mchntCode
	 * @param shopCode
	 * @param roleType
	 * @return
	 */
	public List<MerchantManager> getMerchantManagerByRoleType(String mchntCode, String shopCode, String roleType) {
		return merchantManagerDao.getMerchantManagerByRoleType(mchntCode, shopCode, roleType);
	}
}
