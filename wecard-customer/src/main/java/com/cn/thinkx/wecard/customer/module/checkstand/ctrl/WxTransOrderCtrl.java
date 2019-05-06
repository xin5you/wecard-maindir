package com.cn.thinkx.wecard.customer.module.checkstand.ctrl;

import com.alibaba.fastjson.JSONArray;
import com.cn.thinkx.common.activemq.service.WechatMQProducerService;
import com.cn.thinkx.common.wecard.domain.base.ResultHtml;
import com.cn.thinkx.common.wecard.domain.merchant.MerchantInf;
import com.cn.thinkx.common.wecard.domain.trans.WxTransLog;
import com.cn.thinkx.common.wecard.domain.trans.WxTransOrder;
import com.cn.thinkx.common.wecard.domain.trans.WxTransOrderDetail;
import com.cn.thinkx.common.wecard.domain.user.UserInf;
import com.cn.thinkx.common.wecard.domain.user.UserMerchantAcct;
import com.cn.thinkx.facade.bean.ShopInfQueryRequest;
import com.cn.thinkx.facade.service.HKBTxnFacade;
import com.cn.thinkx.pms.base.redis.util.ChannelSignUtil;
import com.cn.thinkx.pms.base.redis.util.HttpWebUtil;
import com.cn.thinkx.pms.base.redis.util.SignUtil;
import com.cn.thinkx.pms.base.utils.*;
import com.cn.thinkx.pms.base.utils.BaseConstants.ITFRespCode;
import com.cn.thinkx.pms.base.utils.BaseConstants.TransOrderResult;
import com.cn.thinkx.service.txn.Java2TxnBusinessFacade;
import com.cn.thinkx.wecard.customer.core.util.HttpUtil;
import com.cn.thinkx.wecard.customer.module.base.ctrl.BaseController;
import com.cn.thinkx.wecard.customer.module.checkstand.service.WxTransOrderService;
import com.cn.thinkx.wecard.customer.module.checkstand.util.Constants;
import com.cn.thinkx.wecard.customer.module.checkstand.valid.WxTransOrderTxnReqValid;
import com.cn.thinkx.wecard.customer.module.checkstand.vo.OrderRefund;
import com.cn.thinkx.wecard.customer.module.checkstand.vo.TransOrderReq;
import com.cn.thinkx.wecard.customer.module.checkstand.vo.TransOrderResp;
import com.cn.thinkx.wecard.customer.module.checkstand.vo.TranslogRefundReq;
import com.cn.thinkx.wecard.customer.module.customer.service.*;
import com.cn.thinkx.wecard.customer.module.merchant.service.MerchantInfService;
import com.cn.thinkx.wecard.customer.module.merchant.service.MerchantManagerService;
import com.cn.thinkx.wecard.customer.module.merchant.service.ShopInfService;
import com.cn.thinkx.wecard.customer.module.pub.domain.TxnResp;
import com.cn.thinkx.wecard.middleware.resp.hkbtxnfacade.entity.ShopInfoQueryITFResp;
import com.cn.thinkx.wechat.base.wxapi.process.CustomerWxPayApi;
import com.cn.thinkx.wechat.base.wxapi.process.CustomerWxPayApiClient;
import com.cn.thinkx.wechat.base.wxapi.process.WxMemoryCacheClient;
import com.cn.thinkx.wechat.base.wxapi.util.WxConstants;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * 收银台 商户商城 下单请求支付系列
 *
 * @author zqy
 */
@Controller
@RequestMapping("trans/order")
public class WxTransOrderCtrl extends BaseController {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    @Qualifier("hkbTxnFacade")
    private HKBTxnFacade hkbTxnFacade;

    @Autowired
    @Qualifier("wxTransLogService")
    private WxTransLogService wxTransLogService;

    @Autowired
    @Qualifier("wxTransOrderService")
    private WxTransOrderService wxTransOrderService;

    @Autowired
    @Qualifier("merchantInfService")
    private MerchantInfService merchantInfService;

    @Autowired
    @Qualifier("userMerchantAcctService")
    private UserMerchantAcctService userMerchantAcctService;

    @Autowired
    @Qualifier("userInfService")
    private UserInfService userInfService;

    @Autowired
    @Qualifier("java2TxnBusinessFacade")
    private Java2TxnBusinessFacade java2TxnBusinessFacade;

