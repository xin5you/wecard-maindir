package com.cn.thinkx.integrationpay.shenxinpay.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cn.thinkx.biz.translog.service.IntfaceTransLogService;
import com.cn.thinkx.facade.bean.IntegrationPayRequest;
import com.cn.thinkx.integrationpay.base.entity.IntegrationPayReq;
import com.cn.thinkx.integrationpay.base.entity.IntegrationPayResp;
import com.cn.thinkx.integrationpay.shenxinpay.utils.PaymentBuildUtil;
import com.cn.thinkx.pms.base.redis.core.JedisClusterUtils;
import com.cn.thinkx.pms.base.redis.util.RedisConstants;
import com.cn.thinkx.pms.base.redis.util.RedisDictProperties;
import com.cn.thinkx.pms.base.redis.vo.PaymentChannelApiVO;
import com.cn.thinkx.pms.base.redis.vo.PaymentChannelVO;
import com.cn.thinkx.pms.base.utils.BaseConstants.PaymentType;
import com.cn.thinkx.pms.base.utils.BaseIntegrationPayConstants;
import com.cn.thinkx.pms.base.utils.BaseIntegrationPayConstants.RefundType;
import com.cn.thinkx.pms.base.utils.BaseIntegrationPayConstants.SponsorCode;
import com.cn.thinkx.pms.base.utils.BaseIntegrationPayConstants.TransCode;
import com.cn.thinkx.pms.base.utils.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

/**
 * 申鑫支付交易实现
 *
 * @author pucker
 */
public class ShenXinPayService {
    private static Logger logger = LoggerFactory.getLogger(ShenXinPayService.class);

    @Autowired
    @Qualifier("intfaceTransLogService")
    private IntfaceTransLogService intfaceTransLogService;

    /**
     * 申鑫支付消费接口
     *
     * @param termSwtFlowNo 终端流水号
     * @param transAmt      上送交易金额
     * @param paymentType   支付类型
     * @param authInfo      条码信息
     * @param orderDesc     订单描述
     * @return
     */
    public IntegrationPayResp payment(IntegrationPayRequest req) {
        String txnType = getPaymentType(req.getPaymentType());
        String instId = RedisDictProperties.getInstance().getdictValueByCode("INT_SX_PAY_INSID");
        String mchntNo = RedisDictProperties.getInstance().getdictValueByCode("INT_SX_PAY_MCHNTID");
        logger.info("请求申鑫支付消费接口参数[{}]", JSONArray.toJSON(req));
        String[] keys = {"INSTID", "USRID", "OUTORDERID", "TXAMT", "BODY", "TXNTYPE", "NOTIFYURL", "SCENE", "AUTHCODE",
                "SUBJECT"};
        String[] params = {instId, mchntNo, req.getTermSwtFlowNo(),
                NumberUtils.RMBCentToYuan(req.getTransAmt()), "知了企服", txnType, "", "1", req.getAuthInfo(),
                req.getOrderDesc()};
        try {
            String service = httpUrl(req.getTermChnlNo(), req.getTransId());
            logger.info("调用申鑫支付消费接口地址[{}]", service);
            String returnStr = PaymentBuildUtil.buildPay(keys, params, service);
            logger.info("调用申鑫支付消费接口返回参数[{}]", returnStr);
            IntegrationPayResp payResp = PaymentBuildUtil.parseSXPayReturnXml(returnStr.trim());
            if (BaseIntegrationPayConstants.SX_PAYMENT_SUCCESS.equals(payResp.getCode())) {
                String[] queryKeys = {"ORD_ID", "TXNTYPE"};
                String[] queryParams = {payResp.getSwtFlowNo(), txnType};
                String queryService = httpUrl(req.getTermChnlNo(), TransCode.CW77.getCode());
                String queryReturnStr = PaymentBuildUtil.buildPay(queryKeys, queryParams, queryService);
                IntegrationPayResp queryResp = PaymentBuildUtil.parseSXQueryReturnXml(queryReturnStr.trim());
                logger.info("调用申鑫订单查询返回参数---->>[{}],封装参数[{}]", queryReturnStr, JSONArray.toJSONString(queryResp));
                if (queryResp.getPayStatus() == null || !BaseIntegrationPayConstants.SX_PAYMENT_TYPE.equals(queryResp.getPayStatus())) {
                    payResp.setCode(BaseIntegrationPayConstants.RESPONSE_EXCEPTION_CODE);
                    payResp.setInfo(queryResp.getRespMsg());
                }
                return payResp;
            }
        } catch (Exception e) {
            logger.error("## 调用申鑫支付消费接口异常，终端流水号[{}]", req.getTermSwtFlowNo(), e);
        }
        return null;
    }

