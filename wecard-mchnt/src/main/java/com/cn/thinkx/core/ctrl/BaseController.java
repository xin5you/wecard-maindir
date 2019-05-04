package com.cn.thinkx.core.ctrl;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.cn.thinkx.core.domain.ResultHtml;
import com.cn.thinkx.core.util.Constants;
import com.cn.thinkx.core.util.MessageUtil;
import com.cn.thinkx.pms.base.utils.DateUtil;
import com.cn.thinkx.pms.base.utils.RSAUtil;
import com.cn.thinkx.pms.base.utils.StringUtil;
import com.cn.thinkx.wxcms.WxCmsContents;

@Controller
@RequestMapping("/base")
public class BaseController {
	/****
	 * 获取openId异常
	 * @return
	 */
	protected ResultHtml requestOpenIdError(){
		ResultHtml result=new ResultHtml();
		result.setStatus(false);
		result.setMsg(MessageUtil.ERROR_OPENID_REQ_MSSAGE);
		return result;
	}
	/***错误异常提示**/
	protected ResultHtml errorMsg(){
		ResultHtml result=new ResultHtml();
		result.setStatus(false);
		result.setMsg(MessageUtil.ERROR_MSSAGE);
		return result;
	}
	/**
	 * 检查手机动态口令是否正确
	 * @param phoneCode 手机动态口令
	 * @param type 业务类型
	 */
	protected boolean checkPhoneCode(String phoneCode, HttpSession session, ResultHtml result){
		if (StringUtil.isNullOrEmpty(phoneCode)) {
			result.setStatus(Boolean.FALSE);
			result.setMsg(MessageUtil.ERROR_MESSAGE_PHONE_CODE+"不能为空");
			return false;
		}
		String sysPhoneCode = (String) session.getAttribute(WxCmsContents.SESSION_PHONECODE);
		if (sysPhoneCode == null) {// session过期
			result.setStatus(Boolean.FALSE);
			result.setMsg( "请先发送动"+MessageUtil.ERROR_MESSAGE_PHONE_CODE+"至您的手机");
			return false;
		}
		if (!sysPhoneCode.equalsIgnoreCase(phoneCode)) {
			result.setStatus(Boolean.FALSE);
			result.setMsg(MessageUtil.ERROR_MESSAGE_PHONE_CODE+"不正确，请重新输入");
			return false;
		}
		Long expireTime = (Long) session.getAttribute(WxCmsContents.SESSION_PHONECODE_TIME);
		if (DateUtil.getCurrDate().getTime() > expireTime) {
			result.setStatus(Boolean.FALSE);
			result.setMsg(MessageUtil.ERROR_MESSAGE_PHONE_CODE+"失效，请重新申请");
			return false;
		}
		return true;
	}
	
	/**验证不通过页面**/
	@RequestMapping(value = "/unvalidated")
	public ModelAndView unValidated(HttpServletRequest request){
		ModelAndView mv = new ModelAndView("wxclient/common/unvalidated");
		mv.addObject("msg",MessageUtil.NO_AUTHORIZATION);
		return mv;
	}
	
	/***粉丝关注引导页面**/
	@RequestMapping(value = "/fansTips")
	public ModelAndView fansAttentionTips(HttpServletRequest request){
		ModelAndView mv = new ModelAndView("wxclient/common/fansTips");
		mv.addObject("msg",MessageUtil.NO_AUTHORIZATION);
		return mv;
	}
	
	public void setPwdRSAKeyByopenId(HttpServletRequest request,ModelAndView mv,String openid ) throws NoSuchAlgorithmException{
		/***提现页面***/
		HashMap<String, Object> map = null;
		map = RSAUtil.getKeys();
		//生成公钥和私钥  
	    RSAPublicKey publicKey = (RSAPublicKey) map.get("public");  
	    RSAPrivateKey privateKey = (RSAPrivateKey) map.get("private");
	    //私钥保存在session中，用于解密
	    Constants.sessionMap.put(openid, privateKey);
	    //公钥信息保存在页面，用于加密
	    String publicKeyExponent = publicKey.getPublicExponent().toString(16);
	    String publicKeyModulus = publicKey.getModulus().toString(16);
	    mv.addObject("publicKeyExponent", publicKeyExponent);
	    mv.addObject("publicKeyModulus", publicKeyModulus);
	
	}
}
