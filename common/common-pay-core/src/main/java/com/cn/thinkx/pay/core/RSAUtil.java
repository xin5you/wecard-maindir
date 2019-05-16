package com.cn.thinkx.pay.core;

import org.apache.commons.codec.binary.Hex;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

public class RSAUtil extends Coder{
	 public static final String KEY_ALGORITHM = "RSA";
	public static final String MODE="RSA/ECB/PKCS1Padding";
	//public static final String MODE="RSA/ECB/NoPadding";
	    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";

	    private static final String PUBLIC_KEY = "RSAPublicKey";
	    private static final String PRIVATE_KEY = "RSAPrivateKey";

	    /**
	     * 用私钥对信息生成数字签名
	     *
	     * @param data
	     *            加密数据
	     * @param privateKey
	     *            私钥
	     *
	     * @return
	     * @throws Exception
	     */
	    public static String sign(byte[] data, String privateKey) throws Exception {
	        // 解密由base64编码的私钥
	        byte[] keyBytes = decryptBASE64(privateKey);
	        // 构造PKCS8EncodedKeySpec对象
	        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
	        // KEY_ALGORITHM 指定的加密算法
	        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
	        // 取私钥匙对象
	        PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);
	        // 用私钥对信息生成数字签名
	        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
	        signature.initSign(priKey);
	        signature.update(data);
	        return encryptBASE64(signature.sign());
	    }

	    /**
	     * 校验数字签名
	     *
	     * @param data
	     *            加密数据
	     * @param publicKey
	     *            公钥
	     * @param sign
	     *            数字签名
	     *
	     * @return 校验成功返回true 失败返回false
	     * @throws Exception
	     *
	     */
	    public static boolean verify(byte[] data, String publicKey, String sign)
	            throws Exception {

	        // 解密由base64编码的公钥
	        byte[] keyBytes = decryptBASE64(publicKey);

	        // 构造X509EncodedKeySpec对象
	        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);

	        // KEY_ALGORITHM 指定的加密算法
	        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);

	        // 取公钥匙对象
	        PublicKey pubKey = keyFactory.generatePublic(keySpec);

	        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
	        signature.initVerify(pubKey);
	        signature.update(data);

	        // 验证签名是否正常
	        return signature.verify(decryptBASE64(sign));
	    }



	public static String byte2hex(byte[] b)
	{
		String hs = "";
		String stmp = "";
		for (int i = 0; i < b.length; i++)
		{
			stmp = Integer.toHexString(b[i] & 0xFF);
			if (stmp.length() == 1)
			{
				hs += "0" + stmp;
			}
			else
			{
				hs += stmp;
			}
		}
		return hs.toUpperCase();
	}

	public static byte[] hex2byte(String hex) throws IllegalArgumentException
	{
		if (hex.length() % 2 != 0)
		{
			throw new IllegalArgumentException();
		}
		char[] arr = hex.toCharArray();
		byte[] b = new byte[hex.length() / 2];
		for (int i = 0, j = 0, l = hex.length(); i < l; i++, j++)
		{
			String swap = "" + arr[i++] + arr[i];
			int byteint = Integer.parseInt(swap, 16) & 0xFF;
			b[j] = new Integer(byteint).byteValue();
		}
		return b;
	}
	/**
	 * 公钥解密
	 * @param msg
	 * @param publicKey
	 * @return
	 * @throws Exception
	 */
	public static String decryptByPublicKey(String msg, String publicKey) throws Exception {
		//Log.e("msg",msg);
		//Log.e("publicKey",publicKey);
		byte[] decryptStrBase64 = RSAUtil.decryptBASE64(msg);
		//Log.e("decryptStrBase64", Arrays.toString(decryptStrBase64));
		byte[] decryptByte = RSAUtil.decryptByPublicKey(decryptStrBase64, publicKey);
		//Log.e("decryptByte", Arrays.toString(decryptByte));
		return new String(decryptByte,"utf-8");
	}
	    /**
	     * 解密<br>
	     * 用公钥解密
	     *
	     * @param data
	     * @param key
	     * @return
	     * @throws Exception
	     */
	    public static byte[] decryptByPublicKey(byte[] data, String key)

	            throws Exception {
			int MAX_ENCRYPT_BLOCK = 128;
			//	Log.e("data",Arrays.toString(data));
	        // 对密钥解密
	        byte[] keyBytes = decryptBASE64(key);
		//	Log.e("keyBytes",Arrays.toString(keyBytes));

	        // 取得公钥
	        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
	        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
	        Key publicKey = keyFactory.generatePublic(x509KeySpec);

	        // 对数据解密
	        Cipher cipher = Cipher.getInstance(MODE,"BC");
	        cipher.init(Cipher.DECRYPT_MODE, publicKey);
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int inputLen = data.length;
			int offSet = 0;
			byte[] cache;
			int i = 0;
			// 对数据分段解密
			while (inputLen - offSet > 0) {
				if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
					cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
				} else {
					cache = cipher.doFinal(data, offSet, inputLen - offSet);
				}
				out.write(cache, 0, cache.length);
				i++;
				offSet = i * MAX_ENCRYPT_BLOCK;
			}
			byte[] encryptData = out.toByteArray();
			out.close();
			return encryptData;
			//return cipher.doFinal(data);
	    }


	/**
	 * 解密<br>
	 * 用私钥解密
	 *
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptByPrivateKey(byte[] data, String key)
			throws Exception {
		int MAX_ENCRYPT_BLOCK = 128;
		// 对密钥解密
		byte[] keyBytes = decryptBASE64(key);

		// 取得私钥
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);

		// 对数据解密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int inputLen = data.length;
		int offSet = 0;
		byte[] cache;
		int i = 0;
		// 对数据分段解密
		while (inputLen - offSet > 0) {
			if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
				cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
			} else {
				cache = cipher.doFinal(data, offSet, inputLen - offSet);
			}
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * MAX_ENCRYPT_BLOCK;
		}
		byte[] encryptData = out.toByteArray();
		out.close();
		return encryptData;
	//	return cipher.doFinal(data);
	}

	    /**
	     * 加密<br>
	     * 用公钥加密
	     *
	     * @param data
	     * @param key
	     * @return
	     * @throws Exception
	     */
	    public static byte[] encryptByPublicKey(byte[] data, String key)
	            throws Exception {
	        // 对公钥解密
	        byte[] keyBytes = decryptBASE64(key);

	        // 取得公钥
	        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
	        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
	        Key publicKey = keyFactory.generatePublic(x509KeySpec);

	        // 对数据加密
	        Cipher cipher = Cipher.getInstance(MODE);
	        cipher.init(Cipher.ENCRYPT_MODE, publicKey);

	        return cipher.doFinal(data);
	    }


	/**
	 * 私钥解密
	 *
	 * @param data
	 * @param privateKey
	 * @return
	 * @throws Exception
	 */
