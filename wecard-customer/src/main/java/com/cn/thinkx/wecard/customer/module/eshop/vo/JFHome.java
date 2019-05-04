package com.cn.thinkx.wecard.customer.module.eshop.vo;

/**
 * 通卡商城 对接嘉福参数封装类
 * 
 * @author xiaomei
 *
 */
public class JFHome {

	private String ident;   		//接入标识
	private String service;			//服务接口
	private String format;			//数据返回格式
	private String charset;			//编码格式
	private String timestamp;		//时间戳
	private String version;			//版本号
	private String biz_content; 	//业务参数
	private String extand_params;	//拓展参数
	private String sign_type;		//签名类型
	private String sign;	    	//签名
	//服务场景参数
	private String e_eid;   //渠道企业标识
	private String e_uid;   //渠道用户标识
	private String mobile;	//用户手机号
	private String type;	//类型 （美团：1，点评：2，不传则默认为美团）

	public String getIdent() {
		return ident;
	}

	public void setIdent(String ident) {
		this.ident = ident;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getBiz_content() {
		return biz_content;
	}

	public void setBiz_content(String biz_content) {
		this.biz_content = biz_content;
	}

	public String getExtand_params() {
		return extand_params;
	}

	public void setExtand_params(String extand_params) {
		this.extand_params = extand_params;
	}

	public String getSign_type() {
		return sign_type;
	}

	public void setSign_type(String sign_type) {
		this.sign_type = sign_type;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getE_eid() {
		return e_eid;
	}

	public void setE_eid(String e_eid) {
		this.e_eid = e_eid;
	}

	public String getE_uid() {
		return e_uid;
	}

	public void setE_uid(String e_uid) {
		this.e_uid = e_uid;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "JFHome [ident=" + ident + ", service=" + service + ", format=" + format + ", charset=" + charset
				+ ", timestamp=" + timestamp + ", version=" + version + ", biz_content=" + biz_content
				+ ", extand_params=" + extand_params + ", sign_type=" + sign_type + ", sign=" + sign + ", e_eid="
				+ e_eid + ", e_uid=" + e_uid + ", mobile=" + mobile + ", type=" + type + "]";
	}

}
