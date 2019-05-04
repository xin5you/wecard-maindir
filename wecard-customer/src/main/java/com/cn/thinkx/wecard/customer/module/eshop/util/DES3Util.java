package com.cn.thinkx.wecard.customer.module.eshop.util;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONArray;
import com.cn.thinkx.wecard.customer.module.eshop.vo.HTTTDParams;


/**
 * 3des 加密、解密 工具类
 * 
 * @author xiaomei
 *
 */
public class DES3Util {
	private static final String ALGORITHM = "DESede";

	private static final String TRANSFORMATION = "DESede/ECB/NoPadding"; //定义加密算法,可用 

	private static Logger LOGGER = LoggerFactory.getLogger(DES3Util.class);

	/**
	 * 加密方法
	 * @param keybyte sdk 传过来的临时会话密钥(skey)
	 * @param src 需要加密的值
	 * @return
	 */
	public static String encryptMode(String keybyte,String src){
		byte[] out = null;
		int sLen = src.getBytes().length;
		int dLen = sLen;
		if (sLen % 8 != 0) {
			dLen = (sLen / 8 + 1) * 8;
		}
		byte[] in = new byte[dLen];
		System.arraycopy(src.getBytes(), 0, in, 0, sLen);
		try {
			//生成密钥
			SecretKey deskey = new SecretKeySpec(build3DesKey(keybyte), ALGORITHM);
			//加密
			Cipher c1 = Cipher.getInstance(TRANSFORMATION);
			c1.init(Cipher.ENCRYPT_MODE, deskey);
			out = c1.doFinal(in);
			return byte2HexStr(out);
		}catch(Exception e){
			LOGGER.error("ThreeDesUtil encryptMode error",e);
			return null;
		}
	}

	/**
	 * 解密方法
	 * @param key 解密密钥
	 * @param msg 需要解密的值
	 * @return
	 */
	public static String decryptMode(String key, String msg){
		byte[] src = hexStringToBytes(msg);
		try {
			//生成密钥
			SecretKey deskey = new SecretKeySpec(build3DesKey(key), ALGORITHM);
			//解密
			Cipher c1 = Cipher.getInstance(TRANSFORMATION);
			c1.init(Cipher.DECRYPT_MODE, deskey);
			byte[] out = c1.doFinal(src);
			return new String(out);
		}catch(Exception e){
			LOGGER.error("decryptMode error e",e);
			return null;
		}	
	}

	/**
	 * 由于 mas系统生成的 key 是16进制的32位字符 ，而3des 加密
	 *解密的key的长度只能是24位的字节数组，所以需要处理 16进制的32位字符的key 
	 * @param keyStr 需要处理的key
	 * @return
	 */
	public static byte[] build3DesKey(String keyStr){
		byte[] key = new byte[24]; // 声明一个24位的字节数组，默认里面都是0
		byte[] temp = null;
		try {
			temp = hexStringToBytes(keyStr);
		} catch (Exception e) {
			LOGGER.error("build3DesKey error e",e);
			return null;
		} 
		System.arraycopy(temp, 0, key, 0, 16);
		System.arraycopy(temp, 0, key, 16, 8);
		return key;
	} 

	/**
	 * String 16进制 转 byte []
	 * 
	 * @param hexString
	 * @return
	 */
	public static byte[] hexStringToBytes(String hexString) {
		if (hexString == null || hexString.equals("")) {
			return null;
		}
		hexString = hexString.toUpperCase();
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
		}
		return d;
	}

	/**
	 * byte [] 转换成十六进制 string
	 * 
	 * @param byte[]
	 *            b byte数组
	 * @return String
	 */
	public static String byte2HexStr(byte[] b) {
		String stmp = "";
		StringBuilder sb = new StringBuilder("");
		for (int n = 0; n < b.length; n++) {
			stmp = Integer.toHexString(b[n] & 0xFF);
			sb.append((stmp.length() == 1) ? "0" + stmp : stmp);
			// sb.append(" ");
		}
		return sb.toString().toUpperCase().trim();
	}

	/**
	 * Convert char to byte
	 * 
	 * @param c
	 *            char
	 * @return byte
	 */
	private static byte charToByte(char c) {
		return (byte) "0123456789ABCDEF".indexOf(c);
	}

	public static void main(String[] args) {
		//添加新安全算法,如果用JCE就要把它添加进去
		HTTTDParams data = new HTTTDParams();
		data.setInstitutionNo("");
		data.setMobile("15575311169");
		data.setNickName("文丁美");
		data.setUserId("2018020213144200000941");
		String skey = "fecef880503947889ce62c35f774e082";
		System.out.println("加密前的字符串:" + JSONArray.toJSONString(data));
		String  encoded = encryptMode(skey, JSONArray.toJSONString(data));
		System.out.println("加密后的字符串:" + encoded);
		String myMsgArr = decryptMode(skey, encoded);
		System.out.println("解密后的字符串：" + myMsgArr.trim());
	}
}
