package com.cn.thinkx.wxclient.ctrl;

import com.alibaba.fastjson.JSONArray;
import com.cn.thinkx.beans.CtrlSystem;
import com.cn.thinkx.beans.TxnPackageBean;
import com.cn.thinkx.common.activemq.service.WechatMQProducerService;
import com.cn.thinkx.core.ctrl.BaseController;
import com.cn.thinkx.core.domain.ResultHtml;
import com.cn.thinkx.core.util.Constants;
import com.cn.thinkx.core.util.Constants.ChannelCode;
import com.cn.thinkx.core.util.Constants.TransCode;
import com.cn.thinkx.core.util.MessageUtil;
import com.cn.thinkx.customer.domain.PersonInf;
import com.cn.thinkx.customer.service.PersonInfService;
import com.cn.thinkx.merchant.domain.*;
import com.cn.thinkx.merchant.service.*;
import com.cn.thinkx.pms.base.redis.util.RedisDictProperties;
import com.cn.thinkx.pms.base.redis.util.SignatureUtil;
import com.cn.thinkx.pms.base.service.MessageService;
import com.cn.thinkx.pms.base.utils.BaseConstants.templateMsgPayment;
import com.cn.thinkx.pms.base.utils.BaseConstants.templateMsgRefund;
import com.cn.thinkx.pms.base.utils.DateUtil;
import com.cn.thinkx.pms.base.utils.MD5Util;
import com.cn.thinkx.pms.base.utils.NumberUtils;
import com.cn.thinkx.pms.base.utils.StringUtil;
import com.cn.thinkx.pub.domain.TxnResp;
import com.cn.thinkx.pub.service.PublicService;
import com.cn.thinkx.pub.utils.HKBTxnUtil;
import com.cn.thinkx.service.txn.Java2TxnBusinessFacade;
import com.cn.thinkx.wechat.base.wxapi.domain.Account;
import com.cn.thinkx.wechat.base.wxapi.process.CustomerWxPayApiClient;
import com.cn.thinkx.wechat.base.wxapi.process.WxMemoryCacheClient;
import com.cn.thinkx.wechat.base.wxapi.util.WXTemplateUtil;
import com.cn.thinkx.wxclient.domain.WxTransLog;
import com.cn.thinkx.wxclient.service.CtrlSystemService;
import com.cn.thinkx.wxclient.service.WxTransLogService;
import com.cn.thinkx.wxcms.service.AccountService;
import net.sf.json.JSONObject;
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
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 手机微信页面 商户账户管理页面
 */
@Controller
@RequestMapping("/wxclient/account")
public class WxMchntAcctInfCtrl extends BaseController {
    Logger logger = LoggerFactory.getLogger(WxMchntAcctInfCtrl.class);

    @Autowired
    @Qualifier("mchntAcctInfService")
    private MchntAcctInfService mchntAcctInfService;

    @Autowired
    @Qualifier("merchantManagerService")
    private MerchantManagerService merchantManagerService;

    @Autowired
    @Qualifier("merchantInfService")
    private MerchantInfService merchantInfService;

    @Autowired
    @Qualifier("transLogService")
    private TransLogService transLogService;

    @Autowired
    @Qualifier("publicService")
    private PublicService publicService;

    @Autowired
    @Qualifier("transRuleService")
    private TransRuleService transRuleService;

    @Autowired
    @Qualifier("wxTransLogService")
    private WxTransLogService wxTransLogService;

    @Autowired
    @Qualifier("shopInfService")
    private ShopInfService shopInfService;

    @Autowired
    @Qualifier("ctrlSystemService")
    private CtrlSystemService ctrlSystemService;

    @Autowired
    @Qualifier("personInfService")
    private PersonInfService personInfService;

    @Autowired
    private Java2TxnBusinessFacade java2TxnBusinessFacade;

    @Autowired
    @Qualifier("accountService")
    private AccountService accountService;

    @Autowired
    @Qualifier("messageService")
    private MessageService messageService;

    @Autowired
    @Qualifier("wechatMQProducerService")
    private WechatMQProducerService wechatMQProducerService;

    @Autowired
    @Qualifier("insInfService")
    private InsInfService insInfService;

    @Autowired
    @Qualifier("cardBalService")
    private CardBalService cardBalService;

    /**
     * 交易记录 统计 list
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/mchntTransList")
    public ModelAndView mchntTransList(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("wxclient/translog/mchntTransList");
        // 拦截器已经处理了缓存,这里直接取
        String openid = WxMemoryCacheClient.getOpenid(request);
        String startDate = request.getParameter("settleDateStart");
        String endDate = request.getParameter("settleDateEnd");

        try {
            if (StringUtil.isNullOrEmpty(openid)) {
                return unValidated(request);
            }
            MerchantManager mchntManager = merchantManagerService.getMerchantInsInfByOpenId(openid);
            if (mchntManager == null) {
                return unValidated(request);
            }
            TransLog ts = new TransLog();
            ts.setInsCode(mchntManager.getInsCode());
            ts.setMchntCode(mchntManager.getMchntCode());
            ts.setShopCode(mchntManager.getShopCode());
            if (StringUtil.isNullOrEmpty(startDate)) {
                ts.setStartDate(
                        DateUtil.getFormatStringFormString(DateUtil.getCurrentDateStr(), DateUtil.FORMAT_YYYYMMDD));
            } else {
                ts.setStartDate(DateUtil.getFormatStringFormString(startDate, DateUtil.FORMAT_YYYYMMDD));
            }
            if (StringUtil.isNullOrEmpty(endDate)) {
                ts.setEndDate(
                        DateUtil.getFormatStringFormString(DateUtil.getCurrentDateStr(), DateUtil.FORMAT_YYYYMMDD));
            } else {
                ts.setEndDate(DateUtil.getFormatStringFormString(endDate, DateUtil.FORMAT_YYYYMMDD));
            }
            List<TransLog> transLogList = transLogService.getTransLogListBySettleDate(ts);
            if (transLogList == null) {
                transLogList = new ArrayList<TransLog>();
            } else {
                for (int i = 0; i < transLogList.size(); i++) {
                    transLogList.get(i).setTransAmt(NumberUtils.RMBCentToYuan(transLogList.get(i).getTransAmt()));
                }
            }

            TransLog rt = transLogService.queryTransCountBySettleDate(ts);
            if (rt == null) {
                rt = new TransLog();
                rt.setConsumerAmount(0);
                rt.setPaybackAmount(0);
            }
            ts.setStartDate(DateUtil.getFormatStringFormString(ts.getStartDate(), DateUtil.FORMAT_YYYY_MM_DD));
            ts.setEndDate(DateUtil.getFormatStringFormString(ts.getEndDate(), DateUtil.FORMAT_YYYY_MM_DD));

            ShopInf shopInf = new ShopInf();
            if (StringUtil.isNullOrEmpty(mchntManager.getShopId()))
                shopInf.setShopName("所有门店");
            else {
                shopInf = shopInfService.getShopInfById(mchntManager.getShopId());
                if (shopInf == null) {
                    logger.error("## 门店交易统计查询失败：管理员门店号[{}]对应的门店不存在", mchntManager.getShopId());
                    mv = new ModelAndView("common/failure");
                    mv.addObject("failureMsg", "对不起，您所在的门店已不存在");
                    return mv;
                }
            }
            mv.addObject("transLogList", transLogList);
            mv.addObject("resultTransLog", rt);
            mv.addObject("ts", ts);
            mv.addObject("shopInf", shopInf);

        } catch (Exception ex) {
            logger.error("交易统计出错：", ex);
        }
        return mv;
    }

    /**
     * 交易记录 （会员卡余额、会员卡数量、充值、消费、退款） 统计
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/mchntTransLogCount")
    public ModelAndView mchntTransLogCount(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("wxclient/translog/mchntTransLogCount");
        // 拦截器已经处理了缓存,这里直接取
        String openid = WxMemoryCacheClient.getOpenid(request);

        String startDate = request.getParameter("settleDateStart");
        String endDate = request.getParameter("settleDateEnd");
        // String startDate = "2017-04-21";
        // String endDate = "2017-06-20";
        try {
            if (StringUtil.isNullOrEmpty(openid)) {
                return unValidated(request);
            }
            MerchantManager mchntManager = merchantManagerService.getMerchantInsInfByOpenId(openid);
            MerchantManager mchnt = merchantManagerService.getMerchantManagerByOpenId(openid);
            if (mchntManager == null || mchnt == null) {
                return unValidated(request);
            }
            /** 查看商户机构信息 */
            InsInf insInf = insInfService.getInsInfByMchntId(mchnt.getMchntId());

