package com.cn.thinkx.wecard.customer.module.customer.ctrl;

import com.alibaba.fastjson.JSON;
import com.cn.thinkx.beans.CtrlSystem;
import com.cn.thinkx.beans.TxnPackageBean;
import com.cn.thinkx.common.activemq.service.WechatMQProducerService;
import com.cn.thinkx.common.wecard.domain.base.ResultHtml;
import com.cn.thinkx.common.wecard.domain.detail.DetailBizInfo;
import com.cn.thinkx.common.wecard.domain.person.PersonInf;
import com.cn.thinkx.common.wecard.domain.trans.WxTransLog;
import com.cn.thinkx.common.wecard.domain.user.UserInf;
import com.cn.thinkx.common.wecard.domain.user.UserMerchantAcct;
import com.cn.thinkx.facade.bean.MchntInfQueryRequest;
import com.cn.thinkx.facade.service.HKBTxnFacade;
import com.cn.thinkx.pms.base.redis.core.JedisClusterUtils;
import com.cn.thinkx.pms.base.redis.util.SignUtil;
import com.cn.thinkx.pms.base.redis.util.*;
import com.cn.thinkx.pms.base.redis.vo.CustomerQrCodeVO;
import com.cn.thinkx.pms.base.utils.*;
import com.cn.thinkx.pms.base.utils.BaseConstants.ChannelCode;
import com.cn.thinkx.pms.base.utils.BaseConstants.TransCode;
import com.cn.thinkx.service.txn.Java2TxnBusinessFacade;
import com.cn.thinkx.wecard.customer.core.util.MessageUtil;
import com.cn.thinkx.wecard.customer.module.base.ctrl.BaseController;
import com.cn.thinkx.wecard.customer.module.base.domain.JsonView;
import com.cn.thinkx.wecard.customer.module.customer.service.*;
import com.cn.thinkx.wecard.customer.module.merchant.service.MerchantInfService;
import com.cn.thinkx.wecard.customer.module.merchant.service.TransLogService;
import com.cn.thinkx.wecard.customer.module.pub.service.CommonSerivce;
import com.cn.thinkx.wecard.customer.module.pub.service.PublicService;
import com.cn.thinkx.wecard.customer.module.wxapi.service.BizService;
import com.cn.thinkx.wecard.customer.module.wxcms.service.AccountFansService;
import com.cn.thinkx.wecard.middleware.resp.hkbtxnfacade.entity.MerchantInfoQueryITFResp;
import com.cn.thinkx.wecard.middleware.resp.hkbtxnfacade.vo.MerchantInfoQueryITFVo;
import com.cn.thinkx.wechat.base.wxapi.domain.AccountFans;
import com.cn.thinkx.wechat.base.wxapi.process.WxMemoryCacheClient;
import com.cn.thinkx.wechat.base.wxapi.util.WxConstants;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 微信会员页面
 */
@Controller
@RequestMapping("/customer/user")
public class CustomerManagerCtrl extends BaseController {
    private Logger logger = LoggerFactory.getLogger(CustomerManagerCtrl.class);

    @Autowired
    @Qualifier("publicService")
    private PublicService publicService;

    @Autowired
    private Java2TxnBusinessFacade java2TxnBusinessFacade;

    @Autowired
    private AccountFansService accountFansService;

    @Autowired
    @Qualifier("personInfService")
    private PersonInfService personInfService;

    @Autowired
    @Qualifier("userInfService")
    private UserInfService userInfService;

    @Autowired
    @Qualifier("wxTransLogService")
    private WxTransLogService wxTransLogService;

    @Autowired
    @Qualifier("ctrlSystemService")
    private CtrlSystemService ctrlSystemService;

    @Autowired
    @Qualifier("transLogService")
    private TransLogService transLogService;

    @Autowired
    @Qualifier("bizService")
    private BizService bizService;

    @Autowired
    @Qualifier("wechatMQProducerService")
    private WechatMQProducerService wechatMQProducerService;

    @Autowired
    @Qualifier("merchantInfService")
    private MerchantInfService merchantInfService;

    @Autowired
    @Qualifier("userMerchantAcctService")
    private UserMerchantAcctService userMerchantAcctService;

    @Autowired
    private HKBTxnFacade hkbTxnFacade;

    @Autowired
    private CommonSerivce commonSerivce;

