package com.cn.thinkx.wecard.facade.telrecharge.resp;

public class TeleRespDomain<T> implements java.io.Serializable {

	private static final long serialVersionUID = -5133396812614899856L;

	public static final String SUCCESS_CODE = "00";
	public static final String SUCCESS_MSG = "操作成功";

	private String code;

	private String msg;

	private T data;

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

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

}
