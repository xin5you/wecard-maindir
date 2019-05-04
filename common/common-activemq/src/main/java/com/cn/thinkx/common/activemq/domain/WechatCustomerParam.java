
package com.cn.thinkx.common.activemq.domain;

/**
 * 
 * @描述: 微信客服消息参数封装类 
 * @作者: zhuqiuyou
 * @创建时间: 2017-1-20,上午11:09:03
 * @版本号: V1.0
 */
public class WechatCustomerParam {
	
	/**
	 * 微信号
	 */
	private String acountName;

	/** 发件人 **/
	private String fromOpenId;
	
	/** 收件人 **/
	private String toOpenId;
	
	/** 主题 **/
	private String subject;
	
	/** 消息内容 **/
	private String content;

	public String getAcountName() {
		return acountName;
	}

	public void setAcountName(String acountName) {
		this.acountName = acountName;
	}

	public String getFromOpenId() {
		return fromOpenId;
	}

	public void setFromOpenId(String fromOpenId) {
		this.fromOpenId = fromOpenId;
	}

	public String getToOpenId() {
		return toOpenId;
	}

	public void setToOpenId(String toOpenId) {
		this.toOpenId = toOpenId;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
}
