package com.cn.thinkx.wecard.customer.module.dingchi.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.cn.thinkx.beans.CtrlSystem;
import com.cn.thinkx.beans.TxnPackageBean;
import com.cn.thinkx.common.activemq.service.WechatMQProducerService;
import com.cn.thinkx.common.wecard.domain.shop.ShopInf;
import com.cn.thinkx.common.wecard.domain.trans.InterfaceTrans;
import com.cn.thinkx.common.wecard.domain.trans.WxTransLog;
import com.cn.thinkx.pms.base.http.HttpClientUtil;
import com.cn.thinkx.pms.base.redis.util.*;
import com.cn.thinkx.pms.base.service.MessageService;
import com.cn.thinkx.pms.base.utils.*;
import com.cn.thinkx.pms.base.utils.BaseConstants.*;
import com.cn.thinkx.service.txn.Java2TxnBusinessFacade;
import com.cn.thinkx.wecard.customer.module.checkstand.util.Constants;
import com.cn.thinkx.wecard.customer.module.customer.service.CtrlSystemService;
import com.cn.thinkx.wecard.customer.module.customer.service.WxTransLogService;
import com.cn.thinkx.wecard.customer.module.dingchi.service.UnicomAyncService;
import com.cn.thinkx.wecard.customer.module.dingchi.utils.DCUtils;
import com.cn.thinkx.wecard.customer.module.dingchi.vo.UnicomAyncNotify;
import com.cn.thinkx.wecard.customer.module.dingchi.vo.UnicomAyncReq;
import com.cn.thinkx.wecard.customer.module.dingchi.vo.UnicomAyncResp;
import com.cn.thinkx.wecard.customer.module.merchant.service.ShopInfService;
import com.cn.thinkx.wecard.customer.module.merchant.service.TransLogService;
import com.cn.thinkx.wecard.customer.module.pub.domain.TxnResp;
import com.cn.thinkx.wechat.base.wxapi.process.WxMemoryCacheClient;
import com.cn.thinkx.wechat.base.wxapi.util.WXTemplateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCluster;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Date;

@Service("unicomAyncService")
public class UnicomAyncServiceImpl implements UnicomAyncService {
    Logger logger = LoggerFactory.getLogger(UnicomAyncServiceImpl.class);

    @Autowired
    private CtrlSystemService ctrlSystemService;

    @Autowired
    private WxTransLogService wxTransLogService;

    @Autowired
    private JedisCluster jedisCluster;

    @Autowired
    private Java2TxnBusinessFacade java2TxnBusinessFacade;

    @Autowired
    private WechatMQProducerService wechatMQProducerService;

    @Autowired
    private MessageService messageService;

    @Autowired
    private TransLogService transLogService;

    @Autowired
    private ShopInfService shopInfService;
	
	/*@Autowired
	private CardKeysProductService cardKeysProductService;*/

    private static final String DINGCHI_LL_USERID = RedisPropertiesUtils.getProperty("DINGCHI_LL_USERID");
    private static final String DINGCHI_LL_KEY = RedisPropertiesUtils.getProperty("DINGCHI_LL_KEY");
    private static final String DINGCHI_HF_USERID = RedisPropertiesUtils.getProperty("DINGCHI_HF_USERID");
    private static final String DINGCHI_HF_KEY = RedisPropertiesUtils.getProperty("DINGCHI_HF_KEY");

