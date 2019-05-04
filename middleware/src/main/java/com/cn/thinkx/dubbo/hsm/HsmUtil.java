package com.cn.thinkx.dubbo.hsm;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cn.thinkx.pms.base.utils.NumberUtils;
import com.cn.thinkx.pms.base.utils.ReadPropertiesFile;
import com.cn.thinkx.pms.base.utils.StringUtil;
import com.cn.thinkx.pms.socket.encdec.EncDec72_DataIN;
import com.cn.thinkx.pms.socket.encdec.EncDec72_DataOUT;
import com.cn.thinkx.pms.socket.util.PmsSession;
import com.cn.thinkx.pms.socket.util.PmsSessionApp;

public class HsmUtil {
	private static Logger log = LoggerFactory.getLogger(HsmUtil.class);

	/**
	 * 转pin
	 * 
	 * @param userId
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public static String encodePassword(String userId, String password) throws Exception {
		if (StringUtil.isNullOrEmpty(userId) || StringUtil.isNullOrEmpty(password)) {
			throw new Exception("params userId or password is null");
		}
		int nRet = 0;
		OutputSecResult outRslt = null;
		PmsSession session = null;
		EncDec72_DataIN encdec72in = null;
		EncDec72_DataOUT encdec72out = null;
		try {
			session = new PmsSession();
			PmsSessionApp hApp = new PmsSessionApp();
			nRet = session.GetLastError();
			if (nRet != 0) {
				log.error(SecException.ERR_SEC_CODE_8002 + SecException.ERR_SEC_MSG_8002);
				throw new SecException(SecException.ERR_SEC_CODE_8002 + SecException.ERR_SEC_MSG_8002);
			}
			byte[] bts = encodePasswordAndUserId(password, userId);
			if (bts != null && bts.length == 8) {
				String keyIndex = ReadPropertiesFile.getInstance().getProperty("TXN_BMK_INDEX", "ini");
				if (StringUtil.isEmpty(keyIndex)) {
					keyIndex = "016";
				}
				encdec72in = new EncDec72_DataIN(keyIndex.getBytes(), "00000000".getBytes(), "1".getBytes(),
						"0".getBytes(), "8".getBytes(), bts);

				encdec72out = new EncDec72_DataOUT();
				nRet = hApp.EncDec72_Data(session, encdec72in, encdec72out);
			} else {
				throw new Exception("transform pin failed");
			}

		} catch (Exception e) {
			log.error("hsm failed", e);
			if (encdec72out != null)
				throw new SecException(SecException.ERR_SEC_CODE_8002 + SecException.ERR_SEC_MSG_8002 + ":" + new String(encdec72out.reply_code));
			
		} finally {
			if (session != null) {
				session.HsmSessionEnd();
			}
		}
		if (nRet != GlobalSocketConst.SUCCESS) {
			throw new SecException(SecException.ERR_SEC_CODE_8002 + SecException.ERR_SEC_MSG_8002 + ":" + new String(encdec72out.reply_code));
		} else {
			outRslt = new OutputSecResult();
			outRslt.setData(encdec72out.data);
			outRslt.setDataLen(encdec72out.data_len);

			if (!StringUtil.isNullOrEmpty(encdec72out.data)) {
				return PmsSessionApp.byte2hex(encdec72out.data).replace(" ", "");
			}
		}
		return null;
	}
	
	/**
	 * 转mac
	 * 
	 * @param userId
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public static String encodeMac(String nonStr) throws Exception {
		if (StringUtil.isNullOrEmpty(nonStr)) {
			throw new Exception("params nonStr is null");
		}
		int nRet = 0;
		OutputSecResult outRslt = null;
		PmsSession session = null;
		EncDec72_DataIN encdec72in = null;
		EncDec72_DataOUT encdec72out = null;
		try {
			session = new PmsSession();
			PmsSessionApp hApp = new PmsSessionApp();
			nRet = session.GetLastError();
			if (nRet != 0) {
				log.error(SecException.ERR_SEC_CODE_8002 + SecException.ERR_SEC_MSG_8002);
				throw new SecException(SecException.ERR_SEC_CODE_8002 + SecException.ERR_SEC_MSG_8002);
			}
			byte[] bts = encodeMacAndNonStr(nonStr);
			if (bts != null && bts.length == 8) {
				String keyIndex = ReadPropertiesFile.getInstance().getProperty("TXN_BMK_INDEX", "ini");
				if (StringUtil.isEmpty(keyIndex)) {
					keyIndex = "016";
				}
				encdec72in = new EncDec72_DataIN(keyIndex.getBytes(), "00000000".getBytes(), "1".getBytes(),
						"0".getBytes(), "8".getBytes(), bts);
				
				encdec72out = new EncDec72_DataOUT();
				nRet = hApp.EncDec72_Data(session, encdec72in, encdec72out);
			} else {
				throw new Exception("transform mac failed");
			}
			
		} catch (Exception e) {
			log.error("hsm failed", e);
			throw new SecException(SecException.ERR_SEC_CODE_8002 + SecException.ERR_SEC_MSG_8002 + ":"
					+ new String(encdec72out.reply_code));
		} finally {
			if (session != null) {
				session.HsmSessionEnd();
			}
		}
		if (nRet != GlobalSocketConst.SUCCESS) {
			throw new SecException(SecException.ERR_SEC_CODE_8002 + SecException.ERR_SEC_MSG_8002 + ":"
					+ new String(encdec72out.reply_code));
		} else {
			outRslt = new OutputSecResult();
			outRslt.setData(encdec72out.data);
			outRslt.setDataLen(encdec72out.data_len);
			
			if (!StringUtil.isNullOrEmpty(encdec72out.data)) {
				return PmsSessionApp.byte2hex(encdec72out.data).replace(" ", "");
			}
		}
		return null;
	}

	/**
	 * 将密码和卡号转化为高低位的编码
	 * 
	 * @param psw
	 *            6位 密码
	 * @param card
	 *            卡号
	 * @return
	 */
	private static byte[] encodePasswordAndUserId(String password, String userId) {
		/* 密码长度 + 密码 + 8个零（补足十六位） */
		String sec = "06" + password + "FFFFFFFF";
		/* FFFF（前补F）补足十六位， + 除去最后一位的校验位 向前去12为卡号 */
		String sec1 = userId.substring(userId.length() - 16, userId.length());
		if (!(sec.matches("[0-9,A-F]{16}") && sec1.matches("[0-9,A-F]{16}"))) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < 16; i++) {

			/* 16进制 转化 为10进制 */
			String c = sec.substring(i, i + 1);
			int k = Integer.parseInt(c, 16);

			String d = sec1.substring(i, i + 1);
			int kk = Integer.parseInt(d, 16);
			/* 异或 */
			sb.append(Integer.toHexString(k ^ kk));
		}
		String s = sb.toString();
		byte[] bts = new byte[8];
		for (int i = 0; i < 16; i = i + 2) {
			String c = s.substring(i, i + 1);
			int k = Integer.parseInt(c, 16);
			// 高位
			int t1 = k << 4;
			String c2 = s.substring(i + 1, i + 2);
			int k2 = Integer.parseInt(c2, 16);
			// 低位
			int t2 = k2;
			bts[i / 2] = (byte) (t1 | t2);
		}
		return bts;
	}
	
	private static byte[] encodeMacAndNonStr(String nonStr) {
		byte[] bts = new byte[8];
		for (int i = 0; i < 16; i = i + 2) {
			String c = nonStr.substring(i, i + 1);
			int k = Integer.parseInt(c, 16);
			// 高位
			int t1 = k << 4;
			String c2 = nonStr.substring(i + 1, i + 2);
			int k2 = Integer.parseInt(c2, 16);
			// 低位
			int t2 = k2;
			bts[i / 2] = (byte) (t1 | t2);
		}
		return bts;
	}
	
	public static void main(String[] args) {
		try {
			System.out.println(HsmUtil.encodeMac(NumberUtils.get16RandomNum()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
