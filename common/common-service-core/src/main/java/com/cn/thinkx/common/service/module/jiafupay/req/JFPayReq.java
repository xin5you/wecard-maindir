package com.cn.thinkx.common.service.module.jiafupay.req;

/**
 * 嘉福支付接口请求参数
 * 
 * @author xiaomei
 * @date 2018/4/16
 */
public class JFPayReq {

	private String channel;		//接入标识
	private String service; 	//服务接口
	private String format;		//返回类型
	private String charset;		//编码格式
	private String timestamp;	//unix时间戳
	private String version;		//版本号
	private String content;		//业务参数
	private String sign_type;	//签名类型
	private String sign;		//签名
	
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
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
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
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
	
}