    @Override
    public UnicomAyncResp buy(UnicomAyncReq vo, HttpServletRequest request) {
        String mobile = request.getParameter("mobile");// 用户手机号
        String itemId = request.getParameter("itemId");// 商品编号
        String type = request.getParameter("type");// 充值类型(1：话费、2：流量)

        if (vo == null) {
            logger.error("## UnicomAyncService--->buy()方法请求参数 UnicomAyncReq为空");
            return null;
        }
        if (StringUtil.isNullOrEmpty(type)) {
            logger.error("## UnicomAyncService--->buy()方法请求参数 type 为空");
            return null;
        }
        if (StringUtil.isNullOrEmpty(itemId)) {
            logger.error("## UnicomAyncService--->buy()方法请求参数 itemId 为空");
            return null;
        }
        if (StringUtil.isNullOrEmpty(mobile)) {
            logger.error("## UnicomAyncService--->buy()方法请求参数 mobile 为空");
            return null;
        }

        /*itemId = "11111111";*/

        vo.setUid(mobile);
        vo.setItemId(itemId);

        String DINGCHI_URL = jedisCluster.hget(RedisConstants.REDIS_HASH_TABLE_TB_BASE_DICT_KV, BaseConstants.DINGCHI_HTTP_URL);
        String DINGCHI_BUY = jedisCluster.hget(RedisConstants.REDIS_HASH_TABLE_TB_BASE_DICT_KV, BaseConstants.DINGCHI_BUY_URL);

        String result = null;
        if ("1".equals(type)) {// 充值话费
            vo.setUserId(DINGCHI_HF_USERID);
            vo.setSign(DCUtils.genSign(vo, DINGCHI_HF_KEY));
        }
        if ("2".equals(type)) {// 充值流量
            vo.setUserId(DINGCHI_LL_USERID);
            vo.setSign(DCUtils.genSign(vo, DINGCHI_LL_KEY));
        }

        String url = DCUtils.genUrl(vo, DINGCHI_URL + DINGCHI_BUY); // 请求GET路径(带参数)
        logger.info("请求鼎驰直充接口URL[{}]", url);
        result = HttpClientUtil.sendGet(url);
        return JSONArray.parseObject(result, UnicomAyncResp.class);
    }

