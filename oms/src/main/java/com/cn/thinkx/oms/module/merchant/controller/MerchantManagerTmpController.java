package com.cn.thinkx.oms.module.merchant.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cn.thinkx.oms.base.controller.BaseController;
import com.cn.thinkx.oms.module.merchant.model.MerchantInf;
import com.cn.thinkx.oms.module.merchant.model.MerchantManagerTmp;
import com.cn.thinkx.oms.module.merchant.model.ShopInf;
import com.cn.thinkx.oms.module.merchant.service.MerchantInfService;
import com.cn.thinkx.oms.module.merchant.service.MerchantManagerTmpService;
import com.cn.thinkx.oms.module.merchant.service.ShopInfService;
import com.cn.thinkx.oms.module.sys.model.User;
import com.cn.thinkx.oms.util.StringUtils;
import com.cn.thinkx.pms.base.utils.BaseConstants;
import com.github.pagehelper.PageInfo;

@Controller
@RequestMapping(value = "merchant/managerTmp")
public class MerchantManagerTmpController extends BaseController {

	Logger logger = LoggerFactory.getLogger(MerchantManagerTmpController.class);

	@Autowired
	@Qualifier("merchantInfService")
	private MerchantInfService merchantInfService;

	@Autowired
	@Qualifier("merchantManagerTmpService")
	private MerchantManagerTmpService merchantManagerTmpService;

	@Autowired
	@Qualifier("shopInfService")
	private ShopInfService shopInfService;

