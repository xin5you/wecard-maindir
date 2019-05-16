package com.cn.thinkx.pay.service;

import com.alibaba.fastjson.JSONObject;
import com.cn.thinkx.pay.core.KeyUtils;
import com.cn.thinkx.pay.domain.UnifyPayForAnotherVO;
import com.cn.thinkx.pay.domain.UnifyQueryVO;
import com.cn.thinkx.pms.base.http.HttpClientUtil;
import com.cn.thinkx.pms.base.utils.MD5Util;

import java.util.Map;
import java.util.UUID;

/**
 * 中付统一代付服务接口
 */
public class ZFPaymentServer {

    private static final String PAYFORANOTHER_URL="http://210.13.122.106:2008/paymentServer/PayForAnother";//统一代付接口

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

            JSONObject params=new JSONObject();
            params.put("service", un.getService());
            params.put("merchantNo", un.getMerchantNo());
            params.put("payMoney", un.getPayMoney());
            params.put("orderId", un.getOrderId());
            params.put("bankCard", un.getBankCard());
            params.put("name", un.getName());
            params.put("bankName", un.getBankName());
            params.put("acctType", un.getAcctType());
            params.put("cnaps", un.getCnaps());
            params.put("province", un.getProvince());
            params.put("city", un.getCity());
            params.put("certType", un.getCertType());
            params.put("certNumber", un.getCertNumber());
            params.put("sessionId", un.getSessionId());
            params.put("payKey", un.getPayKey());
            params.put("MD5", un.getMD5());
            res=HttpClientUtil.sendPostReturnStr(PAYFORANOTHER_URL, params.toJSONString());
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
            params.put("MD5", queryVO.getMD5());
            res=HttpClientUtil.sendPostReturnStr(PAYFORANOTHER_URL, params.toJSONString());
            System.out.println("代付查询返回的结果："+res);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return JSONObject.parseObject(res);
    }

    public static void main(String[] args) {
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
        un.setSessionId("WQ747V58RAX043KUHA68886E98");
        un.setPayKey("111111");
        un.setMD5(MD5Util.md5(un.getOrderId()+un.getMerchantNo()+un.getPayMoney()+un.getBankCard()+"dsFGDS213"));

        ZFPaymentServer.doPayForAnotherPay(un);

    }
}
