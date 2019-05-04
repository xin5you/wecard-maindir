package com.cn.thinkx.common.service.module.jiafupay.resp;

/**
 * 接收嘉福支付返回参数
 * 
 * @author xiaomei
 * @date 2018/4/16
 */
public class JFPayResp {

	private String code;		//状态码
	private String errmsg;		//错误说明
	private String resp_data;	//响应业务参数
	private String timestamp;	//unix时间戳
	private String sign;		//签名
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getErrmsg() {
		return errmsg;
	}
	public void setErrmsg(String errmsg) {
		this.errmsg = errmsg;
	}
	public String getResp_data() {
		return resp_data;
	}
	public void setResp_data(String resp_data) {
		this.resp_data = resp_data;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	
}
