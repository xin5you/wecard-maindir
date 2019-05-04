package com.cn.thinkx.pms;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IoSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MyIoHandler extends IoHandlerAdapter{
	private final static Logger log = LoggerFactory
			.getLogger(MyIoHandler.class);

	@Override
	public void sessionOpened(IoSession session) throws Exception {
		
	}

	@Override
	public void sessionClosed(IoSession session) throws Exception {
		
	}

	@Override
	public void messageReceived(IoSession session, Object message)
			throws Exception {
		String ip = session.getRemoteAddress().toString();
		log.info("===> Message From " + ip + " : " + message);		
	}
	
	

}
