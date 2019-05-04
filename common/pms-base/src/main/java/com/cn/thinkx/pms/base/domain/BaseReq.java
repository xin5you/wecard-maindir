package com.cn.thinkx.pms.base.domain;

import java.io.Serializable;

public class BaseReq implements Serializable {

	/**
	 * 序列号
	 */
	private static final long serialVersionUID = 8134774424031641621L;

	/**
	 * 请求ID
	 */
	private String id;

	/**
	 * 请求参数
	 */
	private String paramData;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getParamData() {
		return paramData;
	}

	public void setParamData(String paramData) {
		this.paramData = paramData;
	}

}