    @Override
    public String hkbPayment(HttpServletRequest request) {
        String openid = WxMemoryCacheClient.getOpenid(request);
        String itemId = request.getParameter("itemId");// 商品编号
        String mobile = request.getParameter("mobile");// 用户手机号
        String type = request.getParameter("type");// 充值类型，1：话费、2：流量

        if (StringUtil.isNullOrEmpty(openid)) {
            logger.error("## 薪无忧支付接口,获取用户openid为空");
            return null;
        }
        if (StringUtil.isNullOrEmpty(mobile)) {
            logger.error("## 薪无忧支付接口,获取充值mobile为空");
            return null;
        }
        if (StringUtil.isNullOrEmpty(itemId)) {
            logger.error("## 薪无忧支付接口,获取充值itemId为空");
            return null;
        }

        int phoneType = DCUtils.matchMobPhnNo(mobile);
        String transAmount = null;
		/*if (phoneType == 1) {
			transAmount = YDFlowType.findByItemId(itemId).getAmount();
		} else if (phoneType == 2) {
			transAmount = LTFlowType.findByItemId(itemId).getAmount();
		} else if (phoneType == 3) {
			transAmount = DXFlowType.findByItemId(itemId).getAmount();
		} else {
			logger.error("##薪无忧支付接口，根据手机号查询所属运营商不存在");
			return null;
		}*/
        transAmount = NumberUtils.RMBYuanToCent(transAmount);

        String channel = null;
        if (StringUtil.equals("1", type)) {// 充值话费
            channel = ChannelCode.CHANNEL8.toString();
        } else if (StringUtil.equals("2", type)) {// 充值流量
            channel = ChannelCode.CHANNEL9.toString();
        } else {
            logger.error("## 薪无忧支付接口,获取充值类型为空");
            return null;
        }

        String mchntCode = jedisCluster.hget(RedisConstants.REDIS_HASH_TABLE_TB_BASE_DICT_KV, BaseConstants.ACC_HKB_MCHNT_NO);
        String shopCode = jedisCluster.hget(RedisConstants.REDIS_HASH_TABLE_TB_BASE_DICT_KV, BaseConstants.ACC_HKB_SHOP_NO);
        String insCode = jedisCluster.hget(RedisConstants.REDIS_HASH_TABLE_TB_BASE_DICT_KV, BaseConstants.ACC_HKB_INS_CODE);
        CtrlSystem cs = ctrlSystemService.getCtrlSystem();// 得到日切信息
        if (cs == null) {
            logger.error("## 薪无忧支付接口，日切信息为空");
            return null;
        }
        if (!BaseConstants.TRANS_FLAG_YES.equals(cs.getTransFlag())) {
            logger.error("## 薪无忧支付接口，日切状态不允许");
            return null;
        }
        // 日切状态为允许时，插入微信端流水
        WxTransLog log = new WxTransLog();
        String wxPrimaryKey = wxTransLogService.getPrimaryKey();
        log.setWxPrimaryKey(wxPrimaryKey);
        log.setTableNum(cs.getCurLogNum());// 得到当前可以进行操作的流水表号
        log.setSettleDate(cs.getSettleDate());// 交易日期
        log.setTransId(TransCode.CW10.getCode());// 客户消费
        log.setTransSt(0);// 插入时为0，报文返回时更新为1
        log.setInsCode(insCode);
        log.setMchntCode(mchntCode);
        log.setShopCode(shopCode);
        log.setSponsor(SponsorCode.SPONSOR00.toString());
        log.setUserInfUserName(openid);
        log.setOperatorOpenId(openid);
        log.setTransAmt(transAmount);// 卡面面额
        log.setUploadAmt(transAmount);// 上送金额
        log.setTransCurrCd(BaseConstants.TRANS_CURR_CD);
        log.setTransChnl(channel);
        int i = wxTransLogService.insertWxTransLog(log);// 插入业务流水(微信端)
        if (i != 1) {
            logger.error("## 薪无忧支付接口--->insertWxTransLog微信端插入流水记录数量不为1");
            return null;
        }

        TxnPackageBean txnBean = new TxnPackageBean();
        TxnResp txnResp = new TxnResp();
        txnBean.setTxnType(TransCode.CW10.getCode() + "0");// 交易类型，发送报文时补0
        txnBean.setSwtTxnDate(DateUtil.getCurrentDateStr());// 交易日期
        txnBean.setSwtTxnTime(DateUtil.getCurrentTimeStr());// 交易时间
        txnBean.setSwtSettleDate(log.getSettleDate());// 清算日期
        txnBean.setSwtFlowNo(log.getWxPrimaryKey());
        txnBean.setIssChannel(log.getInsCode());// 机构渠道号
        txnBean.setInnerMerchantNo(log.getMchntCode());// 商户号
        txnBean.setInnerShopNo(log.getShopCode());// 门店号
        txnBean.setTxnAmount(log.getTransAmt());// 交易金额
        txnBean.setOriTxnAmount(log.getUploadAmt());// 原交易金额
        txnBean.setCardNo("U" + openid);// 卡号 U开头为客户端交易，C开头则为刷卡交易
        txnBean.setChannel(log.getTransChnl());// 渠道号
        txnBean.setTimestamp(System.currentTimeMillis());// 时间戳
        txnBean.setResv6("1"); // 表示不需要输入密码
        String signature = TxnChannelSignatureUtil.genSign(txnBean); // 生成的签名
        txnBean.setSignature(signature);
        // 远程调用消费接口
        String json = new String();
        try {
            json = java2TxnBusinessFacade.consumeTransactionITF(txnBean);
            if (!StringUtil.isNullOrEmpty(json))
                txnResp = JSONArray.parseObject(json, TxnResp.class);
            if (txnResp == null || !BaseConstants.TXN_TRANS_RESP_SUCCESS.equals(txnResp.getCode())) {
                json = java2TxnBusinessFacade.transExceptionQueryITF(log.getWxPrimaryKey());
                txnResp = JSONArray.parseObject(json, TxnResp.class);
            }
        } catch (Exception e) {
            logger.error("## 远程调用消费接口异常，返回json[{}]，wxp流水号[{}]", json, log.getWxPrimaryKey(), e);
        }
        try {
            int i2 = wxTransLogService.updateWxTransLog(log, txnResp);
            if (i2 != 1) {
                logger.error("## 薪无忧支付接口--->更新wxp流水失败，wxp流水号[{}]", log.getWxPrimaryKey());
            }
        } catch (Exception e) {
            logger.error("## 薪无忧支付接口--->更新wxp流水异常，wxp流水号[{}]", log.getWxPrimaryKey(), e);
        }
        txnResp.setWxPrimaryKey(wxPrimaryKey);
        logger.info("薪无忧支付接口hkbPayment()返回信息[{}]", JSONArray.toJSONString(txnResp));
        return JSONArray.toJSONString(txnResp);
    }

