package com.cn.thinkx.oms.module.diy.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cn.thinkx.oms.base.controller.BaseController;
import com.cn.thinkx.oms.module.diy.model.DiyRole;
import com.cn.thinkx.oms.module.diy.model.DiyUser;
import com.cn.thinkx.oms.module.diy.service.DiyRoleService;
import com.cn.thinkx.oms.module.diy.service.DiyUserService;
import com.cn.thinkx.oms.module.merchant.model.MerchantInf;
import com.cn.thinkx.oms.module.merchant.model.ShopInf;
import com.cn.thinkx.oms.module.merchant.service.MerchantInfService;
import com.cn.thinkx.oms.module.merchant.service.ShopInfService;
import com.cn.thinkx.oms.module.sys.model.User;
import com.cn.thinkx.oms.util.StringUtils;
import com.cn.thinkx.pms.base.utils.MD5Util;
import com.cn.thinkx.pms.base.utils.StringUtil;
import com.cn.thinkx.wecard.facade.telrecharge.model.TelChannelInf;
import com.cn.thinkx.wecard.facade.telrecharge.service.TelChannelInfFacade;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping(value = "diy/diyUser")
public class DiyUserController extends BaseController {

	Logger logger = LoggerFactory.getLogger(DiyUserController.class);

	String roleIdTemp = "";

	@Autowired
	@Qualifier("diyUserService")
	private DiyUserService diyUserService;

	@Autowired
	@Qualifier("merchantInfService")
	private MerchantInfService merchantInfService;

	@Autowired
	@Qualifier("shopInfService")
	private ShopInfService shopInfService;

	@Autowired
	@Qualifier("diyRoleService")
	private DiyRoleService diyRoleService;

	@Autowired
	private TelChannelInfFacade telChannelInfFacade;

