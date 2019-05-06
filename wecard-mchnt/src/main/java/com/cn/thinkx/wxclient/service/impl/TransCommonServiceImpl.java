package com.cn.thinkx.wxclient.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.cn.thinkx.beans.CtrlSystem;
import com.cn.thinkx.beans.TxnPackageBean;
import com.cn.thinkx.common.activemq.service.WechatMQProducerService;
import com.cn.thinkx.core.util.Constants;
import com.cn.thinkx.core.util.Constants.ChannelCode;
import com.cn.thinkx.core.util.Constants.TransCode;
import com.cn.thinkx.customer.domain.UserInf;
import com.cn.thinkx.customer.domain.UserMerchantAcct;
import com.cn.thinkx.customer.service.PersonInfService;
import com.cn.thinkx.customer.service.UserInfService;
import com.cn.thinkx.customer.service.UserMerchantAcctService;
import com.cn.thinkx.facade.bean.CardBalQueryRequest;
import com.cn.thinkx.facade.service.HKBTxnFacade;
import com.cn.thinkx.merchant.domain.MerchantManager;
import com.cn.thinkx.pms.base.redis.util.RedisDictProperties;
import com.cn.thinkx.pms.base.redis.util.SignUtil;
import com.cn.thinkx.pms.base.redis.util.TxnChannelSignatureUtil;
import com.cn.thinkx.pms.base.utils.BaseConstants;
import com.cn.thinkx.pms.base.utils.DateUtil;
import com.cn.thinkx.pms.base.utils.NumberUtils;
import com.cn.thinkx.pms.base.utils.StringUtil;
import com.cn.thinkx.pub.domain.TxnResp;
import com.cn.thinkx.service.txn.Java2TxnBusinessFacade;
import com.cn.thinkx.wechat.base.wxapi.util.WXTemplateUtil;
import com.cn.thinkx.wxclient.domain.WxTransLog;
import com.cn.thinkx.wxclient.service.CtrlSystemService;
import com.cn.thinkx.wxclient.service.TransCommonService;
import com.cn.thinkx.wxclient.service.WxTransLogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("transCommonService")
public class TransCommonServiceImpl implements TransCommonService {

    private final Logger logger = LoggerFactory.getLogger(TransCommonServiceImpl.class);


    @Autowired
    private Java2TxnBusinessFacade java2TxnBusinessFacade;

    @Autowired
    @Qualifier("ctrlSystemService")
    private CtrlSystemService ctrlSystemService;

    @Autowired
    @Qualifier("wxTransLogService")
    private WxTransLogService wxTransLogService;

    @Autowired
    private UserMerchantAcctService userMerchantAcctService;


    @Autowired
    @Qualifier("userInfService")
    private UserInfService userInfService;


    @Autowired
    @Qualifier("personInfService")
    private PersonInfService personInfService;

    @Autowired
    @Qualifier("wechatMQProducerService")
    private WechatMQProducerService wechatMQProducerService;

    @Autowired
    private HKBTxnFacade hkbTxnFacade;

    /**
     * 消费交易-根据规则引擎计算后的金额判断客户是否需要输入密码
     *
     * @param merchantManager 商户管理员
     * @param copenId         客户端 openId
     * @param transAmt        drools 计算后的实际金额
     * @return boolean true 需要输入密码   false 不输入密码
     */
    public boolean doCustomerNeed2EnterPassword(MerchantManager merchantManager, String copenId, int transAmt) throws Exception {

        UserMerchantAcct userMerchantAcct = new UserMerchantAcct();
        userMerchantAcct.setExternalId(copenId);
        userMerchantAcct.setMchntCode(merchantManager.getMchntCode());
        userMerchantAcct.setInsCode(merchantManager.getInsCode());
        List<UserMerchantAcct> merchantAcctList = userMerchantAcctService.getUserMerchantAcctByUser(userMerchantAcct);// 获取无pin限额视图

        if (merchantAcctList == null || merchantAcctList.size() == 0) {
            return true; //需要输入密码
        }
        if (merchantAcctList != null && merchantAcctList.size() > 0) {
            CtrlSystem cs = ctrlSystemService.getCtrlSystem();// 得到日切信息
            if (cs != null) {
                UserMerchantAcct resultAcct = merchantAcctList.get(0);
                int noPinTxnAmt = Integer.parseInt(resultAcct.getNopinTxnAmt());
                int dayTotalAmt2 = 0;
                if (cs.getSettleDate().equals(resultAcct.getLastTxnDate())) {
                    dayTotalAmt2 = Integer.parseInt(resultAcct.getDayTotalAmt2()); //日交易总额
                }
                if (noPinTxnAmt >= (transAmt + (dayTotalAmt2))) {
                    return false;
                } else {
                    return true;
                }
            }
        }
        return true;
    }

