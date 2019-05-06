package com.cn.thinkx.wecard.api.module.customer.controller;

import com.alibaba.fastjson.JSONArray;
import com.cn.thinkx.common.service.module.channel.domain.ChannelSecurityInf;
import com.cn.thinkx.facade.bean.CardBalQueryRequest;
import com.cn.thinkx.facade.bean.CardTransDetailQueryRequest;
import com.cn.thinkx.facade.bean.CusCardOpeningRequest;
import com.cn.thinkx.facade.bean.RechargeTransRequest;
import com.cn.thinkx.facade.bean.base.BaseTxnReq;
import com.cn.thinkx.facade.service.HKBTxnFacade;
import com.cn.thinkx.pms.base.redis.util.ChannelSignUtil;
import com.cn.thinkx.pms.base.utils.BaseConstants;
import com.cn.thinkx.wecard.api.module.core.domain.TxnResp;
import com.cn.thinkx.wecard.api.module.customer.util.ChannelInfRedisCacheUtil;
import com.cn.thinkx.wecard.api.module.customer.util.CustomerUserReqValid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("customer/card")
public class CustomerCardController {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    @Qualifier("hkbTxnFacade")
    private HKBTxnFacade hkbTxnFacade;

    /**
     * 客户开卡接口
     *
     * @param request
     * @return
     */
    @RequestMapping("/cardOpening")
    @ResponseBody
    public String customerCardOpening(HttpServletRequest request) {
        TxnResp resp = new TxnResp();

        String swtTxnDate = request.getParameter("swtTxnDate"); // 交易时间 yyyyMMdd
        String swtTxnTime = request.getParameter("swtTxnTime"); // 交易时间 HHmmss
        String swtSettleDate = request.getParameter("swtSettleDate"); // 清算时间
        // yyyyMMdd
        String channel = request.getParameter("channel"); // 渠道号
        String innerMerchantNo = request.getParameter("innerMerchantNo"); // 商户号
        String userId = request.getParameter("userId"); // 用户ID
        String sign = request.getParameter("sign"); // 签名

        CusCardOpeningRequest req = new CusCardOpeningRequest();
        req.setSwtTxnDate(swtTxnDate);
        req.setSwtTxnTime(swtTxnTime);
        req.setSwtSettleDate(swtSettleDate);
        req.setChannel(channel);
        req.setInnerMerchantNo(innerMerchantNo);
        req.setUserId(userId);
        req.setSign(sign);

        logger.info("CustomerCardController.customerCardOpening req param jsonStr is -------------->"
                + JSONArray.toJSONString(req));

        // 获取渠道信息
        ChannelSecurityInf channelInf = ChannelInfRedisCacheUtil.getChannelSecurityInfByCode(channel);

        // 参数校验
        if (CustomerUserReqValid.cusCardOpeningValid(req, resp, channelInf)) {
            return JSONArray.toJSONString(resp);
        }

        // 重生生成签名
        req.setTimestamp(System.currentTimeMillis());
        req.setSign(ChannelSignUtil.genSign(req));

        String resultJsonStr = "";
        try {
            resultJsonStr = hkbTxnFacade.customerCardOpeningITF(req);
        } catch (Exception e) {
            logger.error("客户开卡-->" + BaseConstants.RESPONSE_EXCEPTION_INFO, e);
        }

        return resultJsonStr;
    }

