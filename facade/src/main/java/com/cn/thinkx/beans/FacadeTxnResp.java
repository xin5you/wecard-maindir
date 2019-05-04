package com.cn.thinkx.beans;

/**
 * 交易返回对象，用于接收交易返回时转换json字符串(初始化为code:99 info:网络异常)
 * 
 * @author pucker
 *
 */
public class FacadeTxnResp {

	private String code;
	private String info;
	
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

}