    @Override
    public String hkbRefund(String refundId, String oriSwtFlowNo) {
        InterfaceTrans itfLog = transLogService.getInterfaceTransByPrimaryKey(oriSwtFlowNo);
        if (StringUtil.isNullOrEmpty(itfLog) || !ITFRespCode.CODE00.getCode().equals(itfLog.getRespCode())) {
            logger.error("## 薪无忧退款接口，查询流水号为[{}],ITF层原交易不存在", oriSwtFlowNo);
            return null;
        }

        if (StringUtil.isNullOrEmpty(refundId))
            refundId = itfLog.getDmsRelatedKey();

        CtrlSystem cs = ctrlSystemService.getCtrlSystem();// 得到日切信息
        if (cs == null || !Constants.TRANS_FLAG_YES.equals(cs.getTransFlag())) {
            logger.error("##申请退款--->日切信息交易允许状态：日切中");
            return null;
        }

        WxTransLog wxLog = new WxTransLog();
        String wxPrimaryKey = wxTransLogService.getPrimaryKey();
        wxLog.setWxPrimaryKey(wxPrimaryKey);
        wxLog.setTableNum(cs.getCurLogNum());// 得到当前可以进行操作的流水表号
        wxLog.setDmsRelatedKey(refundId);
        wxLog.setSettleDate(cs.getSettleDate());// 交易日期
        wxLog.setTransId(TransCode.CW11.getCode());
        wxLog.setTransSt(0);// 插入时为0，报文返回时更新为1
        wxLog.setInsCode(itfLog.getInsCode());
        wxLog.setMchntCode(itfLog.getMchntCode());
        wxLog.setShopCode(itfLog.getShopCode());
        wxLog.setSponsor(SponsorCode.SPONSOR00.toString());
        wxLog.setUserInfUserName(itfLog.getUserInfUserName());
        wxLog.setOperatorOpenId(itfLog.getUserInfUserName());
        wxLog.setTransAmt(itfLog.getTransAmt());// 卡面面额
        wxLog.setUploadAmt(itfLog.getTransAmt());// 上送金额
        wxLog.setTransCurrCd(BaseConstants.TRANS_CURR_CD);
        wxLog.setTransChnl(itfLog.getTransChnl());
        int i = wxTransLogService.insertWxTransLog(wxLog);// 插入业务流水(微信端)
        if (i != 1) {
            logger.error("## 微信交易--->insertWxTransLog微信端插入流水记录数量不为1");
            return null;
        }

        TxnPackageBean txnBean = new TxnPackageBean();
        TxnResp txnResp = new TxnResp();
        try {
            Date currDate = DateUtil.getCurrDate();
            txnBean.setSwtFlowNo(wxLog.getWxPrimaryKey()); //接口平台交易流水号
            txnBean.setTxnType(TransCode.CW11.getCode() + "0");
            txnBean.setSwtTxnDate(DateUtil.getStringFromDate(currDate, DateUtil.FORMAT_YYYYMMDD));
            txnBean.setSwtTxnTime(DateUtil.getStringFromDate(currDate, DateUtil.FORMAT_HHMMSS));
            txnBean.setSwtSettleDate(cs.getSettleDate());// 交易日期
            txnBean.setChannel(itfLog.getTransChnl());   //请求渠道
            txnBean.setIssChannel(itfLog.getInsCode()); // 机构渠道号
            txnBean.setInnerMerchantNo(itfLog.getMchntCode()); // 商户号
            txnBean.setInnerShopNo(itfLog.getShopCode());
            txnBean.setCardNo("U" + itfLog.getUserInfUserName()); // U+UserName
            txnBean.setTxnAmount(itfLog.getTransAmt()); // 交易金额
            txnBean.setOriTxnAmount(itfLog.getTransAmt());
            txnBean.setTimestamp(System.currentTimeMillis());// 时间戳
            txnBean.setOriSwtFlowNo(itfLog.getInterfacePrimaryKey());//原交易流水号
            String sign = SignatureUtil.genB2CTransMsgSignature(txnBean.getSwtSettleDate(),
                    txnBean.getSwtFlowNo(), txnBean.getIssChannel(), txnBean.getInnerMerchantNo(),
                    txnBean.getTxnAmount(), txnBean.getOriSwtFlowNo(), txnBean.getOriTxnAmount(),
                    txnBean.getCardNo(), txnBean.getPinTxn(), txnBean.getTimestamp());
            txnBean.setMac(sign);
            //远程调用支持隔日退款接口
            String json = null;
            try {
                json = java2TxnBusinessFacade.transRefundITF(txnBean); //退款接口
            } catch (Exception e) {
                logger.error("## 退款接口异常，外部退款流水号为[{}]", wxLog.getWxPrimaryKey(), e);
            }
            logger.info("退款接口，接收的返回参数为：[{}]", json);
            //接收薪无忧退款接口返回值，判断是否为空,并调用查询接口判断
            try {
                json = java2TxnBusinessFacade.transExceptionQueryITF(wxLog.getWxPrimaryKey());
            } catch (Exception e) {
                logger.error("## 查询接口失败，外部退款流水号为[{}]", wxLog.getWxPrimaryKey(), e);
            }
            logger.info("查询接口，接收的返回参数为：[{}]", json);
            txnResp = JSONArray.parseObject(json, TxnResp.class);

            try {
                int i2 = wxTransLogService.updateWxTransLog(wxLog, txnResp);// 更新微信端流水
                if (i2 != 1) {
                    logger.error("## 更新流水失败，wxp流水号[{}]", wxLog.getWxPrimaryKey());
                    return null;
                }
            } catch (Exception e) {
                logger.error("## 更新流水异常，wxp流水号[{}]", wxLog.getWxPrimaryKey(), e);
            }
        } catch (Exception e) {
            logger.error("## 申请退款--->系统故障", e);
        } finally {
            try {
                if (!ITFRespCode.CODE00.getCode().equals(txnResp.getCode())) { //退款不成功后发短信给开发人员
                    String phonesStr = RedisDictProperties.getInstance().getdictValueByCode("SYSTEM_MONITORING_USER_PHONES");
                    String[] users = phonesStr.split(",");
                    if (users != null) {
                        Arrays.asList(users).stream().forEach(x ->
                                messageService.sendMessage(x, "【薪无忧】在退款时出现故障,原交易流水号:" + oriSwtFlowNo + ",请及时处理!")
                        );
                    }
                }
            } catch (Exception e) {
                logger.error("申请退款--->系统故障", e);
            }
        }
        //退款成功，发送模板消息
        if (ITFRespCode.CODE00.getCode().equals(txnResp.getCode())) {
            String payAmt = NumberUtils.RMBCentToYuan(itfLog.getTransAmt());
            ShopInf shopInf = shopInfService.getShopInfByCode(itfLog.getShopCode());
            String txnDate = null;
            try {
                txnDate = DateUtil.getChineseDateFormStr(txnBean.getSwtTxnDate() + txnBean.getSwtTxnTime());
            } catch (Exception e) {
                logger.error("## 退款模板消息出错（时间计算异常） ", e);
            }
            wechatMQProducerService.sendTemplateMsg(RedisDictProperties.getInstance().getdictValueByCode("WX_CUSTOMER_ACCOUNT"), itfLog.getUserInfUserName(), "WX_TEMPLATE_ID_4", null,
                    WXTemplateUtil.setRefundData(txnResp.getInterfacePrimaryKey(), shopInf.getMchntName(), shopInf.getShopName(), payAmt, txnDate, templateMsgRefund.findByCode(itfLog.getTransChnl()).getValue(), templateMsgPayment.templateMsgPayment8.getValue()));
        }
        return JSONArray.toJSONString(txnResp);
    }

