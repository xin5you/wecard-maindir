package com.cn.thinkx.wecard.customer.module.welfaremart.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cn.thinkx.common.wecard.domain.base.ResultHtml;
import com.cn.thinkx.common.wecard.domain.cardkeys.*;
import com.cn.thinkx.common.wecard.domain.person.PersonInf;
import com.cn.thinkx.common.wecard.domain.phoneRecharge.PhoneRechargeShop;
import com.cn.thinkx.common.wecard.domain.user.UserMerchantAcct;
import com.cn.thinkx.pms.base.http.HttpClientUtil;
import com.cn.thinkx.pms.base.redis.util.ChannelSignUtil;
import com.cn.thinkx.pms.base.redis.util.RedisConstants;
import com.cn.thinkx.pms.base.redis.util.RedisDictProperties;
import com.cn.thinkx.pms.base.utils.*;
import com.cn.thinkx.pms.base.utils.BaseConstants.*;
import com.cn.thinkx.wecard.customer.core.util.MessageUtil;
import com.cn.thinkx.wecard.customer.module.checkstand.vo.TransOrderReq;
import com.cn.thinkx.wecard.customer.module.customer.service.PersonInfService;
import com.cn.thinkx.wecard.customer.module.customer.service.UserMerchantAcctService;
import com.cn.thinkx.wecard.customer.module.phonerecharge.vo.PhoneInfo;
import com.cn.thinkx.wecard.customer.module.welfaremart.service.*;
import com.cn.thinkx.wecard.customer.module.welfaremart.util.CardRechargeSignUtil;
import com.cn.thinkx.wecard.customer.module.welfaremart.vo.*;
import com.cn.thinkx.wechat.base.wxapi.process.WxMemoryCacheClient;
import com.cn.thinkx.wechat.base.wxapi.util.WxSignUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisCluster;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;

@Service("welfareMartService")
public class WelfareMartServiceImpl implements WelfareMartService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    @Qualifier("personInfService")
    private PersonInfService personInfService;

    @Autowired
    @Qualifier("cardKeysService")
    private CardKeysService cardKeysService;

    @Autowired
    @Qualifier("cardKeysTransLogService")
    private CardKeysTransLogService cardKeysTransLogService;

    @Autowired
    @Qualifier("cardKeysOrderInfService")
    private CardKeysOrderInfService cardKeysOrderInfService;

    @Autowired
    @Qualifier("userMerchantAcctService")
    private UserMerchantAcctService userMerchantAcctService;

    @Autowired
    @Qualifier("cardKeysProductService")
    private CardKeysProductService cardKeysProductService;

    @Autowired
    @Qualifier("userBankInfService")
    private UserBankInfService userBankInfService;

    @Autowired
    @Qualifier("jedisCluster")
    private JedisCluster jedisCluster;