    /**
     * 消费交易-验密后调用交易核心
     *
     * @param log             微信端流水
     * @param merchantManager 商户管理员
     * @param copenId         客户端 openId
     * @param sponsor         请求渠道 // 00--客户微信平台  10--商户微信平台
     * @param transAmt        交易金额 （单位分）
     * @param oritxnAmount    原交易金额（单位分）
     * @param transPwd        交易密码
     * @return
     * @param@param oprOpenid 当前请求操作用户openId
     */
    public TxnResp scanCodeJava2TxnBusiness(WxTransLog log, MerchantManager merchantManager, String oprOpenid, String copenId, String sponsor, int oritxnAmount, int transAmt, String transPwd) throws Exception {
        TxnResp resp = new TxnResp();
        UserInf userInf = userInfService.getUserInfByOpenId(copenId);

        //是否已经开卡校验查询
        UserMerchantAcct acc = new UserMerchantAcct();
        acc.setExternalId(copenId);
        acc.setMchntCode(log.getMchntCode());
        acc.setInsCode(merchantManager.getInsCode());
        List<UserMerchantAcct> cardList = userMerchantAcctService.getUserMerchantAcctByUser(acc);
        if (cardList == null || cardList.size() < 1) {
            try {
                //请求开卡
                TxnResp cardCheckResp = userMerchantAcctService.customerAccountOpeningITF(null, null, copenId, merchantManager.getMchntCode(), merchantManager.getInsCode());
                if (cardCheckResp != null && !Constants.TXN_TRANS_RESP_SUCCESS.equals(cardCheckResp.getCode())) {
                    userMerchantAcctService.customerAccountOpeningITF(null, null, copenId, merchantManager.getMchntCode(), merchantManager.getInsCode());
                }
            } catch (Exception ex) {
                logger.error("扫码支付，用户开卡失败-->" + ex);
            }
        }

        CtrlSystem cs = ctrlSystemService.getCtrlSystem();// 得到日切信息
        log.setTableNum(cs.getCurLogNum());

        TxnPackageBean txnBean = new TxnPackageBean();
        txnBean.setTxnType(TransCode.CW10.getCode() + "0");// 交易类型，发送报文时补0
        txnBean.setSwtTxnDate(DateUtil.getCurrentDateStr());// 交易日期
        txnBean.setSwtTxnTime(DateUtil.getCurrentTimeStr());// 交易时间
        txnBean.setSwtSettleDate(log.getSettleDate());// 清算日期
        txnBean.setSwtFlowNo(log.getWxPrimaryKey());
        txnBean.setIssChannel(merchantManager.getInsCode());// 机构渠道号
        txnBean.setInnerMerchantNo(merchantManager.getMchntCode());// 商户号
        txnBean.setInnerShopNo(merchantManager.getShopCode());// 门店号
        txnBean.setTxnAmount(String.valueOf(transAmt));// 交易金额
        txnBean.setOriTxnAmount(log.getUploadAmt());// 原交易金额
        txnBean.setCardNo("U" + copenId);// 卡号 U开头为客户端交易，C开头则为刷卡交易

        try {
            //String descrypedPwd = "";//RSAUtil.decryptByPrivateKey(pinTxn, privateKey);// 解密后的密码,pinTxn是提交过来的密码
            txnBean.setPinTxn(transPwd);// 交易密码
        } catch (Exception e1) {
            logger.error("解密交易密码出错", e1);
            wxTransLogService.updateWxTransLog(log, resp);
            resp.setCode(Constants.TXN_TRANS_ERROR);
            resp.setInfo("网络异常，请稍后再试");
            return resp;
        }

        txnBean.setChannel(ChannelCode.CHANNEL1.toString());// 渠道号
        txnBean.setUserId(userInf.getUserId());// 用户id
        txnBean.setTimestamp(System.currentTimeMillis());// 时间戳
        String signature = TxnChannelSignatureUtil.genSign(txnBean); //生成的签名
        txnBean.setSignature(signature);
        String json = new String();

        try {
            // 远程调用消费接口
            json = java2TxnBusinessFacade.consumeTransactionITF(txnBean);
            resp = JSONArray.parseObject(json, TxnResp.class);
        } catch (Exception ex) {
            logger.error("消费交易异常----》", ex);
        }

        try {
            logger.info("resp  is -------------->" + JSONArray.toJSONString(resp));
            wxTransLogService.updateWxTransLog(log, resp);  //更新微信流水

        } catch (Exception ex) {
            logger.error("更新交易流返回码异常:", ex);
        }

        if (resp == null) {
            resp = new TxnResp();
            resp.setCode(Constants.TXN_TRANS_ERROR);
            resp.setInfo("网络异常，请稍后再试");
            return resp;
        }
        if ("00".equals(resp.getCode())) {
            try {
                /**=======================消费交易 发送C端用户消息 begin ======================**/
                int giveAmt = oritxnAmount - transAmt;
                String txnDate = DateUtil.getChineseDateFormStr(txnBean.getSwtTxnDate() + txnBean.getSwtTxnTime());
                /**客服消息-客户
                 String content_c=String.format(WechatCustomerMessageUtil.WECHAT_CUSTOMER_CW10_SUCCESS,txnDate,merchantManager.getMchntName(),merchantManager.getShopName(),NumberUtils.RMBCentToYuan(transAmt));
                 WechatCustomerParam param=new WechatCustomerParam();
                 param.setAcountName(RedisDictProperties.getInstance().getdictValueByCode("WX_CUSTOMER_ACCOUNT"));
                 param.setFromOpenId(merchantManager.getMangerName());
                 param.setContent(content_c);
                 param.setToOpenId(copenId);
                 wechatMQProducerService.sendMessage(param);*/
                /** 发送模板消息    （C端用户）*/
                CardBalQueryRequest req = new CardBalQueryRequest();
                req.setChannel(ChannelCode.CHANNEL1.toString());
                req.setUserId(copenId);
                req.setInnerMerchantNo(merchantManager.getMchntCode());
                req.setTimestamp(System.currentTimeMillis());
                req.setSign(SignUtil.genSign(req));
                String cardBalJson = hkbTxnFacade.cardBalanceQueryITF(req);
                TxnResp cbResp = JSONArray.parseObject(cardBalJson, TxnResp.class);
                final String accBal_f = StringUtil.isNullOrEmpty(cbResp.getBalance()) ? "0" : cbResp.getBalance();
                /** 模板消息-客户 */
                wechatMQProducerService.sendTemplateMsg(RedisDictProperties.getInstance().getdictValueByCode("WX_CUSTOMER_ACCOUNT"), copenId,
                        "WX_TEMPLATE_ID_0", null, WXTemplateUtil.setCardPayData(txnDate, merchantManager.getMchntName(),
                                merchantManager.getShopName(), NumberUtils.RMBCentToYuan(transAmt), NumberUtils.RMBCentToYuan(accBal_f), BaseConstants.templateMsgPayment.findByCode(req.getChannel()).getValue()));

                /**=======================收款通知 发送B端管理员 begin ======================**/
                String customerPhone = personInfService.getPhoneNumberByOpenId(copenId);
                /**发送客服消息
                 String notice_m=String.format(WechatCustomerMessageUtil.WECHAT_MCHNT_CW10_SUCCESS,merchantManager.getMchntName(),StringUtil.getPhoneNumberFormatLast4(customerPhone),merchantManager.getShopName(), NumberUtils.RMBCentToYuan(transAmt),txnDate,resp.getInterfacePrimaryKey());
                 param=new WechatCustomerParam();
                 param.setAcountName(RedisDictProperties.getInstance().getdictValueByCode("WX_MCHNT_ACCOUNT"));
                 param.setToOpenId(merchantManager.getMangerName());
                 param.setContent(notice_m);
                 wechatMQProducerService.sendMessage(param);*/
                /**发送模板消息（收款通知）*/
                String mchntAcount = RedisDictProperties.getInstance().getdictValueByCode("WX_MCHNT_ACCOUNT");
                String payType = "会员卡付款";
                wechatMQProducerService.sendTemplateMsg(mchntAcount, merchantManager.getMangerName(), "WX_TEMPLATE_ID_6", null,
                        WXTemplateUtil.setProceedsMsg(StringUtil.getPhoneNumberFormatLast4(customerPhone), merchantManager.getMchntName(), NumberUtils.RMBCentToYuan(transAmt), merchantManager.getShopName(), payType, resp.getInterfacePrimaryKey(), txnDate));

            } catch (Exception ex) {
                logger.error("消费交易客服消息发送失败：", ex);
            }
        } else {
            if (StringUtil.isNullOrEmpty(resp.getInfo())) {
                resp.setInfo("网络异常，请稍后再试");
            }
        }
        /**=======================消费交易 发送 C端用户消息 end  ======================**/
        return resp;

    }


}
