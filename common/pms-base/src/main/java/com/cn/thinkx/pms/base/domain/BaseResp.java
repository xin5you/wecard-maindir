package com.cn.thinkx.pms.base.domain;

import java.io.Serializable;

public class BaseResp implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 8134774424031641621L;

	/**
	 * 返回码
	 */
	private String respCode;

	/**
	 * 返回信息
	 */
	private String respMsg;

	public String getRespCode() {
		return respCode;
	}

	public void setRespCode(String respCode) {
		this.respCode = respCode;
	}

	public String getRespMsg() {
		return respMsg;
	}

	public void setRespMsg(String respMsg) {
		this.respMsg = respMsg;
	}

}
