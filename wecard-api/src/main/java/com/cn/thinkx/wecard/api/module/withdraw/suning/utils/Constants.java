package com.cn.thinkx.wecard.api.module.withdraw.suning.utils;


public class Constants {

	/**
	 * 出款返回码
	 * 
	 * @author xiaomei
	 *
	 */
	public enum RespCode {
		R0("0", "true"),
		R1("1", "false"),
		R2("2", "processing");
		
		private String code;
		private String value;
		
		RespCode(String code, String value) {
			this.code = code;
			this.value = value;
		}
		
		public String getCode() {
			return code;
		}
		
		public String getValue() {
			return value;
		}
		
		public static RespCode findByValue(String value) {
			for (RespCode t : RespCode.values()) {
				if (t.value.equalsIgnoreCase(value)) {
					return t;
				}
			}
			return null;
		}
	}
	
	/**
	 * 出款状态
	 * 
	 * @author xiaomei
	 *
	 */
	public enum withdrawStat {
		S66("66", "申请代付"),
		S00("00", "新建"),
		S01("01", "已受理"),
		S02("02", "处理中"),
		S03("03", "支付中"),
		S04("04", "支付失败"),
		S05("05", "支付成功"),
		S06("06", "支付异常"),
		S07("07", "处理成功");
		
		private String code;
		private String value;
		
		withdrawStat(String code, String value) {
			this.code = code;
			this.value = value;
		}
		
		public String getCode() {
			return code;
		}
		
		public String getValue() {
			return value;
		}
		
		public static withdrawStat findByCode(String code) {
			for (withdrawStat t : withdrawStat.values()) {
				if (t.code.equalsIgnoreCase(code)) {
					return t;
				}
			}
			return null;
		}
	}
	
	/**
	 * 异步回调返回明细订单状态
	 * 
	 * @author xiaomei
	 *
	 */
	public enum orderNotifyStat {
		stat0("31", "false"),
		stat1("32", "true"),
		stat2("33", "processing");
		
		private String code;
		private String value;
		
		orderNotifyStat(String code, String value) {
			this.code = code;
			this.value = value;
		}
		
		public String getCode() {
			return code;
		}
		
		public String getValue() {
			return value;
		}
		
		public static orderNotifyStat findByValue(String value) {
			for (orderNotifyStat t : orderNotifyStat.values()) {
				if (t.value.equalsIgnoreCase(value)) {
					return t;
				}
			}
			return null;
		}
	}
	
}
