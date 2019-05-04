package com.cn.thinkx.wecard.api.module.withdraw.suning.vo;

/**
 * 代付回调参数
 * 
 * @author xiaomei
 *
 */
public class BatchDataNotify {

	private Content content;
	private String sign;
	private String sign_type;
	private String vk_version;
	
	public Content getContent() {
		return content;
	}
	public void setContent(Content content) {
		this.content = content;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getSign_type() {
		return sign_type;
	}
	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}
	public String getVk_version() {
		return vk_version;
	}
	public void setVk_version(String vk_version) {
		this.vk_version = vk_version;
	}
	
}