            CardBal cardBal = null;
            TransLog ts = new TransLog();

            if (StringUtil.isNullOrEmpty(startDate)) {
                ts.setStartDate(DateUtil.getFormatDateFormString(DateUtil.getNextDay(DateUtil.getSystemDate()),
                        DateUtil.FORMAT_YYYYMMDD));
            } else {
                ts.setStartDate(DateUtil.getFormatStringFormString(startDate, DateUtil.FORMAT_YYYYMMDD));
            }

            if (StringUtil.isNullOrEmpty(endDate)) {
                ts.setEndDate(DateUtil.getFormatDateFormString(DateUtil.getNextDay(DateUtil.getSystemDate()),
                        DateUtil.FORMAT_YYYYMMDD));
            } else {
                ts.setEndDate(DateUtil.getFormatStringFormString(endDate, DateUtil.FORMAT_YYYYMMDD));
            }

            /*** 查看会员卡和卡余额信息 **/
            CardBal cb = new CardBal();
            cb.setInsCode(insInf.getInsCode());
            cb.setProductCode(insInf.getProductCode());
            cb.setSelectDate(ts.getEndDate());
            cardBal = cardBalService.getCardBalByInsCodeAndProductCode(cb);

            /*** 查看查看交易类型统计信息 **/
            ts.setInsCode(mchntManager.getInsCode());
            ts.setMchntCode(mchntManager.getMchntCode());
            TransLog total = transLogService.queryHisTransCountByMchntId(ts);
            if (total == null) {
                total = new TransLog();
            }
            ts.setStartDate(DateUtil.getFormatStringFormString(ts.getStartDate(), DateUtil.FORMAT_YYYY_MM_DD));
            ts.setEndDate(DateUtil.getFormatStringFormString(ts.getEndDate(), DateUtil.FORMAT_YYYY_MM_DD));

            mv.addObject("total", total);
            mv.addObject("cardBal", cardBal);
            mv.addObject("mchntName", mchntManager.getMchntName());

