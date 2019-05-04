package com.cn.thinkx.pms.connect.utils;


public class ConnectConstant {
	
	public static final String outMessTypeHEX = "HEX";


	public static final String outMessTypeSTR = "STR";

	public static final String VTXN = "vTxn";
	
	public static final String TXN_TYPE = "TXN_TYPE";
	
	public static final String TXN_CHANNEL = "TXN_CHNL_ID";

	// 通信成功后返回值
	public static final String RESPONSE_SUCCESS_CODE = "00";
	/**
	 * CONNECTION_NM:链路名称 
	 */
	public final static String CONNECTION_NM = "CONNECTION_NM";
	/**
	 * REMOTE_IP:远端IP
	 */
	public final static String REMOTE_IP = "REMOTE_IP";
	/**
	 * REMOTE_PORT:远端端口
	 */
	public final static String REMOTE_PORT = "REMOTE_PORT";
	public final static String LINK_UUID = "LINK_UUID";

	/**
	 * 搭建链路的最长允许时间（微秒）
	 */
	public final static long CONNECTION_CREATING_MAX_MILLIS = 9000;

	/**
	 * 链路状态 0:已关闭 1:建立中 2:已链接
	 */
	public enum CONNECTION_STATUS {
		CLOSED("0"), CREATING("1"), ESTABLISHED("2");
		private final String value;

		CONNECTION_STATUS(String value) {
			this.value = value;
		};

		@Override
		public String toString() {
			return this.value;
		}
	};

	/**
	 * 链路名称
	 */
	public enum CONNECTION_NAME {
		CONNECTION_TXN_1, CONNECTION_TXN_2
	};

}
