package com.cn.thinkx.wecard.api.module.pay.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cn.thinkx.beans.CtrlSystem;
import com.cn.thinkx.beans.TxnPackageBean;
import com.cn.thinkx.common.activemq.service.WechatMQProducerService;
import com.cn.thinkx.common.service.module.jiafupay.constants.Constants;
import com.cn.thinkx.common.service.module.jiafupay.constants.Constants.JFRespCode;
import com.cn.thinkx.common.service.module.jiafupay.service.JFPayService;
import com.cn.thinkx.common.service.module.jiafupay.vo.JFChnlReq;
import com.cn.thinkx.common.service.module.jiafupay.vo.JFChnlResp;
import com.cn.thinkx.facade.bean.CardBalQueryRequest;
import com.cn.thinkx.facade.bean.IntegrationPayRequest;
import com.cn.thinkx.facade.service.HKBTxnFacade;
import com.cn.thinkx.facade.service.IntegrationPayFacade;
import com.cn.thinkx.pms.base.redis.core.JedisClusterUtils;
import com.cn.thinkx.pms.base.redis.core.JedisUtils;
import com.cn.thinkx.pms.base.redis.service.RedisCacheService;
import com.cn.thinkx.pms.base.redis.util.SignUtil;
import com.cn.thinkx.pms.base.redis.util.*;
import com.cn.thinkx.pms.base.redis.vo.BoxDeviceInfoVO;
import com.cn.thinkx.pms.base.redis.vo.CustomerQrCodeVO;
import com.cn.thinkx.pms.base.redis.vo.ShopInfVO;
import com.cn.thinkx.pms.base.redis.vo.WsScanCodePayMsg;
import com.cn.thinkx.pms.base.utils.*;
import com.cn.thinkx.pms.base.utils.BaseConstants.ChannelCode;
import com.cn.thinkx.pms.base.utils.BaseConstants.ITFRespCode;
import com.cn.thinkx.pms.base.utils.BaseConstants.TransCode;
import com.cn.thinkx.pms.base.utils.BaseConstants.mchntAccountStat;
import com.cn.thinkx.service.txn.Java2TxnBusinessFacade;
import com.cn.thinkx.wecard.api.module.core.domain.TxnResp;
import com.cn.thinkx.wecard.api.module.mchnt.model.MerchantInf;
import com.cn.thinkx.wecard.api.module.mchnt.model.ShopInf;
import com.cn.thinkx.wecard.api.module.mchnt.service.MerchantInfService;
import com.cn.thinkx.wecard.api.module.mchnt.service.ShopInfService;
import com.cn.thinkx.wecard.api.module.pay.req.PPScanCodeReq;
import com.cn.thinkx.wecard.api.module.pay.resp.IntegrationPayResp;
import com.cn.thinkx.wecard.api.module.pay.resp.PPScanCodeResp;
import com.cn.thinkx.wecard.api.module.pay.service.PPBoxPayService;
import com.cn.thinkx.wecard.api.module.pay.utils.PPBoxConstants;
import com.cn.thinkx.wecard.api.module.pay.utils.PPBoxConstants.PPBoxPayType;
import com.cn.thinkx.wecard.api.module.pay.utils.PPBoxPayUtil;
import com.cn.thinkx.wecard.api.module.pay.valid.IntegrationPayRespValid;
import com.cn.thinkx.wecard.api.module.pay.valid.PPBoxPAYReqValid;
import com.cn.thinkx.wecard.api.module.pub.model.DetailBizInfo;
import com.cn.thinkx.wecard.api.module.pub.service.ChannelUserInfService;
import com.cn.thinkx.wecard.api.module.pub.service.PublicService;
import com.cn.thinkx.wecard.api.module.trans.model.WxTransLog;
import com.cn.thinkx.wecard.api.module.trans.service.CtrlSystemService;
import com.cn.thinkx.wecard.api.module.trans.service.UserMerchantAcctService;
import com.cn.thinkx.wecard.api.module.trans.service.WxTransLogService;
import com.cn.thinkx.wechat.base.wxapi.util.WXTemplateUtil;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import redis.clients.jedis.JedisCluster;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 业务消息处理
 */
@Service("ppboxPayService")
public class PPBoxPayServiceImpl implements PPBoxPayService {
    private Logger logger = LoggerFactory.getLogger(PPBoxPayServiceImpl.class);

    @Autowired
    @Qualifier("java2TxnBusinessFacade")
    private Java2TxnBusinessFacade java2TxnBusinessFacade;

    @Autowired
    @Qualifier("hkbTxnFacade")
    private HKBTxnFacade hkbTxnFacade;

    @Autowired
    @Qualifier("integrationPayFacade")
    private IntegrationPayFacade integrationPayFacade;

