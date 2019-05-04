package com.cn.iboot.diy.api.base.exception;


/**
 * 业务操作通用异常处理返回
 * 
 */
public class BizHandlerException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public BizHandlerException(String code, String msg) {
		super(msg);
		this.code = code;
	}

	// 返回码
	private String code;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
}
