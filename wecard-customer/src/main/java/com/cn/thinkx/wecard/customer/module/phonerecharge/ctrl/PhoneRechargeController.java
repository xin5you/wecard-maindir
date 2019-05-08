package com.cn.thinkx.wecard.customer.module.phonerecharge.ctrl;

import java.util.ArrayList;
import java.util.List;

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

import com.alibaba.fastjson.JSONObject;
import com.cn.thinkx.common.wecard.domain.person.PersonInf;
import com.cn.thinkx.common.wecard.domain.phoneRecharge.PhoneRechargeOrder;
import com.cn.thinkx.common.wecard.domain.phoneRecharge.PhoneRechargeShop;
import com.cn.thinkx.common.wecard.domain.user.UserInf;
import com.cn.thinkx.pms.base.utils.BaseConstants;
import com.cn.thinkx.pms.base.utils.BaseConstants.ShopUnitType;
import com.cn.thinkx.pms.base.utils.BaseConstants.phoneRechargeTransStat;
import com.cn.thinkx.pms.base.utils.NumberUtils;
import com.cn.thinkx.pms.base.utils.StringUtil;
import com.cn.thinkx.wecard.customer.module.base.ctrl.BaseController;
import com.cn.thinkx.wecard.customer.module.checkstand.vo.TransOrderReq;
import com.cn.thinkx.wecard.customer.module.customer.service.PersonInfService;
import com.cn.thinkx.wecard.customer.module.customer.service.UserInfService;
import com.cn.thinkx.wecard.customer.module.phonerecharge.service.PhoneRechargeService;
import com.cn.thinkx.wechat.base.wxapi.process.WxMemoryCacheClient;

import redis.clients.jedis.JedisCluster;

@Controller
@RequestMapping("/phoneRecharge")
public class PhoneRechargeController extends BaseController{
	 
	Logger logger = LoggerFactory.getLogger(PhoneRechargeController.class);
	
	@Autowired
	@Qualifier("userInfService")
	private UserInfService userInfService;
	
	@Autowired
	@Qualifier("personInfService")
	private PersonInfService personInfService;
	
	@Autowired
	@Qualifier("phoneRechargeService")
	private PhoneRechargeService phoneRechargeService;
	
	@Autowired
	@Qualifier("jedisCluster")
	private JedisCluster jedisCluster;

	/**
	 * 跳转手机充值页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/toPhoneRecharge")
	public ModelAndView toPhoneRecharge(HttpServletRequest request) {
		ModelAndView mv = new ModelAndView("welfaremart/cardin/cardIn");
		try {
			List<PhoneRechargeShop> allShopList = JSONObject.parseArray(jedisCluster.get(BaseConstants.PHONE_RECHARGE_ALL_GOODS), PhoneRechargeShop.class);
			List<PhoneRechargeShop> newAllShopList = new ArrayList<>();
			allShopList.stream().sorted((e1, e2) -> {
				return Double.compare(Double.parseDouble(e1.getShopFace()), Double.parseDouble(e2.getShopFace()));
			}).forEach(e -> newAllShopList.add(e));
			newAllShopList.forEach(e -> e.setResv1(ShopUnitType.findByCode(e.getResv1())));
			mv.addObject("phoneRechargeShopList", newAllShopList);
		} catch (Exception e) {
			logger.error("## 卡券集市--->跳转手机充值页面异常{}", e);
			return new ModelAndView("redirect:/common/500.html");
		}
		return mv;
	}
	
	/**
	 * 手机充值（手机号校验获取可充值信息）
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/phoneRechargeMobileValid")
	@ResponseBody
	public List<PhoneRechargeShop> phoneRechargeMobileValid(HttpServletRequest request){
		List<PhoneRechargeShop> phoneRechargeShopList = new ArrayList<PhoneRechargeShop>();
		
		String openid = WxMemoryCacheClient.getOpenid(request); 
		if (StringUtil.isNullOrEmpty(openid)) {
			logger.info("★★★★★Request phoneRecharge--->flowRechargeMobileValid get openid is [Null]★★★★★");
			return null;
		}
		PersonInf personInf = personInfService.getPersonInfByOpenId(openid);
		if (StringUtil.isNullOrEmpty(personInf)) {
			logger.error("★★★★★Request phoneRecharge--->flowRechargeMobileValid get personInf is [Null]，openID[{}]★★★★★", openid);
			return null;
		}
		String mobile = request.getParameter("mobile");
		try {
			phoneRechargeShopList = phoneRechargeService.phoneRechargeMobileValid(request);
		} catch (Exception e) {
			logger.error("## 手机充值--->查询userID[{}]手机号[{}]可充值信息异常{} ", personInf.getUserId(), mobile, e);
		}
		return phoneRechargeShopList;
	}
	
	/**
	 * 手机充值提交
	 * 
	 * @param request
	 */
	@RequestMapping(value = "/phoneRechargeCommit")
	public ModelAndView phoneRechargeCommit(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mv = new ModelAndView("welfaremart/cardin/inFail");
		mv.addObject("check", "phoneRecharge");
		String openid = WxMemoryCacheClient.getOpenid(request); 
		if (StringUtil.isNullOrEmpty(openid)) {
			logger.info("★★★★★Request phoneRecharge--->phoneRechargeCommit get openid is [Null]★★★★★");
			return new ModelAndView("redirect:/common/500.html");
		}
		PersonInf personInf = personInfService.getPersonInfByOpenId(openid);
		if (StringUtil.isNullOrEmpty(personInf)) {
			logger.info("★★★★★Request phoneRecharge--->phoneRechargeCommit get personInf is [Null]★★★★★");
			return mv;
		}
		TransOrderReq order = null;
		try {
			order = phoneRechargeService.toPhoneRechargeUnifiedOrder(request);
		} catch (Exception e) {
			logger.error("## 手机充值--->用户[{}]手机充值跳转收银台异常{}", personInf.getUserId(), e);
		}
		if (order != null) {
			mv = new ModelAndView("welfaremart/cardshow/unifiedOrder");
			mv.addObject("orderReq", order);
			return mv;
		}
		return mv;
	}
	
	/**
	 * 手机充值交易明细
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/phoneTransDetails")
	public ModelAndView phoneTransDetails(HttpServletRequest request){
		ModelAndView mv = new ModelAndView("welfaremart/cardin/phoneTransDetails");
		
		String openid = WxMemoryCacheClient.getOpenid(request);// 从缓存中获取openid
		if (StringUtil.isNullOrEmpty(openid)) {
			logger.error("## Request buyCardRecharge get openid[Null] failed");
			return super.error500(request);
		}
		/*** 用户是否已经注册薪无忧会员 **/
		UserInf user = userInfService.getUserInfByOpenId(openid);// TODO 从缓存中获取
		if (user == null) {
			mv = new ModelAndView("redirect:/customer/user/userRegister.html");
			return mv;
		}
		PhoneRechargeOrder phoneRechargeOrder = new PhoneRechargeOrder();
		phoneRechargeOrder.setUserId(user.getUserId());
		List<PhoneRechargeOrder> list = phoneRechargeService.getPhoneRechargeOrderList(phoneRechargeOrder);
		
		for (PhoneRechargeOrder phoneOrder : list) {
			phoneOrder.setPhone(NumberUtils.hiddingMobileNo(phoneOrder.getPhone()));
			phoneOrder.setTransAmt(NumberUtils.RMBCentToYuan(phoneOrder.getTransAmt()));
			phoneOrder.setTransStat(phoneRechargeTransStat.findByCode(phoneOrder.getTransStat()).getValue());
		}
		mv.addObject("transList", list);
		return mv;
	}
	
}
