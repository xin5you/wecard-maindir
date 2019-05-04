package com.cn.thinkx.oms.module.phoneRecharge.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.cn.thinkx.oms.base.controller.BaseController;
import com.cn.thinkx.oms.module.phoneRecharge.model.PhoneRechargeShop;
import com.cn.thinkx.oms.module.phoneRecharge.service.PhoneRechargeShopService;
import com.cn.thinkx.oms.module.phoneRecharge.vo.ShopUnit;
import com.cn.thinkx.oms.module.sys.model.User;
import com.cn.thinkx.oms.util.StringUtils;
import com.cn.thinkx.pms.base.utils.BaseConstants;
import com.cn.thinkx.pms.base.utils.BaseConstants.IsUsableType;
import com.cn.thinkx.pms.base.utils.BaseConstants.OperatorType;
import com.cn.thinkx.pms.base.utils.BaseConstants.ShopType;
import com.cn.thinkx.pms.base.utils.BaseConstants.ShopUnitType;
import com.cn.thinkx.pms.base.utils.BaseConstants.phoneRechargeSupplier;
import com.cn.thinkx.pms.base.utils.NumberUtils;
import com.github.pagehelper.PageInfo;

import redis.clients.jedis.JedisCluster;

@Controller
@RequestMapping(value="phone/phoneRecharge")
public class PhoneRechargeShopController extends BaseController {

	@Autowired
	private PhoneRechargeShopService phoneRechargeShopService;
	
	@Autowired
	@Qualifier("jedisCluster")
	private JedisCluster jedisCluster;
	
	/**
	 * 手机充值商品信息列表
	 * @param request
	 * @return
	 */
	@RequestMapping("/listPhoneRechargeShop")
	public ModelAndView listPhoneRechargeShop(HttpServletRequest request){
		ModelAndView mv = new ModelAndView("phoneRecharge/phoneRechargeShop/listPhoneRechargeShop");
		String operStatus=StringUtils.nullToString(request.getParameter("operStatus"));
		int startNum = parseInt(request.getParameter("pageNum"), 1);
		int pageSize = parseInt(request.getParameter("pageSize"), 10);
		String supplier =request.getParameter("supplier");
		String oper = request.getParameter("oper");
		
		PhoneRechargeShop pps = new PhoneRechargeShop();
		pps.setSupplier(supplier);
		pps.setOper(oper);
		
		PageInfo<PhoneRechargeShop> pageList = null;
		try {
			pageList = phoneRechargeShopService.getPhoneRechargeShopPage(startNum, pageSize, pps);
		} catch (Exception e) {
			logger.error(" ## 查询手机充值商品信息列表出错", e);
		}
		
		mv.addObject("OperatorTypeList", OperatorType.values());
		mv.addObject("SupplierList", phoneRechargeSupplier.values());
		mv.addObject("pageInfo", pageList);
		mv.addObject("operStatus", operStatus);
		mv.addObject("pps", pps);
		return mv;
	}
	
	
	
	@RequestMapping(value = "/intoAddPhoneRechargeShop")
	public ModelAndView intoAddPhoneRechargeShop(HttpServletRequest req, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("phoneRecharge/phoneRechargeShop/addPhoneRechargeShop");

		mv.addObject("OperatorTypeList", OperatorType.values());
		mv.addObject("SupplierList", phoneRechargeSupplier.values());
		mv.addObject("ShopTypeList", ShopType.values());
		return mv;
	}
	