    @Override
    public UnicomAyncResp hkbQuery(HttpServletRequest req) {
        String DINGCHI_URL = jedisCluster.hget(RedisConstants.REDIS_HASH_TABLE_TB_BASE_DICT_KV, BaseConstants.DINGCHI_HTTP_URL);
        String DINGCHI_QUERY = jedisCluster.hget(RedisConstants.REDIS_HASH_TABLE_TB_BASE_DICT_KV, BaseConstants.DINGCHI_QUERY_URL);

        String userId = req.getParameter("userId");// 合作方用户编号(鼎驰科技方提供)
        String serialno = req.getParameter("serialno");// 合作方商户系统的流水号,全局唯一

        WxTransLog log = wxTransLogService.getWxTransLogById(serialno);// 查询薪无忧业务流水
        if (log == null) {
            logger.error("## 薪无忧查询接口，外部流水号[{}]在wxp流水中不存在", serialno);
            return null;
        }

        UnicomAyncReq vo = new UnicomAyncReq();
        vo.setUserId(userId);
        vo.setSerialno(serialno);
        String key = null;
        if (ChannelCode.CHANNEL8.equals(log.getTransChnl()))
            key = DINGCHI_HF_KEY;
        if (ChannelCode.CHANNEL9.equals(log.getTransChnl()))
            key = DINGCHI_LL_KEY;
        String hkbSign = DCUtils.genSign(vo, key);
        vo.setSign(hkbSign);
        String url = DCUtils.genUrl(vo, DINGCHI_URL + DINGCHI_QUERY); // 请求GET路径(带参数)
        String result = HttpClientUtil.sendGet(DCUtils.genUrl(vo, url));
        UnicomAyncResp resp = JSONArray.parseObject(result, UnicomAyncResp.class);
        return resp;
    }