    @Autowired
    @Qualifier("shopInfService")
    private ShopInfService shopInfService;

    @Autowired
    @Qualifier("merchantManagerService")
    private MerchantManagerService merchantManagerService;

    @Autowired
    @Qualifier("wechatMQProducerService")
    private WechatMQProducerService wechatMQProducerService;

    @Autowired
    @Qualifier("personInfService")
    private PersonInfService personInfService;

    @Autowired
    @Qualifier("ctrlSystemService")
    private CtrlSystemService ctrlSystemService;


    /**
     * 收银台 商户商城 订单查询
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/transOrderQuery")
    @ResponseBody
    public String transOrderQuery(HttpServletRequest request) {
        TransOrderReq orderReq = new TransOrderReq();

        String channel = StringUtil.nullToString(request.getParameter("channel")); // 渠道
        String userId = StringUtil.nullToString(request.getParameter("userId"));
        String orderId = StringUtil.nullToString(request.getParameter("orderId")); // 商户订单号
        String txnFlowNo = StringUtil.nullToString(request.getParameter("txnFlowNo")); // 商户订单号
        String sign = StringUtil.nullToString(request.getParameter("sign")); // 商户订单号

        orderReq.setChannel(channel);
        orderReq.setUserId(userId);
        orderReq.setOrderId(orderId);
        orderReq.setTxnFlowNo(txnFlowNo);
        orderReq.setSign(sign);

        logger.info("transOrderQuery params is-->{()}", JSONArray.toJSONString(orderReq));

        /*** 参数校验 **/
        TransOrderResp txnResp = new TransOrderResp();
        if (WxTransOrderTxnReqValid.transOrderQueryValid(orderReq, txnResp)) {
            txnResp.setRespResult(TransOrderResult.FAIL.getValue());
            return JSONArray.toJSONString(txnResp);
        }

        /** 渠道的订单信息查询 */
        WxTransOrder transOrder = wxTransOrderService.getWxTransOrdeByTxnFlowNo(channel, orderId, txnFlowNo);
        if (transOrder == null) {
            txnResp.setRespResult(TransOrderResult.FAIL.getValue());
            txnResp.setInfo("交易订单不存在");
            return JSONArray.toJSONString(txnResp);
        }