    @Autowired
    @Qualifier("customerManagerService")
    private CustomerManagerService customerManagerService;

    /**
     * 钱包 付款码 Created by Pucker 2016/7/28
     **/
    @RequestMapping(value = "/qrCode")
    public ModelAndView qrCode(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("customer/wallet/qrCode");

        String openid = WxMemoryCacheClient.getOpenid(request);// 从缓存中获取openid
        mv.addObject("openid", openid);

        /*** 用户是否已经注册汇卡包会员 **/
        UserInf user = userInfService.getUserInfByOpenId(openid);// TODO 用户信息存放于redis中
        if (user == null) {
            mv = new ModelAndView("redirect:/customer/user/userRegister.html");
            return mv;
        }
        try {
            // 生成密钥的处理
            mv = getPublicPrivateKey(request, mv);
            // websocket 连接域名
            mv.addObject("wsUrl", HttpWebUtil.getMerchantWsUrl());
            // map = RSAUtil.getKeys();
            // //生成公钥和私钥
            // RSAPublicKey publicKey = (RSAPublicKey) map.get("public");
            // RSAPrivateKey privateKey = (RSAPrivateKey) map.get("private");
            // //私钥保存在session中，用于解密
            // request.getSession().setAttribute(WxConstants.RSA_PRIVATE_KEY_SESSION,
            // privateKey);
            //
            // //公钥信息保存在页面，用于加密
            // String publicKeyExponent =
            // publicKey.getPublicExponent().toString(16);
            // String publicKeyModulus = publicKey.getModulus().toString(16);
            //
            // mv.addObject("publicKeyExponent", publicKeyExponent);
            // mv.addObject("publicKeyModulus", publicKeyModulus);
            //
        } catch (Exception e) {
            logger.error("生成密钥失败", e);
            mv = new ModelAndView("common/failure");
            mv.addObject("failureMsg", "系统异常");
            return mv;
        }

        return mv;
    }

    /**
     * 钱包 定时生成付款二维码 Created by Pucker 2016/9/18
     **/
    @RequestMapping(value = "/genCustomerQrcode")
    @ResponseBody
    public Map<String, String> genCustomerQrcode(HttpServletRequest request) {
        Map<String, String> obj = new HashMap<String, String>();
        obj.put("codeValue", "");

        String openid = WxMemoryCacheClient.getOpenid(request);
        if (StringUtil.isNullOrEmpty(StringUtil.trim(openid))) {
            logger.error("## 钱包二维码生成失败：openid获取失败");
            return obj;
        }

        String payType = request.getParameter("payType");
        long currTime = DateUtil.getCurrentTimeMillis(); // 和商户端获取时间戳保持一致
        // 将用户信息封装 成对象
        CustomerQrCodeVO customerQrCodeVo = new CustomerQrCodeVO();
        customerQrCodeVo.setCurrTime(currTime);
        customerQrCodeVo.setOpenid(openid);
        customerQrCodeVo.setPayType(payType);
        // 对象转JSON
        JSONObject json = JSONObject.fromObject(customerQrCodeVo);
        // 转成String
        String jsonStr = json.toString();
        // 获取二维码auth_code数字
        String authCode = commonSerivce.findMmSsAddSeqId("40");
        try {
            // 向redis中set 当前用户auth_code 信息
            JedisClusterUtils.getInstance().set(authCode, jsonStr, 90);// 设置90秒过期
            String codeValue = authCode;
            obj.put("codeValue", codeValue);
        } catch (Exception e) {
            logger.error("## 二维码加密生成失败：", e);
        }
        return obj;
    }