	@RequestMapping(value = "/addPhoneRechargeShopCommit")
	public ModelAndView addPhoneRechargeShopCommit(HttpServletRequest req, HttpServletResponse resp) {
		ModelAndView mv = new ModelAndView("redirect:/phone/phoneRecharge/listPhoneRechargeShop.do");

		User user = getCurrUser(req);
		try {
			PhoneRechargeShop pps = getPhoneRechargeShop(req, user);

			int i = phoneRechargeShopService.insertPhoneRechargeShop(pps);
			if (i == 1) {
				setRedis();
				mv.addObject("operStatus", 1);
			}
		} catch (Exception e) {
			logger.error(" ## 跳转新增手机充值商品页面出错", e);
		}

		return mv;
	}
	
	
	@RequestMapping(value = "/intoEditPhoneRechargeShop")
	public ModelAndView intoEditPhoneRechargeShop(HttpServletRequest req, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("phoneRecharge/phoneRechargeShop/editPhoneRechargeShop");
		
		String goodsID = req.getParameter("goodsID");
		
		try {
			PhoneRechargeShop prs = phoneRechargeShopService.getPhoneRechargeShopById(goodsID);
			prs.setShopPrice(NumberUtils.RMBCentToYuan(prs.getShopPrice()));
			prs.setSupplierType(phoneRechargeSupplier.findByCode(prs.getSupplier()).getValue());
			prs.setOperType(OperatorType.findByCode(prs.getOper()));
			mv.addObject("prs", prs);
			mv.addObject("ShopTypeList", ShopType.values());
			mv.addObject("IsUsableTypeList", IsUsableType.values());
			List<ShopUnit> list = getShopUnit(prs.getShopType());
			mv.addObject("ShopUnitTypeList", list);
		} catch (Exception e) {
			logger.error(" ## 跳转编辑手机充值商品页面出错", e);
		}
		return mv;
	}
	
	
	@RequestMapping(value = "/editPhoneRechargeShopCommit")
	public ModelAndView editPhoneRechargeShopCommit(HttpServletRequest req, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("redirect:/phone/phoneRecharge/listPhoneRechargeShop.do");
	
		
		User user = getCurrUser(req);
		try {
			PhoneRechargeShop pps = getPhoneRechargeShop(req, user);

			int i = phoneRechargeShopService.updatePhoneRechargeShop(pps);
			if (i == 1) {
				setRedis();
				mv.addObject("operStatus", 2);
			}
		} catch (Exception e) {
			logger.error(" ## 编辑手机充值商品出错", e);
		}

		return mv;
	}

	@RequestMapping(value = "/intoViewPhoneRechargeShop")
	public ModelAndView intoViewPhoneRechargeShop(HttpServletRequest req,HttpServletResponse resp) {
		ModelAndView mv = new ModelAndView("phoneRecharge/phoneRechargeShop/viewPhoneRechargeShop");
		String goodsID = req.getParameter("goodsID");
		try {
			PhoneRechargeShop prs = phoneRechargeShopService.getPhoneRechargeShopById(goodsID);
			prs.setShopPrice(NumberUtils.RMBCentToYuan(prs.getShopPrice()));
			prs.setSupplierType(phoneRechargeSupplier.findByCode(prs.getSupplier()).getValue());
			prs.setOperType(OperatorType.findByCode(prs.getOper()));
			mv.addObject("prs", prs);
			mv.addObject("ShopTypeList", ShopType.values());
			mv.addObject("IsUsableTypeList", IsUsableType.values());
			List<ShopUnit> list = getShopUnit(prs.getShopType());
			mv.addObject("ShopUnitTypeList", list);
		} catch (Exception e) {
			logger.error(" ## 查询手机充值商品详情出错", e);
		}
		return mv;
	}
	
	@RequestMapping(value = "/deletePhoneRechargeShopCommit")
	@ResponseBody
	public Map<String, Object> deletePhoneRechargeShopCommit(HttpServletRequest req, HttpServletResponse response) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		resultMap.put("status", Boolean.TRUE);
		String goodsID = req.getParameter("goodsID");
		
		try {
			int i = phoneRechargeShopService.deletePhoneRechargeShop(goodsID);
			if (i == 1) {
				setRedis();
			}
		} catch (Exception e) {
			logger.error(" ## 删除手机充值商品出错", e);
			resultMap.put("status", Boolean.FALSE);
			resultMap.put("msg", "删除商品失败，请重新操作");
			logger.error(e.getLocalizedMessage(), e);
		}
		
