package com.cn.thinkx.common.service.module.jiafupay.util;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import javax.crypto.Cipher;

import com.cn.thinkx.pms.base.utils.StringUtil;

/**
 * @Description: RSA工具
 * @author xiaomei
 * @date 2018年4月16日
 */
public class RSAUtil {

	/** */
	/**
	 * 加密算法RSA
	 */
	public static final String KEY_ALGORITHM = "RSA";

	/** */
	/**
	 * 签名算法
	 */
	public static final String SIGNATURE_ALGORITHM_MD5 = "MD5withRSA";
	public static final String SIGNATURE_ALGORITHM_SHA1 = "SHA1withRSA";


	/** */
	/**
	 * 获取私钥的key
	 */
	private static final String PRIVATE_KEY = "RSAPrivateKey";

	/** */
	/**
	 * RSA最大加密明文大小
	 */
	private static final int MAX_ENCRYPT_BLOCK = 117;

	/** */
	/**
	 * RSA最大解密密文大小
	 */
	private static final int MAX_DECRYPT_BLOCK = 128;

	/** */
	/**
	 * <p>
	 * 生成密钥对(公钥和私钥)
	 * </p>
	 *
	 * @return
	 * @throws Exception
	 */
	public static Map<String, Object> genKeyPair(String orgPublicKey) throws Exception {
		KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance(KEY_ALGORITHM);
		keyPairGen.initialize(1024);
		KeyPair keyPair = keyPairGen.generateKeyPair();
		RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
		// RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
		Map<String, Object> keyMap = new HashMap<String, Object>(2);
		keyMap.put(orgPublicKey, publicKey);
		// keyMap.put(PRIVATE_KEY, privateKey);
		return keyMap;
	}

	/**
	 * <p>
	 * 用私钥对信息生成数字签名
	 * </p>
	 *
	 * @param data
	 *            已加密数据
	 * @param privateKey
	 *            私钥(BASE64编码)
	 *
	 * @return
	 * @throws Exception
	 */
	public static String sign(byte[] data, String privateKey, String algorithm) throws Exception {
		byte[] keyBytes = Base64Util.decode(privateKey);
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
		Signature signature = Signature.getInstance(algorithm);
		signature.initSign(privateK);
		signature.update(data);
		return Base64Util.encode(signature.sign());
	}
	public static String sign(byte[] data, String privateKey) throws Exception {
		return sign(data, privateKey, SIGNATURE_ALGORITHM_SHA1);
	}


	/** */
	/**
	 * <p>
	 * 校验数字签名
	 * </p>
	 *
	 * @param data
	 *            已加密数据
	 * @param publicKey
	 *            公钥(BASE64编码)
	 * @param sign
	 *            数字签名
	 *
	 * @return
	 * @throws Exception
	 *
	 */
	public static boolean verify(byte[] data, String publicKey, String sign, String algorithm) throws Exception {
		byte[] keyBytes = Base64Util.decode(publicKey);
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		PublicKey publicK = keyFactory.generatePublic(keySpec);
		Signature signature = Signature.getInstance(algorithm);
		signature.initVerify(publicK);
		signature.update(data);
		return signature.verify(Base64Util.decode(sign));
	}

	public static boolean verify(byte[] data, String publicKey, String sign) throws Exception {
		return verify(data, publicKey, sign, SIGNATURE_ALGORITHM_SHA1);
	}