//	@Autowired
//	private PhoneRechargeShopMapper phoneRechargeShopMapper;

    @Override
    public WelfareUserInfo toWelfareMartHome(String userId) throws Exception {
        String productCode = RedisDictProperties.getInstance().getdictValueByCode(BaseConstants.ACC_HKB_PROD_NO);
        String mchntCode = RedisDictProperties.getInstance().getdictValueByCode(BaseConstants.ACC_HKB_MCHNT_NO);
        String insCode = RedisDictProperties.getInstance().getdictValueByCode(BaseConstants.ACC_HKB_INS_CODE);
        // 账户信息
        UserMerchantAcct uAcc = new UserMerchantAcct();
        uAcc.setUserId(userId);
        uAcc.setMchntCode(mchntCode);
        uAcc.setInsCode(insCode);
        uAcc.setProductCode(productCode);
        List<UserMerchantAcct> userAccList = userMerchantAcctService.getUserMerchantAcctByUser(uAcc);
        if (userAccList == null || userAccList.size() < 1) {
            logger.error("## 卡券集市--->toWelfareMartHome：无法查找到通卡账户[{}]信息", userId);
            return null;
        }
        WelfareUserInfo userInfo = new WelfareUserInfo();
        // 用户持有的未核销的卡密数
        String cardNum = cardKeysService.getCardKeysCount(userId);
        userInfo.setCardNum(cardNum);
        userInfo.setUserId(userId);
        userInfo.setBalance(userAccList.get(0).getAccBal());
        userInfo.setMchntCode(mchntCode);
        return userInfo;
    }

    @Override
    public TransOrderReq buyCardCommit(HttpServletRequest request) throws Exception {
        String openid = WxMemoryCacheClient.getOpenid(request);
        if (StringUtil.isNullOrEmpty(openid)) {
            logger.error("★★★★★Request WelfareMart--->buyCardCommit get openid is [Null]★★★★★");
            return null;
        }

        PersonInf personInf = personInfService.getPersonInfByOpenId(openid);
        if (StringUtil.isNullOrEmpty(personInf)) {
            logger.error("★★★★★Request WelfareMart--->buyCardCommit get personInf is [Null]★★★★★");
            return null;
        }

        String num = request.getParameter("num");
        String productCode = request.getParameter("productCode");

        if (StringUtil.isNullOrEmpty(num) || StringUtil.isNullOrEmpty(productCode)) {
            logger.error("★★★★★Request WelfareMart--->buyCardCommit get num | productCode is [Null]★★★★★");
            return null;
        }

        CardKeysProduct product = new CardKeysProduct();
        product.setProductCode(productCode);
        product.setIsPutaway("0");
        product.setDataStat("0");
        CardKeysProduct ckp = cardKeysProductService.getCardKeysProductByCode(product);
        if (StringUtil.isNullOrEmpty(ckp)) {
            logger.error("## 卡券集市--->购买卡券接口，查询userID[{}]产品号[{}]信息为空", personInf.getUserId(), productCode);
            return null;
        }

        /** 新增卡密交易订单信息 */
        CardKeysOrderInf cko = new CardKeysOrderInf();
        cko.setOrderId(RandomUtils.getOrderIdByUUId("G"));
        cko.setUserId(personInf.getUserId());
        cko.setProductCode(productCode);
        int amt = NumberUtils.mul(Integer.parseInt(num), Integer.parseInt(ckp.getAmount()));
        cko.setAmount(String.valueOf(amt));
        cko.setType(orderType.O1.getCode());
        cko.setStat(orderStat.OS10.getCode());
        cko.setNum(num);
        cko.setPaidAmount("0");
        if (cardKeysOrderInfService.insertCardKeysOrderInf(cko) < 1) {
            logger.error("## 卡券集市--->购卡支付接口接口，为userID[{}]插入CardKeysOrderInf信息失败", personInf.getUserId());
            return null;
        }
        // 得到通卡商户门店等信息
        String mchntCode = RedisDictProperties.getInstance().getdictValueByCode(BaseConstants.ACC_HKB_MCHNT_NO);
        String shopCode = RedisDictProperties.getInstance().getdictValueByCode(BaseConstants.ACC_HKB_SHOP_NO);
        String notifyUrl = RedisDictProperties.getInstance().getdictValueByCode(BaseConstants.HKB_WELFAREMART_BUYCARD_NOTIFY_URL);
        String redirectUrl = RedisDictProperties.getInstance().getdictValueByCode(BaseConstants.HKB_WELFAREMART_REDIRECT_URL);
        // 设置薪无忧收银台所需参数
        TransOrderReq req = new TransOrderReq();
        req.setChannel(ChannelCode.CHANNEL9.toString());
        req.setUserId(personInf.getUserId());
        req.setOrderId(cko.getOrderId());
        req.setInnerMerchantNo(mchntCode);
        req.setInnerShopNo(shopCode);
        req.setCommodityName(ckp.getProductName());
        req.setCommodityNum(num);
        req.setTxnAmount(cko.getAmount());
        req.setNotify_url(notifyUrl);
        req.setRedirect_type("1");// 1：需要重定向，0：不需要重定向
        req.setRedirect_url(redirectUrl);
        req.setAttach(productCode);
        req.setSign(ChannelSignUtil.genSign(req));
        return req;
    }

    @Override
    public List<CardKeysProduct> welfareBugCardRedirect(HttpServletRequest request) throws Exception {
        OrderRedirect redirect = new OrderRedirect();
        redirect.setChannel(request.getParameter("channel"));
        redirect.setRespResult(request.getParameter("respResult"));
        redirect.setUserId(request.getParameter("userId"));
        redirect.setOrderId(request.getParameter("orderId"));
        redirect.setTxnFlowNo(request.getParameter("txnFlowNo"));
        redirect.setAttach(request.getParameter("attach"));
        redirect.setSign(request.getParameter("sign"));
        logger.info("卡券集市--->购卡重定向接口，请求参数[{}]", JSONArray.toJSONString(redirect));
        if (StringUtil.isNullOrEmpty(redirect.getChannel())) {
            logger.error("## 卡券集市--->购卡重定向接口，请求参数channel为空");
            return null;
        }
        if (StringUtil.isNullOrEmpty(redirect.getRespResult())) {
            logger.error("## 卡券集市--->购卡重定向接口，请求参数respResult为空");
            return null;
        }
        if (StringUtil.isNullOrEmpty(redirect.getUserId())) {
            logger.error("## 卡券集市--->购卡重定向接口，请求参数userId为空");
            return null;
        }
        if (StringUtil.isNullOrEmpty(redirect.getOrderId())) {
            logger.error("## 卡券集市--->购卡重定向接口，请求参数orderId为空");
            return null;
        }
        if (StringUtil.isNullOrEmpty(redirect.getTxnFlowNo())) {
            logger.error("## 卡券集市--->购卡重定向接口，请求参数txnFlowNo为空");
            return null;
        }
        if (StringUtil.isNullOrEmpty(redirect.getSign())) {
            logger.error("## 卡券集市--->购卡重定向接口，请求参数sign为空");
            return null;
        }

        String genSign = ChannelSignUtil.genSign(redirect);
        if (!genSign.equals(redirect.getSign())) {
            StringBuffer buf = new StringBuffer();
            SortedMap<String, String> map = WxSignUtil.genSortedMap(redirect);
            for (Map.Entry<String, String> item : map.entrySet()) {
                String key = item.getKey();
                String val = item.getValue();
                buf.append(key).append("=").append(val);
                buf.append("&");
            }
            logger.error("## 卡券集市--->购卡重定向接口，验签失败，userID[{}]卡券集市生成签名[{}]", redirect.getUserId(), genSign);
            return null;
        }

        PersonInf personInf = personInfService.getPersonInfByUserId(redirect.getUserId());
        if (StringUtil.isNullOrEmpty(personInf)) {
            logger.error("## 卡券集市--->购卡重定向接口，查询用户[{}]信息为空", personInf.getUserId());
            return null;
        }

        // 查询未转让的卡密
        List<CardKeys> cardKeys = cardKeysService.getCardKeysByAccountId(redirect.getUserId());
        if (cardKeys.size() <= 0) {
            logger.error("## 卡券集市--->购卡重定向接口，查询用户[{}]卡密信息为空", redirect.getUserId());
            return null;
        }
        // 根据未转让的卡密查询产品信息
        List<CardKeysProduct> phoneList = new ArrayList<CardKeysProduct>();
        for (CardKeys card : cardKeys) {
            CardKeysProduct ckp = new CardKeysProduct();
            ckp.setProductCode(card.getProductCode());
            ckp.setNum(card.getValidNum());
            CardKeysProduct ckpt = cardKeysProductService.getCardKeysProductByCard(ckp);
            if (ckpt != null) {
                ckpt.setAmount(NumberUtils.RMBCentToYuan(ckpt.getAmount()));
                ckpt.setOrgAmount(NumberUtils.RMBCentToYuan(ckpt.getOrgAmount()));
                ckpt.setMobile(NumberUtils.hiddingMobileNo(personInf.getMobilePhoneNo()));
                // 判断卡产品类型，目前只列出话费卡信息
                if (CardProductType.CP11.getCode().equals(ckpt.getProductType()))
                    phoneList.add(ckpt);
            }
        }
        for (CardKeysProduct phone : phoneList) {
            phone.setProductTypeCode(phone.getProductType());
            phone.setProductType(CardProductType.findByCode(phone.getProductType()).getValue());
        }

        return phoneList;
    }

    @Override
    public List<CardKeysProduct> cardBagList(HttpServletRequest request) throws Exception {
        String openid = WxMemoryCacheClient.getOpenid(request);
        if (StringUtil.isNullOrEmpty(openid)) {
            logger.error("★★★★★Request WelfareMart--->cardBagList get openid is [Null]★★★★★");
            return null;
        }

        PersonInf personInf = personInfService.getPersonInfByOpenId(openid);
        if (StringUtil.isNullOrEmpty(personInf)) {
            logger.error("★★★★★Request WelfareMart--->cardBagList get personInf is [Null]★★★★★");
            return null;
        }
        // 查询未转让的卡密
        List<CardKeys> cardKeys = cardKeysService.getCardKeysByAccountId(personInf.getUserId());
        if (cardKeys.size() <= 0) {
//			logger.info("卡券集市--->跳转卡券列表查询用户[{}]无可用卡券", personInf.getUserId());
            return null;
        }
        // 根据未转让的卡密查询产品信息
        List<CardKeysProduct> phoneList = new ArrayList<CardKeysProduct>();
        for (CardKeys card : cardKeys) {
            CardKeysProduct ckp = new CardKeysProduct();
            ckp.setProductCode(card.getProductCode());
            ckp.setNum(card.getValidNum());
            CardKeysProduct ckpt = cardKeysProductService.getCardKeysProductByCard(ckp);
            if (ckpt != null) {
                ckpt.setAmount(NumberUtils.RMBCentToYuan(ckpt.getAmount()));
                ckpt.setOrgAmount(NumberUtils.RMBCentToYuan(ckpt.getOrgAmount()));
                ckpt.setMobile(NumberUtils.hiddingMobileNo(personInf.getMobilePhoneNo()));
                // 判断卡产品类型，目前只列出话费卡信息
                if (CardProductType.CP11.getCode().equals(ckpt.getProductType()))
                    phoneList.add(ckpt);
            }
        }
        for (CardKeysProduct phone : phoneList) {
            phone.setProductTypeCode(phone.getProductType());
            phone.setProductType(CardProductType.findByCode(phone.getProductType()).getValue());
        }
        return phoneList;
    }

    @Override
    @Transactional
    public WelfaremartResellResp resellCommit(HttpServletRequest request, String userId) throws Exception {
        WelfaremartResellResp resp = new WelfaremartResellResp();
        resp.setCode("1");// 不需要弹框提示
        resp.setStatus(Boolean.FALSE);

        String resellNum = request.getParameter("resellNum");
        String productCode = request.getParameter("productCode");
        String bankNo = request.getParameter("bankNo");
        if (StringUtil.isNullOrEmpty(userId)) {
            logger.error("## 卡券集市--->转让接口，接收userId为空");
            resp.setMsg(MessageUtil.ERROR_MSSAGE);
            return resp;
        }
        if (StringUtil.isNullOrEmpty(productCode)) {
            logger.error("## 卡券集市--->转让接口，接收userID[{}]productCode为空", userId);
            resp.setMsg(MessageUtil.ERROR_MSSAGE);
            return resp;
        }
        if (StringUtil.isNullOrEmpty(bankNo)) {
            logger.error("## 卡券集市--->转让接口，接收userID[{}]银行卡号为空", userId);
            resp.setMsg(MessageUtil.RESELL_BANKNO_IS_NULL);
            return resp;
        }

        UserBankInf welfareUserInfo = bankNoValid(bankNo, userId);
        if (welfareUserInfo == null) {
            logger.error("## 卡券集市--->删除用户银行卡接口，未查到userID[{}]银行卡[{}]的相关信息", userId, bankNo);
            resp.setMsg(MessageUtil.ERROR_MSSAGE);
            return resp;
        } else {
            if (bankCheck.B0.getCode().equals(welfareUserInfo.getCheck())) {
                logger.error("## 卡券集市--->删除用户银行卡接口，userID[{}]银行卡号[{}]不存在，", userId, bankNo);
                resp.setMsg(MessageUtil.ERROR_MSSAGE);
                return resp;
            } else if (bankCheck.B2.getCode().equals(welfareUserInfo.getCheck())) {
                logger.error("## 卡券集市--->删除用户银行卡接口，userID[{}]银行卡[{}]不正确", userId, bankNo);
                resp.setMsg(MessageUtil.ERROR_MSSAGE);
                return resp;
            } else if (bankCheck.B3.getCode().equals(welfareUserInfo.getCheck())) {
                logger.error("## 卡券集市--->删除用户银行卡接口，userID[{}]银行卡[{}]类型为信用卡，", userId, bankNo);
                resp.setMsg(MessageUtil.BANKNO_TYPE_ERROR);
                return resp;
            }
        }

        if (StringUtil.isNullOrEmpty(resellNum) || Integer.parseInt(resellNum) == 0) {
            logger.error("## 卡券集市--->转让接口，接收userID[{}]resellNum为空", userId);
            resp.setMsg(MessageUtil.RESELL_NUM_IS_NULL);
            return resp;
        }
        // 转让请求接口地址
        String HKB_WELFAREMART_RESELL_URL = jedisCluster.hget(RedisConstants.REDIS_HASH_TABLE_TB_BASE_DICT_KV,
                BaseConstants.HKB_WELFAREMART_RESELL_URL);
        String WELFAREMART_RESELL_KEY = jedisCluster.hget(RedisConstants.REDIS_HASH_TABLE_TB_BASE_DICT_KV,
                BaseConstants.WELFAREMART_RESELL_KEY);

        WelfaremartResellReq req = new WelfaremartResellReq();
        req.setResellNum(resellNum);
        req.setProductCode(productCode);
        req.setBankNo(bankNo);
        req.setUserId(userId);
        String sign = SignUtil.genSign(req, WELFAREMART_RESELL_KEY);

        JSONObject paramData = new JSONObject();
        paramData.put("resellNum", resellNum);
        paramData.put("productCode", productCode);
        paramData.put("bankNo", bankNo);
        paramData.put("userId", userId);
        paramData.put("sign", sign);
        logger.info("卡券集市 用户[{}]转让提交请求链接[{}] 参数{}", userId, HKB_WELFAREMART_RESELL_URL, paramData.toString());
        String resultStr = HttpClientUtil.sendPost(HKB_WELFAREMART_RESELL_URL, paramData.toString());
        logger.info("卡券集市 用户[{}]转让提交返回{}", userId, resultStr);

        WelfaremartResellResp resellResp = null;
        if (!StringUtil.isNullOrEmpty(resultStr)) {
            resellResp = JSON.parseObject(resultStr, WelfaremartResellResp.class);
        } else {
            return resp;
        }

        return resellResp;
    }

    @Override
    public UserBankInf bankNoValid(String bankNo, String userId) throws Exception {
        UserBankInf userBankInf = new UserBankInf();
        String bankDetail = BankUtil.getCardDetail(bankNo);
        JSONObject bankJson = JSON.parseObject(bankDetail);
        if (!StringUtil.equals(bankJson.getString("validated"), "true")) {
            logger.error("## 卡券集市--->校验用户银行卡接口，userID[{}]银行卡号[{}]校验失败", userId, bankNo);
            userBankInf.setCheck(bankCheck.B2.getCode());
            return userBankInf;
        }
        if (bankJson.getString("cardType").equals("CC")) {
            logger.error("## 卡券集市--->校验用户银行卡接口，userID[{}]银行卡号[{}]类型为信用卡", userId, bankNo);
            userBankInf.setCheck(bankCheck.B3.getCode());
            return userBankInf;
        }


        // 查询用户银行卡号信息
        UserBankInf bankInf = userBankInfService.getUserBankInfByBankNo(bankNo);
        if (bankInf == null)
            userBankInf.setCheck("0");
        else
            userBankInf.setCheck("1");

        String bankName = BankUtil.getBankNameByCode(bankJson.getString("bank"));
        String type = bankType.findByCode(bankJson.getString("cardType")).getValue();
        userBankInf.setBankType(bankJson.getString("cardType"));
        userBankInf.setBankTypeName(type);
        userBankInf.setAccountBank(bankJson.getString("bank"));
        userBankInf.setBankName(bankName);
        return userBankInf;
    }

    @Override
    public ResultHtml addBankCommit(HttpServletRequest request) throws Exception {
        ResultHtml result = new ResultHtml();
        result.setStatus(Boolean.FALSE);

        String openid = WxMemoryCacheClient.getOpenid(request);
        if (StringUtil.isNullOrEmpty(openid)) {
            logger.error("★★★★★Request WelfareMart--->addBankCommit get openid is [Null]★★★★★");
            result.setMsg(MessageUtil.ERROR_MSSAGE);
            return result;
        }
        PersonInf personInf = personInfService.getPersonInfByOpenId(openid);
        if (StringUtil.isNullOrEmpty(personInf)) {
            logger.error("★★★★★Request WelfareMart--->addBankCommit get personInf is [Null]，openID[{}]★★★★★", openid);
            result.setMsg(MessageUtil.ERROR_MSSAGE);
            return result;
        }

        String bankNo = request.getParameter("bankNo");
        String accountBankAddr = request.getParameter("accountBankAddr");
        String accountBranch = request.getParameter("accountBranch");
        String isdefault = request.getParameter("isdefault");
        String check = request.getParameter("check");

        if (StringUtil.isNullOrEmpty(check)) {
            logger.error("## 卡券集市--->新增用户银行卡接口，获取userID[{}]check标志为空", personInf.getUserId());
            result.setMsg(MessageUtil.ERROR_MSSAGE);
            return result;
        }

        if (StringUtil.isNullOrEmpty(bankNo)) {
            logger.error("## 卡券集市--->新增用户银行卡接口，获取userID[{}]银行卡号为空", personInf.getUserId());
            result.setMsg(MessageUtil.ERROR_MSSAGE);
            return result;
        }

        if (StringUtil.isNullOrEmpty(accountBankAddr)) {
            logger.error("## 卡券集市--->新增用户银行卡接口，获取userID[{}]开户行地址为空", personInf.getUserId());
            result.setMsg(MessageUtil.ERROR_MSSAGE);
            return result;
        }

        UserBankInf welfareUserInfo = bankNoValid(bankNo, personInf.getUserId());
        if (StringUtil.isNullOrEmpty(welfareUserInfo)) {
            logger.error("## 卡券集市--->新增用户银行卡接口，未查到userID[{}]银行卡[{}]的相关信", personInf.getUserId(), bankNo);
            result.setMsg(MessageUtil.ERROR_MSSAGE);
            return result;
        } else {
            if (bankCheck.B1.getCode().equals(welfareUserInfo.getCheck())) {
                logger.error("## 卡券集市--->新增用户银行卡接口，userID[{}]银行卡[{}]已被绑定", personInf.getUserId(), bankNo);
                result.setMsg(MessageUtil.BANKNO_EXIST);
                return result;
            } else if (bankCheck.B2.getCode().equals(welfareUserInfo.getCheck())) {
                logger.error("## 卡券集市--->新增用户银行卡接口，userID[{}]银行卡[{}]不正确", personInf.getUserId(), bankNo);
                result.setMsg(MessageUtil.BANKNO_ERROR);
                return result;
            } else if (bankCheck.B3.getCode().equals(welfareUserInfo.getCheck())) {
                logger.error("## 卡券集市--->新增用户银行卡接口，userID[{}]银行卡[{}]类型为信用卡", personInf.getUserId(), bankNo);
                result.setMsg(MessageUtil.BANKNO_TYPE_ERROR);
                return result;
            }
        }

        UserBankInf userBankInf = new UserBankInf();
        userBankInf.setBankNo(bankNo);
        userBankInf.setUserId(personInf.getUserId());
        userBankInf.setUserName(personInf.getPersonalName());
        userBankInf.setBankType(welfareUserInfo.getBankType());
        userBankInf.setAccountBank(welfareUserInfo.getAccountBank());
        userBankInf.setAccountBranch(accountBranch);
        userBankInf.setAccountBankAddr(accountBankAddr);
        if (StringUtil.isNullOrEmpty(isdefault)) {
            userBankInf.setIsdefault("1");
        } else {
            if ("0".equals(isdefault)) {
                UserBankInf bankInf = userBankInfService.getIsDefaultByUserId(personInf.getUserId());
                if (bankInf != null) {
                    UserBankInf bank = new UserBankInf();
                    bank.setBankNo(bankInf.getBankNo());
                    bank.setIsdefault("1");
                    if (userBankInfService.updateUserBankInf(bank) < 1) {
                        logger.error("## 卡券集市--->新增用户银行卡接口，设置userID[{}]默认银行卡[{}]失败", personInf.getUserId(), bankInf.getBankNo());
                        result.setMsg(MessageUtil.ERROR_MSSAGE);
                        return result;
                    }
                }
            }
            userBankInf.setIsdefault(isdefault);
        }

        // 新增用户银行卡信息
        if (userBankInfService.insertUserBankInf(userBankInf) < 1) {
            logger.error("## 卡券集市--->新增用户银行卡接口，userID[{}]新增银行卡[{}]失败", personInf.getUserId(), bankNo);
            result.setMsg(MessageUtil.ERROR_MSSAGE);
            return result;
        }
        result.setCheck(check);
        result.setStatus(Boolean.TRUE);
        return result;
    }

    @Override
    public List<UserBankInf> bankCardList(HttpServletRequest request) throws Exception {
        String openid = WxMemoryCacheClient.getOpenid(request);
        if (StringUtil.isNullOrEmpty(openid)) {
            logger.error("## 银行卡管理--->银行卡列表接口，获取openid为空");
            return null;
        }

        PersonInf personInf = personInfService.getPersonInfByOpenId(openid);
        if (StringUtil.isNullOrEmpty(personInf)) {
            logger.error("## 银行卡管理--->银行卡列表接口，查询用户openid[{}]信息不存在", openid);
            return null;
        }

        // 查询用户银行卡信息
        List<UserBankInf> userBankList = userBankInfService.getUserBankInfByUserId(personInf.getUserId());
        for (UserBankInf u : userBankList) {
            u.setLogo(BankUtil.getBankLogoByCode(u.getAccountBank()));
            u.setBankTypeName(bankType.findByCode(u.getBankType()).getValue());
            // 显示银行卡后四位
            u.setBankNum(u.getBankNo().substring(u.getBankNo().length() - 4, u.getBankNo().length()));
        }
        return userBankList;
    }

    @Override
    public boolean deleteBankCard(HttpServletRequest request) throws Exception {
        String openid = WxMemoryCacheClient.getOpenid(request);
        if (StringUtil.isNullOrEmpty(openid)) {
            logger.error("★★★★★Request WelfareMart--->deleteBankCard get openid is [Null]★★★★★");
            return false;
        }
        PersonInf personInf = personInfService.getPersonInfByOpenId(openid);
        if (StringUtil.isNullOrEmpty(personInf)) {
            logger.error("★★★★★Request WelfareMart--->deleteBankCard get personInf is [Null]，openID[{}]★★★★★", openid);
            return false;
        }

        String bankNo = request.getParameter("bankNo");
        if (StringUtil.isNullOrEmpty(bankNo)) {
            logger.error("## 银行卡管理--->删除用户银行卡接口，获取userID[{}]银行卡号为空", personInf.getUserId());
            return false;
        }

        UserBankInf welfareUserInfo = bankNoValid(bankNo, personInf.getUserId());
        if (welfareUserInfo == null) {
            logger.error("## 卡券集市--->删除用户银行卡接口，未查到userID[{}]银行卡[{}]的相关信息", personInf.getUserId(), bankNo);
            return false;
        } else {
            if (bankCheck.B0.getCode().equals(welfareUserInfo.getCheck())) {
                logger.error("## 卡券集市--->删除用户银行卡接口，userID[{}]银行卡[{}]不存在", personInf.getUserId(), bankNo);
                return false;
            } else if (bankCheck.B2.getCode().equals(welfareUserInfo.getCheck())) {
                logger.error("## 卡券集市--->删除用户银行卡接口，userID[{}]银行卡[{}]不正确", personInf.getUserId(), bankNo);
                return false;
            } else if (bankCheck.B3.getCode().equals(welfareUserInfo.getCheck())) {
                logger.error("## 卡券集市--->删除用户银行卡接口，userID[{}]银行卡[{}]类型为信用卡", personInf.getUserId(), bankNo);
                return false;
            }
        }

        if (userBankInfService.deleteUserBankInf(bankNo) < 0) {
            logger.error("## 银行卡管理--->删除用户银行卡接口，删除userID[{}]银行卡号[{}]信息失败", personInf.getUserId(), bankNo);
            return false;
        }

        return true;
    }


    @Override
    public List<CardKeysOrderInf> cardBagTransDetails(String userId) throws Exception {
        List<CardKeysOrderInf> orderList = cardKeysOrderInfService.getOrderInfListByUserId(userId);
        if (orderList != null && orderList.size() >= 1) {
            for (CardKeysOrderInf order : orderList) {
                if (order.getProductType().equals(CardProductType.CP11.getCode())) {
                    order.setProductType("话费卡");
                } else {
                    order.setProductType(CardProductType.findByCode(order.getProductType()).getValue());
                }
                if (orderStat.OS23.getCode().equals(order.getStat())) {
                    order.setStat("充值失败");
                } else if (orderStat.OS24.getCode().equals(order.getStat())) {
                    order.setStat("充值失败");
                } else if (orderStat.OS32.getCode().equals(order.getStat())) {
                    order.setStat("转让成功");
                } else if (orderStat.OS34.getCode().equals(order.getStat())) {
                    order.setStat("转让失败");
                } else if (orderStat.OS35.getCode().equals(order.getStat())) {
                    order.setStat("转让失败");
                } else {
                    order.setStat(orderStat.findByCode(order.getStat()).getValue());
                }
            }
        }
        return orderList;
    }

    @Override
    public boolean isDefaultBankCard(HttpServletRequest request) throws Exception {
        String openid = WxMemoryCacheClient.getOpenid(request);
        if (StringUtil.isNullOrEmpty(openid)) {
            logger.info("★★★★★Request WelfareMart--->isDefaultBankCard get openid is [Null]★★★★★");
            return false;
        }
        PersonInf personInf = personInfService.getPersonInfByOpenId(openid);
        if (StringUtil.isNullOrEmpty(personInf)) {
            logger.info("★★★★★Request WelfareMart--->isDefaultBankCard get personInf is [Null]，openID[{}]★★★★★", openid);
            return false;
        }

        String bankNo = request.getParameter("bankNo");
        if (StringUtil.isNullOrEmpty(bankNo)) {
            logger.error("## 卡券集市--->设置用户银行卡默认接口，获取userID[{}]银行卡号为空", personInf.getUserId());
            return false;
        }

        UserBankInf welfareUserInfo = bankNoValid(bankNo, personInf.getUserId());
        if (welfareUserInfo == null) {
            logger.error("## 卡券集市--->设置用户银行卡默认接口，未查到userID[{}]银行卡[{}]的相关信息", personInf.getUserId(), bankNo);
            return false;
        } else {
            if (bankCheck.B0.getCode().equals(welfareUserInfo.getCheck())) {
                logger.error("## 卡券集市--->设置用户银行卡默认接口，userID[{}]银行卡[{}]不存在", personInf.getUserId(), bankNo);
                return false;
            } else if (bankCheck.B2.getCode().equals(welfareUserInfo.getCheck())) {
                logger.error("## 卡券集市--->设置用户银行卡默认接口，userID[{}]银行卡[{}]不正确", personInf.getUserId(), bankNo);
                return false;
            } else if (bankCheck.B3.getCode().equals(welfareUserInfo.getCheck())) {
                logger.error("## 卡券集市--->设置用户银行卡默认接口，userID[{}]银行卡[{}]类型为信用卡", personInf.getUserId(), bankNo);
                return false;
            }
        }
        UserBankInf userBank = userBankInfService.getIsDefaultByUserId(personInf.getUserId());
        if (userBank != null) {
            userBank.setIsdefault("1");
            if (userBankInfService.updateUserBankInf(userBank) < 1) {
                logger.error("## 卡券集市--->设置用户银行卡默认接口，撤销userID[{}]银行卡[{}]信息默认状态异常", personInf.getUserId(), userBank.getBankNo());
                return false;
            }
        }
        UserBankInf bankInf = new UserBankInf();
        bankInf.setBankNo(bankNo);
        bankInf.setIsdefault("0");
        if (userBankInfService.updateUserBankInf(bankInf) < 1) {
            logger.error("## 卡券集市--->设置用户银行卡默认接口，设置userID[{}]银行卡[{}]信息为默认状态异常", personInf.getUserId(), bankInf.getBankNo());
            return false;
        }
        return true;
    }

    @Override
    public CardRechargeResp cardRecharge(HttpServletRequest request) throws Exception {
        String openid = WxMemoryCacheClient.getOpenid(request);
        if (StringUtil.isNullOrEmpty(openid)) {
            logger.error("★★★★★Request WelfareMart--->cardRecharge get openid is [Null]★★★★★");
            return null;
        }
        PersonInf personInf = personInfService.getPersonInfByOpenId(openid);
        if (StringUtil.isNullOrEmpty(personInf)) {
            logger.error("★★★★★Request WelfareMart--->cardRecharge get personInf is [Null]，openID[{}]★★★★★", openid);
            return null;
        }

        String productCode = request.getParameter("productCode");
        if (StringUtil.isNullOrEmpty(productCode)) {
            logger.error("## 卡券集市--->充值接口，接收userID[{}]卡产品号为空", personInf.getUserId());
            return null;
        }

        CardKeysProduct ckp = new CardKeysProduct();
        ckp.setProductCode(productCode);
        CardKeysProduct product = cardKeysProductService.getCardKeysProductByCode(ckp);

        //查询手机所属运营商
        JSONObject paramData = new JSONObject();
        paramData.put("phone", personInf.getMobilePhoneNo());
        String url = jedisCluster.hget(RedisConstants.REDIS_HASH_TABLE_TB_BASE_DICT_KV, BaseConstants.GET_PHONE_INFO_URL);

        String result = HttpClientUtil.sendPost(url, paramData.toString());
        PhoneInfo phoneInfo = JSONObject.parseObject(result, PhoneInfo.class);
        if (phoneInfo == null) {
            logger.error("## 卡券集市--->充值接口，查询userID[{}]手机号[{}]所属运营商信息为空", personInf.getUserId(), personInf.getMobilePhoneNo());
            return null;
        }
		
		/*String shopFace = String.valueOf(new Double(NumberUtils.RMBCentToYuan(product.getOrgAmount())).intValue());
		
		PhoneRechargeShop phoneRechargeShop = new PhoneRechargeShop();
		phoneRechargeShop.setOper(String.valueOf(phoneType));
		phoneRechargeShop.setShopFace(shopFace);
		phoneRechargeShop.setIsUsable(IsUsableType.IsUsableType1.getCode());
		
		if (CardProductType.CP11.getCode().equals(product.getProductType())) {//话费调用立方
			phoneRechargeShop.setSupplier(phoneRechargeSupplier.PRS1.getCode());
			phoneRechargeShop.setShopType(ShopType.ShopType1.getCode());
			phoneRechargeShop.setResv1(ShopUnitType.ShopUnitType01.getCode());
		} else if (CardProductType.CP12.getCode().equals(product.getProductType())) {//流量调用鼎驰
//			phoneRechargeShop.setSupplier(phoneRechargeSupplier.PRS2.getCode());
//			phoneRechargeShop.setShopType(ShopType.ShopType2.getCode());
		}*/
//		PhoneRechargeShop rechargeShop = phoneRechargeShopMapper.getPhoneRechargeShopByRechargeShop(phoneRechargeShop);

        List<PhoneRechargeShop> rechargeShopList = new ArrayList<PhoneRechargeShop>();
        String phoneRechargeShopArray = null;
        if (OperatorType.OperatorType1.getValue().equals(phoneInfo.getOperator())) {
            phoneRechargeShopArray = jedisCluster.get(BaseConstants.PHONE_RECHARGE_YD_GOODS);
        } else if (OperatorType.OperatorType2.getValue().equals(phoneInfo.getOperator())) {
            phoneRechargeShopArray = jedisCluster.get(BaseConstants.PHONE_RECHARGE_LT_GOODS);
        } else if (OperatorType.OperatorType3.getValue().equals(phoneInfo.getOperator())) {
            phoneRechargeShopArray = jedisCluster.get(BaseConstants.PHONE_RECHARGE_DX_GOODS);
        } else {
            logger.error("## 卡券集市--->充值接口，查询userID[{}]手机号[{}]所属运营商不存在", personInf.getUserId(), personInf.getMobilePhoneNo());
            return null;
        }

        if (!StringUtil.isNullOrEmpty(phoneRechargeShopArray)) {
            rechargeShopList = JSONObject.parseArray(phoneRechargeShopArray, PhoneRechargeShop.class);
        }

        CardRechargeResp resp = new CardRechargeResp();
        resp.setCode(BaseConstants.RESPONSE_EXCEPTION_CODE);
        resp.setMsg(BaseConstants.RESPONSE_EXCEPTION_INFO);
        if (rechargeShopList.size() < 1) {
            logger.error("## 卡券集市--->充值接口，查询userID[{}]充值面额[{}]不存在", personInf.getUserId(), NumberUtils.RMBCentToYuan(product.getOrgAmount()) + product.getProductUnit());
            resp.setMsg("该卡券暂不支持充值功能");
            return resp;
        }

        String shopFace = String.valueOf(new Double(NumberUtils.RMBCentToYuan(product.getOrgAmount())).intValue());
        for (PhoneRechargeShop prs : rechargeShopList) {
            if (prs.getShopFace().equals(shopFace) && prs.getIsUsable().equals(IsUsableType.IsUsableType1.getCode())) {
                if (CardProductType.CP11.getCode().equals(product.getProductType())) {//话费调用立方
                    if (prs.getSupplier().equals(phoneRechargeSupplier.PRS1.getCode()) &&
                            prs.getShopType().equals(ShopType.ShopType1.getCode()) &&
                            prs.getResv1().equals(ShopUnitType.ShopUnitType01.getCode()) &&
                            prs.getDataStat().equals("0")) {
                        resp.setCode(BaseConstants.RESPONSE_SUCCESS_CODE);
                        resp.setMsg(BaseConstants.RESPONSE_SUCCESS_INFO);
                        break;
                    } else {
                        logger.error("## 卡券集市--->充值接口，查询userID[{}]话费充值面额[{}]信息为空", personInf.getUserId(), shopFace + product.getProductUnit());
                        resp.setMsg("该卡券暂不支持充值功能");
                    }
                }
				/*else if (CardProductType.CP12.getCode().equals(product.getProductType())) {//流量调用鼎驰
					if (prs.getSupplier().equals(phoneRechargeSupplier.PRS2.getCode()) && 
							prs.getShopType().equals(ShopType.ShopType2.getCode()) &&
							prs.getResv1().equals(ShopUnitType.ShopUnitType02.getCode())) {
							
					} else {
						
					}
				}*/
            } else {
                logger.error("## 卡券集市--->充值接口，查询userID[{}]话费充值面额[{}]暂不支持充值功能", personInf.getUserId(), shopFace + product.getProductUnit());
                resp.setMsg("该卡券暂不支持充值功能");
            }
        }
        return resp;
    }

    @Override
    public boolean cardRechargeCommit(String productCode, PersonInf personInf) throws Exception {
        boolean flag = false;
        if (StringUtil.isNullOrEmpty(productCode)) {
            logger.error("## 卡券集市--->充值接口，获取userID[{}]productCode为空", personInf.getUserId());
            return flag;
        }
        CardKeysProduct product = new CardKeysProduct();
        product.setProductCode(productCode);
        CardKeysProduct ckp = cardKeysProductService.getCardKeysProductByCode(product);
        if (ckp == null) {
            logger.error("## 卡券集市--->充值接口，查询userID[{}]充值的卡产品[{}]信息为空", personInf.getUserId(), productCode);
            return flag;
        }
        CardKeys cardKey = new CardKeys();
        cardKey.setProductCode(productCode);
        cardKey.setAccountId(personInf.getUserId());
        int cardNum = cardKeysService.getLoseNumByAccountId(cardKey);
        if (cardNum == 0) {
            logger.error("## 卡券集市--->充值接口，userID[{}]无可充值卡密", personInf.getUserId());
            return flag;
        }

        //查询手机所属运营商
        JSONObject paramData = new JSONObject();
        paramData.put("phone", personInf.getMobilePhoneNo());
        String url = jedisCluster.hget(RedisConstants.REDIS_HASH_TABLE_TB_BASE_DICT_KV, BaseConstants.GET_PHONE_INFO_URL);

        String result = HttpClientUtil.sendPost(url, paramData.toString());
        PhoneInfo phoneInfo = JSONObject.parseObject(result, PhoneInfo.class);
        if (phoneInfo == null) {
            logger.error("## 卡券集市--->充值接口，查询userID[{}]手机号[{}]所属运营商信息为空", personInf.getUserId(), personInf.getMobilePhoneNo());
            return flag;
        }
		
		/*String shopFace = String.valueOf(new Double(NumberUtils.RMBCentToYuan(ckp.getOrgAmount())).intValue());
		PhoneRechargeShop phoneRechargeShop = new PhoneRechargeShop();
		phoneRechargeShop.setOper(String.valueOf(phoneType));
		phoneRechargeShop.setShopFace(shopFace);
		phoneRechargeShop.setIsUsable(IsUsableType.IsUsableType1.getCode());
		
		if (CardProductType.CP11.getCode().equals(product.getProductType())) {//话费调用立方
			phoneRechargeShop.setSupplier(phoneRechargeSupplier.PRS1.getCode());
			phoneRechargeShop.setShopType(ShopType.ShopType1.getCode());
			phoneRechargeShop.setResv1(ShopUnitType.ShopUnitType01.getCode());
		} else if (CardProductType.CP12.getCode().equals(product.getProductType())) {//流量调用鼎驰
//			phoneRechargeShop.setSupplier(phoneRechargeSupplier.PRS2.getCode());
//			phoneRechargeShop.setShopType(ShopType.ShopType2.getCode());
		}
		PhoneRechargeShop rechargeShop = phoneRechargeShopMapper.getPhoneRechargeShopByRechargeShop(phoneRechargeShop);
		if (rechargeShop == null) {
			logger.error("## 卡券集市--->充值接口，根据[{}]查询userID[{}]话费充值信息为空", JSONArray.toJSONString(phoneRechargeShop), personInf.getUserId());
			return flag;
		}*/

        List<PhoneRechargeShop> rechargeShopList = new ArrayList<PhoneRechargeShop>();
        String phoneRechargeShopArray = null;
        if (OperatorType.OperatorType1.getValue().equals(phoneInfo.getOperator())) {
            phoneRechargeShopArray = jedisCluster.get(BaseConstants.PHONE_RECHARGE_YD_GOODS);
        } else if (OperatorType.OperatorType2.getValue().equals(phoneInfo.getOperator())) {
            phoneRechargeShopArray = jedisCluster.get(BaseConstants.PHONE_RECHARGE_LT_GOODS);
        } else if (OperatorType.OperatorType3.getValue().equals(phoneInfo.getOperator())) {
            phoneRechargeShopArray = jedisCluster.get(BaseConstants.PHONE_RECHARGE_DX_GOODS);
        } else {
            logger.error("## 卡券集市--->充值接口，查询userID[{}]手机号[{}]所属运营商不存在", personInf.getUserId(), personInf.getMobilePhoneNo());
            return flag;
        }

        if (!StringUtil.isNullOrEmpty(phoneRechargeShopArray)) {
            rechargeShopList = JSONObject.parseArray(phoneRechargeShopArray, PhoneRechargeShop.class);
        }

        if (rechargeShopList.size() < 1) {
            logger.error("## 卡券集市--->充值接口，查询userID[{}]充值面额[{}]不存在", personInf.getUserId(), NumberUtils.RMBCentToYuan(ckp.getOrgAmount()) + ckp.getProductUnit());
            return flag;
        }

        String shopFace = String.valueOf(new Double(NumberUtils.RMBCentToYuan(ckp.getOrgAmount())).intValue());
        String shopPrice = null;
        for (PhoneRechargeShop prs : rechargeShopList) {
            if (prs.getShopFace().equals(shopFace) && prs.getIsUsable().equals(IsUsableType.IsUsableType1.getCode())) {
                if (CardProductType.CP11.getCode().equals(ckp.getProductType())) {//话费调用立方
                    if (prs.getSupplier().equals(phoneRechargeSupplier.PRS1.getCode()) &&
                            prs.getShopType().equals(ShopType.ShopType1.getCode()) &&
                            prs.getResv1().equals(ShopUnitType.ShopUnitType01.getCode())) {
                        shopPrice = prs.getShopPrice();
                        break;
                    } else {
                        logger.error("## 卡券集市--->充值接口，查询userID[{}]话费充值面额[{}]信息为空", personInf.getUserId(), shopFace + ckp.getProductUnit());
                        return flag;
                    }
                } else if (CardProductType.CP12.getCode().equals(ckp.getProductType())) {//流量调用鼎驰
					/*if (prs.getSupplier().equals(phoneRechargeSupplier.PRS2.getCode()) && 
							prs.getShopType().equals(ShopType.ShopType2.getCode()) &&
							prs.getResv1().equals(ShopUnitType.ShopUnitType02.getCode())) {
							
					} else {
						
					}*/
                }
            }
        }

        //设置卡密充值交易订单信息
        CardKeysOrderInf cko = new CardKeysOrderInf();
        cko.setOrderId(RandomUtils.getOrderIdByUUId("C"));
        cko.setUserId(personInf.getUserId());
        cko.setProductCode(productCode);
        cko.setAmount(ckp.getOrgAmount());
        cko.setPaidAmount("0");
        cko.setType(orderType.O2.getCode());
        cko.setStat(orderStat.OS23.getCode());
        cko.setNum("1");

        //设置卡密充值流水信息 及 和 待核销卡密信息
        CardKeys cardKeys = new CardKeys();
        cardKeys.setAccountId(personInf.getUserId());
        cardKeys.setProductCode(productCode);
        cardKeys.setValidNum("1");
        List<CardKeys> cardKeysList = cardKeysService.getCardKeysList(cardKeys);
        List<CardKeysTransLog> cktList = new ArrayList<CardKeysTransLog>();
        for (CardKeys card : cardKeysList) {
            CardKeysTransLog ckt = new CardKeysTransLog();
            ckt.setCardKey(card.getCardKey());
            ckt.setOrderId(cko.getOrderId());
            ckt.setTransId(TransType.W20.getCode());
            ckt.setProductCode(productCode);
            ckt.setTransAmt(ckp.getOrgAmount());
            ckt.setOrgTransAmt(ckp.getOrgAmount());
            cktList.add(ckt);
        }

        //新增卡密充值交易流水信息
        if (!cardKeysOrderInfService.insertCardRechargeOrder(cko, cktList)) {
            logger.error("## 卡券集市--->充值接口，新增userID[{}]卡密充值交易流水及核销卡密信息失败", personInf.getUserId());
            return flag;
        }

        String WELFAREMART_RECHARGE_REQUEST_URL = jedisCluster.hget(RedisConstants.REDIS_HASH_TABLE_TB_BASE_DICT_KV,
                BaseConstants.WELFAREMART_RECHARGE_REQUEST_URL);
        String WELFAREMART_RECHARGE_NOTIFY_URL = jedisCluster.hget(RedisConstants.REDIS_HASH_TABLE_TB_BASE_DICT_KV,
                BaseConstants.WELFAREMART_RECHARGE_NOTIFY_URL);
        String accessToken = jedisCluster.hget(RedisConstants.REDIS_HASH_TABLE_TB_BASE_DICT_KV,
                BaseConstants.BM_ACCESS_TOKEN);
        String signKey = jedisCluster.hget(RedisConstants.REDIS_HASH_TABLE_TB_BASE_DICT_KV, phoneRechargeReqChnl.PRRC2.getCode() + "_SIGN_KEY");

        CardRechargeReq cardOrder = new CardRechargeReq();
        cardOrder.setChannelOrderNo(cko.getOrderId());
        cardOrder.setUserId(personInf.getUserId());
        cardOrder.setPhone(personInf.getMobilePhoneNo());
        cardOrder.setTelephoneFace(shopFace);
        cardOrder.setTransAmt(shopPrice);
        cardOrder.setOrderType(phoneRechargeOrderType.PROT1.getCode());
        cardOrder.setReqChannel(phoneRechargeReqChnl.PRRC2.getCode());
        cardOrder.setCallBack(WELFAREMART_RECHARGE_NOTIFY_URL);
        cardOrder.setTimestamp(System.currentTimeMillis());
        cardOrder.setAccessToken(accessToken);
        String sign = CardRechargeSignUtil.genSign(cardOrder, signKey);
        cardOrder.setSign(sign);

        logger.info("卡券集市--->充值接口，提交用户[{}]请求链接[{}]参数{}", personInf.getUserId(), WELFAREMART_RECHARGE_REQUEST_URL, JSONArray.toJSONString(cardOrder));
        String resultStr = null;
        try {
            //http://192.168.1.112:8989/ecom-front/hkb/recharge/phoneRecharge
            resultStr = HttpClientUtil.sendPost(WELFAREMART_RECHARGE_REQUEST_URL, JSONArray.toJSONString(cardOrder));
        } catch (Exception e) {
            logger.error("## 卡券集市--->充值接口，userID[{}]话费充值异常{}", personInf.getUserId(), e);
        }
        logger.info("卡券集市--->充值接口，用户[{}]充值返回参数{}", personInf.getUserId(), resultStr);
        CardRechargeResp respResult = JSON.parseObject(resultStr, CardRechargeResp.class);
        if (respResult == null) {
            logger.error("卡券集市--->充值接口，用户[{}]充值返回参数为空", personInf.getUserId());
            cko.setStat(orderStat.OS21.getCode());
        } else {
            if ("00".equals(respResult.getCode())) {// 充值受理中
                cko.setStat(orderStat.OS20.getCode());
            } else {//充值失败
                cko.setStat(orderStat.OS21.getCode());
            }
        }
        if (cardKeysOrderInfService.updateCardKeysOrderInf(cko) < 1) {
            logger.error("## 卡券集市--->充值接口，更新userID[{}]CardKeysOrderInf状态[{}]失败", personInf.getUserId(), cko.getStat());
            return flag;
        }
        if (orderStat.OS21.getCode().equals(cko.getStat()))
            return flag;

        return true;
    }

}