    @Override
    public String hkbNotify(HttpServletRequest req) {
        String userId = req.getParameter("userId");// 合作方用户编号(鼎驰科技方提供)
        String bizId = req.getParameter("bizId");// 业务编号
        String id = req.getParameter("id");// 鼎驰科技方订单号
        String downstreamSerialno = req.getParameter("downstreamSerialno");// 合作方商户系统的流水号
        String status = req.getParameter("status");// 状态 2 是成功 3 是失败
        String statusDesc = req.getParameter("statusDesc");// 失败时错误信息、如填写不需要作为加密串
        String sign = req.getParameter("sign");// 校验码   (加密方式和 合作方交易的请求加密方式一样，私钥也一样)

        WxTransLog log = wxTransLogService.getWxTransLogById(downstreamSerialno);// 查询薪无忧业务流水
        if (log == null) {
            logger.error("## 薪无忧异步回调接口，查询Wxp流水为空，WxPrimaryKey[{}]");
            return null;
        }

        UnicomAyncNotify vo = new UnicomAyncNotify();
        vo.setUserId(userId);
        vo.setBizId(bizId);
        vo.setId(id);
        vo.setDownstreamSerialno(downstreamSerialno);
        vo.setStatus(status);

        String key = null;
        if (ChannelCode.CHANNEL8.equals(log.getTransChnl())) {
            key = DINGCHI_HF_KEY;
        } else {
            key = DINGCHI_LL_KEY;
        }
        String hkbSign = DCUtils.genSign(vo, key);
        if (!StringUtils.equals(sign, hkbSign)) {// 签名不正确
            logger.error("## 薪无忧异步回调接口，验签失败，鼎驰回调信息[{}]", JSONArray.toJSONString(vo));
            return null;
        }
        if (!StringUtil.isNullOrEmpty(id)) {
            log.setDmsRelatedKey(id);// 鼎驰订单号
            CtrlSystem cs = ctrlSystemService.getCtrlSystem();// 得到日切信息
            if (cs == null) {
                logger.error("## 薪无忧异步回调接口，日切信息为空");
                return null;
            }
            if (!BaseConstants.TRANS_FLAG_YES.equals(cs.getTransFlag())) {
                logger.error("## 薪无忧异步回调接口，日切状态不允许");
                return null;
            }
            log.setTableNum(cs.getCurLogNum());// 得到当前可以进行操作的流水表号
            try {
                int i2 = wxTransLogService.updateWxTransLog(log, null);//更新Wxp流水
                if (i2 != 1) {
                    logger.error("## 薪无忧异步回调接口--->更新wxp外部订单号流水失败，wxp流水号[{}]", log.getWxPrimaryKey());
                }
            } catch (Exception e) {
                logger.error("## 薪无忧异步回调接口--->更新wxp外部订单号流水异常，wxp流水号[{}]", log.getWxPrimaryKey(), e);
            }
        }
        if ("3".equals(status)) {// 交易失败
            logger.error("## 薪无忧异步回调接口，鼎驰充值失败：订单号[{}] 交易流水号[{}] 错误信息[{}]", id, downstreamSerialno, statusDesc);
            //发起退款
            String json = hkbRefund(id, downstreamSerialno);
            logger.info("薪无忧异步回调接口，退款接口返回参数[{}]", json);
            return "ok";
        } else if ("2".equals(status)) {// 交易成功
            logger.info("## 薪无忧异步回调接口，鼎驰充值成功：订单号[{}] 交易流水号[{}]", id, downstreamSerialno);
            return "ok";
        } else {
            logger.error("## 薪无忧异步回调接口，鼎驰回调异常：订单号[{}] status[{}] 错误信息[{}]", id, status, statusDesc);
        }
        return null;
    }
	