    /**
     * 钱包 扫一扫 Created by Pucker 2016/8/12
     **/
    @RequestMapping(value = "/scanCode")
    public ModelAndView scanCode(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("customer/wallet/scanCode");

        String openid = WxMemoryCacheClient.getOpenid(request);// 从缓存中获取openid
        String mchntCode = request.getParameter("mchntCode");
        String shopCode = request.getParameter("shopCode");

        /*** 用户是否已经注册汇卡包会员 **/
        UserInf user = userInfService.getUserInfByOpenId(openid);
        if (user == null) {
            mv = new ModelAndView("redirect:/customer/user/userRegister.html");
            return mv;
        }

        /** openId加密 */
        try {
            String rand = RandomUtils.get8Random();
            String codeValue = DES3Util.Encrypt3DES(rand + openid, BaseKeyUtil.getEncodingAesKey());
            mv.addObject("cOpenid", codeValue);
        } catch (Exception e) {
            logger.error("二维码加密生成失败：", e);
            mv.addObject("cOpenid", "");
        }

        if (StringUtil.isNullOrEmpty(mchntCode) || StringUtil.isNullOrEmpty(shopCode)) {
            logger.info("Customer scan code failed that's because of the merchant code or shop code is null.");
            mv = new ModelAndView("common/failure");
            mv.addObject("failureMsg", "扫描二维码失败");
        } else {
            getMchntInfo(mv, mchntCode, shopCode);

            /*** 判断用户是否已经是某商户会员 **/
            String userMchntAccBal = merchantInfService.getCheckMerchantInf(mchntCode, openid);

            String accHkbMchntNo = RedisDictProperties.getInstance().getdictValueByCode("ACC_HKB_MCHNT_NO");
            UserMerchantAcct userMerchantAcct = new UserMerchantAcct();
            userMerchantAcct.setExternalId(openid);
            userMerchantAcct.setMchntCode(accHkbMchntNo);
            List<UserMerchantAcct> productlist = userMerchantAcctService.getUserMerchantAcctByUser(userMerchantAcct);


            mv.addObject("userMchntAccBal", userMchntAccBal);// 用户商户下的卡余额
            mv.addObject("accHkbAccBal", productlist.get(0).getAccBal());
        }
        return mv;
    }

