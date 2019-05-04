package com.cn.thinkx.pms.socket.util;
/**
 * socket 通道的常量
 * @author sunyue
 *
 */
public class GlobalSocketConst {
	public static final int SUCCESS = 0;
	public static final int FAIL = 1;
	public static final int MAX_RSA_KEY = 50;
	// ************************************
	public static final int REPLY_CODE_LEN = 2;
	public static final int INDEX_LEN = 3;
	public static final int MODULE_LEN = 4;
	public static final int FLAG_LEN = 2;
	public static final int PWD_LEN = 8;
	public static final int DATA_LEN = 4;
	public static final int PUBLIC_LEN = 516;
	public static final int PRIVATE_LEN = 1416;

	/* 错误码 */
	public static final int ERR_MODULE_LEN = 0x01; // 模长错误
	public static final int ERR_PASSWD = 0x02; // 口令错误
	public static final int ERR_DATA_LEN = 0x03; // 数据长度错误
	public static final int ERR_KEY_LEN = 0x04; // 密钥长度错误

	// *************************************

	public static final int MAX_MAC_ELEMENT_LEN = 400;
	public static final int MAC_ELEMENT_LEN_LEN = 3;
	public static final int MAC_LEN = 8;
	public static final int PIN_LEN = 8;
	public static final int PAN_LEN = 19; // 8*2
	public static final int PIK_LEN = 24 * 2;
	public static final int MAK_LEN = 24 * 2;
	public static final int CHV_LEN = 8 * 2;
	public static final int PIN_M_LEN = 12;
	public static final int PIK_LEN_LEN = 2;
	public static final int MAK_LEN_LEN = 2;
	public static final int PIN_LEN_LEN = 3;
	public static final int PIN_BLK_LEN = 8 * 2;
	public static final int PWD_BLK_LEN = 24 * 2;
	public static final int EKEY_INVALID_BMK_INDEX = 0x0c;
	public static final int EKEY_LENGTH = 0x32;
	public static final int EPIN_LENGTH = 0x31;
	public static final int MAX_DATA_LEN = 5000;
}