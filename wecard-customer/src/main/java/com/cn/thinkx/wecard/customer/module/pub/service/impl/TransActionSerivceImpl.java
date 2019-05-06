package com.cn.thinkx.wecard.customer.module.pub.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.cn.thinkx.beans.CtrlSystem;
import com.cn.thinkx.beans.TxnPackageBean;
import com.cn.thinkx.common.activemq.service.WechatMQProducerService;
import com.cn.thinkx.common.service.module.jiafupay.service.JFPayService;
import com.cn.thinkx.common.wecard.domain.accmchnt.AccMchntTransLog;
import com.cn.thinkx.common.wecard.domain.merchant.MerchantInf;
import com.cn.thinkx.common.wecard.domain.merchant.MerchantManager;
import com.cn.thinkx.common.wecard.domain.shop.ShopInf;
import com.cn.thinkx.common.wecard.domain.trans.WxTransLog;
import com.cn.thinkx.common.wecard.domain.user.UserInf;
import com.cn.thinkx.common.wecard.domain.user.UserMerchantAcct;
import com.cn.thinkx.facade.bean.CardBalQueryRequest;
import com.cn.thinkx.facade.bean.RechargeTransRequest;
import com.cn.thinkx.facade.service.HKBTxnFacade;
import com.cn.thinkx.pms.base.redis.core.JedisUtils;
import com.cn.thinkx.pms.base.redis.util.SignUtil;
import com.cn.thinkx.pms.base.redis.util.*;
import com.cn.thinkx.pms.base.redis.vo.WsScanCodePayMsg;
import com.cn.thinkx.pms.base.utils.*;
import com.cn.thinkx.pms.base.utils.BaseConstants.*;
import com.cn.thinkx.service.drools.WxDroolsExcutor;
import com.cn.thinkx.service.txn.Java2TxnBusinessFacade;
import com.cn.thinkx.wecard.customer.core.util.HttpUtil;
import com.cn.thinkx.wecard.customer.module.customer.service.*;
import com.cn.thinkx.wecard.customer.module.merchant.service.MerchantInfService;
import com.cn.thinkx.wecard.customer.module.merchant.service.MerchantManagerService;
import com.cn.thinkx.wecard.customer.module.merchant.service.ShopInfService;
import com.cn.thinkx.wecard.customer.module.pub.domain.TxnResp;
import com.cn.thinkx.wecard.customer.module.pub.service.AccMchntTransLogService;
import com.cn.thinkx.wecard.customer.module.pub.service.PublicService;
import com.cn.thinkx.wecard.customer.module.pub.service.TransActionService;
import com.cn.thinkx.wecard.customer.module.pub.util.WXPayOrderUtil;
import com.cn.thinkx.wecard.customer.module.wxcms.WxCmsContents;
import com.cn.thinkx.wechat.base.wxapi.process.*;
import com.cn.thinkx.wechat.base.wxapi.util.WXTemplateUtil;
import com.cn.thinkx.wechat.base.wxapi.util.WxConstants;
import com.cn.thinkx.wechat.base.wxapi.vo.WxPayCallback;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.interfaces.RSAPrivateKey;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service("transActionSerivce")
public class TransActionSerivceImpl implements TransActionService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    @Qualifier("ctrlSystemService")
    private CtrlSystemService ctrlSystemService;

    @Autowired
    private PublicService publicService;

    @Autowired
    private Java2TxnBusinessFacade java2TxnBusinessFacade;

    @Autowired
    private WxDroolsExcutor wxDroolsExcutor;

    @Autowired
    private WxTransLogService wxTransLogService;

    @Autowired
    private UserMerchantAcctService userMerchantAcctService;

    @Autowired
    @Qualifier("userInfService")
    private UserInfService userInfService;

    @Autowired
    @Qualifier("shopInfService")
    private ShopInfService shopInfService;

    @Autowired
    @Qualifier("merchantInfService")
    private MerchantInfService merchantInfService;

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
    private HKBTxnFacade hkbTxnFacade;

    @Autowired
    @Qualifier("accMchntTransLogService")
    private AccMchntTransLogService accMchntTransLogService;

    @Autowired
    @Qualifier("jfPayService")
    private JFPayService jfPayService;

    private static final ExecutorService es = Executors.newCachedThreadPool();

    @Override
    public WxTransLog insertWxTransLog(HttpServletRequest request) {
        String oprOpenid = WxMemoryCacheClient.getOpenid(request);
        String sponsor = request.getParameter("sponsor");
        String openid = request.getParameter("openid");// 商户扫描用户二维码时得到的用户openid(已动态加密)
        String merchantCode = request.getParameter("merchantCode");
        String shopCode = request.getParameter("shopCode");
        String insCode = request.getParameter("insCode");
        String transMoney = request.getParameter("money");
        String payType = request.getParameter("payType");
        try {
            if (openid != null) {
                openid = DES3Util.Decrypt3DES(openid, BaseKeyUtil.getEncodingAesKey());
                openid = StringUtil.trim(openid.substring(8, openid.length()));
            }
        } catch (Exception e) {
            logger.error("## 二维码解密生成失败：", e);
            return null;
        }

        CtrlSystem cs = ctrlSystemService.getCtrlSystem();// 得到日切信息
        if (cs == null) {
            logger.error("## insertWxTransLog--->日切信息为空");
            return null;
        }

        WxTransLog log = new WxTransLog();
        if (!BaseConstants.TRANS_FLAG_YES.equals(cs.getTransFlag())) {// 日切状态为不允许时返回空值
            logger.error("## 日切信息交易允许状态：日切中");
            return null;
        }

        String id = wxTransLogService.getPrimaryKey();
        log.setWxPrimaryKey(id);
        log.setTableNum(cs.getCurLogNum());// 得到当前可以进行操作的流水表号
        log.setSettleDate(cs.getSettleDate());// 交易日期
        if (StringUtil.isNullOrEmpty(payType) || "VIPCARD_PAY".equals(payType)) {
            log.setTransId(TransCode.CW10.getCode());// 交易类型 会员卡支付
            log.setTransChnl(ChannelCode.CHANNEL1.toString());
        } else {
            log.setTransId(TransCode.CW71.getCode());// 交易类型 微信快捷支付
            log.setTransChnl(ChannelCode.CHANNEL2.toString());
        }
        log.setTransSt(0);// 插入时为0，报文返回时更新为1
        log.setInsCode(insCode);// 客户端传过来的机构code
        log.setMchntCode(merchantCode);
        log.setShopCode(shopCode);
        log.setSponsor(sponsor);
        log.setOperatorOpenId(oprOpenid);
        log.setUserInfUserName(openid);
        transMoney = NumberUtils.RMBYuanToCent(transMoney);// 原交易金额单位元转分
        log.setTransAmt(transMoney);// 实际交易金额 插入时候默认与上送金额一致
        log.setUploadAmt(transMoney);// 上送金额
        log.setTransCurrCd(BaseConstants.TRANS_CURR_CD);
        int i = wxTransLogService.insertWxTransLog(log);// 插入流水记录
        if (i != 1) {
            logger.error("## 微信端插入流水记录数量不为1");
            return null;
        }
        return log;
    }

    @Override
    public TxnResp doCustomerNeed2EnterPassword(HttpServletRequest request) {
        TxnResp resp = new TxnResp();

        String merchantCode = request.getParameter("merchantCode");
        String openid = request.getParameter("cOpenid");
        String wxPrimaryKey = request.getParameter("wxPrimaryKey");

        UserMerchantAcct userMerchantAcct = new UserMerchantAcct();
        try {
            openid = DES3Util.Decrypt3DES(openid, BaseKeyUtil.getEncodingAesKey());
            openid = StringUtil.trim(openid.substring(8, openid.length()));
        } catch (Exception e) {
            logger.error("## 二维码解密生成失败：", e);
            return resp;
        }
        userMerchantAcct.setExternalId(openid);
        userMerchantAcct.setMchntCode(merchantCode);

        WxTransLog log = new WxTransLog();
        try {
            log = wxTransLogService.getWxTransLogById(wxPrimaryKey);
            int transAmt = wxDroolsExcutor.getConsumeDiscount(userMerchantAcct.getMchntId(), null,
                    Integer.parseInt(log.getTransAmt()));// 调用规则引擎

            boolean noPinTxnSt = userMerchantAcctService.doCustomerNeed2EnterPassword(userMerchantAcct, transAmt);
            if (noPinTxnSt) // 如果实际消费金额大于无PIN限额 需要验密
                resp.setCode("1");// 需要密码
            else
                resp.setCode("0");// 不需要密码

            resp.setUserId(userMerchantAcct.getUserId());
            resp.setTransAmt(String.valueOf(transAmt));
        } catch (Exception e) {
            logger.error("## 获取无pin限额视图出错", e);
            resp.setCode(BaseConstants.RESPONSE_EXCEPTION_CODE);
            log.setRespCode(BaseConstants.RESPONSE_EXCEPTION_CODE);
            return resp;
        }
        return resp;
    }

    @Override
    public TxnResp scanCodeJava2TxnBusiness(HttpServletRequest request, HttpSession session) {
        String wxPrimaryKey = request.getParameter("wxPrimaryKey");
        String insCode = request.getParameter("insCode");
        String merchantCode = request.getParameter("merchantCode");
        String mchntName = request.getParameter("mchntName");
        String shopCode = request.getParameter("shopCode");
        String shopName = request.getParameter("shopName");
        String transAmt = request.getParameter("transAmt");
        String uploadAmt = request.getParameter("uploadAmt");
        String openid = request.getParameter("openid");
        String pinTxn = request.getParameter("pinTxn");
        String tableNum = request.getParameter("tableNum");
        String settleDate = request.getParameter("settleDate");

        TxnResp resp = new TxnResp();
        try {
            openid = DES3Util.Decrypt3DES(openid, BaseKeyUtil.getEncodingAesKey());
            openid = StringUtil.trim(openid.substring(8, openid.length()));
        } catch (Exception e) {
            logger.error("## 二维码解密生成失败：", e);
            resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
            return resp;
        }

        TxnPackageBean txnBean = new TxnPackageBean();
        txnBean.setTxnType(TransCode.CW10.getCode() + "0");// 交易类型，发送报文时补0
        txnBean.setSwtTxnDate(DateUtil.getCurrentDateStr());// 交易日期
        txnBean.setSwtTxnTime(DateUtil.getCurrentTimeStr());// 交易时间
        txnBean.setSwtSettleDate(settleDate);// 清算日期
        txnBean.setSwtFlowNo(wxPrimaryKey);
        txnBean.setIssChannel(insCode);// 机构渠道号
        txnBean.setInnerMerchantNo(merchantCode);// 商户号
        txnBean.setInnerShopNo(shopCode);// 门店号
        txnBean.setTxnAmount(transAmt);// 交易金额
        txnBean.setOriTxnAmount(uploadAmt);// 原交易金额
        txnBean.setCardNo("U" + openid);// 卡号 U开头为客户端交易，C开头则为刷卡交易

        try {
            if (!StringUtil.isNullOrEmpty(pinTxn)) {
                RSAPrivateKey privateKey = (RSAPrivateKey) request.getSession().getAttribute(WxConstants.RSA_PRIVATE_KEY_SESSION);
                String descrypedPwd = RSAUtil.decryptByPrivateKey(pinTxn, privateKey);// 解密后的密码,pinTxn是提交过来的密码
                txnBean.setPinTxn(descrypedPwd);// 交易密码
            } else {
                txnBean.setPinTxn("");// 交易密码
            }
        } catch (Exception e1) {
            logger.error("## 解密交易密码出错", e1);
            wxTransLogService.updateWxTransLog(tableNum, wxPrimaryKey, BaseConstants.RESPONSE_EXCEPTION_CODE, transAmt);
            resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
            return resp;
        }

        try {
            UserInf user = userInfService.getUserInfByOpenId(openid);
            txnBean.setUserId(user.getUserId());// 用户id
        } catch (Exception e1) {
            logger.error("## 查找用户异常", e1);
            wxTransLogService.updateWxTransLog(tableNum, wxPrimaryKey, BaseConstants.RESPONSE_EXCEPTION_CODE, transAmt);
            resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
            return resp;
        }

        txnBean.setChannel(ChannelCode.CHANNEL1.toString());// 渠道号
        txnBean.setTimestamp(System.currentTimeMillis());// 时间戳
        String signature = TxnChannelSignatureUtil.genSign(txnBean); // 生成的签名
        txnBean.setSignature(signature);

        // 远程调用消费接口
        String json = new String();
        WxTransLog log = null;
        try {
            log = wxTransLogService.getWxTransLogById(wxPrimaryKey);
            json = java2TxnBusinessFacade.consumeTransactionITF(txnBean);
            resp = JSONArray.parseObject(json, TxnResp.class);
            if (resp == null || !BaseConstants.TXN_TRANS_RESP_SUCCESS.equals(resp.getCode())) {
                json = java2TxnBusinessFacade.transExceptionQueryITF(wxPrimaryKey);
                resp = JSONArray.parseObject(json, TxnResp.class);
            }
        } catch (Exception e) {
            logger.error("## 远程调用消费接口异常，返回json{}===流水号[{}]", json, wxPrimaryKey, e);
        }

        // 更新微信端流水
        try {
            if (resp == null) {
                resp = new TxnResp();
                resp.setCode(BaseConstants.RESPONSE_EXCEPTION_CODE);
                resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
                logger.error("## 远程调用消费接口返回值为空，流水号[{}]", wxPrimaryKey);
            }
            wxTransLogService.updateWxTransLog(tableNum, wxPrimaryKey, resp.getCode(), transAmt);

            if (ITFRespCode.CODE51.getCode().equals(resp.getCode())) {

                MerchantInf mchntInfo = merchantInfService.getMerchantInfByCode(txnBean.getInnerMerchantNo());
                //商户是否有通卡支付权限
                if (!MchntTypeEnum.MCHNTTYPE00.getCode().equals(mchntInfo.getMchntType())) {
                    logger.error("无通卡支付权限");
                    resp.setInfo(ITFRespCode.findByCode(resp.getCode()).getValue());
                    return resp;
                }
                resp = hkbJava2TxnBusiness(request, txnBean, tableNum, settleDate, openid,
                        pinTxn, mchntName, shopName);
                return resp;
            }

            if (!BaseConstants.TXN_TRANS_RESP_SUCCESS.equals(resp.getCode())) {
                return resp;
            }
        } catch (Exception ex) {
            logger.error("## 消费交易更新微信流水异常", ex);
        }

        try {
            CardBalQueryRequest req = new CardBalQueryRequest();
            req.setChannel(ChannelCode.CHANNEL1.toString());
            req.setUserId(openid);
            req.setInnerMerchantNo(merchantCode);
            req.setTimestamp(System.currentTimeMillis());
            req.setSign(SignUtil.genSign(req));
            String cardBalJson = hkbTxnFacade.cardBalanceQueryITF(req);
            TxnResp cbResp = JSONArray.parseObject(cardBalJson, TxnResp.class);

            final TxnPackageBean txn_f = txnBean;
            final TxnResp resp_f = resp;
            final String mchntName_f = mchntName;
            final String shopName_f = shopName;
            final String openid_f = openid;
            final String transAmt_f = transAmt;
            final String txnDate = DateUtil.getChineseDateFormStr(txn_f.getSwtTxnDate() + txn_f.getSwtTxnTime());
            final WxTransLog log_f = log;
            final String accBal_f = StringUtil.isNullOrEmpty(cbResp.getBalance()) ? "0" : cbResp.getBalance();

            es.execute(new Runnable() {
                @Override
                public void run() {
                    String payAmt = "" + NumberUtils.formatMoney(resp_f.getTransAmt());// 实际支付金额
                    MpAccount mpAccount = WxMemoryCacheClient.getSingleMpAccount();

                    /**         发送客服消息
                     String notice_c = String.format(WechatCustomerMessageUtil.WECHAT_CUSTOMER_CW10_SUCCESS, txnDate,
                     mchntName_f, shopName_f, payAmt);
                     wechatMQProducerService.sendWechatMessage(mpAccount.getAccount(), notice_c, openid_f);*/

                    /**        发送模板消息                */
                    wechatMQProducerService.sendTemplateMsg(mpAccount.getAccount(), openid_f, "WX_TEMPLATE_ID_0", null,
                            WXTemplateUtil.setCardPayData(txnDate, mchntName_f, shopName_f, payAmt, NumberUtils.RMBCentToYuan(accBal_f), shopName_f));
                }
            });
            es.execute(new Runnable() {
                @Override
                public void run() {
                    /**=======================收款通知 发送B端管理员  ======================**/
                    String customerPhone = personInfService.getPhoneNumberByOpenId(openid_f);
                    /** 发送客服消息
                     String notice_m = String.format(WechatCustomerMessageUtil.WECHAT_MCHNT_CW10_SUCCESS, mchntName_f,
                     StringUtil.getPhoneNumberFormatLast4(customerPhone), shopName_f,
                     NumberUtils.RMBCentToYuan(transAmt_f), txnDate, resp_f.getInterfacePrimaryKey());*/

                    List<MerchantManager> mngList = merchantManagerService.getMerchantManagerByRoleType(
                            log_f.getMchntCode(), log_f.getShopCode(), RoleNameEnum.CASHIER_ROLE_MCHANT.getRoleType());
                    if (mngList != null && mngList.size() > 0) {
                        String mchntAcount = RedisDictProperties.getInstance().getdictValueByCode("WX_MCHNT_ACCOUNT");
                        String payType = "会员卡付款";
                        for (MerchantManager mng : mngList) {
                            /** 发送客服消息
                             wechatMQProducerService.sendWechatMessage(mchntAcount, notice_m, mng.getMangerName());*/
                            /**发送模板消息（收款通知）*/
                            wechatMQProducerService.sendTemplateMsg(mchntAcount, mng.getMangerName(), "WX_TEMPLATE_ID_6", null,
                                    WXTemplateUtil.setProceedsMsg(StringUtil.getPhoneNumberFormatLast4(customerPhone), mchntName_f, NumberUtils.RMBCentToYuan(transAmt_f), shopName_f, payType, resp_f.getInterfacePrimaryKey(), txnDate));
                        }
                    }
                }
            });
        } catch (Exception e) {
            logger.error("## 发送客服消息接口异常， 接口流水号[{}]", wxPrimaryKey, e);
        }
        return resp;
    }

    @Override
    public JSONObject cardRechargeCommit(HttpServletRequest request) {
        String openid = WxMemoryCacheClient.getOpenid(request);
        String mchntCode = request.getParameter("mchntCode"); //商户号
        String oriTxnAmount = request.getParameter("amount"); //充值的金额
        int uploadAmt = NumberUtils.disRatehundred(Double.parseDouble(oriTxnAmount));// 原交易金额单位元转分

        UserMerchantAcct userMerchantAcct = new UserMerchantAcct();
        userMerchantAcct.setExternalId(openid);
        userMerchantAcct.setMchntCode(mchntCode);
        List<UserMerchantAcct> cardList = userMerchantAcctService.getUserMerchantAcctByUser(userMerchantAcct);
        if (cardList == null || cardList.size() < 1) {
            logger.error("## openid[{}]无卡号", openid);
            return null;
        }

        userMerchantAcct = cardList.get(0);

        CtrlSystem cs = ctrlSystemService.getCtrlSystem();// TODO 放入缓存 得到日切信息
        WxTransLog log = new WxTransLog();
        if (cs == null) {
            logger.error("## 日切信息交易查询为空");
            return null;
        }
        if (!BaseConstants.TRANS_FLAG_YES.equals(cs.getTransFlag())) {
            logger.error("## 日切信息交易允许状态：日切中");
            return null;
        }
        try {
            String chargeTime = DateUtil.getCurrentTimeStr();
            String id = wxTransLogService.getPrimaryKey();
            log.setWxPrimaryKey(id);
            log.setTableNum(cs.getCurLogNum());// 得到当前可以进行操作的流水表号
            log.setSettleDate(cs.getSettleDate());// 交易日期
            log.setTransId(TransCode.CW20.getCode());//客户充值
            log.setTransSt(0);// 插入时为0，报文返回时更新为1
            log.setInsCode(userMerchantAcct.getInsCode());// 商户所属的机构code
            log.setMchntCode(userMerchantAcct.getMchntCode());
            log.setUserInfUserName(openid);
            log.setTransAmt("" + uploadAmt);// 实际交易金额
            log.setUploadAmt("" + uploadAmt);// 上送金额
            log.setTransCurrCd(BaseConstants.TRANS_CURR_CD);
            log.setTransChnl(ChannelCode.CHANNEL2.toString());

            String notifyUrl = StringUtil.trim(HttpUtil.getHttpDomain(request) + "/pay/wxNotify.html");//通知回调方法
            JSONObject obj = CustomerWxPayApiClient.unifiedOrder(WxMemoryCacheClient.getSingleMpAccount(),
                    log.getWxPrimaryKey(), log.getTransAmt(), log.getUserInfUserName(),
                    StringUtil.trim(HttpUtil.getIpAddr(request)), notifyUrl);// 统一下单
            if (!"SUCCESS".equals(obj.getString("return_code"))) {
                logger.error("## 微信支付失败--->openid[{}] return_code[{}] return_msg[{}]", openid,
                        obj.getString("return_code"), obj.getString("return_msg"));
                return null;
            }
            if (!"SUCCESS".equals(obj.getString("result_code"))) {
                logger.error("## 微信支付失败--->openid[{}] err_code[{}] err_code_des[{}]", openid,
                        obj.getString("err_code"), obj.getString("err_code_des"));
                return null;
            }
            String localSign = CustomerWxPayApi.genUnifiedOrderBackSign(obj);// 生成统一下单返回签名
            if (!localSign.equals(obj.getString("sign"))) {// 验签
                logger.error("## 卡充值统一下单返回签名验证失败--->openid[{}]", openid);
                return null;
            }

            wxTransLogService.insertWxTransLog(log);// 插入业务流水(微信端)

            String appId = obj.getString("appid");
            String timeStamp = String.valueOf(System.currentTimeMillis() / 1000);
            String nonceStr = UUID.randomUUID().toString().replace("-", "");
            String packageStr = "prepay_id=" + obj.getString("prepay_id");
            String paySign = CustomerWxPayApi.getPaySign(appId, timeStamp, nonceStr, packageStr);

            obj.put("timeStamp", timeStamp);// js调用微信支付用的时间戳
            obj.put("nonceStr", nonceStr);// js调用微信支付用的随机数
            obj.put("paySign", paySign);// js调用微信支付用的签名
            obj.put("wxPrimaryKey", id);// 调用微信支付后查询业务流水主键
            obj.put("txnDate", log.getSettleDate() + chargeTime);

            return obj;
        } catch (Exception ex) {
            logger.error("## 充值交易异常", ex);
        }

        return null;
    }

    @Override
    public WxPayCallback wxNotify(HttpServletRequest request) {
        WxPayCallback back = new WxPayCallback();
        back.setReturn_code("FAIL");// 返回code默认失败
        back.setReturn_msg("充值交易失败");// 返回msg默认业务失败
        JSONObject obj = null;
        try {
            obj = CustomerWxPayApi.parseWxPayReturnXml(request);
        } catch (Exception e) {
            logger.error("## 解析微信支付返回信息失败", e);
            return back;
        }

        String wxPrimaryKey = StringUtil.trim(obj.getString("attach"));// 业务流水(微信端)
        CtrlSystem cs = ctrlSystemService.getCtrlSystem();// TODO 得到日切信息
        if (cs == null) {
            logger.error("## 日切信息查询失败 wxPrimaryKey[{}]", wxPrimaryKey);
            return back;
        }
        WxTransLog log = wxTransLogService.getWxTransLogById(wxPrimaryKey);// 查询业务流水
        TxnPackageBean txnBean = new TxnPackageBean();
        if (log == null) {
            logger.error("## 查询业务流水为空 wxPrimaryKey[{}]", wxPrimaryKey);
            return back;
        }
        log.setTableNum(cs.getCurLogNum());
        String openid = log.getUserInfUserName();
        try {
            // 支付交易结果-成功
            if (obj.containsKey("return_code") && "SUCCESS".equals(obj.getString("return_code"))) {
                // 业务结果-成功
                if (obj.containsKey("result_code") && "SUCCESS".equals(obj.getString("result_code"))) {
                    // 业务数据交易状态为0时表示微信支付通知未处理，此时处理业务逻辑
                    if (log.getTransSt() == 0) {
                        String cashFee = obj.getString("cash_fee");// 现金支付金额
                        UserMerchantAcct acc = new UserMerchantAcct();
                        acc.setExternalId(openid);
                        acc.setMchntCode(log.getMchntCode());
                        List<UserMerchantAcct> cardList = userMerchantAcctService.getUserMerchantAcctByUser(acc);
                        if (cardList == null || cardList.size() < 1) {
                            logger.error("## openid[{}]无卡号", openid);
                            return back;
                        }
                        acc = cardList.get(0);
                        int transAmt = wxDroolsExcutor.getRechargeDiscount(acc.getMchntId(), null, Integer.parseInt(cashFee));// 调用规则引擎
                        String transId = obj.getString("transaction_id");// 微信支付订单号
                        String notifySign = CustomerWxPayApi.getPayNotifySign(obj);// 生成通知签名
                        // 验签通过
                        if (obj.containsKey("sign") && obj.getString("sign").equals(notifySign)) {
                            txnBean.setTxnType(TransCode.CW20.getCode() + "0");// 交易类型，发送报文时补0
                            txnBean.setSwtTxnDate(DateUtil.getCurrentDateStr());// 交易日期
                            txnBean.setSwtTxnTime(DateUtil.getCurrentTimeStr());// 交易时间
                            txnBean.setSwtSettleDate(log.getSettleDate());// 清算日期
                            txnBean.setChannel(ChannelCode.CHANNEL2.toString());// 渠道号
                            txnBean.setSwtFlowNo(wxPrimaryKey);
                            txnBean.setIssChannel(log.getInsCode());// 机构渠道号
                            txnBean.setInnerMerchantNo(log.getMchntCode());// 商户号
                            txnBean.setInnerShopNo(log.getShopCode());// 门店号
                            txnBean.setTxnAmount("" + transAmt);// 交易金额
                            txnBean.setOriTxnAmount(log.getUploadAmt());// 原交易金额
                            txnBean.setCardNo(log.getUserInfUserName());// 卡号
                            try {
                                String json = java2TxnBusinessFacade.rechargeTransactionITF(txnBean);// 远程调用充值接口
                                TxnResp resp = JSONArray.parseObject(json, TxnResp.class);
                                log.setTransAmt("" + transAmt);// 订单实际支付金额
                                log.setDmsRelatedKey(transId);// 微信流水号
                                log.setRespCode(resp.getCode());// 返回码

                                wxTransLogService.updateWxTransLog(log, null);// 更新微信端流水
                                if (BaseConstants.TXN_TRANS_RESP_SUCCESS.equals(resp.getCode())) {// 远程接口调用成功
                                    back.setReturn_code("SUCCESS");
                                    back.setReturn_msg("OK");
                                    logger.info("微信回调成功：openid[{}] 业务流水[{}] 微信订单号[{}] 支付完成时间[{}]", openid,
                                            log.getWxPrimaryKey(), log.getDmsRelatedKey(), obj.getString("time_end"));
                                    return back;
                                } else {
                                    logger.error("## 微信回调失败：openid[{}] resp[{}]", openid, resp.getInfo());
                                }
                            } catch (Exception e) {
                                logger.error("## 微信回调--->调用充值接口异常，openid[{}] 流水号[{}]", openid, wxPrimaryKey, e);
                            }
                        } else {
                            back.setReturn_msg("签名失败");
                            logger.error("##　微信回调--->验签失败 openid[{}] 签名参数[{}]", openid, obj);
                        }
                    } else {// 业务数据交易状态不为0时(通常为1)表示支付通知已处理，此时直接给微信通知返回成功
                        if (BaseConstants.TXN_TRANS_RESP_SUCCESS.equals(log.getRespCode())) {
                            back.setReturn_code("SUCCESS");
                            back.setReturn_msg("OK");
                        }
                        return back;
                    }
                } else {
                    logger.error("## 微信回调失败 openid[{}] err_code_des[{}]", openid, obj.getString("err_code_des"));
                    back.setReturn_msg(obj.getString("err_code_des"));
                    back = publicService.queryWxPayReusult(log, back);// 支付失败时调用微信支付查询订单API
                }
            } else {
                logger.error("## 微信回调失败  openid[{}] return_msg[{}]", openid, obj.getString("return_msg"));
                back.setReturn_msg(obj.getString("return_msg"));
            }

            log.setRespCode(BaseConstants.RESPONSE_EXCEPTION_CODE);// 失败-返回码
            wxTransLogService.updateWxTransLog(log, null);
        } catch (Exception e) {
            logger.error("## 微信回调失败  openid[{}]", openid, e);
        }
        return back;
    }

    @Override
    public JSONObject queryPayReusult(HttpServletRequest request) {
        String wxPrimaryKey = request.getParameter("wxPrimaryKey");
        JSONObject obj = new JSONObject();
        WxPayCallback back = new WxPayCallback();

        back.setReturn_code("FAIL");// 返回code默认失败
        back.setReturn_msg("查询订单失败");// 返回msg默认业务失败
        WxTransLog log = wxTransLogService.getWxTransLogById(wxPrimaryKey);// 查询业务流水
        WxPayCallback callBack = publicService.queryWxPayReusult(log, back);

        if ("SUCCESS".equals(callBack.getReturn_code()))
            obj.put("returnCode", BaseConstants.SUCCESS);
        else
            obj.put("returnCode", BaseConstants.FAILED);
        return obj;
    }

    @Override
    public JSONObject wxCustomNotify(HttpServletRequest request) {
        JSONObject obj = new JSONObject();
        obj.put("returnCode", BaseConstants.FAILED);
        String wxPrimaryKey = request.getParameter("wxPrimaryKey");
        String txnDate = request.getParameter("txnDate");

        WxTransLog log = wxTransLogService.getWxTransLogById(wxPrimaryKey);// 查询业务流水
        // 若没有微信支付订单号则不发送客服消息
        if (log == null || !BaseConstants.TXN_TRANS_RESP_SUCCESS.equals(log.getRespCode())
                || StringUtil.isNullOrEmpty(log.getDmsRelatedKey())) {
            return obj;
        }

        WxPayCallback back = new WxPayCallback();
        back.setReturn_code("FAIL");// 返回code默认失败
        back.setReturn_msg("查询订单失败");// 返回msg默认业务失败
        WxPayCallback callBack = publicService.queryWxPayReusult(log, back);// 查询微信支付订单
        if (!"SUCCESS".equals(callBack.getReturn_code())) {
            return obj;
        }

        String openid = log.getUserInfUserName();
        String mchntCode = log.getMchntCode();

        double chargeAmt = NumberUtils.formatMoney(log.getUploadAmt());// 充值金额为上送金额
        String temp = "" + (Integer.parseInt(log.getTransAmt()) - Integer.parseInt(log.getUploadAmt()));// 计算优惠金额
        double giveAmt = NumberUtils.formatMoney(temp);

        UserMerchantAcct acc = new UserMerchantAcct();
        acc.setExternalId(openid);
        acc.setMchntCode(mchntCode);
        List<UserMerchantAcct> cardList = userMerchantAcctService.getUserMerchantAcctByUser(acc);
        if (cardList != null && cardList.size() > 0) {
            acc = cardList.get(0);
            String txnDateChinese = "";
            try {
                txnDateChinese = DateUtil.getChineseDateFormStr(txnDate);
            } catch (Exception e) {
                logger.error("充值交易发送客服消息失败：", e);
            }
            String content = "充值成功提醒：您于" + txnDateChinese + "通过微信支付对【" + acc.getProductName() + "】充值 " +
                    chargeAmt + " 元，赠送 " + giveAmt + " 元。当前余额 " + acc.getAccBal() + " 元。";
            MpAccount mpAccount = WxMemoryCacheClient.getSingleMpAccount();
            obj = WxApiClient.sendCustomTextMessage(openid, content, mpAccount);
            if (obj != null) {
                obj.put("returnCode", BaseConstants.SUCCESS);
                obj.put("chargeAmt", "" + chargeAmt);
                obj.put("giveAmt", "" + giveAmt);
            }
        }
        return obj;
    }

    @Override
    public JSONObject BSCweChatQuickPayCommit(HttpServletRequest request) {
        JSONObject obj = new JSONObject();
        String wxTransLogKey = request.getParameter("wxTransLogKey"); // 微信端流水号
        WxTransLog log = wxTransLogService.getWxTransLogById(wxTransLogKey);
        if (log == null) {
            logger.error("## 查询微信业务流水为空 wxTransLogKey[{}]", wxTransLogKey);
            return null;
        }
        String openid = log.getUserInfUserName();

        // 查询用户是否开卡
        UserMerchantAcct acc = new UserMerchantAcct();
        acc.setExternalId(openid);
        acc.setMchntCode(log.getMchntCode());
        List<UserMerchantAcct> cardList = userMerchantAcctService.getUserMerchantAcctByUser(acc);
        if (cardList == null || cardList.size() < 1) {
            try {
                // 请求开卡
                TxnResp cardCheckResp = userMerchantAcctService.doCustomerAccountOpening(null, null, openid,
                        log.getMchntCode(), log.getInsCode());
                if (cardCheckResp != null && !BaseConstants.TXN_TRANS_RESP_SUCCESS.equals(cardCheckResp.getCode())) {
                    userMerchantAcctService.doCustomerAccountOpening(null, null, openid, log.getMchntCode(),
                            log.getInsCode());
                }
            } catch (Exception ex) {
                logger.error("## B扫C扫码快捷支付，用户openid[{}]开卡失败", openid, ex);
            }
        }
        try {
            String chargeTime = DateUtil.getCurrentTimeStr();
            String notifyUrl = HttpWebUtil.getCustomerDomainUrl() + "pay/BSCweChantQuickNotify.html"; //微信快捷支付回调
            obj = CustomerWxPayApiClient.unifiedOrder(WxMemoryCacheClient.getSingleMpAccount(), log.getWxPrimaryKey(),
                    log.getTransAmt(), openid, StringUtil.trim(HttpUtil.getIpAddr(request)), notifyUrl);// 统一下单
            if (!"SUCCESS".equals(obj.getString("return_code"))) {
                logger.error("## B扫C微信快捷支付失败--->openid[{}] return_code[{}] return_msg[{}]", openid,
                        obj.getString("return_code"), obj.getString("return_msg"));
                return null;
            }
            if (!"SUCCESS".equals(obj.getString("result_code"))) {
                logger.error("## B扫C微信快捷支付失败--->openid[{}] err_code[{}] err_code_des[{}]", openid,
                        obj.getString("err_code"), obj.getString("err_code_des"));
                return null;
            }
            String sign = obj.getString("sign");// 微信返回的签名
            String localSign = CustomerWxPayApi.genUnifiedOrderBackSign(obj);// 生成统一下单返回签名
            if (!localSign.equals(sign)) {// 验签
                logger.error("## B扫C统一下单返回签名验证失败--->openid[{}]", openid);
                return null;
            }
            String appId = obj.getString("appid");
            String timeStamp = String.valueOf(System.currentTimeMillis() / 1000);
            String nonceStr = UUID.randomUUID().toString().replace("-", "");
            String packageStr = "prepay_id=" + obj.getString("prepay_id");
            String paySign = CustomerWxPayApi.getPaySign(appId, timeStamp, nonceStr, packageStr);

            obj.put("timeStamp", timeStamp);// js调用微信支付用的时间戳
            obj.put("nonceStr", nonceStr);// js调用微信支付用的随机数
            obj.put("paySign", paySign);// js调用微信支付用的签名
            obj.put("wxPrimaryKey", log.getWxPrimaryKey());// 调用微信支付后查询业务流水主键
            obj.put("txnDate", log.getSettleDate() + chargeTime);
            logger.info("B扫C微信快捷支付成功，openid[{}]，返回数据：{}", openid, obj.toString());

            return obj;
        } catch (Exception ex) {
            logger.error("## B扫C微信快捷支付异常", ex);
        }

        return null;
    }

    @Override
    public String BSCweChantQuickNotify(HttpServletRequest request) {
        WxPayCallback back = new WxPayCallback();
        back.setReturn_code("FAIL");// 返回code默认失败
        back.setReturn_msg("快捷支付交易失败");// 返回msg默认业务失败
        JSONObject obj = null;
        try {
            obj = CustomerWxPayApi.parseWxPayReturnXml(request);
        } catch (Exception e) {
            logger.error("## 解析B扫C快捷支付返回信息失败", e);
            return MsgXmlUtil.toXML(back);
        }

        String wxPrimaryKey = StringUtil.trim(obj.getString("attach"));// 业务流水(微信端)
        CtrlSystem cs = ctrlSystemService.getCtrlSystem();// TODO 得到日切信息
        if (cs == null) {
            logger.error("## 日切信息查询失败 wxPrimaryKey[{}]", wxPrimaryKey);
            return null;
        }

        WxTransLog log = wxTransLogService.getWxTransLogById(wxPrimaryKey);// 查询业务流水
        TxnPackageBean txnBean = new TxnPackageBean();
        if (log == null) {
            logger.error("## 查询业务流水为空 wxPrimaryKey[{}]", wxPrimaryKey);
            return null;
        }
        log.setTableNum(cs.getCurLogNum());
        String openid = log.getUserInfUserName();
        // 支付交易结果-成功
        if (obj.containsKey("return_code") && "SUCCESS".equals(obj.getString("return_code"))) {
            // 业务结果-成功
            if (obj.containsKey("result_code") && "SUCCESS".equals(obj.getString("result_code"))) {
                // 业务数据交易状态为0时表示微信支付通知未处理，此时处理业务逻辑
                if (log.getTransSt() == 0) {
                    String cashFee = obj.getString("cash_fee");// 现金支付金额
                    // 查询用户是否开卡
                    UserMerchantAcct acc = new UserMerchantAcct();
                    acc.setExternalId(log.getUserInfUserName());
                    acc.setMchntCode(log.getMchntCode());
                    List<UserMerchantAcct> cardList = userMerchantAcctService.getUserMerchantAcctByUser(acc);
                    if (cardList == null || cardList.size() < 1) {
                        try {
                            // 请求开卡
                            TxnResp cardCheckResp = userMerchantAcctService.doCustomerAccountOpening(null, null,
                                    openid, log.getMchntCode(), log.getInsCode());
                            if (cardCheckResp != null && !BaseConstants.TXN_TRANS_RESP_SUCCESS.equals(cardCheckResp.getCode())) {
                                userMerchantAcctService.doCustomerAccountOpening(null, null, openid,
                                        log.getMchntCode(), log.getInsCode());
                            }
                        } catch (Exception ex) {
                            logger.error("## B扫C快捷支付，用户openid[{}]开卡失败", openid, ex);
                        }
                    }

                    int transAmt = Integer.parseInt(log.getTransAmt());// 获取实际订单金额
                    String transId = obj.getString("transaction_id");// 微信支付订单号
                    String notifySign = CustomerWxPayApi.getPayNotifySign(obj);// 生成通知签名
                    if (obj.containsKey("sign") && obj.getString("sign").equals(notifySign)) {// 验签通过
                        UserInf userInf = userInfService.getUserInfByOpenId(openid);
                        txnBean.setTxnType(TransCode.CW71.getCode() + "0");// 交易类型，发送报文时补0
                        txnBean.setSwtTxnDate(DateUtil.getCurrentDateStr());// 交易日期
                        txnBean.setSwtTxnTime(DateUtil.getCurrentTimeStr());// 交易时间
                        txnBean.setSwtSettleDate(log.getSettleDate());// 清算日期
                        txnBean.setSwtFlowNo(log.getWxPrimaryKey());
                        txnBean.setIssChannel(log.getInsCode());// 机构号，从机构信息表中获取
                        txnBean.setInnerMerchantNo(log.getMchntCode());// 商户号
                        txnBean.setInnerShopNo(log.getShopCode());// 门店号
                        txnBean.setTxnAmount(String.valueOf(transAmt));// 交易金额
                        txnBean.setOriTxnAmount(log.getUploadAmt());// 原交易金额
                        txnBean.setChannel(ChannelCode.CHANNEL2.toString());// 渠道号
                        txnBean.setUserId(userInf.getUserId());// 用户id
                        txnBean.setCardNo("U" + openid);
                        txnBean.setTimestamp(System.currentTimeMillis());// 时间戳
                        String signature = TxnChannelSignatureUtil.genSign(txnBean); // 生成的签名
                        txnBean.setSignature(signature);
                        String json = new String();
                        TxnResp resp = new TxnResp();
                        try {
                            json = java2TxnBusinessFacade.quickConsumeTransactionITF(txnBean);// 远程调用快捷消费接口
                            resp = JSONArray.parseObject(json, TxnResp.class);
                            logger.info("B扫C远程调用快捷消费接口返回resp{}", JSONArray.toJSONString(resp));

                            if (resp != null && !BaseConstants.TXN_TRANS_RESP_SUCCESS.equals(resp.getCode())) {
                                json = java2TxnBusinessFacade.transExceptionQueryITF(log.getWxPrimaryKey());
                                resp = JSONArray.parseObject(json, TxnResp.class);
                            }
                        } catch (Exception e) {
                            logger.error("## B扫C远程调用快捷支付接口异常，流水号[{}]", wxPrimaryKey, e);
                        }
                        try {
                            // 更新微信端流水
                            log.setTransId(TransCode.CW71.getCode());
                            log.setTransChnl(ChannelCode.CHANNEL2.toString());
                            log.setDmsRelatedKey(transId);// 微信流水号
                            log.setRespCode(resp.getCode());// 返回码
                            wxTransLogService.updateWxTransLog(log, resp);// 更新微信端流水
                        } catch (Exception e) {
                            logger.error("## B扫C更新微信端流水异常，流水号[{}]", wxPrimaryKey, e);
                        }

                        // WEBSOCKET发送消息给页面
                        try {
                            WsScanCodePayMsg wsMsg = new WsScanCodePayMsg();
                            wsMsg.setText(resp.getInfo());
                            wsMsg.setCode(resp.getCode());
                            wsMsg.setFromUser(log.getUserInfUserName());
                            wsMsg.setToUser(log.getOperatorOpenId());
                            wsMsg.setReqType("C");
                            wsMsg.setSendType(BaseConstants.WSSendTypeEnum.SEND_TYPE_80.getCode());
                            wsMsg.setTransAmt(NumberUtils.RMBCentToYuan(transAmt)); //交易金额
                            wsMsg.setWxTransLogKey(log.getWxPrimaryKey());
                            if (JedisUtils.JEDIS_STATUS) {// 发送给Ｂ端业务处理结果
                                JedisUtils.pubChannel(BaseConstants.RedisChannelEnum.B_SCAN_QR_CODE_PAY.getCode(),
                                        JSONArray.toJSONString(wsMsg));
                            }
                            wsMsg.setToUser(log.getUserInfUserName());
                            if (JedisUtils.JEDIS_STATUS) {// 发送给Ｃ端业务处理结果
                                JedisUtils.pubChannel(BaseConstants.RedisChannelEnum.B_SCAN_QR_CODE_PAY.getCode(),
                                        JSONArray.toJSONString(wsMsg));
                            }
                        } catch (Exception e) {
                            logger.error("## B扫C发送websocket异常，流水号[{}]", wxPrimaryKey, e);
                        }

                        MpAccount c_mpAccount = WxMemoryCacheClient.getMpAccount(WxCmsContents.getCurrMpAccount());
                        MerchantInf merchantInf = merchantInfService.getMerchantInfByCode(log.getMchntCode());// 所属商户
                        ShopInf shopInf = shopInfService.getShopInfByCode(log.getShopCode());// 所属门店


                        String payAmt = NumberUtils.RMBCentToYuan(transAmt);// 实际支付金额
//						int temp = Integer.parseInt(log.getUploadAmt()) - transAmt;// 计算优惠金额
//						String giveAmt = NumberUtils.RMBCentToYuan(temp);// 优惠金额 分转元

                        //业务逻辑处理成功 发送客服消息
                        if (BaseConstants.TXN_TRANS_RESP_SUCCESS.equals(resp.getCode())) {
                            try {
                                // 客服消息-客户
                                String txnDate = DateUtil.getChineseDateFormStr(txnBean.getSwtTxnDate() + txnBean.getSwtTxnTime());
                                String notice_c = String.format(WechatCustomerMessageUtil.WECHAT_CUSTOMER_W71_SUCCESS,
                                        txnDate, merchantInf.getMchntName(), shopInf.getShopName(), payAmt);
                                wechatMQProducerService.sendWechatMessage(c_mpAccount.getAccount(), notice_c, openid);
                                /**=======================收款通知 发送B端管理员  ======================**/
                                String customerPhone = personInfService.getPhoneNumberByOpenId(openid);
                                /**    发送客服消息
                                 String notice_m = String.format(WechatCustomerMessageUtil.WECHAT_MCHNT_W71_SUCCESS,
                                 merchantInf.getMchntName(), StringUtil.getPhoneNumberFormatLast4(customerPhone),
                                 shopInf.getShopName(), NumberUtils.RMBCentToYuan(transAmt), txnDate,
                                 resp.getInterfacePrimaryKey());*/

                                List<MerchantManager> mngList = merchantManagerService.getMerchantManagerByRoleType(log.getMchntCode(),
                                        log.getShopCode(), RoleNameEnum.CASHIER_ROLE_MCHANT.getRoleType());
                                if (mngList != null && mngList.size() > 0) {
                                    String mchntAcount = RedisDictProperties.getInstance().getdictValueByCode("WX_MCHNT_ACCOUNT");
                                    String payType = "微信支付";
                                    for (MerchantManager mng : mngList) {
                                        /** 发送客服消息
                                         wechatMQProducerService.sendWechatMessage(mchntAcount, notice_m, mng.getMangerName());*/
                                        /**发送模板消息（收款通知）*/
                                        wechatMQProducerService.sendTemplateMsg(mchntAcount, mng.getMangerName(), "WX_TEMPLATE_ID_6", null,
                                                WXTemplateUtil.setProceedsMsg(StringUtil.getPhoneNumberFormatLast4(customerPhone), merchantInf.getMchntName(), NumberUtils.RMBCentToYuan(transAmt), shopInf.getShopName(), payType, resp.getInterfacePrimaryKey(), txnDate));
                                    }
                                }
                            } catch (Exception e) {
                                logger.error("## B扫C发送客服消息异常，流水号[{}]", wxPrimaryKey, e);
                            }
                        } else {// 业务逻辑处理失败，进行退款
                            try {
                                String txnDate = DateUtil.getChineseDateFormStr(txnBean.getSwtTxnDate() + txnBean.getSwtTxnTime());
                                json = java2TxnBusinessFacade.transExceptionQueryITF(log.getWxPrimaryKey());
                                resp = JSONArray.parseObject(json, TxnResp.class);
                                if (!BaseConstants.TXN_TRANS_RESP_SUCCESS.equals(resp.getCode())) {
                                    // 请求微信退款  暂时只做退款请求 不做其他业务处理
                                    if (cashFee != null)
                                        log.setTransAmt(cashFee);
                                    //微信退款
                                    JSONObject refundResJson = WXPayOrderUtil.wxTransRefundOrder(log, request);
                                    if (refundResJson != null) {
                                        if (refundResJson.containsKey("return_code") && "SUCCESS".equals(refundResJson.getString("return_code"))) {
                                            String notice_c = String.format(WechatCustomerMessageUtil.WECHAT_CUSTOMER_W71_REFUNDORDER_SUCCESS,
                                                    txnDate, merchantInf.getMchntName(), shopInf.getShopName(), payAmt);
                                            wechatMQProducerService.sendWechatMessage(c_mpAccount.getAccount(), notice_c, openid);
                                        } else {
                                            String notice_c = String.format(WechatCustomerMessageUtil.WECHAT_CUSTOMER_W71_REFUNDORDER_FAIL,
                                                    log.getWxPrimaryKey());
                                            wechatMQProducerService.sendWechatMessage(c_mpAccount.getAccount(), notice_c, openid);
                                        }
                                    }
                                }
                            } catch (Exception e) {
                                logger.error("## B扫C用户退款异常，流水号：" + wxPrimaryKey, e);
                            }
                        }
                    } else {
                        back.setReturn_msg("签名失败");
                        logger.info("## B扫C验签失败，流水号[{}]，签名参数为{}", wxPrimaryKey, obj.toString());
                        //微信退款
                        WXPayOrderUtil.wxTransRefundOrder(log, request);
                        wxTransLogService.updateWxTransLog(log, null);// 更新微信端流水
                        back.setReturn_code("SUCCESS");
                        back.setReturn_msg("OK");
                        return MsgXmlUtil.toXML(back);
                    }
                } else {// 业务数据交易状态不为0时(通常为1)表示支付通知已处理，此时直接给微信通知返回成功
                    if (BaseConstants.TXN_TRANS_RESP_SUCCESS.equals(log.getRespCode())) {
                        back.setReturn_code("SUCCESS");
                        back.setReturn_msg("OK");
                    }
                    return MsgXmlUtil.toXML(back);
                }
            } else {
                logger.error("## B扫C微信支付失败：流水号[{}] err_code_des[{}]", wxPrimaryKey, obj.getString("err_code_des"));
            }
        } else {
            logger.info("## B扫C微信支付失败：流水号[{}] return_msg[{}]", wxPrimaryKey, obj.getString("return_msg"));
            back.setReturn_msg(obj.getString("return_msg"));
        }
        return MsgXmlUtil.toXML(back);
    }

    @Override
    public TxnResp BSCweChantQuickPayOrderQuery(HttpServletRequest request) {
        String wxPrimaryKey = StringUtil.trim(request.getParameter("wxPrimaryKey"));// 业务流水(微信端)
        TxnResp resp = new TxnResp();
        WxTransLog log = wxTransLogService.getWxTransLogById(wxPrimaryKey);// 查询业务流水
        String json = "";
        try {
            if (log != null) {
                if (log.getTransSt() == 0) {
                    json = java2TxnBusinessFacade.transExceptionQueryITF(wxPrimaryKey);  //交易异常查询
                    resp = JSONArray.parseObject(json, TxnResp.class);
                    if (resp == null || !BaseConstants.TXN_TRANS_RESP_SUCCESS.equals(resp.getCode())) {
                        Thread.sleep(2000);
                        json = java2TxnBusinessFacade.transExceptionQueryITF(wxPrimaryKey);  //交易异常查询
                        resp = JSONArray.parseObject(json, TxnResp.class);
                        if (resp == null)
                            resp = new TxnResp();
                    }
                    resp.setTransAmt(NumberUtils.RMBCentToYuan(log.getTransAmt()));
                } else {
                    resp.setCode(StringUtil.nullToString(log.getRespCode()));
                    resp.setTransAmt(NumberUtils.RMBCentToYuan(log.getTransAmt()));
                }
                if (!BaseConstants.TXN_TRANS_RESP_SUCCESS.equals(resp.getCode()))
                    resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
                resp.setTransAmt(NumberUtils.RMBCentToYuan(log.getTransAmt()));
            }
        } catch (Exception e) {
            resp.setCode(BaseConstants.RESPONSE_EXCEPTION_CODE);// 失败-返回码
            resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
        }
        return resp;
    }

    @Override
    public JSONObject CSBweChatQuickPayCommit(HttpServletRequest request) {
        JSONObject obj = new JSONObject();
        String wxTransLogKey = request.getParameter("wxTransLogKey");// 微信端流水主键
        WxTransLog log = wxTransLogService.getWxTransLogById(wxTransLogKey);
        if (log == null) {
            logger.error("## 查询微信业务流水为空 wxTransLogKey[{}]", wxTransLogKey);
            return null;
        }
        String openid = log.getUserInfUserName();

        // 查询用户是否开卡
        UserMerchantAcct acc = new UserMerchantAcct();
        acc.setExternalId(log.getUserInfUserName());
        acc.setMchntCode(log.getMchntCode());
        List<UserMerchantAcct> cardList = userMerchantAcctService.getUserMerchantAcctByUser(acc);
        if (cardList == null || cardList.size() < 1) {
            try {
                // 请求开卡
                TxnResp cardCheckResp = userMerchantAcctService.doCustomerAccountOpening(null, null, openid,
                        log.getMchntCode(), log.getInsCode());
                if (cardCheckResp != null && !BaseConstants.TXN_TRANS_RESP_SUCCESS.equals(cardCheckResp.getCode())) {
                    userMerchantAcctService.doCustomerAccountOpening(null, null, openid, log.getMchntCode(),
                            log.getInsCode());
                }
            } catch (Exception ex) {
                logger.error("## C扫B扫码快捷支付，用户openid[{}]开卡失败", openid, ex);
            }
        }
        try {
            String chargeTime = DateUtil.getCurrentTimeStr();
            String notifyUrl = HttpWebUtil.getCustomerDomainUrl() + "pay/CSBweChantQuickNotify.html"; //微信回调
            obj = CustomerWxPayApiClient.unifiedOrder(WxMemoryCacheClient.getSingleMpAccount(), log.getWxPrimaryKey(),
                    log.getTransAmt(), log.getUserInfUserName(), StringUtil.trim(HttpUtil.getIpAddr(request)),
                    notifyUrl);// 统一下单

            if (!"SUCCESS".equals(obj.getString("return_code"))) {
                logger.error("## C扫B微信快捷支付失败--->openid[{}] return_code[{}] return_msg[{}]", openid,
                        obj.getString("return_code"), obj.getString("return_msg"));
                return null;
            }
            if (!"SUCCESS".equals(obj.getString("result_code"))) {
                logger.error("## C扫B微信快捷支付失败--->openid[{}] err_code[{}] err_code_des[{}]", openid,
                        obj.getString("err_code"), obj.getString("err_code_des"));
                return null;
            }

            String sign = obj.getString("sign");// 微信返回的签名
            String localSign = CustomerWxPayApi.genUnifiedOrderBackSign(obj);// 生成统一下单返回签名
            if (!localSign.equals(sign)) {// 验签
                logger.error("## C扫B统一下单返回签名验证失败--->openid[{}]", openid);
                return null;
            }

            String appId = obj.getString("appid");
            String timeStamp = String.valueOf(System.currentTimeMillis() / 1000); //上报时的时间戳，单位秒
            String nonceStr = UUID.randomUUID().toString().replace("-", "");
            String packageStr = "prepay_id=" + obj.getString("prepay_id");
            String paySign = CustomerWxPayApi.getPaySign(appId, timeStamp, nonceStr, packageStr);

            obj.put("timeStamp", timeStamp);// js调用微信支付用的时间戳
            obj.put("nonceStr", nonceStr);// js调用微信支付用的随机数
            obj.put("paySign", paySign);// js调用微信支付用的签名
            obj.put("wxPrimaryKey", log.getWxPrimaryKey());// 调用微信支付后查询业务流水主键
            obj.put("txnDate", log.getSettleDate() + chargeTime);
            logger.info("C扫B微信快捷支付成功，openid[{}]，返回数据：{}", openid, obj.toString());

            return obj;
        } catch (Exception ex) {
            logger.error("## C扫B微信快捷支付异常", ex);
        }

        return null;
    }

    @Override
    public String CSBweChantQuickNotify(HttpServletRequest request) {
        WxPayCallback back = new WxPayCallback();
        back.setReturn_code("FAIL");// 返回code默认失败
        back.setReturn_msg("快捷支付交易失败");// 返回msg默认业务失败
        JSONObject obj = null;
        try {
            obj = CustomerWxPayApi.parseWxPayReturnXml(request);
        } catch (Exception e) {
            logger.error("## 解析C扫B快捷支付返回信息失败", e);
            return MsgXmlUtil.toXML(back);
        }

        String wxPrimaryKey = StringUtil.trim(obj.getString("attach"));// 业务流水(微信端)
        CtrlSystem cs = ctrlSystemService.getCtrlSystem();// TODO 得到日切信息
        if (cs == null) {
            logger.error("## 日切信息查询失败 wxPrimaryKey[{}]", wxPrimaryKey);
            return null;
        }
        WxTransLog log = wxTransLogService.getWxTransLogById(wxPrimaryKey);// 查询业务流水
        if (log == null) {
            logger.error("## 查询业务流水为空 wxPrimaryKey[{}]", wxPrimaryKey);
            return null;
        }
        TxnPackageBean txnBean = new TxnPackageBean();
        log.setTableNum(cs.getCurLogNum());
        String openid = log.getUserInfUserName();
        // 支付交易结果-成功
        if (obj.containsKey("return_code") && "SUCCESS".equals(obj.getString("return_code"))) {
            // 业务结果-成功
            if (obj.containsKey("result_code") && "SUCCESS".equals(obj.getString("result_code"))) {
                // 业务数据交易状态为0时表示微信支付通知未处理，此时处理业务逻辑
                if (log.getTransSt() == 0) {
                    String cashFee = obj.getString("cash_fee");// 现金支付金额
                    UserMerchantAcct acc = new UserMerchantAcct();
                    acc.setExternalId(openid);
                    acc.setMchntCode(log.getMchntCode());
                    List<UserMerchantAcct> cardList = userMerchantAcctService.getUserMerchantAcctByUser(acc);
                    if (cardList == null || cardList.size() < 1) {
                        try {
                            // 请求开卡
                            TxnResp cardCheckResp = userMerchantAcctService.doCustomerAccountOpening(null, null,
                                    openid, log.getMchntCode(), log.getInsCode());
                            if (cardCheckResp != null && !BaseConstants.TXN_TRANS_RESP_SUCCESS.equals(cardCheckResp.getCode())) {
                                userMerchantAcctService.doCustomerAccountOpening(null, null, openid,
                                        log.getMchntCode(), log.getInsCode());
                            }
                        } catch (Exception ex) {
                            logger.error("## C扫B扫码快捷支付，用户openid[{}]开卡失败", openid, ex);
                        }
                    }
                    int transAmt = Integer.parseInt(log.getTransAmt());// 获取实际支付金额
                    String transId = obj.getString("transaction_id");// 微信支付订单号
                    String notifySign = CustomerWxPayApi.getPayNotifySign(obj);// 生成通知签名
                    // 验签通过
                    if (obj.containsKey("sign") && obj.getString("sign").equals(notifySign)) {
                        UserInf userInf = userInfService.getUserInfByOpenId(openid);
                        txnBean.setTxnType(TransCode.CW71.getCode() + "0");// 交易类型，发送报文时补0
                        txnBean.setSwtTxnDate(DateUtil.getCurrentDateStr());// 交易日期
                        txnBean.setSwtTxnTime(DateUtil.getCurrentTimeStr());// 交易时间
                        txnBean.setSwtSettleDate(log.getSettleDate());// 清算日期
                        txnBean.setSwtFlowNo(log.getWxPrimaryKey());
                        txnBean.setIssChannel(log.getInsCode());// 机构号，从机构信息表中获取
                        txnBean.setInnerMerchantNo(log.getMchntCode());// 商户号
                        txnBean.setInnerShopNo(log.getShopCode());// 门店号
                        txnBean.setTxnAmount(String.valueOf(transAmt));// 交易金额
                        txnBean.setOriTxnAmount(log.getUploadAmt());// 原交易金额
                        txnBean.setChannel(ChannelCode.CHANNEL2.toString());// 渠道号
                        txnBean.setUserId(userInf.getUserId());// 用户id
                        txnBean.setCardNo("U" + openid);
                        txnBean.setTimestamp(System.currentTimeMillis());// 时间戳
                        String signature = TxnChannelSignatureUtil.genSign(txnBean);// 生成的签名
                        txnBean.setSignature(signature);
                        String json = new String();
                        TxnResp resp = new TxnResp();
                        try {
                            json = java2TxnBusinessFacade.quickConsumeTransactionITF(txnBean);// 远程调用快捷消费接口
                            resp = JSONArray.parseObject(json, TxnResp.class);
                            logger.info("C扫B远程调用快捷消费接口返回resp{}", JSONArray.toJSONString(resp));

                            if (resp != null && !BaseConstants.TXN_TRANS_RESP_SUCCESS.equals(resp.getCode())) {
                                json = java2TxnBusinessFacade.transExceptionQueryITF(log.getWxPrimaryKey());
                                resp = JSONArray.parseObject(json, TxnResp.class);
                            }
                        } catch (Exception e) {
                            logger.error("## C扫B远程调用快捷支付接口异常，流水号[{}]", wxPrimaryKey, e);
                        }

                        try {
                            // 更新微信端流水
                            log.setTransId(TransCode.CW71.getCode());
                            log.setTransChnl(ChannelCode.CHANNEL2.toString());
                            log.setDmsRelatedKey(transId);// 微信流水号
                            log.setRespCode(resp.getCode());// 返回码
                            wxTransLogService.updateWxTransLog(log, resp);// 更新微信端流水
                        } catch (Exception e) {
                            logger.error("## C扫B更新微信端流水异常，流水号[{}]", wxPrimaryKey, e);
                        }

                        MpAccount c_mpAccount = WxMemoryCacheClient.getMpAccount(WxCmsContents.getCurrMpAccount());
                        MerchantInf merchantInf = merchantInfService.getMerchantInfByCode(log.getMchntCode());// 所属商户
                        ShopInf shopInf = shopInfService.getShopInfByCode(log.getShopCode());// 所属门店

                        String payAmt = NumberUtils.RMBCentToYuan(transAmt);// 实际支付金额
//						int temp = Integer.parseInt(log.getUploadAmt())-transAmt;// 计算优惠金额
//						String giveAmt = NumberUtils.RMBCentToYuan(temp);// 优惠金额 分转元

                        if (BaseConstants.TXN_TRANS_RESP_SUCCESS.equals(resp.getCode())) {
                            try {
                                // 客服消息-客户
                                String txnDate = DateUtil.getChineseDateFormStr(txnBean.getSwtTxnDate() + txnBean.getSwtTxnTime());
								/*String notice_c = String.format(WechatCustomerMessageUtil.WECHAT_CUSTOMER_W71_SUCCESS, 
										txnDate, merchantInf.getMchntName(), shopInf.getShopName(), payAmt);
								wechatMQProducerService.sendWechatMessage(c_mpAccount.getAccount(), notice_c, openid);*/
                                /**        发送模板消息-客户                */
                                wechatMQProducerService.sendTemplateMsg(c_mpAccount.getAccount(), openid, "WX_TEMPLATE_ID_1", null,
                                        WXTemplateUtil.setQuickPayData(txnDate, merchantInf.getMchntName(), shopInf.getShopName(), payAmt));

                                /**=======================收款客服消息 发送B端管理员  ======================**/
                                String customerPhone = personInfService.getPhoneNumberByOpenId(openid);
                                /** 发送客服消息
                                 String notice_m = String.format(WechatCustomerMessageUtil.WECHAT_MCHNT_W71_SUCCESS,
                                 merchantInf.getMchntName(), StringUtil.getPhoneNumberFormatLast4(customerPhone),
                                 shopInf.getShopName(), NumberUtils.RMBCentToYuan(transAmt), txnDate,
                                 resp.getInterfacePrimaryKey());*/

                                List<MerchantManager> mngList = merchantManagerService.getMerchantManagerByRoleType(
                                        log.getMchntCode(), log.getShopCode(), RoleNameEnum.CASHIER_ROLE_MCHANT.getRoleType());
                                if (mngList != null && mngList.size() > 0) {
                                    String mchntAcount = RedisDictProperties.getInstance().getdictValueByCode("WX_MCHNT_ACCOUNT");
                                    String payType = "微信支付";
                                    for (MerchantManager mng : mngList) {
                                        /** 发送客服消息
                                         wechatMQProducerService.sendWechatMessage(mchntAcount, notice_m, mng.getMangerName());*/
                                        /**发送模板消息（收款通知）*/
                                        wechatMQProducerService.sendTemplateMsg(mchntAcount, mng.getMangerName(), "WX_TEMPLATE_ID_6", null,
                                                WXTemplateUtil.setProceedsMsg(StringUtil.getPhoneNumberFormatLast4(customerPhone), merchantInf.getMchntName(), NumberUtils.RMBCentToYuan(transAmt), shopInf.getShopName(), payType, resp.getInterfacePrimaryKey(), txnDate));
                                    }
                                }

                                // 更新成功后给微信通知返回成功
                                back.setReturn_code("SUCCESS");
                                back.setReturn_msg("OK");
                                logger.info("微信回调成功：openid[{}] 业务流水[{}] 微信订单号[{}] 支付完成时间[{}]", openid,
                                        log.getWxPrimaryKey(), log.getDmsRelatedKey(), obj.getString("time_end"));

                                return MsgXmlUtil.toXML(back);
                            } catch (Exception e) {
                                logger.error("## 微信回调失败：openid[{}] 流水号[{}]", openid, wxPrimaryKey, e);
                            }
                        } else { //业务逻辑处理失败，进行退款
                            try {
                                String txnDate = DateUtil.getChineseDateFormStr(txnBean.getSwtTxnDate() + txnBean.getSwtTxnTime());
                                json = java2TxnBusinessFacade.transExceptionQueryITF(log.getWxPrimaryKey());
                                resp = JSONArray.parseObject(json, TxnResp.class);
                                if (!BaseConstants.TXN_TRANS_RESP_SUCCESS.equals(resp.getCode())) {
                                    //请求微信退款  暂时只做退款请求 不做其他业务处理
                                    if (cashFee != null)
                                        log.setTransAmt(cashFee);
                                    //微信退款
                                    JSONObject refundResJson = WXPayOrderUtil.wxTransRefundOrder(log, request);

                                    if (refundResJson.containsKey("return_code") &&
                                            "SUCCESS".equals(refundResJson.getString("return_code"))) {
										/*String notice_c = String.format(WechatCustomerMessageUtil.WECHAT_CUSTOMER_W71_REFUNDORDER_SUCCESS, 
												txnDate, merchantInf.getMchntName(), shopInf.getShopName(), payAmt);
										wechatMQProducerService.sendWechatMessage(c_mpAccount.getAccount(), notice_c, openid);*/

                                        /**        发送模板消息-客户                */
                                        wechatMQProducerService.sendTemplateMsg(c_mpAccount.getAccount(), openid, "WX_TEMPLATE_ID_2", null,
                                                WXTemplateUtil.setPayBackData(txnDate, payAmt));
                                    } else {
                                        String notice_c = String.format(WechatCustomerMessageUtil.WECHAT_CUSTOMER_W71_REFUNDORDER_FAIL,
                                                log.getWxPrimaryKey());
                                        wechatMQProducerService.sendWechatMessage(c_mpAccount.getAccount(), notice_c, openid);
                                    }
                                    // 更新成功后给微信通知返回成功
                                    back.setReturn_code("SUCCESS");
                                    back.setReturn_msg("OK");
                                    logger.info("微信回调失败退款：openid[{}] 业务流水[{}] 微信订单号[{}] 支付完成时间[{}]", openid,
                                            log.getWxPrimaryKey(), log.getDmsRelatedKey(), obj.getString("time_end"));
                                    return MsgXmlUtil.toXML(back);
                                }
                            } catch (Exception e) {
                                logger.error("## 微信回调失败退款异常：openid[{}] 流水号[{}]", openid, wxPrimaryKey, e);
                            }
                        }
                    } else {
                        back.setReturn_msg("签名失败");
                        logger.info("C扫B验签失败，签名参数[{}]", obj.toString());
                        //微信退款
                        WXPayOrderUtil.wxTransRefundOrder(log, request);
                        wxTransLogService.updateWxTransLog(log, null);// 更新微信端流水
                        back.setReturn_code("SUCCESS");
                        back.setReturn_msg("OK");
                        return MsgXmlUtil.toXML(back);
                    }
                } else {// 业务数据交易状态不为0时(通常为1)表示支付通知已处理，此时直接给微信通知返回成功
                    if (BaseConstants.TXN_TRANS_RESP_SUCCESS.equals(log.getRespCode())) {
                        back.setReturn_code("SUCCESS");
                        back.setReturn_msg("OK");
                    }
                    return MsgXmlUtil.toXML(back);
                }
            } else {
                logger.error("## 微信回调失败 openid[{}] err_code_des[{}]", openid, obj.getString("err_code_des"));
                back.setReturn_msg(obj.getString("err_code_des"));
            }
        } else {
            logger.error("## 微信回调失败  openid[{}] return_msg[{}]", openid, obj.getString("return_msg"));
            back.setReturn_msg(obj.getString("return_msg"));
        }

        return MsgXmlUtil.toXML(back);
    }

    @Override
    public TxnResp CSBweChantQuickPayOrderQuery(HttpServletRequest request) {
        String wxPrimaryKey = StringUtil.trim(request.getParameter("wxPrimaryKey"));// 业务流水(微信端)
        TxnResp resp = new TxnResp();
        WxTransLog log = wxTransLogService.getWxTransLogById(wxPrimaryKey);// 查询业务流水
        String json = "";
        try {
            if (log != null) {
                if (log.getTransSt() == 0) {
                    json = java2TxnBusinessFacade.transExceptionQueryITF(wxPrimaryKey);
                    resp = JSONArray.parseObject(json, TxnResp.class);
                    if (resp == null || !BaseConstants.TXN_TRANS_RESP_SUCCESS.equals(resp.getCode())) {
                        Thread.sleep(2000);
                        json = java2TxnBusinessFacade.transExceptionQueryITF(wxPrimaryKey);
                        resp = JSONArray.parseObject(json, TxnResp.class);
                        if (resp == null)
                            resp = new TxnResp();
                    }
                    resp.setTransAmt(NumberUtils.RMBCentToYuan(log.getTransAmt()));
                } else {
                    resp.setCode(StringUtil.nullToString(log.getRespCode()));
                    resp.setTransAmt(NumberUtils.RMBCentToYuan(log.getTransAmt()));
                }
                if (!BaseConstants.TXN_TRANS_RESP_SUCCESS.equals(resp.getCode())) {
                    resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
                }
                resp.setTransAmt(log.getTransAmt());
            }
        } catch (Exception e) {
            resp.setCode(BaseConstants.RESPONSE_EXCEPTION_CODE);
            resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
        }
        return resp;
    }

    @Override
    public JSONObject buyCardWechatPayCommit(HttpServletRequest request) {
        JSONObject obj = new JSONObject();
        String wxTransLogKey = request.getParameter("wxTransLogKey"); //微信端流水主键
        WxTransLog log = wxTransLogService.getWxTransLogById(wxTransLogKey);
        if (log == null) {
            logger.error("## 查询微信业务流水为空 wxTransLogKey[{}]", wxTransLogKey);
            return null;
        }
        String openid = log.getUserInfUserName();

        //查询用户是否开卡
        UserMerchantAcct acc = new UserMerchantAcct();
        acc.setExternalId(openid);
        acc.setMchntCode(log.getMchntCode());
        List<UserMerchantAcct> cardList = userMerchantAcctService.getUserMerchantAcctByUser(acc);
        if (cardList == null || cardList.size() < 1) {
            try {
                // 请求开卡
                TxnResp cardCheckResp = userMerchantAcctService.doCustomerAccountOpening(null, null, openid,
                        log.getMchntCode(), log.getInsCode());
                if (cardCheckResp != null && !BaseConstants.TXN_TRANS_RESP_SUCCESS.equals(cardCheckResp.getCode())) {
                    userMerchantAcctService.doCustomerAccountOpening(null, null, openid, log.getMchntCode(),
                            log.getInsCode());
                }
            } catch (Exception ex) {
                logger.error("## 购卡充值，用户openid[{}]开卡失败", openid, ex);
            }
        }
        try {
            String chargeTime = DateUtil.getCurrentTimeStr();
            String notifyUrl = HttpWebUtil.getCustomerDomainUrl() + "pay/buyCardWechatPayNotify.html"; //微信快捷支付回调
            obj = CustomerWxPayApiClient.unifiedOrder(WxMemoryCacheClient.getSingleMpAccount(), log.getWxPrimaryKey(),
                    log.getUploadAmt(), log.getUserInfUserName(), StringUtil.trim(HttpUtil.getIpAddr(request)),
                    notifyUrl);// 统一下单
            if (!"SUCCESS".equals(obj.getString("return_code"))) {
                logger.error("## 购卡充值失败--->openid[{}] return_code[{}] return_msg[{}]", openid,
                        obj.getString("return_code"), obj.getString("return_msg"));
                return null;
            }
            if (!"SUCCESS".equals(obj.getString("result_code"))) {
                logger.error("## 购卡充值失败--->openid[{}] err_code[{}] err_code_des[{}]", openid,
                        obj.getString("err_code"), obj.getString("err_code_des"));
                return null;
            }
            String sign = obj.getString("sign");// 微信返回的签名
            String localSign = CustomerWxPayApi.genUnifiedOrderBackSign(obj);// 生成统一下单返回签名
            if (!localSign.equals(sign)) {// 验签
                logger.error("## 购卡充值统一下单返回签名验证失败--->openid[{}]", openid);
                return null;
            }
            String appId = obj.getString("appid");
            String timeStamp = String.valueOf(System.currentTimeMillis() / 1000);
            String nonceStr = UUID.randomUUID().toString().replace("-", "");
            String packageStr = "prepay_id=" + obj.getString("prepay_id");
            String paySign = CustomerWxPayApi.getPaySign(appId, timeStamp, nonceStr, packageStr);

            obj.put("timeStamp", timeStamp);// js调用微信支付用的时间戳
            obj.put("nonceStr", nonceStr);// js调用微信支付用的随机数
            obj.put("paySign", paySign);// js调用微信支付用的签名
            obj.put("wxPrimaryKey", log.getWxPrimaryKey());// 调用微信支付后查询业务流水主键
            obj.put("txnDate", log.getSettleDate() + chargeTime);
            logger.info("购卡充值成功，openid[{}]，返回数据：{}", openid, obj.toString());

            return obj;
        } catch (Exception ex) {
            logger.error("## 购卡充值微信支付异常", ex);
        }
        return null;
    }

    @Override
    public String buyCardWechatPayNotify(HttpServletRequest request) {
        WxPayCallback back = new WxPayCallback();
        back.setReturn_code("FAIL");// 返回code默认失败
        back.setReturn_msg("购卡充值，微信支付交易失败");// 返回msg默认业务失败
        JSONObject obj = null;
        try {
            obj = CustomerWxPayApi.parseWxPayReturnXml(request);
        } catch (Exception e) {
            logger.error("## 解析购卡充值返回信息失败", e);
            return MsgXmlUtil.toXML(back);
        }

        String wxPrimaryKey = StringUtil.trim(obj.getString("attach"));// 业务流水(微信端)
        CtrlSystem cs = ctrlSystemService.getCtrlSystem();// TODO 得到日切信息
        if (cs == null) {
            logger.error("## 日切信息查询失败 wxPrimaryKey[{}]", wxPrimaryKey);
            return null;
        }
        WxTransLog log = wxTransLogService.getWxTransLogById(wxPrimaryKey);// 查询业务流水
        if (log == null) {
            logger.error("## 查询业务流水为空 wxPrimaryKey[{}]", wxPrimaryKey);
            return null;
        }
        String openid = log.getUserInfUserName();
        log.setTableNum(cs.getCurLogNum());
        // 支付交易结果-成功
        if (obj.containsKey("return_code") && "SUCCESS".equals(obj.getString("return_code"))) {
            // 业务结果-成功
            if (obj.containsKey("result_code") && "SUCCESS".equals(obj.getString("result_code"))) {
                // 业务数据交易状态为0时表示微信支付通知未处理，此时处理业务逻辑
                if (log.getTransSt() == 0) {
                    String cashFee = obj.getString("cash_fee");// 现金支付金额

                    //查询用户是否开卡
                    UserMerchantAcct acc = new UserMerchantAcct();
                    acc.setExternalId(log.getUserInfUserName());
                    acc.setMchntCode(log.getMchntCode());
                    List<UserMerchantAcct> cardList = userMerchantAcctService.getUserMerchantAcctByUser(acc);
                    if (cardList == null || cardList.size() < 1) {
                        try {
                            // 请求开卡
                            TxnResp cardCheckResp = userMerchantAcctService.doCustomerAccountOpening(null, null, openid, log.getMchntCode(), log.getInsCode());
                            if (cardCheckResp != null && !BaseConstants.TXN_TRANS_RESP_SUCCESS.equals(cardCheckResp.getCode())) {
                                userMerchantAcctService.doCustomerAccountOpening(null, null, openid, log.getMchntCode(), log.getInsCode());
                            }
                        } catch (Exception ex) {
                            logger.error("## 购卡充值，用户openid[{}]开卡失败", openid, ex);
                        }
                    }

//					int transAmt = Integer.parseInt(log.getTransAmt());// 获取实际订单金额
                    String transId = obj.getString("transaction_id");// 微信支付订单号
                    String notifySign = CustomerWxPayApi.getPayNotifySign(obj);// 生成通知签名
                    // 验签通过
                    if (obj.containsKey("sign") && obj.getString("sign").equals(notifySign)) {
                        RechargeTransRequest buyCardRechargReq = new RechargeTransRequest();
                        buyCardRechargReq.setSwtTxnDate(DateUtil.getCurrentDateStr());// 交易日期
                        buyCardRechargReq.setSwtTxnTime(DateUtil.getCurrentTimeStr());// 交易时间
                        buyCardRechargReq.setSwtSettleDate(log.getSettleDate());// 清算日期
                        buyCardRechargReq.setChannel(ChannelCode.CHANNEL2.toString());
                        buyCardRechargReq.setSwtFlowNo(log.getWxPrimaryKey());
                        buyCardRechargReq.setInnerMerchantNo(log.getMchntCode());
                        buyCardRechargReq.setCommodityCode(log.getRemarks());
                        buyCardRechargReq.setCommodityNum("1");
                        buyCardRechargReq.setCardNo(log.getUserInfUserName());
                        buyCardRechargReq.setTxnAmount(log.getTransAmt()); //交易金额，卡面总金额
                        buyCardRechargReq.setOriTxnAmount(log.getUploadAmt());  //原交易金额，售价总金额
                        TxnResp resp = new TxnResp();
                        try {
                            buyCardRechargReq.setTimestamp(System.currentTimeMillis());
                            buyCardRechargReq.setSign(SignUtil.genSign(buyCardRechargReq));
                            String json = hkbTxnFacade.rechargeTransactionITF(buyCardRechargReq);
                            resp = JSONArray.parseObject(json, TxnResp.class);
                            logger.info("购卡充值远程调用充值接口返回resp{}", JSONArray.toJSONString(resp));

                            if (resp != null && !BaseConstants.TXN_TRANS_RESP_SUCCESS.equals(resp.getCode())) {
                                json = java2TxnBusinessFacade.transExceptionQueryITF(log.getWxPrimaryKey());
                                resp = JSONArray.parseObject(json, TxnResp.class);
                            }
                        } catch (Exception e) {
                            logger.error("## 购卡充值远程调用快捷支付接口异常，流水号[{}]", wxPrimaryKey, e);
                        }

                        try {
                            log.setDmsRelatedKey(transId);// 微信流水号
                            log.setRespCode(resp.getCode());// 返回码
                            wxTransLogService.updateWxTransLog(log, resp);// 更新微信端流水
                        } catch (Exception e) {
                            logger.error("## 购卡充值更新微信端流水异常，流水号[{}]", wxPrimaryKey, e);
                        }
                        MpAccount c_mpAccount = WxMemoryCacheClient.getMpAccount(WxCmsContents.getCurrMpAccount());
                        MerchantInf merchantInf = merchantInfService.getMerchantInfByCode(log.getMchntCode()); //所属商户
                        String uploadAmt = NumberUtils.RMBCentToYuan(log.getTransAmt());//充值金额
                        // 远程接口调用成功
                        if (BaseConstants.TXN_TRANS_RESP_SUCCESS.equals(resp.getCode())) {
                            try {
                                String txnDate = DateUtil.getChineseDateFormStr(buyCardRechargReq.getSwtTxnDate() + buyCardRechargReq.getSwtTxnTime());
                                CardBalQueryRequest req = new CardBalQueryRequest();
                                req.setChannel(ChannelCode.CHANNEL1.toString());
                                req.setUserId(openid);
                                req.setInnerMerchantNo(merchantInf.getMchntCode());
                                req.setTimestamp(System.currentTimeMillis());
                                req.setSign(SignUtil.genSign(req));
                                String cardBalJson = hkbTxnFacade.cardBalanceQueryITF(req);
                                TxnResp cbResp = JSONArray.parseObject(cardBalJson, TxnResp.class);
                                String accBal = StringUtil.isNullOrEmpty(cbResp.getBalance()) ? "0" : cbResp.getBalance();
                                /**        客服消息-客户                */
								/*String notice_c = String.format(WechatCustomerMessageUtil.WECHAT_CUSTOMER_W20_SUCCESS, 
										txnDate, merchantInf.getMchntName(), uploadAmt);
								wechatMQProducerService.sendWechatMessage(c_mpAccount.getAccount(), notice_c, openid);*/
                                /**        模板消息-客户                */
                                String desc = "您已成功购卡充值\n";
                                String channelName = BaseIntegrationPayConstants.OMSChannelCode.findOMSChannelCodeByCode(ChannelCode.CHANNEL2.toString());    //充值渠道，微信
                                wechatMQProducerService.sendTemplateMsg(c_mpAccount.getAccount(), openid,
                                        "WX_TEMPLATE_ID_3", null, WXTemplateUtil.setPurchaseData(merchantInf.getMchntName(), channelName, desc, txnDate, uploadAmt, NumberUtils.RMBCentToYuan(accBal)));
                            } catch (Exception e) {
                                logger.error("## 购卡充值远-远程客户消息接口异常，流水号[{}]", wxPrimaryKey, e);
                            }
                            // 更新成功后给微信通知返回成功
                            back.setReturn_code("SUCCESS");
                            back.setReturn_msg("OK");
                            logger.info("微信回调成功：openid[{}] 业务流水[{}] 微信订单号[{}] 支付完成时间[{}]", openid,
                                    log.getWxPrimaryKey(), log.getDmsRelatedKey(), obj.getString("time_end"));

                            return MsgXmlUtil.toXML(back);
                        } else {
                            try {
                                String txnDate = DateUtil.getChineseDateFormStr(buyCardRechargReq.getSwtTxnDate() + buyCardRechargReq.getSwtTxnTime());
                                //请求微信退款  暂时只做退款请求 不做其他业务处理
                                if (cashFee != null)
                                    log.setTransAmt(cashFee);

                                //微信退款
                                JSONObject refundResJson = WXPayOrderUtil.wxTransRefundOrder(log, request);
                                if (refundResJson.containsKey("return_code") && "SUCCESS".equals(refundResJson.getString("return_code"))) {
									/*String notice_c = String.format(WechatCustomerMessageUtil.WECHAT_CUSTOMER_W20_REFUNDORDER_SUCCESS, 
											txnDate, merchantInf.getMchntName());
									wechatMQProducerService.sendWechatMessage(c_mpAccount.getAccount(), notice_c, openid);*/
                                    /**        发送模板消息-客户                */
                                    wechatMQProducerService.sendTemplateMsg(c_mpAccount.getAccount(), openid,
                                            "WX_TEMPLATE_ID_2", null, WXTemplateUtil.setPayBackData(txnDate, uploadAmt));
                                } else {
                                    String notice_c = String.format(WechatCustomerMessageUtil.WECHAT_CUSTOMER_W20_REFUNDORDER_FAIl, log.getWxPrimaryKey());
                                    wechatMQProducerService.sendWechatMessage(c_mpAccount.getAccount(), notice_c, openid);
                                }
                                // 更新成功后给微信通知返回成功
                                back.setReturn_code("SUCCESS");
                                back.setReturn_msg("OK");

                                logger.error("微信回调失败：openid[{}] 业务流水[{}] 微信订单号[{}] 支付完成时间[{}]", openid,
                                        log.getWxPrimaryKey(), log.getDmsRelatedKey(), obj.getString("time_end"));

                                return MsgXmlUtil.toXML(back);
                            } catch (Exception e) {
                                logger.error("## 微信回调失败--->用户退款异常：openid[{}] 流水号[{}]", openid, wxPrimaryKey, e);
                            }
                        }
                    } else {
                        back.setReturn_msg("签名失败");
                        logger.error("购卡充值验签失败，签名参数[{}]", obj.toString());
                        //微信退款
                        WXPayOrderUtil.wxRechargeRefundOrder(log, request);
                        wxTransLogService.updateWxTransLog(log, null);// 更新微信端流水
                        back.setReturn_code("SUCCESS");
                        back.setReturn_msg("OK");
                        return MsgXmlUtil.toXML(back);
                    }
                } else {// 业务数据交易状态不为0时(通常为1)表示支付通知已处理，此时直接给微信通知返回成功
                    if (BaseConstants.TXN_TRANS_RESP_SUCCESS.equals(log.getRespCode())) {
                        back.setReturn_code("SUCCESS");
                        back.setReturn_msg("OK");
                    }
                    return MsgXmlUtil.toXML(back);
                }
            } else {
                logger.error("## 微信回调失败 openid[{}] err_code_des[{}]", openid, obj.getString("err_code_des"));
            }
        } else {
            logger.error("## 购卡充值微信支付失败：流水号[{}] return_msg[{}]", wxPrimaryKey, obj.getString("return_msg"));
        }
        return MsgXmlUtil.toXML(back);
    }

    @Override
    public TxnResp wxBuyCardOrderQuery(HttpServletRequest request) {
        String wxPrimaryKey = StringUtil.trim(request.getParameter("wxPrimaryKey"));// 业务流水(微信端)
        TxnResp resp = new TxnResp();
        WxTransLog log = wxTransLogService.getWxTransLogById(wxPrimaryKey);// 查询业务流水
        String json = "";
        try {
            if (log != null) {
                if (log.getTransSt() == 0) {
                    json = java2TxnBusinessFacade.transExceptionQueryITF(wxPrimaryKey);// 交易异常查询
                    resp = JSONArray.parseObject(json, TxnResp.class);
                    if (resp == null || !BaseConstants.TXN_TRANS_RESP_SUCCESS.equals(resp.getCode())) {
                        Thread.sleep(2000);
                        json = java2TxnBusinessFacade.transExceptionQueryITF(wxPrimaryKey);// 交易异常查询
                        resp = JSONArray.parseObject(json, TxnResp.class);
                        if (resp == null)
                            resp = new TxnResp();
                    }
                    resp.setTransAmt(NumberUtils.RMBCentToYuan(log.getTransAmt()));
                } else {
                    resp.setCode(StringUtil.nullToString(log.getRespCode()));
                    resp.setTransAmt(NumberUtils.RMBCentToYuan(log.getTransAmt()));
                }
                if (!BaseConstants.TXN_TRANS_RESP_SUCCESS.equals(resp.getCode())) {
                    resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
                }
                resp.setTransAmt(NumberUtils.RMBCentToYuan(log.getTransAmt()));
            }
        } catch (Exception e) {
            resp.setCode(BaseConstants.RESPONSE_EXCEPTION_CODE);// 失败-返回码
            resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
        }
        return resp;
    }


    public TxnResp hkbJava2TxnBusiness(HttpServletRequest request, TxnPackageBean wxpLog, String tableNum, String settleDate, String openid,
                                       String pinTxn, String mchntName, String shopName) {
        TxnResp resp = new TxnResp();
        String oprOpenid = WxMemoryCacheClient.getOpenid(request);
        WxTransLog log = new WxTransLog();
        String accMchntCode = RedisDictProperties.getInstance().getdictValueByCode("ACC_HKB_MCHNT_NO");
        String accShopCode = RedisDictProperties.getInstance().getdictValueByCode("ACC_HKB_SHOP_NO");
        String accInsCode = RedisDictProperties.getInstance().getdictValueByCode("ACC_HKB_INS_CODE");
        String id = wxTransLogService.getPrimaryKey();
        log.setWxPrimaryKey(id);
        log.setTableNum(tableNum);// 得到当前可以进行操作的流水表号
        log.setSettleDate(settleDate);// 交易日期
        log.setTransId(TransCode.CW10.getCode());// 交易类型 会员卡支付
        log.setTransChnl(ChannelCode.CHANNEL7.toString());
        log.setTransSt(0);// 插入时为0，报文返回时更新为1
        log.setMchntCode(accMchntCode);
        log.setShopCode(accShopCode);
        log.setInsCode(accInsCode);// 客户端传过来的机构code
        log.setSponsor(SponsorCode.SPONSOR00.toString());
        log.setOperatorOpenId(oprOpenid);
        log.setUserInfUserName(openid);
        String transMoney = wxpLog.getTxnAmount();// 原交易金额单位元转分
        log.setTransAmt(transMoney);// 实际交易金额 插入时候默认与上送金额一致
        log.setUploadAmt(transMoney);// 上送金额
        log.setTransCurrCd(BaseConstants.TRANS_CURR_CD);
        int i = wxTransLogService.insertWxTransLog(log);// 插入流水记录

        if (i != 1) {
            logger.error("## 微信端插入流水记录数量不为1");
            return null;
        }

        AccMchntTransLog accTransLog = new AccMchntTransLog();
        accTransLog.setAccPrimaryKey(id);
        accTransLog.setMchntPrimaryKey(wxpLog.getSwtFlowNo());
        accTransLog.setTransAmt(transMoney);
        accTransLog.setSettleDate(settleDate);
        accTransLog.setUserInf(openid);
        accTransLog.setMchntCode(wxpLog.getInnerMerchantNo());
        accTransLog.setShopCode(wxpLog.getInnerShopNo());
        accTransLog.setTransSt(1);
        accTransLog.setTransId(TransCode.CW10.getCode());
        int j = accMchntTransLogService.insertAccMchntTransLog(accTransLog);

        if (j != 1) {
            logger.error("## 微信端插入商户通卡信息记录数量不为1");
            return null;
        }

        TxnPackageBean txnBean = new TxnPackageBean();
        txnBean.setTxnType(TransCode.CW10.getCode() + "0");// 交易类型，发送报文时补0
        txnBean.setSwtTxnDate(DateUtil.getCurrentDateStr());// 交易日期
        txnBean.setSwtTxnTime(DateUtil.getCurrentTimeStr());// 交易时间
        txnBean.setSwtSettleDate(settleDate);// 清算日期
        txnBean.setSwtFlowNo(id);
        txnBean.setInnerMerchantNo(accMchntCode);// 商户号
        txnBean.setInnerShopNo(accShopCode);// 门店号
        txnBean.setIssChannel(accInsCode);// 机构渠道号
        txnBean.setTxnAmount(transMoney);// 交易金额
        txnBean.setOriTxnAmount(transMoney);// 原交易金额
        txnBean.setCardNo("U" + openid);// 卡号 U开头为客户端交易，C开头则为刷卡交易
        txnBean.setResv6("1"); // 表示不需要输入密码
        try {
            if (!StringUtil.isNullOrEmpty(pinTxn)) {
                RSAPrivateKey privateKey = (RSAPrivateKey) request.getSession().getAttribute(WxConstants.RSA_PRIVATE_KEY_SESSION);
                String descrypedPwd = RSAUtil.decryptByPrivateKey(pinTxn, privateKey);// 解密后的密码,pinTxn是提交过来的密码
                txnBean.setPinTxn(descrypedPwd);// 交易密码
            } else {
                txnBean.setPinTxn("");// 交易密码
            }
        } catch (Exception e1) {
            logger.error("## 解密交易密码出错", e1);
            wxTransLogService.updateWxTransLog(tableNum, id, BaseConstants.RESPONSE_EXCEPTION_CODE, transMoney);
            resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
            return resp;
        }

        try {
            UserInf user = userInfService.getUserInfByOpenId(openid);
            txnBean.setUserId(user.getUserId());// 用户id
        } catch (Exception e1) {
            logger.error("## 查找用户异常", e1);
            wxTransLogService.updateWxTransLog(tableNum, id, BaseConstants.RESPONSE_EXCEPTION_CODE, transMoney);
            resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
            return resp;
        }

        txnBean.setChannel(ChannelCode.CHANNEL7.toString());// 渠道号
        txnBean.setTimestamp(System.currentTimeMillis());// 时间戳
        String signature = TxnChannelSignatureUtil.genSign(txnBean); // 生成的签名
        txnBean.setSignature(signature);

        // 远程调用消费接口
        String json = new String();
        WxTransLog wxlog = null;
        try {
            wxlog = wxTransLogService.getWxTransLogById(id);
            json = java2TxnBusinessFacade.consumeTransactionITF(txnBean);
            logger.info("通卡消费返回参数", json);
            resp = JSONArray.parseObject(json, TxnResp.class);
            if (resp == null || !BaseConstants.TXN_TRANS_RESP_SUCCESS.equals(resp.getCode())) {
                json = java2TxnBusinessFacade.transExceptionQueryITF(id);
                resp = JSONArray.parseObject(json, TxnResp.class);
            }
        } catch (Exception e) {
            logger.error("## 远程调用消费接口异常，返回json{}===流水号[{}]", json, id, e);
        }

        // 更新微信端流水
        try {
            if (resp == null) {
                resp = new TxnResp();
                resp.setCode(BaseConstants.RESPONSE_EXCEPTION_CODE);
                resp.setInfo(BaseConstants.RESPONSE_EXCEPTION_INFO);
                logger.error("## 远程调用消费接口返回值为空，流水号[{}]", id);
            }
            wxTransLogService.updateWxTransLog(tableNum, id, resp.getCode(), transMoney);
            accMchntTransLogService.updateAccMchntTransLog(id, resp.getCode());
            if (!BaseConstants.TXN_TRANS_RESP_SUCCESS.equals(resp.getCode())) {
                return resp;
            }
        } catch (Exception ex) {
            logger.error("## 消费交易更新微信流水异常", ex);
        }

        try {
            CardBalQueryRequest req = new CardBalQueryRequest();
            req.setChannel(ChannelCode.CHANNEL1.toString());
            req.setUserId(openid);
            req.setInnerMerchantNo(accMchntCode);
            req.setTimestamp(System.currentTimeMillis());
            req.setSign(SignUtil.genSign(req));
            String cardBalJson = hkbTxnFacade.cardBalanceQueryITF(req);
            TxnResp cbResp = JSONArray.parseObject(cardBalJson, TxnResp.class);

            final TxnPackageBean txn_f = txnBean;
            final TxnResp resp_f = resp;
            final String mchntName_f = mchntName;
            final String shopName_f = shopName;
            final String openid_f = openid;
            final String transAmt_f = transMoney;
            final String txnDate = DateUtil.getChineseDateFormStr(txn_f.getSwtTxnDate() + txn_f.getSwtTxnTime());
            final WxTransLog log_f = wxlog;
            final String accBal_f = StringUtil.isNullOrEmpty(cbResp.getBalance()) ? "0" : cbResp.getBalance();

            es.execute(new Runnable() {
                @Override
                public void run() {
                    String payAmt = "" + NumberUtils.formatMoney(resp_f.getTransAmt());// 实际支付金额
                    MpAccount mpAccount = WxMemoryCacheClient.getSingleMpAccount();

                    /**         发送客服消息
                     String notice_c = String.format(WechatCustomerMessageUtil.WECHAT_CUSTOMER_CW10_SUCCESS, txnDate,
                     mchntName_f, shopName_f, payAmt);
                     wechatMQProducerService.sendWechatMessage(mpAccount.getAccount(), notice_c, openid_f);*/

                    /**        发送模板消息                */
                    wechatMQProducerService.sendTemplateMsg(mpAccount.getAccount(), openid_f, "WX_TEMPLATE_ID_0", null,
                            WXTemplateUtil.setHKBPayData(txnDate, mchntName_f, shopName_f, payAmt, NumberUtils.RMBCentToYuan(accBal_f), shopName_f));
                }
            });
            es.execute(new Runnable() {
                @Override
                public void run() {
                    /**=======================收款通知 发送B端管理员  ======================**/
                    String customerPhone = personInfService.getPhoneNumberByOpenId(openid_f);
                    /**    发送客服消息
                     String notice_m = String.format(WechatCustomerMessageUtil.WECHAT_MCHNT_CW10_SUCCESS, mchntName_f,
                     StringUtil.getPhoneNumberFormatLast4(customerPhone), shopName_f,
                     NumberUtils.RMBCentToYuan(transAmt_f), txnDate, resp_f.getInterfacePrimaryKey());*/

                    List<MerchantManager> mngList = merchantManagerService.getMerchantManagerByRoleType(
                            log_f.getMchntCode(), log_f.getShopCode(), RoleNameEnum.CASHIER_ROLE_MCHANT.getRoleType());
                    if (mngList != null && mngList.size() > 0) {
                        String mchntAcount = RedisDictProperties.getInstance().getdictValueByCode("WX_MCHNT_ACCOUNT");
                        String payType = "会员卡付款";
                        for (MerchantManager mng : mngList) {
                            /**    发送客服消息
                             wechatMQProducerService.sendWechatMessage(mchntAcount, notice_m, mng.getMangerName());*/
                            /**    发送模板消息（收款通知）*/
                            wechatMQProducerService.sendTemplateMsg(mchntAcount, mng.getMangerName(), "WX_TEMPLATE_ID_6", null,
                                    WXTemplateUtil.setProceedsMsg(StringUtil.getPhoneNumberFormatLast4(customerPhone), mchntName_f, NumberUtils.RMBCentToYuan(transAmt_f), shopName_f, payType, resp_f.getInterfacePrimaryKey(), txnDate));
                        }
                    }
                }
            });
        } catch (Exception e) {
            logger.error("## 发送客服消息接口异常， 接口流水号[{}]", id, e);
        }
        return resp;
//		return null;
    }

}
