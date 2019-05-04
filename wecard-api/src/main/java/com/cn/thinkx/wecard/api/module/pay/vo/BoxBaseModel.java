package com.cn.thinkx.wecard.api.module.pay.vo;

/**
 * 扫描盒子公共请求参数
 * @author zqy
 *
 */
public class BoxBaseModel {

	private String nonce_str;
	
	private String sign;

	public String getNonce_str() {
		return nonce_str;
	}

	public String getSign() {
		return sign;
	}

	public void setNonce_str(String nonce_str) {
		this.nonce_str = nonce_str;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}
}
