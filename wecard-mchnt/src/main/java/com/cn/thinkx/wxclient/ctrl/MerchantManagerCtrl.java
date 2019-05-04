package com.cn.thinkx.wxclient.ctrl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.cn.thinkx.core.ctrl.BaseController;
import com.cn.thinkx.core.domain.ResultHtml;
import com.cn.thinkx.core.util.MessageUtil;
import com.cn.thinkx.merchant.domain.MerchantManager;
import com.cn.thinkx.merchant.service.MerchantInfService;
import com.cn.thinkx.merchant.service.MerchantManagerService;
import com.cn.thinkx.pms.base.utils.StringUtil;
import com.cn.thinkx.pms.base.utils.WechatCustomerMessageUtil;
import com.cn.thinkx.wechat.base.wxapi.domain.AccountFans;
import com.cn.thinkx.wechat.base.wxapi.domain.AccountMenuGroup;
import com.cn.thinkx.wechat.base.wxapi.process.MpAccount;
import com.cn.thinkx.wechat.base.wxapi.process.WxApiClient;
import com.cn.thinkx.wechat.base.wxapi.process.WxMemoryCacheClient;
import com.cn.thinkx.wxapi.service.BizService;
import com.cn.thinkx.wxclient.domain.WxRole;
import com.cn.thinkx.wxclient.service.WxRoleService;
import com.cn.thinkx.wxcms.service.AccountFansService;
import com.cn.thinkx.wxcms.service.AccountMenuGroupService;

/**
 * 手机微信页面
 */
@Controller
@RequestMapping("/wxclient/manager")
public class MerchantManagerCtrl extends BaseController {
	Logger logger = LoggerFactory.getLogger(MerchantManagerCtrl.class);

	@Autowired
	@Qualifier("merchantInfService")
	private MerchantInfService merchantInfService;

	@Autowired
	@Qualifier("merchantManagerService")
	private MerchantManagerService merchantManagerService;

	@Autowired
	private AccountFansService accountFansService;

	@Autowired
	private BizService bizService;

	@Autowired
	private AccountMenuGroupService accountMenuGroupService;

	@Autowired
	@Qualifier("wxRoleService")
	private WxRoleService wxRoleService;

	/**
	 * 商户管理 员工注册
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/employeeRegister")
	public ModelAndView mchtRegister(HttpServletRequest request) {
		// 拦截器已经处理了缓存,这里直接取
		String openid = WxMemoryCacheClient.getOpenid(request);
		if (StringUtil.isNullOrEmpty(openid)) {
			return unValidated(request);// 商户代表注册，未获取的openId,跳转到
		}
		ModelAndView mv = new ModelAndView("wxclient/employee/employeeRegister");

		return mv;
	}

	/**
	 * 员工注册 提交保存
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/employeeCommit", method = RequestMethod.POST)
	public @ResponseBody ResultHtml employeeCommit(HttpServletRequest request, HttpServletResponse response) {
		ResultHtml resultHtml = new ResultHtml();
		resultHtml.setStatus(false);
		try {
			// 拦截器已经处理了缓存,这里直接取
			String openid = WxMemoryCacheClient.getOpenid(request);
			if (StringUtil.isNullOrEmpty(openid)) {
				return requestOpenIdError();
			}

			String phoneNumber = StringUtil.nullToString(request.getParameter("phoneNumber"));// 手机号
			String phoneCode = StringUtil.nullToString(request.getParameter("phoneCode")); // 验证码
			String userName = StringUtil.nullToString(request.getParameter("userName")); // 邀请码
			String empNo = StringUtil.nullToString(request.getParameter("empNo")); // 邀请码

			/** step1. 验证openId **/
			if (StringUtil.isNullOrEmpty(openid)) {
				resultHtml.setMsg(MessageUtil.NO_AUTHORIZATION);// 未授权，非法访问
				return resultHtml;
			}

			/** step2. 动态口令验证 **/
			if (StringUtil.isNullOrEmpty(phoneCode)) {
				resultHtml.setMsg(MessageUtil.ERROR_MESSAGE_PHONE_CODE);// 手机号验证码
				return resultHtml;
			}
			if (StringUtil.isNullOrEmpty(phoneNumber) || !checkPhoneCode(phoneCode, request.getSession(), resultHtml)) {
				resultHtml.setStatus(false);
				return resultHtml;
			}