    @Autowired
    @Qualifier("ctrlSystemService")
    private CtrlSystemService ctrlSystemService;

    @Autowired
    @Qualifier("wxTransLogService")
    private WxTransLogService wxTransLogService;

    @Autowired
    @Qualifier("wechatMQProducerService")
    private WechatMQProducerService wechatMQProducerService;

    @Autowired
    @Qualifier("redisCacheService")
    private RedisCacheService redisCacheService;

    @Autowired
    @Qualifier("shopInfService")
    private ShopInfService shopInfService;

    @Autowired
    @Qualifier("userMerchantAcctService")
    private UserMerchantAcctService userMerchantAcctService;

    @Autowired
    @Qualifier("jedisCluster")
    private JedisCluster jedisCluster;

    @Autowired
    @Qualifier("publicService")
    private PublicService publicService;

    @Autowired
    @Qualifier("merchantInfService")
    private MerchantInfService merchantInfService;

    @Autowired
    @Qualifier("jfPayService")
    private JFPayService jfPayService;

    @Autowired
    @Qualifier("channelUserInfService")
    private ChannelUserInfService channelUserInfService;

    private static final ExecutorService es = Executors.newFixedThreadPool(3);

    /**
     * paipai提交扫码
     *
     * @param req
     * @return
     */
    public String doPPScanTrans(PPScanCodeReq req, HttpServletRequest request) {
        PPScanCodeResp resp = new PPScanCodeResp();
        resp.setCode(PPBoxConstants.PPBoxCode.FAIL.getValue());
        resp.setMsg("支付失败");
        resp.setSub_code(PPBoxConstants.PPBoxSubCode.SYSTEMERROR.getCode());
        resp.setSub_msg(PPBoxConstants.PPBoxSubCode.SYSTEMERROR.getValue());

        if (req == null)
            return JSONObject.toJSONString(resp);

        if (PPBoxPAYReqValid.scancodeValid(req, resp))
            return JSONObject.toJSONString(resp);

        /** 获取扫描盒子信息 */
        BoxDeviceInfoVO deviceVO = (BoxDeviceInfoVO) redisCacheService.hgetVO(
                RedisConstants.REDIS_HASH_TABLE_TB_SCAN_BOX_DEVICE_INF, req.getDevice_no(), BoxDeviceInfoVO.class);
        if (deviceVO == null) {
            resp.setSub_code(PPBoxConstants.PPBoxSubCode.NOAUTH.getCode());
            resp.setSub_msg(PPBoxConstants.PPBoxSubCode.NOAUTH.getValue());
            return JSONObject.toJSONString(resp);
        }

        if (StringUtil.isNullOrEmpty(req.getTotal_fee())) {// 如果请求的支付金额为空
            if ("1".equals(deviceVO.getFixedPayFlag())) { // 如果当前设备是定额支付
                req.setTotal_fee(deviceVO.getFixedPayAmt()); // 设置请求支付金额
            } else {
                return JSONObject.toJSONString(resp);
            }
        }
        logger.info("PPBox Info：{}", deviceVO.toString());

        /** 判断商户是否开户   xiaomei   2018/4/17 */
        String accountStat = merchantInfService.getAccountStatByMchntCode(deviceVO.getMchntCode());
        if (mchntAccountStat.MAS00.getCode().equals(accountStat)) {
            logger.error("##扫码盒子   此商户未开户，暂不支持进行交易，商户号为[{}]", deviceVO.getMchntCode());
            resp.setSub_code(PPBoxConstants.PPBoxSubCode.MCHNTNOTRANS.getCode());
            resp.setSub_msg(PPBoxConstants.PPBoxSubCode.MCHNTNOTRANS.getValue());
            return JSONObject.toJSONString(resp);
        }

        String openID = null;
        String jfUserID = null;
        WxTransLog log = new WxTransLog();
        /*** qrCodeVO 获取用户信息 **/
        CustomerQrCodeVO qrCodeVO = null;
        String pay_type = PPBoxPayUtil.getPayTypeByAuthCode(req.getAuth_code()); // 支付方式
        String termChannelNo = deviceVO.getChannelNo();// 终端通道号
        String hkbChannelNo = BaseIntegrationPayConstants.HKB_TERM_CHNL_NO;// 知了企服会员卡通道号
        if (PPBoxConstants.PPBoxPayType.HKBPAY.getValue().equals(pay_type) && hkbChannelNo.equals(termChannelNo)) {// 知了企服会员卡
            String auth_jsonStr = JedisClusterUtils.getInstance().get(req.getAuth_code());// 根据授权码获取用户信息
            logger.info("用户条码信息[{}]", auth_jsonStr);
            if (StringUtil.isNullOrEmpty(auth_jsonStr)) {
                resp.setSub_code(PPBoxConstants.PPBoxSubCode.PAYMENT_AUTH_CODE_INVALID.getCode());// 支付授权码无效
                resp.setSub_msg(PPBoxConstants.PPBoxSubCode.PAYMENT_AUTH_CODE_INVALID.getValue());// 支付授权码无效
                return JSONObject.toJSONString(resp);
            }
            qrCodeVO = JSONArray.parseObject(auth_jsonStr, CustomerQrCodeVO.class);

            if (StringUtil.isNullOrEmpty(StringUtil.trim(qrCodeVO.getOpenid()))) {
                logger.error("## redis获取当前授权码对应用户openid 或 userID失败，授权码[{}]", req.getAuth_code());
                return JSONObject.toJSONString(resp);
            }
            if (!StringUtil.isNullOrEmpty(StringUtil.trim(qrCodeVO.getHkbUserID())) && !StringUtil.isNullOrEmpty(StringUtil.trim(qrCodeVO.getJfUserID()))) {
                jfUserID = qrCodeVO.getJfUserID();
                pay_type = PPBoxConstants.PPBoxPayType.JFPAY.getValue();
            }
            openID = qrCodeVO.getOpenid();
            log.setSponsor(BaseConstants.SponsorCode.SPONSOR20.toString());
            log.setTransId(TransCode.CW10.getCode());// 交易类型 会员卡支付
            log.setTransChnl(ChannelCode.CHANNEL1.toString());
            log.setAdditionalInfo(deviceVO.getChannelNo());
        } else if (PPBoxPayType.WXPAY.getValue().equals(pay_type) && !hkbChannelNo.equals(termChannelNo)) {// 微信
            openID = req.getAuth_code();
            log.setSponsor(BaseConstants.SponsorCode.SPONSOR30.toString());
            log.setTransId(TransCode.CW71.getCode());// 交易类型 快捷支付
            log.setTransChnl(ChannelCode.CHANNEL2.toString());
            log.setAdditionalInfo(deviceVO.getChannelNo());
        } else if (PPBoxPayType.ALIPAY.getValue().equals(pay_type) && !hkbChannelNo.equals(termChannelNo)) {// 支付宝
            openID = req.getAuth_code();
            log.setSponsor(BaseConstants.SponsorCode.SPONSOR40.toString());
            log.setTransId(TransCode.CW71.getCode());// 交易类型 快捷支付
            log.setTransChnl(ChannelCode.CHANNEL3.toString());
            log.setAdditionalInfo(deviceVO.getChannelNo());
        } else if (PPBoxPayType.INVALID.getValue().equals(pay_type)) { // 无效二维码
            resp.setSub_code(PPBoxConstants.PPBoxSubCode.PAYMENT_AUTH_CODE_INVALID.getCode());// 支付失败
            resp.setSub_msg(PPBoxConstants.PPBoxSubCode.PAYMENT_AUTH_CODE_INVALID.getValue());// 支付失败
            return JSONObject.toJSONString(resp);
        } else {
            resp.setSub_code(PPBoxConstants.PPBoxSubCode.PAYMENT_AUTH_CODE_INVALID.getCode());// 支付授权码无效
            resp.setSub_msg(PPBoxConstants.PPBoxSubCode.PAYMENT_AUTH_CODE_INVALID.getValue());// 支付授权码无效
            return JSONObject.toJSONString(resp);
        }

        /** 判断支付方式为嘉福的渠道  xiaomei 2018/4/17 */
        if (PPBoxPayType.JFPAY.getValue().equals(pay_type)) {
            log.setSponsor(BaseConstants.SponsorCode.SPONSOR20.toString());
            log.setTransId(TransCode.CW71.getCode());// 交易类型 会员卡支付
            log.setTransChnl(ChannelCode.CHANNEL4.toString());
            log.setAdditionalInfo(deviceVO.getChannelNo());
        }

        CtrlSystem cs = ctrlSystemService.getCtrlSystem();// TODO 得到日切信息
        if (cs == null)
            return JSONObject.toJSONString(resp);

        if (!BaseConstants.TRANS_FLAG_YES.equals(cs.getTransFlag())) {
            logger.error("## 用户[{}](发起方[{}])扫码交易，日切信息交易允许状态：日切中", openID, log.getSponsor());
            return JSONObject.toJSONString(resp);
        }

        String wxPrimaryKey = wxTransLogService.getPrimaryKey();
        log.setWxPrimaryKey(wxPrimaryKey);
        log.setTableNum(cs.getCurLogNum());
        log.setSettleDate(cs.getSettleDate());
        log.setTransSt(0);// 插入时为0，报文返回时更新为1
        log.setInsCode(deviceVO.getInsCode());// 客户端传过来的机构code
        log.setMchntCode(deviceVO.getMchntCode());
        log.setShopCode(deviceVO.getShopCode());
        log.setUserInfUserName(openID);
        log.setTransAmt(req.getTotal_fee());// 实际交易金额 插入时候默认与上送金额一致
        log.setUploadAmt(req.getTotal_fee());// 上送金额
        log.setTransCurrCd(BaseConstants.TRANS_CURR_CD);
        log.setTermCode(req.getDevice_no());// 扫描盒子设备ID
        try {
            int i = wxTransLogService.insertWxTransLog(log);// 插入流水记录
            if (i != 1) {
                logger.error("## 用户[{}]扫码交易，盒子扫码插入流水记录数量不为1", openID);
                return JSONObject.toJSONString(resp);
            }
        } catch (Exception ex) {
            logger.error("## 盒子扫码插入流水异常", ex);
            return JSONObject.toJSONString(resp);
        }

        TxnPackageBean txnBean = new TxnPackageBean();
        TxnResp txnResp = new TxnResp();
        DetailBizInfo detail = new DetailBizInfo();
        boolean flag = false;
        /** ========知了企服会员卡支付进入以下流程========= **/
        if (PPBoxConstants.PPBoxPayType.HKBPAY.getValue().equals(pay_type)) {
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
            txnBean.setCardNo("U" + openID);// 卡号 U开头为客户端交易，C开头则为刷卡交易
            txnBean.setChannel(log.getTransChnl());// 渠道号
            txnBean.setTimestamp(System.currentTimeMillis());// 时间戳
            txnBean.setInnerPosNo(log.getTermCode()); // 扫描盒子设备ID
            txnBean.setResv6("1"); // 表示不需要输入密码
            String signature = TxnChannelSignatureUtil.genSign(txnBean); // 生成的签名
            txnBean.setSignature(signature);
            // 远程调用消费接口
            String json = new String();
            try {
                json = java2TxnBusinessFacade.consumeTransactionITF(txnBean);
                txnResp = JSONArray.parseObject(json, TxnResp.class);
                if (txnResp == null || !BaseConstants.TXN_TRANS_RESP_SUCCESS.equals(txnResp.getCode())) {
                    json = java2TxnBusinessFacade.transExceptionQueryITF(wxPrimaryKey);
                    txnResp = JSONArray.parseObject(json, TxnResp.class);
                }
            } catch (Exception e) {
                logger.error("## 远程调用消费接口异常，返回json[{}]，流水号[{}]", json, wxPrimaryKey, e);
            }

            if (txnResp == null) {
                txnResp = new TxnResp();
                txnResp.setCode(BaseConstants.TXN_TRANS_ERROR);
            }

            String respCode = txnResp.getCode();// 用于判断用户账户是否能使用通卡进行交易
            // 如果用户未购卡，默认开卡
            if (BaseConstants.ITFRespCode.CODE06.getCode().equals(txnResp.getCode())) {
                try {
                    // 用户开卡
                    TxnResp cardOpenResp = userMerchantAcctService.doCustomerCardOpeningITF(null, null, openID,
                            log.getMchntCode(), log.getInsCode());
                    if (BaseConstants.ITFRespCode.CODE00.getCode().equals(cardOpenResp.getCode())) {
                        respCode = BaseConstants.ITFRespCode.CODE51.getCode();
                    } else {
                        txnResp.setCode(BaseConstants.TXN_TRANS_ERROR);
                    }
                } catch (Exception e) {
                    logger.error("## 用户开卡异常 respCode[{}]", txnResp.getCode(), e);
                }
            }

            logger.info("盒子扫码支付交易返回[{}]", JSONArray.toJSONString(txnResp));
            try {
                int i = wxTransLogService.updateWxTransLog(log, txnResp);// 更新微信端流水
                if (i != 1) {
                    logger.error("## 盒子扫码支付交易更新流水失败，流水号[{}]", log.getWxPrimaryKey());
                    return JSONObject.toJSONString(resp);
                }
            } catch (Exception e) {
                logger.error("## 盒子扫码支付交易更新流水异常，流水号[{}]", wxPrimaryKey, e);
            }

            /** 当会员卡余额不足时，判断该商户是否可以使用通卡进行消费   xiaomei   2018/4/11 */
            if (BaseConstants.ITFRespCode.CODE51.getCode().equals(respCode)) {
                MerchantInf merchantInf = merchantInfService.getMerchantInfByMchntCode(deviceVO.getMchntCode());
                if (BaseConstants.MchntTypeEnum.MCHNTTYPE00.getCode().equals(merchantInf.getMchntType())) {
                    String accMchntCode = jedisCluster.hget(RedisConstants.REDIS_HASH_TABLE_TB_BASE_DICT_KV, BaseConstants.ACC_HKB_MCHNT_NO);
                    String accShopCode = jedisCluster.hget(RedisConstants.REDIS_HASH_TABLE_TB_BASE_DICT_KV, BaseConstants.ACC_HKB_SHOP_NO);
                    String accInsCode = jedisCluster.hget(RedisConstants.REDIS_HASH_TABLE_TB_BASE_DICT_KV, BaseConstants.ACC_HKB_INS_CODE);
                    if (!StringUtil.isNullOrEmpty(accMchntCode))
                        detail.setMchntCode(accMchntCode);
                    if (!StringUtil.isNullOrEmpty(accShopCode))
                        detail.setShopCode(accShopCode);
                    if (!StringUtil.isNullOrEmpty(accInsCode))
                        detail.setInsCode(accInsCode);
                    if (StringUtil.isNullOrEmpty(detail))
                        logger.error("## 盒子扫码支付缓存中获取通卡信息为空，流水号[{}]", log.getWxPrimaryKey());
                    flag = true;
                    /** 调用通卡消费封装方法*/
                    String accjson = publicService.accMchntTrans(log, cs, detail);
                    txnResp = JSONArray.parseObject(accjson, TxnResp.class);
                    logger.info("盒子扫码支付   调用通卡消费封装方法accMchntTrans，返回值[{}]", JSONObject.toJSONString(txnResp));
                    if (!BaseConstants.TXN_TRANS_RESP_SUCCESS.equals(txnResp.getCode()))
                        return JSONObject.toJSONString(resp);
                } else {
                    logger.error("## 盒子扫码支付   因用户[{}]的商户[{}]会员卡余额不足且暂不支持使用通卡余额消费", openID, deviceVO.getMchntCode());
                }
            }
        } else if (PPBoxConstants.PPBoxPayType.WXPAY.getValue().equals(pay_type)) {
            /** ========微信支付进入以下流程========= **/
            String str = send2IntegrationPay(wxPrimaryKey, req, request, openID, log, resp, pay_type, txnResp, deviceVO);
            if (str != null)
                return str;
        } else if (PPBoxConstants.PPBoxPayType.ALIPAY.getValue().equals(pay_type)) {
            /** ========支付宝支付进入以下流程========= **/
            String str = send2IntegrationPay(wxPrimaryKey, req, request, openID, log, resp, pay_type, txnResp, deviceVO);
            if (str != null)
                return str;
        } else if (PPBoxConstants.PPBoxPayType.UNIONPAY.getValue().equals(pay_type)) {
            /** ========云闪付支付进入以下流程========= **/
            String str = send2IntegrationPay(wxPrimaryKey, req, request, openID, log, resp, pay_type, txnResp, deviceVO);
            if (str != null)
                return str;
        } else if (PPBoxConstants.PPBoxPayType.JFPAY.getValue().equals(pay_type)) {
            /** ========嘉福支付进入以下流程========= **/
            JFChnlReq jfChnlReq = new JFChnlReq();
            jfChnlReq.setJfUserId(jfUserID);
            jfChnlReq.setTxnAmount(log.getTransAmt());
            jfChnlReq.setSwtFlowNo(log.getWxPrimaryKey());
            String str = JFPay(jfChnlReq, resp, cs.getCurLogNum(), txnResp);
            if (str != null)
                return str;
        } else {
            return JSONObject.toJSONString(resp);
        }

        String mCode = ""; //区别是否是通卡的商户号
        final String deviceMchntCode = deviceVO.getMchntCode();
        if (BaseConstants.TXN_TRANS_RESP_SUCCESS.equals(txnResp.getCode()) &&
                PPBoxConstants.PPBoxPayType.HKBPAY.getValue().equals(pay_type)) {
            try {
                String customerAcount = RedisDictProperties.getInstance().getdictValueByCode("WX_CUSTOMER_ACCOUNT");
                ShopInfVO shopInfVo = (ShopInfVO) redisCacheService.hgetVO(
                        RedisConstants.REDIS_HASH_TABLE_TB_SHOP_INF, deviceVO.getShopCode(), ShopInfVO.class);
                if (shopInfVo == null) {
                    ShopInf shopinf = shopInfService.getShopInfByCode(deviceVO.getShopCode());
                    shopInfVo = new ShopInfVO();
                    shopInfVo.setMchntName(shopinf.getMchntName());
                    shopInfVo.setShopName(shopinf.getShopName());
                }

                final String txnDate = DateUtil.getChineseDateFormStr(txnBean.getSwtTxnDate() + txnBean.getSwtTxnTime());
                final String payAmt = NumberUtils.RMBCentToYuan(log.getTransAmt());// 实际支付金额
                final String openID_f = openID;
                final String mchntName = shopInfVo.getMchntName();
                final String shopName = shopInfVo.getShopName();
                final String cusAcount_f = customerAcount;
                if (flag) {
                    mCode = detail.getMchntCode();
                } else {
                    mCode = deviceVO.getMchntCode();
                }
                final String mchntCode = mCode;
                /** // 客服消息-客户
                 String notice_c = String.format(WechatCustomerMessageUtil.WECHAT_CUSTOMER_CW10_SUCCESS, txnDate,
                 shopInfVo.getMchntName(), shopInfVo.getShopName(), payAmt);
                 wechatMQProducerService.addWechatMessageQueue(customerAcount, notice_c, openID);*/

                es.execute(new Runnable() {
                    public void run() {
                        try {
                            CardBalQueryRequest cbReq = new CardBalQueryRequest();
                            cbReq.setChannel(ChannelCode.CHANNEL1.toString());
                            cbReq.setUserId(openID_f);
                            cbReq.setInnerMerchantNo(mchntCode);
                            cbReq.setTimestamp(System.currentTimeMillis());
                            cbReq.setSign(SignUtil.genSign(cbReq));
                            String cardBalJson = hkbTxnFacade.cardBalanceQueryITF(cbReq);
                            TxnResp cbResp = JSONArray.parseObject(cardBalJson, TxnResp.class);
                            String accBal = StringUtil.isNullOrEmpty(cbResp.getBalance()) ? "0" : cbResp.getBalance();
                            /**发送模板消息*/
                            TreeMap<String, TreeMap<String, String>> templateMsg = new TreeMap<String, TreeMap<String, String>>();
                            if (mchntCode.equals(deviceMchntCode)) {
                                templateMsg = WXTemplateUtil.setCardPayData(txnDate, mchntName, shopName, payAmt, NumberUtils.RMBCentToYuan(accBal), shopName);
                            } else {
                                templateMsg = WXTemplateUtil.setHKBPayData(txnDate, mchntName, shopName, payAmt, NumberUtils.RMBCentToYuan(accBal), shopName);
                            }
                            wechatMQProducerService.addTemplateMsgQueue(cusAcount_f, openID_f, "WX_TEMPLATE_ID_0", null, templateMsg);
                        } catch (Exception e) {
                            logger.error("## 发送模板消息添加消息队列异常，openID[{}]", openID_f, e);
                        }
                    }
                });
            } catch (Exception e) {
                logger.error("## 盒子扫码支付交易发送用户模板消息异常，流水号[{}]", wxPrimaryKey, e);
            }
        }

        // 二维码结果页面
        // 发送websocket 消息给页面
        try {
            WsScanCodePayMsg wsMsg = new WsScanCodePayMsg();
            wsMsg.setText(txnResp.getInfo());
            wsMsg.setCode(txnResp.getCode());
            wsMsg.setFromUser(openID);
            wsMsg.setToUser(openID);
            wsMsg.setReqType(BaseConstants.PayReqTypeEnum.REQ_TYPE_A.getCode());
            wsMsg.setSendType(BaseConstants.WSSendTypeEnum.SEND_TYPE_90.getCode());
            wsMsg.setTransAmt(log.getTransAmt()); // 交易金额
            wsMsg.setWxTransLogKey(log.getWxPrimaryKey());
            wsMsg.setMchntCode(log.getMchntCode());
            if (JedisUtils.JEDIS_STATUS) {// 发送给Ｃ端业务处理结果
                JedisUtils.pubChannel(BaseConstants.RedisChannelEnum.B_SCAN_QR_CODE_PAY.getCode(),
                        JSONArray.toJSONString(wsMsg));
            }
        } catch (Exception e) {
            logger.error("## 发送websocket异常，流水号[{}]", wxPrimaryKey, e);
        }

        // 返回结果信息
        if (BaseConstants.TXN_TRANS_RESP_SUCCESS.equals(txnResp.getCode())) {
            // 支付成功后删除缓存信息
            JedisClusterUtils.getInstance().del(req.getAuth_code());
            // 组装返回报文
            resp.setPp_trade_no(req.getPp_trade_no());
            resp.setTotal_fee(req.getTotal_fee());
            resp.setTime_end(DateUtil.getDateText(new Date(), DateUtil.FORMAT_YYYYMMDDHHMMSS));
            resp.setPay_type(pay_type);
            resp.setNonce_str(req.getNonce_str());
            resp.setPrint_qr(deviceVO.getPrintQr());
            resp.setPrintType(deviceVO.getPrintType());

            if (!StringUtil.isNullOrEmpty(deviceVO.getPrint())) {
                try {
                    // 十六进制GBK编码
                    String print = StringUtils.stringToHexString(deviceVO.getPrint().getBytes("GBK"));
                    resp.setPrint(print);
                } catch (Exception e) {
                    logger.error("## 十六进制GBK编码转换异常，流水号[{}]", wxPrimaryKey, e);
                }
            }
            if (!StringUtil.isNullOrEmpty(deviceVO.getReceipt())) {
                try {
                    // 格式为使用 base64 编码的GBK字符串
                    String receipt = Base64.encodeBase64String(deviceVO.getReceipt().getBytes("GBK"));
                    resp.setReceipt(receipt);
                } catch (Exception e) {
                    logger.error("## base64编码转换异常，流水号[{}]", wxPrimaryKey, e);
                }
            }
            resp.setTransaction_id(txnResp.getTxnFlowNo());
            resp.setCode(PPBoxConstants.PPBoxCode.SUCCESS.getValue());
            resp.setMsg("支付成功");
            resp.setSign(ChannelSignUtil.genPPScanPaySign(resp));
            resp.setSub_code(PPBoxConstants.PPBoxCode.SUCCESS.getValue());// 支付成功
            resp.setSub_msg("支付成功");// 支付成功
        }

        return JSONObject.toJSONString(resp);
    }

