package com.cn.thinkx.facade.bean.base;

import java.io.Serializable;

/**
 * 知了企服接口基础请求封装类
 * 
 * @author pucker
 *
 */
public class BaseReq implements Serializable {

	private static final long serialVersionUID = 1L;
	/**
	 * 时间戳
	 */
	private long timestamp;
	/**
	 * 签名
	 */
	private String sign;

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

}
