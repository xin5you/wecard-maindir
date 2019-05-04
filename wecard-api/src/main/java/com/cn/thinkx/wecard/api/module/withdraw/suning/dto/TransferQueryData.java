/*
 * Copyright (C), 2002-2016, 苏宁易购电子商务有限公司
 * FileName: BatchTransferData.java
 * Author:   16031333
 * Date:     2016年5月25日 上午10:30:43
 * Description: //模块目的、功能描述      
 * History: //修改记录
 * <author>      <time>      <version>    <desc>
 * 修改人姓名             修改时间            版本号                  描述
 */
package com.cn.thinkx.wecard.api.module.withdraw.suning.dto;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.util.HashMap;
import java.util.Map;

import com.cn.thinkx.wecard.api.module.withdraw.suning.utils.HttpClientQueryUtil;
import com.suning.epps.codec.Digest;
import com.suning.epps.codec.RSAUtil;

/**
 * 〈一句话功能简述〉<br>
 * 批量转账 数据拼写
 *
 * @author 16031333
 * @see [相关类/方法]（可选）
 * @since [产品/模块版本] （可选）
 */
public class TransferQueryData {

	private String publicKeyIndex = "xpy1";
	private String signAlgorithm = "RSA";
	private String merchantNo = "suning";
	private String inputCharset = "UTF-8";
	
	private String batchNo = "11012345128";
	private String payMerchantNo = "70055676";

	/**
	 * 请求转账网关
	 * 
	 * @param baseUrl
	 *            请求业务数据 batchNum 请求的批次数目 detailNum 单批次中的明细数目
	 * @return signature 签名
	 * @throws UnsupportedEncodingException
	 * @throws java.security.InvalidKeyException
	 * @see [相关类/方法]（可选）
	 * @since [产品/模块版本] （可选）
	 */
	public String batchWithDraw(String baseUrl)
			throws InvalidKeyException, NoSuchAlgorithmException,
			InvalidKeySpecException, SignatureException,
			UnsupportedEncodingException, java.security.InvalidKeyException {

		String singnature = calculateSign();
		String responseStr = HttpClientQueryUtil.post(publicKeyIndex, signAlgorithm,
				merchantNo, inputCharset, baseUrl, singnature, batchNo,payMerchantNo);
		return responseStr;
	}

	/**
	 * 计算签名
	 * 
	 * @param body
	 *            请求业务数据
	 * @return signature 签名
	 * @throws java.security.InvalidKeyException
	 * @see [相关类/方法]（可选）
	 * @since [产品/模块版本] （可选）
	 */
	public String calculateSign() throws NoSuchAlgorithmException,
			InvalidKeySpecException, InvalidKeyException, SignatureException,
			java.security.InvalidKeyException {
		Map<String, String> signMap = new HashMap<String, String>();
		signMap.put("merchantNo", "suning");
	    signMap.put("publicKeyIndex", "xpy1");
	    signMap.put("inputCharset", "UTF-8");
		signMap.put("batchNo", batchNo);
		signMap.put("payMerchantNo", payMerchantNo);
		String wagKeyString = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBAIWvIOflNkPsEvGWCvvTM4tlWcodoPbC52Q6EXsv9UJyOqbzfX9u9xGRLiUv3fOs9J02QQPK6ZPQiR0g8RvPv2858bh5finE13iwIuYTpgSRZVi2Kn7goer4qqwXD_TjM1B6PIpOylJksbF9RESZP0A8cG2twJprdZ54xYj4HIGvAgMBAAECgYAPG-b9LpO-g3z0nv-ozIsD0zWduVGK8iZS1plJMfdnRh_I5LYnY_Q6oQz1GP7d3otbBVm9wv45PZVxnFqDySwajI4uAP9ZZQ8RHrPTNttFgLm3OQ0shbIUhBi2vXorxicR-EnS4qWpCOP10o5JrlpieZ295S2p7Dn_xmIoRgPRKQJBAN4ilfdxuEU3E-eiPo98gUXFPpCCCXKla4JMvN2R6em8d0MVYT8g0rXoXS44UnEg0vOoJ7ulPh5Col6ilqR2op0CQQCaEIGvc2PDa8jHXSmDuwpl4ogqafNyY7FCjPqWvlG-_auU0qaKBuVhIEMuy-3ZUMFFCsmGkMOKr_7ACTW3bM27AkEAwCihHIYmhtGniWhjwBJPbgC8J5wl-iQ5RWWGuBGCjSz46nIzRr3pKW2SNeqI_s4LTrY3cO74NoskFMOHl0v9TQJARmWofH0jZtZHZiGBqLm8pJWAVrEXFnvLMXetwVexjq3myxf-FS_VfC37xNRWGGi4B05Ii352e1az9xe-PdQvpQJATWPfQc1IR-cefoAvEcUyQlTsthVQkJ3wUpRMEosw2V5a1f9euwhJXJDf6ca8zOhtyfXuTIag1YturYfKyXgY3w";
		String digest = Digest.digest(Digest.mapToString(Digest
				.treeMap(signMap)));
		PrivateKey privateKey = RSAUtil.getPrivateKey(wagKeyString);
		String signature = RSAUtil.sign(digest, privateKey);
		return signature;
	}
	
}
