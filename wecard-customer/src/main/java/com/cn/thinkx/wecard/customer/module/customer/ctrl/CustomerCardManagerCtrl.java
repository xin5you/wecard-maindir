package com.cn.thinkx.wecard.customer.module.customer.ctrl;

import com.alibaba.fastjson.JSONArray;
import com.cn.thinkx.beans.CtrlSystem;
import com.cn.thinkx.common.wecard.domain.merchant.MerchantInf;
import com.cn.thinkx.common.wecard.domain.person.PersonInf;
import com.cn.thinkx.common.wecard.domain.trans.InterfaceTrans;
import com.cn.thinkx.common.wecard.domain.trans.WxTransLog;
import com.cn.thinkx.common.wecard.domain.user.UserInf;
import com.cn.thinkx.common.wecard.domain.user.UserMerchantAcct;
import com.cn.thinkx.facade.bean.CardTransDetailQueryRequest;
import com.cn.thinkx.facade.bean.CusAccListQueryRequest;
import com.cn.thinkx.facade.bean.CusAccQueryRequest;
import com.cn.thinkx.facade.bean.MchntInfQueryRequest;
import com.cn.thinkx.facade.bean.base.BaseTxnReq;
import com.cn.thinkx.facade.service.HKBTxnFacade;
import com.cn.thinkx.pms.base.redis.util.RedisConstants;
import com.cn.thinkx.pms.base.redis.util.SignUtil;
import com.cn.thinkx.pms.base.utils.BaseConstants;
import com.cn.thinkx.pms.base.utils.BaseConstants.ChannelCode;
import com.cn.thinkx.pms.base.utils.BaseConstants.TransCode;
import com.cn.thinkx.pms.base.utils.DateUtil;
import com.cn.thinkx.pms.base.utils.NumberUtils;
import com.cn.thinkx.pms.base.utils.StringUtil;
import com.cn.thinkx.wecard.customer.module.base.ctrl.BaseController;
import com.cn.thinkx.wecard.customer.module.customer.service.*;
import com.cn.thinkx.wecard.customer.module.merchant.service.MerchantInfService;
import com.cn.thinkx.wecard.customer.module.merchant.service.TransLogService;
import com.cn.thinkx.wecard.customer.module.pub.domain.TxnResp;
import com.cn.thinkx.wecard.customer.module.wxcms.service.AccountFansService;
import com.cn.thinkx.wecard.middleware.resp.hkbtxnfacade.entity.*;
import com.cn.thinkx.wecard.middleware.resp.hkbtxnfacade.vo.CardTransDetailQueryITFVo;
import com.cn.thinkx.wecard.middleware.resp.hkbtxnfacade.vo.CustomerAccListQueryITFVo;
import com.cn.thinkx.wecard.middleware.resp.hkbtxnfacade.vo.MchtSellingCardListQueryITFVo;
import com.cn.thinkx.wecard.middleware.resp.hkbtxnfacade.vo.MerchantInfoQueryITFVo;
import com.cn.thinkx.wechat.base.wxapi.domain.AccountFans;
import com.cn.thinkx.wechat.base.wxapi.process.WxMemoryCacheClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import redis.clients.jedis.JedisCluster;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 微信会员页面
 */
@Controller
@RequestMapping("/customer/card")
public class CustomerCardManagerCtrl extends BaseController {
    Logger logger = LoggerFactory.getLogger(CustomerCardManagerCtrl.class);

    @Autowired
    private HKBTxnFacade hkbTxnFacade;

    @Autowired
    @Qualifier("merchantInfService")
    private MerchantInfService merchantInfService;

    @Autowired
    @Qualifier("ctrlSystemService")
    private CtrlSystemService ctrlSystemService;

    @Autowired
    @Qualifier("wxTransLogService")
    private WxTransLogService wxTransLogService;

    @Autowired
    @Qualifier("userMerchantAcctService")
    private UserMerchantAcctService userMerchantAcctService;