	/**
	 * 查询商户用户列表
	 * 
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/listDiyUser")
	public ModelAndView listDiyUser(HttpServletRequest req, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("diy/diyUser/listDiyUser");
		String operStatus = StringUtils.nullToString(req.getParameter("operStatus"));
		int startNum = parseInt(req.getParameter("pageNum"), 1);
		int pageSize = parseInt(req.getParameter("pageSize"), 10);
		DiyUser bizUser = new DiyUser();
		PageInfo<DiyUser> pageList = null;
		try {
			User user = getCurrUser(req);
			bizUser = getDiyUserInfo(req, user);
			bizUser.setMchntCode(StringUtils.nullToString(req.getParameter("mchntName")));
			pageList = diyUserService.getDiyUserPage(startNum, pageSize, bizUser);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("查询角色列表信息出错", e);
		}
		mv.addObject("bizUser", bizUser);
		mv.addObject("pageInfo", pageList);
		mv.addObject("operStatus", operStatus);
		return mv;
	}

	/**
	 * 进入新增页面
	 * 
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/intoAddDiyUser")
	public ModelAndView intoAddDiyUser(HttpServletRequest req, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("diy/diyUser/addDiyUser");
		// 获取商户信息
		List<MerchantInf> mchntList = merchantInfService.getMerchantInfListBySelect();
		mv.addObject("mchntList", mchntList);
		// 获取角色列表
		List<DiyRole> diyRoleList = diyRoleService.getDiyRoleList(new DiyRole());
		mv.addObject("diyRoleList", diyRoleList);

		// 添加分销商于用户的关联
		String roleIdTmp = getRoleId(diyRoleList, "分销商管理员"); // 门店财务的角色id
		mv.addObject("roleIdTmp", roleIdTmp);
		// 查询分销商信息
		try {
			TelChannelInf telChannelInf = new TelChannelInf();
			List<TelChannelInf> telChannelList = telChannelInfFacade.getTelChannelInfList(telChannelInf);
			mv.addObject("telChannelList", telChannelList);
		} catch (Exception e) {
			logger.error("## 查询分销商信息出错", e);
		}
		return mv;
	}

	/**
	 * 用户添加提交
	 * 
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/addDiyUserCommit")
	@ResponseBody
	public Map<String, Object> addDiyUserCommit(HttpServletRequest req, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		resultMap.put("status", Boolean.TRUE);
		String roleId = StringUtils.nullToString(req.getParameter("roleId"));
		String channelId = StringUtils.nullToString(req.getParameter("channelId"));
		try {
			User user = getCurrUser(req);
			DiyUser diyUser = getDiyUserInfo(req, user);
			DiyUser phoneNoUser = diyUserService.getDiyUserByPhoneNo(diyUser);
			if (phoneNoUser != null) {
				resultMap.put("status", Boolean.FALSE);
				resultMap.put("msg", "当前手机号码已存在，请重新输入");
				return resultMap;
			}
			DiyUser loginNameUser = diyUserService.getDiyUserByUserName(diyUser);
			if (loginNameUser != null) {
				resultMap.put("status", Boolean.FALSE);
				resultMap.put("msg", "当前商户下用户名已存在，请重新输入");
				return resultMap;
			}
			diyUser.setPassword(MD5Util.md5(diyUser.getPassword()));
			diyUser.setIsdefault("0");
			diyUser.setDataStat("0");
			diyUser.setCreateUser(user.getId().toString());
			diyUserService.saveDiyUser(diyUser, roleId, user);

			// 添加分销商于用户的关联
			List<DiyRole> diyRoleList = diyRoleService.getDiyRoleList(new DiyRole());
			String roleIdTmp = getRoleId(diyRoleList, "分销商管理员"); // 门店财务的角色id
			if (!StringUtil.isNullOrEmpty(channelId) && roleIdTmp.equals(roleId)) {
				TelChannelInf telChannelInf = telChannelInfFacade.getTelChannelInfById(channelId);
				if (!StringUtil.isNullOrEmpty(telChannelInf) && StringUtil.isNullOrEmpty(telChannelInf.getResv1())) {
					telChannelInf.setResv1(diyUser.getMchntCode());
					telChannelInfFacade.updateTelChannelInf(telChannelInf);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("status", Boolean.FALSE);
			resultMap.put("msg", "新增用户失败，请重新添加");
			logger.error(e.getLocalizedMessage(), e);
		}
		return resultMap;
	}

	/**
	 * 进入编辑用户
	 * 
	 * @param req
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/intoEditDiyUser")
	public ModelAndView intoEditDiyUser(HttpServletRequest req, HttpServletResponse response) throws Exception {
		ModelAndView mv = new ModelAndView("diy/diyUser/editDiyUser");
		String userId = req.getParameter("userId");
		DiyUser diyUser = diyUserService.getDiyUserById(userId);

		// 获取商户信息
		List<MerchantInf> mchntList = merchantInfService.getMerchantInfListBySelect();
		mv.addObject("mchntList", mchntList);

		// 获取所有的门店
		ShopInf shopInf = new ShopInf();
		shopInf.setMchntCode(diyUser.getMchntCode());
		List<ShopInf> shopInfList = shopInfService.findShopInfList(shopInf);
		mv.addObject("shopInfList", shopInfList);

		// 获取角色列表
		List<DiyRole> diyRoleList = diyRoleService.getDiyRoleList(new DiyRole());
		mv.addObject("diyRoleList", diyRoleList);

		String roleIdFinance = "";
		String roleIdBoss = "";
		for (DiyRole diyRole : diyRoleList) {
			if ("总财务".equals(diyRole.getRoleName())) {
				roleIdFinance = diyRole.getId();
			}
			if ("商户老板".equals(diyRole.getRoleName())) {
				roleIdBoss = diyRole.getId();
			}
			if ("分销商管理员".equals(diyRole.getRoleName())) {
				diyRoleList.remove(diyRoleList);
			}
		}
		mv.addObject("roleIdFinance", roleIdFinance);
		mv.addObject("roleIdBoss", roleIdBoss);
		List<DiyRole> diyUserRoleList = diyRoleService.getDiyUserRoleByUserId(diyUser.getId().toString());
		mv.addObject("diyUserRoleList", diyUserRoleList);

		mv.addObject("diyUser", diyUser);
		return mv;
	}

	/**
	 * 用户编辑 提交
	 * 
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/editDiyUserCommit")
	@ResponseBody
	public Map<String, Object> editUserCommit(HttpServletRequest req, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		resultMap.put("status", Boolean.TRUE);
		String roleId = StringUtils.nullToString(req.getParameter("roleId"));
		try {
			User user = getCurrUser(req);
			DiyUser diyUser = getDiyUserInfo(req, user);
			DiyUser phoneNoUser = diyUserService.getDiyUserByPhoneNo(diyUser);
			if (phoneNoUser != null && !phoneNoUser.getId().equals(diyUser.getId())) {
				resultMap.put("status", Boolean.FALSE);
				resultMap.put("msg", "当前手机号码已存在，请重新输入");
				return resultMap;
			}
			DiyUser loginNameUser = diyUserService.getDiyUserByUserName(diyUser);
			if (loginNameUser != null && !phoneNoUser.getId().equals(diyUser.getId())) {
				if (!diyUser.getId().equals(loginNameUser.getId())) {
					resultMap.put("status", Boolean.FALSE);
					resultMap.put("msg", "当前商户下用户名已存在，请重新输入");
					return resultMap;
				}
			}

			List<DiyRole> diyRoleList = diyRoleService.getDiyRoleList(new DiyRole());
			String roleIdTmp = getRoleId(diyRoleList, "门店财务"); // 门店财务的角色id
			if (roleIdTmp.equals(roleId)) {
				diyUser.setRoleId(roleId);
				DiyUser userNumber = diyUserService.getRoleByMchntCodeAndShopCode(diyUser);
				if (!StringUtil.isNullOrEmpty(userNumber)) {
					if (userNumber.getCountNumber() > 0 && !userNumber.getId().equals(diyUser.getId())) {
						resultMap.put("status", Boolean.FALSE);
						resultMap.put("msg", "当前门店下的门店财务已存在，请重新选择角色");
						return resultMap;
					}
				}
			}
			diyUserService.updateDiyUser(diyUser, roleId, user);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("status", Boolean.FALSE);
			resultMap.put("msg", "编辑用户失败，请重新操作");
			logger.error(e.getLocalizedMessage(), e);
		}
		return resultMap;
	}

	/**
	 * 删除用户 commit
	 * 
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/deleteDiyUserCommit")
	@ResponseBody
	public Map<String, Object> deleteUserCommit(HttpServletRequest req, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("status", Boolean.TRUE);
		String userId = req.getParameter("userId");
		try {
			diyUserService.deleteDiyUser(userId);
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("status", Boolean.FALSE);
			resultMap.put("msg", "删除用户失败，请重新操作");
			logger.error(e.getLocalizedMessage(), e);
		}
		return resultMap;
	}

	/**
	 * 获取商户下的所有门店
	 * 
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/intoAddMchntEshopInfGetShop")
	@ResponseBody
	public ModelMap intoAddMchntEshopInfGetShop(HttpServletRequest req, HttpServletResponse response) {
		ModelMap map = new ModelMap();
		map.addAttribute("status", Boolean.TRUE);
		String mchntCode = req.getParameter("mchntCode");
		ShopInf shopInf = new ShopInf();
		shopInf.setMchntCode(mchntCode);
		List<ShopInf> shopInfList = shopInfService.findShopInfList(shopInf);
		map.addAttribute("shopInfList", shopInfList);
		return map;
	}

	/**
	 * @Title: getUserInfo @Description: 用户表封装 @param @param
	 * req @param @return @param @throws Exception @return User 返回类型 @throws
	 */
	private DiyUser getDiyUserInfo(HttpServletRequest req, User user) throws Exception {
		DiyUser diyUser = null;
		String userId = StringUtils.nullToString(req.getParameter("userId"));
		if (!StringUtils.isNullOrEmpty(userId)) {
			diyUser = diyUserService.getDiyUserById(userId);
		} else {
			diyUser = new DiyUser();
		}
		diyUser.setUserName(StringUtils.nullToString(req.getParameter("userName")));
		String password = StringUtils.nullToString(req.getParameter("password"));
		if (!StringUtil.isNullOrEmpty(password)) {
			diyUser.setPassword(password);
		}
		diyUser.setMchntName(StringUtils.nullToString(req.getParameter("mchntName")));
		diyUser.setPhoneNo(StringUtils.nullToString(req.getParameter("phoneNo")));
		diyUser.setMchntCode(StringUtils.nullToString(req.getParameter("mchntCode")));
		diyUser.setShopCode(StringUtils.nullToString(req.getParameter("shopCode")));
		diyUser.setUpdateUser(user.getId().toString());
		return diyUser;
	}

	/**
	 * 筛选角色是总财务的角色id
	 * 
	 * @param roleList
	 * @return
	 */
	public String getRoleId(List<DiyRole> roleList, String roleName) {
		for (DiyRole diyRole : roleList) {
			if (roleName.equals(diyRole.getRoleName())) {
				roleIdTemp = diyRole.getId();
			}
		}
		return roleIdTemp;
	}
}
