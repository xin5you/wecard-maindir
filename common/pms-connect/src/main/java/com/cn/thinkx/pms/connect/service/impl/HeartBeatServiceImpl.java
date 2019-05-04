package com.cn.thinkx.pms.connect.service.impl;

import java.net.InetSocketAddress;

import javax.annotation.Resource;

import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.cn.thinkx.pms.connect.entity.TxnPackageDTO;
import com.cn.thinkx.pms.connect.service.HeartBeatService;
import com.cn.thinkx.pms.connect.service.Java2TXNBusinessService;

@Service
public class HeartBeatServiceImpl implements HeartBeatService {

	private Java2TXNBusinessService java2TXNBusinessService;
	
	@Resource
	private NioSocketAcceptor acceptorServer; 

	Logger log = LoggerFactory.getLogger(this.getClass());

	public void procHeartBeatTask() {
		InetSocketAddress address = acceptorServer.getLocalAddress(); 
		if (address == null) {
			log.info("procHeartBeatTask()--->mina listener havn't inited");
		} else {
			log.info("heart beat service start ");
			TxnPackageDTO txnPackageDTO = new TxnPackageDTO();
			try {
				Boolean flag = java2TXNBusinessService.heartBeatTransation(txnPackageDTO);
				if (flag) {
					log.info("This heart beat is successful");
				} else {
					log.info("This heart beat is faliuer");
				}
				log.info("heart beat service end ");
			} catch (Exception e) {
				log.error(this.getClass().getName(),e);
			}
		}
	}

	public void setJava2TXNBusinessService(Java2TXNBusinessService java2txnBusinessService) {
		java2TXNBusinessService = java2txnBusinessService;
	}

}
