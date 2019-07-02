package com.cn.thinkx.api.bm001.constants;

public class BMConstants {



	public final static String BM_APP_KEY = "BM_APP_KEY";
	
	public final static String BM_APP_SECRET = "BM_APP_SECRET";
	
	public final static String BM_ACCESS_TOKEN = "BM_ACCESS_TOKEN";
	
	public final static String BM_SERVER_URL = "BM_SERVER_URL";

	public enum RechargeState{
		RechargeState00("0","充值中"),
		RechargeState01("1","充值成功"),
		RechargeState09("9","充值失败");

		private String code;
		private String value;

		RechargeState(String code, String value) {
			this.code = code;
			this.value = value;
		}

		public String getCode() {
			return code;
		}

		public String getValue() {
			return value;
		}

		public static RechargeState findByCode(String code) {
			for (RechargeState t : RechargeState.values()) {
				if (t.code.equalsIgnoreCase(code)) {
					return t;
				}
			}
			return null;
		}

	}

}
