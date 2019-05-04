package com.cn.thinkx.pms;
import java.io.IOException;
import java.net.InetSocketAddress;

import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolCodecFilter;
import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
import org.apache.mina.filter.keepalive.KeepAliveFilter;
import org.apache.mina.filter.keepalive.KeepAliveMessageFactory;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Server {

	private static final int PORT = 10404;
	/** 30秒后超时 */
	private static final int IDELTIMEOUT = 30;
	/** 15秒发送一次心跳包 */
	private static final int HEARTBEATRATE = 15;
	/** 心跳包内容 */
	private static final String HEARTBEATREQUEST = "0x11";
	private static final String HEARTBEATRESPONSE = "0x12";
	private static final Logger LOG = LoggerFactory.getLogger(Server.class);

	public static void main(String[] args) throws IOException {
		IoAcceptor acceptor = new NioSocketAcceptor();
		acceptor.getSessionConfig().setReadBufferSize(1024);
		acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE,
				IDELTIMEOUT);
		
		acceptor.getFilterChain().addLast("logger", new LoggingFilter());
		acceptor.getFilterChain().addLast(
				"codec",
				new ProtocolCodecFilter(new TextLineCodecFactory()));

		KeepAliveMessageFactory heartBeatFactory = new KeepAliveMessageFactoryImpl();
		//下面注释掉的是自定义Handler方式
//		KeepAliveRequestTimeoutHandler heartBeatHandler = new 
//								KeepAliveRequestTimeoutHandlerImpl();
//		KeepAliveFilter heartBeat = new KeepAliveFilter(heartBeatFactory,
//				IdleStatus.BOTH_IDLE, heartBeatHandler);
		
		KeepAliveFilter heartBeat = new KeepAliveFilter(heartBeatFactory,
				IdleStatus.BOTH_IDLE);

		//设置是否forward到下一个filter
		heartBeat.setForwardEvent(true);
		//设置心跳频率
		heartBeat.setRequestInterval(HEARTBEATRATE);

		acceptor.getFilterChain().addLast("heartbeat", heartBeat);

		acceptor.setHandler(new MyIoHandler());
		acceptor.bind(new InetSocketAddress(PORT));
		System.out.println("Server started on port： " + PORT);
	}

	/**
	 * @ClassName KeepAliveMessageFactoryImpl
	 * @Description 内部类，实现KeepAliveMessageFactory（心跳工厂）
	 * @author cruise
	 *
	 */
	private static class KeepAliveMessageFactoryImpl implements
			KeepAliveMessageFactory {

		@Override
		public boolean isRequest(IoSession session, Object message) {
			LOG.info("请求心跳包信息: " + message);
			if (message.equals(HEARTBEATREQUEST))
				return true;
			return false;
		}

		@Override
		public boolean isResponse(IoSession session, Object message) {
//			LOG.info("响应心跳包信息: " + message);
//			if(message.equals(HEARTBEATRESPONSE))
//				return true;
			return false;
		}

		@Override
		public Object getRequest(IoSession session) {
			LOG.info("请求预设信息: " + HEARTBEATREQUEST);
			/** 返回预设语句 */
			return HEARTBEATREQUEST;
		}

		@Override
		public Object getResponse(IoSession session, Object request) {
			LOG.info("响应预设信息: " + HEARTBEATRESPONSE);
			/** 返回预设语句 */
			return HEARTBEATRESPONSE;
//			return null;
		}

	}
	
	/**
	 * 对应上面的注释
	 * KeepAliveFilter(heartBeatFactory,IdleStatus.BOTH_IDLE,heartBeatHandler)
	 * 心跳超时处理
	 * KeepAliveFilter 在没有收到心跳消息的响应时，会报告给的KeepAliveRequestTimeoutHandler。
	 * 默认的处理是 KeepAliveRequestTimeoutHandler.CLOSE
	 * （即如果不给handler参数，则会使用默认的从而Close这个Session）
	 * @author cruise
	 *
	 */

//	private static class KeepAliveRequestTimeoutHandlerImpl implements
//			KeepAliveRequestTimeoutHandler {
//
//	
//		@Override
//		public void keepAliveRequestTimedOut(KeepAliveFilter filter,
//				IoSession session) throws Exception {
//			Server.LOG.info("心跳超时！");
//		}
//
//	}
}