        /** 订单信息返回 **/
        if (BaseConstants.RESPONSE_SUCCESS_CODE.equals(transOrder.getOrderDetail().getRespCode())) {
            txnResp.setRespResult(TransOrderResult.SUCCESS.getValue());
        } else {
            txnResp.setRespResult(TransOrderResult.FAIL.getValue());
        }
        txnResp.setChannel(transOrder.getTransChnl());
        txnResp.setUserId(transOrder.getUserId());
        txnResp.setTxnFlowNo(transOrder.getOrderDetail().getTxnFlowNo());
        txnResp.setOriTxnAmount(transOrder.getOrderDetail().getTransAmt());
        txnResp.setTxnAmount(transOrder.getOrderDetail().getUploadAmt());
        txnResp.setAttach(transOrder.getOrderDetail().getAdditionalInfo());
        String respSign = ChannelSignUtil.genSign(txnResp); // 获取签名
        txnResp.setSign(respSign);
        return JSONArray.toJSONString(txnResp);
    }

    /**
     * 收银台 商户商城 订单请求支付
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/unifiedOrder")
    public ModelAndView unifiedOrder(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("checkstand/mchntpay/unifiedOrder");

        TransOrderResp txnResp = new TransOrderResp();
        txnResp.setRespResult(TransOrderResult.FAIL.getValue());

        String channel = request.getParameter("channel"); // 渠道
        String userId = request.getParameter("userId");
        String orderId = request.getParameter("orderId"); // 商户订单号
        String innerMerchantNo = request.getParameter("innerMerchantNo");
        String innerShopNo = request.getParameter("innerShopNo");
        String commodityName = request.getParameter("commodityName");// 商品名称
        String commodityNum = request.getParameter("commodityNum"); // 商品数量
        String txnAmount = request.getParameter("txnAmount"); // 交易金额
        String attach = request.getParameter("attach"); // 用户附加信息
        String notify_type = StringUtil.nullToString(request.getParameter("notify_type"));// 是否通知
        String notify_url = request.getParameter("notify_url");// 通知地址
        String redirect_type = request.getParameter("redirect_type");// 重定向标记
        String redirect_url = request.getParameter("redirect_url");// 重定向URL
        String signType = request.getParameter("signType");// 重定向URL
        String sign = request.getParameter("sign");// 重定向URL

        txnResp.setRedirectType(redirect_type);
        txnResp.setRedirectUrl(redirect_url);

        /** step1 验证请求参数 **/
        TransOrderReq orderReq = new TransOrderReq();
        orderReq.setChannel(channel);
        orderReq.setUserId(userId);
        orderReq.setOrderId(orderId);
        orderReq.setInnerMerchantNo(innerMerchantNo);
        orderReq.setInnerShopNo(innerShopNo);
        orderReq.setCommodityName(commodityName);
        orderReq.setCommodityNum(commodityNum);
        orderReq.setTxnAmount(txnAmount);
        orderReq.setAttach(attach);
        orderReq.setNotify_type(notify_type);
        orderReq.setNotify_url(notify_url);
        orderReq.setRedirect_type(redirect_type);
        orderReq.setRedirect_url(redirect_url);
        orderReq.setSignType(signType);
        orderReq.setSign(sign);

        /*** 参数校验 **/
        if (WxTransOrderTxnReqValid.unifiedOrderValid(orderReq, txnResp)) {
            txnResp.setRespResult(TransOrderResult.FAIL.getValue());
            return orderVailFailView(txnResp);
        }

        /** 商户信息校验 获取机构号 */
        MerchantInf mchntInf = merchantInfService.getMerchantInfByCode(innerMerchantNo);
        if (mchntInf == null) {
            txnResp.setRespResult(TransOrderResult.FAIL.getValue());
            txnResp.setInfo("商户不存在");
            return orderVailFailView(txnResp);
        }

        /** 用户信息校验信息校验 */
        UserInf userInf = userInfService.getUserChannelInfByUserId(BaseConstants.ChannelCode.CHANNEL1.toString(),
                userId);
        if (userInf == null) {
            txnResp.setInfo("用户账户不存在");
            return orderVailFailView(txnResp);
        }
        /** 渠道的订单信息查询 */
        WxTransOrder transOrder = wxTransOrderService.getWxTransOrdeByTxnFlowNo(channel, orderId, "");
        if (transOrder != null) {
            if (BaseConstants.RESPONSE_SUCCESS_CODE.equals(transOrder.getOrderDetail().getRespCode())) {// 当前订单已经处理成功
                txnResp.setInfo("订单已经处理成功");
                return orderVailFailView(txnResp);
            }
        } else {
            /** step3 insert 订单数据 **/
            String orderKey = wxTransLogService.getPrimaryKey();

            transOrder = new WxTransOrder();
            transOrder.setSponsor("00");
            transOrder.setOrderKey(orderKey);
            transOrder.setDmsRelatedKey(orderId); // 商户订单号
            transOrder.setUserId(userId); // 用户Id
            transOrder.setMchntCode(innerMerchantNo);
            transOrder.setShopCode(innerShopNo);
            transOrder.setInsCode(mchntInf.getInsCode()); // 机构信息
            transOrder.setOrderAmt(txnAmount);
            transOrder.setTransCurrCd(BaseConstants.TRANS_CURR_CD);
            transOrder.setTransChnl(channel);
            transOrder.setUserInfUserName(userInf.getExternalId());
            transOrder.setDataStat("0");

            WxTransOrderDetail orderDetail = new WxTransOrderDetail();
            orderDetail.setCommodityName(commodityName);
            orderDetail.setCommodityNum(commodityNum);
            orderDetail.setTransAmt(txnAmount);
            orderDetail.setUploadAmt(txnAmount);
            orderDetail.setAdditionalInfo(attach);
            orderDetail.setNotifyType(notify_type);
            orderDetail.setNotifyUrl(notify_url);
            orderDetail.setRedirectType(redirect_type);
            orderDetail.setRedirectUrl(redirect_url);
            orderDetail.setSignType(signType);
            orderDetail.setDataStat("0");

            try {
                int i = wxTransOrderService.saveWxTransOrder(transOrder, orderDetail);
                if (i <= 0) {// 当前订单insert 失败
                    txnResp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
                    return orderVailFailView(txnResp);
                }
            } catch (Exception ex) {
                logger.error("## 添加订单记录失败", ex);
                txnResp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
                return orderVailFailView(txnResp);
            }
        }

        /*** 商户门店信息查询 **/
        ShopInfoQueryITFResp shopInfo = null;
        try {
            ShopInfQueryRequest mchntShopReq = new ShopInfQueryRequest();
            mchntShopReq.setChannel(orderReq.getChannel());
            mchntShopReq.setInnerMerchantNo(orderReq.getInnerMerchantNo());
            mchntShopReq.setInnerShopNo(orderReq.getInnerShopNo());
            mchntShopReq.setTimestamp(System.currentTimeMillis());
            mchntShopReq.setSign(SignUtil.genSign(mchntShopReq));

            String jsonStr = hkbTxnFacade.getShopInfoQueryITF(mchntShopReq);
            shopInfo = JSONArray.parseObject(jsonStr, ShopInfoQueryITFResp.class);
        } catch (Exception e) {
            logger.error("## 收银台 商户商城, 查询商户信息异常", e);
        }
        if (shopInfo == null) {
            shopInfo = new ShopInfoQueryITFResp();
        }
        mv.addObject("shopInfo", shopInfo.getShopInfo());

        /*** 判断用户是否已经是某商户会员 **/
        String userMchntAccBal = wxTransOrderService.getMchntAccBal(innerMerchantNo, userId);
        mv.addObject("userMchntAccBal", userMchntAccBal);// 用户商户下的卡余额

        orderReq.setTxnAmount(NumberUtils.RMBCentToYuan(orderReq.getTxnAmount())); // 交易金额转为元
        mv.addObject("orderReq", orderReq);// 订单请求信息
        mv.addObject("orderKey", transOrder.getOrderKey());// 订单表主键

        /*** 生成密码非对称加密信息 **/
        try {
            HashMap<String, Object> map = RSAUtil.getKeys();
            // 生成公钥和私钥
            RSAPublicKey publicKey = (RSAPublicKey) map.get("public");
            RSAPrivateKey privateKey = (RSAPrivateKey) map.get("private");
            // 私钥保存在session中，用于解密
            request.getSession().setAttribute(WxConstants.TRANS_ORDER_RSA_PRIVATE_KEY_SESSION, privateKey);
            // 公钥信息保存在页面，用于加密
            String publicKeyExponent = publicKey.getPublicExponent().toString(16);
            String publicKeyModulus = publicKey.getModulus().toString(16);
            mv.addObject("publicKeyExponent", publicKeyExponent);
            mv.addObject("publicKeyModulus", publicKeyModulus);
        } catch (NoSuchAlgorithmException e) {
            logger.error("## 生成密钥异常", e);
            mv = new ModelAndView("common/failure");
            mv.addObject("failureMsg", BaseConstants.RESPONSE_EXCEPTION_INFO);
            return mv;
        }
        return mv;
    }

    /**
     * 收银台 消费交易-插入微信端流水
     **/
    @RequestMapping(value = "/insertWxTransLogByTransOrder")
    @ResponseBody
    public WxTransLog insertWxTransLogByTransOrder(HttpServletRequest request) {
        WxTransLog log = wxTransOrderService.insertWxTransLogByTransOrder(request);
        return log;
    }

    /**
     * 消费交易-判断是否需要输入密码
     **/
    @RequestMapping(value = "/doCustomerNeed2EnterPassword")
    @ResponseBody
    public TxnResp doCustomerNeed2EnterPassword(HttpServletRequest request) {
        TxnResp resp = new TxnResp();

        String orderKey = request.getParameter("orderKey");

        /** 查找订单信息 **/
        WxTransOrder transOrder = null;
        try {
            transOrder = wxTransOrderService.getWxTransOrdeByOrderKey(orderKey);
        } catch (Exception e) {
            logger.error("查找订单信息异常", e);
            resp.setCode(BaseConstants.RESPONSE_EXCEPTION_CODE);
            resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
            return resp;
        }

        /** 获取无pin限额 */
        UserMerchantAcct userMerchantAcct = new UserMerchantAcct();
        userMerchantAcct.setUserId(transOrder.getUserId());
        userMerchantAcct.setMchntCode(transOrder.getMchntCode());
        try {
            // 无PIN限额 需要验密
            boolean noPinTxnSt = userMerchantAcctService.doCustomerNeed2EnterPassword(userMerchantAcct,
                    Integer.parseInt(transOrder.getOrderAmt()));
            if (noPinTxnSt) {// 如果实际消费金额大于无PIN限额 需要验密
                resp.setCode("1");// 需要密码
            } else {
                resp.setCode("0");// 不需要密码
            }
        } catch (Exception e) {
            logger.error("获取无pin限额视图出错", e);
            resp.setCode(BaseConstants.RESPONSE_EXCEPTION_CODE);
            return resp;
        }
        return resp;
    }

    /**
     * 收银台 会员卡消费交易-验密后调用交易核心
     **/
    @RequestMapping(value = "/transOrderJava2TxnBusiness")
    @ResponseBody
    public TxnResp transOrderJava2TxnBusiness(HttpServletRequest request) {

        return wxTransOrderService.doTransOrderJava2TxnBusiness(request);
    }

    /**
     * 收银台 微信快捷支付 调用微信网关 请求支付 Created by zqy 2017/6/30
     **/
    @RequestMapping(value = "/transOrderChatQuickPay")
    @ResponseBody
    public JSONObject transOrderChatQuickPayCommit(HttpServletRequest request) {
        JSONObject obj = new JSONObject();
        String wxTransLogKey = request.getParameter("wxTransLogKey"); // 微信端流水主键

        WxTransLog translog = wxTransLogService.getWxTransLogById(wxTransLogKey);
        // 查询用户是否开卡
        UserMerchantAcct acc = new UserMerchantAcct();
        acc.setExternalId(translog.getUserInfUserName());
        acc.setMchntCode(translog.getMchntCode());
        List<UserMerchantAcct> cardList = userMerchantAcctService.getUserMerchantAcctByUser(acc);
        if (cardList == null || cardList.size() < 1) {
            try {
                // 请求开卡
                TxnResp cardCheckResp = userMerchantAcctService.doCustomerAccountOpening(null, null,
                        translog.getUserInfUserName(), translog.getMchntCode(), translog.getInsCode());
                if (cardCheckResp != null && !BaseConstants.TXN_TRANS_RESP_SUCCESS.equals(cardCheckResp.getCode())) {
                    userMerchantAcctService.doCustomerAccountOpening(null, null, translog.getUserInfUserName(),
                            translog.getMchntCode(), translog.getInsCode());
                }
            } catch (Exception ex) {
                logger.error("收银台  微信快捷支付 ，用户开卡失败-->" + ex);
            }
        }
        try {
            String chargeTime = DateUtil.getCurrentTimeStr();
            String notifyUrl = HttpWebUtil.getCustomerDomainUrl() + "trans/order/transOrderWeChantQuickNotify.html"; // 微信快捷支付 通知回调方法
            obj = CustomerWxPayApiClient.unifiedOrder(WxMemoryCacheClient.getSingleMpAccount(),
                    translog.getWxPrimaryKey(), translog.getTransAmt(), translog.getUserInfUserName(),
                    StringUtil.trim(HttpUtil.getIpAddr(request)), notifyUrl);// 统一下单
            if ("SUCCESS".equals(obj.getString("return_code"))) {
                if ("SUCCESS".equals(obj.getString("result_code"))) {// 统一下单成功，微信服务器已生成预付单
                    String sign = obj.getString("sign");// 微信返回的签名
                    String localSign = CustomerWxPayApi.genUnifiedOrderBackSign(obj);// 生成统一下单返回签名
                    if (!localSign.equals(sign)) {// 验签
                        logger.info("收银台  微信快捷支付 --->统一下单返回签名验证失败");
                        return null;
                    }
                    String appId = obj.getString("appid");
                    String timeStamp = "" + System.currentTimeMillis() / 1000;
                    String nonceStr = UUID.randomUUID().toString().replace("-", "");
                    String packageStr = "prepay_id=" + obj.getString("prepay_id");
                    String paySign = CustomerWxPayApi.getPaySign(appId, timeStamp, nonceStr, packageStr);

                    obj.put("timeStamp", timeStamp);// js调用微信支付用的时间戳
                    obj.put("nonceStr", nonceStr);// js调用微信支付用的随机数
                    obj.put("paySign", paySign);// js调用微信支付用的签名
                    obj.put("wxPrimaryKey", translog.getWxPrimaryKey());// 调用微信支付后查询业务流水主键
                    obj.put("txnDate", translog.getSettleDate() + chargeTime);
                    return obj;
                } else {
                    logger.info("收银台 微信快捷支付-->" + obj.getString("err_code") + "|" + obj.getString("err_code_des"));
                }
            } else {
                logger.info("收银台 微信快捷支付-->" + obj.getString("return_code") + "|" + obj.getString("return_msg"));
            }
        } catch (Exception ex) {
            logger.error("收银台 微信快捷支付-->订单生成异常", ex.getLocalizedMessage());
        }
        return null;
    }

    /**
     * 收银台 微信快捷支付 回调通知
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/transOrderWeChantQuickNotify")
    @ResponseBody
    public String transOrderWeChantQuickNotify(HttpServletRequest request) {

        return wxTransOrderService.doTransOrderWeChantQuickNotify(request);
    }

    /**
     * 收银台 消费交易查询- Created by zqy 2017/6/30
     **/
    @RequestMapping(value = "/transOrderWeChantQuickPayOrderQuery")
    @ResponseBody
    public TxnResp BSCweChantQuickPayOrderQuery(HttpServletRequest request, HttpSession session) {
        String wxPrimaryKey = StringUtil.trim(request.getParameter("wxPrimaryKey"));// 业务流水(微信端)
        TxnResp resp = new TxnResp();
        WxTransLog log = wxTransLogService.getWxTransLogById(wxPrimaryKey);// 查询业务流水
        String json = "";
        try {
            if (log != null) {
                if (log.getTransSt() == 0) {
                    json = java2TxnBusinessFacade.transExceptionQueryITF(wxPrimaryKey); // 交易异常查询
                    resp = JSONArray.parseObject(json, TxnResp.class);
                    if (resp == null || !BaseConstants.TXN_TRANS_RESP_SUCCESS.equals(resp.getCode())) {
                        Thread.sleep(2000);
                        json = java2TxnBusinessFacade.transExceptionQueryITF(wxPrimaryKey); // 交易异常查询
                        resp = JSONArray.parseObject(json, TxnResp.class);
                        if (resp == null) {
                            resp = new TxnResp();
                        }
                    }
                    resp.setTransAmt(NumberUtils.RMBCentToYuan(log.getTransAmt()));
                } else {
                    resp.setCode(StringUtil.nullToString(log.getRespCode()));
                    resp.setTransAmt(NumberUtils.RMBCentToYuan(log.getTransAmt()));
                }
                if (!BaseConstants.TXN_TRANS_RESP_SUCCESS.equals(resp.getCode())) {
                    resp.setInfo("网络异常，请稍后再试");
                }
                resp.setTransAmt(NumberUtils.RMBCentToYuan(log.getTransAmt()));
            }
        } catch (Exception e) {
            resp.setCode(BaseConstants.RESPONSE_EXCEPTION_CODE);// 失败-返回码
            resp.setInfo("网络异常，请稍后再试");
        }
        return resp;
    }

    /**
     * 收银台 交易 支付成功
     **/
    @RequestMapping(value = "/orderPaySuccess")
    public ModelAndView paySuccess(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("checkstand/mchntpay/orderPaySuccess");
        String wxPrimaryKey = StringUtil.trim(request.getParameter("wxTransLogKey"));// 业务流水(微信端)

        WxTransLog wxTransLog = null;
        try {
            wxTransLog = wxTransLogService.getWxTransLogById(wxPrimaryKey);
        } catch (Exception ex) {

        }
        if (wxTransLog != null) {
            mv.addObject("transAmt", NumberUtils.RMBCentToYuan(wxTransLog.getTransAmt()));
            mv.addObject("transId", wxTransLog.getTransId());

            /** 查找订单信息 **/
            WxTransOrder transOrder = null;
            try {
                transOrder = wxTransOrderService.getWxTransOrdeByOrderKey(wxTransLog.getOrderId());
                if (transOrder != null) {
                    mv.addObject("orderKey", transOrder.getOrderDetail().getOrderKey());
                    mv.addObject("redirectType", transOrder.getOrderDetail().getRedirectType());
                    mv.addObject("redirectUrl", transOrder.getOrderDetail().getRedirectUrl());
                }
            } catch (Exception e) {
                logger.error("查找订单信息异常", e);
            }
        }

        return mv;
    }

    /**
     * 收银台 交易 支付失败
     **/
    @RequestMapping(value = "/orderPayFail")
    public ModelAndView payFailed(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("checkstand/mchntpay/orderPayFail");

        String wxPrimaryKey = StringUtil.trim(request.getParameter("wxTransLogKey"));// 业务流水(微信端)
        WxTransLog wxTransLog = null;
        try {
            wxTransLog = wxTransLogService.getWxTransLogById(wxPrimaryKey);
        } catch (Exception ex) {
            logger.error("收银台交易支付失败查找订单信息异常", ex);
        }

        if (wxTransLog != null) {
            if (StringUtil.isNullOrEmpty(wxTransLog.getRespCode())) {
                try {
                    String json = java2TxnBusinessFacade.transExceptionQueryITF(wxPrimaryKey);
                    TxnResp resp = JSONArray.parseObject(json, TxnResp.class);
                    wxTransLog.setTransSt(1);// 插入时为0，报文返回时更新为1
                    wxTransLogService.updateWxTransLog(wxTransLog, resp);

                    if (resp != null && BaseConstants.RESPONSE_SUCCESS_CODE.equals(resp.getCode())) {
                        return new ModelAndView(
                                "redirect:/trans/order/orderPaySuccess.html?wxTransLogKey=" + wxPrimaryKey);
                    }

                } catch (Exception ex) {
                    logger.error("收银台交易支付失败查找订单信息异常", ex);
                }
            }
            mv.addObject("transAmt", NumberUtils.RMBCentToYuan(wxTransLog.getTransAmt()));
            mv.addObject("transId", wxTransLog.getTransId());

            String errorInfo = "";
            ITFRespCode respCodeEnum = ITFRespCode.findByCode(wxTransLog.getRespCode());
            if (respCodeEnum != null) {
                errorInfo = respCodeEnum.getValue();
            }
            mv.addObject("errorInfo", errorInfo);
            /** 查找订单信息 **/
            WxTransOrder transOrder = null;
            try {
                transOrder = wxTransOrderService.getWxTransOrdeByOrderKey(wxTransLog.getOrderId());
                if (transOrder != null) {
                    mv.addObject("orderKey", transOrder.getOrderKey());
                    mv.addObject("redirectType", transOrder.getOrderDetail().getRedirectType());
                    mv.addObject("redirectUrl", transOrder.getOrderDetail().getRedirectUrl());
                }
            } catch (Exception e) {
                logger.error("收银台交易支付失败查找订单信息异常", e);
            }
        }
        return mv;
    }

    @RequestMapping(value = "/orderRedirectUrl")
    public void orderRedirectUrl(HttpServletRequest request, HttpServletResponse response) {
        String orderKey = request.getParameter("orderKey"); // transOrder主键

        /** 查找订单信息 **/
        WxTransOrder transOrder = null;
        try {
            transOrder = wxTransOrderService.getWxTransOrdeByOrderKey(orderKey);
            if (transOrder != null) {
                TransOrderResp orderResp = new TransOrderResp();
                orderResp.setChannel(transOrder.getTransChnl());
                /** 订单信息返回 **/
                if (BaseConstants.RESPONSE_SUCCESS_CODE.equals(transOrder.getOrderDetail().getRespCode())) {
                    orderResp.setRespResult(TransOrderResult.SUCCESS.getValue());
                } else {
                    orderResp.setRespResult(TransOrderResult.FAIL.getValue());
                }
                orderResp.setUserId(transOrder.getUserId());
                orderResp.setAttach(transOrder.getOrderDetail().getAdditionalInfo());
                orderResp.setOrderId(transOrder.getDmsRelatedKey());
                orderResp.setTxnFlowNo(transOrder.getOrderDetail().getTxnFlowNo());

                String sign = SignUtil.genSign(orderResp);

                StringBuffer url = new StringBuffer();
                url.append(transOrder.getOrderDetail().getRedirectUrl()).append("?").append("channel=")
                        .append(orderResp.getChannel()).append("&respResult=").append(orderResp.getRespResult())
                        .append("&userId=").append(StringUtil.nullToString(orderResp.getUserId())).append("&orderId=")
                        .append(StringUtil.nullToString(orderResp.getOrderId())).append("&txnFlowNo=")
                        .append(StringUtil.nullToString(orderResp.getTxnFlowNo())).append("&attach=")
                        .append(StringUtil.nullToString(orderResp.getAttach())).append("&sign=").append(sign);

                response.setContentType("text/html;charset=utf-8");
                PrintWriter out = response.getWriter();
//				out.println("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\">");
//				out.println("<HTML>");
//				out.println("<HEAD><TITLE>知了企服收银台</TITLE></HEAD>");
//				out.println("<BODY>");
                out.println("<form name=\"submitForm\" action=\"" + transOrder.getOrderDetail().getRedirectUrl()
                        + "\" method=\"post\">");
                out.println("<input type=\"hidden\" name=\"channel\" value=\"" + orderResp.getChannel() + "\"/>");
                out.println("<input type=\"hidden\" name=\"respResult\" value=\"" + orderResp.getRespResult() + "\"/>");
                out.println("<input type=\"hidden\" name=\"userId\" value=\""
                        + StringUtil.nullToString(orderResp.getUserId()) + "\"/>");
                out.println("<input type=\"hidden\" name=\"orderId\" value=\""
                        + StringUtil.nullToString(orderResp.getOrderId()) + "\"/>");
                out.println("<input type=\"hidden\" name=\"txnFlowNo\" value=\""
                        + StringUtil.nullToString(orderResp.getTxnFlowNo()) + "\"/>");
                out.println("<input type=\"hidden\" name=\"attach\" value=\""
                        + StringUtil.nullToString(orderResp.getAttach()) + "\"/>");
                out.println("<input type=\"hidden\" name=\"sign\" value=\"" + sign + "\"/>");

                out.println("</from>");
                out.println("<script>window.document.submitForm.submit();</script> ");
//				out.println("</BODY>");
//				out.println("</HTML>");
                out.flush();
                out.close();

            }
        } catch (Exception e) {
            logger.error("收银台交易支付失败查找订单信息异常", e);
        }
    }

    private ModelAndView orderVailFailView(TransOrderResp txnResp) {
        ModelAndView mv = new ModelAndView("checkstand/mchntpay/orderVailFail");
        txnResp.setRespResult(TransOrderResult.FAIL.getValue());
        mv.addObject("txnResp", txnResp);
        return mv;
    }

    /**
     * 收银台 验证失败跳转
     **/
    @RequestMapping(value = "/orderVailFail")
    private ModelAndView toOrderVailFailView(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("checkstand/mchntpay/orderVailFail");
        String respCode = request.getParameter("code");
        mv.addObject("txnResp", Constants.TransRespEnum.findNameByCode(respCode));
        return mv;
    }

    /**
     * 商户 退款
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/translogRefundCommit")
    @ResponseBody
    public ResultHtml translogRefundCommit(HttpServletRequest request) {
//		String channel = request.getParameter("channel");
//		String txnPrimaryKey = request.getParameter("txnPrimaryKey");
//		String phoneNumber = request.getParameter("phoneNumber");
//		String phoneCode = request.getParameter("phoneCode");
//		String sign = request.getParameter("sign");4028C080B7E9A12CCE909A1357903A1C
        String transreq = request.getParameter("transreq");
//		String channel = "40006001";
//		String txnPrimaryKey = "2018011613305700141950";
//		String phoneNumber = "15073940405";
//		String phoneCode = "209102";

        TranslogRefundReq req = JSONArray.parseObject(transreq, TranslogRefundReq.class);
//				new TranslogRefundReq();
//		req.setChannel(channel);
//		req.setTxnPrimaryKey(txnPrimaryKey);
//		req.setPhoneNumber(phoneNumber);
//		req.setPhoneCode(phoneCode);
//		String sign = ChannelSignUtil.genSign(req);
//		req.setSign(sign);

        return wxTransOrderService.doTranslogRefund(req, request);
    }

    /**
     * 退款接口（知了企服商城退款接口）
     *
     * @param request
     * @param obj
     * @return
     * @author xiaomei
     */
    @RequestMapping(value = "/transOrderRefund", method = RequestMethod.POST)
    @ResponseBody
    public String transOrderRefund(HttpServletRequest request, @RequestBody JSONObject obj) {
        OrderRefund req = (OrderRefund) JSONObject.toBean(obj, OrderRefund.class);
        String refundResp = wxTransOrderService.transOrderRefund(req);
        logger.info("退款接口--->返回参数[{}]", refundResp);
        return refundResp;
    }

}
