package com.cn.thinkx.wecard.api.module.withdraw.suning.vo;

/**
 * 代付查询返回参数
 * 
 * @author xiaomei
 *
 */
public class WithdrawQuery {

	private String responseCode;
	private String responseMsg;
	private Content content;
	
	public String getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	public String getResponseMsg() {
		return responseMsg;
	}
	public void setResponseMsg(String responseMsg) {
		this.responseMsg = responseMsg;
	}
	public Content getContent() {
		return content;
	}
	public void setContent(Content content) {
		this.content = content;
	}
	
}
