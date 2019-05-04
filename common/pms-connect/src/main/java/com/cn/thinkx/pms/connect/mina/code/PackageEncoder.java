package com.cn.thinkx.pms.connect.mina.code;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import com.cn.thinkx.pms.connect.entity.CommMessage;
import com.cn.thinkx.pms.connect.mina.AppObjectProcessor;
import com.cn.thinkx.pms.connect.mina.CommPackageProcessor;
import com.cn.thinkx.pms.connect.mina.PackageSecurityProcessor;

public class PackageEncoder extends ProtocolEncoderAdapter {

	AppObjectProcessor objMsgProcessor;

	CommPackageProcessor commPackageProcessor;

	PackageSecurityProcessor packageSecurityProcessor;

	public void encode(IoSession Session, Object message, ProtocolEncoderOutput out) throws Exception {

		CommMessage commMessage = (CommMessage) message;
		if (objMsgProcessor != null) {
			byte[] outBytes = objMsgProcessor.obj2msg(commMessage);
			if (outBytes != null) {
				commMessage.setMessagbody(outBytes);
				commMessage.setLength(outBytes.length);
			}

		}
		IoBuffer outBuffer = commPackageProcessor.commEncode(commMessage);
		if (packageSecurityProcessor != null) {
			packageSecurityProcessor.generateMac(commMessage);
			outBuffer.clear();
			outBuffer.put(commMessage.getOriginMessag());
		}
		outBuffer.flip();
		out.write(outBuffer);
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
