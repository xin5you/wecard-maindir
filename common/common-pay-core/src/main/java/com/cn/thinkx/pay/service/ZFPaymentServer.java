package com.cn.thinkx.pay.service;

import com.alibaba.fastjson.JSONObject;
import com.cn.thinkx.pay.core.DES3;
import com.cn.thinkx.pay.core.KeyDemo;
import com.cn.thinkx.pay.core.KeyUtils;
import com.cn.thinkx.pay.core.RSAUtil;
import com.cn.thinkx.pay.domain.UnifyPayForAnotherVO;
import com.cn.thinkx.pay.domain.UnifyQueryVO;
import com.cn.thinkx.pms.base.http.HttpClientUtil;
import com.cn.thinkx.pms.base.utils.DES;
import com.cn.thinkx.pms.base.utils.MD5Util;
import sun.security.krb5.internal.crypto.Des;
import sun.security.krb5.internal.crypto.Des3;

import java.util.Map;
import java.util.UUID;

/**
 * 中付统一代付服务接口
 */
public class ZFPaymentServer {

    private static final String PAYFORANOTHER_URL="http://210.13.122.106:2008/paymentServer/PayForAnother";//统一代付接口

    private static final String Str32="00000000000000000000000000000000";
    /**
     * 代付签到接口
     * @param merchantNo 商户号
     * @return
     */
    public static JSONObject getPayForAnotherSessionId(String merchantNo,String md5){
        KeyUtils k=new KeyUtils();
        String res="";
        try {
            //1,先获取rsa私钥
      /*      String privateKey=k.getRSAKey();
            System.out.println(privateKey);
            //2,获取所需密钥
            //md5,aes
            Map<String, String> map=k.getKeys(privateKey);
            String aes=map.get("AES");
            String md5=map.get("MD5");
            System.out.println("AES:"+aes+",MD5:"+md5);*/

            JSONObject params=new JSONObject();
            params.put("service", "p4aSignIn");
            params.put("merchantNo", merchantNo);
            params.put("MD5", MD5Util.md5(merchantNo+md5).toUpperCase());
            res=HttpClientUtil.sendPostReturnStr(PAYFORANOTHER_URL, params.toJSONString());
           System.out.println("返回的签到数据："+res);

        }catch (Exception ex){
            ex.printStackTrace();
        }
        return JSONObject.parseObject(res);
    }

    /**
     * 代付接口
     * @param un
     * @return
     */
    public static JSONObject doPayForAnotherPay(UnifyPayForAnotherVO un){
        KeyUtils k=new KeyUtils();
        String res="";
        try {
            //1,先获取rsa私钥
            String privateKey=k.getRSAKey();
            //2,获取所需密钥
            //md5,aes
            Map<String, String> map=k.getKeys(privateKey);
            String aes=map.get("AES");
            String MD5=map.get("MD5");
            System.out.println("aes:"+aes);
            System.out.println("MD5:"+MD5);

            //zek
            Map<String, String> map2=k.getZek(privateKey);
            String zek=map2.get("ZEK");
            System.out.println("ZEK:"+zek);

            System.out.println(zek.length());
            JSONObject msg=new JSONObject();
           /* params.put("service", un.getService());
            params.put("merchantNo", un.getMerchantNo());
            params.put("payMoney", DES3.encrypt3DES((Str32.substring(0, 32-un.getPayMoney().length())+un.getPayMoney()),zek));
            params.put("orderId", un.getOrderId());
            params.put("bankCard", DES3.encrypt3DES((Str32.substring(0, 32-un.getBankCard().length())+un.getBankCard()),zek));
            params.put("name", un.getName());
            params.put("bankName", un.getBankName());
            params.put("acctType", un.getAcctType());
            params.put("cnaps", un.getCnaps());
            params.put("province", un.getProvince());
            params.put("city", un.getCity());
            params.put("certType", un.getCertType());
            params.put("certNumber", DES3.encrypt3DES((Str32.substring(0, 32-un.getCertNumber().length())+un.getCertNumber()),zek));
            params.put("sessionId", un.getSessionId());
            params.put("payKey", un.getPayKey());
            String md5Str=un.getOrderId()+un.getMerchantNo()+un.getPayMoney()+un.getBankCard()+MD5;
            System.out.println(md5Str);
            params.put("MD5", MD5Util.md5(md5Str));*/

            msg.put("merchantNo", un.getMerchantNo());
            msg.put("service", "p4aPay");
            msg.put("payMoney", DES3.encrypt3DES((Str32.substring(0, 32-un.getPayMoney().length())+un.getPayMoney()),zek));
            msg.put("orderId", un.getOrderId());
            msg.put("bankCard", DES3.encrypt3DES((Str32.substring(0, 32-un.getBankCard().length())+un.getBankCard()),zek));
            msg.put("name", un.getName());
            msg.put("bankName", un.getBankName());
            msg.put("acctType", un.getAcctType());
            msg.put("cnaps", un.getCnaps());
            msg.put("province", un.getProvince());
            msg.put("city", un.getCity());
            msg.put("certType", "0");
            msg.put("certNumber", DES3.encrypt3DES((Str32.substring(0, 32-un.getCertNumber().length())+un.getCertNumber()),zek));
            msg.put("sessionId", un.getSessionId());
            msg.put("payKey", un.getPayKey());

            msg.put("MD5", MD5Util.md5(msg.getString("orderId")+msg.getString("merchantNo")+un.getPayMoney()+un.getBankCard()+MD5).toUpperCase());

            res=HttpClientUtil.sendPostReturnStr(PAYFORANOTHER_URL, msg.toJSONString());
            System.out.println("统一代付返回的结果："+res);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return JSONObject.parseObject(res);
    }

    /**
     * 代付查询接口
     * @param queryVO
     * @return
     */
    public static JSONObject doPayForAnotherQuery(UnifyQueryVO queryVO){
        KeyUtils k=new KeyUtils();
        String res="";
        try {

            JSONObject params=new JSONObject();
            params.put("service", queryVO.getService());
            params.put("merchantNo", queryVO.getMerchantNo());
            params.put("orderNumber", queryVO.getOrderNumber());
            params.put("inTradeOrderNo", queryVO.getInTradeOrderNo());
            params.put("tradeTime", queryVO.getTradeTime());
            params.put("sessionId", queryVO.getSessionId());
            params.put("MD5", MD5Util.md5((params.getString("orderNumber")+params.getString("merchantNo")+queryVO.getMD5())).toUpperCase());

      /*      msg.put("service", "p4aQuery");
            msg.put("merchantNo", queryVO.getMerchantNo());
            msg.put("sessionId", queryVO.getSessionId());
            msg.put("orderNumber",  queryVO.getOrderNumber());
            msg.put("tradeTime", "2019-05-17");
            msg.put("MD5", MD5Util.md5(msg.getString("orderNumber")+msg.getString("merchantNo")+MD5Key,"UTF-8"));*/

            res=HttpClientUtil.sendPostReturnStr(PAYFORANOTHER_URL, params.toJSONString());
            System.out.println("代付查询返回的结果："+res);
        }catch (Exception ex){
            ex.printStackTrace();
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
        JSONObject json= getPayForAnotherSessionId("990149635210001","dsFGDS213");
        UnifyQueryVO queryVO =new UnifyQueryVO();
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
