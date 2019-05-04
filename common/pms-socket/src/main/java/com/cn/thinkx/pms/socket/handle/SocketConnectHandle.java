package com.cn.thinkx.pms.socket.handle;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SocketConnectHandle {
	private static Logger logger = LoggerFactory.getLogger(SocketConnectHandle.class);
	public final int FLAG_NOTUSE = 0;
	public final int FLAG_USED = 1;
	public final int FLAG_FAULT = 2;
	private Socket iSocketHandle = null;

	private int iStatus;

	private InputStream iInputStream = null;
	private OutputStream iOutputStream = null;
	private String strIP = null;

	private int iPort = -1;
	private int iTimeout = -1;

	/**
	 * socket调用(加密机)
	 * @param aIP 目标端IP
	 * @param aPort 目标端端口
	 * @param aTimeout 连接超时时间
	 */
	public SocketConnectHandle(String aIP, int aPort, int aTimeout) {
		strIP = new String(aIP);
		iPort = aPort;
		iTimeout = aTimeout;
		connect();
	}

	public void connect() {
		try {
			iSocketHandle = new Socket();
			InetSocketAddress hsmAddr = new InetSocketAddress(strIP, iPort);
			iSocketHandle.setOOBInline(true);
			iSocketHandle.connect(hsmAddr, iTimeout);
			iSocketHandle.setSoTimeout(iTimeout);
			iSocketHandle.setTcpNoDelay(true);
			iSocketHandle.setReceiveBufferSize(2048);
			iInputStream = iSocketHandle.getInputStream();
			iOutputStream = iSocketHandle.getOutputStream();

			testCmd00();
			setNotused();
		} catch (IOException e) {
			logger.error(this.getClass().getName(),e);
			setFault();
			releaseSocketHandle();
		}
	}

	public void releaseSocketHandle() {
		setFault();
		if (iInputStream != null) {
			try {
				iInputStream.close();
			} catch (Exception e) {
			}
			iInputStream = null;
		}
		if (iOutputStream != null) {
			try {
				iOutputStream.close();
			} catch (Exception e) {
			}
			iOutputStream = null;
		}

		if (iSocketHandle != null) {
			try {
				iSocketHandle.close();
			} catch (Exception e) {
			}
			iSocketHandle = null;
		}
	}

	public void write(byte[] aByteOut, int aLength) throws IOException {
		iOutputStream.write(aByteOut, 0, aLength);
	}

	public int read(byte[] aByteIn, int aBufferSize) throws IOException {
		return iInputStream.read(aByteIn, 0, aBufferSize);
	}

	public void testCmd00() throws IOException {
		byte[] comm_data = new byte[64];
		write(comm_data, 1);
		if (read(comm_data, 64) <= 0)
			throw new IOException();
	}

	public String getIP() {
		return strIP;
	}

	public void setUsed() {
		iStatus = FLAG_USED;
	}

	public void setNotused() {
		iStatus = FLAG_NOTUSE;
	}

	public void setFault() {
		iStatus = FLAG_FAULT;
	}

	public int getStatus() {
		return iStatus;
	}

	public boolean isUsed() {
		return (iStatus == FLAG_USED);
	}

	public boolean isUsable() {
		return (iStatus == FLAG_NOTUSE);
	}

	public boolean isFault() {
		return (iStatus == FLAG_FAULT);
	}
}
