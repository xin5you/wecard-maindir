package com.cn.thinkx.pay.service;

import com.alibaba.fastjson.JSONObject;
import com.cn.thinkx.pay.core.DES3;
import com.cn.thinkx.pay.core.KeyUtils;
import com.cn.thinkx.pay.domain.UnifyPayForAnotherVO;
import com.cn.thinkx.pay.domain.UnifyQueryVO;
import com.cn.thinkx.pms.base.http.HttpClientUtil;
import com.cn.thinkx.pms.base.redis.util.RedisDictProperties;
import com.cn.thinkx.pms.base.utils.MD5Util;
import com.cn.thinkx.pms.base.utils.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Objects;

/**
 * 中付统一代付服务接口
 */
public class ZFPaymentServer {
    private static Logger logger = LoggerFactory.getLogger(ZFPaymentServer.class);

    private static final String STR32 = "00000000000000000000000000000000";

    /**
     * 代付签到接口
     *
     * @param merchantNo 商户号
     * @return 签到返回码
     */
    public static JSONObject getPayForAnotherSessionId(String merchantNo, String md5) {
        String res = "";
        try {
            JSONObject params = new JSONObject();
            params.put("service", "p4aSignIn");
            params.put("merchantNo", merchantNo);
            params.put("MD5", Objects.requireNonNull(MD5Util.md5(merchantNo + md5)).toUpperCase());
            res = HttpClientUtil.sendPostReturnStr(RedisDictProperties.getInstance().getdictValueByCode(KeyUtils.ZHONGFU_PAY_URL), params.toJSONString());
            logger.info("返回的签到数据：{}", res);
        } catch (Exception ex) {
            logger.error("##代付签到接口执行异常{}", ex);
        }
        return JSONObject.parseObject(res);
    }

    /**
     * 代付接口
     *
     * @param un 代付请求支付VO
     * @return 代付返回JSON
     */
    public static JSONObject doPayForAnotherPay(UnifyPayForAnotherVO un) {
        KeyUtils k = new KeyUtils();
        String res = "";
        try {
            //1,先获取rsa私钥
            String privateKey = k.getRSAKey();

/*            //2,获取所需密钥
            //md5,aes
            Map<String, String> map = k.getKeys(privateKey);
            String md5 = map.get("MD5");*/

            String md5 = RedisDictProperties.getInstance().getdictValueByCode(KeyUtils.ZHONGFU_SIGN_MD5);
            if (StringUtil.isNullOrEmpty(md5)) {
                Map<String, String> map = k.getKeys(privateKey);
                md5 = map.get("MD5");
            }
            //zek
            Map<String, String> map2 = k.getZek(privateKey);
            String zek = map2.get("ZEK");

            JSONObject msg = new JSONObject();

            msg.put("merchantNo", RedisDictProperties.getInstance().getdictValueByCode(KeyUtils.ZHONGFU_PAY_USER_KEY));
            msg.put("service", "p4aPay");
            msg.put("payMoney", DES3.encrypt3DES((STR32.substring(0, 32 - un.getPayMoney().length()) + un.getPayMoney()), zek));
            msg.put("orderId", un.getOrderId());
            msg.put("bankCard", DES3.encrypt3DES((STR32.substring(0, 32 - un.getBankCard().length()) + un.getBankCard()), zek));
            msg.put("name", un.getName());
            msg.put("bankName", un.getBankName());
            msg.put("acctType", un.getAcctType());
            msg.put("cnaps", un.getCnaps());
            msg.put("province", un.getProvince());
            msg.put("city", un.getCity());
            msg.put("certType", un.getCertType());
            msg.put("certNumber", DES3.encrypt3DES((STR32.substring(0, 32 - un.getCertNumber().length()) + un.getCertNumber()), zek));
            msg.put("sessionId", un.getSessionId());
            msg.put("payKey", un.getPayKey());

            //清算号
            if (StringUtil.isNotEmpty(un.getQsBankCode())) {
                msg.put("qsBankCode", un.getQsBankCode());
            }
            //手机号
            if (StringUtil.isNotEmpty(un.getMobile())) {
                msg.put("mobile", un.getMobile());
            }
            //回调地址
            if (StringUtil.isNotEmpty(un.getMerchantURL())) {
                msg.put("merchantURL", un.getMerchantURL());
            }

            //orderId+merchantNo+payMoney+bankCard+key(MD5校验,参数均为明文进行加密)
            //MD5密钥参见3.2.3获取MD5密钥接口
            msg.put("MD5", Objects.requireNonNull(MD5Util.md5(msg.getString("orderId") + msg.getString("merchantNo") + un.getPayMoney() + un.getBankCard() + md5)).toUpperCase());

            logger.info("##代付查询request params {}", msg.toJSONString());

            res = HttpClientUtil.sendPostReturnStr(RedisDictProperties.getInstance().getdictValueByCode(KeyUtils.ZHONGFU_PAY_URL), msg.toJSONString());

            logger.info("##中付统一代付结果 response result {}", res);
        } catch (Exception ex) {
            logger.error("##代付接口执行异常{}", ex);
        }
        return JSONObject.parseObject(res);
    }

