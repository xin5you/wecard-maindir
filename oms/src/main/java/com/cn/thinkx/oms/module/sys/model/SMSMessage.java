package com.cn.thinkx.oms.module.sys.model;

public class SMSMessage {

	private String phone;
	
	private String content;
	
	private boolean flag;

	public String getPhone() {
		return phone;
	}

	public String getContent() {
		return content;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}
	
	
	
}