	/** */
	/**
	 * <P>
	 * 私钥解密
	 * </p>
	 *
	 * @param encryptedData
	 *            已加密数据
	 * @param privateKey
	 *            私钥(BASE64编码)
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptByPrivateKey(byte[] encryptedData, String privateKey) throws Exception {
		byte[] keyBytes = Base64Util.decode(privateKey);
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, privateK);
		int inputLen = encryptedData.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		// 对数据分段解密
		while (inputLen - offSet > 0) {
			if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
				cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
			} else {
				cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
			}
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * MAX_DECRYPT_BLOCK;
		}
		byte[] decryptedData = out.toByteArray();
		out.close();
		return decryptedData;
	}

	/** */
	/**
	 * <p>
	 * 公钥解密
	 * </p>
	 *
	 * @param encryptedData
	 *            已加密数据
	 * @param publicKey
	 *            公钥(BASE64编码)
	 * @return
	 * @throws Exception
	 */
	public static byte[] decryptByPublicKey(byte[] encryptedData, String publicKey) throws Exception {
		byte[] keyBytes = Base64Util.decode(publicKey);
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key publicK = keyFactory.generatePublic(x509KeySpec);
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, publicK);
		int inputLen = encryptedData.length;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int offSet = 0;
		byte[] cache;
		int i = 0;
		// 对数据分段解密
		while (inputLen - offSet > 0) {
			if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
				cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
			} else {
				cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
			}
			out.write(cache, 0, cache.length);
			i++;
			offSet = i * MAX_DECRYPT_BLOCK;
		}
		byte[] decryptedData = out.toByteArray();
		out.close();
		return decryptedData;
	}

	/** */
	/**
	 * <p>
	 * 公钥加密
	 * </p>
	 *
	 * @param data
	 *            源数据
	 * @param publicKey
	 *            公钥(BASE64编码)
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptByPublicKey(byte[] data, String publicKey) throws Exception {
		// byte[] keyBytes = Base64Utils.decode(publicKey);
		byte[] keyBytes = Base64Util.decode(publicKey);

		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);

		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key publicK = keyFactory.generatePublic(x509KeySpec);
		// 对数据加密
		// Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		Cipher cipher = Cipher.getInstance(KEY_ALGORITHM);
		cipher.init(Cipher.ENCRYPT_MODE, publicK);

		return cipher.doFinal(data);
	}

	/** */
	/**
	 * <p>
	 * 私钥加密
	 * </p>
	 *
	 * @param data
	 *            源数据
	 * @param privateKey
	 *            私钥(BASE64编码)
	 * @return
	 * @throws Exception
	 */
	public static byte[] encryptByPrivateKey(byte[] data, String privateKey) throws Exception {

		byte[] keyBytes = Base64Util.decode(privateKey);
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, privateK);
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
		//byte[] encryptedData = out.toByteArray();
		out.close();

		byte[] epByte = cipher.doFinal(data);

		return Base64Util.encodeByte(epByte);
	}

	/** */
	/**
	 * <p>
	 * 获取私钥
	 * </p>
	 *
	 * @param keyMap
	 *            密钥对
	 * @return
	 * @throws Exception
	 */
	public static String getPrivateKey(Map<String, Object> keyMap) throws Exception {
		Key key = (Key) keyMap.get(PRIVATE_KEY);
		return Base64Util.encode(key.getEncoded());
	}

	/** */
	/**
	 * <p>
	 * 获取公钥
	 * </p>
	 *
	 * @param keyMap
	 *            密钥对
	 * @return
	 * @throws Exception
	 */
	public static String getPublicKey(Map<String, Object> keyMap, String publicKey) throws Exception {
		Key key = (Key) keyMap.get(publicKey);
		return Base64Util.encode(key.getEncoded());
	}
	
	public static void getProperty(SortedMap<String, String> map, Object obj, Class<?> clazz) {
		// 获取f对象对应类中的所有属性域
		Field[] fields = clazz.getDeclaredFields();
		for (int i = 0, len = fields.length; i < len; i++) {
			String varName = fields[i].getName();
			// 过滤签名字段
			if ("sign".equals(varName) || "serialVersionUID".equals(varName))
				continue;
			try {
				// 获取原来的访问控制权限
				boolean accessFlag = fields[i].isAccessible();
				// 修改访问控制权限
				fields[i].setAccessible(true);
				// 获取在对象f中属性fields[i]对应的对象中的变量
				Object o = fields[i].get(obj);
				if (o != null && !StringUtil.isNullOrEmpty(o.toString()))
					map.put(varName, o.toString());
				// 恢复访问控制权限
				fields[i].setAccessible(accessFlag);
			} catch (IllegalArgumentException ex) {
				ex.printStackTrace();
			} catch (IllegalAccessException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	/**
	 * 拼接待加密字符串  (嘉福)
	 * 
	 * @param jfResp
	 * @return
	 */
	public static String jfParamMap(Object obj) {
		SortedMap<String, String> map = new TreeMap<String, String>();
		if (obj != null) {
			getProperty(map, obj, obj.getClass());// 将当前类属性及其值放入map
			if (obj.getClass().getSuperclass() != null && obj.getClass().getGenericSuperclass() != null) {
				getProperty(map, obj, obj.getClass().getSuperclass());// 将当前类父类属性及其值放入map
				if (obj.getClass().getSuperclass().getSuperclass() != null && obj.getClass().getGenericSuperclass().getClass().getGenericSuperclass() != null)
					getProperty(map, obj, obj.getClass().getSuperclass().getSuperclass());// 将当前类父类属性及其值放入map
			}
		}
		//按字典排序法拼接参数
		StringBuilder forSignResp = new StringBuilder();
		for (String key : map.keySet())
			forSignResp.append(key).append("=").append(map.get(key)).append("&");
		forSignResp.deleteCharAt(forSignResp.length()-1);
		return forSignResp.toString();
	}
	
	public static void main(String[] args) {
		String privateKey = "MIICeAIBADANBgkqhkiG9w0BAQEFAASCAmIwggJeAgEAAoGBAM2zceGVxyMgRFlj"
				+"gH1EPpCTF1NwuDCzmfe+hAdMNt6mi+gWP7DRgHcM+W97M4y4fqvrbE3qMCLYxWV6"
				+"4PV9vCVt7DnwGRlTUI2uapH+qygKikkEsW64ExrNissezXvd8FpA+cEKGYe8dfuF"
				+"TmvkUBYsyFpNguVh4l8MMd97zlv5AgMBAAECgYAwuNc5ioN/g24NCi9t/FMhZWW6"
				+"AtHt3yyR7NNS8y9v71zmHRb657SY/j66tCDSrQR77ihaECVUq6mdzswn+Z8thbJN"
				+"SF2vaS7ouij1UcqwUiUMN6JOG0ulJqsUILlUAOtLV8SJhhfMgp9LltyRDEJnx0fh"
				+"lVeoPgfJnjvnFN9UgQJBAOjyBYFXTG3cOkgSZJyu1khoAk/5NAJ5wnWSLIwYzdKo"
				+"6+CmtCLe3lS8tPf/GhtDJQ7QDpBZZuOQcluzjyycj7ECQQDiDyZJzLzvPsMpoJnJ"
				+"Wu4eVSnZzP8hsH30uHhhs5iNJVrmCm7NxvZ68JGR6I+7G42HdMbxxDnHIVRW0da4"
				+"VqrJAkEAhbIScs+V8B/L232i/UJjfh8j5J6UuS8E6tHAe4/o4ZVN/Bvxm2nqlRvR"
				+"idyCMU9yP3QuR67oHIfU+sitzd71oQJBAJOxljBtZZtiBBx0Y0Wc9So4Ngmo15/p"
				+"McHrrCsZLbg6CbN2aYJkVbFo75LGwPFcRRGVCE35CE7DxdibbJAvf5ECQQDGS9NR"
				+"qExKyfkCZkEASULQpYlcMyQ+EU2O4qRygIODdDFOB9VhnKVYm6RSKybBrYP167sG"
				+"TgYwhPssNxulE2VL";

		String publicKey ="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDNs3HhlccjIERZY4B9RD6QkxdT"
				+"cLgws5n3voQHTDbepovoFj+w0YB3DPlvezOMuH6r62xN6jAi2MVleuD1fbwlbew5"
				+"8BkZU1CNrmqR/qsoCopJBLFuuBMazYrLHs173fBaQPnBChmHvHX7hU5r5FAWLMha"
				+"TYLlYeJfDDHfe85b+QIDAQAB";

		try {
			String sign = RSAUtil.sign("待签名文本".getBytes(), privateKey);
			boolean flag = RSAUtil.verify("待签名文本".getBytes(), publicKey, sign);
			System.out.println(flag);
		} catch (Exception e) {
			
		}
	}
	/* StringBuffer chkValueSb = new StringBuffer();
	 String timestamp = DateUtil.getDateNoSs();
	 chkValueSb.append("CPIC").append(",").append("PCIC").append(",").append("13901620812")
	 .append(",").append("100.00").append(",").append("").append(",")
	 .append(timestamp).append(",").append("1000000011").append(",").append(RSAUtils.MD5_KEY);

	 System.out.println("chkValuesb="+chkValueSb.toString());

	 String chkValueMD5 = MD5Util.sign(chkValueSb.toString()).toLowerCase();

	 Map<String, Object> keyMap = RSAUtils.genKeyPair(PUBLIC_KEY);
	 String publicKey = RSAUtils.getPublicKey(keyMap);
	 String privateKey = RSAUtils.getPrivateKey(keyMap);

	 System.out.println("publicKey="+publicKey);
	 System.out.println("privateKey="+privateKey);

	 */
	/*String  sign="QTe4yXCurkOp19H6xHt3j3MkeyH7jLd4yM%2BzIdv7CkHtw5pzDLoBvWEp0tbkgmqqMY0LVX44ETVb%0AaacH%2BTXBSjFQWASBZOoCl/qPpv9gx6bG42rxPkhunxqAck0x/6pZ/EhoTeBMWhflOX0ojCHucIxO%0A08s3hxReEpc6N69lwzM%3D%0A";
	String publicKey="MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCiuZnq5UZx/5i0VKS8sU7TU52hVYAxNY7GeZElAjVGl4FmuiN7mrMIcpuGyDiZ5XLL8JaKZQzfvi1ri5vnslRJlf2d92HPTv1Sbhlli1uVFh862QtEdp3LilqDh0ZojL9SFRipSqgVq4E4cmcdaAgyXFnKLaHegHhRKzcwhnXVEwIDAQAB";
	String s="entName=北京首都在线科技有限公司&osid=777668130&ticket=capitalonline&timestamp=1474363693";

	System.out.println(verify(s.getBytes("UTF-8"), publicKey, sign));*/
	//	 String aaa =
	//	 "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC/LehDzIiBZKZieF+Z2rE0V0iuy3ZWrI7Nd8Clyy4m1pQdxrJjzAiKyRSyd0+cjoM2zm2pWodJcoOxljkPcpSYxRLbt9VG5rtYRV8ZpNRK+qxoWRBAEa4V4l7bZygCWbzAVodLpXIw9dtJS8s1eKTyLcfFMLgm7vWjclkFOBN0uQIDAQAB";
	//	 BASE64Decoder decoder = new BASE64Decoder();
	//	 byte[] keyBytes = decoder.decodeBuffer(aaa);
	//	
	//	 X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
	//	 KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
	//	 Key publicK = keyFactory.generatePublic(x509KeySpec);
	//	 System.out.println(publicK);
	//	
	//	 Cipher cipher = Cipher.getInstance("RSA");
	//	 cipher.init(Cipher.ENCRYPT_MODE, publicK);
	//	 System.out.println(Base64Util.encode(cipher.doFinal("7dccf8e0727046ef69988e233e18eda9".getBytes())));
	//	// System.out.println(new
	//	 String(RSAUtils.encryptByPublicKey(chkValueMD5.getBytes(), publicKey)));

}
