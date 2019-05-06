package com.cn.thinkx.pub.ctrl;

import com.cn.thinkx.core.ctrl.BaseController;
import com.cn.thinkx.core.domain.ResultHtml;
import com.cn.thinkx.core.util.Constants;
import com.cn.thinkx.customer.service.PersonInfService;
import com.cn.thinkx.merchant.domain.MerchantManager;
import com.cn.thinkx.merchant.service.MerchantManagerService;
import com.cn.thinkx.pms.base.redis.util.RedisDictProperties;
import com.cn.thinkx.pms.base.service.MessageService;
import com.cn.thinkx.pms.base.utils.BaseConstants.SendMsgTypeEnum;
import com.cn.thinkx.pms.base.utils.DateUtil;
import com.cn.thinkx.pms.base.utils.NumberUtils;
import com.cn.thinkx.pms.base.utils.RandomUtils;
import com.cn.thinkx.pms.base.utils.StringUtil;
import com.cn.thinkx.wechat.base.wxapi.process.WxMemoryCacheClient;
import com.cn.thinkx.wxcms.WxCmsContents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Calendar;
import java.util.Date;


@Controller
@RequestMapping(value = "/pub")
public class CommonController extends BaseController {

    Logger logger = LoggerFactory.getLogger(CommonController.class);


    @Autowired
    @Qualifier("merchantManagerService")
    private MerchantManagerService merchantManagerService;


    @Autowired
    @Qualifier("personInfService")
    private PersonInfService personInfService;


    @Autowired
    @Qualifier("messageService")
    private MessageService messageService;


    /**
     * 发送消息目标
     *
     * @param phoneNumber
     * @param session
     * @return
     */
    private ResultHtml getResultMap(String phoneNumber, String bizName, HttpSession session) {
        ResultHtml resultMap = new ResultHtml();
        int expireMinutes = NumberUtils.parseInt(RedisDictProperties.getInstance().getdictValueByCode("SMS_EXPIRE_TIME"));
        String phoneCode = RandomUtils.getRandomNumbernStr(6);
		/*boolean sendStatus = messageService.sendMessage(phoneNumber, "【知了企服】验证码：" + phoneCode + 
				"（有效期" + expireMinutes + "分钟）您正在操作<" + bizName + ">业务，切勿告知他人！");*/
        String templateCode = "";
        if (SendMsgTypeEnum.msg_01.getName().equals(bizName)) {
            templateCode = RedisDictProperties.getInstance().getdictValueByCode("ALIYUN_MSM_TEMPLATE_CODE_REGISTER");
        } else if (SendMsgTypeEnum.msg_02.getName().equals(bizName)) {
            templateCode = RedisDictProperties.getInstance().getdictValueByCode("ALIYUN_MSM_TEMPLATE_CODE_PWDRESET");
        } else if (SendMsgTypeEnum.msg_03.getName().equals(bizName)) {

        } else if (SendMsgTypeEnum.msg_04.getName().equals(bizName)) {
            templateCode = RedisDictProperties.getInstance().getdictValueByCode("ALIYUN_MSM_TEMPLATE_CODE_CARDRESELL");
        } else if (SendMsgTypeEnum.msg_05.getName().equals(bizName)) {
            templateCode = RedisDictProperties.getInstance().getdictValueByCode("ALIYUN_MSM_TEMPLATE_CODE_ADDBANKCARD");
        }
        String templateParam = "{\"code\":\"" + phoneCode + "\"}";
        boolean sendStatus = messageService.sendMessage(phoneNumber, templateCode, templateParam);
        if (sendStatus) {
            // 手机动态码
            session.setAttribute(WxCmsContents.SESSION_PHONECODE, phoneCode);

            Date expireDate = DateUtil.addDate(DateUtil.getCurrDate(), Calendar.MINUTE, expireMinutes);
            session.setAttribute(WxCmsContents.SESSION_PHONECODE_TIME, expireDate.getTime());
            logger.info("phoneNumber===" + phoneNumber + ",phoneCode= " + phoneCode + " expire time is " +
                    DateUtil.getDateTime("yyyy-MM-dd HH:mm:ss", expireDate));
            resultMap.setStatus(true);
        } else {
            resultMap.setStatus(false);
            resultMap.setMsg("短信发送失败");
        }
        return resultMap;
    }

