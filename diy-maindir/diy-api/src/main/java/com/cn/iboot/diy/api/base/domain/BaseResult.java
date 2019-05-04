package com.cn.iboot.diy.api.base.domain;

import java.io.Serializable;

public class BaseResult<T> implements Serializable {
	private static final long serialVersionUID = 1L;
	public static final String SUCCESS_CODE = "00";
	public static final String SUCCESS_MSG = "操作成功";

	/** 返回码 */
	private String code;
	/** 返回信息 */
	private String msg;
	/** 返回对象 */
	private T object;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getObject() {
		return object;
	}

	public void setObject(T object) {
		this.object = object;
	}

}
