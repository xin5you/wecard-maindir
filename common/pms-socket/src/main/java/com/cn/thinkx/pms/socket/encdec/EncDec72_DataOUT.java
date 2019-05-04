package com.cn.thinkx.pms.socket.encdec;

import com.cn.thinkx.pms.socket.util.GlobalSocketConst;

public class EncDec72_DataOUT {
	public byte[] reply_code = new byte[GlobalSocketConst.REPLY_CODE_LEN]; /* 返回码 */

	public byte[] data_len = new byte[2]; /* 返回数据长度 */

	public byte[] data; /* 加解密后的数据 */
}