    /**
     * 钱包 扫一扫,输入支付金额之后，跳转到确人支付画面
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/payConfirm", method = RequestMethod.POST)
    public ModelAndView payConfirm(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("customer/wallet/payConfirm");
        DetailBizInfo detail = new DetailBizInfo();
        String payAmountTemp = request.getParameter("payAmount");
        detail.setInsCode(request.getParameter("insCode"));
        detail.setMchntCode(request.getParameter("merchantCode"));
        detail.setMchntName(request.getParameter("mchntName"));
        detail.setShopCode(request.getParameter("shopCode"));
        detail.setShopName(request.getParameter("shopName"));
        detail.setQrCodeUrl(request.getParameter("qrCodeUrl"));
        detail.setMchntType(request.getParameter("mchntType"));
        String userMchntAccBal = request.getParameter("userMchntAccBal");
        String accHkbAccBal = request.getParameter("accHkbAccBal");
        String jfUserId = request.getParameter("jfUserId");
        String userId = request.getParameter("userId");
        String accHkbMchntNo = RedisDictProperties.getInstance().getdictValueByCode("ACC_HKB_MCHNT_NO");
        // 金额转换（元转分）
        BigDecimal b1 = new BigDecimal(payAmountTemp);
        BigDecimal b2 = new BigDecimal(100);
        String RMBYuanToCent = b1.multiply(b2).setScale(0, BigDecimal.ROUND_DOWN).toString();
        // 金额转换（分转元）
        BigDecimal b3 = new BigDecimal(RMBYuanToCent);
        BigDecimal b4 = new BigDecimal(100);
        String payAmount = b3.divide(b4).setScale(2, BigDecimal.ROUND_DOWN).toString();

        // 获取页面的key(用于加密密码)，生成密钥的处理
        try {
            mv = getPublicPrivateKey(request, mv);
        } catch (Exception e) {
            logger.error("生成密钥失败", e);
            mv = new ModelAndView("common/failure");
            mv.addObject("failureMsg", "系统异常");
            return mv;
        }
        // HashMap<String, Object> map = null;
        // try {
        // map = RSAUtil.getKeys();
        // //生成公钥和私钥
        // RSAPublicKey publicKey = (RSAPublicKey) map.get("public");
        // RSAPrivateKey privateKey = (RSAPrivateKey) map.get("private");
        // //私钥保存在session中，用于解密
        // request.getSession().setAttribute(WxConstants.RSA_PRIVATE_KEY_SESSION,
        // privateKey);
        // //公钥信息保存在页面，用于加密
        // String publicKeyExponent =
        // publicKey.getPublicExponent().toString(16);
        // String publicKeyModulus = publicKey.getModulus().toString(16);
        // mv.addObject("publicKeyExponent", publicKeyExponent);
        // mv.addObject("publicKeyModulus", publicKeyModulus);
        // } catch (NoSuchAlgorithmException e) {
        // logger.error("生成密钥失败", e);
        // mv = new ModelAndView("common/failure");
        // mv.addObject("failureMsg", "系统异常");
        // return mv;
        // }
        String brandLogo = request.getParameter("brandLogo");
        mv.addObject("brandLogo", brandLogo);
        mv.addObject("cOpenid", request.getParameter("openid"));
        mv.addObject("detail", detail);
        mv.addObject("payAmount", payAmount);
        mv.addObject("userMchntAccBal", userMchntAccBal);
        mv.addObject("accHkbAccBal", accHkbAccBal);
        mv.addObject("accHkbMchntNo", accHkbMchntNo);
        mv.addObject("jfUserId", jfUserId);
        mv.addObject("userId", userId);
        return mv;
    }

    @RequestMapping(value = "/userRegister")
    public ModelAndView userRegister(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("customer/user/userRegister");

        // HashMap<String, Object> map = null;
        String openid = WxMemoryCacheClient.getOpenid(request);// 从缓存中获取openid
        if (StringUtil.isNullOrEmpty(openid)) {
            logger.info("★★★★★Request userRegister get openid is [Null]★★★★★");
            return super.error500(request);
        }

        // 发送客服消息 通知用户注册成功
        // try{
        //
        // List<MsgNews> newsList=msgNewsDao.getMsgNewsByUserReg(); //首次关注欢迎语
        // 图文消息
        // JSONObject
        // jsonStr=WxApiClient.sendCustomerNewsMessage(openid,newsList,WxMemoryCacheClient.getSingleMpAccount());
        // String accessToken =
        // WxApiClient.getAccessToken(WxMemoryCacheClient.getSingleMpAccount());
        // String
        // mchntAcount=RedisDictProperties.getInstance().getdictValueByCode("WX_MCHNT_ACCOUNT");
        // AccessToken token =
        // WxMemoryCacheClient.getSingleAccessToken(mchntAcount);
        // MsgResponseTemplate template = new MsgResponseTemplate();
        // template.setTouser("oP2TN1cZnT3E0bxVtrxTE2Cawow0");
        // template.setTemplate_id("wzZsOkcYM2w5sxpnRvX6o1dumpucda5OIAkhrT1I__w");
        // template.setUrl("http://www.baidu.com");
        // BaseDate first = new BaseDate("恭喜你购买成功！","#173177");
        // BaseDate money = new BaseDate("39.8元","#173177");
        // BaseDate time = new BaseDate("2014年9月22日","#173177");
        // BaseDate remark = new BaseDate("欢迎再次购买！","#173177");
        // Map<String, BaseDate> data = new TreeMap<String, BaseDate>();
        // data.put("first", first);
        // data.put("money", money);
        // data.put("time", time);
        // data.put("remark", remark);
        // template.setData(data);
        // String content =
        // com.alibaba.fastjson.JSONObject.toJSONString(template);
        // JSONObject jsonStr
        // =WxApi.httpsRequest(WxApi.getSendTemplateMessageUrl(token.getAccessToken()),
        // HttpMethod.POST, content);
        // logger.info("用户注册成功发送图文消息状态----------------------->:"+jsonStr.toString());
        // }catch(Exception ex){
        // logger.error("用户注册---------->发送客服消息是吧"+ex);
        // }

        /*** 用户是否已经注册知了企服会员 **/
        UserInf user = userInfService.getUserInfByOpenId(openid);
        if (user != null) {
            mv = new ModelAndView("customer/user/userRegistered");
            return mv;
        }

