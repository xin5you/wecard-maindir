package com.cn.thinkx.oms.module.sys.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cn.thinkx.oms.base.controller.BaseController;
import com.cn.thinkx.oms.module.sys.model.Organization;
import com.cn.thinkx.oms.module.sys.model.Role;
import com.cn.thinkx.oms.module.sys.model.User;
import com.cn.thinkx.oms.module.sys.service.OrganizationService;
import com.cn.thinkx.oms.module.sys.service.RoleService;
import com.cn.thinkx.oms.module.sys.service.UserService;
import com.cn.thinkx.oms.util.StringUtils;
import com.cn.thinkx.pms.base.utils.MD5Util;
import com.cn.thinkx.pms.base.utils.StringUtil;
import com.github.pagehelper.PageInfo;



@Controller
@RequestMapping(value = "sys/user")
public class UserController extends BaseController {
	
	@Autowired
	@Qualifier("userService")
	private UserService userService;

	@Autowired
	@Qualifier("organizationService")
	private OrganizationService organizationService;
	
	@Autowired
	@Qualifier("roleService")
	private RoleService roleService;
	
	@RequestMapping(value = "/listUser")
	public ModelAndView listUser(HttpServletRequest req, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("sys/user/listUser");
		String operStatus=StringUtils.nullToString(req.getParameter("operStatus"));
		int startNum = parseInt(req.getParameter("pageNum"), 1);
		int pageSize = parseInt(req.getParameter("pageSize"), 10);
		User bizUser=new User();
		PageInfo<User> pageList = null;
		try {
			bizUser=getUserInfo(req);
			pageList=userService.getUserPage(startNum, pageSize, bizUser);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询角色列表信息出错", e);
		}
		mv.addObject("bizUser", bizUser);
		mv.addObject("pageInfo", pageList);
		mv.addObject("operStatus", operStatus);
		return mv;
	}
	
	
	@RequestMapping(value = "/intoAddUser")
	public ModelAndView intoAddUser(HttpServletRequest req, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("sys/user/addUser");
		List<Organization> organizationList=organizationService.getOrganizationList(new Organization());
		List<Role> roleList=roleService.getRoleList(new Role());
		
		mv.addObject("organizationList", organizationList);
		mv.addObject("roleList", roleList);
		return mv;
	}
	
	/**
	 * 角色添加提交
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/addUserCommit")
	@ResponseBody
	public Map<String, Object> addUserCommit(HttpServletRequest req, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		resultMap.put("status", Boolean.TRUE);
		String [] rolesIds=req.getParameterValues("rolesIds[]");

		try {
			User user=getUserInfo(req);
			
			User loginNameUser=userService.getUserByLoginName(user.getLoginname());
			if(loginNameUser !=null){
				resultMap.put("status", Boolean.FALSE);
				resultMap.put("msg", "当前登陆名已存在，请重新输入");
				return resultMap;
			}
			user.setPassword(MD5Util.md5(user.getPassword()));
			user.setIsdefault("1");
			user.setState("0");
			userService.saveUser(user, rolesIds);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("status", Boolean.FALSE);
			resultMap.put("msg", "新增用户失败，请重新添加");
			logger.error(e.getLocalizedMessage(), e);
		}
		return resultMap;
	}
	
	
	/**
	 * 修改密码
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/updatePwdCommit")
	@ResponseBody
	public Map<String, Object> updatePwdCommit(HttpServletRequest req, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		resultMap.put("status", Boolean.TRUE);
		String  oldPasswrod=req.getParameter("oldPasswrod");
		String  newPasswordPage=req.getParameter("newPasswordPage");
		String  newPassword2Page=req.getParameter("newPassword2Page");
		if(!newPasswordPage.equals(newPassword2Page)){
			resultMap.put("status", Boolean.FALSE);
			resultMap.put("msg", "修改密码失败，请重新提交");
			return resultMap;
		}
		try {
			User user=getCurrUser(req);
			User currUser=userService.getUserById(user.getId().toString());
			if(currUser !=null){
				if(!currUser.getPassword().equals(oldPasswrod)){
					resultMap.put("status", Boolean.FALSE);
					resultMap.put("msg", "请输入正确的旧密码");
					return resultMap;
				}
				currUser.setPassword(newPasswordPage);
				userService.updateUser(currUser);
			}

		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("status", Boolean.FALSE);
			resultMap.put("msg", "修改密码失败，请重新提交");
			logger.error(e.getLocalizedMessage(), e);
		}
		return resultMap;
	}
	
	
	
	/**
	 * @param req
	 * @param response
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/intoEditUser")
	public ModelAndView intoEditUser(HttpServletRequest req, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("sys/user/editUser");
		String userId=req.getParameter("userId");
		User user=userService.getUserById(userId);
		
		List<Organization> organizationList=organizationService.getOrganizationList(new Organization());
		List<Role> roleList=roleService.getRoleList(new Role());
		
		List<Role> userRoleList=roleService.getUserRoleByUserId(user.getId().toString());
		mv.addObject("userRoleList", userRoleList);
		
		mv.addObject("organizationList", organizationList);
		mv.addObject("roleList", roleList);
		mv.addObject("currUser", user);
		return mv;
	}
	
	
	/**
	 * 用户编辑 提交
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/editUserCommit")
	@ResponseBody
	public Map<String, Object> editUserCommit(HttpServletRequest req, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		resultMap.put("status", Boolean.TRUE);
		String [] rolesIds=req.getParameterValues("rolesIds[]");

		try {
			User user=getUserInfo(req);
			
			User loginNameUser=userService.getUserByLoginName(user.getLoginname());
			if(loginNameUser !=null){
				if(!user.getId().equals(loginNameUser.getId())){
					resultMap.put("status", Boolean.FALSE);
					resultMap.put("msg", "当前登陆名已存在，请重新输入");
					return resultMap;
				}
			}
			userService.updateUser(user, rolesIds);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("status", Boolean.FALSE);
			resultMap.put("msg", "编辑用户失败，请重新操作");
			logger.error(e.getLocalizedMessage(), e);
		}
		return resultMap;
	}

	
	/**
	* @Title: getUserInfo
	* @Description: 用户表封装
	* @param @param req
	* @param @return
	* @param @throws Exception
	* @return User    返回类型
	* @throws
	*/ 
	private User getUserInfo(HttpServletRequest req) throws Exception {
		User user=null;
		String userId=StringUtils.nullToString(req.getParameter("userId"));
		if(!StringUtils.isNullOrEmpty(userId)){
			user=userService.getUserById(userId);
		}else{
			user=new User();
		}
		user.setLoginname(StringUtils.nullToString(req.getParameter("loginname")));
		user.setName(StringUtils.nullToString(req.getParameter("name")));
		String password=StringUtils.nullToString(req.getParameter("password"));
		if(!StringUtil.isNullOrEmpty(password)){
			user.setPassword(password);
		}
		user.setSex(StringUtils.nullToString(req.getParameter("sex")));
		user.setUsertype(StringUtils.nullToString(req.getParameter("usertype")));
		user.setOrganizationId(StringUtils.nullToString(req.getParameter("organizationId")));
		return user;
	}

	
	/**
	 * 删除用户 commit
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/deleteUserCommit")
	@ResponseBody
	public Map<String, Object> deleteUserCommit(HttpServletRequest req, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("status", Boolean.TRUE);
		String userId=req.getParameter("userId");
		try {
			userService.deleteUser(userId);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("status", Boolean.FALSE);
			resultMap.put("msg", "删除用户失败，请重新操作");
			logger.error(e.getLocalizedMessage(), e);
		}
		return resultMap;
	}
	
}