    /**
     * 代付查询接口
     *
     * @param queryVO 代付查询VO
     * @return 代付查询返回JSON
     */
    public static JSONObject doPayForAnotherQuery(UnifyQueryVO queryVO) {
        String res = "";
        try {

            JSONObject params = new JSONObject();
            params.put("service", queryVO.getService());
            params.put("merchantNo", queryVO.getMerchantNo());
            params.put("orderNumber", queryVO.getOrderNumber());
            params.put("inTradeOrderNo", queryVO.getInTradeOrderNo());
            params.put("tradeTime", queryVO.getTradeTime());
            params.put("sessionId", queryVO.getSessionId());
            params.put("MD5", Objects.requireNonNull(MD5Util.md5((params.getString("orderNumber") + params.getString("merchantNo") + queryVO.getMD5()))).toUpperCase());
            logger.info("##代付查询request params {}", params.toJSONString());
            res = HttpClientUtil.sendPostReturnStr(RedisDictProperties.getInstance().getdictValueByCode(KeyUtils.ZHONGFU_PAY_URL), params.toJSONString());
            logger.info("##代付查询response result {}", res);
        } catch (Exception ex) {
            logger.error("##代付查询接口执行异常{}", ex);
        }
        return JSONObject.parseObject(res);
    }

    public static void main(String[] args) {

/*       JSONObject json= getPayForAnotherSessionId("990149635210001","dsFGDS213");
        UnifyPayForAnotherVO un=new UnifyPayForAnotherVO();
        un.setService("p4aPay");
        un.setMerchantNo("990149635210001");
        un.setPayMoney("1");
        un.setOrderId(UUID.randomUUID().toString());
        un.setBankCard("6214830215284406");
        un.setName("朱秋友");
        un.setBankName("招商银行");
        un.setAcctType("1");
        un.setCnaps("308290003564"); //
        un.setProvince("上海");
        un.setCity("上海");
        un.setCertType("0");
        un.setCertNumber("430525198807107433");
        un.setSessionId(json.getString("sessionId"));
        un.setPayKey("111111");

        ZFPaymentServer.doPayForAnotherPay(un);*/
        JSONObject json = getPayForAnotherSessionId("990149635210001", "dsFGDS213");
        UnifyQueryVO queryVO = new UnifyQueryVO();
        queryVO.setService("p4aQuery");
        queryVO.setMerchantNo("990149635210001");
        queryVO.setOrderNumber("20190517135757796201");
        /* queryVO.setInTradeOrderNo("750c6eef-efb0-4606-9fe3-996b8270c394");*/
        queryVO.setTradeTime("2019-05-17");
        queryVO.setSessionId(json.getString("sessionId"));
        queryVO.setMD5("dsFGDS213");
        ZFPaymentServer.doPayForAnotherQuery(queryVO);
    }
}
