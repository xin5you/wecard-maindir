package com.cn.thinkx.pms.base.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class NumberUtils {

    public static int parseInt(String str, int defaultNum) {
        int num = 0;
        try {
            num = Integer.parseInt(str);
        } catch (Exception e) {
            num = defaultNum;
        }
        return num;
    }

    public static int parseInt(String str) {
        return parseInt(str, 0);
    }

    public static String parseString(String str) {
        if (str == null)
            return "";
        return str;
    }

    public static long parseLong(String str) {
        if (str == null)
            return 0;
        try {
            return Long.parseLong(str);
        } catch (Exception e) {
            return 0;
        }
    }

    public static double parseDouble(String str) {
        return parseDouble(str, 0.0d);
    }

    public static double parseDouble(String str, double defaultNum) {
        try {
            return Double.parseDouble(str);
        } catch (Exception e) {
            return defaultNum;
        }
    }

    public static double round(double value) {
        return round(value, 2);
    }

    public static String format(String str, int scale) {
        String mat = "%." + scale + "f";
        // "%.4f"
        return String.format(mat, str);
    }

    public static String format(double data) {
        return String.format("%.2f", data);
    }

    public static String format(String str) {
        return format(str, 2);
    }

    public static double round(double value, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal v = BigDecimal.valueOf(value);
        return v.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 两个数据相减
     *
     * @param v1
     * @param v2
     * @return
     */
    public static double sub(double v1, double v2) {
        BigDecimal b1 = BigDecimal.valueOf(v1);
        BigDecimal b2 = BigDecimal.valueOf(v2);
        return b1.subtract(b2).doubleValue();
    }

    /**
     * 两个数据相减
     *
     * @param v1
     * @param v2
     * @return
     */
    public static int sub(int v1, int v2) {
        int str = v1 - v2;
        return str;
    }

    /**
     * 提供精确的乘法运算。
     *
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积
     */
    public static double mul(String v1, String v2) {
        BigDecimal b1 = new BigDecimal(Double.parseDouble(v1));
        BigDecimal b2 = new BigDecimal(Double.parseDouble(v2));
        return b1.multiply(b2).doubleValue();
    }

    /**
     * 提供精确的乘法运算。
     *
     * @param v1 被乘数
     * @param v2 乘数
     * @return 两个参数的积
     */
    public static int mul(int v1, int v2) {
        int str = v1 * v2;
        return str;
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 定精度，以后的数字四舍五入。
     *
     * @param v1    被除数
     * @param v2    除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static double formatMoneydiv(double amount, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException("The scale must be a positive integer or zero");
        }
        BigDecimal b1 = BigDecimal.valueOf(amount);
        BigDecimal b2 = BigDecimal.valueOf(v2);
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

    /**
     * 单位分转为元
     *
     * @return
     */
    public static double formatMoney(long amount) {
        return formatMoneydiv(amount, 100, 2);
    }

    /**
     * 单位分转为元
     *
     * @return
     */
    public static double formatMoney(double amount) {
        return formatMoneydiv(amount, 100, 2);
    }

    /**
     * 单位分转为元
     *
     * @return
     */
    public static double formatMoney(String amount) {
        return formatMoneydiv(Double.parseDouble(amount), 100, 2);
    }

    /**
     * 汇率转换 4位小数点
     *
     * @return
     */
    public static double formatRate4(double rate) {
        return formatMoneydiv(rate, 10000, 4);
    }

    /**
     * 汇率转换 4位小数点
     *
     * @return
     */
    public static double formatRate4(String rate) {
        return formatMoneydiv(Long.parseLong(rate), 10000, 4);
    }

    /**
     * 汇率转换 2位小数点
     *
     * @return
     */
    public static double formatRate2(String amount) {
        return formatMoneydiv(Double.parseDouble(amount), 10000, 2);
    }

    /**
     * 汇率转换 2位小数点
     *
     * @return
     */
    public static double formatRate2(double rate) {
        return formatMoneydiv(rate, 10000, 2);
    }

    /**
     * 万分比 转换 int
     *
     * @param rate
     * @return
     */
    public static int disRateTenThousand(double rate) {
        return disRate(rate, 10000);
    }

    /**
     * 千分比转换 int
     *
     * @param rate
     * @return
     */
    public static int disRateThousand(double rate) {
        return disRate(rate, 1000);
    }

    /**
     * 百分比转换 int
     *
     * @param rate
     * @return
     */
    public static int disRatehundred(double rate) {
        return disRate(rate, 100);
    }

    /**
     * 费率 转换
     *
     * @param rate
     * @return
     */
    public static int disRate(double rate, int dis) {
        BigDecimal b1 = BigDecimal.valueOf(rate);
        BigDecimal b2 = BigDecimal.valueOf(dis);
        return b1.multiply(b2).intValue();
    }

    /**
     * 人民币 元转分
     *
     * @param money
     * @return
     */
    public static String RMBYuanToCent(String money) {
        BigDecimal b1 = new BigDecimal(money);
        BigDecimal b2 = new BigDecimal(100);
        return b1.multiply(b2).setScale(0, BigDecimal.ROUND_DOWN).toString();
    }

    public static String RMBYuanToCent(int money) {
        BigDecimal b1 = new BigDecimal(money);
        BigDecimal b2 = new BigDecimal(100);
        return b1.multiply(b2).setScale(0, BigDecimal.ROUND_DOWN).toString();
    }

    /**
     * 人民币 元转厘
     *
     * @param money
     * @return
     */
    public static String RMBYuanToMile(String money) {
        BigDecimal b1 = new BigDecimal(money);
        BigDecimal b2 = new BigDecimal(1000);
        return b1.multiply(b2).setScale(0, BigDecimal.ROUND_DOWN).toString();
    }

    public static String RMBYuanToMile(int money) {
        BigDecimal b1 = new BigDecimal(money);
        BigDecimal b2 = new BigDecimal(1000);
        return b1.multiply(b2).setScale(0, BigDecimal.ROUND_DOWN).toString();
    }

    /**
     * 人民币 分转元
     *
     * @param money
     * @return
     */
    public static String RMBCentToYuan(String money) {
        BigDecimal b1 = new BigDecimal(money);
        BigDecimal b2 = new BigDecimal(100);
        return b1.divide(b2).setScale(2, BigDecimal.ROUND_DOWN).toString();
    }

    /**
     * 人民币 分转元
     *
     * @param money
     * @return
     */
    public static String RMBCentToYuan(int money) {
        BigDecimal b1 = new BigDecimal(money);
        BigDecimal b2 = new BigDecimal(100);
        return b1.divide(b2, 2, BigDecimal.ROUND_DOWN).toString();
    }

    public static String addMoney(String money1, String money2) {
        BigDecimal b1 = new BigDecimal(money1);
        BigDecimal b2 = new BigDecimal(money2);
        return b1.add(b2).toString();
    }

    /**
     * 人民币 厘转元
     *
     * @param money
     * @return
     */
    public static String RMBMileToYuan(String money) {
        BigDecimal b1 = new BigDecimal(money);
        BigDecimal b2 = new BigDecimal(1000);
        return b1.divide(b2).setScale(2, BigDecimal.ROUND_DOWN).toString();
    }

    /**
     * 人民币 厘转元
     *
     * @param money
     * @return
     */
    public static String RMBMileToYuan(int money) {
        BigDecimal b1 = new BigDecimal(money);
        BigDecimal b2 = new BigDecimal(1000);
        return b1.divide(b2).setScale(2, BigDecimal.ROUND_DOWN).toString();
    }

    public static String addMoneyMile(String money1, String money2) {
        BigDecimal b1 = new BigDecimal(money1);
        BigDecimal b2 = new BigDecimal(money2);
        return b1.add(b2).toString();
    }

    public static String RMMultiplyToDecimal(String a1, String a2) {
        BigDecimal b1 = new BigDecimal(a1);
        BigDecimal b2 = new BigDecimal(a2);
        return b1.multiply(b2).toString();
    }

    /**
     * 距离转换
     *
     * @param dic
     * @return
     */
    public static String getConverDistance(String dis) {

        double distance = parseDouble(dis);
        if (distance < 100) {
            return "<100m";
        } else if (distance >= 100 && distance < 1000) {
            return new DecimalFormat("#").format(distance) + "m";
        } else {
            distance = distance / 1000;
            return new DecimalFormat("#.##").format(distance) + "km";
        }
    }

    /**
     * 获取16位随机数字
     *
     * @return
     */
    public static String get16RandomNum() {
        return "" + (long) (Math.random() * 10000000000000000l);
    }

    /**
     * 隐藏手机号中间四位
     *
     * @param mobileNo
     * @return
     */
    public static String hiddingMobileNo(String mobileNo) {
        return mobileNo.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2");
    }

    /**
     * 方法描述 隐藏银行卡号中间的字符串（使用*号），显示前四后四
     *
     * @param cardNo
     * @return
     */
    public static String hideCardNo(String cardNo) {
        if (StringUtils.isBlank(cardNo))
            return cardNo;

        int length = cardNo.length();
        int beforeLength = 4;
        int afterLength = 4;
        //替换字符串，当前使用“*”
        String replaceSymbol = "*";
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            if (i < beforeLength || i >= (length - afterLength))
                sb.append(cardNo.charAt(i));
            else
                sb.append(replaceSymbol);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        BigDecimal b1 = new BigDecimal(NumberUtils.mul(3, Integer.parseInt("500")));
        BigDecimal b2 = new BigDecimal(100);
        int loseAmt = b1.divide(b2, 0, BigDecimal.ROUND_DOWN).intValue();
        System.out.println(loseAmt);
        System.out.println(NumberUtils.RMBCentToYuan(loseAmt));
    }

}