	/*@Override
	public List<CardKeysProduct> mobileSign(HttpServletRequest req) {
		String mobile = req.getParameter("mobile");// 用户手机号
		String type = req.getParameter("type");
		int mobileMatch = DCUtils.matchMobPhnNo(mobile);
		CardKeysProduct ckp = new CardKeysProduct();
		if ("1".equals(type)) 
			ckp.setProductType(CardProductType.CP11.getCode());
		else if ("2".equals(type))
			ckp.setProductType(CardProductType.CP12.getCode());
		else 
			return null;
		if (mobileMatch == 1)
			ckp.setSupplier(supplierType.ST1.getValue());
		else if (mobileMatch == 2)
			ckp.setSupplier(supplierType.ST2.getValue());
		else if (mobileMatch == 3)
			ckp.setSupplier(supplierType.ST3.getValue());
		else 
			return null;
		List<CardKeysProduct> cardKeysProductList =  cardKeysProductService.getCardKeysProductList(ckp);
		if (cardKeysProductList.size() == 0)
			return null;
		return cardKeysProductList;
	}*/

    public static void main(String[] args) {
        UnicomAyncReq req = new UnicomAyncReq();
        req.setUserId("377");
        req.setItemId("13627");
        req.setDtCreate("20180509152133");
        req.setUid("13162666043");
        req.setSerialno("2018050915204300128302");
        req.setSign(DCUtils.genSign(req, "f9ba9bde36cc16f44bb8727d2e46d7dac8605f316124c376b2129691007a7391"));
        System.out.println(req.getSign());
    }

}