    @Autowired
    @Qualifier("userInfService")
    private UserInfService userInfService;

    @Autowired
    @Qualifier("personInfService")
    private PersonInfService personInfService;

    @Autowired
    @Qualifier("transLogService")
    private TransLogService transLogService;

    @Autowired
    private AccountFansService accountFansService;

    @Autowired
    @Qualifier("jedisCluster")
    private JedisCluster jedisCluster;

    /**
     * 卡包 列表页面
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/cardList")
    public ModelAndView cardList(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("customer/card/cardBagList");
        CustomerAccListQueryITFResp accList = new CustomerAccListQueryITFResp(); // 商户门店信息
        String openid = WxMemoryCacheClient.getOpenid(request);
        String innerMerchantNo = request.getParameter("innerMerchantNo"); // 商户CODE
        long timestamp = System.currentTimeMillis();// 时间戳
        CusAccListQueryRequest baseTxnReq = new CusAccListQueryRequest();
        try {
            baseTxnReq.setChannel(BaseConstants.ChannelCode.CHANNEL1.toString());
            baseTxnReq.setRemarks(BaseConstants.ACC_ITF);
            baseTxnReq.setInnerMerchantNo(innerMerchantNo);
            baseTxnReq.setUserId(openid);
            baseTxnReq.setTimestamp(timestamp);
            baseTxnReq.setSign(SignUtil.genSign(baseTxnReq));

            String jsonStr = hkbTxnFacade.customerAccListQueryITF(baseTxnReq);
            accList = JSONArray.parseObject(jsonStr, CustomerAccListQueryITFResp.class);

            if (accList != null && BaseConstants.TXN_TRANS_RESP_SUCCESS.equals(accList.getCode())) {
                for (CustomerAccListQueryITFVo tmp : accList.getProductList()) {
                    tmp.setProductBalance(NumberUtils.RMBCentToYuan(tmp.getProductBalance())); // 金额转换
                }
            }

        } catch (Exception e) {
            logger.error("## 卡包	客户会员卡列表信息--->", e);
            accList = new CustomerAccListQueryITFResp();
        }
        mv.addObject("cardList", accList.getProductList());
        return mv;
    }

    /**
     * 卡包 卡交易明细
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/cardTransList")
    public ModelAndView mchntCardTransList(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("customer/card/cardTransList");
        String innerMerchantNo = request.getParameter("innerMerchantNo");
        mv.addObject("innerMerchantNo", innerMerchantNo);
        return mv;
    }

    @RequestMapping(value = "/cardTransListAjax")
    public @ResponseBody
    CardTransDetailQueryITFResp mchntCardTransListAjax(HttpServletRequest request) {

        CardTransDetailQueryITFResp respBean = new CardTransDetailQueryITFResp();
        String openid = WxMemoryCacheClient.getOpenid(request);
        String pageNum = request.getParameter("pageNum");
        String itemSize = request.getParameter("itemSize");
        String innerMerchantNo = request.getParameter("innerMerchantNo");
        try {
            CardTransDetailQueryRequest cardTransReq = new CardTransDetailQueryRequest();
            cardTransReq.setUserId(openid);
            cardTransReq.setPageNum(pageNum);
            cardTransReq.setItemSize(itemSize);
            cardTransReq.setInnerMerchantNo(innerMerchantNo);
            cardTransReq.setTimestamp(System.currentTimeMillis());
            cardTransReq.setChannel(BaseConstants.ChannelCode.CHANNEL1.toString());
            cardTransReq.setSign(SignUtil.genSign(cardTransReq));
            String respJsonStr = hkbTxnFacade.cardTransDetailQueryITF(cardTransReq);

            /** 查询卡的交易明细 **/
            respBean = JSONArray.parseObject(respJsonStr, CardTransDetailQueryITFResp.class);

