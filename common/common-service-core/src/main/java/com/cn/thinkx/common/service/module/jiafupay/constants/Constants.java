package com.cn.thinkx.common.service.module.jiafupay.constants;

public class Constants {

	public static final String CHANNEL = "HKB";

	//服务接口
	public static final String SERVICE = "tl.trade.payment";

	//返回类型
	public static final String FORMAT = "json";

	//编码格式
	public static final String CHARSET = "utf-8";

	//版本号
	public static final String VERSION = "1.0";

	/*
	 * 签名类型
	 */
	public enum SignType {
		ST0("MD5"),
		ST1("RSA");

		private final String value;

		SignType(String value) {
			this.value = value;
		};

		@Override
		public String toString() {
			return this.value;
		}

		public static SignType findByCode(String code) {
			for (SignType t : SignType.values()) {
				if (t.value.equalsIgnoreCase(code)) {
					return t;
				}
			}
			return null;
		}
	}

	/*
	 * 支付方式
	 */
	public enum PayMethod {
		pay0("benefit", "福利账户"),
		pay1("salary", "工资账户"),
		pay2("wx_wft", "微信支付"),
		pay3("alipay", "支付宝支付"),
		pay4("yiqizou", "一起走支付");

		private String code;
		private String value;

		PayMethod(String code, String value) {
			this.code = code;
			this.value = value;
		}

		public String getCode() {
			return code;
		}

		public String getValue() {
			return value;
		}

		public static PayMethod findByCode(String code) {
			for (PayMethod t : PayMethod.values()) {
				if (t.code.equalsIgnoreCase(code)) {
					return t;
				}
			}
			return null;
		}
	}

	/*
	 * 嘉福交易返回码
	 */
	public enum JFRespCode {
		R00("0000", "交易成功"),
		R01("4000", "交易失败"),
		R02("4001", "未知的接入渠道"),
		R03("4008", "签名错误"),
		R04("4009", "系统内部错误"),
		R05("4010", "参数错误"),
		R06("4011", "流水未找到"),
		R07("6001", "交易重复"),
		R08("6002", "未找到原交易"),
		R09("6003", "交易已成功");
		
		private String code;
		private String value;

		JFRespCode(String code, String value) {
			this.code = code;
			this.value = value;
		}

		public String getCode() {
			return code;
		}

		public String getValue() {
			return value;
		}

		public static JFRespCode findByCode(String code) {
			for (JFRespCode t : JFRespCode.values()) {
				if (t.code.equalsIgnoreCase(code)) {
					return t;
				}
			}
			return null;
		}
	}

}
