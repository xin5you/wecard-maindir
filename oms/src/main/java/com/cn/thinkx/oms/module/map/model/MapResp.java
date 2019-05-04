package com.cn.thinkx.oms.module.map.model;

public class MapResp {
	
	private int status;
	
	private String message;
	
	private AddressResult result;

	public int getStatus() {
		return status;
	}

	public String getMessage() {
		return message;
	}

	public AddressResult getResult() {
		return result;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setResult(AddressResult result) {
		this.result = result;
	}
	
}