            mv.addObject("ts", ts);

        } catch (Exception ex) {
            logger.error("交易统计出错：", ex);
        }
        return mv;
    }

    /**
     * 交易记录明细
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/transLogDetail")
    public ModelAndView transLogDetail(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("wxclient/translog/transLogDetail");
        // 拦截器已经处理了缓存,这里直接取
        String openid = WxMemoryCacheClient.getOpenid(request);
        String txnPrimaryKey = request.getParameter("txnPrimaryKey");
        TransLog translog = new TransLog();
        ShopInf shopInf = new ShopInf();
        PersonInf personInf = new PersonInf();
        String roleType = "";
        try {
            MerchantManager merchantManager = merchantManagerService.getMerchantInsInfByOpenId(openid);
            roleType = merchantManager.getRoleType();
            translog = transLogService.getTransLogByLogId(txnPrimaryKey);
            shopInf = shopInfService.getShopInfByCode(translog.getShopCode());
            personInf = personInfService.getPersonInfByAccountNo(translog.getPriAcctNo());
            if (personInf != null) {
                personInf.setMobilePhoneNo(StringUtil.getPhoneNumberFormatLast4(personInf.getMobilePhoneNo()));
            }
            translog.setTransAmt(NumberUtils.RMBCentToYuan(translog.getTransAmt()));
        } catch (Exception ex) {
            logger.error("交易记录明细查询出错：", ex);
            ;
        }
        mv.addObject("translog", translog);
        mv.addObject("shopInf", shopInf);
        mv.addObject("personInf", personInf);
        mv.addObject("roleType", roleType);
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
        String openid = WxMemoryCacheClient.getOpenid(request);

        ResultHtml resultHtml = new ResultHtml();
        resultHtml.setStatus(false);
        resultHtml.setMsg(Constants.TXN_TRANS_ERROR_DESC);
        String txnPrimaryKey = request.getParameter("txnPrimaryKey");
        String phoneCode = request.getParameter("phoneCode");

        MerchantManager mchntManager = merchantManagerService.getMerchantInsInfByOpenId(openid);
        // 参数校验
        try {
            /** step1. 验证openId **/
            if (StringUtil.isNullOrEmpty(openid)) {
                resultHtml.setMsg(MessageUtil.NO_AUTHORIZATION);// 未授权，非法访问
                return resultHtml;
            }

            /** step2. 动态口令验证 **/
            if (StringUtil.isNullOrEmpty(phoneCode)) {
                resultHtml.setMsg(MessageUtil.ERROR_MESSAGE_PHONE_CODE);// 手机号验证码
                return resultHtml;
            }
            if (mchntManager == null || StringUtil.isNullOrEmpty(mchntManager.getPhoneNumber())
                    || !checkPhoneCode(phoneCode, request.getSession(), resultHtml)) {
                resultHtml.setStatus(false);
                return resultHtml;
            }
        } catch (Exception ex) {
            resultHtml.setStatus(false);
            resultHtml.setMsg(Constants.TXN_TRANS_ERROR_DESC);
            logger.error("## 商户退款translogRefundCommit 参数校验校验失败", ex);
        }

        TransLog transLog = transLogService.getTransLogByLogId(txnPrimaryKey);

        if (transLog == null) {
            resultHtml.setStatus(false);
            resultHtml.setMsg("当前交易订单号不存在，请重新确认");
            return resultHtml;
        }

        CtrlSystem cs = ctrlSystemService.getCtrlSystem();// 得到日切信息
        if (cs == null || !Constants.TRANS_FLAG_YES.equals(cs.getTransFlag())) {
            resultHtml.setStatus(false);
            resultHtml.setMsg("网络连接失败，请稍后再试");
            logger.error("## 商户退款translogRefundCommit 日切信息交易允许状态：日切中");
            return resultHtml;
        }

        TxnResp payBackResp = new TxnResp();// 外部退款，Resp 对象
        IntfaceTransLog itfLog = transLogService.getIntfaceTransLogByLogId(transLog.getDmsRelatedKey());
        if (itfLog == null) {
            resultHtml.setStatus(false);
            resultHtml.setMsg("订单流水号 " + transLog.getDmsRelatedKey() + " 不存在");
            logger.error("## 商户退款translogRefundCommitITF 订单号：[{}]ITF流水不存在", transLog.getDmsRelatedKey());
            return resultHtml;
        }

        // 记录流水
        WxTransLog log = new WxTransLog();
        String dms_related_key = "";// 外部系统交易流水号
        try {
            String id = wxTransLogService.getPrimaryKey();
            log.setWxPrimaryKey(id);
            log.setTableNum(cs.getCurLogNum());// 得到当前可以进行操作的流水表号
            log.setSettleDate(cs.getSettleDate());// 交易日期
            if (TransCode.CW71.getCode().equals(itfLog.getTransId())) {
                log.setTransId(TransCode.CW74.getCode());// 交易类型
            } else {
                log.setTransId(TransCode.CW11.getCode());// 交易类型
            }
            log.setTransSt(0);// 插入时为0，报文返回时更新为1
            log.setInsCode(transLog.getInsCode());
            log.setMchntCode(transLog.getMchntCode());
            log.setShopCode(transLog.getShopCode());
            log.setOperatorOpenId(openid);
            log.setTransChnl(ChannelCode.CHANNEL1.toString());
            log.setUserInfUserName(transLog.getUserName());
            log.setTransAmt(transLog.getTransAmt());// 实际交易金额 插入时候默认与上送金额一致
            log.setTransCurrCd(Constants.TRANS_CURR_CD);
            log.setOrgWxPrimaryKey(StringUtil.nullToString(itfLog.getDmsRelatedKey())); // 外面流水号
            int i = wxTransLogService.insertWxTransLog(log);// 插入流水记录
            if (i != 1) {
                logger.error("## 商户退款translogRefundCommit 微信端插入流水记录数量不为1");
                return resultHtml;
            }

        } catch (Exception ex) {
            resultHtml.setStatus(false);
            resultHtml.setMsg("参数校验校验失败");
            logger.error("## 商户退款translogRefundCommit 参数校验校验失败", ex);
        }

        // ========================外部系统退款 begin========================
        String transChnl = "";// 请求渠道
        // 微信快捷支付退款
        if (ChannelCode.CHANNEL2.toString().equals(itfLog.getTransChnl()) && TransCode.CW71.getCode().equals(itfLog.getTransId())) {
            try {
                transChnl = ChannelCode.CHANNEL2.toString();
                Account account = accountService.getByAccount(RedisDictProperties.getInstance().getdictValueByCode("WX_CUSTOMER_ACCOUNT"));
                JSONObject refundResJson = CustomerWxPayApiClient.getRefundOrder(account, itfLog.getDmsRelatedKey(),
                        itfLog.getDmsRelatedKey(), itfLog.getTransAmt(), itfLog.getTransAmt(), request);// 申請退款
                if (refundResJson != null) {
                    if (refundResJson.containsKey("return_code") && "SUCCESS".equals(refundResJson.getString("return_code"))) {
                        payBackResp.setCode(Constants.TXN_TRANS_RESP_SUCCESS);
                        dms_related_key = refundResJson.getString("transaction_id");
                    } else {
                        // 如果失败 重新发起退款
                        refundResJson = CustomerWxPayApiClient.getRefundOrder(account, itfLog.getDmsRelatedKey(),
                                itfLog.getDmsRelatedKey(), itfLog.getTransAmt(), itfLog.getTransAmt(), request);// 申請退款
                        if (refundResJson.containsKey("return_code") && "SUCCESS".equals(refundResJson.getString("return_code"))) {
                            payBackResp.setCode(Constants.TXN_TRANS_RESP_SUCCESS);
                            dms_related_key = refundResJson.getString("transaction_id");
                        }
                    }
                }
            } catch (Exception ex) {
                resultHtml.setStatus(false);
                resultHtml.setMsg("外部系统退款异常");
                logger.error("## 商户退款translogRefundCommit 微信退款款异常", ex);
            }
        } else {
            transChnl = ChannelCode.CHANNEL1.toString();
            // 嘉福发起的交易
            if (ChannelCode.CHANNEL4.toString().equals(itfLog.getTransChnl())) {
                try {
                    // 嘉福支付
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("orderId", itfLog.getDmsRelatedKey());
                    map.put("extOrderId", transLog.getDmsRelatedKey());
                    map.put("userId", itfLog.getUserInfUserName());
                    String timestamp = "" + System.currentTimeMillis();
                    map.put("timestamp", timestamp);
                    map.put("sign", MD5Util.md5("extOrderId=" + transLog.getDmsRelatedKey() + "&orderId=" + itfLog.getDmsRelatedKey()
                            + "&timestamp=" + timestamp + "&userId=" + itfLog.getUserInfUserName() + "&key="
                            + RedisDictProperties.getInstance().getdictValueByCode("PAYBACK_SIGN_KEY")).toUpperCase());
                    // String jfResult = java2TxnBusinessFacade.HKBPayBackToJF(bean);
                    String jfResult = HKBTxnUtil.hkbPayBackToJF(map);
                    if (StringUtil.isNullOrEmpty(jfResult)) {
                        logger.error("## 商户退款translogRefundCommit 订单号[{}]知了企服退款至嘉福账户交易接口返回值为空", txnPrimaryKey);
                        resultHtml.setMsg(Constants.TXN_TRANS_ERROR_DESC);
                    }
                    payBackResp = JSONArray.parseObject(jfResult, TxnResp.class);
                    if (payBackResp != null)
                        dms_related_key = payBackResp.getOrderId();
                    else
                        payBackResp = new TxnResp();

                } catch (Exception ex) {
                    resultHtml.setStatus(false);
                    resultHtml.setMsg("外部系统退款异常");
                    logger.error("## 商户退款translogRefundCommit 嘉福支付退款异常", ex);
                }
            } else {
                // 微信端发起的交易
                dms_related_key = itfLog.getDmsRelatedKey();
                payBackResp.setCode(Constants.TXN_TRANS_RESP_SUCCESS);
            }
        }
        // ========================外部系统退款 end========================
        // 请求middleware 进行内部系统退款
        TxnResp resp = new TxnResp();
        TxnPackageBean txnBean = new TxnPackageBean();
        try {
            // 交易成功(00)以及重复交易(98)均为成功交易，调用middleware
            if (Constants.TXN_TRANS_RESP_SUCCESS.equals(payBackResp.getCode()) || Constants.JF_TRANS_RESP_SUCCESS.equals(payBackResp.getCode())) {
                try {
                    Date currDate = DateUtil.getCurrDate();
                    txnBean.setSwtFlowNo(log.getWxPrimaryKey()); // 接口平台交易流水号
                    txnBean.setTxnType(log.getTransId() + "0");// 0: 同步 1:异步
                    txnBean.setSwtTxnDate(DateUtil.getStringFromDate(currDate, DateUtil.FORMAT_YYYYMMDD));
                    txnBean.setSwtTxnTime(DateUtil.getStringFromDate(currDate, DateUtil.FORMAT_HHMMSS));
                    txnBean.setSwtSettleDate(log.getSettleDate());
                    txnBean.setChannel(transChnl); //
                    txnBean.setIssChannel(transLog.getInsCode()); // 机构渠道号
                    txnBean.setInnerMerchantNo(transLog.getMchntCode()); // 商户号
                    txnBean.setInnerShopNo(transLog.getShopCode());
                    txnBean.setCardNo("U" + transLog.getUserName()); // U+UserName
                    txnBean.setTxnAmount(transLog.getTransAmt()); // 交易金额
                    txnBean.setOriTxnAmount(transLog.getTransAmt());
                    txnBean.setTimestamp(System.currentTimeMillis());// 时间戳
                    txnBean.setOriSwtFlowNo(transLog.getDmsRelatedKey());
                    long currentTime = System.currentTimeMillis();
                    txnBean.setTimestamp(currentTime);
                    String signature = SignatureUtil.genB2CTransMsgSignature(txnBean.getSwtSettleDate(),
                            txnBean.getSwtFlowNo(), txnBean.getIssChannel(), txnBean.getInnerMerchantNo(),
                            txnBean.getTxnAmount(), txnBean.getOriSwtFlowNo(), txnBean.getOriTxnAmount(),
                            txnBean.getCardNo(), txnBean.getPinTxn(), txnBean.getTimestamp());
                    txnBean.setMac(signature);
                    // 远程调用退款接口
                    String json = java2TxnBusinessFacade.consumeRefundITF(txnBean);
                    resp = JSONArray.parseObject(json, TxnResp.class);
                    if (resp == null || StringUtil.isNullOrEmpty(resp.getCode())) {
                        json = java2TxnBusinessFacade.transExceptionQueryITF(log.getWxPrimaryKey());
                        resp = JSONArray.parseObject(json, TxnResp.class);
                    }
                } catch (Exception ex) {
                    logger.error("## 商户退款translogRefundCommit 系统故障", ex);
                }

                try {
                    logger.info("退款交易成功并返回", JSONArray.toJSONString(resp));
                    log.setTransSt(1);// 插入时为0，报文返回时更新为1
                    log.setRespCode(resp.getCode());
                    log.setTransAmt(resp.getTransAmt());// 交易核心返回的优惠后实际交易金额
                    log.setDmsRelatedKey(dms_related_key);// 外部流水号
                    wxTransLogService.updateWxTransLog(log, resp);
                } catch (Exception ex) {
                    logger.error("## 商户退款translogRefundCommit 更新WXP流水异常", ex);
                }

                if (!Constants.TXN_TRANS_RESP_SUCCESS.equals(resp.getCode())) {
                    // 出现异常情况的处理
                    logger.error("## 商户退款translogRefundCommit 消费交易撤销失败", resp.getInfo());
                    resultHtml.setStatus(false);
                    resultHtml.setMsg(resp.getInfo());
                    return resultHtml;
                } else {
                    resultHtml.setStatus(true);
                }
            } else if (Constants.JF_TRANS_RESP_SUCCESS.equals(payBackResp.getCode())) {
                resultHtml.setMsg(Constants.JF_PAY_BACK_REPEAT_DESC);
            } else {
                resultHtml.setMsg(payBackResp.getInfo());
            }
        } catch (Exception ex) {
            resultHtml.setStatus(false);
            resultHtml.setMsg(Constants.JF_PAY_BACK_REPEAT_DESC);
            logger.error("## 商户退款translogRefundCommit 系统故障", ex);
        } finally {
            try {
                if (!Constants.TXN_TRANS_RESP_SUCCESS.equals(resp.getCode())) { // 多次退款不成功后发短信给开发人员
                    String phonesStr = RedisDictProperties.getInstance().getdictValueByCode("SYSTEM_MONITORING_USER_PHONES");
                    String[] users = phonesStr.split(",");
                    if (users != null) {
                        for (int i = 0; i < users.length; i++) {
                            messageService.sendMessage(users[i], "【知了企服】商户退款出现故障，交易流水号：" + log.getWxPrimaryKey() + "，请您及时处理！");
                        }
                    }
                }
            } catch (Exception ex) {
                logger.error("## 商户退款translogRefundCommit 短信发送异常", ex);
            }
        }

        // 退款成功，发送客服消息
        if (Constants.TXN_TRANS_RESP_SUCCESS.equals(resp.getCode())) {
            if (ChannelCode.CHANNEL1.toString().equals(itfLog.getTransChnl()) || ChannelCode.CHANNEL2.toString().equals(itfLog.getTransChnl())) {
                final String transIdF = txnPrimaryKey;
                final String swtTxnDateF = txnBean.getSwtTxnDate();
                final String swtTxnTimeF = txnBean.getSwtTxnTime();
                final String mchntNameF = mchntManager.getMchntName();
                final String shopNameF = mchntManager.getShopName();
                final String transAmtF = NumberUtils.RMBCentToYuan(transLog.getTransAmt());
                final String openID = itfLog.getUserInfUserName();
                final String channel = itfLog.getTransChnl();
                ExecutorService es = Executors.newSingleThreadExecutor();
                es.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            /**        发送客服消息                */
//							WechatCustomerParam param = new WechatCustomerParam();
//							String txnDate = DateUtil.getChineseDateFormStr(swtTxnDateF + swtTxnTimeF);
//							param.setAcountName(RedisDictProperties.getInstance().getdictValueByCode("WX_CUSTOMER_ACCOUNT"));
//							param.setContent(String.format(WechatCustomerMessageUtil.WECHAT_CUSTOMER_REFUNDORDER, txnDate,
//									mchntNameF, shopNameF, transAmtF));
//							param.setToOpenId(openID);
//							wechatMQProducerService.sendMessage(param);
                            String txnDate = DateUtil.getChineseDateFormStr(swtTxnDateF + swtTxnTimeF);
                            /**        发送模板消息                */
                            wechatMQProducerService.sendTemplateMsg(RedisDictProperties.getInstance().getdictValueByCode("WX_CUSTOMER_ACCOUNT"),
                                    openID, "WX_TEMPLATE_ID_4", null,
                                    WXTemplateUtil.setRefundData(transIdF, mchntNameF, shopNameF, transAmtF, txnDate, templateMsgRefund.findByCode(channel).getValue(), templateMsgPayment.findByCode(channel).getValue()));
                        } catch (Exception ex) {
                            logger.error("## 商户退款translogRefundCommit 模板消息发送失败", ex);
                        }
                    }
                });
                es.shutdown();
            }
        }
        return resultHtml;
    }
    // /**
    // * 交易记录 统计 list
    // * @param request
    // * @return
    // */
    // @RequestMapping(value = "/mchntTransDetailList")
    // public ModelAndView mchntTransDetailList(HttpServletRequest
    // request,TransLog ts, Pagination<TransLog> pagination) {
    //
    // ModelAndView mv = new
    // ModelAndView("wxclient/translog/mchntTransDetailList");
    // // 拦截器已经处理了缓存,这里直接取
    // String openid = WxMemoryCacheClient.getOpenid(request);
    // try{
    // if(StringUtil.isNullOrEmpty(openid)){
    // return unValidated(request);
    // }
    // MerchantManager mchntManager=
    // merchantManagerService.getMerchantInsInfByOpenId(openid);
    // if(mchntManager==null){
    // return unValidated(request);
    // }
    // ts.setInsCode(mchntManager.getInsCode());
    // ts.setMchntCode(mchntManager.getMchntCode());
    // if(!mchntManager.getRoleType().equals(Constants.RoleNameEnum.BOSS_ROLE_MCHANT.getRoleType())
    // &&
    // !mchntManager.getRoleType().equals(Constants.RoleNameEnum.FINANCIAL_RORE_MCHANT.getRoleType())){
    // ts.setShopCode(mchntManager.getShopCode());
    // }
    // pagination= transLogService.querymchntTransDetailList(ts, pagination);
    //
    // mv.addObject("ts", ts);
    // mv.addObject("pagination", pagination);
    // } catch(Exception ex){
    // ex.printStackTrace();
    // }
    // return mv;
    // }

    /**
     * 账户管理
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/mchntAcctList")
    public ModelAndView mchntAcctList(HttpServletRequest request) {

        ModelAndView mv = new ModelAndView("wxclient/account/mchntAcctList");
        // 拦截器已经处理了缓存,这里直接取
        String openid = WxMemoryCacheClient.getOpenid(request);
        MerchantManager mchntManager = merchantManagerService.getMerchantInsInfByOpenId(openid);
        if (mchntManager == null) {
            return unValidated(request);
        }
        try {
            String insId = mchntManager.getInsId(); // 商户结构Id
            String manchtId = mchntManager.getMchntId();// 所属商户Id
            // 查询账户沉淀资金
            long sumDepsositMoney = mchntAcctInfService.getSumAccBalByMchnt(
                    Constants.MchntAcctTypeEnum.DEPOSIT_MONEY_ACCT_TYPE.getCode(),
                    Constants.MchntAcctStatEnum.NORMAL_ACCT_SATA.getCode(), mchntManager.getInsId(),
                    mchntManager.getMchntId());

            // 查看商户卡余额
            long sumCardBal = mchntAcctInfService.getSumCardBalByMchnt(null, insId, manchtId);

            mv.addObject("sumDepsositMoney", NumberUtils.formatMoney(sumDepsositMoney));
            mv.addObject("sumCardBal", NumberUtils.formatMoney(sumCardBal));

            mv.addObject("abortDate", DateUtil.getStringFromDate(new Date(), DateUtil.FORMAT_YYYY_MM_DD));
        } catch (Exception ex) {

        }
        return mv;
    }

    // /**
    // * 提现列表list
    // * @param request
    // * @return
    // */
    // @RequestMapping(value = "/withdrawDetaillist")
    // public ModelAndView withdrawDetaillist(HttpServletRequest
    // request,TransLog ts, Pagination<TransLog> pagination) {
    //
    // ModelAndView mv = new
    // ModelAndView("wxclient/account/withdrawDetailList");
    // // 拦截器已经处理了缓存,这里直接取
    // String openid =
    // WxMemoryCacheClient.getOpenid(request.getSession().getId());
    // try{
    // MerchantManager mchntManager=
    // merchantManagerService.getMerchantInsInfByOpenId(openid);
    // ts.setTransId(TransCode.MB10.getCode());
    // ts.setInsCode(mchntManager.getInsCode());
    // ts.setMchntCode(mchntManager.getMchntCode());
    // pagination= transLogService.listEntityQuery(ts, pagination);
    //
    // /***交易流水总资金**/
    // long transAmt= transLogService.querySumTransAmount(ts);
    // mv.addObject("pagination", pagination);
    // mv.addObject("ts", ts);
    // mv.addObject("transAmtCount",NumberUtils.formatMoney(transAmt));
    // } catch(Exception ex){
    // ex.printStackTrace();
    // }
    // return mv;
    // }

    // /**
    // * 提现页面
    // * @param request
    // * @return
    // */
    // @RequestMapping(value = "/mchntWithdraw")
    // public ModelAndView mchntWithdraw(HttpServletRequest request){
    //
    // ModelAndView mv = new ModelAndView("wxclient/account/withdraw");
    // // 拦截器已经处理了缓存,这里直接取
    // String openid =
    // WxMemoryCacheClient.getOpenid(request.getSession().getId());
    // if(StringUtil.isNullOrEmpty(openid)){
    // return unValidated(request);
    // }
    // MerchantManager mchntManager=
    // merchantManagerService.getMerchantInsInfByOpenId(openid);
    // String insId=mchntManager.getInsId(); //商户结构Id
    // String manchtId=mchntManager.getMchntId();//所属商户Id
    // try{
    // //查询账户沉淀资金
    // long
    // sumDepsositMoney=mchntAcctInfService.getSumAccBalByMchnt(Constants.MchntAcctTypeEnum.DEPOSIT_MONEY_ACCT_TYPE.getCode(),
    // Constants.MchntAcctStatEnum.NORMAL_ACCT_SATA.getCode(),insId, manchtId);
    // mv.addObject("sumDepsositMoney",NumberUtils.formatMoney(sumDepsositMoney));
    // //格式化
    //
    // String
    // predictDate=DateUtil.getStringFromDate(DateUtil.addDate(DateUtil.getCurrDate(),
    // Calendar.DAY_OF_MONTH,1),DateUtil.FORMAT_YYYY_MM_DD);
    // mv.addObject("predictTime",predictDate +" 23:59:59");
    //// //手续费率
    //// MerchantCashManager
    // cashManager=merchantInfService.getMerchantCashManagerById(manchtId);
    //// mv.addObject("frossProfitRate",
    // NumberUtils.formatRate4(cashManager.getGrossProfitRate()));
    // } catch(Exception ex){
    // logger.error("merchant withdraw error of mchntId="+manchtId);
    // }
    //
    // /***提现页面 RAS 密码私钥获取***/
    // try {
    // setPwdRSAKeyByopenId(request,mv,openid);
    // } catch (NoSuchAlgorithmException e) {
    // logger.error("生成密钥失败", e);
    // mv = new ModelAndView("common/failure");
    // mv.addObject("failureMsg", "系统异常");
    // return mv;
    // }
    // return mv;
    // }
    //
    // /**
    // * 商户提现 submit 操作
    // * @param request
    // * @param response
    // * @return
    // */
    // @RequestMapping(value = "/widthdrawCommit",method = RequestMethod.POST)
    // public @ResponseBody TxnResp widthdrawCommit(HttpServletRequest
    // request,HttpServletResponse response){
    // TxnResp resp = new TxnResp();
    //
    // int
    // amount=NumberUtils.disRatehundred(Double.parseDouble(StringUtil.nullToString(request.getParameter("amount"))));//提现金额
    // 单位分
    // String
    // password=StringUtil.nullToString(request.getParameter("password"));
    // //提现密码
    // WxTransLog log= null;
    //
    // try{
    // // 拦截器已经处理了缓存,这里直接取
    // String openid =
    // WxMemoryCacheClient.getOpenid(request.getSession().getId());
    // if(StringUtil.isNullOrEmpty(openid)){
    // return null;
    // }
    // MerchantManager
    // mm=merchantManagerService.getMerchantInsInfByOpenId(openid);//查询所属商户号
    // 关联查询 查找出来机构ID
    //
    // //根据模和私钥指数获取私钥
    // RSAPrivateKey privateKey =
    // (RSAPrivateKey)Constants.sessionMap.get(openid);
    // password = RSAUtil.decryptByPrivateKey(password, privateKey);
    //
    // CtrlSystem cs = ctrlSystemService.getCtrlSystem();// 得到日切信息
    // if (cs != null) {
    // if (Constants.TRANS_FLAG_YES.equals(cs.getTransFlag())) {//
    // 日切状态为允许时，插入微信端流水
    // log= new WxTransLog();
    // String id = wxTransLogService.getPrimaryKey();
    // log.setWxPrimaryKey(id);
    // log.setTableNum(cs.getCurLogNum());// 得到当前可以进行操作的流水表号
    // log.setSettleDate(cs.getSettleDate());// 交易日期
    // log.setTransId(TransCode.MB10.getCode());// 交易类型 提现
    // log.setTransSt(0);// 插入时为0，报文返回时更新为1
    // log.setInsCode(mm.getInsCode());// 商户所属的机构code
    // log.setMchntCode(mm.getMchntCode());
    // log.setUserInfUserName(openid);
    // log.setTransAmt(String.valueOf(amount));// 实际交易金额 插入时候默认与上送金额一致
    // log.setUploadAmt(String.valueOf(amount));// 上送金额
    // log.setTransCurrCd(Constants.TRANS_CURR_CD);
    // int i = wxTransLogService.insertWxTransLog(log);// 插入流水记录
    // if (i != 1) {
    // resp.setCode(Constants.TXN_TRANS_EXCEPTION);
    // logger.info("商户提现--->insertIntfaceTransLog微信端插入流水记录数量不为1");
    // return resp;
    // }
    // TxnPackageBean txnBean = new TxnPackageBean();
    // Date currDate=DateUtil.getCurrDate();
    // txnBean.setSwtFlowNo(log.getWxPrimaryKey()); //接口平台交易流水号
    // txnBean.setTxnType(log.getTransId()+"0");// 0: 同步 1:异步
    // txnBean.setSwtTxnDate(DateUtil.getStringFromDate(currDate,DateUtil.FORMAT_YYYYMMDD));
    // txnBean.setSwtTxnTime(DateUtil.getStringFromDate(currDate,DateUtil.FORMAT_HHMMSS));
    // txnBean.setSwtSettleDate(log.getSettleDate());
    // txnBean.setChannel(ChannelCode.CHANNEL2.toString()); // 充值、商户提现
    // (从微信公众号发起)
    // txnBean.setIssChannel(mm.getInsCode()); //机构渠道号
    // txnBean.setInnerMerchantNo(mm.getMchntCode()); //商户号
    // txnBean.setCardNo("U"+openid); //U+UserName
    // txnBean.setTxnAmount(String.valueOf(amount)); //交易金额
    // txnBean.setPinTxn(DES3Util.Encrypt3DES(password, KEY)); //交易密码，明文
    // txnBean.setUserId(mm.getMangerId());
    // txnBean.setTimestamp(System.currentTimeMillis());// 时间戳
    //
    // String signature =
    // SignatureUtil.getTxnBeanSignature(txnBean.getSwtSettleDate(),
    // txnBean.getSwtFlowNo(),
    // txnBean.getIssChannel(),txnBean.getCardNo(),txnBean.getTxnAmount(),
    // txnBean.getPinTxn(), txnBean.getTimestamp());
    // txnBean.setSignature(signature);
    // // 远程调用提现接口
    // String json = java2TxnBusinessFacade.merchantWithdrawITF(txnBean);
    // //用户提现接口
    //
    // resp = JSONArray.parseObject(json, TxnResp.class);
    // log.setTransSt(1);// 插入时为0，报文返回时更新为1
    // log.setRespCode(resp.getCode());
    // log.setTransAmt(resp.getTransAmt());// 交易核心返回的优惠后实际交易金额
    // log.setTransFee(resp.getCardHolderFee()); //手续费
    // wxTransLogService.updateWxTransLog(log,resp);
    // if (!Constants.TXN_TRANS_RESP_SUCCESS.equals(resp.getCode())) {
    // logger.info("商户提现--->"+resp.getInfo());
    // return resp;
    // }
    // resp.setTransAmt(String.valueOf(NumberUtils.formatMoney(resp.getTransAmt())));
    // resp.setCardHolderFee(String.valueOf(NumberUtils.formatMoney(resp.getCardHolderFee())));
    // } else {
    // resp.setCode(Constants.TXN_TRANS_EXCEPTION);
    // logger.info("商户提现--->日切信息交易允许状态：日切中");
    // return resp;
    // }
    // }
    // }catch(Exception ex){
    // if(ex instanceof com.alibaba.dubbo.rpc.RpcException){
    // try{
    // if(log !=null){
    // String json =
    // java2TxnBusinessFacade.transExceptionQueryITF(log.getWxPrimaryKey());
    // resp = JSONArray.parseObject(json, TxnResp.class);
    // if("00".equals(resp.getCode())){
    // wxTransLogService.updateWxTransLog(log,resp);
    // }
    // }
    // }catch(Exception e){
    // resp.setCode(Constants.TXN_TRANS_EXCEPTION);
    // resp.setInfo(MessageUtil.ERROR_MSSAGE);//异常信息处理
    // return resp;
    // }
    // }
    // if(log !=null){
    // resp.setCode(Constants.TXN_TRANS_EXCEPTION);
    // resp.setInfo(MessageUtil.ERROR_MSSAGE);//异常信息处理
    // wxTransLogService.updateWxTransLog(log,resp);
    // }
    // return resp;
    // }
    // return resp;
    // }
    //

    // /**
    // * 提现账户设置
    // * @param request
    // * @return
    // */
    // @RequestMapping(value = "/mchntSetting")
    // public ModelAndView mchntWithdrawSet(HttpServletRequest request){
    //
    // ModelAndView mv = new ModelAndView("wxclient/account/mchntSettings");
    // // 拦截器已经处理了缓存,这里直接取
    // String openid =
    // WxMemoryCacheClient.getOpenid(request.getSession().getId());
    // if(StringUtil.isNullOrEmpty(openid)){
    // return unValidated(request);
    // }
    // MerchantManager
    // mm=merchantManagerService.getMerchantInsInfByOpenId(openid);//查询所属商户号
    // 关联查询 查找出来机构ID
    // if(mm ==null){
    // return unValidated(request);
    // }
    // if(StringUtil.isNotEmpty(mm.getPhoneNumber())){
    // mv.addObject("phoneNo",StringUtil.getPhoneNumberFormat(mm.getPhoneNumber()));
    // }
    // /***提现页面 RAS 密码私钥获取***/
    // try {
    // setPwdRSAKeyByopenId(request,mv,openid);
    // } catch (NoSuchAlgorithmException e) {
    // logger.error("生成密钥失败", e);
    // mv = new ModelAndView("common/failure");
    // mv.addObject("failureMsg", "系统异常");
    // return mv;
    // }
    // return mv;
    // }

    // /**
    // * 商户信息设置提交
    // * @param request
    // * @param response
    // * @return
    // */
    // @RequestMapping(value = "/mchntPwdRetCommit",method = RequestMethod.POST)
    // public @ResponseBody TxnResp userSettingsCommit(HttpServletRequest
    // request,HttpServletResponse response){
    // TxnResp resp = new TxnResp();
    // String
    // phoneCode=StringUtil.nullToString(request.getParameter("phoneCode"));
    // //验证码
    // String
    // password=StringUtil.nullToString(request.getParameter("password"));
    // //交易密码
    // String
    // passwordConfirm=StringUtil.nullToString(request.getParameter("passwordConfirm"));
    // //二次确认密码
    //
    // try{
    // // 拦截器已经处理了缓存,这里直接取
    // String openid =
    // WxMemoryCacheClient.getOpenid(request.getSession().getId());
    // if(StringUtil.isNullOrEmpty(openid)){
    // resp.setCode(Constants.TXN_TRANS_EXCEPTION);
    // resp.setInfo("请从微信平台重新访问");
    // return resp;
    // }
    // MerchantManager
    // mm=merchantManagerService.getMerchantInsInfByOpenId(openid);//查询所属商户号
    // 关联查询 查找出来机构ID
    // if(mm ==null){
    // resp.setCode(Constants.TXN_TRANS_EXCEPTION);
    // resp.setInfo("您不是管理员，不能访问该页面");
    // return resp;
    // }
    // String phoneNumber=mm.getPhoneNumber();//手机号
    //
    // /**step1. 动态口令验证**/
    // if(StringUtil.isNullOrEmpty(phoneCode)){
    // resp.setCode(Constants.TXN_TRANS_EXCEPTION);
    // resp.setInfo(MessageUtil.ERROR_MESSAGE_PHONE_CODE);//手机号验证码
    // return resp;
    // }
    // ResultHtml resultHtml = new ResultHtml();
    // if(StringUtil.isNullOrEmpty(phoneNumber) ||
    // !checkPhoneCode(phoneCode,request.getSession(),resultHtml)){
    // resp.setCode(Constants.TXN_TRANS_EXCEPTION);
    // resp.setInfo(resultHtml.getMsg());
    // return resp;
    // }
    // /**step2. 交易密码校验**/
    // if(StringUtil.isEmpty(password) || !password.equals(passwordConfirm)){
    // resp.setCode(Constants.TXN_TRANS_EXCEPTION);
    // resp.setInfo("您的交易密码和二次确认密码不匹配,请重新输入");
    // return resp;
    // }
    // //根据模和私钥指数获取私钥
    // RSAPrivateKey privateKey =
    // (RSAPrivateKey)Constants.sessionMap.get(openid);
    // password = RSAUtil.decryptByPrivateKey(password, privateKey);
    // if(StringUtil.isNotEmpty(openid)){ //密码设置
    // CtrlSystem cs = ctrlSystemService.getCtrlSystem();// 得到日切信息
    // WxTransLog log = new WxTransLog();
    // if (cs != null) {
    // if (Constants.TRANS_FLAG_YES.equals(cs.getTransFlag())) {//
    // 日切状态为允许时，插入微信端流水
    // try{
    // String id = wxTransLogService.getPrimaryKey();
    // log.setWxPrimaryKey(id);
    // log.setTableNum(cs.getCurLogNum());// 得到当前可以进行操作的流水表号
    // log.setSettleDate(cs.getSettleDate());// 交易日期
    // log.setTransId(TransCode.CW81.getCode());//密码重置 0: 同步 1:异步
    // log.setTransSt(0);// 插入时为0，报文返回时更新为1
    // log.setOperatorOpenId(openid);
    // int i = wxTransLogService.insertWxTransLog(log);// 插入流水记录
    // if (i != 1) {
    // resp.setCode(Constants.TXN_TRANS_EXCEPTION);
    // logger.info("商户密码重置--->insertIntfaceTransLog微信端插入流水记录数量不为1");
    // return resp;
    // }
    // TxnPackageBean txnBean = new TxnPackageBean();
    // Date currDate=DateUtil.getCurrDate();
    // txnBean.setSwtFlowNo(log.getWxPrimaryKey()); //接口平台交易流水号
    // txnBean.setTxnType(log.getTransId()+"0");//密码重置 0: 同步 1:异步
    // txnBean.setSwtTxnDate(DateUtil.getStringFromDate(currDate,DateUtil.FORMAT_YYYYMMDD));
    // txnBean.setSwtTxnTime(DateUtil.getStringFromDate(currDate,DateUtil.FORMAT_HHMMSS));
    // txnBean.setSwtSettleDate(log.getSettleDate());
    // txnBean.setChannel(ChannelCode.CHANNEL1.toString()); // 商户开户、客户开户、密码重置、消费
    // (从微信公众号发起)
    // txnBean.setInnerMerchantNo(mm.getMchntCode());//商户号
    // txnBean.setIssChannel(mm.getInsCode()); //机构渠道号
    // txnBean.setCardNo("U"+openid); //U+UserName
    // txnBean.setAccType(BaseConstants.PWDAcctType.MERCHANT_PWD_200.getCode());
    // //账户类型
    // txnBean.setPinTxn(DES3Util.Encrypt3DES(password, KEY));
    // txnBean.setTimestamp(System.currentTimeMillis());// 时间戳
    // /***生成签名***/
    // String signature =
    // SignatureUtil.getTxnBeanSignature(txnBean.getSwtSettleDate(),
    // txnBean.getSwtFlowNo(),
    // txnBean.getIssChannel(),txnBean.getCardNo(),txnBean.getTxnAmount(),
    // txnBean.getPinTxn(), txnBean.getTimestamp());
    // txnBean.setSignature(signature);
    // // 远程调用重置密码接口
    // String json = java2TxnBusinessFacade.customerPasswordResetITF(txnBean);
    // resp = JSONArray.parseObject(json, TxnResp.class);
    // log.setTransSt(1);// 插入时为0，报文返回时更新为1
    // log.setRespCode(resp.getCode());
    // wxTransLogService.updateWxTransLog(log,resp);
    // if (!Constants.TXN_TRANS_RESP_SUCCESS.equals(resp.getCode())) {
    // logger.info("商户密码重置--->"+resp.getInfo());
    // return resp;
    // }
    // Constants.sessionMap.remove(openid);// 删除session
    // }catch(Exception ex){
    // resp.setCode(Constants.TXN_TRANS_EXCEPTION);
    // resp.setInfo(MessageUtil.ERROR_MSSAGE);//系统异常提示
    // wxTransLogService.updateWxTransLog(log,resp);
    // logger.info("商户密码重置--->异常");
    // return resp;
    // }
    // } else {
    // resp.setCode(Constants.TXN_TRANS_EXCEPTION);
    // resp.setInfo(MessageUtil.ERROR_MSSAGE);
    // logger.info("商户密码重置--->日切信息交易允许状态：日切中");
    // return resp;
    // }
    //
    // }
    // }
    // }catch(Exception ex){
    // resp.setCode(Constants.TXN_TRANS_EXCEPTION);
    // resp.setInfo(MessageUtil.ERROR_MSSAGE);//手机号验证码
    // return resp;
    // }
    // return resp;
    // }
    //

    /**
     * 优惠管理列表list
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/specialOfferList")
    public ModelAndView specialOfferList(HttpServletRequest request) {

        ModelAndView mv = new ModelAndView("wxclient/rule/specialOfferList");
        try {
            List<RuleTemplate> tempList = transRuleService.getRuleTemplateList();
            mv.addObject("tempList", tempList);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return mv;
    }

    /**
     * 规则列表list
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/ruleTypeList")
    public ModelAndView ruleTypeList(HttpServletRequest request) {

        ModelAndView mv = new ModelAndView("wxclient/rule/ruleTypeList");
        // 拦截器已经处理了缓存,这里直接取
        String openid = WxMemoryCacheClient.getOpenid(request);
        String templateCode = StringUtil.nullToString(request.getParameter("templateCode")); // 规则模板
        try {
            MerchantManager mchntManager = merchantManagerService.getMerchantInsInfByOpenId(openid);
            Map<String, String> paramMap = new HashMap<String, String>();
            paramMap.put("mchntId", mchntManager.getMchntId());
            paramMap.put("templateCode", templateCode);
            List<TransRuleDeclare> declareList = transRuleService.getTransRuleDeclareList(paramMap);

            mv.addObject("templateCode", templateCode);
            mv.addObject("declareList", declareList);
            mv.addObject("mchntManager", mchntManager);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return mv;
    }

    /**
     * 规则添加 页面
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/addRule")
    public ModelAndView addRule(HttpServletRequest request) {

        ModelAndView mv = new ModelAndView("wxclient/rule/addRule");
        String templateCode = StringUtil.nullToString(request.getParameter("templateCode")); // 规则模板
        List<RuleType> ruleTypeList = transRuleService.getRuleTypeListByTempCode(templateCode);
        mv.addObject("ruleTypeList", ruleTypeList);
        mv.addObject("ruleTypeCode", ruleTypeList.get(0).getRuleTypeCode());
        return mv;
    }

    /***
     * 添加规则 submit ctrl
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/addRuleCommit", method = RequestMethod.POST)
    public @ResponseBody
    ResultHtml addRuleCommit(HttpServletRequest request) {
        ResultHtml resultHtml = new ResultHtml();
        resultHtml.setStatus(false);
        try {
            // 拦截器已经处理了缓存,这里直接取
            String openid = WxMemoryCacheClient.getOpenid(request);
            if (StringUtil.isNullOrEmpty(openid)) {
                return requestOpenIdError();
            }
            MerchantManager merchantManager = merchantManagerService.getMerchantManagerByOpenId(openid);

            String ruleTypeCode = StringUtil.nullToString(request.getParameter("ruleTypeCode")); // 规则类型
            String factor1 = StringUtil.nullToString(request.getParameter("factor1")); // 因子
            String param1 = StringUtil.nullToString(request.getParameter("param1")); // 参数
            String bTime = StringUtil.nullToString(request.getParameter("bTime")); // 开始时间
            String eTime = StringUtil.nullToString(request.getParameter("eTime")); // 开始时间
            String dataStat = StringUtil.nullToString(request.getParameter("dataStat")); // 0:新增，1：编辑
            String transRuleDesp = StringUtil.nullToString(request.getParameter("transRuleDesp")); // 活动描述

            TransRule transRule = new TransRule();
            transRule.setMchntId(merchantManager.getMchntId());
            transRule.setIsSuperposed(
                    com.cn.thinkx.pms.base.utils.DroolsConstants.RuleSuperposedEnum.FALSE_SUP.getCode()); // 是否叠加
            // 不叠加
            transRule.setSalience("10");// 执行顺序 可随意，优惠叠加类型请设置0
            transRule.setStartTime(DateUtil.toDateStart(bTime));
            transRule.setEndTime(DateUtil.toDateEnd(eTime));
            transRule.setDataStat(dataStat);
            transRule.setCreateUser(merchantManager.getMangerId()); // 管理员ID
            transRule.setTransRuleDesp(transRuleDesp);
            List<RuleFactor> factorList = new ArrayList<>();

            RuleFactor ruleFactor = new RuleFactor();
            if (com.cn.thinkx.pms.base.utils.DroolsConstants.RuleTypeEnum.RULETYPE_2000.getCode()
                    .equals(ruleTypeCode)) {
                ruleFactor.setRuleFactor(NumberUtils.disRatehundred(Double.parseDouble(factor1)));// 百分比转
                ruleFactor.setRuleParam(NumberUtils.disRateTenThousand(Double.parseDouble(param1))); // 万分比转换
            } else if (com.cn.thinkx.pms.base.utils.DroolsConstants.RuleTypeEnum.RULETYPE_4000.getCode()
                    .equals(ruleTypeCode)) {
                ruleFactor.setRuleFactor(NumberUtils.disRatehundred(Double.parseDouble(factor1)));// 百分比转
                ruleFactor.setRuleParam(NumberUtils.disRateTenThousand(Double.parseDouble(param1)));// 万分比转换
            } else {
                ruleFactor.setRuleFactor(NumberUtils.disRatehundred(Double.parseDouble(factor1)));// 百分比转
                ruleFactor.setRuleParam(NumberUtils.disRatehundred(Double.parseDouble(param1)));// 百分比转
            }
            ruleFactor.setCreateUser(merchantManager.getMangerId());// 管理员ID
            ruleFactor.setDataStat(dataStat);
            factorList.add(ruleFactor);

            int operNum = transRuleService.insertRechargePresentRule(ruleTypeCode, transRule, factorList);

            if (operNum == 1) {
                resultHtml.setStatus(true);
                return resultHtml;
            } else {
                resultHtml.setMsg("商户优惠活动规则添加失败，请重新添加");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return errorMsg();
        }
        return resultHtml;
    }

    /**
     * 规则编辑 页面
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/editRule")
    public ModelAndView editRule(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("wxclient/rule/editRule");
        String ruleId = StringUtil.nullToString(request.getParameter("ruleId")); // 规则Id
        TransRuleDeclare model = transRuleService.getTransRuleDeclareByRuleId(ruleId);

        // 拦截器已经处理了缓存,这里直接取
        String openid = WxMemoryCacheClient.getOpenid(request);
        MerchantManager merchantManager = merchantManagerService.getMerchantManagerByOpenId(openid);
        mv.addObject("mchntManager", merchantManager);
        mv.addObject("rule", model);
        return mv;
    }

    /***
     * 添加规则 submit ctrl
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/editRuleCommit", method = RequestMethod.POST)
    public @ResponseBody
    ResultHtml editRuleCommit(HttpServletRequest request) {
        ResultHtml resultHtml = new ResultHtml();
        resultHtml.setStatus(false);
        try {
            // 拦截器已经处理了缓存,这里直接取
            String openid = WxMemoryCacheClient.getOpenid(request);
            if (StringUtil.isNullOrEmpty(openid)) {
                return requestOpenIdError();
            }
            MerchantManager merchantManager = merchantManagerService.getMerchantManagerByOpenId(openid);
            if (merchantManager == null) {
                return errorMsg();
            }
            String transRuleId = StringUtil.nullToString(request.getParameter("transRuleId")); // 规则类型
            String bTime = StringUtil.nullToString(request.getParameter("bTime")); // 开始时间
            String eTime = StringUtil.nullToString(request.getParameter("eTime")); // 结算时间
            String dataStat = StringUtil.nullToString(request.getParameter("dataStat")); // 0:新增，1：编辑
            String transRuleDesp = StringUtil.nullToString(request.getParameter("transRuleDesp")); // 活动描述
            TransRule transRule = transRuleService.getTransRuleById(transRuleId);
            if (DateUtil.toDateStart(bTime).getTime() > DateUtil.toDateStart(eTime).getTime()) {
                resultHtml.setMsg("开始时间不能小于结束时间");
                return resultHtml;
            }
            if (DateUtil.toDateStart(eTime).getTime() < DateUtil.getCurrDate().getTime()) {
                resultHtml.setMsg("活动已经结束，暂不能重新开启活动！请重新添加优惠活动规则");
                return resultHtml;
            }
            if (transRule == null) {
                return errorMsg();
            }
            transRule.setTransRuleDesp(transRuleDesp);
            if ("0".equals(dataStat)) {
                transRule.setStartTime(DateUtil.toDateStart(bTime));
                transRule.setEndTime(DateUtil.toDateEnd(eTime));
                transRule.setDataStat(dataStat);
            } else {
                if (DateUtil.getCurrDate().getTime() > transRule.getStartTime().getTime()) { // 如果活动已经开始
                    transRule.setEndTime(
                            DateUtil.toDateEnd(DateUtil.getStringFromDate(new Date(), DateUtil.FORMAT_YYYY_MM_DD)));
                } else {
                    transRule.setStartTime(DateUtil.toDateStart(bTime));
                    transRule.setEndTime(DateUtil.toDateEnd(eTime));
                    transRule.setDataStat(dataStat);
                }
            }
            transRule.setUpdateUser(merchantManager.getMangerId());
            int operNum = transRuleService.updateTransRule(transRule);
            if (operNum == 1) {
                resultHtml.setStatus(true);
                return resultHtml;
            } else {
                resultHtml.setMsg("商户优惠活动规则编辑失败，请重新提交");
            }
        } catch (Exception ex) {

            return errorMsg();
        }
        return resultHtml;
    }
}
