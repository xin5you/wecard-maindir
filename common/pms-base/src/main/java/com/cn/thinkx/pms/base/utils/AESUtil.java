package com.cn.thinkx.pms.base.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Encoder;

public class AESUtil {
	private static final String Algorithm = "AES"; 
	
	//初始化向量
	public static String getParam(){
		Date dt = new Date();
		SimpleDateFormat matter = new SimpleDateFormat("yyyyMMdd");
		String iv = MD5Util.md5(matter.format(dt)).substring(0,16);
		return iv;
	}
	
	/**
	 * AES加密
	 * @param encryptstr
	 * @param iv
	 * @return
	 */
	public static String jzEncrypt(String encryptstr ,String sKey){
		return byte2hex(encrypt(encryptstr,getParam(), sKey));
	}
	
	/**
	 * 字符串转换成十六进制字符串
	 * @param str
	 * @return
	 */
	public static String byte2hex(String str) {
		char[] chars = "0123456789abcdef".toCharArray();
		StringBuilder sb = new StringBuilder("");
		byte[] bs = str.getBytes();
		int bit ;
		for (int i = 0; i < bs.length; i++) {
			bit = (bs[i] & 0x0f0)>>4;
			sb.append(chars[bit]);
			bit = bs[i] & 0x0f;
			sb.append(chars[bit]);
		}
		return sb.toString().trim();
	}
	
	/**
	 * AES系统中的CBC模式加密
	 * @param sSrc
	 * @param ivParameter
	 * @return
	 */
	public static String encrypt(String sSrc, String ivParameter, String sKey){
		try{
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			byte[] raw = sKey.getBytes();
			SecretKeySpec skeySpec = new SecretKeySpec(raw, Algorithm);
			IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec,iv);
			byte[] encrypted = cipher.doFinal(sSrc.getBytes("UTF-8"));
			return new BASE64Encoder().encode(encrypted);
		} catch(Exception e){
			return null;
		}
	}
	
	public static void main(String[] args) {
		String sKey = "litj49jk4op589o4";
//		String str= "2017062606345600000042|15073940405|oppGJ00P7dNXpBStrw1hB2lTIUlU|101000000173958|00000129";
//		String result = jzEncrypt(str);
//		System.out.println(result);

//		getStringFromDate
		
		String s = "2017062606345600000042";
		String r = jzEncrypt(s,sKey);
		System.out.println(r);
		
		String str = String.valueOf(System.currentTimeMillis());
		String result = jzEncrypt(str,sKey);
		System.out.println(result);
	}
}