        try {
            mv = getPublicPrivateKey(request, mv);
            // //生成公钥和密钥 begin
            // map = RSAUtil.getKeys();
            // //生成公钥和私钥
            // RSAPublicKey publicKey = (RSAPublicKey) map.get("public");
            // RSAPrivateKey privateKey = (RSAPrivateKey) map.get("private");
            // //私钥保存在session中，用于解密
            // request.getSession().setAttribute(WxConstants.RSA_PRIVATE_KEY_SESSION,
            // privateKey);
            // //公钥信息保存在页面，用于加密
            // String publicKeyExponent =
            // publicKey.getPublicExponent().toString(16);
            // String publicKeyModulus = publicKey.getModulus().toString(16);
            // mv.addObject("publicKeyExponent", publicKeyExponent);
            // mv.addObject("publicKeyModulus", publicKeyModulus);
            //
            // //生成公钥和密钥 end
        } catch (Exception ex) {
            logger.error("生成密钥失败", ex);
            mv = new ModelAndView("common/failure");
            mv.addObject("failureMsg", "系统异常");
        }
        return mv;
    }

    /**
     * 客户注册认证界面
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/userRegisterCommit", method = RequestMethod.POST)
    public ModelAndView userRegisterCommit(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mvSuccess = new ModelAndView("customer/user/success");
        ModelAndView mvFail = new ModelAndView("customer/user/fail");

        JsonView resp = new JsonView();
        resp.setCode(BaseConstants.WX_EXCEPTION_CODE);
        resp.setInfo(BaseConstants.WX_EXCEPTION_INFO);

        String phoneNumber = StringUtil.nullToString(request.getParameter("phoneNumber"));// 手机号
        String phoneCode = StringUtil.nullToString(request.getParameter("phoneCode")); // 验证码
        // String loginPwdConfirm = StringUtil.nullToString(request.getParameter("passwordConfirm"));

        String openid = WxMemoryCacheClient.getOpenid(request);
        if (StringUtil.isNullOrEmpty(openid)) {
            mvFail.addObject("resp", resp);
            return mvFail;
        }
        /** step1. 动态口令验证 **/
        if (StringUtil.isNullOrEmpty(phoneCode)) {
            resp.setCode(BaseConstants.RESPONSE_EXCEPTION_CODE);
            resp.setInfo(MessageUtil.ERROR_MESSAGE_PHONE_CODE);// 手机号验证码
            mvFail.addObject("resp", resp);
            return mvFail;
        }
        ResultHtml resultHtml = new ResultHtml();
        if (StringUtil.isNullOrEmpty(phoneNumber) || !checkPhoneCode(phoneCode, request.getSession(), resultHtml)) {
            resp.setCode(BaseConstants.RESPONSE_EXCEPTION_CODE);
            resp.setInfo(resultHtml.getMsg());
            mvFail.addObject("resp", resp);
            return mvFail;
        }
        // /**step2. 交易密码校验**/
        // if(StringUtils.isEmpty(loginPwd) || !loginPwd.equals(loginPwdConfirm)) {
        // resp.setCode("990");
        // resp.setInfo("您的确认密码和交易密码不匹配,请重新输入");
        // return resp;
        // }

        String loginPwd = StringUtil.nullToString(request.getParameter("userpwd")); // 交易密码
        // 查找当前手机号,已经在当前渠道注册
        PersonInf personInf = personInfService.getPersonInfByPhoneAndChnl(phoneNumber, ChannelCode.CHANNEL1.toString());
        if (personInf != null) {
            resp.setCode(BaseConstants.RESPONSE_EXCEPTION_CODE);
            resp.setInfo("当前手机号已经注册");
            mvFail.addObject("resp", resp);
            return mvFail;
        }

        // 根据模和私钥指数获取私钥
        RSAPrivateKey privateKey = (RSAPrivateKey) request.getSession().getAttribute(WxConstants.RSA_PRIVATE_KEY_SESSION);
        String password = "";
        String userId = "";
        try {
            password = RSAUtil.decryptByPrivateKey(loginPwd, privateKey);
            // insert user person 获取用户ID
            userId = personInfService.addPersonInfRegister(openid, phoneNumber);
        } catch (Exception ex) {
            logger.error("## 用户[{}]注册失败", phoneNumber, ex);
            resp.setCode(BaseConstants.RESPONSE_EXCEPTION_CODE);
            resp.setInfo(MessageUtil.ERROR_MSSAGE);
            mvFail.addObject("resp", resp);
            return mvFail;
        }
        try {
            boolean regReturn = customerManagerService.userRegisterCommit(request, openid, resp, mvSuccess, userId,
                    password, mvFail);
            if (!regReturn)
                return mvFail;
        } catch (Exception e) {
            logger.error("## 用户注册确认异常", e);
        }
        return mvSuccess;
    }

    /**
     * 用户信息设置
     **/
    @RequestMapping(value = "/userSettings")
    public ModelAndView userSettings(HttpServletRequest request) {

        ModelAndView mv = new ModelAndView("customer/user/userSettings");
        // HashMap<String, Object> map = null;

        try {
            // 拦截器已经处理了缓存,这里直接取
            String openid = WxMemoryCacheClient.getOpenid(request);
            // 生成公钥和密钥
            mv = getPublicPrivateKey(request, mv);
            // //生成公钥和密钥 begin
            // map = RSAUtil.getKeys();
            // //生成公钥和私钥
            // RSAPublicKey publicKey = (RSAPublicKey) map.get("public");
            // RSAPrivateKey privateKey = (RSAPrivateKey) map.get("private");
            // //私钥保存在session中，用于解密
            // request.getSession().setAttribute(WxConstants.RSA_PRIVATE_KEY_SESSION,
            // privateKey);
            //
            // //公钥信息保存在页面，用于加密
            // String publicKeyExponent =
            // publicKey.getPublicExponent().toString(16);
            // String publicKeyModulus = publicKey.getModulus().toString(16);
            // mv.addObject("publicKeyExponent", publicKeyExponent);
            // mv.addObject("publicKeyModulus", publicKeyModulus);
            // //生成公钥和密钥 end

            AccountFans accountFans = accountFansService.getByOpenId(openid);
            if (accountFans != null) {
                mv.addObject("accountFans", accountFans);
                String phoneNo = personInfService.getPhoneNumberByOpenId(openid); // 获取手机号码
                if (StringUtils.isNotEmpty(phoneNo)) {
                    mv.addObject("phoneNo", StringUtil.getPhoneNumberFormat(phoneNo));
                } else {
                    UserInf user = userInfService.getUserInfByOpenId(openid);
                    if (user == null) {
                        mv = new ModelAndView("redirect:/customer/user/userRegister.html");
                        return mv;
                    }
                }
            } else {
                return fansAttentionTips(request);
            }
        } catch (Exception ex) {
            logger.error("生成密钥失败", ex);
            mv = new ModelAndView("common/failure");
            mv.addObject("failureMsg", "系统异常");
        }
        return mv;
    }

    /**
     * 客户设置提交
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/userPwdResCommit", method = RequestMethod.POST)
    public ModelAndView userSettingsCommit(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mvSuccess = new ModelAndView("customer/user/success");
        ModelAndView mvFail = new ModelAndView("customer/user/fail");
        JsonView resp = new JsonView();
        String phoneCode = StringUtil.nullToString(request.getParameter("phoneCode")); // 验证码
        String password = StringUtil.nullToString(request.getParameter("userpwd")); // 交易密码
        // String
        // passwordConfirm=StringUtil.nullToString(request.getParameter("passwordConfirm"));
        // //二次确认密码
        try {
            // 拦截器已经处理了缓存,这里直接取
            String openid = WxMemoryCacheClient.getOpenid(request);
            if (StringUtil.isNullOrEmpty(openid)) {
                resp.setCode("990");
                resp.setInfo("请从微信平台重新访问");
                mvFail.addObject("resp", resp);
                return mvFail;
            }

            /** step1. 动态口令验证 **/
            String phoneNumber = personInfService.getPhoneNumberByOpenId(openid); // 获取手机号码//手机号
            if (StringUtil.isNullOrEmpty(phoneCode)) {
                resp.setCode(BaseConstants.RESPONSE_EXCEPTION_CODE);
                resp.setInfo(MessageUtil.ERROR_MESSAGE_PHONE_CODE);// 手机号验证码
                mvFail.addObject("resp", resp);
                return mvFail;
            }
            ResultHtml resultHtml = new ResultHtml();
            if (StringUtil.isNullOrEmpty(phoneNumber) || !checkPhoneCode(phoneCode, request.getSession(), resultHtml)) {
                resp.setCode(BaseConstants.RESPONSE_EXCEPTION_CODE);
                resp.setInfo(resultHtml.getMsg());
                mvFail.addObject("resp", resp);
                return mvFail;
            }
            /** step2. 交易密码校验 **/
            // if(StringUtils.isEmpty(password) ||
            // !password.equals(passwordConfirm)){
            // resp.setCode(BaseConstants.RESPONSE_EXCEPTION_CODE);
            // resp.setInfo("您的交易密码和二次确认密码不匹配,请重新输入");
            // mvFail.addObject("resp", resp);
            // return mvFail;
            // }
            // 根据模和私钥指数获取私钥
            RSAPrivateKey privateKey = (RSAPrivateKey) request.getSession().getAttribute(WxConstants.RSA_PRIVATE_KEY_SESSION);
            password = RSAUtil.decryptByPrivateKey(password, privateKey);

            if (StringUtils.isNotEmpty(openid)) { // 密码设置
                CtrlSystem cs = ctrlSystemService.getCtrlSystem();// 得到日切信息
                WxTransLog log = new WxTransLog();
                if (cs != null) {
                    if (BaseConstants.TRANS_FLAG_YES.equals(cs.getTransFlag())) {// 日切状态为允许时，插入微信端流水
                        String id = wxTransLogService.getPrimaryKey();
                        try {
                            log.setWxPrimaryKey(id);
                            log.setTableNum(cs.getCurLogNum());// 得到当前可以进行操作的流水表号
                            log.setSettleDate(cs.getSettleDate());// 交易日期
                            log.setTransId(TransCode.CW81.getCode());// 密码重置 0: 同步 1:异步
                            log.setTransSt(0);// 插入时为0，报文返回时更新为1
                            log.setOperatorOpenId(openid);
                            int i = wxTransLogService.insertWxTransLog(log);// 插入流水记录
                            if (i != 1) {
                                resp.setCode("992");
                                logger.error("## 密码重置 用户[{}]微信端插入流水记录数量不为1", openid);
                                mvFail.addObject("resp", resp);
                                return mvFail;
                            }
                        } catch (Exception ex) {
                            logger.info("## 密码重置 用户[{}] 微信端插入流水异常", openid, ex);
                            resp.setCode(BaseConstants.RESPONSE_EXCEPTION_CODE);
                            resp.setInfo(MessageUtil.ERROR_MSSAGE);
                            mvFail.addObject("resp", resp);
                            return mvFail;
                        }

                        TxnPackageBean txnBean = new TxnPackageBean();
                        Date currDate = DateUtil.getCurrDate();
                        txnBean.setSwtFlowNo(log.getWxPrimaryKey()); // 接口平台交易流水号
                        txnBean.setTxnType(log.getTransId() + "0");// 密码重置 0: 同步
                        // 1:异步
                        txnBean.setSwtTxnDate(DateUtil.getStringFromDate(currDate, DateUtil.FORMAT_YYYYMMDD));
                        txnBean.setSwtTxnTime(DateUtil.getStringFromDate(currDate, DateUtil.FORMAT_HHMMSS));
                        txnBean.setSwtSettleDate(log.getSettleDate());
                        txnBean.setChannel(ChannelCode.CHANNEL1.toString()); // 商户开户、客户开户、密码重置、消费(从微信公众号发起)
                        txnBean.setCardNo("U" + openid); // U+UserName
                        txnBean.setAccType(BaseConstants.PWDAcctType.CUSTOMER_PWD_100.getCode()); // 账户类型
                        txnBean.setPinTxn(DES3Util.Encrypt3DES(password, BaseKeyUtil.getEncodingAesKey()));
                        txnBean.setTimestamp(System.currentTimeMillis());// 时间戳
                        /*** 生成签名 ***/
                        String signature = SignatureUtil.getTxnBeanSignature(txnBean.getSwtSettleDate(),
                                txnBean.getSwtFlowNo(), txnBean.getIssChannel(), txnBean.getCardNo(),
                                txnBean.getTxnAmount(), txnBean.getPinTxn(), txnBean.getTimestamp());
                        txnBean.setSignature(signature);
                        // 远程调用重置密码接口
                        try {
                            String json = java2TxnBusinessFacade.customerPasswordResetITF(txnBean);
                            logger.info("密码重置 用户[{}] 远程调用重置密码接口返回[{}]", openid, json);
                            resp = JSON.parseObject(json, JsonView.class);
                            if (resp == null) {
                                json = java2TxnBusinessFacade.transExceptionQueryITF(log.getWxPrimaryKey());
                                resp = JSON.parseObject(json, JsonView.class);
                            }
                        } catch (Exception ex) {
                            logger.error("## 密码重置 用户[{}] 远程调用重置密码接口异常", openid, ex);
                            resp.setCode(BaseConstants.RESPONSE_EXCEPTION_CODE);
                            resp.setInfo(MessageUtil.ERROR_MSSAGE);
                            mvFail.addObject("resp", resp);
                            return mvFail;
                        }

                        try {
                            log.setTransSt(1);// 插入时为0，报文返回时更新为1
                            log.setRespCode(resp.getCode());
                            wxTransLogService.updateWxTransLog(log.getTableNum(), log.getWxPrimaryKey(), resp.getCode(), "");
                            if (!BaseConstants.TXN_TRANS_RESP_SUCCESS.equals(resp.getCode())) {
                                logger.error("## 密码重置 用户[{}] 远程调用充值密码接口返回[{}]", openid, resp.getInfo());
                                mvFail.addObject("resp", resp);
                                return mvFail;
                            }
                        } catch (Exception ex) {
                            logger.error("## 密码重置 用户[{}] 修改微信端流水异常：", openid, ex);
                        }
                    } else {
                        resp.setCode(BaseConstants.RESPONSE_EXCEPTION_CODE);
                        resp.setInfo(MessageUtil.ERROR_MSSAGE);
                        logger.error("## 密码重置 用户[{}] 日切信息交易允许状态：日切中", openid);
                        mvFail.addObject("resp", resp);
                        return mvFail;
                    }

                }
            }
        } catch (Exception ex) {
            resp.setCode(BaseConstants.RESPONSE_EXCEPTION_CODE);
            resp.setInfo(MessageUtil.ERROR_MSSAGE);
            mvFail.addObject("resp", resp);
            return mvFail;
        }
        mvSuccess.addObject("resp", resp);
        return mvSuccess;
    }

    /*
     * 公钥和私钥 的处理
     */
    public ModelAndView getPublicPrivateKey(HttpServletRequest request, ModelAndView mv) {
        HashMap<String, Object> map = null;
        try {
            map = RSAUtil.getKeys();
            // 生成公钥和私钥
            RSAPublicKey publicKey = (RSAPublicKey) map.get("public");
            RSAPrivateKey privateKey = (RSAPrivateKey) map.get("private");
            // 私钥保存在session中，用于解密
            request.getSession().setAttribute(WxConstants.RSA_PRIVATE_KEY_SESSION, privateKey);

            // 公钥信息保存在页面，用于加密
            String publicKeyExponent = publicKey.getPublicExponent().toString(16);
            String publicKeyModulus = publicKey.getModulus().toString(16);

            mv.addObject("publicKeyExponent", publicKeyExponent);
            mv.addObject("publicKeyModulus", publicKeyModulus);
        } catch (Exception e) {
            logger.error("生成密钥失败", e);
            mv = new ModelAndView("common/failure");
            mv.addObject("failureMsg", "系统异常");
        }
        return mv;
    }

    public void getMchntInfo(ModelAndView mv, String mchntCode, String shopCode) {
        DetailBizInfo detail = new DetailBizInfo();
        if (!"null".equals(mchntCode) && !StringUtil.isNullOrEmpty(mchntCode))
            detail.setMchntCode(mchntCode);
        if (!"null".equals(shopCode) && !StringUtil.isNullOrEmpty(shopCode))
            detail.setShopCode(shopCode);
        detail = publicService.getDetailBizInfo(detail);
        if (detail == null) {
            mv.addObject("detail", new DetailBizInfo());
        }

        // 查询商户二维码信息
        MerchantInfoQueryITFResp merchantInfo = new MerchantInfoQueryITFResp(); // 商户门店信息

        long timestamp = System.currentTimeMillis();// 时间戳
        MchntInfQueryRequest baseTxnReq = new MchntInfQueryRequest();

        try {
            baseTxnReq.setInnerMerchantNo(mchntCode);
            baseTxnReq.setTimestamp(timestamp);
            baseTxnReq.setSign(SignUtil.genSign(baseTxnReq));

            String jsonStr = hkbTxnFacade.merchantInfoQueryITF(baseTxnReq);
            merchantInfo = JSON.parseObject(jsonStr, MerchantInfoQueryITFResp.class);

            MerchantInfoQueryITFVo mchntInfo = null;
            if (merchantInfo != null && BaseConstants.TXN_TRANS_RESP_SUCCESS.equals(merchantInfo.getCode())) {
                if (merchantInfo.getMerchantInfo() != null) {
                    mchntInfo = merchantInfo.getMerchantInfo();
                }
            }
            if (mchntInfo == null) {
                mchntInfo = new MerchantInfoQueryITFVo();
            }
            detail.setBrandLogo(mchntInfo.getBrandLogo());
            detail.setMchntType(mchntInfo.getMchntType());
            mv.addObject("detail", detail);
        } catch (Exception e) {
            logger.error("钱包 扫一扫	 查询商户信息失败--->", e);
        }
    }

}