	/**
	 * 商户员工列（临时表）列表查询
	 * 
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/listMerchantManagerTmp")
	public ModelAndView listMerchantManagerTmp(HttpServletRequest req, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("merchant/merchantManagerTmp/listMerchantManagerTmp");
		String operStatus = StringUtils.nullToString(req.getParameter("operStatus"));
		PageInfo<MerchantManagerTmp> pageList = null;
		MerchantManagerTmp merchantManagerTmp = this.getMerchantManagerTmpInfo(req, null);
		try {
			int startNum = parseInt(req.getParameter("pageNum"), 1);
			int pageSize = parseInt(req.getParameter("pageSize"), 10);
			pageList = merchantManagerTmpService.getMerchantManagerTmpPage(startNum, pageSize, merchantManagerTmp);
		} catch (Exception e) {
			logger.error("查询列表信息出错", e);
		}
		mv.addObject("pageInfo", pageList);
		mv.addObject("operStatus", operStatus);
		mv.addObject("merchantManagerTmp", merchantManagerTmp);
		return mv;
	}

	@RequestMapping(value = "/intoAddMerchantManagerTmp")
	public ModelAndView intoAddMerchantManagerTmp(HttpServletRequest req, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("merchant/merchantManagerTmp/addMerchantManagerTmp");
		List<MerchantInf> mchntList = merchantInfService.getMerchantInfList(null);
		mv.addObject("mchntList", mchntList);
		return mv;
	}

	/**
	 * 商户添加提交
	 * 
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/addMerchantManagerTmpCommit")
	@ResponseBody
	public Map<String, Object> addMerchantManagerTmpCommit(HttpServletRequest req, HttpServletResponse resp) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("status", Boolean.TRUE);
		User user = getCurrUser(req);
		try {
			MerchantManagerTmp merchantManagerTmp = this.getMerchantManagerTmpInfo(req, user);
			
			if(!StringUtils.isNullOrEmpty(merchantManagerTmp.getShopId()) && (merchantManagerTmp.getRoleType().contains(BaseConstants.RoleNameEnum.BOSS_ROLE_MCHANT.getRoleType())  || merchantManagerTmp.getRoleType().contains(BaseConstants.RoleNameEnum.FINANCIAL_RORE_MCHANT.getRoleType()))){
				resultMap.put("status", Boolean.FALSE);
				resultMap.put("msg", "商户管理员中老板角色和商户财务角色不能属于门店，请重新分配");
				return resultMap;
			}
			
			MerchantManagerTmp m1 = merchantManagerTmpService
					.getMchntManagerTmpByPhoneNumber(merchantManagerTmp.getPhoneNumber());
			if (m1 == null) {
				merchantManagerTmp.setCreateUser(user.getId().toString());
				merchantManagerTmpService.insertMerchantManagerTmp(merchantManagerTmp);
			} else {
				resultMap.put("status", Boolean.FALSE);
				resultMap.put("msg", "当前手机号已经被注册，请重新输入手机号");
				return resultMap;
			}
		} catch (Exception e) {
			resultMap.put("status", Boolean.FALSE);
			resultMap.put("msg", "新增员工失败，请重新输入");
			logger.error(e.getLocalizedMessage(), e);
		}
		return resultMap;
	}

	/**
	 * 商户员工临时表编辑页面
	 * 
	 * @param req
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getShopInfListByMchntId")
	@ResponseBody
	public Map<String, Object> getShopInfListByMchntId(HttpServletRequest req, HttpServletResponse response)
			throws Exception {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("status", Boolean.TRUE);
		String mchntId = req.getParameter("mchntId");
		ShopInf shopInf = new ShopInf();
		shopInf.setMchntId(mchntId);
		List<ShopInf> shopInfList = shopInfService.findShopInfList(shopInf);
		resultMap.put("shopInfList", shopInfList);
		return resultMap;
	}

	/**
	 * 商户员工临时表编辑页面
	 * 
	 * @param req
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/intoEditMerchantManagerTmp")
	public ModelAndView intoEditMerchantManagerTmp(HttpServletRequest req, HttpServletResponse response)
			throws Exception {
		ModelAndView mv = new ModelAndView("merchant/merchantManagerTmp/editMerchantManagerTmp");
		String mangerId = req.getParameter("mangerId");

		MerchantManagerTmp merchantManagerTmp = merchantManagerTmpService.getMerchantManagerTmpById(mangerId);
		mv.addObject("merchantManagerTmp", merchantManagerTmp);

		List<MerchantInf> mchntList = merchantInfService.getMerchantInfList(null);
		mv.addObject("mchntList", mchntList);

		ShopInf shopInf = new ShopInf();
		shopInf.setMchntId(merchantManagerTmp.getMchntId());
		List<ShopInf> shopInfList = shopInfService.findShopInfList(shopInf);
		mv.addObject("shopInfList", shopInfList);
		return mv;
	}

	/**
	 * 商户员工临时表编辑 提交
	 * 
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/editMerchantManagerTmpCommit")
	@ResponseBody
	public Map<String, Object> editMerchantManagerTmpCommit(HttpServletRequest req, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("status", Boolean.TRUE);
		User user = getCurrUser(req);
		try {
			MerchantManagerTmp merchantManagerTmp = this.getMerchantManagerTmpInfo(req, user);
			
			if(!StringUtils.isNullOrEmpty(merchantManagerTmp.getShopId()) && (merchantManagerTmp.getRoleType().contains(BaseConstants.RoleNameEnum.BOSS_ROLE_MCHANT.getRoleType())  || merchantManagerTmp.getRoleType().contains(BaseConstants.RoleNameEnum.FINANCIAL_RORE_MCHANT.getRoleType()))){
				resultMap.put("status", Boolean.FALSE);
				resultMap.put("msg", "商户管理员中老板角色和商户财务角色不能属于门店，请重新分配");
				return resultMap;
			}
			
			MerchantManagerTmp m1 = merchantManagerTmpService
					.getMchntManagerTmpByPhoneNumber(merchantManagerTmp.getPhoneNumber());
			if (m1 != null) {
				if (!merchantManagerTmp.getMangerId().equals(m1.getMangerId())) {
					resultMap.put("status", Boolean.FALSE);
					resultMap.put("msg", "当前手机号已经被注册，请重新输入手机号");
					return resultMap;
				}
			}
			merchantManagerTmpService.updateMerchantManagerTmp(merchantManagerTmp);
		} catch (Exception e) {
			resultMap.put("status", Boolean.FALSE);
			resultMap.put("msg", "新增员工失败，请重新输入");
			logger.error(e.getLocalizedMessage(), e);
		}
		return resultMap;
	}

	/**
	 * 删除商户员工临时表 commit
	 * 
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/deleteMerchantManagerTmpCommit")
	@ResponseBody
	public Map<String, Object> deleteMerchantManagerTmpCommit(HttpServletRequest req, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("status", Boolean.TRUE);
		String mangerId = req.getParameter("mangerId");
		try {
			merchantManagerTmpService.deleteMerchantManagerTmp(mangerId);
		} catch (Exception e) {
			resultMap.put("status", Boolean.FALSE);
			resultMap.put("msg", "删除商户失败，请重新操作");
			logger.error(e.getLocalizedMessage(), e);
		}
		return resultMap;
	}

	/**
	 * @Title: getMerchantManagerTmpInfo @Description: 商户员工临时表封装 @param @param
	 * req @param @return @param @throws Exception @return MerchantManagerTmp
	 * 返回类型 @throws
	 */
	private MerchantManagerTmp getMerchantManagerTmpInfo(HttpServletRequest req, User user) {

		MerchantManagerTmp merchantManagerTmp = null;
		String mangerId = StringUtils.nullToString(req.getParameter("mangerId"));
		if (!StringUtils.isNullOrEmpty(mangerId)) {
			merchantManagerTmp = merchantManagerTmpService.getMerchantManagerTmpById(mangerId);
		} else {
			merchantManagerTmp = new MerchantManagerTmp();
		}
		merchantManagerTmp.setMchntId(StringUtils.nullToString(req.getParameter("mchntId")));
		merchantManagerTmp.setShopId(StringUtils.nullToString(req.getParameter("shopId")));
		merchantManagerTmp.setRoleType(StringUtils.nullToString(req.getParameter("roleType")));
		merchantManagerTmp.setName(StringUtils.nullToString(req.getParameter("name")));
		merchantManagerTmp.setPhoneNumber(StringUtils.nullToString(req.getParameter("phoneNumber")));
		merchantManagerTmp.setMchntName(StringUtils.nullToString(req.getParameter("mchntName")));
		merchantManagerTmp.setShopName(StringUtils.nullToString(req.getParameter("shopName")));
		merchantManagerTmp.setRemarks(StringUtils.nullToString(req.getParameter("remarks")));
		merchantManagerTmp.setRoleType(StringUtils.nullToString(req.getParameter("roleType")));

		if (user != null) {
			merchantManagerTmp.setCreateUser(user.getId().toString());
			merchantManagerTmp.setUpdateUser(user.getId().toString());
		}
		return merchantManagerTmp;
	}
}
