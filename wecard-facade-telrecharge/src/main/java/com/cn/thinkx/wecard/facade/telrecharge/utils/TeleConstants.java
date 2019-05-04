package com.cn.thinkx.wecard.facade.telrecharge.utils;

import java.math.BigDecimal;

public class TeleConstants {

	/**
	 * 话费充值 方法名称
	 * 
	 * @author zhuqiuyou
	 *
	 */
	public enum ReqMethodCode {
		
		R1("1", "hkb.api.mobile.charge"),
		R2("2", "hkb.api.mobile.data");
		
		private String code;
		private String value;
		
		ReqMethodCode(String code, String value) {
			this.code = code;
			this.value = value;
		}
		
		public String getCode() {
			return code;
		}
		
		public String getValue() {
			return value;
		}
		
		public static ReqMethodCode findByValue(String value) {
			for (ReqMethodCode t : ReqMethodCode.values()) {
				if (t.value.equalsIgnoreCase(value)) {
					return t;
				}
			}
			return null;
		}
	}
	
	
	/**
	 * 话费充值 分销商订单状态
	 * 
	 * @author zhuqiuyou
	 *
	 */
	public enum ChannelOrderPayStat {
		ORDER_PAY_0("0", "待扣款"),
		ORDER_PAY_1("1", "已扣款"),
		ORDER_PAY_2("2", "已退款"),
		ORDER_PAY_5("5", "退款中");
		
		private String code;
		private String value;
		
		ChannelOrderPayStat(String code, String value) {
			this.code = code;
			this.value = value;
		}
		
		public String getCode() {
			return code;
		}
		
		public String getValue() {
			return value;
		}
	}
	
	/**
	 * 话费充值 分销商订单处理状态
	 * 
	 * @author zhuqiuyou
	 *
	 */
	public enum ChannelOrderNotifyStat {
		
		ORDER_NOTIFY_1("1", "处理中"),
		ORDER_NOTIFY_2("2", "处理失败"),
		ORDER_NOTIFY_3("3", "处理成功");
		
		private String code;
		private String value;
		
		ChannelOrderNotifyStat(String code, String value) {
			this.code = code;
			this.value = value;
		}
		
		public String getCode() {
			return code;
		}
		
		public String getValue() {
			return value;
		}
	}
	
	
	/**
	 * 话费充值 供应商订单处理充值状态
	 * 
	 * @author zhuqiuyou
	 *
	 */
	public enum ProviderRechargeState {
		
		RECHARGE_STATE_0("0", "充值中"),
		RECHARGE_STATE_1("1", "充值成功"),
		RECHARGE_STATE_3("3", "充值失败"),
		RECHARGE_STATE_8("8", "待充值"),
		RECHARGE_STATE_9("9", "已撤销");
		
		private String code;
		private String value;
		
		ProviderRechargeState(String code, String value) {
			this.code = code;
			this.value = value;
		}
		
		public String getCode() {
			return code;
		}
		
		public String getValue() {
			return value;
		}
	}
	
	
	
	public static void main(String[] args) {
		BigDecimal b = new BigDecimal("10.000"); 
		b=b.setScale(0, BigDecimal.ROUND_DOWN);
		
		System.out.println(b);
	}
}
