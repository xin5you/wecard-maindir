package com.cn.thinkx.pay.core;

import javax.crypto.Cipher;

import javax.crypto.spec.SecretKeySpec;

import java.net.URLEncoder;
import java.security.InvalidKeyException;


public class DES3 {
    public static String encrypt3DES(String data, String key) throws Exception {
        if (key.length() != 32) {
            throw new InvalidKeyException();
        }

        String firstKey = key.substring(0, 16);
        String secondKey = key.substring(16, 32);
        String result = null;
        result = encryptDES(data, firstKey);
        result = decryptDES(result, secondKey);
        result = encryptDES(result, firstKey);

        return result;
    }

    public static String encryptDES(String data, String key) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(hex2byte(key), "DES");
        Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        byte[] encryptedData = cipher.doFinal(hex2byte(data));
        return byte2hex(encryptedData);
    }

    public static String decryptDES(String data, String key) throws Exception {
        SecretKeySpec secretKey = new SecretKeySpec(hex2byte(key), "DES");
        Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte decryptedData[] = cipher.doFinal(hex2byte(data));
        return byte2hex(decryptedData);
    }
    public static byte[] hex2byte(String hex) throws IllegalArgumentException {
        if (hex.length() % 2 != 0) {
            throw new IllegalArgumentException();
        }
        char[] arr = hex.toCharArray();
        byte[] b = new byte[hex.length() / 2];
        for (int i = 0, j = 0, l = hex.length(); i < l; i++, j++) {
            String swap = "" + arr[i++] + arr[i];
            int byteint = Integer.parseInt(swap, 16) & 0xFF;
            b[j] = new Integer(byteint).byteValue();
        }
        return b;
    }

    public static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int i = 0; i < b.length; i++) {
            stmp = Integer.toHexString(b[i] & 0xFF);
            if (stmp.length() == 1) {
                hs += "0" + stmp;
            } else {
                hs += stmp;
            }
        }
        return hs.toUpperCase();
    }



    public static void main(String[] args) throws Exception {
        final String key = "00000000000000000000000000000000";
        // 加密流程
        String certNumber = "1";

        System.out.println( encrypt3DES((key.substring(0, 32-certNumber.length())+certNumber),key));

    }
}