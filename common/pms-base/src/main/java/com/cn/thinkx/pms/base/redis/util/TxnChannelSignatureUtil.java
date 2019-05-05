package com.cn.thinkx.pms.base.redis.util;

import com.cn.thinkx.pms.base.utils.StringUtil;

import java.lang.reflect.Field;
import java.security.MessageDigest;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * 知了企服 渠道接入 接口签名验证
 *
 * @author pucker
 */
public class TxnChannelSignatureUtil {

    /**
     * 根据传入对象生成签名
     *
     * @param obj
     * @return
     */
    public static String genSign(Object obj) {
        SortedMap<String, String> map = new TreeMap<String, String>();
        if (obj != null) {
            getProperty(map, obj, obj.getClass());// 将当前类属性及其值放入map
            if (obj.getClass().getSuperclass() != null && obj.getClass().getGenericSuperclass() != null) {
                getProperty(map, obj, obj.getClass().getSuperclass());// 将当前类父类属性及其值放入map
                if (obj.getClass().getSuperclass().getSuperclass() != null && obj.getClass().getGenericSuperclass().getClass().getGenericSuperclass() != null)
                    getProperty(map, obj, obj.getClass().getSuperclass().getSuperclass());// 将当前类父类属性及其值放入map
            }
        }
        return sign(map, map.get("channel"));
    }

    public static void getProperty(SortedMap<String, String> map, Object obj, Class<?> clazz) {
        // 获取f对象对应类中的所有属性域
        Field[] fields = clazz.getDeclaredFields();
        for (int i = 0, len = fields.length; i < len; i++) {
            String varName = fields[i].getName();

            // 过滤签名字段
            if ("signature".equals(varName) || "serialVersionUID".equals(varName)) {
                continue;
            }
            try {
                // 获取原来的访问控制权限
                boolean accessFlag = fields[i].isAccessible();
                // 修改访问控制权限
                fields[i].setAccessible(true);
                // 获取在对象f中属性fields[i]对应的对象中的变量
                Object o = fields[i].get(obj);
                if (o != null && !StringUtil.isNullOrEmpty(o.toString())) {
                    if ("timestamp".equals(varName) && "0".equals(o.toString())) {  //处理时间戳不填页面逻辑处理，后续逻辑若删除该代码，需慎重

                    } else {
                        map.put(varName, o.toString());
                    }
                }
                // 恢复访问控制权限
                fields[i].setAccessible(accessFlag);
            } catch (IllegalArgumentException ex) {
                ex.printStackTrace();
            } catch (IllegalAccessException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static String byteToStr(byte[] byteArray) {
        String rst = "";
        for (int i = 0; i < byteArray.length; i++) {
            rst += byteToHex(byteArray[i]);
        }
        return rst;
    }

    public static String byteToHex(byte b) {
        char[] Digit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        char[] tempArr = new char[2];
        tempArr[0] = Digit[(b >>> 4) & 0X0F];
        tempArr[1] = Digit[b & 0X0F];
        String s = new String(tempArr);
        return s;
    }

    /**
     * MD5编码
     *
     * @param origin 原始字符串
     * @return 经过MD5加密之后的结果
     */
    public static String MD5Encode(String origin) {
        String resultString = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.reset();
            md.update(origin.getBytes("UTF-8"));
            resultString = byteToStr(md.digest());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultString;
    }

    /**
     * 组装报文签名
     *
     * @param items
     * @param channel
     * @return
     */
    public static String sign(SortedMap<String, String> items, String channel) {
        StringBuilder forSign = new StringBuilder();
        for (String key : items.keySet()) {
            forSign.append(key).append("=").append(items.get(key)).append("&");
        }
        forSign.append("key=").append(RedisDictProperties.getInstance().getChannelKeyByCode(channel));
        String result = MD5Encode(forSign.toString());
        return result.toUpperCase();
    }

    public static boolean isSignExpired(long currentTime, long txnTimestamp) {
        int signatureTimeout = Integer.parseInt(RedisDictProperties.getInstance().getdictValueByCode("SIGNATURE_TIMEOUT"));
        long valid = signatureTimeout * 60 * 1000;// 签名有效期(分钟转毫秒)
        if ((currentTime - txnTimestamp) > valid) {// 签名失效
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println(MD5Encode("innerMerchantNo=120020200000002&innerShopNo=00000001&"
                + "timestamp=1477474285129&key=1OsUdUSqOJilhNl64W1hv1JhgPNYy7UQ5yb8tL01h0I").toUpperCase());
    }
}