    /**
     * 充值交易接口
     *
     * @param request
     * @return
     */
    @RequestMapping("/rechTransaction")
    @ResponseBody
    public String rechargeTransaction(HttpServletRequest request) {
        TxnResp resp = new TxnResp();

        String swtTxnDate = request.getParameter("swtTxnDate"); // 交易时间 yyyyMMdd
        String swtTxnTime = request.getParameter("swtTxnTime"); // 交易时间 HHmmss
        String swtSettleDate = request.getParameter("swtSettleDate"); // 清算时间
        // yyyyMMdd
        String channel = request.getParameter("channel"); // 渠道号
        String swtFlowNo = request.getParameter("swtFlowNo"); // 流水号
        String innerMerchantNo = request.getParameter("innerMerchantNo"); // 商户号
        String commodityNum = request.getParameter("commodityNum"); // 商品数量
        String cardNo = request.getParameter("cardNo"); // 卡号
        String txnAmount = request.getParameter("txnAmount"); // 交易金额
        String oriTxnAmount = request.getParameter("oriTxnAmount"); // 原交易金额
        String sign = request.getParameter("sign"); // 签名

        RechargeTransRequest req = new RechargeTransRequest();
        req.setSwtTxnDate(swtTxnDate);
        req.setSwtTxnTime(swtTxnTime);
        req.setSwtSettleDate(swtSettleDate);
        req.setChannel(channel);
        req.setSwtFlowNo(swtFlowNo);
        req.setInnerMerchantNo(innerMerchantNo);
        req.setCommodityNum(commodityNum);
        req.setCardNo(cardNo);
        req.setTxnAmount(txnAmount);
        req.setOriTxnAmount(oriTxnAmount);
        req.setSign(sign);

        logger.info("CustomerCardController.rechargeTransaction req param jsonStr is -------------->"
                + JSONArray.toJSONString(req));
        // 获取渠道信息
        ChannelSecurityInf channelInf = ChannelInfRedisCacheUtil.getChannelSecurityInfByCode(channel);

        // 参数校验
        if (CustomerUserReqValid.rechargeTransValid(req, resp, channelInf)) {
            return JSONArray.toJSONString(resp);
        }
        // 重新生成签名
        req.setTimestamp(System.currentTimeMillis());
        req.setSign(ChannelSignUtil.genSign(req));

        String resultJsonStr = "";
        try {
            resultJsonStr = hkbTxnFacade.rechargeTransactionITF(req);
        } catch (Exception e) {
            logger.error("充值交易-->" + BaseConstants.RESPONSE_EXCEPTION_INFO, e);
        }
        return resultJsonStr;
    }

    /**
     * 会员卡余额查询接口
     *
     * @param request
     * @return
     */
    @RequestMapping("/cardBalaQuery")
    @ResponseBody
    public String cardBalanceQuery(HttpServletRequest request) {
        TxnResp resp = new TxnResp();

        String channel = request.getParameter("channel"); // 渠道号
        String userId = request.getParameter("userId"); // 用户ID
        String innerMerchantNo = request.getParameter("innerMerchantNo"); // 商户号
        String sign = request.getParameter("sign"); // 签名

        CardBalQueryRequest req = new CardBalQueryRequest();
        req.setChannel(channel);
        req.setUserId(userId);
        req.setInnerMerchantNo(innerMerchantNo);
        req.setSign(sign);

        logger.info("CustomerCardController.cardBalanceQuery req param jsonStr is -------------->"
                + JSONArray.toJSONString(req));
        // 获取渠道信息
        ChannelSecurityInf channelInf = ChannelInfRedisCacheUtil.getChannelSecurityInfByCode(channel);

        // 参数校验
        if (CustomerUserReqValid.cardBalQueryValid(req, resp, channelInf)) {
            return JSONArray.toJSONString(resp);
        }
        // 重新生成签名
        req.setTimestamp(System.currentTimeMillis());
        req.setSign(ChannelSignUtil.genSign(req));

        String resultJsonStr = "";

        try {
            resultJsonStr = hkbTxnFacade.cardBalanceQueryITF(req);
        } catch (Exception e) {
            logger.error("会员卡余额查询-->" + BaseConstants.RESPONSE_EXCEPTION_INFO, e);
        }

        return resultJsonStr;
    }