            if (respBean != null && BaseConstants.TXN_TRANS_RESP_SUCCESS.equals(respBean.getCode())) {
                Date transDate;
                for (CardTransDetailQueryITFVo tmp : respBean.getTransList()) {
                    transDate = DateUtil.getTextDate(tmp.getTransTime(), "yyyy-MM-dd HH:mm:ss");
                    tmp.setBalance(NumberUtils.RMBCentToYuan(tmp.getBalance()));
                    tmp.setTxnAmount(NumberUtils.RMBCentToYuan(tmp.getTxnAmount()));
                    tmp.setDateStr(new SimpleDateFormat("yyyy-MM-dd").format(transDate));
                    tmp.setTimeStr(new SimpleDateFormat("HH:mm:ss").format(transDate));
                    tmp.setTxnFlowNo(tmp.getTxnFlowNo());
                }
            } else {
                respBean = new CardTransDetailQueryITFResp();
            }

        } catch (Exception ex) {
            logger.error("## 卡包  卡交易明细--------》" + ex);
        }
        return respBean;
    }

    @RequestMapping(value = "/transDetailQuery")
    public ModelAndView transDetailQuery(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("customer/card/transDetail");
        String transId = StringUtil.nullToString(request.getParameter("transId"));
        InterfaceTrans trans = transLogService.getInterfaceTransByPrimaryKey(transId);
        trans.setTransAmt(NumberUtils.RMBCentToYuan(trans.getTransAmt()));
        mv.addObject("transIdDesc", BaseConstants.TransCode.findByCode(trans.getTransId()).getValue());
        mv.addObject("transTime", DateUtil.COMMON_FULL.getFormat().format(trans.getCreateTime()));
        mv.addObject("trans", trans);
        return mv;
    }

    /**
     * 上面在售卡页面
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/cardRecharge")
    public ModelAndView cardRechargePage(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("customer/card/cardRecharge");

        ShopInfoQueryITFResp shopInf = new ShopInfoQueryITFResp(); // 商户门店信息

        String innerMerchantNo = request.getParameter("innerMerchantNo"); // 商户CODE

        MerchantInfoQueryITFVo mchntInfo = null;
        MerchantInfoQueryITFResp merchantInfo = new MerchantInfoQueryITFResp(); // 商户门店信息

        long timestamp = System.currentTimeMillis();// 时间戳
        MchntInfQueryRequest mchntInfQueryReq = new MchntInfQueryRequest();
        try {
            mchntInfQueryReq.setInnerMerchantNo(innerMerchantNo);
            mchntInfQueryReq.setTimestamp(timestamp);
            mchntInfQueryReq.setSign(SignUtil.genSign(mchntInfQueryReq));
            String jsonStr = hkbTxnFacade.merchantInfoQueryITF(mchntInfQueryReq);
            merchantInfo = JSONArray.parseObject(jsonStr, MerchantInfoQueryITFResp.class);
            if (merchantInfo != null && BaseConstants.TXN_TRANS_RESP_SUCCESS.equals(merchantInfo.getCode())) {
                if (merchantInfo.getMerchantInfo() != null) {
                    mchntInfo = merchantInfo.getMerchantInfo();
                }
            }
            if (mchntInfo == null) {
                mchntInfo = new MerchantInfoQueryITFVo();
            }

        } catch (Exception e) {
            logger.error("## 卡包  客户会员卡列表信息--->", e);
        }

        MchtSellingCardListQueryITFResp cardList = new MchtSellingCardListQueryITFResp(); // 商户在售卡列表
        BaseTxnReq baseTxnReq = new BaseTxnReq();

        String activeRule = "";
        try {
            baseTxnReq.setInnerMerchantNo(innerMerchantNo);
            baseTxnReq.setTimestamp(timestamp);
            baseTxnReq.setSign(SignUtil.genSign(baseTxnReq));

            String jsonStr = hkbTxnFacade.mchtSellingCardListQueryITF(baseTxnReq);
            cardList = JSONArray.parseObject(jsonStr, MchtSellingCardListQueryITFResp.class);

            if (cardList != null && BaseConstants.TXN_TRANS_RESP_SUCCESS.equals(cardList.getCode())) {

                if (cardList.getCardList() != null && cardList.getCardList().size() > 0) {
                    String commodityPrice;
                    String commodityAmount;
                    for (int i = 0; i < cardList.getCardList().size(); i++) {
                        activeRule = cardList.getCardList().get(0).getActiveRule(); // 活动规则取的是第一个
                        commodityPrice = cardList.getCardList().get(i).getCommodityPrice();
                        commodityAmount = cardList.getCardList().get(i).getCommodityAmount();
                        // 商品售价
                        cardList.getCardList().get(i).setCommodityPrice(NumberUtils.RMBCentToYuan(commodityPrice));
                        // 商品优惠了的价格
                        cardList.getCardList().get(i).setFavorablePrice(NumberUtils
                                .RMBCentToYuan((Integer.parseInt(commodityAmount) - Integer.parseInt(commodityPrice))));
                    }
                }
            }
        } catch (Exception e) {
            logger.error("## 卡包   商户在售卡列表信息--->", e);
        }
        mv.addObject("merchantInfo", mchntInfo);
        mv.addObject("activeRule", activeRule);
        mv.addObject("shopInf", shopInf.getShopInfo());
        mv.addObject("sellingCardList", cardList);
        return mv;
    }

    /**
     * 卡包 购卡充值
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/buyCardRecharge")
    public ModelAndView buyCardRecharge(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("customer/card/buyCardRecharge");
        // 拦截器已经处理了缓存,这里直接取

        String openid = WxMemoryCacheClient.getOpenid(request);// 从缓存中获取openid
        if (StringUtil.isNullOrEmpty(openid)) {
            logger.error("## Request buyCardRecharge get openid[Null] failed");
            return super.error500(request);
        }
        /*** 用户是否已经注册薪无忧会员 **/
        UserInf user = userInfService.getUserInfByOpenId(openid);// TODO 从缓存中获取
        if (user == null) {
            mv = new ModelAndView("redirect:/customer/user/userRegister.html");
            return mv;
        }

        String mchntCode = request.getParameter("mchntCode");// 商户号
        String commodityCode = request.getParameter("commodityCode");// 商品CODE

        MchtSellingCardListQueryITFResp cardList = new MchtSellingCardListQueryITFResp(); // 商户在售卡列表

        MchtSellingCardListQueryITFVo cardCommodity = new MchtSellingCardListQueryITFVo();// 卡商品信息

        // 查询商户信息
        MerchantInfoQueryITFResp merchantInfo = new MerchantInfoQueryITFResp(); //
        try {
            MchntInfQueryRequest merchantInfoReq = new MchntInfQueryRequest();
            long timestamp = System.currentTimeMillis();// 时间戳

            merchantInfoReq.setInnerMerchantNo(mchntCode);
            merchantInfoReq.setTimestamp(timestamp);
            merchantInfoReq.setSign(SignUtil.genSign(merchantInfoReq));

            String merchantInfoStr = hkbTxnFacade.merchantInfoQueryITF(merchantInfoReq);
            merchantInfo = JSONArray.parseObject(merchantInfoStr, MerchantInfoQueryITFResp.class);

            MerchantInfoQueryITFVo mchntInfo = null;
            if (merchantInfo != null && BaseConstants.TXN_TRANS_RESP_SUCCESS.equals(merchantInfo.getCode())) {
                if (merchantInfo.getMerchantInfo() != null) {
                    mchntInfo = merchantInfo.getMerchantInfo();
                }
            }
            if (mchntInfo == null) {
                mchntInfo = new MerchantInfoQueryITFVo();
            }

            /*** 查询商品信息 **/
            BaseTxnReq baseTxnReq = new BaseTxnReq();
            baseTxnReq.setInnerMerchantNo(mchntCode);
            baseTxnReq.setTimestamp(timestamp);
            baseTxnReq.setSign(SignUtil.genSign(baseTxnReq));

            String jsonStr = hkbTxnFacade.mchtSellingCardListQueryITF(baseTxnReq);
            cardList = JSONArray.parseObject(jsonStr, MchtSellingCardListQueryITFResp.class);
            if (cardList != null && BaseConstants.TXN_TRANS_RESP_SUCCESS.equals(cardList.getCode())) {
                if (cardList.getCardList() != null && cardList.getCardList().size() > 0) {
                    for (int i = 0; i < cardList.getCardList().size(); i++) {
                        if (commodityCode.equals(cardList.getCardList().get(i).getCommodityCode())) {
                            cardCommodity = cardList.getCardList().get(i);
                            cardCommodity.setCommodityPrice(NumberUtils.RMBCentToYuan(cardCommodity.getCommodityPrice()));
                            break;
                        }
                    }
                }
                mv.addObject("productImage", cardList.getProductImage()); // 会员卡图片
            }
        } catch (Exception e) {
            logger.error("## 卡包	购卡充值 在售卡列表信息--->", e);
        }
        mv.addObject("merchantInfo", merchantInfo.getMerchantInfo());
        mv.addObject("cardCommodity", cardCommodity);
        return mv;
    }

    /**
     * 卡包 购卡充值 提交前检查是否开户开卡
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/buyCardRechargeCheck")
    public @ResponseBody
    Map<String, Object> buyCardRechargeCheck(HttpServletRequest request) {
        Map<String, Object> returnMap = new HashMap<String, Object>();

        returnMap.put("code", BaseConstants.TXN_TRANS_RESP_SUCCESS);
        returnMap.put("accountFlag", "0");
        returnMap.put("cardFlag", "0");

        String mchntCode = request.getParameter("mchntCode");
        String commodityCode = request.getParameter("commodityCode");

        String openid = WxMemoryCacheClient.getOpenid(request);
        MerchantInf merchantInf = merchantInfService.getMerchantInfByCode(mchntCode);

        try {
            /*** 查询商品信息 **/
            BaseTxnReq baseTxnReq = new BaseTxnReq();
            baseTxnReq.setInnerMerchantNo(mchntCode);
            baseTxnReq.setTimestamp(System.currentTimeMillis());
            baseTxnReq.setSign(SignUtil.genSign(baseTxnReq));

            String jsonStr = hkbTxnFacade.mchtSellingCardListQueryITF(baseTxnReq);
            MchtSellingCardListQueryITFResp cardList = JSONArray.parseObject(jsonStr, MchtSellingCardListQueryITFResp.class);

            MchtSellingCardListQueryITFVo cardCommodity = new MchtSellingCardListQueryITFVo();// 卡商品信息
            if (cardList != null && BaseConstants.TXN_TRANS_RESP_SUCCESS.equals(cardList.getCode())) {
                if (cardList.getCardList() != null && cardList.getCardList().size() > 0) {
                    for (int i = 0; i < cardList.getCardList().size(); i++) {
                        if (commodityCode.equals(cardList.getCardList().get(i).getCommodityCode())) {
                            cardCommodity = cardList.getCardList().get(i);
                            break;
                        }
                    }
                }
            }

            /*** 客户账户查询 ***/
            CusAccQueryRequest cusAccReq = new CusAccQueryRequest();
            cusAccReq.setUserId(openid);
            cusAccReq.setInnerMerchantNo(mchntCode);
            cusAccReq.setChannel(ChannelCode.CHANNEL1.toString());
            cusAccReq.setTimestamp(System.currentTimeMillis());
            cusAccReq.setSign(SignUtil.genSign(cusAccReq));

            String customerAccountStr = hkbTxnFacade.customerAccountQueryITF(cusAccReq); //
            CustomerAccountQueryITFResp customerAccount = JSONArray.parseObject(customerAccountStr, CustomerAccountQueryITFResp.class);
            if (customerAccount != null && BaseConstants.TXN_TRANS_RESP_SUCCESS.equals(customerAccount.getCode())) {
                String insCode = merchantInfService.getInsCodeByInsId(merchantInf.getInsId());// 机构号

                if ("0".equals(customerAccount.getAccountFlag())) {
                    returnMap.put("accountFlag", "0");
                    returnMap.put("cardFlag", "0");
                    return returnMap; // 没开户 直接返回
                }
                if ("0".equals(customerAccount.getCardFlag())) { // 开卡

                    TxnResp resp = userMerchantAcctService.doCustomerAccountOpening(cardCommodity.getProductCode(),
                            null, openid, mchntCode, insCode);
                    if (resp != null && BaseConstants.TXN_TRANS_RESP_SUCCESS.equals(resp.getCode())) {
                        returnMap.put("cardFlag", "1");
                        customerAccount.setCardFlag("1");
                    } else {
                        returnMap.put("code", BaseConstants.RESPONSE_EXCEPTION_CODE);
                    }
                }
                if ("1".equals(customerAccount.getAccountFlag()) && "1".equals(customerAccount.getCardFlag())) {
                    returnMap.put("accountFlag", customerAccount.getAccountFlag());
                    returnMap.put("cardFlag", customerAccount.getCardFlag());

                    CtrlSystem cs = ctrlSystemService.getCtrlSystem();// 得到日切信息
                    WxTransLog log = new WxTransLog();
                    if (cs != null) {
                        if (BaseConstants.TRANS_FLAG_YES.equals(cs.getTransFlag())) {// 日切状态为允许时，插入微信端流水
                            String wxPrimaryKey = wxTransLogService.getPrimaryKey();
                            log.setWxPrimaryKey(wxPrimaryKey);
                            log.setTableNum(cs.getCurLogNum());// 得到当前可以进行操作的流水表号
                            log.setSettleDate(cs.getSettleDate());// 交易日期
                            log.setTransId(TransCode.CW20.getCode());// 客户充值
                            log.setTransSt(0);// 插入时为0，报文返回时更新为1
                            log.setMchntCode(merchantInf.getMchntCode());
                            log.setInsCode(insCode);
                            log.setUserInfUserName(openid);
                            log.setOperatorOpenId(openid);
                            log.setProductCode(cardCommodity.getProductCode()); // 商品CODE
                            log.setTransAmt(cardCommodity.getCommodityAmount());// 卡面面额
                            log.setUploadAmt(cardCommodity.getCommodityPrice());// 上送金额
                            log.setTransCurrCd(BaseConstants.TRANS_CURR_CD);
                            log.setTransChnl(ChannelCode.CHANNEL2.toString());
                            log.setRemarks(cardCommodity.getCommodityCode()); // 商品号
                            int i = wxTransLogService.insertWxTransLog(log);// 插入业务流水(微信端)
                            if (i != 1) {
                                logger.info("购卡充值 微信交易--->insertIntfaceTransLog微信端插入流水记录数量不为1");
                                returnMap.put("code", BaseConstants.RESPONSE_EXCEPTION_CODE);
                            }
                            returnMap.put("wxPrimaryKey", wxPrimaryKey);
                        } else {
                            logger.info("购卡充值 微信交易--->日切信息交易允许状态：日切中");
                            returnMap.put("code", BaseConstants.RESPONSE_EXCEPTION_CODE);
                        }
                    }
                }
            } else {
                returnMap.put("code", BaseConstants.RESPONSE_EXCEPTION_CODE);
                return returnMap;
            }
        } catch (Exception e) {
            logger.error("## 卡包  购卡充值 提交前检查是否开户开卡-->", e);
            returnMap.put("code", BaseConstants.RESPONSE_EXCEPTION_CODE);
        }
        return returnMap;
    }

    /**
     * 进入薪无忧通卡账户
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/toMyHKBAccount")
    public ModelAndView toMyHKBAccount(HttpServletRequest request) {
        ModelAndView mv = new ModelAndView("customer/user/userAccHKBInfo");

        String openid = WxMemoryCacheClient.getOpenid(request);
        if (StringUtil.isNullOrEmpty(openid)) {
            logger.info("★★★★★Request toMyHKBAccount get openid is [Null]★★★★★");
            return super.error500(request);
        }
        PersonInf personInf = personInfService.getPersonInfByOpenId(openid);
        if (personInf == null) {
            mv = new ModelAndView("redirect:/customer/user/userRegister.html");
            return mv;
        }
        personInf.setMobilePhoneNo(NumberUtils.hiddingMobileNo(personInf.getMobilePhoneNo()));
        mv.addObject("personInf", personInf);

        AccountFans fans = accountFansService.getByOpenId(openid);
        if (fans == null) {
            logger.error("## 用户[{}]进入通卡账户失败：无法查找到粉丝信息", openid);
            mv = new ModelAndView("redirect:/common/500.html");
            return mv;
        }
        mv.addObject("fansInfo", fans);

        String productCode = jedisCluster.hget(RedisConstants.REDIS_HASH_TABLE_TB_BASE_DICT_KV, BaseConstants.ACC_HKB_PROD_NO);
        String mchntCode = jedisCluster.hget(RedisConstants.REDIS_HASH_TABLE_TB_BASE_DICT_KV, BaseConstants.ACC_HKB_MCHNT_NO);
        String insCode = jedisCluster.hget(RedisConstants.REDIS_HASH_TABLE_TB_BASE_DICT_KV, BaseConstants.ACC_HKB_INS_CODE);

        UserMerchantAcct uAcc = new UserMerchantAcct();
        uAcc.setExternalId(openid);
        uAcc.setUserId(personInf.getUserId());
        uAcc.setMchntCode(mchntCode);
        uAcc.setInsCode(insCode);
        uAcc.setProductCode(productCode);
        List<UserMerchantAcct> userAccList = userMerchantAcctService.getUserMerchantAcctByUser(uAcc);
        if (userAccList == null || userAccList.size() < 1) {
            logger.error("## 用户[{}]进入通卡账户失败：无法查找到通卡账户信息", openid);
            mv = new ModelAndView("redirect:/common/500.html");
            return mv;
        }
        mv.addObject("userAccInfo", userAccList.get(0));

        //工资余额
        String productCode2 = jedisCluster.hget(RedisConstants.REDIS_HASH_TABLE_TB_BASE_DICT_KV, BaseConstants.WAGES_XIN5YOU_PROD_NO);
        String mchntCode2 = jedisCluster.hget(RedisConstants.REDIS_HASH_TABLE_TB_BASE_DICT_KV, BaseConstants.WAGES_XIN5YOU_MCHNT_NO);
        String insCode2 = jedisCluster.hget(RedisConstants.REDIS_HASH_TABLE_TB_BASE_DICT_KV, BaseConstants.WAGES_XIN5YOU_INS_CODE);
        uAcc = new UserMerchantAcct();
        uAcc.setExternalId(openid);
        uAcc.setUserId(personInf.getUserId());
        uAcc.setMchntCode(mchntCode2);
        uAcc.setInsCode(insCode2);
        uAcc.setProductCode(productCode2);
        userAccList = userMerchantAcctService.getUserMerchantAcctByUser(uAcc);

        if (userAccList == null || userAccList.size() < 1) {
            logger.error("## 用户[{}]进入工资账户失败：无法查找到工资账户信息", openid);
            uAcc.setAccBal("0");
            mv.addObject("userWageInfo", uAcc);
            return mv;
        }
        mv.addObject("userWageInfo", userAccList.get(0));

        return mv;
    }

}
