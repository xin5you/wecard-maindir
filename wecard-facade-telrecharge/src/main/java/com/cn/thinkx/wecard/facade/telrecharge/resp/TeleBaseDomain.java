package com.cn.thinkx.wecard.facade.telrecharge.resp;

/**
 * 手机充值入参公用参数
 * @author zhuqiuyou
 *
 */
public class TeleBaseDomain implements java.io.Serializable {

	private static final long serialVersionUID = 5096161795264953693L;

	private String channelId; //渠道标识
	
	private String channelToken; //渠道token
	
	private String method;//充值的类型
	
	private String v; //版本
	
	private String timestamp; //日期格式
	
	private String sign; //系统签名

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getChannelToken() {
		return channelToken;
	}

	public void setChannelToken(String channelToken) {
		this.channelToken = channelToken;
	}

	public String getV() {
		return v;
	}

	public void setV(String v) {
		this.v = v;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
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
