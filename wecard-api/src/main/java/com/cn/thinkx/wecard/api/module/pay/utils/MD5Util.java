package com.cn.thinkx.wecard.api.module.pay.utils;

import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
	public static String getMD5Str(String sMsg) {
		byte[] msg = sMsg.getBytes();
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(msg);
		} catch (NoSuchAlgorithmException localNoSuchAlgorithmException) {
		}
		byte[] b = messageDigest.digest();
		return new String(Base64.encodeBase64(b));
	}

	public static String getMD5Str1(String sMsg) throws UnsupportedEncodingException {
		byte[] msg = sMsg.getBytes("UTF-8");
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(msg);
		} catch (NoSuchAlgorithmException localNoSuchAlgorithmException) {
		}
		byte[] b = messageDigest.digest();
		return new String(Base64.encodeBase64(b));
	}

	public static String getMD5Str2(String sMsg) {

		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(sMsg.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		byte[] byteArray = messageDigest.digest();

		StringBuffer md5StrBuff = new StringBuffer();

		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1)
				md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
			else {
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
			}
		}
		return md5StrBuff.toString();
	}

	/**
	 * Method MD5Encode Description 说明：
	 */
	public static String MD5Encode(String origin) {
		String resultString = null;

		try {
			resultString = origin;

			MessageDigest md = MessageDigest.getInstance("MD5");

			resultString = byteArrayToHexString(md.digest(resultString.getBytes("utf-8")));
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultString;
	}

	/**
	 * Method byteToHexString Description 说明：
	 *
	 * @param b
	 *            说明：
	 * @return 返回值说明：
	 */
	private static String byteToHexString(byte b) {
		int n = b;

		if (n < 0) {
			n += 256;
		}

		int d1 = n >>> 4;
		int d2 = n % 16;

		return hexDigits[d1] + hexDigits[d2];
	}

	/**
	 * Field hexDigits Description
	 */
	private final static String[] hexDigits = { "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d",
			"e", "f" };

	/**
	 * Method byteArrayToHexString Description 说明：
	 *
	 * @param b
	 *            说明：
	 * @return 返回值说明：
	 */
	public static String byteArrayToHexString(byte[] b) {
		StringBuilder resultSb = new StringBuilder();

		for (byte aB : b) {
			resultSb.append(byteToHexString(aB));
		}

		return resultSb.toString();
	}
}