    /**
     * 申鑫退款接口
     *
     * @param req
     * @return
     */
    public IntegrationPayResp refund(IntegrationPayReq req) {
        logger.info("## 调用申鑫退款接口请求参数[{}],封装参数[{}]", JSONArray.toJSONString(req));
        String sponsor = getSponsorType(req.getPaymentType());
        // 机构号 商户号 终端号 机构订单号 交易金额 流水号 原交易日期
        String instId = RedisDictProperties.getInstance().getdictValueByCode("INT_SX_REFUND_INSID");
        String mid = RedisDictProperties.getInstance().getdictValueByCode("INT_SX_REFUND_MCHNTID");
        String tid = RedisDictProperties.getInstance().getdictValueByCode("INT_SX_REFUND_TERMID");
        String[] keys = {"instId", "mid", "tid", "instOrderId", "refundAmt", "platformSeq", "oriTradeDate"};
        String[] params = {instId, mid, tid, req.getSwtFlowNo(), NumberUtils.RMBCentToYuan(req.getTransAmt()), req.getOrderNo(),
                req.getSettleDate()};
        // 批次号 渠道标志 异步URL 版本号 介质 请求日期 请求时间
        String[] keysAddi = {"batchNo", "channelFlag", "notifyURL", "version", "media", "requestDate", "requestTime"};
        String[] paramsAddi = {"", sponsor, "hkb", "1.0.0", "app", req.getReqDate(), req.getReqTime()};
        try {
            String service = httpUrl(req.getTermChnlNo(), req.getTransId());
            String returnStr = PaymentBuildUtil.buildRefund(keys, params, service, keysAddi, paramsAddi);
            IntegrationPayResp refundResp = PaymentBuildUtil.parseSXRefund(returnStr.trim());
            logger.info("## 调用申鑫退款接口返回参数[{}],封装参数[{}]", JSONArray.toJSONString(returnStr), JSONArray.toJSONString(refundResp));
            if (BaseIntegrationPayConstants.SX_REFUND_SUCCESS.equals(refundResp.getCode())) {
                String[] queryKeys = {"ORD_ID", "TXNTYPE"};
                String[] queryParams = {refundResp.getTraceId(), sponsor};
                String queryService = httpUrl(req.getTermChnlNo(), TransCode.CW77.getCode());
                String queryReturnStr = PaymentBuildUtil.buildPay(queryKeys, queryParams, queryService);
                IntegrationPayResp queryResp = PaymentBuildUtil.parseSXQueryReturnXml(queryReturnStr.trim());
                logger.info("调用申鑫订单查询返回参数---->>[{}],封装参数[{}]", queryReturnStr, JSONArray.toJSONString(queryResp));
                if (queryResp.getPayStatus() == null || !BaseIntegrationPayConstants.SX_PAYMENT_TYPE.equals(queryResp.getPayStatus())) {
                    refundResp.setCode(BaseIntegrationPayConstants.RESPONSE_EXCEPTION_CODE);
                    refundResp.setInfo(queryResp.getRespMsg());
                }
                return refundResp;
            }
        } catch (Exception e) {
            logger.error("## 调用申鑫支付退款接口异常，外部流水号[{}]", req.getSwtFlowNo(), e);
        }
        return null;
    }

    /**
     * 订单查询
     *
     * @param req
     * @return
     */
    public IntegrationPayResp search(IntegrationPayReq req) {
        String txnType = getPaymentType(req.getPaymentType());
        String[] keys = {"ORD_ID", "TXNTYPE"};
        String[] params = {req.getSwtFlowNo(), txnType};
        try {
            String service = httpUrl(req.getTermChnlNo(), req.getTransId());
            String returnStr = PaymentBuildUtil.buildPay(keys, params, service);
            logger.info("订单查询请求参数---->>[{}]", returnStr);
            return PaymentBuildUtil.parseSXQueryReturnXml(returnStr.trim());
        } catch (Exception e) {
            logger.error("## 调用申鑫查询接口异常，外部流水号[{}]", req.getSwtFlowNo(), e);
        }
        return null;
    }

    /**
     * @param paymentType 支付方式
     * @return
     */
    public static String getPaymentType(String paymentType) {
        String txnType;
        if (PaymentType.WXPAY.getValue().equals(paymentType) || SponsorCode.SPONSOR30.toString().equals(paymentType))
            txnType = "05";
        else if (PaymentType.ALIPAY.getValue().equals(paymentType) || SponsorCode.SPONSOR40.toString().equals(paymentType))
            txnType = "06";
        else if (PaymentType.UNIONPAY.getValue().equals(paymentType) || SponsorCode.SPONSOR50.toString().equals(paymentType))
            txnType = "07";
        else
            txnType = PaymentType.INVALID.getValue();
        return txnType;
    }

    /**
     * @param paymentType 支付方式
     * @return
     */
    public static String getSponsorType(String sponsor) {
        String txnType;
        if (PaymentType.WXPAY.getValue().equals(sponsor) || SponsorCode.SPONSOR30.toString().equals(sponsor))
            txnType = RefundType.WXPAY.getValue();
        else if (PaymentType.ALIPAY.getValue().equals(sponsor) || SponsorCode.SPONSOR40.toString().equals(sponsor))
            txnType = RefundType.ALIPAY.getValue();
        else if (PaymentType.UNIONPAY.getValue().equals(sponsor) || SponsorCode.SPONSOR50.toString().equals(sponsor))
            txnType = RefundType.UNIONPAY.getValue();
        else
            txnType = RefundType.INVALID.getValue();
        return txnType;
    }

    /**
     * @param termChnlNo 通道号
     * @param transId    交易类型
     * @return
     */
    public String httpUrl(String termChnlNo, String transId) {
        String url = null;
        PaymentChannelVO payment = null;
        try {
            String str = JedisClusterUtils.getInstance().hget(RedisConstants.REDIS_HASH_TABLE_TB_PAYMENT_CHANNELS_INF,
                    termChnlNo);
            payment = JSONObject.parseObject(str, PaymentChannelVO.class);
            for (PaymentChannelApiVO paymentAPI : payment.getApiList()) {
                if (transId.equals(paymentAPI.getApiType())) {
                    url = paymentAPI.getUrl();
                    break;
                }
            }
        } catch (Exception e) {
            logger.error("通道信息[{}]", JSONArray.toJSON(payment), e);
        }
        return url;
    }

}
