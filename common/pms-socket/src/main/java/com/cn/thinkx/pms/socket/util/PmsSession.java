package com.cn.thinkx.pms.socket.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cn.thinkx.pms.base.utils.ReadPropertiesFile;
import com.cn.thinkx.pms.socket.handle.SocketConnectHandle;

/**
 * confidential machine 连接加密机,并收发数据
 * 
 * @author sunyue
 * 
 */
public class PmsSession {
	private static Logger logger = LoggerFactory.getLogger(PmsSession.class);
	private static final SessionMonitor sSessionMonitor = new SessionMonitor();
	private static final int ERR_CONFIG_FILE = 0x90;
	private static final int ERR_CONNECT_HSM = 0x91;
	private static final int ERR_SENDTO_HSM = 0x92;
	private static final int ERR_RECVFORM_HSM = 0x93;
	private static final int ERR_SESSION_END = 0x94;
	private static final int ERR_HANDLE_FAULT = 0x95;

	private static SocketConnectHandle[] sHandles;
	/* 机密机的最大连接数 */
	private static int sBalance;
	private static int sPort;
	private static int sTimeOut;

	private static int loopMilliSecond = 20;

	private int iCurIdx = -1;
	private int iLastErr = -1;
	
	public PmsSession() {
		iLastErr = 0;
		iCurIdx = -1;

		try {
			initial();
		} catch (Throwable t) {
			iLastErr = ERR_CONFIG_FILE;
			//logger.error("Failed to initialize the socket sesion with profile =" + profile, t);
			return;
		}
		/* 在超时时间内，循环查找，直到找到为止 */
		for (int loop = 0; loop < (sTimeOut / loopMilliSecond); loop++) {
			try {
				if ((iCurIdx = getSession(-1)) != -1)
					break;
				Thread.sleep(20);
			} catch (InterruptedException e) {
				break;
			}
		}

		if (iCurIdx == -1)
			iLastErr = ERR_HANDLE_FAULT;
	}

	/**
	 * 
	 * @param aProfileFile
	 * @throws Exception
	 *             void
	 */
	public static synchronized void initial() throws Exception {
		if (sHandles != null) {
			logger.info("Pms Session has already bean initialized");
			return;
		}
		//logger.info(" Begin to initialize cfm Session");
		//Properties pr = PropertiesLoaderUtils.loadAllProperties(profile);

		String sDigit = ReadPropertiesFile.getInstance().getProperty("BALANCE", "ini");
		sBalance = Integer.parseInt(sDigit);
		sDigit = ReadPropertiesFile.getInstance().getProperty("PORT", "ini");
		sPort = Integer.parseInt(sDigit);
		sDigit = ReadPropertiesFile.getInstance().getProperty("TIMEOUT", "ini");
		sTimeOut = Integer.parseInt(sDigit);
		sDigit = ReadPropertiesFile.getInstance().getProperty("LOOPMILLISECOND", "ini");
		loopMilliSecond = Integer.parseInt(sDigit);
		String sIP = ReadPropertiesFile.getInstance().getProperty("IP", "ini");
		int nError = 0;
		SocketConnectHandle[] tHandle = new SocketConnectHandle[sBalance];
		for (int j = 0; j < sBalance; j++) {
			tHandle[j] = new SocketConnectHandle(sIP, sPort, sTimeOut);
			if (tHandle[j].isFault())
				nError++;
		}

		if (nError == sBalance) {
			throw new Exception("Failed to create connection with confidential  machine");
		}

		/** Create and start the Monitor thread **/
		sHandles = tHandle;
		sSessionMonitor.addHandle(sHandles);
		sSessionMonitor.start();
		//logger.info("####### Initialize cfm Session successfully ######");
	}

