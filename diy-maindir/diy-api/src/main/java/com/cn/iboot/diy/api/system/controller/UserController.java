package com.cn.iboot.diy.api.system.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.cn.iboot.diy.api.base.constants.Constants;
import com.cn.iboot.diy.api.base.constants.ExceptionEnum;
import com.cn.iboot.diy.api.base.domain.BaseResult;
import com.cn.iboot.diy.api.base.exception.BizHandlerException;
import com.cn.iboot.diy.api.base.utils.MD5Utils;
import com.cn.iboot.diy.api.base.utils.NumberUtils;
import com.cn.iboot.diy.api.base.utils.ResultsUtil;
import com.cn.iboot.diy.api.base.utils.StringUtil;
import com.cn.iboot.diy.api.base.utils.StringUtils;
import com.cn.iboot.diy.api.system.domain.Role;
import com.cn.iboot.diy.api.system.domain.User;
import com.cn.iboot.diy.api.system.service.RoleService;
import com.cn.iboot.diy.api.system.service.UserService;
import com.github.pagehelper.PageInfo;

@RestController
@RequestMapping(value = "system/user")
public class UserController {

	private Logger logger = LoggerFactory.getLogger(getClass());

	String roleIdTemp="";
	
	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	/**
	 * 用户列表
	 * 
	 * @param req
	 * @return
	 */
	@GetMapping(value = "/listUser")
	public ModelAndView listUser(HttpServletRequest req, User user) {
		ModelAndView mv = new ModelAndView("system/user/listUser");
		HttpSession session = req.getSession();
		User u = (User) session.getAttribute(Constants.SESSION_USER);
		int startNum = NumberUtils.parseInt(req.getParameter("pageNum"), 1);
		int pageSize = NumberUtils.parseInt(req.getParameter("pageSize"), 10);
		try {
			user.setMchntCode(u.getMchntCode());
			PageInfo<User> pageList = userService.getUserPage(startNum, pageSize, user);
			mv.addObject("userSession", u);
			mv.addObject("pageInfo", pageList);
		} catch (Exception e) {
			logger.error("## 查询用户列表信息出错", e);
		}
		return mv;
	}

	/**
	 * 用户列表
	 * 
	 * @param req
	 * @param response
	 * @return
	 */
	@PostMapping(value = "/listUser")
	public ModelAndView listUser(HttpServletRequest req, HttpServletResponse resp, User user) {
		ModelAndView mv = new ModelAndView("system/user/listUser");
		HttpSession session = req.getSession();
		User u = (User) session.getAttribute(Constants.SESSION_USER);
		int startNum = NumberUtils.parseInt(req.getParameter("pageNum"), 1);
		int pageSize = NumberUtils.parseInt(req.getParameter("pageSize"), 10);
		try {
			user.setMchntCode(u.getMchntCode());
			PageInfo<User> pageList = userService.getUserPage(startNum, pageSize, user);
			mv.addObject("userSession", u);
			mv.addObject("user", user);
			mv.addObject("pageInfo", pageList);
		} catch (Exception e) {
			logger.error("## 查询用户列表信息出错", e);
		}
		return mv;
	}

	/**
	 * 新增用户信息页面
	 * 
	 * @param req
	 * @param response
	 * @return
	 */
	@PostMapping(value = "/intoAddUser")
	public ModelAndView intoAddUser(HttpServletRequest req) {
		ModelAndView mv = new ModelAndView("system/user/addUser");
		HttpSession session = req.getSession();
		User u = (User) session.getAttribute(Constants.SESSION_USER);
		try {
			List<User> shopList = userService.getShopListByUserId(u.getId());
			List<Role> roleList = roleService.getRoleList();
			String roleIdTmp = getRoleId(roleList,"总财务");	//总财务的角色id
			String mchntName = userService.getMchntNameByMchntCode(u.getMchntCode());
			mv.addObject("roleIdTmp", roleIdTmp);
			mv.addObject("mchntName", mchntName);
			mv.addObject("mchntCode", u.getMchntCode());
			mv.addObject("shopList", shopList);
			mv.addObject("roleList", roleList);
		} catch (Exception e) {
			logger.error("## 查询跳转新增用户页面信息出错", e);
		}
		return mv;
	}

