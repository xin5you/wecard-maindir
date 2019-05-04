package com.cn.thinkx.pms;

import java.net.ConnectException;
import java.net.InetSocketAddress;

import org.apache.mina.core.RuntimeIoException;
import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.transport.socket.SocketConnector;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

public class MinaClient {
	public SocketConnector socketConnector;

	/**
	 * 缺省连接超时时间
	 */
	public static final int DEFAULT_CONNECT_TIMEOUT = 5;

	public static final String HOST = "localhost";

	public static final int PORT = 6003;

	public MinaClient() {
		init();
	}

	public void init() {
		socketConnector = new NioSocketConnector();

		socketConnector.getSessionConfig().setKeepAlive(true);

		socketConnector.getFilterChain().addLast("codec", new ProtocolCodecFilter(new TextLineCodecFactory()));

		ClientIoHandler ioHandler = new ClientIoHandler();
		socketConnector.setHandler(ioHandler);
	}

	public void sendMessage(final String msg) {
		InetSocketAddress addr = new InetSocketAddress(HOST, PORT);
		ConnectFuture cf = socketConnector.connect(addr);
		try {
			cf.awaitUninterruptibly();
			cf.getSession().write(msg);
			System.out.println("send message " + msg);
		} catch (RuntimeIoException e) {
			if (e.getCause() instanceof ConnectException) {
				try {
					if (cf.isConnected()) {
						cf.getSession().close(false);
					}
				} catch (RuntimeIoException e1) {
				}
			}
		}
	}

	public static void main(String[] args) throws InterruptedException {
		String msg = "004860000000486016|vPatchResp|vTxn000004S2200082014112400615174100820141124008600000010000120000008319420000000000000130810000050.  015272120000000000008000000140302721300518000850153=1802201225013100100000000.019272130051800085015300000000810009420000000000000000016C205D449F9B5141B00000200006      012            000012............00000000000000010820202020202020202020202020202020202020202020202020202020202020202020202020202020202020202020202014112415174100000000000000000000000000000000";
		MinaClient clent = new MinaClient();
		clent.sendMessage(msg);
		// clent.getSocketConnector().dispose();
		// System.exit(0);
	}

	public SocketConnector getSocketConnector() {
		return socketConnector;
	}

	public void setSocketConnector(SocketConnector socketConnector) {
		this.socketConnector = socketConnector;
	}

}

class ClientIoHandler extends IoHandlerAdapter {

	private void releaseSession(IoSession session) throws Exception {
		System.out.println("releaseSession");
		if (session.isConnected()) {
			session.close(false);
		}
	}

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		System.out.println("sessionOpened");
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		System.out.println("sessionClosed");
	}

	@Override
	public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
		System.out.println("sessionIdle");
		try {
			releaseSession(session);
		} catch (RuntimeIoException e) {
		}
	}

	@Override
	public void messageReceived(IoSession session, Object message) throws Exception {
		System.out.println("Receive Server message " + message);

		super.messageReceived(session, message);

		releaseSession(session);
	}

	@Override
	public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
		System.out.println("exceptionCaught");
		cause.printStackTrace();
		releaseSession(session);
	}

	@Override
	public void messageSent(IoSession session, Object message) throws Exception {
		System.out.println("messageSent");
		super.messageSent(session, message);
	}

}