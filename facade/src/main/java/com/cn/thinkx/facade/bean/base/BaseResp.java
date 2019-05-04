package com.cn.thinkx.facade.bean.base;

/**
 * 交易返回对象，用于接收交易返回时转换json字符串
 * 
 * @author pucker
 *
 */
public class BaseResp {

	/**
	 * 返回码
	 */
	private String code;
	/**
	 * 返回描述
	 */
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