    /***
     * 发送短信
     * @param request
     * @return
     */
    @RequestMapping("sendPhoneSMS")
    @ResponseBody
    public ResultHtml sendPhoneSMS(HttpServletRequest request) {
        ResultHtml resultMap = new ResultHtml();
        String bindingPhone = request.getParameter("phoneNumber");
        String bizCode = request.getParameter("bizCode");
        try {

            HttpSession session = request.getSession();
            if (StringUtil.isNotEmpty(bindingPhone)) {
                return getResultMap(bindingPhone, Constants.SendMsgTypeEnum.findByCode(bizCode).getName(), session);
            } else {
                resultMap.setStatus(false);
                resultMap.setMsg("请重新输入手机号码");
                return resultMap;
            }
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage(), e);

        }
        return resultMap;
    }


    /***
     * 客户会员发送短信
     * @param request
     * @return
     */
    @RequestMapping("sendUserPhoneSMS")
    @ResponseBody
    public ResultHtml sendUserPhoneSMS(HttpServletRequest request) {
        ResultHtml resultMap = new ResultHtml();
        String openid = WxMemoryCacheClient.getOpenid(request);
        String bizCode = request.getParameter("bizCode");
        try {
            if (StringUtil.isNullOrEmpty(openid)) {
                resultMap.setStatus(false);
                resultMap.setMsg("请从微信菜单重新进入页面");
                return resultMap;
            }
            String bindingPhone = personInfService.getPhoneNumberByOpenId(openid); //获取手机号码
            if (bindingPhone == null || StringUtil.isNullOrEmpty(bindingPhone)) {
                resultMap.setStatus(false);
                resultMap.setMsg("您不是管理员，不能访问该页面");
                return resultMap;
            }
            HttpSession session = request.getSession();
            if (StringUtil.isNotEmpty(bindingPhone)) {
                return getResultMap(bindingPhone, Constants.SendMsgTypeEnum.findByCode(bizCode).getName(), session);
            } else {
                resultMap.setStatus(false);
                resultMap.setMsg("请重新输入手机号码");
                return resultMap;
            }
        } catch (Exception e) {
            logger.error(e.getLocalizedMessage(), e);

        }
        return resultMap;
    }


    /***
     * 商户发送短信
     * @param request
     * @return
     */
    @RequestMapping("sendMchntPhoneSMS")
    @ResponseBody
    public ResultHtml sendMchntPhoneSMS(HttpServletRequest request) {
        ResultHtml resultMap = new ResultHtml();
        // 拦截器已经处理了缓存,这里直接取
        String openid = WxMemoryCacheClient.getOpenid(request);
        String bizCode = request.getParameter("bizCode");
        try {
            if (StringUtil.isNullOrEmpty(openid)) {
                resultMap.setStatus(false);
                resultMap.setMsg("请从微信菜单重新进入页面");
                return resultMap;
            }
            MerchantManager mm = merchantManagerService.getMerchantInsInfByOpenId(openid);//查询所属商户号 关联查询  查找出来机构ID
            if (mm == null || StringUtil.isNullOrEmpty(mm.getPhoneNumber())) {
                resultMap.setStatus(false);
                resultMap.setMsg("您不是管理员，不能访问该页面");
                return resultMap;
            }
            String bindingPhone = mm.getPhoneNumber();
            HttpSession session = request.getSession();
            if (StringUtil.isNotEmpty(bindingPhone)) {
                return getResultMap(bindingPhone, Constants.SendMsgTypeEnum.findByCode(bizCode).getName(), session);
            } else {
                resultMap.setStatus(false);
                resultMap.setMsg("请重新输入手机号码");
                return resultMap;
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e.getLocalizedMessage(), e);

        }
        return resultMap;
    }

    /**
     * 支付成功   Created by Pucker 2016/9/18
     **/
    @RequestMapping(value = "/paySuccess")
    public ModelAndView paySuccess(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("common/pay_success");

        String transAmt = request.getParameter("transAmt");
        if (!StringUtil.isNullOrEmpty(transAmt)) {
            transAmt = StringUtil.isNullOrEmpty(transAmt) ? "0" : NumberUtils.RMBCentToYuan(transAmt);// 分转成元
        }
        mv.addObject("transAmt", transAmt);

        return mv;
    }

    /**
     * 支付失败   Created by Pucker 2016/9/18
     **/
    @RequestMapping(value = "/payFailed")
    public ModelAndView payFailed(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("common/pay_fail");

        String transAmt = request.getParameter("transAmt");
        String errorInfo = request.getParameter("errorInfo");
        if (!StringUtil.isNullOrEmpty(transAmt)) {
            transAmt = StringUtil.isNullOrEmpty(transAmt) ? "0" : NumberUtils.RMBCentToYuan(transAmt);// 分转成元
        }
        mv.addObject("transAmt", transAmt);
        if (!StringUtil.isNullOrEmpty(errorInfo)) {
            try {
                mv.addObject("errorInfo", URLDecoder.decode(errorInfo, "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                logger.error("支付失败结果页面转码失败", e);
            }
        }

        return mv;
    }

}
