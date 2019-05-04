package com.cn.thinkx.common.service.module.jiafupay.vo;

/**
 * 请求嘉福支付封装方法 返回参数
 * 
 * @author xiaomei
 * @date 2018/4/16
 */
public class JFChnlResp {

	private String code;	//返回码
	private String msg;		//返回描述
	private String swtFlowNo;	//嘉福流水
	
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
	public String getSwtFlowNo() {
		return swtFlowNo;
	}
	public void setSwtFlowNo(String swtFlowNo) {
		this.swtFlowNo = swtFlowNo;
	}
	
}
