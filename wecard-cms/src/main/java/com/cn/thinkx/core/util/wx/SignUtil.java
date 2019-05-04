package com.cn.thinkx.core.util.wx;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.SortedMap;

/**
 * 微信验证
 * 
 */

public class SignUtil {
	
	/**
	 * @param signature 微信加密签名
	 * @param timestamp tocken
	 * @param timestamp 时间戳
	 * @param nonce 随机数
	 * @return
	 */
	public static boolean validSign(String signature, String tocken, String timestamp, String nonce) {
		String[] paramArr = new String[] { tocken, timestamp, nonce };
		//对token、timestamp、nonce 进行字典排序，并拼接成字符串
		Arrays.sort(paramArr);
		StringBuilder sb = new StringBuilder(paramArr[0]);
		sb.append(paramArr[1]).append(paramArr[2]);
		String ciphertext = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-1");
			byte[] digest = md.digest(sb.toString().getBytes());// 对接后的字符串进行sha1加密
			ciphertext = byteToStr(digest);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		// 将sha1加密后的字符串与  signature 进行比较
		return ciphertext != null ? ciphertext.equals(signature.toUpperCase()) : false;
	}

	public static String byteToStr(byte[] byteArray) {
		String rst = "";
		for (int i = 0; i < byteArray.length; i++) {
			rst += byteToHex(byteArray[i]);
		}
		return rst;
	}
	
	public static String byteToHex(byte b) {
		char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		char[] tempArr = new char[2];
		tempArr[0] = Digit[(b >>> 4) & 0X0F];
		tempArr[1] = Digit[b & 0X0F];
		String s = new String(tempArr);
		return s;
	}
	
	/**
	 * MD5编码
	 * 
	 * @param origin
	 *            原始字符串
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
	
    public static String signature(SortedMap<String,String> items){
    	StringBuilder forSign= new StringBuilder();
    	for(String key:items.keySet()){
    		forSign.append(key).append("=").append(items.get(key)).append("&");
    	}
    	forSign.setLength(forSign.length()-1);
    	String result = encryptSHA1(forSign.toString());
    	return result;
    }
    
    public static String encryptSHA1(String content){
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-1");
            digest.update(content.getBytes());
            byte messageDigest[] = digest.digest();
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
	
    public static void main(String[] args) {
		System.out.println(MD5Encode("appid=111&attach=222").toUpperCase());
	}
}

