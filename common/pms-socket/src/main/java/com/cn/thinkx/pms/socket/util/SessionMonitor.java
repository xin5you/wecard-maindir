package com.cn.thinkx.pms.socket.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cn.thinkx.pms.socket.handle.SocketConnectHandle;

public class SessionMonitor extends Thread {
	Logger logger = LoggerFactory.getLogger(SessionMonitor.class);
	private static SocketConnectHandle[] sHandle;

	public void addHandle(SocketConnectHandle[] aHandle) {
		sHandle = aHandle;
	}

	public void run() {
		int i, j = 0;

		while (true) {
			try {
				sleep(10000);
			} catch (Exception e) {
			}

			for (i = 0; i < sHandle.length; i++) {
				if (sHandle[i].isFault()) {
					try {
						sHandle[i].releaseSocketHandle();
						sHandle[i].connect();
						logger.info((new StringBuffer("HSM SessionMonitor.run() - reconnect the fault HandleID["))
								.append(i).append("]").toString());
					} catch (Throwable t) {
						logger.error("HSM SessionMonitor.run() - " + t.getMessage());
					}
				}
			}

			j++;
			// 10 second * 6 * 60 = 1 hour
			if (j == 6 * 60) {
				j = 0;
				for (i = 0; i < sHandle.length; i++) {
					if (sHandle[i].isUsable()) {
						sHandle[i].setUsed();
						try {
							sHandle[i].testCmd00();
							sHandle[i].setNotused();
						} catch (Exception e) {
							sHandle[i].setFault();
						} catch (Error err) {
							sHandle[i].setFault();
						}
					}
				}
			}
		}
	}
}
