package com.cn.thinkx.oms.module.common.model;
/**
 * 交易返回对象，用于接收交易返回时转换json字符串
 * 
 * @author pucker
 *
 */
public class TxnResp {

	private String code;
	private String info;
	private String transAmt;
	private String userId;
	
	/**返回接口层流水主键**/
	private String interfacePrimaryKey;
	
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

	public String getTransAmt() {
		return transAmt;
	}

	public void setTransAmt(String transAmt) {
		this.transAmt = transAmt;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getInterfacePrimaryKey() {
		return interfacePrimaryKey;
	}

	public void setInterfacePrimaryKey(String interfacePrimaryKey) {
		this.interfacePrimaryKey = interfacePrimaryKey;
	}
	
}