    /**
     * 会员卡消费交易接口
     *
     * @param request
     * @return
     */
    @RequestMapping("/conTransaction")
    @ResponseBody
    public String hkbConsumeTransaction(HttpServletRequest request) {
        TxnResp resp = new TxnResp();

        String swtTxnDate = request.getParameter("swtTxnDate"); // 交易时间 yyyyMMdd
        String swtTxnTime = request.getParameter("swtTxnTime"); // 交易时间 HHmmss
        String swtSettleDate = request.getParameter("swtSettleDate"); // 清算时间
        // yyyyMMdd
        String channel = request.getParameter("channel"); // 渠道号
        String swtFlowNo = request.getParameter("swtFlowNo"); // 流水号
        String innerMerchantNo = request.getParameter("innerMerchantNo"); // 商户号
        String innerShopNo = request.getParameter("innerShopNo"); // 门店号
        String cardNo = request.getParameter("cardNo"); // 卡号
        String txnAmount = request.getParameter("txnAmount"); // 交易金额
        String oriTxnAmount = request.getParameter("oriTxnAmount"); // 原交易金额
        String pinTxn = request.getParameter("pinTxn"); // 交易密码
        String sign = request.getParameter("sign"); // 签名

        BaseTxnReq req = new BaseTxnReq();
        req.setSwtTxnDate(swtTxnDate);
        req.setSwtTxnTime(swtTxnTime);
        req.setSwtSettleDate(swtSettleDate);
        req.setChannel(channel);
        req.setSwtFlowNo(swtFlowNo);
        req.setInnerMerchantNo(innerMerchantNo);
        req.setInnerShopNo(innerShopNo);
        req.setCardNo(cardNo);
        req.setTxnAmount(txnAmount);
        req.setOriTxnAmount(oriTxnAmount);
        req.setPinTxn(pinTxn);
        req.setSign(sign);

        logger.info("CustomerCardController.hkbConsumeTransaction req param jsonStr is -------------->"
                + JSONArray.toJSONString(req));
        // 获取渠道信息
        ChannelSecurityInf channelInf = ChannelInfRedisCacheUtil.getChannelSecurityInfByCode(channel);

        // 参数校验
        if (CustomerUserReqValid.hkbConsumeTransValid(req, resp, channelInf)) {
            return JSONArray.toJSONString(resp);
        }
        // 重新生产签名
        req.setTimestamp(System.currentTimeMillis());
        req.setSign(ChannelSignUtil.genSign(req));
        String resultJsonStr = "";

        try {
            resultJsonStr = hkbTxnFacade.hkbConsumeTransactionITF(req);
        } catch (Exception e) {
            logger.error("会员卡消费交易-->" + BaseConstants.RESPONSE_EXCEPTION_INFO, e);
        }

        return resultJsonStr;
    }

    /**
     * 快捷支付交易接口
     *
     * @param request
     * @return
     */
    @RequestMapping("/quickPayTransaction")
    @ResponseBody
    public String quickPayMentTransaction(HttpServletRequest request) {
        TxnResp resp = new TxnResp();

        String swtTxnDate = request.getParameter("swtTxnDate"); // 交易时间 yyyyMMdd
        String swtTxnTime = request.getParameter("swtTxnTime"); // 交易时间 HHmmss
        String swtSettleDate = request.getParameter("swtSettleDate"); // 清算时间
        // yyyyMMdd
        String channel = request.getParameter("channel"); // 渠道号
        String swtFlowNo = request.getParameter("swtFlowNo"); // 流水号
        String innerMerchantNo = request.getParameter("innerMerchantNo"); // 商户号
        String innerShopNo = request.getParameter("innerShopNo"); // 门店号
        String cardNo = request.getParameter("cardNo"); // 卡号
        String txnAmount = request.getParameter("txnAmount"); // 交易金额
        String oriTxnAmount = request.getParameter("oriTxnAmount"); // 原交易金额
        String pinTxn = request.getParameter("pinTxn"); // 交易密码
        String sign = request.getParameter("sign"); // 签名

        BaseTxnReq req = new BaseTxnReq();
        req.setSwtTxnDate(swtTxnDate);
        req.setSwtTxnTime(swtTxnTime);
        req.setSwtSettleDate(swtSettleDate);
        req.setChannel(channel);
        req.setSwtFlowNo(swtFlowNo);
        req.setInnerMerchantNo(innerMerchantNo);
        req.setInnerShopNo(innerShopNo);
        req.setCardNo(cardNo);
        req.setTxnAmount(txnAmount);
        req.setOriTxnAmount(oriTxnAmount);
        req.setPinTxn(pinTxn);
        req.setSign(sign);

        logger.info("CustomerCardController.quickPayMentTransaction req param jsonStr is -------------->"
                + JSONArray.toJSONString(req));
        // 获取渠道信息
        ChannelSecurityInf channelInf = ChannelInfRedisCacheUtil.getChannelSecurityInfByCode(channel);

        // 参数校验
        if (CustomerUserReqValid.quickPayTransValid(req, resp, channelInf)) {
            return JSONArray.toJSONString(resp);
        }
        // 重新生产签名
        req.setTimestamp(System.currentTimeMillis());
        req.setSign(ChannelSignUtil.genSign(req));
        String resultJsonStr = "";

        try {
            resultJsonStr = hkbTxnFacade.quickPaymentTransactionITF(req);
        } catch (Exception e) {
            logger.error("快捷支付交易-->" + BaseConstants.RESPONSE_EXCEPTION_INFO, e);
        }
        return resultJsonStr;
    }

