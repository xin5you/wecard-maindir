package com.cn.thinkx.integrationpay.shenxinpay.utils;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Locale;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.commons.codec.binary.Base64;

public class CryptUtil {
	public static byte[] encrypt(byte[] src, byte[] key) throws RuntimeException {
		try {
			SecureRandom sr = new SecureRandom();

			DESKeySpec dks = new DESKeySpec(key);

			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey securekey = keyFactory.generateSecret(dks);

			Cipher cipher = Cipher.getInstance("DES");

			cipher.init(1, securekey, sr);

			return cipher.doFinal(src);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static byte[] decrypt(byte[] src, byte[] key) throws RuntimeException {
		try {
			SecureRandom sr = new SecureRandom();

			DESKeySpec dks = new DESKeySpec(key);

			SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
			SecretKey securekey = keyFactory.generateSecret(dks);

			Cipher cipher = Cipher.getInstance("DES");

			cipher.init(2, securekey, sr);

			return cipher.doFinal(src);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	public static final String decrypt(String data, String key) throws Exception {
		return new String(decrypt(hex2byte(data.getBytes()), key.getBytes()));
	}

	public static final String encrypt(String data, String key) {
		if (data != null)
			try {
				return byte2hex(encrypt(data.getBytes(), key.getBytes()));
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		return null;
	}

	private static String byte2hex(byte[] b) {
		StringBuilder hs = new StringBuilder();

		for (int n = 0; (b != null) && (n < b.length); n++) {
			String stmp = Integer.toHexString(b[n] & 0xFF);
			if (stmp.length() == 1)
				hs.append('0');
			hs.append(stmp);
		}
		return hs.toString().toUpperCase();
	}

	public static byte[] hex2byte(byte[] b) {
		if (b.length % 2 != 0)
			throw new IllegalArgumentException();
		byte[] b2 = new byte[b.length / 2];
		for (int n = 0; n < b.length; n += 2) {
			String item = new String(b, n, 2);
			b2[(n / 2)] = (byte) Integer.parseInt(item, 16);
		}
		return b2;
	}

	public static String GetEncodeStr(String str) {
		byte[] bytes = (byte[]) null;
		try {
			bytes = str.getBytes("UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		StringBuilder sb = new StringBuilder(bytes.length * 2);

		for (int i = 0; i < bytes.length; i++) {
			sb.append("0123456789ABCDEF".charAt((bytes[i] & 0xF0) >> 4));
			sb.append("0123456789ABCDEF".charAt((bytes[i] & 0xF) >> 0));
		}
		return sb.toString();
	}

	public static String GetDecodeStr(String bytes) {
		String sRes = "";
		ByteArrayOutputStream baos = new ByteArrayOutputStream(bytes.length() / 2);

		for (int i = 0; i < bytes.length(); i += 2)
			baos.write("0123456789ABCDEF".indexOf(bytes.charAt(i)) << 4 | "0123456789ABCDEF".indexOf(bytes.charAt(i + 1)));
		try {
			sRes = new String(baos.toByteArray(), "UTF-8");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return sRes;
	}

	public static byte[] parseBytesByHexString(final String hexString) {
		if (hexString == null || hexString.length() == 0 || hexString.equals("")) {
			return new byte[0];
		}
		if (hexString.length() % 2 != 0) {
			throw new IllegalArgumentException("hexString length must be an even number!");
		}
		StringBuffer sb = new StringBuffer(hexString);
		StringBuffer sb1 = new StringBuffer(2);
		int n = hexString.length() / 2;
		byte[] bytes = new byte[n];
		for (int i = 0; i < n; i++) {
			if (sb1.length() > 1) {
				sb1.deleteCharAt(0);
				sb1.deleteCharAt(0);
			}
			sb1.append(sb.charAt(0));
			sb1.append(sb.charAt(1));
			sb.deleteCharAt(0);
			sb.deleteCharAt(0);
			bytes[i] = (byte) Integer.parseInt(sb1.toString(), 16);
		}
		return bytes;
	}

	public static String hexStr2Str(String hexStr) {
		hexStr = hexStr.toString().trim().replace(" ", "").toUpperCase(Locale.US);
		char[] hexs = hexStr.toCharArray();
		byte[] bytes = new byte[hexStr.length() / 2];
		int iTmp = 0x00;

		for (int i = 0; i < bytes.length; i++) {
			iTmp = "0123456789ABCDEF".indexOf(hexs[2 * i]) << 4;
			iTmp |= "0123456789ABCDEF".indexOf(hexs[2 * i + 1]);
			bytes[i] = (byte) (iTmp & 0xFF);
		}
		return new String(bytes);
	}

	/**
	 * 读取公钥
	 * @param pubFilePath
	 * @param algorithm
	 * @return
	 */
	public static PublicKey getPublicKey(String pubFilePath, String algorithm){
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(pubFilePath);
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
			StringBuilder sb = new StringBuilder();
			String readLine = null;
			while ((readLine = br.readLine()) != null) {
				if (readLine.charAt(0) == '-') {
					continue;
				} else {
					sb.append(readLine);
					sb.append('\r');
				}
			}

			X509EncodedKeySpec pubX509 = new X509EncodedKeySpec(Base64.decodeBase64(sb.toString().getBytes()));
			KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
			PublicKey publicKey = keyFactory.generatePublic(pubX509);

			return publicKey;
		} catch (FileNotFoundException e) {
			System.out.println("公钥文件不存在");
			return null;
		} catch (IOException e) {
			System.out.println("读取公钥异常");
			return null;
		} catch (NoSuchAlgorithmException e) {
			System.out.println("生成密钥工厂异常");
			return null;
		} catch (InvalidKeySpecException e) {
			System.out.println("生成公钥对象异常");
			return null;
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (IOException e) {
				System.out.println("公钥文件读取流关闭异常");
				return null;
			}
		}
	}

	/**
	 * 读取私钥
	 * @param inputStream
	 * @param keyAlgorithm
	 * @return
	 */
	public static PrivateKey getPrivateKey(String priFilePath, String algorithm){
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(priFilePath);
			BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
			StringBuilder sb = new StringBuilder();
			String readLine = null;
			while ((readLine = br.readLine()) != null) {
				if (readLine.charAt(0) == '-') {
					continue;
				} else {
					sb.append(readLine);
					sb.append('\r');
				}
			}

			PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(Base64.decodeBase64(sb.toString().getBytes()));
			KeyFactory keyFactory = KeyFactory.getInstance(algorithm);
			PrivateKey privateKey = keyFactory.generatePrivate(priPKCS8);

			return privateKey;
		}  catch (FileNotFoundException e) {
			System.out.println("私钥文件不存在");
			return null;
		} catch (IOException e) {
			System.out.println("读取私钥异常");
			return null;
		} catch (NoSuchAlgorithmException e) {
			System.out.println("生成密钥工厂异常");
			return null;
		} catch (InvalidKeySpecException e) {
			System.out.println("生成私钥对象异常");
			return null;
		} finally {
			try {
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (IOException e) {
				System.out.println("私钥文件读取流关闭异常");
				return null;
			}
		}
	}

	/**
	 * 加密交易报文
	 * @param plainBytes
	 * @param pubKeyFilePath
	 * @param cipherAlgorithm
	 * @return
	 */
	public static byte[] encryptTransContent(byte[] plainBytes, String pubKeyFilePath, String cipherAlgorithm){
		int keyByteSize = 2048 / 8;
		int encryptBlockSize = keyByteSize - 11;
		int nBlock = plainBytes.length / encryptBlockSize;
		if((plainBytes.length % encryptBlockSize) != 0){
			nBlock += 1;
		}

		try {
			PublicKey publicKey = getPublicKey(pubKeyFilePath, CryptConstant.ALGORRITHM_WITH_RSA);
			Cipher cipher = Cipher.getInstance(cipherAlgorithm);
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);

			ByteArrayOutputStream outbuf = new ByteArrayOutputStream(nBlock * keyByteSize);
			for (int offset = 0; offset < plainBytes.length; offset += encryptBlockSize) {
				int inputLen = plainBytes.length - offset;
				if (inputLen > encryptBlockSize) {
					inputLen = encryptBlockSize;
				}
				byte[] encryptedBlock = cipher.doFinal(plainBytes, offset, inputLen);
				outbuf.write(encryptedBlock);
			}

			outbuf.flush();
			outbuf.close();
			return outbuf.toByteArray();
		} catch (Exception e) {
			System.out.println("报文加密异常");
			return null;
		}
	}

	/**
	 * 解密交易报文
	 * @param encryptedBytes
	 * @param priKeyFilePath
	 * @param cipherAlgorithm
	 * @return
	 */
	public static byte[] decryptTransContent(byte[] encryptedBytes, String priKeyFilePath, String cipherAlgorithm){
		int keyByteSize = 2048 / 8;
		int decryptBlockSize = keyByteSize - 11;
		int nBlock = encryptedBytes.length / keyByteSize;

		try {
			PrivateKey privateKey = getPrivateKey(priKeyFilePath, CryptConstant.ALGORRITHM_WITH_RSA);
			Cipher cipher = Cipher.getInstance(cipherAlgorithm);
			cipher.init(Cipher.DECRYPT_MODE, privateKey);

			ByteArrayOutputStream outbuf = new ByteArrayOutputStream(nBlock * decryptBlockSize);
			for (int offset = 0; offset < encryptedBytes.length; offset += keyByteSize) {
				int inputLen = encryptedBytes.length - offset;
				if (inputLen > keyByteSize) {
					inputLen = keyByteSize;
				}
				byte[] decryptedBlock = cipher.doFinal(encryptedBytes, offset, inputLen);
				outbuf.write(decryptedBlock);
			}

			outbuf.flush();
			outbuf.close();
			return outbuf.toByteArray();
		} catch (Exception e) {
			System.out.println("报文解密异常");
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 签名交易报文
	 * @param plainBytes
	 * @param priKeyFilePath
	 * @param signAlgorithm
	 * @return
	 */
	public static byte[] signTransContent(byte[] plainBytes, String priKeyFilePath, String signAlgorithm){
		try {
			PrivateKey privateKey = getPrivateKey(priKeyFilePath, CryptConstant.ALGORRITHM_WITH_RSA);
			Signature signature = Signature.getInstance(signAlgorithm);
			signature.initSign(privateKey);
			signature.update(plainBytes);
			byte[] signBytes = signature.sign();

			return signBytes;
		} catch (NoSuchAlgorithmException e) {
			System.out.println("数字签名算法不识别");
			return null;
		} catch (InvalidKeyException e) {
			System.out.println("数字签名私钥无效");
			return null;
		} catch (SignatureException e) {
			System.out.println("数字签名异常");
			return null;
		}
	}

	/**
	 * 验证报文签名
	 * @param plainBytes
	 * @param pubKeyFilePath
	 * @param signAlgorithm
	 * @return
	 */
	public static boolean verifyTransContent(byte[] plainBytes, byte[] signBytes, String pubKeyFilePath, String signAlgorithm){
		boolean isValid = false;
		try {
			PublicKey publicKey = getPublicKey(pubKeyFilePath, CryptConstant.ALGORRITHM_WITH_RSA);
			Signature signature = Signature.getInstance(signAlgorithm);
			signature.initVerify(publicKey);
			signature.update(plainBytes);
			isValid = signature.verify(signBytes);
			return isValid;
		} catch (NoSuchAlgorithmException e) {
			System.out.println("验证签名算法不识别");
			return false;
		} catch (InvalidKeyException e) {
			System.out.println("验证签名公钥无效");
			return false;
		} catch (SignatureException e) {
			System.out.println("验证签名异常");
			return false;
		}
	}
}