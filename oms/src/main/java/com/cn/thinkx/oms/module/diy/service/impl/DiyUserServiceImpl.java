package com.cn.thinkx.oms.module.diy.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.cn.thinkx.oms.module.diy.mapper.DiyRoleMapper;
import com.cn.thinkx.oms.module.diy.mapper.DiyUserMapper;
import com.cn.thinkx.oms.module.diy.model.DiyDataAuth;
import com.cn.thinkx.oms.module.diy.model.DiyUser;
import com.cn.thinkx.oms.module.diy.model.DiyUserDataAuth;
import com.cn.thinkx.oms.module.diy.model.DiyUserRole;
import com.cn.thinkx.oms.module.diy.service.DiyUserService;
import com.cn.thinkx.oms.module.sys.model.User;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service("diyUserService")
public class DiyUserServiceImpl implements DiyUserService {

	@Autowired
	@Qualifier("diyUserMapper")
	private DiyUserMapper diyUserMapper;

	@Autowired
	@Qualifier("diyRoleMapper")
	private DiyRoleMapper diyRoleMapper;

	/**
	 * 根据登录名得到用户对象
	 * 
	 * @param userName
	 * @return DiyUser
	 */
	public DiyUser getDiyUserByUserName(DiyUser diyUser) {
		return diyUserMapper.getDiyUserByUserName(diyUser);
	}

	/**
	 * 根据Id得到用户对象
	 * 
	 * @param userId
	 * @return DiyUser
	 */
	public DiyUser getDiyUserById(String userId) throws Exception {
		return diyUserMapper.getDiyUserById(userId);
	}

	/**
	 * 修改用户信息
	 * 
	 */
	public int updateDiyUser(DiyUser diyUser, String rolesId, User user) throws Exception {
		int operNum = diyUserMapper.updateDiyUser(diyUser);
		// 修改用户对应的商户信息
		DiyDataAuth diyDataAuth = this.getDiyDataAuthById(diyUser.getId());
		diyDataAuth.setMchntCode(diyUser.getMchntCode());
		diyDataAuth.setShopCode(diyUser.getShopCode());
		diyDataAuth.setUpdateUser(user.getId().toString());
		diyUserMapper.updateDiyDataAuth(diyDataAuth);

		DiyUserRole diyUserRole = new DiyUserRole();
		diyUserRole.setUserId(diyUser.getId());
		diyUserRole.setRoleId(rolesId);
		diyUserRole.setDataStat("0");
		diyUserRole.setCreateUser(user.getId().toString());
		diyUserRole.setUpdateUser(user.getId().toString());
		diyUserMapper.updateDiyUserRole(diyUserRole);

		return operNum;
	}

	/**
	 * 删除用户信息
	 */
	public void deleteDiyUser(String userId) throws Exception {
		// 删除用户角色表信息
		this.deleteDiyUserRoleByUserId(userId);
		DiyDataAuth diyDataAuth = this.getDiyDataAuthById(userId);
		this.deleteDiyUserDataAuthByUserId(userId);
		this.deleteDiyDataAuthByAuthId(diyDataAuth.getId());
		diyUserMapper.deleteDiyUserById(userId);
	}

	/**
	 * 添加用户
	 * 
	 * @param DiyUser
	 * @param rolesId
	 * @return
	 * @throws Exception
	 */
	public int saveDiyUser(DiyUser diyUser, String rolesId, User user) throws Exception {
		int operNum = diyUserMapper.insertDiyUser(diyUser);

		DiyDataAuth diyDataAuth = new DiyDataAuth();
		diyDataAuth.setMchntCode(diyUser.getMchntCode());
		diyDataAuth.setShopCode(diyUser.getShopCode());
		diyDataAuth.setDataStat("0");
		diyDataAuth.setCasecade("0");
		diyDataAuth.setCreateUser(user.getId().toString());
		diyDataAuth.setUpdateUser(user.getId().toString());
		this.saveDiyDataAuth(diyDataAuth);

		DiyUserDataAuth diyUserDataAuth = new DiyUserDataAuth();
		diyUserDataAuth.setAuthId(diyDataAuth.getId());
		diyUserDataAuth.setUserId(diyUser.getId());
		diyUserDataAuth.setCreateUser(user.getId().toString());
		diyUserDataAuth.setUpdateUser(user.getId().toString());
		diyUserDataAuth.setDataStat("0");
		this.saveDiyUserDataAuth(diyUserDataAuth);

		DiyUserRole diyUserRole = new DiyUserRole();
		diyUserRole.setUserId(diyUser.getId());
		diyUserRole.setRoleId(rolesId);
		diyUserRole.setDataStat("0");
		diyUserRole.setCreateUser(user.getId().toString());
		diyUserRole.setUpdateUser(user.getId().toString());
		diyUserMapper.saveDiyUserRole(diyUserRole);

		return operNum;
	}

	/**
	 * 查询所有用户
	 */
	public List<DiyUser> getDiyUserList(DiyUser entity) {
		return diyUserMapper.getDiyUserList(entity);
	}

	/**
	 * 用户列表
	 * 
	 * @param startNum
	 * @param pageSize
	 * @param DiyUser
	 * @return
	 */
	public PageInfo<DiyUser> getDiyUserPage(int startNum, int pageSize, DiyUser diyUser) {
		PageHelper.startPage(startNum, pageSize);
		List<DiyUser> diyUserList = getDiyUserList(diyUser);
		for (DiyUser diyUser2 : diyUserList) {
			if ("分销商管理员".equals(diyUser2.getRoleName())) {
				diyUser2.setRoleCheckflag("0");
			} else {
				diyUser2.setRoleCheckflag("1");
			}
		}
		PageInfo<DiyUser> userPage = new PageInfo<DiyUser>(diyUserList);
		return userPage;
	}

	/**
	 * 增加用户角色
	 * 
	 * @param entity
	 */
	public void saveDiyUserRole(DiyUserRole entity) {
		diyUserMapper.saveDiyUserRole(entity);
	}

	/**
	 * 删除用户角色
	 * 
	 * @param userId
	 */
	public void deleteDiyUserRoleByUserId(String userId) {
		diyUserMapper.deleteDiyUserRoleByUserId(userId);

	}

	/**
	 * 新增用户数据权限
	 */
	public void saveDiyUserDataAuth(DiyUserDataAuth entity) {
		diyUserMapper.saveDiyUserDataAuth(entity);
	}

	/**
	 * 新增数据权限
	 */
	public void saveDiyDataAuth(DiyDataAuth entity) {
		diyUserMapper.saveDiyDataAuth(entity);
	}

	/**
	 * 修改数据权限
	 */
	public int updateDiyDataAuth(DiyDataAuth entity) {
		return diyUserMapper.updateDiyDataAuth(entity);
	}

	/**
	 * 删除用户数据权限
	 */
	public int deleteDiyUserDataAuthByUserId(String userId) {
		return diyUserMapper.deleteDiyUserDataAuthByUserId(userId);
	}

	@Override
	public void deleteDiyDataAuthByAuthId(String id) {
		diyUserMapper.deleteDiyDataAuthByAuthId(id);
	}

	@Override
	public DiyDataAuth getDiyDataAuthById(String id) {
		return diyUserMapper.getDiyDataAuthById(id);
	}

	@Override
	public DiyUser getDiyUserByPhoneNo(DiyUser diyUser) {
		return diyUserMapper.getDiyUserByPhoneNo(diyUser);
	}

	@Override
	public DiyUser getRoleByMchntCodeAndShopCode(DiyUser diyUser) {
		return diyUserMapper.getRoleByMchntCodeAndShopCode(diyUser);
	}

}
