package com.cn.thinkx.pms.connect.mina.filter;

//import org.apache.log4j.Level;   
//import org.apache.log4j.Logger; 
import java.nio.charset.Charset;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.filterchain.IoFilterAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.core.write.WriteRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cn.thinkx.pms.base.utils.StringUtil;
import com.cn.thinkx.pms.connect.entity.CommMessage;

public class PackLogFilter extends IoFilterAdapter {

	Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public void exceptionCaught(NextFilter nextFilter, IoSession session, Throwable cause) throws Exception {
		logger.error("received msg : + ", cause);
		nextFilter.exceptionCaught(session, cause);
	}

	@Override
	public void messageReceived(NextFilter nextFilter, IoSession session, Object message) throws Exception {
		logger.info("received msg :\r\n " + getMessFromObj(message,"HEX"));
		nextFilter.messageReceived(session, message);
	}

	@Override
	public void messageSent(NextFilter nextFilter, IoSession session, WriteRequest writeRequest) throws Exception {
//		logger.info("sent msg :\r\n " + writeRequest.getMessage());
		nextFilter.messageSent(session, writeRequest);
	}

	@Override
	public void sessionCreated(NextFilter nextFilter, IoSession session) throws Exception {
		logger.info("create session is ok !");
		nextFilter.sessionCreated(session);
	}

	@Override
	public void sessionOpened(NextFilter nextFilter, IoSession session) throws Exception {
		logger.info("open session is ok !");
		nextFilter.sessionOpened(session);
	}

	@Override
	public void sessionIdle(NextFilter nextFilter, IoSession session, IdleStatus status) throws Exception {
		logger.info("now sesion is idle");
		nextFilter.sessionIdle(session, status);
	}

	@Override
	public void sessionClosed(NextFilter nextFilter, IoSession session) throws Exception {
		if (nextFilter != null) {
			logger.info("sesion is closed");
			nextFilter.sessionClosed(session);
		}
	}
	private Object getMessFromObj(Object message ,String outMessType) {
		String messageStr = null;
		byte[] messBytes = null;
		// StringBuffer
		if (message instanceof CommMessage) {
			CommMessage outmessage = (CommMessage) message;
			messBytes = outmessage.getMessagbody();
		} else if (message instanceof IoBuffer) {
			IoBuffer messBuffer = (IoBuffer) message;
			int messLen = messBuffer.remaining();
			messBytes = new byte[messLen];
			messBuffer.get(messBytes, 0, messLen);
			// messageStr = DataTransTools.bytesToHexString(messBytes);
			messBuffer.position(0);
		} else {
			return "Unknown original message type";
		}
		if (outMessType.equals("HEX")) {
			messageStr = "[len=" + messBytes.length + ", content=" + StringUtil.bytesToHexString(messBytes) + "]";
			return messageStr;
		} else if (outMessType.equals("STR")) {
			messageStr = "[len=" + messBytes.length + ", content=" + new String(messBytes, Charset.forName("us-ascii"))
					+ "]";
			return messageStr;
		} else {
			return "Unknown out message type:" + outMessType;
		}
	}
}
