package com.cn.thinkx.wecard.customer.module.checkstand.vo;

import java.io.Serializable;

/**
 * 收银台订单 base domain
 * @author zqy
 */
public class OrderBaseTxnResp implements Serializable {

	private static final long serialVersionUID = -8612385952903368976L;
	private String channel;
	private String respResult;
	private String info;


	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getRespResult() {
		return respResult;
	}

	public void setRespResult(String respResult) {
		this.respResult = respResult;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}
	
}
