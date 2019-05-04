/*
 * Copyright (C), 2002-2016, 苏宁易购电子商务有限公司
 * FileName: BatchTransferData.java
 * Author:   16031333
 * Date:     2016年5月25日 上午10:30:43
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.cn.thinkx.wecard.api.module.withdraw.suning.dto;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cn.thinkx.wecard.api.module.withdraw.suning.utils.HttpClientUtil;
import com.cn.thinkx.wecard.api.module.withdraw.suning.utils.BizUtil;
import com.suning.epps.codec.Digest;
import com.suning.epps.codec.RSAUtil;

/**
 * 〈一句话功能简述〉<br>
 * 批量出款 数据拼写
 * 
 * @author 16031333
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class BatchWithdrawData {

    private String publicKeyIndex = "xpy1";
    private String signAlgorithm = "RSA";
    private String merchantNo = "suning";
    private String inputCharset = "UTF-8";

    String batchNo;

    /**
     * 请求转账网关
     * 
     * @param baseUrl 请求业务数据 batchNum 请求的批次数目 detailNum 单批次中的明细数目
     * @return signature 签名
     * @throws UnsupportedEncodingException
     * @throws java.security.InvalidKeyException
     * @throws KeyManagementException 
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    public String batchWithDraw(String baseUrl) throws InvalidKeyException, NoSuchAlgorithmException,
            InvalidKeySpecException, SignatureException, UnsupportedEncodingException,
            java.security.InvalidKeyException, KeyManagementException {

        batchNo = BizUtil.generalBatchNo();
        System.out.println(batchNo);

        String bussinessParam = createBatchData(1, 2).toJSONString();
        String singnature = calculateSign(bussinessParam);
        String responseStr = HttpClientUtil.post(publicKeyIndex, signAlgorithm, merchantNo, inputCharset, baseUrl,
                singnature, bussinessParam);
        return responseStr;
    }

    /**
     * 计算签名
     * 
     * @param body 请求业务数据
     * @return signature 签名
     * @throws java.security.InvalidKeyException
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    public static String calculateSign(String body) throws NoSuchAlgorithmException, InvalidKeySpecException,
            InvalidKeyException, SignatureException, java.security.InvalidKeyException {
        Map<String, String> signMap = new HashMap<>();
        signMap.put("merchantNo", "suning");
        signMap.put("publicKeyIndex", "xpy1");
        signMap.put("inputCharset", "UTF-8");
        signMap.put("body", body);
        String wagKeyString = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIWvIOflNkPsEvGWCvvTM4tlWcodoPbC52Q6EXsv9UJyOqbzfX9u9xGRLiUv3fOs9J02QQPK6ZPQiR0g8RvPv2858bh5finE13iwIuYTpgSRZVi2Kn7goer4qqwXD_TjM1B6PIpOylJksbF9RESZP0A8cG2twJprdZ54xYj4HIGvAgMBAAECgYAPG-b9LpO-g3z0nv-ozIsD0zWduVGK8iZS1plJMfdnRh_I5LYnY_Q6oQz1GP7d3otbBVm9wv45PZVxnFqDySwajI4uAP9ZZQ8RHrPTNttFgLm3OQ0shbIUhBi2vXorxicR-EnS4qWpCOP10o5JrlpieZ295S2p7Dn_xmIoRgPRKQJBAN4ilfdxuEU3E-eiPo98gUXFPpCCCXKla4JMvN2R6em8d0MVYT8g0rXoXS44UnEg0vOoJ7ulPh5Col6ilqR2op0CQQCaEIGvc2PDa8jHXSmDuwpl4ogqafNyY7FCjPqWvlG-_auU0qaKBuVhIEMuy-3ZUMFFCsmGkMOKr_7ACTW3bM27AkEAwCihHIYmhtGniWhjwBJPbgC8J5wl-iQ5RWWGuBGCjSz46nIzRr3pKW2SNeqI_s4LTrY3cO74NoskFMOHl0v9TQJARmWofH0jZtZHZiGBqLm8pJWAVrEXFnvLMXetwVexjq3myxf-FS_VfC37xNRWGGi4B05Ii352e1az9xe-PdQvpQJATWPfQc1IR-cefoAvEcUyQlTsthVQkJ3wUpRMEosw2V5a1f9euwhJXJDf6ca8zOhtyfXuTIag1YturYfKyXgY3w";
        String digest = Digest.digest(Digest.mapToString(Digest.treeMap(signMap)));
        PrivateKey privateKey = RSAUtil.getPrivateKey(wagKeyString);
        String signature = RSAUtil.sign(digest, privateKey);
        return signature;
    }

    /**
     * 生成请求wag多个批次的json数据方法
     * 
     * @param batchNum 请求批次数目
     * @return JSONArray
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    public JSONArray createBatchData(int batchNum, int detailNum) {
        JSONArray jsonArray = new JSONArray();
        if (batchNum > 0) {
            for (int i = 0; i < batchNum; i++) {
                jsonArray.add(bulidBatchContentJosn(detailNum));
            }
        }
        return jsonArray;
    }

    /**
     * 生成body批次数据
     * 
     * @param
     * @return JSONObject
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    public JSONObject bulidBatchContentJosn(int detailNum) {

        JSONObject contentObject = new JSONObject();
        contentObject.put("batchNo", batchNo);
        contentObject.put("merchantNo", "70055995");// 70057241;70056575
        contentObject.put("productCode", "01060000029");
        contentObject.put("totalNum", detailNum);
        contentObject.put("totalAmount", 100 * detailNum);// 40*detailNum
        contentObject.put("currency", "CNY");
        contentObject.put("payDate", "20160111");
        contentObject.put("tunnelData", "{\"businessOrderId\":\"aaaaaaaaaaxxxxxxaaaaabe\"}");
        contentObject.put("goodsType", "220026");
        contentObject.put("remark", "这是一个demo");
        contentObject.put("productCode", "01070000031");
        contentObject.put("detailData", bulidDetailContentJosn(detailNum));
        contentObject.put("notifyUrl", "http://10.24.42.78:8080/epps-demo/batchtransfer/notify.htm");
        contentObject.put("batchOrderName", "批量出款");
        return contentObject;
    }

    /**
     * 生成body明细数据
     * 
     * @param
     * @return JSONObject
     * @see [相关类/方法]（可选）
     * @since [产品/模块版本] （可选）
     */
    public JSONArray bulidDetailContentJosn(int detailNum) {
        JSONArray detailArray = new JSONArray();
        for (int i = 0; i < detailNum; i++) {
            JSONObject detailObject = new JSONObject();
            detailObject.put("serialNo", BizUtil.generalBatchNo());
            detailObject.put("receiverType", "PERSON");
            detailObject.put("receiverCurrency", "CNY");
            detailObject.put("receiverName", "妞妞");
            detailObject.put("amount", 100);
            detailObject.put("orderName", "运费险");
            detailObject.put("bankName", "中国建设银行");
            detailObject.put("receiverCardNo", "6221660070291641");
            detailObject.put("bankCode", "CCB");
            detailObject.put("bankProvince", "");
            detailObject.put("bankCity", "");
            detailObject.put("payeeBankLinesNo", "");
            detailArray.add(detailObject);
        }
        return detailArray;

    }

}
