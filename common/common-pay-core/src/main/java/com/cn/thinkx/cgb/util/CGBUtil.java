package com.cn.thinkx.cgb.util;

import cn.hutool.core.util.XmlUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpUtil;
import com.cn.thinkx.cgb.model.*;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class CGBUtil {
    private static Logger logger=LoggerFactory.getLogger(CGBUtil.class);
    public static String doPost(String url, String xmlString) {
        try {
            HttpRequest post = HttpUtil.createPost(url);
            post.timeout(15000);
            post.header("Content-Type", "text/xml;charset=utf8");
            post.body(xmlString);
            return post.execute(true).body();
        } catch (Exception e) {
            logger.error("##发生异常 {}" , e);
            return null;
        }
    }
    /**
     * @return
     */
    public static String getDateDays(long daysLong) {
        String dateStr = null;
        //获取单天时间 如果时间是凌晨0点到0点10需要把时间转换为昨天
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");//设置日期格式
        dateStr = df.format(daysLong);
        String currentTime = getcurrentTime();
        String beforeDawnsdf = new SimpleDateFormat("yyyy-MM-dd").format(daysLong);
        String beforeDawnStart = beforeDawnsdf + " 00:00:00";
        String beforeDawnEnd = beforeDawnsdf + " 00:10:00";
        //比较时间段
        if (compareCurrentTime(beforeDawnStart, currentTime, beforeDawnEnd)) {
            dateStr = df.format(daysLong - 86400000);
        }
        System.out.println(dateStr);
        return dateStr;
    }
    //获取当前时间 yyyy-MM-dd hh:mm:ss
    public static String getcurrentTime() {
        return new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(System.currentTimeMillis());
    }
    /**
     * @param beforeDawnStart 开始时间
     * @param currentTime     当前时间
     * @param beforeDawnEnd   结束时间
     * @return
     */
    public static boolean compareCurrentTime(String beforeDawnStart, String currentTime, String beforeDawnEnd) {
        try {
            //  currentTime="2019-01-19 00:00:00";
            Date beforeDawnDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(beforeDawnStart);
            Date currentTimeDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(currentTime);
            Date beforeDawnEndDate = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").parse(beforeDawnEnd);
            if (beforeDawnDate.compareTo(currentTimeDate) <= 0 && beforeDawnEndDate.compareTo(currentTimeDate) >= 0) {
                return true;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }
    public static String encryption(String dataXml, String pukPaht, String pvkPaht) {
        KeyDTO keyDTO = getKey(pukPaht, pvkPaht);
        if (keyDTO.isFlag() == false) {
            logger.info("keyDTO {}" , keyDTO);
            return null;
        }
        String sign = sign(dataXml, keyDTO.getPrivateKey());

        return sign;
    }


    public static KeyDTO getKey(String pukPaht, String pvkPaht) {
        //广发说公钥一样先写死
        if (pukPaht == null) {
            CgbInit cgbInit = new CgbInit();
            pukPaht = cgbInit.getAccount();
        }
        KeyDTO keyDTO = new KeyDTO();
        byte[] pvk = new byte[2048];
        byte[] pu = new byte[2048];
        try {
            pvk = read(pvkPaht);
            pu = read(pukPaht);

        } catch (IOException e) {
            // TODO Auto-generated catch block
            logger.error("##发生异常 {}" , e);
            keyDTO.setFlag(false);
            keyDTO.setMsg("第三方秘钥路径错误");
        }
        try {
            X509EncodedKeySpec pubX09 = new X509EncodedKeySpec(Base64.decodeBase64(pu));
            PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decodeBase64(pvk));
            KeyFactory key = KeyFactory.getInstance("RSA");
            keyDTO.setPrivateKey(key.generatePrivate(priPKCS8));
            keyDTO.setPublicKey(key.generatePublic(pubX09));
            keyDTO.setFlag(true);
            return keyDTO;
        } catch (Exception e) {
            logger.error("##发生异常 {}" , e);
            keyDTO.setFlag(false);
            keyDTO.setMsg("第三方错误");
            return keyDTO;
        }
    }

    public static final byte[] read(String filePath) throws IOException {
        byte[] abyte0;
        if (filePath == null) {
            throw new IllegalArgumentException("Illegal Argument: filePath");
        }

        FileInputStream crls = null;
        try {
            int rLength;
            crls = new FileInputStream(filePath);
            byte[] out = new byte[crls.available()];
            byte[] buffer = new byte[65536];

            for (int offset = 0; (rLength = crls.read(buffer, 0, buffer.length)) != -1; offset += rLength) {
                System.arraycopy(buffer, 0, out, offset, rLength);
            }

            abyte0 = out;
        } catch (IOException e) {
            throw e;
        } finally {
            if (crls != null) {
                try {
                    crls.close();
                } catch (Exception localException1) {
                    logger.error("##发生异常 {}" , localException1);
                }
            }
        }
        return abyte0;
    }

    public static String sign(String unsigned, PrivateKey pvk) {
        String signed = null;
        try {
            Signature signature = Signature.getInstance("SHA1WithRSA");
            signature.initSign(pvk);
            signature.update(unsigned.getBytes("GBK"));
            signed = new String(Base64.encodeBase64(signature.sign(), false), "GBK");
        } catch (Exception e) {
            logger.error("##发生异常 {}" , e);
        }
        return signed;
    }

    /**
     * 验签
     */
    public static boolean verify(String signed, String unsigned, PublicKey puk) {
        try {
            Signature sig = Signature.getInstance("SHA1withRSA");
            sig.initVerify(puk);
            sig.update(unsigned.getBytes("GBK"));
            return sig.verify(Base64.decodeBase64(signed.getBytes()));
        } catch (Exception e) {
            logger.error("##发生异常 {}" , e);
            return false;
        }
    }

    public static String getxml(RequestParametersDTO requestParametersDTO, String pukPaht, String pvkPaht) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            Map<String, Object> messageMap = new HashMap<String, Object>();
            Map<String, Object> objectMap = JavaBeanUtil.convertBeanToUpCaseKeyMap(requestParametersDTO.getObject());
            Map<String, Object> commHeadDTOMap = JavaBeanUtil.convertBeanToUpCaseKeyMap(requestParametersDTO.getCommHeadDTO());
            messageMap.put("commHead", commHeadDTOMap);
            messageMap.put("Body", objectMap);
            map.put("Message", messageMap);

            //String signedInfo = XmlUtil.toStr(XmlUtil.mapToXml(messageMap, "Message"), "GBK",false);/**/
            String signedInfo = CGBXmlUtil.MapToXML(map);
            SignatureDTO signatureDTO = new SignatureDTO();
            String sing = CGBUtil.encryption(signedInfo, pukPaht, pvkPaht);
            if (sing == null) {
                return null;
            }
            signatureDTO.setSignedInfo(sing);
            signatureDTO.setDigestMethod("SHA1");
            signatureDTO.setSignatureMethod("RSA");
            Map<String, Object> signatureDTOMap = JavaBeanUtil.convertBeanToUpCaseKeyMap(signatureDTO);
            map.put("Signature", signatureDTOMap);
        } catch (Exception e) {
            logger.error("##发生异常 {}" , e);
            return null;
        }
        return XmlUtil.toStr(XmlUtil.mapToXml(map, "BEDC"), "UTF-8", false);
    }

    //解析返回参数
    public static ResponseParametersDTO getResponseParametersDTO(HashMap<String, Object> map) {
        ResponseParametersDTO responseParametersDTO = new ResponseParametersDTO();
        Map<String, Object> BEDCMap = (Map<String, Object>) map.get("BEDC");
        Map<String, Object> messageMap = (Map<String, Object>) BEDCMap.get("Message");
        Map<String, Object> objectMap = null;
        if (messageMap.get("Body") != null) {
            try {
                objectMap = (Map<String, Object>) messageMap.get("Body");
            } catch (Exception e) {
                   logger.error("messageMap.get(\"Body\")错误 {}",e);
            }
        }

        Map<String, Object> commHeadDTOMap = (Map<String, Object>) messageMap.get("commHead");
        Map<String, Object> SignatureMap = (Map<String, Object>) BEDCMap.get("Signature");
        responseParametersDTO.setCommHeadDTOMap(commHeadDTOMap);
        responseParametersDTO.setObjectMap(objectMap);
        responseParametersDTO.setSignatureMap(SignatureMap);
        responseParametersDTO.setMessageMap(messageMap);
        return responseParametersDTO;
    }

    /**
     * @param signedInfo
     * @param pukPath
     * @param pvkPath
     * @return
     */
    public static boolean ResponseVerify(String messageXml, String signedInfo, String pukPath, String pvkPath) {
        try {
            KeyDTO keyDTO = getKey(pukPath, pvkPath);
            return verify(signedInfo, messageXml, keyDTO.getPublicKey());
        } catch (Exception e) {
            logger.error("系统错误延签失败 {}", e);
            return false;
        }
    }

    /**
     * 验证是否null或者""
     */
    public static boolean isnull(String str) {
        if (str == null || str.equals("null") || str.trim().isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 截取对应的字符串
     * @param strXml
     * @param s
     * @param s1
     * @return
     */
    public static String substring(String strXml, String s, String s1) throws UnsupportedEncodingException {
        int index = strXml.indexOf(s);
        int endindex = strXml.indexOf(s1) + s1.length();
        return strXml.substring(index, endindex);
    }


}