			MerchantManager merchantManager = merchantManagerService.getMchntManagerByPhoneNumber(phoneNumber);
			if (merchantManager != null) {
				resultHtml.setStatus(false);
				resultHtml.setMsg("当前手机号已经注册为商户管理员，请重新填写用户手机号");
				return resultHtml;
			}

			MerchantManager merchantManagerTmp = merchantManagerService.getMchntManagerTmpByPhoneNumber(phoneNumber);
			if (merchantManagerTmp == null) {
				resultHtml.setStatus(false);
				resultHtml.setMsg("当前手机号没有报备我司，暂时不能注册为管理员");
				return resultHtml;
			}
			merchantManagerTmp.setName(userName);
			merchantManagerTmp.setRemarks(empNo);
			AccountFans accountFans = accountFansService.getByOpenId(openid);
			if (accountFans == null) {
				resultHtml.setStatus(false);
				resultHtml.setMsg(MessageUtil.ERROR_MSSAGE);
				logger.error("## 员工openid[{}]注册，找不到对应的粉丝记录", openid);
				return resultHtml;
			}
			int r = merchantManagerService.insertEmployeeManager(accountFans, merchantManagerTmp); // 员工注册

			if (r > 0) {
				try {
					// 移动用户分组
					MpAccount mpAccount = WxMemoryCacheClient.getSingleMpAccount();
					AccountMenuGroup accountMenuGroup = accountMenuGroupService.getMembersGroupsId();
					if (accountMenuGroup != null) {
						bizService.updateFansGroupId(openid, String.valueOf(accountMenuGroup.getId()), mpAccount);
						MerchantManager m1 = merchantManagerService.getMerchantInsInfByOpenId(openid);
						WxRole role = wxRoleService.findWxRoleByRoleType(merchantManagerTmp.getRoleType());

						String content = "";
						if (StringUtil.isNullOrEmpty(m1.getShopName())) {
							m1.setShopName("所有门店");
							String.format(WechatCustomerMessageUtil.E_REG_MSG_PARAM2, m1.getMchntName(),
									role.getRoleName());
						} else {
							String.format(WechatCustomerMessageUtil.E_REG_MSG_PARAM3, m1.getMchntName(),
									m1.getShopName(), role.getRoleName());
						}
						WxApiClient.sendCustomTextMessage(openid, content, mpAccount); // 发送注册成功微信消息
					}
				} catch (Exception ex) {
					logger.error("## 移动用户分组异常", ex);
				}
				resultHtml.setStatus(true);
				return resultHtml;
			} else {
				resultHtml.setStatus(false);
				resultHtml.setMsg(MessageUtil.ERROR_MSSAGE); // 没查找到订阅信息
			}
		} catch (Exception ex) {
			logger.error("## 员工注册发生异常", ex);
			return errorMsg();
		}
		return resultHtml;
	}

	/**
	 * 商户管理 查看员工详情
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/viewEmployee")
	public ModelAndView viewEmployee(HttpServletRequest request) {
		// 拦截器已经处理了缓存,这里直接取
		String openid = WxMemoryCacheClient.getOpenid(request);
		if (StringUtil.isNullOrEmpty(openid)) {
			return unValidated(request);// 商户代表注册，未获取的openId,跳转到
		}
		ModelAndView mv = new ModelAndView("wxclient/employee/viewEmployee");
		MerchantManager merchantManager = merchantManagerService.getMerchantInsInfByOpenId(openid);

		if (merchantManager == null) {
			merchantManager = new MerchantManager();
		}
		if (!StringUtil.isNullOrEmpty(merchantManager.getRoleType())) {
			String[] roleTypes = merchantManager.getRoleType().split(",");
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < roleTypes.length; i++) {
				WxRole role = wxRoleService.findWxRoleByRoleType(roleTypes[i]);
				if (role != null) {
					sb.append(role.getRoleName());
					if (i < roleTypes.length - 1) {
						sb.append(",");
					}
				}
			}
			merchantManager.setRoleName(sb.toString());
		}
		mv.addObject("merchantManager", merchantManager);
		return mv;
	}
}