	private static synchronized int getSession(int idx) {
		int i = 0;
		boolean tStatus = false;
		int sHsmNumber = 1;
		int sPreIndex = -1;
		int tNumOfSession = sHsmNumber * sBalance;

		for (i = sPreIndex + 1; i < tNumOfSession; i++) {
			SocketConnectHandle tHandle = sHandles[i];

			/** If is a retry request, switch to next balance HSM **/
			if (idx != -1 && ((idx % sHsmNumber) == (i % sHsmNumber) || tHandle.isUsed())) {
				if ((idx % sHsmNumber) == (i % sHsmNumber)) {
					continue;
				} else {
					i = i + sHsmNumber;
				}
			}

			if (tHandle.isUsable()) {
				tHandle.setUsed();
				sPreIndex = i;
				tStatus = true;
				break;
			}
		}

		if (!tStatus) {
			for (i = 0; i < sPreIndex; i++) {
				SocketConnectHandle tHandle = sHandles[i];
				if (idx != -1 && ((idx % sHsmNumber) == (i % sHsmNumber) || tHandle.isUsed())) {
					if ((idx % sHsmNumber) == (i % sHsmNumber)) {
						continue;
					} else {
						i = i + sHsmNumber;
					}
				}

				if (tHandle.isUsable()) {
					tHandle.setUsed();
					sPreIndex = i;
					tStatus = true;
					break;
				}
			}
		}

		if (!tStatus) {
			i = -1;
			logger.error("HsmSession.getSession() fail to get a TCP Connection !" + " E1".getBytes() + "  1");
		}

		return i;
	}

	public int GetPortConf() {
		return sPort;
	}

	public int GetLastError() {
		return iLastErr;
	}

	public void SetErrCode(int nErrCode) {
		iLastErr = nErrCode;
		return;
	}

	/**
	 * 
	 * @param nErrCode
	 * @return String
	 */
	public String ParseErrCode(int nErrCode) {
		String sMeaning;
		switch (nErrCode) {
		case 0:
			sMeaning = "0x" + Integer.toHexString(nErrCode) + ":操作正确,状态正常";
			break;
		case ERR_CONFIG_FILE:
			sMeaning = "0x" + Integer.toHexString(nErrCode) + ":配置文件异常";
			break;
		case ERR_CONNECT_HSM:
			sMeaning = "0x" + Integer.toHexString(nErrCode) + ":连接密码机失败";
			break;
		case ERR_SENDTO_HSM:
			sMeaning = "0x" + Integer.toHexString(nErrCode) + ":发送数据至密码机失败";
			break;
		case ERR_RECVFORM_HSM:
			sMeaning = "0x" + Integer.toHexString(nErrCode) + ":接收密码机数据失败";
			break;
		case ERR_SESSION_END:
			sMeaning = "0x" + Integer.toHexString(nErrCode) + ":连接已关闭";
			break;
		case ERR_HANDLE_FAULT:
			sMeaning = "0x" + Integer.toHexString(nErrCode) + ":连接句柄状态异常";
			break;
		default:
			sMeaning = "0x" + Integer.toHexString(nErrCode) + ":异常操作,检查密码机日志";
			break;
		}
		return sMeaning;
	}

	/**
	 * Send data to HSM
	 */
	private int SendData(byte[] byteOut, int nLength) {
		logger.debug("HandleID =" + iCurIdx + " send Data to confidential machine start ");
		SocketConnectHandle tHandle = sHandles[iCurIdx];
		if (tHandle.isFault()) {
			iLastErr = ERR_HANDLE_FAULT;
			logger.error("HandleID[" + iCurIdx + "] send data failed - " + ParseErrCode(iLastErr));
			return iLastErr;
		}

		try {
			tHandle.write(byteOut, nLength);
			iLastErr = 0;
		} catch (Throwable e) {
			tHandle.setFault();
			iLastErr = ERR_SENDTO_HSM;
			logger.error("HandleID[" + iCurIdx + "] send data failed - " + ParseErrCode(iLastErr));
			logger.error(this.getClass().getName(), e);
		}

		logger.info((new StringBuffer("HandleID[")).append(iCurIdx).append("]Data send to [").append(tHandle.getIP())
				.append("];").append("byteOut = [").append(byteOut).append("];").append("nLength = [").append(nLength)
				.append("];Return Result iLastErr = [").append(iLastErr).append("], description=[")
				.append(ParseErrCode(iLastErr)).append("]").toString());
		return iLastErr;
	}