    /**
     * 会员卡交易明细查询接口
     *
     * @param request
     * @return
     */
    @RequestMapping("/cardTransDetailQuery")
    @ResponseBody
    public String cardTransDetailQuery(HttpServletRequest request) {
        TxnResp resp = new TxnResp();

        String channel = request.getParameter("channel"); // 渠道号
        String userId = request.getParameter("userId"); // 用户ID
        String innerMerchantNo = request.getParameter("innerMerchantNo"); // 商户号
        String pageNum = request.getParameter("pageNum"); // 页码
        String itemSize = request.getParameter("itemSize"); // 每页条数
        String sign = request.getParameter("sign"); // 签名

        CardTransDetailQueryRequest req = new CardTransDetailQueryRequest();

        req.setChannel(channel);
        req.setUserId(userId);
        req.setInnerMerchantNo(innerMerchantNo);
        req.setPageNum(pageNum);
        req.setItemSize(itemSize);
        req.setSign(sign);

        logger.info("CustomerCardController.cardTransDetailQuery req param jsonStr is -------------->"
                + JSONArray.toJSONString(req));
        // 获取渠道信息
        ChannelSecurityInf channelInf = ChannelInfRedisCacheUtil.getChannelSecurityInfByCode(channel);

        // 参数校验
        if (CustomerUserReqValid.cardTransDetailQueryValid(req, resp, channelInf)) {
            return JSONArray.toJSONString(resp);
        }
        // 重新生产签名
        req.setTimestamp(System.currentTimeMillis());
        req.setSign(ChannelSignUtil.genSign(req));

        String resultJsonStr = "";

        try {
            resultJsonStr = hkbTxnFacade.cardTransDetailQueryITF(req);
        } catch (Exception e) {
            logger.error("会员卡交易明细查询-->" + BaseConstants.RESPONSE_EXCEPTION_INFO, e);
        }

        return resultJsonStr;
    }

    /**
     * 交易异常处理查询接口
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/transExceptionQuery")
    @ResponseBody
    public String transExceptionQuery(HttpServletRequest request) {
        TxnResp resp = new TxnResp();

        String channel = request.getParameter("channel"); // 渠道号
        String swtFlowNo = request.getParameter("swtFlowNo"); // 流水号
        String sign = request.getParameter("sign"); // 签名

        BaseTxnReq req = new BaseTxnReq();
        req.setChannel(channel);
        req.setSwtFlowNo(swtFlowNo);
        req.setSign(sign);

        logger.info("CustomerCardController.transExceptionQuery req param jsonStr is -------------->"
                + JSONArray.toJSONString(req));
        // 获取渠道信息
        ChannelSecurityInf channelInf = ChannelInfRedisCacheUtil.getChannelSecurityInfByCode(channel);

        // 参数校验
        if (CustomerUserReqValid.transExceptionQueryValid(req, resp, channelInf)) {
            return JSONArray.toJSONString(resp);
        }

        // 重新生产签名
        req.setTimestamp(System.currentTimeMillis());
        req.setSign(ChannelSignUtil.genSign(req));

        String resultJsonStr = "";

        try {
            resultJsonStr = hkbTxnFacade.transExceptionQueryITF(req);
        } catch (Exception e) {
            logger.error("交易异常处理查询-->" + BaseConstants.RESPONSE_EXCEPTION_INFO, e);
        }

        return resultJsonStr;
    }

}
