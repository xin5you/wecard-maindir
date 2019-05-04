package com.cn.thinkx.pms.socket.util;

import com.cn.thinkx.pms.socket.encdec.EncDec72_DataIN;
import com.cn.thinkx.pms.socket.encdec.EncDec72_DataOUT;

public class PmsSessionApp {
	public static final int SECBUF_MAX_SIZE = 512;
	private byte[] iSecBufferIn = new byte[SECBUF_MAX_SIZE];
	private byte[] iSecBufferOut = new byte[SECBUF_MAX_SIZE];

	private static boolean Hex2Str(byte[] in, byte[] out, int len) {
		byte[] asciiCode = { 0x41, 0x42, 0x43, 0x44, 0x45, 0x46 };

		if (len > in.length) {
			return false;
		}

		byte[] temp = new byte[2 * len];

		for (int i = 0; i < len; i++) {
			temp[2 * i] = (byte) ((in[i] & 0xf0) / 16);
			temp[2 * i + 1] = (byte) (in[i] & 0x0f);
		}

		for (int i = 0; i < 2 * len; i++) {
			if (temp[i] <= 9 && temp[i] >= 0) {
				out[i] = (byte) (temp[i] + 0x30);
			} else {
				out[i] = asciiCode[temp[i] - 0x0a];
			}
		}

		return true;
	}

	/** Convert bye to HEX **/
	public static String byte2hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = Integer.toHexString(b[n] & 0XFF);
			if (stmp.length() == 1) {
				hs = hs + "0" + stmp;
			} else {
				hs = hs + stmp;
			}
			if (n < b.length - 1) {
				hs = hs + " ";
			}
		}
		return hs.toUpperCase();
	}

	public int EncDec72_Data(PmsSession session, EncDec72_DataIN pmsg_in, EncDec72_DataOUT pmsg_out) {
		int iSndLen = 0, nResult = 0;
		byte[] retCode = new byte[1];

		iSecBufferIn[0] = 0x72;
		iSndLen += 1;
		/** BMK INDEX **/
		String temp = new String(pmsg_in.index);
		int i = Integer.parseInt(temp);
		if (i > 999) {
			retCode[0] = (byte) GlobalSocketConst.EKEY_INVALID_BMK_INDEX;
			Hex2Str(retCode, pmsg_out.reply_code, 1);
			return GlobalSocketConst.FAIL;
		}
		iSecBufferIn[iSndLen++] = (byte) ((i >> 8) & 0xff);
		iSecBufferIn[iSndLen++] = (byte) (i & 0xff);

		/** key **/
		// byte tmpKey[] = new byte[16];
		// Str2Hex(pmsg_in.work_key, tmpKey, 32);
		// System.arraycopy(pmsg_in.work_key, 0, iSecBufferIn, iSndLen, 16);
		// iSndLen += 16;

		/** initial_vecotr **/
		byte tmpInitialVecotr[] = new byte[8];
		// Str2Hex(pmsg_in.initial_vecotr, tmpInitialVecotr, 16);
		System.arraycopy(tmpInitialVecotr, 0, iSecBufferIn, iSndLen, 8);
		iSndLen += 8;

		/** encdec_flag **/
		iSecBufferIn[iSndLen++] = (byte) (pmsg_in.encdec_flag[0] - '0');

		/** algorithm_flag **/
		iSecBufferIn[iSndLen++] = (byte) (pmsg_in.algorithm_flag[0] - '0');

		/** data len **/
		temp = new String(pmsg_in.data_len);
		i = Integer.parseInt(temp.trim());
		iSecBufferIn[iSndLen++] = (byte) ((i >> 8) & 0xff);
		iSecBufferIn[iSndLen++] = (byte) (i & 0xff);

		/** data **/
		System.arraycopy(pmsg_in.data, 0, iSecBufferIn, iSndLen, i);
		iSndLen += i;

		System.out.println("iSecBufferIn:" + PmsSessionApp.byte2hex(iSecBufferIn));

		nResult = session.SndAndRcvData(iSecBufferIn, iSndLen, iSecBufferOut);

		System.out.println("iSecBufferOut:" + PmsSessionApp.byte2hex(iSecBufferOut));

		if (nResult != 0) {
			retCode[0] = (byte) nResult;
			Hex2Str(retCode, pmsg_out.reply_code, 1);
			return GlobalSocketConst.FAIL;
		}

		if (iSecBufferOut[0] == (byte) 'A') {
			// 设置输出响应码，将成功的'A'转义为字符串"00"
			pmsg_out.reply_code[0] = 0x30;
			pmsg_out.reply_code[1] = 0x30;

			// 设置输出DATA的长度，转义为字符串
			i = ((iSecBufferOut[1]) << 8) | (iSecBufferOut[2] & 0xff);
			String tt = Integer.toString(i);
			System.arraycopy("00".getBytes(), 0, pmsg_out.data_len, 0, 2);
			System.arraycopy(tt.getBytes(), 0, pmsg_out.data_len, 2 - tt.length(), tt.length());

			// 分配输出DATA的所占字节数组，并进行设置
			pmsg_out.data = new byte[i];
			System.arraycopy(iSecBufferOut, 3, pmsg_out.data, 0, i);
		} else {
			retCode[0] = iSecBufferOut[1];
			Hex2Str(retCode, pmsg_out.reply_code, 1);
			return GlobalSocketConst.FAIL;
		}
		return GlobalSocketConst.SUCCESS;
	}

}