//	public static String decryptByPrivateKey(String data, String privateKey)
//			throws Exception {
//		byte[] keyBytes = decryptBASE64(privateKey);
//
//		// 取得私钥
//		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
//		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
//		Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);
//		Cipher cipher = Cipher.getInstance("RSA");
//		cipher.init(Cipher.DECRYPT_MODE, privateKey);
//		//模长
//		int key_len = privateKey.getModulus().bitLength() / 8;
//		byte[] bytes = data.getBytes();
//		byte[] bcd = ASCII_To_BCD(bytes, bytes.length);
//		System.err.println(bcd.length);
//		//如果密文长度大于模长则要分组解密
//		String ming = "";
//		byte[][] arrays = splitArray(bcd, key_len);
//		for(byte[] arr : arrays){
//			ming += new String(cipher.doFinal(arr));
//		}
//		return ming;
//	}
	/**
	 * ASCII码转BCD码
	 *
	 */
	public static byte[] ASCII_To_BCD(byte[] ascii, int asc_len) {
		byte[] bcd = new byte[asc_len / 2];
		int j = 0;
		for (int i = 0; i < (asc_len + 1) / 2; i++) {
			bcd[i] = asc_to_bcd(ascii[j++]);
			bcd[i] = (byte) (((j >= asc_len) ? 0x00 : asc_to_bcd(ascii[j++])) + (bcd[i] << 4));
		}
		return bcd;
	}

	/**
	 *拆分数组
	 */
	public static byte[][] splitArray(byte[] data,int len){
		int x = data.length / len;
		int y = data.length % len;
		int z = 0;
		if(y!=0){
			z = 1;
		}
		byte[][] arrays = new byte[x+z][];
		byte[] arr;
		for(int i=0; i<x+z; i++){
			arr = new byte[len];
			if(i==x+z-1 && y!=0){
				System.arraycopy(data, i*len, arr, 0, y);
			}else{
				System.arraycopy(data, i*len, arr, 0, len);
			}
			arrays[i] = arr;
		}
		return arrays;
	}

	public static byte asc_to_bcd(byte asc) {
		byte bcd;

		if ((asc >= '0') && (asc <= '9'))
			bcd = (byte) (asc - '0');
		else if ((asc >= 'A') && (asc <= 'F'))
			bcd = (byte) (asc - 'A' + 10);
		else if ((asc >= 'a') && (asc <= 'f'))
			bcd = (byte) (asc - 'a' + 10);
		else
			bcd = (byte) (asc - 48);
		return bcd;
	}


	/**
	     * 加密<br>
	     * 用私钥加密
	     *
	     * @param data
	     * @param key
	     * @return
	     * @throws Exception
	     */
	    public static byte[] encryptByPrivateKey(byte[] data, String key)
	            throws Exception {
			int MAX_ENCRYPT_BLOCK = 117;
			// 对密钥解密
	        byte[] keyBytes = decryptBASE64(key);

	        // 取得私钥
	        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
	        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
	        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);

	        // 对数据加密
	        Cipher cipher = Cipher.getInstance("RSA/None/PKCS1Padding");
	        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
			int inputLen = data.length;
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			int offSet = 0;
			byte[] cache;
			int i = 0;
			// 对数据分段加密
			while (inputLen - offSet > 0) {
				if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
					cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
				} else {
					cache = cipher.doFinal(data, offSet, inputLen - offSet);
				}
				out.write(cache, 0, cache.length);
				i++;
				offSet = i * MAX_ENCRYPT_BLOCK;
			}
			byte[] encryptedData = out.toByteArray();
			out.close();
			return encryptedData;


			//return cipher.doFinal(data);
	    }

	    /**
	     * 取得私钥
	     *
	     * @param keyMap
	     * @return
	     * @throws Exception
	     */
	    public static String getPrivateKey(Map<String, Object> keyMap)
	            throws Exception {
	        Key key = (Key) keyMap.get(PRIVATE_KEY);

	        return encryptBASE64(key.getEncoded());
	    }

	    /**
	     * 取得公钥
	     *
	     * @param keyMap
	     * @return
	     * @throws Exception
	     */
	    public static String getPublicKey(Map<String, Object> keyMap)
	            throws Exception {
	        Key key = (Key) keyMap.get(PUBLIC_KEY);

	        return encryptBASE64(key.getEncoded());
	    }

	    /**
	     * 初始化密钥
	     *
	     * @return
	     * @throws Exception
	     */
	    public static Map<String, Object> initKey() throws Exception {
	        KeyPairGenerator keyPairGen = KeyPairGenerator
	                .getInstance(KEY_ALGORITHM);
	        keyPairGen.initialize(1024);

	        KeyPair keyPair = keyPairGen.generateKeyPair();

	        // 公钥
	        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();

	        // 私钥
	        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

	        Map<String, Object> keyMap = new HashMap<String, Object>(2);

	        keyMap.put(PUBLIC_KEY, publicKey);
	        keyMap.put(PRIVATE_KEY, privateKey);
	        return keyMap;
	    }

	public static String getSHA256Str(String str){
		MessageDigest messageDigest;
		String encdeStr = "";
		try {
			messageDigest = MessageDigest.getInstance("SHA-256");
			byte[] hash = messageDigest.digest(str.getBytes("UTF-8"));
			encdeStr = Hex.encodeHexString(hash);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return encdeStr;
	}
}
