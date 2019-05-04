package com.cn.thinkx.pms.connect.mina.code;

import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.session.IoSession;
import org.apache.mina.filter.codec.ProtocolEncoderAdapter;
import org.apache.mina.filter.codec.ProtocolEncoderOutput;

import com.cn.thinkx.pms.connect.entity.CommMessage;
import com.cn.thinkx.pms.connect.utils.ProtocolCodecConst;

public class HeadPadLenPackageEncoder extends ProtocolEncoderAdapter {

	// length type: "N"-number string, "BCD"-number string with bcd compress,
	// "Bi"-binary data
	private String lenFieldType = ProtocolCodecConst.lenTypeNumStr;
	private int lenFieldLen = 4;
	private boolean includeLenField = false;

	public String getLenFieldType() {
		return lenFieldType;
	}

	public void setLenFieldType(String lenFieldType) {
		this.lenFieldType = lenFieldType;
	}

	public int getLenFieldLen() {
		return lenFieldLen;
	}

	public void setLenFieldLen(int lenFieldLen) {
		this.lenFieldLen = lenFieldLen;
	}

	public boolean isIncludeLenField() {
		return includeLenField;
	}

	public void setIncludeLenField(boolean includeLenField) {
		this.includeLenField = includeLenField;
	}

	public void encode(IoSession Session, Object message, ProtocolEncoderOutput out) throws Exception {

		IoBuffer buffer = IoBuffer.allocate(1024);
		buffer.setAutoExpand(true);

		CommMessage outmessage = (CommMessage) message;
		int bytesize = outmessage.getLength();
		//
		putPackLenIntoIoBuffer(buffer, bytesize);

		buffer.put(outmessage.getMessagbody());
		buffer.flip();
		// System.out.println("start write back");
		out.write(buffer);
		// System.out.println("end write back");
	}
	private void putPackLenIntoIoBuffer(IoBuffer buffer, int messageSize) throws Exception {
		if (messageSize <= 0) {
			throw new Exception("message length is not correct, which is less than zero");
		}

		int maxSize = 0;

		if (lenFieldType.equals(ProtocolCodecConst.lenTypeNumStr)) {
			Charset set = Charset.forName("us-ascii");
			CharsetEncoder encoder = set.newEncoder();
			maxSize = (int) Math.pow(10, lenFieldLen);
			if (messageSize > maxSize) {
				throw new Exception("message length is too long, which is more than " + maxSize);
			}

			String formatStr = String.format("%%0%dd", lenFieldLen);
			String lenStr = String.format(formatStr, messageSize);

			buffer.putString(lenStr, encoder);
		} else if (lenFieldType.equals(ProtocolCodecConst.lenTypeBinary)) {
			byte[] body = new byte[lenFieldLen];
			byte byteLen = 0;
			int sizeItr = messageSize;
			for (int i = 0; i < lenFieldLen; i++) {
				byteLen = (byte) (sizeItr % 256);
				body[lenFieldLen - i - 1] = byteLen;
				sizeItr = sizeItr / 256;
			}
			buffer.put(body);
		} else if (lenFieldType.equals(ProtocolCodecConst.lenTypeBCD)) {
			byte[] body = new byte[lenFieldLen];
			byte byteLenLow = 0;
			byte byteLenHigh = 0;
			byte byteLen = 0;
			int sizeItr = messageSize;
			for (int i = 0; i < lenFieldLen; i++) {
				byteLenLow = (byte) (sizeItr % 10);
				sizeItr = sizeItr / 10;
				byteLenHigh = (byte) (sizeItr % 10);
				sizeItr = sizeItr / 10;

				byteLen = (byte) (byteLenHigh * 16 + byteLenLow);

				body[lenFieldLen - i - 1] = byteLen;
			}
			buffer.put(body);
		}
	}

}