	/**
	 * 新增用户信息
	 * 
	 * @param req
	 * @param user
	 * @return
	 */
	@PostMapping(value = "/addUser")
	public BaseResult<Object> addUser(HttpServletRequest req, User user) {
		HttpSession session = req.getSession();
		User u = (User) session.getAttribute(Constants.SESSION_USER);
		try {
			//user.getPhoneNo().substring(0, 3) + "****" + user.getPhoneNo().substring(8, 11);
			if (StringUtils.isEmpty(user.getUserName()))
				user.setUserName(user.getPhoneNo());
			User loginPhoneNo = userService.getUserByPhoneNo(user.getPhoneNo());
			if (loginPhoneNo != null) {
				logger.error("## 当前手机号码已存在");
				return ResultsUtil.error(ExceptionEnum.userNews.UN17.getCode(), ExceptionEnum.userNews.UN17.getMsg());
			}
			List<Role> roleList = roleService.getRoleList();
			String roleIdTmp = getRoleId(roleList,"门店财务");	//门店财务的角色id
			if(roleIdTmp.equals(user.getRoleId())){
				User userNumber = userService.getRoleByMchntCodeAndShopCode(user);
				if(!StringUtil.isNullOrEmpty(userNumber)){
					if(userNumber.getCountNumber() > 0){
						logger.error("## 当前门店下的门店财务已存在，请重新选择职位");
						return ResultsUtil.error(ExceptionEnum.userNews.UN19.getCode(), ExceptionEnum.userNews.UN19.getMsg());
					}
				}
			}
			
			if(("0").equals(user.getShopCode()))
				user.setShopCode(null);
			User userName = userService.getUserByUserName(user);
			if (StringUtil.isNullOrEmpty(userName)) {
				user.setCreateUser(u.getId());
				user.setUpdateUser(u.getId());
				user.setPassword(MD5Utils.MD5(user.getPassword()));
				userService.addUser(user);
			} else {
				logger.error("## 当前商户下用户名已存在");
				return ResultsUtil.error(ExceptionEnum.userNews.UN18.getCode(), ExceptionEnum.userNews.UN18.getMsg());
			}
		} catch (BizHandlerException e) {
			logger.error("## 新增用户出错", e.getMessage());
			return ResultsUtil.error(e.getCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("## 新增用户出错", e);
			return ResultsUtil.error(ExceptionEnum.ERROR_CODE, ExceptionEnum.ERROR_MSG);
		}
		return ResultsUtil.success();
	}

	/**
	 * 用户详情信息页面
	 * 
	 * @param req
	 * @return
	 */
	@PostMapping(value = "/detailUser")
	public User detailUser(HttpServletRequest req, User user) {
		User use = new User();
		String shopName = "";
		try {
			use = userService.selectByPrimaryKey(user.getId());
			
			if(use.getUserName().equals(use.getPhoneNo()))
				use.setUserName(use.getUserName().substring(0, 3) + "****" + use.getUserName().substring(8, 11));
			
			String roleName = roleService.getRoleNameByUserId(user.getId());
			User u = userService.getShopMchntCodeByUserId(user.getId());
			if (u.getShopCode() != null)
				shopName = userService.getShopNameByShopCode(u.getShopCode());
			String mchntName = userService.getMchntNameByMchntCode(u.getMchntCode());
			use.setRoleName(roleName);
			use.setMchntName(mchntName);
			use.setShopName(shopName);
		} catch (Exception e) {
			logger.error("## 查看用户详情信息出错", e);
		}
		return use;
	}

	/**
	 * 进入编辑用户
	 * 
	 * @param req
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/intoEditUser")
	public ModelAndView intoEditUser(HttpServletRequest req){
		ModelAndView mv = new ModelAndView("system/user/editUser");
		HttpSession session = req.getSession();
		User u = (User) session.getAttribute(Constants.SESSION_USER);
		try {
			String userId = req.getParameter("userId");
			User user = userService.getShopMchntCodeByUserId(userId);
			List<User> shopList = userService.getShopListByUserId(u.getId());
			List<Role> roleList = roleService.getRoleList();
			String roleIdTmp = getRoleId(roleList,"总财务");	//总财务的角色id
			String mchntName = userService.getMchntNameByMchntCode(user.getMchntCode());
			List<Role> userRole = roleService.getUserRoleByUserId(userId);
			userRole.stream().forEach(role ->{
				user.setRoleId(role.getId());
			});
			mv.addObject("roleIdTmp", roleIdTmp);
			mv.addObject("shopList", shopList);
			mv.addObject("roleList", roleList);
			mv.addObject("user", user);
			mv.addObject("mchntName", mchntName);
			mv.addObject("userRole", userRole);
		} catch (Exception e) {
			logger.error("## 查询跳转编辑用户页面信息出错", e);
		}
		return mv;
	}

	/**
	 * 用户编辑 提交
	 * 
	 * @param req
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@PostMapping(value = "/editUserCommit")
	public BaseResult<Object> editUserCommit(HttpServletRequest req, User user){
		try {
			User  u = userService.selectByPrimaryKey(user.getId());
			User userName = userService.getUserByUserName(user);
			if (!StringUtil.isNullOrEmpty(userName) && !userName.getId().equals(user.getId()) ) {
				logger.error("## 当前商户下用户名已存在");
				return ResultsUtil.error(ExceptionEnum.userNews.UN18.getCode(), ExceptionEnum.userNews.UN18.getMsg());
			} 
			List<Role> roleList = roleService.getRoleList();
			String roleIdTmp = getRoleId(roleList,"门店财务");	//门店财务的角色id
			if(roleIdTmp.equals(user.getRoleId())){
				User userNumber = userService.getRoleByMchntCodeAndShopCode(user);
				if(!StringUtil.isNullOrEmpty(userNumber)){
					if(userNumber.getCountNumber() > 0 && !userNumber.getId().equals(user.getId())){
						logger.error("## 当前门店下的门店财务已存在，请重新选择职位");
						return ResultsUtil.error(ExceptionEnum.userNews.UN19.getCode(), ExceptionEnum.userNews.UN19.getMsg());
					}
				}
			}
			userService.updateUser(req,user);
		} catch (BizHandlerException e) {
			logger.error("## 编辑用户出错", e.getMessage());
			return ResultsUtil.error(e.getCode(), e.getMessage());
		}catch (Exception e) {
			logger.error("##  编辑用户出错", e);
			return ResultsUtil.error(ExceptionEnum.ERROR_CODE, ExceptionEnum.ERROR_MSG);
		}
		return ResultsUtil.success();
	}

	/**
	 * 删除用户信息
	 * 
	 * @param req
	 * @param response
	 * @return
	 */
	@PostMapping(value = "/deleteUser")
	public BaseResult<Object> deleteUser(HttpServletRequest req, User user) {
		try {
			if (userService.deleteUser(user.getId()) > 0)
				return ResultsUtil.success();
			else
				return ResultsUtil.error(ExceptionEnum.userNews.UN03.getCode(), ExceptionEnum.userNews.UN03.getMsg());
		} catch (BizHandlerException e) {
			logger.error("## 删除用户出错", e.getMessage());
			return ResultsUtil.error(e.getCode(), e.getMessage());
		} catch (Exception e) {
			logger.error("## 删除用户出错", e);
			return ResultsUtil.error(ExceptionEnum.ERROR_CODE, ExceptionEnum.ERROR_MSG);
		}
	}

	/**
	 * 筛选角色是总财务的角色id
	 * 
	 * @param roleList
	 * @return
	 */
	public String getRoleId(List<Role> roleList,String roleName){
		roleList.stream().forEach(role ->{
			if(roleName.equals(role.getRoleName())){
				roleIdTemp = role.getId();
			}
		});
		return roleIdTemp;
	}
}