		return resultMap;
	}
	
	@RequestMapping(value = "/getShopUnit")
	@ResponseBody
	public ModelMap getShopUnit(HttpServletRequest req, HttpServletResponse response){
		ModelMap map = new ModelMap();
		
		String shopTypeCode = req.getParameter("shopTypeCode");
		
		List<ShopUnit> list =getShopUnit(shopTypeCode);
		
		map.addAttribute("list", list);
		
		return map;
	}
	
	private PhoneRechargeShop getPhoneRechargeShop(HttpServletRequest req, User user) throws Exception {
		String goodsId = StringUtils.nullToString(req.getParameter("goodsId"));
		String supplier = StringUtils.nullToString(req.getParameter("supplier"));
		String oper = StringUtils.nullToString(req.getParameter("oper"));
		String shopNo = StringUtils.nullToString(req.getParameter("shopNo"));
		String shopFace = StringUtils.nullToString(req.getParameter("shopFace"));
		String shopPrice = StringUtils.nullToString(req.getParameter("shopPrice"));
		String isUsable = StringUtils.nullToString(req.getParameter("isUsable"));
		String shopType = StringUtils.nullToString(req.getParameter("shopType"));
		String remarks = StringUtils.nullToString(req.getParameter("remarks"));
		String resv1 = StringUtils.nullToString(req.getParameter("resv1"));

		PhoneRechargeShop pps = new PhoneRechargeShop();
		
		pps.setId(goodsId);
		pps.setSupplier(supplier);
		pps.setOper(oper);
		pps.setShopNo(shopNo);
		pps.setShopFace(shopFace);
		pps.setShopPrice(NumberUtils.RMBYuanToCent(shopPrice));
		pps.setIsUsable(isUsable);
		pps.setShopType(shopType);
		pps.setRemarks(remarks);
		pps.setResv1(resv1);

		if (user != null) {
			pps.setCreateUser(user.getId().toString());
			pps.setUpdateUser(user.getId().toString());
		}

		return pps;
	}
	
	private void setRedis(){
		PhoneRechargeShop allShop = new PhoneRechargeShop();
		allShop.setSupplier(phoneRechargeSupplier.PRS1.getCode());
		List<PhoneRechargeShop> allShopList = phoneRechargeShopService.getShopFaceByPhoneRechargeShop(allShop);
		jedisCluster.set(BaseConstants.PHONE_RECHARGE_ALL_GOODS, JSONObject.toJSONString(allShopList));
		
		PhoneRechargeShop YDShop = new PhoneRechargeShop();
		YDShop.setSupplier(phoneRechargeSupplier.PRS1.getCode());
		List<PhoneRechargeShop> YDShopList = phoneRechargeShopService.getYDShopFaceByPhoneRechargeShop(YDShop);
		jedisCluster.set(BaseConstants.PHONE_RECHARGE_YD_GOODS, JSONObject.toJSONString(YDShopList));
		
		
		PhoneRechargeShop TLShop = new PhoneRechargeShop();
		TLShop.setSupplier(phoneRechargeSupplier.PRS1.getCode());
		List<PhoneRechargeShop> TLShopList = phoneRechargeShopService.getLTShopFaceByPhoneRechargeShop(TLShop);
		jedisCluster.set(BaseConstants.PHONE_RECHARGE_LT_GOODS, JSONObject.toJSONString(TLShopList));
		
		PhoneRechargeShop DXShop = new PhoneRechargeShop();
		DXShop.setSupplier(phoneRechargeSupplier.PRS1.getCode());
		List<PhoneRechargeShop> DXShopList = phoneRechargeShopService.getDXShopFaceByPhoneRechargeShop(DXShop);
		jedisCluster.set(BaseConstants.PHONE_RECHARGE_DX_GOODS, JSONObject.toJSONString(DXShopList));
		
	}
	
	private List<ShopUnit> getShopUnit(String code){
		List<ShopUnit> list = new ArrayList<ShopUnit>();
		ShopUnit shopUnit = null;
		
		for (ShopUnitType t : ShopUnitType.values()) {
			shopUnit = new ShopUnit();
			if (t.getTypeCode().equalsIgnoreCase(code)) {
				shopUnit.setCode(t.getCode());
				shopUnit.setTypeCode(t.getTypeCode());
				shopUnit.setValue(t.getValue());
				list.add(shopUnit);
			}
		}
		return list;
	}
}
