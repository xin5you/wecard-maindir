package com.cn.thinkx.pms.connect.mina.code;

import java.net.InetSocketAddress;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.CumulativeProtocolDecoder;
import org.apache.mina.filter.codec.ProtocolDecoderOutput;

import com.cn.thinkx.pms.connect.entity.CommMessage;
import com.cn.thinkx.pms.connect.mina.AppObjectProcessor;
import com.cn.thinkx.pms.connect.mina.CommPackageProcessor;
import com.cn.thinkx.pms.connect.mina.PackageSecurityProcessor;

public class PackageDecoder extends CumulativeProtocolDecoder {

	AppObjectProcessor objMsgProcessor;

	CommPackageProcessor commPackageProcessor;

	PackageSecurityProcessor packageSecurityProcessor = null;

	@Override
	protected boolean doDecode(IoSession Session, IoBuffer in, ProtocolDecoderOutput out) throws Exception {

		in.mark();
		CommMessage commMessage = commPackageProcessor.commDecode(in);
		if (commMessage == null) {
			in.reset();
			return false;
		}

		// set message object for comm message
		if (objMsgProcessor != null) {
			commMessage.setMessageObject(objMsgProcessor.msg2obj(commMessage.getMessagbody()));
		}

		// set ip and port for comm message
		InetSocketAddress address = (InetSocketAddress) Session.getServiceAddress();
		commMessage.setRemoteip(address.getAddress().getHostAddress());
		commMessage.setRemoteport(address.getPort());

		if (packageSecurityProcessor != null) {
			boolean checkMac = packageSecurityProcessor.isValidMac(commMessage);
			if (checkMac == false) {
				throw new Exception("Mac check error");
			}
		}

		out.write(commMessage);
		return true;
	}

	public void setObjMsgProcessor(AppObjectProcessor objMsgProcessor) {
		this.objMsgProcessor = objMsgProcessor;
	}

	public void setCommPackageProcessor(CommPackageProcessor commPackageProcessor) {
		this.commPackageProcessor = commPackageProcessor;
	}

	public void setPackageSecurityProcessor(PackageSecurityProcessor packageSecurityProcessor) {
		this.packageSecurityProcessor = packageSecurityProcessor;
	}

}
