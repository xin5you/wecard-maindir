package com.cn.thinkx.wecard.api.module.pay.utils;
/**
 * 派派设备基础数据
 * @author zqy
 *
 */
public class PPBoxConstants {

	
	/**
	 * 派派盒子返回CODE描述
	 *
	 */
	public enum PPBoxCode{
		FAIL("FAIL"),
		SUCCESS("SUCCESS");
		
		private String value;
		
		PPBoxCode(String value){
			this.value=value;
		}
		public String getValue() {
			return value;
		}
	}
	
	
	/**
	 * 派派盒子支付方式
	 *
	 */
	public enum PPBoxPayType{
		INVALID("INVALID"),
		HKBPAY("HKB"),
		WXPAY("WXPAY"),
		ALIPAY("ALIPAY"),
		UNIONPAY("UNIONPAY"),
		JFPAY("JFPAY");
		
		private String value;
		
		PPBoxPayType(String value){
			this.value=value;
		}
		public String getValue() {
			return value;
		}
	}
	
	/**
	 * 派派盒子支付方式
	 *
	 */
	public enum PPBoxPrintType{
		type0("0"),
		type1("1"),
		type2("2");
		
		private String value;
		
		PPBoxPrintType(String value){
			this.value=value;
		}
		public String getValue() {
			return value;
		}
	}
	
	/**
	 * 派派盒子支付方式
	 *
	 */
	public enum PPBoxSubCode{
		SYSTEMERROR("SYSTEMERROR","支付结果失败"),
		INVALID_PARAMETER("INVALID_PARAMETER","参数无效"),
		NOTENOUGH("NOTENOUGH","余额不足"),
		PAYMENT_AUTH_CODE_INVALID("PAYMENT_AUTH_CODE_INVALID","支付授权码无效"),
		ORDERCLOSED("ORDERCLOSED","订单关闭"),
		USERPAYING("USERPAYING","用户支付中，需要输入密码"),
		APPID_ERROR("APPID_ERROR","APPID 参数错误"),
		TRADE_HAS_CLOSE("TRADE_HAS_CLOSE","交易关闭"),
		BUYER_MISMATCH("BUYER_MISMATCH","支付账号错误"),
		TOTAL_FEE_EXCEED("TOTAL_FEE_EXCEED","订单总金额超过额度"),
		SIGNERROR("SIGNERROR","签名错误"),
		TRADE_HAS_SUCCESS("TRADE_HAS_SUCCESS","订单已支付"),
		ERROR_BALANCE_PAYMENT_DISABLE("ERROR_BALANCE_PAYMENT_DISABLE","余额支付功能关闭"),
		NOAUTH("NOAUTH","商户无此接口权限"),
		REQUESTERROR("REQUESTERROR","请求失败"),
		MCHNTNOTRANS("MCHNTNOTRANS", "此商户暂不支持交易");
		
		private String code;
		
		private String value;
		
		PPBoxSubCode(String code,String value){
			this.code=code;
			this.value=value;
		}
	
		public String getCode() {
			return code;
		}
		public String getValue() {
			return value;
		}
	}
}