	/**
	 * Receive data from HSM
	 * **/
	private int RecvData(byte[] byteIn) {
		logger.debug((new StringBuffer("HandleID[")).append(iCurIdx).append("] Receive data from HSM......").toString());
		SocketConnectHandle tHandle = sHandles[iCurIdx];
		int nRcvLen;

		iLastErr = 0;
		if (tHandle.isFault()) {
			iLastErr = ERR_HANDLE_FAULT;
			logger.error((new StringBuffer("HandleID[")).append(iCurIdx).append("]HsmSession.RecvData() failed - ")
					.append(ParseErrCode(iLastErr)).toString());
			return -1;
		}

		try {
			nRcvLen = tHandle.read(byteIn, 256);
		} catch (Throwable e) {
			logger.error(this.getClass().getName(),e);
			nRcvLen = -1;
			iLastErr = ERR_RECVFORM_HSM;
			logger.error((new StringBuffer("HandleID[")).append(iCurIdx).append("]HsmSession.RecvData() failed - ")
					.append(ParseErrCode(iLastErr)).append(":").append(e.getMessage()).toString());
		}

		/** In case of receive timeout **/
		if (nRcvLen <= 0) {
			tHandle.setFault();
			logger.error((new StringBuffer("HandleID[")).append(iCurIdx)
					.append("]HsmSession.RecvData() failed - received timeout").toString());
		}

		logger.info((new StringBuffer("HandleID[")).append(iCurIdx).append("]Data received from [")
				.append(tHandle.getIP()).append("];").append("byteIn = [").append(byteIn).append("];")
				.append("nRcvLen = [").append(nRcvLen).append("];Return Result iLastErr = [").append(iLastErr)
				.append("], description=[").append(ParseErrCode(iLastErr)).append("]").toString());
		return nRcvLen;
	}

	public int SndAndRcvData(byte[] SndData, int nLength, byte[] RcvData) {
		int ret;
		long start = System.currentTimeMillis();
		/*
		 * ret = SendData(SndData,nLength); if(ret != 0) return ret; ret =
		 * RecvData(RcvData); if(ret <= 0) return ERR_RECVFORM_HSM;
		 */

		// HsmApp.OutputDataHex("Send Data",SndData,nLength);
		ret = SendData(SndData, nLength);
		if (ret != 0) { // fail to send data, get another connection to send the
						// data
			logger.error(this.getClass().getName(),(new StringBuffer("HandleID[")).append(iCurIdx).append(
					"] send data failed and for 1st time retry"));
			iCurIdx = getSession(iCurIdx);
			if (iCurIdx == -1)
				return ERR_HANDLE_FAULT;
			ret = SendData(SndData, nLength);
			if (ret != 0)
				return ret;
		}

		ret = RecvData(RcvData);
		if (ret <= 0) {// Receive timeout or error, get another connection to do
						// the job
			logger.error(this.getClass().getName(),(new StringBuffer("HandleID[")).append(iCurIdx).append(
					"] rcv data failed and for 2nd time retry"));
			iCurIdx = getSession(iCurIdx);
			if (iCurIdx == -1)
				return ERR_HANDLE_FAULT;
			ret = SendData(SndData, nLength);
			if (ret == 0) {
				if ((ret = RecvData(RcvData)) <= 0)
					return ERR_RECVFORM_HSM;
			} else {
				return ERR_SENDTO_HSM;
			}
		}
		// HsmApp.OutputDataHex("Receive Data",RcvData,ret);
		if (RcvData[0] != 'A') {
			iLastErr = RcvData[1] & 0xff;
		} else
			iLastErr = 0;

		logger.info((new StringBuffer("Hsm SndAndRcvData cost [")).append(System.currentTimeMillis() - start)
				.append("] msec").toString());
		return iLastErr;
	}

	public int HsmSessionEnd() {
		if (iCurIdx != -1) {
			SocketConnectHandle tHandle = sHandles[iCurIdx];
			if (tHandle.isUsed()) {
				logger.info((new StringBuffer("HandleID[")).append(iCurIdx)
						.append("] connection is released to connection pool").toString());
				tHandle.setNotused();
				iLastErr = 0;
			}
		}
		return iLastErr;
	}

	public static synchronized void destroy() {
		logger.info("session start to destory and release connections");
		if (sHandles != null) {
			for (int i = 0; i < sHandles.length; i++) {
				SocketConnectHandle tHandle = sHandles[i];
				if (!tHandle.isFault()) {
					tHandle.releaseSocketHandle();
				}
			}
			sHandles = null;
		}
		logger.info("session  destory completed");
	}

}
