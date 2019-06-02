package com.cn.thinkx.wecard.customer.module.welfaremart.ctrl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cn.thinkx.common.wecard.domain.base.ResultHtml;
import com.cn.thinkx.common.wecard.domain.cardkeys.*;
import com.cn.thinkx.common.wecard.domain.person.PersonInf;
import com.cn.thinkx.pms.base.redis.util.RedisDictProperties;
import com.cn.thinkx.pms.base.utils.BankUtil;
import com.cn.thinkx.pms.base.utils.BaseConstants;
import com.cn.thinkx.pms.base.utils.BaseConstants.CardProductType;
import com.cn.thinkx.pms.base.utils.NumberUtils;
import com.cn.thinkx.pms.base.utils.StringUtil;
import com.cn.thinkx.wecard.customer.core.util.MessageUtil;
import com.cn.thinkx.wecard.customer.module.base.ctrl.BaseController;
import com.cn.thinkx.wecard.customer.module.checkstand.vo.TransOrderReq;
import com.cn.thinkx.wecard.customer.module.customer.service.PersonInfService;
import com.cn.thinkx.wecard.customer.module.pub.service.CommonSerivce;
import com.cn.thinkx.wecard.customer.module.welfaremart.service.*;
import com.cn.thinkx.wecard.customer.module.welfaremart.vo.CardRechargeResp;
import com.cn.thinkx.wecard.customer.module.welfaremart.vo.WelfareUserInfo;
import com.cn.thinkx.wecard.customer.module.welfaremart.vo.WelfaremartResellResp;
import com.cn.thinkx.wecard.customer.module.wxcms.WxCmsContents;
import com.cn.thinkx.wechat.base.wxapi.process.WxMemoryCacheClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/welfareMart")
public class WelfareMartController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(WelfareMartController.class);

    @Autowired
    @Qualifier("cardKeysProductService")
    private CardKeysProductService cardKeysProductService;

    @Autowired
    @Qualifier("personInfService")
    private PersonInfService personInfService;

    @Autowired
    @Qualifier("welfareMartService")
    private WelfareMartService welfareMartService;

    @Autowired
    @Qualifier("commonSerivce")
    private CommonSerivce commonSerivce;

    @Autowired
    @Qualifier("userBankInfService")
    private UserBankInfService userBankInfService;

    @Autowired
    @Qualifier("cardKeysOrderInfService")
    private CardKeysOrderInfService cardKeysOrderInfService;

    @Autowired
    @Qualifier("cardKeysService")
    private CardKeysService cardKeysService;

    @Autowired
    @Qualifier("withdrawBlacklistInfService")
    private WithdrawBlacklistInfService withdrawBlacklistInfService;

    private final int resellFee = Integer.parseInt(BaseConstants.RESELL_FEE);

    /**
     * 用户是否有进入卡券集市操作的权限
     *
     * @param request request
     * @return true or false
     */
    @RequestMapping(value = "/isWithdrawBlacklistInf")
    @ResponseBody
    public boolean isWithdrawBlacklistInf(HttpServletRequest request) {
        String openid = WxMemoryCacheClient.getOpenid(request);
        if (StringUtil.isNullOrEmpty(openid)) {
            logger.info("★★★★★Request WelfareMart--->isWithdrawBlacklistInf get openid is [Null]★★★★★");
            return false;
        }

        PersonInf personInf = personInfService.getPersonInfByOpenId(openid);
        if (StringUtil.isNullOrEmpty(personInf)) {
            logger.info("★★★★★Request WelfareMart--->isWithdrawBlacklistInf get personInf is [Null]★★★★★");
            return false;
        }

        /* 用户是否有此权限 */
        WithdrawBlacklistInf withdrawBlacklistInf = withdrawBlacklistInfService.getWithdrawBlacklistInfByUserPhone(personInf.getMobilePhoneNo());
        return withdrawBlacklistInf == null;
    }

    /**
     * 卡券集市主页
     *
     * @param request request
     * @return ModelAndView
     */
    @RequestMapping(value = "/toWelfareMartHomePage")
    public ModelAndView toWelfareMartHomePage(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("welfaremart/homepage/homepage");
        String openid = WxMemoryCacheClient.getOpenid(request);
        if (StringUtil.isNullOrEmpty(openid)) {
            logger.info("★★★★★Request WelfareMart--->welfareMartHomePage get openid is [Null]★★★★★");
            return new ModelAndView("redirect:/common/500.html");
        }

        PersonInf personInf = personInfService.getPersonInfByOpenId(openid);
        if (StringUtil.isNullOrEmpty(personInf)) {
            logger.info("★★★★★Request WelfareMart--->welfareMartHomePage get personInf is [Null]★★★★★");
            return new ModelAndView("redirect:/common/500.html");
        }

        try {
            /* 账户信息 */
            WelfareUserInfo userInfo = welfareMartService.toWelfareMartHome(personInf.getUserId());
            /* 卡产品列表 */
            List<CardKeysProduct> cardKeysProductList = cardKeysProductService.getCardKeysProductByType(CardProductType.CP11.getCode());
            mv.addObject("cardKeysProductList", cardKeysProductList);
            mv.addObject("userInfo", userInfo);
        } catch (Exception e) {
            logger.error("## 卡券集市--->userID[{}]跳转卡券集市主页异常{}", personInf.getUserId(), e);
            return new ModelAndView("redirect:/common/500.html");
        }
        return mv;
    }

    /**
     * 购卡页面
     *
     * @param request request
     * @return ModelAndView
     */
    @RequestMapping(value = "/toWelfareBuyCard")
    public ModelAndView toWelfareBuyCard(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("welfaremart/cardshow/cardShow");
        String productCode = request.getParameter("productCode");
        //查询卡产品信息
        CardKeysProduct product = new CardKeysProduct();
        product.setProductCode(productCode);
        product.setIsPutaway("0");
        product.setDataStat("0");
        CardKeysProduct ckp;
        try {
            ckp = cardKeysProductService.getCardKeysProductByCode(product);
        } catch (Exception e) {
            logger.error("## 卡券集市--->跳转购卡页面，查询卡产品[{}]信息发生异常{}", productCode, e);
            return new ModelAndView("redirect:/common/500.html");
        }
        //转让费率（一张）
        int amount = NumberUtils.mul(resellFee, new Double(NumberUtils.RMBCentToYuan(ckp.getAmount())).intValue());
        ckp.setLoseAmount(NumberUtils.RMBCentToYuan(amount));
        ckp.setAmount(NumberUtils.RMBCentToYuan(ckp.getAmount()));
        ckp.setOrgAmount(NumberUtils.RMBCentToYuan(ckp.getOrgAmount()));
        mv.addObject("ckp", ckp);
        return mv;
    }

    /**
     * 购卡提交
     *
     * @param request request
     * @return ModelAndView
     */
    @RequestMapping(value = "/welfareBuyCardCommit")
    public ModelAndView welfareBuyCardCommit(HttpServletRequest request) {
        ModelAndView mv;
        String openid = WxMemoryCacheClient.getOpenid(request);
        if (StringUtil.isNullOrEmpty(openid)) {
            logger.error("★★★★★Request WelfareMart--->welfareBuyCardCommit get openid is [Null]★★★★★");
            return new ModelAndView("redirect:/common/500.html");
        }

        PersonInf personInf = personInfService.getPersonInfByOpenId(openid);
        if (StringUtil.isNullOrEmpty(personInf)) {
            logger.error("★★★★★Request WelfareMart--->welfareBuyCardCommit get personInf is [Null]★★★★★");
            return new ModelAndView("redirect:/common/500.html");
        }

        try {
            /** 用户是否有此权限 */
            WithdrawBlacklistInf withdrawBlacklistInf = withdrawBlacklistInfService.getWithdrawBlacklistInfByUserPhone(personInf.getMobilePhoneNo());
            if (withdrawBlacklistInf == null) {
                // 得到通卡商户门店等信息
                String num = request.getParameter("num");
                String productCode = request.getParameter("productCode");

                //xin5you 通卡购买卡密信息
                String mchntCode = RedisDictProperties.getInstance().getdictValueByCode(BaseConstants.ACC_HKB_MCHNT_NO);
                String shopCode = RedisDictProperties.getInstance().getdictValueByCode(BaseConstants.ACC_HKB_SHOP_NO);
                String notifyUrl = RedisDictProperties.getInstance().getdictValueByCode(BaseConstants.HKB_WELFAREMART_BUYCARD_NOTIFY_URL);
                String redirectUrl = RedisDictProperties.getInstance().getdictValueByCode(BaseConstants.HKB_WELFAREMART_REDIRECT_URL);

                TransOrderReq orderReq = welfareMartService.buyCardCommit(openid, num, productCode, mchntCode, shopCode, notifyUrl, redirectUrl, null, null, true);

                mv = new ModelAndView("welfaremart/cardshow/unifiedOrder");
                mv.addObject("orderReq", orderReq);
            } else {
                logger.info("卡券集市--->购买卡券，用户手机号{}在提现黑名单中，暂不支持卡券购买", personInf.getMobilePhoneNo());
                mv = new ModelAndView("welfaremart/cardin/cardFail");
                mv.addObject("msg", MessageUtil.WELFAREMART_NO_BUY);
            }
        } catch (Exception e) {
            logger.error("## 卡券集市--->购买卡券提交异常{}", e);
            return new ModelAndView("redirect:/common/500.html");
        }
        return mv;
    }

    /**
     * 跳转我的卡劵列表
     *
     * @param request request
     * @return ModelAndView
     */
    @RequestMapping(value = "/welfareCardBagList")
    public ModelAndView welfareCardBagList(HttpServletRequest request) {
        ModelAndView mv = null;
        try {
            //卡券列表
            List<CardKeysProduct> phoneList = welfareMartService.cardBagList(request);
            if (phoneList == null || phoneList.size() < 1)
                mv = new ModelAndView("welfaremart/cardshow/noneCard");
            else
                mv = new ModelAndView("welfaremart/cardshow/cardBag");
            mv.addObject("phoneList", phoneList);
        } catch (Exception e) {
            logger.error("## 卡券集市--->查询卡券列表异常{}", e);
        }
        return mv;
    }

    /**
     * 购卡提交重定向方法
     *
     * @param request request
     * @return ModelAndView
     */
    @RequestMapping(value = "/welfareBugCardRedirect")
    public ModelAndView welfareBugCardRedirect(HttpServletRequest request) {
        ModelAndView mv = null;
        try {
            //卡券列表
            List<CardKeysProduct> phoneList = welfareMartService.welfareBugCardRedirect(request);
            if (phoneList == null)
                mv = new ModelAndView("welfaremart/cardshow/cardBag");
            else {
                if (phoneList.size() < 1)
                    mv = new ModelAndView("welfaremart/cardshow/noneCard");
                else
                    mv = new ModelAndView("welfaremart/cardshow/cardBag");
            }
            mv.addObject("phoneList", phoneList);
        } catch (Exception e) {
            logger.error("## 卡券集市--->购卡提交重定向方法异常{}", e);
        }
        return mv;
    }

    /**
     * 跳转转让页面
     *
     * @param request request
     * @return ModelAndView
     */
    @RequestMapping(value = "/toWelfareResellCard")
    public ModelAndView toWelfareResellCard(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("welfaremart/resell/resell");
        String productCode = request.getParameter("productCode");

        String openid = WxMemoryCacheClient.getOpenid(request);
        if (StringUtil.isNullOrEmpty(openid)) {
            logger.error("★★★★★Request WelfareMart--->toWelfareResellCard get openid is [Null]★★★★★");
            return new ModelAndView("redirect:/common/500.html");
        }

        PersonInf personInf = personInfService.getPersonInfByOpenId(openid);
        if (StringUtil.isNullOrEmpty(personInf)) {
            logger.error("★★★★★Request WelfareMart--->toWelfareResellCard get personInf is [Null]★★★★★");
            return new ModelAndView("redirect:/common/500.html");
        }

        if (StringUtil.isNullOrEmpty(productCode)) {
            logger.error("★★★★★Request WelfareMart--->toWelfareResellCard get productCode is [Null]★★★★★");
            return new ModelAndView("redirect:/common/500.html");
        }

        // 查询未转让的卡密张数
        CardKeys card = new CardKeys();
        card.setAccountId(personInf.getUserId());
        card.setProductCode(productCode);
        int num = cardKeysService.getLoseNumByAccountId(card);

        CardKeysProduct ckp = new CardKeysProduct();
        ckp.setProductCode(productCode);
        ckp.setNum(String.valueOf(num));
        //卡券转让信息
        CardKeysProduct ckpt = cardKeysProductService.getCardKeysProductByCard(ckp);
        if (!StringUtil.isNullOrEmpty(ckpt)) {
            ckpt.setProductType(Objects.requireNonNull(CardProductType.findByCode(ckpt.getProductType())).getValue());

            // 计算手续费（费率乘金额除100，不取小数）
            BigDecimal b1 = new BigDecimal(NumberUtils.mul(resellFee, Integer.parseInt(ckpt.getAmount())));
            BigDecimal b2 = new BigDecimal(100);
            int gainAmount = b1.divide(b2, 0, BigDecimal.ROUND_DOWN).intValue();
            int gainAmountCent = NumberUtils.sub(Integer.parseInt(ckpt.getAmount()), gainAmount);
            ckpt.setGainAmount(NumberUtils.RMBCentToYuan(gainAmountCent));
            ckpt.setAmount(NumberUtils.RMBCentToYuan(ckpt.getAmount()));
            ckpt.setOrgAmount(NumberUtils.RMBCentToYuan(ckpt.getOrgAmount()));
        }

        //银行卡列表
        List<UserBankInf> userBankList = userBankInfService.getUserBankInfByUserId(personInf.getUserId());

        //可转让次数
        int appearNum = cardKeysOrderInfService.getMonthResellNum(personInf.getUserId());
        if (appearNum >= 9) {
            appearNum = 0;
        } else {
            appearNum = 9 - appearNum;
        }
        mv.addObject("userBankList", userBankList);
        mv.addObject("mobile", personInf.getMobilePhoneNo());
        mv.addObject("ckpt", ckpt);
        mv.addObject("appearNum", appearNum);
        return mv;
    }

    /**
     * 转让  发送短信验证码
     *
     * @param request request
     * @return ResultHtml
     */
    @RequestMapping(value = "/welfareSendPhoneCode")
    @ResponseBody
    public ResultHtml welfareSendPhoneCode(HttpServletRequest request) {
        return commonSerivce.sendPhoneSMS(request);
    }

    /**
     * 验证银行卡号
     *
     * @param request request
     * @return UserBankInf
     */
    @RequestMapping(value = "/welfareBankNoValid")
    @ResponseBody
    public UserBankInf welfareBankNoValid(HttpServletRequest request) {
        String openid = WxMemoryCacheClient.getOpenid(request);
        if (StringUtil.isNullOrEmpty(openid)) {
            logger.error("★★★★★Request WelfareMart--->welfareBankNoValid get openid is [Null]★★★★★");
            return null;
        }
        PersonInf personInf = personInfService.getPersonInfByOpenId(openid);
        if (StringUtil.isNullOrEmpty(personInf)) {
            logger.error("★★★★★Request WelfareMart--->welfareBankNoValid get personInf is [Null]，openID[{}]★★★★★", openid);
            return null;
        }

        String bankNo = request.getParameter("bankNo");
        if (StringUtil.isNullOrEmpty(bankNo)) {
            logger.error("## 卡券集市--->银行卡校验接口，获取userID[{}]银行卡号为空", personInf.getUserId());
            return null;
        }
        UserBankInf welfareUserInfo = null;
        try {
            welfareUserInfo = welfareMartService.bankNoValid(bankNo, personInf.getUserId());
        } catch (Exception e) {
            logger.error("## 卡券集市--->验证userID[{}]银行卡号[{}]异常{}", personInf.getUserId(), bankNo, e);
        }
        return welfareUserInfo;
    }

    /**
     * 转让提交
     *
     * @param request request
     * @return WelfaremartResellResp
     */
    @RequestMapping(value = "/welfareResellCardCommit")
    @ResponseBody
    public WelfaremartResellResp welfareResellCardCommit(HttpServletRequest request) {
        WelfaremartResellResp resp = new WelfaremartResellResp();
        resp.setStatus(Boolean.FALSE);
        //不需要弹框提示
        resp.setCode("1");

        String openid = WxMemoryCacheClient.getOpenid(request);
        if (StringUtil.isNullOrEmpty(openid)) {
            logger.error("★★★★★Request WelfareMart--->welfareResellCardCommit get openid is [Null]★★★★★");
            resp.setMsg(MessageUtil.ERROR_MSSAGE);
            return resp;
        }
        PersonInf personInf = personInfService.getPersonInfByOpenId(openid);
        if (StringUtil.isNullOrEmpty(personInf)) {
            logger.error("★★★★★Request WelfareMart--->welfareResellCardCommit get personInf is [Null]，openID[{}]★★★★★", openid);
            resp.setMsg(MessageUtil.ERROR_MSSAGE);
            return resp;
        }

        /* 用户是否有此权限 */
        WithdrawBlacklistInf withdrawBlacklistInf = withdrawBlacklistInfService.getWithdrawBlacklistInfByUserPhone(personInf.getMobilePhoneNo());
        if (withdrawBlacklistInf != null) {
            logger.info("卡券集市--->转让提交接口，用户手机号{}在提现黑名单中，暂不支持卡券转让", personInf.getMobilePhoneNo());
            resp.setMsg(MessageUtil.WELFAREMART_NO_RESELL);
            return resp;
        }

        // 验证码
        String phoneCode = StringUtil.nullToString(request.getParameter("phoneCode"));

        try {
            //校验短信验证码是否正确，如果一直则清空session验证码
            if (!checkPhoneCode(phoneCode, request.getSession(), new ResultHtml())) {
                logger.error("## 卡券集市--->转让提交接口，校验userID[{}]短信验证码失败", personInf.getUserId());
                resp.setMsg(MessageUtil.ERROR_MESSAGE_PHONE_CODE + "不正确，请重新输入");
                return resp;
            } else {
                String resellNum = request.getParameter("resellNum");
                String productCode = request.getParameter("productCode");
                String bankNo = request.getParameter("bankNo");
                resp = welfareMartService.resellCommit(resellNum, productCode, bankNo, personInf.getUserId());
                if (resp.isStatus()) {
                    request.getSession().removeAttribute(WxCmsContents.SESSION_PHONECODE);
                }
            }
        } catch (Exception e) {
            logger.error("## 卡券集市--->转让提交接口，userID[{}]转让异常{}", personInf.getUserId(), e);
            resp.setMsg(MessageUtil.ERROR_MSSAGE);
            return resp;
        }
        return resp;
    }

    /**
     * 跳转转让成功页面
     *
     * @param request request
     * @return ModelAndView
     */
    @RequestMapping(value = "/toWelfareResellSuccess")
    public ModelAndView toWelfareResellSuccess(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("welfaremart/resell/resellSuccess");
        String openid = WxMemoryCacheClient.getOpenid(request);
        if (StringUtil.isNullOrEmpty(openid)) {
            logger.error("★★★★★Request WelfareMart--->toWelfareResellSuccess get openid is [Null]★★★★★");
            return new ModelAndView("redirect:/common/500.html");
        }
        PersonInf personInf = personInfService.getPersonInfByOpenId(openid);
        if (StringUtil.isNullOrEmpty(personInf)) {
            logger.error("★★★★★Request WelfareMart--->toWelfareResellSuccess get personInf is [Null]，openID[{}]★★★★★", openid);
            return new ModelAndView("redirect:/common/500.html");
        }

        //当前转让成功的订单号
        String orderId = request.getParameter("orderId");
        //可转让次数
        int resellNum = cardKeysOrderInfService.getMonthResellNum(personInf.getUserId());
        if (resellNum >= 9) {
            resellNum = 0;
        } else {
            resellNum = 9 - resellNum;
        }
        mv.addObject("orderId", orderId);
        mv.addObject("resellNum", resellNum);
        return mv;
    }

    /**
     * 跳转添加银行卡页面
     *
     * @param request request
     * @return ModelAndView
     */
    @RequestMapping(value = "/toWelfareAddBank")
    public ModelAndView toWelfareAddBank(HttpServletRequest request) {
        ModelAndView mv;
        String productCode = request.getParameter("productCode");
        String num = request.getParameter("num");
        String check = request.getParameter("check");

        String openid = WxMemoryCacheClient.getOpenid(request);
        if (StringUtil.isNullOrEmpty(openid)) {
            logger.error("★★★★★Request WelfareMart--->toWelfareAddBank get openid is [Null]★★★★★");
            return new ModelAndView("redirect:/common/500.html");
        }

        PersonInf personInf = personInfService.getPersonInfByOpenId(openid);
        if (StringUtil.isNullOrEmpty(personInf)) {
            logger.error("★★★★★Request WelfareMart--->toWelfareAddBank get personInf is [Null]，openID[{}]★★★★★", openid);
            return new ModelAndView("redirect:/common/500.html");
        }

        /* 用户是否有此权限 */
        WithdrawBlacklistInf withdrawBlacklistInf = withdrawBlacklistInfService.getWithdrawBlacklistInfByUserPhone(personInf.getMobilePhoneNo());
        if (withdrawBlacklistInf != null) {
            logger.info("卡券集市--->跳转银行卡管理接口，用户手机号{}在提现黑名单中，暂不支持添加银行卡", personInf.getMobilePhoneNo());
            mv = new ModelAndView("welfaremart/cardin/cardFail");
            mv.addObject("msg", MessageUtil.WELFAREMART_NO_ADDBANKCARD);
            return mv;
        }

        mv = new ModelAndView("welfaremart/vip/addVip");
        mv.addObject("productCode", productCode);
        mv.addObject("num", num);
        mv.addObject("check", check);
        mv.addObject("userId", personInf.getUserId());
        mv.addObject("userName", personInf.getPersonalName());
        mv.addObject("mobile", personInf.getMobilePhoneNo());
        return mv;
    }

    /**
     * 添加银行卡提交
     *
     * @param request request
     * @return ResultHtml
     */
    @RequestMapping(value = "/welfareAddBankCommit")
    @ResponseBody
    public ResultHtml welfareAddBankCommit(HttpServletRequest request) {
        ResultHtml result = new ResultHtml();

        String openid = WxMemoryCacheClient.getOpenid(request);
        if (StringUtil.isNullOrEmpty(openid)) {
            logger.info("★★★★★Request WelfareMart--->welfareAddBankCommit get openid is [Null]★★★★★");
            return result;
        }
        PersonInf personInf = personInfService.getPersonInfByOpenId(openid);
        if (StringUtil.isNullOrEmpty(personInf)) {
            logger.info("★★★★★Request WelfareMart--->welfareAddBankCommit get personInf is [Null]，openID[{}]★★★★★", openid);
            return result;
        }

        // 验证码
        String phoneCode = StringUtil.nullToString(request.getParameter("phoneCode"));
        String check = request.getParameter("check");
        String bankNo = request.getParameter("bankNo");

        result.setStatus(Boolean.FALSE);
        result.setCheck(check);

        /* 用户是否有此权限 */
        WithdrawBlacklistInf withdrawBlacklistInf = withdrawBlacklistInfService.getWithdrawBlacklistInfByUserPhone(personInf.getMobilePhoneNo());
        if (withdrawBlacklistInf != null) {
            logger.info("卡券集市--->新增用户银行卡接口，用户手机号{}在提现黑名单中，暂不支持添加银行卡", personInf.getMobilePhoneNo());
            result.setMsg(MessageUtil.WELFAREMART_NO_ADDBANKCARD);
            return result;
        }

        if (StringUtil.isNullOrEmpty(bankNo)) {
            logger.error("## 卡券集市--->新增用户银行卡接口，获取userID[{}]bankNo为空", personInf.getUserId());
            result.setMsg(MessageUtil.ERROR_MSSAGE);
            return result;
        }


        String bankDetail = BankUtil.getCardDetail(bankNo);
        JSONObject bankJson = JSON.parseObject(bankDetail);
        if (!StringUtil.equals(bankJson.getString("validated"), "true")) {
            logger.error("## 卡券集市--->新增用户银行卡接口，userID[{}]银行卡号[{}]校验失败", personInf.getUserId(), bankNo);
            result.setMsg(MessageUtil.BANKNO_ERROR);
            return result;
        }

        if ("CC".equals(bankJson.getString("cardType"))) {
            logger.error("## 卡券集市--->新增用户银行卡接口，userID[{}]银行卡号[{}]为信用卡", personInf.getUserId(), bankNo);
            result.setMsg(MessageUtil.BANKNO_TYPE_ERROR);
            return result;
        }

        if (StringUtil.isNullOrEmpty(check)) {
            logger.error("## 卡券集市--->新增用户银行卡接口，userID[{}]获取check标志为空", personInf.getUserId());
            result.setMsg(MessageUtil.ERROR_MSSAGE);
            return result;
        }

        if (!checkPhoneCode(phoneCode, request.getSession(), new ResultHtml())) {
            logger.error("##卡券集市--->新增用户银行卡接口，校验userID[{}]短信验证码失败", personInf.getUserId());
            result.setStatus(Boolean.FALSE);
            result.setMsg(MessageUtil.ERROR_MESSAGE_PHONE_CODE + "不正确，请重新输入");
            return result;
        } else {
            try {
                result = welfareMartService.addBankCommit(request);
            } catch (Exception e) {
                logger.error("## 卡券集市-->新增userID[{}]银行卡信息异常{}", personInf.getUserId(), e);
            }
            if (result.isStatus()) {
                request.getSession().removeAttribute(WxCmsContents.SESSION_PHONECODE);
            }
        }
        logger.info("卡券集市--->新增用户银行卡接口，返回页面参数[{}]", JSONArray.toJSONString(result));
        return result;
    }

    /**
     * 跳转银行卡管理页面
     *
     * @param request request
     * @return ModelAndView
     */
    @RequestMapping(value = "/toBankCardList")
    public ModelAndView toBankCardlist(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("welfaremart/vip/bankCard");

        String openid = WxMemoryCacheClient.getOpenid(request);
        if (StringUtil.isNullOrEmpty(openid)) {
            logger.info("★★★★★Request WelfareMart--->toBankCardlist get openid is [Null]★★★★★");
            return new ModelAndView("redirect:/common/500.html");
        }
        PersonInf personInf = personInfService.getPersonInfByOpenId(openid);
        if (StringUtil.isNullOrEmpty(personInf)) {
            logger.info("★★★★★Request WelfareMart--->toBankCardlist get personInf is [Null]，openID[{}]★★★★★", openid);
            return new ModelAndView("redirect:/common/500.html");
        }

        try {
            List<UserBankInf> userBankList = welfareMartService.bankCardList(request);
            mv.addObject("userBankList", userBankList);
        } catch (Exception e) {
            logger.error("## 卡券集市--->跳转userID[{}]银行卡管理页面异常{}", personInf.getUserId(), e);
        }
        return mv;
    }

    /**
     * 删除银行卡
     *
     * @param request request
     * @return true or false
     */
    @RequestMapping(value = "/deleteBankCard")
    @ResponseBody
    public boolean deleteBankCard(HttpServletRequest request) {
        boolean isDel = false;

        String openid = WxMemoryCacheClient.getOpenid(request);
        if (StringUtil.isNullOrEmpty(openid)) {
            logger.info("★★★★★Request WelfareMart--->deleteBankCard get openid is [Null]★★★★★");
            return false;
        }
        PersonInf personInf = personInfService.getPersonInfByOpenId(openid);
        if (StringUtil.isNullOrEmpty(personInf)) {
            logger.info("★★★★★Request WelfareMart--->deleteBankCard get personInf is [Null]，openID[{}]★★★★★", openid);
            return false;
        }

        try {
            /* 用户是否有此权限 */
            WithdrawBlacklistInf withdrawBlacklistInf = withdrawBlacklistInfService.getWithdrawBlacklistInfByUserPhone(personInf.getMobilePhoneNo());
            if (withdrawBlacklistInf == null) {
                isDel = welfareMartService.deleteBankCard(request);
            } else {
                logger.info("卡券集市--->用户手机号{}在提现黑名单中，暂不支持删除银行卡", personInf.getMobilePhoneNo());
            }
        } catch (Exception e) {
            logger.error("## 卡券集市--->删除用户[{}]银行卡信息异常{}", personInf.getUserId(), e);
        }
        return isDel;
    }

    /**
     * 设置默认银行卡
     *
     * @param request request
     * @return true or false
     */
    @RequestMapping(value = "/isDefaultBankCard")
    @ResponseBody
    public boolean isDefaultBankCard(HttpServletRequest request) {
        boolean isSuccess = false;

        String openid = WxMemoryCacheClient.getOpenid(request);
        if (StringUtil.isNullOrEmpty(openid)) {
            logger.info("★★★★★Request WelfareMart--->isDefaultBankCard get openid is [Null]★★★★★");
            return isSuccess;
        }
        PersonInf personInf = personInfService.getPersonInfByOpenId(openid);
        if (StringUtil.isNullOrEmpty(personInf)) {
            logger.info("★★★★★Request WelfareMart--->isDefaultBankCard get personInf is [Null]，openID[{}]★★★★★", openid);
            return isSuccess;
        }

        try {
            /* 用户是否有此权限 */
            WithdrawBlacklistInf withdrawBlacklistInf = withdrawBlacklistInfService.getWithdrawBlacklistInfByUserPhone(personInf.getMobilePhoneNo());
            if (withdrawBlacklistInf == null) {
                isSuccess = welfareMartService.isDefaultBankCard(request);
            } else {
                logger.info("卡券集市--->用户手机号{}在提现黑名单中，暂不支持设置默认银行卡", personInf.getMobilePhoneNo());
                isSuccess = false;
            }
        } catch (Exception e) {
            logger.error("## 卡券集市---> 设置用户[{}]默认银行卡信息异常{}", personInf.getUserId(), e);
        }
        logger.info("卡券集市---> 设置用户[{}]默认银行卡信息，返回页面结果[{}]", personInf.getUserId(), isSuccess);
        return isSuccess;
    }

    /**
     * 跳转卡券交易明细页面
     *
     * @param request request
     * @return ModelAndView
     */
    @RequestMapping(value = "/toCardBagTransDetails")
    public ModelAndView toCardBagTransDetails(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("welfaremart/detaillist/transactionDetails");
        String openid = WxMemoryCacheClient.getOpenid(request);
        if (StringUtil.isNullOrEmpty(openid)) {
            logger.error("★★★★★Request WelfareMart--->toCardBagTransDetails get openid is [Null]★★★★★");
            return new ModelAndView("redirect:/common/500.html");
        }
        PersonInf personInf = personInfService.getPersonInfByOpenId(openid);
        if (StringUtil.isNullOrEmpty(personInf)) {
            logger.error("★★★★★Request WelfareMart--->toCardBagTransDetails get personInf is [Null]，openID[{}]★★★★★", openid);
            return new ModelAndView("redirect:/common/500.html");
        }

        try {
            List<CardKeysOrderInf> orderList = welfareMartService.cardBagTransDetails(personInf.getUserId());
            mv.addObject("orderList", orderList);
        } catch (Exception e) {
            logger.error("## 卡券集市--->用户[{}]跳转卡券交易明细页面异常{}", personInf.getUserId(), e);
        }
        return mv;
    }

    /**
     * 卡券列表充值
     *
     * @param request request
     * @return CardRechargeResp
     */
    @RequestMapping(value = "/welfareCardRecharge")
    @ResponseBody
    public CardRechargeResp welfareCardRecharge(HttpServletRequest request) {
        CardRechargeResp resp = null;

        String openid = WxMemoryCacheClient.getOpenid(request);
        if (StringUtil.isNullOrEmpty(openid)) {
            logger.error("★★★★★Request WelfareMart--->welfareCardRecharge get openid is [Null]★★★★★");
            return resp;
        }
        PersonInf personInf = personInfService.getPersonInfByOpenId(openid);
        if (StringUtil.isNullOrEmpty(personInf)) {
            logger.error("★★★★★Request WelfareMart--->welfareCardRecharge get personInf is [Null]，openID[{}]★★★★★", openid);
            return resp;
        }
        try {
            /* 用户是否有此权限 */
            WithdrawBlacklistInf withdrawBlacklistInf = withdrawBlacklistInfService.getWithdrawBlacklistInfByUserPhone(personInf.getMobilePhoneNo());
            if (withdrawBlacklistInf != null) {
                logger.info("卡券集市--->充值接口，用户手机号{}在提现黑名单中，暂不支持卡券充值", personInf.getMobilePhoneNo());
                resp = new CardRechargeResp();
                resp.setCode(BaseConstants.TXN_TRANS_ERROR);
                resp.setMsg(MessageUtil.WELFAREMART_NO_RECHARGE);
            } else {
                resp = welfareMartService.cardRecharge(request);
            }
        } catch (Exception e) {
            logger.error("## 卡券集市--->充值接口，查询userID[{}]可充值信息异常{}", personInf.getUserId(), e);
        }
        return resp;
    }

    /**
     * 卡券列表页面充值提交接口
     *
     * @param request request
     */
    @RequestMapping(value = "/welfareCardRechargeCommit")
    public ModelAndView welfareCardRechargeCommit(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("welfaremart/cardin/inFail");
        mv.addObject("check", "cardRecharge");
        String openid = WxMemoryCacheClient.getOpenid(request);
        if (StringUtil.isNullOrEmpty(openid)) {
            logger.info("★★★★★Request WelfareMart--->welfareCardRechargeCommit get openid is [Null]★★★★★");
            return new ModelAndView("redirect:/common/500.html");
        }
        PersonInf personInf = personInfService.getPersonInfByOpenId(openid);
        if (StringUtil.isNullOrEmpty(personInf)) {
            logger.error("★★★★★Request WelfareMart--->welfareCardRechargeCommit get personInf is [Null]，openID[{}]★★★★★", openid);
            return mv;
        }

        /* 用户是否有此权限 */
        WithdrawBlacklistInf withdrawBlacklistInf = withdrawBlacklistInfService.getWithdrawBlacklistInfByUserPhone(personInf.getMobilePhoneNo());
        if (withdrawBlacklistInf != null) {
            logger.info("卡券集市--->充值接口，用户手机号{}在提现黑名单中，暂不支持卡券充值", personInf.getMobilePhoneNo());
            mv = new ModelAndView("welfaremart/cardin/cardFail");
            mv.addObject("msg", MessageUtil.WELFAREMART_NO_RECHARGE);
            return mv;
        }

        String productCode = request.getParameter("productCode");
        if (StringUtil.isNullOrEmpty(productCode)) {
            logger.error("## 卡券集市--->充值接口，获取userID[{}]productCode为空，", personInf.getUserId());
            return mv;
        }

        boolean flag = false;
        try {
            flag = welfareMartService.cardRechargeCommit(productCode, personInf);
        } catch (Exception e) {
            logger.error("## 卡券集市--->充值接口，userID[{}]充值发生异常{}", personInf.getUserId(), e);
        }

        if (flag) {
            mv = new ModelAndView("welfaremart/cardin/cardSuccess");
            mv.addObject("check", "cardRecharge");
            return mv;
        }
        logger.error("## 卡券集市--->充值接口，userID[{}]充值失败", personInf.getUserId());
        return mv;

    }


    /**
     * 工资余额提现页面
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/toWageBalanceTrans")
    public ModelAndView toWageBalance(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("welfaremart/resell/balanceWithdraw");
        //String productCode = request.getParameter("productCodeproductCode");

        String openid = WxMemoryCacheClient.getOpenid(request);
        if (StringUtil.isNullOrEmpty(openid)) {
            logger.error("★★★★★Request WelfareMart--->toWelfareResellCard get openid is [Null]★★★★★");
            return new ModelAndView("redirect:/common/500.html");
        }

        PersonInf personInf = personInfService.getPersonInfByOpenId(openid);
        if (StringUtil.isNullOrEmpty(personInf)) {
            logger.error("★★★★★Request WelfareMart--->toWelfareResellCard get personInf is [Null]★★★★★");
            return new ModelAndView("redirect:/common/500.html");
        }
        //银行卡列表
        List<UserBankInf> userBankList = userBankInfService.getUserBankInfByUserId(personInf.getUserId());

        //可转让次数
        int appearNum = cardKeysOrderInfService.getMonthResellNum(personInf.getUserId());
        if (appearNum >= 9) {
            appearNum = 0;
        } else {
            appearNum = 9 - appearNum;
        }
        mv.addObject("userBankList", userBankList);
        mv.addObject("mobile", personInf.getMobilePhoneNo());
        mv.addObject("appearNum", appearNum);
        return mv;
    }


    /**
     * 工资提现提交
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/welfareBalanceDrawCommit")
    public ModelAndView welfareBalanceDrawCommit(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mv = null;
        String openid = WxMemoryCacheClient.getOpenid(request);
        if (StringUtil.isNullOrEmpty(openid)) {
            logger.error("★★★★★Request WelfareMart--->welfareBuyCardCommit get openid is [Null]★★★★★");
            return new ModelAndView("redirect:/common/500.html");
        }

        PersonInf personInf = personInfService.getPersonInfByOpenId(openid);
        if (StringUtil.isNullOrEmpty(personInf)) {
            logger.error("★★★★★Request WelfareMart--->welfareBuyCardCommit get personInf is [Null]★★★★★");
            return new ModelAndView("redirect:/common/500.html");
        }
        String phoneCode = request.getParameter("phoneCode"); //手机验证码

        try {
            /** 用户是否有此权限 */
            WithdrawBlacklistInf withdrawBlacklistInf = withdrawBlacklistInfService.getWithdrawBlacklistInfByUserPhone(personInf.getMobilePhoneNo());
            if (withdrawBlacklistInf == null) {
                // 得到通卡商户门店等信息
                String num = "1";
                String productCode = RedisDictProperties.getInstance().getdictValueByCode(BaseConstants.CARD_WAGES_XIN5YOU_PROD_NO);
                String transAmt = request.getParameter("transAmt");
                String bankNo = request.getParameter("bankNo"); //银行卡
                //xin5you 工资卡信息
                String mchntCode = RedisDictProperties.getInstance().getdictValueByCode(BaseConstants.WAGES_XIN5YOU_MCHNT_NO);
                String shopCode = RedisDictProperties.getInstance().getdictValueByCode(BaseConstants.WAGES_XIN5YOU_SHOP_NO);
                String notifyUrl = RedisDictProperties.getInstance().getdictValueByCode(BaseConstants.XIN5YOU_WELFAREMART_BALANCEDRAW_NOTIFY_URL);
                String redirectUrl = RedisDictProperties.getInstance().getdictValueByCode(BaseConstants.XIN5YOU_WELFAREMART_BALANCEDRAW_REDIRECT_URL);

                TransOrderReq orderReq = welfareMartService.buyCardCommit(openid, num, productCode, mchntCode, shopCode, notifyUrl, redirectUrl, transAmt, bankNo, false);

                mv = new ModelAndView("welfaremart/cardshow/unifiedOrder");
                mv.addObject("orderReq", orderReq);
            } else {
                logger.info("卡券集市--->购买卡券，用户手机号{}在提现黑名单中，暂不支持卡券购买", personInf.getMobilePhoneNo());
                mv = new ModelAndView("welfaremart/cardin/cardFail");
                mv.addObject("msg", MessageUtil.WELFAREMART_NO_BUY);
            }
        } catch (Exception e) {
            logger.error("## 卡券集市--->购买卡券提交异常{}", e);
            return new ModelAndView("redirect:/common/500.html");
        }
        return mv;
    }

}