    //	private PPScanCodeResp doAggregatePay(PPScanCodeReq req, BoxDeviceInfoVO deviceVO) {
    //		AggrPayReq aggr = new AggrPayReq();
    //		PPScanCodeResp resp = new PPScanCodeResp() ;
    //		try {
    //			BeanUtils.copyProperties(req, aggr);
    //			BeanUtils.copyProperties(deviceVO, aggr);
    //			String areq = JSONObject.toJSONString(aggr);
    //			areq= URLEncoder.encode(areq, "utf-8");
    //			String result = PPBoxPayUtil.sendGet("http://192.168.1.19:8080/aggrpay-web/boxpay/scancode", "message=" +areq);
    //			logger.info("请求聚合支付的参数------->[{}]",areq);
    //			resp = JSONArray.parseObject(result, PPScanCodeResp.class);
    //			logger.info("聚合支付返回的参数------->[{}]",result);
    //		} catch (Exception e) {
    //			logger.error("## base64编码转换异常，流水号[{}]", e.getMessage());
    //		}
    //		return resp;
    //	}

    private String send2IntegrationPay(String wxPrimaryKey, PPScanCodeReq req, HttpServletRequest request, String openID,
                                       WxTransLog log, PPScanCodeResp resp, String txnType, TxnResp txnResp, BoxDeviceInfoVO deviceVO) {
        IntegrationPayRequest payReq = new IntegrationPayRequest();
        payReq.setInsNo(deviceVO.getInsCode());
        payReq.setMchntNo(deviceVO.getMchntCode());
        payReq.setTermSwtFlowNo(wxPrimaryKey);
        payReq.setTransAmt(req.getTotal_fee());
        payReq.setTermChnlNo(deviceVO.getChannelNo());
        payReq.setTransChnlNo(log.getTransChnl());
        payReq.setPaymentType(txnType);
        payReq.setTransId(log.getTransId());
        payReq.setAuthInfo(openID);
        payReq.setTermNo(log.getTermCode());
        payReq.setOrderDesc("知了企服[" + deviceVO.getMchntName() + " - " + deviceVO.getShopName() + "]消费");
        payReq.setTimestamp(System.currentTimeMillis());
        payReq.setSign(IntSignUtil.genSign(payReq, RedisDictProperties.getInstance().getdictValueByCode("INT_KEY")));
        try {
            logger.info("请求远程调用聚合支付接口参数[{}]", JSONArray.toJSON(payReq));
            String returnStr = integrationPayFacade.payMentTransactionITF(payReq);
            IntegrationPayResp payResp = JSONArray.parseObject(returnStr, IntegrationPayResp.class);
            logger.info("远程调用聚合支付接口返回参数[{}]", JSONArray.toJSON(payResp));
            if (payResp != null && payResp.getCode().equals(BaseConstants.ITFRespCode.CODE00.getCode())) {
                if (IntegrationPayRespValid.integrationPayValid(payResp, resp)) {
                    String str = integrationPayFacade.queryTransactionITF(payReq);
                    IntegrationPayResp resultResp = JSONArray.parseObject(str, IntegrationPayResp.class);
                    if (BaseConstants.ITFRespCode.CODE00.getCode().equals(resultResp.getCode())) {
                        txnResp.setCode(BaseConstants.TXN_TRANS_RESP_SUCCESS);
                        int i = wxTransLogService.updateWxTransLog(log, txnResp);// 更新微信端流水
                        if (i == 1) {
                            txnResp.setInfo(ITFRespCode.CODE00.getValue());
                            txnResp.setTxnFlowNo(payResp.getRemarks());
                        }
                    } else {
                        logger.error("## 支付失败：原交易流水号[{}]与支付原样返回流水[{}]不一致", wxPrimaryKey, payResp.getTermSwtFlowNo());
                        return JSONArray.toJSONString(resp);
                    }
                } else {
                    txnResp.setCode(BaseConstants.TXN_TRANS_RESP_SUCCESS);
                    int i = wxTransLogService.updateWxTransLog(log, txnResp);// 更新微信端流水
                    if (i == 1) {
                        txnResp.setInfo(ITFRespCode.CODE00.getValue());
                        txnResp.setTxnFlowNo(payResp.getRemarks());
                    }
                }
            } else {
                logger.error("## 支付失败：知了企服流水号[{}] 返回码[{}] 返回信息[{}]", wxPrimaryKey, payResp.getCode(), payResp.getInfo());
                return JSONObject.toJSONString(resp);
            }
        } catch (Exception e) {
            logger.error("## 盒子扫付款码支付交易异常，流水号[{}]", wxPrimaryKey, e);
        }
        return null;
    }

