package com.cn.thinkx.pms;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cn.thinkx.pms.connect.entity.BizMessageObj;
import com.cn.thinkx.pms.connect.entity.CommMessage;
import com.cn.thinkx.pms.connect.mina.AppObjectProcessor;
import com.cn.thinkx.pms.connect.mina.CommPackageProcessor;
import com.cn.thinkx.pms.connect.mina.processor.AppObjectProcessorImpl;
import com.cn.thinkx.pms.connect.mina.processor.HeadLenCommPackProcessorImpl;
import com.cn.thinkx.pms.connect.utils.ConnectConstant;
import com.cn.thinkx.pms.connect.utils.HSTConstants;
/**
 * The Class SemaphoreServerHandler.
 */
@Service
public class SemaphoreServerHandler extends IoHandlerAdapter {
	

	/** The logger. */
	Logger logger = LoggerFactory.getLogger(this.getClass());

	/** The shortconnect. */
	private boolean shortconnect;
	
	/**
	 * Checks if is shortconnect.
	 * 
	 * @return true, if is shortconnect
	 */
	public boolean isShortconnect() {
		return shortconnect;
	}

	/**
	 * Sets the shortconnect.
	 * 
	 * @param shortconnect
	 *            the new shortconnect
	 */
	public void setShortconnect(boolean shortconnect) {
		this.shortconnect = shortconnect;
	}

	@Override
	public void sessionClosed(IoSession iosession) throws Exception {
		String remoteIp = (String) iosession.getAttribute(HSTConstants.REMOTE_IP);
		String remotePort = (String) iosession.getAttribute(HSTConstants.REMOTE_PORT);
		String connectionNm = (String) iosession.getAttribute(HSTConstants.CONNECTION_NM);
		iosession.close(true);
		logger.info("session " + connectionNm + " closed, remote ip is: [" + remoteIp + "], remote port is: ["
				+ remotePort + "]");
	}

	@Override
	public void sessionCreated(IoSession iosession) throws Exception {
		InetSocketAddress address = (InetSocketAddress) iosession.getRemoteAddress();
		String remoteIp = address.getAddress().getHostAddress();
		String remotePort = String.valueOf(address.getPort());
		iosession.setAttribute(HSTConstants.REMOTE_IP, remoteIp);
		iosession.setAttribute(HSTConstants.REMOTE_PORT, remotePort);
		logger.info("session created, remote ip is: [" + remoteIp + "], remote port is: [" + remotePort + "]");

	}

	/**
	 * Trap exceptions.
	 * 
	 * @param session
	 *            the session
	 * @param cause
	 *            the cause
	 * 
	 * @throws Exception
	 *             the exception
	 */
	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		// client 关断时也会触发该异常
		cause.printStackTrace();
		logger.error(cause.getLocalizedMessage(), cause);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.apache.mina.core.service.IoHandlerAdapter#messageReceived(org.apache
	 * .mina.core.session.IoSession, java.lang.Object)
	 */
	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		logger.info("messageReceived");
		CommMessage recvmessage = (CommMessage) message;
		if (recvmessage == null) {
			logger.info(" recvmessagefromserver error,sessionid is [" + session.getId() + "]");
			return;
		}
		// 得到通讯报文中包号
		String sendkey = ((BizMessageObj) recvmessage.getMessageObject()).getPackageNo();
		logger.info("recieved message seqno is : " + sendkey);
		BizMessageObj bo = (BizMessageObj) recvmessage.getMessageObject();
				bo.setServiceName(ConnectConstant.VTXN);
//		session.write(recvmessage);
		sendMessage(recvmessage);
//		messageSent(session, recvmessage);
	}
	public void sendMessage(CommMessage commMessage ) {
		Socket socket = null;
		BufferedReader br = null;
		PrintWriter pw = null;
		try {
			//客户端socket指定服务器的地址和端口号
			socket = new Socket("127.0.0.1", 10102);
			System.out.println("Socket=" + socket);
			//同服务器原理一样
			br = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(
					socket.getOutputStream())));
			AppObjectProcessor objMsgProcessor = new AppObjectProcessorImpl(); 
			if (objMsgProcessor != null) {
				byte[] outBytes = objMsgProcessor.obj2msg(commMessage);
				byte[] msg = new byte[outBytes.length-104];
				System.arraycopy(outBytes, 0, msg, 104, msg.length-104);
				if (outBytes != null) {
					commMessage.setMessagbody(msg);
					commMessage.setLength(outBytes.length-104);
				}

			}
			CommPackageProcessor commPackageProcessor = new HeadLenCommPackProcessorImpl();
			IoBuffer outBuffer = commPackageProcessor.commEncode(commMessage);
			pw.println(outBuffer);
			pw.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				System.out.println("close......");
				br.close();
				pw.close();
				socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		logger.info("sending message");
		// logger.info("Message len is" + ((CommMessage)message).getLength());
		super.messageSent(session, message);
	}

	/**
	 * On idle, we just write a message on the console.
	 * 
	 * @param session
	 *            the session
	 * @param status
	 *            the status
	 * 
	 * @throws Exception
	 *             the exception
	 */
	@Override
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
	}

}
