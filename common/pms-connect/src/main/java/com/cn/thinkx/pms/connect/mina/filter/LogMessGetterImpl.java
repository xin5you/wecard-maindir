package com.cn.thinkx.pms.connect.mina.filter;

import java.nio.charset.Charset;

import org.apache.mina.core.buffer.IoBuffer;

import com.cn.thinkx.pms.base.utils.StringUtil;
import com.cn.thinkx.pms.connect.entity.CommMessage;
import com.cn.thinkx.pms.connect.mina.ILogMessGetter;

public class LogMessGetterImpl implements ILogMessGetter {

	public static final String outMessTypeHEX = "HEX";

	// public static final String outMessTypeHEXSEP = "HEXSEP";

	public static final String outMessTypeSTR = "STR";

	private String outMessType = outMessTypeHEX;

	public String getOutMessType() {
		return outMessType;
	}

	public void setOutMessType(String outMessType) {
		if (outMessType.equalsIgnoreCase(outMessTypeHEX) 
				|| outMessType.equalsIgnoreCase(outMessTypeSTR)) {
			this.outMessType = outMessType;
			return;
		}
		throw new IllegalArgumentException("Input out message type is not surpported");
	}

	public Object getMessFromObj(Object message) {
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

		if (outMessType.equals(outMessTypeHEX)) {
			messageStr = "[len=" + messBytes.length + ", content=" + StringUtil.bytesToHexString(messBytes) + "]";
			return messageStr;
		} else if (outMessType.equals(outMessTypeSTR)) {
			messageStr = "[len=" + messBytes.length + ", content=" + new String(messBytes, Charset.forName("us-ascii"))	+ "]";
			return messageStr;
		} else {
			return "Unknown out message type:" + outMessType;
		}
	}
}