    private String JFPay(JFChnlReq jfChnlReq, PPScanCodeResp resp, String curLogNum, TxnResp txnResp) {
        WxTransLog log = new WxTransLog();
        log.setTableNum(curLogNum);
        log.setWxPrimaryKey(jfChnlReq.getSwtFlowNo());

        //调用嘉福收银台
        String jfJson = null;
        JFChnlResp jfResp = new JFChnlResp();

        //调用嘉福支付接口（福利余额支付）
        jfChnlReq.setPayType(Constants.PayMethod.pay0.getCode());
        jfJson = jfPayService.doPayMentTrans(jfChnlReq);
        if (!StringUtil.isNullOrEmpty(jfJson)) {
            jfResp = JSONArray.parseObject(jfJson, JFChnlResp.class);
            txnResp.setTxnFlowNo(jfResp.getSwtFlowNo());
            log.setDmsRelatedKey(jfResp.getSwtFlowNo());
        } else {
            logger.error("## 扫码盒子 接收嘉福收银台返回参数为空，嘉福用户ID[{}],支付方式[{}]", jfChnlReq.getJfUserId(), jfChnlReq.getPayType());
        }

        //返回余额不足  调用嘉福支付接口（工资账户支付）
        if (JFRespCode.R01.getCode().equals(jfResp.getCode())) {
            jfChnlReq.setPayType(Constants.PayMethod.pay1.getCode());
            jfJson = jfPayService.doPayMentTrans(jfChnlReq);
            if (!StringUtil.isNullOrEmpty(jfJson)) {
                jfResp = JSONArray.parseObject(jfJson, JFChnlResp.class);
                txnResp.setTxnFlowNo(jfResp.getSwtFlowNo());
                log.setDmsRelatedKey(jfResp.getSwtFlowNo());
            } else {
                logger.error("## 扫码盒子 接收嘉福收银台返回参数为空，嘉福用户ID[{}],支付方式[{}]", jfChnlReq.getJfUserId(), jfChnlReq.getPayType());
            }
        }

        if (JFRespCode.R00.getCode().equals(jfResp.getCode()) || JFRespCode.R09.getCode().equals(jfResp.getCode())) {
            txnResp.setCode(ITFRespCode.CODE00.getCode());
        } else if (JFRespCode.R01.getCode().equals(jfResp.getCode())) {
            txnResp.setCode(BaseConstants.TXN_TRANS_ERROR);
        } else if (JFRespCode.R07.getCode().equals(jfResp.getCode())) {
            txnResp.setCode(ITFRespCode.CODE94.getCode());
        } else if (JFRespCode.R08.getCode().equals(jfResp.getCode())) {
            txnResp.setCode(ITFRespCode.CODE25.getCode());
        } else {
            txnResp.setCode(ITFRespCode.CODE96.getCode());
        }

        // 更新微信端流水
        try {
            int i = wxTransLogService.updateWxTransLog(log, txnResp);
            if (i != 1) {
                logger.error("## 盒子扫码支付，调用嘉福收银台支付，更新流水失败，流水号[{}]", log.getWxPrimaryKey());
                return JSONObject.toJSONString(resp);
            }
        } catch (Exception e) {
            logger.error("## 盒子扫码支付，调用嘉福收银台支付，更新流水异常，流水号[{}]", log.getWxPrimaryKey(), e);
        }
        return null;
    }

}